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

public class UpdateService extends AppCompatActivity {
    ImageButton back;
    MaterialButton reset, update;
    EditText name, price;
    DBHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        int id = getIntent().getIntExtra("id", 0);
        String oName = getIntent().getStringExtra("name");
        Double oPrice = getIntent().getDoubleExtra("price", 0);

        name = findViewById(R.id.txtServiceNameUpdate);
        name.setText(oName);
        price = findViewById(R.id.txtServicePriceUpdate);
        price.setText(String.valueOf(oPrice));

        back = findViewById(R.id.btnBackService);
        reset = findViewById(R.id.btnResetService);
        update = findViewById(R.id.btnUpdateService);
        database = new DBHandler(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(oName);
                price.setText(String.valueOf(oPrice));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Toast.makeText(UpdateService.this, name.getText().toString(), Toast.LENGTH_SHORT).show();
                    boolean isUpdate = database.UpdateService(id, name.getText().toString(), Double.parseDouble(price.getText().toString()));
                    if(isUpdate){
                        Toast.makeText(UpdateService.this, "Update successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateService.this, ViewServiceActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(UpdateService.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d(TAG, "onUpdateService: " + e);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateService.this, ViewServiceActivity.class);
                startActivity(i);
            }
        });

    }
}