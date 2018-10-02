package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface StaffService {
	//职工添加管理
	public void save(Staff model);
	//职工分页查询
	public void queryPage(PageBean pageBean);
	//删除职工信息
	public void deleteBatch(String ids);
	public Staff findById(String id);
	public void update(Staff staff);
	public List<Staff> findListNotDelete();

}
