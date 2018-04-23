package com.example.albert.webnotifier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Albert on 2018/3/8.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllusers();

/*    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE url_name LIKE :urlName AND " + "url LIKE :url LIMIT 1")
    User findByName(String urlName, String url);*/

    @Insert
    void insertAll(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    @Query("delete from user where url_name like :name")
    void deleteUsersByName(String name);

    @Query("SELECT id from user where url_name like :name")
    int getIdByName(String name);

    @Query("SELECT url_name from user where id like :Id")
    String getNameById(int Id);

    @Query("SELECT url from user where url_name like :name")
    String getUrlByName(String name);

    @Query("SELECT url from user where id like :Id")
    String getUrlById(int Id);

    @Query("SELECT word_count from user where url_name like :name")
    int getWordCountByName(String name);

    @Query("SELECT word_count from user where id like :Id")
    int getWordCountById(int Id);

}