package com.qm.dev.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.qm.dev.entity.User;

import tk.mybatis.mapper.common.Mapper;
/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:16:52
 * @Description 如果业务复杂请编写所属的mapper实现
 */
public interface UserMapper extends Mapper<User>{

	/**
	 * 自定义拓展查询业务
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> getTestList(@Param("user")User user);
}