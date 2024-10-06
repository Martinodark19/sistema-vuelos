package vuelos.microservicio_vuelos.configBeans;


import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.withUrl("r2dbc:mysql://localhost:3306/microservicio-vuelos")
                .username("root")
                .password("")
                .build();
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .build();
    }
}
