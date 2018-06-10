package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.provider.ItemProviderFactory;

import java.util.ArrayList;
import java.util.List;

public class SimpleMultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private @NonNull
    List<?> mData;
    private @NonNull
    ItemProviderFactory mItemProviderFactory;

    public SimpleMultiTypeAdapter() {
        this(new ArrayList<>(), new ItemProviderFactory());
    }

    public SimpleMultiTypeAdapter(@NonNull List<?> data) {
        this(data, new ItemProviderFactory());
    }

    public SimpleMultiTypeAdapter(@NonNull ItemProviderFactory itemProviderFactory) {
        this(new ArrayList<>(), itemProviderFactory);
    }

    public SimpleMultiTypeAdapter(@NonNull List<?> data, @NonNull ItemProviderFactory itemProviderFactory) {
        this.mData = data;
        mItemProviderFactory = itemProviderFactory;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mData.get(position);
        return findItemDataType(item);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsItemProvider<?, ?> absItemProvider = mItemProviderFactory.findProviderByType(viewType);
        absItemProvider.setAdapter(this);
        return absItemProvider.onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = mData.get(position);
        //注册的时候已经约束了类型，所以在使用的时候可以不用考虑泛型问题
        AbsItemProvider providerByType = mItemProviderFactory.findProviderByType(holder.getItemViewType());
        providerByType.onBindViewHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItemProviderFactory(@NonNull ItemProviderFactory itemProviderFactory) {
        this.mItemProviderFactory = itemProviderFactory;
    }

    @NonNull
    public ItemProviderFactory getItemProviderFactory() {
        return mItemProviderFactory;
    }

    @NonNull
    public List<?> getData() {
        return mData;
    }

    public void setData(@NonNull List<?> data) {
        mData = data;
    }

    private int findItemDataType(Object item) {
        return mItemProviderFactory.findMappedType(item);
    }
}
