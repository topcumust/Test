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

    private String serverIP = "192.168.1.4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
        	//final TextView ping=(TextView) findViewById(R.id.textView2);
        	Long aStartTime=0L,aEndTime=0L;
        	String aTotal="";
            Looper.prepare();
            try {
            	aStartTime = System.currentTimeMillis();
            	CallHandler callHandler = new CallHandler();
                Client client = new Client(serverIP, 7777, callHandler);
                TestService testService = (TestService) client.getGlobal(TestService.class);
                
                String res = testService.getResponse("");
                //ping.setText(finString);
                //Toast.makeText(MainActivity.this, testService.getResponse(""), Toast.LENGTH_SHORT).show();
                
                aEndTime=System.currentTimeMillis();
                aTotal +=(aEndTime-aStartTime);
               
                Toast.makeText(MainActivity.this,aTotal.toString() , Toast.LENGTH_SHORT).show();
                //ping.setText(totalTime.toString());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Looper.loop();
            return null;
        }

    }
}
