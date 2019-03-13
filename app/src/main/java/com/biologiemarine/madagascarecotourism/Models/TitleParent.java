package com.biologiemarine.madagascarecotourism.Models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;


import java.util.List;

public class TitleParent extends ExpandableGroup<TitleChild> {
    public TitleParent(String title,List<TitleChild> items) {

        super(title, items);


    }


}
