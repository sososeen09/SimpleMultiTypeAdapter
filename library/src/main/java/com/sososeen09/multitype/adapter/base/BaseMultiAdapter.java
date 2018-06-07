package com.sososeen09.multitype.adapter.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.contract.ViewBindClickContract;
import com.sososeen09.multitype.adapter.contract.ViewHolderContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.TypePool;

/**
 * the BaseMultiAdapter is used to enhance the ability for{@link MultiTypeAdapter}
 * add {@link OnItemClickListener} and {@link OnItemChildClickListener}
 *
 * @author sososeen09
 */
public class BaseMultiAdapter extends MultiTypeAdapter implements OnClickAdapterContract, ViewBindClickContract {
    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OffsetDelegate mOffsetDelegate;

    public BaseMultiAdapter() {
    }

    public BaseMultiAdapter(@NonNull List<?> items) {
        super(items);
    }

    public BaseMultiAdapter(@NonNull List<?> items, int initialCapacity) {
        super(items, initialCapacity);
    }

    public BaseMultiAdapter(@NonNull List<?> items, @NonNull TypePool pool) {
        super(items, pool);
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

    public void setData(List<?> items) {
        setItems(items);
    }

    public List<?> getData() {
        return getItems();
    }
}
