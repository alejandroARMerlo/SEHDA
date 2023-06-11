package ar.edu.uno.sehda.persistence;

import ar.edu.uno.sehda.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol,Long> {
}
