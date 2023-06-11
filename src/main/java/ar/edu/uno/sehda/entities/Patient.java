package ar.edu.uno.sehda.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "patients")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<Medic> medics = new HashSet<>();

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private DBUser user;

    public Patient( DBUser user) {

        this.user = user;
    }

    public void addMedic(Medic medic){

        medics.add((medic));
    }

    public Patient() {
    }
}
