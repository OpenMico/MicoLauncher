package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.appcompat.resources.R;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class ResourceManagerInternal {
    private static ResourceManagerInternal b;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> d;
    private SimpleArrayMap<String, e> e;
    private SparseArrayCompat<String> f;
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> g = new WeakHashMap<>(0);
    private TypedValue h;
    private boolean i;
    private ResourceManagerHooks j;
    private static final PorterDuff.Mode a = PorterDuff.Mode.SRC_IN;
    private static final c c = new c(6);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public interface ResourceManagerHooks {
        @Nullable
        Drawable createDrawableFor(@NonNull ResourceManagerInternal resourceManagerInternal, @NonNull Context context, @DrawableRes int i);

        @Nullable
        ColorStateList getTintListForDrawableRes(@NonNull Context context, @DrawableRes int i);

        @Nullable
        PorterDuff.Mode getTintModeForDrawableRes(int i);

        boolean tintDrawable(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable);

        boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable);
    }

    /* loaded from: classes.dex */
    public interface e {
        Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme);
    }

    public static synchronized ResourceManagerInternal get() {
        ResourceManagerInternal resourceManagerInternal;
        synchronized (ResourceManagerInternal.class) {
            if (b == null) {
                b = new ResourceManagerInternal();
                a(b);
            }
            resourceManagerInternal = b;
        }
        return resourceManagerInternal;
    }

    private static void a(@NonNull ResourceManagerInternal resourceManagerInternal) {
        if (Build.VERSION.SDK_INT < 24) {
            resourceManagerInternal.a("vector", new f());
            resourceManagerInternal.a("animated-vector", new b());
            resourceManagerInternal.a("animated-selector", new a());
            resourceManagerInternal.a("drawable", new d());
        }
    }

    public synchronized void setHooks(ResourceManagerHooks resourceManagerHooks) {
        this.j = resourceManagerHooks;
    }

    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int i) {
        return a(context, i, false);
    }

    public synchronized Drawable a(@NonNull Context context, @DrawableRes int i, boolean z) {
        Drawable c2;
        a(context);
        c2 = c(context, i);
        if (c2 == null) {
            c2 = b(context, i);
        }
        if (c2 == null) {
            c2 = ContextCompat.getDrawable(context, i);
        }
        if (c2 != null) {
            c2 = a(context, i, z, c2);
        }
        if (c2 != null) {
            DrawableUtils.a(c2);
        }
        return c2;
    }

    public synchronized void onConfigurationChanged(@NonNull Context context) {
        LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.g.get(context);
        if (longSparseArray != null) {
            longSparseArray.clear();
        }
    }

    private static long a(TypedValue typedValue) {
        return (typedValue.assetCookie << 32) | typedValue.data;
    }

    private Drawable b(@NonNull Context context, @DrawableRes int i) {
        if (this.h == null) {
            this.h = new TypedValue();
        }
        TypedValue typedValue = this.h;
        context.getResources().getValue(i, typedValue, true);
        long a2 = a(typedValue);
        Drawable a3 = a(context, a2);
        if (a3 != null) {
            return a3;
        }
        ResourceManagerHooks resourceManagerHooks = this.j;
        Drawable createDrawableFor = resourceManagerHooks == null ? null : resourceManagerHooks.createDrawableFor(this, context, i);
        if (createDrawableFor != null) {
            createDrawableFor.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, a2, createDrawableFor);
        }
        return createDrawableFor;
    }

    private Drawable a(@NonNull Context context, @DrawableRes int i, boolean z, @NonNull Drawable drawable) {
        ColorStateList a2 = a(context, i);
        if (a2 != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable wrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(wrap, a2);
            PorterDuff.Mode a3 = a(i);
            if (a3 == null) {
                return wrap;
            }
            DrawableCompat.setTintMode(wrap, a3);
            return wrap;
        }
        ResourceManagerHooks resourceManagerHooks = this.j;
        if ((resourceManagerHooks == null || !resourceManagerHooks.tintDrawable(context, i, drawable)) && !a(context, i, drawable) && z) {
            return null;
        }
        return drawable;
    }

    private Drawable c(@NonNull Context context, @DrawableRes int i) {
        int next;
        SimpleArrayMap<String, e> simpleArrayMap = this.e;
        if (simpleArrayMap == null || simpleArrayMap.isEmpty()) {
            return null;
        }
        SparseArrayCompat<String> sparseArrayCompat = this.f;
        if (sparseArrayCompat != null) {
            String str = sparseArrayCompat.get(i);
            if ("appcompat_skip_skip".equals(str) || (str != null && this.e.get(str) == null)) {
                return null;
            }
        } else {
            this.f = new SparseArrayCompat<>();
        }
        if (this.h == null) {
            this.h = new TypedValue();
        }
        TypedValue typedValue = this.h;
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        long a2 = a(typedValue);
        Drawable a3 = a(context, a2);
        if (a3 != null) {
            return a3;
        }
        if (typedValue.string != null && typedValue.string.toString().endsWith(".xml")) {
            try {
                XmlResourceParser xml = resources.getXml(i);
                AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
                while (true) {
                    next = xml.next();
                    if (next == 2 || next == 1) {
                        break;
                    }
                }
                if (next == 2) {
                    String name = xml.getName();
                    this.f.append(i, name);
                    e eVar = this.e.get(name);
                    if (eVar != null) {
                        a3 = eVar.a(context, xml, asAttributeSet, context.getTheme());
                    }
                    if (a3 != null) {
                        a3.setChangingConfigurations(typedValue.changingConfigurations);
                        a(context, a2, a3);
                    }
                } else {
                    throw new XmlPullParserException("No start tag found");
                }
            } catch (Exception e2) {
                Log.e("ResourceManagerInternal", "Exception while inflating drawable", e2);
            }
        }
        if (a3 == null) {
            this.f.append(i, "appcompat_skip_skip");
        }
        return a3;
    }

    private synchronized Drawable a(@NonNull Context context, long j) {
        LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.g.get(context);
        if (longSparseArray == null) {
            return null;
        }
        WeakReference<Drawable.ConstantState> weakReference = longSparseArray.get(j);
        if (weakReference != null) {
            Drawable.ConstantState constantState = weakReference.get();
            if (constantState != null) {
                return constantState.newDrawable(context.getResources());
            }
            longSparseArray.remove(j);
        }
        return null;
    }

    private synchronized boolean a(@NonNull Context context, long j, @NonNull Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.g.get(context);
        if (longSparseArray == null) {
            longSparseArray = new LongSparseArray<>();
            this.g.put(context, longSparseArray);
        }
        longSparseArray.put(j, new WeakReference<>(constantState));
        return true;
    }

    public synchronized Drawable a(@NonNull Context context, @NonNull VectorEnabledTintResources vectorEnabledTintResources, @DrawableRes int i) {
        Drawable c2 = c(context, i);
        if (c2 == null) {
            c2 = vectorEnabledTintResources.a(i);
        }
        if (c2 == null) {
            return null;
        }
        return a(context, i, false, c2);
    }

    public boolean a(@NonNull Context context, @DrawableRes int i, @NonNull Drawable drawable) {
        ResourceManagerHooks resourceManagerHooks = this.j;
        return resourceManagerHooks != null && resourceManagerHooks.tintDrawableUsingColorFilter(context, i, drawable);
    }

    private void a(@NonNull String str, @NonNull e eVar) {
        if (this.e == null) {
            this.e = new SimpleArrayMap<>();
        }
        this.e.put(str, eVar);
    }

    PorterDuff.Mode a(int i) {
        ResourceManagerHooks resourceManagerHooks = this.j;
        if (resourceManagerHooks == null) {
            return null;
        }
        return resourceManagerHooks.getTintModeForDrawableRes(i);
    }

    public synchronized ColorStateList a(@NonNull Context context, @DrawableRes int i) {
        ColorStateList d2;
        d2 = d(context, i);
        if (d2 == null) {
            d2 = this.j == null ? null : this.j.getTintListForDrawableRes(context, i);
            if (d2 != null) {
                a(context, i, d2);
            }
        }
        return d2;
    }

    private ColorStateList d(@NonNull Context context, @DrawableRes int i) {
        SparseArrayCompat<ColorStateList> sparseArrayCompat;
        WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.d;
        if (weakHashMap == null || (sparseArrayCompat = weakHashMap.get(context)) == null) {
            return null;
        }
        return sparseArrayCompat.get(i);
    }

    private void a(@NonNull Context context, @DrawableRes int i, @NonNull ColorStateList colorStateList) {
        if (this.d == null) {
            this.d = new WeakHashMap<>();
        }
        SparseArrayCompat<ColorStateList> sparseArrayCompat = this.d.get(context);
        if (sparseArrayCompat == null) {
            sparseArrayCompat = new SparseArrayCompat<>();
            this.d.put(context, sparseArrayCompat);
        }
        sparseArrayCompat.append(i, colorStateList);
    }

    /* loaded from: classes.dex */
    public static class c extends LruCache<Integer, PorterDuffColorFilter> {
        public c(int i) {
            super(i);
        }

        PorterDuffColorFilter a(int i, PorterDuff.Mode mode) {
            return get(Integer.valueOf(b(i, mode)));
        }

        PorterDuffColorFilter a(int i, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return put(Integer.valueOf(b(i, mode)), porterDuffColorFilter);
        }

        private static int b(int i, PorterDuff.Mode mode) {
            return ((i + 31) * 31) + mode.hashCode();
        }
    }

    public static void a(Drawable drawable, TintInfo tintInfo, int[] iArr) {
        if (!DrawableUtils.canSafelyMutateDrawable(drawable) || drawable.mutate() == drawable) {
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                drawable.setColorFilter(a(tintInfo.mHasTintList ? tintInfo.mTintList : null, tintInfo.mHasTintMode ? tintInfo.mTintMode : a, iArr));
            } else {
                drawable.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode, int[] iArr) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode);
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter a2;
        synchronized (ResourceManagerInternal.class) {
            a2 = c.a(i, mode);
            if (a2 == null) {
                a2 = new PorterDuffColorFilter(i, mode);
                c.a(i, mode, a2);
            }
        }
        return a2;
    }

    private void a(@NonNull Context context) {
        if (!this.i) {
            this.i = true;
            Drawable drawable = getDrawable(context, R.drawable.abc_vector_test);
            if (drawable == null || !a(drawable)) {
                this.i = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    private static boolean a(@NonNull Drawable drawable) {
        return (drawable instanceof VectorDrawableCompat) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }

    /* loaded from: classes.dex */
    public static class f implements e {
        f() {
        }

        @Override // androidx.appcompat.widget.ResourceManagerInternal.e
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class b implements e {
        b() {
        }

        @Override // androidx.appcompat.widget.ResourceManagerInternal.e
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class a implements e {
        a() {
        }

        @Override // androidx.appcompat.widget.ResourceManagerInternal.e
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return AnimatedStateListDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class d implements e {
        d() {
        }

        @Override // androidx.appcompat.widget.ResourceManagerInternal.e
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            String classAttribute = attributeSet.getClassAttribute();
            if (classAttribute == null) {
                return null;
            }
            try {
                Drawable drawable = (Drawable) d.class.getClassLoader().loadClass(classAttribute).asSubclass(Drawable.class).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                if (Build.VERSION.SDK_INT >= 21) {
                    drawable.inflate(context.getResources(), xmlPullParser, attributeSet, theme);
                } else {
                    drawable.inflate(context.getResources(), xmlPullParser, attributeSet);
                }
                return drawable;
            } catch (Exception e) {
                Log.e("DrawableDelegate", "Exception while inflating <drawable>", e);
                return null;
            }
        }
    }
}
