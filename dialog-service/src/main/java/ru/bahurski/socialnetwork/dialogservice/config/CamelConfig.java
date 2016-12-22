package ru.bahurski.socialnetwork.dialogservice.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bahurski.socialnetwork.core.model.dialog.Dialog;
import ru.bahurski.socialnetwork.core.service.Endpoints;
import ru.bahurski.socialnetwork.core.util.converter.camel.FilterDecoder;
import ru.bahurski.socialnetwork.core.util.encoder.JsonContentEncoder;

import javax.jms.ConnectionFactory;
import java.awt.print.Pageable;

/**
 * Created by Ivan on 21/12/2016.
 */
@Configuration
public class CamelConfig {
    private final static String BROKER_URL = "tcp://localhost:61616";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(BROKER_URL);
    }


    @Bean
    public RouteBuilder serverRoutes() {
        return new RouteBuilder() {
            private JsonContentEncoder createMessage = new JsonContentEncoder(Dialog.class);
            private JsonContentEncoder addMessage = new JsonContentEncoder(Long.class, Pageable.class);

            @Override
            public void configure() throws Exception {
                from(Endpoints.DIALOGS_CREATE.getEndpointUrl())
                        .filter(new FilterDecoder(createMessage))
                        .to("bean:dialogService?method=create");

                from(Endpoints.DIALOGS_ADD_MESSAGE.getEndpointUrl())
                        .filter(new FilterDecoder(addMessage))
                        .to("bean:dialogService?method=addMessage");

                // todo
            }
        };
    }
}
