<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-9">
      <div class="row">
        <form class="row g-12" action="/vente/search" method="post">
          <div class="col-md-2">
            <select class="form-select" id="idUnite" name="type">
                <option value="0">type personne</option>
                <option value="1">adulte</option>
                <option value="2">jeune</option>
                <option value="3">bebe</option>
            </select>
          </div>
          <div class="col-md-2">
            <select class="form-select" id="idUnite" name="idCategorie">
                <option value="0">categorie</option>
              <c:forEach var="categorie" items="${categories}">
                  <option value="${categorie.idCategorie}">${categorie.nom}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-md-2">
              <select class="form-select" id="idVendeur" name="vendeur">
                      <option value="0">vendeur</option>
                  <c:forEach var="vendeur" items="${vendeurs}">
                      <option value="${vendeur.idUser}">${vendeur.username}</option>
                  </c:forEach>
              </select>
          </div>
          <div class="col-md-2">
            <input type="date" class="form-control" name="datemin">
          </div>
          <div class="col-md-2">
            <input type="date" class="form-control" name="datemax">
          </div>
          <div class="col-md-2">
            <button type="submit" class="btn btn-success">rechercher</button>
          </div>
        </form><!-- End Multi Columns Form -->
      </div><br>

      <div class="row">

        <!-- Sales List -->
        <div class="col-12">
          <div class="card" style="height:90vh;overflow-y: auto;">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">Liste des ventes</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID-vente</th>
                    <th>NOM-client</th>
                    <th>Medicament</th>
                    <th>Quantite</th>
                    <th>Prix unitaire</th>
                    <th>Prix total</th>
                    <th>Date</th>
                    <th>vendeur</th>
                    <th>commissions</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="vente" items="${ventes}">
                    <tr>
                      <td>${vente.id}</td>
                      <td>${vente.getClient().getNom()}</td>
                      <td>${vente.getMedicament().getNom()}</td>
                      <td>${vente.quantiteVendue}</td>
                      <td>${vente.prixUnitaire}</td>
                      <td>${vente.total}</td>
                      <td>${vente.dateVente}</td>
                      <td>${vente.getVendeur().getUsername()}</td>
                      <td>${vente.commission}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/vente/delete/${vente.id}" method="post">
                            <button class="btn btn-secondary" type="submit">
                              <i class="fa fa-trash"></i>
                            </button>
                          </form>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <!-- End Default Table Example -->
            </div>
          </div>
        </div><!-- End Sales List -->

      </div>
    </div><!-- End Left side columns -->

    <!-- Right side columns -->
    <div class="col-lg-3">
      <div class="card">
        <div class="card-body"><br>
          <!-- Multi Columns Form -->
          <c:if test="${not empty message}">
            <p class="alert alert-warning" style="color: green;">${message}</p>
          </c:if>
          <c:if test="${update == null}">
            <h5 class="card-title">Nouvelle vente</h5>
            <form class="row g-3" action="/vente/create" method="post">
                <div class="col-md-12">
                    <label for="medicament" class="form-label">Medicament</label>
                    <select class="form-select" id="medicament" name="idMedicament">
                        <c:forEach var="medicament" items="${medicaments}">
                            <option value="${medicament.idMedicament}">${medicament.nom}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-12">
                    <label for="vendeur" class="form-label">vendeur</label>
                    <select class="form-select" id="idVendeur" name="idVendeur">
                        <c:forEach var="vendeur" items="${vendeurs}">
                            <option value="${vendeur.idUser}">${vendeur.username}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-12">
                    <label for="client" class="form-label">selectionner un(e) client</label>
                    <select class="form-select" id="idClient" name="idClient">
                        <c:forEach var="client" items="${clients}">
                            <option value="${client.idClient}">${client.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-12">
                    <label for="quantite" class="form-label">Quantite</label>
                    <input type="number" class="form-control" name="quantiteVendue">
                </div>

                <div class="col-md-12">
                    <label for="date" class="form-label">date vente</label>
                    <input type="date" class="form-control" name="date">
                </div>

                <div class="col-md-12">
                    <label for="nom" class="form-label">nom client</label>
                    <input type="" class="form-control" name="nom">
                </div>

                <div class="col-md-12">
                    <label for="adresse" class="form-label">adresse client</label>
                    <input type="" class="form-control" name="adresse">
                </div>

                <div class="col-md-12">
                    <label for="telephone" class="form-label">telephone client</label>
                    <input type="" class="form-control" name="telephone">
                </div>

              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification vente</h5>
            <form class="row g-3" action="/vente/update" method="post">
              <div class="col-md-12">
                <label for="medicament" class="form-label">Medicament</label>
                <select class="form-select" id="medicament" name="idMedicament">
                  <c:forEach var="medicament" items="${medicaments}">
                    <option value="${medicament.idMedicament}" <c:if test="${update.getMedicament().getMedicament() == medicament.getMedicament().getMedicament()}">selected</c:if>>${medicament.nom}</option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-12">
                <label for="quantite" class="form-label">Quantite</label>
                <input type="hidden" name="id" value="${update.id}">
                <input type="number" class="form-control" name="quantiteVendue" value="${update.quantiteVendue}" required>
              </div>

              <div class="col-md-12">
                <label for="prixUnitaire" class="form-label">Prix unitaire</label>
                <input type="number" class="form-control" name="prixUnitaire" value="${update.prixUnitaire}" required>
              </div>

              <div class="text-center">
                <button type="submit" class="btn btn-success">Modifier</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
        </div>
      </div>
    </div><!-- End Right side columns -->

  </div>
</section>

<%@ include file="footer.jsp" %>
