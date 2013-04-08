package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;

public interface ProjectDao {

  Project add(Project project, Client client);
  
  Project update(Project project);
  
  List<Project> findByClientName(String clientName);
  
  Project findByProjectName(String projectName);
  
  void remove(Project project);
  
}
