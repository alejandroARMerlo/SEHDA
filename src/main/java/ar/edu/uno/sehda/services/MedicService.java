package ar.edu.uno.sehda.services;

import ar.edu.uno.sehda.entities.Medic;
import ar.edu.uno.sehda.entities.Patient;
import ar.edu.uno.sehda.entities.Rol;
import ar.edu.uno.sehda.persistence.MedicRepository;
import ar.edu.uno.sehda.persistence.PatientRepository;
import ar.edu.uno.sehda.persistence.RolRepository;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
public class MedicService {

    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public List<Medic> listMedic(){
        return medicRepository.findAll();

    }

    @Transactional
    public void saveMedic(Medic medic){

        Optional<Rol> rol=rolRepository.findById(1L);

        medic.getUser().addRol(rol.get());

        encriptarPassword(medic);

        medicRepository.save(medic);
    }
    @Transactional
    public void assignPatient(Patient patient, String medicUsername){

        Medic medic= medicRepository.findMedicByUsername(medicUsername);

       log.info("NOMBRE DEL ROL DEL MEDICO: " + medicUsername);
        log.info("ID DEL DEL PACIENTE: " + patient.getId() );

       patient =patientRepository.findById(patient.getId()).get();
        log.info("****************************************************************************: " );
        log.info("ID DEL DEL PACIENTE: " + patient.getUser().getUsername());
        medic.addPatient(patient);
        log.info("****************************************************************************: " );

        //medicRepository.save(medic);

    }

    public List<Patient> listAllUnassignPatient(String medicUsername){

        Medic medic= medicRepository.findMedicByUsername(medicUsername);

        Set<Patient> patients= new HashSet<>();

        try {
            //Obtengo los pacientes que tiene asignado el médico
            patients =  medic.getPatients();

            Patient pat= new Patient();

            pat.setId(0L);

            patients.add(pat);
        }

        catch (Exception e){

            System.out.println("ERROR UNO " + e.getMessage());
        }
        List<Long> patientsIdList= new ArrayList<>();

        // Se guarda el id de los pacientes que tiene asignado el médico
        for(Patient p : patients){

            patientsIdList.add(p.getId());
        }

        // se obtienen todos los pacientes que todaavía no están asignados al médico
        return  patientRepository.findByIdNotIn(patientsIdList);

    }

    private void encriptarPassword(Medic medic) {

        String passwordEncriptado = "";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //se realiza la encriptación del paswword del usuario
        passwordEncriptado = encoder.encode(medic.getUser().getPassword());

        medic.getUser().setPassword(passwordEncriptado);
    }

    @Transactional
    public void deleteMedic(Medic medic){
        medicRepository.delete(medic);
    }

    @Transactional(readOnly = true)
    public Medic searchMedic(Medic medic){
        return medicRepository.findById(medic.getId()).orElse(null);
    }
}
