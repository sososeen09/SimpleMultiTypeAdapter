package com.sososeen09.simplemultitypeadapter.binder;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.UserInfo;

/**
 * @author sososeen09
 */

public class FemaleBinder extends BaseItemViewBinder<UserInfo, BaseMultiViewHolder> {

    public FemaleBinder() {
        super(R.layout.item_female);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull UserInfo item) {
        holder.setText(R.id.tv, String.format("name: %s, sex: female", item.name));
    }
}
