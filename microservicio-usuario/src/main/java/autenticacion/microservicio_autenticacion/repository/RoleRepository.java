package autenticacion.microservicio_autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import autenticacion.microservicio_autenticacion.entity.RoleEntity;
import autenticacion.microservicio_autenticacion.entity.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{
    Optional<RoleEntity> findByName(RoleEnum name);
}
