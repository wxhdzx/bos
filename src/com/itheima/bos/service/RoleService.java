package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface RoleService {

	public void save(Role model, String ids);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
