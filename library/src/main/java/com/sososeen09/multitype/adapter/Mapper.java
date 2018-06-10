package com.sososeen09.multitype.adapter;

import com.sososeen09.multitype.adapter.provider.AbsItemProvider;

/**
 * @author sososeen09
 */
public interface Mapper<T> {
    Class<? extends AbsItemProvider<T, ?>> map(T item);
}
