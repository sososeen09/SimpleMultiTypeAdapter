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

import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiAdapter;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.multitype.adapter.base.QuickMultiTypeAdapter;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.simplemultitypeadapter.bean.Address;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;
import com.sososeen09.simplemultitypeadapter.binder.AddressBinder;
import com.sososeen09.simplemultitypeadapter.binder.FemaleBinder;
import com.sososeen09.simplemultitypeadapter.binder.MaleBinder;
import com.sososeen09.simplemultitypeadapter.model.DataProvider;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;

public class ChangeWrapperAdapterActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv;
    TextView tvAdapter1;
    TextView tvAdapter2;
    private QuickMultiTypeAdapter mQuickMultiTypeAdapter;
    private BaseMultiAdapter mBaseMultiAdapter1;
    private BaseMultiAdapter mBaseMultiAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_wrapper_adapter);
        initView();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        tvAdapter1 = findViewById(R.id.tv_set_1);
        tvAdapter2 = findViewById(R.id.tv_set_2);

        tvAdapter1.setOnClickListener(this);
        tvAdapter2.setOnClickListener(this);


        mBaseMultiAdapter1 = new BaseMultiAdapter();
        mBaseMultiAdapter2 = new BaseMultiAdapter();
        mQuickMultiTypeAdapter = new QuickMultiTypeAdapter(mBaseMultiAdapter1);

        configAdapter(mBaseMultiAdapter1);
        configAdapter(mBaseMultiAdapter2);

        mBaseMultiAdapter1.setItems(DataProvider.getNewData(15));
        mBaseMultiAdapter2.setItems(DataProvider.getNewData(8));

        mQuickMultiTypeAdapter.addHeaderView(getHeader());
        mQuickMultiTypeAdapter.addFooterView(getFooter());
        rv.setAdapter(mQuickMultiTypeAdapter);
    }

    private void configAdapter(BaseMultiAdapter baseMultiAdapter) {
        // one to one
        baseMultiAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        // one to one
        baseMultiAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });

        // one to one
        baseMultiAdapter.register(Address.class, new AddressBinder());


        // one to many
        baseMultiAdapter.register(UserInfo.class).to(new FemaleBinder(), new MaleBinder()).withClassLinker(new ClassLinker<UserInfo>() {
            @NonNull
            @Override
            public Class<? extends ItemViewBinder<UserInfo, ?>> index(int position, @NonNull UserInfo userInfo) {
                return userInfo.sexuality == 1 ? MaleBinder.class : FemaleBinder.class;
            }
        });

        // set item click listener
        baseMultiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(ChangeWrapperAdapterActivity.this, "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // set item child click listener
        baseMultiAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(ChangeWrapperAdapterActivity.this, "child view click " + view.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set_1:
                mQuickMultiTypeAdapter.setWrapperdAdapter(mBaseMultiAdapter1);
                break;
            case R.id.tv_set_2:
                mQuickMultiTypeAdapter.setWrapperdAdapter(mBaseMultiAdapter2);
                break;

            default:
                break;
        }
    }
}
