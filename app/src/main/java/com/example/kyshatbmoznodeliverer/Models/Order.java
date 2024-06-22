package com.example.kyshatbmoznodeliverer.Models;

import java.util.List;

public class Order {

    String id, idRest, idUser, idDel, comment, address, entrance, floor, flat, status, date, price;
    List<FoodInCart> orderList;

    public Order(){}

    public Order(String id, String idRest, String idUser,
                 String comment, String address, String entrance,
                 String floor, String flat, String status, String date,
                 String price, List<FoodInCart> orderList) {
        this.id = id;
        this.idRest = idRest;
        this.idUser = idUser;
        this.comment = comment;
        this.address = address;
        this.entrance = entrance;
        this.floor = floor;
        this.flat = flat;
        this.status = status;
        this.date = date;
        this.price = price;
        this.orderList = orderList;
    }

    public Order(String id, String idRest, String idUser, String idDel,
                 String comment, String address, String entrance,
                 String floor, String flat, String status, String date,
                 String price, List<FoodInCart> orderList) {
        this.id = id;
        this.idRest = idRest;
        this.idUser = idUser;
        this.idDel = idDel;
        this.comment = comment;
        this.address = address;
        this.entrance = entrance;
        this.floor = floor;
        this.flat = flat;
        this.status = status;
        this.date = date;
        this.price = price;
        this.orderList = orderList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRest() {
        return idRest;
    }

    public void setIdRest(String idRest) {
        this.idRest = idRest;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdDel() {
        return idDel;
    }

    public void setIdDel(String idDel) {
        this.idDel = idDel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public List<FoodInCart> getOrderList() {
        return orderList;
    }


    public void setOrderList(List<FoodInCart> orderList) {
        this.orderList = orderList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
