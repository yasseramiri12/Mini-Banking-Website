<%--
  Created by IntelliJ IDEA.
  User: YASSER
  Date: 26/01/2026
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Gestion Client</title>
</head>
<body>

<table width="100%" border="0">
    <tr>
        <!-- ===== Recherche + Modification ===== -->
        <td width="50%" valign="top">
            <div>
                <h3>Rechercher un client</h3>

                <form action="client" method="post">
                    <input type="hidden" name="action" value="find">
                    <label>Donner ID client :</label>
                    <input type="text" name="idClient" required>
                    <br><br>
                    <input type="submit" value="Rechercher">
                </form>

                <!-- Affichage client trouvé -->
                <c:if test="${not empty client}">
                    <p>
                        <strong>Client trouvé :</strong>
                            ${client.nom} // ${client.prenom} // ${client.email}
                        <form action="client" method="post">
                            <input type="submit" name="action" value="delete">
                            <input type="hidden" name="idClient" value="${client.ID}">

                </form>
                    ${message}

                    </p>

                    <h3>Modifier le client</h3>

                    <form action="client" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="idClient" value="${client.ID}">

                        <label>Nom :</label>
                        <input type="text" name="nom" value="${client.nom}" required>
                        <br><br>

                        <label>Prénom :</label>
                        <input type="text" name="prenom" value="${client.prenom}" required>
                        <br><br>

                        <label>Email :</label>
                        <input type="email" name="email" value="${client.email}" required>
                        <br><br>

                        <input type="submit" value="Modifier">
                    </form>
                </c:if>

                <c:if test="${not empty message}">
                    <p style="color:green;">${message}</p>
                </c:if>

                <!-- Message si client introuvable -->
                <c:if test="${empty client && not empty param.ID}">
                    <p style="color:red;">Client introuvable</p>
                </c:if>
            </div>
        </td>

        <!-- ===== Ajout de nouveau client ===== -->
        <td width="50%" valign="top">
            <div>
                <h3>Nouveau client</h3>

                <form action="client" method="post">
                    <input type="hidden" name="action" value="add">

                    <label>Nom :</label>
                    <input type="text" name="nom" required>
                    <br><br>

                    <label>Prénom :</label>
                    <input type="text" name="prenom" required>
                    <br><br>

                    <label>Email :</label>
                    <input type="email" name="email" required>
                    <br><br>

                    <input type="submit" value="Enregistrer">
                </form>

            </div>
        </td>
    </tr>
</table>

</body>
</html>
