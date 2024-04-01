package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AddRoomActivity extends AppCompatActivity {
    EditText name, price;
    Spinner spinnerType;
    String selectedType;
    MaterialButton add, cancel;
    DBHandler database = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        name = findViewById(R.id.txtRoomName);
        price = findViewById(R.id.txtRoomPrice);

        add = findViewById(R.id.btnAddRoom);
        cancel = findViewById(R.id.btnCancelRoom);

        spinnerType = findViewById(R.id.spinnerType);
        ArrayList<String> types = new ArrayList<>();
        types.add("Normal");
        types.add("Twin");
        types.add("Queen");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(spinnerAdapter);


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = types.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || price.getText().toString().isEmpty()){
                    Toast.makeText(AddRoomActivity.this, "Please fill entire information", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String n = name.getText().toString();
                        Double p = Double.parseDouble(price.getText().toString());
                        database.addRoom(n, selectedType, p);
                        Toast.makeText(AddRoomActivity.this, "Add room success", Toast.LENGTH_SHORT).show();
                    }catch(Exception e )
                    {
                        Toast.makeText(AddRoomActivity.this, "Add room failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}