<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-12">

      <div class="row">
        <form class="row g-12" action="/service/search" method="post">
          <div class="col-md-3">
            <select class="form-select" id="idUnite" name="rmaladie">
                <option value="0">voir tout</option>
              <c:forEach var="maladie" items="${maladies}">
                  <option value="${maladie.idMaladie}">${maladie.nom}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-md-3">
            <input placeholder="age min" type="number" class="form-control" name="ageMin">
          </div>
          <div class="col-md-3">
            <input placeholder="age max" type="number" class="form-control" name="ageMax">
          </div>
          <div class="col-md-3">
            <button type="submit" class="btn btn-success">rechercher</button>
          </div>
        </form><!-- End Multi Columns Form -->
      </div><br>
      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">Liste des medicaments</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Maladie</th>
                    <th>categorie</th>
                    <th>Description</th>
                    <th>age min</th>
                    <th>age max</th>
                    <th>prix</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="medicament" items="${medicaments}">
                    <tr>
                      <td>${medicament.idMedicament}</td>
                      <td>${medicament.nom}</td>
                      <td>${medicament.getMaladie().getNom()}</td>
                      <td>${medicament.getCategorie().getNom()}</td>
                      <td>${medicament.description}</td>
                      <td>${medicament.ageMin}</td>
                      <td>${medicament.ageMax}</td>
                      <td>${medicament.prix}</td>
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

  </div>
</section>

<%@ include file="footer.jsp" %>
