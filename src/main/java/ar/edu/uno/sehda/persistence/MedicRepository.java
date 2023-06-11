package ar.edu.uno.sehda.persistence;

import ar.edu.uno.sehda.entities.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {
    @Query("select c from Medic c JOIN c.user u where  u.username=:medicUsername")
    Medic findMedicByUsername(String medicUsername);
}
