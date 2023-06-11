package ar.edu.uno.sehda.persistence;

import ar.edu.uno.sehda.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByIdNotIn(List<Long> patientsIdList);
}
