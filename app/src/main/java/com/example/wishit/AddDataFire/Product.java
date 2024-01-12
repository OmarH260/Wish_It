package com.example.wishit.AddDataFire;

public class Product {
    private String photo;
    private String tittle;
    private String description;

    @Override
    public String toString() {
        return "Product{" +
                "photo='" + photo + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String price;

    public Product() {
    }

    public Product(String tittle, String description, String price, String photo) {
        this.tittle = tittle;
        this.description = description;
        this.price = price;
        this.photo = photo;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
