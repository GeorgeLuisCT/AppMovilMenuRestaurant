package com.gaedet.menujosue.models;

public class ShoppingCart {


    private int IdProducto;
    private  String NameProducto;
    private int IconProduct;
    private float Price;
    private int Quantity;
    private float total;

    public ShoppingCart(int idProducto, String nameProducto,int IconProduct, float price, int quantity, float total) {
        IdProducto = idProducto;
        NameProducto = nameProducto;
        this.IconProduct = IconProduct;
        Price = price;
        Quantity = quantity;
        this.total = total;
    }

    public ShoppingCart(String nameProducto, int iconProduct, float price, int quantity, float total) {
        NameProducto = nameProducto;
        IconProduct = iconProduct;
        Price = price;
        Quantity = quantity;
        this.total = total;
    }

    public int getIconProduct() {
        return IconProduct;
    }

    public void setIconProduct(int iconProduct) {
        IconProduct = iconProduct;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public String getNameProducto() {
        return NameProducto;
    }

    public void setNameProducto(String nameProducto) {
        NameProducto = nameProducto;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
