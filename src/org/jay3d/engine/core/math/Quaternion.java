package org.jay3d.engine.core.math;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Quaternion {
    private float x, y, z, w;

    public Quaternion(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3f axis, float angle){
        float sinHalfAngle = (float)Math.sin(angle / 2);
        float cosHalfAngle = (float)Math.cos(angle / 2);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = cosHalfAngle;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y + z* z + w * w);
    }

    public Quaternion normalise(){
        float length = length();

        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    public Quaternion conjugate(){
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(Quaternion q){
        float w_ = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();
        float x_ = x * q.getW() + w * q.getX() + y * q.getZ() - z * q.getY();
        float y_ = y * q.getW() + w * q.getY() + z * q.getX() - x * q.getZ();
        float z_ = z * q.getW() + w * q.getZ() + x * q.getY() - y * q.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector3f v){
        float w_ = -x * v.getX() - y * v.getY() - z * v.getZ();
        float x_ = w * v.getX() + y * v.getZ() - z * v.getY();
        float y_ = w * v.getY() + z * v.getX() - x * v.getZ();
        float z_ = w * v.getZ() + x * v.getY() - y * v.getX();

        return new Quaternion(x_, y_, z_, w_);
    }


    public Quaternion mul(float r)
    {
        return new Quaternion(x * r, y * r, z * r, w * r);
    }

    public boolean equals(Quaternion q){
        return x == q.getX() && y == q.getY() && z == q.getZ() && w == q.getW();
    }

    public Quaternion set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = z;

        return this;
    }

    public Quaternion set(Quaternion q){
        set(q.getX(), q.getY(), q.getZ(), q.getW());

        return this;
    }

    public Matrix4f toRotationMatrix() {
        Vector3f forward = new Vector3f(2.0f * (x*z - w*y), 2.0f * (y*z + w*x), 1.0f - 2.0f * (x*x + y*y));
        Vector3f up = new Vector3f(2.0f * (x*y + w*z), 1.0f - 2.0f * (x*x + z*z), 2.0f * (y*z - w*x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (y*y + z*z), 2.0f * (x*y - w*z), 2.0f * (x*z + w*y));

        return new Matrix4f().initRotation(forward, up, right);
    }

    public Vector3f getForward() {
        return new Vector3f(0, 0, 1).rotate(this);
    }

    public Vector3f getBack() {
        return new Vector3f(0, 0, -1).rotate(this);
    }

    public Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(this);
    }

    public Vector3f getDown() {
        return new Vector3f(0, -1, 0).rotate(this);
    }

    public Vector3f getRight() {
        return new Vector3f(1, 0, 0).rotate(this);
    }

    public Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(this);
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }
}
