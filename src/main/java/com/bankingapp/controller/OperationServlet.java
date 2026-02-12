package com.bankingapp.controller;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankingapp.dao.CompteBancaireDAO;
import com.bankingapp.dao.OpertationDAO;
import com.bankingapp.model.Operation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//!!!!!!!!!!!!!!!!!!! -> setters
//recupererOperation()
//listerOperation()

public class OperationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/operation.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Operation operation;
        try{
            if ("depot".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));
                double montant = Double.parseDouble(request.getParameter("montant"));

                OpertationDAO operationDAO = new OpertationDAO();
                operationDAO.deposer(idCompte,montant);

                request.setAttribute("message", "Depot fait avec succès");
                response.sendRedirect("operation?action=list");

            }
            if ("retirer".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));
                double montant = Double.parseDouble(request.getParameter("montant"));

                OpertationDAO operationDAO = new OpertationDAO();
                operationDAO.retirer(idCompte,montant);

                request.setAttribute("message", "Vous avez retirez avec succès");
                response.sendRedirect("operation?action=list");
            }
            if ("transferer".equals(action)){
                int idCompteEmetteur = Integer.parseInt(request.getParameter("idCompteEmetteur"));
                int idCompteRecepteur = Integer.parseInt(request.getParameter("idCompteRecepteur"));
                double montant = Double.parseDouble(request.getParameter("montant"));

                OpertationDAO operationDAO = new OpertationDAO();
                operationDAO.transferer(idCompteEmetteur,idCompteRecepteur,montant);

                request.setAttribute("message", "Transfert d'argent avec succès");
                response.sendRedirect("operation?action=list");
            }
            if ("listById".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));
                List <Operation> operations = new ArrayList<>();

                OpertationDAO operationDAO = new OpertationDAO();
                operations = operationDAO.listerOperationsParCompte(idCompte);

                request.setAttribute("listOperationById", operations);
                request.getRequestDispatcher("/WEB-INF/views/operation.jsp").forward(request,response);
            }
            if ("list".equals(action)){
                List <Operation> operations = new ArrayList<>();

                OpertationDAO operationDAO = new OpertationDAO();
                operations = operationDAO.listerOperation();

                request.setAttribute("listOperation", operations);
                request.getRequestDispatcher("/WEB-INF/views/operation.jsp").forward(request,response);
            }
            if ("getOperation".equals(action)){
                int idOperation = Integer.parseInt(request.getParameter("idOperation"));

                OpertationDAO operationDAO = new OpertationDAO();
                operation = operationDAO.recupererOperation(idOperation);

                request.setAttribute("operation", operation);
                request.getRequestDispatcher("/WEB-INF/views/operation.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}