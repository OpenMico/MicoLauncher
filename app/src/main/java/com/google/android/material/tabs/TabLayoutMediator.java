package com.google.android.material.tabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public final class TabLayoutMediator {
    @NonNull
    private final TabLayout a;
    @NonNull
    private final ViewPager2 b;
    private final boolean c;
    private final boolean d;
    private final TabConfigurationStrategy e;
    @Nullable
    private RecyclerView.Adapter<?> f;
    private boolean g;
    @Nullable
    private b h;
    @Nullable
    private TabLayout.OnTabSelectedListener i;
    @Nullable
    private RecyclerView.AdapterDataObserver j;

    /* loaded from: classes2.dex */
    public interface TabConfigurationStrategy {
        void onConfigureTab(@NonNull TabLayout.Tab tab, int i);
    }

    public TabLayoutMediator(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2, @NonNull TabConfigurationStrategy tabConfigurationStrategy) {
        this(tabLayout, viewPager2, true, tabConfigurationStrategy);
    }

    public TabLayoutMediator(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2, boolean z, @NonNull TabConfigurationStrategy tabConfigurationStrategy) {
        this(tabLayout, viewPager2, z, true, tabConfigurationStrategy);
    }

    public TabLayoutMediator(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager2, boolean z, boolean z2, @NonNull TabConfigurationStrategy tabConfigurationStrategy) {
        this.a = tabLayout;
        this.b = viewPager2;
        this.c = z;
        this.d = z2;
        this.e = tabConfigurationStrategy;
    }

    public void attach() {
        if (!this.g) {
            this.f = this.b.getAdapter();
            if (this.f != null) {
                this.g = true;
                this.h = new b(this.a);
                this.b.registerOnPageChangeCallback(this.h);
                this.i = new c(this.b, this.d);
                this.a.addOnTabSelectedListener(this.i);
                if (this.c) {
                    this.j = new a();
                    this.f.registerAdapterDataObserver(this.j);
                }
                a();
                this.a.setScrollPosition(this.b.getCurrentItem(), 0.0f, true);
                return;
            }
            throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
        }
        throw new IllegalStateException("TabLayoutMediator is already attached");
    }

    public void detach() {
        RecyclerView.Adapter<?> adapter;
        if (this.c && (adapter = this.f) != null) {
            adapter.unregisterAdapterDataObserver(this.j);
            this.j = null;
        }
        this.a.removeOnTabSelectedListener(this.i);
        this.b.unregisterOnPageChangeCallback(this.h);
        this.i = null;
        this.h = null;
        this.f = null;
        this.g = false;
    }

    public boolean isAttached() {
        return this.g;
    }

    void a() {
        int min;
        this.a.removeAllTabs();
        RecyclerView.Adapter<?> adapter = this.f;
        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                TabLayout.Tab newTab = this.a.newTab();
                this.e.onConfigureTab(newTab, i);
                this.a.addTab(newTab, false);
            }
            if (itemCount > 0 && (min = Math.min(this.b.getCurrentItem(), this.a.getTabCount() - 1)) != this.a.getSelectedTabPosition()) {
                TabLayout tabLayout = this.a;
                tabLayout.selectTab(tabLayout.getTabAt(min));
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class b extends ViewPager2.OnPageChangeCallback {
        @NonNull
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        b(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
            a();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int i) {
            this.b = this.c;
            this.c = i;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int i, float f, int i2) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.c != 2 || this.b == 1;
                if (!(this.c == 2 && this.b == 0)) {
                    z = true;
                }
                tabLayout.setScrollPosition(i, f, z2, z);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int i) {
            TabLayout tabLayout = this.a.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i && i < tabLayout.getTabCount()) {
                int i2 = this.c;
                tabLayout.selectTab(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.b == 0));
            }
        }

        void a() {
            this.c = 0;
            this.b = 0;
        }
    }

    /* loaded from: classes2.dex */
    private static class c implements TabLayout.OnTabSelectedListener {
        private final ViewPager2 a;
        private final boolean b;

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabReselected(TabLayout.Tab tab) {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        c(ViewPager2 viewPager2, boolean z) {
            this.a = viewPager2;
            this.b = z;
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public void onTabSelected(@NonNull TabLayout.Tab tab) {
            this.a.setCurrentItem(tab.getPosition(), this.b);
        }
    }

    /* loaded from: classes2.dex */
    private class a extends RecyclerView.AdapterDataObserver {
        a() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            TabLayoutMediator.this.a();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2) {
            TabLayoutMediator.this.a();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            TabLayoutMediator.this.a();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            TabLayoutMediator.this.a();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            TabLayoutMediator.this.a();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            TabLayoutMediator.this.a();
        }
    }
}
