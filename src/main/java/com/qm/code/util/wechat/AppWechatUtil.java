package com.qm.code.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qm.code.entity.wechat.WeChatUserInfo;
import com.qm.code.entity.wechat.WechatSign;
import com.qm.code.util.base.RedisUtil;
import com.qm.code.util.io.PropertiesUtil;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月20日 上午12:33:19
 * @Description 微信公众号授权工具
 */
@SuppressWarnings("deprecation")
public class AppWechatUtil {

	/**
	 * APPID可在微信公众号中获取
	 */
 	public static final String APPID = PropertiesUtil.get("wechat.appid");
 	/**
 	 * APPSECRET可在微信公众号中获取
 	 */
    public static final String APPSECRET = PropertiesUtil.get("wechat.appsecret");
    /**
     * 回调地址，该地址是用户点击授权后，微信回调我们的地址，回调后返回一些必要的参数。
     */
    public static final String REDIRECT_URI = PropertiesUtil.get("wechat.redirect_uri");

    /**
     * 
     * 微信授权
     * @param response
     * @param REDIRECT_URI 回调地址
     * @throws Exception
     */
    public static void doWechatAuth(HttpServletResponse response) throws Exception {
    	System.out.println("进入微信授权");
    	StringBuffer url = new StringBuffer();
    	url.append("https://open.weixin.qq.com/connect/oauth2/authorize");
    	System.out.println("APPID：" + APPID);
    	url.append("?appid=" + APPID);
    	System.out.println("回调地址：" + URLEncoder.encode(REDIRECT_URI, "UTF-8"));
    	url.append("&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")); //重定向地址
    	url.append("&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
    	response.sendRedirect(url.toString());
    }
    
    
    /**
     * 微信获取用户信息
     * @param request
     * @return
     */
    public static WeChatUserInfo getWechatUserInfo(HttpServletRequest request) {
    	try {
			String code = request.getParameter("code");
			System.out.println("========Code===" + code);
			if (code == null) {
				return null;
			}
			StringBuffer url = new StringBuffer();
			url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
			url.append(APPID);
			url.append("&secret=");
			url.append(APPSECRET);
			url.append("&code=");
			url.append(code);
			url.append("&grant_type=authorization_code");
			JSONObject jsonObject = doGetJson(url.toString());
			WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
			String openId = jsonObject.getString("openid");
			if (openId == null || openId.trim().equals("")) {
				return null;
			}
			weChatUserInfo.setOpenId(openId);
			String token = jsonObject.getString("access_token");
			weChatUserInfo.setToken(token);
			StringBuffer infoUrl = new StringBuffer();
			infoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
			infoUrl.append(token);
			infoUrl.append("&openid=");
			infoUrl.append(openId);
			infoUrl.append("&lang=zh_CN");
			JSONObject userInfoJson = doGetJson(infoUrl.toString());
			System.out.println("微信用户信息如下：\n" + userInfoJson.toString());
			weChatUserInfo.setHeadImgUrl(userInfoJson.getString("headimgurl"));;
			weChatUserInfo.setNickName(userInfoJson.getString("nickname"));
			return weChatUserInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
    }
    
     /**
     * 请求获取JSON格式数据
     * @param url
     * @return
     */
    private static JSONObject doGetJson(String url){
    	try {
			JSONObject jsonObject=null;
			@SuppressWarnings({ "resource" })
			DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(url);
			HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
			HttpEntity httpEntity=httpResponse.getEntity();
			if(httpEntity!=null){
			    String result= EntityUtils.toString(httpEntity,"UTF-8");
			    new JSONObject();
				jsonObject = JSON.parseObject(result);
			    System.out.println("jsonObject:  "+jsonObject);
			}
			httpGet.releaseConnection();
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 获取微信签名
     * @param url
     * @return
     */
    public static WechatSign getSignInfo(String url){
		String jsapi_ticket = (String)RedisUtil.get("jsapi_ticket");
		if(jsapi_ticket == null || jsapi_ticket.trim().equals("")){
			jsapi_ticket = getJSApiTicket();
			RedisUtil.addValue("jsapi_ticket", jsapi_ticket,7000);
		}
		String nonce_str = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		StringBuffer signStr = new StringBuffer();
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		signStr.append("jsapi_ticket=" + jsapi_ticket);
		signStr.append("&noncestr=" + nonce_str);
		signStr.append("&timestamp=" + timestamp);
		signStr.append("&url=" + url);

		System.out.println("=============String--"+signStr.toString());
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(signStr.toString().getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		WechatSign wechatSign = new WechatSign();
		wechatSign.setJsapi_ticket(jsapi_ticket);
		wechatSign.setNonce_str(nonce_str);;
		wechatSign.setTimestamp(timestamp);
		wechatSign.setSignature(signature);
		return wechatSign;
	}
    
    
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
    
    /***
     * 模拟get请求
     * @param url
     * @param charset
     * @param timeout
     * @return
     */
     public static String sendGet(String url, String charset, int timeout)
      {
        String result = "";
        try
        {
          URL u = new URL(url);
          try
          {
            URLConnection conn = u.openConnection();
            conn.connect();
            conn.setConnectTimeout(timeout);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line="";
            while ((line = in.readLine()) != null)
            {
              
              result = result + line;
            }
            in.close();
          } catch (IOException e) {
            return result;
          }
        }
        catch (MalformedURLException e)
        {
          return result;
        }
        
        return result;
      }
     public static String getAccessToken(){
            String appid=APPID;//应用ID
            String appSecret=APPSECRET;//(应用密钥)
            String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";
            String backData = sendGet(url, "utf-8", 10000);
            String accessToken = (String) JSONObject.parseObject(backData).get("access_token");  
            return accessToken;
     }
      
    public static String getJSApiTicket(){ 
        //获取token
        String acess_token= getAccessToken();
        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acess_token+"&type=jsapi";  
        String backData=sendGet(urlStr, "utf-8", 10000);  
        String ticket = (String) JSONObject.parseObject(backData).get("ticket");  
        return  ticket;  
            
    }
}