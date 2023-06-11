package ar.edu.uno.sehda.persistence;

import ar.edu.uno.sehda.entities.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Long>{
    DBUser findByUsername(String username);
}
