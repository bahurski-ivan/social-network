package ru.bahurski.socialnetwork.photoservice.service;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.bahurski.socialnetwork.core.model.photo.Photo;
import ru.bahurski.socialnetwork.core.service.PhotoService;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoDataRepo;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoRepo;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by Ivan on 21/12/2016.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = ru.bahurski.socialnetwork.photoservice.repository.Marker.class)
public class TestAppConfig {
    @Bean
    PhotoService dialogService(PhotoRepo photoRepo, PhotoDataRepo photoDataRepo, PlatformTransactionManager transactionManager) {
        return new PhotoServiceImpl(transactionManager, photoDataRepo, photoRepo);
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:dbTest;MODE=Oracle;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource,
                                                     JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan(Photo.class.getPackage().getName());
        factory.setDataSource(dataSource);
        factory.setJpaProperties(jpaProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
            throws PropertyVetoException {
        return new JpaTransactionManager(emf);
    }
}
