package com.example.kyshatbmoznodeliverer.Models;

public class User {
    private String id, name, phoneNumber, email, role, idRest;
    private Card card;
    private boolean onOrder;

    public User(){}

    public User(String id, String name, String phoneNumber, String email, Card card, String role) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.card = card;
        this.role = role;
    }

    public User(String id, String name, String phoneNumber, String email, String role, String idRest, Card card) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.idRest = idRest;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdRest() {
        return idRest;
    }

    public void setIdRest(String idRest) {
        this.idRest = idRest;
    }

    public boolean isOnOrder() {
        return onOrder;
    }

    public void setOnOrder(boolean onOrder) {
        this.onOrder = onOrder;
    }
}
