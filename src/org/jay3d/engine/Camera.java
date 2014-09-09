package org.jay3d.engine;

import org.jay3d.engine.math.Vector3f;
import org.jay3d.util.Time;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Camera {
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public Camera(){
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1 ,0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up){
        this.pos = pos;
        this.forward = forward;
        this.up = up;

        up.normalise();
        forward.normalise();
    }

    public void input(){
        float moveAmt = (float)(10 * Time.getDelta());
        float rotAmt = (float)(100* Time.getDelta());

        if(Input.getKey(Input.KEY_W))
            move(getForward(), moveAmt);
        if(Input.getKey(Input.KEY_S))
            move(getForward(), -moveAmt);
        if(Input.getKey(Input.KEY_A))
            move(getLeft(), moveAmt);
        if(Input.getKey(Input.KEY_D))
            move(getRight(), moveAmt);

        if(Input.getKey(Input.KEY_UP))
            rotateX(-rotAmt);
        if(Input.getKey(Input.KEY_DOWN))
            rotateX(rotAmt);
        if(Input.getKey(Input.KEY_LEFT))
            rotateY(-rotAmt);
        if(Input.getKey(Input.KEY_RIGHT))
            rotateY(rotAmt);
    }

    public void move(Vector3f dir, float amt){
        pos = pos.add(dir.mul(amt));
    }

    public void rotateX(float angle){
        Vector3f hAxis = Y_AXIS.cross(forward);
        hAxis.normalise();

        forward.rotate(angle, hAxis);
        forward.normalise();

        up = forward.cross(hAxis);
        up.normalise();
    }

    public void rotateY(float angle){
        Vector3f hAxis = Y_AXIS.cross(forward);
        hAxis.normalise();

        forward.rotate(angle, Y_AXIS);
        forward.normalise();

        up = forward.cross(hAxis);
        up.normalise();
    }

    public Vector3f getLeft(){
        Vector3f left = forward.cross(up);
        left.normalise();
        return left;
    }

    public Vector3f getRight(){
        Vector3f right = up.cross(forward);
        right.normalise();
        return right;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }
}
