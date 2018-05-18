package com.sososeen09.simplemultitypeadapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sososeen09.multitype.adapter.base.HeaderFooterWrapperAdapter;
import com.sososeen09.multitype.adapter.base.BaseItemViewBinder;
import com.sososeen09.multitype.adapter.base.BaseMultiAdapter;
import com.sososeen09.multitype.adapter.base.BaseMultiViewHolder;
import com.sososeen09.multitype.adapter.contract.OffsetDelegate;
import com.sososeen09.multitype.adapter.contract.OnClickAdapterContract;
import com.sososeen09.multitype.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.drakeet.multitype.Items;

public class OnClickListenerActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv;

    TextView tvAddHeader;
    TextView tvRemoveHeader;
    TextView tvAddFooter;
    TextView tvRemoveFooter;
    private HeaderFooterWrapperAdapter mHeaderFooterWrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_listener);
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


        final BaseMultiAdapter baseMultiAdapter = new BaseMultiAdapter(new ArrayList<>());

        baseMultiAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
                holder.setText(R.id.tv, item);
            }
        });

        baseMultiAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
            @Override
            public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
                holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
            }
        });


        Items items = new Items();

        List<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            if (random.nextInt(10) % 2 == 0) {
                items.add("this is String, value: " + i);
            } else {
                items.add(i);
            }
        }

        baseMultiAdapter.setItems(items);
        baseMultiAdapter.setOffsetDelegate(new OffsetDelegate() {
            @Override
            public int getOffsetPosition() {
                return mHeaderFooterWrapperAdapter.getHeaderLayoutCount();
            }
        });
        baseMultiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
                Object o = baseMultiAdapter.getItems().get(position);
                Toast.makeText(OnClickListenerActivity.this, "this id " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mHeaderFooterWrapperAdapter = new HeaderFooterWrapperAdapter(baseMultiAdapter);
//        TextView header = new TextView(this);
//        header.setText("this is header");
//
//        TextView footer = new TextView(this);
//        footer.setText("this is footer");
//        mHeaderFooterWrapperAdapter.addHeaderView(header);
//        mHeaderFooterWrapperAdapter.addFooterView(footer);

        rv.setAdapter(mHeaderFooterWrapperAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_header:
                mHeaderFooterWrapperAdapter.addHeaderView(getHeader());
                break;
            case R.id.tv_remove_header:
                mHeaderFooterWrapperAdapter.removeAllHeaderView();
                break;
            case R.id.tv_add_footer:
                mHeaderFooterWrapperAdapter.addFooterView(getFooter());
                break;
            case R.id.tv_remove_footer:
                mHeaderFooterWrapperAdapter.removeAllFooterView();
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
