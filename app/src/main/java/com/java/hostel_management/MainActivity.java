package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHandler database;
    ImageButton btnSetting;

    private ArrayList<ModelRoom> roomList;
    private RecyclerView roomRV;
    private RoomRVAdapterGrid roomRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHandler(MainActivity.this);
        btnSetting = findViewById(R.id.btnSetting);
        roomRV = findViewById(R.id.RVGridRoomm);
        roomList = database.viewAllRoom();

        roomRVAdapter = new RoomRVAdapterGrid(roomList, this);

        roomRV.setAdapter(roomRVAdapter);




        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ManageActivity.class);
                startActivity(i);
            }
        });


    }
}