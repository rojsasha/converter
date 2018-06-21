package com.example.alex.laboratoryone;


public class ConvertMath {
    public String calcLength(int a, int b, String value) {
        double[] length = {1609344, 1000000, 1000, 10, 1};
        if (value.hashCode() == ".".hashCode()) value = "0.";
        if (a == b) {
            return value;

        } else {

            double result = length[a] / length[b] * Double.parseDouble(value);
            return formatResult(result);
        }
    }

    public String calcMass(int a, int b, String value) {
        double[] mass = {1000000, 1000, 453.59237, 28.349523125, 5, 1};
        if (value.hashCode() == ".".hashCode()) value = "0.";

        if (a == b) {
            return value;
        } else {
            double result = mass[a] / mass[b] * Double.parseDouble(value);

            return formatResult(result);
        }
    }

   public String calcCurrency(String a, String b, String value) {
        if (value.hashCode() == ".".hashCode()) value = "0.";
        if (a == b) {
            return value;
        } else {
            double result = Double.parseDouble(a) / Double.parseDouble(b) * Double.parseDouble(value);

            return formatResult(result);
        }
    }

   public String exchange(String a, String b) {
        return formatResult(Double.parseDouble(a) / Double.parseDouble(b));
    }

    public String formatResult(double d) {
        if (d % 1 != 0) {
            return String.format("%f", d).replaceAll("0*$", "").replace(",", ".");
        } else {
            return String.format("%s", (long) d);
        }
    }

}
