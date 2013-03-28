package org.afrosoft.clientinvoicing.service;

import java.util.List;

import org.afrosoft.clientinvoicing.dao.ProjectDao;
import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

  private ProjectDao projectDao;
  
  @Autowired
  public ProjectServiceImpl(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Transactional
  @Override
  public Project addProject(Project project, Client client) {
    project = projectDao.addProject(project, client);
    
    return project;
  }

  @Transactional
  @Override
  public Project updateProject(Project project) {
    project = projectDao.updateProject(project);
    
    return project;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Project> findByClientName(Client client) {
    List<Project> projects = projectDao.findByClientName(client.getName());
    
    return projects;
  }
  
  @Transactional(readOnly = true)
  @Override
  public Project findByName(String projectName) {
  	Project project = projectDao.findByProjectName(projectName);
  	
	  return project;
  }

  @Transactional
	@Override
  public void removeProject(Project project) {
    projectDao.removeProject(project);
  }

}
