package com.sososeen09.multitype.adapter.listener;

import android.view.View;

import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;

/**
 * Interface definition for a callback to be invoked when an itemchild in this
 * view has been clicked
 */
public interface OnItemChildClickListener {
    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param adapter  The adapter
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    void onItemChildClick(OnClickAdapterContract adapter, View view, int position);
}
