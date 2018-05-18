package com.sososeen09.simplemultitypeadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sososeen09.multitype.adapter.SimpleMultiAdapterWithHeaderFooter;

import java.util.ArrayList;
import java.util.List;

public class SimpleMultiAdapterActivity extends AppCompatActivity {

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_multiadapter);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SimpleMultiAdapterWithHeaderFooter aDefault = SimpleMultiAdapterWithHeaderFooter.getDefault();

        TextView header = new TextView(this);
        header.setText("this is header");

        TextView footer = new TextView(this);
        footer.setText("this is footer");

        aDefault.addHeaderView(header);
        aDefault.addFooterView(footer);

        aDefault.register(String.class, new StringItemViewBinder());


        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add("value: " + i);
        }

        aDefault.setNewData(strings);

        rv.setAdapter(aDefault);
    }
}
