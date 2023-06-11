package ar.edu.uno.sehda.controllers;

import ar.edu.uno.sehda.entities.Medic;
import ar.edu.uno.sehda.entities.Patient;
import ar.edu.uno.sehda.services.MedicService;
import ar.edu.uno.sehda.services.PatientService;
import ar.edu.uno.sehda.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@SessionScope
public class IndexControl {

    @Autowired
    private MedicService medicService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;

    @GetMapping( "/")
    public ModelAndView home(){
        ModelAndView model = new ModelAndView("login");
        //model.addObject("usuario", "Japon");
        return model;
    }

    @GetMapping("/registrom")
    public String moveRegisMedic(Medic medic){
        return "registroMedico";
    }

    @GetMapping("/registrop")
    public String moveRegisPatient(Patient patient){
        return "registroPaciente";
    }


    @PostMapping("/singUpMedic")
    public String singUpMedic(Medic medic){

        medicService.saveMedic(medic);
        return "login";
    }

    @PostMapping("/singUpPatient")
    public String singUpPatient(Patient patient){
        patientService.savePatient(patient);
        return "login";
    }

    @GetMapping("/MedicLoged")
    public String singUpMedic(Model model, Patient patient){

        List<Patient> patients;

        Authentication auth;

        auth = SecurityContextHolder.getContext().getAuthentication();

        patients=  medicService.listAllUnassignPatient(auth.getName());

        model.addAttribute("patient", patient);
        model.addAttribute("patientsList", patients);

        return "dashboardMedic";
    }

    @PostMapping("/assignPatient")
    public String assignPatient(@ModelAttribute("patient") Patient patient ){

        log.info("PACIENTE SELECCIONADO:  " + patient.getId());

        Authentication auth;

        auth = SecurityContextHolder.getContext().getAuthentication();

        medicService.assignPatient(patient,auth.getName());

        return "redirect:/MedicLoged";
    }

    @GetMapping("/PatientLoged")
    public String singUpPatient(){
        return "dashboardPatient";
    }


    /*
    @PostMapping("/singUpMedic")
    public String singUpMedic(@RequestParam("name") String name, @RequestParam("surname") String surname,@RequestParam("dni") Integer dni, @RequestParam("phone") Integer phoneNumber, @RequestParam("mail") String mail, @RequestParam("username") String username, @RequestParam("password") String password){
        Medic medic = new Medic(name, surname, dni, phoneNumber, mail, new User(username, password, "ROLE_MEDIC"));
        medicService.saveMedic(medic);
        return "index";
    }

    @PostMapping("/singUpPatient")
    public String singUpPatient(@RequestParam("name") String name, @RequestParam("surname") String surname,@RequestParam("dni") Integer dni, @RequestParam("phone") Integer phoneNumber, @RequestParam("mail") String mail, @RequestParam("username") String username, @RequestParam("password") String password){
        Patient patient = new Patient(name, surname, dni, phoneNumber, mail, new User(username, password, "ROLE_PATIENT"));
        patientService.savePatient(patient);
        return "index";
    }
     */



}
