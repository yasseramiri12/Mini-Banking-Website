package com.bankingapp.dao;

import com.bankingapp.model.CompteBancaire;
import com.bankingapp.model.Operation;
import jakarta.ws.rs.client.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//deposer()
//retirer()
//transferer()
//listerOperationsParCompte()
//listerOperations()
//recupererOperation()

public class OpertationDAO {
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

    private boolean verifierExistanceCompte(int idCompte) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT 1 FROM COMPTES WHERE ID_COMPTE = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,idCompte);
            resultSet=preparedStatement.executeQuery();

            if(!resultSet.next()){
                throw new SQLException("Cet client est introuvable");
            }

        }finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        }
        return true;
    }

    private void enregistrerOperation(String typeOperation, double montant, int idCompte) throws SQLException{
        String sqlOperationEmetteur = "INSERT INTO OPERATION(TYPE_OPERATION, MONTANT, DATE_OPERATION, ID_COMPTE) VALUES (?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlOperationEmetteur)){
            preparedStatement.setString(1, typeOperation);
            preparedStatement.setDouble(2, montant);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, idCompte);

            preparedStatement.executeUpdate();
        }
    }

    public void deposer(int idCompte, double montant) throws SQLException {
        loadDatabase();
        verifierExistanceCompte(idCompte);
        CompteBancaire compteBancaire = null;

        String updateSQL = "UPDATE COMPTES SET SOLDE = SOLDE + ? WHERE ID_COMPTE = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)){
            preparedStatement.setDouble(1,montant);
            preparedStatement.setInt(2,idCompte);

            int rows = preparedStatement.executeUpdate();

            if(rows == 0){
                throw new SQLException("Compte Introuvable");
            }
        }
        String sql = "INSERT INTO OPERATION(TYPE_OPERATION, MONTANT, DATE_OPERATION, ID_COMPTE) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "DEPOT");
            preparedStatement.setDouble(2, montant);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4,idCompte);

            preparedStatement.executeUpdate();

        } finally {
            if (connection != null) connection.close();
        }
    }

    public void retirer (int idCompte, double montant) throws SQLException {
        loadDatabase();
        verifierExistanceCompte(idCompte);

        CompteBancaire compteBancaire = null;

        String updateSQL = "UPDATE COMPTES SET SOLDE = SOLDE - ? WHERE ID_COMPTE = ? AND COMPTE > ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)){
            preparedStatement.setDouble(1,montant);
            preparedStatement.setInt(2,idCompte);

            int rows = preparedStatement.executeUpdate();

            if(rows == 0){
                throw new SQLException("Compte Introuvable");
            }
        }
        String sql = "INSERT INTO OPERATION(TYPE_OPERATION, MONTANT, DATE_OPERATION, ID_COMPTE) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "RETRAIT");
            preparedStatement.setDouble(2, montant);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4,idCompte);

            preparedStatement.executeUpdate();

        } finally {
            if (connection != null) connection.close();
        }
    }

    public void transferer(int idCompteEmetteur, int idCompteRecepteur, double montant) throws SQLException{
        try {
            loadDatabase();
            connection.setAutoCommit(false);

            if (montant <= 0)  throw new SQLException("Montant est négatif ou égale à zero");


            String sqlEmetteur = "UPDATE COMPTES SET SOLDE = SOLDE - ? WHERE ID_COMPTE = ? AND SOLDE >= ? ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlEmetteur)){
                preparedStatement.setDouble(1, montant);
                preparedStatement.setInt(2, idCompteEmetteur);
                preparedStatement.setDouble(3, montant);

                if (preparedStatement.executeUpdate() == 0) throw new SQLException("Fonds insuffisants ou compte émetteur inexistant");
            }

            String sqlRecepteur = "UPDATE COMPTES SET SOLDE = SOLDE + ? WHERE ID_COMPTE = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRecepteur)){
                preparedStatement.setDouble(1, montant);
                preparedStatement.setInt(2, idCompteRecepteur);

                if (preparedStatement.executeUpdate() == 0) throw new SQLException("Fonds insuffisants ou compte émetteur inexistant");
            }

            enregistrerOperation("TRANSFERT SORTANT", montant, idCompteEmetteur);
            enregistrerOperation("TRANSFERT ENTRANT", montant, idCompteRecepteur);

            connection.commit();
            System.out.println("Transfert réussi !");

        } catch (SQLException e) {
            if (connection != null) connection.rollback();
        }
        finally {
            if (connection != null) connection.setAutoCommit(true);
            if (connection != null) connection.close();
        }
    }

    public List<Operation> listerOperationsParCompte (int idCompte) throws SQLException{
        loadDatabase();
        verifierExistanceCompte(idCompte);

        List <Operation> operations = new ArrayList<>();

        Operation operation;
        CompteBancaire compteBancaire;
        CompteBancaireDAO compteBancaireDAO;

        String sql = "SELECT * FROM OPERATION WHERE ID_COMPTE = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idCompte);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    operation = new Operation();
                    compteBancaire = new CompteBancaire();
                    compteBancaireDAO = new CompteBancaireDAO();
                    operation.setIdOperation(resultSet.getInt("ID_OPERATION"));
                    operation.setTypeOperation(resultSet.getString("TYPE_OPERATION"));
                    operation.setMontant(resultSet.getDouble("MONTANT"));
                    operation.setDateOperation(resultSet.getTimestamp("DATE_OPERATION"));
                    int idClient = resultSet.getInt("ID_COMPTE");
                    compteBancaire = compteBancaireDAO.recupererCompteParId(idClient);
                    operation.setCompte(compteBancaire);

                    operations.add(operation);
                }
            }
        }finally {
            if (connection != null) connection.close();
        }
        return operations;
    }

    public List<Operation> listerOperation () throws SQLException{
        loadDatabase();

        List <Operation> operations = new ArrayList<>();
        Operation operation;
        CompteBancaire compteBancaire;
        CompteBancaireDAO compteBancaireDAO;

        String sql = "SELECT * FROM OPERATION";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    operation = new Operation();
                    compteBancaire = new CompteBancaire();
                    compteBancaireDAO = new CompteBancaireDAO();
                    operation.setIdOperation(resultSet.getInt("ID_OPERATION"));
                    operation.setTypeOperation(resultSet.getString("TYPE_OPERATION"));
                    operation.setMontant(resultSet.getDouble("MONTANT"));
                    operation.setDateOperation(resultSet.getTimestamp("DATE_OPERATION"));

                    int idCompte = resultSet.getInt("ID_COMPTE");
                    compteBancaire = compteBancaireDAO.recupererCompteParId(idCompte);
                    operation.setCompte(compteBancaire);

                    operations.add(operation);
                }
            }
        }
        return operations;
    }

    public Operation recupererOperation (int idOparation) throws SQLException{
        loadDatabase();

        Operation operation = null;
        CompteBancaire compteBancaire;
        CompteBancaireDAO compteBancaireDAO;

        String sql = "SELECT * FROM OPERATION WHERE ID_OPERATION = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,idOparation);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    operation = new Operation();
                    compteBancaire = new CompteBancaire();
                    compteBancaireDAO = new CompteBancaireDAO();
                    operation.setIdOperation(resultSet.getInt("ID_OPERATION"));
                    operation.setTypeOperation(resultSet.getString("TYPE_OPERATION"));
                    operation.setMontant(resultSet.getDouble("MONTANT"));
                    operation.setDateOperation(resultSet.getTimestamp("DATE_OPERATION"));

                    int idCompte = resultSet.getInt("ID_COMPTE");
                    compteBancaire = compteBancaireDAO.recupererCompteParId(idCompte);
                    operation.setCompte(compteBancaire);
                }
            }
        }finally {
            if (connection != null) connection.close();
        }
        return operation;
    }
}
