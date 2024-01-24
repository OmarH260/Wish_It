package com.example.wishit.AddDataFire;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Card implements Parcelable {
    private String photo;
    private String title;

    public Card() {
    }

    public Card(String photo, String title) {
        this.photo = photo;
        this.title = title;
    }

    protected Card(Parcel in) {
        photo = in.readString();
        title = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Card{" +
                "photo='" + photo + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(title);
    }
}
