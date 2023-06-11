package ar.edu.uno.sehda.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Medic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String institucion;

    @ManyToMany
    private Set<Patient> patients = new HashSet<>();

    @OneToOne(cascade=CascadeType.ALL)
    private DBUser user;

    public Medic(String institucion, DBUser user) {
        this.institucion = institucion;
        this.user = user;
    }

    public void addPatient(Patient patient){

        patients.add((patient));
    }

    public Medic() {
    }
}
