package com.example.kyshatbmoznodeliverer.Models;

public class Food {
    String id, idRest, name, price, composition, weight, category, photoUriFood;

    public Food(){}

    public Food(String id, String idRest, String name, String price, String composition, String weight, String category, String photoUriFood) {
        this.id = id;
        this.idRest = idRest;
        this.name = name;
        this.price = price;
        this.composition = composition;
        this.weight = weight;
        this.category = category;
        this.photoUriFood = photoUriFood;
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

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoUriFood() {
        return photoUriFood;
    }

    public void setPhotoUriFood(String photoUriFood) {
        this.photoUriFood = photoUriFood;
    }
}
