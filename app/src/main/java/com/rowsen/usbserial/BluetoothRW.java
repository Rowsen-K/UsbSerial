package com.rowsen.usbserial;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.IOException;

public class BluetoothRW {
    Handler handler;
    BluetoothConnect blc;
    byte[] buf;

    BluetoothRW(final Handler handler, final BluetoothConnect blc) {
        this.handler = handler;
        this.blc = blc;
    }

    public void receive() {
        buf = new byte[1024];
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (!Rowsen.completeflag) {
                }
                int n = 0;
                BufferedInputStream in = blc.in;
                System.out.println("开启读接收数据模式！");
                while (true) {
                    try {
                        n = in.read(buf);
                        if (n != -1) {
                            System.out.println("这是读到的字符：" + new String(buf, 0, n));
                            Message msg = new Message();
                            msg.obj = new String(buf, 0, n,"GBK");
                            msg.what = 0;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
