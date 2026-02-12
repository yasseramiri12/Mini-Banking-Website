<%--
  Created by IntelliJ IDEA.
  User: YASSER
  Date: 12/02/2026
  Time: 00:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des opérations</title>
    <style>
        body{
            font-family: Arial;
            margin:40px;
            background:#f5f5f5;
        }

        h2{
            margin-top:40px;
        }

        form{
            background:white;
            padding:15px;
            margin-bottom:20px;
            border-radius:6px;
            box-shadow:0 0 5px rgba(0,0,0,0.1);
        }

        input, button{
            padding:6px;
            margin:5px;
        }

        table{
            width:100%;
            border-collapse: collapse;
            background:white;
            margin-top:20px;
        }

        th, td{
            border:1px solid #ccc;
            padding:8px;
            text-align:center;
        }

        th{
            background:#eee;
        }

        .msg{
            color:green;
            font-weight:bold;
            margin-bottom:20px;
        }
    </style>
</head>
<body>

<h1>💳 Gestion des Opérations Bancaires</h1>

<!-- Message succès -->
<c:if test="${not empty message}">
    <div class="msg">${message}</div>
</c:if>


<!-- ================= DEPOT ================= -->
<h2>Déposer</h2>
<form action="operation" method="post">
    <input type="hidden" name="action" value="depot"/>
    ID Compte :
    <input type="number" name="idCompte" required>

    Montant :
    <input type="number" step="0.01" name="montant" required>

    <button type="submit">Déposer</button>
</form>


<!-- ================= RETRAIT ================= -->
<h2>Retirer</h2>
<form action="operation" method="post">
    <input type="hidden" name="action" value="retirer"/>
    ID Compte :
    <input type="number" name="idCompte" required>

    Montant :
    <input type="number" step="0.01" name="montant" required>

    <button type="submit">Retirer</button>
</form>


<!-- ================= TRANSFERT ================= -->
<h2>Transférer</h2>
<form action="operation" method="post">
    <input type="hidden" name="action" value="transferer"/>

    Compte Émetteur :
    <input type="number" name="idCompteEmetteur" required>

    Compte Récepteur :
    <input type="number" name="idCompteRecepteur" required>

    Montant :
    <input type="number" step="0.01" name="montant" required>

    <button type="submit">Transférer</button>
</form>


<!-- ================= LISTE PAR COMPTE ================= -->
<h2>Voir opérations par compte</h2>
<form action="operation" method="post">
    <input type="hidden" name="action" value="listById"/>
    ID Compte :
    <input type="number" name="idCompte" required>

    <button type="submit">Rechercher</button>
</form>


<!-- ================= TOUTES LES OPERATIONS ================= -->
<h2>Toutes les opérations</h2>
<form action="operation" method="post">
    <input type="hidden" name="action" value="list"/>
    <button type="submit">Afficher toutes</button>
</form>


<!-- ================= TABLE LISTE ================= -->
<c:if test="${not empty listOperation || not empty listOperationById}">
    <h2>Liste des opérations</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Type</th>
            <th>Montant</th>
            <th>Date</th>
            <th>ID Compte</th>
            <th>Détails</th>
        </tr>

        <c:forEach var="op" items="${listOperation != null ? listOperation : listOperationById}">
            <tr>
                <td>${op.idOperation}</td>
                <td>${op.typeOperation}</td>
                <td>${op.montant}</td>
                <td>${op.dateOperation}</td>
                <td>${op.compte.idCompte}</td>
                <td>
                    <form action="operation" method="post">
                        <input type="hidden" name="action" value="getOperation"/>
                        <input type="hidden" name="idOperation" value="${op.idOperation}"/>
                        <button type="submit">Voir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>


<!-- ================= DETAILS OPERATION ================= -->
<c:if test="${not empty operation}">
    <h2>Détail de l'opération</h2>

    <table>
        <tr><th>ID</th><td>${operation.idOperation}</td></tr>
        <tr><th>Type</th><td>${operation.typeOperation}</td></tr>
        <tr><th>Montant</th><td>${operation.montant}</td></tr>
        <tr><th>Date</th><td>${operation.dateOperation}</td></tr>
        <tr><th>ID Compte</th><td>${operation.compte.idCompte}</td></tr>
    </table>
</c:if>

</body>
</html>

