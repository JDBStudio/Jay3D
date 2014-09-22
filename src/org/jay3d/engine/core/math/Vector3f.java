package org.jay3d.engine.core.math;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Vector3f {
    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float max(){
        return Math.max(x, Math.max(y, z));
    }

    public float dot(Vector3f v){
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    public Vector3f normalise(){
        float length = length();

        x /= length;
        y /= length;
        z /= length;

        return new Vector3f(x, y, z);
    }

    public Vector3f rotate(Vector3f axis, float angle){
        float sinAngle = (float)Math.sin(-angle);
        float cosAngle = (float)Math.cos(-angle);

        return this.cross(axis.mul(sinAngle)).add( //Rotation on local X
                (this.mul(cosAngle)).add( //Rotation on local Z
                        axis.mul(this.dot(axis.mul(1 - cosAngle))))); //Rotation on local Y
    }

    public Vector3f rotate(Quaternion rotation){
        Quaternion conjugate = rotation.conjugate();
        Quaternion w = rotation.mul(this).mul(conjugate);

        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    public Vector3f lerp(Vector3f dest, float lerpFactor){
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    public boolean equals(Vector3f r){
        return x == r.getX() && y == r.getY() && z == r.getZ();
    }

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

    public Vector3f cross(Vector3f v){
        float x_ = y * v.getZ() - z * v.getY();
        float y_ = z * v.getX() - x * v.getZ();
        float z_ = x * v.getY() - y * v.getX();

        return new Vector3f(x_, y_, z_);
    }

    public void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector2f getXY(){ return new Vector2f(x, y); }
    public Vector2f getYZ(){ return new Vector2f(y, z); }
    public Vector2f getZX(){ return new Vector2f(z, x); }

    public Vector2f getYX(){ return new Vector2f(y, x); }
    public Vector2f GETZY(){ return new Vector2f(z, y); }
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
