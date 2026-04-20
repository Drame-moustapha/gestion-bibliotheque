@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manuscrit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== INFOS PRINCIPALES =====
    @Column(nullable = false)
    private String titre;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeManuscrit type;

    // ===== AUTEUR =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auteur_id")
    private Auteur auteur;

    // ===== CONTENU =====
    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String langue;

    private String resume;

    // ===== FICHIER =====
    private String fichierUrl; // stockage cloud ou local

    // ===== BUSINESS =====
    private boolean payant = false;

    @Column(nullable = true)
    private Double prix;

    private int nombreVues = 0;

    private boolean publie = false;

    // ===== AUDIT =====
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}