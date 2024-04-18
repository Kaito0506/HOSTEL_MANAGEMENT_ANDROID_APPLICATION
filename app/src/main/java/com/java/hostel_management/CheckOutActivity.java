package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.java.hostel_management.adapter.ServiceRVAdapter;
import com.java.hostel_management.adapter.ServiceRVAdapterCheckout;
import com.java.hostel_management.model.ModelBill;
import com.java.hostel_management.model.ModelBillDetail;
import com.java.hostel_management.model.ModelService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {
    DBHandler database;
    TextView txtRoomName, txtCusName, txtCheckIn,
            txtCheckOut, txtNumNight, txtRoomPriceCheckOut,
            txtRoomPriceTable, txtAmount,  txtTotal, txtTotalRoomPrice;
    ImageButton back;
    MaterialButton pay;
    RecyclerView recyclerView;
    int  room_id;
    String room_name;
    ModelBill selectedBill;
    Double room_price, finalTotal;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        room_id = getIntent().getIntExtra("roomId", 0);
        room_name = getIntent().getStringExtra("roomName");
        room_price = getIntent().getDoubleExtra("roomPrice", 0);

        database = new DBHandler(this);
        txtRoomName = findViewById(R.id.txtRoomNameCheckout);
        txtCusName = findViewById(R.id.txtCusNameCheckout);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtCheckOut = findViewById(R.id.txtCheckOut);
        txtNumNight = findViewById(R.id.txtNumNight);
        txtRoomPriceTable = findViewById(R.id.txtRoomPriceTable);
        txtRoomPriceCheckOut = findViewById(R.id.txtPriceCheckOut);
        txtAmount = findViewById(R.id.txtTotalRoomPrice);
        txtTotalRoomPrice = findViewById(R.id.txtTotalRoomPrice);
        txtTotal = findViewById(R.id.txtTotal);
        recyclerView = findViewById(R.id.RVListDetail);
        selectedBill = database.getUncheckOutBill(room_id);
        ////////////
        back = findViewById(R.id.btnBackMain);
        pay = findViewById(R.id.btnPay);
        /////// binding data
        txtRoomName.setText("Room: "+ room_name);
        txtCusName.setText("Customer: "+ selectedBill.getCus_name());
        txtCheckIn.setText("Check in: " + selectedBill.getCheckIn());
        /////////// get price
        txtRoomPriceTable.setText(ServiceRVAdapter.USformater.format(room_price));
        txtRoomPriceCheckOut.setText("Price: " + ServiceRVAdapter.USformater.format(room_price) + "/night");
        //////  get checkout time
        LocalDateTime now = LocalDateTime.now();
        String checkOut = now.format(dateTimeFormatter);
        txtCheckOut.setText("Check out: " + checkOut);
        ///////////////// set number nights, quantity and total for room
        long quantityNight = calculateDuration( selectedBill.getCheckIn(), checkOut);
        Double totalRoomPrice = (double)quantityNight * room_price;
        txtNumNight.setText(String.valueOf(quantityNight));
        txtTotalRoomPrice.setText(ServiceRVAdapter.USformater.format(totalRoomPrice));

        ////////////////// set adater for RV
        ArrayList<ModelBillDetail> detailList = database.viewAllBillDetail(selectedBill.getId());
        //Toast.makeText(this, String.valueOf(detailList.size()), Toast.LENGTH_SHORT).show();
        ServiceRVAdapterCheckout adapter = new ServiceRVAdapterCheckout( detailList, this);
        recyclerView.setAdapter(adapter);
        ////////////////////////////////////////// calculate all price: room and services
        finalTotal = calculateDetailTotal(detailList);
        finalTotal += totalRoomPrice;
        txtTotal.setText("Total: " + ServiceRVAdapter.USformater.format( finalTotal));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckOutActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = database.checkout(room_id, selectedBill.getId(), checkOut, finalTotal);
                if(isSuccess){
                    Toast.makeText(CheckOutActivity.this, "Pay successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CheckOutActivity.this, MainActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(CheckOutActivity.this, "Error when paying", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private Double calculateDetailTotal(ArrayList<ModelBillDetail> listDetail){
        Double total=0.0;
        for(ModelBillDetail d : listDetail){
            ModelService s = database.getService(d.getService_id());
            total += d.getQuantity()*s.getPrice();
        }
        return  total;
    }
    ///////////////////////////
    private long calculateDuration(String timeIn, String timeOut){
        LocalDateTime in = LocalDateTime.parse(timeIn, dateTimeFormatter);
        LocalDateTime out = LocalDateTime.parse(timeOut, dateTimeFormatter);
        Duration d = Duration.between(in, out);
        long hours = d.toHours();
        long days = d.toDays();
        Double n = (double)hours/24;
        if(n>days || days==0){
            return days+1;
        }
        return days;
    }
}