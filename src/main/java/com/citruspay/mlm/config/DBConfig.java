package com.citruspay.mlm.config;

import java.util.HashMap;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class DBConfig {
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws Throwable {
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager
				.setPackagesToScan("com.citruspay.mlm.domain");
		entityManager.setPersistenceUnitName("citruspay_mlm");
		entityManager.setDataSource(primaryDataSource());
		entityManager.setJpaPropertyMap(propertiesMap());
		return entityManager;
	}
	
	@Bean(name = "citrusEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean citrusEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(secondaryDataSource());
		entityManager
				.setPackagesToScan("com.citruspay.mlm.domain");
		entityManager.setPersistenceUnitName("citruspay_mlm");
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setJpaPropertyMap(propertiesMap());
		return entityManager;
	}

	@Bean
	@ConfigurationProperties(prefix = "datasource.primary")
	public DataSource primaryDataSource() throws NamingException {
		JndiTemplate jndiTemplate = new JndiTemplate();
		return new DriverManagerDataSource();
//		return (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/citruspaysettlement");
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.secondary")
	public DataSource secondaryDataSource() {
		
		return new DriverManagerDataSource();
	}
	
	private HashMap<String, Object> propertiesMap() {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.ejb.naming_strategy",
				"org.hibernate.cfg.ImprovedNamingStrategy");
		
		return properties;
	}
}
