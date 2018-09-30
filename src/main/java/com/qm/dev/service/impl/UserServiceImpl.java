package com.qm.dev.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.code.service.common.impl.QmServiceImpl;
import com.qm.dev.dao.UserMapper;
import com.qm.dev.entity.User;
import com.qm.dev.service.UserService;

/**
 * @author 浅梦工作室
 * @createDate 2018年8月21日 上午1:32:07
 * @Description 拓展service 实现类 Demo
*/
@Service("userService")
public class UserServiceImpl extends QmServiceImpl<User> implements UserService{

	@Resource
	private UserMapper userMapper;
	
	@Override
	public List<Map<String, Object>> getTestList(User user) {
		return userMapper.getTestList(user);
	}
}
