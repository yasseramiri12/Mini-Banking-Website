package com.bankingapp.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bankingapp.dao.CompteBancaireDAO;
import com.bankingapp.model.CompteBancaire;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

public class CompteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/compte.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CompteBancaire compteBancaire;

        try {
            if ("add".equals(action)){
             int idClient = Integer.parseInt(request.getParameter("idClient"));
             String typeCompte = request.getParameter("typeCompte");
             double solde = Double.parseDouble(request.getParameter("solde"));

             Timestamp dateCreation = new Timestamp(System.currentTimeMillis());

             CompteBancaireDAO compteBancaireDAO = new CompteBancaireDAO();
             compteBancaireDAO.ajouterCompteBancaire(idClient,typeCompte,solde,dateCreation);

             request.setAttribute("message","Compte ajouter avec succès");
             response.sendRedirect("compte?action=list");
            }
            if("listById".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));

                CompteBancaireDAO compteBancaireDAO = new CompteBancaireDAO();
                compteBancaire = compteBancaireDAO.recupererCompteParId(idCompte);
                request.setAttribute("compteBancaire",compteBancaire);

                request.getRequestDispatcher("/WEB-INF/views/compte.jsp").forward(request,response);
            }
            if ("update".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));
                double solde = Double.parseDouble(request.getParameter("solde"));
                String typeCompte = request.getParameter("typeCompte");

                CompteBancaireDAO compteBancaireDAO = new CompteBancaireDAO();
                compteBancaireDAO.modifierCompte(idCompte,solde,typeCompte);

                request.setAttribute("message","Compte a été met a jour");
                response.sendRedirect("compte?action=list");
            }
            if ("list".equals(action)){
                List <CompteBancaire> compteBancaireList;
                CompteBancaireDAO compteBancaireDAO = new CompteBancaireDAO();
                compteBancaireList=compteBancaireDAO.listComptes();

                request.setAttribute("listCompte",compteBancaireList);

                request.getRequestDispatcher("/WEB-INF/views/compte.jsp").forward(request,response);
            }
            if("delete".equals(action)){
                int idCompte = Integer.parseInt(request.getParameter("idCompte"));

                CompteBancaireDAO compteBancaireDAO = new CompteBancaireDAO();
                compteBancaireDAO.supprimerCompte(idCompte);

                request.setAttribute("message","Compte supprimer avec succès");
                response.sendRedirect("compte?action=list");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}