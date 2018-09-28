package com.rowsen.usbserial;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RawActivity extends AppCompatActivity {
TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw);
        data = findViewById(R.id.data);
        Rowsen app = (Rowsen) getApplicationContext();
        SharedPreferences sp = getSharedPreferences("SerialPortSetting",MODE_PRIVATE);
        if(sp!=null){
            for(int n = 0;n<app.level1_raw.length;n++){
                System.out.println("========"+sp.getString(app.level1_raw[n],null));
            }
            System.out.println("----------------------------------");
            System.out.println(sp.getAll());
        }
    }
}
