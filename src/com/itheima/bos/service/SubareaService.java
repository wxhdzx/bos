package com.itheima.bos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface SubareaService {
	public void save(Subarea subarea);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public Subarea findById(String id);

	public void update(Subarea subarea);

	public void deleteBatch(String ids);

	public List<Subarea> findListNotAssiociation();
}	
