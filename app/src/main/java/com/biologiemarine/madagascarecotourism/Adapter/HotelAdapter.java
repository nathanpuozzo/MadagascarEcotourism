package com.biologiemarine.madagascarecotourism.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.Models.HotelsPOJO;
import com.biologiemarine.madagascarecotourism.OnRecyclerClickListener;
import com.biologiemarine.madagascarecotourism.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder>{

    private OnRecyclerClickListener onRecyclerClickListener;
    private final List<HotelsPOJO> HotelList;

    public HotelAdapter(List<HotelsPOJO> hotelList){
        this.HotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_list_layout,viewGroup,false);
        HotelAdapter.HotelViewHolder vh = new HotelAdapter.HotelViewHolder( view, onRecyclerClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder hotelViewHolder, int position) {
        HotelsPOJO hotelsPOJO = HotelList.get( position );
        hotelViewHolder.NomHotelView.setText( hotelsPOJO.getHotel() );
        hotelViewHolder.NoteHotelView.setText(hotelsPOJO.getIpe() );
        hotelViewHolder.PrixHotelView.setText( hotelsPOJO.getPrix() );
        Picasso.get().load( hotelsPOJO.getImage() ).fit().centerCrop().into( hotelViewHolder.imageHotelView );
    }

    @Override
    public int getItemCount() {
        return HotelList.size();
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener listener){
        this.onRecyclerClickListener=listener;
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnRecyclerClickListener mListener;
        View mView;
        public TextView NomHotelView,PrixHotelView,NoteHotelView;
        public ImageView imageHotelView;


        public HotelViewHolder(@NonNull View itemView, OnRecyclerClickListener listener) {
            super( itemView );
            mView = itemView;
            this.mListener = listener;

            //Views
            NomHotelView = mView.findViewById( R.id.HotelName );
            PrixHotelView = mView.findViewById( R.id.HotelPrix );
            NoteHotelView = mView.findViewById( R.id.HotelScore );
            imageHotelView = mView.findViewById( R.id.HotelPhoto );

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
