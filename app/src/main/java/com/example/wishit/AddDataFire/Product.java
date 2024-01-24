package com.example.wishit.AddDataFire;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
    private String photo;
    private String tittle;
    private String description;
    private String price;

    public Product() {
    }

    public Product(String tittle, String description, String price, String photo) {
        this.tittle = tittle;
        this.description = description;
        this.price = price;
        this.photo = photo;
    }


    protected Product(Parcel in) {
        photo = in.readString();
        tittle = in.readString();
        description = in.readString();
        price = in.readString();
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

    @Override
    public String toString() {
        return "Product{" +
                "photo='" + photo + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
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
    }
}
