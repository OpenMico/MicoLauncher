package androidx.viewpager2.widget;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class CompositePageTransformer implements ViewPager2.PageTransformer {
    private final List<ViewPager2.PageTransformer> a = new ArrayList();

    public void addTransformer(@NonNull ViewPager2.PageTransformer pageTransformer) {
        this.a.add(pageTransformer);
    }

    public void removeTransformer(@NonNull ViewPager2.PageTransformer pageTransformer) {
        this.a.remove(pageTransformer);
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(@NonNull View view, float f) {
        for (ViewPager2.PageTransformer pageTransformer : this.a) {
            pageTransformer.transformPage(view, f);
        }
    }
}
