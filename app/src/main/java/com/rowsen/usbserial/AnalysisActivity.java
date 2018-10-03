package com.rowsen.usbserial;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;

public class AnalysisActivity extends AppCompatActivity {
    TextView gps;
    // LocationManager lm;
    ListView list;
    BluetoothAdapter ble;
    Set<BluetoothDevice> deviceList;
    BluetoothDeviceAdapter deviceAdapter;
    Rowsen app;
    BluetoothSocket socket;
    BluetoothConnect blc;
    Handler handler;
    BluetoothRW rw;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        gps = findViewById(R.id.gps);
        list = findViewById(R.id.deviceList);
        app = (Rowsen) getApplication();
        ble = BluetoothAdapter.getDefaultAdapter();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        gps.append((String) msg.obj);
                        break;
                }
            }
        };
        if (ble.isEnabled()) {
            deviceList = ble.getBondedDevices();
            deviceAdapter = new BluetoothDeviceAdapter(this, deviceList);
            list.setAdapter(deviceAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    BluetoothDevice dev = deviceAdapter.list[i];
                    try {
                        socket = dev.createRfcommSocketToServiceRecord(app.MY_UUID);
                        //连接蓝牙设备之前先要确定是不是扫描模式，如果在扫描是会打断连接过程的，所以先要关闭扫描
                        if (ble.isDiscovering())
                            ble.cancelDiscovery();
                        //开一个线程去连接蓝牙，因为这是一个耗时过程，不能放在ui线程做
                        blc = new BluetoothConnect(AnalysisActivity.this,handler, socket);
                        blc.start();
                       // rw.receive();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(this, "蓝牙未打开！", Toast.LENGTH_SHORT).show();
            ble.enable();
            ble.startDiscovery();
            finish();
            return;
        }

      /*  lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION },0);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
        lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER,true);
        GpsStatus.NmeaListener listener = new GpsStatus.NmeaListener() {

            @Override
            public void onNmeaReceived(long l, final String s) {
                System.out.println("============nmea sentence:"+s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gps.append(s);
                    }
                });
            }
        };
        lm.addNmeaListener(listener);
        */

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(Rowsen.completeflag) blc.close();
    }
}
