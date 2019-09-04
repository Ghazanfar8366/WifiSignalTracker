package org.satuma.net.AccessPoints;
import org.satuma.net.wifisignaltracker.*;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class ScanAccessPoints extends AppCompatActivity {
SessionManager manager;
    private ListView listview;
    private Button btnScan,btnNext,btnBack;
    static List<ScanResult> list ;
    ArrayList<String> AParray;
    ArrayAdapter<String> APAdapter;
    public ArrayList<ScanResult> scanresult;
    public ArrayList<String> ssidlist;
    private String MAC_LIST;
    private SparseBooleanArray sp;
    private int isScanbtnPressd=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wifi_scan);
        scanresult = new ArrayList<ScanResult>();
        ssidlist = new ArrayList<String>();
        manager=new SessionManager(this);
            listview = (ListView) findViewById(R.id.idlistview);
            listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listview.setItemChecked(2, true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MAC_LIST = "";
                 sp = listview.getCheckedItemPositions();
                for (int i = 0; i < sp.size(); i++) {
                    MAC_LIST += ssidlist.get(sp.keyAt(i)) + "";
                }
                MAC_LIST=pickMac_List();
                //Log.w("##########MACList", MAC_LIST + "--");
            }
        });
        btnScan= (Button) findViewById(R.id.btnscan);
        btnNext= (Button) findViewById(R.id.btn_next);
        btnBack= (Button) findViewById(R.id.btnback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btnBack(v);
            }
        });
        AParray=new ArrayList<String>();
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiConnectivity con;
                con = new WifiConnectivity(ScanAccessPoints.this);
                try {
                    scanresult = con.scanWIFI(ScanAccessPoints.this);
                    //Log.w("Length", scanresult.size() + "");
                    for (ScanResult sc : scanresult ) {
                        ssidlist.add(String.valueOf(sc.SSID));
                        // BSSID_LIST.put(String.valueOf(sc.SSID),String.valueOf(sc.BSSID));
                    }
                } catch (Exception e) {
                    //  Log.w("Ly bi", "A e o masla" + e);
                    Toast.makeText(ScanAccessPoints.this,"Problem:"+e.toString(),Toast.LENGTH_SHORT).show();
                }
                Log.w("Ly bi", "Ab Okay Hai");
                APAdapter = new ArrayAdapter<String>(ScanAccessPoints.this, android.R.layout.simple_list_item_multiple_choice, ssidlist);
                listview.setAdapter(APAdapter);
                isScanbtnPressd+=1;
//                try {
//                    CELLID_LIST = con.scanNetworkCellID(ScanAccessPoints.this);
//                }catch (NullPointerException e){
//                    Toast.makeText(ScanAccessPoints.this,"Please insert sim card.",Toast.LENGTH_SHORT).show();
//                }
                //Log.d("CELL_ID_LIST",CELLID_LIST);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScanbtnPressd < 1) {
                    Toast.makeText(ScanAccessPoints.this, "Please Scan Access Points First.", Toast.LENGTH_SHORT).show();
                } else {
                    MAC_LIST = "";
                    sp = listview.getCheckedItemPositions();
                    MAC_LIST = pickMac_List();
                    Log.w("@@@@@@MACLIST",MAC_LIST);
                    manager.setMACList("");
                    manager.setMACList(MAC_LIST);
                    startActivity(new Intent(ScanAccessPoints.this,org.satuma.net.wifisignaltracker.SSIDListWithStrength.class));
                    finish();
                    //  Log.w("MAC_LIST", MAC_LIST);
                    //Log.w(deptName + deptRadius, deptlat + deptlongt);
                    //Log.w("Cell_id", CELLID_LIST);

                }
            }
        });

        //return view;

    }
    public String pickMac_List() {
        //int i = 0;
        String MacList="";
        Log.w("#####Value of sp",sp.size()+"");
        for (int k=0;k<sp.size();k++) {
            for (ScanResult sc : scanresult) {
                if (ssidlist.get(sp.keyAt(k)).equals(String.valueOf(sc.SSID))) {
                    MacList += sc.BSSID + "/";
                    Log.w("@@@@@@@MACLIST ",MacList);
                }
            }

        }
        return MacList;
    }
    public void btnBack(View view){
        finish();
//        AddDepartFragment fragment = new AddDepartFragment();
//        // fragment.setArguments(arguments);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        //transaction.addToBackStack(null);
//        transaction.commit();
    }
}
