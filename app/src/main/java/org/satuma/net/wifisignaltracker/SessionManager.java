package org.satuma.net.wifisignaltracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Kamran on 8/1/2015.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Temp Context
    Context _tempContext;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "Peeps";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // TEMP login status
   // private static final String IS_TEMP_LOGIN = "IsLoggedIn";
    private static final String LAT="latitude";
    private static final String LONGT="longitude";
    private static final String RADIUS="radius";
    private static final String DeptName="DeptName";
    private static final String MAC_LIST="Maclist";
    private static final String CELLID_LIST="cellidList";
    private static final String LAST_PROXY_DATE="proxyDate";
    private static final String PROXY_Status="proxyStatus";
    private static final String PROXY_CHECKOUT_DATE="checkout_date";
    private static final String PROXY_CHECKOUT_TIME="checkout_time";
    public static final String CHECKOUT_Status="checkout_status";
    private static final String TIME_LOG="time_log";
    //incase of no internet access checkIn status
    private static final String PROXY_CHECKIN_DATE="checkin_date";
    private static final String PROXY_CHECKIN_TIME="checkin_time";
    private static final String CHECKIN_STATUS="checkin_status";
    private static final String isPERMANENTCHECKOUT="isPermanentCheckout";
    private static final String TEMP_CHECKOUT="temp_checkout";
    // User name (make variable public to access from outside)
    private static final String IS_TIMELOG_UPDATED="is_timelog";
    public static final String KEY = "key";
    public static  String USER_ID = "user_id";
    public static final String USERNAME = "name";
    // User id (make variable public to access from outside)
    public static final String KEY_ID = "0";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    String time_list="";
    public static final String PERMISSION="permission";
    // Constructor
    public static final String domainUrl="domain";
    public static final String TIMEIN="timein";
    public static final String TIMEOUT="timeout";
    public static final String isSetdomainUrl="isdomain";
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }
    public  boolean getTempCheckout() {

        return pref.getBoolean(TEMP_CHECKOUT, false);
    }
    public void setTempCheckout(Boolean value){
        editor.putBoolean(TEMP_CHECKOUT, value);
        editor.commit();
    }
    public void setCounter(int val){
        editor.putInt("Count", val);
        editor.commit();
    }
    public int getCounter(){
        return  pref.getInt("Count",0);
    }

    public  boolean getPermanentCheckout() {

        return pref.getBoolean(isPERMANENTCHECKOUT, false);
    }
    public void setPermanentCheckout(Boolean value){
        editor.putBoolean(isPERMANENTCHECKOUT, value);
        editor.commit();
    }
    public void setUserPermission(boolean value){
        editor.putBoolean(PERMISSION,value);
        editor.commit();
    }
    public boolean isUSERonPERMISSION(){

        return pref.getBoolean(PERMISSION,false);
    }
    public  String getTimeLog() {
        return pref.getString(TIME_LOG, "");
    }
    public void setTimeLog(String timeInterval){
       time_list=getTimeLog()+timeInterval;
        editor.putString(TIME_LOG,time_list);
        editor.commit();
    }
    public void clearTimeLog(){
        editor.remove(TIME_LOG);
        editor.commit();
    }
    public String[] getTIME_Log_Array()throws Exception{
        String[] array;
        String maclist=pref.getString(TIME_LOG,null);
        if(maclist!=null) {
            array = maclist.split("/");
        }else{
            array=null;
        }
        return array;
    }
    public void setIsTimelogUpdated(String value){
        editor.putString(IS_TIMELOG_UPDATED, value);
        editor.commit();
    }

    public String IsTimelogUpdated(){
        return pref.getString(IS_TIMELOG_UPDATED,null);
    }
    public  String getProxyCheckinDate() {
        return pref.getString(PROXY_CHECKIN_DATE,null);
    }
    public  String getProxyCheckinTime() {
        return pref.getString(PROXY_CHECKIN_TIME,null);
    }
    public  String getCheckinStatus() {
        return pref.getString(CHECKIN_STATUS,null);
    }
    public void putCheckINArray(String status,String date,String time){
        editor.putString(CHECKIN_STATUS,status );
        editor.putString(PROXY_CHECKIN_DATE, date);
        editor.putString(PROXY_CHECKIN_TIME, time);
        editor.commit();
    }
    public void setKeyname(String user){
       editor.putString(KEY_EMAIL,user);
        editor.commit();
    }
    public String getKeyName(){
        String user=pref.getString(KEY_EMAIL,null);
        return user;
    }
    public String getUserName(){
        String user=pref.getString(USERNAME,null);
        return user;
    }
    public void setLastDate(String date){
        editor.putString(LAST_PROXY_DATE,date);
        editor.commit();
    }
    public String getLastDate(){
        return pref.getString(LAST_PROXY_DATE,null);
    }


    public void setProxyStatus(boolean status){
        editor.putBoolean(PROXY_Status, status);
        editor.commit();
    }
    public boolean getProxyStatus(){
        return pref.getBoolean(PROXY_Status, false);}

    public String getDomainUrl(){
        return pref.getString(domainUrl, null);
    }
    public void setDomainUrl(String url,boolean val){
        editor.putString(domainUrl,url);
        editor.putBoolean(isSetdomainUrl,val);
        editor.commit();
    }
    public boolean isSetDomainUrl(){
        return pref.getBoolean(isSetdomainUrl, false);
    }

    public void putCheckOutArray(String status,String date,String time){
        editor.putString(CHECKOUT_Status,status );
        editor.putString(PROXY_CHECKOUT_DATE, date);
        editor.putString(PROXY_CHECKOUT_TIME, time);
        editor.commit();
    }
    public void removeCheckoutstatus()
    {
        editor.remove(CHECKOUT_Status);
        editor.remove(PROXY_CHECKOUT_DATE);
        editor.remove(PROXY_CHECKOUT_TIME);
        editor.commit();
    }
    public String getCheckoutStatus(){
        return pref.getString(CHECKOUT_Status,null);
    }
    public String getCheckoutDate(){
        return pref.getString(PROXY_CHECKOUT_DATE,null);
    }
    public String getCheckoutTime(){
        return pref.getString(PROXY_CHECKOUT_TIME,null);

    }

    public String getTimein(){
        return pref.getString(TIMEIN, null);
    }
    public void setTimein(String timein){
        editor.putString(TIMEIN,timein);
        editor.commit();
    }
    public String getTimeout(){
        return pref.getString(TIMEOUT, null);
    }
    public void setTimeout(String timeout){
        editor.putString(TIMEOUT, timeout);
        editor.commit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(String key, String email, String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
       // editor.putBoolean(IS_TEMP_LOGIN, true);
        // Storing email in pref
        editor.putString(KEY, key);
        // Storing name in pref
        editor.putString(KEY_ID, id);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // commit changes
        editor.commit();
    }
    public void createSessionLBPMS(String id, String username){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
       // editor.putBoolean(IS_TEMP_LOGIN, true);
        // Storing email in pref
        editor.putString(USER_ID, id);
        // Storing name in pref
        //editor.putString(KEY_ID, id);
        // Storing email in pref
        editor.putString(USERNAME, username);
        // commit changes
        editor.commit();
    }
    public String getUserId(){
        String id=pref.getString(USER_ID,"");
        return id;
    }

    public boolean checkLoginreturn(){
        // Check login status
        if(!this.isLoggedIn()){
            Log.v("URL", "User Is not logged in");
            return false;
        }else {
            Log.v("URL", "User Is logged in");
            return true;
        }
    }
    public void setDeparmentPoints(String lat,String longt,String radius,String deptname,String maclist,String cellidlist){
        editor.putString(LAT,lat);
        editor.putString(LONGT,longt);
        editor.putString(RADIUS,radius);
        editor.putString(DeptName,deptname);
        editor.putString(MAC_LIST,maclist);
        editor.putString(CELLID_LIST,cellidlist);
        editor.commit();
    }
    public void setMACList(String list){
        editor.putString(MAC_LIST,list);
        this.setLoggedIn(true);
        editor.commit();
    }
    public String getLAT(){
        return pref.getString(LAT,null);

    }
    public String getLongt(){
        return pref.getString(LONGT,null);

    }
    public String getRadius(){
        return pref.getString(RADIUS,null);

    }
    public void setTimeElapsed(String time){
        editor.putString("elapse",time);
        editor.commit();
    }
    public String getTimeElapsed(){
        return pref.getString("elapse",null);

    }
    public void setTotalTimeElapsed(String time){
        editor.putString("totalelapse",time);
        editor.commit();
    }
    public String getTotalTimeElapsed(){
        return pref.getString("totalelapse",null);

    }
    public void setInitialized(boolean val){
        editor.putBoolean("isinitialise",val);
        editor.commit();
    }
    public boolean getInitialized(){
        return pref.getBoolean("isinitialise",false);
    }

    public String[] getMAC_Array()throws Exception{
        String[] array;
        String maclist=pref.getString(MAC_LIST,null);
        if(maclist!=null) {
            array = maclist.split("/");
        }else{
            array=null;
        }
        return array;
    }
    public String getMACString(){
        return pref.getString(MAC_LIST,"");
    }
    public String getCEllString(){
        return pref.getString(CELLID_LIST,"");
    }
    public String getDeptName(){
        return pref.getString(DeptName,"");
    }

    public void setLoggedIn(boolean val){
        editor.putBoolean(IS_LOGIN,val);
        editor.commit();
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}