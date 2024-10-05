package vuelos.microservicio_vuelos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import vuelos.microservicio_vuelos.entity.FlightSchedule;

public interface FlightScheduleRepository extends ReactiveCrudRepository<FlightSchedule, Integer> {

    // Método para buscar horarios por aeropuerto de origen y destino
    Flux<FlightSchedule> findByIdAirportOriginAndIdAirportDestination(Integer idAirportOrigin, Integer idAirportDestination);
}
