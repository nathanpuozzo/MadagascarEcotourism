package com.biologiemarine.madagascarecotourism.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.biologiemarine.madagascarecotourism.Models.TitleChild;
import com.biologiemarine.madagascarecotourism.Models.TitleParent;
import com.biologiemarine.madagascarecotourism.R;
import com.biologiemarine.madagascarecotourism.ViewHolders.MyChildViewHolder;
import com.biologiemarine.madagascarecotourism.ViewHolders.MyParentsViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class MyAdapter extends ExpandableRecyclerViewAdapter<MyParentsViewHolder,MyChildViewHolder> {


    public MyAdapter (List<? extends ExpandableGroup> groups){

        super(groups);

    }

    @Override
    public MyParentsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_parent, parent, false);
        return new MyParentsViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_child, parent, false);
        return new MyChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final TitleChild childItem = (TitleChild) group.getItems().get(childIndex);
        holder.onBind(childItem.getTitle());

    }

    @Override
    public void onBindGroupViewHolder(MyParentsViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setParentTitle(group.getTitle());
    }


}
