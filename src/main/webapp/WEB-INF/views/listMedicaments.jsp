<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-9">
      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card" style="height:95vh;overflow-y: auto;">
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
                      <td>${medicament.prix}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/medicament/edit/${medicament.idMedicament}" method="get">
                            <button class="btn btn-success" type="submit">
                              <i class="fa fa-edit"></i>
                            </button>
                          </form>
                          <form action="/medicament/delete/${medicament.idMedicament}" method="post">
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
            <h5 class="card-title">insertion medicament</h5>
            <form class="row g-3" action="/medicament/create" method="post">
                <div class="col-md-12">
                    <label for="nom" class="form-label">nom</label>
                    <input type="" class="form-control" name="nom">
                </div>

                <div class="col-md-12">
                    <label for="description" class="form-label">maladies</label>
                    <select class="form-select" id="idUnite" name="idMaladie">
                        <c:forEach var="maladie" items="${maladies}">
                            <option value="${maladie.idMaladie}">${maladie.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-12">
                    <label for="description" class="form-label">categorie</label>
                    <select class="form-select" id="idUnite" name="idCategorie">
                        <c:forEach var="categorie" items="${categories}">
                            <option value="${categorie.idCategorie}">${categorie.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-12">
                    <label for="nom" class="form-label">Description</label>
                    <textarea class="form-control" name="description"></textarea>
                </div>
                <div class="col-md-12">
                    <label for="nom" class="form-label">age min</label>
                    <input type="number" class="form-control" name="ageMin">
                </div>
                <div class="col-md-12">
                    <label for="nom" class="form-label">age max</label>
                    <input type="number" class="form-control" name="ageMax">
                </div>
              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification medicament</h5>
            <form class="row g-3" action="/medicament/update" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="hidden" name="id" value="${update.idMedicament}">
                <input type="text" class="form-control" name="nom" value="${update.nom}">
              </div>

              <div class="col-md-12">
                    <label for="description" class="form-label">maladies</label>
                    <select class="form-select" id="idUnite" name="idMaladie">
                        <c:forEach var="maladie" items="${maladies}">
                            <option value="${maladie.idMaladie}">${maladie.nom}</option>
                        </c:forEach>
                    </select>
                </div>

              <div class="col-md-12">
                  <label for="description" class="form-label">categorie</label>
                  <select class="form-select" id="idUnite" name="idCategorie">
                      <c:forEach var="categorie" items="${categories}">
                          <option value="${categorie.idCategorie}">${categorie.nom}</option>
                      </c:forEach>
                  </select>
              </div>

              <div class="col-md-12">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" required>${update.description}</textarea>
              </div>

              <div class="col-md-12">
                  <label for="nom" class="form-label">age min</label>
                  <input type="number" class="form-control" name="ageMin" value="${update.ageMin}">
              </div>
              <div class="col-md-12">
                  <label for="nom" class="form-label">age max</label>
                  <input type="number" class="form-control" name="ageMax" value="${update.ageMax}">
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
