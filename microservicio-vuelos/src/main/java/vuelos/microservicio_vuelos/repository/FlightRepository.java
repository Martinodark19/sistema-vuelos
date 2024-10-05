package vuelos.microservicio_vuelos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import vuelos.microservicio_vuelos.entity.Flight;

public interface FlightRepository extends ReactiveCrudRepository<Flight, Integer> {
    
    // Método para buscar vuelos por origen y destino
    Flux<Flight> findByOriginAndDestination(String origin, String destination);

    // Método para buscar vuelos por estado
    Flux<Flight> findByFlightStatus(String status);
}

