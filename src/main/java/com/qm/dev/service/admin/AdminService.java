package com.qm.dev.service.admin;

import java.util.List;
import java.util.Map;

import com.qm.code.service.common.QmService;
import com.qm.dev.entity.Admin;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:27:47
 * @Description 拓展service Demo
*/
public interface AdminService extends QmService<Admin>{
	
	/**
	 * 自定义拓展查询业务
	 * @param admin
	 * @return
	 */
	List<Map<String,Object>> getTestList(Admin admin);
}
