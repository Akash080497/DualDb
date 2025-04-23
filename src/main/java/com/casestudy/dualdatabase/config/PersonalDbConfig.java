package com.casestudy.dualdatabase.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "com.casestudy.dualdatabase.personal.repo",
	entityManagerFactoryRef = "personalEntityManager",
	transactionManagerRef = "personalTransactionManager"
		)
  public class PersonalDbConfig {
	
	   @Primary
	   @Bean
	   @ConfigurationProperties("spring.datasource")
	   public DataSourceProperties personalDataSourceProperties() {
	       return new DataSourceProperties();
	   }
	  
	   @Primary
	   @Bean
	   public DataSource personalDataSource() {
	       return personalDataSourceProperties().initializeDataSourceBuilder().build();
	   }
	   
	   @Primary
	   @Bean
	   public LocalContainerEntityManagerFactoryBean personalEntityManager(
	           EntityManagerFactoryBuilder builder) {
	       return builder
	               .dataSource(personalDataSource())
	               .packages("com.casestudy.dualdatabase.personal.entity")
	               .persistenceUnit("personal")
	               .build();
	   }
	   
	   @Primary
	   @Bean
	   public PlatformTransactionManager personalTransactionManager(
	           @Qualifier("personalEntityManager") EntityManagerFactory emf) {
	       return new JpaTransactionManager(emf);
	   }

}
