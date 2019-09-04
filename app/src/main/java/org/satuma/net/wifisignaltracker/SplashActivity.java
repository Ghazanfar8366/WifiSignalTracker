package org.satuma.net.wifisignaltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
private SessionManager manager;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        manager = new SessionManager(SplashActivity.this);
        if (manager.isLoggedIn()) {

            Intent i = new Intent(SplashActivity.this, SSIDListWithStrength.class);
            startActivity(i);
            finish();
        } else {

            Intent i = new Intent(SplashActivity.this, org.satuma.net.AccessPoints.ScanAccessPoints.class);
            startActivity(i);
            finish();
        }
//
//        // View layout=findViewById(R.id.layout);
//        //ImageView imgview = (ImageView) findViewById(R.id.logo);
//       // final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//        //imgview.startAnimation(animRotate);
//       // layout.startAnimation(animRotate);
//        animRotate.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                manager = new SessionManager(getApplicationContext());
//                finish();
//             //   Log.w("@@@@@DomainURL", manager.isSetDomainUrl() + "");
//
////
//                    if (manager.isLoggedIn()) {
//
//                        Intent i = new Intent(SplashActivity.this, SSIDListWithStrength.class);
//                        startActivity(i);
//                    } else {
//
//                        Intent i = new Intent(SplashActivity.this, org.satuma.net.AccessPoints.ScanAccessPoints.class);
//                        startActivity(i);
//                    }
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }
    }

