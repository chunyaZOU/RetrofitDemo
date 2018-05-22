package com.zcy.ygs.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.zcy.ygs.retrofitdemo.MainActivity.MAIN_STR;

public class Main2Activity extends AppCompatActivity {
    public static final String MAIN2_STR="Bbbbbbbbbbbbbb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ((TextView)findViewById(R.id.tv2)).setText(getIntent().getStringExtra("param")+"\n"+MAIN_STR);
    }
}
