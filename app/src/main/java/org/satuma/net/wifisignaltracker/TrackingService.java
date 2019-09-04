package org.satuma.net.wifisignaltracker;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class TrackingService extends Service {
    private Timer timer;
    private WifiConnectivity con;
    public ArrayList<ScanResult> scanresult;
    public ArrayList<String> ssidlist;
    private Context mContext;
    private boolean rzlt;
    private  Intent alertintent;
    SessionManager manager;
    public TrackingService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    @Override
    public void onCreate() {
        mContext=this;
        manager=new SessionManager(mContext);
        //alertintent=new Intent(mContext,ResultActivity.class);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Log.w("Service","onCommandStarted");
        WakeLocker.acquire(mContext);
        con = new WifiConnectivity(mContext);
        doAsynchronousTask();
        return Service.START_STICKY;

    }

    public void doAsynchronousTask() {
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //TO-DO Here

                       if (con.isWIFIConnected(getApplicationContext())) {
                            rzlt = WifiConnectivity.isMatchedWithAP(getApplicationContext());
                           if(rzlt && manager.getCounter()==1) {
                               alertintent=new Intent(TrackingService.this,SSIDListWithStrength.class);
                               alertintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               alertintent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                               alertintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                               startActivity(alertintent);
                               timer.purge();
                               timer.cancel();
                           }


                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 10000, 1000);
        Log.w("Service","Started");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        WakeLocker.release();
        if(timer!=null){
            timer.cancel();
        }
        stopSelf();
    }
    public  boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}