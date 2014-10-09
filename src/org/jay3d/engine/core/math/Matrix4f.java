package org.jay3d.engine.core.math;

/**
 * The Matrix class maintains a four by four(4x4) matrix in row order.
 *
 * <p>
 *     Matrix4f is used to apply translation and/or rotation, which includes multiple
 *     convenience methods for creating the Matrices, tranforming them by vectors or rotating them.
 * </p>
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Matrix4f {
    private float[][] m;

    /**
     * Constructs and initialises a new four by four(4x4) Matrix
     */
    public Matrix4f(){
        m = new float[4][4];
    }

    /**
     * the current matrix will be set to the identity matrix. This is generally
     * all zeros with ones diagonally.
     * @return
     *      Matrix values set to the identity matrix
     */
    public Matrix4f initIdentity(){
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
        return this;
    }

    /**
     * More commonly known as setTranslation. This will set the Matrix's
     * translation values
     *
     * @param x
     *      Translation on the x-axis
     * @param y
     *      Translation on the y-axis
     * @param z
     *      Translation on the z-axis
     * @return
     *      The current Matrix with the new translation values
     */
    public Matrix4f initTranslation(float x, float y, float z){
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * Transforms the current Matrix by a <code>Vector3f</code>
     * @param v
     *      The <code>Vector(float x, float y, float z)</code> to transform the
     *      matrix with.
     * @return
     *      New <code>Vector3f</code> with it's transformed values.
     */
    public Vector3f transform(Vector3f v){
        return new Vector3f(m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3],
                            m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3],
                            m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3]);
    }

    /**
     * Initialises the Matrix's rotation by x, y, z values
     *
     * @param x
     *      Rotation by x-value
     * @param y
     *      Rotation by y-value
     * @param z
     *      Rotation by z-value
     * @return
     *      Current Matrix with new rotation
     */
    public Matrix4f initRotation(float x, float y, float z)
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();
        x = (float)Math.toRadians(x);
        y = (float)Math.toRadians(y);
        z = (float)Math.toRadians(z);
        rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); rz.m[0][2] = 0;	                 rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  rz.m[1][2] = 0;	                 rz.m[1][3] = 0;
        rz.m[2][0] = 0;	                 rz.m[2][1] = 0;	               rz.m[2][2] = 1;	                 rz.m[2][3] = 0;
        rz.m[3][0] = 0;	                 rz.m[3][1] = 0;	               rz.m[3][2] = 0;	                 rz.m[3][3] = 1;

        rx.m[0][0] = 1;	                 rx.m[0][1] = 0;	               rx.m[0][2] = 0;	                 rx.m[0][3] = 0;
        rx.m[1][0] = 0;	                 rx.m[1][1] = (float)Math.cos(x);  rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0;	                 rx.m[2][1] = (float)Math.sin(x);  rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0;	                 rx.m[3][1] = 0;	               rx.m[3][2] = 0;	                 rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0;	ry.m[0][2] = -(float)Math.sin(y);                ry.m[0][3] = 0;
        ry.m[1][0] = 0;	                 ry.m[1][1] = 1;	ry.m[1][2] = 0;	                                 ry.m[1][3] = 0;
        ry.m[2][0] = (float)Math.sin(y); ry.m[2][1] = 0;	ry.m[2][2] = (float)Math.cos(y);                 ry.m[2][3] = 0;
        ry.m[3][0] = 0;	                 ry.m[3][1] = 0;	ry.m[3][2] = 0;	                                 ry.m[3][3] = 1;
        m = rz.mul(ry.mul(rx)).getM();
        return this;
    }

    /**
     * Initialises the scale of the Matrix by x, y, z values.
     *
     * @param x
     *      Increment of decrement x value
     * @param y
     *      Increment of decrement y value
     * @param z
     *      Increment of decrement z value
     * @return
     *      Current Matrix with new scale values
     */
    public Matrix4f initScale(float x, float y, float z){
        m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * Initialises the Matrix's perspective values. This is mainly used by the Camera class to set things like, field of
     * view, aspect ratio, nearest distance and furthest distance.
     *
     * <p>
     *     Example: initPerspective(90, width/height, 0.001f, 1000f)
     *     Do note that width and height used in the example are usually the Window's width and height.
     * </p>
     * @param fov
     *     The desired field of view to set the matrix to
     * @param aspect
     *     The desired aspect ration to set the matrix to
     * @param zNear
     *     The nearest distance to set the matrix to
     * @param zFar
     *     The furthest distance to set the matrix to
     * @return
     *     Current matrix with new perspective values
     */
    public Matrix4f initPerspective(float fov, float aspect, float zNear, float zFar){
        float tanHalfFOV = (float)Math.tan(fov /2);
        float zRange = zNear - zFar;

        m[0][0] = 1.0f / tanHalfFOV * aspect; m[0][1] = 0;           m[0][2] = 0;                         m[0][3] = 0;
        m[1][0] = 0;                      m[1][1] = 1 / tanHalfFOV; m[1][2] = 0;                      m[1][3] = 0;
        m[2][0] = 0;                      m[2][1] = 0;              m[2][2] = (-zNear - zFar)/zRange; m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;                      m[3][1] = 0;              m[3][2] = 1;                      m[3][3] = 0;

        return this;
    }

    /**
     * Orthographic respesents 3D object in 2D, a derivative of parallel projection.
     *
     * <p>
     *     In computer graphics, orthographic is generally defined by a 6-tuple which is what is used in this engine.
     *     This includes, left, right, bottom, top, near far. These act as the clipping planes, thus form a box consisting
     *     of the minimum corner(left, bottom, -near) and the maximum right, (right, top, -far).
     * </p>
     * @param left
     *      The left value, this forms part of the minimum corner.
     * @param right
     *      The right value, this forms part of the maximum corner.
     * @param bottom
     *      The bottom value, this forms part of the minimum corner.
     * @param top
     *      The top value, this forms part of the maximum corner.
     * @param near
     *      The near value, this forms part of the minimum corner. Do not invert the near value prior to passing it as a
     *      parameter. <code>Matrix4f#initOrthographic</code> does this automatically for you.
     * @param far
     *      The far value, this forms part of the maximum corner, Do not invert the near value prior to passing it as a
     *      parameter. <code>Matrix4f#initOrthographic</code> does this automatically for you.
     * @return
     *      The current matrix with orthographic projection.
     */
    public Matrix4f initOrthographic(float left, float right, float bottom , float top, float near, float far){
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = 2/width;  m[0][1] = 0;        m[0][2] = 0;        m[0][3] = -(right + left)/width;
        m[1][0] = 0;        m[1][1] = 2/height; m[1][2] = 0;        m[1][3] = -(top + bottom)/height;
        m[2][0] = 0;        m[2][1] = 0;        m[2][2] = -2/depth;  m[2][3] = -(far + near)/depth;
        m[3][0] = 0;        m[3][1] = 0;        m[3][2] = 0;        m[3][3] = 1;

        return this;
    }

    /**
     * Initialises the matrix's rotation by converting <code>Vector3f</code> to fit the
     * <code>Matrix#initRotation(Vector3f forward, Vector3f up, Vector3f right)</code>.
     *
     * @param forward
     *      The <code>Vector3f</code> displaying the forward direction.
     * @param up
     *      The <code>Vector3f</code> displaying the up direction.
     * @return
     *      Current Matrix with new rotational values.
     */
    public Matrix4f initRotation(Vector3f forward, Vector3f up){
        forward.normalise();

        Vector3f r = up;
        r.normalise();
        r = r.cross(forward);

        Vector3f u = forward.cross(r);

        return initRotation(forward, u, r);
    }

    /**
     * Initialises the matrix's rotation by using forward, up and right values. Similar to the right-hand rule.
     *
     * @param forward
     *      The <code>Vector3f</code> displaying the forward direction.
     * @param up
     *  The <code>Vector3f</code> displaying the up direction.
     * @param right
     *  The <code>Vector3f</code> displaying the right direction.
     * @return
     *      Current Matrix with new rotational values.
     */
    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
    {
        m[0][0] = right.getX();	m[0][1] = right.getY();	m[0][2] = right.getZ();	m[0][3] = 0;
        m[1][0] = up.getX();	m[1][1] = up.getY();	m[1][2] = up.getZ();	m[1][3] = 0;
        m[2][0] = forward.getX();	m[2][1] = forward.getY();	m[2][2] = forward.getZ();	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
        return this;
    }

    /**
     * Simply multiplies current <code>Matrix</code> by another <code>Matrix</code>.
     *
     * @param matrix
     *      The matrix to multiply current matrix with.
     * @return
     *      Matrix with new multiplied values
     */
    public Matrix4f mul(Matrix4f matrix){
        Matrix4f res = new Matrix4f();

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                res.set(i, j, m[i][0] * matrix.get(0, j)
                            + m[i][1] * matrix.get(1, j)
                            + m[i][2] * matrix.get(2, j)
                            + m[i][3] * matrix.get(3, j));
            }
        }

        return res;
    }

    /**
     * Returns the value of a specified element
     *
     * @param x
     *      Column number
     * @param y
     *      Row number
     * @return
     *      Float element in row[x]column[y]
     */
    public float get(int x, int y){
        return m[x][y];
    }

    /**
     * Sets the value of a specified element to a desired value
     *
     * @param x
     *      Column number
     * @param y
     *      Row number
     * @param value
     *      Value to set in element row[x]column[y]
     */
    public void set(int x, int y, float value){
        this.m[x][y] = value;
    }

    /**
     * Get a 2-dimensional array of the current MAtrix
     *
     * @return
     *      <code>float[][]</code> of current Matrix
     */
    public float[][] getM() {
        float[][] res = new float[4][4];
        for(int i = 0; i < 4; i++){
            System.arraycopy(m[i], 0, res[i], 0, 4);
        }
        return res;
    }

    /**
     * Sets the current Matrix to a specified size
     *
     * @param m
     *      The <code>float[][]</code> to set the current Matrix to
     */
    public void setM(float[][] m) {
        this.m = m;
    }
}
