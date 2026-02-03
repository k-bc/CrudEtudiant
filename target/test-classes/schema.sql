-- Script de création du schéma pour les tests H2

CREATE TABLE IF NOT EXISTS etudiant (
    id_etudiant BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom_etudiant VARCHAR(255) NOT NULL,
    prenom_etudiant VARCHAR(255) NOT NULL,
    opt VARCHAR(50)
);

