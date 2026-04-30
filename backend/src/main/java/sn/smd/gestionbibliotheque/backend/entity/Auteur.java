package sn.smd.gestionbibliotheque.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auteur extends Utilisateur {

    private String biographie;

    private String pays;

    private String institution; // université ou école

    private String specialite;

    // relation corrigée
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Manuscrit> manuscrits = new ArrayList<>();
}