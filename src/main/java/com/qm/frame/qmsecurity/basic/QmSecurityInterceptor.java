package com.qm.frame.qmsecurity.basic;

import com.qm.frame.basic.controller.QmCode;
import com.qm.frame.basic.controller.QmController;
import com.qm.frame.basic.util.HttpApiUtil;
import com.qm.frame.qmsecurity.config.QmSecurityContent;
import com.qm.frame.qmsecurity.entity.QmPermissions;
import com.qm.frame.qmsecurity.entity.QmTokenInfo;
import com.qm.frame.qmsecurity.exception.QmSecurityException;
import com.qm.frame.qmsecurity.manager.QmSecurityManager;
import com.qm.frame.qmsecurity.manager.UserListener;
import com.qm.frame.qmsecurity.note.QmPass;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Copyright © 2018浅梦工作室}. All rights reserved.
 *
 * @author 浅梦
 * @date 2018/12/22 16:56
 * @Description QmSecurity安全拦截器
 */
public class QmSecurityInterceptor extends QmController implements HandlerInterceptor {

    // 日志工具
    private static final Logger LOG = Logger.getLogger(QmSecurityInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException, ServletException {
        // 定义是否需要授权匹配，默认为true。当标注了@QmPass且用户给定needLogin为true时，则该值会变为false;
        boolean isPerssions = true;
        // 查找是否存在pass注解，如果存在则放过请求
        if (handler instanceof HandlerMethod) {
            // 获取方法对象
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 调用getAnnotation获取方法接口标注的QmPass注解的实例。
            QmPass pass = method.getAnnotation(QmPass.class);
            // 如果不为空则方法标注了该注解，进而判断其needLogin的值。
            if (pass != null){
                if (pass.needLogin()) {
                    // 当标注了@QmPass且用户给定needLogin为true时，则该值会变为false;
                    isPerssions = false;
                }else{
                    // 如果为false，则该请求不需要任何校验token和授权的过程，直接返回true;
                    return true;
                }
            }
        }
        if (QmSecurityContent.QMSECURITY_TYPE.trim().equalsIgnoreCase("session")) {
            // session 机制校验
            boolean is = verifySession(request,response,isPerssions);
            if (is == false) {
                return false;
            }
        }else if (QmSecurityContent.QMSECURITY_TYPE.trim().equalsIgnoreCase("token")) {
            // token 机制校验
            boolean is = verifyToken(request,response,isPerssions);
            if (is == false) {
                return false;
            }
        }else {
            throw new QmSecurityException("配置文件qm-security.properties中的qmsecurity.type读取了错误值。");
        }
        LOG.info("※※※通过QmSecurity");
        return true;
    }

    /**
     * session 校验机制
     * @param request
     * @param response
     * @param isPerssions
     * @return
     * @throws IOException
     */
    private boolean verifySession(HttpServletRequest request,HttpServletResponse response,boolean isPerssions) throws IOException, ServletException {
        try {
            UserListener userListener = (UserListener) session.getAttribute("QmSecurity_Session_User");
            if (userListener == null) {
                request.getRequestDispatcher(QmSecurityContent.SESSION_LOGIN_OUT_VIEW).forward(request,response);
                return false;
            }
            // 保存到作用域中提供直接缓存
            request.setAttribute(UserListener.class.getName(), userListener);
            // 该判断为如果标注了@QmPass且needLogin为true时，则isPerssions为false，就不会进入授权匹配了。
            if (isPerssions) {
                LOG.info("※※※正在进行授权访问匹配");
                // 获取请求路由
                String path = request.getServletPath();
                // 校验该角色是否存在匹配当前请求url的匹配规则。
                boolean is = QmSecurityBasic.verifyPermissions(path, userListener.getMatchUrls());
                if (!is) {
                    LOG.info("※※※权限不足,拒绝访问");
                    request.getRequestDispatcher(QmSecurityContent.SESSION_RIGHTS_ERROR_VIEW).forward(request,response);
                    return false;
                }
            }
        } catch (Exception e) {
            request.getRequestDispatcher(QmSecurityContent.SESSION_LOGIN_OUT_VIEW).forward(request,response);
            return false;
        }
        return true;
    }


    /**
     * token 校验机制
     * @param request
     * @param response
     * @param isPerssions
     * @return
     * @throws IOException
     */
    private boolean verifyToken(HttpServletRequest request,HttpServletResponse response,boolean isPerssions) throws IOException {
        // 从头部获取token字段
        String token = request.getHeader(QmSecurityContent.TOKEN_KEY_NAME);
        // 如果为空则直接拦截
        if (token == null) {
            LOG.info("※※※检测不到token拒绝访问");
            response.getWriter().write(super.sendJSON(QmCode._103));
            return false;
        }
        LOG.info("※※※正在验证Token是否正确");
        QmTokenInfo qmTokenInfo = QmSecurityBasic.verifyToken(token);
        if (qmTokenInfo == null) {
            LOG.info("※※※Token失效或已过期");
            response.getWriter().write(super.sendJSON(QmCode._105));
            return false;
        }
        LOG.info("※※※进行请求ip单点匹配");
        String requestIp = HttpApiUtil.getHttpIp(request);
        if (!requestIp.equals(qmTokenInfo.getRequestIp())) {
            LOG.info("※※※请求ip校验失败");
            response.getWriter().write(super.sendJSON(QmCode._104));
            return false;
        }
        // 保存到作用域中提供直接缓存
        request.setAttribute(QmTokenInfo.class.getName(),qmTokenInfo);
        // 该判断为如果标注了@QmPass且needLogin为true时，则isPerssions为false，就不会进入授权匹配了。
        if (isPerssions) {
            LOG.info("※※※正在进行授权访问匹配");
            int roleId = qmTokenInfo.getRoleId();
            // 获取请求路由
            String path = request.getServletPath();
            // 获取该角色的权限信息
            QmPermissions qmPermissions = QmSecurityManager.getQmbject().extractQmPermissions(roleId,false);
            // 校验该角色是否存在匹配当前请求url的匹配规则。
            boolean is = QmSecurityBasic.verifyPermissions(path,qmPermissions.getMatchUrls());
            if (!is) {
                LOG.info("※※※权限不足,拒绝访问");
                response.getWriter().write(super.sendJSON(QmCode._107));
                return false;
            }
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
