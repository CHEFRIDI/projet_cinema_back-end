package iteam.tn.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.Collection;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Cinema{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude;
    private double latitude;
    private double altitude;
    private int nombreSalles;

    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles;

    @ManyToOne
    private Ville ville;
}
