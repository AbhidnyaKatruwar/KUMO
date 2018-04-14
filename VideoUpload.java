package com.example.hp.app;

/**
 * Created by hp on 13/4/18.
 */

public class VideoUpload {
    private String name;
    private String url;

    public VideoUpload(){
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

    public VideoUpload(String name, String url) {
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
