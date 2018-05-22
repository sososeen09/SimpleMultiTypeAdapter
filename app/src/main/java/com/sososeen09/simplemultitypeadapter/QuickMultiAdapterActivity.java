package com.sososeen09.simplemultitypeadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sososeen09.multitype.adapter.ItemDatas;
import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.multitype.adapter.base.BaseQuickWrapperAdapter;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.simplemultitypeadapter.binder.FemaleBinder;
import com.sososeen09.simplemultitypeadapter.binder.MaleBinder;

import java.util.Random;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;

public class QuickMultiAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    TextView tvAddHeader;
    TextView tvRemoveHeader;
    TextView tvAddFooter;
    TextView tvRemoveFooter;
    TextView tvNewData;
    TextView tvAddData;
    private BaseQuickWrapperAdapter mBaseQuickWrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_multi_adapter);

        initView();

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mBaseQuickWrapperAdapter = BaseQuickWrapperAdapter.newInstance();

        // one to one
        mBaseQuickWrapperAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        // one to one
        mBaseQuickWrapperAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });


        // one to many
        mBaseQuickWrapperAdapter.register(UserInfo.class).to(new FemaleBinder(), new MaleBinder()).withClassLinker(new ClassLinker<UserInfo>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<UserInfo, ?>> index(int position, @NonNull UserInfo userInfo) {
                return userInfo.sexuality == 1 ? MaleBinder.class : FemaleBinder.class;
            }
        });

        // set item click listener
        mBaseQuickWrapperAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mBaseQuickWrapperAdapter.getItems().get(position);
                Toast.makeText(QuickMultiAdapterActivity.this, "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ItemDatas items = getNewData(50);

        mBaseQuickWrapperAdapter.setNewData(items);

        rv.setAdapter(mBaseQuickWrapperAdapter);

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
                mBaseQuickWrapperAdapter.addHeaderView(getHeader());
                break;
            case R.id.tv_remove_header:
                mBaseQuickWrapperAdapter.removeAllHeaderView();
                break;
            case R.id.tv_add_footer:
                mBaseQuickWrapperAdapter.addFooterView(getFooter());
                break;
            case R.id.tv_remove_footer:
                mBaseQuickWrapperAdapter.removeAllFooterView();
                break;
            case R.id.tv_new_date:
                mBaseQuickWrapperAdapter.setNewData(getNewData(20));
                break;
            case R.id.tv_add_data:
                mBaseQuickWrapperAdapter.addData(getNewData(mBaseQuickWrapperAdapter.getItems().size(), 20));
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
