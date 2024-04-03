package com.java.hostel_management;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AddServiceActivity extends AppCompatActivity {
    DBHandler database;
    EditText name, price;
    MaterialButton cancel, add;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        database = new DBHandler(this);
        name = findViewById(R.id.txtServicemName);
        price = findViewById(R.id.txtServicePrice);
        cancel = findViewById(R.id.btnCancel);
        add = findViewById(R.id.btnAdd);
        back = findViewById(R.id.btnBackService);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearContent();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddServiceActivity.this, ViewServiceActivity.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || price.getText().toString().isEmpty()){
                    Toast.makeText(AddServiceActivity.this, "Please fill entire information", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String n = name.getText().toString();
                        Double p = Double.parseDouble(price.getText().toString());
                        //Toast.makeText(AddServiceActivity.this, n + String.valueOf(p), Toast.LENGTH_SHORT).show();
                        boolean isSuccess = database.addService(n, p);
                        if(isSuccess){
                            clearContent();
                            Toast.makeText(AddServiceActivity.this, "Add serivce success", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AddServiceActivity.this, "Add service failed", Toast.LENGTH_SHORT).show();
                            //clearContent();
                        }
                    }catch(Exception e )
                    {
                        Toast.makeText(AddServiceActivity.this, "Can not add service failed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: " + e);
                    }
                }
            }
        });

    }
    private void clearContent(){
        name.setText("");
        price.setText("");
    }
}