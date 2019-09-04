package org.satuma.net.wifisignaltracker;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by MianGhazanfar on 7/20/2016.
 */
public class SSIDListWithStrength extends AppCompatActivity {
    private ListView listview;
    private Button btnScan,btnNext,btnBack;
    static List<ScanResult> list ;
    ArrayList<String> AParray;
    ArrayAdapter<String> APAdapter;
    public ArrayList<ScanResult> scanresult;
    public ArrayList<String> ssidlist;
    private String MAC_LIST,CELLID_LIST;
    private SparseBooleanArray sp;
    private int isScanbtnPressd=0;
    Timer timer;
    WifiConnectivity con;
    private  double biglevel= 999;
    private static boolean isTimerOn=false;
    int signalLevel;
    SessionManager manager;
  public static   TextView location,presence_state,date,checkintime,checkouttime,totalactivetime;
    public static TextView time_elapsed,empName;
    String SSid;
    public long startTime = 0;
    Timer timer1,timer2;
    String time;   //Clock's combined time
    String elapsedTime;    //Stopwatch elapsed time
    int stopwatchHour = 0;
    int stopwatchMin = 0;
    int stopwatchSec = 0;
    int stopwatchCentiSec = 0;
    int elapsedSeconds = 0;
    int elapsedCentiseconds = 0;
    boolean isTimer1Runningg=false;
    boolean isTimer2Running=false;
    boolean rzlt=false;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssid__details);
        scanresult  = new ArrayList<ScanResult>();
        stopService(new Intent(SSIDListWithStrength.this, TrackingService.class));
        manager = new SessionManager(this);
        CalenderSYNC sync=new CalenderSYNC();
        wakeWifiLock(getApplicationContext());

        try {
            rzlt = sync.matchDate(manager.getLastDate());
            Log.w("###############", "islastDateMatched" + rzlt);
            if (!rzlt) {
                Log.w("###############", "islastDateMatched" + rzlt);
                manager.setProxyStatus(false);
                manager.setInitialized(false);
                 manager.setTimein(null);
                manager.setTimeout(null);
                manager.setTotalTimeElapsed("00:00:00");
                manager.setTimeElapsed("00:00:00");
                manager.clearTimeLog();
                //ssidlist.clear();
                //APAdapter.clear();
                //APAdapter.notifyDataSetChanged();
                Intent intent=new Intent(SSIDListWithStrength.this, ResultActivity.class);
                intent.putExtra("value","in");
                startActivity(intent);
                stopTimer();

                // startService(new Intent(MyApplication.this,TrackingService.class));

            }
        }catch (Exception e){
            e.printStackTrace();
        }



        if(!manager.getInitialized()) {
            manager.setCounter(1);
            startService(new Intent(SSIDListWithStrength.this, TrackingService.class));
            manager.setInitialized(true);
        }
        //74:a5:28:aa:40:94
        //1a:fe:34:f3:5d:48/5e:cf:7f:0f:43:f4/5e:cf:7f:00:e5:de
        //ROOM_1>5e:cf:7f:0f:43:f4
        //ROOM_2>1a:fe:34:f3:5d:48
        //ROOM_3>5e:cf:7f:00:e5:de
        ///1a:fe:34:f3:5d:48/5e:cf:7f:00:e5:de/
      //  manager.setMACList("1a:fe:34:f3:5d:48/5e:cf:7f:0f:43:f4/5e:cf:7f:00:e5:de");
        ssidlist = new ArrayList<String>();
        presence_state = (TextView) findViewById(R.id.presence_state);
        totalactivetime= (TextView) findViewById(R.id.activeTime);
        empName= (TextView) findViewById(R.id.emp_name);
        date = (TextView) findViewById(R.id.date);
        empName.setText(UserInformation.userName);
        if(date!=null) {
            date.setText(CalenderSYNC.getCurentDate());
        }
        checkintime= (TextView) findViewById(R.id.checkintime);
        checkouttime= (TextView) findViewById(R.id.checkouttime);
try {
          checkintime.setText("Time in: "+manager.getTimein());}catch (NullPointerException e){
    e.printStackTrace();
}
        listview = (ListView) findViewById(R.id.idlistview);
        location = (TextView) findViewById(R.id.location);
        time_elapsed = (TextView) findViewById(R.id.time);
         checkDutyStatus(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MAC_LIST = "";
                Log.w("List", MAC_LIST + "--");
            }
        });
