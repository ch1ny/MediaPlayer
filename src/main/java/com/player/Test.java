package com.player;

import java.util.Scanner;

/**
 * @Author SDU德布罗煜
 * @Date 2021/7/19 12:42
 * @Description
 * @Version 1.0
 */

public class Test {

    static double k, a, b, s, r;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] inputs = input.split(" ");
        k = Double.parseDouble(inputs[0]);
        a = Double.parseDouble(inputs[1]);
        b = Double.parseDouble(inputs[2]);
        s = Double.parseDouble(inputs[3]);
        r = Double.parseDouble(inputs[4]);
        double res = 0;
        for (double i = s; i <= r; i++) {
            res += C(k, f(i));
        }
        System.out.println(res);
    }

    private static double C(double m, double n) {
        double s = 1, p = 1;
        if (n - m > m) {
            m = n - m;
        }
        for (double i = 1; i <= (n - m); i++) {
            p *= i;
        }
        for (double i = (m + 1); i <= n; i++) {
            s *= i;
        }
        return ( s / p );
    }

    private static double f(double i) {
        if (i <= 2) {
            return 1;
        }
        return a * f(i - 1) + b * f(i - 2);
    }

}
