package com.example.desai.kumo;

public class DocUpload {
    private String name;
    private String url;

    public DocUpload(){
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

    public DocUpload(String name, String url) {
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
