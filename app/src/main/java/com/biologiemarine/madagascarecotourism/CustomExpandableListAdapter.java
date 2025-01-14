package com.biologiemarine.madagascarecotourism;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> expandableListTitle;
    private final HashMap<String, List<String>> expandableListDetail;
    private final List<Integer> groupImages;
    private final HashMap<Integer,List<Integer>> childImages;
    //TODO: images devant groups

    public CustomExpandableListAdapter(Context context, List <String> expandableListTitle, List <Integer> groupImages, HashMap <String, List <String>> expandableListDetail, HashMap <Integer, List <Integer>> childImages) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.groupImages= groupImages;
        this.childImages = childImages;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        ImageView expandedListImageView = convertView.findViewById( R.id.imgServicesItem);
        final int imageId = childImages.get(listPosition).get( expandedListPosition );
        switch (listPosition){
            case 0 :
                if(expandedListPosition == 0){expandedListImageView.setImageResource(R.drawable.ic_greenpower);}
                else if (expandedListPosition == 1){expandedListImageView.setImageResource(R.drawable.ic_wastetreatment);}
                else if (expandedListPosition == 2){expandedListImageView.setImageResource(R.drawable.ic_community);}
                else if (expandedListPosition == 3){expandedListImageView.setImageResource(R.drawable.ic_salary);}
                break;
            case 1 :
                if(expandedListPosition == 0){expandedListImageView.setImageResource(R.drawable.ic_bookingcom_icon);}
                else if (expandedListPosition == 1){expandedListImageView.setImageResource(R.drawable.ic_tripadvisor_icon);}
                break;
            case 2 :
                if(expandedListPosition == 0){expandedListImageView.setImageResource(R.drawable.ic_location);}
                else if (expandedListPosition == 1){expandedListImageView.setImageResource(R.drawable.ic_call_answer);}
                else if (expandedListPosition == 2){expandedListImageView.setImageResource(R.drawable.ic_email);}
                else if (expandedListPosition == 3){expandedListImageView.setImageResource(R.drawable.ic_www);}
                break;
            default :
                expandedListImageView.setImageResource(com.mapbox.maps.R.drawable.googleg_disabled_color_18);
                break;
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        ImageView imgServicesGroup = convertView.findViewById(R.id.imgServicesGroup);
        int imageId =groupImages.get(listPosition);
        imgServicesGroup.setImageResource(imageId);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


}

