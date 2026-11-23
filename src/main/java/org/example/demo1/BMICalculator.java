package org.example.demo1;

import java.util.Locale;

public final class BMICalculator {

    private BMICalculator() {
    }

    public static double calculate(double weightKg, double heightCm) {
        validateMeasurement(weightKg, "Weight");
        validateMeasurement(heightCm, "Height");

        double heightMeters = heightCm / 100.0;
        return weightKg / (heightMeters * heightMeters);
    }

    public static double calculateFromText(String weightText, String heightText) {
        if (weightText == null || heightText == null
                || weightText.isBlank() || heightText.isBlank()) {
            throw new IllegalArgumentException("Weight and height are required");
        }

        try {
            return calculate(
                    Double.parseDouble(weightText),
                    Double.parseDouble(heightText));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Measurements must be numeric", exception);
        }
    }

    public static String format(double bmi) {
        if (!Double.isFinite(bmi) || bmi < 0) {
            throw new IllegalArgumentException("BMI must be a finite positive number");
        }
        return String.format(Locale.ROOT, "%.2f", bmi);
    }

    public static boolean isRtl(Locale locale) {
        if (locale == null) {
            return false;
        }
        return switch (locale.getLanguage()) {
            case "fa", "ur", "ar", "he" -> true;
            default -> false;
        };
    }

    public static boolean isValidMeasurement(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }
        try {
            double value = Double.parseDouble(text);
            return value > 0 && Double.isFinite(value);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static void validateMeasurement(double value, String name) {
        if (!Double.isFinite(value) || value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
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
