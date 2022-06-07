package androidx.fragment.app;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.io.PrintWriter;
import java.util.ArrayList;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.lang3.StringUtils;

/* compiled from: BackStackRecord.java */
/* loaded from: classes.dex */
public final class a extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManager.d {
    final FragmentManager a;
    boolean b;
    int c;

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.c >= 0) {
            sb.append(" #");
            sb.append(this.c);
        }
        if (this.l != null) {
            sb.append(StringUtils.SPACE);
            sb.append(this.l);
        }
        sb.append("}");
        return sb.toString();
    }

    public void a(String str, PrintWriter printWriter) {
        a(str, printWriter, true);
    }

    public void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.l);
            printWriter.print(" mIndex=");
            printWriter.print(this.c);
            printWriter.print(" mCommitted=");
            printWriter.println(this.b);
            if (this.i != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.i));
            }
            if (!(this.e == 0 && this.f == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.e));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.f));
            }
            if (!(this.g == 0 && this.h == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.g));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.h));
            }
            if (!(this.m == 0 && this.n == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.m));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.n);
            }
            if (!(this.o == 0 && this.p == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.o));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.p);
            }
        }
        if (!this.d.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            int size = this.d.size();
            for (int i = 0; i < size; i++) {
                FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i);
                switch (aVar.a) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = DiskLruCache.REMOVE;
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    case 10:
                        str2 = "OP_SET_MAX_LIFECYCLE";
                        break;
                    default:
                        str2 = "cmd=" + aVar.a;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(StringUtils.SPACE);
                printWriter.println(aVar.b);
                if (z) {
                    if (!(aVar.c == 0 && aVar.d == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.c));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.d));
                    }
                    if (aVar.e != 0 || aVar.f != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.e));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.f));
                    }
                }
            }
        }
    }

    public a(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager.getFragmentFactory(), fragmentManager.h() != null ? fragmentManager.h().c().getClassLoader() : null);
        this.c = -1;
        this.a = fragmentManager;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getId() {
        return this.c;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getBreadCrumbTitleRes() {
        return this.m;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getBreadCrumbShortTitleRes() {
        return this.o;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public CharSequence getBreadCrumbTitle() {
        if (this.m != 0) {
            return this.a.h().c().getText(this.m);
        }
        return this.n;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public CharSequence getBreadCrumbShortTitle() {
        if (this.o != 0) {
            return this.a.h().c().getText(this.o);
        }
        return this.p;
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public void a(int i, Fragment fragment, @Nullable String str, int i2) {
        super.a(i, fragment, str, i2);
        fragment.mFragmentManager = this.a;
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        if (fragment.mFragmentManager == null || fragment.mFragmentManager == this.a) {
            return super.remove(fragment);
        }
        throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        if (fragment.mFragmentManager == null || fragment.mFragmentManager == this.a) {
            return super.hide(fragment);
        }
        throw new IllegalStateException("Cannot hide Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        if (fragment.mFragmentManager == null || fragment.mFragmentManager == this.a) {
            return super.show(fragment);
        }
        throw new IllegalStateException("Cannot show Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        if (fragment.mFragmentManager == null || fragment.mFragmentManager == this.a) {
            return super.detach(fragment);
        }
        throw new IllegalStateException("Cannot detach Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        if (fragment == null || fragment.mFragmentManager == null || fragment.mFragmentManager == this.a) {
            return super.setPrimaryNavigationFragment(fragment);
        }
        throw new IllegalStateException("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        if (fragment.mFragmentManager != this.a) {
            throw new IllegalArgumentException("Cannot setMaxLifecycle for Fragment not attached to FragmentManager " + this.a);
        } else if (state == Lifecycle.State.INITIALIZED && fragment.mState > -1) {
            throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + state + " after the Fragment has been created");
        } else if (state != Lifecycle.State.DESTROYED) {
            return super.setMaxLifecycle(fragment, state);
        } else {
            throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + state + ". Use remove() to remove the fragment from the FragmentManager and trigger its destruction.");
        }
    }

    public void a(int i) {
        if (this.j) {
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i);
            }
            int size = this.d.size();
            for (int i2 = 0; i2 < size; i2++) {
                FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i2);
                if (aVar.b != null) {
                    aVar.b.mBackStackNesting += i;
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "Bump nesting of " + aVar.b + " to " + aVar.b.mBackStackNesting);
                    }
                }
            }
        }
    }

    public void a() {
        if (this.t != null) {
            for (int i = 0; i < this.t.size(); i++) {
                ((Runnable) this.t.get(i)).run();
            }
            this.t = null;
        }
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public int commit() {
        return a(false);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public int commitAllowingStateLoss() {
        return a(true);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public void commitNow() {
        disallowAddToBackStack();
        this.a.b((FragmentManager.d) this, false);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public void commitNowAllowingStateLoss() {
        disallowAddToBackStack();
        this.a.b((FragmentManager.d) this, true);
    }

    int a(boolean z) {
        if (!this.b) {
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Commit: " + this);
                PrintWriter printWriter = new PrintWriter(new q("FragmentManager"));
                a("  ", printWriter);
                printWriter.close();
            }
            this.b = true;
            if (this.j) {
                this.c = this.a.e();
            } else {
                this.c = -1;
            }
            this.a.a(this, z);
            return this.c;
        }
        throw new IllegalStateException("commit already called");
    }

    @Override // androidx.fragment.app.FragmentManager.d
    public boolean a(@NonNull ArrayList<a> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.j) {
            return true;
        }
        this.a.a(this);
        return true;
    }

    public boolean b(int i) {
        int size = this.d.size();
        for (int i2 = 0; i2 < size; i2++) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i2);
            int i3 = aVar.b != null ? aVar.b.mContainerId : 0;
            if (i3 != 0 && i3 == i) {
                return true;
            }
        }
        return false;
    }

    public boolean a(ArrayList<a> arrayList, int i, int i2) {
        int i3;
        if (i2 == i) {
            return false;
        }
        int size = this.d.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i5);
            int i6 = aVar.b != null ? aVar.b.mContainerId : 0;
            if (!(i6 == 0 || i6 == i4)) {
                for (int i7 = i; i7 < i2; i7++) {
                    a aVar2 = arrayList.get(i7);
                    int size2 = aVar2.d.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        FragmentTransaction.a aVar3 = (FragmentTransaction.a) aVar2.d.get(i8);
                        if (aVar3.b != null) {
                            i3 = aVar3.b.mContainerId;
                        } else {
                            i3 = 0;
                        }
                        if (i3 == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    public void b() {
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i);
            Fragment fragment = aVar.b;
            if (fragment != null) {
                fragment.setPopDirection(false);
                fragment.setNextTransition(this.i);
                fragment.setSharedElementNames(this.q, this.r);
            }
            int i2 = aVar.a;
            if (i2 != 1) {
                switch (i2) {
                    case 3:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.j(fragment);
                        break;
                    case 4:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.k(fragment);
                        break;
                    case 5:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.a(fragment, false);
                        this.a.l(fragment);
                        break;
                    case 6:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.m(fragment);
                        break;
                    case 7:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.a(fragment, false);
                        this.a.n(fragment);
                        break;
                    case 8:
                        this.a.o(fragment);
                        break;
                    case 9:
                        this.a.o(null);
                        break;
                    case 10:
                        this.a.a(fragment, aVar.h);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + aVar.a);
                }
            } else {
                fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                this.a.a(fragment, false);
                this.a.i(fragment);
            }
            if (!this.s && aVar.a != 1 && fragment != null && !FragmentManager.a) {
                this.a.g(fragment);
            }
        }
        if (!(this.s || FragmentManager.a)) {
            FragmentManager fragmentManager = this.a;
            fragmentManager.a(fragmentManager.c, true);
        }
    }

    public void b(boolean z) {
        for (int size = this.d.size() - 1; size >= 0; size--) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(size);
            Fragment fragment = aVar.b;
            if (fragment != null) {
                fragment.setPopDirection(true);
                fragment.setNextTransition(FragmentManager.c(this.i));
                fragment.setSharedElementNames(this.r, this.q);
            }
            int i = aVar.a;
            if (i != 1) {
                switch (i) {
                    case 3:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.i(fragment);
                        break;
                    case 4:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.l(fragment);
                        break;
                    case 5:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.a(fragment, true);
                        this.a.k(fragment);
                        break;
                    case 6:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.n(fragment);
                        break;
                    case 7:
                        fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                        this.a.a(fragment, true);
                        this.a.m(fragment);
                        break;
                    case 8:
                        this.a.o(null);
                        break;
                    case 9:
                        this.a.o(fragment);
                        break;
                    case 10:
                        this.a.a(fragment, aVar.g);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + aVar.a);
                }
            } else {
                fragment.setAnimations(aVar.c, aVar.d, aVar.e, aVar.f);
                this.a.a(fragment, true);
                this.a.j(fragment);
            }
            if (!this.s && aVar.a != 3 && fragment != null && !FragmentManager.a) {
                this.a.g(fragment);
            }
        }
        if (!(this.s || !z || FragmentManager.a)) {
            FragmentManager fragmentManager = this.a;
            fragmentManager.a(fragmentManager.c, true);
        }
    }

    public Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        Fragment fragment2 = fragment;
        int i = 0;
        while (i < this.d.size()) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i);
            switch (aVar.a) {
                case 1:
                case 7:
                    arrayList.add(aVar.b);
                    break;
                case 2:
                    Fragment fragment3 = aVar.b;
                    int i2 = fragment3.mContainerId;
                    Fragment fragment4 = fragment2;
                    int i3 = i;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        Fragment fragment5 = arrayList.get(size);
                        if (fragment5.mContainerId == i2) {
                            if (fragment5 == fragment3) {
                                z = true;
                            } else {
                                if (fragment5 == fragment4) {
                                    this.d.add(i3, new FragmentTransaction.a(9, fragment5));
                                    i3++;
                                    fragment4 = null;
                                }
                                FragmentTransaction.a aVar2 = new FragmentTransaction.a(3, fragment5);
                                aVar2.c = aVar.c;
                                aVar2.e = aVar.e;
                                aVar2.d = aVar.d;
                                aVar2.f = aVar.f;
                                this.d.add(i3, aVar2);
                                arrayList.remove(fragment5);
                                i3++;
                            }
                        }
                    }
                    if (z) {
                        this.d.remove(i3);
                        i = i3 - 1;
                    } else {
                        aVar.a = 1;
                        arrayList.add(fragment3);
                        i = i3;
                    }
                    fragment2 = fragment4;
                    break;
                case 3:
                case 6:
                    arrayList.remove(aVar.b);
                    if (aVar.b == fragment2) {
                        this.d.add(i, new FragmentTransaction.a(9, aVar.b));
                        i++;
                        fragment2 = null;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    this.d.add(i, new FragmentTransaction.a(9, fragment2));
                    i++;
                    fragment2 = aVar.b;
                    break;
            }
            i++;
        }
        return fragment2;
    }

    public Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        for (int size = this.d.size() - 1; size >= 0; size--) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(size);
            int i = aVar.a;
            if (i != 1) {
                if (i != 3) {
                    switch (i) {
                        case 8:
                            fragment = null;
                            break;
                        case 9:
                            fragment = aVar.b;
                            break;
                        case 10:
                            aVar.h = aVar.g;
                            break;
                    }
                }
                arrayList.add(aVar.b);
            }
            arrayList.remove(aVar.b);
        }
        return fragment;
    }

    public boolean c() {
        for (int i = 0; i < this.d.size(); i++) {
            if (b((FragmentTransaction.a) this.d.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void a(Fragment.c cVar) {
        for (int i = 0; i < this.d.size(); i++) {
            FragmentTransaction.a aVar = (FragmentTransaction.a) this.d.get(i);
            if (b(aVar)) {
                aVar.b.setOnStartEnterTransitionListener(cVar);
            }
        }
    }

    private static boolean b(FragmentTransaction.a aVar) {
        Fragment fragment = aVar.b;
        return fragment != null && fragment.mAdded && fragment.mView != null && !fragment.mDetached && !fragment.mHidden && fragment.isPostponed();
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public String getName() {
        return this.l;
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public boolean isEmpty() {
        return this.d.isEmpty();
    }
}
