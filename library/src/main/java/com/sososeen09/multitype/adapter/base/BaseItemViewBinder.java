package com.sososeen09.multitype.adapter.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author sososeen09
 */

public abstract class BaseItemViewBinder<T, VH extends BaseMultiViewHolder> extends ItemViewBinder<T, VH> {

    private @LayoutRes
    int layoutId;

    public BaseItemViewBinder(@LayoutRes int layout) {
        this.layoutId = layout;
    }

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        VH baseMultiViewHolder;
        View view = inflater.inflate(layoutId, parent, false);
        baseMultiViewHolder = onCreateDefViewHolder(view);
        onViewHolderCreated(baseMultiViewHolder);
        bindViewClickListener(baseMultiViewHolder);
        return baseMultiViewHolder;
    }

    /**
     * Called immediately after {@link #onCreateViewHolder(LayoutInflater, ViewGroup)}}has returned.
     * This gives subclasses a chance to initialize themselves once
     * they know their view has been completely created. In this method ,The subclasses can set Listener only once
     *
     * @param baseMultiViewHolder The ViewHolder returned by {@link #onCreateViewHolder(LayoutInflater, ViewGroup)}.
     */
    protected void onViewHolderCreated(@NonNull VH baseMultiViewHolder) {

    }


    protected VH onCreateDefViewHolder(View view) {
        return createBaseViewHolder(view);
    }

    /**
     * if you want to use subclass of BaseMultiViewHolder in the adapter,
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
            vh = (VH) new BaseMultiViewHolder(view);
        } else {
            vh = createGenericKInstance(z, view);
        }
        return vh != null ? vh : (VH) new BaseMultiViewHolder(view);
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
                    if (BaseMultiViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseMultiViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
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

    private void bindViewClickListener(BaseMultiViewHolder baseMultiViewHolder) {
        MultiTypeAdapter adapter = getAdapter();
        if (adapter instanceof OnClickAdapterContract) {
            ((OnClickAdapterContract) adapter).bindViewClickListener(baseMultiViewHolder);
            baseMultiViewHolder.setAdapter(((OnClickAdapterContract) adapter));
        }
    }
}
