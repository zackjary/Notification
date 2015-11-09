package com.example.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.RemoteViews;

public class NmService extends Service {
    private static final int NOTIFICATION_ID = 3;
    private Binder binder;
    private NotificationManager nm;
    private RemoteViews remoteview;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyNmService();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void showNotification() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification();
        notification.tickerText = "new message...";
        notification.icon = R.drawable.ic_launcher;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.contentIntent = pi;
        remoteview = new RemoteViews(getPackageName(), R.layout.activity_notify);
        notification.contentView = remoteview;
//      Intent nextIntent = new Intent(NEXT_BROADCAST_NAME);
//      nextIntent.putExtra("FLAG", NEXT_FLAG);
//      PendingIntent nextPIntent = PendingIntent.getBroadcast(this, 0, nextIntent, 0);
//      view.setOnClickPendingIntent(R.id.btn_close, nextPIntent);

        // nm.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);
    }

    public void hideNotification() {
        stopForeground(true);
        nm.cancel(NOTIFICATION_ID);
    }

    private class MyNmService extends INmService.Stub {

        @Override
        public void updateNotification() throws RemoteException {
            showNotification();
        }

        @Override
        public void cancelNotification() throws RemoteException {
            hideNotification();
        }

    }

}
