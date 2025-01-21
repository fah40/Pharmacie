create database pharmacie;
\c pharmacie;

CREATE TABLE Maladie (
    idMaladie SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE Medicament (
    idMedicament SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    idMaladie INT NOT NULL,
    description TEXT,
    ageMin INT,
    ageMax INT,
    prix DECIMAL(10, 2),
    FOREIGN KEY (idMaladie) REFERENCES Maladie(idMaladie)
);

CREATE TABLE unite (
    idunite SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE matiere (
    idmatiere SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    quantiteStock DECIMAL(10, 2) NOT NULL DEFAULT 0,
    idunite int NOT NULL
);

CREATE TABLE Formule (
    idFormule SERIAL PRIMARY KEY,
    idMedicament INT NOT NULL,
    idmatiere INT NOT NULL,
    quantiteNecessaire DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idMedicament) REFERENCES Medicament(idMedicament),
    FOREIGN KEY (idmatiere) REFERENCES matiere(idmatiere)
);

CREATE TABLE Achatmatiere (
    idAchat SERIAL PRIMARY KEY,
    idmatiere INT NOT NULL,
    quantiteAchetee DECIMAL(10, 2) NOT NULL,
    dateAchat TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    prixUnitaire DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idmatiere) REFERENCES matiere(idmatiere)
);

CREATE TABLE Production (
    idProduction SERIAL PRIMARY KEY,
    idMedicament INT NOT NULL,
    quantiteProduite INT NOT NULL,
    dateProduction TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idMedicament) REFERENCES Medicament(idMedicament)
);

CREATE TABLE Consommationmatiere (
    idConsommation SERIAL PRIMARY KEY,
    idProduction INT NOT NULL,
    idmatiere INT NOT NULL,
    quantiteConsommee DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idProduction) REFERENCES Production(idProduction),
    FOREIGN KEY (idmatiere) REFERENCES matiere(idmatiere)
);

CREATE TABLE MouvemenMatiere (
    idMouvement SERIAL PRIMARY KEY,
    idMatiere INT,
    idMedicament INT,
    entre DECIMAL(10, 2) NOT NULL,
    sortie DECIMAL(10, 2) NOT NULL,
    dateMouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere),
    FOREIGN KEY (idMedicament) REFERENCES Medicament(idMedicament)
);

update medicament set prix = 600 where idMedicament=1;
update medicament set prix = 500 where idMedicament=2;
update medicament set prix = 9100 where idMedicament=3;
update medicament set prix = 9000 where idMedicament=4;
update medicament set prix = 400 where idMedicament=5;
update medicament set prix = 6100 where idMedicament=6;
update medicament set prix = 4600 where idMedicament=7;
update medicament set prix = 2600 where idMedicament=8;