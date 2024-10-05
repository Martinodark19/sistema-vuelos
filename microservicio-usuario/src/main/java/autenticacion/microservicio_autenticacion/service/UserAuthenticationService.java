package autenticacion.microservicio_autenticacion.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import autenticacion.microservicio_autenticacion.config.utils.JwtUtils;
import autenticacion.microservicio_autenticacion.repository.UserRepository;

@Service
public class UserAuthenticationService 
{ 
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserAuthenticationService(JwtUtils jwtUtils,AuthenticationManager authenticationManager,UserRepository userRepository,PasswordEncoder passwordEncoder)
    {
        this.jwtUtils = jwtUtils;
        this.authenticationManager=authenticationManager; 
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public String loginMethod(String username, String password) 
    {
            try 
            {
                // Crear un objeto UsernamePasswordAuthenticationToken con las credenciales del usuario
                UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(username, password);

                // Intentar autenticar al usuario con AuthenticationManager
                Authentication authentication = authenticationManager.authenticate(authenticationToken);

                // Si la autenticación es exitosa, establecer el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generar el token JWT
                String createJwt = jwtUtils.createToken(authentication);

                System.out.println("Se creó el token y se autenticó correctamente");
                return createJwt;
            } 
            catch (AuthenticationException e) 
            {
                // Capturar cualquier excepción relacionada con la autenticación (ej: credenciales incorrectas)
                return "Error: Credenciales incorrectas";
            } 
            catch (Exception error) 
            {
                // Capturar otros errores generales
                return "Ha ocurrido un error interno: " + error.getMessage();
            }
    }
}
