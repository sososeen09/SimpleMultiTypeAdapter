package com.sososeen09.multitype.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author sososeen09
 */
public class DefaultViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private Context mContext;

    public DefaultViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
    }


    public static RecyclerView.ViewHolder createViewHolder(View itemView) {
        return new DefaultViewHolder(itemView.getContext(), itemView);
    }

    public static RecyclerView.ViewHolder createViewHolder(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent,
                false);
        return new DefaultViewHolder(parent.getContext(), itemView);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public Context getContext() {
        return mContext;
    }
}
