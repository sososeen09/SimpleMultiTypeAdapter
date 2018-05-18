package com.sososeen09.multitype.adapter.contract;

import android.support.v7.widget.RecyclerView;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public interface ViewBindClickContract {
    /**
     * ViewHolder's itemView may set OnClickListener,the implemention of OnClickAdapterContract should handle it
     *
     * @param viewHolder
     */
    void bindViewClickListener(RecyclerView.ViewHolder viewHolder);

    /**
     * set the OffsetDelegate, it the adapter has header, the data position with ViewHolder position is not equal,
     * the {@link OffsetDelegate} help to find the correct position between the ViewHolder's itemView with it's data
     *
     * @param offsetDelegate
     */
    void setOffsetDelegate(OffsetDelegate offsetDelegate);
}
