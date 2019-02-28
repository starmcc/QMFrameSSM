package com.qm.frame.basic.config;

import com.qm.frame.basic.exception.QmFrameException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Copyright © 2019浅梦工作室. All rights reserved.
 *
 * @author 浅梦
 * @date 2019/1/30 19:36
 * @Description
 */
public class QmFrameContent {

    private final static Properties PRO = getProperties();

    /**
     * 是否启用AES对称加密传输
     */
    public final static boolean REQUEST_BODY_AES_START = Boolean.parseBoolean(PRO.getProperty("request.body.aes.start","false"));
    /**
     * AES秘钥
     */
    public final static String REQUEST_BODY_AES_KEY = PRO.getProperty("request.body.aes.key","20190101000000qmframe");
    /**
     * 统一使用的编码方式
     */
    public final static String REQUEST_BODY_AES_ENCODING = PRO.getProperty("request.body.aes.encoding","UTF-8");
    /**
     * 加密次数
     */
    public final static int REQUEST_BODY_AES_NUMBER = Integer.parseInt(PRO.getProperty("request.body.aes.number","1"));
    /**
     * 请求数据时，根据该key名解析数据(rest风格)
     */
    public final static String REQUEST_BODY_KEY = PRO.getProperty("request.body.key","value");
    /**
     * 返回数据时，使用的最外层key名(rest风格)
     */
    public final static String RESPONSE_BODY_KEY = PRO.getProperty("response.body.key","value");
    /**
     * 特殊请求不进行解析(包括解析json等)
     * 该配置主要排除第三方API调用接口时特殊请求而框架自动解析json的问题
     * 适用于动态配置，例：/user/**
     */
    public final static List<String> REQUEST_SPECIAL_URI = getRequestSpecialUri();

    /**
     * 记录日志类路径
     */
    public final static String LOGGER_AOP_EXTEND_CLASS = PRO.getProperty("controller.aop.extend.class","");


    /**
     * 获取特殊请求
     * @return
     */
    private final static List<String> getRequestSpecialUri(){
        List<String> specialUriList = new ArrayList<>();
        boolean is = true;
        int num = 0;
        while (is){
            String tempVersion = PRO.getProperty("request.special.uri-[" + num + "]",null);
            if (tempVersion != null) {
                specialUriList.add(tempVersion);
            }else{
                is = false;
            }
            num++;
        }
        return specialUriList;
    }

    private final static Properties getProperties(){
        try {
            Properties properties = new Properties();
            // 读取properties文件,使用InputStreamReader字符流防止文件中出现中文导致乱码
            InputStreamReader inStream = new InputStreamReader
                    (QmFrameContent.class.getClassLoader().getResourceAsStream("qm-frame.properties"),
                            "UTF-8");
            properties.load(inStream);
            inStream.close();
            return properties;
        } catch (Exception e) {
            throw new QmFrameException("读取qm-frame.properties发生了异常！",e);
        }
    }


}
