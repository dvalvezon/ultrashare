package com.ultrashare.component.facilities;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

public class EntityManagerProxy implements EntityManager {

	private final EntityManagerFactory factory;

	private EntityManager realEntityManager = null;

	public EntityManagerProxy(EntityManagerFactory factory) {
		this.factory = factory;
	}

	// TODO - Implement a mechanism to verify for closed EM Factory =
	// EntityManagerFactoryProxy...
	private EntityManager getRealEntityManager() {
		return realEntityManager == null || !realEntityManager.isOpen() ? realEntityManager = factory.createEntityManager() : realEntityManager;
	}

	public void persist(Object entity) {
		getRealEntityManager().persist(entity);
	}

	public <T> T merge(T entity) {
		return getRealEntityManager().merge(entity);
	}

	public void remove(Object entity) {
		getRealEntityManager().remove(entity);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return getRealEntityManager().find(entityClass, primaryKey);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return getRealEntityManager().find(entityClass, primaryKey, properties);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return getRealEntityManager().find(entityClass, primaryKey, lockMode);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
		return getRealEntityManager().find(entityClass, primaryKey, lockMode, properties);
	}

	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		return getRealEntityManager().getReference(entityClass, primaryKey);
	}

	public void flush() {
		getRealEntityManager().flush();
	}

	public void setFlushMode(FlushModeType flushMode) {
		getRealEntityManager().setFlushMode(flushMode);
	}

	public FlushModeType getFlushMode() {
		return getRealEntityManager().getFlushMode();
	}

	public void lock(Object entity, LockModeType lockMode) {
		getRealEntityManager().lock(entity, lockMode);
	}

	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		getRealEntityManager().lock(entity, lockMode, properties);
	}

	public void refresh(Object entity) {
		getRealEntityManager().refresh(entity);
	}

	public void refresh(Object entity, Map<String, Object> properties) {
		getRealEntityManager().refresh(entity, properties);
	}

	public void refresh(Object entity, LockModeType lockMode) {
		getRealEntityManager().refresh(entity, lockMode);
	}

	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		getRealEntityManager().refresh(entity, lockMode, properties);
	}

	public void clear() {
		getRealEntityManager().clear();
	}

	public void detach(Object entity) {
		getRealEntityManager().detach(entity);
	}

	public boolean contains(Object entity) {
		return getRealEntityManager().contains(entity);
	}

	public LockModeType getLockMode(Object entity) {
		return getRealEntityManager().getLockMode(entity);
	}

	public void setProperty(String propertyName, Object value) {
		getRealEntityManager().setProperty(propertyName, value);
	}

	public Map<String, Object> getProperties() {
		return getRealEntityManager().getProperties();
	}

	public Query createQuery(String qlString) {
		return getRealEntityManager().createQuery(qlString);
	}

	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return getRealEntityManager().createQuery(criteriaQuery);
	}

	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return getRealEntityManager().createQuery(qlString, resultClass);
	}

	public Query createNamedQuery(String name) {
		return getRealEntityManager().createNamedQuery(name);
	}

	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return getRealEntityManager().createNamedQuery(name, resultClass);
	}

	public Query createNativeQuery(String sqlString) {
		return getRealEntityManager().createNativeQuery(sqlString);
	}

	@SuppressWarnings("rawtypes")
	public Query createNativeQuery(String sqlString, Class resultClass) {
		return getRealEntityManager().createNativeQuery(sqlString, resultClass);
	}

	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return getRealEntityManager().createNativeQuery(sqlString, resultSetMapping);
	}

	public void joinTransaction() {
		getRealEntityManager().joinTransaction();
	}

	public <T> T unwrap(Class<T> cls) {
		return getRealEntityManager().unwrap(cls);
	}

	public Object getDelegate() {
		return getRealEntityManager().getDelegate();
	}

	public void close() {
		getRealEntityManager().close();
	}

	public boolean isOpen() {
		return getRealEntityManager().isOpen();
	}

	public EntityTransaction getTransaction() {
		return getRealEntityManager().getTransaction();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return getRealEntityManager().getEntityManagerFactory();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return getRealEntityManager().getCriteriaBuilder();
	}

	public Metamodel getMetamodel() {
		return getRealEntityManager().getMetamodel();
	}

}
