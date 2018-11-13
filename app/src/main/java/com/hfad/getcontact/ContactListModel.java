package com.hfad.getcontact;

import android.net.Uri;

public class ContactListModel {

    private String id;
    private String image;
    private String name;
    private String phoneNumber;

    public ContactListModel(String id, String image, String name, String phoneNumber) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
