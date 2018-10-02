package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.web.action.base.BaseAction;

import cn.itcast.crm.domain.Customer;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	// 接收分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 * 添加定区
	 * 
	 * @return
	 */
	public String add() {
		decidedzoneService.save(model, subareaid);
		return "list";
	}
	//分页查询
	public String queryPage() throws IOException {
		decidedzoneService.queryPage(pageBean);
		//以json数据返回
		this.writePageBean2Json(pageBean, new String[] {"currentPage","pageSize","decidedzones","decidedzone","subareas","detachedCriteria"});
		return NONE;
	}
	//查询没有关联的数据
	public String findnoassociationCustomers() throws IOException {
		List<Customer> list = customerService.findnoassociationCustomers();
		//以json个数返回数据
		this.writeList2JSon(list,new String[] {"station","address"});
		return NONE;
	}
	//查询关联的数据
	public String findhasassociationCustomers() throws IOException {
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		this.writeList2JSon(list,new String[] {"station","address"});
		return NONE;
	}
	private Integer[] customerIds;
	
	public Integer[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	//保存关联的客户信息
	public String assigncustomerstodecidedzone() {
		
		customerService.assignCustomersToDecidedZone(customerIds,model.getId());
		return "list";
	}
}
