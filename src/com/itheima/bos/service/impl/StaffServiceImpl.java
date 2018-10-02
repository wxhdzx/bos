package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.utils.PageBean;
//声明service层
@Service
//表名事务管理
@Transactional
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffDao staffDao;
	public void save(Staff model) {
		// TODO Auto-generated method stub
		staffDao.save(model);
	}
	public void queryPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		staffDao.queryPage(pageBean);
	}
	public void deleteBatch(String ids) {
		// TODO Auto-generated method stub
		//将ids按照,分割开
		String[] staffids=ids.split(",");
		//遍历获取选中的每个id值
		for(String id:staffids) {
			
			staffDao.executeUpdate("staff.delete",id);
		}
		
	}
	//根据id查找职工信息
	public Staff findById(String id) {
		// TODO Auto-generated method stub
		Staff staff = staffDao.findById(id);
		return staff;
	}
	public void update(Staff staff) {
		// TODO Auto-generated method stub
		staffDao.update(staff);
	}
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		//添加条件,没有作废的派送员进行查询
		detachedCriteria.add(Restrictions.ne("deltag","1"));
		// TODO Auto-generated method stub
		return staffDao.findByCriteria(detachedCriteria);
	}

}
