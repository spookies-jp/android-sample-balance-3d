package jp.co.spookies.android.balance3d;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Balance3DActivity extends Activity {
	BalanceView glView;
	SensorManager sensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		glView = new BalanceView(this);
		setContentView(glView);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor != null) {
			sensorManager.registerListener(glView, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
		sensorManager.unregisterListener(glView);
	}
}