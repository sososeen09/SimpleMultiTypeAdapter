package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public class HeaderFooterViewBinder<T> extends ItemViewBinder<T, RecyclerView.ViewHolder> {

    private final View mView;

    public HeaderFooterViewBinder(View view) {
        this.mView = view;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new RecyclerView.ViewHolder(mView){};
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Object item) {

    }
}
