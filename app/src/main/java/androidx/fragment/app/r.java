package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: SpecialEffectsController.java */
/* loaded from: classes.dex */
public abstract class r {
    final ArrayList<b> a = new ArrayList<>();
    final ArrayList<b> b = new ArrayList<>();
    boolean c = false;
    boolean d = false;
    private final ViewGroup e;

    abstract void a(@NonNull List<b> list, boolean z);

    @NonNull
    public static r a(@NonNull ViewGroup viewGroup, @NonNull FragmentManager fragmentManager) {
        return a(viewGroup, fragmentManager.y());
    }

    @NonNull
    public static r a(@NonNull ViewGroup viewGroup, @NonNull s sVar) {
        Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
        if (tag instanceof r) {
            return (r) tag;
        }
        r a2 = sVar.a(viewGroup);
        viewGroup.setTag(R.id.special_effects_controller_view_tag, a2);
        return a2;
    }

    public r(@NonNull ViewGroup viewGroup) {
        this.e = viewGroup;
    }

    @NonNull
    public ViewGroup a() {
        return this.e;
    }

    @Nullable
    public b.a a(@NonNull l lVar) {
        b a2 = a(lVar.a());
        b.a d = a2 != null ? a2.d() : null;
        b b2 = b(lVar.a());
        return (b2 == null || !(d == null || d == b.a.NONE)) ? d : b2.d();
    }

