package com.sososeen09.simplemultitypeadapter.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sososeen09.multitype.adapter.ItemDatas;
import com.sososeen09.multitype.adapter.Mapper;
import com.sososeen09.multitype.adapter.quick.QuickItemProvider;
import com.sososeen09.multitype.adapter.quick.QuickViewHolder;
import com.sososeen09.multitype.adapter.quick.QuickMultiTypeAdapter;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.provider.ItemHolderProviderSet;
import com.sososeen09.simplemultitypeadapter.R;
import com.sososeen09.simplemultitypeadapter.bean.Address;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;
import com.sososeen09.simplemultitypeadapter.binder.AddressBinder;
import com.sososeen09.simplemultitypeadapter.binder.FemaleBinder;
import com.sososeen09.simplemultitypeadapter.binder.MaleBinder;

import java.util.Random;

/**
 * @author sososeen09
 */
public class HomeFragment extends Fragment {

    RecyclerView rv;
    private RecyclerView mRvHorzontal;
    private QuickMultiTypeAdapter mQuickMultiTypeAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mQuickMultiTypeAdapter = QuickMultiTypeAdapter.newInstance();

        View headerView = getHeaderView();
        mQuickMultiTypeAdapter.addHeaderView(headerView);

        // one to one
        mQuickMultiTypeAdapter.register(String.class, new QuickItemProvider<String, QuickViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull QuickViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        // one to one
        mQuickMultiTypeAdapter.register(Integer.class, new QuickItemProvider<Integer, QuickViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull QuickViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });

        // one to one
        mQuickMultiTypeAdapter.register(Address.class, new AddressBinder());


        // one to many
        mQuickMultiTypeAdapter.registerOneToMany(UserInfo.class, ItemHolderProviderSet.wrap(new FemaleBinder(), new MaleBinder()), new Mapper<UserInfo>() {
            @Override
            public Class<? extends AbsItemProvider<UserInfo, ?>> map(UserInfo userInfo) {
                return userInfo.sexuality == 1 ? MaleBinder.class : FemaleBinder.class;
            }
        });

        // set item click listener
        mQuickMultiTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(getContext(), "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // set item child click listener
        mQuickMultiTypeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(getContext(), "child view click " + view.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ItemDatas items = getNewData(50);

        mQuickMultiTypeAdapter.setNewData(items);

        rv.setAdapter(mQuickMultiTypeAdapter);

    }

    private View getHeaderView() {
        View view = mInflater.inflate(R.layout.layout_home_page_header, rv, false);
        mRvHorzontal = view.findViewById(R.id.rv_horizontal);
        mRvHorzontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    @NonNull
    private ItemDatas getNewData(int num) {
        return getNewData(0, num);
    }

    @NonNull
    private ItemDatas getNewData(int from, int num) {
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
