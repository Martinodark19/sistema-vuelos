package autenticacion.microservicio_autenticacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import autenticacion.microservicio_autenticacion.entity.UserEntity;




@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> 
{
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);    
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
