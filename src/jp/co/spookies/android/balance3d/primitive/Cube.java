package jp.co.spookies.android.balance3d.primitive;

/**
 * 立方体
 *
 */
public class Cube extends Polygon {
    public Cube(){
        float[] vertices = {
                -1.0f, -1.0f, -1.0f,
                 1.0f, -1.0f, -1.0f,
                 1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                 1.0f, -1.0f,  1.0f,
                 1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
        };
        float[] colors = {
                   0,    0, 1.0f, 1.0f,
                1.0f,    0,    0, 1.0f,
                1.0f,    0,    0, 1.0f,
                1.0f, 1.0f,    0, 1.0f,
                1.0f,    0,    0, 1.0f,
                1.0f,    0,    0, 1.0f,
                1.0f,    0, 1.0f, 1.0f,
                   0, 1.0f,    0, 1.0f,
        };

       byte[] indices = {
                0, 4, 5,    0, 5, 1,
                1, 5, 6,    1, 6, 2,
                2, 6, 7,    2, 7, 3,
                3, 7, 4,    3, 4, 0,
                4, 7, 6,    4, 6, 5,
                3, 0, 1,    3, 1, 2
        };
       array2buffer(vertices, colors, indices);
    }
}
