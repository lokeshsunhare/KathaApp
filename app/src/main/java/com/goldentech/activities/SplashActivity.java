package com.goldentech.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.goldentech.R;
import com.goldentech.common.AllApiActivity;
import com.goldentech.common.Typewriter;

public class SplashActivity extends AppCompatActivity {
    private Typewriter tv_app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);

        tv_app_name = (Typewriter) findViewById(R.id.tv_app_name);
        tv_app_name.setCharacterDelay(150);
        tv_app_name.animateText(getString(R.string.katha_name));

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.sequential);
        RelativeLayout layout_view = findViewById(R.id.layout_view);
        layout_view.startAnimation(myAnim);

        TextView tv_version_name = findViewById(R.id.tv_version_name);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version_name.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(this::endSplash, 3000);

    }

    private void endSplash() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
