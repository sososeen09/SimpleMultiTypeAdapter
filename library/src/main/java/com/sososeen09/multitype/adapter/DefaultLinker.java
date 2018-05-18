package com.sososeen09.multitype.adapter;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

import android.support.annotation.NonNull;

/**
 * @author drakeet
 */
final class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}
