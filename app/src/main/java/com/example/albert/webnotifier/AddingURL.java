package com.example.albert.webnotifier;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Albert on 2018/3/8.
 */

public class AddingURL extends AppCompatActivity{

    private static final String TAG = "AddingURL";

    EditText urlName;
    EditText url;
    Button urlAddingButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_url);

        urlName = findViewById(R.id.input_url_name);
        url = findViewById(R.id.input_url);
        urlAddingButton = findViewById(R.id.button_add_url);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();
        //final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").build();

        urlAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 180308 Save input to database
                Log.d(TAG, "onClick: URL Name = " + urlName.getText().toString() + ", URL = " + url.getText().toString());
                int wordCount = 0;
                try {
                    wordCount = getTheFirstWordCount(url.getText().toString());
                    Log.d(TAG, "onClick: wordcount return with the value = " + wordCount);
                } catch (InterruptedException e) {
                    Log.d(TAG, "onClick: wordCount fail to return");
                    e.printStackTrace();
                }
                db.userDao().insertAll(new User(urlName.getText().toString(), url.getText().toString(), wordCount));
                startActivity(new Intent(AddingURL.this, UrlListActivity.class));
            }
        });
    }

    public int getTheFirstWordCount(String urlString) throws InterruptedException {
        final int [] wordCount = new int [1];
        wordCount[0] = 0;
        final String finalUrlString = urlString;
        if (urlString != null) {
            //Log.d(TAG, "checkUrlUpdate: i = " + i + ", url = " + urlString);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(finalUrlString);
                        Scanner input = new Scanner(url.openStream());
                        while (input.hasNext()) {
                            String line = input.nextLine();
                            wordCount[0] += line.length();
                        }
                        Log.d(TAG, "run: wordCount = " + wordCount[0]);
                        input.close();

                    } catch (MalformedURLException ex) {
                        //System.out.println("Invalid URL");
                        Log.d(TAG, "checkUrlUpdate: Invalid URL");
                        wordCount[0] = -1;
                    } catch (IOException ex) {
                        //System.out.println("IO Errors");
                        Log.d(TAG, "checkUrlUpdate: IO Errors: " + ex);
                        wordCount[0] = -1;
                    } /*catch (java.lang.Exception ex ) {
                            Log.d(TAG, "checkUrlUpdate: " + ex);
                        }*/
                }
            }).start();

        }
        while(wordCount[0] == 0){
            Thread.sleep(2000);
        }
        Log.d(TAG, "getTheFirstWordCount: wordCount before return = " + wordCount[0]);
        return wordCount[0];
    }
}
