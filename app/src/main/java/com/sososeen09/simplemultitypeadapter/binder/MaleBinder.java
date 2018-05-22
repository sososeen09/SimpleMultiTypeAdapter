package com.sososeen09.simplemultitypeadapter.binder;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;

/**
 * @author sososeen09
 */

public class MaleBinder extends BaseItemViewBinder<UserInfo, BaseMultiViewHolder> {

    public MaleBinder() {
        super(R.layout.item_male);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull UserInfo item) {
        holder.setText(R.id.tv, String.format("name: %s, sex: male", item.name));
    }
}
