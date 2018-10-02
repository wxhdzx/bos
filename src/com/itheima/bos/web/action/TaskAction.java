package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.WorkordermanagerService;
import com.itheima.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
	@Resource
	private TaskService taskService;
	@Resource
	private WorkordermanagerService workordermanagerService;
	@Resource
	private RuntimeService runtimeService;
	public String findGroupTask() {
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();
		//组任务过滤
		query.taskCandidateUser(candidateUser);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list",list);
		return "groupTaskList";
	}
	private String taskId;
	public String showData() throws IOException {
		Map<String, Object> variables = taskService.getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskId() {
		return taskId;
	}
	//拾取任务
	public String takeTask() {
		String userId = BOSContext.getLoginUser().getId();
		taskService.claim(taskId,userId);
		return "togroupTaskList";
	}
	
	public String findPersonalTask() {
		TaskQuery query = taskService.createTaskQuery();
		String assignee= BOSContext.getLoginUser().getId();
		query.taskAssignee(assignee);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list",list);
		return "personalTaskList";
	}
	private Integer check;
	
	
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	//办理业务
	public String checkWorkOrderManage() {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String workordermanageId = processInstance.getBusinessKey();
		Workordermanage workordermanage = workordermanagerService.findById(workordermanageId);
		if(check==null) {
			ActionContext.getContext().getValueStack().set("map",workordermanage);
			return "check";
		}
		else {
				workordermanagerService.checkWorkordermanage(taskId, check, workordermanageId);
				return "topersonaltasklist";

		
		}
		
	}
	
	//出库
	public String outStore() {
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	
	//办理配送任务
	public String transferGoods() {
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	
	public String receive() {
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
}
