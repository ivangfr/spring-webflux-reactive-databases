package com.ivanfranchin.notificationapi.config;

//import io.r2dbc.spi.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
//import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
//import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseInitializerConfig {

    // Due to this issue [https://github.com/mirromutth/r2dbc-mysql/issues/248], we are initializing the database
    // while running init-environment.sh

//    @Bean
//    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//
//        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
//        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db-schema.sql")));
//        initializer.setDatabasePopulator(populator);
//
//        return initializer;
//    }
}
