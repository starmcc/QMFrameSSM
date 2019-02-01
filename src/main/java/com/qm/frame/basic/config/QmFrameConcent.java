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
public class QmFrameConcent {

    private final static Properties PRO = getProperties();
    /**
     * 是否启用AES对称加密传输
     */
    public final static boolean BODY_AES_START = Boolean.parseBoolean(PRO.getProperty("body.aes.start","false"));
    /**
     * AES秘钥
     */
    public final static String BODY_AES_KEY = PRO.getProperty("body.aes.key","20190101000000qmframe");
    /**
     * 统一使用的编码方式
     */
    public final static String BODY_AES_ENCODING = PRO.getProperty("body.aes.encoding","UTF-8");
    /**
     * 加密次数
     */
    public final static int BODY_AES_NUMBER = Integer.parseInt(PRO.getProperty("body.aes.number","1"));
    /**
     * 请求数据时，根据该key名解析数据(rest风格)
     */
    public final static String BODY_REQUEST_KEY = PRO.getProperty("body.request.key","value");
    /**
     * 返回数据时，使用的最外层key名(rest风格)
     */
    public final static String BODY_RESPONSE_KEY = PRO.getProperty("body.response.key","value");
    /**
     * 记录日志类路径
     */
    public final static String LOGGER_AOP_EXTEND_CLASS = PRO.getProperty("controller.aop.extend.class","");


    private final static Properties getProperties(){
        try {
            Properties properties = new Properties();
            // 读取properties文件,使用InputStreamReader字符流防止文件中出现中文导致乱码
            InputStreamReader inStream = new InputStreamReader
                    (QmFrameConcent.class.getClassLoader().getResourceAsStream("qm-frame.properties"),
                            "UTF-8");
            properties.load(inStream);
            inStream.close();
            return properties;
        } catch (Exception e) {
            throw new QmFrameException("读取qm-frame.properties发生了异常！",e);
        }
    }


}
