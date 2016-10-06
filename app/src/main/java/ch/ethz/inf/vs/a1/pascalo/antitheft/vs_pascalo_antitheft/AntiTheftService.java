package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AntiTheftService extends Service {
    public AntiTheftService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
