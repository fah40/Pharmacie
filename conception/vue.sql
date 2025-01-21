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
    v.dateVente
FROM VenteMedicament v 
JOIN Client c ON v.idClient=c.idClient 
JOIN Medicament m ON v.idMedicament=m.idMedicament;
    

    