package org.example.demo1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BMICalculatorTest {

    @Test
    void calculatesBmiFromWeightAndCentimeterHeight() {
        assertEquals(24.69, BMICalculator.calculate(70, 168.4), 0.01);
    }

    @Test
    void rejectsZeroOrNegativeMeasurements() {
        assertThrows(IllegalArgumentException.class, () -> BMICalculator.calculate(0, 170));
        assertThrows(IllegalArgumentException.class, () -> BMICalculator.calculate(70, -170));
    }

    @Test
    void classifiesUnderweightBmi() {
        assertEquals("Underweight", BMICalculator.classify(18.49));
    }

    @Test
    void classifiesNormalBmi() {
        assertEquals("Normal", BMICalculator.classify(18.5));
        assertEquals("Normal", BMICalculator.classify(24.99));
    }

    @Test
    void classifiesOverweightBmi() {
        assertEquals("Overweight", BMICalculator.classify(25.0));
        assertEquals("Overweight", BMICalculator.classify(29.99));
    }

    @Test
    void classifiesObesityBmi() {
        assertEquals("Obesity", BMICalculator.classify(30.0));
    }
}
