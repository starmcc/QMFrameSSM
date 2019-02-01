package com.qm.frame.qmsecurity.util;

import com.qm.frame.qmsecurity.config.QmSecurityContent;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Date;

/**
 * AES对称加密技术
 */
public class QmSecurityAESUtil {
    private static final Logger LOG = Logger.getLogger(QmSecurityAESUtil.class);
    /**
     * 加密封装
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptAES(String data) throws Exception {
        Date date = new Date();
        String str = data;
        for (int i = 0; i < QmSecurityContent.TOKEN_ENCRYPT_NUMBER; i++){
            str = encryptAES(str, QmSecurityContent.TOKEN_SECRET, QmSecurityContent.TOKEN_SECRET_ENCODING);
        }
        LOG.debug("加密用时：" + (new Date().getTime() - date.getTime()));
        return str;
    }
    /**
     * 解密封装
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptAES(String data) throws Exception {
        Date date = new Date();
        String str = data;
        for (int i = 0; i < QmSecurityContent.TOKEN_ENCRYPT_NUMBER; i++){
            str = decryptAES(str, QmSecurityContent.TOKEN_SECRET, QmSecurityContent.TOKEN_SECRET_ENCODING);
        }
        LOG.debug("解密用时：" + (new Date().getTime() - date.getTime()));
        return str;
    }


    /**
     * 加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static String encryptAES(String data,String key,String encoding) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes(encoding));
        String hexStr = Base64.encodeBase64String(encryptedData);
        return hexStr;
    }


    /**
     * 解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static String decryptAES(String data,String key,String encoding)throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128, new SecureRandom(key.getBytes()));
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decryptedData = cipher.doFinal(Base64.decodeBase64(data));
        String respStr = new String(decryptedData,encoding);
        return respStr;
    }


}