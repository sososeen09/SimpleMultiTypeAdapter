package com.sososeen09.simplemultitypeadapter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sososeen09.simplemultitypeadapter.fragment.HomeFragment;

/**
 * @author sososeen09
 */
public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportFragmentManager().beginTransaction().add(R.id.fl, onCreateFragment()).commit();
    }

    private Fragment onCreateFragment() {
        return new HomeFragment();
    }
}
