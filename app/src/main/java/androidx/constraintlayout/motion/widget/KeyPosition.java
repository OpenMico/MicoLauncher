package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyPosition extends a {
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    String e = null;
    int f = UNSET;
    int g = 0;
    float h = Float.NaN;
    float i = Float.NaN;
    float j = Float.NaN;
    float k = Float.NaN;
    float l = Float.NaN;
    float m = Float.NaN;
    int n = 0;
    private float p = Float.NaN;
    private float q = Float.NaN;

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    public KeyPosition() {
        this.mType = 2;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        a.b(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyPosition));
    }

    @Override // androidx.constraintlayout.motion.widget.a
    void a(int i, int i2, float f, float f2, float f3, float f4) {
        switch (this.n) {
            case 1:
                a(f, f2, f3, f4);
                return;
            case 2:
                a(i, i2);
                return;
            default:
                b(f, f2, f3, f4);
                return;
        }
    }

    private void a(int i, int i2) {
        float f = this.j;
        float f2 = 0;
        this.p = ((i - 0) * f) + f2;
        this.q = ((i2 - 0) * f) + f2;
    }

    private void a(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = this.j;
        float f8 = this.k;
        this.p = f + (f5 * f7) + ((-f6) * f8);
        this.q = f2 + (f6 * f7) + (f5 * f8);
    }

    private void b(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        float f7 = 0.0f;
        float f8 = Float.isNaN(this.j) ? 0.0f : this.j;
        float f9 = Float.isNaN(this.m) ? 0.0f : this.m;
        float f10 = Float.isNaN(this.k) ? 0.0f : this.k;
        if (!Float.isNaN(this.l)) {
            f7 = this.l;
        }
        this.p = (int) (f + (f8 * f5) + (f7 * f6));
        this.q = (int) (f2 + (f5 * f9) + (f6 * f10));
    }

    @Override // androidx.constraintlayout.motion.widget.a
    public void positionAttributes(View view, RectF rectF, RectF rectF2, float f, float f2, String[] strArr, float[] fArr) {
        switch (this.n) {
            case 1:
                a(rectF, rectF2, f, f2, strArr, fArr);
                return;
            case 2:
                a(view, rectF, rectF2, f, f2, strArr, fArr);
                return;
            default:
                b(rectF, rectF2, f, f2, strArr, fArr);
                return;
        }
    }

    void a(RectF rectF, RectF rectF2, float f, float f2, String[] strArr, float[] fArr) {
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float centerX2 = rectF2.centerX() - centerX;
        float centerY2 = rectF2.centerY() - centerY;
        float hypot = (float) Math.hypot(centerX2, centerY2);
        if (hypot < 1.0E-4d) {
            System.out.println("distance ~ 0");
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            return;
        }
        float f3 = centerX2 / hypot;
        float f4 = centerY2 / hypot;
        float f5 = f2 - centerY;
        float f6 = f - centerX;
        float f7 = ((f3 * f5) - (f6 * f4)) / hypot;
        float f8 = ((f3 * f6) + (f4 * f5)) / hypot;
        if (strArr[0] == null) {
            strArr[0] = "percentX";
            strArr[1] = "percentY";
            fArr[0] = f8;
            fArr[1] = f7;
        } else if ("percentX".equals(strArr[0])) {
            fArr[0] = f8;
            fArr[1] = f7;
        }
    }

    void a(View view, RectF rectF, RectF rectF2, float f, float f2, String[] strArr, float[] fArr) {
        rectF.centerX();
        rectF.centerY();
        rectF2.centerX();
        rectF2.centerY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (strArr[0] == null) {
            strArr[0] = "percentX";
            fArr[0] = f / width;
            strArr[1] = "percentY";
            fArr[1] = f2 / height;
        } else if ("percentX".equals(strArr[0])) {
            fArr[0] = f / width;
            fArr[1] = f2 / height;
        } else {
            fArr[1] = f / width;
            fArr[0] = f2 / height;
        }
    }

    void b(RectF rectF, RectF rectF2, float f, float f2, String[] strArr, float[] fArr) {
        float centerX = rectF.centerX();
        float centerY = rectF.centerY();
        float centerX2 = rectF2.centerX() - centerX;
        float centerY2 = rectF2.centerY() - centerY;
        if (strArr[0] == null) {
            strArr[0] = "percentX";
            fArr[0] = (f - centerX) / centerX2;
            strArr[1] = "percentY";
            fArr[1] = (f2 - centerY) / centerY2;
        } else if ("percentX".equals(strArr[0])) {
            fArr[0] = (f - centerX) / centerX2;
            fArr[1] = (f2 - centerY) / centerY2;
        } else {
            fArr[1] = (f - centerX) / centerX2;
            fArr[0] = (f2 - centerY) / centerY2;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.a
    public boolean intersects(int i, int i2, RectF rectF, RectF rectF2, float f, float f2) {
        a(i, i2, rectF.centerX(), rectF.centerY(), rectF2.centerX(), rectF2.centerY());
        return Math.abs(f - this.p) < 20.0f && Math.abs(f2 - this.q) < 20.0f;
    }

    /* loaded from: classes.dex */
    private static class a {
        private static SparseIntArray a = new SparseIntArray();

        static {
            a.append(R.styleable.KeyPosition_motionTarget, 1);
            a.append(R.styleable.KeyPosition_framePosition, 2);
            a.append(R.styleable.KeyPosition_transitionEasing, 3);
            a.append(R.styleable.KeyPosition_curveFit, 4);
            a.append(R.styleable.KeyPosition_drawPath, 5);
            a.append(R.styleable.KeyPosition_percentX, 6);
            a.append(R.styleable.KeyPosition_percentY, 7);
            a.append(R.styleable.KeyPosition_keyPositionType, 9);
            a.append(R.styleable.KeyPosition_sizePercent, 8);
            a.append(R.styleable.KeyPosition_percentWidth, 11);
            a.append(R.styleable.KeyPosition_percentHeight, 12);
            a.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(KeyPosition keyPosition, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (typedArray.peekValue(index).type == 3) {
                                keyPosition.c = typedArray.getString(index);
                                break;
                            } else {
                                keyPosition.b = typedArray.getResourceId(index, keyPosition.b);
                                break;
                            }
                        } else {
                            keyPosition.b = typedArray.getResourceId(index, keyPosition.b);
                            if (keyPosition.b == -1) {
                                keyPosition.c = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 2:
                        keyPosition.a = typedArray.getInt(index, keyPosition.a);
                        break;
                    case 3:
                        if (typedArray.peekValue(index).type == 3) {
                            keyPosition.e = typedArray.getString(index);
                            break;
                        } else {
                            keyPosition.e = Easing.NAMED_EASING[typedArray.getInteger(index, 0)];
                            break;
                        }
                    case 4:
                        keyPosition.o = typedArray.getInteger(index, keyPosition.o);
                        break;
                    case 5:
                        keyPosition.g = typedArray.getInt(index, keyPosition.g);
                        break;
                    case 6:
                        keyPosition.j = typedArray.getFloat(index, keyPosition.j);
                        break;
                    case 7:
                        keyPosition.k = typedArray.getFloat(index, keyPosition.k);
                        break;
                    case 8:
                        float f = typedArray.getFloat(index, keyPosition.i);
                        keyPosition.h = f;
                        keyPosition.i = f;
                        break;
                    case 9:
                        keyPosition.n = typedArray.getInt(index, keyPosition.n);
                        break;
                    case 10:
                        keyPosition.f = typedArray.getInt(index, keyPosition.f);
                        break;
                    case 11:
                        keyPosition.h = typedArray.getFloat(index, keyPosition.h);
                        break;
                    case 12:
                        keyPosition.i = typedArray.getFloat(index, keyPosition.i);
                        break;
                    default:
                        Log.e("KeyPosition", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                        break;
                }
            }
            if (keyPosition.a == -1) {
                Log.e("KeyPosition", "no frame position");
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        char c;
        switch (str.hashCode()) {
            case -1812823328:
                if (str.equals("transitionEasing")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1127236479:
                if (str.equals("percentWidth")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1017587252:
                if (str.equals("percentHeight")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -827014263:
                if (str.equals("drawPath")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -200259324:
                if (str.equals("sizePercent")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 428090547:
                if (str.equals("percentX")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 428090548:
                if (str.equals("percentY")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.e = obj.toString();
                return;
            case 1:
                this.g = b(obj);
                return;
            case 2:
                this.h = a(obj);
                return;
            case 3:
                this.i = a(obj);
                return;
            case 4:
                float a2 = a(obj);
                this.h = a2;
                this.i = a2;
                return;
            case 5:
                this.j = a(obj);
                return;
            case 6:
                this.k = a(obj);
                return;
            default:
                return;
        }
    }
}
