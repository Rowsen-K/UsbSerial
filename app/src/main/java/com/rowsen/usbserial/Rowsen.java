package com.rowsen.usbserial;

import android.app.Application;
import android.content.SharedPreferences;

public class Rowsen extends Application {
  public  String[] level1_raw = {"波特率","数据位","校验位","停止位" };
  public SharedPreferences sp = getSharedPreferences("SerialPortSetting",MODE_PRIVATE);

}
