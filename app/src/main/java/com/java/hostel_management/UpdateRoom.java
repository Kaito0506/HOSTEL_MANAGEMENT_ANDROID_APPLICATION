package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UpdateRoom extends AppCompatActivity {
    EditText name, price;
    Spinner spinner;
    MaterialButton update, reset;
    private DBHandler database = new DBHandler(this);
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        update = findViewById(R.id.btnUpdateRoom);
        reset = findViewById(R.id.btnResetRoom);

        // get extra from intent
        int id = getIntent().getIntExtra("id", 0);
        String oName = getIntent().getStringExtra("name");
        Double oPrice = getIntent().getDoubleExtra("price", 0);
        int typeid = getIntent().getIntExtra("type", 0);
        DecimalFormat decimalFormat = new DecimalFormat("#");

        name = findViewById(R.id.txtRoomNameUpdate);
        name.setText(oName);
        price = findViewById(R.id.txtRoomPriceUpdate);
        price.setText(decimalFormat.format(oPrice));
        spinner = findViewById(R.id.spinnerTypeUpdate);

        ArrayList<String> types = new ArrayList<>();
        types.add("Normal");
        types.add("Twin");
        types.add("Queen");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set adapter and set old type
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(typeid);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = types.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(oName);
                spinner.setSelection(typeid);
                price.setText(decimalFormat.format(oPrice));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UpdateRoom.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                boolean isUpdate = database.UpdateRoom(id, name.getText().toString(), type, Double.parseDouble(price.getText().toString()));
                if(isUpdate){
                    Toast.makeText(UpdateRoom.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateRoom.this, ViewRoomActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(UpdateRoom.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}