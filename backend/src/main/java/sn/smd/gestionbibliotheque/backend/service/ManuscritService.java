package sn.smd.gestionbibliotheque.backend.service;

import sn.smd.gestionbibliotheque.backend.entity.Manuscrit;

import java.util.List;

public interface ManuscritService {

    Manuscrit create(Manuscrit manuscrit);

    Manuscrit update(Long id, Manuscrit manuscrit);

    void delete(Long id);

    Manuscrit getById(Long id);

    List<Manuscrit> getAll();

    List<Manuscrit> getByAuteur(Long auteurId);

    List<Manuscrit> getPayants();

    List<Manuscrit> searchByTitre(String keyword);

    long count();
}