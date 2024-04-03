package com.java.hostel_management;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RoomRVAdapter extends RecyclerView.Adapter<RoomRVAdapter.ViewHolder>{
    public ArrayList<ModelRoom> roomList;
    private Context context;

    public static NumberFormat VNDformater = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static NumberFormat USformater = NumberFormat.getCurrencyInstance(Locale.US);
    public static int selectedRoomId, selectedPosition;
    public static ModelRoom selectedRoom;

    public RoomRVAdapter(ArrayList<ModelRoom> l, Context c){
        this.roomList = l;
        this.context = c;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // we can use curency of CND or $
        VNDformater.setMaximumFractionDigits(0);
        //USformater.setMaximumFractionDigits(0);

        ModelRoom room = roomList.get(position);
        holder.roomName.setText( room.getName());
        holder.roomType.setText("Type: " + room.getType());
        if(room.getStatus()==0){
            holder.roomStatus.setText("Available");
            holder.roomStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else{
            holder.roomStatus.setText("Unavailable");
            holder.roomStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.roomPrice.setText(String.valueOf(USformater.format(room.getPrice())) + "/night");

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
    private static final String TAG = "NoteRVAdapter";
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView roomName, roomType, roomStatus, roomPrice;
        private Button btnTest;
        private int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.txtShowName);
            roomStatus = itemView.findViewById(R.id.txtShowStatus);
            roomType = itemView.findViewById(R.id.txtShowType);
            roomPrice = itemView.findViewById(R.id.txtShowPrice);
            //btnTest = itemView.findViewById(R.id.buttonTest);
            // add long click listener
            itemView.setOnLongClickListener(this);

        }
        @Override
        public boolean onLongClick(View v) {

            selectedPosition = getAdapterPosition();
            selectedRoom = roomList.get(selectedPosition);
            selectedRoomId = selectedRoom.getId();
            Log.d(TAG, "onLongClick: " + selectedRoomId);
            return false;
        }

    }
}
