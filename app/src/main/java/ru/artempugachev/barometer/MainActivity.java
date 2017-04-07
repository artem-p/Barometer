package ru.artempugachev.barometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private final static String TAG = MainActivity.class.getSimpleName();
    SensorManager mSensorManager;
    Sensor mPressureSensor;
    TextView mCurPressureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCurPressureTextView = (TextView) findViewById(R.id.curPressureText);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (mPressureSensor == null) {
            // no pressure sensor, display message
            Toast.makeText(MainActivity.this, R.string.no_sensor, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPressureSensor != null) {
            mSensorManager.registerListener(this, mPressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float pressure = event.values[0];
        mCurPressureTextView.setText(String.valueOf(pressure));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
