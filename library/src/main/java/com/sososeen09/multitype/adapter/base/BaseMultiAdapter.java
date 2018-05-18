package com.sososeen09.multitype.adapter.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.TypePool;

/**
 * @author sososeen09
 */

public class BaseMultiAdapter extends MultiTypeAdapter implements OnClickAdapterContract {
    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;

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

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an item in this RecyclerView has
     * been clicked and held, or null id no callback as been set.
     */
    @Override
    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * Register a callback to be invoked when an itemchild in View has
     * been  clicked
     *
     * @param listener The callback that will run
     */
    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an itemchild in this RecyclerView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    @Override
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    @Override
    public void bindViewClickListener(RecyclerView.ViewHolder viewHolder) {

    }

    /**
     * override this method if you want to override click event logic
     *
     * @param v
     * @param position
     */
    protected void setOnItemClick(View v, int position) {
        getOnItemClickListener().onItemClick(BaseMultiAdapter.this, v, position);
    }

    public void bindViewClickListener(final RecyclerView.ViewHolder baseViewHolder, final int position) {
        if (baseViewHolder == null) {
            return;
        }
        final View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClick(v, position);
                }
            });
        }
    }


}
