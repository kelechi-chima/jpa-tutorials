package org.afrosoft.clientinvoicing.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.afrosoft.clientinvoicing.domain.Client;
import org.springframework.transaction.annotation.Transactional;

public class ClientServiceImpl implements ClientService {

  private EntityManagerFactory entityManagerFactory;
  
  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Transactional
  @Override
  public List<Client> getAllClients() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    List<Client> clients = entityManager.createNamedQuery("findAllClients", Client.class).getResultList();
    
    return clients;
  }

}
