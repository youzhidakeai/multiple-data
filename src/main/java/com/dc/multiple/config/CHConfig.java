package com.dc.multiple.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Configuration
@EntityScan(basePackages = "com.dc.multiple.entity.ck")
@EnableJpaRepositories(
        basePackages = "com.dc.multiple.dao.ck",
        entityManagerFactoryRef = "ckEntityManagerFactoryBean",
        transactionManagerRef = "ckTransactionManager"
)
public class CHConfig {

    private final DataSource dataSource;

    private final JpaProperties jpaProperties;

    private final EntityManagerFactoryBuilder factoryBuilder;

    private final HibernateProperties hibernateProperties;

    public CHConfig(@Qualifier("ckDataSource") DataSource dataSource,
                              JpaProperties jpaProperties, EntityManagerFactoryBuilder factoryBuilder,
                              HibernateProperties hibernateProperties) {
        this.dataSource = dataSource;
        this.jpaProperties = jpaProperties;
        this.factoryBuilder = factoryBuilder;
        this.hibernateProperties = hibernateProperties;
    }

    @Bean(name = "ckEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        return factoryBuilder.dataSource(dataSource)
                .properties(getVendorProperties())
                .packages("com.dc.multiple.entity.ck")
                .persistenceUnit("ckPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        Map<String, String> properties = jpaProperties.getProperties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        return hibernateProperties.determineHibernateProperties(properties, new HibernateSettings());
    }

    @Bean(name = "ckEntityManager")
    public EntityManager entityManager() {
        return Objects.requireNonNull(entityManagerFactoryBean().getObject()).createEntityManager();
    }

    /**
     * jpa事务管理
     *
     * @return
     */
    @Bean(name = "ckTransactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
