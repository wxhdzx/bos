package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.NoticebillDao;
import com.itheima.bos.dao.WorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.CustomerService;
import com.itheima.bos.service.NoticebillService;
@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
	@Resource
	private NoticebillDao noticebillDao;
	@Resource
	private CustomerService proxy;
	@Resource
	private DecidedzoneDao decidezondeDao;
	@Resource
	private WorkbillDao workbillDao;
	public void save(Noticebill model) {
		// TODO Auto-generated method stub
		noticebillDao.save(model);
		//得到住宅地址
		String pickaddress = model.getPickaddress();
		//通过地址找到定区id
		String did = proxy.findDecidedzoneIdByPickaddress(pickaddress);
		//通过查到了定区id
		if(did!=null) {
			//通过id，查询到订单
			Decidedzone decidedzone = decidezondeDao.findById(did);
			//得到该订单的分派员
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			model.setOrdertype("自动");
			//为派件员创建一个工单
			Workbill workbill=new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新单");
			//保存工单
			workbillDao.save(workbill);
		}else {
			model.setOrdertype("手动");
		}
	}

}
