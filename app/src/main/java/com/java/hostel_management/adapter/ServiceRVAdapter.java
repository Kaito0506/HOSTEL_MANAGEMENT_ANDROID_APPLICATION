package com.java.hostel_management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.hostel_management.R;
import com.java.hostel_management.model.ModelService;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ServiceRVAdapter extends RecyclerView.Adapter<ServiceRVAdapter.ViewHolder>{
    public ArrayList<ModelService> serviceList;
    private Context context;
    public static NumberFormat VNDformater = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    public static NumberFormat USformater = NumberFormat.getCurrencyInstance(Locale.US);
    public static int selectedServiceId, selectedPosition;
    public static ModelService selectedService;

    public ServiceRVAdapter(ArrayList<ModelService> l, Context c){
        this.serviceList = l;
        this.context = c;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // we can use curency of CND or $
        VNDformater.setMaximumFractionDigits(0);
        //USformater.setMaximumFractionDigits(0);

        ModelService service = serviceList.get(position);
        holder.name.setText( service.getName());
        holder.price.setText(String.valueOf(USformater.format(service.getPrice())) );

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
    private static final String TAG = "NoteRVAdapter";
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView name, price;
        private int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtShowServiceName);
            price = itemView.findViewById(R.id.txtShowServicePrice);
            itemView.setOnLongClickListener(this);

        }
        @Override
        public boolean onLongClick(View v) {

            selectedPosition = getAdapterPosition();
            selectedService = serviceList.get(selectedPosition);
            selectedServiceId = selectedService.getId();
           // Log.d(TAG, "onLongClick: " + selectedRoomId);
            return false;
        }

    }
}