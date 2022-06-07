package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.collection.LongSparseArray;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/* loaded from: classes.dex */
public abstract class FragmentStateAdapter extends RecyclerView.Adapter<FragmentViewHolder> implements StatefulAdapter {
    final Lifecycle a;
    final FragmentManager b;
    final LongSparseArray<Fragment> c;
    boolean d;
    private final LongSparseArray<Fragment.SavedState> e;
    private final LongSparseArray<Integer> f;
    private b g;
    private boolean h;

    @NonNull
    public abstract Fragment createFragment(int i);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public final boolean onFailedToRecycleView(@NonNull FragmentViewHolder fragmentViewHolder) {
        return true;
    }

    public FragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        this(fragmentActivity.getSupportFragmentManager(), fragmentActivity.getLifecycle());
    }

    public FragmentStateAdapter(@NonNull Fragment fragment) {
        this(fragment.getChildFragmentManager(), fragment.getLifecycle());
    }

    public FragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        this.c = new LongSparseArray<>();
        this.e = new LongSparseArray<>();
        this.f = new LongSparseArray<>();
        this.d = false;
        this.h = false;
        this.b = fragmentManager;
        this.a = lifecycle;
        super.setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        Preconditions.checkArgument(this.g == null);
        this.g = new b();
        this.g.a(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        this.g.b(recyclerView);
        this.g = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public final FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return FragmentViewHolder.a(viewGroup);
    }

    public final void onBindViewHolder(@NonNull final FragmentViewHolder fragmentViewHolder, int i) {
        long itemId = fragmentViewHolder.getItemId();
        int id = fragmentViewHolder.a().getId();
        Long a2 = a(id);
        if (!(a2 == null || a2.longValue() == itemId)) {
            b(a2.longValue());
            this.f.remove(a2.longValue());
        }
        this.f.put(itemId, Integer.valueOf(id));
        b(i);
        final FrameLayout a3 = fragmentViewHolder.a();
        if (ViewCompat.isAttachedToWindow(a3)) {
            if (a3.getParent() == null) {
                a3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                        if (a3.getParent() != null) {
                            a3.removeOnLayoutChangeListener(this);
                            FragmentStateAdapter.this.a(fragmentViewHolder);
                        }
                    }
                });
            } else {
                throw new IllegalStateException("Design assumption violated.");
            }
        }
        a();
    }

    void a() {
        if (this.h && !b()) {
            ArraySet<Long> arraySet = new ArraySet();
            for (int i = 0; i < this.c.size(); i++) {
                long keyAt = this.c.keyAt(i);
                if (!containsItem(keyAt)) {
                    arraySet.add(Long.valueOf(keyAt));
                    this.f.remove(keyAt);
                }
            }
            if (!this.d) {
                this.h = false;
                for (int i2 = 0; i2 < this.c.size(); i2++) {
                    long keyAt2 = this.c.keyAt(i2);
                    if (!a(keyAt2)) {
                        arraySet.add(Long.valueOf(keyAt2));
                    }
                }
            }
            for (Long l : arraySet) {
                b(l.longValue());
            }
        }
    }

    private boolean a(long j) {
        View view;
        if (this.f.containsKey(j)) {
            return true;
        }
        Fragment fragment = this.c.get(j);
        return (fragment == null || (view = fragment.getView()) == null || view.getParent() == null) ? false : true;
    }

    private Long a(int i) {
        Long l = null;
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            if (this.f.valueAt(i2).intValue() == i) {
                if (l == null) {
                    l = Long.valueOf(this.f.keyAt(i2));
                } else {
                    throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                }
            }
        }
        return l;
    }

    private void b(int i) {
        long itemId = getItemId(i);
        if (!this.c.containsKey(itemId)) {
            Fragment createFragment = createFragment(i);
            createFragment.setInitialSavedState(this.e.get(itemId));
            this.c.put(itemId, createFragment);
        }
    }

    public final void onViewAttachedToWindow(@NonNull FragmentViewHolder fragmentViewHolder) {
        a(fragmentViewHolder);
        a();
    }

    void a(@NonNull final FragmentViewHolder fragmentViewHolder) {
        Fragment fragment = this.c.get(fragmentViewHolder.getItemId());
        if (fragment != null) {
            FrameLayout a2 = fragmentViewHolder.a();
            View view = fragment.getView();
            if (!fragment.isAdded() && view != null) {
                throw new IllegalStateException("Design assumption violated.");
            } else if (fragment.isAdded() && view == null) {
                a(fragment, a2);
            } else if (!fragment.isAdded() || view.getParent() == null) {
                if (fragment.isAdded()) {
                    a(view, a2);
                } else if (!b()) {
                    a(fragment, a2);
                    FragmentTransaction beginTransaction = this.b.beginTransaction();
                    beginTransaction.add(fragment, "f" + fragmentViewHolder.getItemId()).setMaxLifecycle(fragment, Lifecycle.State.STARTED).commitNow();
                    this.g.a(false);
                } else if (!this.b.isDestroyed()) {
                    this.a.addObserver(new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.2
                        @Override // androidx.lifecycle.LifecycleEventObserver
                        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                            if (!FragmentStateAdapter.this.b()) {
                                lifecycleOwner.getLifecycle().removeObserver(this);
                                if (ViewCompat.isAttachedToWindow(fragmentViewHolder.a())) {
                                    FragmentStateAdapter.this.a(fragmentViewHolder);
                                }
                            }
                        }
                    });
                }
            } else if (view.getParent() != a2) {
                a(view, a2);
            }
        } else {
            throw new IllegalStateException("Design assumption violated.");
        }
    }

    private void a(final Fragment fragment, @NonNull final FrameLayout frameLayout) {
        this.b.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.3
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2, @NonNull View view, @Nullable Bundle bundle) {
                if (fragment2 == fragment) {
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                    FragmentStateAdapter.this.a(view, frameLayout);
                }
            }
        }, false);
    }

    void a(@NonNull View view, @NonNull FrameLayout frameLayout) {
        if (frameLayout.getChildCount() > 1) {
            throw new IllegalStateException("Design assumption violated.");
        } else if (view.getParent() != frameLayout) {
            if (frameLayout.getChildCount() > 0) {
                frameLayout.removeAllViews();
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            frameLayout.addView(view);
        }
    }

    public final void onViewRecycled(@NonNull FragmentViewHolder fragmentViewHolder) {
        Long a2 = a(fragmentViewHolder.a().getId());
        if (a2 != null) {
            b(a2.longValue());
            this.f.remove(a2.longValue());
        }
    }

    private void b(long j) {
        ViewParent parent;
        Fragment fragment = this.c.get(j);
        if (fragment != null) {
            if (!(fragment.getView() == null || (parent = fragment.getView().getParent()) == null)) {
                ((FrameLayout) parent).removeAllViews();
            }
            if (!containsItem(j)) {
                this.e.remove(j);
            }
            if (!fragment.isAdded()) {
                this.c.remove(j);
            } else if (b()) {
                this.h = true;
            } else {
                if (fragment.isAdded() && containsItem(j)) {
                    this.e.put(j, this.b.saveFragmentInstanceState(fragment));
                }
                this.b.beginTransaction().remove(fragment).commitNow();
                this.c.remove(j);
            }
        }
    }

    boolean b() {
        return this.b.isStateSaved();
    }

    public boolean containsItem(long j) {
        return j >= 0 && j < ((long) getItemCount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void setHasStableIds(boolean z) {
        throw new UnsupportedOperationException("Stable Ids are required for the adapter to function properly, and the adapter takes care of setting the flag.");
    }

    @Override // androidx.viewpager2.adapter.StatefulAdapter
    @NonNull
    public final Parcelable saveState() {
        Bundle bundle = new Bundle(this.c.size() + this.e.size());
        for (int i = 0; i < this.c.size(); i++) {
            long keyAt = this.c.keyAt(i);
            Fragment fragment = this.c.get(keyAt);
            if (fragment != null && fragment.isAdded()) {
                this.b.putFragment(bundle, a("f#", keyAt), fragment);
            }
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            long keyAt2 = this.e.keyAt(i2);
            if (containsItem(keyAt2)) {
                bundle.putParcelable(a("s#", keyAt2), this.e.get(keyAt2));
            }
        }
        return bundle;
    }

    @Override // androidx.viewpager2.adapter.StatefulAdapter
    public final void restoreState(@NonNull Parcelable parcelable) {
        if (!this.e.isEmpty() || !this.c.isEmpty()) {
            throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
        }
        Bundle bundle = (Bundle) parcelable;
        if (bundle.getClassLoader() == null) {
            bundle.setClassLoader(getClass().getClassLoader());
        }
        for (String str : bundle.keySet()) {
            if (a(str, "f#")) {
                this.c.put(b(str, "f#"), this.b.getFragment(bundle, str));
            } else if (a(str, "s#")) {
                long b2 = b(str, "s#");
                Fragment.SavedState savedState = (Fragment.SavedState) bundle.getParcelable(str);
                if (containsItem(b2)) {
                    this.e.put(b2, savedState);
                }
            } else {
                throw new IllegalArgumentException("Unexpected key in savedState: " + str);
            }
        }
        if (!this.c.isEmpty()) {
            this.h = true;
            this.d = true;
            a();
            c();
        }
    }

    private void c() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.4
            @Override // java.lang.Runnable
            public void run() {
                FragmentStateAdapter fragmentStateAdapter = FragmentStateAdapter.this;
                fragmentStateAdapter.d = false;
                fragmentStateAdapter.a();
            }
        };
        this.a.addObserver(new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.5
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    handler.removeCallbacks(runnable);
                    lifecycleOwner.getLifecycle().removeObserver(this);
                }
            }
        });
        handler.postDelayed(runnable, 10000L);
    }

    @NonNull
    private static String a(@NonNull String str, long j) {
        return str + j;
    }

    private static boolean a(@NonNull String str, @NonNull String str2) {
        return str.startsWith(str2) && str.length() > str2.length();
    }

    private static long b(@NonNull String str, @NonNull String str2) {
        return Long.parseLong(str.substring(str2.length()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class b {
        private ViewPager2.OnPageChangeCallback b;
        private RecyclerView.AdapterDataObserver c;
        private LifecycleEventObserver d;
        private ViewPager2 e;
        private long f = -1;

        b() {
        }

        void a(@NonNull RecyclerView recyclerView) {
            this.e = c(recyclerView);
            this.b = new ViewPager2.OnPageChangeCallback() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.b.1
                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageScrollStateChanged(int i) {
                    b.this.a(false);
                }

                @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                public void onPageSelected(int i) {
                    b.this.a(false);
                }
            };
            this.e.registerOnPageChangeCallback(this.b);
            this.c = new a() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter.b.2
                @Override // androidx.viewpager2.adapter.FragmentStateAdapter.a, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    b.this.a(true);
                }
            };
            FragmentStateAdapter.this.registerAdapterDataObserver(this.c);
            this.d = new LifecycleEventObserver() { // from class: androidx.viewpager2.adapter.FragmentStateAdapter$FragmentMaxLifecycleEnforcer$3
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                    FragmentStateAdapter.b.this.a(false);
                }
            };
            FragmentStateAdapter.this.a.addObserver(this.d);
        }

        void b(@NonNull RecyclerView recyclerView) {
            c(recyclerView).unregisterOnPageChangeCallback(this.b);
            FragmentStateAdapter.this.unregisterAdapterDataObserver(this.c);
            FragmentStateAdapter.this.a.removeObserver(this.d);
            this.e = null;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(boolean z) {
            int currentItem;
            Fragment fragment;
            if (!FragmentStateAdapter.this.b() && this.e.getScrollState() == 0 && !FragmentStateAdapter.this.c.isEmpty() && FragmentStateAdapter.this.getItemCount() != 0 && (currentItem = this.e.getCurrentItem()) < FragmentStateAdapter.this.getItemCount()) {
                long itemId = FragmentStateAdapter.this.getItemId(currentItem);
                if ((itemId != this.f || z) && (fragment = FragmentStateAdapter.this.c.get(itemId)) != null && fragment.isAdded()) {
                    this.f = itemId;
                    FragmentTransaction beginTransaction = FragmentStateAdapter.this.b.beginTransaction();
                    Fragment fragment2 = null;
                    for (int i = 0; i < FragmentStateAdapter.this.c.size(); i++) {
                        long keyAt = FragmentStateAdapter.this.c.keyAt(i);
                        Fragment valueAt = FragmentStateAdapter.this.c.valueAt(i);
                        if (valueAt.isAdded()) {
                            if (keyAt != this.f) {
                                beginTransaction.setMaxLifecycle(valueAt, Lifecycle.State.STARTED);
                            } else {
                                fragment2 = valueAt;
                            }
                            valueAt.setMenuVisibility(keyAt == this.f);
                        }
                    }
                    if (fragment2 != null) {
                        beginTransaction.setMaxLifecycle(fragment2, Lifecycle.State.RESUMED);
                    }
                    if (!beginTransaction.isEmpty()) {
                        beginTransaction.commitNow();
                    }
                }
            }
        }

        @NonNull
        private ViewPager2 c(@NonNull RecyclerView recyclerView) {
            ViewParent parent = recyclerView.getParent();
            if (parent instanceof ViewPager2) {
                return (ViewPager2) parent;
            }
            throw new IllegalStateException("Expected ViewPager2 instance. Got: " + parent);
        }
    }

    /* loaded from: classes.dex */
    private static abstract class a extends RecyclerView.AdapterDataObserver {
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        private a() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2, int i3) {
            onChanged();
        }
    }
}
