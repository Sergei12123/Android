package ru.mirea.ivanov.notificationapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "com.mirea.asd.notification.ANDROID";
    private int IDENTIFICATE_MSG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnMsgNotifClicked(View view) {
        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent resIntent = new Intent(this, MainActivity.class);
        PendingIntent resPendIntent = PendingIntent.getActivity(this, 0, resIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            notifChannel.setDescription("Description");
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.RED);
            notifChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notifChannel.enableVibration(true);
            notifManager.createNotificationChannel(notifChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("NotifText for MIREA")
                .setWhen(System.currentTimeMillis())
                .setProgress(100, 50, false)
                .setContentIntent(resPendIntent);
        Notification notification = builder.build();

        notifManager.notify(IDENTIFICATE_MSG++, notification);
    }
}