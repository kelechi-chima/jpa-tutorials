package org.afrosoft.clientinvoicing.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.afrosoft.clientinvoicing.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("clientRepository")
public class ClientRepositoryImpl implements ClientRepository {

  private static final Logger LOG = LoggerFactory.getLogger(ClientRepositoryImpl.class);
  
  @PersistenceContext(unitName="client-invoicing", type=PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;
  
  @Override
  public List<Client> getAllClients() {
    List<Client> clients = entityManager.createNamedQuery("findAllClients", Client.class).getResultList();
    
    LOG.info("Found {} clients", clients.size());
    
    return clients;
  }

  @Override
  public Client addClient(Client client) {
    entityManager.persist(client);
    
    LOG.info("Client added with id '{}'", client.getId());
    
    return client;
  }

  @Override
  public Client updateClient(Client client) {
    client = entityManager.merge(client);
    
    LOG.info("Updated client: {}", client);
    
    return client;
  }

  @Override
  public void removeClient(Client client) {
    entityManager.remove(client);
    
    LOG.info("Removed client: {}", client);
  }

}
