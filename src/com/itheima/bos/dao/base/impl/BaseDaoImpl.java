package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用实现
 * 
 * @author zhaoqx
 * 
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	// 实体类型
	private Class<T> entityClass;

	// 使用注解方式进行依赖注入
	@Resource
	// @Autowired
	// @Qualifier(value="abc")
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 在构造方法中动态获得操作的实体类型
	 */
	public BaseDaoImpl() {
		// 获得父类（BaseDaoImpl<T>）类型
		ParameterizedType genericSuperclass = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		// 获得父类上的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {// FROM User
		String hql = "FROM  " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 通用更新方法
	 */
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSession();// 从本地线程中获得session对象
		// 使用命名查询语句获得一个查询对象
		Query query = session.getNamedQuery(queryName);
		// 为HQL语句中的？赋值
		int i = 0;
		for (Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();// 执行更新
	}

	//分页查询职工信息
	public void queryPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		//拿到当前页
		int currPage = pageBean.getCurrPage();
		//拿到每页显示数
		int pageSize = pageBean.getPageSize();
		//得到离线条件封装对象
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//改变hibernate的sql封装对象
		detachedCriteria.setProjection(Projections.rowCount());//select count(*) from t_staff;
		//查询得到总记录数
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		//得到总记录数
		Long total = list.get(0);
		pageBean.setTotal(total.intValue());
		//修改hibernate的sql形式
		detachedCriteria.setProjection(null);//select * from t_staff
		detachedCriteria.setResultTransformer(detachedCriteria.ROOT_ENTITY);
		int firstResult=(currPage-1)*pageSize;
		int maxResult=pageSize;
		//得到数据结果集
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResult);
		pageBean.setRows(rows);
	}

	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
