package com.example.kyshatbmoznodeliverer.Models;

import java.util.List;

public class Cart {
    String idUser;
    List<FoodInCart> cartList;

    public Cart(){}

    public Cart(String idUser, List<FoodInCart> cartList) {
        this.idUser = idUser;
        this.cartList = cartList;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<FoodInCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<FoodInCart> cartList) {
        this.cartList = cartList;
    }
}
