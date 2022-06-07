package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

/* loaded from: classes.dex */
public class ContentLoadingProgressBar extends ProgressBar {
    long a;
    boolean b;
    boolean c;
    boolean d;
    private final Runnable e;
    private final Runnable f;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        this.b = false;
        this.a = -1L;
        setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        this.c = false;
        if (!this.d) {
            this.a = System.currentTimeMillis();
            setVisibility(0);
        }
    }

    public ContentLoadingProgressBar(@NonNull Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.a = -1L;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = new Runnable() { // from class: androidx.core.widget.-$$Lambda$ContentLoadingProgressBar$RRZMcqB-4h2-lyW2GeLnU3S6gHA
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.this.e();
            }
        };
        this.f = new Runnable() { // from class: androidx.core.widget.-$$Lambda$ContentLoadingProgressBar$xgunBHfnkGzKMlU2_4e3df8PDjo
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.this.d();
            }
        };
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    private void a() {
        removeCallbacks(this.e);
        removeCallbacks(this.f);
    }

    public void hide() {
        post(new Runnable() { // from class: androidx.core.widget.-$$Lambda$ContentLoadingProgressBar$5wDgwJ1osr9YIRy1q7Xyeirvkd4
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @UiThread
    public void b() {
        this.d = true;
        removeCallbacks(this.f);
        this.c = false;
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.a;
        long j2 = currentTimeMillis - j;
        if (j2 >= 500 || j == -1) {
            setVisibility(8);
        } else if (!this.b) {
            postDelayed(this.e, 500 - j2);
            this.b = true;
        }
    }

    public void show() {
        post(new Runnable() { // from class: androidx.core.widget.-$$Lambda$ContentLoadingProgressBar$YMGfbggZ28c1CMABX68Yt4GxBik
            @Override // java.lang.Runnable
            public final void run() {
                ContentLoadingProgressBar.this.c();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @UiThread
    public void c() {
        this.a = -1L;
        this.d = false;
        removeCallbacks(this.e);
        this.b = false;
        if (!this.c) {
            postDelayed(this.f, 500L);
            this.c = true;
        }
    }
}
