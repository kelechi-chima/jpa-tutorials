package org.afrosoft.clientinvoicing.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDao {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext(unitName = "client-invoicing", type = PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;
	
}
