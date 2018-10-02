package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private IdentityService identityService;
	public void save(Role model, String ids) {
		// TODO Auto-generated method stub
		roleDao.save(model);
		Group group=new GroupEntity(model.getName());
		identityService.saveGroup(group);
		String[] id = ids.split(",");
		for(String roleid:id) {
			Function function=new Function(roleid);
			model.getFunctions().add(function);
		}
	}

	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		roleDao.queryPage(pageBean);
	}

	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}
}
