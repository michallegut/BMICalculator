package legut.zadanie2;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ForKgMTest {
    private float invalidMassUnder0 = -1.0f;
    private float invalidMassUnderMinimum = 9.0f;
    private float invalidMassOverMaximum = 251.0f;
    private float invalidHeightUnder0 = -1.0f;
    private float invalidHeightUnderMinimum = 0.4f;
    private float invalidHeightOverMaximum = 2.6f;
    private float validMass = 100.0f;
    private float validHeight = 2.0f;
    private float validBMI = 25.0f;
    private ForKgM testedObject = new ForKgM();

    @Test
    public void massUnder0() throws Exception {
        assertFalse(testedObject.isValidMass(invalidMassUnder0));
    }

    @Test
    public void massUnderMinimum() throws Exception {
        assertFalse(testedObject.isValidMass(invalidMassUnderMinimum));
    }

    @Test
    public void massOverMaximum() throws Exception {
        assertFalse(testedObject.isValidMass(invalidMassOverMaximum));
    }

    @Test
    public void validMass() throws Exception {
        assertTrue(testedObject.isValidMass(validMass));
    }

    @Test
    public void heightUnder0() throws Exception {
        assertFalse(testedObject.isValidHeight(invalidHeightUnder0));
    }

    @Test
    public void heightUnderMinimum() throws Exception {
        assertFalse(testedObject.isValidHeight(invalidHeightUnderMinimum));
    }

    @Test
    public void heightOverMaximum() throws Exception {
        assertFalse(testedObject.isValidHeight(invalidHeightOverMaximum));
    }

    @Test
    public void validHeight() throws Exception {
        assertTrue(testedObject.isValidHeight(validHeight));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidHeightAndMassBMI() throws Exception {
        testedObject.countBMI(invalidMassUnder0, invalidHeightUnder0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMassBMI() throws Exception {
        testedObject.countBMI(invalidMassUnder0, validHeight);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidHeightBMI() throws Exception {
        testedObject.countBMI(validMass, invalidHeightUnder0);
    }

    @Test
    public void validHeightAndMassBMI() throws Exception {
        assertEquals(validBMI, testedObject.countBMI(validMass, validHeight));
    }
}
