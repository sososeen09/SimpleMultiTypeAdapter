package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public abstract class AbstractItemViewBinder<T, VH extends RecyclerView.ViewHolder> extends ItemViewBinder<T, VH> {

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * the{@link ItemViewBinder#onCreateViewHolder(LayoutInflater, ViewGroup)} modifier is protected, but sometimes
     * the method need to called in {@link com.sososeen09.multitype.adapter.base.BaseMultiAdapter}
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
     */
    @Override
    public abstract @NonNull
    VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * the{@link ItemViewBinder#onBindViewHolder(RecyclerView.ViewHolder, Object)}  modifier is protected, but sometimes
     * the method need to called in {@link com.sososeen09.multitype.adapter.base.BaseMultiAdapter}
     * @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull T item);

}
