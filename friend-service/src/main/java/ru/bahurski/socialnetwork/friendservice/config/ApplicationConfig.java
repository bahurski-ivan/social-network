package ru.bahurski.socialnetwork.friendservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import ru.bahurski.socialnetwork.core.service.FriendService;
import ru.bahurski.socialnetwork.friendservice.repository.FriendRelationRepo;
import ru.bahurski.socialnetwork.friendservice.repository.FriendRequestRepo;
import ru.bahurski.socialnetwork.friendservice.service.FriendServiceImpl;

/**
 * Created by Ivan on 22/12/2016.
 */
@Configuration
@Import(DataBaseConfig.class)
public class ApplicationConfig {
    @Bean
    FriendService dialogService(FriendRelationRepo friendRelationRepo, FriendRequestRepo friendRequestRepo, PlatformTransactionManager transactionManager) {
        return new FriendServiceImpl(friendRelationRepo, friendRequestRepo, transactionManager);
    }
}