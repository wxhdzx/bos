package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	//分页查询
	public String pageQuery() throws IOException {
	//由于function类中存在page属性,在封装currpage时,直接将Page的值封装进function中
		String page = model.getPage();
		pageBean.setCurrPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		//以json的数据格式返回
		this.writePageBean2Json(pageBean,new String[] {"currentPage","pageSize","detachedCriteria","function","functions","roles"});
		return NONE;
	}
	
	//查询所有派送员
	public String listajax() throws IOException {
		List<Function> list = functionService.findAll();
		//将数据以json格式返回
		this.writeList2JSon(list,new String[] {"function","functions","roles"});
		return NONE;
	}
	
	//添加权限管理
	public String add() {
		functionService.save(model);
		return "list";
	}
	
	//根据登录人员查询对应的菜单
	public String findMenu() throws IOException {
		List<Function> list= functionService.findMenu();
		this.writeList2JSon(list,new String[] {"functions","function","roles"});
		return NONE;
	}
}
