package ysaak.common.util;

public final class MathUtils {
    private MathUtils() { /**/ }

    public static Double nullSafeRound(Double value, int decimals) {
        Double roundedValue;
        if (value != null) {
            double x = Math.pow(10, decimals);
            roundedValue = Math.round(value * x) / x;
        }
        else {
            roundedValue = value;
        }

        return roundedValue;
    }
}
