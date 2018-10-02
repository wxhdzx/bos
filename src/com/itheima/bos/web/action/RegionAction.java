package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	//提供上传文件的文件名
	private File myFile;
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	/**
	 * 提供导入方法
	 * @throws IOException 
	 */
	public String importXls() throws IOException {
		String flag="1";
		//使用poi解析excell文件
		try {
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(myFile));
			//获得第一个sheet页
			HSSFSheet sheet= workbook.getSheetAt(0);
			List<Region> list=new ArrayList<Region>();
			for(Row row:sheet) {
				int rowNum = row.getRowNum();
				if(rowNum==0) {
					continue;
				}
				//得到id
				String id = row.getCell(0).getStringCellValue();
				//得到省份
				String province = row.getCell(1).getStringCellValue();
				//得到城市
				String city = row.getCell(2).getStringCellValue();
				//得到区域
				String district = row.getCell(3).getStringCellValue();
				//得到邮编
			    String postcode = row.getCell(4).getStringCellValue();
				//得到区域实体类
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				
				city  = city.substring(0, city.length() - 1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(stringToPinyin, "");
				
				//简码---->>HBSJZCA
				province  = province.substring(0, province.length() - 1);
				district  = district.substring(0, district.length() - 1);
				String info = province + city + district;//河北石家庄长安
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(headByString, "");
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				list.add(region);
			}
			//调用service层的业务逻辑
			
			regionService.saveBatch(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag="0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.print(flag);
		return NONE;
	}
	private String q;
	
	public void setQ(String q) {
		this.q = q;
	}

	//实现模糊查询  实现分区下拉列表框中展示所有地区数据
	public String listajax() throws IOException {
		List<Region> list=null;
		//判断是否输入条件
		if(StringUtils.isNotBlank(q)) {
			list=regionService.findByQ(q);
		}else {
			list=regionService.findAll();
		}
		this.writeList2JSon(list,new String[] {"postcode","shortcode","citycode","subareas"});
		
		return NONE;
	}
	
	//实现分页查询
	public String queryPage() throws IOException {
		//调用业务逻辑层的分页方法
		regionService.pageBean(pageBean);
		//将数据已json格式返回
		this.writePageBean2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	
	//实现数据的添加功能
	public String save() {
		regionService.save(model);
		return "list";
	}
	
	//修改功能
	public String edit() {
		//通过id拿到数据
		Region region=regionService.findById(model.getId());
		region.setProvince(model.getProvince());
		region.setCity(model.getCity());
		region.setDistrict(model.getDistrict());
		region.setPostcode(model.getPostcode());
		region.setShortcode(model.getShortcode());
		region.setCitycode(model.getCitycode());
		//调用save方法
		regionService.update(region);
		return "list";
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	//实现删除功能
	public String delete() {
		regionService.deleteBatch(ids);
		return "list";
	}
} 
