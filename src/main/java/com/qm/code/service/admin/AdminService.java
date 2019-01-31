package com.qm.code.service.admin;

import java.util.List;
import java.util.Map;

import com.qm.code.entity.Admin;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:27:47
 * @Description 拓展service Demo
*/
public interface AdminService{
	
	/**
	 * 自定义拓展查询业务
	 * @param admin
	 * @return
	 */
	List<Map<String,Object>> getTestList(Admin admin);
}
