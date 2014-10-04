package org.jay3d.engine.core.math;

/**
 * Quaternions are also commonly known as versors. They are convenient ways
 * for representing orientations and rotations in 3D space.
 *
 *
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class Quaternion {
    private float x, y, z, w;

    public Quaternion(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Constructs and initialises the Quaternion with a specified axis and angle.
     * Note, the direction you want to rotate is generally the opposite axis.
     * <p>
     *     Example: If you want to rotate your object horizontally, you must rotate it
     *     around the <code>y-axis</code>, this is because you're rotating AROUND the axis
     *     and not ON the axis. If you wish to rotate vertically, you must rotate around the <code>x-axis</code>.
     * </p>
     *
     * @param axis
     *      The desired axis to rotate around on.
     *      <p>
     *          Example: <code>Vector3f(1, 0, 0)</code> rotates around the x-axis.
     *      </p>
     * @param angle
     *      The desired to rotate on.
     */
    public Quaternion(Vector3f axis, float angle){
        float sinHalfAngle = (float)Math.sin(angle / 2);
        float cosHalfAngle = (float)Math.cos(angle / 2);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = cosHalfAngle;
    }

    /**
     * This inisitialises and constructs a Quaternion is relation to a rotation
     * matrix. This method comes from Ken Shoemake's "Quaternion Calculus and Fast Animation" article and thus
     * is not written by me.
     *
     * @param rot
     *      The rotational <code>Matrix</code> used whilst constructing the <code>Quaternion</code>.
     */
    public Quaternion(Matrix4f rot) {
        float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);
        if (trace > 0) {
            float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
            w = 0.25f / s;
            x = (rot.get(1, 2) - rot.get(2, 1)) * s;
            y = (rot.get(2, 0) - rot.get(0, 2)) * s;
            z = (rot.get(0, 1) - rot.get(1, 0)) * s;
        } else {
            if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
                w = (rot.get(1, 2) - rot.get(2, 1)) / s;
                x = 0.25f * s;
                y = (rot.get(1, 0) + rot.get(0, 1)) / s;
                z = (rot.get(2, 0) + rot.get(0, 2)) / s;
            } else if (rot.get(1, 1) > rot.get(2, 2)) {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
                w = (rot.get(2, 0) - rot.get(0, 2)) / s;
                x = (rot.get(1, 0) + rot.get(0, 1)) / s;
                y = 0.25f * s;
                z = (rot.get(2, 1) + rot.get(1, 2)) / s;
            } else {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
                w = (rot.get(0, 1) - rot.get(1, 0)) / s;
                x = (rot.get(2, 0) + rot.get(0, 2)) / s;
                y = (rot.get(1, 2) + rot.get(2, 1)) / s;
                z = 0.25f * s;
            }
        }
        float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        x /= length;
        y /= length;
        z /= length;
        w /= length;
    }

    /**
     * @param dest
     * @param lerpFactor
     * @param shortest
     * @return
     */
    public Quaternion normalLinearInterpolation(Quaternion dest, float lerpFactor, boolean shortest) {
        Quaternion correctedDest = dest;

        if(shortest && this.dot(dest) < 0)
            correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());

        return correctedDest.sub(this).mul(lerpFactor).add(this).normalise();
    }

    /**
     * More commonly known as <code>Slerp</code>, spherical linear interpolation was introduced by
     * Ken shoemake, in context of quaternion interpolation for animating 3D rotation. Thus, this refers
     * to constant-speed motion along unit-radius great circle arc.
     *
     * @param dest
     *      The destination quaternion.
     * @param lerpFactor
     *      The linear interpolation factor.
     * @param shortest
     *      Whether you want it to calculate shrotest possibly distance.
     * @return
     *      Current Quaternion with <code>Slerp</code> added to it.
     */
    public Quaternion sphericalLinearInterpolation(Quaternion dest, float lerpFactor, boolean shortest) {
        final float EPSILON = 1e3f;

        float cos = this.dot(dest);
        Quaternion correctedDest = dest;

        if(shortest && cos < 0){
            cos = -cos;
            correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
        }

        if(Math.abs(cos) >= 1 - EPSILON)
            return normalLinearInterpolation(correctedDest, lerpFactor, false);

        float sin = (float)Math.sqrt(1.0f - cos * cos);
        float angle = (float)Math.atan2(sin, cos);
        float invSin = 1.0f/sin;

        float srcFactor = (float)Math.sin((1.0f - lerpFactor) * angle) * invSin;
        float destFactor = (float)Math.sin((lerpFactor) * angle) * invSin;

        return this.mul(srcFactor).add(correctedDest).mul(destFactor);
    }

    /**
     * Basic quaternion subtraction.
     *
     * @param q
     *      Quaternion to subtract current quaternion by.
     * @return
     *      New quaternion with subtracted values.
     */
    public Quaternion sub(Quaternion q) {
        return new Quaternion(x - q.getX(), y - q.getY(), z - q.getZ(), w - q.getW());
    }

    /**
     * Basic quaternion addition.
     *
     * @param q
     *      Quaternion to add current quaternion by.
     * @return
     *      New quaternion with added values.
     */
    public Quaternion add(Quaternion q) {
        return new Quaternion(x + q.getX(), y + q.getY(), z + q.getZ(), w + q.getW());
    }

    /**
     * Calculates the dot product of current quaternion by another quaternion.
     *
     * @param q
     *      Quaternion to multiple current quaternion by.
     * @return
     *      New quaternion with multiplied values.
     */
    public float dot(Quaternion q) {
        return x * q.getX() + y * q.getY() + z * q.getZ() + w * q.getW();
    }

    /**
     * Calculates the total length of current quaternion.
     *
     * @return
     *      The length of the quaternion.
     */
    public float length() {
        return (float)Math.sqrt(x * x + y * y + z* z + w * w);
    }

    /**
     * Divides each element, x, y, z, w by the total length of the quaternion. This also
     * includes the <code>length()</code> function within the method therefore you shouldn't
     * have to calcualte the length beforehand.
     *
     * @return
     *      New <code>Quaternion</code> with normalised values.
     */
    public Quaternion normalise() {
        float length = length();

        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    /**
     * Negates the x, y, z, w elements of the <code>Quaternion</code>.
     *
     * @return
     *      New <code>Quaternion</code> with conjugated values.
     */
    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    /**
     * Quaternion multiplication with another quaternion.
     *
     * @param q
     *      Quaternion to multiple current quaternion by.
     * @return
     *      New quaternion with multiplied values.
     */
    public Quaternion mul(Quaternion q){
        float w_ = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();
        float x_ = x * q.getW() + w * q.getX() + y * q.getZ() - z * q.getY();
        float y_ = y * q.getW() + w * q.getY() + z * q.getX() - x * q.getZ();
        float z_ = z * q.getW() + w * q.getZ() + x * q.getY() - y * q.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    /**
     * Quaternion multiplication with <code>Vector3f</code>.
     *
     * @param v
     *      <code>Vector3f</code> to multiply current Quaternion by.
     * @return
     *      New Quaternion with multiplied values
     */
    public Quaternion mul(Vector3f v){
        float w_ = -x * v.getX() - y * v.getY() - z * v.getZ();
        float x_ = w * v.getX() + y * v.getZ() - z * v.getY();
        float y_ = w * v.getY() + z * v.getX() - x * v.getZ();
        float z_ = w * v.getZ() + x * v.getY() - y * v.getX();

        return new Quaternion(x_, y_, z_, w_);
    }


    /**
     * Multiplies every element inside the quaternion by a specified float value.
     *
     * @param r
     *      The float value to multiple the <code>Quaternion</code> by.
     * @return
     *      New Quaternion with multiplied values.
     */
    public Quaternion mul(float r)
    {
        return new Quaternion(x * r, y * r, z * r, w * r);
    }

    /**
     * Compares two <code>Quaternions</code> to see if they are equal.
     *
     * @param q
     *      <code>Quaternion</code> to compare current <code>Quaternion</code> by.
     * @return
     *      Whether or not the current <code>Quaternion</code> is equal to the parameter
     */
    public boolean equals(Quaternion q){
        return x == q.getX() && y == q.getY() && z == q.getZ() && w == q.getW();
    }

    /**
     * Sets the x, y, z, w elements inside the <code>Quaternion</code> with their specified values
     *
     * @param x
     *      The value to set the x-element to.
     * @param y
     *      The value to set the y-element to.
     * @param z
     *      The value to set the z-element to.
     * @param w
     *      The value to set the w-element to.
     * @return
     *      Current <code>Quaternion</code> with updated values.
     */
    public Quaternion set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        return this;
    }

    /**
     * Sets current quaternion to a specified <code>Quaternion</code>
     *
     * @param q
     *      Quaternion to set current quaternion to.
     * @return
     *      Current quaternion with updated values.
     */
    public Quaternion set(Quaternion q){
        set(q.getX(), q.getY(), q.getZ(), q.getW());

        return this;
    }

    /**
     * Initialises the <code>Matrix</code> rotation by <code>Quaternion</code> x, y, w, z element values.
     *
     * @return
     *      New Matrix4f with initialised rotation.
     */
    public Matrix4f toRotationMatrix() {
        Vector3f forward = new Vector3f(2.0f * (x*z - w*y), 2.0f * (y*z + w*x), 1.0f - 2.0f * (x*x + y*y));
        Vector3f up = new Vector3f(2.0f * (x*y + w*z), 1.0f - 2.0f * (x*x + z*z), 2.0f * (y*z - w*x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (y*y + z*z), 2.0f * (x*y - w*z), 2.0f * (x*z + w*y));

        return new Matrix4f().initRotation(forward, up, right);
    }

    /**
     * Gets the <code>Vector3f</code> forward by rotating the z-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with forward value.
     */
    public Vector3f getForward() {
        return new Vector3f(0, 0, 1).rotate(this);
    }

    /**
     * Gets the <code>Vector3f</code> backward by rotating the negative z-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with backward value.
     */
    public Vector3f getBack() {
        return new Vector3f(0, 0, -1).rotate(this);
    }

    /**
     * Gets the <code>Vector3f</code> up by rotating the y-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with up value.
     */
    public Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(this);
    }

    /**
     * Gets the <code>Vector3f</code> down by rotating the negative y-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with down value.
     */
    public Vector3f getDown() {
        return new Vector3f(0, -1, 0).rotate(this);
    }

    /**
     * Gets the <code>Vector3f</code> right by rotating the x-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with right value.
     */
    public Vector3f getRight() {
        return new Vector3f(1, 0, 0).rotate(this);
    }

    /**
     * Gets the <code>Vector3f</code> left by rotating the negative x-axis by the current <code>Quaternion</code>.
     *
     * @return
     *      New <code>Vector3f</code> with left value.
     */
    public Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(this);
    }

    /**
     * @return
     *      This <code>Quaternion</code> x-element.
     */
    public float getX() {
        return x;
    }

    /**
     * @param x
     *      The value to set the x-element to.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return
     *      This <code>Quaternion</code> y-element.
     */
    public float getY() {
        return y;
    }

    /**
     * @param y
     *      The value to set the y-element to.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return
     *      This <code>Quaternion</code> z-element.
     */
    public float getZ() {
        return z;
    }

    /**
     * @param z
     *      The value to set the z-element to.
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * @return
     *      This <code>Quaternion</code> w-element.
     */
    public float getW() {
        return w;
    }

    /**
     * @param w
     *      The value to set the w-element to.
     */
    public void setW(float w) {
        this.w = w;
    }
}
