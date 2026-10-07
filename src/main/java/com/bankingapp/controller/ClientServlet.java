package com.bankingapp.controller;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankingapp.dao.ClientDAO;
import com.bankingapp.model.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//@WebServlet(name = "ClientServlet", value = "/ClientServlet")
public class ClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Client client = new Client();

        try {
            if ("find".equals(action)) {
                int idClient = Integer.parseInt(request.getParameter("idClient"));

                ClientDAO clientDAO = new ClientDAO();
                client = clientDAO.recupererClient(idClient);
                request.setAttribute("client", client);

                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request, response);
            }
            else if ("add".equals(action)) {
                String nom = request.getParameter("nomClient");
                String prenom = request.getParameter("prenomClient");
                String email = request.getParameter("EmailClient");

                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);

                ClientDAO clientDAO = new ClientDAO();
                clientDAO.ajouterClient(client);

                request.setAttribute("message", "client added succesfully 123559");
                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request, response);

            }
            else if ("update".equals(action)) {
                String idClient = request.getParameter("idClient");
                int id = Integer.parseInt(idClient);
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");

                if (nom == null || prenom == null || email == null || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()){
                    request.setAttribute("message","All fields are required");
                }else{
                    ClientDAO clientDAO = new ClientDAO();
                    clientDAO.modifierClient(id,nom,prenom,email);
                    request.setAttribute("message","Client modified succesfully");
                }

                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request, response);
            }
            else if ("delete".equals(action)) {
                int idClient = Integer.parseInt(request.getParameter("idClient"));
                BigDecimal id = BigDecimal.valueOf(idClient);

                ClientDAO clientDAO = new ClientDAO();
                clientDAO.supprimerClient(id);

                request.setAttribute("message","Client supprimer avec succès");
                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request,response);
            }
            else if ("list".equals(action)) {
                ClientDAO clientDAO = new ClientDAO();
                List <Client> clientList;
                clientList = clientDAO.listerClient();
                request.setAttribute("clients",clientList);
                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}