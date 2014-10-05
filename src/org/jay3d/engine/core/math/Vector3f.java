package org.jay3d.engine.core.math;

/**
 * Vector3f is a class that defines a <code>Vector</code> for three points on the
 * x-axis, y-axis and z-axis respectively.
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Vector3f {
    private float x, y, z;

    /**
     * Constructs and initialises a <code>Vector</code> with three points.
     *
     * @param x
     *      Point on x-axis
     * @param y
     *      Point on y-axis
     * @param z
     *      Point on z-axis
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return
     *      The length of the <code>Vector</code>
     */
    public float length(){
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float max(){
        return Math.max(x, Math.max(y, z));
    }

    /**
     * @param v
     *      <code>Vector</code> to perform dot product on.
     * @return
     *      The dot product of two <code>Vectors</code>
     */
    public float dot(Vector3f v){
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    /**
     * @return
     *      New <code>Vector</code> with normalised values.
     */
    public Vector3f normalise(){
        float length = length();

        x /= length;
        y /= length;
        z /= length;

        return new Vector3f(x, y, z);
    }

    /**
     * @param angle
     *      Angle to rotate the <code>Vector</code> by.
     * @return
     *      New <code>Vector3f</code> with rotation applied.
     */
    public Vector3f rotate(Vector3f axis, float angle){
        float sinAngle = (float)Math.sin(-angle);
        float cosAngle = (float)Math.cos(-angle);

        return this.cross(axis.mul(sinAngle)).add( //Rotation on local X
                (this.mul(cosAngle)).add( //Rotation on local Z
                        axis.mul(this.dot(axis.mul(1 - cosAngle))))); //Rotation on local Y
    }
    /**
     * @param rotation
     *      Quaternion to rotate the <code>Vector</code>by.
     * @return
     *      New <code>Vector2f</code> with rotation applied.
     */
    public Vector3f rotate(Quaternion rotation){
        Quaternion conjugate = rotation.conjugate();
        Quaternion w = rotation.mul(this).mul(conjugate);

        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    /**
     * Linear Interpolation for Vector3f
     *
     * @param dest
     *      The destination to interpolate to.
     * @param lerpFactor
     *      The interpolation factor.
     * @return
     *      Current Vector3f with linear interpolation.
     */
    public Vector3f lerp(Vector3f dest, float lerpFactor){
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    /**
     * @param r
     *      <code>Vector3f</code> to compare with.
     * @return
     *      Whether or not current and specified Vectors are equal.
     */
    public boolean equals(Vector3f r){
        return x == r.getX() && y == r.getY() && z == r.getZ();
    }

    /**
     * @return
     *      New <code>Vector3f</code> with current absolute values.
     */
    public Vector3f abs(){
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3f add(Vector3f v){
        return new Vector3f(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Vector3f add(float v){
        return new Vector3f(x + v, y + v, z + v);
    }

    public Vector3f sub(Vector3f v){
        return new Vector3f(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public Vector3f sub(float v){
        return new Vector3f(x - v, y - v, z - v);
    }

    public Vector3f mul(Vector3f v){
        return new Vector3f(x * v.getX(), y * v.getY(), z * v.getZ());
    }

    public Vector3f mul(float v){
        return new Vector3f(x * v, y * v, z * v);
    }

    public Vector3f div(Vector3f v){
        return new Vector3f(x / v.getX(), y / v.getY(), z / v.getZ());
    }

    public Vector3f div(float v){
        return new Vector3f(x / v, y / v, z / v);
    }

    /**
     * Cross-product of two <code>Vector3f</code>
     *
     * @param v
     *      <code>Vector3f</code> to perform cross-product with.
     * @return
     *      The final cross-product of both Vectors.
     */
    public Vector3f cross(Vector3f v){
        float x_ = y * v.getZ() - z * v.getY();
        float y_ = z * v.getX() - x * v.getZ();
        float z_ = x * v.getY() - y * v.getX();

        return new Vector3f(x_, y_, z_);
    }

    /**
     * Sets current <code>Vector3f</code> with specified x, y and z values.
     *
     * @param x
     *      New value for x-axis.
     * @param y
     *      New value for y-axis.
     * @param z
     *      new value for z-axis.
     * @return
     *      Current <code>Vector3f</code> with new axis values.
     */
    public Vector3f set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    /**
     * Sets current <code>Vector3f</code> to another <code>Vector3f</code>.
     *
     * @param v
     *      New <code>Vector3f</code> to set current one to.
     * @return
     *      Current Vector3f with updated values.
     */
    public Vector3f set(Vector3f v){
        set(v.getX(), v.getY(), v.getZ());

        return this;
    }

    /**
     * Swizzling to obtain a Vector2f with just x and y values.
     *
     * @return
     *      New <code>Vector2f</code> with x, y, values.
     */
    public Vector2f getXY(){ return new Vector2f(x, y); }

    /**
     * Swizzling to obtain a Vector2f with just y and z values.
     *
     * @return
     *      New <code>Vector2f</code> with y, z, values.
     */
    public Vector2f getYZ(){ return new Vector2f(y, z); }

    /**
     * Swizzling to obtain a Vector2f with just z and x values.
     *
     * @return
     *      New <code>Vector2f</code> with z, x, values.
     */
    public Vector2f getZX(){ return new Vector2f(z, x); }

    /**
     * Swizzling to obtain a Vector2f with just y and x values.
     *
     * @return
     *      New <code>Vector2f</code> with y, x, values.
     */
    public Vector2f getYX(){ return new Vector2f(y, x); }

    /**
     * Swizzling to obtain a Vector2f with just z and y values.
     *
     * @return
     *      New <code>Vector2f</code> with z, y, values.
     */
    public Vector2f GETZY(){ return new Vector2f(z, y); }

    /**
     * Swizzling to obtain a Vector2f with just x and z values.
     *
     * @return
     *      New <code>Vector2f</code> with x, z, values.
     */
    public Vector2f getXZ(){ return new Vector2f(x, z); }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
