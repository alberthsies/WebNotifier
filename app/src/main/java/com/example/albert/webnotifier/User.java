package com.example.albert.webnotifier;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Albert on 2018/3/8.
 */

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "url_name")
    private String urlName;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "word_count")
    private int wordCount;

    public User(String urlName, String url, int wordCount) {
        this.urlName = urlName;
        this.url = url;
        this.wordCount = wordCount;
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

    public  int getWordCount() {return wordCount;}

    public  void setWordCount(int wordCount) {this.wordCount = wordCount;}
}
