package com.sososeen09.multitype.adapter.provider;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.Mapper;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于映射JavaBean和对应的ItemHolderProvider
 * 同一个JavaBean可以映射多个ItemHolderProvider
 */
public class ItemProviderFactory {
    //存放管理的class
    List<Class<?>> classList = new ArrayList<>(10);
    List<ItemHolderProviderSet> mProviderSets = new ArrayList<>(10);
    //所有的已经注册的ItemHolderProvider，按照注册的顺序来排序
    List<AbsItemProvider> mCachedItemViewHolderProvider = new ArrayList<>(10);

    public <T> void register(
            @NonNull Class<T> clazz,
            @NonNull AbsItemProvider<T, ?> binder) {
        //noinspection unchecked
        registerOneToMany(clazz, ItemHolderProviderSet.wrap(binder), null);
    }

    public <T> void registerOneToMany(
            @NonNull Class<T> clazz,
            @NonNull ItemHolderProviderSet<T, ?> binder, Mapper<T> mapper) {
        binder.setMapper(mapper);
        classList.add(clazz);
        mProviderSets.add(binder);
        mCachedItemViewHolderProvider.addAll(binder);
    }

    /**
     * 注意一点就是，所管理的type不能重复，一个type只能对应一个ItemHolderProvider
     *
     * @param item
     * @return
     */
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public int findMappedType(Object item) {
        int clazzIndex = classList.indexOf(item.getClass());
        return clazzIndex + mProviderSets.get(clazzIndex).getIndex(item);
    }

    /**
     * 实际上这个类型对应的就是索引
     *
     * @param viewType
     * @return
     */
    public AbsItemProvider<?, ?> findProviderByType(int viewType) {
        AbsItemProvider absItemProvider = mCachedItemViewHolderProvider.get(viewType);
        if (absItemProvider == null) {
            throw new IllegalStateException(String.format("the viewType : %s corresponding to " +
                    "AbsItemProvider must not be null", viewType));
        }
        return absItemProvider;
    }
}
