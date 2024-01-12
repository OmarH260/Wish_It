package com.example.wishit.AddDataFire;

public class Card {
    private String photo;
    private String title;

    public Card(String photo, String title) {
        this.photo = photo;
        this.title = title;
    }

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
}
