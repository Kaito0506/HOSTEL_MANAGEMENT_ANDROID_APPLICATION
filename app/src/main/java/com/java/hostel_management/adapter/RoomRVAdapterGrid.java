package com.java.hostel_management.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.hostel_management.CheckInActivity;
import com.java.hostel_management.MainActivity;
import com.java.hostel_management.R;
import com.java.hostel_management.model.ModelRoom;

import java.util.ArrayList;

public class RoomRVAdapterGrid extends  RecyclerView.Adapter<RoomRVAdapterGrid.ViewHolder>{
    private ArrayList<ModelRoom> roomList;
    private Context context;
    public RoomRVAdapterGrid(ArrayList<ModelRoom> l, Context c){
        this.roomList = l;
        this.context = c;
    }
    public static int selectedPosition;
    public static ModelRoom selectedRoom;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_room_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRoom room = roomList.get(position);
        holder.name.setText(room.getName());
        holder.type.setText(room.getType());
        holder.price.setText(String.valueOf(RoomRVAdapter.USformater.format(room.getPrice())) + "/night");
        if(room.getStatus()==0){
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.cyan));
            holder.btnCheckIn.setEnabled(true);
        }
        else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.grey));
            holder.btnCheckIn.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
    private static String TAG;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, type, price;
        private int id, status;
        ImageButton btnCheckIn;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtRoomNameGrid);
            type = itemView.findViewById(R.id.txtRoomTypeGrid);
            price = itemView.findViewById(R.id.txtRoomPriceGrid);
            btnCheckIn = itemView.findViewById(R.id.btnCheckin);
            layout = itemView.findViewById(R.id.layoutGrid);

            btnCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = getAdapterPosition();
                    selectedRoom = roomList.get(selectedPosition);
                    Log.d(TAG, "onClick: " + selectedPosition);
                    Intent i = new Intent(context, CheckInActivity.class);
                    i.putExtra("room_id", selectedRoom.getId());
                    i.putExtra("room_name", selectedRoom.getName());
                    context.startActivity(i);

                }
            });
        }
    }



}
