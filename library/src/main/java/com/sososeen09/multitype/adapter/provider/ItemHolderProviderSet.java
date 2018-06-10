package com.sososeen09.multitype.adapter.provider;

import android.support.v7.widget.RecyclerView;

import com.sososeen09.multitype.adapter.Mapper;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemHolderProviderSet<T, VH extends RecyclerView.ViewHolder> extends ArrayList<AbsItemProvider<T, VH>> {

    private Mapper<T> mapper;

    public static <T, VH extends RecyclerView.ViewHolder> ItemHolderProviderSet<T, VH>
    wrap(AbsItemProvider<T, VH>... provider) {
        return new ItemHolderProviderSet<>(provider);
    }

    ItemHolderProviderSet(AbsItemProvider<T, VH>... provider) {
        this.addAll(Arrays.asList(provider));
    }

    public void setMapper(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    public int getIndex(T item) {
        if (mapper != null) {
            Class<? extends AbsItemProvider<T, ?>> providerClass = mapper.map(item);
            //遍历集合，获取Class对应的ItemHolderProvider的索引
            for (int i = 0; i < this.size(); i++) {
                AbsItemProvider<T, VH> holderProvider = get(i);
                if (holderProvider.getClass() == providerClass) {
                    return i;
                }
            }
            throw new IllegalStateException("can't find the corresponding AbsItemProvider index with: " + item.toString());
        }
        return 0;
    }

}
