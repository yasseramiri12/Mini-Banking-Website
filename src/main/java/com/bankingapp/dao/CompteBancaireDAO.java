package com.bankingapp.dao;

import com.bankingapp.model.Client;
import com.bankingapp.model.CompteBancaire;
import com.bankingapp.dao.ClientDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteBancaireDAO {
    private Connection connection;

    private void loadDatabase(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "j2ee_user", "j2ee");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verifierExistanceClient(int idClient) throws SQLException{
        loadDatabase();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT 1 FROM CLIENT WHERE ID_CLIENT = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idClient);
            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new SQLException("Cet client est introuvable");
            }

        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return true;
    }

    public void ajouterCompteBancaire(int idClient, String typeCompte, double solde, Timestamp dateCreation) throws SQLException{
        loadDatabase();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String CheckSql = "SELECT 1 FROM CLIENT WHERE ID_CLIENT = ?";
            preparedStatement = connection.prepareStatement(CheckSql);
            preparedStatement.setInt(1,idClient);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()){
                throw new SQLException("Client introuvable !!");
            }

            if (solde < 0 ){
                throw new SQLException("Le solde  doit etre superieur a zero");
            }

            resultSet.close();
            preparedStatement.close();

            String sql = "INSERT INTO COMPTES(SOLDE, TYPE_COMPTE, DATE_CREATION, ID_CLIENT) VALUES (?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
                   preparedStatement.setDouble(1, solde);
                    preparedStatement.setString(2,typeCompte);
                    preparedStatement.setTimestamp(3, dateCreation);
                    preparedStatement.setInt(4, idClient);
                    preparedStatement.executeUpdate();
        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    public CompteBancaire recupererCompteParId(int idCompte) throws SQLException {
        loadDatabase();

        CompteBancaire compteBancaire = null;
        Client client = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String SQL ="SELECT c.ID_COMPTE,c.SOLDE, c.TYPE_COMPTE, c.DATE_CREATION,\n" +
                    "       cl.NOM, cl.PRENOM, cl.EMAIL\n" +
                    "FROM COMPTES c\n" +
                    "JOIN CLIENT cl ON c.ID_CLIENT = cl.ID_CLIENT\n" +
                    "WHERE c.ID_COMPTE = ?";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1,idCompte);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                compteBancaire = new CompteBancaire();
                client = new Client();
                compteBancaire.setIdCompte(resultSet.getInt("ID_COMPTE"));
                compteBancaire.setSolde(resultSet.getDouble("SOLDE"));
                compteBancaire.setTypeCompte(resultSet.getString("TYPE_COMPTE"));
                compteBancaire.setDateCreation(resultSet.getString("DATE_CREATION"));

                client.setNom(resultSet.getString("NOM"));
                client.setPrenom(resultSet.getString("PRENOM"));
                client.setEmail(resultSet.getString("EMAIL"));

                compteBancaire.setClient(client);

            }
        } finally {
            if(preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
        }
        return compteBancaire;
    }

    public void modifierCompte (int idCompte, double solde, String type_compte) throws SQLException {
        loadDatabase();

        PreparedStatement preparedStatement = null;

        try {
            String sql = "UPDATE COMPTES SET SOLDE = ?, TYPE_COMPTE = ? WHERE ID_COMPTE = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, solde);
            preparedStatement.setString(2, type_compte);
            preparedStatement.setInt(3, idCompte);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    public List <CompteBancaire> listComptes() throws SQLException {
        loadDatabase();
        List <CompteBancaire> compteBancaires = new ArrayList<>();

        String sql = "SELECT * FROM COMPTES";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    CompteBancaire compteBancaire = new CompteBancaire();
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = new Client();
                    int idClient = resultSet.getInt("ID_CLIENT");
                    client = clientDAO.recupererClient(idClient);
                    compteBancaire.setIdCompte(resultSet.getInt("ID_COMPTE"));
                    compteBancaire.setSolde(resultSet.getInt("SOLDE"));
                    compteBancaire.setTypeCompte(resultSet.getString("TYPE_COMPTE"));
                    compteBancaire.setDateCreation(String.valueOf(resultSet.getTimestamp("DATE_CREATION")));
                    compteBancaire.setClient(client);

                    compteBancaires.add(compteBancaire);
                }
            }
        }finally {
            if (connection != null) connection.close();
        }
        return compteBancaires;
    }

    public void supprimerCompte(int idCompte) throws SQLException{
        loadDatabase();
        verifierExistanceClient(idCompte);
        String sql = "DELETE FROM COMPTES WHERE ID_COMPTE = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idCompte);
            preparedStatement.executeUpdate();
            }
        if(connection != null) connection.close();
    }
}
