package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.PageBean;

public interface FunctionService {

	public void pageQuery(PageBean pageBean);

	public List<Function> findAll();

	public void save(Function model);

	public List<Function> findMenu();
	
}
