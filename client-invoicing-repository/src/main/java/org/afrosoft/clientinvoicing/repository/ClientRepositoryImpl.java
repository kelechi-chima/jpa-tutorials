package org.afrosoft.clientinvoicing.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.afrosoft.clientinvoicing.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("clientRepository")
public class ClientRepositoryImpl implements ClientRepository {

  private static final Logger LOG = LoggerFactory.getLogger(ClientRepositoryImpl.class);
  
  @PersistenceContext(unitName="client-invoicing", type=PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;
  
  @Transactional(readOnly=true)
  @Override
  public List<Client> getAllClients() {
    List<Client> clients = entityManager.createNamedQuery("findAllClients", Client.class).getResultList();
    
    LOG.info("Found {} clients", clients.size());
    
    return clients;
  }

  @Transactional
  @Override
  public Client addClient(Client client) {
    entityManager.persist(client);
    
    LOG.info("Client added with id '{}'", client.getId());
    
    return client;
  }

  @Transactional
  @Override
  public Client updateClient(Client client) {
    client = entityManager.merge(client);
    
    LOG.info("Updated client: {}", client);
    
    return client;
  }

  @Transactional
  @Override
  public void removeClient(Client client) {
    // TODO - may need to remove projects first as remove is not cascaded
  }

}
