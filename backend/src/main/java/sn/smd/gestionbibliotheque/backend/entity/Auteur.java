sn.smd.gestionbibliotheque.backend.entity;
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