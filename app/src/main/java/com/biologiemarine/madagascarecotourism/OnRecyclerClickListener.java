package com.biologiemarine.madagascarecotourism;

import android.view.View;

public interface OnRecyclerClickListener {
    /**
     * Called when any item with in recyclerview or any item with in item
     * clicked
     *
     * @param position
     *            The position of the item
     * @param view
     *            The id of the view which is clicked with in the item or
     *            -1 if the item itself clicked
     */
    public void onClick(View view, int position, boolean isLongClick);
}
