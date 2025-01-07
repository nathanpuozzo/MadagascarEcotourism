package com.biologiemarine.madagascarecotourism.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.Models.ContactPOJO;
import com.biologiemarine.madagascarecotourism.OnRecyclerClickListener;
import com.biologiemarine.madagascarecotourism.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private OnRecyclerClickListener onRecyclerClickListener;
    private final List<ContactPOJO> list;
    Context context;


    public MyAdapter(List <ContactPOJO> list){


        this.list = list;


    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_layout,viewGroup,false);
        ViewHolder vh = new ViewHolder( view, onRecyclerClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int position) {
        ContactPOJO ld = list.get(position);
        viewHolder.NomView.setText(ld.getNom());
        viewHolder.LanguesView.setText(ld.getLangues());
        viewHolder.ZonesView.setText(ld.getZones());
        Picasso.get().load(ld.getImage()).transform( new CircleTransform() ).into( viewHolder.imageView );


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener listener){
        this.onRecyclerClickListener=listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecyclerClickListener mListener;
        View mView;
        public TextView NomView,LanguesView,ZonesView;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView, OnRecyclerClickListener listener) {
            super( itemView );
            mView = itemView;
            this.mListener = listener;

            //Views
            NomView = mView.findViewById( R.id.NomGuide );
            LanguesView = mView.findViewById( R.id.LanguesGuide );
            ZonesView = mView.findViewById( R.id.ZonesGuide );
            imageView = mView.findViewById( R.id.ImageGuide );

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

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}