    @Nullable
    private b a(@NonNull Fragment fragment) {
        Iterator<b> it = this.a.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.e().equals(fragment) && !next.f()) {
                return next;
            }
        }
        return null;
    }

    @Nullable
    private b b(@NonNull Fragment fragment) {
        Iterator<b> it = this.b.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.e().equals(fragment) && !next.f()) {
                return next;
            }
        }
        return null;
    }

    public void a(@NonNull b.EnumC0018b bVar, @NonNull l lVar) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + lVar.a());
        }
        a(bVar, b.a.ADDING, lVar);
    }

    public void b(@NonNull l lVar) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + lVar.a());
        }
        a(b.EnumC0018b.VISIBLE, b.a.NONE, lVar);
    }

    public void c(@NonNull l lVar) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + lVar.a());
        }
        a(b.EnumC0018b.GONE, b.a.NONE, lVar);
    }

    public void d(@NonNull l lVar) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + lVar.a());
        }
        a(b.EnumC0018b.REMOVED, b.a.REMOVING, lVar);
    }

    private void a(@NonNull b.EnumC0018b bVar, @NonNull b.a aVar, @NonNull l lVar) {
        synchronized (this.a) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            b a2 = a(lVar.a());
            if (a2 != null) {
                a2.a(bVar, aVar);
                return;
            }
            final a aVar2 = new a(bVar, aVar, lVar, cancellationSignal);
            this.a.add(aVar2);
            aVar2.a(new Runnable() { // from class: androidx.fragment.app.r.1
                @Override // java.lang.Runnable
                public void run() {
                    if (r.this.a.contains(aVar2)) {
                        aVar2.c().b(aVar2.e().mView);
                    }
                }
            });
            aVar2.a(new Runnable() { // from class: androidx.fragment.app.r.2
                @Override // java.lang.Runnable
                public void run() {
                    r.this.a.remove(aVar2);
                    r.this.b.remove(aVar2);
                }
            });
        }
    }

    public void a(boolean z) {
        this.c = z;
    }

    public void b() {
        synchronized (this.a) {
            f();
            this.d = false;
            int size = this.a.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                b bVar = this.a.get(size);
                b.EnumC0018b a2 = b.EnumC0018b.a(bVar.e().mView);
                if (bVar.c() == b.EnumC0018b.VISIBLE && a2 != b.EnumC0018b.VISIBLE) {
                    this.d = bVar.e().isPostponed();
                    break;
                }
                size--;
            }
        }
    }

    public void c() {
        if (this.d) {
            this.d = false;
            d();
        }
    }

    public void d() {
        if (!this.d) {
            if (!ViewCompat.isAttachedToWindow(this.e)) {
                e();
                this.c = false;
                return;
            }
            synchronized (this.a) {
                if (!this.a.isEmpty()) {
                    ArrayList arrayList = new ArrayList(this.b);
                    this.b.clear();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        b bVar = (b) it.next();
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + bVar);
                        }
                        bVar.g();
                        if (!bVar.h()) {
                            this.b.add(bVar);
                        }
                    }
                    f();
                    ArrayList arrayList2 = new ArrayList(this.a);
                    this.a.clear();
                    this.b.addAll(arrayList2);
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        ((b) it2.next()).a();
                    }
                    a(arrayList2, this.c);
                    this.c = false;
                }
            }
        }
    }

    public void e() {
        String str;
        String str2;
        boolean isAttachedToWindow = ViewCompat.isAttachedToWindow(this.e);
        synchronized (this.a) {
            f();
            Iterator<b> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
            Iterator it2 = new ArrayList(this.b).iterator();
            while (it2.hasNext()) {
                b bVar = (b) it2.next();
                if (FragmentManager.a(2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SpecialEffectsController: ");
                    if (isAttachedToWindow) {
                        str2 = "";
                    } else {
                        str2 = "Container " + this.e + " is not attached to window. ";
                    }
                    sb.append(str2);
                    sb.append("Cancelling running operation ");
                    sb.append(bVar);
                    Log.v("FragmentManager", sb.toString());
                }
                bVar.g();
            }
            Iterator it3 = new ArrayList(this.a).iterator();
            while (it3.hasNext()) {
                b bVar2 = (b) it3.next();
                if (FragmentManager.a(2)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("SpecialEffectsController: ");
                    if (isAttachedToWindow) {
                        str = "";
                    } else {
                        str = "Container " + this.e + " is not attached to window. ";
                    }
                    sb2.append(str);
                    sb2.append("Cancelling pending operation ");
                    sb2.append(bVar2);
                    Log.v("FragmentManager", sb2.toString());
                }
                bVar2.g();
            }
        }
    }

    private void f() {
        Iterator<b> it = this.a.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.d() == b.a.ADDING) {
                next.a(b.EnumC0018b.a(next.e().requireView().getVisibility()), b.a.NONE);
            }
        }
    }

    /* compiled from: SpecialEffectsController.java */
    /* loaded from: classes.dex */
    public static class b {
        @NonNull
        private EnumC0018b a;
        @NonNull
        private a b;
        @NonNull
        private final Fragment c;
        @NonNull
        private final List<Runnable> d = new ArrayList();
        @NonNull
        private final HashSet<CancellationSignal> e = new HashSet<>();
        private boolean f = false;
        private boolean g = false;

        /* compiled from: SpecialEffectsController.java */
        /* loaded from: classes.dex */
        public enum a {
            NONE,
            ADDING,
            REMOVING
        }

        void a() {
        }

        /* compiled from: SpecialEffectsController.java */
        /* renamed from: androidx.fragment.app.r$b$b */
        /* loaded from: classes.dex */
        public enum EnumC0018b {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            @NonNull
            public static EnumC0018b a(@NonNull View view) {
                if (view.getAlpha() == 0.0f && view.getVisibility() == 0) {
                    return INVISIBLE;
                }
                return a(view.getVisibility());
            }

            @NonNull
            public static EnumC0018b a(int i) {
                if (i == 0) {
                    return VISIBLE;
                }
                if (i == 4) {
                    return INVISIBLE;
                }
                if (i == 8) {
                    return GONE;
                }
                throw new IllegalArgumentException("Unknown visibility " + i);
            }

            public void b(@NonNull View view) {
                switch (this) {
                    case REMOVED:
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if (viewGroup != null) {
                            if (FragmentManager.a(2)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Removing view " + view + " from container " + viewGroup);
                            }
                            viewGroup.removeView(view);
                            return;
                        }
                        return;
                    case VISIBLE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to VISIBLE");
                        }
                        view.setVisibility(0);
                        return;
                    case GONE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to GONE");
                        }
                        view.setVisibility(8);
                        return;
                    case INVISIBLE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to INVISIBLE");
                        }
                        view.setVisibility(4);
                        return;
                    default:
                        return;
                }
            }
        }

        b(@NonNull EnumC0018b bVar, @NonNull a aVar, @NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            this.a = bVar;
            this.b = aVar;
            this.c = fragment;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.r.b.1
                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    b.this.g();
                }
            });
        }

        @NonNull
        public EnumC0018b c() {
            return this.a;
        }

        @NonNull
        a d() {
            return this.b;
        }

        @NonNull
        public final Fragment e() {
            return this.c;
        }

        final boolean f() {
            return this.f;
        }

        @NonNull
        public String toString() {
            return "Operation {" + Integer.toHexString(System.identityHashCode(this)) + "} {mFinalState = " + this.a + "} {mLifecycleImpact = " + this.b + "} {mFragment = " + this.c + "}";
        }

        final void g() {
            if (!f()) {
                this.f = true;
                if (this.e.isEmpty()) {
                    b();
                    return;
                }
                Iterator it = new ArrayList(this.e).iterator();
                while (it.hasNext()) {
                    ((CancellationSignal) it.next()).cancel();
                }
            }
        }

        final void a(@NonNull EnumC0018b bVar, @NonNull a aVar) {
            switch (aVar) {
                case ADDING:
                    if (this.a == EnumC0018b.REMOVED) {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.c + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.b + " to ADDING.");
                        }
                        this.a = EnumC0018b.VISIBLE;
                        this.b = a.ADDING;
                        return;
                    }
                    return;
                case REMOVING:
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.c + " mFinalState = " + this.a + " -> REMOVED. mLifecycleImpact  = " + this.b + " to REMOVING.");
                    }
                    this.a = EnumC0018b.REMOVED;
                    this.b = a.REMOVING;
                    return;
                case NONE:
                    if (this.a != EnumC0018b.REMOVED) {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.c + " mFinalState = " + this.a + " -> " + bVar + ". ");
                        }
                        this.a = bVar;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public final void a(@NonNull Runnable runnable) {
            this.d.add(runnable);
        }

        public final void a(@NonNull CancellationSignal cancellationSignal) {
            a();
            this.e.add(cancellationSignal);
        }

        public final void b(@NonNull CancellationSignal cancellationSignal) {
            if (this.e.remove(cancellationSignal) && this.e.isEmpty()) {
                b();
            }
        }

        final boolean h() {
            return this.g;
        }

        @CallSuper
        public void b() {
            if (!this.g) {
                if (FragmentManager.a(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
                }
                this.g = true;
                for (Runnable runnable : this.d) {
                    runnable.run();
                }
            }
        }
    }

    /* compiled from: SpecialEffectsController.java */
    /* loaded from: classes.dex */
    public static class a extends b {
        @NonNull
        private final l a;

        a(@NonNull b.EnumC0018b bVar, @NonNull b.a aVar, @NonNull l lVar, @NonNull CancellationSignal cancellationSignal) {
            super(bVar, aVar, lVar.a(), cancellationSignal);
            this.a = lVar;
        }

        @Override // androidx.fragment.app.r.b
        void a() {
            if (d() == b.a.ADDING) {
                Fragment a = this.a.a();
                View findFocus = a.mView.findFocus();
                if (findFocus != null) {
                    a.setFocusedView(findFocus);
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "requestFocus: Saved focused view " + findFocus + " for Fragment " + a);
                    }
                }
                View requireView = e().requireView();
                if (requireView.getParent() == null) {
                    this.a.s();
                    requireView.setAlpha(0.0f);
                }
                if (requireView.getAlpha() == 0.0f && requireView.getVisibility() == 0) {
                    requireView.setVisibility(4);
                }
                requireView.setAlpha(a.getPostOnViewCreatedAlpha());
            }
        }

        @Override // androidx.fragment.app.r.b
        public void b() {
            super.b();
            this.a.c();
        }
    }
}
