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


    public static RecyclerView.ViewHolder createViewHolder(Context context, View itemView) {
        return new DefaultViewHolder(context, itemView);
    }

    public static RecyclerView.ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        return new DefaultViewHolder(context, itemView);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public Context getContext() {
        return mContext;
    }
}
