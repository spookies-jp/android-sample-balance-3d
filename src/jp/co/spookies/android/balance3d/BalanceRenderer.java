package jp.co.spookies.android.balance3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.co.spookies.android.balance3d.primitive.Cube;
import jp.co.spookies.android.balance3d.primitive.Sphere;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class BalanceRenderer implements GLSurfaceView.Renderer {
	private Cube cube;
	private Sphere sphere;
	private float rotation;
	private float[] force = new float[] { 0.0f, 0.0f, 0.0f };
	private float[] velocity = new float[] { 0.0f, 0.0f, 0.0f };
	private float[] position = new float[] { 0.0f, 0.0f, 0.0f };
	private float boardSide = 3.0f;
	private float cubeBottomRadius = 0.6f;
	private final float K = 0.00009f;
	private float angleX, angleY;

	public BalanceRenderer() {
		cube = new Cube();
		sphere = new Sphere(10, 5);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		update();

		// 画面のクリア
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		gl.glPushMatrix();
		{
			// 画面の回転
			gl.glRotatef(angleX, 0, 1, 0);
			gl.glRotatef(angleY, 1, 0, 0);

			gl.glPushMatrix();
			{
				// 球体の描画
				gl.glTranslatef(position[0], position[1], position[2]);
				gl.glRotatef(rotation, position[1], -position[0], 0);
				sphere.draw(gl);
			}
			gl.glPopMatrix();

			// ボードの描画
			gl.glTranslatef(0.0f, 0.0f, 1.1f);
			gl.glScalef(boardSide, boardSide, 0.1f);
			cube.draw(gl);
		}
		gl.glPopMatrix();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// 表示領域の設定
		gl.glViewport(0, 0, width, height);

		// 投影変換のスタック
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		// 透視投影
		float ratio = (float) width / height;
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

		// 幾何変換のスタック
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		// 視点の設定
		GLU.gluLookAt(gl, 0.0f, 0.0f, -5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// クオリティの設定
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		// カリング
		gl.glEnable(GL10.GL_CULL_FACE);
		// スムーズシェーディング
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 隠面処理
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// 配列の有効化
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	}

	private void update() {
		for (int i = 0; i < 3; i++) {
			velocity[i] += force[i];
			position[i] += velocity[i];
		}

		// 球体の回転
		rotation = (float) (Math.sqrt(position[0] * position[0] + position[1]
				* position[1]) * 180 / Math.PI);

		// 落下判定
		if (Math.abs(position[0]) > boardSide + cubeBottomRadius
				|| Math.abs(position[1]) > boardSide + cubeBottomRadius) {
			force[2] = 98f * K;
		}
	}

	/**
	 * 画面の傾きに合わせて力を変える
	 * 
	 * @param theta
	 *            横方向の回転
	 * @param phi
	 *            縦方向の回転
	 */
	public void onOrientationChanged(float theta, float phi) {
		force[0] = theta * K;
		force[1] = -phi * K;
	}

	/**
	 * 視点の回転
	 * 
	 * @param x
	 * @param y
	 */
	public void rotate(float x, float y) {
		angleX += x;
		angleY -= y;
	}
}
