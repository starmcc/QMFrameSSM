package com.qm.dev.service;

import java.util.List;
import java.util.Map;

import com.qm.code.service.common.QmService;
import com.qm.dev.entity.User;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:27:47
 * @Description 拓展service Demo
*/
public interface UserService extends QmService<User>{
	
	/**
	 * 自定义拓展查询业务
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> getTestList(User user);
}
