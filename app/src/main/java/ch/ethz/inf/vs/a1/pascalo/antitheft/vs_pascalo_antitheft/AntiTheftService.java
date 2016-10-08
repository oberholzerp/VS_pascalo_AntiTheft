package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;


public class AntiTheftService extends Service implements AlarmCallback {

    private int delay;
    private int sensitivity;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        delay = intent.getIntExtra(
                "delay",
                Integer.parseInt(getResources().getStringArray(R.array.delay_value_array)[1])
                );
        sensitivity = intent.getIntExtra(
                "sensitivity",
                Integer.parseInt(getResources().getStringArray(R.array.sensitivity_value_array)[1])
        );

        //create a notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_secure)
                        .setContentTitle("Test")
                        .setContentText("Test")
                        .setOngoing(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);


        //create notification manager
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //show built notification
        notificationManager.notify(17, builder.build());

        //create movement detector
        AbstractMovementDetector movementDetector = new SpikeMovementDetector(this, sensitivity);

        //register movement detector to listen to the sensor
        SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sm.registerListener(movementDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDelayStarted() {
        Log.d("AntiTheftService", "onDelayStarted started");
        SystemClock.sleep(delay*1000);
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(17);
    }
}
