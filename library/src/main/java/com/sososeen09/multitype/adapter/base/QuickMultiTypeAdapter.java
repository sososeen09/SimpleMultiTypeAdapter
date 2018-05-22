package com.sososeen09.multitype.adapter.base;

import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.AbstractItemViewBinder;
import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.wrapper.HeaderFooterWrapperAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.drakeet.multitype.OneToManyFlow;

/**
 * @author sososeen09
 */

public class QuickMultiTypeAdapter extends HeaderFooterWrapperAdapter implements OnClickAdapterContract {

    public static QuickMultiTypeAdapter newInstance() {
        BaseMultiAdapter baseMultiAdapter = new BaseMultiAdapter(new ArrayList<>());
        return new QuickMultiTypeAdapter(baseMultiAdapter);
    }


    private BaseMultiAdapter mBaseMultiAdapter;

    private QuickMultiTypeAdapter(BaseMultiAdapter baseMultiAdapter) {
        super(baseMultiAdapter);
        this.mBaseMultiAdapter = baseMultiAdapter;

        OffsetDelegate offsetDelegate = new OffsetDelegate() {
            @Override
            public int getOffsetPosition() {
                return getHeaderLayoutCount();
            }
        };
        mBaseMultiAdapter.setOffsetDelegate(offsetDelegate);
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
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull AbstractItemViewBinder<T, ?> binder) {
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
     * @return {@link OneToManyFlow} for setting the binders
     * @see #register(Class, AbstractItemViewBinder)
     */
    @CheckResult
    public @NonNull
    <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        return mBaseMultiAdapter.register(clazz);
    }

    public void setNewData(List<?> items) {
        mBaseMultiAdapter.setItems(items);

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
        return mBaseMultiAdapter.getItems();
    }
}
