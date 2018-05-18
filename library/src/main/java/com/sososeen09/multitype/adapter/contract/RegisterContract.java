package com.sososeen09.multitype.adapter.contract;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.ItemViewBinder;
import com.sososeen09.multitype.adapter.OneToManyFlow;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public interface RegisterContract {
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
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder);

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
    <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz);


}
