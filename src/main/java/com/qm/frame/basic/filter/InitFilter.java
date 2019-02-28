package com.qm.frame.basic.filter;

import com.qm.frame.basic.config.QmFrameContent;
import com.qm.frame.basic.controller.QmController;
import com.qm.frame.basic.util.QmSpringManager;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Copyright © 2018浅梦工作室. All rights reserved.
 * @author 浅梦
 * @date 2018年11月24日 上午1:15:27
 * @Description 该过滤器主要实现版本控制、重写RequestBody、实现AES对称无痕解密
 */
@WebFilter(urlPatterns="/*",filterName="InitFilter")
@Order(1)
public @Component class InitFilter extends QmController implements Filter{
    /**
     * Logger slf4j
     */
    private static final Logger LOG = Logger.getLogger(InitFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("/n" + "请求URI：" + req.getRequestURI());
        //特殊请求
        if (verifySpecialURI(req)) {
            chain.doFilter(request,response);
            return;
        }
        /**
         * 重写RequestBody,并对body进行对称AES解密。
         */
        ServletRequest requestWrapper = new QmRequestWrapper(req);
        chain.doFilter(requestWrapper, response);
    }

    /**
     * 验证是否为特殊请求
     * @param request
     * @return
     */
    private boolean verifySpecialURI(HttpServletRequest request){
        for (String uri : QmFrameContent.REQUEST_SPECIAL_URI) {
            if (QmSpringManager.verifyMatchURI(uri,request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}