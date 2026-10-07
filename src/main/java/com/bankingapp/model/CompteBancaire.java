package com.bankingapp.model;

public class CompteBancaire {
    private int idCompte;
    private double solde;
    private TypeCompte typeCompte;
    private String dateCreation;
    private Client client;

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        try {
            this.typeCompte = TypeCompte.valueOf(typeCompte.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Type Compte Invalide: EPARGNE / COURANT");
        }
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
