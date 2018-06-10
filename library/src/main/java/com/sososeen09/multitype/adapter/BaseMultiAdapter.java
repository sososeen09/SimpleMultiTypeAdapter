package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.contract.ViewBindClickContract;
import com.sososeen09.multitype.adapter.contract.ViewHolderContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.provider.ItemHolderProviderSet;

import java.util.ArrayList;
import java.util.List;

/**
 * the BaseMultiAdapter is used to enhance the ability for{@link SimpleMultiTypeAdapter}
 * add {@link OnItemClickListener} and {@link OnItemChildClickListener}
 *
 * @author sososeen09
 */
public class BaseMultiAdapter extends SimpleMultiTypeAdapter implements OnClickAdapterContract, ViewBindClickContract {
    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OffsetDelegate mOffsetDelegate;

    public BaseMultiAdapter() {
        this(new ArrayList<>());
    }

    public BaseMultiAdapter(@NonNull List<?> items) {
        super(items);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    @Nullable
    @Override
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    @Override
    public void bindViewClickListener(final ViewHolderContract baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.getItemView();
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClick(v, baseViewHolder.getLayoutPosition() - (mOffsetDelegate == null ? 0 : mOffsetDelegate.getOffsetPosition()));
                }
            });
        }

        baseViewHolder.setOffsetDelegate(mOffsetDelegate);
    }

    /**
     * override this method if you want to override click event logic
     *
     * @param v
     * @param position
     */
    protected void setOnItemClick(View v, int position) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(BaseMultiAdapter.this, v, position);
        }
    }

    @Override
    public void setOffsetDelegate(@NonNull OffsetDelegate offsetDelegate) {
        this.mOffsetDelegate = offsetDelegate;
    }

    public <T> void register(Class<T> clazz, AbsItemProvider<T, ?> binder) {
        getItemProviderFactory().register(clazz, binder);
    }

    public <T> void registerOneToMany(Class<T> clazz, ItemHolderProviderSet<T, ?> binder, Mapper<T> mapper) {
        getItemProviderFactory().registerOneToMany(clazz, binder, mapper);
    }
}
