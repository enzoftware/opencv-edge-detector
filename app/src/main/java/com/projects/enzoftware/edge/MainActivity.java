package com.projects.enzoftware.edge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    public  static String mytag = "ma";

    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(mytag,"ohno");
        }else{
            Log.d(mytag,"a dormir lince");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
