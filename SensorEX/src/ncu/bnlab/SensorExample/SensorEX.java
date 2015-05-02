package ncu.bnlab.SensorExample;
import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.TextView;
public class SensorEX extends Activity
{
private TextView textView;
private SensorManager mSensorManager;
float x, y, z;
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
mSensorManager.registerListener(mSensorListener,
mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
SensorManager.SENSOR_DELAY_FASTEST);
textView = new TextView(this);
setContentView(textView);
}
public void updateTV(float p_x, float p_y, float p_z)
{
}
private final SensorEventListener mSensorListener = new SensorEventListener()
{
public void onSensorChanged(SensorEvent se)
{
x = se.values[0];
y = se.values[1];
z = se.values[2];
textView.setText("x: "+ x +", y: "+ y +", z: " + z);
}
public void onAccuracyChanged(Sensor sensor, int accuracy)
{
}
};
@Override
protected void onResume()
{
super.onResume();
mSensorManager.registerListener(mSensorListener,
mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
SensorManager.SENSOR_DELAY_FASTEST);
}
@Override
protected void onStop()
{
mSensorManager.unregisterListener(mSensorListener);
super.onStop();
}
}