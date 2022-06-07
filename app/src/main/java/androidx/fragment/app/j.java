package androidx.fragment.app;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: FragmentManagerViewModel.java */
/* loaded from: classes.dex */
public final class j extends ViewModel {
    private static final ViewModelProvider.Factory a = new ViewModelProvider.Factory() { // from class: androidx.fragment.app.j.1
        @Override // androidx.lifecycle.ViewModelProvider.Factory
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            return new j(true);
        }
    };
    private final boolean e;
    private final HashMap<String, Fragment> b = new HashMap<>();
    private final HashMap<String, j> c = new HashMap<>();
    private final HashMap<String, ViewModelStore> d = new HashMap<>();
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;

    @NonNull
    public static j a(ViewModelStore viewModelStore) {
        return (j) new ViewModelProvider(viewModelStore, a).get(j.class);
    }

    public j(boolean z) {
        this.e = z;
    }

    public void a(boolean z) {
        this.h = z;
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "onCleared called for " + this);
        }
        this.f = true;
    }

    public boolean a() {
        return this.f;
    }

    public void a(@NonNull Fragment fragment) {
        if (this.h) {
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
            }
        } else if (!this.b.containsKey(fragment.mWho)) {
            this.b.put(fragment.mWho, fragment);
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "Updating retained Fragments: Added " + fragment);
            }
        }
    }

    @Nullable
    public Fragment a(String str) {
        return this.b.get(str);
    }

    @NonNull
    public Collection<Fragment> b() {
        return new ArrayList(this.b.values());
    }

    public boolean b(@NonNull Fragment fragment) {
        if (!this.b.containsKey(fragment.mWho)) {
            return true;
        }
        if (this.e) {
            return this.f;
        }
        return !this.g;
    }

    public void c(@NonNull Fragment fragment) {
        if (!this.h) {
            if ((this.b.remove(fragment.mWho) != null) && FragmentManager.a(2)) {
                Log.v("FragmentManager", "Updating retained Fragments: Removed " + fragment);
            }
        } else if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
        }
    }

    @NonNull
    public j d(@NonNull Fragment fragment) {
        j jVar = this.c.get(fragment.mWho);
        if (jVar != null) {
            return jVar;
        }
        j jVar2 = new j(this.e);
        this.c.put(fragment.mWho, jVar2);
        return jVar2;
    }

    @NonNull
    public ViewModelStore e(@NonNull Fragment fragment) {
        ViewModelStore viewModelStore = this.d.get(fragment.mWho);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.d.put(fragment.mWho, viewModelStore2);
        return viewModelStore2;
    }

    public void f(@NonNull Fragment fragment) {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "Clearing non-config state for " + fragment);
        }
        j jVar = this.c.get(fragment.mWho);
        if (jVar != null) {
            jVar.onCleared();
            this.c.remove(fragment.mWho);
        }
        ViewModelStore viewModelStore = this.d.get(fragment.mWho);
        if (viewModelStore != null) {
            viewModelStore.clear();
            this.d.remove(fragment.mWho);
        }
    }

    @Deprecated
    public void a(@Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.b.clear();
        this.c.clear();
        this.d.clear();
        if (fragmentManagerNonConfig != null) {
            Collection<Fragment> a2 = fragmentManagerNonConfig.a();
            if (a2 != null) {
                for (Fragment fragment : a2) {
                    if (fragment != null) {
                        this.b.put(fragment.mWho, fragment);
                    }
                }
            }
            Map<String, FragmentManagerNonConfig> b = fragmentManagerNonConfig.b();
            if (b != null) {
                for (Map.Entry<String, FragmentManagerNonConfig> entry : b.entrySet()) {
                    j jVar = new j(this.e);
                    jVar.a(entry.getValue());
                    this.c.put(entry.getKey(), jVar);
                }
            }
            Map<String, ViewModelStore> c = fragmentManagerNonConfig.c();
            if (c != null) {
                this.d.putAll(c);
            }
        }
        this.g = false;
    }

    @Nullable
    @Deprecated
    public FragmentManagerNonConfig c() {
        if (this.b.isEmpty() && this.c.isEmpty() && this.d.isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, j> entry : this.c.entrySet()) {
            FragmentManagerNonConfig c = entry.getValue().c();
            if (c != null) {
                hashMap.put(entry.getKey(), c);
            }
        }
        this.g = true;
        if (!this.b.isEmpty() || !hashMap.isEmpty() || !this.d.isEmpty()) {
            return new FragmentManagerNonConfig(new ArrayList(this.b.values()), hashMap, new HashMap(this.d));
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        j jVar = (j) obj;
        return this.b.equals(jVar.b) && this.c.equals(jVar.c) && this.d.equals(jVar.d);
    }

    public int hashCode() {
        return (((this.b.hashCode() * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator<Fragment> it = this.b.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator<String> it2 = this.c.keySet().iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator<String> it3 = this.d.keySet().iterator();
        while (it3.hasNext()) {
            sb.append(it3.next());
            if (it3.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
