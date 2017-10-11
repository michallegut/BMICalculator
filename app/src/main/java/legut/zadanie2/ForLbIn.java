package legut.zadanie2;

class ForLbIn implements CountBMI {
    private final float MIN_MASS=20.0f;
    private final float MAX_MASS=550.0f;
    private final float MIN_HEIGHT=20.0f;
    private final float MAX_HEIGHT=100.0f;

    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if (isValidMass(mass) && isValidHeight(height)) {
            return mass*703/height/height;
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
