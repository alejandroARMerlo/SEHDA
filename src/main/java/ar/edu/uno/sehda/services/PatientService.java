package ar.edu.uno.sehda.services;

import ar.edu.uno.sehda.entities.Medic;
import ar.edu.uno.sehda.entities.Patient;
import ar.edu.uno.sehda.entities.Rol;
import ar.edu.uno.sehda.persistence.PatientRepository;
import ar.edu.uno.sehda.persistence.RolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Patient> listPatient(){
        return patientRepository.findAll();
    }

    @Transactional
    public void savePatient(Patient patient){

        //se recibe como parametro el numero de rol del Paciente
        Optional<Rol> rol= rolRepository.findById(2L);

        patient.getUser().addRol(rol.get());

        encriptarPassword(patient);

        patientRepository.save(patient);
    }

    private void encriptarPassword(Patient patient) {

        String passwordEncriptado = "";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //se realiza la encriptación del paswword del usuario
        passwordEncriptado = encoder.encode(patient.getUser().getPassword());

        patient.getUser().setPassword(passwordEncriptado);
    }

    @Transactional
    public void deletePatient(Patient patient){
        patientRepository.delete(patient);
    }

    @Transactional(readOnly = true)
    public Patient searchPatient(Patient patient){
        return patientRepository.findById(patient.getId()).orElse(null);
    }

}
