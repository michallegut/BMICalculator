package legut.zadanie2;

interface CountBMI {
    float countBMI(float mass, float height) throws Exception;
    boolean isValidMass(float mass);
    boolean isValidHeight(float height);
}
