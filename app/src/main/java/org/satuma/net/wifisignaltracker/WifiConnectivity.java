package org.satuma.net.wifisignaltracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MianGhazanfar on 2/23/2016.
 */
    public class WifiConnectivity {
    private Context _context;
    public WifiConnectivity(Context context){
        _context=context;
    }
    private   WifiManager wifi;
    private WifiInfo wifiInfo;
    private  TelephonyManager telephonyManager;
    private  GsmCellLocation gsmCellLocation;
    public ArrayList<ScanResult> list ;
    private ArrayList<String> apMacArray;
    private String CELLID_LIST="";
    String[] MacArray;
    String[] array;
    //SessionManager sessionManager;

    public  ArrayList<ScanResult> scanWIFI(Context context){
        wifi=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();
        wifiInfo=wifi.getConnectionInfo();
        list = (ArrayList<ScanResult>) wifi.getScanResults();
       //  Log.w("@@@@COnnected Mac&SSID",wifiInfo.getSSID()+" /"+wifiInfo.getBSSID());
        return list;
    }
    public  String scanNetworkCellID(Context context){
        telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        gsmCellLocation= (GsmCellLocation) telephonyManager.getCellLocation();
        int cid=gsmCellLocation.getCid();
        int lac=gsmCellLocation.getLac();
        int psc=gsmCellLocation.getPsc();
        Log.w("***** NetworkProvider",String.valueOf(telephonyManager.getNetworkOperatorName()));
        Log.d("$$$Values from Tele",""+cid+"-"+lac+"-"+psc);
        List<NeighboringCellInfo> neighbors=telephonyManager.getNeighboringCellInfo();
        for(NeighboringCellInfo n:neighbors){
            CELLID_LIST+=String.valueOf(n.getCid())+"+";
            Log.w("*****NiCellTower CID",String.valueOf(n.getCid()));
            Log.w("*****NiCellTower LAC",String.valueOf(n.getLac()));
            Log.w("*****NiCellTower PSC",String.valueOf(n.getPsc()));
            Log.w("*****NiCellTower RSSI",String.valueOf(n.getRssi()));
            Log.w("***** NetworkType",String.valueOf(n.getNetworkType()));
        }
        return CELLID_LIST;
    }
    public boolean isConnectedToAP(ArrayList<ScanResult> scannedArray ,String[] CompanyMacList){
        boolean result=false;
        for(int i=0;i<CompanyMacList.length;i++) {
            for (ScanResult sc : scannedArray) {
                if (sc.BSSID.equals(CompanyMacList[i])){
                    result=true;
                }
            }
        }
            return result;
    }

    public static boolean isMatchedWithAP(Context context){
        boolean result=false;
        SessionManager sessionManager;
        String[] array=null;
        sessionManager=new SessionManager(context);
        WifiConnectivity con = new WifiConnectivity(context);
        if (con.isWIFIConnected(context)) {
            ArrayList<ScanResult> rzlt = con.scanWIFI(context);
            for (ScanResult sc : rzlt) {
                Log.w("Result From rzlt",sc.SSID+">"+sc.BSSID);
            }
            try {
                array = sessionManager.getMAC_Array();
                for (String str : array) {
                    Log.w("Session Mac", str);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.w("ye hai masla", e.toString());
            }
            result = con.isConnectedToAP(rzlt, array);
            Log.w("Matching Result", "" + result);
        }
        return result;
    }

    public  boolean isWIFIConnected(Context mContext){
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi != null || wifi.isConnected();
    }
}
