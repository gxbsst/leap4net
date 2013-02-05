package com.sidways.leap.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.google.common.collect.Lists;
import com.sidways.leap.dao.BaseDao;
import com.sidways.leap.entity.Entity;

/**
 * @author Kim 2012-4-16
 */
public abstract class AbstractBaseDao<T extends Entity> implements BaseDao<T>{

	protected HibernateTemplate template;

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
	
	public void save(T entity) {
		this.template.save(entity);
	}
	
	public void update(T entity) {
		this.template.update(entity);
	}
	
	public T get(String id) {
		return this.template.get(this.getClazz(), id);
	}

	public void delete(String id) {
		this.template.delete(this.get(id));
	}

	@SuppressWarnings("unchecked")
	public T refresh(T entity) {
		return (T) this.template.get(entity.getClass(), entity.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.template.findByCriteria(this.generatDetachedCriteriaAndSetOrder());
	}
	
	protected T head(List<T> entites){
		if(entites.isEmpty()){
			return null;
		}else{
			return entites.get(0);
		}
	}
	
	protected Integer total() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.getClazz());
		return this.template.findByCriteria(detachedCriteria).size();
	}
	
	protected List<String> getOrderFields(){
		return Lists.<String>newArrayList();
	}

	protected DetachedCriteria generateDetachedCriteria() {
		return DetachedCriteria.forClass(this.getClazz());
	}
	
	protected DetachedCriteria generatDetachedCriteriaAndSetOrder() {
		DetachedCriteria detachedCriteria = this.generateDetachedCriteria();
		if(this.getOrderFields() != null){
			for(String field : this.getOrderFields()){
				detachedCriteria.addOrder(Order.asc(field));
			}
		}
		return detachedCriteria;
	}
	
	abstract protected Class<T> getClazz();
}
