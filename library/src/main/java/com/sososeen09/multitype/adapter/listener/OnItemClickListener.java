package com.sososeen09.multitype.adapter.listener;

import android.view.View;

import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;

/**
 * Interface definition for a callback to be invoked when an item in this
 * RecyclerView itemView has been clicked.
 * @author sososeen09
 */
public interface OnItemClickListener {

    /**
     * Callback method to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param adapter  the adpater
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    void onItemClick(OnClickAdapterContract adapter, View view, int position);
}
