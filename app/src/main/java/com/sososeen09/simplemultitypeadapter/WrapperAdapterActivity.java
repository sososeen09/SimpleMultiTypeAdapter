package com.sososeen09.simplemultitypeadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sososeen09.multitype.adapter.base.HeaderFooterWrapperAdapter;
import com.sososeen09.multitype.adapter.SimpleMultiAdapterWithHeaderFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/5/18.
 *
 * @author sososeen09
 */

public class WrapperAdapterActivity extends AppCompatActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper_adapter);


        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));


        SimpleMultiAdapterWithHeaderFooter aDefault = SimpleMultiAdapterWithHeaderFooter.getDefault();

        TextView header = new TextView(this);
        header.setText("this is header");

        TextView footer = new TextView(this);
        footer.setText("this is footer");


        aDefault.register(String.class, new StringItemViewBinder());

        HeaderFooterWrapperAdapter headerFooterWrapperAdapter = new HeaderFooterWrapperAdapter(aDefault);
        headerFooterWrapperAdapter.addHeaderView(header);
        headerFooterWrapperAdapter.addFooterView(footer);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add("value: " + i);
        }

        aDefault.setNewData(strings);

        rv.setAdapter(headerFooterWrapperAdapter);

    }
}
