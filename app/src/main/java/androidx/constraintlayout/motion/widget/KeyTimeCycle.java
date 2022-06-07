package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class KeyTimeCycle extends Key {
    public static final int KEY_TYPE = 3;
    private String e;
    private int f = -1;
    private float g = Float.NaN;
    private float h = Float.NaN;
    private float i = Float.NaN;
    private float j = Float.NaN;
    private float k = Float.NaN;
    private float l = Float.NaN;
    private float m = Float.NaN;
    private float n = Float.NaN;
    private float o = Float.NaN;
    private float p = Float.NaN;
    private float q = Float.NaN;
    private float r = Float.NaN;
    private int s = 0;
    private float t = Float.NaN;
    private float u = 0.0f;

    public KeyTimeCycle() {
        this.mType = 3;
        this.d = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        a.a(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTimeCycle));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.g)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.h)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.i)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.j)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.k)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.o)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.p)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.l)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.m)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.n)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("progress");
        }
        if (this.d.size() > 0) {
            Iterator it = this.d.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + ((String) it.next()));
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void setInterpolation(HashMap<String, Integer> hashMap) {
        if (this.f != -1) {
            if (!Float.isNaN(this.g)) {
                hashMap.put("alpha", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.h)) {
                hashMap.put("elevation", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.i)) {
                hashMap.put("rotation", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.j)) {
                hashMap.put("rotationX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.k)) {
                hashMap.put("rotationY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.o)) {
                hashMap.put("translationX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.p)) {
                hashMap.put("translationY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.q)) {
                hashMap.put("translationZ", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.l)) {
                hashMap.put("transitionPathRotate", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.m)) {
                hashMap.put("scaleX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.m)) {
                hashMap.put("scaleY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.r)) {
                hashMap.put("progress", Integer.valueOf(this.f));
            }
            if (this.d.size() > 0) {
                Iterator it = this.d.keySet().iterator();
                while (it.hasNext()) {
                    hashMap.put("CUSTOM," + ((String) it.next()), Integer.valueOf(this.f));
                }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> hashMap) {
        throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007d, code lost:
        if (r1.equals("scaleY") != false) goto L_0x00ce;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addTimeValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.TimeCycleSplineSet> r11) {
        /*
            Method dump skipped, instructions count: 594
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTimeCycle.addTimeValues(java.util.HashMap):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        char c;
        switch (str.hashCode()) {
            case -1812823328:
                if (str.equals("transitionEasing")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1249320806:
                if (str.equals("rotationX")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1249320805:
                if (str.equals("rotationY")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1225497657:
                if (str.equals("translationX")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -4379043:
                if (str.equals("elevation")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 37232917:
                if (str.equals("transitionPathRotate")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 92909918:
                if (str.equals("alpha")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 579057826:
                if (str.equals("curveFit")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1317633238:
                if (str.equals("mTranslationZ")) {
                    c = '\r';
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
                this.g = a(obj);
                return;
            case 1:
                this.f = b(obj);
                return;
            case 2:
                this.h = a(obj);
                return;
            case 3:
                this.r = a(obj);
                return;
            case 4:
                this.i = a(obj);
                return;
            case 5:
                this.j = a(obj);
                return;
            case 6:
                this.k = a(obj);
                return;
            case 7:
                this.m = a(obj);
                return;
            case '\b':
                this.n = a(obj);
                return;
            case '\t':
                this.e = obj.toString();
                return;
            case '\n':
                this.l = a(obj);
                return;
            case 11:
                this.o = a(obj);
                return;
            case '\f':
                this.p = a(obj);
                return;
            case '\r':
                this.q = a(obj);
                return;
            default:
                return;
        }
    }

    /* loaded from: classes.dex */
    private static class a {
        private static SparseIntArray a = new SparseIntArray();

        static {
            a.append(R.styleable.KeyTimeCycle_android_alpha, 1);
            a.append(R.styleable.KeyTimeCycle_android_elevation, 2);
            a.append(R.styleable.KeyTimeCycle_android_rotation, 4);
            a.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
            a.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
            a.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
            a.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
            a.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
            a.append(R.styleable.KeyTimeCycle_motionTarget, 10);
            a.append(R.styleable.KeyTimeCycle_framePosition, 12);
            a.append(R.styleable.KeyTimeCycle_curveFit, 13);
            a.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
            a.append(R.styleable.KeyTimeCycle_android_translationX, 15);
            a.append(R.styleable.KeyTimeCycle_android_translationY, 16);
            a.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
            a.append(R.styleable.KeyTimeCycle_motionProgress, 18);
            a.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
            a.append(R.styleable.KeyTimeCycle_waveOffset, 21);
            a.append(R.styleable.KeyTimeCycle_waveShape, 19);
        }

        public static void a(KeyTimeCycle keyTimeCycle, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        keyTimeCycle.g = typedArray.getFloat(index, keyTimeCycle.g);
                        break;
                    case 2:
                        keyTimeCycle.h = typedArray.getDimension(index, keyTimeCycle.h);
                        break;
                    case 3:
                    case 11:
                    default:
                        Log.e("KeyTimeCycle", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                        break;
                    case 4:
                        keyTimeCycle.i = typedArray.getFloat(index, keyTimeCycle.i);
                        break;
                    case 5:
                        keyTimeCycle.j = typedArray.getFloat(index, keyTimeCycle.j);
                        break;
                    case 6:
                        keyTimeCycle.k = typedArray.getFloat(index, keyTimeCycle.k);
                        break;
                    case 7:
                        keyTimeCycle.m = typedArray.getFloat(index, keyTimeCycle.m);
                        break;
                    case 8:
                        keyTimeCycle.l = typedArray.getFloat(index, keyTimeCycle.l);
                        break;
                    case 9:
                        keyTimeCycle.e = typedArray.getString(index);
                        break;
                    case 10:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (typedArray.peekValue(index).type == 3) {
                                keyTimeCycle.c = typedArray.getString(index);
                                break;
                            } else {
                                keyTimeCycle.b = typedArray.getResourceId(index, keyTimeCycle.b);
                                break;
                            }
                        } else {
                            keyTimeCycle.b = typedArray.getResourceId(index, keyTimeCycle.b);
                            if (keyTimeCycle.b == -1) {
                                keyTimeCycle.c = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 12:
                        keyTimeCycle.a = typedArray.getInt(index, keyTimeCycle.a);
                        break;
                    case 13:
                        keyTimeCycle.f = typedArray.getInteger(index, keyTimeCycle.f);
                        break;
                    case 14:
                        keyTimeCycle.n = typedArray.getFloat(index, keyTimeCycle.n);
                        break;
                    case 15:
                        keyTimeCycle.o = typedArray.getDimension(index, keyTimeCycle.o);
                        break;
                    case 16:
                        keyTimeCycle.p = typedArray.getDimension(index, keyTimeCycle.p);
                        break;
                    case 17:
                        if (Build.VERSION.SDK_INT >= 21) {
                            keyTimeCycle.q = typedArray.getDimension(index, keyTimeCycle.q);
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        keyTimeCycle.r = typedArray.getFloat(index, keyTimeCycle.r);
                        break;
                    case 19:
                        keyTimeCycle.s = typedArray.getInt(index, keyTimeCycle.s);
                        break;
                    case 20:
                        keyTimeCycle.t = typedArray.getFloat(index, keyTimeCycle.t);
                        break;
                    case 21:
                        if (typedArray.peekValue(index).type == 5) {
                            keyTimeCycle.u = typedArray.getDimension(index, keyTimeCycle.u);
                            break;
                        } else {
                            keyTimeCycle.u = typedArray.getFloat(index, keyTimeCycle.u);
                            break;
                        }
                }
            }
        }
    }
}
