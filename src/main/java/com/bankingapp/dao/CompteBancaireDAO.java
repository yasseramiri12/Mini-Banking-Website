package com.bankingapp.dao;

import com.bankingapp.model.Client;

import java.sql.*;

class CompteBancaireDAO {
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
}
