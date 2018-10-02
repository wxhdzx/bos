package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.BOSContext;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class FunctionSericeImpl implements FunctionService {
	@Autowired
	private FunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		functionDao.queryPage(pageBean);
	}

	public List<Function> findAll() {
		// TODO Auto-generated method stub
		List<Function> list = functionDao.findAll();
		return list;
	}
	
	public void save(Function model) {
		// TODO Auto-generated method stub
		//取出父节点,看父节点是否为空
		Function function = model.getFunction();
		if(function!=null && function.getId().equals("")) {
			model.setFunction(null);
		}
		functionDao.save(model);
	}

	public List<Function> findMenu() {
		// TODO Auto-generated method stub
		User user = BOSContext.getLoginUser();
		List<Function> list=null;
		if(user.getUsername().equals("admin")) {
			//查询所有
			list = functionDao.findAllMenu();
		}else {
			list=functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}
}
