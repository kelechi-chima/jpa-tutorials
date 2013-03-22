package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
  
  private ClientRepository clientRepository;

  @Autowired
  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<Client> getAllClients() {
    List<Client> clients = clientRepository.getAllClients();
    
    return clients;
  }

	@Override
  public Client addClient(Client client) {
	  clientRepository.addClient(client);
	  
	  return client;
  }

	@Override
  public Client updateClient(Client client) {
	  client = clientRepository.updateClient(client);
	  return client;
  }

}
