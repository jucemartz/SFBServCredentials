package com.lottus.sfbservice.credentials.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.lottus.virtualcampus.banner.domain",
        entityManagerFactoryRef = "bannerEntityManager",
        transactionManagerRef = "bannerTransactionManager")
public class BannerPersistenceConfiguration {

    @Primary
    @Bean(name = "bannerEntityManager")
    protected LocalContainerEntityManagerFactoryBean bannerEntityManager(@Autowired Environment env) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(bannerDataSource());
        em.setPackagesToScan("com.lottus.virtualcampus.banner.domain");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("banner.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("banner.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "banner.datasource")
    protected DataSource bannerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "bannerTransactionManager")
    protected PlatformTransactionManager bannerTransactionManager(
            @Qualifier("bannerEntityManager") LocalContainerEntityManagerFactoryBean bannerEntityManager) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(bannerEntityManager.getObject());
        return transactionManager;
    }
}
