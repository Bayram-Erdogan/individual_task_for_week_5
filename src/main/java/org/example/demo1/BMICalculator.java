package org.example.demo1;

public final class BMICalculator {

    private BMICalculator() {
    }

    public static double calculate(double weightKg, double heightCm) {
        if (weightKg <= 0 || heightCm <= 0) {
            throw new IllegalArgumentException("Weight and height must be greater than zero");
        }

        double heightMeters = heightCm / 100.0;
        return weightKg / (heightMeters * heightMeters);
    }

    public static String classify(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Normal";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obesity";
    }
}
