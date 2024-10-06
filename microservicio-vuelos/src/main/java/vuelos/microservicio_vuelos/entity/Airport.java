package vuelos.microservicio_vuelos.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("airports")

public class Airport {

    @Id
    private Integer idAirport;

    @NotNull
    @Size(min = 3, max = 150, message = "El nombre del aeropuerto debe tener entre 3 y 150 caracteres.")
    private String airportName;

    @NotNull
    @Size(min = 3, max = 150, message = "La ubicación debe tener entre 3 y 150 caracteres.")
    private String location;

    @NotNull
    @Size(min = 2, max = 100, message = "El país debe tener entre 2 y 100 caracteres.")
    private String country;
}