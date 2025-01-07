package com.biologiemarine.madagascarecotourism.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class MyParentsViewHolder extends GroupViewHolder {

    public TextView listGroup;

    public MyParentsViewHolder (View itemView){

        super (itemView);
        listGroup = itemView.findViewById(R.id.parentTitle);
    }

    public void setParentTitle(String group){

        listGroup.setText(group);
    }

}
