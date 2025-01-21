<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-9">

      <div class="row">
        <form class="row g-12" action="/conseiller/search" method="post">
          <div class="col-md-3">
            <input type="month" class="form-control" name="date">
          </div>

          <div class="col-md-3">
            <input type="number" class="form-control" name="annee" placeholder="Saisissez une annee">
          </div>

          <div class="col-md-3">
            <input type="submit" class="form-control" value="rechercher">
          </div>
        </form>
      </div><br>

      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">Liste des conseillers</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Medicament</th>
                    <th>Maladie</th>
                    <th>categorie</th>
                    <th>Description</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="conseiller" items="${conseillers}">
                    <tr>
                      <td>${conseiller.id}</td>
                      <td>${conseiller.medicament.getNom()}</td>
                      <td>${conseiller.medicament.getMaladie().getNom()}</td>
                      <td>${conseiller.medicament.getCategorie().getNom()}</td>
                      <td>${conseiller.medicament.getDescription()}</td>
                      <td>${conseiller.date}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/conseiller/delete/${conseiller.id}" method="get">
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
            <h5 class="card-title">Insertion conseiller</h5>
            <form class="row g-3" action="/conseiller/create" method="post">
              <div class="col-md-12">
                <label for="idMedicament" class="form-label">Medicament</label>
                <select class="form-select" id="idUnite" name="idMedicament">
                  <c:forEach var="medicament" items="${medicaments}">
                      <option value="${medicament.idMedicament}">${medicament.nom}</option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-12">
                <label for="date" class="form-label">Date</label>
                <input type="date" class="form-control" name="date" required>
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification conseiller</h5>
            <form class="row g-3" action="/conseiller/update" method="post">
              <div class="col-md-12">
                <label for="idMedicament" class="form-label">ID Medicament</label>
                <input type="hidden" name="id" value="${update.id}">
                <input type="number" class="form-control" name="idMedicament" value="${update.idMedicament}">
              </div>

              <div class="col-md-12">
                <label for="date" class="form-label">Date</label>
                <input type="date" class="form-control" name="date" value="${update.date}" required>
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
