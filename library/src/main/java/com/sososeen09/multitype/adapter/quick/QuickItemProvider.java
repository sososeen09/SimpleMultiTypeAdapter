package com.sososeen09.multitype.adapter.quick;

import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososeen09.multitype.adapter.BaseMultiAdapter;
import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.quick.QuickViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author sososeen09
 */

public abstract class QuickItemProvider<T, VH extends QuickViewHolder> extends AbsItemProvider<T, VH> {

    private View mItemView;
    private @LayoutRes
    int layoutId;

    public QuickItemProvider(@LayoutRes int layout) {
        this.layoutId = layout;
    }

    public QuickItemProvider(@NonNull View itemView){
        this.mItemView = itemView;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        VH baseMultiViewHolder;
        View view = getItemView(inflater, parent);
        baseMultiViewHolder = onCreateDefViewHolder(view);
        onViewHolderCreated(baseMultiViewHolder);
        return baseMultiViewHolder;
    }

    private View getItemView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        if (mItemView != null) {
            return mItemView;

        }
        return inflater.inflate(layoutId, parent, false);
    }

    /**
     * Called immediately after {@link #onCreateViewHolder(LayoutInflater, ViewGroup)}}has returned.
     * This gives subclasses a chance to initialize themselves once
     * they know their view has been completely created. In this method ,The subclasses can set Listener only once
     *
     * @param baseMultiViewHolder The ViewHolder returned by {@link #onCreateViewHolder(LayoutInflater, ViewGroup)}.
     */
    @Override
    @CallSuper
    public void onViewHolderCreated(@NonNull VH baseMultiViewHolder) {
        bindViewClickListener(baseMultiViewHolder);
    }


    protected VH onCreateDefViewHolder(View view) {
        return createBaseViewHolder(view);
    }

    /**
     * if you want to use subclass of QuickViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    @SuppressWarnings("unchecked")
    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH vh;
        // the z may be null as the generic type erasure
        if (z == null) {
            vh = (VH) new QuickViewHolder(view);
        } else {
            vh = createGenericKInstance(z, view);
        }
        return vh != null ? vh : (VH) new QuickViewHolder(view);
    }

    /**
     * get generic parameter VH
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (QuickViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && QuickViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * try to create Generic VH instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void bindViewClickListener(QuickViewHolder quickViewHolder) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter instanceof BaseMultiAdapter) {
            BaseMultiAdapter baseMultiAdapter = (BaseMultiAdapter) adapter;
            baseMultiAdapter.bindViewClickListener(quickViewHolder);
            quickViewHolder.setAdapter(baseMultiAdapter);
        }
    }

}
