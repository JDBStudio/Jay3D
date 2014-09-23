package org.jay3d.engine.core.math;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Transform {
    private Transform parent;
    private Matrix4f parentMatrix;

    private Vector3f pos;
    private Quaternion rot;
    private Vector3f scale;

    private Vector3f oldPos;
    private Quaternion oldRot;
    private Vector3f oldScale;

    public Transform() {
        pos = new Vector3f(0, 0, 0);
        rot = new Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);

        parentMatrix = new Matrix4f().initIdentity();
    }

    public boolean hasChanged(){

        if(oldPos == null){
            oldPos = new Vector3f(0, 0, 0).set(pos);
            oldRot = new Quaternion(0, 0, 0, 0).set(rot);
            oldScale = new Vector3f(0, 0, 0).set(scale);
            return true;
        }

        if(parent != null && parent.hasChanged())
            return true;

        return !pos.equals(oldPos)
                || !rot.equals(oldRot)
                || !scale.equals(oldScale);
    }

    public Matrix4f getTransformation(){
        Matrix4f translationMatrix = new Matrix4f().initTranslation(
                pos.getX(), pos.getY(), pos.getZ());
        Matrix4f rotationMatrix = rot.toRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(),
                scale.getZ());

        if(parent != null && parent.hasChanged())
            parentMatrix = parent.getTransformation();

        if(oldPos != null) {
            oldPos.set(pos);
            oldRot.set(rot);
            oldScale.set(scale);
        }

        return parentMatrix.mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
    }

    public void setParent(Transform parent) {
        this.parent = parent;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public void setPos(float x, float y, float z){
        this.pos = new Vector3f(x, y, z);
    }

    public void setTranslation(float x, float y, float z) {
        this.pos = new Vector3f(x, y, z);
    }

    public Quaternion getRot() {
        return rot;
    }

    public void setRot(Quaternion rot) {
        this.rot = rot;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }
}
