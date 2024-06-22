package com.example.kyshatbmoznodeliverer.Models;

public class FoodInCart {
    String idFood, idRest, quantity, name, price;

    public FoodInCart(){}

    public FoodInCart(String idFood, String idRest, String quantity, String name, String price) {
        this.idFood = idFood;
        this.idRest = idRest;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getIdRest() {
        return idRest;
    }

    public void setIdRest(String idRest) {
        this.idRest = idRest;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
