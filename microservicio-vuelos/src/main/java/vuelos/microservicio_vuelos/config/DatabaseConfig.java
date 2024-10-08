package vuelos.microservicio_vuelos.config;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

@Configuration
public class DatabaseConfig  
{
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


    @Bean
    public ApplicationRunner checkDatabaseConnection(DatabaseClient databaseClient) {
        return args -> {
            databaseClient.sql("SELECT 1")
                    .fetch()
                    .first()
                    .doOnSuccess(result -> System.out.println("Conexión a la base de datos exitosa: " + result))
                    .doOnError(error -> System.err.println("Error al conectarse a la base de datos: " + error.getMessage()))
                    .subscribe();
        };
    }


    @Bean
    public ApplicationRunner initializeDatabaseRunner(DatabaseClient databaseClient) {
        return args -> initializeDatabase(databaseClient).subscribe();
    }

    public Mono<Void> initializeDatabase(DatabaseClient databaseClient) {
        // Creación de la tabla flights
        Mono<Void> createFlightsTable = databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS flights (
                    id_flight INT PRIMARY KEY AUTO_INCREMENT,
                    origin VARCHAR(100) NOT NULL,
                    destination VARCHAR(100) NOT NULL,
                    departure_date TIMESTAMP NOT NULL,
                    arrival_date TIMESTAMP NOT NULL,
                    capacity INT NOT NULL CHECK (capacity >= 1 AND capacity <= 500),
                    available_seats INT CHECK (available_seats >= 0),
                    flight_status VARCHAR(20) NOT NULL
                );
            """)
            .then();

        // Creación de la tabla aircraft
        Mono<Void> createAircraftTable = databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS aircraft (
                    id_aircraft INT PRIMARY KEY AUTO_INCREMENT,
                    model VARCHAR(100) NOT NULL,
                    manufacturer VARCHAR(100) NOT NULL,
                    capacity INT NOT NULL CHECK (capacity >= 1 AND capacity <= 500)
                );
            """)
            .then();

        // Creación de la tabla airports
        Mono<Void> createAirportsTable = databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS airports (
                    id_airport INT PRIMARY KEY AUTO_INCREMENT,
                    airport_name VARCHAR(150) NOT NULL,
                    location VARCHAR(150) NOT NULL,
                    country VARCHAR(100) NOT NULL
                );
            """)
            .then();

        // Creación de la tabla flight_schedule
        Mono<Void> createFlightScheduleTable = databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS flight_schedule (
                    id_schedule INT PRIMARY KEY AUTO_INCREMENT,
                    id_flight INT NOT NULL,
                    id_airport_origin INT NOT NULL,
                    id_airport_destination INT NOT NULL,
                    departure_time TIMESTAMP NOT NULL,
                    arrival_time TIMESTAMP NOT NULL,
                    FOREIGN KEY (id_flight) REFERENCES flights(id_flight),
                    FOREIGN KEY (id_airport_origin) REFERENCES airports(id_airport),
                    FOREIGN KEY (id_airport_destination) REFERENCES airports(id_airport)
                );
            """)
            .then();

        // Ejecutar todas las creaciones de tablas en orden
        return createFlightsTable
                .then(createAircraftTable)
                .then(createAirportsTable)
                .then(createFlightScheduleTable)
                .onErrorResume(error -> {
                    System.out.println("Error al crear tablas: " + error.getMessage());
                    return Mono.empty();
                });
    }
}
