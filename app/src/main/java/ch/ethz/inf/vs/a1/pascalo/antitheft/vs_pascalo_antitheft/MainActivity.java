package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ToggleButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ToggleButton tbActivate = (ToggleButton) findViewById(R.id.toggleButton1);
        tbActivate.setOnClickListener(this);
    }

    public void onClick(View v){

    }

    public void onClickTest(View v){
        ((Button) v).setText(R.string.btn_alarm_on);
    }

    public void onClickToggle(View v){
        ToggleButton tb = (ToggleButton) v;
        if (tb.isChecked()) {
            tb.setText(R.string.btn_alarm_on);
        } else {
            tb.setText(R.string.btn_alarm_off);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton1);
        if (tb.isChecked()) {
            tb.setText(R.string.btn_alarm_on);
        } else {
            tb.setText(R.string.btn_alarm_off);
        }
    }

}
