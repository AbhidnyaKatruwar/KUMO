package com.example.desai.kumo;

public class PdfUpload {
    private String name;
    private String url;

    public PdfUpload(){
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

    public PdfUpload(String name, String url) {
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
