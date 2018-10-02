package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

/**
 * 抽取持久层通用方法
 * @author zhaoqx
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	//提供通用修改方法
	public void executeUpdate(String queryName,Object ...objects);
	//提供职工分页查询
	public void queryPage(PageBean pageBean);
	public void saveOrUpdate(T entity);
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
