package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;

public interface ProjectService {

Project addProject(Project project, Client client);
  
  Project updateProject(Project project);
  
  List<Project> findByClient(Client client);
  
  void removeProject(Project project);
  
}
