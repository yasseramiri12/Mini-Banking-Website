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
    <title>Title</title>
</head>
<table width="100%" border="0">
    <tr>
        <td width="50%" valign="top">
            <div>
                <h3>Rechercher un client</h3>
                <form action="client" method="post">
                    <input type="hidden" name="action" value="find">
                    <p>donner id client</p>
                    <input type="text" name="idClient">
                    <br><br>
                    <input type="submit" value="Rechercher">
                </form>
                <c:out value="${client.nom} // ${client.prenom} // ${client.email}"/>
            </div>
        </td>

        <td width="50%" valign="top">
            <div>
                <h3>Nouveau client</h3>
                <form action="client" method="post">
                    <input type="hidden" name="action" value="add">
                    <p>donner nom client</p>
                    <input type="text" name="nomClient">

                    <p>donner prénom client</p>
                    <input type="text" name="prenomClient">

                    <p>donner email client</p>
                    <input type="email" name="EmailClient">
                    <br><br>
                    <input type="submit" value="Enregistrer">
                </form>
                <c:out value="${message}"/>
            </div>
        </td>
    </tr>
</table>
<c:out value="${client.nom} // ${client.prenom} // ${client.email}"/>
</html>
