package org.satuma.net.wifisignaltracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

/**
 * Created by MianGhazanfar on 7/21/2016.
 */
public  class Notification  {

    private static int notificationID=99;
    //private int notificationID=999;
    public static void throwNotification(Context context){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Boundry Alert, Click Me!");
        mBuilder.setContentText("Hi,You are going out of station");
       // Intent resultIntent = new Intent(context, ResultActivity.class);
       // TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
       // stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
       // stackBuilder.addNextIntent(resultIntent);
       // PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
       // mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
