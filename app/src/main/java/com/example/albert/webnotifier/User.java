package com.example.albert.webnotifier;

/**
 * Created by Albert on 2018/3/8.
 */


public class User {

    private int id;
    private String urlName;
    private String url;

    public User(String urlName, String url) {
        this.urlName = urlName;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
