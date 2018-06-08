package com.sososeen09.simplemultitypeadapter.model;

import android.support.annotation.NonNull;

import com.sososeen09.multitype.adapter.ItemDatas;
import com.sososeen09.simplemultitypeadapter.bean.Address;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;

import java.util.Random;

/**
 * @author sososeen09
 */
public class DataProvider {
    @NonNull
    public static ItemDatas getNewData(int num) {
        return getNewData(0, num);
    }

    @NonNull
    public static  ItemDatas getNewData(int from, int num) {
        ItemDatas items = new ItemDatas();

        Random random = new Random();
        for (int i = from; i < from + num; i++) {
            if (random.nextInt(10) % 2 == 0) {
                items.add("this is String, value: " + i);
            } else if (random.nextInt(10) % 3 == 0) {
                UserInfo userInfo = new UserInfo("LiLei" + i, 1);
                items.add(userInfo);
            } else if (random.nextInt(10) % 4 == 0) {
                UserInfo userInfo = new UserInfo("HanMeiMei" + i, 0);
                items.add(userInfo);
            } else if (random.nextInt(10) % 5 == 0) {
                Address address = new Address("NanJing Road " + i, "ShangHai " + i, "China " + i);
                items.add(address);
            } else {
                items.add(i);
            }
        }
        return items;
    }

}
