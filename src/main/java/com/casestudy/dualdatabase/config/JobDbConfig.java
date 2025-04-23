package com.casestudy.dualdatabase.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
   basePackages = "com.casestudy.dualdatabase.job.repo",
   entityManagerFactoryRef = "jobEntityManager",
   transactionManagerRef = "jobTransactionManager"
)
public class JobDbConfig {
   @Bean
   @ConfigurationProperties("spring.secondary-datasource")
   public DataSourceProperties jobDataSourceProperties() {
       return new DataSourceProperties();
   }
   @Bean
   public DataSource jobDataSource() {
       return jobDataSourceProperties().initializeDataSourceBuilder().build();
   }
   @Bean
   public LocalContainerEntityManagerFactoryBean jobEntityManager(
           EntityManagerFactoryBuilder builder) {
       return builder
               .dataSource(jobDataSource())
               .packages("com.casestudy.dualdatabase.job.entity")
               .persistenceUnit("job")
               .build();
   }
   @Bean
   public PlatformTransactionManager jobTransactionManager(
           @Qualifier("jobEntityManager") EntityManagerFactory emf) {
       return new JpaTransactionManager(emf);
   }
}