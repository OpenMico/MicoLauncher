package androidx.viewpager2.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/* compiled from: CompositeOnPageChangeCallback.java */
/* loaded from: classes.dex */
final class b extends ViewPager2.OnPageChangeCallback {
    @NonNull
    private final List<ViewPager2.OnPageChangeCallback> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(int i) {
        this.a = new ArrayList(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.a.add(onPageChangeCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.a.remove(onPageChangeCallback);
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrolled(int i, float f, @Px int i2) {
        try {
            for (ViewPager2.OnPageChangeCallback onPageChangeCallback : this.a) {
                onPageChangeCallback.onPageScrolled(i, f, i2);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageSelected(int i) {
        try {
            for (ViewPager2.OnPageChangeCallback onPageChangeCallback : this.a) {
                onPageChangeCallback.onPageSelected(i);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
        try {
            for (ViewPager2.OnPageChangeCallback onPageChangeCallback : this.a) {
                onPageChangeCallback.onPageScrollStateChanged(i);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
        }
    }

    private void a(ConcurrentModificationException concurrentModificationException) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", concurrentModificationException);
    }
}
