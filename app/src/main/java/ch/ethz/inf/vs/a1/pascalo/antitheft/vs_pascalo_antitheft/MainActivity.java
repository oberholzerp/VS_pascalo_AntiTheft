package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize toggle button
        ToggleButton tbActivate = (ToggleButton) findViewById(R.id.toggleButton);
        tbActivate.setOnClickListener(this);
        tbActivate.setTextOff("Alarm off. Press to activate!");
        tbActivate.setTextOn("Alarm on. Press to deactivate!");

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onClick(View v){

        switch (v.getId()) {
            case (R.id.toggleButton) :
                ToggleButton tb = (ToggleButton) v;
                if (tb.isChecked()) {

                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

                    String thresh = sharedPref.getString(
                            "sensitivity",
                            getResources().getStringArray(R.array.sensitivity_value_array)[1]
                    );
                    String delay = sharedPref.getString(
                            "delay",
                            getResources().getStringArray(R.array.delay_value_array)[1]
                    );
                    boolean improved = sharedPref.getBoolean(
                            "improved",
                            true
                    );

                    tb.setText(R.string.btn_alarm_on);
                    Intent serviceIntent = new Intent(this, AntiTheftService.class);
                    serviceIntent.putExtra("threshold", Integer.parseInt(thresh));
                    serviceIntent.putExtra("delay", Integer.parseInt(delay));
                    serviceIntent.putExtra("improved", improved);
                    startService(serviceIntent);
                } else {
                    tb.setText(R.string.btn_alarm_off);
                    Intent serviceIntent = new Intent(this, AntiTheftService.class);
                    stopService(serviceIntent);
                }
                break;
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void onClickToggle(View v){

    }


    @Override
    protected void onResume() {
        super.onResume();

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        if (isMyServiceRunning(AntiTheftService.class)) {
            tb.setChecked(true);
            tb.setText(R.string.btn_alarm_on);
        } else {
            tb.setChecked(false);
            tb.setText(R.string.btn_alarm_off);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (isMyServiceRunning(AntiTheftService.class)) {
            Toast.makeText(getApplicationContext(), "The changes will only be apllied after unlocking the device.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.settings :
                Intent myIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(myIntent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }



}
