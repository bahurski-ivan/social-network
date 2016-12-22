package ru.bahurski.socialnetwork.dialogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import ru.bahurski.socialnetwork.core.service.DialogService;
import ru.bahurski.socialnetwork.dialogservice.repository.ChatMessageRepo;
import ru.bahurski.socialnetwork.dialogservice.repository.DialogRepo;
import ru.bahurski.socialnetwork.dialogservice.service.DialogServiceImpl;

/**
 * main app configuration
 */
@Configuration
@Import({DataBaseConfig.class, CamelConfig.class})
public class ApplicationConfig {
    @Bean
    DialogService dialogService(ChatMessageRepo chatMessageRepo, DialogRepo dialogRepo, PlatformTransactionManager transactionManager) {
        return new DialogServiceImpl(chatMessageRepo, dialogRepo, transactionManager);
    }
}
