package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	//注入service
	@Autowired
	private RepositoryService repositoryService;
	public String list() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		//得到最近版本
		query.latestVersion();
		//排序
		query.orderByProcessDefinitionName().desc();//排序
		List<ProcessDefinition> list = query.list();
		//将查询到的List压入到值栈
		ActionContext.getContext().getValueStack().set("list",list);
		return "list";
	}
	//定义一个上传文件字段
	private File zipFile;
	
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}

	public String deploy() throws FileNotFoundException {
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(zipFile)));
		deploymentBuilder.deploy();
		return "toList";
	}
	
	//接收流程定义id
		private String id;
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * 展示png图片
		 */
		public String showpng(){
			//获取png图片对应的输入流
			InputStream pngStream = repositoryService.getProcessDiagram(id);
			ActionContext.getContext().getValueStack().set("pngStream", pngStream);
			return "showpng";
		}
		
		//删除流程
		public String delete() {
			String deltag="0";
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
			query.processDefinitionId(id);
			ProcessDefinition result = query.singleResult();
			//得到部署id
			String deploymentId = result.getDeploymentId();
			//调用删除方法
			try {
				repositoryService.deleteDeployment(deploymentId);
			} catch (Exception e) {
				// TODO: handle exception
				deltag="1";
				ServletActionContext.getContext().getValueStack().set("deltag",deltag);
				ProcessDefinitionQuery query2 = repositoryService.createProcessDefinitionQuery();
				query2.latestVersion();//最近版本
				query2.orderByProcessDefinitionName().desc();
				List<ProcessDefinition> list = query2.list();
				ActionContext.getContext().getValueStack().set("list",list);
				return "list";
			}
			
			return "toList";
		}
		
	
		
}	
