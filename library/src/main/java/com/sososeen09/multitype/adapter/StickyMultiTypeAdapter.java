package com.sososeen09.multitype.adapter;

import android.view.View;

import com.sososeen09.multitype.adapter.base.BaseMultiAdapter;
import com.sososeen09.multitype.adapter.base.QuickMultiTypeAdapter;

import java.util.ArrayList;

/**
 * @author sososeen09
 */
public class StickyMultiTypeAdapter extends QuickMultiTypeAdapter {
    private StickyMultiTypeAdapter(BaseMultiAdapter baseMultiAdapter) {
        super(baseMultiAdapter);
    }


    public static StickyMultiTypeAdapter newInstance() {
        BaseMultiAdapter baseMultiAdapter = new BaseMultiAdapter(new ArrayList<>());
        return new StickyMultiTypeAdapter(baseMultiAdapter);
    }


    public void registerStickyView(View view) {

    }

}
