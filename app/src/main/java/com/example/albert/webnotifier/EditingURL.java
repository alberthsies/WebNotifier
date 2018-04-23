package com.example.albert.webnotifier;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Albert on 2018/4/21.
 */

public class EditingURL extends AppCompatActivity {

    private static final String TAG = "EditingURL";

    String urlName;
    String url;
    Button urlDeletingButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_url);

        Log.d(TAG, "onCreate: ");

        getIncomingIntent();

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").allowMainThreadQueries().build();

        Log.d(TAG, "onCreate: entering the item with id: " + db.userDao().getIdByName(urlName) + ", UrlName: " + urlName + ", and word count = " + db.userDao().getWordCountByName(urlName));
        Toast.makeText(EditingURL.this, "urlName = " + urlName + " and current word count = " + db.userDao().getWordCountByName(urlName), Toast.LENGTH_SHORT).show();

        urlDeletingButton = findViewById(R.id.button_delete_url);

        urlDeletingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: pressing deleting button");
                //Toast.makeText(EditingURL.this,"pressing deleting button",Toast.LENGTH_SHORT).show();
                db.userDao().deleteUsersByName(urlName);
                startActivity(new Intent(EditingURL.this, UrlListActivity.class));

            }
        });

    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");
        if(getIntent().hasExtra("urlName") && getIntent().hasExtra("url")){
            Log.d(TAG, "getIncomingIntent: intent has extra urlName and url");

            urlName = getIntent().getStringExtra("urlName");
            url = getIntent().getStringExtra("url");

            setUrlNameAndUrl(urlName, url);
        }
    }

    private void setUrlNameAndUrl(String N, String U){
        Log.d(TAG, "setUrlNameAndUrl: UrlName = " + N + ", Url = " + U);
        TextView urlName = findViewById(R.id.text_url_name_ed);
        TextView url = findViewById(R.id.text_url_ed);

        urlName.setText(N);
        url.setText(U);
    }
}
