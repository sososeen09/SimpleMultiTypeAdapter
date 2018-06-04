package com.sososeen09.multitype.adapter.loadmore;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by BlingBling on 2016/11/11.
 */

public abstract class LoadMoreView {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> views = new SparseArray<>();

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(View itemView) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(itemView, true);
                visibleLoadFail(itemView, false);
                visibleLoadEnd(itemView, false);
                break;
            case STATUS_FAIL:
                visibleLoading(itemView, false);
                visibleLoadFail(itemView, true);
                visibleLoadEnd(itemView, false);
                break;
            case STATUS_END:
                visibleLoading(itemView, false);
                visibleLoadFail(itemView, false);
                visibleLoadEnd(itemView, true);
                break;
            case STATUS_DEFAULT:
                visibleLoading(itemView, false);
                visibleLoadFail(itemView, false);
                visibleLoadEnd(itemView, false);
                break;

            default:
                break;
        }
    }

    private void visibleLoading(View itemView, boolean visible) {
        getView(itemView, getLoadingViewId()).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadFail(View itemView, boolean visible) {
        getView(itemView, getLoadFailViewId()).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadEnd(View itemView, boolean visible) {
        final int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            getView(itemView, loadEndViewId).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }


    @SuppressWarnings("unchecked")
    private <T extends View> T getView(View itemView, @IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return mLoadMoreEndGone;
    }

    /**
     * load more layout
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * loading view
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadingViewId();

    /**
     * load fail view
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadFailViewId();

    /**
     * load end view, you can return 0
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadEndViewId();
}
