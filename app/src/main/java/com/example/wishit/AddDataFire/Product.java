package com.example.wishit.AddDataFire;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

public class Product implements Parcelable {
    private String photo;
    private String tittle;
    private String description;
    private String price;
    private double rating;
    private String type;
    private String productId = null;

    public Product() {
    }

    public Product(String photo, String tittle, String description, String price, double rating, String type) {
        this.photo = photo;
        this.tittle = tittle;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.type = type;
        if(this.productId == null) {
            this.productId = UUID.randomUUID().toString();
        }
    }


    protected Product(Parcel in) {
        photo = in.readString();
        tittle = in.readString();
        description = in.readString();
        price = in.readString();
        rating = Double.parseDouble(Objects.requireNonNull(in.readString()));
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) { this.tittle = tittle; }

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "photo='" + photo + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(tittle);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(String.valueOf(rating));
    }
}
