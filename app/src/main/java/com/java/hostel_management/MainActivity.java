package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.java.hostel_management.adapter.RoomRVAdapterGrid;
import com.java.hostel_management.model.ModelRoom;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHandler database;
    ImageButton btnRoom, btnService;
    Spinner spinner;
    private ArrayList<ModelRoom> roomList;
    private RecyclerView roomRV;
    private RoomRVAdapterGrid roomRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBHandler(MainActivity.this);
        btnRoom = findViewById(R.id.btnRoom);
        btnService = findViewById(R.id.btnService);

        ArrayList<String> filter = new ArrayList<>();
        filter.add("All");
        filter.add("Available");
        filter.add("Unavailable");


        roomRV = findViewById(R.id.RVGridRoomm);

        spinner = findViewById(R.id.spinnerFilter);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        roomList = database.viewAllRoom();
                        roomRVAdapter = new RoomRVAdapterGrid(roomList, MainActivity.this);
                        roomRV.setAdapter(roomRVAdapter);
                        break;
                    }
                    case 1:{
                        roomList = database.viewAvailableRoom(0);
                        roomRVAdapter = new RoomRVAdapterGrid(roomList, MainActivity.this);
                        roomRV.setAdapter(roomRVAdapter);
                        break;
                    }
                    case 2:{
                        roomList = database.viewAvailableRoom(1);
                        roomRVAdapter = new RoomRVAdapterGrid(roomList, MainActivity.this);
                        roomRV.setAdapter(roomRVAdapter);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewServiceActivity.class);
                startActivity(i);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewRoomActivity.class);
                startActivity(i);
            }
        });










    }
}