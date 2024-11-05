package com.bsw.groupware.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bsw.groupware.model.MenuVO;

@Mapper
public interface MenuMapper {
	
	MenuVO getList();
}
