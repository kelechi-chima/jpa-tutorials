package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;

public interface ClientDao {

  List<Client> getAllClients();
  
  Client add(Client client);
  
  Client update(Client client);
  
  void remove(Client client);
  
}
