package algo.function;

public class Sin {

    public static double factorial(int n) {
        double ans = 1;
        for (int i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }

    public double sin(double x, int iter) {
        if (iter < 0) {
            throw new IllegalArgumentException("Неверные аргументы на входе функции.");
        }
        double tot = 0;
        for (int i = 0; i < iter; i++) {
            tot += Math.pow(-1, i) * (Math.pow(x, 2 * i + 1) / factorial(2 * i + 1));
        }
        return tot;
    }
}
