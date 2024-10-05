package autenticacion.microservicio_autenticacion.dto.AutenticationDto;

import lombok.Data;

@Data
public class LoginRequestDto 
{
    private String email;
    private String password;
}
