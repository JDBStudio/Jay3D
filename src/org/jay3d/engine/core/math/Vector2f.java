package org.jay3d.engine.core.math;

/**
 * Vector2f is a class that defines a <code>Vector</code> for two points on the
 * x-axis and y-axis.
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Vector2f {
    private float x, y;

    /**
     * Constructs and initialises a <code>Vector</code> with two points.
     *
     * @param x
     *      Point on x-axis
     * @param y
     *      Point on y-axis
     */
    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return
     *      The length of the <code>Vector</code>
     */
    public float length(){
        return (float)Math.sqrt(x * x + y * y);
    }

    public float max(){
        return Math.max(x, y);
    }

    /**
     * @param v
     *      <code>Vector</code> to perform dot product on.
     * @return
     *      The dot product of two <code>Vectors</code>
     */
    public float dot(Vector2f v){
        return x * v.getX() + y * v.getY();
    }

    /**
     * @return
     *      New <code>Vector</code> with normalised values.
     */
    public Vector2f normalise(){
        float length = length();

        return new Vector2f(x / length, y / length);
    }

    /**
     * @param angle
     *      Angle to rotate the <code>Vector</code> by.
     * @return
     *      New <code>Vector2f</code> with rotation applied.
     */
    public Vector2f rotate(float angle){
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
    }

    /**
     * Set the Vector2f's points on both x and y axis.
     *
     * @param x
     *      New value for x-axis
     * @param y
     *      New value for y-axis
     * @return
     *      Current Vector2f with updated points.
     */
    public Vector2f set(float x, float y){
        this.x = x;
        this.y = y;

        return this;
    }

    /**
     * Sets current <code>Vector2f</code> to another <code>Vector2f</code>.
     *
     * @param v
     *      New <code>Vector2f</code> to set current one to.
     * @return
     *      Current Vector2f with updated values.
     */
    public Vector2f set(Vector2f v){
        set(v.getX(), v.getY());

        return this;
    }

    /**
     * Linear Interpolation for Vector2f
     *
     * @param dest
     *      The destination to interpolate to.
     * @param lerpFactor
     *      The interpolation factor.
     * @return
     *      Current Vector2f with linear interpolation.
     */
    public Vector2f lerp(Vector2f dest, float lerpFactor){
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    /**
     * @param r
     *      <code>Vector2f</code> to compare with.
     * @return
     *      Whether or not current and specified Vectors are equal.
     */
    public boolean equals(Vector2f r){
        return x == r.getX() && y == r.getY();
    }

    /**
     * Cross-product of two <code>Vector2f</code>
     *
     * @param v
     *      <code>Vector2f</code> to perform cross-product with.
     * @return
     *      The final cross-product of both Vectors
     */
    public float cross(Vector2f v){
        return x * v.getY() - y * v.getX();
    }

    /**
     * @return
     *      New <code>Vector2f</code> with current absolute values.
     */
    public Vector2f abs()
    {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    /**
     * @param v
     *      <code>Vector2f</code> too add current <code>Vector2f</code> with.
     * @return
     *      New <code>Vector2f</code> with added values.
     */
    public Vector2f add(Vector2f v){
        return new Vector2f(x + v.getX(), y + v.getY());
    }

    public Vector2f add(float v){
        return new Vector2f(x + v, y + v);
    }

    public Vector2f sub(Vector2f v){
        return new Vector2f(x - v.getX(), y - v.getY());
    }

    public Vector2f sub(float v){
        return new Vector2f(x - v, y - v);
    }

    public Vector2f mul(Vector2f v){
        return new Vector2f(x * v.getX(), y * v.getY());
    }

    public Vector2f mul(float v){
        return new Vector2f(x * v, y * v);
    }

    public Vector2f div(Vector2f v){
        return new Vector2f(x / v.getX(), y / v.getY());
    }

    public Vector2f div(float v){
        return new Vector2f(x / v, y / v);
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

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
}
