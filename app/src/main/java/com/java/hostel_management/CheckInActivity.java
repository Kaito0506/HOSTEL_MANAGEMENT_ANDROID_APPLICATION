package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckInActivity extends AppCompatActivity {
    private EditText txtCheckIn, txtCheckOut, txtCusName;
    private TextView txtRoom;
    MaterialButton cancel, confirm;
    ImageButton back, chooseDateCheckIn, getChooseDateCheckOut;
    DBHandler database;
    String checkInDate, checkOutDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        // get intent data
        int room_id = getIntent().getIntExtra("room_id", 0);
        String room_name = getIntent().getStringExtra("room_name");
        // initialize control
        database = new DBHandler(this);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtCheckOut = findViewById(R.id.txtCheckOut);
        txtCusName = findViewById(R.id.txtCusName);
        txtRoom = findViewById(R.id.txtRoomName);

        cancel = findViewById(R.id.btnCancelCheckin);
        confirm = findViewById(R.id.btnConfirmCheckIn);
        back = findViewById(R.id.btnBackMain);
        chooseDateCheckIn = findViewById(R.id.btnDateCheckIn);
        getChooseDateCheckOut = findViewById(R.id.btnDateCheckOut);

        txtRoom.setText("Room: " + room_name);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        checkInDate = now.format(formatter);
        txtCheckIn.setText(checkInDate);


        /////////////////
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckInActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCusName.setText("");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
                checkInDate = now.format(formatter);
                txtCheckIn.setText(checkInDate);
                txtCheckOut.setText("");
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = database.addBill(room_id, txtCusName.getText().toString(), checkInDate, checkOutDate);
                if(isSuccess){
                    Toast.makeText(CheckInActivity.this, "Check in successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CheckInActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(CheckInActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}