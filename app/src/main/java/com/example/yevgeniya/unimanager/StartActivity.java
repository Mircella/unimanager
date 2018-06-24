package com.example.yevgeniya.unimanager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.concurrent.TimeUnit;

public class StartActivity extends AppCompatActivity {
    private Handler handler;
    private Intent mainIntent;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        mainIntent = new Intent(this,MainActivity.class);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                startActivity(mainIntent);
                //StartActivity.this.finish();
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    TimeUnit.SECONDS.sleep(3);
                    handler.sendEmptyMessage(0);
                }catch (InterruptedException e){}
            }
        });
        thread.start();
    }
}
