package com.bankingapp.dao;

import com.bankingapp.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
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

    public Client recupererClient(int idClient) throws SQLException{
        Client client = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        loadDatabase();
        try {
            String sql = "SELECT * from CLIENT where ID_CLIENT=?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,idClient);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                client = new Client();
                client.setID(resultSet.getInt("ID_CLIENT"));
                client.setNom(resultSet.getString("NOM"));
                client.setPrenom(resultSet.getString("PRENOM"));
                client.setEmail(resultSet.getString("EMAIL"));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return client;
    }

    public void ajouterClient(Client client) throws SQLException{
        loadDatabase();

        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO CLIENT(NOM, PRENOM, EMAIL) VALUES ( ?, ?, ?)";
            preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setString(3, client.getEmail());

            preparedStatement.executeUpdate();
        } finally {
            if (connection != null) connection.close();
            if (preparedStatement != null ) preparedStatement.close();
        }
    }
}
