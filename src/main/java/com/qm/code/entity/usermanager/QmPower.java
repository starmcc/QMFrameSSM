package com.qm.code.entity.usermanager;

/**
 * @author 浅梦工作室
 * @createDate 2018年9月29日 上午1:41:56
 * @Description 权限id
 */
public class QmPower {
	/**
	 * 权限id
	 */
	private Integer powerId;
	/**
	 * 权限描述
	 */
	private String powerDescription;

	public Integer getPowerId() {
		return powerId;
	}

	public String getPowerDescription() {
		return powerDescription;
	}

	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}

	public void setPowerDescription(String powerDescription) {
		this.powerDescription = powerDescription;
	}

}
