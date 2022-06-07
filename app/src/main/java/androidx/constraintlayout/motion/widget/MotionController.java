package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.motion.widget.TimeCycleSplineSet;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionController {
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    private KeyTrigger[] A;
    View a;
    int b;
    String c;
    private CurveFit[] l;
    private CurveFit m;
    private int[] n;
    private double[] o;
    private double[] p;
    private String[] q;
    private int[] r;
    private HashMap<String, TimeCycleSplineSet> x;
    private HashMap<String, SplineSet> y;
    private HashMap<String, KeyCycleOscillator> z;
    private int g = -1;
    private c h = new c();
    private c i = new c();
    private b j = new b();
    private b k = new b();
    float d = Float.NaN;
    float e = 0.0f;
    float f = 1.0f;
    private int s = 4;
    private float[] t = new float[this.s];
    private ArrayList<c> u = new ArrayList<>();
    private float[] v = new float[1];
    private ArrayList<Key> w = new ArrayList<>();
    private int B = Key.UNSET;

    public c a(int i) {
        return this.u.get(i);
    }

    public MotionController(View view) {
        setView(view);
    }

    public float a() {
        return this.i.f;
    }

    public float b() {
        return this.i.g;
    }

    public void a(float[] fArr, int i) {
        int i2 = i;
        float f = 1.0f;
        float f2 = 1.0f / (i2 - 1);
        HashMap<String, SplineSet> hashMap = this.y;
        KeyCycleOscillator keyCycleOscillator = null;
        SplineSet splineSet = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = this.y;
        SplineSet splineSet2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap3 = this.z;
        KeyCycleOscillator keyCycleOscillator2 = hashMap3 == null ? null : hashMap3.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap4 = this.z;
        if (hashMap4 != null) {
            keyCycleOscillator = hashMap4.get("translationY");
        }
        int i3 = 0;
        while (i3 < i2) {
            float f3 = i3 * f2;
            float f4 = 0.0f;
            if (this.f != f) {
                if (f3 < this.e) {
                    f3 = 0.0f;
                }
                float f5 = this.e;
                if (f3 > f5 && f3 < 1.0d) {
                    f3 = (f3 - f5) * this.f;
                }
            }
            double d = f3;
            Easing easing = this.h.b;
            float f6 = Float.NaN;
            Iterator<c> it = this.u.iterator();
            while (it.hasNext()) {
                c next = it.next();
                if (next.b != null) {
                    if (next.d < f3) {
                        easing = next.b;
                        f4 = next.d;
                    } else if (Float.isNaN(f6)) {
                        f6 = next.d;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f6)) {
                    f6 = 1.0f;
                }
                float f7 = f6 - f4;
                d = (((float) easing.get((f3 - f4) / f7)) * f7) + f4;
            }
            this.l[0].getPos(d, this.o);
            CurveFit curveFit = this.m;
            if (curveFit != null) {
                double[] dArr = this.o;
                if (dArr.length > 0) {
                    curveFit.getPos(d, dArr);
                }
            }
            int i4 = i3 * 2;
            this.h.a(this.n, this.o, fArr, i4);
            if (keyCycleOscillator2 != null) {
                fArr[i4] = fArr[i4] + keyCycleOscillator2.get(f3);
            } else if (splineSet != null) {
                fArr[i4] = fArr[i4] + splineSet.get(f3);
            }
            if (keyCycleOscillator != null) {
                int i5 = i4 + 1;
                fArr[i5] = fArr[i5] + keyCycleOscillator.get(f3);
            } else if (splineSet2 != null) {
                int i6 = i4 + 1;
                fArr[i6] = fArr[i6] + splineSet2.get(f3);
            }
            i3++;
            i2 = i;
            f = 1.0f;
        }
    }

    private float c() {
        float[] fArr = new float[2];
        float f = 1.0f / 99;
        double d = 0.0d;
        double d2 = 0.0d;
        float f2 = 0.0f;
        for (int i = 0; i < 100; i++) {
            float f3 = i * f;
            double d3 = f3;
            Easing easing = this.h.b;
            float f4 = Float.NaN;
            Iterator<c> it = this.u.iterator();
            float f5 = 0.0f;
            while (it.hasNext()) {
                c next = it.next();
                if (next.b != null) {
                    if (next.d < f3) {
                        Easing easing2 = next.b;
                        f5 = next.d;
                        easing = easing2;
                    } else if (Float.isNaN(f4)) {
                        f4 = next.d;
                    }
                }
            }
            if (easing != null) {
                if (Float.isNaN(f4)) {
                    f4 = 1.0f;
                }
                float f6 = f4 - f5;
                d3 = (((float) easing.get((f3 - f5) / f6)) * f6) + f5;
            }
            this.l[0].getPos(d3, this.o);
            this.h.a(this.n, this.o, fArr, 0);
            if (i > 0) {
                f2 = (float) (f2 + Math.hypot(d2 - fArr[1], d - fArr[0]));
            }
            d = fArr[0];
            d2 = fArr[1];
        }
        return f2;
    }

    public a a(int i, int i2, float f, float f2) {
        RectF rectF = new RectF();
        rectF.left = this.h.f;
        rectF.top = this.h.g;
        rectF.right = rectF.left + this.h.h;
        rectF.bottom = rectF.top + this.h.i;
        RectF rectF2 = new RectF();
        rectF2.left = this.i.f;
        rectF2.top = this.i.g;
        rectF2.right = rectF2.left + this.i.h;
        rectF2.bottom = rectF2.top + this.i.i;
        Iterator<Key> it = this.w.iterator();
        while (it.hasNext()) {
            Key next = it.next();
            if (next instanceof a) {
                a aVar = (a) next;
                if (aVar.intersects(i, i2, rectF, rectF2, f, f2)) {
                    return aVar;
                }
            }
        }
        return null;
    }

    public int a(float[] fArr, int[] iArr) {
        if (fArr == null) {
            return 0;
        }
        double[] timePoints = this.l[0].getTimePoints();
        if (iArr != null) {
            Iterator<c> it = this.u.iterator();
            int i = 0;
            while (it.hasNext()) {
                i++;
                iArr[i] = it.next().n;
            }
        }
        int i2 = 0;
        for (double d : timePoints) {
            this.l[0].getPos(d, this.o);
            this.h.a(this.n, this.o, fArr, i2);
            i2 += 2;
        }
        return i2 / 2;
    }

    public int a(String str, float[] fArr, int i) {
        SplineSet splineSet = this.y.get(str);
        if (splineSet == null) {
            return -1;
        }
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr[i2] = splineSet.get(i2 / (fArr.length - 1));
        }
        return fArr.length;
    }

    public void a(float f, float[] fArr, int i) {
        this.l[0].getPos(a(f, (float[]) null), this.o);
        this.h.b(this.n, this.o, fArr, i);
    }

    public void b(float[] fArr, int i) {
        float f = 1.0f / (i - 1);
        for (int i2 = 0; i2 < i; i2++) {
            this.l[0].getPos(a(i2 * f, (float[]) null), this.o);
            this.h.b(this.n, this.o, fArr, i2 * 8);
        }
    }

    public float a(int i, float f, float f2) {
        float f3 = this.i.f - this.h.f;
        float f4 = this.i.g - this.h.g;
        float f5 = this.h.f + (this.h.h / 2.0f);
        float f6 = this.h.g + (this.h.i / 2.0f);
        float hypot = (float) Math.hypot(f3, f4);
        if (hypot < 1.0E-7d) {
            return Float.NaN;
        }
        float f7 = f - f5;
        float f8 = f2 - f6;
        if (((float) Math.hypot(f7, f8)) == 0.0f) {
            return 0.0f;
        }
        float f9 = (f7 * f3) + (f8 * f4);
        switch (i) {
            case 0:
                return f9 / hypot;
            case 1:
                return (float) Math.sqrt((hypot * hypot) - (f9 * f9));
            case 2:
                return f7 / f3;
            case 3:
                return f8 / f3;
            case 4:
                return f7 / f4;
            case 5:
                return f8 / f4;
            default:
                return 0.0f;
        }
    }

    private void a(c cVar) {
        int binarySearch = Collections.binarySearch(this.u, cVar);
        if (binarySearch == 0) {
            Log.e("MotionController", " KeyPath positon \"" + cVar.e + "\" outside of range");
        }
        this.u.add((-binarySearch) - 1, cVar);
    }

    public void a(ArrayList<Key> arrayList) {
        this.w.addAll(arrayList);
    }

    public void a(Key key) {
        this.w.add(key);
    }

    public void setPathMotionArc(int i) {
        this.B = i;
    }

    public void setup(int i, int i2, float f, long j) {
        ArrayList arrayList;
        TimeCycleSplineSet timeCycleSplineSet;
        ConstraintAttribute constraintAttribute;
        SplineSet splineSet;
        ConstraintAttribute constraintAttribute2;
        new HashSet();
        HashSet<String> hashSet = new HashSet<>();
        HashSet<String> hashSet2 = new HashSet<>();
        HashSet<String> hashSet3 = new HashSet<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        if (this.B != Key.UNSET) {
            this.h.l = this.B;
        }
        this.j.a(this.k, hashSet2);
        ArrayList<Key> arrayList2 = this.w;
        double[] dArr = null;
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            arrayList = null;
            while (it.hasNext()) {
                Key next = it.next();
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    a(new c(i, i2, keyPosition, this.h, this.i));
                    if (keyPosition.o != Key.UNSET) {
                        this.g = keyPosition.o;
                    }
                } else if (next instanceof KeyCycle) {
                    next.getAttributeNames(hashSet3);
                } else if (next instanceof KeyTimeCycle) {
                    next.getAttributeNames(hashSet);
                } else if (next instanceof KeyTrigger) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((KeyTrigger) next);
                } else {
                    next.setInterpolation(hashMap);
                    next.getAttributeNames(hashSet2);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            this.A = (KeyTrigger[]) arrayList.toArray(new KeyTrigger[0]);
        }
        if (!hashSet2.isEmpty()) {
            this.y = new HashMap<>();
            Iterator<String> it2 = hashSet2.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                if (next2.startsWith("CUSTOM,")) {
                    SparseArray sparseArray = new SparseArray();
                    String str = next2.split(Constants.ACCEPT_TIME_SEPARATOR_SP)[1];
                    Iterator<Key> it3 = this.w.iterator();
                    while (it3.hasNext()) {
                        Key next3 = it3.next();
                        if (!(next3.d == null || (constraintAttribute2 = next3.d.get(str)) == null)) {
                            sparseArray.append(next3.a, constraintAttribute2);
                        }
                    }
                    splineSet = SplineSet.a(next2, sparseArray);
                } else {
                    splineSet = SplineSet.a(next2);
                }
                if (splineSet != null) {
                    splineSet.setType(next2);
                    this.y.put(next2, splineSet);
                }
            }
            ArrayList<Key> arrayList3 = this.w;
            if (arrayList3 != null) {
                Iterator<Key> it4 = arrayList3.iterator();
                while (it4.hasNext()) {
                    Key next4 = it4.next();
                    if (next4 instanceof KeyAttributes) {
                        next4.addValues(this.y);
                    }
                }
            }
            this.j.a(this.y, 0);
            this.k.a(this.y, 100);
            for (String str2 : this.y.keySet()) {
                this.y.get(str2).setup(hashMap.containsKey(str2) ? hashMap.get(str2).intValue() : 0);
            }
        }
        if (!hashSet.isEmpty()) {
            if (this.x == null) {
                this.x = new HashMap<>();
            }
            Iterator<String> it5 = hashSet.iterator();
            while (it5.hasNext()) {
                String next5 = it5.next();
                if (!this.x.containsKey(next5)) {
                    if (next5.startsWith("CUSTOM,")) {
                        SparseArray sparseArray2 = new SparseArray();
                        String str3 = next5.split(Constants.ACCEPT_TIME_SEPARATOR_SP)[1];
                        Iterator<Key> it6 = this.w.iterator();
                        while (it6.hasNext()) {
                            Key next6 = it6.next();
                            if (!(next6.d == null || (constraintAttribute = next6.d.get(str3)) == null)) {
                                sparseArray2.append(next6.a, constraintAttribute);
                            }
                        }
                        timeCycleSplineSet = TimeCycleSplineSet.a(next5, sparseArray2);
                    } else {
                        timeCycleSplineSet = TimeCycleSplineSet.a(next5, j);
                    }
                    if (timeCycleSplineSet != null) {
                        timeCycleSplineSet.setType(next5);
                        this.x.put(next5, timeCycleSplineSet);
                    }
                }
            }
            ArrayList<Key> arrayList4 = this.w;
            if (arrayList4 != null) {
                Iterator<Key> it7 = arrayList4.iterator();
                while (it7.hasNext()) {
                    Key next7 = it7.next();
                    if (next7 instanceof KeyTimeCycle) {
                        ((KeyTimeCycle) next7).addTimeValues(this.x);
                    }
                }
            }
            for (String str4 : this.x.keySet()) {
                this.x.get(str4).setup(hashMap.containsKey(str4) ? hashMap.get(str4).intValue() : 0);
            }
        }
        c[] cVarArr = new c[this.u.size() + 2];
        cVarArr[0] = this.h;
        cVarArr[cVarArr.length - 1] = this.i;
        if (this.u.size() > 0 && this.g == -1) {
            this.g = 0;
        }
        Iterator<c> it8 = this.u.iterator();
        int i3 = 1;
        while (it8.hasNext()) {
            i3++;
            cVarArr[i3] = it8.next();
        }
        HashSet hashSet4 = new HashSet();
        for (String str5 : this.i.m.keySet()) {
            if (this.h.m.containsKey(str5)) {
                if (!hashSet2.contains("CUSTOM," + str5)) {
                    hashSet4.add(str5);
                }
            }
        }
        this.q = (String[]) hashSet4.toArray(new String[0]);
        this.r = new int[this.q.length];
        int i4 = 0;
        while (true) {
            String[] strArr = this.q;
            if (i4 >= strArr.length) {
                break;
            }
            String str6 = strArr[i4];
            this.r[i4] = 0;
            int i5 = 0;
            while (true) {
                if (i5 >= cVarArr.length) {
                    break;
                } else if (cVarArr[i5].m.containsKey(str6)) {
                    int[] iArr = this.r;
                    iArr[i4] = iArr[i4] + cVarArr[i5].m.get(str6).noOfInterpValues();
                    break;
                } else {
                    i5++;
                }
            }
            i4++;
        }
        boolean z = cVarArr[0].l != Key.UNSET;
        boolean[] zArr = new boolean[18 + this.q.length];
        for (int i6 = 1; i6 < cVarArr.length; i6++) {
            cVarArr[i6].a(cVarArr[i6 - 1], zArr, this.q, z);
        }
        int i7 = 0;
        for (int i8 = 1; i8 < zArr.length; i8++) {
            if (zArr[i8]) {
                i7++;
            }
        }
        this.n = new int[i7];
        int[] iArr2 = this.n;
        this.o = new double[iArr2.length];
        this.p = new double[iArr2.length];
        int i9 = 0;
        for (int i10 = 1; i10 < zArr.length; i10++) {
            if (zArr[i10]) {
                i9++;
                this.n[i9] = i10;
            }
        }
        double[][] dArr2 = (double[][]) Array.newInstance(double.class, cVarArr.length, this.n.length);
        double[] dArr3 = new double[cVarArr.length];
        for (int i11 = 0; i11 < cVarArr.length; i11++) {
            cVarArr[i11].a(dArr2[i11], this.n);
            dArr3[i11] = cVarArr[i11].d;
        }
        int i12 = 0;
        while (true) {
            int[] iArr3 = this.n;
            if (i12 >= iArr3.length) {
                break;
            }
            if (iArr3[i12] < c.a.length) {
                String str7 = c.a[this.n[i12]] + " [";
                for (int i13 = 0; i13 < cVarArr.length; i13++) {
                    str7 = str7 + dArr2[i13][i12];
                }
            }
            i12++;
        }
        this.l = new CurveFit[this.q.length + 1];
        int i14 = 0;
        while (true) {
            String[] strArr2 = this.q;
            if (i14 >= strArr2.length) {
                break;
            }
            String str8 = strArr2[i14];
            int i15 = 0;
            double[] dArr4 = dArr;
            double[][] dArr5 = (double[][]) dArr;
            for (int i16 = 0; i16 < cVarArr.length; i16++) {
                if (cVarArr[i16].a(str8)) {
                    if (dArr5 == null) {
                        dArr4 = new double[cVarArr.length];
                        dArr5 = (double[][]) Array.newInstance(double.class, cVarArr.length, cVarArr[i16].b(str8));
                    }
                    dArr3 = dArr3;
                    dArr4[i15] = cVarArr[i16].d;
                    cVarArr[i16].a(str8, dArr5[i15], 0);
                    i15++;
                } else {
                    dArr3 = dArr3;
                }
            }
            i14++;
            this.l[i14] = CurveFit.get(this.g, Arrays.copyOf(dArr4, i15), (double[][]) Arrays.copyOf(dArr5, i15));
            dArr3 = dArr3;
            dArr = null;
        }
        this.l[0] = CurveFit.get(this.g, dArr3, dArr2);
        if (cVarArr[0].l != Key.UNSET) {
            int length = cVarArr.length;
            int[] iArr4 = new int[length];
            double[] dArr6 = new double[length];
            double[][] dArr7 = (double[][]) Array.newInstance(double.class, length, 2);
            for (int i17 = 0; i17 < length; i17++) {
                iArr4[i17] = cVarArr[i17].l;
                dArr6[i17] = cVarArr[i17].d;
                dArr7[i17][0] = cVarArr[i17].f;
                dArr7[i17][1] = cVarArr[i17].g;
            }
            this.m = CurveFit.getArc(iArr4, dArr6, dArr7);
        }
        float f2 = Float.NaN;
        this.z = new HashMap<>();
        if (this.w != null) {
            Iterator<String> it9 = hashSet3.iterator();
            while (it9.hasNext()) {
                String next8 = it9.next();
                KeyCycleOscillator a = KeyCycleOscillator.a(next8);
                if (a != null) {
                    if (a.variesByPath() && Float.isNaN(f2)) {
                        f2 = c();
                    }
                    a.setType(next8);
                    this.z.put(next8, a);
                }
            }
            Iterator<Key> it10 = this.w.iterator();
            while (it10.hasNext()) {
                Key next9 = it10.next();
                if (next9 instanceof KeyCycle) {
                    ((KeyCycle) next9).addCycleValues(this.z);
                }
            }
            for (KeyCycleOscillator keyCycleOscillator : this.z.values()) {
                keyCycleOscillator.setup(f2);
            }
        }
    }

    public String toString() {
        return " start: x: " + this.h.f + " y: " + this.h.g + " end: x: " + this.i.f + " y: " + this.i.g;
    }

    private void b(c cVar) {
        cVar.a((int) this.a.getX(), (int) this.a.getY(), this.a.getWidth(), this.a.getHeight());
    }

    public void setView(View view) {
        this.a = view;
        this.b = view.getId();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            this.c = ((ConstraintLayout.LayoutParams) layoutParams).getConstraintTag();
        }
    }

    public void a(View view) {
        c cVar = this.h;
        cVar.d = 0.0f;
        cVar.e = 0.0f;
        cVar.a(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.j.b(view);
    }

    public void a(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        c cVar = this.h;
        cVar.d = 0.0f;
        cVar.e = 0.0f;
        b(cVar);
        this.h.a(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        ConstraintSet.Constraint parameters = constraintSet.getParameters(this.b);
        this.h.a(parameters);
        this.d = parameters.motion.mMotionStagger;
        this.j.a(constraintWidget, constraintSet, this.b);
    }

    public void b(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        c cVar = this.i;
        cVar.d = 1.0f;
        cVar.e = 1.0f;
        b(cVar);
        this.i.a(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        this.i.a(constraintSet.getParameters(this.b));
        this.k.a(constraintWidget, constraintSet, this.b);
    }

    private float a(float f, float[] fArr) {
        float f2 = 0.0f;
        float f3 = 1.0f;
        if (fArr != null) {
            fArr[0] = 1.0f;
        } else if (this.f != 1.0d) {
            if (f < this.e) {
                f = 0.0f;
            }
            float f4 = this.e;
            if (f > f4 && f < 1.0d) {
                f = (f - f4) * this.f;
            }
        }
        Easing easing = this.h.b;
        float f5 = Float.NaN;
        Iterator<c> it = this.u.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (next.b != null) {
                if (next.d < f) {
                    easing = next.b;
                    f2 = next.d;
                } else if (Float.isNaN(f5)) {
                    f5 = next.d;
                }
            }
        }
        if (easing != null) {
            if (!Float.isNaN(f5)) {
                f3 = f5;
            }
            float f6 = f3 - f2;
            double d = (f - f2) / f6;
            f = (((float) easing.get(d)) * f6) + f2;
            if (fArr != null) {
                fArr[0] = (float) easing.getDiff(d);
            }
        }
        return f;
    }

    public boolean a(View view, float f, long j, KeyCache keyCache) {
        boolean z;
        TimeCycleSplineSet.d dVar;
        double d;
        float a = a(f, (float[]) null);
        HashMap<String, SplineSet> hashMap = this.y;
        if (hashMap != null) {
            for (SplineSet splineSet : hashMap.values()) {
                splineSet.setProperty(view, a);
            }
        }
        HashMap<String, TimeCycleSplineSet> hashMap2 = this.x;
        if (hashMap2 != null) {
            dVar = null;
            boolean z2 = false;
            for (TimeCycleSplineSet timeCycleSplineSet : hashMap2.values()) {
                if (timeCycleSplineSet instanceof TimeCycleSplineSet.d) {
                    dVar = (TimeCycleSplineSet.d) timeCycleSplineSet;
                } else {
                    z2 |= timeCycleSplineSet.setProperty(view, a, j, keyCache);
                }
            }
            z = z2;
        } else {
            dVar = null;
            z = false;
        }
        CurveFit[] curveFitArr = this.l;
        if (curveFitArr != null) {
            double d2 = a;
            curveFitArr[0].getPos(d2, this.o);
            this.l[0].getSlope(d2, this.p);
            CurveFit curveFit = this.m;
            if (curveFit != null) {
                double[] dArr = this.o;
                if (dArr.length > 0) {
                    curveFit.getPos(d2, dArr);
                    this.m.getSlope(d2, this.p);
                }
            }
            this.h.a(view, this.n, this.o, this.p, (double[]) null);
            HashMap<String, SplineSet> hashMap3 = this.y;
            if (hashMap3 != null) {
                for (SplineSet splineSet2 : hashMap3.values()) {
                    if (splineSet2 instanceof SplineSet.d) {
                        double[] dArr2 = this.p;
                        d2 = d2;
                        ((SplineSet.d) splineSet2).a(view, a, dArr2[0], dArr2[1]);
                    } else {
                        d2 = d2;
                    }
                }
                d = d2;
            } else {
                d = d2;
            }
            if (dVar != null) {
                double[] dArr3 = this.p;
                z = dVar.a(view, keyCache, a, j, dArr3[0], dArr3[1]) | z;
            }
            int i = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.l;
                if (i >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i].getPos(d, this.t);
                this.h.m.get(this.q[i - 1]).setInterpolatedValue(view, this.t);
                i++;
            }
            if (this.j.a == 0) {
                if (a <= 0.0f) {
                    view.setVisibility(this.j.b);
                } else if (a >= 1.0f) {
                    view.setVisibility(this.k.b);
                } else if (this.k.b != this.j.b) {
                    view.setVisibility(0);
                }
            }
            if (this.A != null) {
                int i2 = 0;
                while (true) {
                    KeyTrigger[] keyTriggerArr = this.A;
                    if (i2 >= keyTriggerArr.length) {
                        break;
                    }
                    keyTriggerArr[i2].conditionallyFire(a, view);
                    i2++;
                }
            }
        } else {
            float f2 = this.h.f + ((this.i.f - this.h.f) * a);
            float f3 = this.h.g + ((this.i.g - this.h.g) * a);
            float f4 = f2 + 0.5f;
            int i3 = (int) f4;
            float f5 = f3 + 0.5f;
            int i4 = (int) f5;
            int i5 = (int) (f4 + this.h.h + ((this.i.h - this.h.h) * a));
            int i6 = (int) (f5 + this.h.i + ((this.i.i - this.h.i) * a));
            int i7 = i5 - i3;
            int i8 = i6 - i4;
            if (!(this.i.h == this.h.h && this.i.i == this.h.i)) {
                view.measure(View.MeasureSpec.makeMeasureSpec(i7, 1073741824), View.MeasureSpec.makeMeasureSpec(i8, 1073741824));
            }
            view.layout(i3, i4, i5, i6);
        }
        HashMap<String, KeyCycleOscillator> hashMap4 = this.z;
        if (hashMap4 != null) {
            for (KeyCycleOscillator keyCycleOscillator : hashMap4.values()) {
                if (keyCycleOscillator instanceof KeyCycleOscillator.e) {
                    double[] dArr4 = this.p;
                    ((KeyCycleOscillator.e) keyCycleOscillator).a(view, a, dArr4[0], dArr4[1]);
                } else {
                    keyCycleOscillator.setProperty(view, a);
                }
            }
        }
        return z;
    }

    public void a(float f, float f2, float f3, float[] fArr) {
        double[] dArr;
        float a = a(f, this.v);
        CurveFit[] curveFitArr = this.l;
        int i = 0;
        if (curveFitArr != null) {
            double d = a;
            curveFitArr[0].getSlope(d, this.p);
            this.l[0].getPos(d, this.o);
            float f4 = this.v[0];
            while (true) {
                dArr = this.p;
                if (i >= dArr.length) {
                    break;
                }
                dArr[i] = dArr[i] * f4;
                i++;
            }
            CurveFit curveFit = this.m;
            if (curveFit != null) {
                double[] dArr2 = this.o;
                if (dArr2.length > 0) {
                    curveFit.getPos(d, dArr2);
                    this.m.getSlope(d, this.p);
                    this.h.a(f2, f3, fArr, this.n, this.p, this.o);
                    return;
                }
                return;
            }
            this.h.a(f2, f3, fArr, this.n, dArr, this.o);
            return;
        }
        float f5 = this.i.f - this.h.f;
        float f6 = this.i.g - this.h.g;
        float f7 = (this.i.i - this.h.i) + f6;
        fArr[0] = (f5 * (1.0f - f2)) + (((this.i.h - this.h.h) + f5) * f2);
        fArr[1] = (f6 * (1.0f - f3)) + (f7 * f3);
    }

    public void a(float f, int i, int i2, float f2, float f3, float[] fArr) {
        float a = a(f, this.v);
        HashMap<String, SplineSet> hashMap = this.y;
        KeyCycleOscillator keyCycleOscillator = null;
        SplineSet splineSet = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = this.y;
        SplineSet splineSet2 = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, SplineSet> hashMap3 = this.y;
        SplineSet splineSet3 = hashMap3 == null ? null : hashMap3.get("rotation");
        HashMap<String, SplineSet> hashMap4 = this.y;
        SplineSet splineSet4 = hashMap4 == null ? null : hashMap4.get("scaleX");
        HashMap<String, SplineSet> hashMap5 = this.y;
        SplineSet splineSet5 = hashMap5 == null ? null : hashMap5.get("scaleY");
        HashMap<String, KeyCycleOscillator> hashMap6 = this.z;
        KeyCycleOscillator keyCycleOscillator2 = hashMap6 == null ? null : hashMap6.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap7 = this.z;
        KeyCycleOscillator keyCycleOscillator3 = hashMap7 == null ? null : hashMap7.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap8 = this.z;
        KeyCycleOscillator keyCycleOscillator4 = hashMap8 == null ? null : hashMap8.get("rotation");
        HashMap<String, KeyCycleOscillator> hashMap9 = this.z;
        KeyCycleOscillator keyCycleOscillator5 = hashMap9 == null ? null : hashMap9.get("scaleX");
        HashMap<String, KeyCycleOscillator> hashMap10 = this.z;
        if (hashMap10 != null) {
            keyCycleOscillator = hashMap10.get("scaleY");
        }
        VelocityMatrix velocityMatrix = new VelocityMatrix();
        velocityMatrix.clear();
        velocityMatrix.setRotationVelocity(splineSet3, a);
        velocityMatrix.setTranslationVelocity(splineSet, splineSet2, a);
        velocityMatrix.setScaleVelocity(splineSet4, splineSet5, a);
        velocityMatrix.setRotationVelocity(keyCycleOscillator4, a);
        velocityMatrix.setTranslationVelocity(keyCycleOscillator2, keyCycleOscillator3, a);
        velocityMatrix.setScaleVelocity(keyCycleOscillator5, keyCycleOscillator, a);
        CurveFit curveFit = this.m;
        if (curveFit != null) {
            double[] dArr = this.o;
            if (dArr.length > 0) {
                double d = a;
                curveFit.getPos(d, dArr);
                this.m.getSlope(d, this.p);
                this.h.a(f2, f3, fArr, this.n, this.p, this.o);
            }
            velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
            return;
        }
        int i3 = 0;
        if (this.l != null) {
            double a2 = a(a, this.v);
            this.l[0].getSlope(a2, this.p);
            this.l[0].getPos(a2, this.o);
            float f4 = this.v[0];
            while (true) {
                double[] dArr2 = this.p;
                if (i3 < dArr2.length) {
                    dArr2[i3] = dArr2[i3] * f4;
                    i3++;
                } else {
                    this.h.a(f2, f3, fArr, this.n, dArr2, this.o);
                    velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
                    return;
                }
            }
        } else {
            float f5 = this.i.f - this.h.f;
            float f6 = this.i.g - this.h.g;
            float f7 = (this.i.i - this.h.i) + f6;
            fArr[0] = (f5 * (1.0f - f2)) + (((this.i.h - this.h.h) + f5) * f2);
            fArr[1] = (f6 * (1.0f - f3)) + (f7 * f3);
            velocityMatrix.clear();
            velocityMatrix.setRotationVelocity(splineSet3, a);
            velocityMatrix.setTranslationVelocity(splineSet, splineSet2, a);
            velocityMatrix.setScaleVelocity(splineSet4, splineSet5, a);
            velocityMatrix.setRotationVelocity(keyCycleOscillator4, a);
            velocityMatrix.setTranslationVelocity(keyCycleOscillator2, keyCycleOscillator3, a);
            velocityMatrix.setScaleVelocity(keyCycleOscillator5, keyCycleOscillator, a);
            velocityMatrix.applyTransform(f2, f3, i, i2, fArr);
        }
    }

    public int getDrawPath() {
        int i = this.h.c;
        Iterator<c> it = this.u.iterator();
        while (it.hasNext()) {
            i = Math.max(i, it.next().c);
        }
        return Math.max(i, this.i.c);
    }

    public void setDrawPath(int i) {
        this.h.c = i;
    }

    public void a(View view, a aVar, float f, float f2, String[] strArr, float[] fArr) {
        RectF rectF = new RectF();
        rectF.left = this.h.f;
        rectF.top = this.h.g;
        rectF.right = rectF.left + this.h.h;
        rectF.bottom = rectF.top + this.h.i;
        RectF rectF2 = new RectF();
        rectF2.left = this.i.f;
        rectF2.top = this.i.g;
        rectF2.right = rectF2.left + this.i.h;
        rectF2.bottom = rectF2.top + this.i.i;
        aVar.positionAttributes(view, rectF, rectF2, f, f2, strArr, fArr);
    }

    public int getkeyFramePositions(int[] iArr, float[] fArr) {
        Iterator<Key> it = this.w.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            i++;
            iArr[i] = next.a + (next.mType * 1000);
            this.l[0].getPos(next.a / 100.0f, this.o);
            this.h.a(this.n, this.o, fArr, i2);
            i2 += 2;
        }
        return i;
    }

    public int getKeyFrameInfo(int i, int[] iArr) {
        float[] fArr = new float[2];
        Iterator<Key> it = this.w.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Key next = it.next();
            if (next.mType == i || i != -1) {
                iArr[i3] = 0;
                int i4 = i3 + 1;
                iArr[i4] = next.mType;
                int i5 = i4 + 1;
                iArr[i5] = next.a;
                this.l[0].getPos(next.a / 100.0f, this.o);
                this.h.a(this.n, this.o, fArr, 0);
                int i6 = i5 + 1;
                iArr[i6] = Float.floatToIntBits(fArr[0]);
                int i7 = i6 + 1;
                iArr[i7] = Float.floatToIntBits(fArr[1]);
                if (next instanceof KeyPosition) {
                    KeyPosition keyPosition = (KeyPosition) next;
                    int i8 = i7 + 1;
                    iArr[i8] = keyPosition.n;
                    int i9 = i8 + 1;
                    iArr[i9] = Float.floatToIntBits(keyPosition.j);
                    i7 = i9 + 1;
                    iArr[i7] = Float.floatToIntBits(keyPosition.k);
                }
                int i10 = i7 + 1;
                iArr[i3] = i10 - i3;
                i2++;
                i3 = i10;
            }
        }
        return i2;
    }
}
