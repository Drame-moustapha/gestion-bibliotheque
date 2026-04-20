
sn.smd.gestionbibliotheque.backend.model;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auteurs {

    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true,nullable = false)
    private String username;
    private String sexe;
    @Column(nullable = false)
    private String password;
    private boolean actif;
    private String biographie;
    private List<Role> roles = new ArrayList<>();
}
