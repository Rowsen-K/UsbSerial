package com.rowsen.usbserial;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class SetActivity extends AppCompatActivity {
ExpandableListView expList;
Button confirm;
String[] level1 = {"波特率","数据位","校验位","停止位" };
String[][] level2 = {{"4800","9600","38400","115200"},{"7","8"},{"N","奇校验","偶校验"},{"1","n"}};
Rowsen app = (Rowsen) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        expList = findViewById(R.id.expList);
        confirm = findViewById(R.id.confirm);
        final SharedPreferences.Editor ed = getSharedPreferences("SerialPortSetting",MODE_PRIVATE).edit();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int n = 0;n<level1.length;n++){
                    String[] temp = level1[n].split("：");
                    ed.putString(temp[0],temp[1]);
                }
                ed.commit();
            }
        });
        expList.setAdapter(adp);
        expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                level1[i] =app.level1_raw[i]+ "："+level2[i][i1];
                adp.notifyDataSetChanged();
                return true;
            }
        });
    }
    BaseExpandableListAdapter adp = new BaseExpandableListAdapter() {
        public View v;
        @Override
        public int getGroupCount() {
            return level1.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return level2[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return i;
        }

        @Override
        public Object getChild(int i, int i1) {
            return level2[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if(view == null){
                view = View.inflate(SetActivity.this,R.layout.layout_level1,null);
            }
            TextView title = view.findViewById(R.id.title);
            title.setText(level1[i]);
            v = view;
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if(view == null){
                view = View.inflate(SetActivity.this,R.layout.layout_level2,null);
            }
            // ImageView img = view.findViewById(R.id.img);
            TextView item = view.findViewById(R.id.item);
            item.setText(level2[i][i1]);
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    };
}
