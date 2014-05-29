package jp.co.spookies.android.balance3d.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 多面体描画抽象クラス
 * 
 */
public abstract class Polygon {
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	private ByteBuffer indexBuffer;

	public static final int FLOAT_SIZE = Float.SIZE / 8;
	public static final int VERTEX_SIZE = 3;
	public static final int COLOR_SIZE = 4;

	/**
	 * 配列をバッファへ変換
	 * 
	 * @param vertices
	 *            　頂点配列
	 * @param colors
	 *            　頂点色配列
	 * @param indices
	 *            　インデックス配列
	 */
	public void array2buffer(float[] vertices, float[] colors, byte[] indices) {
		vertexBuffer = createFloatBuffer(vertices);
		colorBuffer = createFloatBuffer(colors);
		indexBuffer = createByteBuffer(indices);
	}

	/**
	 * FloatBufferの生成
	 * 
	 * @param array
	 * @return
	 */
	public FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer buffer = ByteBuffer
				.allocateDirect(array.length * FLOAT_SIZE)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(array).position(0);
		return buffer;
	}

	/**
	 * ByteBufferの生成
	 * 
	 * @param byteArray
	 * @return
	 */
	public ByteBuffer createByteBuffer(byte[] byteArray) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(byteArray.length);
		buffer.put(byteArray).position(0);
		return buffer;
	}

	/**
	 * 描画
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		if (vertexBuffer == null || colorBuffer == null || indexBuffer == null) {
			throw new RuntimeException("Buffers don't initialize yet.");
		}
		gl.glFrontFace(GL10.GL_CW);
		gl.glVertexPointer(VERTEX_SIZE, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(COLOR_SIZE, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indexBuffer.capacity(),
				GL10.GL_UNSIGNED_BYTE, indexBuffer);
	}
}
