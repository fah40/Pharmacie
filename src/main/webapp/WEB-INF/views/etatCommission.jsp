<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<section class="section dashboard">
  <div class="row">

    <!-- Left side columns -->
    <div class="col-lg-4">
      <div class="row">

        <!-- Top Selling -->
        <div class="col-12">
          <div class="card">
            <div class="card-body">
              <!-- Default Table -->
              <h5 class="card-title">etat commission</h5>
              <form class="row g-12" action="/etat/search" method="post">
                <div class="col-4">
                  <input type="date" class="form-control" name="datemin">
                </div>
                <div class="col-4">
                  <input type="date" class="form-control" name="datemax">
                </div>
                <div class="col-4">
                  <button type="submit" class="btn btn-success">rechercher</button>
                </div>
              </form><!-- End Multi Columns Form -->

              <table class="table">
                <thead>
                  <tr>
                    <th>genre</th>
                    <th>commission</th>
                    <th>pourcentage</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="etat" items="${etats}">
                    <tr>
                      <td>${etat.genre.getNom()}</td>
                      <td>${etat.commission}</td>
                      <td>${etat.pctg} %</td>
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
    <div class="col-lg-8">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">Pie Chart</h5>

          <!-- Pie Chart -->
          <canvas id="pieChart" style="max-height: 400px;"></canvas>
          <script>
            document.addEventListener("DOMContentLoaded", () => {
                // Récupérer les données depuis le JSP
                var genres = [];
                var commissions = [];
                
                // Parcourir les objets "etats" dans le JSP pour remplir genres et commissions
                <c:forEach var="etat" items="${etats}">
                    genres.push("${etat.genre.nom}");
                    commissions.push(${etat.commission});
                </c:forEach>

                // Initialiser le graphique
                new Chart(document.querySelector('#pieChart'), {
                    type: 'pie',
                    data: {
                        labels: genres,  // Les genres extraits de votre objet
                        datasets: [{
                            label: 'Commissions par Genre',
                            data: commissions,  // Les commissions extraites de votre objet
                            backgroundColor: [
                                'rgb(54, 162, 235)',
                                'rgb(255, 99, 132)'
                            ],
                            hoverOffset: 4
                        }]
                    }
                });
            });
          </script>

          <!-- End Pie CHart -->

        </div>
      </div>
    </div><!-- End Right side columns -->

  </div>
</section>

<%@ include file="footer.jsp" %>
