-- ====================================================================== Achat
CREATE OR REPLACE FUNCTION update_medicament_prix()
RETURNS TRIGGER AS $$
BEGIN

   IF EXISTS (SELECT 1 FROM medicament WHERE idMedicament = NEW.idMedicament) THEN
      UPDATE medicament
      SET prix = NEW.prix, quantite = quantite + NEW.quantiteProduite
      WHERE idMedicament = NEW.idMedicament;
   END IF;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_prix_trigger
AFTER INSERT ON production
FOR EACH ROW
EXECUTE FUNCTION update_medicament_prix();


-- ====================================================================== Vente 
CREATE OR REPLACE FUNCTION update_medicament_stock()
RETURNS TRIGGER AS $$
BEGIN

   IF EXISTS (SELECT 1 FROM medicament WHERE idMedicament = NEW.idMedicament) THEN
      UPDATE medicament
      SET quantite = quantite - NEW.quantiteVendue
      WHERE idMedicament = NEW.idMedicament;
   END IF;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_stock_trigger
AFTER INSERT ON VenteMedicament
FOR EACH ROW
EXECUTE FUNCTION update_medicament_stock();
