package vuelos.microservicio_vuelos.entity;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("aircraft")
public class Aircraft {

    @Id
    private Integer idAircraft;

    @NotNull
    @Size(min = 2, max = 100, message = "El modelo debe tener entre 2 y 100 caracteres.")
    private String model;

    @NotNull
    @Size(min = 2, max = 100, message = "El fabricante debe tener entre 2 y 100 caracteres.")
    private String manufacturer;

    @Min(value = 1, message = "La capacidad mínima es 1.")
    @Max(value = 500, message = "La capacidad máxima es 500.")
    private Integer capacity;
}