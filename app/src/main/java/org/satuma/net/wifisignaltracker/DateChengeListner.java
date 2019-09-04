package org.satuma.net.wifisignaltracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DateChengeListner extends BroadcastReceiver {
    SessionManager manager;
    private boolean rzlt;
    public DateChengeListner() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        manager=new SessionManager(context);
       Toast.makeText(context,"SAM app detects that you changed the date",Toast.LENGTH_SHORT).show();
 Log.w("SAM app detects", "That u changed date");
        CalenderSYNC sync=new CalenderSYNC();
        try{
            rzlt= sync.matchDate(manager.getLastDate());}catch (Exception e){
            if(!rzlt){
                TrackingService service=new TrackingService();
                if(service.isMyServiceRunning(TrackingService.class)) {
                    Intent i = new Intent();
                    i.setClassName("org.satuma.net.wifisignaltracker", "org.satuma.net.wifisignaltracker.TrackingService");
                    context.stopService(i);
                }
                    manager.setProxyStatus(false);
                    manager.setInitialized(false);
                    manager.setTimein(null);
                    manager.setTimeout(null);
                    manager.setTotalTimeElapsed("00:00:00");
                    manager.setTimeElapsed("00:00:00");
                    manager.setTimeLog(null);
                    SSIDListWithStrength list = new SSIDListWithStrength();
                    list.stopTimer();
                    Intent ii = new Intent();
                    ii.setClassName("org.satuma.net.wifisignaltracker", "org.satuma.net.wifisignaltracker.TrackingService");
                    context.startService(ii);

            }
            e.printStackTrace();
        }

    }
}
