package com.example.kyshatbmoznodeliverer.Models;

public class Feedback {

    String id, idUser, idRest, comment, rating, date;

    public Feedback(){}

    public Feedback(String id, String idUser, String idRest, String comment, String rating, String date) {
        this.id = id;
        this.idUser = idUser;
        this.idRest = idRest;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdRest() {
        return idRest;
    }

    public void setIdRest(String idRest) {
        this.idRest = idRest;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
