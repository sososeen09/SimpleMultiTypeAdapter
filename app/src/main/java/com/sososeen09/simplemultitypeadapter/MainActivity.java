package com.sososeen09.simplemultitypeadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleMulti(View view) {
        startActivity(new Intent(this, SimpleMultiAdapterActivity.class));
    }

    public void wrapperAdapter(View view) {
        startActivity(new Intent(this, WrapperAdapterActivity.class));
    }

    public void onClickTest(View view) {
        startActivity(new Intent(this, OnClickListenerActivity.class));
    }

    public void quickMultiAdapter(View view) {
        startActivity(new Intent(this, QuickMultiAdapterActivity.class));
    }
}
