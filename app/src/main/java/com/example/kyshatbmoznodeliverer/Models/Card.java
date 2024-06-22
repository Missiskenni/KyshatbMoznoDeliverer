package com.example.kyshatbmoznodeliverer.Models;

public class Card {

    private String number, data;

    public Card(){}

    public Card(String number, String data) {
        this.number = number;
        this.data = data;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
