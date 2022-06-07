package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class KeyCycle extends Key {
    public static final int KEY_TYPE = 4;
    private String e = null;
    private int f = 0;
    private int g = -1;
    private float h = Float.NaN;
    private float i = 0.0f;
    private float j = Float.NaN;
    private int k = -1;
    private float l = Float.NaN;
    private float m = Float.NaN;
    private float n = Float.NaN;
    private float o = Float.NaN;
    private float p = Float.NaN;
    private float q = Float.NaN;
    private float r = Float.NaN;
    private float s = Float.NaN;
    private float t = Float.NaN;
    private float u = Float.NaN;
    private float v = Float.NaN;

    public KeyCycle() {
        this.mType = 4;
        this.d = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        a.b(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyCycle));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.l)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.m)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.n)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.p)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.s)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.o)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.t)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.u)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.v)) {
            hashSet.add("translationZ");
        }
        if (this.d.size() > 0) {
            Iterator it = this.d.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + ((String) it.next()));
            }
        }
    }

    public void addCycleValues(HashMap<String, KeyCycleOscillator> hashMap) {
        for (String str : hashMap.keySet()) {
            if (str.startsWith("CUSTOM")) {
                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.d.get(str.substring(7));
                if (constraintAttribute != null && constraintAttribute.getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE) {
                    hashMap.get(str).setPoint(this.a, this.g, this.k, this.h, this.i, constraintAttribute.getValueToInterpolate(), constraintAttribute);
                }
            } else {
                float value = getValue(str);
                if (!Float.isNaN(value)) {
                    hashMap.get(str).setPoint(this.a, this.g, this.k, this.h, this.i, value);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public float getValue(String str) {
        char c;
        switch (str.hashCode()) {
            case -1249320806:
                if (str.equals("rotationX")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1249320805:
                if (str.equals("rotationY")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1225497657:
                if (str.equals("translationX")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1225497655:
                if (str.equals("translationZ")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -40300674:
                if (str.equals("rotation")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -4379043:
                if (str.equals("elevation")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 37232917:
                if (str.equals("transitionPathRotate")) {
                    c = 5;
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
            case 156108012:
                if (str.equals("waveOffset")) {
                    c = 11;
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
                return this.l;
            case 1:
                return this.m;
            case 2:
                return this.n;
            case 3:
                return this.p;
            case 4:
                return this.q;
            case 5:
                return this.o;
            case 6:
                return this.r;
            case 7:
                return this.s;
            case '\b':
                return this.t;
            case '\t':
                return this.u;
            case '\n':
                return this.v;
            case 11:
                return this.i;
            case '\f':
                return this.j;
            default:
                Log.v("KeyCycle", "WARNING! KeyCycle UNKNOWN  " + str);
                return Float.NaN;
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void addValues(HashMap<String, SplineSet> hashMap) {
        Debug.logStack("KeyCycle", "add " + hashMap.size() + " values", 2);
        for (String str : hashMap.keySet()) {
            SplineSet splineSet = hashMap.get(str);
            char c = 65535;
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        c = '\b';
                        break;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        c = '\t';
                        break;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        c = '\n';
                        break;
                    }
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        c = '\f';
                        break;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        c = 6;
                        break;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        c = 7;
                        break;
                    }
                    break;
                case -40300674:
                    if (str.equals("rotation")) {
                        c = 2;
                        break;
                    }
                    break;
                case -4379043:
                    if (str.equals("elevation")) {
                        c = 1;
                        break;
                    }
                    break;
                case 37232917:
                    if (str.equals("transitionPathRotate")) {
                        c = 5;
                        break;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        c = 0;
                        break;
                    }
                    break;
                case 156108012:
                    if (str.equals("waveOffset")) {
                        c = 11;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    splineSet.setPoint(this.a, this.l);
                    break;
                case 1:
                    splineSet.setPoint(this.a, this.m);
                    break;
                case 2:
                    splineSet.setPoint(this.a, this.n);
                    break;
                case 3:
                    splineSet.setPoint(this.a, this.p);
                    break;
                case 4:
                    splineSet.setPoint(this.a, this.q);
                    break;
                case 5:
                    splineSet.setPoint(this.a, this.o);
                    break;
                case 6:
                    splineSet.setPoint(this.a, this.r);
                    break;
                case 7:
                    splineSet.setPoint(this.a, this.s);
                    break;
                case '\b':
                    splineSet.setPoint(this.a, this.t);
                    break;
                case '\t':
                    splineSet.setPoint(this.a, this.u);
                    break;
                case '\n':
                    splineSet.setPoint(this.a, this.v);
                    break;
                case 11:
                    splineSet.setPoint(this.a, this.i);
                    break;
                case '\f':
                    splineSet.setPoint(this.a, this.j);
                    break;
                default:
                    Log.v("KeyCycle", "WARNING KeyCycle UNKNOWN  " + str);
                    break;
            }
        }
    }

    /* loaded from: classes.dex */
    private static class a {
        private static SparseIntArray a = new SparseIntArray();

        static {
            a.append(R.styleable.KeyCycle_motionTarget, 1);
            a.append(R.styleable.KeyCycle_framePosition, 2);
            a.append(R.styleable.KeyCycle_transitionEasing, 3);
            a.append(R.styleable.KeyCycle_curveFit, 4);
            a.append(R.styleable.KeyCycle_waveShape, 5);
            a.append(R.styleable.KeyCycle_wavePeriod, 6);
            a.append(R.styleable.KeyCycle_waveOffset, 7);
            a.append(R.styleable.KeyCycle_waveVariesBy, 8);
            a.append(R.styleable.KeyCycle_android_alpha, 9);
            a.append(R.styleable.KeyCycle_android_elevation, 10);
            a.append(R.styleable.KeyCycle_android_rotation, 11);
            a.append(R.styleable.KeyCycle_android_rotationX, 12);
            a.append(R.styleable.KeyCycle_android_rotationY, 13);
            a.append(R.styleable.KeyCycle_transitionPathRotate, 14);
            a.append(R.styleable.KeyCycle_android_scaleX, 15);
            a.append(R.styleable.KeyCycle_android_scaleY, 16);
            a.append(R.styleable.KeyCycle_android_translationX, 17);
            a.append(R.styleable.KeyCycle_android_translationY, 18);
            a.append(R.styleable.KeyCycle_android_translationZ, 19);
            a.append(R.styleable.KeyCycle_motionProgress, 20);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(KeyCycle keyCycle, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (typedArray.peekValue(index).type == 3) {
                                keyCycle.c = typedArray.getString(index);
                                break;
                            } else {
                                keyCycle.b = typedArray.getResourceId(index, keyCycle.b);
                                break;
                            }
                        } else {
                            keyCycle.b = typedArray.getResourceId(index, keyCycle.b);
                            if (keyCycle.b == -1) {
                                keyCycle.c = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 2:
                        keyCycle.a = typedArray.getInt(index, keyCycle.a);
                        break;
                    case 3:
                        keyCycle.e = typedArray.getString(index);
                        break;
                    case 4:
                        keyCycle.f = typedArray.getInteger(index, keyCycle.f);
                        break;
                    case 5:
                        keyCycle.g = typedArray.getInt(index, keyCycle.g);
                        break;
                    case 6:
                        keyCycle.h = typedArray.getFloat(index, keyCycle.h);
                        break;
                    case 7:
                        if (typedArray.peekValue(index).type == 5) {
                            keyCycle.i = typedArray.getDimension(index, keyCycle.i);
                            break;
                        } else {
                            keyCycle.i = typedArray.getFloat(index, keyCycle.i);
                            break;
                        }
                    case 8:
                        keyCycle.k = typedArray.getInt(index, keyCycle.k);
                        break;
                    case 9:
                        keyCycle.l = typedArray.getFloat(index, keyCycle.l);
                        break;
                    case 10:
                        keyCycle.m = typedArray.getDimension(index, keyCycle.m);
                        break;
                    case 11:
                        keyCycle.n = typedArray.getFloat(index, keyCycle.n);
                        break;
                    case 12:
                        keyCycle.p = typedArray.getFloat(index, keyCycle.p);
                        break;
                    case 13:
                        keyCycle.q = typedArray.getFloat(index, keyCycle.q);
                        break;
                    case 14:
                        keyCycle.o = typedArray.getFloat(index, keyCycle.o);
                        break;
                    case 15:
                        keyCycle.r = typedArray.getFloat(index, keyCycle.r);
                        break;
                    case 16:
                        keyCycle.s = typedArray.getFloat(index, keyCycle.s);
                        break;
                    case 17:
                        keyCycle.t = typedArray.getDimension(index, keyCycle.t);
                        break;
                    case 18:
                        keyCycle.u = typedArray.getDimension(index, keyCycle.u);
                        break;
                    case 19:
                        if (Build.VERSION.SDK_INT >= 21) {
                            keyCycle.v = typedArray.getDimension(index, keyCycle.v);
                            break;
                        } else {
                            break;
                        }
                    case 20:
                        keyCycle.j = typedArray.getFloat(index, keyCycle.j);
                        break;
                    default:
                        Log.e("KeyCycle", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                        break;
                }
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
            case 156108012:
                if (str.equals("waveOffset")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 184161818:
                if (str.equals("wavePeriod")) {
                    c = 14;
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
                this.l = a(obj);
                return;
            case 1:
                this.f = b(obj);
                return;
            case 2:
                this.m = a(obj);
                return;
            case 3:
                this.j = a(obj);
                return;
            case 4:
                this.n = a(obj);
                return;
            case 5:
                this.p = a(obj);
                return;
            case 6:
                this.q = a(obj);
                return;
            case 7:
                this.r = a(obj);
                return;
            case '\b':
                this.s = a(obj);
                return;
            case '\t':
                this.e = obj.toString();
                return;
            case '\n':
                this.o = a(obj);
                return;
            case 11:
                this.t = a(obj);
                return;
            case '\f':
                this.u = a(obj);
                return;
            case '\r':
                this.v = a(obj);
                return;
            case 14:
                this.h = a(obj);
                return;
            case 15:
                this.i = a(obj);
                return;
            default:
                return;
        }
    }
}
