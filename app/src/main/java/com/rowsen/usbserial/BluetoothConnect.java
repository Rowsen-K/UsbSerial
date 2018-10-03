package com.rowsen.usbserial;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class BluetoothConnect extends Thread {
    Context context;
    BluetoothSocket socket;
    Handler handler;
    public BufferedOutputStream out = null;
    public BufferedInputStream in = null;
    public  BluetoothRW rw;
    BluetoothConnect(Context context,Handler handler, BluetoothSocket socket) {
        this.context = context;
        this.handler = handler;
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            socket.connect();
            while (!socket.isConnected()) {
            }
            System.out.println("蓝牙连接完成！");
            in = new BufferedInputStream(socket.getInputStream());
            out = new BufferedOutputStream(socket.getOutputStream());
            Rowsen.completeflag = true;
            rw = new BluetoothRW(handler,this);
            rw.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
