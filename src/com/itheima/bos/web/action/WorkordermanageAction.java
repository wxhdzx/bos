package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workbill;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	
	//保存导入的订单
	public String add() {
		workordermanagerService.save(model);
		return "list";
	}
	
	//查询start为0的1工作单
	public String list() {
		List<Workordermanage> list= workordermanagerService.findListNotStart();
		ActionContext.getContext().getValueStack().set("list",list);
		return "lists";
	}
	
	//启动流程实例
	public String start() {
		//得到工作单的id
		String id = model.getId();
		workordermanagerService.start(id);//启动流程实例
		return "tolists";
	}
}
