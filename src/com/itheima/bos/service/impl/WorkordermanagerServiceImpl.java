package com.itheima.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WorkordermanagerDao;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.WorkordermanagerService;
@Service
@Transactional
public class WorkordermanagerServiceImpl implements WorkordermanagerService {
	@Resource
	private WorkordermanagerDao workordermanagerDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	public void save(Workordermanage model) {
		// TODO Auto-generated method stub
		//设置当前系统的时间
		model.setUpdatetime(new Date());
		workordermanagerDao.save(model);
	}
	//查询strat为0的工作单
	public List<Workordermanage> findListNotStart() {
		// TODO Auto-generated method stub
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start","0"));
		return workordermanagerDao.findByCriteria(detachedCriteria);
	}
	public void start(String id) {
		//通过id查询工作单
		Workordermanage workordermanage = workordermanagerDao.findById(id);
		String processDefinitionKey="transfer";
		//修改流程变量
		workordermanage.setStart("1");
		String businessKey=id;
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("业务数据",workordermanage);
		// TODO Auto-generated method stub
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}
	public Workordermanage findById(String workordermanageId) {
		// TODO Auto-generated method stub
		return workordermanagerDao.findById(workordermanageId);
	}
	
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId) {
		// TODO Auto-generated method stub
		
		Workordermanage workordermanage = workordermanagerDao.findById(workordermanageId);
	
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("check", check);
		String processInstanceId = task.getProcessInstanceId();
		taskService.complete(taskId, variables);
		
		
		if(check==0) {
			workordermanage.setStart("0");
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
		
	}

}
