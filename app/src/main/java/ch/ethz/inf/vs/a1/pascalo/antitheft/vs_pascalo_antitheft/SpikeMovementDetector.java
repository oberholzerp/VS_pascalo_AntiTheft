package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft.AlarmCallback;

public class SpikeMovementDetector extends AbstractMovementDetector {

    public SpikeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
        // Using absolute elementwise sum as suggested in the project descriptions
        // Better approach: using elemtentwise quadratic sum instead
        return( Math.abs(values[0]) + Math.abs(values[1]) + Math.abs(values[2]) > sensitivity);
    }
}
