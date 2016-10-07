package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private AntiTheftService ATS;

    private final String KEY_PREF_AUDIO_LOOP = "audio_loop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize toggle button
        ToggleButton tbActivate = (ToggleButton) findViewById(R.id.toggleButton);
        tbActivate.setOnClickListener(this);
        tbActivate.setTextOff("Alarm off. Press to activate!");
        tbActivate.setTextOn("Alarm on. Press to deactivate!");

        //initialize settings button
        Button btnSettings = (Button) findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(this);

        ATS = new AntiTheftService();


        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case (R.id.toggleButton) :
                ToggleButton tb = (ToggleButton) v;
                if (tb.isChecked()) {
                    tb.setText(R.string.btn_alarm_on);
                    //ATS.onDelayStarted();
                } else {
                    tb.setText(R.string.btn_alarm_off);
                }
                break;
            case (R.id.btn_settings) :
                Intent myIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(myIntent);

                boolean value = true;
                SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                editor.putBoolean("key_stored_boolean", value);
                editor.apply();


                // Retrieve stored value
                boolean val = getPreferences(Context.MODE_PRIVATE).getBoolean("key_stored_boolean",
                        false);
        }
    }


    public void onClickToggle(View v){

    }


    @Override
    protected void onResume() {
        super.onResume();

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        if (tb.isChecked()) {
            tb.setText(R.string.btn_alarm_on);
            //ATS.onDelayStarted();
        } else {
            tb.setText(R.string.btn_alarm_off);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_AUDIO_LOOP)){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean loop = sharedPref.getBoolean(KEY_PREF_AUDIO_LOOP, true);

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
