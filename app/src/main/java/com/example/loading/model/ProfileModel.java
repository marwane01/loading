package com.example.loading.model;


import org.json.JSONException;
import org.json.JSONObject;

public class ProfileModel {
    long id;
    String type;
    String image;
    String name;
    String status;
    String species;
    String gender;



    public ProfileModel(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.type = jsonObject.getString("type");
            this.status = jsonObject.getString("status");
            this.species = jsonObject.getString("species");
            this.gender = jsonObject.getString("gender");
            this.name = jsonObject.getString("name");
            this.image = jsonObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
