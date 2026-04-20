package sn.smd.gestionbibliotheque.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
package sn.smd.gestionbibliotheque.backend.entity.Manuscrit;

import java.util.List;

public interface ManuscritRepository extends JpaRepository<Manuscrit, Long> {
    List<Manuscrit> findAllByAuteurId(Long id);

}
