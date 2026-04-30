package sn.smd.gestionbibliotheque.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.smd.gestionbibliotheque.backend.entity.Auteur;


public interface AuteurRepository extends JpaRepository<Auteur, Long> {
}
