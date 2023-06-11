package ar.edu.uno.sehda.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;

    private String rolName;


}
