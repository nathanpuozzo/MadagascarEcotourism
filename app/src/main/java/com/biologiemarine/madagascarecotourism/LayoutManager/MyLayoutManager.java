package com.biologiemarine.madagascarecotourism.LayoutManager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class MyLayoutManager extends LinearLayoutManager {

    public MyLayoutManager(Context context) {
        super(context);

        isAutoMeasureEnabled();

    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,
                          int heightSpec) {

        if(!isAutoMeasureEnabled()) {
            View view = recycler.getViewForPosition( 0 );

            if(view != null){
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight();
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        }
    }
}