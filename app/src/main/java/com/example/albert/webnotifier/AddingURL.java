package com.example.albert.webnotifier;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        urlAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 180308 Save input to database
                Log.d(TAG, "onClick: URL Name = " + urlName.getText().toString() + ", URL = " + url.getText().toString());
            }
        });
    }
}
