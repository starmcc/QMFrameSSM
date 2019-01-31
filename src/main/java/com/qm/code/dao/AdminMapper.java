package com.qm.code.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.qm.code.entity.Admin;

import tk.mybatis.mapper.common.Mapper;
/**
 * @author 浅梦工作室
 * @createDate 2018年8月19日 下午8:16:52
 * @Description 如果业务复杂请编写所属的mapper实现
 */
public interface AdminMapper extends Mapper<Admin>{

	/**
	 * 自定义拓展查询业务
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> getTestList(@Param("admin")Admin admin);
}