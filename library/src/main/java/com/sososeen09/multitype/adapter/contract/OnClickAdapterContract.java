package com.sososeen09.multitype.adapter.contract;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public interface OnClickAdapterContract {

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    void setOnItemClickListener(@Nullable OnItemClickListener listener);

    /**
     * @return The callback to be invoked with an item in this RecyclerView has
     * been clicked and held, or null id no callback as been set.
     */
    OnItemClickListener getOnItemClickListener();

    /**
     * Register a callback to be invoked when an itemchild in View has
     * been  clicked
     *
     * @param listener The callback that will run
     */
    void setOnItemChildClickListener(OnItemChildClickListener listener);

    /**
     * @return The callback to be invoked with an itemchild in this RecyclerView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    OnItemChildClickListener getOnItemChildClickListener();

    /**
     * ViewHolder's itemView may set OnClickListener,the implemention of OnClickAdapterContract should handle it
     * @param viewHolder
     */
    void bindViewClickListener(RecyclerView.ViewHolder viewHolder);
}
