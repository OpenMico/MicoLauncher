package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class MotionScene {
    public static final int LAYOUT_HONOR_REQUEST = 1;
    public static final int LAYOUT_IGNORE_REQUEST = 0;
    public static final String TAG = "MotionScene";
    public static final int UNSET = -1;
    float c;
    float d;
    private final MotionLayout e;
    private MotionEvent p;
    private MotionLayout.MotionTracker s;
    private boolean t;
    StateSet a = null;
    Transition b = null;
    private boolean f = false;
    private ArrayList<Transition> g = new ArrayList<>();
    private Transition h = null;
    private ArrayList<Transition> i = new ArrayList<>();
    private SparseArray<ConstraintSet> j = new SparseArray<>();
    private HashMap<String, Integer> k = new HashMap<>();
    private SparseIntArray l = new SparseIntArray();
    private boolean m = false;
    private int n = 400;
    private int o = 0;
    private boolean q = false;
    private boolean r = false;

    public float getPathPercent(View view, int i) {
        return 0.0f;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    public void a(int i, int i2) {
        int i3;
        int i4;
        StateSet stateSet = this.a;
        if (stateSet != null) {
            i4 = stateSet.stateGetConstraintID(i, -1, -1);
            if (i4 == -1) {
                i4 = i;
            }
            i3 = this.a.stateGetConstraintID(i2, -1, -1);
            if (i3 == -1) {
                i3 = i2;
            }
        } else {
            i4 = i;
            i3 = i2;
        }
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if ((next.c == i3 && next.d == i4) || (next.c == i2 && next.d == i)) {
                this.b = next;
                Transition transition = this.b;
                if (transition != null && transition.l != null) {
                    this.b.l.a(this.t);
                    return;
                }
                return;
            }
        }
        Transition transition2 = this.h;
        Iterator<Transition> it2 = this.i.iterator();
        while (it2.hasNext()) {
            Transition next2 = it2.next();
            if (next2.c == i2) {
                transition2 = next2;
            }
        }
        Transition transition3 = new Transition(this, transition2);
        transition3.d = i4;
        transition3.c = i3;
        if (i4 != -1) {
            this.g.add(transition3);
        }
        this.b = transition3;
    }

    public void addTransition(Transition transition) {
        int a = a(transition);
        if (a == -1) {
            this.g.add(transition);
        } else {
            this.g.set(a, transition);
        }
    }

    public void removeTransition(Transition transition) {
        int a = a(transition);
        if (a != -1) {
            this.g.remove(a);
        }
    }

    private int a(Transition transition) {
        int i = transition.a;
        if (i != -1) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                if (this.g.get(i2).a == i) {
                    return i2;
                }
            }
            return -1;
        }
        throw new IllegalArgumentException("The transition must have an id");
    }

    public boolean validateLayout(MotionLayout motionLayout) {
        return motionLayout == this.e && motionLayout.a == this;
    }

    public void setTransition(Transition transition) {
        this.b = transition;
        Transition transition2 = this.b;
        if (transition2 != null && transition2.l != null) {
            this.b.l.a(this.t);
        }
    }

    private int b(int i) {
        int stateGetConstraintID;
        StateSet stateSet = this.a;
        return (stateSet == null || (stateGetConstraintID = stateSet.stateGetConstraintID(i, -1, -1)) == -1) ? i : stateGetConstraintID;
    }

    public List<Transition> getTransitionsWithState(int i) {
        int b = b(i);
        ArrayList arrayList = new ArrayList();
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.d == b || next.c == b) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public void addOnClickListeners(MotionLayout motionLayout, int i) {
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.m.size() > 0) {
                Iterator it2 = next.m.iterator();
                while (it2.hasNext()) {
                    ((Transition.a) it2.next()).a(motionLayout);
                }
            }
        }
        Iterator<Transition> it3 = this.i.iterator();
        while (it3.hasNext()) {
            Transition next2 = it3.next();
            if (next2.m.size() > 0) {
                Iterator it4 = next2.m.iterator();
                while (it4.hasNext()) {
                    ((Transition.a) it4.next()).a(motionLayout);
                }
            }
        }
        Iterator<Transition> it5 = this.g.iterator();
        while (it5.hasNext()) {
            Transition next3 = it5.next();
            if (next3.m.size() > 0) {
                Iterator it6 = next3.m.iterator();
                while (it6.hasNext()) {
                    ((Transition.a) it6.next()).a(motionLayout, i, next3);
                }
            }
        }
        Iterator<Transition> it7 = this.i.iterator();
        while (it7.hasNext()) {
            Transition next4 = it7.next();
            if (next4.m.size() > 0) {
                Iterator it8 = next4.m.iterator();
                while (it8.hasNext()) {
                    ((Transition.a) it8.next()).a(motionLayout, i, next4);
                }
            }
        }
    }

    public Transition bestTransitionFor(int i, float f, float f2, MotionEvent motionEvent) {
        if (i == -1) {
            return this.b;
        }
        List<Transition> transitionsWithState = getTransitionsWithState(i);
        float f3 = 0.0f;
        Transition transition = null;
        RectF rectF = new RectF();
        for (Transition transition2 : transitionsWithState) {
            if (!transition2.o && transition2.l != null) {
                transition2.l.a(this.t);
                RectF a = transition2.l.a(this.e, rectF);
                if (a == null || motionEvent == null || a.contains(motionEvent.getX(), motionEvent.getY())) {
                    RectF a2 = transition2.l.a(this.e, rectF);
                    if (a2 == null || motionEvent == null || a2.contains(motionEvent.getX(), motionEvent.getY())) {
                        float f4 = transition2.l.f(f, f2);
                        float f5 = transition2.c == i ? f4 * (-1.0f) : f4 * 1.1f;
                        if (f5 > f3) {
                            transition = transition2;
                            f3 = f5;
                        }
                    }
                }
            }
        }
        return transition;
    }

    public ArrayList<Transition> getDefinedTransitions() {
        return this.g;
    }

    public Transition getTransitionById(int i) {
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (next.a == i) {
                return next;
            }
        }
        return null;
    }

    public int[] getConstraintSetIds() {
        int[] iArr = new int[this.j.size()];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = this.j.keyAt(i);
        }
        return iArr;
    }

    public boolean a(MotionLayout motionLayout, int i) {
        if (h() || this.f) {
            return false;
        }
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            if (!(next.n == 0 || this.b == next)) {
                if (i == next.d && (next.n == 4 || next.n == 2)) {
                    motionLayout.setState(MotionLayout.f.FINISHED);
                    motionLayout.setTransition(next);
                    if (next.n == 4) {
                        motionLayout.transitionToEnd();
                        motionLayout.setState(MotionLayout.f.SETUP);
                        motionLayout.setState(MotionLayout.f.MOVING);
                    } else {
                        motionLayout.setProgress(1.0f);
                        motionLayout.a(true);
                        motionLayout.setState(MotionLayout.f.SETUP);
                        motionLayout.setState(MotionLayout.f.MOVING);
                        motionLayout.setState(MotionLayout.f.FINISHED);
                        motionLayout.a();
                    }
                    return true;
                } else if (i == next.c && (next.n == 3 || next.n == 1)) {
                    motionLayout.setState(MotionLayout.f.FINISHED);
                    motionLayout.setTransition(next);
                    if (next.n == 3) {
                        motionLayout.transitionToStart();
                        motionLayout.setState(MotionLayout.f.SETUP);
                        motionLayout.setState(MotionLayout.f.MOVING);
                    } else {
                        motionLayout.setProgress(0.0f);
                        motionLayout.a(true);
                        motionLayout.setState(MotionLayout.f.SETUP);
                        motionLayout.setState(MotionLayout.f.MOVING);
                        motionLayout.setState(MotionLayout.f.FINISHED);
                        motionLayout.a();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean h() {
        return this.s != null;
    }

    public void setRtl(boolean z) {
        this.t = z;
        Transition transition = this.b;
        if (transition != null && transition.l != null) {
            this.b.l.a(this.t);
        }
    }

    /* loaded from: classes.dex */
    public static class Transition {
        public static final int AUTO_ANIMATE_TO_END = 4;
        public static final int AUTO_ANIMATE_TO_START = 3;
        public static final int AUTO_JUMP_TO_END = 2;
        public static final int AUTO_JUMP_TO_START = 1;
        public static final int AUTO_NONE = 0;
        private int a;
        private boolean b;
        private int c;
        private int d;
        private int e;
        private String f;
        private int g;
        private int h;
        private float i;
        private final MotionScene j;
        private ArrayList<KeyFrames> k;
        private d l;
        private ArrayList<a> m;
        private int n;
        private boolean o;
        private int p;
        private int q;
        private int r;

        public int getLayoutDuringTransition() {
            return this.q;
        }

        public void addOnClick(Context context, XmlPullParser xmlPullParser) {
            this.m.add(new a(context, this, xmlPullParser));
        }

        public void setAutoTransition(int i) {
            this.n = i;
        }

        public int getAutoTransition() {
            return this.n;
        }

        public int getId() {
            return this.a;
        }

        public int getEndConstraintSetId() {
            return this.c;
        }

        public int getStartConstraintSetId() {
            return this.d;
        }

        public void setDuration(int i) {
            this.h = i;
        }

        public int getDuration() {
            return this.h;
        }

        public float getStagger() {
            return this.i;
        }

        public List<KeyFrames> getKeyFrameList() {
            return this.k;
        }

        public List<a> getOnClickList() {
            return this.m;
        }

        public d getTouchResponse() {
            return this.l;
        }

        public void setStagger(float f) {
            this.i = f;
        }

        public void setPathMotionArc(int i) {
            this.p = i;
        }

        public int getPathMotionArc() {
            return this.p;
        }

        public boolean isEnabled() {
            return !this.o;
        }

        public void setEnable(boolean z) {
            this.o = !z;
        }

        public String debugString(Context context) {
            String resourceEntryName = this.d == -1 ? "null" : context.getResources().getResourceEntryName(this.d);
            if (this.c == -1) {
                return resourceEntryName + " -> null";
            }
            return resourceEntryName + " -> " + context.getResources().getResourceEntryName(this.c);
        }

        public boolean isTransitionFlag(int i) {
            return (i & this.r) != 0;
        }

        /* loaded from: classes.dex */
        public static class a implements View.OnClickListener {
            int a;
            int b;
            private final Transition c;

            public a(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.a = -1;
                this.b = 17;
                this.c = transition;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.OnClick);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == R.styleable.OnClick_targetId) {
                        this.a = obtainStyledAttributes.getResourceId(index, this.a);
                    } else if (index == R.styleable.OnClick_clickAction) {
                        this.b = obtainStyledAttributes.getInt(index, this.b);
                    }
                }
                obtainStyledAttributes.recycle();
            }

            public void a(MotionLayout motionLayout, int i, Transition transition) {
                int i2 = this.a;
                View view = motionLayout;
                if (i2 != -1) {
                    view = motionLayout.findViewById(i2);
                }
                if (view == null) {
                    Log.e(MotionScene.TAG, "OnClick could not find id " + this.a);
                    return;
                }
                int i3 = transition.d;
                int i4 = transition.c;
                if (i3 == -1) {
                    view.setOnClickListener(this);
                    return;
                }
                boolean z = true;
                boolean z2 = ((this.b & 1) != 0 && i == i3) | ((this.b & 1) != 0 && i == i3) | ((this.b & 256) != 0 && i == i3) | ((this.b & 16) != 0 && i == i4);
                if ((this.b & 4096) == 0 || i != i4) {
                    z = false;
                }
                if (z2 || z) {
                    view.setOnClickListener(this);
                }
            }

            public void a(MotionLayout motionLayout) {
                int i = this.a;
                if (i != -1) {
                    View findViewById = motionLayout.findViewById(i);
                    if (findViewById == null) {
                        Log.e(MotionScene.TAG, " (*)  could not find id " + this.a);
                        return;
                    }
                    findViewById.setOnClickListener(null);
                }
            }

            boolean a(Transition transition, MotionLayout motionLayout) {
                Transition transition2 = this.c;
                if (transition2 == transition) {
                    return true;
                }
                int i = transition2.c;
                int i2 = this.c.d;
                return i2 == -1 ? motionLayout.d != i : motionLayout.d == i2 || motionLayout.d == i;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MotionLayout motionLayout = this.c.j.e;
                if (motionLayout.isInteractionEnabled()) {
                    if (this.c.d == -1) {
                        int currentState = motionLayout.getCurrentState();
                        if (currentState == -1) {
                            motionLayout.transitionToState(this.c.c);
                            return;
                        }
                        Transition transition = new Transition(this.c.j, this.c);
                        transition.d = currentState;
                        transition.c = this.c.c;
                        motionLayout.setTransition(transition);
                        motionLayout.transitionToEnd();
                        return;
                    }
                    Transition transition2 = this.c.j.b;
                    int i = this.b;
                    boolean z = ((i & 1) == 0 && (i & 256) == 0) ? false : true;
                    int i2 = this.b;
                    boolean z2 = ((i2 & 16) == 0 && (i2 & 4096) == 0) ? false : true;
                    if (z && z2) {
                        Transition transition3 = this.c.j.b;
                        Transition transition4 = this.c;
                        if (transition3 != transition4) {
                            motionLayout.setTransition(transition4);
                        }
                        if (motionLayout.getCurrentState() == motionLayout.getEndState() || motionLayout.getProgress() > 0.5f) {
                            z = false;
                        } else {
                            z2 = false;
                        }
                    }
                    if (!a(transition2, motionLayout)) {
                        return;
                    }
                    if (z && (this.b & 1) != 0) {
                        motionLayout.setTransition(this.c);
                        motionLayout.transitionToEnd();
                    } else if (z2 && (this.b & 16) != 0) {
                        motionLayout.setTransition(this.c);
                        motionLayout.transitionToStart();
                    } else if (z && (this.b & 256) != 0) {
                        motionLayout.setTransition(this.c);
                        motionLayout.setProgress(1.0f);
                    } else if (z2 && (this.b & 4096) != 0) {
                        motionLayout.setTransition(this.c);
                        motionLayout.setProgress(0.0f);
                    }
                }
            }
        }

        Transition(MotionScene motionScene, Transition transition) {
            this.a = -1;
            this.b = false;
            this.c = -1;
            this.d = -1;
            this.e = 0;
            this.f = null;
            this.g = -1;
            this.h = 400;
            this.i = 0.0f;
            this.k = new ArrayList<>();
            this.l = null;
            this.m = new ArrayList<>();
            this.n = 0;
            this.o = false;
            this.p = -1;
            this.q = 0;
            this.r = 0;
            this.j = motionScene;
            if (transition != null) {
                this.p = transition.p;
                this.e = transition.e;
                this.f = transition.f;
                this.g = transition.g;
                this.h = transition.h;
                this.k = transition.k;
                this.i = transition.i;
                this.q = transition.q;
            }
        }

        public Transition(int i, MotionScene motionScene, int i2, int i3) {
            this.a = -1;
            this.b = false;
            this.c = -1;
            this.d = -1;
            this.e = 0;
            this.f = null;
            this.g = -1;
            this.h = 400;
            this.i = 0.0f;
            this.k = new ArrayList<>();
            this.l = null;
            this.m = new ArrayList<>();
            this.n = 0;
            this.o = false;
            this.p = -1;
            this.q = 0;
            this.r = 0;
            this.a = i;
            this.j = motionScene;
            this.d = i2;
            this.c = i3;
            this.h = motionScene.n;
            this.q = motionScene.o;
        }

        Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.a = -1;
            this.b = false;
            this.c = -1;
            this.d = -1;
            this.e = 0;
            this.f = null;
            this.g = -1;
            this.h = 400;
            this.i = 0.0f;
            this.k = new ArrayList<>();
            this.l = null;
            this.m = new ArrayList<>();
            this.n = 0;
            this.o = false;
            this.p = -1;
            this.q = 0;
            this.r = 0;
            this.h = motionScene.n;
            this.q = motionScene.o;
            this.j = motionScene;
            a(motionScene, context, Xml.asAttributeSet(xmlPullParser));
        }

        private void a(MotionScene motionScene, Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Transition);
            a(motionScene, context, obtainStyledAttributes);
            obtainStyledAttributes.recycle();
        }

        private void a(MotionScene motionScene, Context context, TypedArray typedArray) {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (index == R.styleable.Transition_constraintSetEnd) {
                    this.c = typedArray.getResourceId(index, this.c);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.c))) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.c);
                        motionScene.j.append(this.c, constraintSet);
                    }
                } else if (index == R.styleable.Transition_constraintSetStart) {
                    this.d = typedArray.getResourceId(index, this.d);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.d))) {
                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.load(context, this.d);
                        motionScene.j.append(this.d, constraintSet2);
                    }
                } else if (index == R.styleable.Transition_motionInterpolator) {
                    TypedValue peekValue = typedArray.peekValue(index);
                    if (peekValue.type == 1) {
                        this.g = typedArray.getResourceId(index, -1);
                        if (this.g != -1) {
                            this.e = -2;
                        }
                    } else if (peekValue.type == 3) {
                        this.f = typedArray.getString(index);
                        if (this.f.indexOf("/") > 0) {
                            this.g = typedArray.getResourceId(index, -1);
                            this.e = -2;
                        } else {
                            this.e = -1;
                        }
                    } else {
                        this.e = typedArray.getInteger(index, this.e);
                    }
                } else if (index == R.styleable.Transition_duration) {
                    this.h = typedArray.getInt(index, this.h);
                } else if (index == R.styleable.Transition_staggered) {
                    this.i = typedArray.getFloat(index, this.i);
                } else if (index == R.styleable.Transition_autoTransition) {
                    this.n = typedArray.getInteger(index, this.n);
                } else if (index == R.styleable.Transition_android_id) {
                    this.a = typedArray.getResourceId(index, this.a);
                } else if (index == R.styleable.Transition_transitionDisable) {
                    this.o = typedArray.getBoolean(index, this.o);
                } else if (index == R.styleable.Transition_pathMotionArc) {
                    this.p = typedArray.getInteger(index, -1);
                } else if (index == R.styleable.Transition_layoutDuringTransition) {
                    this.q = typedArray.getInteger(index, 0);
                } else if (index == R.styleable.Transition_transitionFlags) {
                    this.r = typedArray.getInteger(index, 0);
                }
            }
            if (this.d == -1) {
                this.b = true;
            }
        }
    }

    public MotionScene(MotionLayout motionLayout) {
        this.e = motionLayout;
    }

    public MotionScene(Context context, MotionLayout motionLayout, int i) {
        this.e = motionLayout;
        a(context, i);
        this.j.put(R.id.motion_base, new ConstraintSet());
        this.k.put("motion_base", Integer.valueOf(R.id.motion_base));
    }

    private void a(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        Transition transition = null;
        try {
            int eventType = xml.getEventType();
            while (true) {
                char c = 1;
                if (eventType != 1) {
                    if (eventType != 0) {
                        switch (eventType) {
                            case 2:
                                String name = xml.getName();
                                if (this.m) {
                                    System.out.println("parsing = " + name);
                                }
                                switch (name.hashCode()) {
                                    case -1349929691:
                                        if (name.equals("ConstraintSet")) {
                                            c = 5;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1239391468:
                                        if (name.equals("KeyFrameSet")) {
                                            c = 6;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 269306229:
                                        if (name.equals("Transition")) {
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 312750793:
                                        if (name.equals("OnClick")) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 327855227:
                                        if (name.equals("OnSwipe")) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 793277014:
                                        if (name.equals(TAG)) {
                                            c = 0;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1382829617:
                                        if (name.equals("StateSet")) {
                                            c = 4;
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
                                        a(context, xml);
                                        continue;
                                    case 1:
                                        ArrayList<Transition> arrayList = this.g;
                                        Transition transition2 = new Transition(this, context, xml);
                                        arrayList.add(transition2);
                                        if (this.b == null && !transition2.b) {
                                            this.b = transition2;
                                            if (!(this.b == null || this.b.l == null)) {
                                                this.b.l.a(this.t);
                                            }
                                        }
                                        if (transition2.b) {
                                            if (transition2.c == -1) {
                                                this.h = transition2;
                                            } else {
                                                this.i.add(transition2);
                                            }
                                            this.g.remove(transition2);
                                        }
                                        transition = transition2;
                                        continue;
                                    case 2:
                                        if (transition == null) {
                                            Log.v(TAG, " OnSwipe (" + context.getResources().getResourceEntryName(i) + ".xml:" + xml.getLineNumber() + ")");
                                        }
                                        transition.l = new d(context, this.e, xml);
                                        continue;
                                    case 3:
                                        transition.addOnClick(context, xml);
                                        continue;
                                    case 4:
                                        this.a = new StateSet(context, xml);
                                        continue;
                                    case 5:
                                        b(context, xml);
                                        continue;
                                    case 6:
                                        transition.k.add(new KeyFrames(context, xml));
                                        continue;
                                        continue;
                                    default:
                                        Log.v(TAG, "WARNING UNKNOWN ATTRIBUTE " + name);
                                        continue;
                                }
                            case 3:
                                continue;
                            default:
                                continue;
                        }
                    } else {
                        xml.getName();
                    }
                    eventType = xml.next();
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    private void a(Context context, XmlPullParser xmlPullParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.MotionScene);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R.styleable.MotionScene_defaultDuration) {
                this.n = obtainStyledAttributes.getInt(index, this.n);
            } else if (index == R.styleable.MotionScene_layoutDuringTransition) {
                this.o = obtainStyledAttributes.getInteger(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    private int a(Context context, String str) {
        int i;
        if (str.contains("/")) {
            i = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
            if (this.m) {
                System.out.println("id getMap res = " + i);
            }
        } else {
            i = -1;
        }
        if (i != -1) {
            return i;
        }
        if (str != null && str.length() > 1) {
            return Integer.parseInt(str.substring(1));
        }
        Log.e(TAG, "error in parsing id");
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0054, code lost:
        if (r8.equals("deriveConstraintsFrom") != false) goto L_0x0058;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(android.content.Context r14, org.xmlpull.v1.XmlPullParser r15) {
        /*
            r13 = this;
            androidx.constraintlayout.widget.ConstraintSet r0 = new androidx.constraintlayout.widget.ConstraintSet
            r0.<init>()
            r1 = 0
            r0.setForceId(r1)
            int r2 = r15.getAttributeCount()
            r3 = -1
            r4 = r1
            r5 = r3
            r6 = r5
        L_0x0011:
            r7 = 1
            if (r4 >= r2) goto L_0x0075
            java.lang.String r8 = r15.getAttributeName(r4)
            java.lang.String r9 = r15.getAttributeValue(r4)
            boolean r10 = r13.m
            if (r10 == 0) goto L_0x0036
            java.io.PrintStream r10 = java.lang.System.out
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "id string = "
            r11.append(r12)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            r10.println(r11)
        L_0x0036:
            int r10 = r8.hashCode()
            r11 = -1496482599(0xffffffffa6cd7cd9, float:-1.4258573E-15)
            if (r10 == r11) goto L_0x004e
            r7 = 3355(0xd1b, float:4.701E-42)
            if (r10 == r7) goto L_0x0044
            goto L_0x0057
        L_0x0044:
            java.lang.String r7 = "id"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x0057
            r7 = r1
            goto L_0x0058
        L_0x004e:
            java.lang.String r10 = "deriveConstraintsFrom"
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r7 = r3
        L_0x0058:
            switch(r7) {
                case 0: goto L_0x0061;
                case 1: goto L_0x005c;
                default: goto L_0x005b;
            }
        L_0x005b:
            goto L_0x0072
        L_0x005c:
            int r6 = r13.a(r14, r9)
            goto L_0x0072
        L_0x0061:
            int r5 = r13.a(r14, r9)
            java.util.HashMap<java.lang.String, java.lang.Integer> r7 = r13.k
            java.lang.String r8 = stripID(r9)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            r7.put(r8, r9)
        L_0x0072:
            int r4 = r4 + 1
            goto L_0x0011
        L_0x0075:
            if (r5 == r3) goto L_0x008f
            androidx.constraintlayout.motion.widget.MotionLayout r1 = r13.e
            int r1 = r1.k
            if (r1 == 0) goto L_0x0080
            r0.setValidateOnParse(r7)
        L_0x0080:
            r0.load(r14, r15)
            if (r6 == r3) goto L_0x008a
            android.util.SparseIntArray r14 = r13.l
            r14.put(r5, r6)
        L_0x008a:
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintSet> r14 = r13.j
            r14.put(r5, r0)
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.b(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public ConstraintSet getConstraintSet(Context context, String str) {
        if (this.m) {
            PrintStream printStream = System.out;
            printStream.println("id " + str);
            PrintStream printStream2 = System.out;
            printStream2.println("size " + this.j.size());
        }
        for (int i = 0; i < this.j.size(); i++) {
            int keyAt = this.j.keyAt(i);
            String resourceName = context.getResources().getResourceName(keyAt);
            if (this.m) {
                PrintStream printStream3 = System.out;
                printStream3.println("Id for <" + i + "> is <" + resourceName + "> looking for <" + str + ">");
            }
            if (str.equals(resourceName)) {
                return this.j.get(keyAt);
            }
        }
        return null;
    }

    public ConstraintSet a(int i) {
        return a(i, -1, -1);
    }

    ConstraintSet a(int i, int i2, int i3) {
        int stateGetConstraintID;
        if (this.m) {
            PrintStream printStream = System.out;
            printStream.println("id " + i);
            PrintStream printStream2 = System.out;
            printStream2.println("size " + this.j.size());
        }
        StateSet stateSet = this.a;
        if (!(stateSet == null || (stateGetConstraintID = stateSet.stateGetConstraintID(i, i2, i3)) == -1)) {
            i = stateGetConstraintID;
        }
        if (this.j.get(i) != null) {
            return this.j.get(i);
        }
        Log.e(TAG, "Warning could not find ConstraintSet id/" + Debug.getName(this.e.getContext(), i) + " In MotionScene");
        SparseArray<ConstraintSet> sparseArray = this.j;
        return sparseArray.get(sparseArray.keyAt(0));
    }

    public void setConstraintSet(int i, ConstraintSet constraintSet) {
        this.j.put(i, constraintSet);
    }

    public void getKeyFrames(MotionController motionController) {
        Transition transition = this.b;
        if (transition == null) {
            Transition transition2 = this.h;
            if (transition2 != null) {
                Iterator it = transition2.k.iterator();
                while (it.hasNext()) {
                    ((KeyFrames) it.next()).addFrames(motionController);
                }
                return;
            }
            return;
        }
        Iterator it2 = transition.k.iterator();
        while (it2.hasNext()) {
            ((KeyFrames) it2.next()).addFrames(motionController);
        }
    }

    public Key a(Context context, int i, int i2, int i3) {
        Transition transition = this.b;
        if (transition == null) {
            return null;
        }
        Iterator it = transition.k.iterator();
        while (it.hasNext()) {
            KeyFrames keyFrames = (KeyFrames) it.next();
            for (Integer num : keyFrames.getKeys()) {
                if (i2 == num.intValue()) {
                    Iterator<Key> it2 = keyFrames.getKeyFramesForView(num.intValue()).iterator();
                    while (it2.hasNext()) {
                        Key next = it2.next();
                        if (next.a == i3 && next.mType == i) {
                            return next;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    public boolean a(View view, int i) {
        Transition transition = this.b;
        if (transition == null) {
            return false;
        }
        Iterator it = transition.k.iterator();
        while (it.hasNext()) {
            Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
            while (it2.hasNext()) {
                if (it2.next().a == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setKeyframe(View view, int i, String str, Object obj) {
        Transition transition = this.b;
        if (transition != null) {
            Iterator it = transition.k.iterator();
            while (it.hasNext()) {
                Iterator<Key> it2 = ((KeyFrames) it.next()).getKeyFramesForView(view.getId()).iterator();
                while (it2.hasNext()) {
                    if (it2.next().a == i) {
                        int i2 = ((obj != null ? ((Float) obj).floatValue() : 0.0f) > 0.0f ? 1 : ((obj != null ? ((Float) obj).floatValue() : 0.0f) == 0.0f ? 0 : -1));
                        str.equalsIgnoreCase("app:PerpendicularPath_percent");
                    }
                }
            }
        }
    }

    public boolean a() {
        Iterator<Transition> it = this.g.iterator();
        while (it.hasNext()) {
            if (it.next().l != null) {
                return true;
            }
        }
        Transition transition = this.b;
        return (transition == null || transition.l == null) ? false : true;
    }

    public void a(MotionEvent motionEvent, int i, MotionLayout motionLayout) {
        MotionLayout.MotionTracker motionTracker;
        MotionEvent motionEvent2;
        RectF rectF = new RectF();
        if (this.s == null) {
            this.s = this.e.obtainVelocityTracker();
        }
        this.s.addMovement(motionEvent);
        if (i != -1) {
            int action = motionEvent.getAction();
            boolean z = false;
            if (action == 0) {
                this.c = motionEvent.getRawX();
                this.d = motionEvent.getRawY();
                this.p = motionEvent;
                this.q = false;
                if (this.b.l != null) {
                    RectF b = this.b.l.b(this.e, rectF);
                    if (b == null || b.contains(this.p.getX(), this.p.getY())) {
                        RectF a = this.b.l.a(this.e, rectF);
                        if (a == null || a.contains(this.p.getX(), this.p.getY())) {
                            this.r = false;
                        } else {
                            this.r = true;
                        }
                        this.b.l.b(this.c, this.d);
                        return;
                    }
                    this.p = null;
                    this.q = true;
                    return;
                }
                return;
            } else if (action == 2 && !this.q) {
                float rawY = motionEvent.getRawY() - this.d;
                float rawX = motionEvent.getRawX() - this.c;
                if ((rawX != 0.0d || rawY != 0.0d) && (motionEvent2 = this.p) != null) {
                    Transition bestTransitionFor = bestTransitionFor(i, rawX, rawY, motionEvent2);
                    if (bestTransitionFor != null) {
                        motionLayout.setTransition(bestTransitionFor);
                        RectF a2 = this.b.l.a(this.e, rectF);
                        if (a2 != null && !a2.contains(this.p.getX(), this.p.getY())) {
                            z = true;
                        }
                        this.r = z;
                        this.b.l.a(this.c, this.d);
                    }
                } else {
                    return;
                }
            }
        }
        if (!this.q) {
            Transition transition = this.b;
            if (!(transition == null || transition.l == null || this.r)) {
                this.b.l.a(motionEvent, this.s, i, this);
            }
            this.c = motionEvent.getRawX();
            this.d = motionEvent.getRawY();
            if (motionEvent.getAction() == 1 && (motionTracker = this.s) != null) {
                motionTracker.recycle();
                this.s = null;
                if (motionLayout.d != -1) {
                    a(motionLayout, motionLayout.d);
                }
            }
        }
    }

    public void a(float f, float f2) {
        Transition transition = this.b;
        if (transition != null && transition.l != null) {
            this.b.l.e(f, f2);
        }
    }

    public void b(float f, float f2) {
        Transition transition = this.b;
        if (transition != null && transition.l != null) {
            this.b.l.d(f, f2);
        }
    }

    public float c(float f, float f2) {
        Transition transition = this.b;
        if (transition == null || transition.l == null) {
            return 0.0f;
        }
        return this.b.l.c(f, f2);
    }

    public int b() {
        Transition transition = this.b;
        if (transition == null) {
            return -1;
        }
        return transition.d;
    }

    public int c() {
        Transition transition = this.b;
        if (transition == null) {
            return -1;
        }
        return transition.c;
    }

    public Interpolator getInterpolator() {
        switch (this.b.e) {
            case -2:
                return AnimationUtils.loadInterpolator(this.e.getContext(), this.b.g);
            case -1:
                final Easing interpolator = Easing.getInterpolator(this.b.f);
                return new Interpolator() { // from class: androidx.constraintlayout.motion.widget.MotionScene.1
                    @Override // android.animation.TimeInterpolator
                    public float getInterpolation(float f) {
                        return (float) interpolator.get(f);
                    }
                };
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new DecelerateInterpolator();
            case 3:
                return null;
            case 4:
                return new AnticipateInterpolator();
            case 5:
                return new BounceInterpolator();
            default:
                return null;
        }
    }

    public int getDuration() {
        Transition transition = this.b;
        if (transition != null) {
            return transition.h;
        }
        return this.n;
    }

    public void setDuration(int i) {
        Transition transition = this.b;
        if (transition != null) {
            transition.setDuration(i);
        } else {
            this.n = i;
        }
    }

    public int gatPathMotionArc() {
        Transition transition = this.b;
        if (transition != null) {
            return transition.p;
        }
        return -1;
    }

    public float getStaggered() {
        Transition transition = this.b;
        if (transition != null) {
            return transition.i;
        }
        return 0.0f;
    }

    public float d() {
        Transition transition = this.b;
        if (transition == null || transition.l == null) {
            return 0.0f;
        }
        return this.b.l.b();
    }

    public float e() {
        Transition transition = this.b;
        if (transition == null || transition.l == null) {
            return 0.0f;
        }
        return this.b.l.c();
    }

    public void f() {
        Transition transition = this.b;
        if (transition != null && transition.l != null) {
            this.b.l.a();
        }
    }

    public boolean g() {
        Transition transition = this.b;
        if (transition == null || transition.l == null) {
            return false;
        }
        return this.b.l.d();
    }

    public void a(MotionLayout motionLayout) {
        for (int i = 0; i < this.j.size(); i++) {
            int keyAt = this.j.keyAt(i);
            if (c(keyAt)) {
                Log.e(TAG, "Cannot be derived from yourself");
                return;
            } else {
                d(keyAt);
            }
        }
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            this.j.valueAt(i2).readFallback(motionLayout);
        }
    }

    private boolean c(int i) {
        int i2 = this.l.get(i);
        int size = this.l.size();
        while (i2 > 0) {
            if (i2 == i) {
                return true;
            }
            size--;
            if (size < 0) {
                return true;
            }
            i2 = this.l.get(i2);
        }
        return false;
    }

    private void d(int i) {
        int i2 = this.l.get(i);
        if (i2 > 0) {
            d(this.l.get(i));
            ConstraintSet constraintSet = this.j.get(i);
            ConstraintSet constraintSet2 = this.j.get(i2);
            if (constraintSet2 == null) {
                Log.e(TAG, "ERROR! invalid deriveConstraintsFrom: @id/" + Debug.getName(this.e.getContext(), i2));
                return;
            }
            constraintSet.readFallback(constraintSet2);
            this.l.put(i, -1);
        }
    }

    public static String stripID(String str) {
        if (str == null) {
            return "";
        }
        int indexOf = str.indexOf(47);
        return indexOf < 0 ? str : str.substring(indexOf + 1);
    }

    public int lookUpConstraintId(String str) {
        return this.k.get(str).intValue();
    }

    public String lookUpConstraintName(int i) {
        for (Map.Entry<String, Integer> entry : this.k.entrySet()) {
            if (entry.getValue().intValue() == i) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void disableAutoTransition(boolean z) {
        this.f = z;
    }
}
