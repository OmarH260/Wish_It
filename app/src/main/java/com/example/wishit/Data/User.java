package com.example.wishit.Data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.UUID;

public class User implements Parcelable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean isAdmin;
    private Boolean isGuest;
    private String userID = null;

    public User() {
    }
    public User(Boolean isGuest) {
        this.isGuest = isGuest;
        this.isAdmin = false;
        this.firstName = "Guest";
        this.lastName = "";
        this.phoneNumber = "";
        if(this.userID == null) {
            this.userID = UUID.randomUUID().toString();
        }
    }

    public User(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.isAdmin = false;
        this.isGuest = false;
        if(this.userID == null) {
            this.userID = UUID.randomUUID().toString();
        }
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        byte tmpIsAdmin = in.readByte();
        isAdmin = tmpIsAdmin == 0 ? null : tmpIsAdmin == 1;
        byte tmpIsGuest = in.readByte();
        isGuest = tmpIsGuest == 0 ? null : tmpIsGuest == 1;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getGuest() {
        return isGuest;
    }

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isAdmin=" + isAdmin +
                ", isGuest=" + isGuest +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (isAdmin == null ? 0 : isAdmin ? 1 : 2));
        dest.writeByte((byte) (isGuest == null ? 0 : isGuest ? 1 : 2));
    }
}
