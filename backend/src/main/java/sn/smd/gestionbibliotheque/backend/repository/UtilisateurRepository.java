package sn.smd.gestionbibliotheque.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}