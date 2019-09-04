package org.satuma.net.wifisignaltracker;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by MianGhazanfar on 7/29/2016.
 */
public class MyApplication extends Application {
    private SessionManager manager;
    private boolean rzlt;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("@@@@@@@@@@@@@", "MY Application");
        WakeLocker.setNeverSleepPolicy(getApplicationContext());
//        manager = new SessionManager(getApplicationContext());
//        if (manager.isLoggedIn()) {
//           // finish();
//            Intent i = new Intent(getApplicationContext(), SSIDListWithStrength.class);
//            startActivity(i);
//        } else {
//            //finish();
//            Intent i = new Intent(getApplicationContext(), org.satuma.net.AccessPoints.ScanAccessPoints.class);
//            startActivity(i);
//        }

    }
}
