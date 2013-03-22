package org.afrosoft.clientinvoicing.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.afrosoft.clientinvoicing.domain.Client;
import org.springframework.transaction.annotation.Transactional;

public class ClientServiceImpl implements ClientService {
  
  @PersistenceContext(unitName="client-invoicing", type=PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;

  @Transactional
  @Override
  public List<Client> getAllClients() {
  	// TODO move this to a DAO layer
    List<Client> clients = entityManager.createNamedQuery("findAllClients", Client.class).getResultList();
    
    return clients;
  }

	@Override
  public void addClient(Client client) {
  }

	@Override
  public Client updateClient(Client client) {
	  return null;
  }

}
