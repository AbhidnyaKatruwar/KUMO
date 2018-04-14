package com.example.hp.app;

import android.provider.MediaStore;

/**
 * Created by hp on 13/4/18.
 */

public class AudioUpload {
    private String name;
    private String url;

    public AudioUpload(){
        //Required for DataSnapshot
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {

        return name;
    }

    public AudioUpload(String name, String url) {
        if (name.trim().equals("")) {
            name = "Anonymus";
        }
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}