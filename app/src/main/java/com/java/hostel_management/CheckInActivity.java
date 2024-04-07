package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class CheckInActivity extends AppCompatActivity {
    private EditText txtCheckIn, txtCheckOut, txtCusName;
    private TextView txtRoom;
    MaterialButton cancel, confirm;
    ImageButton back, chooseDateCheckIn, getChooseDateCheckOut;
    DBHandler database;
    String checkInDate, selectedDate, selectedTime;
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
        txtCusName = findViewById(R.id.txtCusName);
        txtRoom = findViewById(R.id.txtRoomName);

        cancel = findViewById(R.id.btnCancelCheckin);
        confirm = findViewById(R.id.btnConfirmCheckIn);
        back = findViewById(R.id.btnBackMain);
        chooseDateCheckIn = findViewById(R.id.btnDateCheckIn);

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
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCusName.getText().toString().isEmpty()){
                    Toast.makeText(CheckInActivity.this, "Please fill Customer name fiekd", Toast.LENGTH_SHORT).show();
                }else {
                    boolean isSuccess = database.addBill(room_id, txtCusName.getText().toString(), checkInDate);
                    if(isSuccess){
                        Toast.makeText(CheckInActivity.this, "Check in successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CheckInActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(CheckInActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        chooseDateCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();


            }
        });

    }
    private void chooseDate(){
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DATE);
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = String.valueOf(dayOfMonth)+"/"+ String.valueOf(month+1)+"/"+ String.valueOf(year);
                chooseTime();
            }
        }, y, m, d);
        dateDialog.show();
    }

    private void chooseTime(){
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        int s = c.get(Calendar.SECOND);
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = String.format("%02d:%02d:%02d", hourOfDay, minute, s);
                selectedDate = selectedTime + " " + selectedDate;
                txtCheckIn.setText(selectedDate);
            }
        }, h, m, true);
        timeDialog.show();
    }
}