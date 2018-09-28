package com.rowsen.usbserial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button set;
    Button raw;
    Button analysis;
    Button simulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        raw = findViewById(R.id.raw);
        analysis = findViewById(R.id.analysis);
        simulator = findViewById(R.id.simulator);
        set = findViewById(R.id.set);
        set.setOnClickListener(this);
        raw.setOnClickListener(this);
        analysis.setOnClickListener(this);
        simulator.setOnClickListener(this);
/*
        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            System.out.println("=============没有可用的串口");
            return;
        }
        else
            System.out.println("===================串口的个数："+availableDrivers.size());

// Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            System.out.println("================没有可用的串口！");
            return;
        }

// Read some data! Most have just one port (port 0).
        final UsbSerialPort port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            new Thread(){
                @Override
                public void run() {
                    while (true) {
                        byte buffer[] = new byte[16];
                        int numBytesRead = 0;
                        try {
                            numBytesRead = port.read(buffer, 1000);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("Communication", "Read " + numBytesRead + " bytes.");
                        String temp = new String(buffer,0,numBytesRead);
                        sb.append(temp);
                        handler.sendEmptyMessage(0);
                    }
                }
            }.start();

        } catch (IOException e) {
            // Deal with error.
        } finally {
            try {
                port.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 */

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set:
                startActivity(new Intent(this, SetActivity.class));
                break;
            case R.id.raw:
                startActivity(new Intent(this, RawActivity.class));
                break;
            case R.id.analysis:
                startActivity(new Intent(this, AnalysisActivity.class));
                break;
            case R.id.simulator:
                startActivity(new Intent(this, SimulatorActivity.class));
                break;
        }
    }
}
