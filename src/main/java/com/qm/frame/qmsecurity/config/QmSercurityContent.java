package com.qm.frame.qmsecurity.config;

import com.qm.frame.basic.config.QmFrameConcent;
import com.qm.frame.basic.exception.QmFrameException;
import com.qm.frame.qmsecurity.basic.QmSecurityRealm;
import com.qm.frame.qmsecurity.util.QmSecuritySpringApplication;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Copyright © 2018浅梦工作室}. All rights reserved.
 *
 * @author 浅梦
 * @date 2018/12/23 18:26
 * @Description 提供给调用者注入使用
 */
public class QmSercurityContent {


    private final static Properties PRO = getProperties();

    public final static String TOKEN_SECRET = PRO.getProperty("token.secret","token_secret");

    public final static String TOKEN_KEY_NAME =  PRO.getProperty("token.key.name","token");

    public final static String TOKEN_SECRET_ENCODING = PRO.getProperty("token.secret.encoding","UTF-8");

    public final static Integer TOKEN_ENCRYPT_NUMBER = Integer.parseInt(PRO.getProperty("token.encrypt.number","2"));

    public final static QmSecurityRealm SECURITY_REALM = getSecurityRealm();

    /**
     * 设置自定义的realm
     * realm.class.name
     * @return
     */
    private static QmSecurityRealm getSecurityRealm(){
        String className = PRO.getProperty("realm.class.name");

        if (className == null || className.trim().equals("")) {
            return null;
        }
        QmSecurityRealm realm = (QmSecurityRealm) QmSecuritySpringApplication.getBean(className);
        return realm;
    }

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
