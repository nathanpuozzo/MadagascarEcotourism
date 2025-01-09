package com.biologiemarine.madagascarecotourism.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.Models.AirePOJO;
import com.biologiemarine.madagascarecotourism.OnRecyclerClickListener;
import com.biologiemarine.madagascarecotourism.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AireAdapter extends RecyclerView.Adapter<AireAdapter.AireViewHolder>{

    private OnRecyclerClickListener onRecyclerClickListener;
    private final List<AirePOJO> list;
    Context context;


    public AireAdapter(List <AirePOJO> list){

        this.list = list;
    }

    @NonNull
    @Override
    public AireAdapter.AireViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.area_image_layout,viewGroup,false);
        AireAdapter.AireViewHolder vh = new AireAdapter.AireViewHolder( view, onRecyclerClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AireAdapter.AireViewHolder viewHolder, int position) {
        AirePOJO ld = list.get(position);
        viewHolder.NomView.setText(ld.getSHORT_NAME());
        viewHolder.ProvinceView.setText(ld.getPROVINCE());
        viewHolder.TypeView.setText(ld.getSTATUT_JUR());
        Picasso.get().load(ld.getImage()).fit().centerCrop().into( viewHolder.imageView );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener listener){
        this.onRecyclerClickListener=listener;
    }

    public class AireViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecyclerClickListener mListener;
        View mView;
        public TextView NomView,ProvinceView,TypeView;
        public ImageView imageView;


        public AireViewHolder(@NonNull View itemView, OnRecyclerClickListener listener) {
            super( itemView );
            mView = itemView;
            this.mListener = listener;

            //Views
            NomView = mView.findViewById( R.id.areaName );
            ProvinceView = mView.findViewById( R.id.areaProvince );
            TypeView = mView.findViewById( R.id.areaType );
            imageView = mView.findViewById( R.id.areaImage );

            //Click on Item
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View view) {

            if(mListener != null){
                mListener.onClick( view,getAdapterPosition() );

            }

        }
    }
}
