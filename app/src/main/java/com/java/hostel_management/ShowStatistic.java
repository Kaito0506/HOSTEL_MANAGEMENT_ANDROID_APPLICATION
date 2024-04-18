package com.java.hostel_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.java.hostel_management.adapter.BillRVAdapter;
import com.java.hostel_management.model.ModelBill;

import java.util.ArrayList;

public class ShowStatistic extends AppCompatActivity {
    RecyclerView RVBill;
    ImageButton back;
    ArrayList<ModelBill> billList;
    DBHandler database;
    BillRVAdapter adapter;
    Spinner spinnerMonth, spinnerYear;
    int selectedMonth=0, selectedYear=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_statistic);
        back = findViewById(R.id.btnBackMain);
        database = new DBHandler(this);

        ///////////////////////
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowStatistic.this, MainActivity.class);
                startActivity(i);
            }
        });
        // Create months list
        ArrayList<String> months = new ArrayList<>();
        months.add("All");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        // create year list
        ArrayList<String> years = new ArrayList<>();
        years.add("All");
        for(int i=2022 ; i<2050 ; i++){
            years.add(String.valueOf(i));
        }
        // Create adapters for months and years
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerMonth.setAdapter(monthsAdapter);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerYear.setAdapter(yearsAdapter);
        // month select
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = position;
                if(selectedMonth==0 && selectedYear==0){
                    billList = database.viewAllBill();
                    RVBill = findViewById(R.id.RVListBills);
                    adapter = new BillRVAdapter(billList, view.getContext());
                    RVBill.setAdapter(adapter);
                }
                else {
                    selectedMonth = position;
                    if(selectedMonth!=0 && selectedYear!=0){
                        billList = database.getBillsByfilter(selectedMonth, selectedYear);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }else if(selectedYear==0 && selectedMonth!=0){
                        billList = database.getBillsByMonth(selectedMonth);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }
                    else if(selectedYear!=0 && selectedMonth==0){
                        billList = database.getBillsByYear(selectedYear);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedYear = 0; // Set selectedYear to 0 for "All"
                } else {
                    selectedYear = Integer.parseInt(years.get(position));
                }
                /////////
                if(selectedMonth==0 && position==0){
                    billList = database.viewAllBill();
                    RVBill = findViewById(R.id.RVListBills);
                    adapter = new BillRVAdapter(billList, view.getContext());
                    RVBill.setAdapter(adapter);
                }
                else {
                    if(selectedMonth!=0 && selectedYear!=0){
                        billList = database.getBillsByfilter(selectedMonth, selectedYear);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }else if(selectedYear==0 && selectedMonth!=0){
                        billList = database.getBillsByMonth(selectedMonth);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }
                    else if(selectedYear!=0 && selectedMonth==0){
                        billList = database.getBillsByYear(selectedYear);
                        adapter = new BillRVAdapter(billList, view.getContext());
                        RVBill.setAdapter(adapter);
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}