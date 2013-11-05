package com.ultrashare.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> {

	private EntityManager em;

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass, EntityManager em) {
		this.entityClass = entityClass;
		this.em = em;
	}

	private void openTransaction() {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		} else {
			em.getTransaction().rollback();
			em.getTransaction().begin();
		}
	}

	private void closeTransaction() {
		if (em.getTransaction().isActive()) {
			em.getTransaction().commit();
		}
	}

	public void save(T entity) {
		openTransaction();
		em.persist(entity);
		closeTransaction();
	}

	public void delete(T entity) {
		openTransaction();
		T entityToBeRemoved = em.merge(entity);
		em.remove(entityToBeRemoved);
		closeTransaction();
	}

	public T update(T entity) {
		openTransaction();
		T mergedEntity = em.merge(entity);
		closeTransaction();
		return mergedEntity;

	}

	public T find(Long entityID) {
		return em.find(entityClass, entityID);
	}

	public List<T> findAll() {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (Exception e) {
			if (e instanceof NoResultException) {
				System.out.println("NoResultException: Query \"" + namedQuery + "\" nao retornou nenhum resultado para findOneResult()... Prosseguindo...");
			} else {
				System.out.println("Error while running query: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return result;
	}

	protected long findCount(String namedQuery, Map<String, Object> parameters) {
		long result = 0;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = ((Number) query.getSingleResult()).longValue();

		} catch (Exception e) {
			if (e instanceof NoResultException) {
				System.out.println("NoResultException: Query \"" + namedQuery + "\" nao retornou nenhum resultado para findOneResult()... Prosseguindo...");
			} else {
				System.out.println("Error while running query: " + e.getMessage());
				e.printStackTrace();
			}
			result = 0;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findResults(String namedQuery, Map<String, Object> parameters) {
		List<T> results = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			results = (List<T>) query.getResultList();

		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findResults(String namedQuery, Map<String, Object> parameters, int maxResult) {
		List<T> results = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			query.setMaxResults(maxResult);

			results = (List<T>) query.getResultList();

		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return results;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
