package com.sososeen09.multitype.adapter.contract;

import android.view.View;

/**
 * @author sososeen09
 */
public interface ViewHolderContract extends OffsetDelegateContract {
    /**
     * return the itemView the ViewHolder held
     *
     * @return itemView
     */
    View getItemView();

    /**
     *  return the ViewHolder position in adapter
     * @return position
     */
    int getLayoutPosition();
}
