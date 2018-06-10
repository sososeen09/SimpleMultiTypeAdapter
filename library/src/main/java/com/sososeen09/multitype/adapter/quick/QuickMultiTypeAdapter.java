package com.sososeen09.multitype.adapter.quick;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.BaseMultiAdapter;
import com.sososeen09.multitype.adapter.Mapper;
import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.provider.ItemHolderProviderSet;
import com.sososeen09.multitype.adapter.provider.ItemProviderFactory;
import com.sososeen09.multitype.adapter.wrapper.HeaderFooterWrapperAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sososeen09
 */

public class QuickMultiTypeAdapter extends HeaderFooterWrapperAdapter implements OnClickAdapterContract, OffsetDelegate {

    public static QuickMultiTypeAdapter newInstance() {
        BaseMultiAdapter baseMultiAdapter = new BaseMultiAdapter(new ArrayList<>());
        return new QuickMultiTypeAdapter(baseMultiAdapter);
    }


    private BaseMultiAdapter mBaseMultiAdapter;

    public QuickMultiTypeAdapter(BaseMultiAdapter baseMultiAdapter) {
        super(baseMultiAdapter);
        this.mBaseMultiAdapter = baseMultiAdapter;
        mBaseMultiAdapter.setOffsetDelegate(this);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mBaseMultiAdapter.setOnItemClickListener(listener);
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return mBaseMultiAdapter.getOnItemClickListener();
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mBaseMultiAdapter.setOnItemChildClickListener(listener);
    }

    @Nullable
    @Override
    public OnItemChildClickListener getOnItemChildClickListener() {
        return mBaseMultiAdapter.getOnItemChildClickListener();
    }

    /**
     * Registers a type class and its item view binder. If you have registered the class,
     * it will override the original binder(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     * <p>
     * Note that the method should not be called after
     * {@link RecyclerView#setAdapter(RecyclerView.Adapter)}, or you have to call the setAdapter
     * again.
     * </p>
     *
     * @param clazz  the class of a item
     * @param binder the item view binder
     * @param <T>    the item data type
     */
    public <T> void register(@NonNull Class<T> clazz, @NonNull AbsItemProvider<T, ?> binder) {
        mBaseMultiAdapter.register(clazz, binder);
    }

    /**
     * Registers a type class to multiple item view binders. If you have registered the
     * class, it will override the original binder(s). Note that the method is non-thread-safe
     * so that you should not use it in concurrent operation.
     * <p>
     * Note that the method should not be called after
     * {@link RecyclerView#setAdapter(RecyclerView.Adapter)}, or you have to call the setAdapter
     * again.
     * </p>
     *
     * @param clazz the class of a item
     * @param <T>   the item data type
     * @return {@link ItemProviderFactory} for setting the binders
     * @see #register(Class, AbsItemProvider)
     */
    public <T> void registerOneToMany(Class<T> clazz, ItemHolderProviderSet<T, ?> binder, Mapper<T> mapper) {
        mBaseMultiAdapter.registerOneToMany(clazz, binder, mapper);
    }

    public void setNewData(List<?> items) {
        mBaseMultiAdapter.setData(items);
        reOpenLoadMore();
        notifyDataSetChanged();
    }


    /**
     * add one new data in to certain location
     *
     * @param position
     */
    @SuppressWarnings("unchecked")
    public void addData(@IntRange(from = 0) int position, @NonNull Object data) {
        List<Object> items = (List<Object>) getItems();
        items.add(data);
        notifyItemInserted(position + getHeaderLayoutCount());
    }

    /**
     * add one new data
     */
    @SuppressWarnings("unchecked")
    public void addData(@NonNull Object data) {
        List<Object> items = (List<Object>) getItems();
        items.add(data);
        notifyItemInserted(items.size() + getHeaderLayoutCount());
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    @SuppressWarnings("unchecked")
    public void remove(@IntRange(from = 0) int position) {
        List<Object> items = (List<Object>) getItems();
        items.remove(position);
        int internalPosition = position + getHeaderLayoutCount();
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, items.size() - internalPosition);
    }

    /**
     * change data
     */
    @SuppressWarnings("unchecked")
    public void setData(@IntRange(from = 0) int index, @NonNull Object data) {
        List<Object> items = (List<Object>) getItems();
        items.set(index, data);
        notifyItemChanged(index + getHeaderLayoutCount());
    }

    /**
     * add new data in to certain location
     *
     * @param position the insert position
     * @param newData  the new data collection
     */
    @SuppressWarnings("unchecked")
    public void addData(@IntRange(from = 0) int position, @NonNull Collection<?> newData) {
        List<Object> items = (List<Object>) getItems();
        items.addAll(position, newData);
        notifyItemRangeInserted(position + getHeaderLayoutCount(), newData.size());
    }

    /**
     * add new data to the end of mData
     *
     * @param newData the new data collection
     */
    @SuppressWarnings("unchecked")
    public void addData(@NonNull Collection<?> newData) {
        List<Object> items = (List<Object>) getItems();
        items.addAll(newData);
        notifyItemRangeInserted(items.size() - newData.size() + getHeaderLayoutCount(), newData.size());
    }

    /**
     * use data to replace all item in mData. this method is different {@link #setNewData(List)},
     * it doesn't change the mData reference
     *
     * @param data data collection
     */
    @SuppressWarnings("unchecked")
    public void replaceData(@NonNull Collection<?> data) {
        // 不是同一个引用才清空列表
        List<Object> items = (List<Object>) getItems();
        if (data != items) {
            items.clear();
            items.addAll(data);
        }
        notifyDataSetChanged();
    }

    public List<?> getItems() {
        return mBaseMultiAdapter.getData();
    }


    @Override
    public void setWrapperdAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof BaseMultiAdapter) {
            ((BaseMultiAdapter) adapter).setOffsetDelegate(this);
            mBaseMultiAdapter = (BaseMultiAdapter) adapter;
        }
        reOpenLoadMore();
        super.setWrapperdAdapter(adapter);
    }

    @Override
    public int getOffsetPosition() {
        return getHeaderLayoutCount();
    }
}
