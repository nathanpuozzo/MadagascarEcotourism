package com.biologiemarine.madagascarecotourism.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.ContactPOJO;
import com.biologiemarine.madagascarecotourism.OnItemRecyclerClick;
import com.biologiemarine.madagascarecotourism.R;

import java.util.ArrayList;

public class CustomContactAdapter extends RecyclerView.Adapter<CustomContactAdapter.MyViewHolder> {
    // declaring some fields.
    private ArrayList<ContactPOJO> arrayList = new ArrayList<>();
    private OnItemRecyclerClick listener;
    // A constructor.
    public CustomContactAdapter(ArrayList<ContactPOJO> arrayList, OnItemRecyclerClick listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        ContactPOJO contact = arrayList.get(position);
        holder.photo.setImageDrawable(contact.getmPhoto());
        holder.name.setText(contact.getmName());
        holder.location.setText(contact.getmLocation());
        holder.note.setText(contact.getmNote());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             listener.onRecyclerViewItemClicked(position,view.getId());
            }
        });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name, location, note;
        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            photo = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            location = itemView.findViewById(R.id.textView2);
            note = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               listener.onRecyclerViewItemClicked(getAdapterPosition(),view.getId());
                }
            });

        }
    }

}
