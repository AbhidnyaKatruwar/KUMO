package com.example.desai.kumo;


import com.google.firebase.database.Exclude;

public class ImageUpload {
    public String name;
    public String url;
    private String mKey;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(){
        //Required to show images in ImageListActivity DataChange function
    }

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }


}

