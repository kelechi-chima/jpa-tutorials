package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.springframework.stereotype.Repository;

@Repository("clientDao")
public class ClientDaoImpl extends BaseDao implements ClientDao {
  
  @Override
  public List<Client> getAllClients() {
    List<Client> clients = entityManager.createNamedQuery("findAllClients", Client.class).getResultList();
    
    logger.info("Found {} clients", clients.size());
    
    return clients;
  }

  @Override
  public Client add(Client client) {
    entityManager.persist(client);
    
    logger.info("Added client with id '{}'", client.getId());
    
    return client;
  }

  @Override
  public Client update(Client client) {
    client = entityManager.merge(client);
    
    logger.info("Updated client: {}", client);
    
    return client;
  }

  @Override
  public void remove(Client client) {
    entityManager.remove(client);
    
    logger.info("Removed client: {}", client);
  }

}
