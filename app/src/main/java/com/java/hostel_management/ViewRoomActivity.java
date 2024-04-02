package com.java.hostel_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

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

        //////////////
        registerForContextMenu(roomRV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_room_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        ModelRoom room = RoomRVAdapter.selectedRoom;
        //get room spinner selection
        int selectedType;
        switch (room.getType()){
            case "Normal":{
                selectedType=0;
                break;
            }
            case "Twin":{
                selectedType=1;
                break;
            }
            case "Queen":{
                selectedType=2;
                break;
            } default:{
                selectedType=0;
            }
        }
        int position = RoomRVAdapter.selectedPosition;
        // Delete menu item select
        if (item.getItemId() == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this room?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (database.deleteRoom(room.getId())) {
                                roomList.remove(position);
                                roomRVAdapter.notifyDataSetChanged();
                                Toast.makeText(ViewRoomActivity.this, "Successfully deleted room", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ViewRoomActivity.this, "Failed to delete room", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked "No", dismiss the dialog
                            dialog.dismiss();
                        }
                    })
                    .show();
        }// Edit menu select
        else if(item.getItemId()==R.id.menu_edit){
            Intent i = new Intent(ViewRoomActivity.this, UpdateRoom.class);
            i.putExtra("name", room.getName());
            i.putExtra("price", room.getPrice());
            i.putExtra("type", selectedType);
            i.putExtra("id", room.getId());
            startActivity(i);
        }
        return super.onContextItemSelected(item);




    }
}