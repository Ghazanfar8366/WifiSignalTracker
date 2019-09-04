package org.satuma.net.wifisignaltracker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by MianGhazanfar on 3/16/2016.
 */
public class CalenderSYNC {


    public static void AddProxy( Context ctx,String title ,String Time) throws ParseException {
        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
           // ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        ContentValues l_event = new ContentValues();

        l_event.put("title", title);
        l_event.put("calendar_id", 1);
        l_event.put("description", "This status is created by LBPMS app.");
        l_event.put("eventLocation", " ");

        DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy, h:mma");
        String dateStarts = Time;
        String dateEnds = Time;

        Date startDate, endDate;
        startDate = formatter.parse(dateStarts);
        endDate = formatter.parse(dateEnds);
        l_event.put("dtstart", startDate.getTime());
        l_event.put("dtend", endDate.getTime());
        l_event.put("eventStatus", 1);
        l_event.put("allDay", 0);
        l_event.put("hasAlarm", 1);
        l_event.put("eventTimezone", TimeZone.getDefault().getID());
        Uri l_eventUri;
       // if (Build.VERSION.SDK_INT >= 8) {
            l_eventUri = Uri.parse("content://com.android.calendar/events");
        //} else {
          //  l_eventUri = Uri.parse("content://calendar/events");
        //}
        Uri l_uri = ctx.getContentResolver().insert(l_eventUri, l_event);
       Log.v("++++++test", "Inserted in Calender");
    }
         public void openCalender(Context context){
                        Log.w("Calender Synced", "Successfull");
                PackageManager packmngr = context.getPackageManager();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> list = packmngr.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
                ResolveInfo Resolvebest = null;
                for (final ResolveInfo info : list) {
                    if (info.activityInfo.packageName.endsWith(".calendar"))
                        Resolvebest = info;
                }
                if (Resolvebest != null) {
                    intent.setClassName(Resolvebest.activityInfo.packageName,
                            Resolvebest.activityInfo.name);
                    context.startActivity(intent);
                }
    }
    public boolean matchDate(String lastdate) throws ParseException {
        String lastProxydate = lastdate;
        boolean result=false;
        Log.w("@@@@@@@@@@@last date",lastProxydate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = sdf.parse(lastProxydate);
        Calendar c = Calendar.getInstance();
        String now=sdf.format(c.getTime());
        Log.w("NEW DATE()",""+now);
        Date Currentdate=sdf.parse(now);
        if (new Date().after(strDate)) {
            if(Currentdate.equals(strDate) ) result = true;
            //return true Date if matched else return false
        }else result=false;
        return result;
    }
    public static String getCurentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String now=sdf.format(c.getTime());
        return now;
    }
    public String getCurentDatee(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String now=sdf.format(c.getTime());
        return now;
    }
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String time=sdf.format(c.getTime());
        return time;
    }
    public static String getCurrentDateString(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }
    public static String calTimeDiffrence(String time_in,String time_out) throws ParseException {

        String dateStart = time_in;
        String dateStop = time_out;

        //Custom date format
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
        System.out.println("Time in hours: " + diffHours + " hours.");
        return  diffHours + ":" + diffMinutes + ":" +diffSeconds; // updated value every1 second

    }
    public static long addupElapseTime(String time_in, String time_out) {
          long time1=getSeconds(time_in);
        long time2=getSeconds(time_out);
      long sum=time1+time2;
          return sum;
    }
    public static String getTotalElapsTime(long seconds){
        long remseconds=seconds%60;
        long minutes = (seconds%3600)/60;
        long hours   = (seconds/3600);
        return  hours + ":" + minutes + ":" +remseconds; // updated value every1 second
    }
    public static long getSeconds(String time){
        String[] sec=time.split(":");
        long htos=Integer.valueOf(sec[0])*60*60;
        long mtos=Integer.valueOf(sec[1])*60;
        long stos=Integer.valueOf(sec[2]);

        return htos+mtos+stos;

    }
    }

