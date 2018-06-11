package com.sososeen09.multitype.adapter;

import com.sososeen09.multitype.adapter.provider.AbsItemProvider;

/**
 * use the {@link Mapper} to find the corresponding Class of {@link AbsItemProvider} with JavaBean Object
 *
 * @author sososeen09
 */
public interface Mapper<T> {
    Class<? extends AbsItemProvider<T, ?>> map(T item);
}
