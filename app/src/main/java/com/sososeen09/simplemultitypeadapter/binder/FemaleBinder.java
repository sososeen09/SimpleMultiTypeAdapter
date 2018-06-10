package com.sososeen09.simplemultitypeadapter.binder;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.quick.QuickItemProvider;
import com.sososeen09.multitype.adapter.quick.QuickViewHolder;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;

/**
 * @author sososeen09
 */

public class FemaleBinder extends QuickItemProvider<UserInfo, QuickViewHolder> {

    public FemaleBinder() {
        super(R.layout.item_female);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, @NonNull UserInfo item) {
        holder.setText(R.id.tv, String.format("name: %s, sex: female", item.name));
    }
}
