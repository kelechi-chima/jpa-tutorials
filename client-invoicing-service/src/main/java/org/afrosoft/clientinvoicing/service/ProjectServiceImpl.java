package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.afrosoft.clientinvoicing.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

  private ProjectRepository projectRepository;
  
  @Autowired
  public ProjectServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Transactional
  @Override
  public Project addProject(Project project, Client client) {
    project = projectRepository.addProject(project, client);
    
    return project;
  }

  @Transactional
  @Override
  public Project updateProject(Project project) {
    project = projectRepository.updateProject(project);
    
    return project;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Project> findByClientName(Client client) {
    List<Project> projects = projectRepository.findByClientName(client.getName());
    
    return projects;
  }
  
  @Transactional(readOnly = true)
  @Override
  public Project findByName(String projectName) {
  	Project project = projectRepository.findByProjectName(projectName);
  	
	  return project;
  }

  @Transactional
	@Override
  public void removeProject(Project project) {
    projectRepository.removeProject(project);
  }

}
