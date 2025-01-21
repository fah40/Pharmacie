<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">
    <c:if test="${check == null}">
    <div class="col-lg-7 scrollable-div" style="height:90vh;overflow-y: auto;">
    <div class="row">
        <h5>Liste des Produits</h5>
    </c:if>
    <c:if test="${check != null}">
    <div class="col-lg-3">
    <div class="row">
    </c:if>
        <c:forEach var="produit" items="${produits}">
            <div class="col-xxl-4 col-md-2">
                <div class="card info-card sales-card">
                <div class="card-body">
                    <h5 class="card-title"><span>${produit.id}</span>.</h5>
                    <div class="d-flex align-items-center">
                        <div class="ps-3">
                            <h6  class="text-secondary fw-bold">${produit.nom}</h6>
                            <h4 class="text-success fw-bold">${produit.prix}</h4>
                            <span class="text-muted small pt-2 ps-1">${produit.description}</span>
                            <form action="/achat/acheter/${produit.id}" method="post">
                                <button class="btn btn-warning" type="submit"><i class="bi bi-cart"></i></button>
                            </form>

                        </div>
                    </div>
                </div>

                </div>
            </div>
        </c:forEach>
        </div>
    </div>

    <div class="col-lg-5">
      <div class="row">
        <!-- Top Selling -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">paniers</h5>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Quantité</th>
                            <th>Prix Unitaire</th>
                            <th>Total</th>
                        </tr>
                    </thead>                                                                                                                                                            
                    <tbody>
                        <c:forEach var="item" items="${panier}">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.nom}</td>
                                <td>${item.quantite}</td>
                                <td>${item.prix}</td>
                                <td>${item.total}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>total :</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>${total}</td>
                        </tr>
                    </tbody>
                </table>
                </br>
              <c:if test="${check == null}">
                <form style="display: flex;gap: 10px;" action="/achat/validation" method="post">
                    <select class="form-control" name="mode">
                        <option value="1">credit</option>
                        <option value="2">espece</option>
                    </select>
                    <button class="btn btn-success" type="submit">Valider</button>
                </form>
              </c:if>
              <c:if test="${check != null}">
                <form class="row g-3" action="/achat/save" method="post">
                  <div class="col-md-12">
                    <label for="nom" class="form-label">selectionner</label>
                    <select class="form-control" name="mode">
                      <option value="0">-</option>
                      <c:forEach var="item" items="${clients}">
                        <option value="${item.id}">${item.nom}</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-12">
                    <label for="nom" class="form-label">Nom</label>
                    <input type="text" class="form-control" name="nom">
                  </div>
                  <div class="col-md-8">
                    <label for="prix" class="form-label">paye</label>
                    <input type="number" class="form-control" name="paye">
                  </div>
                  <div class="col-md-8">
                    <label for="prix" class="form-label">reste a payer</label>
                    <input type="number" class="form-control" name="reste">
                  </div>

                  <div class="col-md-12">
                    <label for="prix" class="form-label">date d'&eacute;cheance</label>
                    <input type="date" class="form-control" name="date">
                  </div>

                  <div class="text-center">
                    <button type="submit" class="btn btn-warning">Ajouter</button>
                    <button type="reset" class="btn btn-secondary">Reset</button>
                  </div>
                </form>
              </c:if>
              <!-- End Default Table Example -->
            </div>
          </div>
        </div><!-- End Top Selling -->

      </div>
    </div><!-- End Left side columns -->
  
  </div>
</section>

<%@ include file="footer.jsp" %>