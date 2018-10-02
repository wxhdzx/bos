package com.itheima.bos.service;

import java.util.List;

import cn.itcast.crm.domain.Customer;

// 客户服务接口 
public interface CustomerService {
	// 未关联定区客户
	public List<Customer> findnoassociationCustomers();

	// 查询已经关联指定定区的客户
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// 将未关联定区客户关联到定区上
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
	//通过手机号码查询客户
	public Customer findCustomerByTelephone(String telephone);
	//通过地址获取分区id
	public String findDecidedzoneIdByPickaddress(String address);
}
