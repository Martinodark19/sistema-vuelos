package vuelos.microservicio_vuelos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import vuelos.microservicio_vuelos.entity.Airport;

public interface AirportRepository extends ReactiveCrudRepository<Airport, Integer> {
    // Método para buscar aeropuertos por país
    Flux<Airport> findByCountry(String country);
}
