package androidx.fragment.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;
    private final FragmentFactory a;
    private final ClassLoader b;
    ArrayList<a> d;
    int e;
    int f;
    int g;
    int h;
    int i;
    boolean j;
    boolean k;
    @Nullable
    String l;
    int m;
    CharSequence n;
    int o;
    CharSequence p;
    ArrayList<String> q;
    ArrayList<String> r;
    boolean s;
    ArrayList<Runnable> t;

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract void commitNow();

    public abstract void commitNowAllowingStateLoss();

    @NonNull
    @Deprecated
    public FragmentTransaction setTransitionStyle(@StyleRes int i) {
        return this;
    }

    /* loaded from: classes.dex */
    public static final class a {
        int a;
        Fragment b;
        int c;
        int d;
        int e;
        int f;
        Lifecycle.State g;
        Lifecycle.State h;

        public a() {
        }

        public a(int i, Fragment fragment) {
            this.a = i;
            this.b = fragment;
            this.g = Lifecycle.State.RESUMED;
            this.h = Lifecycle.State.RESUMED;
        }

        a(int i, @NonNull Fragment fragment, Lifecycle.State state) {
            this.a = i;
            this.b = fragment;
            this.g = fragment.mMaxState;
            this.h = state;
        }
    }

    @Deprecated
    public FragmentTransaction() {
        this.d = new ArrayList<>();
        this.k = true;
        this.s = false;
        this.a = null;
        this.b = null;
    }

    public FragmentTransaction(@NonNull FragmentFactory fragmentFactory, @Nullable ClassLoader classLoader) {
        this.d = new ArrayList<>();
        this.k = true;
        this.s = false;
        this.a = fragmentFactory;
        this.b = classLoader;
    }

    void a(a aVar) {
        this.d.add(aVar);
        aVar.c = this.e;
        aVar.d = this.f;
        aVar.e = this.g;
        aVar.f = this.h;
    }

    @NonNull
    private Fragment a(@NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        FragmentFactory fragmentFactory = this.a;
        if (fragmentFactory != null) {
            ClassLoader classLoader = this.b;
            if (classLoader != null) {
                Fragment instantiate = fragmentFactory.instantiate(classLoader, cls.getName());
                if (bundle != null) {
                    instantiate.setArguments(bundle);
                }
                return instantiate;
            }
            throw new IllegalStateException("The FragmentManager must be attached to itshost to create a Fragment");
        }
        throw new IllegalStateException("Creating a Fragment requires that this FragmentTransaction was built with FragmentManager.beginTransaction()");
    }

    @NonNull
    public final FragmentTransaction add(@NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return add(a(cls, bundle), str);
    }

    @NonNull
    public FragmentTransaction add(@NonNull Fragment fragment, @Nullable String str) {
        a(0, fragment, str, 1);
        return this;
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int i, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        return add(i, a(cls, bundle));
    }

    @NonNull
    public FragmentTransaction add(@IdRes int i, @NonNull Fragment fragment) {
        a(i, fragment, null, 1);
        return this;
    }

    @NonNull
    public final FragmentTransaction add(@IdRes int i, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return add(i, a(cls, bundle), str);
    }

    @NonNull
    public FragmentTransaction add(@IdRes int i, @NonNull Fragment fragment, @Nullable String str) {
        a(i, fragment, str, 1);
        return this;
    }

    public FragmentTransaction a(@NonNull ViewGroup viewGroup, @NonNull Fragment fragment, @Nullable String str) {
        fragment.mContainer = viewGroup;
        return add(viewGroup.getId(), fragment, str);
    }

    public void a(int i, Fragment fragment, @Nullable String str, int i2) {
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            if (fragment.mTag == null || str.equals(fragment.mTag)) {
                fragment.mTag = str;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + str);
            }
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            } else if (fragment.mFragmentId == 0 || fragment.mFragmentId == i) {
                fragment.mFragmentId = i;
                fragment.mContainerId = i;
            } else {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + i);
            }
        }
        a(new a(i2, fragment));
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int i, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle) {
        return replace(i, cls, bundle, null);
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int i, @NonNull Fragment fragment) {
        return replace(i, fragment, (String) null);
    }

    @NonNull
    public final FragmentTransaction replace(@IdRes int i, @NonNull Class<? extends Fragment> cls, @Nullable Bundle bundle, @Nullable String str) {
        return replace(i, a(cls, bundle), str);
    }

    @NonNull
    public FragmentTransaction replace(@IdRes int i, @NonNull Fragment fragment, @Nullable String str) {
        if (i != 0) {
            a(i, fragment, str, 2);
            return this;
        }
        throw new IllegalArgumentException("Must use non-zero containerViewId");
    }

    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        a(new a(3, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        a(new a(4, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        a(new a(5, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        a(new a(6, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction attach(@NonNull Fragment fragment) {
        a(new a(7, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        a(new a(8, fragment));
        return this;
    }

    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        a(new a(10, fragment, state));
        return this;
    }

    public boolean isEmpty() {
        return this.d.isEmpty();
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2) {
        return setCustomAnimations(i, i2, 0, 0);
    }

    @NonNull
    public FragmentTransaction setCustomAnimations(@AnimRes @AnimatorRes int i, @AnimRes @AnimatorRes int i2, @AnimRes @AnimatorRes int i3, @AnimRes @AnimatorRes int i4) {
        this.e = i;
        this.f = i2;
        this.g = i3;
        this.h = i4;
        return this;
    }

    @NonNull
    public FragmentTransaction addSharedElement(@NonNull View view, @NonNull String str) {
        if (n.a()) {
            String transitionName = ViewCompat.getTransitionName(view);
            if (transitionName != null) {
                if (this.q == null) {
                    this.q = new ArrayList<>();
                    this.r = new ArrayList<>();
                } else if (this.r.contains(str)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + str + "' has already been added to the transaction.");
                } else if (this.q.contains(transitionName)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + transitionName + "' has already been added to the transaction.");
                }
                this.q.add(transitionName);
                this.r.add(str);
            } else {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
        }
        return this;
    }

    @NonNull
    public FragmentTransaction setTransition(int i) {
        this.i = i;
        return this;
    }

    @NonNull
    public FragmentTransaction addToBackStack(@Nullable String str) {
        if (this.k) {
            this.j = true;
            this.l = str;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public boolean isAddToBackStackAllowed() {
        return this.k;
    }

    @NonNull
    public FragmentTransaction disallowAddToBackStack() {
        if (!this.j) {
            this.k = false;
            return this;
        }
        throw new IllegalStateException("This transaction is already being added to the back stack");
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@StringRes int i) {
        this.m = i;
        this.n = null;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbTitle(@Nullable CharSequence charSequence) {
        this.m = 0;
        this.n = charSequence;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@StringRes int i) {
        this.o = i;
        this.p = null;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setBreadCrumbShortTitle(@Nullable CharSequence charSequence) {
        this.o = 0;
        this.p = charSequence;
        return this;
    }

    @NonNull
    public FragmentTransaction setReorderingAllowed(boolean z) {
        this.s = z;
        return this;
    }

    @NonNull
    @Deprecated
    public FragmentTransaction setAllowOptimization(boolean z) {
        return setReorderingAllowed(z);
    }

    @NonNull
    public FragmentTransaction runOnCommit(@NonNull Runnable runnable) {
        disallowAddToBackStack();
        if (this.t == null) {
            this.t = new ArrayList<>();
        }
        this.t.add(runnable);
        return this;
    }
}
