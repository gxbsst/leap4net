package com.sidways.leap.service;

import java.util.Collection;

import com.sidways.leap.entity.Entity;

/**
 * @author kim
 * 
 */
public interface GeneralDBService<T extends Entity> {

	public T add(GeneralEntityProxy<T> proxy);

	public T update(GeneralEntityProxy<T> proxy);

	public void delete(String id);

	public GeneralViewProxy get(String id);

	public Collection<GeneralViewProxy> getAll();

	public interface GeneralEntityProxy<T extends Entity> {

		public T toEntity();
	}

	public interface GeneralViewProxy{

	}
}
