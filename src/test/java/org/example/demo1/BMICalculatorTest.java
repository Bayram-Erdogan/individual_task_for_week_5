package org.example.demo1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

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

    @Test
    void calculatesBmiFromTextAndFormatsResult() {
        double bmi = BMICalculator.calculateFromText("70", "168");
        assertEquals("24.80", BMICalculator.format(bmi));
    }

    @Test
    void rejectsMissingAndNonNumericText() {
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculateFromText(null, "170"));
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculateFromText(" ", "170"));
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculateFromText("seventy", "170"));
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculateFromText("70", "0"));
    }

    @Test
    void rejectsInvalidFormattedBmi() {
        assertThrows(IllegalArgumentException.class, () -> BMICalculator.format(-1));
        assertThrows(IllegalArgumentException.class, () -> BMICalculator.format(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> BMICalculator.format(Double.POSITIVE_INFINITY));
    }

    @ParameterizedTest
    @ValueSource(strings = {"fa", "ur", "ar", "he"})
    void detectsRtlLocales(String language) {
        assertEquals(true, BMICalculator.isRtl(Locale.of(language)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "fr", "vi"})
    void detectsLtrLocales(String language) {
        assertEquals(false, BMICalculator.isRtl(Locale.of(language)));
    }

    @Test
    void handlesNullLocale() {
        assertEquals(false, BMICalculator.isRtl(null));
    }

    @ParameterizedTest
    @CsvSource({
            "70,true",
            "168.5,true",
            "0,false",
            "-10,false",
            "'',false",
            "'  ',false",
            "abc,false"
    })
    void validatesMeasurementText(String text, boolean expected) {
        assertEquals(expected, BMICalculator.isValidMeasurement(text));
    }

    @Test
    void rejectsNullMeasurementText() {
        assertEquals(false, BMICalculator.isValidMeasurement(null));
    }

    @Test
    void rejectsFiniteBoundaryValues() {
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculate(Double.POSITIVE_INFINITY, 170));
        assertThrows(IllegalArgumentException.class,
                () -> BMICalculator.calculate(70, Double.NaN));
    }
}
