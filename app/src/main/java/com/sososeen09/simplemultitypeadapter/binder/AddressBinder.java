package com.sososeen09.simplemultitypeadapter.binder;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.bean.Address;

/**
 * @author sososeen09
 */
public class AddressBinder extends BaseItemViewBinder<Address,BaseMultiViewHolder> {
    public AddressBinder() {
        super(R.layout.item_address);
    }

    @Override
    protected void onViewHolderCreated(@NonNull BaseMultiViewHolder baseMultiViewHolder) {
        super.onViewHolderCreated(baseMultiViewHolder);
        baseMultiViewHolder.addOnClickListener(R.id.tv_street)
                .addOnClickListener(R.id.tv_country)
                .addOnClickListener(R.id.tv_city);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Address item) {
        holder.setText(R.id.tv_street, item.getStreet());
        holder.setText(R.id.tv_city, item.getCity());
        holder.setText(R.id.tv_country, item.getCountry());
    }
}
