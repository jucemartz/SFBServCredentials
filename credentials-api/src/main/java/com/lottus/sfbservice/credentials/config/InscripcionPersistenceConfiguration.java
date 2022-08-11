package com.lottus.sfbservice.credentials.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.lottus.sfbservice.credentials.persistence",
        entityManagerFactoryRef = "inscripcionEntityManager",
        transactionManagerRef = "inscripcionTransactionManager")
@EnableJpaAuditing
public class InscripcionPersistenceConfiguration {

    @Bean(name = "inscripcionEntityManager")
    protected LocalContainerEntityManagerFactoryBean inscripcionEntityManager(@Autowired Environment env) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(inscripcionDataSource());
        em.setPackagesToScan("com.lottus.sfbservice.credentials.domain.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("inscricion.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("inscripcion.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @ConfigurationProperties(prefix = "inscripcion.datasource")
    protected DataSource inscripcionDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "inscripcionTransactionManager")
    protected PlatformTransactionManager inscripcionTransactionManager(
            @Qualifier("inscripcionEntityManager") LocalContainerEntityManagerFactoryBean inscripcionEntityManager) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(inscripcionEntityManager.getObject());
        return transactionManager;
    }
}
