package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHandler(MainActivity.this);

        Button button = findViewById(R.id.btnTest);
        TextView hello = findViewById(R.id.txtHello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    database.addRoomType("Hourly", 80000);
                    hello.setText("success");
                }catch (Exception e){

                }

            }
        });

    }
}