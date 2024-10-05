package vuelos.microservicio_vuelos.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("flight_schedule")
public class FlightSchedule 
{

    @Id
    private Integer idSchedule;

    @NotNull
    private Integer idFlight;

    @NotNull
    private Integer idAirportOrigin;

    @NotNull
    private Integer idAirportDestination;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;
}