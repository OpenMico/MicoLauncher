package androidx.fragment.app;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentStore.java */
/* loaded from: classes.dex */
public class m {
    private final ArrayList<Fragment> a = new ArrayList<>();
    private final HashMap<String, l> b = new HashMap<>();
    private j c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull j jVar) {
        this.c = jVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j a() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.b.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable List<String> list) {
        this.a.clear();
        if (list != null) {
            for (String str : list) {
                Fragment e = e(str);
                if (e != null) {
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "restoreSaveState: added (" + str + "): " + e);
                    }
                    a(e);
                } else {
                    throw new IllegalStateException("No instantiated fragment for (" + str + ")");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull l lVar) {
        Fragment a = lVar.a();
        if (!b(a.mWho)) {
            this.b.put(a.mWho, lVar);
            if (a.mRetainInstanceChangedWhileDetached) {
                if (a.mRetainInstance) {
                    this.c.a(a);
                } else {
                    this.c.c(a);
                }
                a.mRetainInstanceChangedWhileDetached = false;
            }
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Added fragment to active set " + a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Fragment fragment) {
        if (!this.a.contains(fragment)) {
            synchronized (this.a) {
                this.a.add(fragment);
            }
            fragment.mAdded = true;
            return;
        }
        throw new IllegalStateException("Fragment already added: " + fragment);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                lVar.a(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        Iterator<Fragment> it = this.a.iterator();
        while (it.hasNext()) {
            l lVar = this.b.get(it.next().mWho);
            if (lVar != null) {
                lVar.c();
            }
        }
        for (l lVar2 : this.b.values()) {
            if (lVar2 != null) {
                lVar2.c();
                Fragment a = lVar2.a();
                if (a.mRemoving && !a.isInBackStack()) {
                    b(lVar2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Fragment fragment) {
        synchronized (this.a) {
            this.a.remove(fragment);
        }
        fragment.mAdded = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull l lVar) {
        Fragment a = lVar.a();
        if (a.mRetainInstance) {
            this.c.c(a);
        }
        if (this.b.put(a.mWho, null) != null && FragmentManager.a(2)) {
            Log.v("FragmentManager", "Removed fragment from active set " + a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        this.b.values().removeAll(Collections.singleton(null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public ArrayList<k> e() {
        ArrayList<k> arrayList = new ArrayList<>(this.b.size());
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                Fragment a = lVar.a();
                k m = lVar.m();
                arrayList.add(m);
                if (FragmentManager.a(2)) {
                    Log.v("FragmentManager", "Saved state of " + a + ": " + m.m);
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ArrayList<String> f() {
        synchronized (this.a) {
            if (this.a.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<>(this.a.size());
            Iterator<Fragment> it = this.a.iterator();
            while (it.hasNext()) {
                Fragment next = it.next();
                arrayList.add(next.mWho);
                if (FragmentManager.a(2)) {
                    Log.v("FragmentManager", "saveAllState: adding fragment (" + next.mWho + "): " + next);
                }
            }
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public List<l> g() {
        ArrayList arrayList = new ArrayList();
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                arrayList.add(lVar);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public List<Fragment> h() {
        ArrayList arrayList;
        if (this.a.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.a) {
            arrayList = new ArrayList(this.a);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public List<Fragment> i() {
        ArrayList arrayList = new ArrayList();
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                arrayList.add(lVar.a());
            } else {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int j() {
        return this.b.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment b(@IdRes int i) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            Fragment fragment = this.a.get(size);
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment;
            }
        }
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                Fragment a = lVar.a();
                if (a.mFragmentId == i) {
                    return a;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment a(@Nullable String str) {
        if (str != null) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                Fragment fragment = this.a.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (str == null) {
            return null;
        }
        for (l lVar : this.b.values()) {
            if (lVar != null) {
                Fragment a = lVar.a();
                if (str.equals(a.mTag)) {
                    return a;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(@NonNull String str) {
        return this.b.get(str) != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public l c(@NonNull String str) {
        return this.b.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment d(@NonNull String str) {
        Fragment findFragmentByWho;
        for (l lVar : this.b.values()) {
            if (!(lVar == null || (findFragmentByWho = lVar.a().findFragmentByWho(str)) == null)) {
                return findFragmentByWho;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment e(@NonNull String str) {
        l lVar = this.b.get(str);
        if (lVar != null) {
            return lVar.a();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c(@NonNull Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup == null) {
            return -1;
        }
        int indexOf = this.a.indexOf(fragment);
        for (int i = indexOf - 1; i >= 0; i--) {
            Fragment fragment2 = this.a.get(i);
            if (fragment2.mContainer == viewGroup && fragment2.mView != null) {
                return viewGroup.indexOfChild(fragment2.mView) + 1;
            }
        }
        while (true) {
            indexOf++;
            if (indexOf >= this.a.size()) {
                return -1;
            }
            Fragment fragment3 = this.a.get(indexOf);
            if (fragment3.mContainer == viewGroup && fragment3.mView != null) {
                return viewGroup.indexOfChild(fragment3.mView);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        String str2 = str + "    ";
        if (!this.b.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (l lVar : this.b.values()) {
                printWriter.print(str);
                if (lVar != null) {
                    Fragment a = lVar.a();
                    printWriter.println(a);
                    a.dump(str2, fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size = this.a.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(this.a.get(i).toString());
            }
        }
    }
}
