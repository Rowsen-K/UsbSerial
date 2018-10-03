package com.rowsen.usbserial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;

public class SimulatorActivity extends AppCompatActivity {
EditText data;
Button send;
SerialPort serialPort;
Rowsen app;
BufferedOutputStream out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);
        data = findViewById(R.id.senddata);
        send = findViewById(R.id.send);
        app = (Rowsen) getApplication();
        try {
            serialPort = new SerialPort(new File("/dev/ttyS1"),Integer.valueOf(app.sp.getString("波特率",null)),0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out  = new BufferedOutputStream(serialPort.getOutputStream());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            out.write(data.getText().toString().getBytes());
                            out.flush();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    data.setText("");
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        serialPort.close();
        super.onDestroy();
    }


}
