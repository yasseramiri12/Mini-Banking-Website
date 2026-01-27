package com.bankingapp.controller;

import java.io.*;
import java.sql.SQLException;

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
            } else if ("add".equals(action)) {
                String nom = request.getParameter("nomClient");
                String prenom = request.getParameter("prenomClient");
                String email = request.getParameter("EmailClient");

                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);

                ClientDAO clientDAO = new ClientDAO();
                clientDAO.ajouterClient(client);

                request.setAttribute("message", "client added succesfully");
                request.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(request, response);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}