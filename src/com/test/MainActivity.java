package com.test;

import android.support.v7.app.ActionBarActivity;

import java.io.IOException;

import test.common.TestService;
import lipermi.handler.CallHandler;
import lipermi.net.Client;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String serverIP = "192.168.1.7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView ping=(TextView) findViewById(R.id.pingtime);
        final Button btnGet = (Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new Conn().execute();
            }
        });

    }

    class Conn extends AsyncTask<Void, Void, MainActivity> {

        @Override
        protected MainActivity doInBackground(Void... params) {
        	Long aStartTime,aEndTime,total;
            Looper.prepare();
            try {
            	aStartTime = System.currentTimeMillis();
                CallHandler callHandler = new CallHandler();
                Client client = new Client(serverIP, 7777, callHandler);
                TestService testService = (TestService) client.getGlobal(TestService.class);
                String msg = testService.getResponse("");
                //ping.setText(finString);
                //Toast.makeText(MainActivity.this, testService.getResponse("abc"), Toast.LENGTH_SHORT).show();
                aEndTime = System.currentTimeMillis();
                total=aEndTime-aStartTime;
                String totalString=total.toString();
                Toast.makeText(MainActivity.this, totalString, Toast.LENGTH_SHORT).show();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Looper.loop();
            return null;
        }

    }
}
