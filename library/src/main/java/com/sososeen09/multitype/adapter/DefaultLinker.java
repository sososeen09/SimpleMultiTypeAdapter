package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;

import me.drakeet.multitype.Linker;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

class DefaultLinker<T> implements Linker<T> {
    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}
