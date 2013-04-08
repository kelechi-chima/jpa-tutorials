package org.afrosoft.clientinvoicing.dao;

import java.util.List;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.springframework.stereotype.Repository;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDao implements ProjectDao {
  
  @Override
  public Project add(Project project, Client client) {
    Client currentClient = entityManager.merge(client);
    currentClient.getProjects().add(project);
    project.setClient(currentClient);
    entityManager.persist(project);
    
    logger.info("Added project with id '{}'", project.getId());
    
    return project;
  }

  @Override
  public Project update(Project project) {
    project = entityManager.merge(project);
    
    logger.info("Updated project: {}", project);
    
    return project;
  }
  
  @Override
  public List<Project> findByClientName(String clientName) {
    List<Project> projects = entityManager.createNamedQuery("findProjectsByClientName", Project.class)
        .setParameter("clientName", clientName)
        .getResultList();

    logger.info("Found {} projects for {}", projects.size(), clientName);
      
    return projects;
  }

  @Override
  public Project findByProjectName(String projectName) {
  	List<Project> projects = entityManager.createNamedQuery("findProjectByProjectName", Project.class)
  			.setParameter("projectName", projectName)
  			.getResultList();
  		
  	if (projects != null && !projects.isEmpty()) {
  		return projects.get(0);
  	}
  	
	  return null;
  }

  @Override
  public void remove(Project project) {
    entityManager.remove(project);
  }

}
