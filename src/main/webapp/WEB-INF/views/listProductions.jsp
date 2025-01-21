<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-9">
      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">stock des produits</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>ID Medicament</th>
                    <th>ID Laboratoire</th>
                    <th>Quantite Produite</th>
                    <th>prix unitaire</th>
                    <th>Date d'achat</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="production" items="${productions}">
                    <tr>
                      <td>${production.idProduction}</td>
                      <td>${production.getMaladie()}</td>
                      <td>${production.getLaboratoire().getId()}-${production.getLaboratoire().getNom()}</td>
                      <td>${production.quantiteProduite}</td>
                      <td>${production.prix}</td>
                      <td>${production.dateProduction}</td>
                      <td>
                        <div class="text-right" style="display: flex; gap: 5px;">
                          <%-- <form action="/production/edit/${production.idProduction}" method="get">
                            <button class="btn btn-success" type="submit">
                              <i class="fa fa-edit"></i>
                            </button>
                          </form> --%>
                          <form action="/production/delete/${production.idProduction}" method="post">
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
        </div><!-- End Top Selling -->

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
            <h5 class="card-title">Insertion achat</h5>
            <form class="row g-3" action="/production/create" method="post">
              <div class="col-md-12">
                <label for="idMedicament" class="form-label">Medicament</label>
                <select class="form-select" id="idUnite" name="idMedicament">
                  <c:forEach var="medicament" items="${medicaments}">
                      <option value="${medicament.idMedicament}">${medicament.nom}</option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-12">
                <label for="idLaboratoire" class="form-label">Laboratoire</label>
                <select class="form-select" id="idUnite" name="idLaboratoire">
                  <c:forEach var="laboratoire" items="${laboratoires}">
                      <option value="${laboratoire.id}">${laboratoire.nom}</option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-12">
                <label for="quantiteProduite" class="form-label">Quantite Produite</label>
                <input type="number" class="form-control" name="quantiteProduite">
              </div>

              <div class="col-md-12">
                <label for="prix" class="form-label">prix Produite</label>
                <input type="number" class="form-control" name="prix">
              </div>

              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification Production</h5>
            <form class="row g-3" action="/production/update" method="post">
              <div class="col-md-12">
                <label for="idMedicament" class="form-label">ID Medicament</label>
                <input type="hidden" name="idProduction" value="${update.idProduction}">
                <input type="number" class="form-control" name="idMedicament" value="${update.idMedicament}">
              </div>

              <div class="col-md-12">
                <label for="quantiteProduite" class="form-label">Quantite Produite</label>
                <input type="number" class="form-control" name="quantiteProduite" value="${update.quantiteProduite}">
              </div>

              <div class="col-md-12">
                <label for="dateProduction" class="form-label">Date de Production</label>
                <input type="date" class="form-control" name="dateProduction" value="${update.dateProduction}">
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
