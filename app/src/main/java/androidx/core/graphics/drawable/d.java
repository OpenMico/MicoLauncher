package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: WrappedDrawableState.java */
/* loaded from: classes.dex */
public final class d extends Drawable.ConstantState {
    int a;
    Drawable.ConstantState b;
    ColorStateList c;
    PorterDuff.Mode d;

    public d(@Nullable d dVar) {
        this.c = null;
        this.d = b.a;
        if (dVar != null) {
            this.a = dVar.a;
            this.b = dVar.b;
            this.c = dVar.c;
            this.d = dVar.d;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable(@Nullable Resources resources) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new c(this, resources);
        }
        return new b(this, resources);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        int i = this.a;
        Drawable.ConstantState constantState = this.b;
        return i | (constantState != null ? constantState.getChangingConfigurations() : 0);
    }

    public boolean a() {
        return this.b != null;
    }
}
