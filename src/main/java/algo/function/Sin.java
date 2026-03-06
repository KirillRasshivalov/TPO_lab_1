package algo.function;

public class Sin {
    public static double sin(double x) {
        double sumNew, sumOld, sum;
        int i = 1;
        sum = sumNew = x;
        do {
            sumOld = sumNew;
            i++;
            sum = sum * x * x / i;
            i++;
            sum = sum / i;
            sum = -sum;
            sumNew = sumOld + sum;
        } while (Math.abs(sumNew - sumOld) > 1e-10);
        return sumNew;
    }

    public static double etalon_sinus(double x) {
        return Math.sin(x);
    }
}
