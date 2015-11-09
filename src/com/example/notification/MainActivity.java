package com.example.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    private INmService serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service = new Intent();
        service.setClass(getApplicationContext(), NmService.class);
        bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    public void open(View view) {
        try {
            serv.updateNotification();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancel(View view) {
        try {
            serv.cancelNotification();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (conn != null) {
                conn = null;
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serv = INmService.Stub.asInterface(service);
        }
    };
}
