package com.qm.frame.basic.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qm.frame.basic.config.QmFrameConcent;
import com.qm.frame.basic.util.AESUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2018浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2018年11月24日 上午1:42:26
 * @Description 父类Controller, 编写Controller请继承该类。
 */
public @Component class QmController {

    private static final Logger LOG = Logger.getLogger(QmController.class);

    @Autowired
    protected HttpServletRequest request;
    @Autowired(required = false)
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    /**
     * 接口回调方法
     *
     * @param code QmCode
     * @return
     */
    public String sendJSON(QmCode code) {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("code",code.getCode());
        responseMap.put("msg",QmCode.getMsg(code));
        responseMap.put("data",null);
        responseMap.put("responseTime",new Date());
        return this.parseJsonToResponse(responseMap);
    }

    /**
     * 接口回调方法
     *
     * @param code QmCode
     * @param data 传递数据
     * @return
     */
    public String sendJSON(QmCode code, Object data) {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("code",code.getCode());
        responseMap.put("msg",QmCode.getMsg(code));
        responseMap.put("data",data);
        responseMap.put("responseTime",new Date());
        return this.parseJsonToResponse(responseMap);
    }

    /**
     * 接口回调方法
     *
     * @param code QmCode
     * @param msg  自定义消息
     * @param data 传递数据
     * @return
     */
    public String sendJSON(QmCode code, String msg, Object data) {
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("code",code.getCode());
        responseMap.put("msg",msg);
        responseMap.put("data",data);
        responseMap.put("responseTime",new Date());
        return this.parseJsonToResponse(responseMap);
    }


    /**
     * 转换json
     * @param responseMap
     * @return
     */
    private String parseJsonToResponse(Map<String,Object> responseMap){
        //SerializerFeature.WriteMapNullValue设置后,返回Bean时字段为空时默认返回null
        String value = JSONObject.toJSONString(responseMap, SerializerFeature.WriteMapNullValue);
        value = StringEscapeUtils.unescapeJava(value);
        try {
            if (QmFrameConcent.AES_START) {
                value = AESUtil.encryptAES(value);
                Map<String,String> resMap = new HashMap<>();
                resMap.put(QmFrameConcent.RESPONSE_DATA_KEY,value);
                return StringEscapeUtils.unescapeJava(JSONObject.toJSONString(resMap,SerializerFeature.WriteMapNullValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("加密失败");
        }
        Map<String,Map<String,Object>> resMap = new HashMap<>();
        resMap.put(QmFrameConcent.RESPONSE_DATA_KEY,responseMap);
        return StringEscapeUtils.unescapeJava(JSONObject.toJSONString(resMap,SerializerFeature.WriteMapNullValue));
    }
}
