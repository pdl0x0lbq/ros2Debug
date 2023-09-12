package com.pdl0x0lbq.ros2debug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.ros2.rcljava.RCLJava;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}