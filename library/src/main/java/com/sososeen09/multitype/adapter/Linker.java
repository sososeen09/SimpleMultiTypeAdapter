package com.sososeen09.multitype.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.OneToManyEndpoint;

/**
 * An interface to link the items and binders by array integer index.
 *
 * @author drakeet
 */
public interface Linker<T> {

    /**
     * Returns the index of your registered binders for your item. The result should be in range of
     * {@code [0, one-to-multiple-binders.length)}.
     *
     * <p>Note: The argument of {@link me.drakeet.multitype.OneToManyFlow#to(me.drakeet.multitype.ItemViewBinder[])} is the
     * one-to-multiple-binders.</p>
     *
     * @param position The position in items
     * @param t Your item data
     * @return The index of your registered binders
     * @see me.drakeet.multitype.OneToManyFlow#to(ItemViewBinder[])
     * @see OneToManyEndpoint#withLinker(me.drakeet.multitype.Linker)
     */
    @IntRange(from = 0) int index(int position, @NonNull T t);
}
