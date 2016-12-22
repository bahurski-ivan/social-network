package ru.bahurski.socialnetwork.photoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import ru.bahurski.socialnetwork.core.service.PhotoService;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoDataRepo;
import ru.bahurski.socialnetwork.photoservice.repository.PhotoRepo;
import ru.bahurski.socialnetwork.photoservice.service.PhotoServiceImpl;

/**
 * Created by Ivan on 22/12/2016.
 */
@Configuration
@Import(DataBaseConfig.class)
public class ApplicationConfig {
    @Bean
    PhotoService dialogService(PhotoRepo photoRepo, PhotoDataRepo photoDataRepo, PlatformTransactionManager transactionManager) {
        return new PhotoServiceImpl(transactionManager, photoDataRepo, photoRepo);
    }
}