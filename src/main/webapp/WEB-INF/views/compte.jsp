<%--
  Created by IntelliJ IDEA.
  User: YASSER
  Date: 05/02/2026
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Gestion des comptes bancaires</title>
</head>

<body>

<h2>Gestion des Comptes Bancaires</h2>

<!-- ================= MESSAGE ================= -->
<c:if test="${not empty message}">
    <p style="color:green;font-weight:bold">
            ${message}
    </p>
</c:if>


<!-- ================= AJOUTER ================= -->
<h3>Ajouter un compte</h3>

<form action="compte" method="post">
    <input type="hidden" name="action" value="add"/>

    ID Client :
    <input type="number" name="idClient" required/><br><br>

    Type :
    <select name="typeCompte">
        <option value="COURANT">Courant</option>
        <option value="EPARGNE">Épargne</option>
    </select><br><br>

    Solde :
    <input type="number" step="0.01" name="solde" required/><br><br>

    <input type="submit" value="Ajouter"/>
</form>


<hr>


<!-- ================= LISTE ================= -->
<h3>Liste des comptes</h3>

<table border="1" cellpadding="6">

    <tr>
        <th>ID Compte</th>
        <th>Solde</th>
        <th>Type</th>
        <th>Date</th>
        <th>Client</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="c" items="${listCompte}">
        <tr>
            <td>${c.idCompte}</td>
            <td>${c.solde}</td>
            <td>${c.typeCompte}</td>
            <td>${c.dateCreation}</td>
            <td>
                    ${c.client.nom} ${c.client.prenom}
            </td>

            <td>

                <!-- Modifier -->
                <form action="compte" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="idCompte" value="${c.idCompte}"/>

                    <input type="number" step="0.01" name="solde" placeholder="nouveau solde" required/>
                    <input type="text" name="typeCompte" placeholder="type" />

                    <input type="submit" value="Modifier"/>
                </form>


                <!-- Supprimer -->
                <form action="compte" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="idCompte" value="${c.idCompte}"/>

                    <input type="submit" value="Supprimer"/>
                </form>

            </td>
        </tr>
    </c:forEach>

</table>


<hr>


<!-- ================= BOUTON REFRESH ================= -->
<form action="compte" method="post">
    <input type="hidden" name="action" value="list"/>
    <input type="submit" value="Actualiser la liste"/>
</form>


</body>
</html>

