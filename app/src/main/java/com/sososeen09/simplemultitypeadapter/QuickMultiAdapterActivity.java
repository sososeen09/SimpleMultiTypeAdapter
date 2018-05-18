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

import java.util.Random;

public class QuickMultiAdapterActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rv;
    TextView tvAddHeader;
    TextView tvRemoveHeader;
    TextView tvAddFooter;
    TextView tvRemoveFooter;
    private BaseQuickWrapperAdapter mBaseQuickWrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_multi_adapter);

        tvAddHeader = findViewById(R.id.tv_add_header);
        tvRemoveHeader = findViewById(R.id.tv_remove_header);
        tvAddFooter = findViewById(R.id.tv_add_footer);
        tvRemoveFooter = findViewById(R.id.tv_remove_footer);

        tvAddHeader.setOnClickListener(this);
        tvRemoveHeader.setOnClickListener(this);
        tvAddFooter.setOnClickListener(this);
        tvRemoveFooter.setOnClickListener(this);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        mBaseQuickWrapperAdapter = BaseQuickWrapperAdapter.newInstance();

        mBaseQuickWrapperAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        mBaseQuickWrapperAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });


        mBaseQuickWrapperAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = mBaseQuickWrapperAdapter.getItems().get(position);
                Toast.makeText(QuickMultiAdapterActivity.this, "this id " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ItemDatas items = new ItemDatas();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            if (random.nextInt(10) % 2 == 0) {
                items.add("this is String, value: " + i);
            } else {
                items.add(i);
            }
        }

        mBaseQuickWrapperAdapter.setNewData(items);

        rv.setAdapter(mBaseQuickWrapperAdapter);

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
