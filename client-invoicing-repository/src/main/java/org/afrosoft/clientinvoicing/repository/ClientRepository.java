package org.afrosoft.clientinvoicing.repository;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;

public interface ClientRepository {

  List<Client> getAllClients();
  
  Client addClient(Client client);
  
  Client updateClient(Client client);
  
  void removeClient(Client client);
  
}
