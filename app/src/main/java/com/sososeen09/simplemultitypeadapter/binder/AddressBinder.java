package com.sososeen09.simplemultitypeadapter.binder;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.quick.QuickItemProvider;
import com.sososeen09.multitype.adapter.quick.QuickViewHolder;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.bean.Address;

/**
 * @author sososeen09
 */
public class AddressBinder extends QuickItemProvider<Address,QuickViewHolder> {
    public AddressBinder() {
        super(R.layout.item_address);
    }

    @Override
    public void onViewHolderCreated(@NonNull QuickViewHolder quickViewHolder) {
        super.onViewHolderCreated(quickViewHolder);
        quickViewHolder.addOnClickListener(R.id.tv_street)
                .addOnClickListener(R.id.tv_country)
                .addOnClickListener(R.id.tv_city);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, @NonNull Address item) {
        holder.setText(R.id.tv_street, item.getStreet());
        holder.setText(R.id.tv_city, item.getCity());
        holder.setText(R.id.tv_country, item.getCountry());
    }
}
