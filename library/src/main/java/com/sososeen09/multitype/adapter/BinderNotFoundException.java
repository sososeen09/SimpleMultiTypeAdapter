package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;

/**
 * Created on 2018/5/17.
 *
 * @author sososeen09
 */

public class BinderNotFoundException extends Exception {
    BinderNotFoundException(@NonNull Class<?> clazz) {
        super("Do you have registered {className}.class to the binder in the adapter/pool?"
                .replace("{className}", clazz.getSimpleName()));
    }
}
