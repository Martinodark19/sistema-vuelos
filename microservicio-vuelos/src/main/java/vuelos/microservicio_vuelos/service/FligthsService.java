package vuelos.microservicio_vuelos.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import vuelos.microservicio_vuelos.dto.FlightDto;
import vuelos.microservicio_vuelos.entity.Flight;
import vuelos.microservicio_vuelos.repository.FlightRepository;

@Service
public class FligthsService
{
    private FlightRepository flightRepository;

    public FligthsService(FlightRepository flightRepository)
    {
        this.flightRepository = flightRepository;
    }

    public Mono<Boolean> createFlight(FlightDto flight) 
    {
        // Crea el objeto Flight a partir del DTO
        Flight flightObj = new Flight();
        flightObj.setOrigin(flight.getOrigin());
        flightObj.setDestination(flight.getDestination());
        flightObj.setDepartureDate(flight.getDepartureDate());
        flightObj.setArrivalDate(flight.getArrivalDate());
        flightObj.setCapacity(flight.getCapacity());
        flightObj.setAvailableSeats(flight.getAvailableSeats());
        flightObj.setFlightStatus(flight.getFlightStatus());
        System.out.println(flight.getOrigin());
        System.out.println(flight.getDestination());

        // Guardamos la entidad en el repositorio de manera reactiva y devolvemos un Mono<Boolean>
        return flightRepository.save(flightObj)
            .map(savedFlight -> {

                System.out.println("por el servicio: " + savedFlight);
                return true;  // Devuelve true si la entidad fue guardada correctamente
            })
            .onErrorResume(error -> {
                System.out.println(error);
                return Mono.just(false);
            });  
    }
    
}