//        btnScan = (Button) findViewById(R.id.btnscan);
//        btnScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                long totalelasped ;
//
////                    totalelasped = CalenderSYNC.addupElapseTime("2:1:13", "1:1:13");
////                    Log.w("ADD up ELapse TIme",totalelasped+"");
//
//                //manager.setTotalTimeElapsed(totalelasped + "");
//                totalactivetime.setText(CalenderSYNC.getTotalElapsTime(Long.valueOf(manager.getTotalTimeElapsed())));
//                Log.w("Counter", manager.getCounter() + "");
//                Log.w("proxystatus",manager.getProxyStatus()+"");
//                Log.w("TimeLog",manager.getMACString()+"");
//                Log.w("Time In",manager.getTimein()+"");
//                Log.w("Total TIMe Elapsed",manager.getTotalTimeElapsed()+"");
//                Log.w("Time out",manager.getTimeout()+"");
//                Log.w("Time Elapsed",manager.getTimeElapsed()+"");
//                Log.w("Time in seconds",CalenderSYNC.getSeconds("2:1:13")+"");
//
//            }
//        });
    }

        public void checkDutyStatus(Context mContext){
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isTimer1Runningg=true;
                            if (!ssidlist.isEmpty()) {
                                ssidlist.clear();
                            }
//                            wakeWifiLock(SSIDListWithStrength.this);
                            con = new WifiConnectivity(SSIDListWithStrength.this);
                            try {
                                scanresult = con.scanWIFI(SSIDListWithStrength.this);
                                for (ScanResult sc : scanresult) {
                                    int signalLevel = sc.level;
                                    int level = WifiManager.calculateSignalLevel(sc.level, 100);
//                                    if (sc.SSID.contains("ROOM_")) {
                                        if (Math.abs(signalLevel) <= biglevel) {
                                            SSid = sc.SSID;
                                        }
                                        Log.w("SSID", sc.SSID);

                                   //   ssidlist.add(String.valueOf(sc.SSID) + "\n\t Level:" + level + "\t SignalRatio: " + signalLevel + " dBm\n");
                                        biglevel = Math.abs(signalLevel);
                                    //}
                                    //ssidlist.add(String.valueOf(sc.SSID) + "\n\t Level:" + level + "\t SignalRatio: " + signalLevel + " dBm\n");
                                }
                            } catch (Exception e) {
                                //  Log.w("Ly bi", "A e o masla" + e);
                                Toast.makeText(SSIDListWithStrength.this, "Problem:" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                            try {
                                timeIntervalListview();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //APAdapter = new ArrayAdapter<String>(SSIDListWithStrength.this, android.R.layout.simple_list_item_1, ssidlist);
                            //listview.setAdapter(APAdapter);
                            totalactivetime.setText("Prev. Active Time:"+manager.getTotalTimeElapsed());

                            location.setText("Current Location: " + SSid);
                            isScanbtnPressd += 1;
                            //  org.satuma.net.wifisignaltracker.Timer timer=new org.satuma.net.wifisignaltracker.Timer();
                            if (WifiConnectivity.isMatchedWithAP(SSIDListWithStrength.this)) {
                                try {
                                    timeIntervalListview();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                             APAdapter = new ArrayAdapter<String>(SSIDListWithStrength.this, android.R.layout.simple_list_item_1, ssidlist);
                                listview.setAdapter(APAdapter);
                                presence_state.setText("ON DUTY");
                                presence_state.setBackgroundColor(Color.GREEN);
                                CalenderSYNC sync=new CalenderSYNC();

                                try{
                           rzlt= sync.matchDate(manager.getLastDate());}catch (Exception e){
                                    manager.setLastDate(sync.getCurentDatee());
                                    e.printStackTrace();
                                }
                                if(!rzlt && !manager.getProxyStatus()){
                                    Intent intent=new Intent(SSIDListWithStrength.this,ResultActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.putExtra("value", "in");
//                                    if(isRunning(SSIDListWithStrength.this)){
//                                        finish();
//                                        startActivity(intent);
//                                    }
                                    checkintime.setText("Time in: " + manager.getTimein());
                                    totalactivetime.setText("Prev. Active Time:"+manager.getTotalTimeElapsed());
                                    //stopService(new Intent(SSIDListWithStrength.this,TrackingService.class));
                                }else if(rzlt && manager.getProxyStatus()&& manager.getCounter()==1){
                                  //  stopService(new Intent(SSIDListWithStrength.this,TrackingService.class));
                                    manager.setTimein(CalenderSYNC.getCurrentTime());
                                        try {
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(UserInformation.userContact, null, UserInformation.userName+"\nGoing on duty at:" + manager.getTimein() + "\nTotal active Time:" + manager.getTotalTimeElapsed(), null, null);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    manager.setCounter(0);
                                    checkintime.setText("Time in: "+manager.getTimein());
                                    totalactivetime.setText("Prev. Active Time:"+manager.getTotalTimeElapsed());
                                }
                                try {
                                    time_elapsed.setText(CalenderSYNC.calTimeDiffrence(manager.getTimein(), CalenderSYNC.getCurrentTime()));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                manager.setProxyStatus(true);
                            } else { presence_state.setText("OFF DUTY");

                                presence_state.setBackgroundColor(Color.RED);
                                if(!time_elapsed.getText().toString().equals("00:00:00")) {
                                    if(manager.getTotalTimeElapsed()==null){
                                        manager.setTotalTimeElapsed("00:00:00");
                                    }
                                    long totalelasped = CalenderSYNC.addupElapseTime(manager.getTotalTimeElapsed(),time_elapsed.getText().toString());
                                    manager.setTotalTimeElapsed(CalenderSYNC.getTotalElapsTime(totalelasped) + "");
                            if(manager.getCounter()==0){
                                    manager.setTimeout(CalenderSYNC.getCurrentTime());
                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(UserInformation.userContact, null, UserInformation.userName+"\n Going Off duty at:" + manager.getTimeout() + "\nTotal active Time:" + manager.getTotalTimeElapsed(), null, null);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                                    manager.setTimeElapsed(time_elapsed.getText().toString());
                                    checkouttime.setText("Time out: " + manager.getTimeout());
                                    String timelog="Elapsed Time: "+manager.getTimeElapsed()+"\nTime in: "+manager.getTimein()+" "+" "+"Time Out: "+manager.getTimeout()+"  "+"/";
                                    time_elapsed.setText("00:00:00");
                                    manager.setTimeLog(timelog);
                                }
                                totalactivetime.setText("Active Time:"+manager.getTotalTimeElapsed());
                                checkouttime.setText("Time out: " + manager.getTimeout());
                                try {
                                    timeIntervalListview();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                APAdapter = new ArrayAdapter<String>(SSIDListWithStrength.this, android.R.layout.simple_list_item_1, ssidlist);
                                listview.setAdapter(APAdapter);
                                stopTimer();
                                   if(manager.getCounter()==0) {
                                      // finish();
                                   }
                                       //wakeWifiLock(SSIDListWithStrength.this);
                                       startService(new Intent(SSIDListWithStrength.this, TrackingService.class));

                                manager.setCounter(1);
                               // }

                            }
                        }

                    });

                }

            }, 0, 1000);
        }
    public  void stopTimer(){
        if(isTimer1Runningg) {
            timer.cancel();
            timer.purge();
            timer=null;
        }
    }



    public String stopwatch() {

        elapsedSeconds = elapsedCentiseconds / 100; //Conversion to enable use of same formula above
        stopwatchHour = elapsedSeconds / 3600;
        stopwatchMin = (elapsedSeconds % 3600) / 60;
        stopwatchSec = elapsedSeconds % 60;

        //Formats stopwatch to two digits per time unit.
        elapsedTime = (stopwatchHour < 10 ? "0" : "") + stopwatchHour + ":";
        elapsedTime += (stopwatchMin < 10 ? "0" : "") + stopwatchMin + ":";
        elapsedTime += (stopwatchSec < 10 ? "0" : "") + stopwatchSec + ":";

        //Grabs last two digits of total centiseconds for centisecond component.
        String centiSeconds = String.valueOf(elapsedCentiseconds);
        if (centiSeconds.length() >1) {
            elapsedTime += centiSeconds.charAt(centiSeconds.length() - 2);
            elapsedTime += centiSeconds.charAt(centiSeconds.length() - 1);
        } else {
            elapsedTime += "0" + centiSeconds.charAt(centiSeconds.length() - 1);
        }
        elapsedCentiseconds++;
        return elapsedTime;
    }
    public void timeIntervalListview() throws Exception {
        String[] list=manager.getTIME_Log_Array();
        for (String item:list
                ) {
            ssidlist.add(item);

        }
    }
   //isActivity Running
   public static boolean isRunning(Context ctx) {
       ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
       List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
       for (ActivityManager.RunningTaskInfo task : tasks) {
           if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
               return true;
       }
       return false;
   }
    public  void kill(){
       finish();
    }

    public static void wakeWifiLock(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiManager.MulticastLock lock = wifi.createMulticastLock("lockWiFiMulticast");
        lock.setReferenceCounted(false);
        lock.acquire();
    }
}