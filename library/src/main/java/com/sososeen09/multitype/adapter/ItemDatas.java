package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * simple Collection class
 *
 * @author sososeen09
 */
public class ItemDatas extends ArrayList<Object> {

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ItemDatas() {
        super();
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ItemDatas(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ItemDatas(@NonNull Collection<?> c) {
        super(c);
    }
}
