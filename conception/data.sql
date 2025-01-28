insert into genre(nom) values
('femme'),
('homme'),
('autre');


CREATE TABLE users(
   idUser SERIAL PRIMARY KEY,
   username VARCHAR(50) UNIQUE,
   password VARCHAR(255) NOT NULL,
   telephone VARCHAR(20) NOT NULL,
   idGenre INTEGER NOT NULL,
   FOREIGN KEY(idGenre) REFERENCES genre(idGenre)
);


insert into users(username,password,telephone,idGenre) values
('Benja','pass1','09826475',1),
('Nary','pass1','09826475',1),
('Nivo','pass1','09826475',2),
('Fara','pass1','09826475',2),
('Koto','pass1','09826475',1),
('momo','pass1','09826475',3);