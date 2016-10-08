package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;


public class MyMovementDetector extends AbstractMovementDetector {

    public MyMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {

        return( Math.pow(values[0],2) + Math.pow(values[1], 2) + Math.pow(values[2], 2) > sensitivity);
    }
}
