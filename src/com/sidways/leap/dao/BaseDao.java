package com.sidways.leap.dao;

import java.util.List;

import com.sidways.leap.entity.Entity;


/**
 * @author Kim 2012-4-16
 */
public interface BaseDao<T extends Entity> {

	public void save(T entity);
	
	public void update(T entity);
	
	public void delete(String id);
	
	public T get(String id);
	
	public T refresh(T entity);
	
	public List<T> getAll();
}
