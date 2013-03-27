package org.afrosoft.clientinvoicing.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.afrosoft.clientinvoicing.domain.Client;
import org.afrosoft.clientinvoicing.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("projectRepository")
public class ProjectRepositoryImpl implements ProjectRepository {

  private static final Logger LOG = LoggerFactory.getLogger(ProjectRepositoryImpl.class);
  
  @PersistenceContext(unitName="client-invoicing", type=PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;
  
  @Override
  public Project addProject(Project project, Client client) {
    Client currentClient = entityManager.merge(client);
    currentClient.getProjects().add(project);
    project.setClient(currentClient);
    entityManager.persist(project);
    
    LOG.info("Project added with id '{}'", project.getId());
    
    return project;
  }

  @Override
  public Project updateProject(Project project) {
    project = entityManager.merge(project);
    
    LOG.info("Updated project: {}", project);
    
    return project;
  }
  
  @Override
  public List<Project> findByClientName(String clientName) {
    List<Project> projects = entityManager.createNamedQuery("findProjectsByClientName", Project.class)
        .setParameter("clientName", clientName)
        .getResultList();

    LOG.info("Found {} projects for {}", projects.size(), clientName);
      
    return projects;
  }

  @Override
  public Project findByProjectName(String projectName) {
  	Project project = entityManager.createNamedQuery("findProjectByProjectName", Project.class)
  			.setParameter("projectName", projectName)
  			.getSingleResult();
  		
	  return project;
  }

  @Override
  public void removeProject(Project project) {
    entityManager.remove(project);
  }

}
