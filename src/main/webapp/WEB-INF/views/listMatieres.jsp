<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-9">
      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card" style="height:50vh;overflow-y: auto;">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">Liste des matieres</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Quantite restant</th>
                    <th>Unite</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="matiere" items="${matieres}">
                    <tr>
                      <td>${matiere.idMatiere}</td>
                      <td>${matiere.nom}</td>
                      <td>${matiere.quantiteStock}</td>
                      <td>${matiere.getUnite()}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/matiere/edit/${matiere.idMatiere}" method="get">
                            <button class="btn btn-success" type="submit">
                              <i class="fa fa-edit"></i>
                            </button>
                          </form>
                          <form action="/matiere/delete/${matiere.idMatiere}" method="post">
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

          <div class="card" style="height:50vh;overflow-y: auto;">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">Liste des matieres</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Quantite</th>
                    <th>Unite</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="matiere" items="${matieres}">
                    <tr>
                      <td>${matiere.idMatiere}</td>
                      <td>${matiere.nom}</td>
                      <td>${matiere.quantiteStock}</td>
                      <td>${matiere.getUnite()}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/matiere/edit/${matiere.idMatiere}" method="get">
                            <button class="btn btn-success" type="submit">
                              <i class="fa fa-edit"></i>
                            </button>
                          </form>
                          <form action="/matiere/delete/${matiere.idMatiere}" method="post">
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
            <h5 class="card-title">Insertion matiere</h5>
            <form class="row g-3" action="/matiere/create" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="text" class="form-control" name="nom">
              </div>

              <div class="col-md-12">
                <label for="description" class="form-label">Unite</label>
                <select class="form-select" id="idUnite" name="idUnite">
                    <c:forEach var="unite" items="${unites}">
                        <option value="${unite.idUnite}">${unite.nom}</option>
                    </c:forEach>
                </select>
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification matiere</h5>
            <form class="row g-3" action="/matiere/update" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="hidden" name="id" value="${update.idMatiere}">
                <input type="text" class="form-control" name="nom" value="${update.nom}">
              </div>

              <div class="col-md-12">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" required>${update.description}</textarea>
              </div>

              <div class="col-md-8">
                <label for="prix" class="form-label">Prix</label>
                <input type="number" class="form-control" name="prix" value="${update.prix}">
              </div>

              <div class="text-center">
                <button type="submit" class="btn btn-success">Modifier</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
        </div>
      </div>

      <br><br><br><br><br>
      <div class="card">
        <div class="card-body"><br>
          <!-- Multi Columns Form -->
          <c:if test="${not empty message}">
            <p class="alert alert-warning" style="color: green;">${message}</p>
          </c:if>
          <c:if test="${update == null}">
            <h5 class="card-title">Achat  matiere</h5>
            <form class="row g-3" action="/matiere/achat" method="post">
                <div class="col-md-12">
                    <label for="description" class="form-label">Matiere</label>
                    <select class="form-select" id="idUnite" name="idUnite">
                        <c:forEach var="matiere" items="${matieres}">
                            <option value="${matiere.idMatiere}">${matiere.nom}</option>
                        </c:forEach>
                    </select>
                </div>
              
                <div class="col-md-12">
                    <label for="nom" class="form-label">quantite</label>
                    <input type="number" class="form-control" name="quantite">
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification matiere</h5>
            <form class="row g-3" action="/matiere/update" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="hidden" name="id" value="${update.idMatiere}">
                <input type="text" class="form-control" name="nom" value="${update.nom}">
              </div>

              <div class="col-md-12">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" required>${update.description}</textarea>
              </div>

              <div class="col-md-8">
                <label for="prix" class="form-label">Prix</label>
                <input type="number" class="form-control" name="prix" value="${update.prix}">
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
