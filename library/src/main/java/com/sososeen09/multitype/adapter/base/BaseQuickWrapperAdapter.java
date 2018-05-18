package com.sososeen09.multitype.adapter.base;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.OneToManyFlow;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public class BaseQuickWrapperAdapter extends HeaderFooterWrapperAdapter implements OnClickAdapterContract {

    public static BaseQuickWrapperAdapter newInstance() {
        BaseMultiAdapter baseMultiAdapter = new BaseMultiAdapter(new ArrayList<>());
        return new BaseQuickWrapperAdapter(baseMultiAdapter);
    }


    private BaseMultiAdapter mBaseMultiAdapter;

    private BaseQuickWrapperAdapter(BaseMultiAdapter baseMultiAdapter) {
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
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
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
     * @see #register(Class, ItemViewBinder)
     */
    @CheckResult
    public @NonNull
    <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        return mBaseMultiAdapter.register(clazz);
    }

    public void setNewData(List<?> items) {
        mBaseMultiAdapter.setItems(items);
        mBaseMultiAdapter.notifyDataSetChanged();
    }

    public List<?> getItems() {
        return mBaseMultiAdapter.getItems();
    }
}
