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
              <h5 class="card-title">Historique des prix</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Medicament</th>
                    <th>Prix</th>
                    <th>Date</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="modifPrix" items="${modifPrixs}">
                    <tr>
                      <td>${modifPrix.idHistorique}</td>
                      <td>${modifPrix.medicament.getNom()}</td>
                      <td>${modifPrix.prix}</td>
                      <td>${modifPrix.dateModif}</td>
                      <td>
                        <div class="text-right" style="display: flex; gap: 5px;">
                          <form action="/modifPrix/delete/${modifPrix.idHistorique}" method="post">
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
            <h5 class="card-title">modification prix</h5>
            <form class="row g-3" action="/modifPrix/create" method="post">
              <div class="col-md-12">
                <label for="medicament" class="form-label">Medicament</label>
                <select class="form-select" id="medicament" name="idMedicament" required>
                  <c:forEach var="medicament" items="${medicaments}">
                    <option value="${medicament.idMedicament}">${medicament.nom}</option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-12">
                <label for="prix" class="form-label">Prix</label>
                <input type="number" class="form-control" id="prix" name="prix" step="0.01" required>
              </div>

              <div class="col-md-12">
                <label for="date" class="form-label">Date</label>
                <input type="date" class="form-control" id="date" name="dateModif" required>
              </div>

              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form>
        </div>
      </div>
    </div><!-- End Right side columns -->

  </div>
</section>

<%@ include file="footer.jsp" %>
