package org.satuma.net.wifisignaltracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

/**
 * Created by MianGhazanfar on 7/21/2016.
 */
public class ResultActivity extends Activity {
    TextView status;
    public static boolean state=false;
SessionManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeein);
        String value=getIntent().getStringExtra("value");
        status= (TextView) findViewById(R.id.status);
        manager=new SessionManager(this);
        CalenderSYNC sync=new CalenderSYNC();
        if(value.equals("in")) {
            ///................................


            //.................................
            status.setText("You:Checked in");
            manager.setLastDate(sync.getCurentDatee());
            manager.setTimein(CalenderSYNC.getCurrentTime());
            SSIDListWithStrength.checkintime.setText(manager.getTimein());
            //+923452277573
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(UserInformation.userContact, null, "SAM Alert\n"+UserInformation.userName+"\n Going on duty at:" + manager.getTimein(), null, null);
            }catch (Exception e){
                e.printStackTrace();
            }
            state=true;
        }else if(value.equals("out") & state==true){
            state=false;
            status.setText("You:Checked out");
        }

    }
}
