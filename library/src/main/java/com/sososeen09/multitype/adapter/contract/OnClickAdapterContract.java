package com.sososeen09.multitype.adapter.contract;

import android.support.annotation.Nullable;
import android.view.View;

import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public interface OnClickAdapterContract {

    /**
     * RecyclerView is nonsupport for set item click listener,as {@link android.widget.ListView}，
     * but the function is necessary。so add it。
     * the {@link OnItemClickListener#onItemClick(OnClickAdapterContract, View, int)} method will be called when user
     * click the item if be set
     *
     * @param listener The callback which you want to receive the item click event
     */
    void setOnItemClickListener(@Nullable OnItemClickListener listener);

    /**
     * @return The callback which you want to receive the item click event
     * it may be null if not set.
     */
    @Nullable
    OnItemClickListener getOnItemClickListener();

    /**
     * Register a callback to be invoked when an itemchild in View has
     * been  clicked
     *
     * @param listener The callback that will run
     */
    void setOnItemChildClickListener(@Nullable OnItemChildClickListener listener);

    /**
     * @return The callback to be invoked with an itemchild in this RecyclerView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    OnItemChildClickListener getOnItemChildClickListener();

}
