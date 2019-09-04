package org.satuma.net.wifisignaltracker;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
/**
 * Created by MianGhazanfar on 8/5/2016.
 */
public class WakeLocker {
        private static PowerManager.WakeLock wl_cpu;
        private static PowerManager mPowerManager;
        private static WifiManager.WifiLock wifiLock = null;
        @SuppressLint({ "InlinedApi", "Wakelock" })
        public static void acquire(Context ctx) {
            try {
                mPowerManager = (PowerManager) ctx
                        .getSystemService(Context.POWER_SERVICE);
                WifiManager wm = (WifiManager) ctx
                        .getSystemService(Context.WIFI_SERVICE);
                wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF,
                        "MyWifiLock");
                wifiLock.acquire();
                wl_cpu = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE, "MyCpuLock");
                wl_cpu.acquire(10000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static void release() {
try {
    if ((wifiLock != null) ) {
        wifiLock.release();
        wifiLock = null;
    }
    if ((wl_cpu != null)) {
        wl_cpu.release();
        wl_cpu = null;
    }
}catch (Exception e){e.printStackTrace();}

        }


    public static void setNeverSleepPolicy(Context ctx) {
        try {
            ContentResolver cr = ctx.getContentResolver();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                int set = android.provider.Settings.System.WIFI_SLEEP_POLICY_NEVER;
                android.provider.Settings.System.putInt(cr, android.provider.Settings.System.WIFI_SLEEP_POLICY, set);
            } else {
                int set = android.provider.Settings.Global.WIFI_SLEEP_POLICY_NEVER;
                android.provider.Settings.System.putInt(cr, android.provider.Settings.Global.WIFI_SLEEP_POLICY, set);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
