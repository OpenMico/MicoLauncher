package androidx.appcompat.app;

/* compiled from: TwilightCalculator.java */
/* loaded from: classes.dex */
class f {
    private static f d;
    public long a;
    public long b;
    public int c;

    f() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static f a() {
        if (d == null) {
            d = new f();
        }
        return d;
    }

    public void a(long j, double d2, double d3) {
        float f = ((float) (j - 946728000000L)) / 8.64E7f;
        float f2 = (0.01720197f * f) + 6.24006f;
        double d4 = f2;
        double sin = (Math.sin(d4) * 0.03341960161924362d) + d4 + (Math.sin(2.0f * f2) * 3.4906598739326E-4d) + (Math.sin(f2 * 3.0f) * 5.236000106378924E-6d) + 1.796593063d + 3.141592653589793d;
        double d5 = (-d3) / 360.0d;
        double round = ((float) Math.round((f - 9.0E-4f) - d5)) + 9.0E-4f + d5 + (Math.sin(d4) * 0.0053d) + (Math.sin(2.0d * sin) * (-0.0069d));
        double asin = Math.asin(Math.sin(sin) * Math.sin(0.4092797040939331d));
        double d6 = 0.01745329238474369d * d2;
        double sin2 = (Math.sin(-0.10471975803375244d) - (Math.sin(d6) * Math.sin(asin))) / (Math.cos(d6) * Math.cos(asin));
        if (sin2 >= 1.0d) {
            this.c = 1;
            this.a = -1L;
            this.b = -1L;
        } else if (sin2 <= -1.0d) {
            this.c = 0;
            this.a = -1L;
            this.b = -1L;
        } else {
            double acos = (float) (Math.acos(sin2) / 6.283185307179586d);
            this.a = Math.round((round + acos) * 8.64E7d) + 946728000000L;
            this.b = Math.round((round - acos) * 8.64E7d) + 946728000000L;
            if (this.b >= j || this.a <= j) {
                this.c = 1;
            } else {
                this.c = 0;
            }
        }
    }
}
