package androidx.viewpager2.widget;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

/* compiled from: PageTransformerAdapter.java */
/* loaded from: classes.dex */
final class d extends ViewPager2.OnPageChangeCallback {
    private final LinearLayoutManager a;
    private ViewPager2.PageTransformer b;

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageSelected(int i) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(LinearLayoutManager linearLayoutManager) {
        this.a = linearLayoutManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ViewPager2.PageTransformer a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable ViewPager2.PageTransformer pageTransformer) {
        this.b = pageTransformer;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrolled(int i, float f, int i2) {
        if (this.b != null) {
            float f2 = -f;
            for (int i3 = 0; i3 < this.a.getChildCount(); i3++) {
                View childAt = this.a.getChildAt(i3);
                if (childAt != null) {
                    this.b.transformPage(childAt, (this.a.getPosition(childAt) - i) + f2);
                } else {
                    throw new IllegalStateException(String.format(Locale.US, "LayoutManager returned a null child at pos %d/%d while transforming pages", Integer.valueOf(i3), Integer.valueOf(this.a.getChildCount())));
                }
            }
        }
    }
}
