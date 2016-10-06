package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AntiTheftService ATS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ToggleButton tbActivate = (ToggleButton) findViewById(R.id.toggleButton);
        tbActivate.setOnClickListener(this);
        tbActivate.setTextOff("Alarm off. Press to activate!");
        tbActivate.setTextOn("Alarm on. Press to deactivate!");

        ATS = new AntiTheftService();

    }

    @Override
    public void onClick(View v){
        ToggleButton tb = (ToggleButton) v;
        if (tb.isChecked()) {
            tb.setText(R.string.btn_alarm_on);
            ATS.onDelayStarted();
        } else {
            tb.setText(R.string.btn_alarm_off);
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
            ATS.onDelayStarted();
        } else {
            tb.setText(R.string.btn_alarm_off);
        }
    }

}
