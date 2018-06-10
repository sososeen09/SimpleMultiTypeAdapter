package com.sososeen09.multitype.adapter.provider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososeen09.multitype.adapter.BaseMultiAdapter;

/**
 * 用于提供ViewHolder的类，每一个ItemHolderProvider都对应唯一的一个JavaBean对象，但是同一个JavaBean对象可以对应多个ItemHolderProvider
 *
 * @param <T>  JavaBean
 * @param <VH>
 * @author sososeen09
 */
public abstract class AbsItemProvider<T, VH extends RecyclerView.ViewHolder> {

    private RecyclerView.Adapter mAdapter;

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * the{@link AbsItemProvider#onCreateViewHolder(LayoutInflater, ViewGroup)} modifier is protected, but sometimes
     * the method need to called in {@link BaseMultiAdapter}
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
     */
    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 当ViewHolder创建好的时候，回调该方法，用于对ViewHolder做一些初始化，类比{@link android.app.Fragment#onViewCreated(View, Bundle)}
     *
     * @param viewHolder
     */
    public abstract void onViewHolderCreated(@NonNull VH viewHolder);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * the{@link AbsItemProvider#onBindViewHolder(RecyclerView.ViewHolder, Object)}  modifier is protected, but sometimes
     * the method need to called in {@link BaseMultiAdapter}
     *
     * @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);


    /**
     * 给Binder设置Adapter
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }
}
