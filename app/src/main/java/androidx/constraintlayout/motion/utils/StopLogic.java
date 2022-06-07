package androidx.constraintlayout.motion.utils;

import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

/* loaded from: classes.dex */
public class StopLogic extends MotionInterpolator {
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private float i;
    private int j;
    private String k;
    private boolean l = false;
    private float m;
    private float n;

    public void debug(String str, String str2, float f) {
        Log.v(str, str2 + " ===== " + this.k);
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(this.l ? "backwards" : "forward ");
        sb.append(" time = ");
        sb.append(f);
        sb.append("  stages ");
        sb.append(this.j);
        Log.v(str, sb.toString());
        Log.v(str, str2 + " dur " + this.d + " vel " + this.a + " pos " + this.g);
        if (this.j > 1) {
            Log.v(str, str2 + " dur " + this.e + " vel " + this.b + " pos " + this.h);
        }
        if (this.j > 2) {
            Log.v(str, str2 + " dur " + this.f + " vel " + this.c + " pos " + this.i);
        }
        float f2 = this.d;
        if (f <= f2) {
            Log.v(str, str2 + "stage 0");
            return;
        }
        int i = this.j;
        if (i == 1) {
            Log.v(str, str2 + "end stage 0");
            return;
        }
        float f3 = f - f2;
        float f4 = this.e;
        if (f3 < f4) {
            Log.v(str, str2 + " stage 1");
        } else if (i == 2) {
            Log.v(str, str2 + "end stage 1");
        } else if (f3 - f4 < this.f) {
            Log.v(str, str2 + " stage 2");
        } else {
            Log.v(str, str2 + " end stage 2");
        }
    }

    public float getVelocity(float f) {
        float f2 = this.d;
        if (f <= f2) {
            float f3 = this.a;
            return f3 + (((this.b - f3) * f) / f2);
        }
        int i = this.j;
        if (i == 1) {
            return 0.0f;
        }
        float f4 = f - f2;
        float f5 = this.e;
        if (f4 < f5) {
            float f6 = this.b;
            return f6 + (((this.c - f6) * f4) / f5);
        } else if (i == 2) {
            return this.h;
        } else {
            float f7 = f4 - f5;
            float f8 = this.f;
            if (f7 >= f8) {
                return this.i;
            }
            float f9 = this.c;
            return f9 - ((f7 * f9) / f8);
        }
    }

    private float a(float f) {
        float f2 = this.d;
        if (f <= f2) {
            float f3 = this.a;
            return (f3 * f) + ((((this.b - f3) * f) * f) / (f2 * 2.0f));
        }
        int i = this.j;
        if (i == 1) {
            return this.g;
        }
        float f4 = f - f2;
        float f5 = this.e;
        if (f4 < f5) {
            float f6 = this.g;
            float f7 = this.b;
            return f6 + (f7 * f4) + ((((this.c - f7) * f4) * f4) / (f5 * 2.0f));
        } else if (i == 2) {
            return this.h;
        } else {
            float f8 = f4 - f5;
            float f9 = this.f;
            if (f8 >= f9) {
                return this.i;
            }
            float f10 = this.h;
            float f11 = this.c;
            return (f10 + (f11 * f8)) - (((f11 * f8) * f8) / (f9 * 2.0f));
        }
    }

    public void config(float f, float f2, float f3, float f4, float f5, float f6) {
        this.m = f;
        this.l = f > f2;
        if (this.l) {
            a(-f3, f - f2, f5, f6, f4);
        } else {
            a(f3, f2 - f, f5, f6, f4);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.MotionInterpolator, android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float a = a(f);
        this.n = f;
        return this.l ? this.m - a : this.m + a;
    }

    @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
    public float getVelocity() {
        return this.l ? -getVelocity(this.n) : getVelocity(this.n);
    }

    private void a(float f, float f2, float f3, float f4, float f5) {
        if (f == 0.0f) {
            f = 1.0E-4f;
        }
        this.a = f;
        float f6 = f / f3;
        float f7 = (f6 * f) / 2.0f;
        if (f < 0.0f) {
            float sqrt = (float) Math.sqrt((f2 - ((((-f) / f3) * f) / 2.0f)) * f3);
            if (sqrt < f4) {
                this.k = "backward accelerate, decelerate";
                this.j = 2;
                this.a = f;
                this.b = sqrt;
                this.c = 0.0f;
                this.d = (sqrt - f) / f3;
                this.e = sqrt / f3;
                this.g = ((f + sqrt) * this.d) / 2.0f;
                this.h = f2;
                this.i = f2;
                return;
            }
            this.k = "backward accelerate cruse decelerate";
            this.j = 3;
            this.a = f;
            this.b = f4;
            this.c = f4;
            this.d = (f4 - f) / f3;
            this.f = f4 / f3;
            float f8 = ((f + f4) * this.d) / 2.0f;
            float f9 = (this.f * f4) / 2.0f;
            this.e = ((f2 - f8) - f9) / f4;
            this.g = f8;
            this.h = f2 - f9;
            this.i = f2;
        } else if (f7 >= f2) {
            this.k = "hard stop";
            this.j = 1;
            this.a = f;
            this.b = 0.0f;
            this.g = f2;
            this.d = (2.0f * f2) / f;
        } else {
            float f10 = f2 - f7;
            float f11 = f10 / f;
            if (f11 + f6 < f5) {
                this.k = "cruse decelerate";
                this.j = 2;
                this.a = f;
                this.b = f;
                this.c = 0.0f;
                this.g = f10;
                this.h = f2;
                this.d = f11;
                this.e = f6;
                return;
            }
            float sqrt2 = (float) Math.sqrt((f3 * f2) + ((f * f) / 2.0f));
            float f12 = (sqrt2 - f) / f3;
            this.d = f12;
            float f13 = sqrt2 / f3;
            this.e = f13;
            if (sqrt2 < f4) {
                this.k = "accelerate decelerate";
                this.j = 2;
                this.a = f;
                this.b = sqrt2;
                this.c = 0.0f;
                this.d = f12;
                this.e = f13;
                this.g = ((f + sqrt2) * this.d) / 2.0f;
                this.h = f2;
                return;
            }
            this.k = "accelerate cruse decelerate";
            this.j = 3;
            this.a = f;
            this.b = f4;
            this.c = f4;
            this.d = (f4 - f) / f3;
            this.f = f4 / f3;
            float f14 = ((f + f4) * this.d) / 2.0f;
            float f15 = (this.f * f4) / 2.0f;
            this.e = ((f2 - f14) - f15) / f4;
            this.g = f14;
            this.h = f2 - f15;
            this.i = f2;
        }
    }
}
