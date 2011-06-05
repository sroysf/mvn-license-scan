package com.codechronicle.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codechronicle.dto.BeanMapperUtil;

@Service
public class EntityService {

	private static final Logger log = LoggerFactory.getLogger(EntityService.class);
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public <T> T addOrUpdateEntity(T entity) {
		
		try {
			Object id = MethodUtils.invokeExactMethod(entity, "getId", null);
			
			// Consider blank strings equivalent of null.
			if (id instanceof String) {
				String strId = (String)id;
				if (strId.length() == 0) {
					id = null;
				}
			}
			
			if (id != null) {
				log.info("Updating existing entity of type " + entity.getClass().getName() + ", ID = " + id);
				
				//TODO: There must be a way to avoid the cast here
				T existingEntity = (T)em.find(entity.getClass(), id);
				if (existingEntity != null) {
					BeanMapperUtil.copyProperties(entity, existingEntity);
				} else {
					throw new RuntimeException("Unable to update existing entity. Entity with ID = " + id + " not found.");
				}
			} else {
				log.info("Saving new entity of type " + entity.getClass().getName());
				em.persist(entity);
			}
			
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		
		return entity;
	}
	
	public <T> List<T> findAll(Class<T> entityClass) {
		
		String fullClassName = entityClass.getName();
		String entityName = fullClassName.substring(fullClassName.lastIndexOf('.')+1);
		TypedQuery<T> query = em.createQuery("Select x from " + entityName + " x", entityClass);
		List<T> entityList = query.getResultList();
		return entityList;
	}
	
	public <T> T findById(Class<T> entityClass, Object id) {
		return em.find(entityClass, id);
	}
}
