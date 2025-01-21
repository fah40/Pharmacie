create database pharmacie;
\c pharmacie;

CREATE TABLE maladie(
   idMaladie SERIAL,
   nom VARCHAR(50) ,
   description TEXT,
   PRIMARY KEY(idMaladie)
);

CREATE TABLE categorie(
   idCategorie SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(idCategorie)
);

CREATE TABLE medicament(
   idMedicament SERIAL,
   nom VARCHAR(50) ,
   idMaladie INTEGER NOT NULL,
   idCategorie INTEGER NOT NULL,
   description TEXT,
   ageMin INTEGER,
   ageMax INTEGER,
   prix NUMERIC(15,2),
   quantite INTEGER,
   FOREIGN KEY(idCategorie) REFERENCES categorie(idCategorie),
   PRIMARY KEY(idMedicament),
   FOREIGN KEY(idMaladie) REFERENCES maladie(idMaladie)
);

CREATE TABLE unite(
   idunite SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(idunite)
);

CREATE TABLE matiere(
   idmatiere SERIAL,
   nom VARCHAR(50) ,
   quantiteStock NUMERIC(15,2)  ,
   idunite INTEGER NOT NULL,
   PRIMARY KEY(idmatiere),
   FOREIGN KEY(idunite) REFERENCES unite(idunite)
);

CREATE TABLE Achatmatiere(
   idAchat SERIAL,
   idmatiere INTEGER NOT NULL,
   quantiteAchetee NUMERIC(15,2),
   dateAchat TIMESTAMP DEFAULT now(),
   prixUnitaire NUMERIC(15,2),
   PRIMARY KEY(idAchat),
   FOREIGN KEY(idmatiere) REFERENCES matiere(idmatiere)
);

CREATE TABLE laboratoire (
   id SERIAL PRIMARY KEY,
   nom VARCHAR(50)
);

CREATE TABLE production(
   idProduction SERIAL,
   idMedicament INTEGER,
   idLaboratoire INTEGER,
   quantiteProduite INTEGER NOT NULL,
   prix NUMERIC(15,2),
   dateProduction TIMESTAMP DEFAULT now(),
   PRIMARY KEY(idProduction),
   FOREIGN KEY(idMedicament) REFERENCES medicament(idMedicament),
   FOREIGN KEY(idLaboratoire) REFERENCES laboratoire(id)
);

CREATE TABLE MouvemenMatiere(
   idMouvement SERIAL,
   entre NUMERIC(15,2)  ,
   sortie NUMERIC(15,2)  ,
   dateMouvement TIMESTAMP DEFAULT now(),
   idMatiere INTEGER,
   PRIMARY KEY(idMouvement),
   FOREIGN KEY(idMatiere) REFERENCES matiere(idMatiere)
);

CREATE TABLE formule(
   idFormule SERIAL,
   idMedicament INTEGER,
   idmatiere INT NOT NULL,
   quantiteNecessaire NUMERIC(15,2)  ,
   PRIMARY KEY(idFormule),
   FOREIGN KEY(idMedicament) REFERENCES medicament(idMedicament),
   FOREIGN KEY(idmatiere) REFERENCES matiere(idmatiere)
);

CREATE TABLE Consommationmatiere (
    idConsommation SERIAL PRIMARY KEY,
    idProduction INT NOT NULL,
    idmatiere INT NOT NULL,
    quantiteConsommee DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idmatiere) REFERENCES matiere(idmatiere),
    FOREIGN KEY (idProduction) REFERENCES Production(idProduction)
);

CREATE TABLE Client(
   idClient SERIAL,
   nom VARCHAR(50),
   adresse VARCHAR(100),
   telephone VARCHAR(20),
   PRIMARY KEY(idClient)
);

CREATE TABLE VenteMedicament(
   id SERIAL PRIMARY KEY,
   idMedicament INTEGER,
   quantiteVendue INTEGER,
   dateVente DATE DEFAULT CURRENT_DATE,
   prixUnitaire NUMERIC(15,2),
   idClient INTEGER,
   idVendeur INTEGER,
   commission NUMERIC(15,2),
   FOREIGN KEY(idVendeur) REFERENCES User(idUser)
   FOREIGN KEY(idMedicament) REFERENCES medicament(idMedicament),
   FOREIGN KEY(idClient) REFERENCES Client(idClient)
);

CREATE TABLE Conseiller(
   id SERIAL PRIMARY KEY,
   idMedicament INTEGER,
   date DATE,
   FOREIGN KEY(idMedicament) REFERENCES medicament(idMedicament)
);

CREATE TABLE User(
   idUser SERIAL PRIMARY KEY,
   username VARCHAR(50) UNIQUE,
   password VARCHAR(255) NOT NULL,
   telephone VARCHAR(20) NOT NULL
);

ALTER TABLE VenteMedicament
ALTER COLUMN dateVente SET DEFAULT now();
