package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.dao.ClientDao;
import org.afrosoft.clientinvoicing.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
  
  private ClientDao clientDao;

  @Autowired
  public ClientServiceImpl(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Client> getAllClients() {
    List<Client> clients = clientDao.getAllClients();
    
    return clients;
  }

  @Transactional
	@Override
  public Client addClient(Client client) {
	  clientDao.addClient(client);
	  
	  return client;
  }

  @Transactional
	@Override
  public Client updateClient(Client client) {
	  client = clientDao.updateClient(client);
	  return client;
  }

  @Transactional
	@Override
  public void removeClient(Client client) {
  	// TODO
  }

}
