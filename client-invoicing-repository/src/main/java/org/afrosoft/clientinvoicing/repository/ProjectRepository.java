package org.afrosoft.clientinvoicing.repository;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;

public interface ProjectRepository {

  Project addProject(Project project, Client client);
  
  Project updateProject(Project project);
  
  List<Project> findByClientName(String clientName);
  
  void removeProject(Project project);
  
}
