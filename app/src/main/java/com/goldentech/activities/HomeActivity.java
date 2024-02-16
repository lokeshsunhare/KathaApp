package com.goldentech.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goldentech.R;
import com.goldentech.fragments.HomeFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = findViewById(R.id.tv_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            title.setText(getResources().getString(R.string.app_name));
        }
        displaySelectedFragment(0);

        MobileAds.initialize(this, initializationStatus -> {
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdFailedToLoad: Ad failed: " + errorCode);
            }

        });

        findViewById(R.id.layout_mahabarat).setOnClickListener(v -> {
            callIntent("https://www.youtube.com/channel/UCHKGDg0GJKBsA9mFraDOLHA");
        });
        findViewById(R.id.layout_ramayan).setOnClickListener(v -> {
            callIntent("https://www.youtube.com/watch?v=-vgYRr5Tt0k");
        });
        findViewById(R.id.layout_vishnu_puran).setOnClickListener(v -> {
            callIntent("https://www.youtube.com/watch?v=wQZpS-9ubic&list=PLNKJJImmGYISh0bc__cVPUuDy2TDB5PZt");
        });
    }

    public void displaySelectedFragment(int id) {
        Fragment fragment = null;

        if (id == 0) {
            fragment = new HomeFragment();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (id == 0)
                ft.replace(R.id.frameContainer, fragment);
            else
                ft.replace(R.id.frameContainer, fragment).addToBackStack(null);
            ft.commit();
        }

    }

    private void callIntent(String url) {

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "No any browser to view the video", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
