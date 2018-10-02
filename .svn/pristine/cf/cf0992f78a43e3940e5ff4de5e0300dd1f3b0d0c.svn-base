package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.User;
import com.itheima.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//通过手机号码查询客户信息
	public String findCustomerByTelephone() throws IOException {
		//调用customerservice的方法
		Customer customer = customerService.findCustomerByTelephone(model.getTelephone());
		//将数据以json格式返回
		this.writeObject2Json(customer,new String[] {""});
		return NONE;
	}
	//提供关联新表
	public String add() {
		//通过session取到当前用户
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		model.setUser(user);
		//调用save方法
		noticebillService.save(model);
		
		return "list";
	}
}
