package org.afrosoft.clientinvoicing.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This Spring configuration class defines an application context to support integration tests.<p>
 * 
 * It reuses the main application context defined in the xml configuration file, while defining
 * additional components to support integration testing such as a DataSource and JdbcTemplate. 
 */
@Configuration
@ImportResource("classpath:/client-invoicing-repository-config.xml")
@Profile("dev")
public class TestConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}
