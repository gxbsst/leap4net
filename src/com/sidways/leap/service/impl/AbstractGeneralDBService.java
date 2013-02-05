package com.sidways.leap.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.sidways.leap.dao.BaseDao;
import com.sidways.leap.entity.Entity;
import com.sidways.leap.service.GeneralDBService;

/**
 * @author kim
 * 
 */
public abstract class AbstractGeneralDBService<T extends Entity> implements GeneralDBService<T> {

	protected BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Transactional
	public T add(GeneralEntityProxy<T> proxy) {
		T entity = proxy.toEntity();
		this.baseDao.save(entity);
		return entity;
	}

	public GeneralViewProxy get(String id) {
		return this.generateGeneralViewProxy(this.baseDao.get(id));
	}

	@Transactional
	public T update(GeneralEntityProxy<T> proxy) {
		T entity = proxy.toEntity();
		T entityDB = this.baseDao.get(entity.getId());
		BeanUtils.copyProperties(entity, entityDB);
		this.baseDao.save(entityDB);
		return entity;
	}

	@Transactional
	public void delete(String id) {
		this.baseDao.delete(id);
	}

	public Collection<GeneralViewProxy> getAll() {
		return this.generateGeneralViewProxies(this.baseDao.getAll());
	}

	protected Collection<GeneralViewProxy> generateGeneralViewProxies(Collection<T> entites){
		Collection<GeneralViewProxy> proxies = new ArrayList<GeneralViewProxy>();
		for (T entity : entites) {
			proxies.add(this.generateGeneralViewProxy(entity));
		}
		return proxies;
	}

	abstract protected GeneralViewProxy generateGeneralViewProxy(T entity);
}
