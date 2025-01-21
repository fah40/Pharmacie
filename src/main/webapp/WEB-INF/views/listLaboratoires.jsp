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
              <h5 class="card-title">Liste des laboratoires</h5>
              <table class="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nom</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="laboratoire" items="${laboratoires}">
                    <tr>
                      <td>${laboratoire.id}</td>
                      <td>${laboratoire.nom}</td>
                      <td>
                        <div class="text-right" style="display: flex;gap: 5px;">
                          <form action="/laboratoire/edit/${laboratoire.id}" method="get">
                            <button class="btn btn-success" type="submit">
                              <i class="fa fa-edit"></i>
                            </button>
                          </form>
                          <form action="/laboratoire/delete/${laboratoire.id}" method="post">
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
            <h5 class="card-title">Insertion laboratoire</h5>
            <form class="row g-3" action="/laboratoire/create" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="text" class="form-control" name="nom">
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-success">Ajouter</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
            </form><!-- End Multi Columns Form -->
          </c:if>
          <c:if test="${update != null}">
            <h5 class="card-title">Modification laboratoire</h5>
            <form class="row g-3" action="/laboratoire/update" method="post">
              <div class="col-md-12">
                <label for="nom" class="form-label">Nom</label>
                <input type="hidden" name="id" value="${update.id}">
                <input type="text" class="form-control" name="nom" value="${update.nom}">
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
