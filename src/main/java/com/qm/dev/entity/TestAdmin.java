package com.qm.dev.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;
@Table(name="test_admin")
@NameStyle(Style.normal)
public class TestAdmin {
	@Id
	private Integer id;
}
