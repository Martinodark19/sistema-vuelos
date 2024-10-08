package autenticacion.microservicio_autenticacion.controller.authentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autenticacion.microservicio_autenticacion.dto.AutenticationDto.LoginRequestDto;
import autenticacion.microservicio_autenticacion.dto.AutenticationDto.SignupRequest;
import autenticacion.microservicio_autenticacion.entity.RoleEntity;
import autenticacion.microservicio_autenticacion.entity.RoleEnum;
import autenticacion.microservicio_autenticacion.entity.UserEntity;
import autenticacion.microservicio_autenticacion.repository.RoleRepository;
import autenticacion.microservicio_autenticacion.repository.UserRepository;
import autenticacion.microservicio_autenticacion.service.UserAuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class AuthenticationLogin 
{
    private UserAuthenticationService userAuthenticationService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public AuthenticationLogin(UserAuthenticationService userAuthenticationService,UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder)
    {
        this.userAuthenticationService = userAuthenticationService;
        this.userRepository = userRepository;
        this.roleRepository =roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) 
    {
        System.out.println("paso por aqui");
        System.out.println(signUpRequest.getRole());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) 
        {
        return ResponseEntity
            .badRequest()
            .body("El usuario a registrar ya existe con el mismo username. Intente con uno que no haya sido creado");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) 
        {
        return ResponseEntity.badRequest().body("El usuario a registrar ya existe con el mismo email. Intente con uno que no haya sido creado");
        }

        // encoding password 
        String passwordEncoderNew = passwordEncoder.encode(signUpRequest.getPassword());

        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getUsername(), 
                signUpRequest.getEmail(),
                passwordEncoderNew);

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) 
        {
            System.out.println("entro al primer if");
            RoleEntity userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        } 
        else 
        {
            System.out.println("entro al primer else");

        strRoles.forEach(role -> {
            switch (role) {
            case "admin":
            RoleEntity adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
            case "mod":
            RoleEntity modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
            default:
            RoleEntity userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            }
        });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginMethod(@RequestBody LoginRequestDto loginRequestDto)
    {
        try
        {
            // validamos los datos del login
            if(loginRequestDto.getEmail() !=null && loginRequestDto.getPassword()!=null)
            {                       
                String autentication = userAuthenticationService.loginMethod(loginRequestDto.getEmail(),loginRequestDto.getPassword());
                return ResponseEntity.ok().body(autentication);
            }
            else
            {
                return ResponseEntity.status(404).body("Las credenciales ingresadas son incorrectas");
        }
        }
        catch(Exception error)
        {
            return ResponseEntity.status(404).body("Ha ocurrido un error interno al autenticar al usuario " + error);
        }
    }


    @GetMapping("/prueba")
    public ResponseEntity<String> testMethod()
    {
        return ResponseEntity.ok().body("el metodo funciona felicidades");
    }


}
