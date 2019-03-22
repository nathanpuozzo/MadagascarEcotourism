package com.biologiemarine.madagascarecotourism.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.HotelsPOJO;
import com.biologiemarine.madagascarecotourism.OnRecyclerClickListener;
import com.biologiemarine.madagascarecotourism.R;

import java.util.ArrayList;

class MyParentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private OnRecyclerClickListener onRecyclerClickListener;
    ImageView hotel_photo;
    TextView hotel_name, hotel_location, hotel_score;
    private Context context;

    public MyParentsViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.v("ViewHolder","in View Holder");
        hotel_photo = itemView.findViewById(R.id.HotelPhoto);
        hotel_name = itemView.findViewById(R.id.HotelName);
        hotel_location = itemView.findViewById(R.id.HotelLocation);
        hotel_score = itemView.findViewById(R.id.HotelScore);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener){

        this.onRecyclerClickListener = onRecyclerClickListener;
    }

    @Override
    public void onClick(View v) {

        onRecyclerClickListener.onClick(v,getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {

        onRecyclerClickListener.onClick(v,getAdapterPosition());

        return true;
    }
}

public class CustomHotelAdapter extends RecyclerView.Adapter<MyParentsViewHolder> {

    private OnRecyclerClickListener listener;
    private ArrayList<HotelsPOJO> hotelArrayList = new ArrayList<>();
    private Context context;

    public CustomHotelAdapter(ArrayList<HotelsPOJO> arrayList,OnRecyclerClickListener listener){
        this.listener = listener;
        this.hotelArrayList= arrayList;
        this.context=context;
    }


    @Override
    public MyParentsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_layout,parent,false);
        return new MyParentsViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder( MyParentsViewHolder hotelViewHolder,int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        HotelsPOJO contact = hotelArrayList.get(position);
        hotelViewHolder.hotel_photo.setImageDrawable(contact.getHotelPhoto());
        hotelViewHolder.hotel_name.setText(contact.getHotelName());
        hotelViewHolder.hotel_location.setText(contact.getHotelLocation());
        hotelViewHolder.hotel_score.setText(contact.getHotelScore());

        hotelViewHolder.setOnRecyclerClickListener((view, position1) ->
                listener.onClick(view, position1));

    }

    @Override
    public int getItemCount() {
        return hotelArrayList.size();
    }

/*
    public class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView hotel_photo;
        TextView hotel_name, hotel_location, hotel_score;

        public HotelViewHolder(View hotelView){
            super(hotelView);
            Log.v("ViewHolder","in View Holder");
            hotel_photo = hotelView.findViewById(R.id.HotelPhoto);
            hotel_name = hotelView.findViewById(R.id.HotelName);
            hotel_location = hotelView.findViewById(R.id.HotelLocation);
            hotel_score = hotelView.findViewById(R.id.HotelScore);
        }

    }

*/

}
