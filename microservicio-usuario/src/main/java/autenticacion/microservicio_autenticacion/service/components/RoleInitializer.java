package autenticacion.microservicio_autenticacion.service.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import autenticacion.microservicio_autenticacion.entity.RoleEntity;
import autenticacion.microservicio_autenticacion.entity.RoleEnum;
import autenticacion.microservicio_autenticacion.repository.RoleRepository;

@Component
public class RoleInitializer 
{
    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(RoleEnum.ROLE_USER).isEmpty()) {
                roleRepository.save(new RoleEntity(RoleEnum.ROLE_USER));
            }

            if (roleRepository.findByName(RoleEnum.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new RoleEntity(RoleEnum.ROLE_ADMIN));
            }

            if (roleRepository.findByName(RoleEnum.ROLE_MODERATOR).isEmpty()) {
                roleRepository.save(new RoleEntity(RoleEnum.ROLE_MODERATOR));
            }

            // Agrega más roles si es necesario
        };
    }
}
