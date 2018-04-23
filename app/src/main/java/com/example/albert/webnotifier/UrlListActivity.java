package com.example.albert.webnotifier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.net.URL;
import java.util.TimerTask;
import java.util.Timer;

public class UrlListActivity extends AppCompatActivity {

    private static final String TAG = "UrlListActivity";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    FloatingActionButton fab;

    //final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_url_list);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        List<User> users = db.userDao().getAllusers();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: pressed!");
                //Toast.makeText(UrlListActivity.this,"hello",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UrlListActivity.this, AddingURL.class));
            }
        });

        //TODO: Set timer (180423 DONE)
        Timer timer = new Timer();
        TimerTask checkNewUpdateTimer = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate: check method checkUrlUpdate();");
                checkUrlUpdate();
                Log.d(TAG, "onCreate: ================================================================");
            }
        };

        timer.schedule(checkNewUpdateTimer, 0, 10000);

    }

    // TODO: Add settings
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_url_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void checkUrlUpdate(){
        Log.d(TAG, "checkUrlUpdate: ");
        final int numOfList = adapter.getItemCount();
        Log.d(TAG, "checkUrlUpdate: numOfList = " + numOfList);
        int i = 0;
        int worthI = 1;

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        while(worthI <= numOfList){
            final String urlString = db.userDao().getUrlById(i);
            //Log.d(TAG, "checkUrlUpdate: url = " + urlString);
            if(urlString != null) {
                final int ID = i;
                Log.d(TAG, "checkUrlUpdate: i = " + i + ", url = " + urlString);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d(TAG, "checkUrlUpdate: here 1");
                            URL url = new URL(urlString);
                            Log.d(TAG, "checkUrlUpdate: here 2");
                            int count = 0;
                            Log.d(TAG, "checkUrlUpdate: here 3");
                            Scanner input = new Scanner(url.openStream());
                            Log.d(TAG, "checkUrlUpdate: here 4");
                            while (input.hasNext()) {
                                String line = input.nextLine();
                                count += line.length();
                            }
                            Log.d(TAG, "checkUrlUpdate: here 5");

                            //System.out.println("The file size is " + count + " characters");
                            Log.d(TAG, "checkUrlUpdate: he file size is " + count + " characters");
                            //TODO: Send notification
                            int preWordCount = db.userDao().getWordCountById(ID);
                            if(preWordCount != count){
                                //send notification to user
                                Log.d(TAG, "run: send notification to user");

                                int NOTIFICATION_ID = 234;

                                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


                                    String CHANNEL_ID = "my_channel_01";
                                    CharSequence name = "my_channel";
                                    String Description = "This is my channel";
                                    int importance = NotificationManager.IMPORTANCE_HIGH;
                                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                                    mChannel.setDescription(Description);
                                    mChannel.enableLights(true);
                                    //mChannel.setLightColor(Color.RED);
                                    mChannel.enableVibration(true);
                                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                    mChannel.setShowBadge(false);
                                    notificationManager.createNotificationChannel(mChannel);
                                }

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(UrlListActivity.this, "My Channel 1")
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle(db.userDao().getNameById(ID) + " has a new update!")
                                        .setContentText(db.userDao().getUrlById(ID) + " has a new update!");

                                Intent resultIntent = new Intent(UrlListActivity.this, UrlListActivity.class);
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(UrlListActivity.this);
                                stackBuilder.addParentStack(UrlListActivity.class);
                                stackBuilder.addNextIntent(resultIntent);
                                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                                builder.setContentIntent(resultPendingIntent);

                                notificationManager.notify(NOTIFICATION_ID, builder.build());

                                //update url word count
                                Log.d(TAG, "run: update new url word count");
                                User updateUser = new User(db.userDao().getNameById(ID), urlString, count);
                                updateUser.setId(ID);
                                db.userDao().update(updateUser);
                            }

                            input.close();
                        } catch (MalformedURLException ex) {
                            //System.out.println("Invalid URL");
                            Log.d(TAG, "checkUrlUpdate: Invalid URL");
                        } catch (IOException ex) {
                            //System.out.println("IO Errors");
                            Log.d(TAG, "checkUrlUpdate: IO Errors: " + ex);
                        } /*catch (java.lang.Exception ex ) {
                            Log.d(TAG, "checkUrlUpdate: " + ex);
                        }*/
                    }
                }).start();

                worthI++;
            }

            i++;
        }
    }
}
