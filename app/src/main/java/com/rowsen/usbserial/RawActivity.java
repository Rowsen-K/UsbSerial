package com.rowsen.usbserial;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;

public class RawActivity extends AppCompatActivity {
TextView data;
SerialPort serialPort;
BufferedInputStream in;
byte[] temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw);
        data = findViewById(R.id.data);
        Rowsen app = (Rowsen) getApplication();
        if(app.sp!=null){
            try {
                serialPort = new SerialPort(new File("/dev/ttyS1"),Integer.valueOf(app.sp.getString("波特率",null)),0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        in =new BufferedInputStream(serialPort.getInputStream());
        temp = new byte[1024];

       //读取线程,问题：一次接受的数据超过8字节模拟器就挂掉，真机未测试
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    try {
                        final int n = in.read(temp);
                       if(n!=-1){
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   data.append(new String(temp,0,n));
                                   System.out.println("============"+data.getText());
                               }
                           });
                       }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        serialPort.close();
        super.onDestroy();
    }
}
