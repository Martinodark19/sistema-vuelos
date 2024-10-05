package autenticacion.microservicio_autenticacion.dto.AutenticationDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordDto 
{
    @NotBlank
    private String email;

}
