package com.sososeen09.simplemultitypeadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sososeen09.multitype.adapter.AbstractItemViewBinder;

/**
 * Created on 2018/5/17.
 *
 * @author sososeen09
 */

public final class StringItemViewBinder extends AbstractItemViewBinder<String, StringItemViewBinder.Holder> {

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_multi, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @NonNull String item) {
        holder.tv.setText(item);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}
