package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import java.net.Inet4Address;
import java.util.ArrayList;

public class ViewRoomActivity extends AppCompatActivity {
    private DBHandler database;
    private ArrayList<ModelRoom> roomList;
    private RecyclerView roomRV;
    private RoomRVAdapter roomRVAdapter;
    private ImageButton btnBack, btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        database = new DBHandler(this);
        roomList = new ArrayList<>();
        btnBack = findViewById(R.id.btnBackManage);
        btnAdd = findViewById(R.id.btnDirectAddRoom);

        roomList = database.viewAllRoom();

        roomRVAdapter = new RoomRVAdapter(roomList, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewRoomActivity.this, RecyclerView.VERTICAL, false);

        roomRV = findViewById(R.id.RVListRoom);
        roomRV.setLayoutManager(layoutManager);
        roomRV.setAdapter(roomRVAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewRoomActivity.this, ManageActivity.class);
                startActivity(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewRoomActivity.this, AddRoomActivity.class);
                startActivity(i);
            }
        });
    }





}