package com.rowsen.usbserial;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothDeviceAdapter extends BaseAdapter {
    Context context;
    BluetoothDevice[] list;

    BluetoothDeviceAdapter(Context context, Set<BluetoothDevice> deviceSet) {
        this.context = context;
        list = new BluetoothDevice[deviceSet.size()];
        deviceSet.toArray(list);
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null)
            v = View.inflate(context,R.layout.layout_device_item,null);
        else v = view;
        TextView name = v.findViewById(R.id.deviceName);
        TextView id = v.findViewById(R.id.deviceId);
        name.setText("设备"+i+"："+list[i].getName());
        id.setText("设备地址："+list[i].getAddress());
        return v;
    }
}
