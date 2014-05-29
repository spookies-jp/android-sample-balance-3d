package jp.co.spookies.android.balance3d;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class BalanceView extends GLSurfaceView implements SensorEventListener {
	private BalanceRenderer renderer;
	private float preX, preY;

	public BalanceView(Context context) {
		super(context);
		renderer = new BalanceRenderer();
		setRenderer(renderer);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		renderer.onOrientationChanged(event.values[0], event.values[1]);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// ドラッグに合わせて視点を変更
			renderer.rotate(x - preX, y - preY);
		}
		preX = x;
		preY = y;
		return true;
	}
}
