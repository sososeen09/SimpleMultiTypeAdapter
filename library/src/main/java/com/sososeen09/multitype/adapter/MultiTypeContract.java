package com.sososeen09.multitype.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created on 2018/5/17.
 *
 * @author sososeen09
 */

public interface MultiTypeContract {

    <T> void register(Class<T> tClass, ItemViewBinder<T, ?> itemViewBinder);

    RecyclerView.Adapter getAdapter();


    void setNewData(List<?> list);

    void addData(List<?> list);

    void setOnItemClickListener(OnItemClickListener onItemClickListener);

    void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener);

    <T> void register(Class<? extends T> clazz, ItemViewBinder<T, ?> binder, Linker<T> linker);


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
        void onItemChildClick(RecyclerView.Adapter adapter, View view, int position);
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView itemView has been clicked.
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
        void onItemClick(RecyclerView.Adapter adapter, View view, int position);
    }

}
