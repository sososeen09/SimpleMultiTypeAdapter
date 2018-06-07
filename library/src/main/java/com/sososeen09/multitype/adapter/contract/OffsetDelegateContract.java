package com.sososeen09.multitype.adapter.contract;

/**
 * @author sososeen09
 */
public interface OffsetDelegateContract {
    /**
     * set the OffsetDelegate, it the adapter has header, the data position with ViewHolder position is not equal,
     * the {@link OffsetDelegate} help to find the correct position between the ViewHolder's itemView with it's data
     *
     * @param offsetDelegate
     */
    void setOffsetDelegate(OffsetDelegate offsetDelegate);
}
