package legut.zadanie2;

class ForKgM implements CountBMI {
    private final float MIN_MASS=10.0f;
    private final float MAX_MASS=250.0f;
    private final float MIN_HEIGHT=0.5f;
    private final float MAX_HEIGHT=2.5f;

    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if (isValidMass(mass) && isValidHeight(height)) {
            return mass/height/height;
        }
        else throw new IllegalArgumentException();
    }
    public boolean isValidMass(float mass) {
        return mass>=MIN_MASS && mass<=MAX_MASS;
    }
    public boolean isValidHeight(float height) {
        return height>=MIN_HEIGHT && height<=MAX_HEIGHT;
    }
}
