package com.rowsen.usbserial;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.UUID;

import android_serialport_api.SerialPort;

public class Rowsen extends Application {
    public String[] level1_raw;
    public SharedPreferences sp;
    public SerialPort serialPort;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static boolean completeflag = false;
    @Override
    public void onCreate() {
        super.onCreate();
        level1_raw = new String[]{"波特率", "数据位", "校验位", "停止位"};
        sp = getSharedPreferences("SerialPortSettings", MODE_PRIVATE);
    }
}
