package sn.smd.gestionbibliotheque.backend.service;

import sn.smd.gestionbibliotheque.backend.entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    Utilisateur create(Utilisateur user);

    Utilisateur update(Long id, Utilisateur user);

    void delete(Long id);

    Utilisateur getById(Long id);

    Utilisateur getByUsername(String username);

    List<Utilisateur> getAll(Boolean actif);

    Utilisateur toggleActive(Long id);

    Utilisateur activate(Long id);

    long count();
}