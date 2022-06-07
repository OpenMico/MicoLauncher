package androidx.constraintlayout.motion.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MotionConstrainedPoint.java */
/* loaded from: classes.dex */
public class b implements Comparable<b> {
    static String[] d = {"position", "x", "y", "width", "height", "pathRotate"};
    private float A;
    int b;
    private Easing u;
    private float w;
    private float x;
    private float y;
    private float z;
    private float i = 1.0f;
    int a = 0;
    private boolean j = false;
    private float k = 0.0f;
    private float l = 0.0f;
    private float m = 0.0f;
    public float c = 0.0f;
    private float n = 1.0f;
    private float o = 1.0f;
    private float p = Float.NaN;
    private float q = Float.NaN;
    private float r = 0.0f;
    private float s = 0.0f;
    private float t = 0.0f;
    private int v = 0;
    private float B = Float.NaN;
    private float C = Float.NaN;
    LinkedHashMap<String, ConstraintAttribute> e = new LinkedHashMap<>();
    int f = 0;
    double[] g = new double[18];
    double[] h = new double[18];

    private boolean a(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(b bVar, HashSet<String> hashSet) {
        if (a(this.i, bVar.i)) {
            hashSet.add("alpha");
        }
        if (a(this.k, bVar.k)) {
            hashSet.add("elevation");
        }
        int i = this.b;
        int i2 = bVar.b;
        if (i != i2 && this.a == 0 && (i == 0 || i2 == 0)) {
            hashSet.add("alpha");
        }
        if (a(this.l, bVar.l)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.B) || !Float.isNaN(bVar.B)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.C) || !Float.isNaN(bVar.C)) {
            hashSet.add("progress");
        }
        if (a(this.m, bVar.m)) {
            hashSet.add("rotationX");
        }
        if (a(this.c, bVar.c)) {
            hashSet.add("rotationY");
        }
        if (a(this.p, bVar.p)) {
            hashSet.add("transformPivotX");
        }
        if (a(this.q, bVar.q)) {
            hashSet.add("transformPivotY");
        }
        if (a(this.n, bVar.n)) {
            hashSet.add("scaleX");
        }
        if (a(this.o, bVar.o)) {
            hashSet.add("scaleY");
        }
        if (a(this.r, bVar.r)) {
            hashSet.add("translationX");
        }
        if (a(this.s, bVar.s)) {
            hashSet.add("translationY");
        }
        if (a(this.t, bVar.t)) {
            hashSet.add("translationZ");
        }
    }

    void a(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.A = f4;
    }

    /* renamed from: a */
    public int compareTo(b bVar) {
        return Float.compare(this.w, bVar.w);
    }

    public void a(View view) {
        this.b = view.getVisibility();
        this.i = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.j = false;
        if (Build.VERSION.SDK_INT >= 21) {
            this.k = view.getElevation();
        }
        this.l = view.getRotation();
        this.m = view.getRotationX();
        this.c = view.getRotationY();
        this.n = view.getScaleX();
        this.o = view.getScaleY();
        this.p = view.getPivotX();
        this.q = view.getPivotY();
        this.r = view.getTranslationX();
        this.s = view.getTranslationY();
        if (Build.VERSION.SDK_INT >= 21) {
            this.t = view.getTranslationZ();
        }
    }

    public void a(ConstraintSet.Constraint constraint) {
        this.a = constraint.propertySet.mVisibilityMode;
        this.b = constraint.propertySet.visibility;
        this.i = (constraint.propertySet.visibility == 0 || this.a != 0) ? constraint.propertySet.alpha : 0.0f;
        this.j = constraint.transform.applyElevation;
        this.k = constraint.transform.elevation;
        this.l = constraint.transform.rotation;
        this.m = constraint.transform.rotationX;
        this.c = constraint.transform.rotationY;
        this.n = constraint.transform.scaleX;
        this.o = constraint.transform.scaleY;
        this.p = constraint.transform.transformPivotX;
        this.q = constraint.transform.transformPivotY;
        this.r = constraint.transform.translationX;
        this.s = constraint.transform.translationY;
        this.t = constraint.transform.translationZ;
        this.u = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        this.B = constraint.motion.mPathRotate;
        this.v = constraint.motion.mDrawPath;
        this.C = constraint.propertySet.mProgress;
        for (String str : constraint.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(str);
            if (constraintAttribute.getType() != ConstraintAttribute.AttributeType.STRING_TYPE) {
                this.e.put(str, constraintAttribute);
            }
        }
    }

    public void a(HashMap<String, SplineSet> hashMap, int i) {
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
                        c = 11;
                        break;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        c = '\f';
                        break;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        c = '\r';
                        break;
                    }
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        c = '\b';
                        break;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        c = '\t';
                        break;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        c = '\n';
                        break;
                    }
                    break;
                case -760884510:
                    if (str.equals("transformPivotX")) {
                        c = 5;
                        break;
                    }
                    break;
                case -760884509:
                    if (str.equals("transformPivotY")) {
                        c = 6;
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
                        c = 7;
                        break;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            float f = 1.0f;
            float f2 = 0.0f;
            switch (c) {
                case 0:
                    if (!Float.isNaN(this.i)) {
                        f = this.i;
                    }
                    splineSet.setPoint(i, f);
                    break;
                case 1:
                    if (!Float.isNaN(this.k)) {
                        f2 = this.k;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 2:
                    if (!Float.isNaN(this.l)) {
                        f2 = this.l;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 3:
                    if (!Float.isNaN(this.m)) {
                        f2 = this.m;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 4:
                    if (!Float.isNaN(this.c)) {
                        f2 = this.c;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 5:
                    if (!Float.isNaN(this.p)) {
                        f2 = this.p;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 6:
                    if (!Float.isNaN(this.q)) {
                        f2 = this.q;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case 7:
                    if (!Float.isNaN(this.B)) {
                        f2 = this.B;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case '\b':
                    if (!Float.isNaN(this.C)) {
                        f2 = this.C;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case '\t':
                    if (!Float.isNaN(this.n)) {
                        f = this.n;
                    }
                    splineSet.setPoint(i, f);
                    break;
                case '\n':
                    if (!Float.isNaN(this.o)) {
                        f = this.o;
                    }
                    splineSet.setPoint(i, f);
                    break;
                case 11:
                    if (!Float.isNaN(this.r)) {
                        f2 = this.r;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case '\f':
                    if (!Float.isNaN(this.s)) {
                        f2 = this.s;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                case '\r':
                    if (!Float.isNaN(this.t)) {
                        f2 = this.t;
                    }
                    splineSet.setPoint(i, f2);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)[1];
                        if (this.e.containsKey(str2)) {
                            ConstraintAttribute constraintAttribute = this.e.get(str2);
                            if (splineSet instanceof SplineSet.b) {
                                ((SplineSet.b) splineSet).a(i, constraintAttribute);
                                break;
                            } else {
                                Log.e("MotionPaths", str + " splineSet not a CustomSet frame = " + i + ", value" + constraintAttribute.getValueToInterpolate() + splineSet);
                                break;
                            }
                        } else {
                            Log.e("MotionPaths", "UNKNOWN customName " + str2);
                            break;
                        }
                    } else {
                        Log.e("MotionPaths", "UNKNOWN spline " + str);
                        break;
                    }
            }
        }
    }

    public void b(View view) {
        a(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        a(view);
    }

    public void a(ConstraintWidget constraintWidget, ConstraintSet constraintSet, int i) {
        a(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        a(constraintSet.getParameters(i));
    }
}
