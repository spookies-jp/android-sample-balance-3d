package jp.co.spookies.android.balance3d.primitive;

import java.util.Random;

/**
 * 球体
 * 
 */
public class Sphere extends Polygon {
	private final float RADIUS = 1.0f;

	/**
	 * 
	 * @param num_theta
	 *            横分割数
	 * @param num_phi
	 *            　縦分割数
	 */
	public Sphere(int num_theta, int num_phi) {
		// 分割数から角度のきざみを計算
		double d_theta = 2 * Math.PI / num_theta;
		double d_phi = Math.PI / num_phi;
		int pt;
		int num = (num_phi - 1) * num_theta;

		float[] vertices = new float[(2 + num) * VERTEX_SIZE];

		// vertices生成
		pt = 0;
		for (int i = 0; i < num_phi + 1; i++) {
			double y = -RADIUS * Math.cos(i * d_phi);
			double r = Math.sqrt(RADIUS * RADIUS - y * y);
			if (i == 0) { // 一番上部
				vertices[pt++] = 0;
				vertices[pt++] = -RADIUS;
				vertices[pt++] = 0;
			} else if (i == num_phi) { // 底部
				vertices[pt++] = 0;
				vertices[pt++] = RADIUS;
				vertices[pt++] = 0;
			} else {
				// 分割数だけ三角形を生成
				for (int j = 0; j < num_theta; j++) {
					vertices[pt++] = (float) (r * Math.cos(j * d_theta));
					vertices[pt++] = (float) y;
					vertices[pt++] = (float) (r * Math.sin(j * d_theta));
				}
			}
		}

		// indices生成
		byte[] indices = new byte[num * 2 * VERTEX_SIZE];
		pt = 0;
		for (int i = 0; i < num_phi; i++) {
			int m = (i - 1) * num_theta;
			for (int j = 0; j < num_theta; j++) {

				if (i == 0) { // 一番上部
					indices[pt++] = 0;
					indices[pt++] = (byte) ((j + 1) % num_theta + 1);
					indices[pt++] = (byte) (j + 1);
				} else if (i == num_phi - 1) { // 底部
					indices[pt++] = (byte) (j + 1 + m);
					indices[pt++] = (byte) ((j + 1 + m) % num_theta + 1 + m);
					indices[pt++] = (byte) (1 + m + num_theta);
				} else {
					// 側面の頂点を指定
					indices[pt++] = (byte) (j + 1 + m);
					indices[pt++] = (byte) ((j + 1) % num_theta + 1 + m);
					indices[pt++] = (byte) (j + 1 + num_theta + m);

					indices[pt++] = (byte) ((j + 1) % num_theta + 1 + num_theta + m);
					indices[pt++] = (byte) (j + 1 + num_theta + m);
					indices[pt++] = (byte) ((j + 1) % num_theta + 1 + m);
				}
			}
		}

		// colors生成
		float[] colors = new float[(2 + (num_phi - 1) * num_theta) * COLOR_SIZE];
		Random rand = new Random();
		for (pt = 0; pt < colors.length;) {
			// 完全ランダムに色を割り当てる
			colors[pt++] = rand.nextFloat();
			colors[pt++] = rand.nextFloat();
			colors[pt++] = rand.nextFloat();
			colors[pt++] = 1.0f;
		}
		array2buffer(vertices, colors, indices);
	}
}