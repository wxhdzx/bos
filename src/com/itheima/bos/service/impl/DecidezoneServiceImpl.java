package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class DecidezoneServiceImpl implements DecidedzoneService {
	@Autowired
	private DecidedzoneDao decidedzonedao;
	@Autowired
	private SubareaDao subareadao;
	public void save(Decidedzone model, String[] subareaid) {
		// TODO Auto-generated method stub
		//保存分区对象
		decidedzonedao.save(model);
		//通过id查询所有分区对象
		for(String id:subareaid) {
			subareadao.findById(id);
		}
	}
	//分页查询
	public void queryPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		decidedzonedao.queryPage(pageBean);
	}

}
