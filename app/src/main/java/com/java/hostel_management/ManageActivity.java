package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ManageActivity extends AppCompatActivity {
    ImageButton btnBack, btnRoom, btnService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        btnBack = findViewById(R.id.btnBackMain);
        btnRoom = findViewById(R.id.btnRoomMange);
        btnService = findViewById(R.id.btnServiceManage);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageActivity.this, ViewRoomActivity.class);
                startActivity(i);
            }
        });


        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManageActivity.this, ViewServiceActivity.class);
                startActivity(i);
            }
        });


    }
}