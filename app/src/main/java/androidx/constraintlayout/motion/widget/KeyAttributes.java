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
public class KeyAttributes extends Key {
    public static final int KEY_TYPE = 1;
    private String e;
    private int f = -1;
    private boolean g = false;
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
    private float s = Float.NaN;
    private float t = Float.NaN;
    private float u = Float.NaN;

    public KeyAttributes() {
        this.mType = 1;
        this.d = new HashMap();
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void load(Context context, AttributeSet attributeSet) {
        a.a(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyAttribute));
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.h)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.i)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.j)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.k)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.l)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.m)) {
            hashSet.add("transformPivotX");
        }
        if (!Float.isNaN(this.n)) {
            hashSet.add("transformPivotY");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.s)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.t)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.o)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.p)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.u)) {
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
            if (!Float.isNaN(this.h)) {
                hashMap.put("alpha", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.i)) {
                hashMap.put("elevation", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.j)) {
                hashMap.put("rotation", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.k)) {
                hashMap.put("rotationX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.l)) {
                hashMap.put("rotationY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.m)) {
                hashMap.put("transformPivotX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.n)) {
                hashMap.put("transformPivotY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.r)) {
                hashMap.put("translationX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.s)) {
                hashMap.put("translationY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.t)) {
                hashMap.put("translationZ", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.o)) {
                hashMap.put("transitionPathRotate", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.p)) {
                hashMap.put("scaleX", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.q)) {
                hashMap.put("scaleY", Integer.valueOf(this.f));
            }
            if (!Float.isNaN(this.u)) {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
        if (r1.equals("transitionPathRotate") != false) goto L_0x00e0;
     */
    @Override // androidx.constraintlayout.motion.widget.Key
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.widget.SplineSet> r7) {
        /*
            Method dump skipped, instructions count: 586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyAttributes.addValues(java.util.HashMap):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.constraintlayout.motion.widget.Key
    public void setValue(String str, Object obj) {
        char c;
        switch (str.hashCode()) {
            case -1913008125:
                if (str.equals("motionProgress")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1812823328:
                if (str.equals("transitionEasing")) {
                    c = 11;
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
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (str.equals("translationY")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -987906986:
                if (str.equals("pivotX")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -987906985:
                if (str.equals("pivotY")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (str.equals("scaleX")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (str.equals("scaleY")) {
                    c = '\n';
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
                    c = '\r';
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
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1941332754:
                if (str.equals("visibility")) {
                    c = '\f';
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
                this.h = a(obj);
                return;
            case 1:
                this.f = b(obj);
                return;
            case 2:
                this.i = a(obj);
                return;
            case 3:
                this.u = a(obj);
                return;
            case 4:
                this.j = a(obj);
                return;
            case 5:
                this.k = a(obj);
                return;
            case 6:
                this.l = a(obj);
                return;
            case 7:
                this.m = a(obj);
                return;
            case '\b':
                this.n = a(obj);
                return;
            case '\t':
                this.p = a(obj);
                return;
            case '\n':
                this.q = a(obj);
                return;
            case 11:
                this.e = obj.toString();
                return;
            case '\f':
                this.g = c(obj);
                return;
            case '\r':
                this.o = a(obj);
                return;
            case 14:
                this.r = a(obj);
                return;
            case 15:
                this.s = a(obj);
                return;
            case 16:
                this.t = a(obj);
                return;
            default:
                return;
        }
    }

    /* loaded from: classes.dex */
    private static class a {
        private static SparseIntArray a = new SparseIntArray();

        static {
            a.append(R.styleable.KeyAttribute_android_alpha, 1);
            a.append(R.styleable.KeyAttribute_android_elevation, 2);
            a.append(R.styleable.KeyAttribute_android_rotation, 4);
            a.append(R.styleable.KeyAttribute_android_rotationX, 5);
            a.append(R.styleable.KeyAttribute_android_rotationY, 6);
            a.append(R.styleable.KeyAttribute_android_transformPivotX, 19);
            a.append(R.styleable.KeyAttribute_android_transformPivotY, 20);
            a.append(R.styleable.KeyAttribute_android_scaleX, 7);
            a.append(R.styleable.KeyAttribute_transitionPathRotate, 8);
            a.append(R.styleable.KeyAttribute_transitionEasing, 9);
            a.append(R.styleable.KeyAttribute_motionTarget, 10);
            a.append(R.styleable.KeyAttribute_framePosition, 12);
            a.append(R.styleable.KeyAttribute_curveFit, 13);
            a.append(R.styleable.KeyAttribute_android_scaleY, 14);
            a.append(R.styleable.KeyAttribute_android_translationX, 15);
            a.append(R.styleable.KeyAttribute_android_translationY, 16);
            a.append(R.styleable.KeyAttribute_android_translationZ, 17);
            a.append(R.styleable.KeyAttribute_motionProgress, 18);
        }

        public static void a(KeyAttributes keyAttributes, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                switch (a.get(index)) {
                    case 1:
                        keyAttributes.h = typedArray.getFloat(index, keyAttributes.h);
                        break;
                    case 2:
                        keyAttributes.i = typedArray.getDimension(index, keyAttributes.i);
                        break;
                    case 3:
                    case 11:
                    default:
                        Log.e("KeyAttribute", "unused attribute 0x" + Integer.toHexString(index) + "   " + a.get(index));
                        break;
                    case 4:
                        keyAttributes.j = typedArray.getFloat(index, keyAttributes.j);
                        break;
                    case 5:
                        keyAttributes.k = typedArray.getFloat(index, keyAttributes.k);
                        break;
                    case 6:
                        keyAttributes.l = typedArray.getFloat(index, keyAttributes.l);
                        break;
                    case 7:
                        keyAttributes.p = typedArray.getFloat(index, keyAttributes.p);
                        break;
                    case 8:
                        keyAttributes.o = typedArray.getFloat(index, keyAttributes.o);
                        break;
                    case 9:
                        keyAttributes.e = typedArray.getString(index);
                        break;
                    case 10:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (typedArray.peekValue(index).type == 3) {
                                keyAttributes.c = typedArray.getString(index);
                                break;
                            } else {
                                keyAttributes.b = typedArray.getResourceId(index, keyAttributes.b);
                                break;
                            }
                        } else {
                            keyAttributes.b = typedArray.getResourceId(index, keyAttributes.b);
                            if (keyAttributes.b == -1) {
                                keyAttributes.c = typedArray.getString(index);
                                break;
                            } else {
                                break;
                            }
                        }
                    case 12:
                        keyAttributes.a = typedArray.getInt(index, keyAttributes.a);
                        break;
                    case 13:
                        keyAttributes.f = typedArray.getInteger(index, keyAttributes.f);
                        break;
                    case 14:
                        keyAttributes.q = typedArray.getFloat(index, keyAttributes.q);
                        break;
                    case 15:
                        keyAttributes.r = typedArray.getDimension(index, keyAttributes.r);
                        break;
                    case 16:
                        keyAttributes.s = typedArray.getDimension(index, keyAttributes.s);
                        break;
                    case 17:
                        if (Build.VERSION.SDK_INT >= 21) {
                            keyAttributes.t = typedArray.getDimension(index, keyAttributes.t);
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        keyAttributes.u = typedArray.getFloat(index, keyAttributes.u);
                        break;
                    case 19:
                        keyAttributes.m = typedArray.getDimension(index, keyAttributes.m);
                        break;
                    case 20:
                        keyAttributes.n = typedArray.getDimension(index, keyAttributes.n);
                        break;
                }
            }
        }
    }
}
