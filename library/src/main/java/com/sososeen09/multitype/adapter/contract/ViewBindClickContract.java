package com.sososeen09.multitype.adapter.contract;

/**
 * @author sososeen09
 */
public interface ViewBindClickContract extends OffsetDelegateContract {
    /**
     * ViewHolder's itemView may set OnClickListener,the implemention of OnClickAdapterContract should handle it
     *
     * @param viewHolder
     */
    void bindViewClickListener(ViewHolderContract viewHolder);
}
