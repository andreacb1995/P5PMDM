package com.example.andreacarballidop5pmdm.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.andreacarballidop5pmdm.R;

import java.util.Random;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = new Random().nextInt(200);
        String texto= intent.getStringExtra("texto");
        String idChannel = intent.getStringExtra("idChannel");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,idChannel)
                .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                .setContentTitle(texto)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.notify(id,builder.build());
    }
}
