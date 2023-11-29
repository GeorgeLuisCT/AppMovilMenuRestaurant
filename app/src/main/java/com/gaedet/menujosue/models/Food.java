package com.gaedet.menujosue.models;

public class Food {

    private int id;
    private String Name;
    private int Icon;
    private float price;
    private  String Description;
    private  int isFavorite;

    public Food(int id, int icon, String name, float price, String description) {
        this.id = id;
        this.Icon = icon;
        Name = name;
        this.price = price;
        Description = description;
    }

    public Food(int id, int icon, String name, float price, String description, int isFavorite) {
        this.id = id;
        this.Icon = icon;
        Name = name;
        this.price = price;
        Description = description;
        this.isFavorite = isFavorite;
    }

    public Food(int icon, String name, float price, String description) {
        this.Icon = icon;
        Name = name;
        this.price = price;
        Description = description;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        this.Icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
