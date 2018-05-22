package com.sososeen09.multitype.adapter.stick;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author sososeen09
 */
public class StickLayoutManager extends LinearLayoutManager {

    public static final String TAG = "StickLayoutManager";

    public StickLayoutManager(Context context) {
        super(context);
    }

    public StickLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public StickLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        Log.d(TAG, "is pre layout:" + state.isPreLayout());
        //TODO StickyLayout
    }
}
