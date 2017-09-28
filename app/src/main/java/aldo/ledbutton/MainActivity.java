package aldo.ledbutton;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {
    private Gpio mLedGpio = null;
    private boolean mLedVal = true;

    Thread mThread = new Thread() {
        public void run() {
            while (true) {
                // put your main code here, to run repeatedly:

                try {
                    if (mLedVal) {
                        mLedGpio.setValue(false);
                        mLedVal = false;
                    } else {
                        mLedGpio.setValue(true);
                        mLedVal = true;
                    }
                } catch (IOException e) {}

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // put your setup code here, to run once:

        PeripheralManagerService pioService = new PeripheralManagerService();

        try {
            mLedGpio = pioService.openGpio("BCM6");
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mLedGpio.setValue(true);
        } catch (IOException e) {}

        mThread.start();
    }
}