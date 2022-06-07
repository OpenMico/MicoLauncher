package fftlib;

/* loaded from: classes4.dex */
public class FFT {
    public static Complex[] fft(Complex[] complexArr) {
        int length = complexArr.length;
        if (length == 1) {
            return new Complex[]{complexArr[0]};
        }
        if (length % 2 == 0) {
            int i = length / 2;
            Complex[] complexArr2 = new Complex[i];
            for (int i2 = 0; i2 < i; i2++) {
                complexArr2[i2] = complexArr[i2 * 2];
            }
            Complex[] fft = fft(complexArr2);
            for (int i3 = 0; i3 < i; i3++) {
                complexArr2[i3] = complexArr[(i3 * 2) + 1];
            }
            Complex[] fft2 = fft(complexArr2);
            Complex[] complexArr3 = new Complex[length];
            for (int i4 = 0; i4 < i; i4++) {
                double d = ((i4 * (-2)) * 3.141592653589793d) / length;
                Complex complex = new Complex(Math.cos(d), Math.sin(d));
                complexArr3[i4] = fft[i4].plus(complex.times(fft2[i4]));
                complexArr3[i4 + i] = fft[i4].minus(complex.times(fft2[i4]));
            }
            return complexArr3;
        }
        throw new IllegalArgumentException("n is not a power of 2");
    }

    public static double[] fft(double[] dArr, int i) {
        int length = dArr.length;
        if (length == 1) {
            return dArr;
        }
        Complex[] complexArr = new Complex[length];
        double[] dArr2 = new double[length / 2];
        for (int i2 = 0; i2 < length; i2++) {
            complexArr[i2] = new Complex(dArr[i2], 0.0d);
        }
        Complex[] fft = fft(complexArr);
        for (int i3 = 0; i3 < dArr2.length; i3++) {
            dArr2[i3] = Math.sqrt(Math.pow(fft[i3].re(), 2.0d) + Math.pow(fft[i3].im(), 2.0d)) / dArr.length;
        }
        return dArr2;
    }

    public static Complex[] ifft(Complex[] complexArr) {
        int length = complexArr.length;
        Complex[] complexArr2 = new Complex[length];
        for (int i = 0; i < length; i++) {
            complexArr2[i] = complexArr[i].conjugate();
        }
        Complex[] fft = fft(complexArr2);
        for (int i2 = 0; i2 < length; i2++) {
            fft[i2] = fft[i2].conjugate();
        }
        for (int i3 = 0; i3 < length; i3++) {
            fft[i3] = fft[i3].scale(1.0d / length);
        }
        return fft;
    }

    public static Complex[] cconvolve(Complex[] complexArr, Complex[] complexArr2) {
        if (complexArr.length == complexArr2.length) {
            int length = complexArr.length;
            Complex[] fft = fft(complexArr);
            Complex[] fft2 = fft(complexArr2);
            Complex[] complexArr3 = new Complex[length];
            for (int i = 0; i < length; i++) {
                complexArr3[i] = fft[i].times(fft2[i]);
            }
            return ifft(complexArr3);
        }
        throw new IllegalArgumentException("Dimensions don't agree");
    }

    public static Complex[] convolve(Complex[] complexArr, Complex[] complexArr2) {
        Complex complex = new Complex(0.0d, 0.0d);
        Complex[] complexArr3 = new Complex[complexArr.length * 2];
        for (int i = 0; i < complexArr.length; i++) {
            complexArr3[i] = complexArr[i];
        }
        for (int length = complexArr.length; length < complexArr.length * 2; length++) {
            complexArr3[length] = complex;
        }
        Complex[] complexArr4 = new Complex[complexArr2.length * 2];
        for (int i2 = 0; i2 < complexArr2.length; i2++) {
            complexArr4[i2] = complexArr2[i2];
        }
        for (int length2 = complexArr2.length; length2 < complexArr2.length * 2; length2++) {
            complexArr4[length2] = complex;
        }
        return cconvolve(complexArr3, complexArr4);
    }

    public static void show(Complex[] complexArr, String str) {
        System.out.println(str);
        System.out.println("-------------------");
        for (int i = 0; i < 4096; i++) {
            System.out.println(complexArr[i]);
        }
        System.out.println();
    }

    public static double fun(int i) {
        return Math.sin(i * 15.0f);
    }

    public static double getY(double[] dArr) {
        double d = 0.0d;
        int i = 0;
        for (int i2 = 0; i2 < dArr.length; i2++) {
            if (dArr[i2] > d) {
                d = dArr[i2];
                i = i2;
            }
        }
        int i3 = i + 1;
        log(String.format("x： %s ， y: %s", Integer.valueOf(i3), Double.valueOf(d)));
        log(String.format("频率： %sHz", Float.valueOf(i3 / 4096.0f)));
        log(String.format("频率2： %sHz", Float.valueOf((4096 - i3) / 4096.0f)));
        log(String.format("振幅： %s", Double.valueOf(d)));
        return d;
    }

    public static void log(String str) {
        System.out.println(str);
    }
}
