package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.d;
import androidx.fragment.app.n;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaomi.mipush.sdk.Constants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class FragmentManager implements FragmentResultOwner {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static boolean a = true;
    private static boolean f = false;
    private ActivityResultLauncher<Intent> C;
    private ActivityResultLauncher<IntentSenderRequest> D;
    private ActivityResultLauncher<String[]> E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I;
    private boolean J;
    private ArrayList<a> K;
    private ArrayList<Boolean> L;
    private ArrayList<Fragment> M;
    private ArrayList<f> N;
    private j O;
    ArrayList<a> b;
    @Nullable
    Fragment d;
    private boolean h;
    private ArrayList<Fragment> j;
    private OnBackPressedDispatcher l;
    private ArrayList<OnBackStackChangedListener> q;
    private FragmentHostCallback<?> v;
    private FragmentContainer w;
    private Fragment x;
    private final ArrayList<d> g = new ArrayList<>();
    private final m i = new m();
    private final f k = new f(this);
    private final OnBackPressedCallback m = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            FragmentManager.this.a();
        }
    };
    private final AtomicInteger n = new AtomicInteger();
    private final Map<String, Bundle> o = Collections.synchronizedMap(new HashMap());
    private final Map<String, c> p = Collections.synchronizedMap(new HashMap());
    private Map<Fragment, HashSet<CancellationSignal>> r = Collections.synchronizedMap(new HashMap());
    private final n.a s = new n.a() { // from class: androidx.fragment.app.FragmentManager.4
        @Override // androidx.fragment.app.n.a
        public void a(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            FragmentManager.this.a(fragment, cancellationSignal);
        }

        @Override // androidx.fragment.app.n.a
        public void b(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            if (!cancellationSignal.isCanceled()) {
                FragmentManager.this.b(fragment, cancellationSignal);
            }
        }
    };
    private final g t = new g(this);
    private final CopyOnWriteArrayList<FragmentOnAttachListener> u = new CopyOnWriteArrayList<>();
    int c = -1;
    private FragmentFactory y = null;
    private FragmentFactory z = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.5
        @Override // androidx.fragment.app.FragmentFactory
        @NonNull
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String str) {
            return FragmentManager.this.h().instantiate(FragmentManager.this.h().c(), str, null);
        }
    };
    private s A = null;
    private s B = new s() { // from class: androidx.fragment.app.FragmentManager.7
        @Override // androidx.fragment.app.s
        @NonNull
        public r a(@NonNull ViewGroup viewGroup) {
            return new c(viewGroup);
        }
    };
    ArrayDeque<b> e = new ArrayDeque<>();
    private Runnable P = new Runnable() { // from class: androidx.fragment.app.FragmentManager.8
        @Override // java.lang.Runnable
        public void run() {
            FragmentManager.this.a(true);
        }
    };

    /* loaded from: classes.dex */
    public interface BackStackEntry {
        @Nullable
        @Deprecated
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Nullable
        @Deprecated
        CharSequence getBreadCrumbTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    /* loaded from: classes.dex */
    public static abstract class FragmentLifecycleCallbacks {
        @Deprecated
        public void onFragmentActivityCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPreAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Bundle bundle) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }
    }

    /* loaded from: classes.dex */
    public interface OnBackStackChangedListener {
        @MainThread
        void onBackStackChanged();
    }

    /* loaded from: classes.dex */
    public interface d {
        boolean a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2);
    }

    public static int c(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i != 4099) {
            return i != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    @FragmentStateManagerControl
    public static void enableNewStateManager(boolean z) {
        a = z;
    }

    @Deprecated
    public static void enableDebugLogging(boolean z) {
        f = z;
    }

    public static boolean a(int i) {
        return f || Log.isLoggable("FragmentManager", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class c implements FragmentResultListener {
        private final Lifecycle a;
        private final FragmentResultListener b;
        private final LifecycleEventObserver c;

        c(@NonNull Lifecycle lifecycle, @NonNull FragmentResultListener fragmentResultListener, @NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.a = lifecycle;
            this.b = fragmentResultListener;
            this.c = lifecycleEventObserver;
        }

        public boolean a(Lifecycle.State state) {
            return this.a.getCurrentState().isAtLeast(state);
        }

        @Override // androidx.fragment.app.FragmentResultListener
        public void onFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
            this.b.onFragmentResult(str, bundle);
        }

        public void a() {
            this.a.removeObserver(this.c);
        }
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new q("FragmentManager"));
        FragmentHostCallback<?> fragmentHostCallback = this.v;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
            }
        } else {
            try {
                dump("  ", null, printWriter, new String[0]);
            } catch (Exception e3) {
                Log.e("FragmentManager", "Failed dumping state", e3);
            }
        }
        throw runtimeException;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    @NonNull
    public FragmentTransaction beginTransaction() {
        return new a(this);
    }

    public boolean executePendingTransactions() {
        boolean a2 = a(true);
        G();
        return a2;
    }

    private void C() {
        synchronized (this.g) {
            boolean z = true;
            if (!this.g.isEmpty()) {
                this.m.setEnabled(true);
                return;
            }
            OnBackPressedCallback onBackPressedCallback = this.m;
            if (getBackStackEntryCount() <= 0 || !a(this.x)) {
                z = false;
            }
            onBackPressedCallback.setEnabled(z);
        }
    }

    public boolean a(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.getPrimaryNavigationFragment()) && a(fragmentManager.x);
    }

    public boolean b(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.isMenuVisible();
    }

    void a() {
        a(true);
        if (this.m.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.l.onBackPressed();
        }
    }

    public void popBackStack() {
        a((d) new e(null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        return a((String) null, -1, 0);
    }

    public void popBackStack(@Nullable String str, int i) {
        a((d) new e(str, -1, i), false);
    }

    public boolean popBackStackImmediate(@Nullable String str, int i) {
        return a(str, -1, i);
    }

    public void popBackStack(int i, int i2) {
        if (i >= 0) {
            a((d) new e(null, i, i2), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        if (i >= 0) {
            return a((String) null, i, i2);
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    private boolean a(@Nullable String str, int i, int i2) {
        a(false);
        d(true);
        Fragment fragment = this.d;
        if (fragment != null && i < 0 && str == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean a2 = a(this.K, this.L, str, i, i2);
        if (a2) {
            this.h = true;
            try {
                b(this.K, this.L);
            } finally {
                F();
            }
        }
        C();
        J();
        this.i.d();
        return a2;
    }

    public int getBackStackEntryCount() {
        ArrayList<a> arrayList = this.b;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @NonNull
    public BackStackEntry getBackStackEntryAt(int i) {
        return this.b.get(i);
    }

    public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        if (this.q == null) {
            this.q = new ArrayList<>();
        }
        this.q.add(onBackStackChangedListener);
    }

    public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.q;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    void a(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
        if (this.r.get(fragment) == null) {
            this.r.put(fragment, new HashSet<>());
        }
        this.r.get(fragment).add(cancellationSignal);
    }

    void b(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
        HashSet<CancellationSignal> hashSet = this.r.get(fragment);
        if (hashSet != null && hashSet.remove(cancellationSignal) && hashSet.isEmpty()) {
            this.r.remove(fragment);
            if (fragment.mState < 5) {
                t(fragment);
                f(fragment);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void setFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
        c cVar = this.p.get(str);
        if (cVar == null || !cVar.a(Lifecycle.State.STARTED)) {
            this.o.put(str, bundle);
        } else {
            cVar.onFragmentResult(str, bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResult(@NonNull String str) {
        this.o.remove(str);
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    @SuppressLint({"SyntheticAccessor"})
    public final void setFragmentResultListener(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final FragmentResultListener fragmentResultListener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.fragment.app.FragmentManager.6
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                    Bundle bundle;
                    if (event == Lifecycle.Event.ON_START && (bundle = (Bundle) FragmentManager.this.o.get(str)) != null) {
                        fragmentResultListener.onFragmentResult(str, bundle);
                        FragmentManager.this.clearFragmentResult(str);
                    }
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        lifecycle.removeObserver(this);
                        FragmentManager.this.p.remove(str);
                    }
                }
            };
            lifecycle.addObserver(lifecycleEventObserver);
            c put = this.p.put(str, new c(lifecycle, fragmentResultListener, lifecycleEventObserver));
            if (put != null) {
                put.a();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResultListener(@NonNull String str) {
        c remove = this.p.remove(str);
        if (remove != null) {
            remove.a();
        }
    }

    public void putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            a(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(str, fragment.mWho);
    }

    @Nullable
    public Fragment getFragment(@NonNull Bundle bundle, @NonNull String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return null;
        }
        Fragment b2 = b(string);
        if (b2 == null) {
            a(new IllegalStateException("Fragment no longer exists for key " + str + ": unique id " + string));
        }
        return b2;
    }

    @NonNull
    public static <F extends Fragment> F findFragment(@NonNull View view) {
        F f2 = (F) b(view);
        if (f2 != null) {
            return f2;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    @Nullable
    private static Fragment b(@NonNull View view) {
        while (view != null) {
            Fragment a2 = a(view);
            if (a2 != null) {
                return a2;
            }
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    @Nullable
    public static Fragment a(@NonNull View view) {
        Object tag = view.getTag(R.id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    public void a(@NonNull FragmentContainerView fragmentContainerView) {
        for (l lVar : this.i.g()) {
            Fragment a2 = lVar.a();
            if (a2.mContainerId == fragmentContainerView.getId() && a2.mView != null && a2.mView.getParent() == null) {
                a2.mContainer = fragmentContainerView;
                lVar.s();
            }
        }
    }

    @NonNull
    public List<Fragment> getFragments() {
        return this.i.h();
    }

    @NonNull
    public ViewModelStore c(@NonNull Fragment fragment) {
        return this.O.e(fragment);
    }

    @NonNull
    private j r(@NonNull Fragment fragment) {
        return this.O.d(fragment);
    }

    public void d(@NonNull Fragment fragment) {
        this.O.a(fragment);
    }

    public void e(@NonNull Fragment fragment) {
        this.O.c(fragment);
    }

    @NonNull
    public List<Fragment> b() {
        return this.i.i();
    }

    public int c() {
        return this.i.j();
    }

    @Nullable
    public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment) {
        l c2 = this.i.c(fragment.mWho);
        if (c2 == null || !c2.a().equals(fragment)) {
            a(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return c2.n();
    }

    public boolean isDestroyed() {
        return this.I;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.x;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.x)));
            sb.append("}");
        } else {
            FragmentHostCallback<?> fragmentHostCallback = this.v;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.v)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        int size;
        int size2;
        String str2 = str + "    ";
        this.i.a(str, fileDescriptor, printWriter, strArr);
        ArrayList<Fragment> arrayList = this.j;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i = 0; i < size2; i++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(this.j.get(i).toString());
            }
        }
        ArrayList<a> arrayList2 = this.b;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i2 = 0; i2 < size; i2++) {
                a aVar = this.b.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(aVar.toString());
                aVar.a(str2, printWriter);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.n.get());
        synchronized (this.g) {
            int size3 = this.g.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i3 = 0; i3 < size3; i3++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(this.g.get(i3));
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.v);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.w);
        if (this.x != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.x);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.c);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.G);
        printWriter.print(" mStopped=");
        printWriter.print(this.H);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.I);
        if (this.F) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.F);
        }
    }

    public void a(@NonNull l lVar) {
        Fragment a2 = lVar.a();
        if (!a2.mDeferStart) {
            return;
        }
        if (this.h) {
            this.J = true;
            return;
        }
        a2.mDeferStart = false;
        if (a) {
            lVar.c();
        } else {
            f(a2);
        }
    }

    public boolean b(int i) {
        return this.c >= i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x015a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void a(@androidx.annotation.NonNull androidx.fragment.app.Fragment r9, int r10) {
        /*
            Method dump skipped, instructions count: 440
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentManager.a(androidx.fragment.app.Fragment, int):void");
    }

    private void s(@NonNull Fragment fragment) {
        HashSet<CancellationSignal> hashSet = this.r.get(fragment);
        if (hashSet != null) {
            Iterator<CancellationSignal> it = hashSet.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            hashSet.clear();
            t(fragment);
            this.r.remove(fragment);
        }
    }

    public void a(@NonNull Fragment fragment, boolean z) {
        ViewGroup w = w(fragment);
        if (w != null && (w instanceof FragmentContainerView)) {
            ((FragmentContainerView) w).setDrawDisappearingViewsLast(!z);
        }
    }

    private void t(@NonNull Fragment fragment) {
        fragment.performDestroyView();
        this.t.g(fragment, false);
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        fragment.mInLayout = false;
    }

    public void f(@NonNull Fragment fragment) {
        a(fragment, this.c);
    }

    private void u(@NonNull final Fragment fragment) {
        if (fragment.mView != null) {
            d.a a2 = d.a(this.v.c(), fragment, !fragment.mHidden, fragment.getPopDirection());
            if (a2 == null || a2.b == null) {
                if (a2 != null) {
                    fragment.mView.startAnimation(a2.a);
                    a2.a.start();
                }
                fragment.mView.setVisibility((!fragment.mHidden || fragment.isHideReplaced()) ? 0 : 8);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            } else {
                a2.b.setTarget(fragment.mView);
                if (!fragment.mHidden) {
                    fragment.mView.setVisibility(0);
                } else if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                } else {
                    final ViewGroup viewGroup = fragment.mContainer;
                    final View view = fragment.mView;
                    viewGroup.startViewTransition(view);
                    a2.b.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.FragmentManager.9
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            viewGroup.endViewTransition(view);
                            animator.removeListener(this);
                            if (fragment.mView != null && fragment.mHidden) {
                                fragment.mView.setVisibility(8);
                            }
                        }
                    });
                }
                a2.b.start();
            }
        }
        q(fragment);
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    public void g(@NonNull Fragment fragment) {
        if (this.i.b(fragment.mWho)) {
            f(fragment);
            if (!(fragment.mView == null || !fragment.mIsNewlyAdded || fragment.mContainer == null)) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha);
                }
                fragment.mPostponedAlpha = 0.0f;
                fragment.mIsNewlyAdded = false;
                d.a a2 = d.a(this.v.c(), fragment, true, fragment.getPopDirection());
                if (a2 != null) {
                    if (a2.a != null) {
                        fragment.mView.startAnimation(a2.a);
                    } else {
                        a2.b.setTarget(fragment.mView);
                        a2.b.start();
                    }
                }
            }
            if (fragment.mHiddenChanged) {
                u(fragment);
            }
        } else if (a(3)) {
            Log.d("FragmentManager", "Ignoring moving " + fragment + " to state " + this.c + "since it is not added to " + this);
        }
    }

    public void a(int i, boolean z) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.v == null && i != -1) {
            throw new IllegalStateException("No activity");
        } else if (z || i != this.c) {
            this.c = i;
            if (a) {
                this.i.c();
            } else {
                for (Fragment fragment : this.i.h()) {
                    g(fragment);
                }
                for (l lVar : this.i.g()) {
                    Fragment a2 = lVar.a();
                    if (!a2.mIsNewlyAdded) {
                        g(a2);
                    }
                    if (a2.mRemoving && !a2.isInBackStack()) {
                        this.i.b(lVar);
                    }
                }
            }
            D();
            if (this.F && (fragmentHostCallback = this.v) != null && this.c == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.F = false;
            }
        }
    }

    private void D() {
        for (l lVar : this.i.g()) {
            a(lVar);
        }
    }

    @NonNull
    public l h(@NonNull Fragment fragment) {
        l c2 = this.i.c(fragment.mWho);
        if (c2 != null) {
            return c2;
        }
        l lVar = new l(this.t, this.i, fragment);
        lVar.a(this.v.c().getClassLoader());
        lVar.a(this.c);
        return lVar;
    }

    public l i(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        l h = h(fragment);
        fragment.mFragmentManager = this;
        this.i.a(h);
        if (!fragment.mDetached) {
            this.i.a(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (y(fragment)) {
                this.F = true;
            }
        }
        return h;
    }

    public void j(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            this.i.b(fragment);
            if (y(fragment)) {
                this.F = true;
            }
            fragment.mRemoving = true;
            v(fragment);
        }
    }

    public void k(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
            v(fragment);
        }
    }

    public void l(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    public void m(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (a(2)) {
                    Log.v("FragmentManager", "remove from detach: " + fragment);
                }
                this.i.b(fragment);
                if (y(fragment)) {
                    this.F = true;
                }
                v(fragment);
            }
        }
    }

    public void n(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                this.i.a(fragment);
                if (a(2)) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                if (y(fragment)) {
                    this.F = true;
                }
            }
        }
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int i) {
        return this.i.b(i);
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String str) {
        return this.i.a(str);
    }

    public Fragment a(@NonNull String str) {
        return this.i.d(str);
    }

    @Nullable
    public Fragment b(@NonNull String str) {
        return this.i.e(str);
    }

    private void E() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    public boolean isStateSaved() {
        return this.G || this.H;
    }

    public void a(@NonNull d dVar, boolean z) {
        if (!z) {
            if (this.v != null) {
                E();
            } else if (this.I) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
        }
        synchronized (this.g) {
            if (this.v != null) {
                this.g.add(dVar);
                d();
            } else if (!z) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    void d() {
        synchronized (this.g) {
            boolean z = false;
            boolean z2 = this.N != null && !this.N.isEmpty();
            if (this.g.size() == 1) {
                z = true;
            }
            if (z2 || z) {
                this.v.d().removeCallbacks(this.P);
                this.v.d().post(this.P);
                C();
            }
        }
    }

    public int e() {
        return this.n.getAndIncrement();
    }

    private void d(boolean z) {
        if (this.h) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.v == null) {
            if (this.I) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        } else if (Looper.myLooper() == this.v.d().getLooper()) {
            if (!z) {
                E();
            }
            if (this.K == null) {
                this.K = new ArrayList<>();
                this.L = new ArrayList<>();
            }
            this.h = true;
            try {
                a((ArrayList<a>) null, (ArrayList<Boolean>) null);
            } finally {
                this.h = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public void b(@NonNull d dVar, boolean z) {
        if (!z || (this.v != null && !this.I)) {
            d(z);
            if (dVar.a(this.K, this.L)) {
                this.h = true;
                try {
                    b(this.K, this.L);
                } finally {
                    F();
                }
            }
            C();
            J();
            this.i.d();
        }
    }

    private void F() {
        this.h = false;
        this.L.clear();
        this.K.clear();
    }

    /* JADX WARN: Finally extract failed */
    public boolean a(boolean z) {
        d(z);
        boolean z2 = false;
        while (c(this.K, this.L)) {
            this.h = true;
            try {
                b(this.K, this.L);
                F();
                z2 = true;
            } catch (Throwable th) {
                F();
                throw th;
            }
        }
        C();
        J();
        this.i.d();
        return z2;
    }

    private void a(@Nullable ArrayList<a> arrayList, @Nullable ArrayList<Boolean> arrayList2) {
        int indexOf;
        int indexOf2;
        ArrayList<f> arrayList3 = this.N;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i = 0;
        while (i < size) {
            f fVar = this.N.get(i);
            if (arrayList != null && !fVar.a && (indexOf2 = arrayList.indexOf(fVar.b)) != -1 && arrayList2 != null && arrayList2.get(indexOf2).booleanValue()) {
                this.N.remove(i);
                i--;
                size--;
                fVar.e();
            } else if (fVar.c() || (arrayList != null && fVar.b.a(arrayList, 0, arrayList.size()))) {
                this.N.remove(i);
                i--;
                size--;
                if (arrayList == null || fVar.a || (indexOf = arrayList.indexOf(fVar.b)) == -1 || arrayList2 == null || !arrayList2.get(indexOf).booleanValue()) {
                    fVar.d();
                } else {
                    fVar.e();
                }
            }
            i++;
        }
    }

    private void b(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (!arrayList.isEmpty()) {
            if (arrayList.size() == arrayList2.size()) {
                a(arrayList, arrayList2);
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i < size) {
                    if (!arrayList.get(i).s) {
                        if (i2 != i) {
                            a(arrayList, arrayList2, i2, i);
                        }
                        i2 = i + 1;
                        if (arrayList2.get(i).booleanValue()) {
                            while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).s) {
                                i2++;
                            }
                        }
                        a(arrayList, arrayList2, i, i2);
                        i = i2 - 1;
                    }
                    i++;
                }
                if (i2 != size) {
                    a(arrayList, arrayList2, i2, size);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v4 */
    private void a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        boolean z;
        ArrayList<Boolean> arrayList3;
        int i3;
        int i4;
        int i5;
        int i6;
        ArrayList<Boolean> arrayList4;
        boolean z2;
        int i7;
        boolean z3;
        boolean z4 = arrayList.get(i).s;
        ArrayList<Fragment> arrayList5 = this.M;
        if (arrayList5 == null) {
            this.M = new ArrayList<>();
        } else {
            arrayList5.clear();
        }
        this.M.addAll(this.i.h());
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z5 = false;
        int i8 = i;
        while (i8 < i2) {
            a aVar = arrayList.get(i8);
            if (!arrayList2.get(i8).booleanValue()) {
                primaryNavigationFragment = aVar.a(this.M, primaryNavigationFragment);
            } else {
                primaryNavigationFragment = aVar.b(this.M, primaryNavigationFragment);
            }
            i8++;
            z5 = z5 || aVar.j;
        }
        this.M.clear();
        if (z4 || this.c < 1) {
            z = true;
        } else if (a) {
            for (int i9 = i; i9 < i2; i9++) {
                Iterator it = arrayList.get(i9).d.iterator();
                while (it.hasNext()) {
                    Fragment fragment = ((FragmentTransaction.a) it.next()).b;
                    if (!(fragment == null || fragment.mFragmentManager == null)) {
                        this.i.a(h(fragment));
                    }
                }
            }
            z = true;
        } else {
            z = true;
            n.a(this.v.c(), this.w, arrayList, arrayList2, i, i2, false, this.s);
        }
        b(arrayList, arrayList2, i, i2);
        if (a) {
            boolean booleanValue = arrayList2.get(i2 - 1).booleanValue();
            for (int i10 = i; i10 < i2; i10++) {
                a aVar2 = arrayList.get(i10);
                if (booleanValue) {
                    int size = aVar2.d.size();
                    int i11 = z ? 1 : 0;
                    int i12 = z ? 1 : 0;
                    int i13 = z ? 1 : 0;
                    int i14 = z ? 1 : 0;
                    for (int i15 = size - i11; i15 >= 0; i15--) {
                        Fragment fragment2 = ((FragmentTransaction.a) aVar2.d.get(i15)).b;
                        if (fragment2 != null) {
                            h(fragment2).c();
                        }
                    }
                } else {
                    Iterator it2 = aVar2.d.iterator();
                    while (it2.hasNext()) {
                        Fragment fragment3 = ((FragmentTransaction.a) it2.next()).b;
                        if (fragment3 != null) {
                            h(fragment3).c();
                        }
                    }
                }
            }
            a(this.c, z);
            for (r rVar : a(arrayList, i, i2)) {
                rVar.a(booleanValue);
                rVar.b();
                rVar.d();
            }
            i3 = i2;
            arrayList3 = arrayList2;
        } else {
            if (z4) {
                ArraySet<Fragment> arraySet = new ArraySet<>();
                b(arraySet);
                i4 = z;
                z2 = z4;
                i5 = i2;
                i6 = i;
                arrayList4 = arrayList2;
                i7 = a(arrayList, arrayList2, i, i2, arraySet);
                a(arraySet);
            } else {
                int i16 = z ? 1 : 0;
                boolean z6 = z ? 1 : 0;
                boolean z7 = z ? 1 : 0;
                boolean z8 = z ? 1 : 0;
                i4 = i16;
                z2 = z4;
                i5 = i2;
                i6 = i;
                arrayList4 = arrayList2;
                i7 = i5;
            }
            if (i7 == i6 || !z2) {
                arrayList3 = arrayList4;
                i3 = i5;
            } else {
                if (this.c >= i4) {
                    arrayList3 = arrayList4;
                    i3 = i5;
                    z3 = i4;
                    n.a(this.v.c(), this.w, arrayList, arrayList2, i, i7, true, this.s);
                } else {
                    arrayList3 = arrayList4;
                    i3 = i5;
                    z3 = i4;
                }
                a(this.c, z3);
            }
        }
        for (int i17 = i; i17 < i3; i17++) {
            a aVar3 = arrayList.get(i17);
            if (arrayList3.get(i17).booleanValue() && aVar3.c >= 0) {
                aVar3.c = -1;
            }
            aVar3.a();
        }
        if (z5) {
            K();
        }
    }

    private Set<r> a(@NonNull ArrayList<a> arrayList, int i, int i2) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            Iterator it = arrayList.get(i).d.iterator();
            while (it.hasNext()) {
                Fragment fragment = ((FragmentTransaction.a) it.next()).b;
                if (!(fragment == null || (viewGroup = fragment.mContainer) == null)) {
                    hashSet.add(r.a(viewGroup, this));
                }
            }
            i++;
        }
        return hashSet;
    }

    private void a(@NonNull ArraySet<Fragment> arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            Fragment valueAt = arraySet.valueAt(i);
            if (!valueAt.mAdded) {
                View requireView = valueAt.requireView();
                valueAt.mPostponedAlpha = requireView.getAlpha();
                requireView.setAlpha(0.0f);
            }
        }
    }

    private int a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2, @NonNull ArraySet<Fragment> arraySet) {
        int i3 = i2;
        for (int i4 = i2 - 1; i4 >= i; i4--) {
            a aVar = arrayList.get(i4);
            boolean booleanValue = arrayList2.get(i4).booleanValue();
            if (aVar.c() && !aVar.a(arrayList, i4 + 1, i2)) {
                if (this.N == null) {
                    this.N = new ArrayList<>();
                }
                f fVar = new f(aVar, booleanValue);
                this.N.add(fVar);
                aVar.a(fVar);
                if (booleanValue) {
                    aVar.b();
                } else {
                    aVar.b(false);
                }
                i3--;
                if (i4 != i3) {
                    arrayList.remove(i4);
                    arrayList.add(i3, aVar);
                }
                b(arraySet);
            }
        }
        return i3;
    }

    void a(@NonNull a aVar, boolean z, boolean z2, boolean z3) {
        if (z) {
            aVar.b(z3);
        } else {
            aVar.b();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(aVar);
        arrayList2.add(Boolean.valueOf(z));
        if (z2 && this.c >= 1) {
            n.a(this.v.c(), this.w, arrayList, arrayList2, 0, 1, true, this.s);
        }
        if (z3) {
            a(this.c, true);
        }
        for (Fragment fragment : this.i.i()) {
            if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && aVar.b(fragment.mContainerId)) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha);
                }
                if (z3) {
                    fragment.mPostponedAlpha = 0.0f;
                } else {
                    fragment.mPostponedAlpha = -1.0f;
                    fragment.mIsNewlyAdded = false;
                }
            }
        }
    }

    private static void b(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            a aVar = arrayList.get(i);
            boolean z = true;
            if (arrayList2.get(i).booleanValue()) {
                aVar.a(-1);
                if (i != i2 - 1) {
                    z = false;
                }
                aVar.b(z);
            } else {
                aVar.a(1);
                aVar.b();
            }
            i++;
        }
    }

    private void v(@NonNull Fragment fragment) {
        ViewGroup w = w(fragment);
        if (w != null && fragment.getEnterAnim() + fragment.getExitAnim() + fragment.getPopEnterAnim() + fragment.getPopExitAnim() > 0) {
            if (w.getTag(R.id.visible_removing_fragment_view_tag) == null) {
                w.setTag(R.id.visible_removing_fragment_view_tag, fragment);
            }
            ((Fragment) w.getTag(R.id.visible_removing_fragment_view_tag)).setPopDirection(fragment.getPopDirection());
        }
    }

    private ViewGroup w(@NonNull Fragment fragment) {
        if (fragment.mContainer != null) {
            return fragment.mContainer;
        }
        if (fragment.mContainerId > 0 && this.w.onHasView()) {
            View onFindViewById = this.w.onFindViewById(fragment.mContainerId);
            if (onFindViewById instanceof ViewGroup) {
                return (ViewGroup) onFindViewById;
            }
        }
        return null;
    }

    private void b(@NonNull ArraySet<Fragment> arraySet) {
        int i = this.c;
        if (i >= 1) {
            int min = Math.min(i, 5);
            for (Fragment fragment : this.i.h()) {
                if (fragment.mState < min) {
                    a(fragment, min);
                    if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded) {
                        arraySet.add(fragment);
                    }
                }
            }
        }
    }

    private void G() {
        if (a) {
            for (r rVar : I()) {
                rVar.c();
            }
        } else if (this.N != null) {
            while (!this.N.isEmpty()) {
                this.N.remove(0).d();
            }
        }
    }

    private void H() {
        if (a) {
            for (r rVar : I()) {
                rVar.e();
            }
        } else if (!this.r.isEmpty()) {
            for (Fragment fragment : this.r.keySet()) {
                s(fragment);
                f(fragment);
            }
        }
    }

    private Set<r> I() {
        HashSet hashSet = new HashSet();
        for (l lVar : this.i.g()) {
            ViewGroup viewGroup = lVar.a().mContainer;
            if (viewGroup != null) {
                hashSet.add(r.a(viewGroup, y()));
            }
        }
        return hashSet;
    }

    private boolean c(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        synchronized (this.g) {
            if (this.g.isEmpty()) {
                return false;
            }
            int size = this.g.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                z |= this.g.get(i).a(arrayList, arrayList2);
            }
            this.g.clear();
            this.v.d().removeCallbacks(this.P);
            return z;
        }
    }

    private void J() {
        if (this.J) {
            this.J = false;
            D();
        }
    }

    private void K() {
        if (this.q != null) {
            for (int i = 0; i < this.q.size(); i++) {
                this.q.get(i).onBackStackChanged();
            }
        }
    }

    public void a(a aVar) {
        if (this.b == null) {
            this.b = new ArrayList<>();
        }
        this.b.add(aVar);
    }

    boolean a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2, @Nullable String str, int i, int i2) {
        int i3;
        ArrayList<a> arrayList3 = this.b;
        if (arrayList3 == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size = arrayList3.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.b.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i >= 0) {
                i3 = this.b.size() - 1;
                while (i3 >= 0) {
                    a aVar = this.b.get(i3);
                    if ((str != null && str.equals(aVar.getName())) || (i >= 0 && i == aVar.c)) {
                        break;
                    }
                    i3--;
                }
                if (i3 < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    i3--;
                    while (i3 >= 0) {
                        a aVar2 = this.b.get(i3);
                        if ((str == null || !str.equals(aVar2.getName())) && (i < 0 || i != aVar2.c)) {
                            break;
                        }
                        i3--;
                    }
                }
            } else {
                i3 = -1;
            }
            if (i3 == this.b.size() - 1) {
                return false;
            }
            for (int size2 = this.b.size() - 1; size2 > i3; size2--) {
                arrayList.add(this.b.remove(size2));
                arrayList2.add(true);
            }
        }
        return true;
    }

    @Deprecated
    public FragmentManagerNonConfig f() {
        if (this.v instanceof ViewModelStoreOwner) {
            a(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.O.c();
    }

    public Parcelable g() {
        int size;
        G();
        H();
        a(true);
        this.G = true;
        this.O.a(true);
        ArrayList<k> e2 = this.i.e();
        b[] bVarArr = null;
        if (e2.isEmpty()) {
            if (a(2)) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        ArrayList<String> f2 = this.i.f();
        ArrayList<a> arrayList = this.b;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            bVarArr = new b[size];
            for (int i = 0; i < size; i++) {
                bVarArr[i] = new b(this.b.get(i));
                if (a(2)) {
                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.b.get(i));
                }
            }
        }
        i iVar = new i();
        iVar.a = e2;
        iVar.b = f2;
        iVar.c = bVarArr;
        iVar.d = this.n.get();
        Fragment fragment = this.d;
        if (fragment != null) {
            iVar.e = fragment.mWho;
        }
        iVar.f.addAll(this.o.keySet());
        iVar.g.addAll(this.o.values());
        iVar.h = new ArrayList<>(this.e);
        return iVar;
    }

    public void a(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.v instanceof ViewModelStoreOwner) {
            a(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.O.a(fragmentManagerNonConfig);
        a(parcelable);
    }

    public void a(@Nullable Parcelable parcelable) {
        l lVar;
        if (parcelable != null) {
            i iVar = (i) parcelable;
            if (iVar.a != null) {
                this.i.b();
                Iterator<k> it = iVar.a.iterator();
                while (it.hasNext()) {
                    k next = it.next();
                    if (next != null) {
                        Fragment a2 = this.O.a(next.b);
                        if (a2 != null) {
                            if (a(2)) {
                                Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + a2);
                            }
                            lVar = new l(this.t, this.i, a2, next);
                        } else {
                            lVar = new l(this.t, this.i, this.v.c().getClassLoader(), getFragmentFactory(), next);
                        }
                        Fragment a3 = lVar.a();
                        a3.mFragmentManager = this;
                        if (a(2)) {
                            Log.v("FragmentManager", "restoreSaveState: active (" + a3.mWho + "): " + a3);
                        }
                        lVar.a(this.v.c().getClassLoader());
                        this.i.a(lVar);
                        lVar.a(this.c);
                    }
                }
                for (Fragment fragment : this.O.b()) {
                    if (!this.i.b(fragment.mWho)) {
                        if (a(2)) {
                            Log.v("FragmentManager", "Discarding retained Fragment " + fragment + " that was not found in the set of active Fragments " + iVar.a);
                        }
                        this.O.c(fragment);
                        fragment.mFragmentManager = this;
                        l lVar2 = new l(this.t, this.i, fragment);
                        lVar2.a(1);
                        lVar2.c();
                        fragment.mRemoving = true;
                        lVar2.c();
                    }
                }
                this.i.a(iVar.b);
                if (iVar.c != null) {
                    this.b = new ArrayList<>(iVar.c.length);
                    for (int i = 0; i < iVar.c.length; i++) {
                        a a4 = iVar.c[i].a(this);
                        if (a(2)) {
                            Log.v("FragmentManager", "restoreAllState: back stack #" + i + " (index " + a4.c + "): " + a4);
                            PrintWriter printWriter = new PrintWriter(new q("FragmentManager"));
                            a4.a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.b.add(a4);
                    }
                } else {
                    this.b = null;
                }
                this.n.set(iVar.d);
                if (iVar.e != null) {
                    this.d = b(iVar.e);
                    x(this.d);
                }
                ArrayList<String> arrayList = iVar.f;
                if (arrayList != null) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        Bundle bundle = iVar.g.get(i2);
                        bundle.setClassLoader(this.v.c().getClassLoader());
                        this.o.put(arrayList.get(i2), bundle);
                    }
                }
                this.e = new ArrayDeque<>(iVar.h);
            }
        }
    }

    @NonNull
    public FragmentHostCallback<?> h() {
        return this.v;
    }

    @Nullable
    public Fragment i() {
        return this.x;
    }

    @NonNull
    public FragmentContainer j() {
        return this.w;
    }

    @NonNull
    public m k() {
        return this.i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint({"SyntheticAccessor"})
    public void a(@NonNull FragmentHostCallback<?> fragmentHostCallback, @NonNull FragmentContainer fragmentContainer, @Nullable final Fragment fragment) {
        String str;
        if (this.v == null) {
            this.v = fragmentHostCallback;
            this.w = fragmentContainer;
            this.x = fragment;
            if (this.x != null) {
                addFragmentOnAttachListener(new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.10
                    @Override // androidx.fragment.app.FragmentOnAttachListener
                    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2) {
                        fragment.onAttachFragment(fragment2);
                    }
                });
            } else if (fragmentHostCallback instanceof FragmentOnAttachListener) {
                addFragmentOnAttachListener((FragmentOnAttachListener) fragmentHostCallback);
            }
            if (this.x != null) {
                C();
            }
            if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
                OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) fragmentHostCallback;
                this.l = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
                Fragment fragment2 = onBackPressedDispatcherOwner;
                if (fragment != null) {
                    fragment2 = fragment;
                }
                this.l.addCallback(fragment2, this.m);
            }
            if (fragment != null) {
                this.O = fragment.mFragmentManager.r(fragment);
            } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
                this.O = j.a(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore());
            } else {
                this.O = new j(false);
            }
            this.O.a(isStateSaved());
            this.i.a(this.O);
            FragmentHostCallback<?> fragmentHostCallback2 = this.v;
            if (fragmentHostCallback2 instanceof ActivityResultRegistryOwner) {
                ActivityResultRegistry activityResultRegistry = ((ActivityResultRegistryOwner) fragmentHostCallback2).getActivityResultRegistry();
                if (fragment != null) {
                    str = fragment.mWho + Constants.COLON_SEPARATOR;
                } else {
                    str = "";
                }
                String str2 = "FragmentManager:" + str;
                this.C = activityResultRegistry.register(str2 + "StartActivityForResult", new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.11
                    /* renamed from: a */
                    public void onActivityResult(ActivityResult activityResult) {
                        b pollFirst = FragmentManager.this.e.pollFirst();
                        if (pollFirst == null) {
                            Log.w("FragmentManager", "No Activities were started for result for " + this);
                            return;
                        }
                        String str3 = pollFirst.a;
                        int i = pollFirst.b;
                        Fragment d2 = FragmentManager.this.i.d(str3);
                        if (d2 == null) {
                            Log.w("FragmentManager", "Activity result delivered for unknown Fragment " + str3);
                            return;
                        }
                        d2.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                    }
                });
                this.D = activityResultRegistry.register(str2 + "StartIntentSenderForResult", new a(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.2
                    /* renamed from: a */
                    public void onActivityResult(ActivityResult activityResult) {
                        b pollFirst = FragmentManager.this.e.pollFirst();
                        if (pollFirst == null) {
                            Log.w("FragmentManager", "No IntentSenders were started for " + this);
                            return;
                        }
                        String str3 = pollFirst.a;
                        int i = pollFirst.b;
                        Fragment d2 = FragmentManager.this.i.d(str3);
                        if (d2 == null) {
                            Log.w("FragmentManager", "Intent Sender result delivered for unknown Fragment " + str3);
                            return;
                        }
                        d2.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                    }
                });
                this.E = activityResultRegistry.register(str2 + "RequestPermissions", new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: androidx.fragment.app.FragmentManager.3
                    @SuppressLint({"SyntheticAccessor"})
                    /* renamed from: a */
                    public void onActivityResult(Map<String, Boolean> map) {
                        String[] strArr = (String[]) map.keySet().toArray(new String[0]);
                        ArrayList arrayList = new ArrayList(map.values());
                        int[] iArr = new int[arrayList.size()];
                        for (int i = 0; i < arrayList.size(); i++) {
                            iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                        }
                        b pollFirst = FragmentManager.this.e.pollFirst();
                        if (pollFirst == null) {
                            Log.w("FragmentManager", "No permissions were requested for " + this);
                            return;
                        }
                        String str3 = pollFirst.a;
                        int i2 = pollFirst.b;
                        Fragment d2 = FragmentManager.this.i.d(str3);
                        if (d2 == null) {
                            Log.w("FragmentManager", "Permission request result delivered for unknown Fragment " + str3);
                            return;
                        }
                        d2.onRequestPermissionsResult(i2, strArr, iArr);
                    }
                });
                return;
            }
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void l() {
        if (this.v != null) {
            this.G = false;
            this.H = false;
            this.O.a(false);
            for (Fragment fragment : this.i.h()) {
                if (fragment != null) {
                    fragment.noteStateNotSaved();
                }
            }
        }
    }

    public void a(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i, @Nullable Bundle bundle) {
        if (this.C != null) {
            this.e.addLast(new b(fragment.mWho, i));
            if (!(intent == null || bundle == null)) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            }
            this.C.launch(intent);
            return;
        }
        this.v.onStartActivityFromFragment(fragment, intent, i, bundle);
    }

    public void a(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        Intent intent2;
        if (this.D != null) {
            if (bundle != null) {
                if (intent == null) {
                    intent2 = new Intent();
                    intent2.putExtra("androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE", true);
                } else {
                    intent2 = intent;
                }
                if (a(2)) {
                    Log.v("FragmentManager", "ActivityOptions " + bundle + " were added to fillInIntent " + intent2 + " for fragment " + fragment);
                }
                intent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
            } else {
                intent2 = intent;
            }
            IntentSenderRequest build = new IntentSenderRequest.Builder(intentSender).setFillInIntent(intent2).setFlags(i3, i2).build();
            this.e.addLast(new b(fragment.mWho, i));
            if (a(2)) {
                Log.v("FragmentManager", "Fragment " + fragment + "is launching an IntentSender for result ");
            }
            this.D.launch(build);
            return;
        }
        this.v.onStartIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public void a(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        if (this.E != null) {
            this.e.addLast(new b(fragment.mWho, i));
            this.E.launch(strArr);
            return;
        }
        this.v.onRequestPermissionsFromFragment(fragment, strArr, i);
    }

    public void m() {
        this.G = false;
        this.H = false;
        this.O.a(false);
        d(0);
    }

    public void n() {
        this.G = false;
        this.H = false;
        this.O.a(false);
        d(1);
    }

    public void o() {
        d(2);
    }

    public void p() {
        this.G = false;
        this.H = false;
        this.O.a(false);
        d(4);
    }

    public void q() {
        this.G = false;
        this.H = false;
        this.O.a(false);
        d(5);
    }

    public void r() {
        this.G = false;
        this.H = false;
        this.O.a(false);
        d(7);
    }

    public void s() {
        d(5);
    }

    public void t() {
        this.H = true;
        this.O.a(true);
        d(4);
    }

    public void u() {
        d(1);
    }

    public void v() {
        this.I = true;
        a(true);
        H();
        d(-1);
        this.v = null;
        this.w = null;
        this.x = null;
        if (this.l != null) {
            this.m.remove();
            this.l = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.C;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.D.unregister();
            this.E.unregister();
        }
    }

    /* JADX WARN: Finally extract failed */
    private void d(int i) {
        try {
            this.h = true;
            this.i.a(i);
            a(i, false);
            if (a) {
                for (r rVar : I()) {
                    rVar.e();
                }
            }
            this.h = false;
            a(true);
        } catch (Throwable th) {
            this.h = false;
            throw th;
        }
    }

    public void b(boolean z) {
        for (Fragment fragment : this.i.h()) {
            if (fragment != null) {
                fragment.performMultiWindowModeChanged(z);
            }
        }
    }

    public void c(boolean z) {
        for (Fragment fragment : this.i.h()) {
            if (fragment != null) {
                fragment.performPictureInPictureModeChanged(z);
            }
        }
    }

    public void a(@NonNull Configuration configuration) {
        for (Fragment fragment : this.i.h()) {
            if (fragment != null) {
                fragment.performConfigurationChanged(configuration);
            }
        }
    }

    public void w() {
        for (Fragment fragment : this.i.h()) {
            if (fragment != null) {
                fragment.performLowMemory();
            }
        }
    }

    public boolean a(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        if (this.c < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z = false;
        for (Fragment fragment : this.i.h()) {
            if (fragment != null && b(fragment) && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z = true;
            }
        }
        if (this.j != null) {
            for (int i = 0; i < this.j.size(); i++) {
                Fragment fragment2 = this.j.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.j = arrayList;
        return z;
    }

    public boolean a(@NonNull Menu menu) {
        boolean z = false;
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.i.h()) {
            if (fragment != null && b(fragment) && fragment.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public boolean a(@NonNull MenuItem menuItem) {
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.i.h()) {
            if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public boolean b(@NonNull MenuItem menuItem) {
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.i.h()) {
            if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void b(@NonNull Menu menu) {
        if (this.c >= 1) {
            for (Fragment fragment : this.i.h()) {
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public void o(@Nullable Fragment fragment) {
        if (fragment == null || (fragment.equals(b(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this))) {
            Fragment fragment2 = this.d;
            this.d = fragment;
            x(fragment2);
            x(this.d);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    private void x(@Nullable Fragment fragment) {
        if (fragment != null && fragment.equals(b(fragment.mWho))) {
            fragment.performPrimaryNavigationFragmentChanged();
        }
    }

    public void x() {
        C();
        x(this.d);
    }

    @Nullable
    public Fragment getPrimaryNavigationFragment() {
        return this.d;
    }

    public void a(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        if (!fragment.equals(b(fragment.mWho)) || !(fragment.mHost == null || fragment.mFragmentManager == this)) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        fragment.mMaxState = state;
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.y = fragmentFactory;
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.y;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.x;
        if (fragment != null) {
            return fragment.mFragmentManager.getFragmentFactory();
        }
        return this.z;
    }

    @NonNull
    public s y() {
        s sVar = this.A;
        if (sVar != null) {
            return sVar;
        }
        Fragment fragment = this.x;
        if (fragment != null) {
            return fragment.mFragmentManager.y();
        }
        return this.B;
    }

    @NonNull
    public g z() {
        return this.t;
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.t.a(fragmentLifecycleCallbacks, z);
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        this.t.a(fragmentLifecycleCallbacks);
    }

    public void addFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.u.add(fragmentOnAttachListener);
    }

    public void p(@NonNull Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.u.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    public void removeFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.u.remove(fragmentOnAttachListener);
    }

    boolean A() {
        boolean z = false;
        for (Fragment fragment : this.i.i()) {
            if (fragment != null) {
                z = y(fragment);
                continue;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    private boolean y(@NonNull Fragment fragment) {
        return (fragment.mHasMenu && fragment.mMenuVisible) || fragment.mChildFragmentManager.A();
    }

    public void q(@NonNull Fragment fragment) {
        if (fragment.mAdded && y(fragment)) {
            this.F = true;
        }
    }

    @NonNull
    public LayoutInflater.Factory2 B() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class e implements d {
        final String a;
        final int b;
        final int c;

        e(String str, @Nullable int i, int i2) {
            FragmentManager.this = r1;
            this.a = str;
            this.b = i;
            this.c = i2;
        }

        @Override // androidx.fragment.app.FragmentManager.d
        public boolean a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            if (FragmentManager.this.d == null || this.b >= 0 || this.a != null || !FragmentManager.this.d.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.a(arrayList, arrayList2, this.a, this.b, this.c);
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class f implements Fragment.c {
        final boolean a;
        final a b;
        private int c;

        f(@NonNull a aVar, boolean z) {
            this.a = z;
            this.b = aVar;
        }

        @Override // androidx.fragment.app.Fragment.c
        public void a() {
            this.c--;
            if (this.c == 0) {
                this.b.a.d();
            }
        }

        @Override // androidx.fragment.app.Fragment.c
        public void b() {
            this.c++;
        }

        public boolean c() {
            return this.c == 0;
        }

        void d() {
            boolean z = this.c > 0;
            for (Fragment fragment : this.b.a.getFragments()) {
                fragment.setOnStartEnterTransitionListener(null);
                if (z && fragment.isPostponed()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.b.a.a(this.b, this.a, !z, true);
        }

        void e() {
            this.b.a.a(this.b, this.a, false, false);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static class b implements Parcelable {
        public static final Parcelable.Creator<b> CREATOR = new Parcelable.Creator<b>() { // from class: androidx.fragment.app.FragmentManager.b.1
            /* renamed from: a */
            public b createFromParcel(Parcel parcel) {
                return new b(parcel);
            }

            /* renamed from: a */
            public b[] newArray(int i) {
                return new b[i];
            }
        };
        String a;
        int b;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        b(@NonNull String str, int i) {
            this.a = str;
            this.b = i;
        }

        b(@NonNull Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeInt(this.b);
        }
    }

    /* loaded from: classes.dex */
    public static class a extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        a() {
        }

        @NonNull
        /* renamed from: a */
        public Intent createIntent(@NonNull Context context, IntentSenderRequest intentSenderRequest) {
            Bundle bundleExtra;
            Intent intent = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = intentSenderRequest.getFillInIntent();
            if (!(fillInIntent == null || (bundleExtra = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) == null)) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundleExtra);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra("androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE", false)) {
                    intentSenderRequest = new IntentSenderRequest.Builder(intentSenderRequest.getIntentSender()).setFillInIntent(null).setFlags(intentSenderRequest.getFlagsValues(), intentSenderRequest.getFlagsMask()).build();
                }
            }
            intent.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, intentSenderRequest);
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "CreateIntent created the following intent: " + intent);
            }
            return intent;
        }

        @NonNull
        /* renamed from: a */
        public ActivityResult parseResult(int i, @Nullable Intent intent) {
            return new ActivityResult(i, intent);
        }
    }
}
