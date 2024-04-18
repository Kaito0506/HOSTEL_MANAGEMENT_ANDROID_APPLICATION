package com.java.hostel_management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.java.hostel_management.DBHandler;
import com.java.hostel_management.R;
import com.java.hostel_management.model.ModelBill;
import com.java.hostel_management.model.ModelRoom;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BillRVAdapter extends RecyclerView.Adapter<BillRVAdapter.ViewHolder>{
    public ArrayList<ModelBill> billList;
    private Context context;
    private  DBHandler databse;


    public BillRVAdapter(ArrayList<ModelBill> l, Context c){
        this.billList = l;
        this.context = c;
        databse = new DBHandler(c);
    }


    private static final String TAG = "NoteRVAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelBill bill = billList.get(position);
        String roomName = databse.getRoomFromBill(bill.getRoomId());

        holder.billId.setText(roomName);
        holder.billCusName.setText("Customer: " + bill.getCus_name());
        holder.billCheckIn.setText("In: "+bill.getCheckIn());
        if(bill.getCheckOut()!=null){
            holder.billCheckOut.setText("Out: "+bill.getCheckOut());
        }
        holder.billTotal.setText(RoomRVAdapter.USformater.format(bill.getTotal()));
        if(bill.getStatus()==0){
            holder.billStatus.setText("Uncheck");
            holder.billStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }else{
            holder.billStatus.setText("Paid");
            holder.billStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView billId, billCusName, billCheckIn, billCheckOut, billStatus, billTotal;
        private int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billId = itemView.findViewById(R.id.txtBillId);
            billCusName = itemView.findViewById(R.id.txtBillCusName);
            billCheckIn = itemView.findViewById(R.id.txtBillCheckIn);
            billCheckOut = itemView.findViewById(R.id.txtBillCheckOut);
            billStatus = itemView.findViewById(R.id.txtBillStatus);
            billTotal = itemView.findViewById(R.id.txtBillTotal);
        }
    }
}