package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* compiled from: ActionBarBackgroundDrawable.java */
/* loaded from: classes.dex */
class a extends Drawable {
    final ActionBarContainer a;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public a(ActionBarContainer actionBarContainer) {
        this.a = actionBarContainer;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!this.a.d) {
            if (this.a.a != null) {
                this.a.a.draw(canvas);
            }
            if (this.a.b != null && this.a.e) {
                this.a.b.draw(canvas);
            }
        } else if (this.a.c != null) {
            this.a.c.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.a.d) {
            if (this.a.c != null) {
                this.a.c.getOutline(outline);
            }
        } else if (this.a.a != null) {
            this.a.a.getOutline(outline);
        }
    }
}
