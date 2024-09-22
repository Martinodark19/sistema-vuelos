package autenticacion.microservicio_autenticacion.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import autenticacion.microservicio_autenticacion.entity.UserEntity;
import autenticacion.microservicio_autenticacion.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) 
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        // Buscar el usuario en la base de datos
        Optional<UserEntity> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isEmpty()) 
        {
            throw new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username);
        }

        UserEntity user = optionalUser.get();
        // obtener roles 

            // Convierte los roles (RoleEntity) a GrantedAuthority
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Asume que getName() devuelve un RoleEnum o String
            .collect(Collectors.toSet());
        

        // Construir un objeto UserDetails a partir de la entidad UserEntity
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities) // Asume que el método getAuthorities() devuelve los roles/authorities del usuario
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
