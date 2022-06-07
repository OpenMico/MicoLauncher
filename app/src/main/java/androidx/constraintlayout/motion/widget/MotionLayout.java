package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Flow;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.ViewCompat;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
    public static final int DEBUG_SHOW_NONE = 0;
    public static final int DEBUG_SHOW_PATH = 2;
    public static final int DEBUG_SHOW_PROGRESS = 1;
    public static boolean IS_IN_EDIT_MODE = false;
    public static final int TOUCH_UP_COMPLETE = 0;
    public static final int TOUCH_UP_COMPLETE_TO_END = 2;
    public static final int TOUCH_UP_COMPLETE_TO_START = 1;
    public static final int TOUCH_UP_DECELERATE = 4;
    public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
    public static final int TOUCH_UP_STOP = 3;
    public static final int VELOCITY_LAYOUT = 1;
    public static final int VELOCITY_POST_LAYOUT = 0;
    public static final int VELOCITY_STATIC_LAYOUT = 3;
    public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
    int A;
    int B;
    int C;
    float D;
    private long O;
    private boolean P;
    private TransitionListener Q;
    private float R;
    private float S;
    private DesignTool W;
    MotionScene a;
    private e al;
    Interpolator b;
    b l;
    int n;
    int o;
    int p;
    int q;
    float s;
    float t;
    long u;
    float v;
    int x;
    int y;
    int z;
    float c = 0.0f;
    private int H = -1;
    int d = -1;
    private int I = -1;
    private int J = 0;
    private int K = 0;
    private boolean L = true;
    HashMap<View, MotionController> e = new HashMap<>();
    private long M = 0;
    private float N = 1.0f;
    float f = 0.0f;
    float g = 0.0f;
    float h = 0.0f;
    boolean i = false;
    boolean j = false;
    int k = 0;
    private boolean T = false;
    private StopLogic U = new StopLogic();
    private a V = new a();
    boolean m = true;
    boolean r = false;
    private boolean aa = false;
    private ArrayList<MotionHelper> ab = null;
    private ArrayList<MotionHelper> ac = null;
    private ArrayList<TransitionListener> ad = null;
    private int ae = 0;
    private long af = -1;
    private float ag = 0.0f;
    private int ah = 0;
    private float ai = 0.0f;
    boolean w = false;
    protected boolean mMeasureDuringTransition = false;
    private KeyCache aj = new KeyCache();
    private boolean ak = false;
    f E = f.UNDEFINED;
    c F = new c();
    private boolean am = false;
    private RectF an = new RectF();
    private View ao = null;
    ArrayList<Integer> G = new ArrayList<>();

    /* loaded from: classes.dex */
    public interface MotionTracker {
        void addMovement(MotionEvent motionEvent);

        void clear();

        void computeCurrentVelocity(int i);

        void computeCurrentVelocity(int i, float f);

        float getXVelocity();

        float getXVelocity(int i);

        float getYVelocity();

        float getYVelocity(int i);

        void recycle();
    }

    /* loaded from: classes.dex */
    public interface TransitionListener {
        void onTransitionChange(MotionLayout motionLayout, int i, int i2, float f);

        void onTransitionCompleted(MotionLayout motionLayout, int i);

        void onTransitionStarted(MotionLayout motionLayout, int i, int i2);

        void onTransitionTrigger(MotionLayout motionLayout, int i, boolean z, float f);
    }

    /* loaded from: classes.dex */
    public enum f {
        UNDEFINED,
        SETUP,
        MOVING,
        FINISHED
    }

    private static boolean a(float f2, float f3, float f4) {
        if (f2 > 0.0f) {
            float f5 = f2 / f4;
            return f3 + ((f2 * f5) - (((f4 * f5) * f5) / 2.0f)) > 1.0f;
        }
        float f6 = (-f2) / f4;
        return f3 + ((f2 * f6) + (((f4 * f6) * f6) / 2.0f)) < 0.0f;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f2, float f3, boolean z) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedScrollAccepted(View view, View view2, int i, int i2) {
    }

    public MotionLayout(@NonNull Context context) {
        super(context);
        a((AttributeSet) null);
    }

    public MotionLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public MotionLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    protected long getNanoTime() {
        return System.nanoTime();
    }

    public MotionTracker obtainVelocityTracker() {
        return d.a();
    }

    public void enableTransition(int i, boolean z) {
        MotionScene.Transition transition = getTransition(i);
        if (z) {
            transition.setEnable(true);
            return;
        }
        if (transition == this.a.b) {
            Iterator<MotionScene.Transition> it = this.a.getTransitionsWithState(this.d).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MotionScene.Transition next = it.next();
                if (next.isEnabled()) {
                    this.a.b = next;
                    break;
                }
            }
        }
        transition.setEnable(false);
    }

    public void setState(f fVar) {
        if (fVar != f.FINISHED || this.d != -1) {
            f fVar2 = this.E;
            this.E = fVar;
            if (fVar2 == f.MOVING && fVar == f.MOVING) {
                f();
            }
            switch (fVar2) {
                case UNDEFINED:
                case SETUP:
                    if (fVar == f.MOVING) {
                        f();
                    }
                    if (fVar == f.FINISHED) {
                        fireTransitionCompleted();
                        return;
                    }
                    return;
                case MOVING:
                    if (fVar == f.FINISHED) {
                        fireTransitionCompleted();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class d implements MotionTracker {
        private static d b = new d();
        VelocityTracker a;

        private d() {
        }

        public static d a() {
            b.a = VelocityTracker.obtain();
            return b;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void recycle() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.a = null;
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void clear() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void addMovement(MotionEvent motionEvent) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void computeCurrentVelocity(int i) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(i);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public void computeCurrentVelocity(int i, float f) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(i, f);
            }
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getXVelocity() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                return velocityTracker.getXVelocity();
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getYVelocity() {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                return velocityTracker.getYVelocity();
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getXVelocity(int i) {
            VelocityTracker velocityTracker = this.a;
            if (velocityTracker != null) {
                return velocityTracker.getXVelocity(i);
            }
            return 0.0f;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker
        public float getYVelocity(int i) {
            if (this.a != null) {
                return getYVelocity(i);
            }
            return 0.0f;
        }
    }

    public void setTransition(int i, int i2) {
        if (!isAttachedToWindow()) {
            if (this.al == null) {
                this.al = new e();
            }
            this.al.b(i);
            this.al.a(i2);
            return;
        }
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            this.H = i;
            this.I = i2;
            motionScene.a(i, i2);
            this.F.a(this.mLayoutWidget, this.a.a(i), this.a.a(i2));
            rebuildScene();
            this.g = 0.0f;
            transitionToStart();
        }
    }

    public void setTransition(int i) {
        if (this.a != null) {
            MotionScene.Transition transition = getTransition(i);
            int i2 = this.d;
            this.H = transition.getStartConstraintSetId();
            this.I = transition.getEndConstraintSetId();
            if (!isAttachedToWindow()) {
                if (this.al == null) {
                    this.al = new e();
                }
                this.al.b(this.H);
                this.al.a(this.I);
                return;
            }
            float f2 = Float.NaN;
            int i3 = this.d;
            float f3 = 0.0f;
            if (i3 == this.H) {
                f2 = 0.0f;
            } else if (i3 == this.I) {
                f2 = 1.0f;
            }
            this.a.setTransition(transition);
            this.F.a(this.mLayoutWidget, this.a.a(this.H), this.a.a(this.I));
            rebuildScene();
            if (!Float.isNaN(f2)) {
                f3 = f2;
            }
            this.g = f3;
            if (Float.isNaN(f2)) {
                Log.v("MotionLayout", Debug.getLocation() + " transitionToStart ");
                transitionToStart();
                return;
            }
            setProgress(f2);
        }
    }

    public void setTransition(MotionScene.Transition transition) {
        this.a.setTransition(transition);
        setState(f.SETUP);
        if (this.d == this.a.c()) {
            this.g = 1.0f;
            this.f = 1.0f;
            this.h = 1.0f;
        } else {
            this.g = 0.0f;
            this.f = 0.0f;
            this.h = 0.0f;
        }
        this.O = transition.isTransitionFlag(1) ? -1L : getNanoTime();
        int b2 = this.a.b();
        int c2 = this.a.c();
        if (b2 != this.H || c2 != this.I) {
            this.H = b2;
            this.I = c2;
            this.a.a(this.H, this.I);
            this.F.a(this.mLayoutWidget, this.a.a(this.H), this.a.a(this.I));
            this.F.b(this.H, this.I);
            this.F.a();
            rebuildScene();
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    public void loadLayoutDescription(int i) {
        if (i != 0) {
            try {
                this.a = new MotionScene(getContext(), this, i);
                if (Build.VERSION.SDK_INT < 19 || isAttachedToWindow()) {
                    this.a.a(this);
                    this.F.a(this.mLayoutWidget, this.a.a(this.H), this.a.a(this.I));
                    rebuildScene();
                    this.a.setRtl(isRtl());
                }
            } catch (Exception e2) {
                throw new IllegalArgumentException("unable to parse MotionScene file", e2);
            }
        } else {
            this.a = null;
        }
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        if (Build.VERSION.SDK_INT >= 19) {
            return super.isAttachedToWindow();
        }
        return getWindowToken() != null;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    public void setState(int i, int i2, int i3) {
        setState(f.SETUP);
        this.d = i;
        this.H = -1;
        this.I = -1;
        if (this.mConstraintLayoutSpec != null) {
            this.mConstraintLayoutSpec.updateConstraints(i, i2, i3);
            return;
        }
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            motionScene.a(i).applyTo(this);
        }
    }

    public void setInterpolatedProgress(float f2) {
        if (this.a != null) {
            setState(f.MOVING);
            Interpolator interpolator = this.a.getInterpolator();
            if (interpolator != null) {
                setProgress(interpolator.getInterpolation(f2));
                return;
            }
        }
        setProgress(f2);
    }

    public void setProgress(float f2, float f3) {
        if (!isAttachedToWindow()) {
            if (this.al == null) {
                this.al = new e();
            }
            this.al.a(f2);
            this.al.b(f3);
            return;
        }
        setProgress(f2);
        setState(f.MOVING);
        this.c = f3;
        a(1.0f);
    }

    /* loaded from: classes.dex */
    public class e {
        float a = Float.NaN;
        float b = Float.NaN;
        int c = -1;
        int d = -1;
        final String e = "motion.progress";
        final String f = "motion.velocity";
        final String g = "motion.StartState";
        final String h = "motion.EndState";

        e() {
            MotionLayout.this = r1;
        }

        void a() {
            if (!(this.c == -1 && this.d == -1)) {
                int i = this.c;
                if (i == -1) {
                    MotionLayout.this.transitionToState(this.d);
                } else {
                    int i2 = this.d;
                    if (i2 == -1) {
                        MotionLayout.this.setState(i, -1, -1);
                    } else {
                        MotionLayout.this.setTransition(i, i2);
                    }
                }
                MotionLayout.this.setState(f.SETUP);
            }
            if (!Float.isNaN(this.b)) {
                MotionLayout.this.setProgress(this.a, this.b);
                this.a = Float.NaN;
                this.b = Float.NaN;
                this.c = -1;
                this.d = -1;
            } else if (!Float.isNaN(this.a)) {
                MotionLayout.this.setProgress(this.a);
            }
        }

        public Bundle b() {
            Bundle bundle = new Bundle();
            bundle.putFloat("motion.progress", this.a);
            bundle.putFloat("motion.velocity", this.b);
            bundle.putInt("motion.StartState", this.c);
            bundle.putInt("motion.EndState", this.d);
            return bundle;
        }

        public void a(Bundle bundle) {
            this.a = bundle.getFloat("motion.progress");
            this.b = bundle.getFloat("motion.velocity");
            this.c = bundle.getInt("motion.StartState");
            this.d = bundle.getInt("motion.EndState");
        }

        public void a(float f) {
            this.a = f;
        }

        public void a(int i) {
            this.d = i;
        }

        public void b(float f) {
            this.b = f;
        }

        public void b(int i) {
            this.c = i;
        }

        public void c() {
            this.d = MotionLayout.this.I;
            this.c = MotionLayout.this.H;
            this.b = MotionLayout.this.getVelocity();
            this.a = MotionLayout.this.getProgress();
        }
    }

    public void setTransitionState(Bundle bundle) {
        if (this.al == null) {
            this.al = new e();
        }
        this.al.a(bundle);
        if (isAttachedToWindow()) {
            this.al.a();
        }
    }

    public Bundle getTransitionState() {
        if (this.al == null) {
            this.al = new e();
        }
        this.al.c();
        return this.al.b();
    }

    public void setProgress(float f2) {
        int i = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
        if (i < 0 || f2 > 1.0f) {
            Log.w("MotionLayout", "Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
        }
        if (!isAttachedToWindow()) {
            if (this.al == null) {
                this.al = new e();
            }
            this.al.a(f2);
            return;
        }
        if (i <= 0) {
            this.d = this.H;
            if (this.g == 0.0f) {
                setState(f.FINISHED);
            }
        } else if (f2 >= 1.0f) {
            this.d = this.I;
            if (this.g == 1.0f) {
                setState(f.FINISHED);
            }
        } else {
            this.d = -1;
            setState(f.MOVING);
        }
        if (this.a != null) {
            this.P = true;
            this.h = f2;
            this.f = f2;
            this.O = -1L;
            this.M = -1L;
            this.b = null;
            this.i = true;
            invalidate();
        }
    }

    public void b() {
        int childCount = getChildCount();
        this.F.b();
        boolean z = true;
        this.i = true;
        int width = getWidth();
        int height = getHeight();
        int gatPathMotionArc = this.a.gatPathMotionArc();
        int i = 0;
        if (gatPathMotionArc != -1) {
            for (int i2 = 0; i2 < childCount; i2++) {
                MotionController motionController = this.e.get(getChildAt(i2));
                if (motionController != null) {
                    motionController.setPathMotionArc(gatPathMotionArc);
                }
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            MotionController motionController2 = this.e.get(getChildAt(i3));
            if (motionController2 != null) {
                this.a.getKeyFrames(motionController2);
                motionController2.setup(width, height, this.N, getNanoTime());
            }
        }
        float staggered = this.a.getStaggered();
        if (staggered != 0.0f) {
            boolean z2 = ((double) staggered) < 0.0d;
            float abs = Math.abs(staggered);
            float f2 = -3.4028235E38f;
            float f3 = Float.MAX_VALUE;
            float f4 = -3.4028235E38f;
            float f5 = Float.MAX_VALUE;
            int i4 = 0;
            while (true) {
                if (i4 >= childCount) {
                    z = false;
                    break;
                }
                MotionController motionController3 = this.e.get(getChildAt(i4));
                if (!Float.isNaN(motionController3.d)) {
                    break;
                }
                float a2 = motionController3.a();
                float b2 = motionController3.b();
                float f6 = z2 ? b2 - a2 : b2 + a2;
                f5 = Math.min(f5, f6);
                f4 = Math.max(f4, f6);
                i4++;
            }
            if (z) {
                for (int i5 = 0; i5 < childCount; i5++) {
                    MotionController motionController4 = this.e.get(getChildAt(i5));
                    if (!Float.isNaN(motionController4.d)) {
                        f3 = Math.min(f3, motionController4.d);
                        f2 = Math.max(f2, motionController4.d);
                    }
                }
                while (i < childCount) {
                    MotionController motionController5 = this.e.get(getChildAt(i));
                    if (!Float.isNaN(motionController5.d)) {
                        motionController5.f = 1.0f / (1.0f - abs);
                        if (z2) {
                            motionController5.e = abs - (((f2 - motionController5.d) / (f2 - f3)) * abs);
                        } else {
                            motionController5.e = abs - (((motionController5.d - f3) * abs) / (f2 - f3));
                        }
                    }
                    i++;
                }
                return;
            }
            while (i < childCount) {
                MotionController motionController6 = this.e.get(getChildAt(i));
                float a3 = motionController6.a();
                float b3 = motionController6.b();
                float f7 = z2 ? b3 - a3 : b3 + a3;
                motionController6.f = 1.0f / (1.0f - abs);
                motionController6.e = abs - (((f7 - f5) * abs) / (f4 - f5));
                i++;
            }
        }
    }

    public void touchAnimateTo(int i, float f2, float f3) {
        if (this.a != null && this.g != f2) {
            this.T = true;
            this.M = getNanoTime();
            this.N = this.a.getDuration() / 1000.0f;
            this.h = f2;
            this.i = true;
            switch (i) {
                case 0:
                case 1:
                case 2:
                    if (i == 1) {
                        f2 = 0.0f;
                    } else if (i == 2) {
                        f2 = 1.0f;
                    }
                    this.U.config(this.g, f2, f3, this.N, this.a.d(), this.a.e());
                    int i2 = this.d;
                    this.h = f2;
                    this.d = i2;
                    this.b = this.U;
                    break;
                case 4:
                    this.V.a(f3, this.g, this.a.d());
                    this.b = this.V;
                    break;
                case 5:
                    if (!a(f3, this.g, this.a.d())) {
                        this.U.config(this.g, f2, f3, this.N, this.a.d(), this.a.e());
                        this.c = 0.0f;
                        int i3 = this.d;
                        this.h = f2;
                        this.d = i3;
                        this.b = this.U;
                        break;
                    } else {
                        this.V.a(f3, this.g, this.a.d());
                        this.b = this.V;
                        break;
                    }
            }
            this.P = false;
            this.M = getNanoTime();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a extends MotionInterpolator {
        float a = 0.0f;
        float b = 0.0f;
        float c;

        a() {
            MotionLayout.this = r1;
        }

        public void a(float f, float f2, float f3) {
            this.a = f;
            this.b = f2;
            this.c = f3;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator, android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = this.a;
            if (f2 > 0.0f) {
                float f3 = this.c;
                if (f2 / f3 < f) {
                    f = f2 / f3;
                }
                MotionLayout motionLayout = MotionLayout.this;
                float f4 = this.a;
                float f5 = this.c;
                motionLayout.c = f4 - (f5 * f);
                return ((f4 * f) - (((f5 * f) * f) / 2.0f)) + this.b;
            }
            float f6 = this.c;
            if ((-f2) / f6 < f) {
                f = (-f2) / f6;
            }
            MotionLayout motionLayout2 = MotionLayout.this;
            float f7 = this.a;
            float f8 = this.c;
            motionLayout2.c = (f8 * f) + f7;
            return (f7 * f) + (((f8 * f) * f) / 2.0f) + this.b;
        }

        @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
        public float getVelocity() {
            return MotionLayout.this.c;
        }
    }

    void a(float f2) {
        if (this.a != null) {
            float f3 = this.g;
            float f4 = this.f;
            if (f3 != f4 && this.P) {
                this.g = f4;
            }
            float f5 = this.g;
            if (f5 != f2) {
                this.T = false;
                this.h = f2;
                this.N = this.a.getDuration() / 1000.0f;
                setProgress(this.h);
                this.b = this.a.getInterpolator();
                this.P = false;
                this.M = getNanoTime();
                this.i = true;
                this.f = f5;
                this.g = f5;
                invalidate();
            }
        }
    }

    private void c() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            MotionController motionController = this.e.get(childAt);
            if (motionController != null) {
                motionController.a(childAt);
            }
        }
    }

    public void transitionToStart() {
        a(0.0f);
    }

    public void transitionToEnd() {
        a(1.0f);
    }

    public void transitionToState(int i) {
        if (!isAttachedToWindow()) {
            if (this.al == null) {
                this.al = new e();
            }
            this.al.a(i);
            return;
        }
        transitionToState(i, -1, -1);
    }

    public void transitionToState(int i, int i2, int i3) {
        int convertToConstraintSet;
        MotionScene motionScene = this.a;
        if (!(motionScene == null || motionScene.a == null || (convertToConstraintSet = this.a.a.convertToConstraintSet(this.d, i, i2, i3)) == -1)) {
            i = convertToConstraintSet;
        }
        int i4 = this.d;
        if (i4 != i) {
            if (this.H == i) {
                a(0.0f);
            } else if (this.I == i) {
                a(1.0f);
            } else {
                this.I = i;
                if (i4 != -1) {
                    setTransition(i4, i);
                    a(1.0f);
                    this.g = 0.0f;
                    transitionToEnd();
                    return;
                }
                this.T = false;
                this.h = 1.0f;
                this.f = 0.0f;
                this.g = 0.0f;
                this.O = getNanoTime();
                this.M = getNanoTime();
                this.P = false;
                this.b = null;
                this.N = this.a.getDuration() / 1000.0f;
                this.H = -1;
                this.a.a(this.H, this.I);
                this.a.b();
                int childCount = getChildCount();
                this.e.clear();
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    this.e.put(childAt, new MotionController(childAt));
                }
                this.i = true;
                this.F.a(this.mLayoutWidget, null, this.a.a(i));
                rebuildScene();
                this.F.b();
                c();
                int width = getWidth();
                int height = getHeight();
                for (int i6 = 0; i6 < childCount; i6++) {
                    MotionController motionController = this.e.get(getChildAt(i6));
                    this.a.getKeyFrames(motionController);
                    motionController.setup(width, height, this.N, getNanoTime());
                }
                float staggered = this.a.getStaggered();
                if (staggered != 0.0f) {
                    float f2 = Float.MAX_VALUE;
                    float f3 = -3.4028235E38f;
                    for (int i7 = 0; i7 < childCount; i7++) {
                        MotionController motionController2 = this.e.get(getChildAt(i7));
                        float b2 = motionController2.b() + motionController2.a();
                        f2 = Math.min(f2, b2);
                        f3 = Math.max(f3, b2);
                    }
                    for (int i8 = 0; i8 < childCount; i8++) {
                        MotionController motionController3 = this.e.get(getChildAt(i8));
                        float a2 = motionController3.a();
                        float b3 = motionController3.b();
                        motionController3.f = 1.0f / (1.0f - staggered);
                        motionController3.e = staggered - ((((a2 + b3) - f2) * staggered) / (f3 - f2));
                    }
                }
                this.f = 0.0f;
                this.g = 0.0f;
                this.i = true;
                invalidate();
            }
        }
    }

    public float getVelocity() {
        return this.c;
    }

    public void getViewVelocity(View view, float f2, float f3, float[] fArr, int i) {
        float f4 = this.c;
        float f5 = this.g;
        if (this.b != null) {
            float signum = Math.signum(this.h - f5);
            float interpolation = this.b.getInterpolation(this.g + 1.0E-5f);
            float interpolation2 = this.b.getInterpolation(this.g);
            f4 = (signum * ((interpolation - interpolation2) / 1.0E-5f)) / this.N;
            f5 = interpolation2;
        }
        Interpolator interpolator = this.b;
        if (interpolator instanceof MotionInterpolator) {
            f4 = ((MotionInterpolator) interpolator).getVelocity();
        }
        MotionController motionController = this.e.get(view);
        if ((i & 1) == 0) {
            motionController.a(f5, view.getWidth(), view.getHeight(), f2, f3, fArr);
        } else {
            motionController.a(f5, f2, f3, fArr);
        }
        if (i < 2) {
            fArr[0] = fArr[0] * f4;
            fArr[1] = fArr[1] * f4;
        }
    }

    /* loaded from: classes.dex */
    public class c {
        ConstraintWidgetContainer a = new ConstraintWidgetContainer();
        ConstraintWidgetContainer b = new ConstraintWidgetContainer();
        ConstraintSet c = null;
        ConstraintSet d = null;
        int e;
        int f;

        c() {
            MotionLayout.this = r1;
        }

        void a(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidgetContainer constraintWidgetContainer2) {
            ConstraintWidget constraintWidget;
            ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
            HashMap<ConstraintWidget, ConstraintWidget> hashMap = new HashMap<>();
            hashMap.put(constraintWidgetContainer, constraintWidgetContainer2);
            constraintWidgetContainer2.getChildren().clear();
            constraintWidgetContainer2.copy(constraintWidgetContainer, hashMap);
            Iterator<ConstraintWidget> it = children.iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                if (next instanceof Barrier) {
                    constraintWidget = new Barrier();
                } else if (next instanceof Guideline) {
                    constraintWidget = new Guideline();
                } else if (next instanceof Flow) {
                    constraintWidget = new Flow();
                } else if (next instanceof Helper) {
                    constraintWidget = new HelperWidget();
                } else {
                    constraintWidget = new ConstraintWidget();
                }
                constraintWidgetContainer2.add(constraintWidget);
                hashMap.put(next, constraintWidget);
            }
            Iterator<ConstraintWidget> it2 = children.iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                hashMap.get(next2).copy(next2, hashMap);
            }
        }

        void a(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            this.c = constraintSet;
            this.d = constraintSet2;
            this.a = new ConstraintWidgetContainer();
            this.b = new ConstraintWidgetContainer();
            this.a.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
            this.b.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
            this.a.removeAllChildren();
            this.b.removeAllChildren();
            a(MotionLayout.this.mLayoutWidget, this.a);
            a(MotionLayout.this.mLayoutWidget, this.b);
            if (MotionLayout.this.g > 0.5d) {
                if (constraintSet != null) {
                    a(this.a, constraintSet);
                }
                a(this.b, constraintSet2);
            } else {
                a(this.b, constraintSet2);
                if (constraintSet != null) {
                    a(this.a, constraintSet);
                }
            }
            this.a.setRtl(MotionLayout.this.isRtl());
            this.a.updateHierarchy();
            this.b.setRtl(MotionLayout.this.isRtl());
            this.b.updateHierarchy();
            ViewGroup.LayoutParams layoutParams = MotionLayout.this.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == -2) {
                    this.a.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                    this.b.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                }
                if (layoutParams.height == -2) {
                    this.a.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                    this.b.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                }
            }
        }

        private void a(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet) {
            SparseArray<ConstraintWidget> sparseArray = new SparseArray<>();
            Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            sparseArray.put(0, constraintWidgetContainer);
            sparseArray.put(MotionLayout.this.getId(), constraintWidgetContainer);
            Iterator<ConstraintWidget> it = constraintWidgetContainer.getChildren().iterator();
            while (it.hasNext()) {
                ConstraintWidget next = it.next();
                sparseArray.put(((View) next.getCompanionWidget()).getId(), next);
            }
            Iterator<ConstraintWidget> it2 = constraintWidgetContainer.getChildren().iterator();
            while (it2.hasNext()) {
                ConstraintWidget next2 = it2.next();
                View view = (View) next2.getCompanionWidget();
                constraintSet.applyToLayoutParams(view.getId(), layoutParams);
                next2.setWidth(constraintSet.getWidth(view.getId()));
                next2.setHeight(constraintSet.getHeight(view.getId()));
                if (view instanceof ConstraintHelper) {
                    constraintSet.applyToHelper((ConstraintHelper) view, next2, layoutParams, sparseArray);
                    if (view instanceof androidx.constraintlayout.widget.Barrier) {
                        ((androidx.constraintlayout.widget.Barrier) view).validateParams();
                    }
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    layoutParams.resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
                } else {
                    layoutParams.resolveLayoutDirection(0);
                }
                MotionLayout.this.applyConstraintsFromLayoutParams(false, view, next2, layoutParams, sparseArray);
                if (constraintSet.getVisibilityMode(view.getId()) == 1) {
                    next2.setVisibility(view.getVisibility());
                } else {
                    next2.setVisibility(constraintSet.getVisibility(view.getId()));
                }
            }
            Iterator<ConstraintWidget> it3 = constraintWidgetContainer.getChildren().iterator();
            while (it3.hasNext()) {
                ConstraintWidget next3 = it3.next();
                if (next3 instanceof VirtualLayout) {
                    Helper helper = (Helper) next3;
                    ((ConstraintHelper) next3.getCompanionWidget()).updatePreLayout(constraintWidgetContainer, helper, sparseArray);
                    ((VirtualLayout) helper).captureWidgets();
                }
            }
        }

        ConstraintWidget a(ConstraintWidgetContainer constraintWidgetContainer, View view) {
            if (constraintWidgetContainer.getCompanionWidget() == view) {
                return constraintWidgetContainer;
            }
            ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
            int size = children.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = children.get(i);
                if (constraintWidget.getCompanionWidget() == view) {
                    return constraintWidget;
                }
            }
            return null;
        }

        public void a() {
            a(MotionLayout.this.J, MotionLayout.this.K);
            MotionLayout.this.b();
        }

        public void a(int i, int i2) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            MotionLayout motionLayout = MotionLayout.this;
            motionLayout.B = mode;
            motionLayout.C = mode2;
            int optimizationLevel = motionLayout.getOptimizationLevel();
            if (MotionLayout.this.d == MotionLayout.this.getStartState()) {
                MotionLayout.this.resolveSystem(this.b, optimizationLevel, i, i2);
                if (this.c != null) {
                    MotionLayout.this.resolveSystem(this.a, optimizationLevel, i, i2);
                }
            } else {
                if (this.c != null) {
                    MotionLayout.this.resolveSystem(this.a, optimizationLevel, i, i2);
                }
                MotionLayout.this.resolveSystem(this.b, optimizationLevel, i, i2);
            }
            if (((MotionLayout.this.getParent() instanceof MotionLayout) && mode == 1073741824 && mode2 == 1073741824) ? false : true) {
                MotionLayout motionLayout2 = MotionLayout.this;
                motionLayout2.B = mode;
                motionLayout2.C = mode2;
                if (motionLayout2.d == MotionLayout.this.getStartState()) {
                    MotionLayout.this.resolveSystem(this.b, optimizationLevel, i, i2);
                    if (this.c != null) {
                        MotionLayout.this.resolveSystem(this.a, optimizationLevel, i, i2);
                    }
                } else {
                    if (this.c != null) {
                        MotionLayout.this.resolveSystem(this.a, optimizationLevel, i, i2);
                    }
                    MotionLayout.this.resolveSystem(this.b, optimizationLevel, i, i2);
                }
                MotionLayout.this.x = this.a.getWidth();
                MotionLayout.this.y = this.a.getHeight();
                MotionLayout.this.z = this.b.getWidth();
                MotionLayout.this.A = this.b.getHeight();
                MotionLayout motionLayout3 = MotionLayout.this;
                motionLayout3.mMeasureDuringTransition = (motionLayout3.x == MotionLayout.this.z && MotionLayout.this.y == MotionLayout.this.A) ? false : true;
            }
            int i3 = MotionLayout.this.x;
            int i4 = MotionLayout.this.y;
            if (MotionLayout.this.B == Integer.MIN_VALUE || MotionLayout.this.B == 0) {
                i3 = (int) (MotionLayout.this.x + (MotionLayout.this.D * (MotionLayout.this.z - MotionLayout.this.x)));
            }
            if (MotionLayout.this.C == Integer.MIN_VALUE || MotionLayout.this.C == 0) {
                i4 = (int) (MotionLayout.this.y + (MotionLayout.this.D * (MotionLayout.this.A - MotionLayout.this.y)));
            }
            MotionLayout.this.resolveMeasuredDimension(i, i2, i3, i4, this.a.isWidthMeasuredTooSmall() || this.b.isWidthMeasuredTooSmall(), this.a.isHeightMeasuredTooSmall() || this.b.isHeightMeasuredTooSmall());
        }

        public void b() {
            int childCount = MotionLayout.this.getChildCount();
            MotionLayout.this.e.clear();
            for (int i = 0; i < childCount; i++) {
                View childAt = MotionLayout.this.getChildAt(i);
                MotionLayout.this.e.put(childAt, new MotionController(childAt));
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt2 = MotionLayout.this.getChildAt(i2);
                MotionController motionController = MotionLayout.this.e.get(childAt2);
                if (motionController != null) {
                    if (this.c != null) {
                        ConstraintWidget a = a(this.a, childAt2);
                        if (a != null) {
                            motionController.a(a, this.c);
                        } else if (MotionLayout.this.k != 0) {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                    if (this.d != null) {
                        ConstraintWidget a2 = a(this.b, childAt2);
                        if (a2 != null) {
                            motionController.b(a2, this.d);
                        } else if (MotionLayout.this.k != 0) {
                            Log.e("MotionLayout", Debug.getLocation() + "no widget for  " + Debug.getName(childAt2) + " (" + childAt2.getClass().getName() + ")");
                        }
                    }
                }
            }
        }

        public void b(int i, int i2) {
            this.e = i;
            this.f = i2;
        }

        public boolean c(int i, int i2) {
            return (i == this.e && i2 == this.f) ? false : true;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View, android.view.ViewParent
    public void requestLayout() {
        MotionScene motionScene;
        if (this.mMeasureDuringTransition || this.d != -1 || (motionScene = this.a) == null || motionScene.b == null || this.a.b.getLayoutDuringTransition() != 0) {
            super.requestLayout();
        }
    }

    @Override // android.view.View
    public String toString() {
        Context context = getContext();
        return Debug.getName(context, this.H) + "->" + Debug.getName(context, this.I) + " (pos:" + this.g + " Dpos/Dt:" + this.c;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.a == null) {
            super.onMeasure(i, i2);
            return;
        }
        boolean z = false;
        boolean z2 = (this.J == i && this.K == i2) ? false : true;
        if (this.am) {
            this.am = false;
            a();
            g();
            z2 = true;
        }
        if (this.mDirtyHierarchy) {
            z2 = true;
        }
        this.J = i;
        this.K = i2;
        int b2 = this.a.b();
        int c2 = this.a.c();
        if ((z2 || this.F.c(b2, c2)) && this.H != -1) {
            super.onMeasure(i, i2);
            this.F.a(this.mLayoutWidget, this.a.a(b2), this.a.a(c2));
            this.F.a();
            this.F.b(b2, c2);
        } else {
            z = true;
        }
        if (this.mMeasureDuringTransition || z) {
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int width = this.mLayoutWidget.getWidth() + getPaddingLeft() + getPaddingRight();
            int height = this.mLayoutWidget.getHeight() + paddingTop;
            int i3 = this.B;
            if (i3 == Integer.MIN_VALUE || i3 == 0) {
                int i4 = this.x;
                width = (int) (i4 + (this.D * (this.z - i4)));
                requestLayout();
            }
            int i5 = this.C;
            if (i5 == Integer.MIN_VALUE || i5 == 0) {
                int i6 = this.y;
                height = (int) (i6 + (this.D * (this.A - i6)));
                requestLayout();
            }
            setMeasuredDimension(width, height);
        }
        d();
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        MotionScene motionScene = this.a;
        return (motionScene == null || motionScene.b == null || this.a.b.getTouchResponse() == null || (this.a.b.getTouchResponse().f() & 2) != 0) ? false : true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onStopNestedScroll(View view, int i) {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            float f2 = this.s;
            float f3 = this.v;
            motionScene.b(f2 / f3, this.t / f3);
        }
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (!(!this.r && i == 0 && i2 == 0)) {
            iArr[0] = iArr[0] + i3;
            iArr[1] = iArr[1] + i4;
        }
        this.r = false;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(final View view, int i, int i2, int[] iArr, int i3) {
        d touchResponse;
        int e2;
        MotionScene motionScene = this.a;
        if (motionScene != null && motionScene.b != null && this.a.b.isEnabled()) {
            MotionScene.Transition transition = this.a.b;
            if (transition == null || !transition.isEnabled() || (touchResponse = transition.getTouchResponse()) == null || (e2 = touchResponse.e()) == -1 || view.getId() == e2) {
                MotionScene motionScene2 = this.a;
                if (motionScene2 != null && motionScene2.g()) {
                    float f2 = this.f;
                    if ((f2 == 1.0f || f2 == 0.0f) && view.canScrollVertically(-1)) {
                        return;
                    }
                }
                if (!(transition.getTouchResponse() == null || (this.a.b.getTouchResponse().f() & 1) == 0)) {
                    float c2 = this.a.c(i, i2);
                    if ((this.g <= 0.0f && c2 < 0.0f) || (this.g >= 1.0f && c2 > 0.0f)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            view.setNestedScrollingEnabled(false);
                            view.post(new Runnable() { // from class: androidx.constraintlayout.motion.widget.MotionLayout.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    view.setNestedScrollingEnabled(true);
                                }
                            });
                            return;
                        }
                        return;
                    }
                }
                float f3 = this.f;
                long nanoTime = getNanoTime();
                float f4 = i;
                this.s = f4;
                float f5 = i2;
                this.t = f5;
                this.v = (float) ((nanoTime - this.u) * 1.0E-9d);
                this.u = nanoTime;
                this.a.a(f4, f5);
                if (f3 != this.f) {
                    iArr[0] = i;
                    iArr[1] = i2;
                }
                a(false);
                if (iArr[0] != 0 || iArr[1] != 0) {
                    this.r = true;
                }
            }
        }
    }

    /* loaded from: classes.dex */
    private class b {
        float[] a;
        Path d;
        int p;
        int s;
        final int j = -21965;
        final int k = -2067046;
        final int l = -13391360;
        final int m = 1996488704;
        final int n = 10;
        Rect q = new Rect();
        boolean r = false;
        Paint e = new Paint();
        Paint f = new Paint();
        Paint g = new Paint();
        Paint h = new Paint();
        private float[] u = new float[8];
        Paint i = new Paint();
        DashPathEffect o = new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f);
        float[] c = new float[100];
        int[] b = new int[50];

        public b() {
            MotionLayout.this = r7;
            this.s = 1;
            this.e.setAntiAlias(true);
            this.e.setColor(-21965);
            this.e.setStrokeWidth(2.0f);
            this.e.setStyle(Paint.Style.STROKE);
            this.f.setAntiAlias(true);
            this.f.setColor(-2067046);
            this.f.setStrokeWidth(2.0f);
            this.f.setStyle(Paint.Style.STROKE);
            this.g.setAntiAlias(true);
            this.g.setColor(-13391360);
            this.g.setStrokeWidth(2.0f);
            this.g.setStyle(Paint.Style.STROKE);
            this.h.setAntiAlias(true);
            this.h.setColor(-13391360);
            this.h.setTextSize(r7.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.i.setAntiAlias(true);
            this.g.setPathEffect(this.o);
            if (this.r) {
                this.e.setStrokeWidth(8.0f);
                this.i.setStrokeWidth(8.0f);
                this.f.setStrokeWidth(8.0f);
                this.s = 4;
            }
        }

        public void a(Canvas canvas, HashMap<View, MotionController> hashMap, int i, int i2) {
            if (!(hashMap == null || hashMap.size() == 0)) {
                canvas.save();
                if (!MotionLayout.this.isInEditMode() && (i2 & 1) == 2) {
                    String str = MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.I) + Constants.COLON_SEPARATOR + MotionLayout.this.getProgress();
                    canvas.drawText(str, 10.0f, MotionLayout.this.getHeight() - 30, this.h);
                    canvas.drawText(str, 11.0f, MotionLayout.this.getHeight() - 29, this.e);
                }
                for (MotionController motionController : hashMap.values()) {
                    int drawPath = motionController.getDrawPath();
                    if (i2 > 0 && drawPath == 0) {
                        drawPath = 1;
                    }
                    if (drawPath != 0) {
                        this.p = motionController.a(this.c, this.b);
                        if (drawPath >= 1) {
                            int i3 = i / 16;
                            float[] fArr = this.a;
                            if (fArr == null || fArr.length != i3 * 2) {
                                this.a = new float[i3 * 2];
                                this.d = new Path();
                            }
                            int i4 = this.s;
                            canvas.translate(i4, i4);
                            this.e.setColor(1996488704);
                            this.i.setColor(1996488704);
                            this.f.setColor(1996488704);
                            this.g.setColor(1996488704);
                            motionController.a(this.a, i3);
                            a(canvas, drawPath, this.p, motionController);
                            this.e.setColor(-21965);
                            this.f.setColor(-2067046);
                            this.i.setColor(-2067046);
                            this.g.setColor(-13391360);
                            int i5 = this.s;
                            canvas.translate(-i5, -i5);
                            a(canvas, drawPath, this.p, motionController);
                            if (drawPath == 5) {
                                a(canvas, motionController);
                            }
                        }
                    }
                }
                canvas.restore();
            }
        }

        public void a(Canvas canvas, int i, int i2, MotionController motionController) {
            if (i == 4) {
                c(canvas);
            }
            if (i == 2) {
                b(canvas);
            }
            if (i == 3) {
                d(canvas);
            }
            a(canvas);
            b(canvas, i, i2, motionController);
        }

        private void a(Canvas canvas) {
            canvas.drawLines(this.a, this.e);
        }

        private void b(Canvas canvas, int i, int i2, MotionController motionController) {
            int i3;
            int i4;
            float f;
            float f2;
            int i5;
            if (motionController.a != null) {
                i4 = motionController.a.getWidth();
                i3 = motionController.a.getHeight();
            } else {
                i4 = 0;
                i3 = 0;
            }
            for (int i6 = 1; i6 < i2 - 1; i6++) {
                if (i != 4 || this.b[i6 - 1] != 0) {
                    float[] fArr = this.c;
                    int i7 = i6 * 2;
                    float f3 = fArr[i7];
                    float f4 = fArr[i7 + 1];
                    this.d.reset();
                    this.d.moveTo(f3, f4 + 10.0f);
                    this.d.lineTo(f3 + 10.0f, f4);
                    this.d.lineTo(f3, f4 - 10.0f);
                    this.d.lineTo(f3 - 10.0f, f4);
                    this.d.close();
                    int i8 = i6 - 1;
                    motionController.a(i8);
                    if (i == 4) {
                        int[] iArr = this.b;
                        if (iArr[i8] == 1) {
                            a(canvas, f3 - 0.0f, f4 - 0.0f);
                            i5 = 3;
                            f2 = f4;
                            f = f3;
                        } else if (iArr[i8] == 2) {
                            b(canvas, f3 - 0.0f, f4 - 0.0f);
                            i5 = 3;
                            f2 = f4;
                            f = f3;
                        } else if (iArr[i8] == 3) {
                            i5 = 3;
                            f2 = f4;
                            f = f3;
                            a(canvas, f3 - 0.0f, f4 - 0.0f, i4, i3);
                        } else {
                            i5 = 3;
                            f2 = f4;
                            f = f3;
                        }
                        canvas.drawPath(this.d, this.i);
                    } else {
                        i5 = 3;
                        f2 = f4;
                        f = f3;
                    }
                    if (i == 2) {
                        a(canvas, f - 0.0f, f2 - 0.0f);
                    }
                    if (i == i5) {
                        b(canvas, f - 0.0f, f2 - 0.0f);
                    }
                    if (i == 6) {
                        a(canvas, f - 0.0f, f2 - 0.0f, i4, i3);
                    }
                    canvas.drawPath(this.d, this.i);
                }
            }
            float[] fArr2 = this.a;
            if (fArr2.length > 1) {
                canvas.drawCircle(fArr2[0], fArr2[1], 8.0f, this.f);
                float[] fArr3 = this.a;
                canvas.drawCircle(fArr3[fArr3.length - 2], fArr3[fArr3.length - 1], 8.0f, this.f);
            }
        }

        private void a(Canvas canvas, float f, float f2, float f3, float f4) {
            canvas.drawRect(f, f2, f3, f4, this.g);
            canvas.drawLine(f, f2, f3, f4, this.g);
        }

        private void b(Canvas canvas) {
            float[] fArr = this.a;
            canvas.drawLine(fArr[0], fArr[1], fArr[fArr.length - 2], fArr[fArr.length - 1], this.g);
        }

        private void c(Canvas canvas) {
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < this.p; i++) {
                if (this.b[i] == 1) {
                    z = true;
                }
                if (this.b[i] == 2) {
                    z2 = true;
                }
            }
            if (z) {
                b(canvas);
            }
            if (z2) {
                d(canvas);
            }
        }

        private void a(Canvas canvas, float f, float f2) {
            float[] fArr = this.a;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float hypot = (float) Math.hypot(f3 - f5, f4 - f6);
            float f7 = f5 - f3;
            float f8 = f6 - f4;
            float f9 = (((f - f3) * f7) + ((f2 - f4) * f8)) / (hypot * hypot);
            float f10 = f3 + (f7 * f9);
            float f11 = f4 + (f9 * f8);
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f10, f11);
            float hypot2 = (float) Math.hypot(f10 - f, f11 - f2);
            String str = "" + (((int) ((hypot2 * 100.0f) / hypot)) / 100.0f);
            a(str, this.h);
            canvas.drawTextOnPath(str, path, (hypot2 / 2.0f) - (this.q.width() / 2), -20.0f, this.h);
            canvas.drawLine(f, f2, f10, f11, this.g);
        }

        void a(String str, Paint paint) {
            paint.getTextBounds(str, 0, str.length(), this.q);
        }

        private void d(Canvas canvas) {
            float[] fArr = this.a;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[fArr.length - 2];
            float f4 = fArr[fArr.length - 1];
            canvas.drawLine(Math.min(f, f3), Math.max(f2, f4), Math.max(f, f3), Math.max(f2, f4), this.g);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), this.g);
        }

        private void b(Canvas canvas, float f, float f2) {
            float[] fArr = this.a;
            float f3 = fArr[0];
            float f4 = fArr[1];
            float f5 = fArr[fArr.length - 2];
            float f6 = fArr[fArr.length - 1];
            float min = Math.min(f3, f5);
            float max = Math.max(f4, f6);
            float min2 = f - Math.min(f3, f5);
            float max2 = Math.max(f4, f6) - f2;
            String str = "" + (((int) (((min2 * 100.0f) / Math.abs(f5 - f3)) + 0.5d)) / 100.0f);
            a(str, this.h);
            canvas.drawText(str, ((min2 / 2.0f) - (this.q.width() / 2)) + min, f2 - 20.0f, this.h);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.g);
            String str2 = "" + (((int) (((max2 * 100.0f) / Math.abs(f6 - f4)) + 0.5d)) / 100.0f);
            a(str2, this.h);
            canvas.drawText(str2, f + 5.0f, max - ((max2 / 2.0f) - (this.q.height() / 2)), this.h);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.g);
        }

        private void a(Canvas canvas, float f, float f2, int i, int i2) {
            String str = "" + (((int) ((((f - (i / 2)) * 100.0f) / (MotionLayout.this.getWidth() - i)) + 0.5d)) / 100.0f);
            a(str, this.h);
            canvas.drawText(str, ((f / 2.0f) - (this.q.width() / 2)) + 0.0f, f2 - 20.0f, this.h);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.g);
            String str2 = "" + (((int) ((((f2 - (i2 / 2)) * 100.0f) / (MotionLayout.this.getHeight() - i2)) + 0.5d)) / 100.0f);
            a(str2, this.h);
            canvas.drawText(str2, f + 5.0f, 0.0f - ((f2 / 2.0f) - (this.q.height() / 2)), this.h);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.g);
        }

        private void a(Canvas canvas, MotionController motionController) {
            this.d.reset();
            for (int i = 0; i <= 50; i++) {
                motionController.a(i / 50, this.u, 0);
                Path path = this.d;
                float[] fArr = this.u;
                path.moveTo(fArr[0], fArr[1]);
                Path path2 = this.d;
                float[] fArr2 = this.u;
                path2.lineTo(fArr2[2], fArr2[3]);
                Path path3 = this.d;
                float[] fArr3 = this.u;
                path3.lineTo(fArr3[4], fArr3[5]);
                Path path4 = this.d;
                float[] fArr4 = this.u;
                path4.lineTo(fArr4[6], fArr4[7]);
                this.d.close();
            }
            this.e.setColor(1140850688);
            canvas.translate(2.0f, 2.0f);
            canvas.drawPath(this.d, this.e);
            canvas.translate(-2.0f, -2.0f);
            this.e.setColor(-65536);
            canvas.drawPath(this.d, this.e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        a(false);
        super.dispatchDraw(canvas);
        if (this.a != null) {
            if ((this.k & 1) == 1 && !isInEditMode()) {
                this.ae++;
                long nanoTime = getNanoTime();
                long j = this.af;
                if (j != -1) {
                    long j2 = nanoTime - j;
                    if (j2 > 200000000) {
                        this.ag = ((int) ((this.ae / (((float) j2) * 1.0E-9f)) * 100.0f)) / 100.0f;
                        this.ae = 0;
                        this.af = nanoTime;
                    }
                } else {
                    this.af = nanoTime;
                }
                Paint paint = new Paint();
                paint.setTextSize(42.0f);
                StringBuilder sb = new StringBuilder();
                sb.append(this.ag + " fps " + Debug.getState(this, this.H) + " -> ");
                sb.append(Debug.getState(this, this.I));
                sb.append(" (progress: ");
                sb.append(((int) (getProgress() * 1000.0f)) / 10.0f);
                sb.append(" ) state=");
                int i = this.d;
                sb.append(i == -1 ? "undefined" : Debug.getState(this, i));
                String sb2 = sb.toString();
                paint.setColor(ViewCompat.MEASURED_STATE_MASK);
                canvas.drawText(sb2, 11.0f, getHeight() - 29, paint);
                paint.setColor(-7864184);
                canvas.drawText(sb2, 10.0f, getHeight() - 30, paint);
            }
            if (this.k > 1) {
                if (this.l == null) {
                    this.l = new b();
                }
                this.l.a(canvas, this.e, this.a.getDuration(), this.k);
            }
        }
    }

    private void d() {
        boolean z;
        float signum = Math.signum(this.h - this.g);
        long nanoTime = getNanoTime();
        float f2 = this.g + (!(this.b instanceof StopLogic) ? ((((float) (nanoTime - this.O)) * signum) * 1.0E-9f) / this.N : 0.0f);
        if (this.P) {
            f2 = this.h;
        }
        int i = (signum > 0.0f ? 1 : (signum == 0.0f ? 0 : -1));
        if ((i <= 0 || f2 < this.h) && (signum > 0.0f || f2 > this.h)) {
            z = false;
        } else {
            f2 = this.h;
            z = true;
        }
        Interpolator interpolator = this.b;
        if (interpolator != null && !z) {
            if (this.T) {
                f2 = interpolator.getInterpolation(((float) (nanoTime - this.M)) * 1.0E-9f);
            } else {
                f2 = interpolator.getInterpolation(f2);
            }
        }
        if ((i > 0 && f2 >= this.h) || (signum <= 0.0f && f2 <= this.h)) {
            f2 = this.h;
        }
        this.D = f2;
        int childCount = getChildCount();
        long nanoTime2 = getNanoTime();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            MotionController motionController = this.e.get(childAt);
            if (motionController != null) {
                motionController.a(childAt, f2, nanoTime2, this.aj);
            }
        }
        if (this.mMeasureDuringTransition) {
            requestLayout();
        }
    }

    public void a(boolean z) {
        boolean z2;
        float f2;
        boolean z3;
        int i;
        int i2;
        if (this.O == -1) {
            this.O = getNanoTime();
        }
        float f3 = this.g;
        if (f3 > 0.0f && f3 < 1.0f) {
            this.d = -1;
        }
        boolean z4 = false;
        if (this.aa || (this.i && (z || this.h != this.g))) {
            float signum = Math.signum(this.h - this.g);
            long nanoTime = getNanoTime();
            if (!(this.b instanceof MotionInterpolator)) {
                f2 = ((((float) (nanoTime - this.O)) * signum) * 1.0E-9f) / this.N;
                this.c = f2;
            } else {
                f2 = 0.0f;
            }
            float f4 = this.g + f2;
            if (this.P) {
                f4 = this.h;
            }
            int i3 = (signum > 0.0f ? 1 : (signum == 0.0f ? 0 : -1));
            if ((i3 <= 0 || f4 < this.h) && (signum > 0.0f || f4 > this.h)) {
                z3 = false;
            } else {
                f4 = this.h;
                this.i = false;
                z3 = true;
            }
            this.g = f4;
            this.f = f4;
            this.O = nanoTime;
            Interpolator interpolator = this.b;
            if (interpolator != null && !z3) {
                if (this.T) {
                    float interpolation = interpolator.getInterpolation(((float) (nanoTime - this.M)) * 1.0E-9f);
                    this.g = interpolation;
                    this.O = nanoTime;
                    Interpolator interpolator2 = this.b;
                    if (interpolator2 instanceof MotionInterpolator) {
                        float velocity = ((MotionInterpolator) interpolator2).getVelocity();
                        this.c = velocity;
                        if (Math.abs(velocity) * this.N <= 1.0E-5f) {
                            this.i = false;
                        }
                        if (velocity > 0.0f && interpolation >= 1.0f) {
                            this.g = 1.0f;
                            this.i = false;
                            interpolation = 1.0f;
                        }
                        if (velocity >= 0.0f || interpolation > 0.0f) {
                            f4 = interpolation;
                        } else {
                            this.g = 0.0f;
                            this.i = false;
                            f4 = 0.0f;
                        }
                    } else {
                        f4 = interpolation;
                    }
                } else {
                    float interpolation2 = interpolator.getInterpolation(f4);
                    Interpolator interpolator3 = this.b;
                    if (interpolator3 instanceof MotionInterpolator) {
                        this.c = ((MotionInterpolator) interpolator3).getVelocity();
                    } else {
                        this.c = ((interpolator3.getInterpolation(f4 + f2) - interpolation2) * signum) / f2;
                    }
                    f4 = interpolation2;
                }
            }
            if (Math.abs(this.c) > 1.0E-5f) {
                setState(f.MOVING);
            }
            if ((i3 > 0 && f4 >= this.h) || (signum <= 0.0f && f4 <= this.h)) {
                f4 = this.h;
                this.i = false;
            }
            int i4 = (f4 > 1.0f ? 1 : (f4 == 1.0f ? 0 : -1));
            if (i4 >= 0 || f4 <= 0.0f) {
                this.i = false;
                setState(f.FINISHED);
            }
            int childCount = getChildCount();
            this.aa = false;
            long nanoTime2 = getNanoTime();
            this.D = f4;
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                MotionController motionController = this.e.get(childAt);
                if (motionController != null) {
                    this.aa = motionController.a(childAt, f4, nanoTime2, this.aj) | this.aa;
                }
            }
            boolean z5 = (i3 > 0 && f4 >= this.h) || (signum <= 0.0f && f4 <= this.h);
            if (!this.aa && !this.i && z5) {
                setState(f.FINISHED);
            }
            if (this.mMeasureDuringTransition) {
                requestLayout();
            }
            z2 = true;
            this.aa = (!z5) | this.aa;
            if (!(f4 > 0.0f || (i2 = this.H) == -1 || this.d == i2)) {
                this.d = i2;
                this.a.a(i2).applyCustomAttributes(this);
                setState(f.FINISHED);
                z4 = true;
            }
            if (f4 >= 1.0d && this.d != (i = this.I)) {
                this.d = i;
                this.a.a(i).applyCustomAttributes(this);
                setState(f.FINISHED);
                z4 = true;
            }
            if (this.aa || this.i) {
                invalidate();
            } else if ((i3 > 0 && i4 == 0) || (signum < 0.0f && f4 == 0.0f)) {
                setState(f.FINISHED);
            }
            if ((!this.aa && this.i && i3 > 0 && i4 == 0) || (signum < 0.0f && f4 == 0.0f)) {
                a();
            }
        } else {
            z4 = false;
            z2 = true;
        }
        float f5 = this.g;
        if (f5 >= 1.0f) {
            if (this.d != this.I) {
                z4 = z2;
            }
            this.d = this.I;
        } else if (f5 <= 0.0f) {
            if (this.d != this.H) {
                z4 = z2;
            }
            this.d = this.H;
        }
        this.am |= z4;
        if (z4 && !this.ak) {
            requestLayout();
        }
        this.f = this.g;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.ak = true;
        try {
            if (this.a == null) {
                super.onLayout(z, i, i2, i3, i4);
                return;
            }
            int i5 = i3 - i;
            int i6 = i4 - i2;
            if (!(this.p == i5 && this.q == i6)) {
                rebuildScene();
                a(true);
            }
            this.p = i5;
            this.q = i6;
            this.n = i5;
            this.o = i6;
        } finally {
            this.ak = false;
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout
    protected void parseLayoutDescription(int i) {
        this.mConstraintLayoutSpec = null;
    }

    private void a(AttributeSet attributeSet) {
        MotionScene motionScene;
        IS_IN_EDIT_MODE = isInEditMode();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MotionLayout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            boolean z = true;
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.MotionLayout_layoutDescription) {
                    this.a = new MotionScene(getContext(), this, obtainStyledAttributes.getResourceId(index, -1));
                } else if (index == R.styleable.MotionLayout_currentState) {
                    this.d = obtainStyledAttributes.getResourceId(index, -1);
                } else if (index == R.styleable.MotionLayout_motionProgress) {
                    this.h = obtainStyledAttributes.getFloat(index, 0.0f);
                    this.i = true;
                } else if (index == R.styleable.MotionLayout_applyMotionScene) {
                    z = obtainStyledAttributes.getBoolean(index, z);
                } else if (index == R.styleable.MotionLayout_showPaths) {
                    if (this.k == 0) {
                        this.k = obtainStyledAttributes.getBoolean(index, false) ? 2 : 0;
                    }
                } else if (index == R.styleable.MotionLayout_motionDebug) {
                    this.k = obtainStyledAttributes.getInt(index, 0);
                }
            }
            obtainStyledAttributes.recycle();
            if (this.a == null) {
                Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
            }
            if (!z) {
                this.a = null;
            }
        }
        if (this.k != 0) {
            e();
        }
        if (this.d == -1 && (motionScene = this.a) != null) {
            this.d = motionScene.b();
            this.H = this.a.b();
            this.I = this.a.c();
        }
    }

    public void setScene(MotionScene motionScene) {
        this.a = motionScene;
        this.a.setRtl(isRtl());
        rebuildScene();
    }

    private void e() {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            return;
        }
        int b2 = motionScene.b();
        MotionScene motionScene2 = this.a;
        a(b2, motionScene2.a(motionScene2.b()));
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        Iterator<MotionScene.Transition> it = this.a.getDefinedTransitions().iterator();
        while (it.hasNext()) {
            MotionScene.Transition next = it.next();
            if (next == this.a.b) {
                Log.v("MotionLayout", "CHECK: CURRENT");
            }
            a(next);
            int startConstraintSetId = next.getStartConstraintSetId();
            int endConstraintSetId = next.getEndConstraintSetId();
            String name = Debug.getName(getContext(), startConstraintSetId);
            String name2 = Debug.getName(getContext(), endConstraintSetId);
            if (sparseIntArray.get(startConstraintSetId) == endConstraintSetId) {
                Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + name + "->" + name2);
            }
            if (sparseIntArray2.get(endConstraintSetId) == startConstraintSetId) {
                Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + name + "->" + name2);
            }
            sparseIntArray.put(startConstraintSetId, endConstraintSetId);
            sparseIntArray2.put(endConstraintSetId, startConstraintSetId);
            if (this.a.a(startConstraintSetId) == null) {
                Log.e("MotionLayout", " no such constraintSetStart " + name);
            }
            if (this.a.a(endConstraintSetId) == null) {
                Log.e("MotionLayout", " no such constraintSetEnd " + name);
            }
        }
    }

    private void a(int i, ConstraintSet constraintSet) {
        String name = Debug.getName(getContext(), i);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            if (id == -1) {
                Log.w("MotionLayout", "CHECK: " + name + " ALL VIEWS SHOULD HAVE ID's " + childAt.getClass().getName() + " does not!");
            }
            if (constraintSet.getConstraint(id) == null) {
                Log.w("MotionLayout", "CHECK: " + name + " NO CONSTRAINTS for " + Debug.getName(childAt));
            }
        }
        int[] knownIds = constraintSet.getKnownIds();
        for (int i3 = 0; i3 < knownIds.length; i3++) {
            int i4 = knownIds[i3];
            String name2 = Debug.getName(getContext(), i4);
            if (findViewById(knownIds[i3]) == null) {
                Log.w("MotionLayout", "CHECK: " + name + " NO View matches id " + name2);
            }
            if (constraintSet.getHeight(i4) == -1) {
                Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
            }
            if (constraintSet.getWidth(i4) == -1) {
                Log.w("MotionLayout", "CHECK: " + name + "(" + name2 + ") no LAYOUT_HEIGHT");
            }
        }
    }

    private void a(MotionScene.Transition transition) {
        Log.v("MotionLayout", "CHECK: transition = " + transition.debugString(getContext()));
        Log.v("MotionLayout", "CHECK: transition.setDuration = " + transition.getDuration());
        if (transition.getStartConstraintSetId() == transition.getEndConstraintSetId()) {
            Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
        }
    }

    public void setDebugMode(int i) {
        this.k = i;
        invalidate();
    }

    public void getDebugMode(boolean z) {
        this.k = z ? 2 : 1;
        invalidate();
    }

    private boolean a(float f2, float f3, View view, MotionEvent motionEvent) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (a(view.getLeft() + f2, view.getTop() + f3, viewGroup.getChildAt(i), motionEvent)) {
                    return true;
                }
            }
        }
        this.an.set(view.getLeft() + f2, view.getTop() + f3, f2 + view.getRight(), f3 + view.getBottom());
        if (motionEvent.getAction() == 0) {
            if (this.an.contains(motionEvent.getX(), motionEvent.getY()) && view.onTouchEvent(motionEvent)) {
                return true;
            }
        } else if (view.onTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MotionScene.Transition transition;
        d touchResponse;
        int e2;
        RectF a2;
        MotionScene motionScene = this.a;
        if (motionScene != null && this.L && (transition = motionScene.b) != null && transition.isEnabled() && (touchResponse = transition.getTouchResponse()) != null && ((motionEvent.getAction() != 0 || (a2 = touchResponse.a(this, new RectF())) == null || a2.contains(motionEvent.getX(), motionEvent.getY())) && (e2 = touchResponse.e()) != -1)) {
            View view = this.ao;
            if (view == null || view.getId() != e2) {
                this.ao = findViewById(e2);
            }
            View view2 = this.ao;
            if (view2 != null) {
                this.an.set(view2.getLeft(), this.ao.getTop(), this.ao.getRight(), this.ao.getBottom());
                if (this.an.contains(motionEvent.getX(), motionEvent.getY()) && !a(0.0f, 0.0f, this.ao, motionEvent)) {
                    return onTouchEvent(motionEvent);
                }
            }
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionScene motionScene = this.a;
        if (motionScene == null || !this.L || !motionScene.a()) {
            return super.onTouchEvent(motionEvent);
        }
        MotionScene.Transition transition = this.a.b;
        if (transition != null && !transition.isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        this.a.a(motionEvent, getCurrentState(), this);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        int i;
        super.onAttachedToWindow();
        MotionScene motionScene = this.a;
        if (!(motionScene == null || (i = this.d) == -1)) {
            ConstraintSet a2 = motionScene.a(i);
            this.a.a(this);
            if (a2 != null) {
                a2.applyTo(this);
            }
            this.H = this.d;
        }
        a();
        e eVar = this.al;
        if (eVar != null) {
            eVar.a();
            return;
        }
        MotionScene motionScene2 = this.a;
        if (motionScene2 != null && motionScene2.b != null && this.a.b.getAutoTransition() == 4) {
            transitionToEnd();
            setState(f.SETUP);
            setState(f.MOVING);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            motionScene.setRtl(isRtl());
        }
    }

    public void a() {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            if (motionScene.a(this, this.d)) {
                requestLayout();
                return;
            }
            int i = this.d;
            if (i != -1) {
                this.a.addOnClickListeners(this, i);
            }
            if (this.a.a()) {
                this.a.f();
            }
        }
    }

    public int getCurrentState() {
        return this.d;
    }

    public float getProgress() {
        return this.g;
    }

    public void a(int i, float f2, float f3, float f4, float[] fArr) {
        String str;
        HashMap<View, MotionController> hashMap = this.e;
        View viewById = getViewById(i);
        MotionController motionController = hashMap.get(viewById);
        if (motionController != null) {
            motionController.a(f2, f3, f4, fArr);
            float y = viewById.getY();
            float f5 = this.S;
            int i2 = ((f2 - this.R) > 0.0f ? 1 : ((f2 - this.R) == 0.0f ? 0 : -1));
            this.R = f2;
            this.S = y;
            return;
        }
        if (viewById == null) {
            str = "" + i;
        } else {
            str = viewById.getContext().getResources().getResourceName(i);
        }
        Log.w("MotionLayout", "WARNING could not find view id " + str);
    }

    public long getTransitionTimeMs() {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            this.N = motionScene.getDuration() / 1000.0f;
        }
        return this.N * 1000.0f;
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        this.Q = transitionListener;
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        if (this.ad == null) {
            this.ad = new ArrayList<>();
        }
        this.ad.add(transitionListener);
    }

    public boolean removeTransitionListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.ad;
        if (arrayList == null) {
            return false;
        }
        return arrayList.remove(transitionListener);
    }

    public void fireTrigger(int i, boolean z, float f2) {
        TransitionListener transitionListener = this.Q;
        if (transitionListener != null) {
            transitionListener.onTransitionTrigger(this, i, z, f2);
        }
        ArrayList<TransitionListener> arrayList = this.ad;
        if (arrayList != null) {
            Iterator<TransitionListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onTransitionTrigger(this, i, z, f2);
            }
        }
    }

    private void f() {
        ArrayList<TransitionListener> arrayList;
        if ((this.Q != null || ((arrayList = this.ad) != null && !arrayList.isEmpty())) && this.ai != this.f) {
            if (this.ah != -1) {
                TransitionListener transitionListener = this.Q;
                if (transitionListener != null) {
                    transitionListener.onTransitionStarted(this, this.H, this.I);
                }
                ArrayList<TransitionListener> arrayList2 = this.ad;
                if (arrayList2 != null) {
                    Iterator<TransitionListener> it = arrayList2.iterator();
                    while (it.hasNext()) {
                        it.next().onTransitionStarted(this, this.H, this.I);
                    }
                }
                this.w = true;
            }
            this.ah = -1;
            float f2 = this.f;
            this.ai = f2;
            TransitionListener transitionListener2 = this.Q;
            if (transitionListener2 != null) {
                transitionListener2.onTransitionChange(this, this.H, this.I, f2);
            }
            ArrayList<TransitionListener> arrayList3 = this.ad;
            if (arrayList3 != null) {
                Iterator<TransitionListener> it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    it2.next().onTransitionChange(this, this.H, this.I, this.f);
                }
            }
            this.w = true;
        }
    }

    protected void fireTransitionCompleted() {
        int i;
        ArrayList<TransitionListener> arrayList;
        if ((this.Q != null || ((arrayList = this.ad) != null && !arrayList.isEmpty())) && this.ah == -1) {
            this.ah = this.d;
            if (!this.G.isEmpty()) {
                ArrayList<Integer> arrayList2 = this.G;
                i = arrayList2.get(arrayList2.size() - 1).intValue();
            } else {
                i = -1;
            }
            int i2 = this.d;
            if (!(i == i2 || i2 == -1)) {
                this.G.add(Integer.valueOf(i2));
            }
        }
        g();
    }

    private void g() {
        ArrayList<TransitionListener> arrayList;
        if (this.Q != null || ((arrayList = this.ad) != null && !arrayList.isEmpty())) {
            this.w = false;
            Iterator<Integer> it = this.G.iterator();
            while (it.hasNext()) {
                Integer next = it.next();
                TransitionListener transitionListener = this.Q;
                if (transitionListener != null) {
                    transitionListener.onTransitionCompleted(this, next.intValue());
                }
                ArrayList<TransitionListener> arrayList2 = this.ad;
                if (arrayList2 != null) {
                    Iterator<TransitionListener> it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        it2.next().onTransitionCompleted(this, next.intValue());
                    }
                }
            }
            this.G.clear();
        }
    }

    public DesignTool getDesignTool() {
        if (this.W == null) {
            this.W = new DesignTool(this);
        }
        return this.W;
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof MotionHelper) {
            MotionHelper motionHelper = (MotionHelper) view;
            if (this.ad == null) {
                this.ad = new ArrayList<>();
            }
            this.ad.add(motionHelper);
            if (motionHelper.isUsedOnShow()) {
                if (this.ab == null) {
                    this.ab = new ArrayList<>();
                }
                this.ab.add(motionHelper);
            }
            if (motionHelper.isUseOnHide()) {
                if (this.ac == null) {
                    this.ac = new ArrayList<>();
                }
                this.ac.add(motionHelper);
            }
        }
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList<MotionHelper> arrayList = this.ab;
        if (arrayList != null) {
            arrayList.remove(view);
        }
        ArrayList<MotionHelper> arrayList2 = this.ac;
        if (arrayList2 != null) {
            arrayList2.remove(view);
        }
    }

    public void setOnShow(float f2) {
        ArrayList<MotionHelper> arrayList = this.ab;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.ab.get(i).setProgress(f2);
            }
        }
    }

    public void setOnHide(float f2) {
        ArrayList<MotionHelper> arrayList = this.ac;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.ac.get(i).setProgress(f2);
            }
        }
    }

    public int[] getConstraintSetIds() {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getConstraintSetIds();
    }

    public ConstraintSet getConstraintSet(int i) {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            return null;
        }
        return motionScene.a(i);
    }

    @Deprecated
    public void rebuildMotion() {
        Log.e("MotionLayout", "This method is deprecated. Please call rebuildScene() instead.");
        rebuildScene();
    }

    public void rebuildScene() {
        this.F.a();
        invalidate();
    }

    public void updateState(int i, ConstraintSet constraintSet) {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            motionScene.setConstraintSet(i, constraintSet);
        }
        updateState();
        if (this.d == i) {
            constraintSet.applyTo(this);
        }
    }

    public void updateState() {
        this.F.a(this.mLayoutWidget, this.a.a(this.H), this.a.a(this.I));
        rebuildScene();
    }

    public ArrayList<MotionScene.Transition> getDefinedTransitions() {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            return null;
        }
        return motionScene.getDefinedTransitions();
    }

    public int getStartState() {
        return this.H;
    }

    public int getEndState() {
        return this.I;
    }

    public float getTargetPosition() {
        return this.h;
    }

    public void setTransitionDuration(int i) {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            Log.e("MotionLayout", "MotionScene not defined");
        } else {
            motionScene.setDuration(i);
        }
    }

    public MotionScene.Transition getTransition(int i) {
        return this.a.getTransitionById(i);
    }

    public int a(String str) {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            return 0;
        }
        return motionScene.lookUpConstraintId(str);
    }

    public String a(int i) {
        MotionScene motionScene = this.a;
        if (motionScene == null) {
            return null;
        }
        return motionScene.lookUpConstraintName(i);
    }

    public void b(boolean z) {
        MotionScene motionScene = this.a;
        if (motionScene != null) {
            motionScene.disableAutoTransition(z);
        }
    }

    public void setInteractionEnabled(boolean z) {
        this.L = z;
    }

    public boolean isInteractionEnabled() {
        return this.L;
    }
}
