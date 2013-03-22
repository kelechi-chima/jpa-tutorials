package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;

public interface ClientService {

  List<Client> getAllClients();
  
  void addClient(Client client);
  
  Client updateClient(Client client);
  
}
