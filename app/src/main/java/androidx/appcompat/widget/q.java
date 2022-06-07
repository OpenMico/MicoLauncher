package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TintResources.java */
/* loaded from: classes.dex */
public class q extends n {
    private final WeakReference<Context> a;

    public q(@NonNull Context context, @NonNull Resources resources) {
        super(resources);
        this.a = new WeakReference<>(context);
    }

    @Override // androidx.appcompat.widget.n, android.content.res.Resources
    public Drawable getDrawable(int i) throws Resources.NotFoundException {
        Drawable drawable = super.getDrawable(i);
        Context context = this.a.get();
        if (!(drawable == null || context == null)) {
            ResourceManagerInternal.get().a(context, i, drawable);
        }
        return drawable;
    }
}
