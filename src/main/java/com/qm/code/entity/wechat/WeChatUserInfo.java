package com.qm.code.entity.wechat;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月20日 上午12:34:19
 * @Description 微信用户信息实体类
 */
public class WeChatUserInfo {
	private String headImgUrl;// 头像
	private String nickName; // 昵称
	private String openId; // openId
	private String token; // token

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public String getOpenId() {
		return openId;
	}

	public String getToken() {
		return token;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setToken(String token) {
		this.token = token;
	}

}