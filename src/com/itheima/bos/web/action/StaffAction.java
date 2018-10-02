package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


//表明web层
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	//调用业务逻辑层的方法
	
	//添加取派员信息
	public String saveStaff() {
		staffService.save(model);
		return "list";
	}
	
	//分页查询取派员信息
	public String pageQuery() throws IOException {
		staffService.queryPage(pageBean);
		//将所有数据以json的格式返回
		this.writePageBean2Json(pageBean,new String[] {"currentPage","detachedCriteria","pageSize","decidedzones"});
	
		return NONE;
	}
	
	//实现删除派送派送员
	public String delete() {
		staffService.deleteBatch(ids);
		return "list";
	}
	
	public String edit() {
		Staff staff = staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		
		//调用修改
		staffService.update(staff);
		return "list";
	}
	//实现定区取派员的信息
	public String listajax() throws IOException {
		List<Staff> list=staffService.findListNotDelete();
		//以json的数据格式返回
		this.writeList2JSon(list,new String[] {"decidedzones","standard","station","deltag","haspda ","telephone"});
		return NONE;
	}
}
