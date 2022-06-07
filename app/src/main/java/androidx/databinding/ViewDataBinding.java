package androidx.databinding;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.CallbackRegistry;
import androidx.databinding.Observable;
import androidx.databinding.ObservableList;
import androidx.databinding.ObservableMap;
import androidx.databinding.library.R;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ViewDataBinding extends BaseObservable {
    public static final String BINDING_TAG_PREFIX = "binding_";
    static int a = Build.VERSION.SDK_INT;
    private static final int b = 8;
    private static final boolean c;
    private static final a d;
    private static final a e;
    private static final a f;
    private static final a g;
    private static final CallbackRegistry.NotifierCallback<OnRebindCallback, ViewDataBinding, Void> h;
    private static final ReferenceQueue<ViewDataBinding> i;
    private static final View.OnAttachStateChangeListener j;
    private final Runnable k = new Runnable() { // from class: androidx.databinding.ViewDataBinding.7
        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                ViewDataBinding.this.l = false;
            }
            ViewDataBinding.f();
            if (Build.VERSION.SDK_INT < 19 || ViewDataBinding.this.o.isAttachedToWindow()) {
                ViewDataBinding.this.executePendingBindings();
                return;
            }
            ViewDataBinding.this.o.removeOnAttachStateChangeListener(ViewDataBinding.j);
            ViewDataBinding.this.o.addOnAttachStateChangeListener(ViewDataBinding.j);
        }
    };
    private boolean l = false;
    private boolean m = false;
    protected final DataBindingComponent mBindingComponent;
    private e[] n;
    private final View o;
    private CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> p;
    private boolean q;
    private Choreographer r;
    private final Choreographer.FrameCallback s;
    private Handler t;
    private ViewDataBinding u;
    private LifecycleOwner v;
    private OnStartListener w;
    private boolean x;

    /* loaded from: classes.dex */
    public interface a {
        e a(ViewDataBinding viewDataBinding, int i);
    }

    /* loaded from: classes.dex */
    public interface c<T> {
        void a(LifecycleOwner lifecycleOwner);

        void a(T t);

        void b(T t);
    }

    protected abstract void executeBindings();

    public abstract boolean hasPendingBindings();

    public abstract void invalidateAll();

    protected abstract boolean onFieldChange(int i2, Object obj, int i3);

    public abstract boolean setVariable(int i2, @Nullable Object obj);

    static {
        c = a >= 16;
        d = new a() { // from class: androidx.databinding.ViewDataBinding.1
            @Override // androidx.databinding.ViewDataBinding.a
            public e a(ViewDataBinding viewDataBinding, int i2) {
                return new g(viewDataBinding, i2).a();
            }
        };
        e = new a() { // from class: androidx.databinding.ViewDataBinding.2
            @Override // androidx.databinding.ViewDataBinding.a
            public e a(ViewDataBinding viewDataBinding, int i2) {
                return new d(viewDataBinding, i2).a();
            }
        };
        f = new a() { // from class: androidx.databinding.ViewDataBinding.3
            @Override // androidx.databinding.ViewDataBinding.a
            public e a(ViewDataBinding viewDataBinding, int i2) {
                return new f(viewDataBinding, i2).a();
            }
        };
        g = new a() { // from class: androidx.databinding.ViewDataBinding.4
            @Override // androidx.databinding.ViewDataBinding.a
            public e a(ViewDataBinding viewDataBinding, int i2) {
                return new b(viewDataBinding, i2).a();
            }
        };
        h = new CallbackRegistry.NotifierCallback<OnRebindCallback, ViewDataBinding, Void>() { // from class: androidx.databinding.ViewDataBinding.5
            /* renamed from: a */
            public void onNotifyCallback(OnRebindCallback onRebindCallback, ViewDataBinding viewDataBinding, int i2, Void r4) {
                switch (i2) {
                    case 1:
                        if (!onRebindCallback.onPreBind(viewDataBinding)) {
                            viewDataBinding.m = true;
                            return;
                        }
                        return;
                    case 2:
                        onRebindCallback.onCanceled(viewDataBinding);
                        return;
                    case 3:
                        onRebindCallback.onBound(viewDataBinding);
                        return;
                    default:
                        return;
                }
            }
        };
        i = new ReferenceQueue<>();
        if (Build.VERSION.SDK_INT < 19) {
            j = null;
        } else {
            j = new View.OnAttachStateChangeListener() { // from class: androidx.databinding.ViewDataBinding.6
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                @TargetApi(19)
                public void onViewAttachedToWindow(View view) {
                    ViewDataBinding.a(view).k.run();
                    view.removeOnAttachStateChangeListener(this);
                }
            };
        }
    }

    public ViewDataBinding(DataBindingComponent dataBindingComponent, View view, int i2) {
        this.mBindingComponent = dataBindingComponent;
        this.n = new e[i2];
        this.o = view;
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("DataBinding must be created in view's UI Thread");
        } else if (c) {
            this.r = Choreographer.getInstance();
            this.s = new Choreographer.FrameCallback() { // from class: androidx.databinding.ViewDataBinding.8
                @Override // android.view.Choreographer.FrameCallback
                public void doFrame(long j2) {
                    ViewDataBinding.this.k.run();
                }
            };
        } else {
            this.s = null;
            this.t = new Handler(Looper.myLooper());
        }
    }

    protected void setRootTag(View view) {
        view.setTag(R.id.dataBinding, this);
    }

    protected void setRootTag(View[] viewArr) {
        for (View view : viewArr) {
            view.setTag(R.id.dataBinding, this);
        }
    }

    public static int getBuildSdkInt() {
        return a;
    }

    @MainThread
    public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
        LifecycleOwner lifecycleOwner2 = this.v;
        if (lifecycleOwner2 != lifecycleOwner) {
            if (lifecycleOwner2 != null) {
                lifecycleOwner2.getLifecycle().removeObserver(this.w);
            }
            this.v = lifecycleOwner;
            if (lifecycleOwner != null) {
                if (this.w == null) {
                    this.w = new OnStartListener();
                }
                lifecycleOwner.getLifecycle().addObserver(this.w);
            }
            e[] eVarArr = this.n;
            for (e eVar : eVarArr) {
                if (eVar != null) {
                    eVar.a(lifecycleOwner);
                }
            }
        }
    }

    @Nullable
    public LifecycleOwner getLifecycleOwner() {
        return this.v;
    }

    public void addOnRebindCallback(@NonNull OnRebindCallback onRebindCallback) {
        if (this.p == null) {
            this.p = new CallbackRegistry<>(h);
        }
        this.p.add(onRebindCallback);
    }

    public void removeOnRebindCallback(@NonNull OnRebindCallback onRebindCallback) {
        CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry = this.p;
        if (callbackRegistry != null) {
            callbackRegistry.remove(onRebindCallback);
        }
    }

    public void executePendingBindings() {
        ViewDataBinding viewDataBinding = this.u;
        if (viewDataBinding == null) {
            e();
        } else {
            viewDataBinding.executePendingBindings();
        }
    }

    private void e() {
        if (this.q) {
            requestRebind();
        } else if (hasPendingBindings()) {
            this.q = true;
            this.m = false;
            CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry = this.p;
            if (callbackRegistry != null) {
                callbackRegistry.notifyCallbacks(this, 1, null);
                if (this.m) {
                    this.p.notifyCallbacks(this, 2, null);
                }
            }
            if (!this.m) {
                executeBindings();
                CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry2 = this.p;
                if (callbackRegistry2 != null) {
                    callbackRegistry2.notifyCallbacks(this, 3, null);
                }
            }
            this.q = false;
        }
    }

    protected static void executeBindingsOn(ViewDataBinding viewDataBinding) {
        viewDataBinding.e();
    }

    public void a() {
        executeBindings();
    }

    public void unbind() {
        e[] eVarArr = this.n;
        for (e eVar : eVarArr) {
            if (eVar != null) {
                eVar.a();
            }
        }
    }

    public static ViewDataBinding a(View view) {
        if (view != null) {
            return (ViewDataBinding) view.getTag(R.id.dataBinding);
        }
        return null;
    }

    @NonNull
    public View getRoot() {
        return this.o;
    }

    public void a(int i2, Object obj, int i3) {
        if (!this.x && onFieldChange(i2, obj, i3)) {
            requestRebind();
        }
    }

    protected boolean unregisterFrom(int i2) {
        e eVar = this.n[i2];
        if (eVar != null) {
            return eVar.a();
        }
        return false;
    }

    protected void requestRebind() {
        ViewDataBinding viewDataBinding = this.u;
        if (viewDataBinding != null) {
            viewDataBinding.requestRebind();
            return;
        }
        LifecycleOwner lifecycleOwner = this.v;
        if (lifecycleOwner == null || lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            synchronized (this) {
                if (!this.l) {
                    this.l = true;
                    if (c) {
                        this.r.postFrameCallback(this.s);
                    } else {
                        this.t.post(this.k);
                    }
                }
            }
        }
    }

    protected Object getObservedField(int i2) {
        e eVar = this.n[i2];
        if (eVar == null) {
            return null;
        }
        return eVar.b();
    }

    private boolean a(int i2, Object obj, a aVar) {
        if (obj == null) {
            return unregisterFrom(i2);
        }
        e eVar = this.n[i2];
        if (eVar == null) {
            registerTo(i2, obj, aVar);
            return true;
        } else if (eVar.b() == obj) {
            return false;
        } else {
            unregisterFrom(i2);
            registerTo(i2, obj, aVar);
            return true;
        }
    }

    protected boolean updateRegistration(int i2, Observable observable) {
        return a(i2, observable, d);
    }

    protected boolean updateRegistration(int i2, ObservableList observableList) {
        return a(i2, observableList, e);
    }

    protected boolean updateRegistration(int i2, ObservableMap observableMap) {
        return a(i2, observableMap, f);
    }

    protected boolean updateLiveDataRegistration(int i2, LiveData<?> liveData) {
        this.x = true;
        try {
            return a(i2, liveData, g);
        } finally {
            this.x = false;
        }
    }

    protected void ensureBindingComponentIsNotNull(Class<?> cls) {
        if (this.mBindingComponent == null) {
            throw new IllegalStateException("Required DataBindingComponent is null in class " + getClass().getSimpleName() + ". A BindingAdapter in " + cls.getCanonicalName() + " is not static and requires an object to use, retrieved from the DataBindingComponent. If you don't use an inflation method taking a DataBindingComponent, use DataBindingUtil.setDefaultComponent or make all BindingAdapter methods static.");
        }
    }

    protected void registerTo(int i2, Object obj, a aVar) {
        if (obj != null) {
            e eVar = this.n[i2];
            if (eVar == null) {
                eVar = aVar.a(this, i2);
                this.n[i2] = eVar;
                LifecycleOwner lifecycleOwner = this.v;
                if (lifecycleOwner != null) {
                    eVar.a(lifecycleOwner);
                }
            }
            eVar.a((e) obj);
        }
    }

    protected static ViewDataBinding bind(DataBindingComponent dataBindingComponent, View view, int i2) {
        return DataBindingUtil.a(dataBindingComponent, view, i2);
    }

    protected static Object[] mapBindings(DataBindingComponent dataBindingComponent, View view, int i2, IncludedLayouts includedLayouts, SparseIntArray sparseIntArray) {
        Object[] objArr = new Object[i2];
        a(dataBindingComponent, view, objArr, includedLayouts, sparseIntArray, true);
        return objArr;
    }

    protected static boolean parse(String str, boolean z) {
        return str == null ? z : Boolean.parseBoolean(str);
    }

    protected static byte parse(String str, byte b2) {
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException unused) {
            return b2;
        }
    }

    protected static short parse(String str, short s) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException unused) {
            return s;
        }
    }

    protected static int parse(String str, int i2) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    protected static long parse(String str, long j2) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j2;
        }
    }

    protected static float parse(String str, float f2) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f2;
        }
    }

    protected static double parse(String str, double d2) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    protected static char parse(String str, char c2) {
        return (str == null || str.isEmpty()) ? c2 : str.charAt(0);
    }

    protected static int getColorFromResource(View view, int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return view.getContext().getColor(i2);
        }
        return view.getResources().getColor(i2);
    }

    protected static ColorStateList getColorStateListFromResource(View view, int i2) {
        if (Build.VERSION.SDK_INT >= 23) {
            return view.getContext().getColorStateList(i2);
        }
        return view.getResources().getColorStateList(i2);
    }

    protected static Drawable getDrawableFromResource(View view, int i2) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getContext().getDrawable(i2);
        }
        return view.getResources().getDrawable(i2);
    }

    protected static <T> T getFromArray(T[] tArr, int i2) {
        if (tArr == null || i2 < 0 || i2 >= tArr.length) {
            return null;
        }
        return tArr[i2];
    }

    protected static <T> void setTo(T[] tArr, int i2, T t) {
        if (tArr != null && i2 >= 0 && i2 < tArr.length) {
            tArr[i2] = t;
        }
    }

    protected static boolean getFromArray(boolean[] zArr, int i2) {
        if (zArr == null || i2 < 0 || i2 >= zArr.length) {
            return false;
        }
        return zArr[i2];
    }

    protected static void setTo(boolean[] zArr, int i2, boolean z) {
        if (zArr != null && i2 >= 0 && i2 < zArr.length) {
            zArr[i2] = z;
        }
    }

    protected static byte getFromArray(byte[] bArr, int i2) {
        if (bArr == null || i2 < 0 || i2 >= bArr.length) {
            return (byte) 0;
        }
        return bArr[i2];
    }

    protected static void setTo(byte[] bArr, int i2, byte b2) {
        if (bArr != null && i2 >= 0 && i2 < bArr.length) {
            bArr[i2] = b2;
        }
    }

    protected static short getFromArray(short[] sArr, int i2) {
        if (sArr == null || i2 < 0 || i2 >= sArr.length) {
            return (short) 0;
        }
        return sArr[i2];
    }

    protected static void setTo(short[] sArr, int i2, short s) {
        if (sArr != null && i2 >= 0 && i2 < sArr.length) {
            sArr[i2] = s;
        }
    }

    protected static char getFromArray(char[] cArr, int i2) {
        if (cArr == null || i2 < 0 || i2 >= cArr.length) {
            return (char) 0;
        }
        return cArr[i2];
    }

    protected static void setTo(char[] cArr, int i2, char c2) {
        if (cArr != null && i2 >= 0 && i2 < cArr.length) {
            cArr[i2] = c2;
        }
    }

    protected static int getFromArray(int[] iArr, int i2) {
        if (iArr == null || i2 < 0 || i2 >= iArr.length) {
            return 0;
        }
        return iArr[i2];
    }

    protected static void setTo(int[] iArr, int i2, int i3) {
        if (iArr != null && i2 >= 0 && i2 < iArr.length) {
            iArr[i2] = i3;
        }
    }

    protected static long getFromArray(long[] jArr, int i2) {
        if (jArr == null || i2 < 0 || i2 >= jArr.length) {
            return 0L;
        }
        return jArr[i2];
    }

    protected static void setTo(long[] jArr, int i2, long j2) {
        if (jArr != null && i2 >= 0 && i2 < jArr.length) {
            jArr[i2] = j2;
        }
    }

    protected static float getFromArray(float[] fArr, int i2) {
        if (fArr == null || i2 < 0 || i2 >= fArr.length) {
            return 0.0f;
        }
        return fArr[i2];
    }

    protected static void setTo(float[] fArr, int i2, float f2) {
        if (fArr != null && i2 >= 0 && i2 < fArr.length) {
            fArr[i2] = f2;
        }
    }

    protected static double getFromArray(double[] dArr, int i2) {
        if (dArr == null || i2 < 0 || i2 >= dArr.length) {
            return 0.0d;
        }
        return dArr[i2];
    }

    protected static void setTo(double[] dArr, int i2, double d2) {
        if (dArr != null && i2 >= 0 && i2 < dArr.length) {
            dArr[i2] = d2;
        }
    }

    protected static <T> T getFromList(List<T> list, int i2) {
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return null;
        }
        return list.get(i2);
    }

    protected static <T> void setTo(List<T> list, int i2, T t) {
        if (list != null && i2 >= 0 && i2 < list.size()) {
            list.set(i2, t);
        }
    }

    protected static <T> T getFromList(SparseArray<T> sparseArray, int i2) {
        if (sparseArray == null || i2 < 0) {
            return null;
        }
        return sparseArray.get(i2);
    }

    protected static <T> void setTo(SparseArray<T> sparseArray, int i2, T t) {
        if (sparseArray != null && i2 >= 0 && i2 < sparseArray.size()) {
            sparseArray.put(i2, t);
        }
    }

    @TargetApi(16)
    protected static <T> T getFromList(LongSparseArray<T> longSparseArray, int i2) {
        if (longSparseArray == null || i2 < 0) {
            return null;
        }
        return longSparseArray.get(i2);
    }

    @TargetApi(16)
    protected static <T> void setTo(LongSparseArray<T> longSparseArray, int i2, T t) {
        if (longSparseArray != null && i2 >= 0 && i2 < longSparseArray.size()) {
            longSparseArray.put(i2, t);
        }
    }

    protected static <T> T getFromList(androidx.collection.LongSparseArray<T> longSparseArray, int i2) {
        if (longSparseArray == null || i2 < 0) {
            return null;
        }
        return longSparseArray.get(i2);
    }

    protected static <T> void setTo(androidx.collection.LongSparseArray<T> longSparseArray, int i2, T t) {
        if (longSparseArray != null && i2 >= 0 && i2 < longSparseArray.size()) {
            longSparseArray.put(i2, t);
        }
    }

    protected static boolean getFromList(SparseBooleanArray sparseBooleanArray, int i2) {
        if (sparseBooleanArray == null || i2 < 0) {
            return false;
        }
        return sparseBooleanArray.get(i2);
    }

    protected static void setTo(SparseBooleanArray sparseBooleanArray, int i2, boolean z) {
        if (sparseBooleanArray != null && i2 >= 0 && i2 < sparseBooleanArray.size()) {
            sparseBooleanArray.put(i2, z);
        }
    }

    protected static int getFromList(SparseIntArray sparseIntArray, int i2) {
        if (sparseIntArray == null || i2 < 0) {
            return 0;
        }
        return sparseIntArray.get(i2);
    }

    protected static void setTo(SparseIntArray sparseIntArray, int i2, int i3) {
        if (sparseIntArray != null && i2 >= 0 && i2 < sparseIntArray.size()) {
            sparseIntArray.put(i2, i3);
        }
    }

    @TargetApi(18)
    protected static long getFromList(SparseLongArray sparseLongArray, int i2) {
        if (sparseLongArray == null || i2 < 0) {
            return 0L;
        }
        return sparseLongArray.get(i2);
    }

    @TargetApi(18)
    protected static void setTo(SparseLongArray sparseLongArray, int i2, long j2) {
        if (sparseLongArray != null && i2 >= 0 && i2 < sparseLongArray.size()) {
            sparseLongArray.put(i2, j2);
        }
    }

    protected static <K, T> T getFrom(Map<K, T> map, K k) {
        if (map == null) {
            return null;
        }
        return map.get(k);
    }

    protected static <K, T> void setTo(Map<K, T> map, K k, T t) {
        if (map != null) {
            map.put(k, t);
        }
    }

    protected static void setBindingInverseListener(ViewDataBinding viewDataBinding, InverseBindingListener inverseBindingListener, PropertyChangedInverseListener propertyChangedInverseListener) {
        if (inverseBindingListener != propertyChangedInverseListener) {
            if (inverseBindingListener != null) {
                viewDataBinding.removeOnPropertyChangedCallback((PropertyChangedInverseListener) inverseBindingListener);
            }
            if (propertyChangedInverseListener != null) {
                viewDataBinding.addOnPropertyChangedCallback(propertyChangedInverseListener);
            }
        }
    }

    protected static int safeUnbox(Integer num) {
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    protected static long safeUnbox(Long l) {
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    protected static short safeUnbox(Short sh) {
        if (sh == null) {
            return (short) 0;
        }
        return sh.shortValue();
    }

    protected static byte safeUnbox(Byte b2) {
        if (b2 == null) {
            return (byte) 0;
        }
        return b2.byteValue();
    }

    protected static char safeUnbox(Character ch) {
        if (ch == null) {
            return (char) 0;
        }
        return ch.charValue();
    }

    protected static double safeUnbox(Double d2) {
        if (d2 == null) {
            return 0.0d;
        }
        return d2.doubleValue();
    }

    protected static float safeUnbox(Float f2) {
        if (f2 == null) {
            return 0.0f;
        }
        return f2.floatValue();
    }

    protected static boolean safeUnbox(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    protected void setContainedBinding(ViewDataBinding viewDataBinding) {
        if (viewDataBinding != null) {
            viewDataBinding.u = this;
        }
    }

    protected static Object[] mapBindings(DataBindingComponent dataBindingComponent, View[] viewArr, int i2, IncludedLayouts includedLayouts, SparseIntArray sparseIntArray) {
        Object[] objArr = new Object[i2];
        for (View view : viewArr) {
            a(dataBindingComponent, view, objArr, includedLayouts, sparseIntArray, true);
        }
        return objArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0110 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(androidx.databinding.DataBindingComponent r16, android.view.View r17, java.lang.Object[] r18, androidx.databinding.ViewDataBinding.IncludedLayouts r19, android.util.SparseIntArray r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.databinding.ViewDataBinding.a(androidx.databinding.DataBindingComponent, android.view.View, java.lang.Object[], androidx.databinding.ViewDataBinding$IncludedLayouts, android.util.SparseIntArray, boolean):void");
    }

    private static int a(String str, int i2, IncludedLayouts includedLayouts, int i3) {
        CharSequence subSequence = str.subSequence(str.indexOf(47) + 1, str.length() - 2);
        String[] strArr = includedLayouts.layouts[i3];
        int length = strArr.length;
        while (i2 < length) {
            if (TextUtils.equals(subSequence, strArr[i2])) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private static int a(ViewGroup viewGroup, int i2) {
        String str = (String) viewGroup.getChildAt(i2).getTag();
        String substring = str.substring(0, str.length() - 1);
        int length = substring.length();
        int childCount = viewGroup.getChildCount();
        for (int i3 = i2 + 1; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            String str2 = childAt.getTag() instanceof String ? (String) childAt.getTag() : null;
            if (str2 != null && str2.startsWith(substring)) {
                if (str2.length() == str.length() && str2.charAt(str2.length() - 1) == '0') {
                    return i2;
                }
                if (a(str2, length)) {
                    i2 = i3;
                }
            }
        }
        return i2;
    }

    private static boolean a(String str, int i2) {
        int length = str.length();
        if (length == i2) {
            return false;
        }
        while (i2 < length) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
            i2++;
        }
        return true;
    }

    private static int b(String str, int i2) {
        int length = str.length();
        int i3 = 0;
        while (i2 < length) {
            i3 = (i3 * 10) + (str.charAt(i2) - '0');
            i2++;
        }
        return i3;
    }

    public static void f() {
        while (true) {
            Reference<? extends ViewDataBinding> poll = i.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof e) {
                ((e) poll).a();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class e<T> extends WeakReference<ViewDataBinding> {
        protected final int a;
        private final c<T> b;
        private T c;

        public e(ViewDataBinding viewDataBinding, int i, c<T> cVar) {
            super(viewDataBinding, ViewDataBinding.i);
            this.a = i;
            this.b = cVar;
        }

        public void a(LifecycleOwner lifecycleOwner) {
            this.b.a(lifecycleOwner);
        }

        public void a(T t) {
            a();
            this.c = t;
            T t2 = this.c;
            if (t2 != null) {
                this.b.b(t2);
            }
        }

        public boolean a() {
            boolean z;
            T t = this.c;
            if (t != null) {
                this.b.a((c<T>) t);
                z = true;
            } else {
                z = false;
            }
            this.c = null;
            return z;
        }

        public T b() {
            return this.c;
        }

        protected ViewDataBinding c() {
            ViewDataBinding viewDataBinding = (ViewDataBinding) get();
            if (viewDataBinding == null) {
                a();
            }
            return viewDataBinding;
        }
    }

    /* loaded from: classes.dex */
    private static class g extends Observable.OnPropertyChangedCallback implements c<Observable> {
        final e<Observable> a;

        @Override // androidx.databinding.ViewDataBinding.c
        public void a(LifecycleOwner lifecycleOwner) {
        }

        public g(ViewDataBinding viewDataBinding, int i) {
            this.a = new e<>(viewDataBinding, i, this);
        }

        public e<Observable> a() {
            return this.a;
        }

        /* renamed from: a */
        public void b(Observable observable) {
            observable.addOnPropertyChangedCallback(this);
        }

        /* renamed from: b */
        public void a(Observable observable) {
            observable.removeOnPropertyChangedCallback(this);
        }

        @Override // androidx.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            ViewDataBinding c = this.a.c();
            if (c != null && this.a.b() == observable) {
                c.a(this.a.a, observable, i);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class d extends ObservableList.OnListChangedCallback implements c<ObservableList> {
        final e<ObservableList> a;

        @Override // androidx.databinding.ViewDataBinding.c
        public void a(LifecycleOwner lifecycleOwner) {
        }

        public d(ViewDataBinding viewDataBinding, int i) {
            this.a = new e<>(viewDataBinding, i, this);
        }

        public e<ObservableList> a() {
            return this.a;
        }

        /* renamed from: a */
        public void b(ObservableList observableList) {
            observableList.addOnListChangedCallback(this);
        }

        /* renamed from: b */
        public void a(ObservableList observableList) {
            observableList.removeOnListChangedCallback(this);
        }

        @Override // androidx.databinding.ObservableList.OnListChangedCallback
        public void onChanged(ObservableList observableList) {
            ObservableList b;
            ViewDataBinding c = this.a.c();
            if (c != null && (b = this.a.b()) == observableList) {
                c.a(this.a.a, b, 0);
            }
        }

        @Override // androidx.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeChanged(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }

        @Override // androidx.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeInserted(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }

        @Override // androidx.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeMoved(ObservableList observableList, int i, int i2, int i3) {
            onChanged(observableList);
        }

        @Override // androidx.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeRemoved(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }
    }

    /* loaded from: classes.dex */
    private static class f extends ObservableMap.OnMapChangedCallback implements c<ObservableMap> {
        final e<ObservableMap> a;

        @Override // androidx.databinding.ViewDataBinding.c
        public void a(LifecycleOwner lifecycleOwner) {
        }

        public f(ViewDataBinding viewDataBinding, int i) {
            this.a = new e<>(viewDataBinding, i, this);
        }

        public e<ObservableMap> a() {
            return this.a;
        }

        /* renamed from: a */
        public void b(ObservableMap observableMap) {
            observableMap.addOnMapChangedCallback(this);
        }

        /* renamed from: b */
        public void a(ObservableMap observableMap) {
            observableMap.removeOnMapChangedCallback(this);
        }

        @Override // androidx.databinding.ObservableMap.OnMapChangedCallback
        public void onMapChanged(ObservableMap observableMap, Object obj) {
            ViewDataBinding c = this.a.c();
            if (c != null && observableMap == this.a.b()) {
                c.a(this.a.a, observableMap, 0);
            }
        }
    }

    /* loaded from: classes.dex */
    private static class b implements c<LiveData<?>>, Observer {
        final e<LiveData<?>> a;
        LifecycleOwner b;

        public b(ViewDataBinding viewDataBinding, int i) {
            this.a = new e<>(viewDataBinding, i, this);
        }

        @Override // androidx.databinding.ViewDataBinding.c
        public void a(LifecycleOwner lifecycleOwner) {
            LiveData<?> b = this.a.b();
            if (b != null) {
                if (this.b != null) {
                    b.removeObserver(this);
                }
                if (lifecycleOwner != null) {
                    b.observe(lifecycleOwner, this);
                }
            }
            this.b = lifecycleOwner;
        }

        public e<LiveData<?>> a() {
            return this.a;
        }

        /* renamed from: a */
        public void b(LiveData<?> liveData) {
            LifecycleOwner lifecycleOwner = this.b;
            if (lifecycleOwner != null) {
                liveData.observe(lifecycleOwner, this);
            }
        }

        /* renamed from: b */
        public void a(LiveData<?> liveData) {
            liveData.removeObserver(this);
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(@Nullable Object obj) {
            this.a.c().a(this.a.a, this.a.b(), 0);
        }
    }

    /* loaded from: classes.dex */
    public static class IncludedLayouts {
        public final int[][] indexes;
        public final int[][] layoutIds;
        public final String[][] layouts;

        public IncludedLayouts(int i) {
            this.layouts = new String[i];
            this.indexes = new int[i];
            this.layoutIds = new int[i];
        }

        public void setIncludes(int i, String[] strArr, int[] iArr, int[] iArr2) {
            this.layouts[i] = strArr;
            this.indexes[i] = iArr;
            this.layoutIds[i] = iArr2;
        }
    }

    /* loaded from: classes.dex */
    protected static abstract class PropertyChangedInverseListener extends Observable.OnPropertyChangedCallback implements InverseBindingListener {
        final int a;

        public PropertyChangedInverseListener(int i) {
            this.a = i;
        }

        @Override // androidx.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            if (i == this.a || i == 0) {
                onChange();
            }
        }
    }

    /* loaded from: classes.dex */
    public class OnStartListener implements LifecycleObserver {
        private OnStartListener() {
            ViewDataBinding.this = r1;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            ViewDataBinding.this.executePendingBindings();
        }
    }
}
