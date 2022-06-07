package fftlib;

import java.util.Objects;

/* loaded from: classes4.dex */
public class Complex {
    private final double a;
    private final double b;

    public Complex(double d, double d2) {
        this.a = d;
        this.b = d2;
    }

    public String toString() {
        return String.format("hypot: %s, complex: %s+%si", Double.valueOf(hypot()), Double.valueOf(this.a), Double.valueOf(this.b));
    }

    public double hypot() {
        return Math.hypot(this.a, this.b);
    }

    public double phase() {
        return Math.atan2(this.b, this.a);
    }

    public Complex plus(Complex complex) {
        return new Complex(this.a + complex.a, this.b + complex.b);
    }

    public Complex minus(Complex complex) {
        return new Complex(this.a - complex.a, this.b - complex.b);
    }

    public Complex times(Complex complex) {
        double d = this.a;
        double d2 = complex.a;
        double d3 = this.b;
        double d4 = complex.b;
        return new Complex((d * d2) - (d3 * d4), (d * d4) + (d3 * d2));
    }

    public Complex scale(double d) {
        return new Complex(this.a * d, d * this.b);
    }

    public Complex conjugate() {
        return new Complex(this.a, -this.b);
    }

    public Complex reciprocal() {
        double d = this.a;
        double d2 = this.b;
        double d3 = (d * d) + (d2 * d2);
        return new Complex(d / d3, (-d2) / d3);
    }

    public double re() {
        return this.a;
    }

    public double im() {
        return this.b;
    }

    public Complex divides(Complex complex) {
        return times(complex.reciprocal());
    }

    public Complex exp() {
        return new Complex(Math.exp(this.a) * Math.cos(this.b), Math.exp(this.a) * Math.sin(this.b));
    }

    public Complex sin() {
        return new Complex(Math.sin(this.a) * Math.cosh(this.b), Math.cos(this.a) * Math.sinh(this.b));
    }

    public Complex cos() {
        return new Complex(Math.cos(this.a) * Math.cosh(this.b), (-Math.sin(this.a)) * Math.sinh(this.b));
    }

    public Complex tan() {
        return sin().divides(cos());
    }

    public static Complex plus(Complex complex, Complex complex2) {
        return new Complex(complex.a + complex2.a, complex.b + complex2.b);
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Complex complex = (Complex) obj;
        return this.a == complex.a && this.b == complex.b;
    }

    public int hashCode() {
        return Objects.hash(Double.valueOf(this.a), Double.valueOf(this.b));
    }
}
