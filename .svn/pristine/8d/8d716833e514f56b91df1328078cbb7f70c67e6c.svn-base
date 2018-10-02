<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menubutton---菜单按钮</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h1>第一种自动给定数据,datagrid视图渲染 </h1> 
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>张三</td>
				<td>20</td>
			</tr>
			<tr>
				<td>002</td>
				<td>李四</td>
				<td>30</td>
			</tr>
		</tbody>
		
	</table>
	
	<h1> 第二种方式,通过ajax发送json数据 </h1> 
	<table class="easyui-datagrid" data-options="url:'/bos19/json/data.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		
	</table>
	
	<h1>第三种方式,通过js代码自动获取</h1>
	<table id="grid">
	</table>
	<script type="text/javascript">
		$(function(){
			//拿到表格
			$("#grid").datagrid({
				//指定column属性
				columns:[[
					{field:'id',title:'编号',checkbox:true},
					{field:'name',title:'姓名'},
					{field:'age',title:'年龄'},
				]],  
				//指定数据
				url:'/bos19/json/data.json',
				//获取工具栏
				toolbar:[
					{text:'添加',iconCls:'icon-add'},
					{text:'删除',iconCls:'icon-remove',
						//给删除按钮添加一个点击事件
						 handler:function(){
							//获得选中的行
				        	 	var rows = $("#grid").datagrid("getSelections");
				        	 	for(var i=0;i<rows.length;i++){
				        	 		var id = rows[i].id;
				        	 		alert(id);
				        	 	}
				         	  }},

					{text:'修改',iconCls:'icon-edit'},
				
					],
				//单选效果
				singleSelect:true,
				//分页效果
				pagination:true,
				pageList:[3,5,7]
			});
		});
	</script>
</body>
</html>