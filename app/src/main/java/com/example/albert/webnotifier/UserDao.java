package com.example.albert.webnotifier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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

    @Delete
    void delete(User user);

}