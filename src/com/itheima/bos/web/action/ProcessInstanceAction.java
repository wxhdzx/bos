package com.itheima.bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
//编辑流程实例
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport{
	@Autowired
	private  RuntimeService runtimeService;
	@Resource
	private  RepositoryService repositoryService;
	public String list() {
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.list();
		ServletActionContext.getContext().getValueStack().set("list",list);
		return "list";
	}
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

	public String findData() throws IOException {
		Map<String, Object> variables = runtimeService.getVariables(id);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	public String showPng() {
		
		ProcessInstance result = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		String processDefinitionId = result.getProcessDefinitionId();
		ProcessDefinition singleResult = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		deploymentId = singleResult.getDeploymentId();
		imageName = singleResult.getDiagramResourceName();
		//查询坐标
		String activityId = result.getActivityId();
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		ActivityImpl findActivity = pd.findActivity(activityId);
		int x = findActivity.getX();
		int y = findActivity.getY();
		int width=findActivity.getWidth();
		int height = findActivity.getHeight();
		ActionContext.getContext().getValueStack().set("x",x);
		ActionContext.getContext().getValueStack().set("y",y);
		ActionContext.getContext().getValueStack().set("width",width);
		ActionContext.getContext().getValueStack().set("height",height);
		return "showPng";
	}
	
	
	private String deploymentId;
	private String imageName;
	public String viewImage() {
		InputStream pngStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream",pngStream);
		return "viewImage";
	}
	
	

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
