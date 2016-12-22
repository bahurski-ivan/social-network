package ru.bahurski.socialnetwork.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import ru.bahurski.socialnetwork.core.service.UserService;
import ru.bahurski.socialnetwork.userservice.repository.UserRepo;
import ru.bahurski.socialnetwork.userservice.service.UserServiceImpl;

/**
 * main application config
 */
@Configuration
@Import(DataBaseConfig.class)
public class ApplicationConfig {
    @Bean
    UserService dialogService(UserRepo userRepo, PlatformTransactionManager transactionManager) {
        return new UserServiceImpl(userRepo, transactionManager);
    }
}
