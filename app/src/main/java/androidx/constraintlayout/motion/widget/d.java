package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TouchResponse.java */
/* loaded from: classes.dex */
public class d {
    private static final float[][] p = {new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}, new float[]{0.5f, 1.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}};
    private static final float[][] q = {new float[]{0.0f, -1.0f}, new float[]{0.0f, 1.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}};
    private float m;
    private float n;
    private final MotionLayout o;
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = -1;
    private int e = -1;
    private int f = -1;
    private float g = 0.5f;
    private float h = 0.5f;
    private float i = 0.0f;
    private float j = 1.0f;
    private boolean k = false;
    private float[] l = new float[2];
    private float r = 4.0f;
    private float s = 1.2f;
    private boolean t = true;
    private float u = 1.0f;
    private int v = 0;
    private float w = 10.0f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(Context context, MotionLayout motionLayout, XmlPullParser xmlPullParser) {
        this.o = motionLayout;
        a(context, Xml.asAttributeSet(xmlPullParser));
    }

    public void a(boolean z) {
        if (z) {
            float[][] fArr = q;
            fArr[4] = fArr[3];
            fArr[5] = fArr[2];
            float[][] fArr2 = p;
            fArr2[5] = fArr2[2];
            fArr2[6] = fArr2[1];
        } else {
            float[][] fArr3 = q;
            fArr3[4] = fArr3[2];
            fArr3[5] = fArr3[3];
            float[][] fArr4 = p;
            fArr4[5] = fArr4[1];
            fArr4[6] = fArr4[2];
        }
        float[][] fArr5 = p;
        int i = this.a;
        this.h = fArr5[i][0];
        this.g = fArr5[i][1];
        float[][] fArr6 = q;
        int i2 = this.b;
        this.i = fArr6[i2][0];
        this.j = fArr6[i2][1];
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.OnSwipe);
        a(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    private void a(TypedArray typedArray) {
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.OnSwipe_touchAnchorId) {
                this.d = typedArray.getResourceId(index, this.d);
            } else if (index == R.styleable.OnSwipe_touchAnchorSide) {
                this.a = typedArray.getInt(index, this.a);
                float[][] fArr = p;
                int i2 = this.a;
                this.h = fArr[i2][0];
                this.g = fArr[i2][1];
            } else if (index == R.styleable.OnSwipe_dragDirection) {
                this.b = typedArray.getInt(index, this.b);
                float[][] fArr2 = q;
                int i3 = this.b;
                this.i = fArr2[i3][0];
                this.j = fArr2[i3][1];
            } else if (index == R.styleable.OnSwipe_maxVelocity) {
                this.r = typedArray.getFloat(index, this.r);
            } else if (index == R.styleable.OnSwipe_maxAcceleration) {
                this.s = typedArray.getFloat(index, this.s);
            } else if (index == R.styleable.OnSwipe_moveWhenScrollAtTop) {
                this.t = typedArray.getBoolean(index, this.t);
            } else if (index == R.styleable.OnSwipe_dragScale) {
                this.u = typedArray.getFloat(index, this.u);
            } else if (index == R.styleable.OnSwipe_dragThreshold) {
                this.w = typedArray.getFloat(index, this.w);
            } else if (index == R.styleable.OnSwipe_touchRegionId) {
                this.e = typedArray.getResourceId(index, this.e);
            } else if (index == R.styleable.OnSwipe_onTouchUp) {
                this.c = typedArray.getInt(index, this.c);
            } else if (index == R.styleable.OnSwipe_nestedScrollFlags) {
                this.v = typedArray.getInteger(index, 0);
            } else if (index == R.styleable.OnSwipe_limitBoundsTo) {
                this.f = typedArray.getResourceId(index, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f, float f2) {
        this.m = f;
        this.n = f2;
        this.k = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(MotionEvent motionEvent, MotionLayout.MotionTracker motionTracker, int i, MotionScene motionScene) {
        float f;
        int i2;
        float f2;
        motionTracker.addMovement(motionEvent);
        switch (motionEvent.getAction()) {
            case 0:
                this.m = motionEvent.getRawX();
                this.n = motionEvent.getRawY();
                this.k = false;
                return;
            case 1:
                this.k = false;
                motionTracker.computeCurrentVelocity(1000);
                float xVelocity = motionTracker.getXVelocity();
                float yVelocity = motionTracker.getYVelocity();
                float progress = this.o.getProgress();
                int i3 = this.d;
                if (i3 != -1) {
                    this.o.a(i3, progress, this.h, this.g, this.l);
                } else {
                    float min = Math.min(this.o.getWidth(), this.o.getHeight());
                    float[] fArr = this.l;
                    fArr[1] = this.j * min;
                    fArr[0] = min * this.i;
                }
                float f3 = this.i;
                float[] fArr2 = this.l;
                float f4 = fArr2[0];
                float f5 = this.j;
                float f6 = fArr2[1];
                if (f3 != 0.0f) {
                    f = xVelocity / fArr2[0];
                } else {
                    f = yVelocity / fArr2[1];
                }
                float f7 = !Float.isNaN(f) ? (f / 3.0f) + progress : progress;
                if (f7 != 0.0f && f7 != 1.0f && (i2 = this.c) != 3) {
                    this.o.touchAnimateTo(i2, ((double) f7) < 0.5d ? 0.0f : 1.0f, f);
                    if (0.0f >= progress || 1.0f <= progress) {
                        this.o.setState(MotionLayout.f.FINISHED);
                        return;
                    }
                    return;
                } else if (0.0f >= f7 || 1.0f <= f7) {
                    this.o.setState(MotionLayout.f.FINISHED);
                    return;
                } else {
                    return;
                }
            case 2:
                float rawY = motionEvent.getRawY() - this.n;
                float rawX = motionEvent.getRawX() - this.m;
                if (Math.abs((this.i * rawX) + (this.j * rawY)) > this.w || this.k) {
                    float progress2 = this.o.getProgress();
                    if (!this.k) {
                        this.k = true;
                        this.o.setProgress(progress2);
                    }
                    int i4 = this.d;
                    if (i4 != -1) {
                        this.o.a(i4, progress2, this.h, this.g, this.l);
                    } else {
                        float min2 = Math.min(this.o.getWidth(), this.o.getHeight());
                        float[] fArr3 = this.l;
                        fArr3[1] = this.j * min2;
                        fArr3[0] = min2 * this.i;
                    }
                    float f8 = this.i;
                    float[] fArr4 = this.l;
                    if (Math.abs(((f8 * fArr4[0]) + (this.j * fArr4[1])) * this.u) < 0.01d) {
                        float[] fArr5 = this.l;
                        fArr5[0] = 0.01f;
                        fArr5[1] = 0.01f;
                    }
                    if (this.i != 0.0f) {
                        f2 = rawX / this.l[0];
                    } else {
                        f2 = rawY / this.l[1];
                    }
                    float max = Math.max(Math.min(progress2 + f2, 1.0f), 0.0f);
                    if (max != this.o.getProgress()) {
                        this.o.setProgress(max);
                        motionTracker.computeCurrentVelocity(1000);
                        this.o.c = this.i != 0.0f ? motionTracker.getXVelocity() / this.l[0] : motionTracker.getYVelocity() / this.l[1];
                    } else {
                        this.o.c = 0.0f;
                    }
                    this.m = motionEvent.getRawX();
                    this.n = motionEvent.getRawY();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(float f, float f2) {
        this.m = f;
        this.n = f2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float c(float f, float f2) {
        this.o.a(this.d, this.o.getProgress(), this.h, this.g, this.l);
        if (this.i != 0.0f) {
            float[] fArr = this.l;
            if (fArr[0] == 0.0f) {
                fArr[0] = 1.0E-7f;
            }
            return (f * this.i) / this.l[0];
        }
        float[] fArr2 = this.l;
        if (fArr2[1] == 0.0f) {
            fArr2[1] = 1.0E-7f;
        }
        return (f2 * this.j) / this.l[1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(float f, float f2) {
        float f3;
        boolean z = false;
        this.k = false;
        float progress = this.o.getProgress();
        this.o.a(this.d, progress, this.h, this.g, this.l);
        float f4 = this.i;
        float[] fArr = this.l;
        float f5 = fArr[0];
        float f6 = this.j;
        float f7 = fArr[1];
        if (f4 != 0.0f) {
            f3 = (f * f4) / fArr[0];
        } else {
            f3 = (f2 * f6) / fArr[1];
        }
        if (!Float.isNaN(f3)) {
            progress += f3 / 3.0f;
        }
        if (progress != 0.0f) {
            float f8 = 1.0f;
            boolean z2 = progress != 1.0f;
            if (this.c != 3) {
                z = true;
            }
            if (z && z2) {
                MotionLayout motionLayout = this.o;
                int i = this.c;
                if (progress < 0.5d) {
                    f8 = 0.0f;
                }
                motionLayout.touchAnimateTo(i, f8, f3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(float f, float f2) {
        float f3;
        float f4 = this.i;
        float f5 = this.j;
        float progress = this.o.getProgress();
        if (!this.k) {
            this.k = true;
            this.o.setProgress(progress);
        }
        this.o.a(this.d, progress, this.h, this.g, this.l);
        float f6 = this.i;
        float[] fArr = this.l;
        if (Math.abs((f6 * fArr[0]) + (this.j * fArr[1])) < 0.01d) {
            float[] fArr2 = this.l;
            fArr2[0] = 0.01f;
            fArr2[1] = 0.01f;
        }
        float f7 = this.i;
        if (f7 != 0.0f) {
            f3 = (f * f7) / this.l[0];
        } else {
            f3 = (f2 * this.j) / this.l[1];
        }
        float max = Math.max(Math.min(progress + f3, 1.0f), 0.0f);
        if (max != this.o.getProgress()) {
            this.o.setProgress(max);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        View view;
        int i = this.d;
        if (i != -1) {
            view = this.o.findViewById(i);
            if (view == null) {
                Log.e("TouchResponse", "cannot find TouchAnchorId @id/" + Debug.getName(this.o.getContext(), this.d));
            }
        } else {
            view = null;
        }
        if (view instanceof NestedScrollView) {
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: androidx.constraintlayout.motion.widget.d.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    return false;
                }
            });
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: androidx.constraintlayout.motion.widget.d.2
                @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                public void onScrollChange(NestedScrollView nestedScrollView2, int i2, int i3, int i4, int i5) {
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float b() {
        return this.s;
    }

    public float c() {
        return this.r;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d() {
        return this.t;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RectF a(ViewGroup viewGroup, RectF rectF) {
        View findViewById;
        int i = this.e;
        if (i == -1 || (findViewById = viewGroup.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int e() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RectF b(ViewGroup viewGroup, RectF rectF) {
        View findViewById;
        int i = this.f;
        if (i == -1 || (findViewById = viewGroup.findViewById(i)) == null) {
            return null;
        }
        rectF.set(findViewById.getLeft(), findViewById.getTop(), findViewById.getRight(), findViewById.getBottom());
        return rectF;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float f(float f, float f2) {
        return (f * this.i) + (f2 * this.j);
    }

    public String toString() {
        return this.i + " , " + this.j;
    }

    public int f() {
        return this.v;
    }
}
