package com.bankingapp.model;

import java.sql.Timestamp;

public class Operation {
    private int idOperation;
    private String typeOperation;
    private double montant;
    private Timestamp dateOperation;
    private CompteBancaire compte;

    public int getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(int idOperation) {
        this.idOperation = idOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Timestamp getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Timestamp dateOperation) {
        this.dateOperation = dateOperation;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }
}
