package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	//保存分区字段
	
	public String save() {
		
		subareaService.save(model);
		return "list";
	}
	//在subarea.xml文件中将懒加载设置为false
	//实现无条件分页查询
	/*public String pageQuery() throws IOException {
		subareaService.pageQuery(pageBean);
		//将数据以json格式返回
		this.writePageBean2Json(pageBean,new String[] {"detachedCriteria", "currentPage",
				"pageSize", "decidedzone", "subareas"});
		return NONE;
	}*/
	//实现条件组合分页查询
	public String pageQuery() throws IOException {
		//得到离线条件封装对象
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		//得到关键字
		String addresskey = model.getAddresskey();
		//得到区域对象region
		Region region = model.getRegion();
		//实现关键字条件查询
		//判断关键字是否为空
		if(StringUtils.isNotBlank(addresskey)) {
			//对关键字进行模糊查询
			detachedCriteria2.add(Restrictions.like("addresskey","%"+addresskey+"%"));
		}
		//对区域数据进行模糊查询
		if(region!=null) {
			//设置别名
			detachedCriteria2.createAlias("region","r");
			//得到省份
			String province = region.getProvince();
			//对省进行模糊查询
			if(StringUtils.isNotBlank(province)) {
				detachedCriteria2.add(Restrictions.like("r.province","%"+province+"%"));
			}
			//得到城市
			String city = region.getCity();
			//对市进行模糊查询
			if(StringUtils.isNotBlank(city)) {
				detachedCriteria2.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			//得到区
			String district = region.getDistrict();
			if(StringUtils.isNotBlank(district)) {
				detachedCriteria2.add(Restrictions.like("r.district","%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		//将数据以json格式返回
		this.writePageBean2Json(pageBean,new String[] {"detachedCriteria", "currentPage",
				"pageSize", "decidedzone", "subareas"});
		return NONE;
	}
	
	public String exportXls() throws IOException {
		List<Subarea> list=subareaService.findAll();
		//在内存中创建一个excel文件,通过客户端下载
		HSSFWorkbook workbook=new HSSFWorkbook();
		//创建一个sheet页
		HSSFSheet sheet=workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow headRow=sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		for(Subarea subarea:list) {
			HSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
		}
		//下载数据
		String filename="分区数据.xls";
		//浏览器种类
		String agent=ServletActionContext.getRequest().getHeader("User-Agent");
		filename=FileUtils.encodeDownloadFilename(filename, agent);
		//一个流两个头
		ServletOutputStream out=ServletActionContext.getResponse().getOutputStream();
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setHeader("content-disposition","attchment;filename="+filename);
		workbook.write(out);
		
		return NONE;
	}
	
	//实现修改功能
	public String edit() {
		//通过id拿到分区数据
		Subarea subarea=subareaService.findById(model.getId());
		subarea.setAddresskey(model.getAddresskey());
		subarea.setStartnum(model.getStartnum());
		subarea.setEndnum(model.getEndnum());
		subarea.setSingle(model.getSingle());
		subarea.setPosition(model.getPosition());
		subareaService.update(subarea);
		return "list";
	}
	
	//实现批量删除功能
	public String delete() {
		subareaService.deleteBatch(ids);
		return "list";
	}
	
	//实现定区关联
	public String listajax() throws IOException {
		List<Subarea> list=subareaService.findListNotAssiociation();
		//以json格式的数据返回
		this.writeList2JSon(list, new String[] {"decidedzone","region"});
		return NONE;
	}
}
