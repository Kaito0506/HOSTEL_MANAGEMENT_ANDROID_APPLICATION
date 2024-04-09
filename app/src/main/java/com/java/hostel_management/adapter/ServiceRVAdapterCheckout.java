package com.java.hostel_management.adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.hostel_management.DBHandler;
import com.java.hostel_management.R;
import com.java.hostel_management.model.ModelBillDetail;
import com.java.hostel_management.model.ModelService;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ServiceRVAdapterCheckout extends RecyclerView.Adapter<ServiceRVAdapterCheckout.ViewHolder>{
    private ArrayList<ModelBillDetail> detailList;
    public static int selectedServiceId, selectedPosition;
    public static ModelService selectedService;
    private static final String TAG = "xxx";
    private Context context;
    private DBHandler database;
    public ServiceRVAdapterCheckout(  ArrayList<ModelBillDetail> l, Context c){
        this.detailList = l;
        this.context = c;
        database = new DBHandler(c);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bill_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelBillDetail detail = detailList.get(position);
        ModelService service = database.getService(detail.getService_id());
        Double total = detail.getQuantity() * service.getPrice();

        holder.name.setText(service.getName());
        holder.quantity.setText(String.valueOf(detail.getQuantity()));
        holder.unitPrice.setText(ServiceRVAdapter.USformater.format(service.getPrice()));
        holder.total.setText(ServiceRVAdapter.USformater.format(total));

    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , quantity, unitPrice, total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.showServiceName);
            quantity = itemView.findViewById(R.id.showServiceQuantity);
            unitPrice = itemView.findViewById(R.id.showServicePrice);
            total = itemView.findViewById(R.id.showServiceTotal);

        }
    }
}
