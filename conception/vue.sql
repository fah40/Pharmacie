CREATE OR REPLACE VIEW v_venteMedicament AS
SELECT 
    m.*,
    v.id,
    v.quantiteVendue,
    v.dateVente
FROM medicament m
JOIN VenteMedicament v ON v.idMedicament=m.idMedicament;

CREATE OR REPLACE VIEW v_conseillerMedicament AS
SELECT 
    m.*,
    c.id,
    c.date
FROM medicament m
JOIN Conseiller c ON c.idMedicament=m.idMedicament;

CREATE OR REPLACE VIEW v_historiqueVente AS
SELECT 
    c.nom as nomClient,
    c.idClient,
    m.*,
    v.id,
    v.quantiteVendue,
    v.dateVente,
    v.idVendeur,
    v.commission,
    g.idGenre
FROM VenteMedicament v 
JOIN Client c ON v.idClient=c.idClient 
JOIN Medicament m ON v.idMedicament=m.idMedicament
JOIN Users u ON v.idVendeur=u.idUser
JOIN Genre g ON u.idGenre=g.idGenre;
    
CREATE OR REPLACE VIEW v_commission AS
SELECT 
    v.dateVente,
    v.idVendeur,
    g.idGenre,
    g.nom,
    v.commission
FROM VenteMedicament v 
JOIN Users u ON v.idVendeur=u.idUser
JOIN Genre g ON u.idGenre=g.idGenre;

CREATE OR REPLACE VIEW v_commission AS
SELECT 
    g.idGenre,
    sum(v.commission)
FROM VenteMedicament;

WITH total_commission AS (
    SELECT SUM(commission) AS total
    FROM v_historiqueVente
)
SELECT 
    idGenre, 
    SUM(commission) AS commission, 
    ROUND(SUM(commission) * 100.0 / (SELECT total FROM total_commission), 2) AS pourcentage
FROM 
    v_historiqueVente 
GROUP BY 
    idGenre;

WITH total_commission AS (
    SELECT SUM(commission) AS total
    FROM v_historiqueVente 
    WHERE datevente >= '2025-01-01' AND datevente <= '2025-01-31'
)
SELECT 
    idGenre, 
    SUM(commission) AS commission, 
    ROUND(SUM(commission) * 100.0 / (SELECT total FROM total_commission), 2) AS pourcentage
FROM 
    v_historiqueVente 
WHERE 
    datevente >= '2025-01-01' AND datevente <= '2025-01-31'
GROUP BY 
    idGenre;



WITH total_commission AS (
    SELECT SUM(commission) AS total
    FROM v_historiqueVente 
    WHERE 1=1
)
SELECT 
    idGenre, 
    SUM(commission) AS commission, 
    ROUND(SUM(commission) * 100.0 / (SELECT total FROM total_commission), 2) AS pourcentage
FROM 
    v_historiqueVente 
WHERE 
    1=1
GROUP BY 
    idGenre;

