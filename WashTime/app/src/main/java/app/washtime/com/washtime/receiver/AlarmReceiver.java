package app.washtime.com.washtime.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.main.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    public static int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_arrow_back_white_24dp)
                        .setContentTitle("Notification")
                        .setContentText("This is a test notification")
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis());

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        notificationManager.notify(0, builder.build());
    }
}
