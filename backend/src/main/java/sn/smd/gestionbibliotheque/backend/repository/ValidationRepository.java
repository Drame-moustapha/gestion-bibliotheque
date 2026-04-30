package sn.smd.gestionbibliotheque.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sn.smd.gestionbibliotheque.backend.entity.Validation;


import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}
