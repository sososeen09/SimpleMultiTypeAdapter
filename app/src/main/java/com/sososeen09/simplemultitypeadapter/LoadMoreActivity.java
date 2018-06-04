package com.sososeen09.simplemultitypeadapter;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sososeen09.multitype.adapter.ItemDatas;
import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.multitype.adapter.base.QuickMultiTypeAdapter;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.listener.OnRequestLoadMoreListener;
import com.sososeen09.simplemultitypeadapter.bean.Address;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;
import com.sososeen09.simplemultitypeadapter.binder.AddressBinder;
import com.sososeen09.simplemultitypeadapter.binder.FemaleBinder;
import com.sososeen09.simplemultitypeadapter.binder.MaleBinder;

import java.util.Random;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;

public class LoadMoreActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "LoadMoreActivity";
    RecyclerView rv;
    TextView tvAddHeader;
    TextView tvRemoveHeader;
    TextView tvAddFooter;
    TextView tvRemoveFooter;
    TextView tvNewData;
    TextView tvAddData;
    private QuickMultiTypeAdapter mQuickMultiTypeAdapter;

    private int loadMoreCount = 0;
    private int MAX_LOAD_MORE_COUNT = 5;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_multi_adapter);
        initView();

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mQuickMultiTypeAdapter = QuickMultiTypeAdapter.newInstance();

        // one to one
        mQuickMultiTypeAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        // one to one
        mQuickMultiTypeAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });

        // one to one
        mQuickMultiTypeAdapter.register(Address.class, new AddressBinder());


        // one to many
        mQuickMultiTypeAdapter.register(UserInfo.class).to(new FemaleBinder(), new MaleBinder()).withClassLinker(new ClassLinker<UserInfo>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<UserInfo, ?>> index(int position, @NonNull UserInfo userInfo) {
                return userInfo.sexuality == 1 ? MaleBinder.class : FemaleBinder.class;
            }
        });

        // set item click listener
        mQuickMultiTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(LoadMoreActivity.this, "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // set item child click listener
        mQuickMultiTypeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(LoadMoreActivity.this, "child view click " + view.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ItemDatas items = getNewData(50);

        mQuickMultiTypeAdapter.setNewData(items);

        rv.setAdapter(mQuickMultiTypeAdapter);

        mQuickMultiTypeAdapter.setOnLoadMoreListener(new OnRequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e(TAG, "onLoadMoreRequested: loadMoreCount: " + loadMoreCount);
                getRemoteData();
            }
        });
    }

    private void getRemoteData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mQuickMultiTypeAdapter.loadMoreEnd(false);
//                mQuickMultiTypeAdapter.loadMoreComplete();
//                if (loadMoreCount < MAX_LOAD_MORE_COUNT) {
//                    if (loadMoreCount % 2 == 1) {
//                        mQuickMultiTypeAdapter.loadMoreFail();
//                    } else {
//                        mQuickMultiTypeAdapter.loadMoreComplete();
//                        mQuickMultiTypeAdapter.addData(getNewData(mQuickMultiTypeAdapter.getItems().size(), 20));
//                    }
//                } else {
//                    mQuickMultiTypeAdapter.loadMoreEnd(false);
//                }
//
//                loadMoreCount++;
            }
        }, 2000);
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

    private void initView() {
        tvAddHeader = findViewById(R.id.tv_add_header);
        tvRemoveHeader = findViewById(R.id.tv_remove_header);
        tvAddFooter = findViewById(R.id.tv_add_footer);
        tvRemoveFooter = findViewById(R.id.tv_remove_footer);
        tvNewData = findViewById(R.id.tv_new_date);
        tvAddData = findViewById(R.id.tv_add_data);

        tvAddHeader.setOnClickListener(this);
        tvRemoveHeader.setOnClickListener(this);
        tvAddFooter.setOnClickListener(this);
        tvRemoveFooter.setOnClickListener(this);
        tvNewData.setOnClickListener(this);
        tvAddData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_header:
                mQuickMultiTypeAdapter.addHeaderView(getHeader());
                break;
            case R.id.tv_remove_header:
                mQuickMultiTypeAdapter.removeAllHeaderView();
                break;
            case R.id.tv_add_footer:
                mQuickMultiTypeAdapter.addFooterView(getFooter());
                break;
            case R.id.tv_remove_footer:
                mQuickMultiTypeAdapter.removeAllFooterView();
                break;
            case R.id.tv_new_date:
                mQuickMultiTypeAdapter.setNewData(getNewData(20));
                break;
            case R.id.tv_add_data:
                mQuickMultiTypeAdapter.addData(getNewData(mQuickMultiTypeAdapter.getItems().size(), 20));
                break;

            default:
                break;
        }
    }

    public View getHeader() {
        TextView header = new TextView(this);
        header.setText("this is header");
        return header;
    }

    public View getFooter() {
        TextView header = new TextView(this);
        header.setText("this is footer");
        return header;
    }
}
