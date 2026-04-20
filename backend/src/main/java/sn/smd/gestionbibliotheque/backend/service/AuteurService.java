package sn.smd.gestionbibliotheque.backend.service;

import sn.smd.gestionbibliotheque.backend.entity.Auteur;

import java.util.List;

public interface AuteurService {

    Auteur create(Auteur auteur);

    Auteur update(Long id, Auteur auteur);

    void delete(Long id);

    Auteur getById(Long id);

    List<Auteur> getAll();

    long count();
}