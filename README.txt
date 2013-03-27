This project was for exploring JPA and its features.

It uses JPA 2.0.
Initially the persistence provider was EclipseLink 2.4.0, but I changed it to Hibernate 4.
The simple reason for the change was I wanted to get started quickly with JBoss AS7 which includes Hibernate.

Spring 3.2 was added to provide several services.
In the web tier I initially used simple servlets and JSPs instead of Spring MVC so I could focus on JPA. Afterwards I 
replaced the plain servlets with Spring HttpRequestHandlerServlets to enable dependency injection of Spring managed beans.
In the service tier Spring accesses the JTA transaction deployed in JBoss JNDI and decorates the service components. 
It simplifies integration testing in the data/repository layer.

The backend used is Postgresql 9.1 with JDBC4.