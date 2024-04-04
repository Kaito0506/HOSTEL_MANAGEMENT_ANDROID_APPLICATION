package com.java.hostel_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.java.hostel_management.adapter.ServiceRVAdapter;
import com.java.hostel_management.model.ModelService;

import java.util.ArrayList;

public class ViewServiceActivity extends AppCompatActivity {
    DBHandler database;
    ArrayList<ModelService> serviceList;
    RecyclerView serviceRV;
    ServiceRVAdapter adapter;
    ImageButton back, add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        database = new DBHandler(this);
        serviceList = database.viewAllService();
        serviceRV = findViewById(R.id.RVListService);
        adapter = new ServiceRVAdapter(serviceList, this);
        serviceRV.setAdapter(adapter);

        //////////
        back = findViewById(R.id.btnBackManage);
        add = findViewById(R.id.btnDirectAddService);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewServiceActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewServiceActivity.this, AddServiceActivity.class);
                startActivity(i);
            }
        });

        registerForContextMenu(serviceRV);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_room_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = ServiceRVAdapter.selectedPosition;
        ModelService service = ServiceRVAdapter.selectedService;
        if(item.getItemId()==R.id.menu_delete){
            new AlertDialog.Builder(this).setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete this service")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isSuccess = database.deleteService(service.getId());
                            if(isSuccess){
                                serviceList.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ViewServiceActivity.this, "Delete service successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("No", null).show();
        } else if (item.getItemId()==R.id.menu_edit) {
            Intent i = new Intent(ViewServiceActivity.this, UpdateService.class);
            i.putExtra("id", service.getId());
            i.putExtra("name", service.getName());
            i.putExtra("price", service.getPrice());
            startActivity(i);
        }
        return super.onContextItemSelected(item);
    }
}