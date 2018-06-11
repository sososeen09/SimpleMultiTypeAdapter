package com.sososeen09.multitype.adapter.provider;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.Mapper;

import java.util.ArrayList;
import java.util.List;


/**
 * use to store the JavaBean class and the corresponding {@link AbsItemProvider}
 *
 * @author sososeen09
 */
public class ItemProviderFactory {
    /**
     * use to store the JavaBean class witch registered
     */
    List<Class<?>> classList = new ArrayList<>(10);

    /**
     * as one JavaBean class can map more than one {@link AbsItemProvider} object, so use the {@link ItemProviderSet}
     * to store the JavaBean class mapping
     */
    List<ItemProviderSet> mProviderSets = new ArrayList<>(10);

    /**
     * all the registered  {@link AbsItemProvider},the index of it can be the viewType of {@link android.support.v7.widget.RecyclerView.ViewHolder}
     *
     * @see android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)
     */
    List<AbsItemProvider> mRegisteredItemProviderList = new ArrayList<>(10);

    public <T> void register(
            @NonNull Class<T> clazz,
            @NonNull AbsItemProvider<T, ?> binder) {
        //noinspection unchecked
        registerOneToMany(clazz, ItemProviderSet.wrap(binder), null);
    }

    public <T> void registerOneToMany(
            @NonNull Class<T> clazz,
            @NonNull ItemProviderSet<T, ?> binder, Mapper<T> mapper) {
        binder.setMapper(mapper);
        classList.add(clazz);
        mProviderSets.add(binder);
        mRegisteredItemProviderList.addAll(binder);
    }

    /**
     * find the {@link AbsItemProvider} type int the Adapter with JavaBean obj,
     * one {@link AbsItemProvider} can only be the unique viewType
     * the type
     *
     * @param item JavaBean object
     * @return viewType
     */
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public int findMappedType(Object item) {
        int clazzIndex = classList.indexOf(item.getClass());
        return clazzIndex + mProviderSets.get(clazzIndex).getIndex(item);
    }

    /**
     * find the corresponding {@link AbsItemProvider} obj with viewType,
     * the viewType is the index of {@link AbsItemProvider} obj in mRegisteredItemProviderList in fact
     *
     * @param viewType
     * @return
     */
    public AbsItemProvider<?, ?> findProviderByType(int viewType) {
        AbsItemProvider absItemProvider = mRegisteredItemProviderList.get(viewType);
        if (absItemProvider == null) {
            throw new IllegalStateException(String.format("the viewType : %s corresponding to " +
                    "AbsItemProvider must not be null", viewType));
        }
        return absItemProvider;
    }
}
