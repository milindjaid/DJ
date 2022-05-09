package com.example.demo.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.intercepters.RequestInterceptor;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
//@EnableCaching
@EnableAutoConfiguration
public class DBCongifiguration implements WebMvcConfigurer {

	@Autowired
	org.springframework.core.env.Environment env;
	
	@Primary
	@Bean(name = "dataSource")
	public DataSource dataSource() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		DataSource ds1 = new DriverManagerDataSource("jdbc:mysql://localhost:3306/demo", "root", "root");
		try {
			Connection con = ds1.getConnection();
			System.out.println("Connection Successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds1;
//		return DataSourceBuilder.create().build();
	}

	@Bean(name = "entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() throws ClassNotFoundException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		System.out.println("DS==>:: " + dataSource());
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setPackagesToScan(new String[] { "com.example.demo.entity" });
		System.out.println("SSSS==>::: " + sessionFactory);
		return sessionFactory;
	}
	
	@Bean(initMethod = "abc") 
	public Customer getCustomer() {
		System.out.println("Instantiation customer bean>>>");
		return new Customer();	
	}
	
	public void abc() {
		System.out.println("This is init method for LocalSessionFactoryBean>>>>>");
	}

	Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.ddl-auto", "update");
		properties.put("show-sql", true);
		properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/demo");
		properties.put("hibernate.connection.username", "root");
		properties.put("hibernate.connection.password", "root");
		properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		return properties;
	}

	@Bean(name = "hibernateTemplate")
	HibernateTemplate getHibernateTemplate() throws ClassNotFoundException {
		return new HibernateTemplate(sessionFactory().getObject());
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws ClassNotFoundException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getRequestInterceptor());
	}
	
	@Bean
	public RequestInterceptor getRequestInterceptor() {
		return new RequestInterceptor();
	}
	
	@Profile("dev")
	@Bean
	public Order devOrder() {
		System.out.println("Creating dev profile customer bean...");
		Order order =  new Order(1l, "250", "200", "50", "M K");
		return order;
	}
	
	@Profile("test")
	@Bean
	public Order testOrder() {
		System.out.println("Creating test profile customer bean...");
		Order order =  new Order(1l, "2500", "2000", "500", "A K");
		return order;
	}
	
	@Profile("prod")
	@Bean
	public Order prodOrder() {
		System.out.println("Creating prod profile customer bean..."+Arrays.toString(env.getActiveProfiles()));
		Order order =  new Order(1l, "25000", "20000", "5000", "P K");
		return order;
	}
}
