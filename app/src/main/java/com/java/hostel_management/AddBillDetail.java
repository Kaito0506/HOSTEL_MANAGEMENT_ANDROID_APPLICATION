package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.java.hostel_management.model.ModelBill;
import com.java.hostel_management.model.ModelService;

import java.util.ArrayList;

public class AddBillDetail extends AppCompatActivity {
    DBHandler database;
    EditText quantity;
    TextView txtRoomName;
    MaterialButton cancel, add;
    ImageButton back;
    Spinner spinner;
    int selectedItemId;
    ArrayList<ModelService> serviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_detail);

        int room_id = getIntent().getIntExtra("roomId", 0);
        String room_name = getIntent().getStringExtra("roomName");
        database = new DBHandler(this);
        quantity = findViewById(R.id.txtServiceQuantity);
        txtRoomName = findViewById(R.id.txtRoomNameService);
        txtRoomName.setText("Room: " + room_name);
        cancel = findViewById(R.id.btnCancelService);
        add = findViewById(R.id.btnAddService);
        back = findViewById(R.id.btnBackMain);

        // get bill and add bill detail
        ModelBill bill = database.getUncheckOutBill(room_id);
        int bill_id = bill.getId();

        // bind data for spinner
        serviceList = new ArrayList<>();
        serviceList = database.viewAllService();
        ArrayList<String> listName = new ArrayList<>();
        for(ModelService s  : serviceList){
            listName.add(s.getName());
        }
        // initial adapter for spinner
        spinner = findViewById(R.id.spinner_service);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemId = serviceList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                quantity.setText("");
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddBillDetail.this, MainActivity.class);
                startActivity(i);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean isSuccess = database.addBillDetail(bill_id, selectedItemId, Integer.parseInt(quantity.getText().toString()));
                    if (isSuccess){
                        Toast.makeText(AddBillDetail.this, "Add service successfully", Toast.LENGTH_SHORT).show();
                        cancel.callOnClick();
                    }else {
                        Toast.makeText(AddBillDetail.this, "Add service failed", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                }
            }
        });

    }
}