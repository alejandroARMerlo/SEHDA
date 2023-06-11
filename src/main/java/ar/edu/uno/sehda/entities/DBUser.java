package ar.edu.uno.sehda.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class DBUser implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private String surname;

    private String name;

    private String email;

    private String phone;

    private int dni;

    @ManyToMany
    private Set<Rol> roles = new HashSet<Rol>();

    public DBUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void addRol(Rol rol){

           roles.add((rol));
    }
    public DBUser() {

    }
}
