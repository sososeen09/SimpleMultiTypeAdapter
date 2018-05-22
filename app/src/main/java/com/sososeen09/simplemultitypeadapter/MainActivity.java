package com.sososeen09.simplemultitypeadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @author sososeen09
 */
public class MainActivity extends AppCompatActivity {

    TextView tvIntro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIntro = findViewById(R.id.tv_intro);

        tvIntro.setText(R.string.intro);
    }

    public void quickMultiAdapter(View view) {
        startActivity(new Intent(this, QuickMultiAdapterActivity.class));
    }

    public void homePage(View view) {
        startActivity(new Intent(this, HomePageActivity.class));
    }
}
