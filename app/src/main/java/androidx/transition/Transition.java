package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public abstract class Transition implements Cloneable {
    public static final int MATCH_ID = 3;
    public static final int MATCH_INSTANCE = 1;
    public static final int MATCH_ITEM_ID = 4;
    public static final int MATCH_NAME = 2;
    private EpicenterCallback H;
    private ArrayMap<String, String> I;
    TransitionPropagation h;
    private ArrayList<TransitionValues> y;
    private ArrayList<TransitionValues> z;
    private static final int[] a = {2, 1, 3, 4};
    private static final PathMotion i = new PathMotion() { // from class: androidx.transition.Transition.1
        @Override // androidx.transition.PathMotion
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, a>> A = new ThreadLocal<>();
    private String j = getClass().getName();
    private long k = -1;
    long b = -1;
    private TimeInterpolator l = null;
    ArrayList<Integer> c = new ArrayList<>();
    ArrayList<View> d = new ArrayList<>();
    private ArrayList<String> m = null;
    private ArrayList<Class<?>> n = null;
    private ArrayList<Integer> o = null;
    private ArrayList<View> p = null;
    private ArrayList<Class<?>> q = null;
    private ArrayList<String> r = null;
    private ArrayList<Integer> s = null;
    private ArrayList<View> t = null;
    private ArrayList<Class<?>> u = null;
    private q v = new q();
    private q w = new q();
    TransitionSet e = null;
    private int[] x = a;
    private ViewGroup B = null;
    boolean f = false;
    ArrayList<Animator> g = new ArrayList<>();
    private int C = 0;
    private boolean D = false;
    private boolean E = false;
    private ArrayList<TransitionListener> F = null;
    private ArrayList<Animator> G = new ArrayList<>();
    private PathMotion J = i;

    /* loaded from: classes.dex */
    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition transition);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface MatchOrder {
    }

    /* loaded from: classes.dex */
    public interface TransitionListener {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    private static boolean a(int i2) {
        return i2 >= 1 && i2 <= 4;
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        return null;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    public Transition() {
    }

    @SuppressLint({"RestrictedApi"})
    public Transition(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.c);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long namedInt2 = TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "startDelay", 2, -1);
        if (namedInt2 > 0) {
            setStartDelay(namedInt2);
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlResourceParser, "interpolator", 0, 0);
        if (namedResourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, namedResourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            setMatchOrder(b(namedString));
        }
        obtainStyledAttributes.recycle();
    }

    private static int[] b(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, Constants.ACCEPT_TIME_SEPARATOR_SP);
        int[] iArr = new int[stringTokenizer.countTokens()];
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String trim = stringTokenizer.nextToken().trim();
            if ("id".equalsIgnoreCase(trim)) {
                iArr[i2] = 3;
            } else if ("instance".equalsIgnoreCase(trim)) {
                iArr[i2] = 1;
            } else if ("name".equalsIgnoreCase(trim)) {
                iArr[i2] = 2;
            } else if ("itemId".equalsIgnoreCase(trim)) {
                iArr[i2] = 4;
            } else if (trim.isEmpty()) {
                int[] iArr2 = new int[iArr.length - 1];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                i2--;
                iArr = iArr2;
            } else {
                throw new InflateException("Unknown match type in matchOrder: '" + trim + LrcRow.SINGLE_QUOTE);
            }
            i2++;
        }
        return iArr;
    }

    @NonNull
    public Transition setDuration(long j) {
        this.b = j;
        return this;
    }

    public long getDuration() {
        return this.b;
    }

    @NonNull
    public Transition setStartDelay(long j) {
        this.k = j;
        return this;
    }

    public long getStartDelay() {
        return this.k;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.l = timeInterpolator;
        return this;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.l;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.x = a;
            return;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (!a(iArr[i2])) {
                throw new IllegalArgumentException("matches contains invalid value");
            } else if (a(iArr, i2)) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.x = (int[]) iArr.clone();
    }

    private static boolean a(int[] iArr, int i2) {
        int i3 = iArr[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            if (iArr[i4] == i3) {
                return true;
            }
        }
        return false;
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        TransitionValues remove;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View keyAt = arrayMap.keyAt(size);
            if (keyAt != null && b(keyAt) && (remove = arrayMap2.remove(keyAt)) != null && b(remove.view)) {
                this.y.add(arrayMap.removeAt(size));
                this.z.add(remove);
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        View view;
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View valueAt = longSparseArray.valueAt(i2);
            if (valueAt != null && b(valueAt) && (view = longSparseArray2.get(longSparseArray.keyAt(i2))) != null && b(view)) {
                TransitionValues transitionValues = arrayMap.get(valueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (!(transitionValues == null || transitionValues2 == null)) {
                    this.y.add(transitionValues);
                    this.z.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        View view;
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View valueAt = sparseArray.valueAt(i2);
            if (valueAt != null && b(valueAt) && (view = sparseArray2.get(sparseArray.keyAt(i2))) != null && b(view)) {
                TransitionValues transitionValues = arrayMap.get(valueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (!(transitionValues == null || transitionValues2 == null)) {
                    this.y.add(transitionValues);
                    this.z.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        View view;
        int size = arrayMap3.size();
        for (int i2 = 0; i2 < size; i2++) {
            View valueAt = arrayMap3.valueAt(i2);
            if (valueAt != null && b(valueAt) && (view = arrayMap4.get(arrayMap3.keyAt(i2))) != null && b(view)) {
                TransitionValues transitionValues = arrayMap.get(valueAt);
                TransitionValues transitionValues2 = arrayMap2.get(view);
                if (!(transitionValues == null || transitionValues2 == null)) {
                    this.y.add(transitionValues);
                    this.z.add(transitionValues2);
                    arrayMap.remove(valueAt);
                    arrayMap2.remove(view);
                }
            }
        }
    }

    private void b(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            TransitionValues valueAt = arrayMap.valueAt(i2);
            if (b(valueAt.view)) {
                this.y.add(valueAt);
                this.z.add(null);
            }
        }
        for (int i3 = 0; i3 < arrayMap2.size(); i3++) {
            TransitionValues valueAt2 = arrayMap2.valueAt(i3);
            if (b(valueAt2.view)) {
                this.z.add(valueAt2);
                this.y.add(null);
            }
        }
    }

    private void a(q qVar, q qVar2) {
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<>(qVar.a);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<>(qVar2.a);
        int i2 = 0;
        while (true) {
            int[] iArr = this.x;
            if (i2 < iArr.length) {
                switch (iArr[i2]) {
                    case 1:
                        a(arrayMap, arrayMap2);
                        break;
                    case 2:
                        a(arrayMap, arrayMap2, qVar.d, qVar2.d);
                        break;
                    case 3:
                        a(arrayMap, arrayMap2, qVar.b, qVar2.b);
                        break;
                    case 4:
                        a(arrayMap, arrayMap2, qVar.c, qVar2.c);
                        break;
                }
                i2++;
            } else {
                b(arrayMap, arrayMap2);
                return;
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void createAnimators(ViewGroup viewGroup, q qVar, q qVar2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        int i2;
        View view;
        Animator animator;
        TransitionValues transitionValues;
        ArrayMap<Animator, a> a2 = a();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long j = Long.MAX_VALUE;
        int i3 = 0;
        while (i3 < size) {
            TransitionValues transitionValues2 = arrayList.get(i3);
            TransitionValues transitionValues3 = arrayList2.get(i3);
            if (transitionValues2 != null && !transitionValues2.a.contains(this)) {
                transitionValues2 = null;
            }
            if (transitionValues3 != null && !transitionValues3.a.contains(this)) {
                transitionValues3 = null;
            }
            if (transitionValues2 == null && transitionValues3 == null) {
                size = size;
                i2 = i3;
            } else if (transitionValues2 == null || transitionValues3 == null || isTransitionRequired(transitionValues2, transitionValues3)) {
                Animator createAnimator = createAnimator(viewGroup, transitionValues2, transitionValues3);
                if (createAnimator != null) {
                    if (transitionValues3 != null) {
                        view = transitionValues3.view;
                        String[] transitionProperties = getTransitionProperties();
                        if (transitionProperties != null && transitionProperties.length > 0) {
                            transitionValues = new TransitionValues(view);
                            size = size;
                            TransitionValues transitionValues4 = qVar2.a.get(view);
                            if (transitionValues4 != null) {
                                int i4 = 0;
                                while (i4 < transitionProperties.length) {
                                    transitionValues.values.put(transitionProperties[i4], transitionValues4.values.get(transitionProperties[i4]));
                                    i4++;
                                    i3 = i3;
                                    transitionValues4 = transitionValues4;
                                }
                                i2 = i3;
                            } else {
                                i2 = i3;
                            }
                            int size2 = a2.size();
                            int i5 = 0;
                            while (true) {
                                if (i5 >= size2) {
                                    animator = createAnimator;
                                    break;
                                }
                                a aVar = a2.get(a2.keyAt(i5));
                                if (aVar.c != null && aVar.a == view && aVar.b.equals(getName()) && aVar.c.equals(transitionValues)) {
                                    animator = null;
                                    break;
                                }
                                i5++;
                            }
                        } else {
                            size = size;
                            i2 = i3;
                            animator = createAnimator;
                            transitionValues = null;
                        }
                    } else {
                        size = size;
                        i2 = i3;
                        view = transitionValues2.view;
                        animator = createAnimator;
                        transitionValues = null;
                    }
                    if (animator != null) {
                        TransitionPropagation transitionPropagation = this.h;
                        if (transitionPropagation != null) {
                            long startDelay = transitionPropagation.getStartDelay(viewGroup, this, transitionValues2, transitionValues3);
                            sparseIntArray.put(this.G.size(), (int) startDelay);
                            j = Math.min(startDelay, j);
                        } else {
                            j = j;
                        }
                        a2.put(animator, new a(view, getName(), this, ab.b(viewGroup), transitionValues));
                        this.G.add(animator);
                    }
                } else {
                    size = size;
                    i2 = i3;
                }
            } else {
                size = size;
                i2 = i3;
            }
            i3 = i2 + 1;
        }
        if (sparseIntArray.size() != 0) {
            for (int i6 = 0; i6 < sparseIntArray.size(); i6++) {
                Animator animator2 = this.G.get(sparseIntArray.keyAt(i6));
                animator2.setStartDelay((sparseIntArray.valueAt(i6) - j) + animator2.getStartDelay());
            }
        }
    }

    public boolean b(View view) {
        ArrayList<Class<?>> arrayList;
        ArrayList<String> arrayList2;
        int id = view.getId();
        ArrayList<Integer> arrayList3 = this.o;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList<View> arrayList4 = this.p;
        if (arrayList4 != null && arrayList4.contains(view)) {
            return false;
        }
        ArrayList<Class<?>> arrayList5 = this.q;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.q.get(i2).isInstance(view)) {
                    return false;
                }
            }
        }
        if (!(this.r == null || ViewCompat.getTransitionName(view) == null || !this.r.contains(ViewCompat.getTransitionName(view)))) {
            return false;
        }
        if ((this.c.size() == 0 && this.d.size() == 0 && (((arrayList = this.n) == null || arrayList.isEmpty()) && ((arrayList2 = this.m) == null || arrayList2.isEmpty()))) || this.c.contains(Integer.valueOf(id)) || this.d.contains(view)) {
            return true;
        }
        ArrayList<String> arrayList6 = this.m;
        if (arrayList6 != null && arrayList6.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.n != null) {
            for (int i3 = 0; i3 < this.n.size(); i3++) {
                if (this.n.get(i3).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayMap<Animator, a> a() {
        ArrayMap<Animator, a> arrayMap = A.get();
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<Animator, a> arrayMap2 = new ArrayMap<>();
        A.set(arrayMap2);
        return arrayMap2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void runAnimators() {
        start();
        ArrayMap<Animator, a> a2 = a();
        Iterator<Animator> it = this.G.iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (a2.containsKey(next)) {
                start();
                a(next, a2);
            }
        }
        this.G.clear();
        end();
    }

    private void a(Animator animator, final ArrayMap<Animator, a> arrayMap) {
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    Transition.this.g.add(animator2);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    arrayMap.remove(animator2);
                    Transition.this.g.remove(animator2);
                }
            });
            animate(animator);
        }
    }

    @NonNull
    public Transition addTarget(@NonNull View view) {
        this.d.add(view);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.c.add(Integer.valueOf(i2));
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String str) {
        if (this.m == null) {
            this.m = new ArrayList<>();
        }
        this.m.add(str);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class<?> cls) {
        if (this.n == null) {
            this.n = new ArrayList<>();
        }
        this.n.add(cls);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view) {
        this.d.remove(view);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.c.remove(Integer.valueOf(i2));
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String str) {
        ArrayList<String> arrayList = this.m;
        if (arrayList != null) {
            arrayList.remove(str);
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class<?> cls) {
        ArrayList<Class<?>> arrayList = this.n;
        if (arrayList != null) {
            arrayList.remove(cls);
        }
        return this;
    }

    private static <T> ArrayList<T> a(ArrayList<T> arrayList, T t, boolean z) {
        if (t == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, t);
        }
        return b.b(arrayList, t);
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z) {
        this.p = a(this.p, view, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int i2, boolean z) {
        this.o = a(this.o, i2, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z) {
        this.r = a(this.r, str, z);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view, boolean z) {
        this.t = a(this.t, view, z);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int i2, boolean z) {
        this.s = a(this.s, i2, z);
        return this;
    }

    private ArrayList<Integer> a(ArrayList<Integer> arrayList, int i2, boolean z) {
        if (i2 <= 0) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, Integer.valueOf(i2));
        }
        return b.b(arrayList, Integer.valueOf(i2));
    }

    private ArrayList<View> a(ArrayList<View> arrayList, View view, boolean z) {
        if (view == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, view);
        }
        return b.b(arrayList, view);
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class<?> cls, boolean z) {
        this.q = a(this.q, cls, z);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class<?> cls, boolean z) {
        this.u = a(this.u, cls, z);
        return this;
    }

    private ArrayList<Class<?>> a(ArrayList<Class<?>> arrayList, Class<?> cls, boolean z) {
        if (cls == null) {
            return arrayList;
        }
        if (z) {
            return b.a(arrayList, cls);
        }
        return b.b(arrayList, cls);
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.c;
    }

    @NonNull
    public List<View> getTargets() {
        return this.d;
    }

    @Nullable
    public List<String> getTargetNames() {
        return this.m;
    }

    @Nullable
    public List<Class<?>> getTargetTypes() {
        return this.n;
    }

    public void a(ViewGroup viewGroup, boolean z) {
        ArrayMap<String, String> arrayMap;
        ArrayList<String> arrayList;
        ArrayList<Class<?>> arrayList2;
        a(z);
        if ((this.c.size() > 0 || this.d.size() > 0) && (((arrayList = this.m) == null || arrayList.isEmpty()) && ((arrayList2 = this.n) == null || arrayList2.isEmpty()))) {
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                View findViewById = viewGroup.findViewById(this.c.get(i2).intValue());
                if (findViewById != null) {
                    TransitionValues transitionValues = new TransitionValues(findViewById);
                    if (z) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.a.add(this);
                    a(transitionValues);
                    if (z) {
                        a(this.v, findViewById, transitionValues);
                    } else {
                        a(this.w, findViewById, transitionValues);
                    }
                }
            }
            for (int i3 = 0; i3 < this.d.size(); i3++) {
                View view = this.d.get(i3);
                TransitionValues transitionValues2 = new TransitionValues(view);
                if (z) {
                    captureStartValues(transitionValues2);
                } else {
                    captureEndValues(transitionValues2);
                }
                transitionValues2.a.add(this);
                a(transitionValues2);
                if (z) {
                    a(this.v, view, transitionValues2);
                } else {
                    a(this.w, view, transitionValues2);
                }
            }
        } else {
            b(viewGroup, z);
        }
        if (!(z || (arrayMap = this.I) == null)) {
            int size = arrayMap.size();
            ArrayList arrayList3 = new ArrayList(size);
            for (int i4 = 0; i4 < size; i4++) {
                arrayList3.add(this.v.d.remove(this.I.keyAt(i4)));
            }
            for (int i5 = 0; i5 < size; i5++) {
                View view2 = (View) arrayList3.get(i5);
                if (view2 != null) {
                    this.v.d.put(this.I.valueAt(i5), view2);
                }
            }
        }
    }

    private static void a(q qVar, View view, TransitionValues transitionValues) {
        qVar.a.put(view, transitionValues);
        int id = view.getId();
        if (id >= 0) {
            if (qVar.b.indexOfKey(id) >= 0) {
                qVar.b.put(id, null);
            } else {
                qVar.b.put(id, view);
            }
        }
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            if (qVar.d.containsKey(transitionName)) {
                qVar.d.put(transitionName, null);
            } else {
                qVar.d.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (qVar.c.indexOfKey(itemIdAtPosition) >= 0) {
                    View view2 = qVar.c.get(itemIdAtPosition);
                    if (view2 != null) {
                        ViewCompat.setHasTransientState(view2, false);
                        qVar.c.put(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                ViewCompat.setHasTransientState(view, true);
                qVar.c.put(itemIdAtPosition, view);
            }
        }
    }

    public void a(boolean z) {
        if (z) {
            this.v.a.clear();
            this.v.b.clear();
            this.v.c.clear();
            return;
        }
        this.w.a.clear();
        this.w.b.clear();
        this.w.c.clear();
    }

    private void b(View view, boolean z) {
        if (view != null) {
            int id = view.getId();
            ArrayList<Integer> arrayList = this.o;
            if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
                ArrayList<View> arrayList2 = this.p;
                if (arrayList2 == null || !arrayList2.contains(view)) {
                    ArrayList<Class<?>> arrayList3 = this.q;
                    if (arrayList3 != null) {
                        int size = arrayList3.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            if (this.q.get(i2).isInstance(view)) {
                                return;
                            }
                        }
                    }
                    if (view.getParent() instanceof ViewGroup) {
                        TransitionValues transitionValues = new TransitionValues(view);
                        if (z) {
                            captureStartValues(transitionValues);
                        } else {
                            captureEndValues(transitionValues);
                        }
                        transitionValues.a.add(this);
                        a(transitionValues);
                        if (z) {
                            a(this.v, view, transitionValues);
                        } else {
                            a(this.w, view, transitionValues);
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ArrayList<Integer> arrayList4 = this.s;
                        if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                            ArrayList<View> arrayList5 = this.t;
                            if (arrayList5 == null || !arrayList5.contains(view)) {
                                ArrayList<Class<?>> arrayList6 = this.u;
                                if (arrayList6 != null) {
                                    int size2 = arrayList6.size();
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        if (this.u.get(i3).isInstance(view)) {
                                            return;
                                        }
                                    }
                                }
                                ViewGroup viewGroup = (ViewGroup) view;
                                for (int i4 = 0; i4 < viewGroup.getChildCount(); i4++) {
                                    b(viewGroup.getChildAt(i4), z);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view, boolean z) {
        TransitionSet transitionSet = this.e;
        if (transitionSet != null) {
            return transitionSet.getTransitionValues(view, z);
        }
        return (z ? this.v : this.w).a.get(view);
    }

    TransitionValues a(View view, boolean z) {
        TransitionSet transitionSet = this.e;
        if (transitionSet != null) {
            return transitionSet.a(view, z);
        }
        ArrayList<TransitionValues> arrayList = z ? this.y : this.z;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            TransitionValues transitionValues = arrayList.get(i3);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == view) {
                i2 = i3;
                break;
            }
            i3++;
        }
        if (i2 < 0) {
            return null;
        }
        return (z ? this.z : this.y).get(i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void pause(View view) {
        if (!this.E) {
            ArrayMap<Animator, a> a2 = a();
            int size = a2.size();
            ak b2 = ab.b(view);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                a valueAt = a2.valueAt(i2);
                if (valueAt.a != null && b2.equals(valueAt.d)) {
                    a.a(a2.keyAt(i2));
                }
            }
            ArrayList<TransitionListener> arrayList = this.F;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.F.clone();
                int size2 = arrayList2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((TransitionListener) arrayList2.get(i3)).onTransitionPause(this);
                }
            }
            this.D = true;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void resume(View view) {
        if (this.D) {
            if (!this.E) {
                ArrayMap<Animator, a> a2 = a();
                int size = a2.size();
                ak b2 = ab.b(view);
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    a valueAt = a2.valueAt(i2);
                    if (valueAt.a != null && b2.equals(valueAt.d)) {
                        a.b(a2.keyAt(i2));
                    }
                }
                ArrayList<TransitionListener> arrayList = this.F;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.F.clone();
                    int size2 = arrayList2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ((TransitionListener) arrayList2.get(i3)).onTransitionResume(this);
                    }
                }
            }
            this.D = false;
        }
    }

    public void a(ViewGroup viewGroup) {
        a aVar;
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
        a(this.v, this.w);
        ArrayMap<Animator, a> a2 = a();
        int size = a2.size();
        ak b2 = ab.b(viewGroup);
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator keyAt = a2.keyAt(i2);
            if (!(keyAt == null || (aVar = a2.get(keyAt)) == null || aVar.a == null || !b2.equals(aVar.d))) {
                TransitionValues transitionValues = aVar.c;
                View view = aVar.a;
                TransitionValues transitionValues2 = getTransitionValues(view, true);
                TransitionValues a3 = a(view, true);
                if (transitionValues2 == null && a3 == null) {
                    a3 = this.w.a.get(view);
                }
                if (!(transitionValues2 == null && a3 == null) && aVar.e.isTransitionRequired(transitionValues, a3)) {
                    if (keyAt.isRunning() || keyAt.isStarted()) {
                        keyAt.cancel();
                    } else {
                        a2.remove(keyAt);
                    }
                }
            }
        }
        createAnimators(viewGroup, this.v, this.w, this.y, this.z);
        runAnimators();
    }

    public boolean isTransitionRequired(@Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties != null) {
            for (String str : transitionProperties) {
                if (a(transitionValues, transitionValues2, str)) {
                    return true;
                }
            }
            return false;
        }
        for (String str2 : transitionValues.values.keySet()) {
            if (a(transitionValues, transitionValues2, str2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void animate(Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay() + animator.getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.Transition.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                Transition.this.end();
                animator2.removeListener(this);
            }
        });
        animator.start();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void start() {
        if (this.C == 0) {
            ArrayList<TransitionListener> arrayList = this.F;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.F.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList2.get(i2)).onTransitionStart(this);
                }
            }
            this.E = false;
        }
        this.C++;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected void end() {
        this.C--;
        if (this.C == 0) {
            ArrayList<TransitionListener> arrayList = this.F;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.F.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList2.get(i2)).onTransitionEnd(this);
                }
            }
            for (int i3 = 0; i3 < this.v.c.size(); i3++) {
                View valueAt = this.v.c.valueAt(i3);
                if (valueAt != null) {
                    ViewCompat.setHasTransientState(valueAt, false);
                }
            }
            for (int i4 = 0; i4 < this.w.c.size(); i4++) {
                View valueAt2 = this.w.c.valueAt(i4);
                if (valueAt2 != null) {
                    ViewCompat.setHasTransientState(valueAt2, false);
                }
            }
            this.E = true;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void b(ViewGroup viewGroup) {
        ArrayMap<Animator, a> a2 = a();
        int size = a2.size();
        if (!(viewGroup == null || size == 0)) {
            ak b2 = ab.b(viewGroup);
            ArrayMap arrayMap = new ArrayMap(a2);
            a2.clear();
            for (int i2 = size - 1; i2 >= 0; i2--) {
                a aVar = (a) arrayMap.valueAt(i2);
                if (!(aVar.a == null || b2 == null || !b2.equals(aVar.d))) {
                    ((Animator) arrayMap.keyAt(i2)).end();
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void cancel() {
        for (int size = this.g.size() - 1; size >= 0; size--) {
            this.g.get(size).cancel();
        }
        ArrayList<TransitionListener> arrayList = this.F;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.F.clone();
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((TransitionListener) arrayList2.get(i2)).onTransitionCancel(this);
            }
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        if (this.F == null) {
            this.F = new ArrayList<>();
        }
        this.F.add(transitionListener);
        return this;
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.F;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(transitionListener);
        if (this.F.size() == 0) {
            this.F = null;
        }
        return this;
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.J = i;
        } else {
            this.J = pathMotion;
        }
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.J;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.H = epicenterCallback;
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.H;
    }

    @Nullable
    public Rect getEpicenter() {
        EpicenterCallback epicenterCallback = this.H;
        if (epicenterCallback == null) {
            return null;
        }
        return epicenterCallback.onGetEpicenter(this);
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.h = transitionPropagation;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.h;
    }

    public void a(TransitionValues transitionValues) {
        String[] propagationProperties;
        if (this.h != null && !transitionValues.values.isEmpty() && (propagationProperties = this.h.getPropagationProperties()) != null) {
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= propagationProperties.length) {
                    z = true;
                    break;
                } else if (!transitionValues.values.containsKey(propagationProperties[i2])) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                this.h.captureValues(transitionValues);
            }
        }
    }

    public Transition c(ViewGroup viewGroup) {
        this.B = viewGroup;
        return this;
    }

    public void b(boolean z) {
        this.f = z;
    }

    public String toString() {
        return a("");
    }

    public Transition clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.G = new ArrayList<>();
            transition.v = new q();
            transition.w = new q();
            transition.y = null;
            transition.z = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @NonNull
    public String getName() {
        return this.j;
    }

    public String a(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.b != -1) {
            str2 = str2 + "dur(" + this.b + ") ";
        }
        if (this.k != -1) {
            str2 = str2 + "dly(" + this.k + ") ";
        }
        if (this.l != null) {
            str2 = str2 + "interp(" + this.l + ") ";
        }
        if (this.c.size() <= 0 && this.d.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (this.c.size() > 0) {
            String str4 = str3;
            for (int i2 = 0; i2 < this.c.size(); i2++) {
                if (i2 > 0) {
                    str4 = str4 + ", ";
                }
                str4 = str4 + this.c.get(i2);
            }
            str3 = str4;
        }
        if (this.d.size() > 0) {
            for (int i3 = 0; i3 < this.d.size(); i3++) {
                if (i3 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.d.get(i3);
            }
        }
        return str3 + ")";
    }

    /* loaded from: classes.dex */
    public static class a {
        View a;
        String b;
        TransitionValues c;
        ak d;
        Transition e;

        a(View view, String str, Transition transition, ak akVar, TransitionValues transitionValues) {
            this.a = view;
            this.b = str;
            this.c = transitionValues;
            this.d = akVar;
            this.e = transition;
        }
    }

    /* loaded from: classes.dex */
    public static class b {
        static <T> ArrayList<T> a(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (!arrayList.contains(t)) {
                arrayList.add(t);
            }
            return arrayList;
        }

        static <T> ArrayList<T> b(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                return arrayList;
            }
            arrayList.remove(t);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        }
    }
}
