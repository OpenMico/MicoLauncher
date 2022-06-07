package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentLifecycleCallbacksDispatcher.java */
/* loaded from: classes.dex */
public class g {
    @NonNull
    private final CopyOnWriteArrayList<a> a = new CopyOnWriteArrayList<>();
    @NonNull
    private final FragmentManager b;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FragmentLifecycleCallbacksDispatcher.java */
    /* loaded from: classes.dex */
    public static final class a {
        @NonNull
        final FragmentManager.FragmentLifecycleCallbacks a;
        final boolean b;

        a(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
            this.a = fragmentLifecycleCallbacks;
            this.b = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(@NonNull FragmentManager fragmentManager) {
        this.b = fragmentManager;
    }

    public void a(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.a.add(new a(fragmentLifecycleCallbacks, z));
    }

    public void a(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.a) {
            int i = 0;
            int size = this.a.size();
            while (true) {
                if (i >= size) {
                    break;
                } else if (this.a.get(i).a == fragmentLifecycleCallbacks) {
                    this.a.remove(i);
                    break;
                } else {
                    i++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Fragment fragment, boolean z) {
        Context c = this.b.h().c();
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().a(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentPreAttached(this.b, fragment, c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Fragment fragment, boolean z) {
        Context c = this.b.h().c();
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().b(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentAttached(this.b, fragment, c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().a(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentPreCreated(this.b, fragment, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().b(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentCreated(this.b, fragment, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().c(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentActivityCreated(this.b, fragment, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().a(fragment, view, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentViewCreated(this.b, fragment, view, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().c(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentStarted(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().d(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentResumed(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().e(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentPaused(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().f(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentStopped(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(@NonNull Fragment fragment, @NonNull Bundle bundle, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().d(fragment, bundle, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentSaveInstanceState(this.b, fragment, bundle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void g(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().g(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentViewDestroyed(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void h(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().h(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentDestroyed(this.b, fragment);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void i(@NonNull Fragment fragment, boolean z) {
        Fragment i = this.b.i();
        if (i != null) {
            i.getParentFragmentManager().z().i(fragment, true);
        }
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!z || next.b) {
                next.a.onFragmentDetached(this.b, fragment);
            }
        }
    }
}
