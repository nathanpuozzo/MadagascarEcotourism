package com.biologiemarine.madagascarecotourism.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.biologiemarine.madagascarecotourism.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class MyChildViewHolder extends ChildViewHolder {

    public TextView option1,option2;

    public MyChildViewHolder (View itemView){
        super (itemView);
        option1 = itemView.findViewById(R.id.option1);
        option2 = itemView.findViewById(R.id.option2);
    }

    public void onBind(String Sousdoc) {
        option1.setText(Sousdoc);
        option2.setText(Sousdoc);
    }


}
