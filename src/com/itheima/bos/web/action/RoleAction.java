package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	//提供一个ids
	private String ids;

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	//提供一个保存方法
	public String add() {
		roleService.save(model,ids);
		return "list";
	}
	
	//分页查询
	public String pageQuery() throws IOException {
		roleService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean,new String[] {"currentPage","pageSize","detachedCriteria","users","functions"});
		return NONE;
	}
	
	//查询所有
	public String listajax() throws IOException {
		List<Role> list=roleService.findAll();
		this.writeList2JSon(list,new String[] {"functions","users"});
		return NONE;
	}
}
