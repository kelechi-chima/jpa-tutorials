package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.afrosoft.clientinvoicing.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

  private ProjectRepository projectRepository;
  
  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Project addProject(Project project, Client client) {
    project = projectRepository.addProject(project, client);
    
    return project;
  }

  @Override
  public Project updateProject(Project project) {
    project = projectRepository.updateProject(project);
    
    return project;
  }

  @Override
  public List<Project> findByClient(Client client) {
    List<Project> projects = projectRepository.findByClientName(client.getName());
    
    return projects;
  }

  @Override
  public void removeProject(Project project) {
    projectRepository.removeProject(project);
  }

}
