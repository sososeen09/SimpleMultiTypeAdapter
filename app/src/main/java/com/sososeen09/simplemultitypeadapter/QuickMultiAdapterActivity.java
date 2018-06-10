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
import com.sososeen09.multitype.adapter.Mapper;
import com.sososeen09.multitype.adapter.quick.QuickItemProvider;
import com.sososeen09.multitype.adapter.quick.QuickViewHolder;
import com.sososeen09.multitype.adapter.quick.QuickMultiTypeAdapter;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemChildClickListener;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;
import com.sososeen09.multitype.adapter.provider.AbsItemProvider;
import com.sososeen09.multitype.adapter.provider.ItemHolderProviderSet;
import com.sososeen09.simplemultitypeadapter.bean.Address;
import com.sososeen09.simplemultitypeadapter.bean.UserInfo;
import com.sososeen09.simplemultitypeadapter.binder.AddressBinder;
import com.sososeen09.simplemultitypeadapter.binder.FemaleBinder;
import com.sososeen09.simplemultitypeadapter.binder.MaleBinder;
import com.sososeen09.simplemultitypeadapter.model.DataProvider;

/**
 * @author sososeen09
 */
public class QuickMultiAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    TextView tvAddHeader;
    TextView tvRemoveHeader;
    TextView tvAddFooter;
    TextView tvRemoveFooter;
    TextView tvNewData;
    TextView tvAddData;
    private QuickMultiTypeAdapter mQuickMultiTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_multi_adapter);

        initView();

        mQuickMultiTypeAdapter = QuickMultiTypeAdapter.newInstance();

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
                Toast.makeText(QuickMultiAdapterActivity.this, "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // set item child click listener
        mQuickMultiTypeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mQuickMultiTypeAdapter.getItems().get(position);
                Toast.makeText(QuickMultiAdapterActivity.this, "child view click " + view.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ItemDatas items = DataProvider.getNewData(50);

        mQuickMultiTypeAdapter.setNewData(items);

        rv.setAdapter(mQuickMultiTypeAdapter);

    }

    private void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

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
                mQuickMultiTypeAdapter.setNewData(DataProvider.getNewData(20));
                break;
            case R.id.tv_add_data:
                mQuickMultiTypeAdapter.addData(DataProvider.getNewData(mQuickMultiTypeAdapter.getItems().size(), 20));
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
