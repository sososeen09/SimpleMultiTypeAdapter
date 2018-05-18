package com.sososeen09.multitype.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Linker;
import me.drakeet.multitype.MultiTypePool;
import me.drakeet.multitype.TypePool;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created on 2018/5/17.
 *
 * @author sososeen09
 *
 *         参考 {@link  android.widget.HeaderViewListAdapter}
 */

public class SimpleMultiAdapterWithHeaderFooter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MultiTypeContract {

    public static SimpleMultiAdapterWithHeaderFooter getDefault() {
        return new SimpleMultiAdapterWithHeaderFooter();
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    protected List<?> mData;

    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;

    private @NonNull
    TypePool mTypePool;

    public static final int HEADER_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    private LayoutInflater mLayoutInflater;


    public SimpleMultiAdapterWithHeaderFooter() {
        this(null);
    }

    public SimpleMultiAdapterWithHeaderFooter(List<?> data) {
        mData = data == null ? new ArrayList<>() : data;
        mTypePool = new MultiTypePool();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        AbstractItemViewBinder<?, ?> itemViewBinder = findViewBinder(viewType);
        return itemViewBinder.onCreateViewHolder(mLayoutInflater, parent);
    }

    private AbstractItemViewBinder<?, ?> findViewBinder(int viewType) {
        if (viewType == HEADER_VIEW) {
            return new HeaderFooterViewBinder<>(mHeaderLayout);
        } else if (viewType == FOOTER_VIEW) {
            return new HeaderFooterViewBinder<>(mFooterLayout);
        }
        return (AbstractItemViewBinder<?, ?>) mTypePool.getItemViewBinder(viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        // wet not handle the HEADER_VIEW and FOOTER_VIEW
        switch (viewType) {
            case HEADER_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                Object item = mData.get(position - getHeaderLayoutCount());
                AbstractItemViewBinder binder = (AbstractItemViewBinder) mTypePool.getItemViewBinder(holder.getItemViewType());
                binder.onBindViewHolder(holder, item);

                break;
        }
    }


    @Override
    public int getItemCount() {
        int count;
        count = getHeaderLayoutCount() + mData.size() + getFooterLayoutCount();
        return count;
    }


    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeaderLayoutCount();
        if (position < numHeaders) {
            return HEADER_VIEW;
        } else {
            int adjPosition = position - numHeaders;
            int adapterCount = mData.size();
            if (adjPosition < adapterCount) {
                return getDefItemViewType(adjPosition);
            } else {
                adjPosition = adjPosition - adapterCount;
                int numFooters = getFooterLayoutCount();
                if (adjPosition < numFooters) {
                    return FOOTER_VIEW;
                }
            }
        }

        return getDefItemViewType(position);
    }

    protected int getDefItemViewType(int position) {
        Object item = mData.get(position);
        return indexInTypesOf(position, item);
    }

    private int indexInTypesOf(int position, Object item) {
        int index = mTypePool.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked")
            Linker<Object> linker = (Linker<Object>) mTypePool.getLinker(index);
            return index + linker.index(position, item);
        }

        return super.getItemViewType(position);
    }


    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }


    /**
     * Append header to the rear of the mHeaderLayout.
     *
     * @param header
     */
    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    /**
     * Add header view to mHeaderLayout and set header view position in mHeaderLayout.
     * When index = -1 or index big= child count in mHeaderLayout,
     * the effect of this method is the same as that of {@link #addHeaderView(View)}.
     *
     * @param header
     * @param index  the position in mHeaderLayout of this header.
     *               When index = -1 or index big= child count in mHeaderLayout,
     *               the effect of this method is the same as that of {@link #addHeaderView(View)}.
     */
    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    /**
     * @param header
     * @param index
     * @param orientation
     */
    public int addHeaderView(View header, int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mHeaderLayout.addView(header, index);
        /**
         * if is the first time to insert, need notify
         */
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    /**
     * Append footer to the rear of the mFooterLayout.
     *
     * @param footer
     */
    public int addFooterView(View footer) {
        return addFooterView(footer, -1, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }

    /**
     * Add footer view to mFooterLayout and set footer view position in mFooterLayout.
     * When index = -1 or index big= child count in mFooterLayout,
     * the effect of this method is the same as that of {@link #addFooterView(View)}.
     *
     * @param footer
     * @param index  the position in mFooterLayout of this footer.
     *               When index = -1 or index big= child count in mFooterLayout,
     *               the effect of this method is the same as that of {@link #addFooterView(View)}.
     */
    public int addFooterView(View footer, int index, int orientation) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    private int getHeaderViewPosition() {
        //Return to header view notify position
        return 0;
    }


    private int getFooterViewPosition() {
        //Return to footer view notify position
        return getHeaderLayoutCount() + mData.size();
    }

    /**
     * if addFooterView will be return 1, if not will be return 0
     */
    public int getFooterLayoutCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public void setData(List<?> data) {
        mData = data;
    }

    @Override
    public <T> void register(Class<T> tClass, AbstractItemViewBinder<T, ?> itemViewBinder) {
        mTypePool.register(tClass, itemViewBinder, new DefaultLinker<T>());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List<?> data) {
        this.mData = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void addData(List data) {
        mData.addAll(data);
        notifyItemInserted(mData.size() + getHeaderLayoutCount());
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    @Override
    public <T> void register(Class<? extends T> clazz, AbstractItemViewBinder<T, ?> binder, Linker<T> linker) {

    }
}
