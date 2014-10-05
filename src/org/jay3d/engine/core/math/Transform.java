package org.jay3d.engine.core.math;

/**
 * Transformation class represents translation, rotation and scale
 * inside one object.
 *
 * @author Juxhin Dyrmishi Brigjaj
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

    /**
     * Constructs and initialises a <code>Transform</code> with default position, rotation and scale.
     * <p>
     *     Default position: <code>Vector3f( 0, 0, 0 )</code>(Origin).
     * </p>
     * <p>
     *     Default rotation: <code>Quaternion( 0, 0, 0, 1 )</code>(No rotation).
     * </p>
     * <p>
     *     Default scale: <code>Vector3f( 1, 1, 1 )</code>(Default scale).
     * </p>
     */
    public Transform() {
        pos = new Vector3f(0, 0, 0);
        rot = new Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);

        parentMatrix = new Matrix4f().initIdentity();
    }

    /**
     * If the <code>Transform</code> does not exist it will be set to default values. Otherwise
     * if it does already exist, it will be updated to the current position, rotation and scale.
     */
    public void update(){
        if(oldPos != null) {
            oldPos.set(pos);
            oldRot.set(rot);
            oldScale.set(scale);
        }else{
            oldPos = new Vector3f(0, 0, 0).set(pos).add(1.0f);
            oldRot = new Quaternion(0, 0, 0, 0).set(rot).mul(0.5f);
            oldScale = new Vector3f(0, 0, 0).set(scale).add(1.0f);

        }
    }

    /**
     * Applies object rotation using angle and axis.
     *
     * @param axis
     *      The axis to rotate on.
     * @param angle
     *      The angle to rotate the object by.
     */
    public void rotate(Vector3f axis, float angle){
        rot = new Quaternion(axis, angle).mul(rot).normalise();
    }

    /**
     * Whether or not the current <code>Transform</code> is equal to the previous transform.
     *
     * @return
     *      Whether <code>Transform</code> has changed or not.
     */
    public boolean hasChanged(){
        return parent != null
                && parent.hasChanged()
                || !pos.equals(oldPos)
                || !rot.equals(oldRot)
                || !scale.equals(oldScale);

    }

    /**
     * Gets a whole Matrix containing all the <code>Transform</code> information, including position, rotation and scale.
     *
     * @return
     *      Parent matrix with updated transformation values.
     */
    public Matrix4f getTransformation(){
        Matrix4f translationMatrix = new Matrix4f().initTranslation(
                pos.getX(), pos.getY(), pos.getZ());
        Matrix4f rotationMatrix = rot.toRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        if(parent != null && parent.hasChanged())
            parentMatrix = parent.getTransformation();

        return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
    }

    /**
     * @return
     *      The <code>Transform</code> parent matrix.
     */
    private Matrix4f getParentMatrix(){
        if(parent != null && parent.hasChanged())
            parentMatrix = parent.getTransformation();

        return parentMatrix;
    }


    /**
     * @return
     *      Returns parent rotation multiplied by current rotation value.
     */
    public Quaternion getTransformedRot(){
        Quaternion parentRotation = new Quaternion(0, 0, 0, 1);
        if(parent != null)
            parentRotation = parent.getTransformedRot();

        return parentRotation.mul(rot);
    }

    public void lookAt(Vector3f point, Vector3f up) {
        rot = getLookAtDirection(point, up);
    }

    public Quaternion getLookAtDirection(Vector3f point, Vector3f up) {
        return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalise(), up));
    }

    /**
     * @return
     *      Parent Matrix position
     */
    public Vector3f getTranformedPos(){
        return getParentMatrix().transform(pos);
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
