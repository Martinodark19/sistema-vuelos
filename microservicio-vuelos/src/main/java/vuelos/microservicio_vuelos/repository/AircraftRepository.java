package vuelos.microservicio_vuelos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import vuelos.microservicio_vuelos.entity.Aircraft;

public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, Integer> {
    // Puedes agregar métodos adicionales si es necesario
}
