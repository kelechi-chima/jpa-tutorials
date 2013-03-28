package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;

public interface ClientDao {

  List<Client> getAllClients();
  
  Client addClient(Client client);
  
  Client updateClient(Client client);
  
  void removeClient(Client client);
  
}
