package ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ch.ethz.inf.vs.a1.pascalo.antitheft.vs_pascalo_antitheft", appContext.getPackageName());
    }

    public static class SpikeMovementDetectorTest implements AlarmCallback {

        SpikeMovementDetector movementDetector;
        boolean result;

        @Before
        public void setup() {
            result = false;
            movementDetector = new SpikeMovementDetector(this, 10);
        }

        @Test
        public void test1() throws Exception {
            float[] values1 = {0.0f, 0.0f, 0.0f};
            result = movementDetector.doAlarmLogic(values1);
            assertFalse(result);
        }

        @Test
        public void test2() throws Exception {
            float[] values1 = {1.1f, 0.5f, 1.0f};
            result = movementDetector.doAlarmLogic(values1);
            assertFalse(result);
        }

        @Test
        public void test3() throws Exception {
            float[] values1 = {5.0f, 5.0f, 0.0f};
            result = movementDetector.doAlarmLogic(values1);
            assertTrue(result);
        }

        @Test
        public void test4() throws Exception {
            float[] values1 = {5.0f, 5.0f, -5.0f};
            result = movementDetector.doAlarmLogic(values1);
            assertTrue(result);
        }

        @Test
        public void test5() throws Exception {
            float[] values1 = {5.0f, 5.0f, -5.5f};
            result = movementDetector.doAlarmLogic(values1);
            assertTrue(result);
        }

        @Test
        public void test6() throws Exception {
            float[] values1 = {-5.0f, -5.0f, -5.0f};
            result = movementDetector.doAlarmLogic(values1);
            assertTrue(result);
        }

        @Test
        public void test7() throws Exception {
            float[] values1 = {3.3f, 3.3f, 3.3f};
            result = movementDetector.doAlarmLogic(values1);
            assertFalse(result);
        }

        @Override
        public void onTimeoutStarted() {
            // Do not do anything
        }
    }
}
