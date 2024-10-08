package vuelos.microservicio_vuelos.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import vuelos.microservicio_vuelos.entity.Flight.FlightStatus;

@Data
@NoArgsConstructor
public class FlightDto 
{
    @NotNull
    @Size(min = 3, max = 100, message = "El origen debe tener entre 3 y 100 caracteres.")
    private String origin;

    @NotNull
    @Size(min = 3, max = 100, message = "El destino debe tener entre 3 y 100 caracteres.")
    private String destination;

    @NotNull
    private LocalDateTime departureDate;

    @NotNull
    private LocalDateTime arrivalDate;

    @Min(value = 1, message = "La capacidad mínima es 1.")
    @Max(value = 500, message = "La capacidad máxima es 500.")
    private Integer capacity;

    @Min(value = 0, message = "Los asientos disponibles no pueden ser negativos.")
    private Integer availableSeats;

    @NotNull
    private FlightStatus flightStatus;
}
