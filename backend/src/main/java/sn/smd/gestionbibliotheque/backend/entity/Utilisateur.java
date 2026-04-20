@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    private String sexe;

    @Column(nullable = false)
    private String password;

    private boolean actif = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastLogin;

    // ROLE
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}