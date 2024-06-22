package com.example.kyshatbmoznodeliverer.Models;

public class Restaurant {

    String id, description, name, photoUriRest;

    public Restaurant(){};

    public Restaurant(String id, String description, String name, String photoUriRest) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.photoUriRest = photoUriRest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUriRest() {
        return photoUriRest;
    }

    public void setPhotoUriRest(String photoUriRest) {
        this.photoUriRest = photoUriRest;
    }
}
