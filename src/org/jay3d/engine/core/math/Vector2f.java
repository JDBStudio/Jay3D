package org.jay3d.engine.core.math;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y);
    }

    public float max(){
        return Math.max(x, y);
    }

    public float dot(Vector2f v){
        return x * v.getX() + y * v.getY();
    }

    public Vector2f normalise(){
        float length = length();

        return new Vector2f(x / length, y / length);
    }

    public Vector2f rotate(float angle){
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
    }

    public Vector2f set(float x, float y){
        this.x = x;
        this.y = y;

        return this;
    }

    public Vector2f set(Vector2f v){
        set(v.getX(), v.getY());

        return this;
    }

    public Vector2f lerp(Vector2f dest, float lerpFactor){
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    public boolean equals(Vector2f r){
        return x == r.getX() && y == r.getY();
    }

    public float cross(Vector2f r){
        return x * r.getY() - y * r.getX();
    }

    public Vector2f abs()
    {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

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
