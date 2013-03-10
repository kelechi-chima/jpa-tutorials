package org.afrosoft.clientinvoicing.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTest {

	private static Logger LOG = LoggerFactory.getLogger(JdbcTest.class);
	
	public static void main(String[] args) throws Exception {
	  String url = "jdbc:postgresql://localhost/clientinvoicing";
	  Properties props = new Properties();
	  props.setProperty("user", "kelechi");
	  props.setProperty("password", "owerri82");
	  
	  Connection conn = null;
	  try {
	  	conn = DriverManager.getConnection(url, props);
	  	LOG.info("Got connection: {}", conn);
	  } finally {
	  	if (conn != null) {
	  		conn.close();
	  		LOG.info("Closed connection: {}", conn);
	  	}
	  }
	  
  }
}
