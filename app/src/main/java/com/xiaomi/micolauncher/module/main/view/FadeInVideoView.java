package com.xiaomi.micolauncher.module.main.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;

/* loaded from: classes3.dex */
public class FadeInVideoView extends RelativeLayout {
    ImageView a;
    ImageView b;
    ImageView c;
    TextView d;
    TextView e;
    TextView f;
    private boolean g;
    private String h;
    private Bitmap i;
    private boolean j;
    private ValueAnimator k;
    private ValueAnimator l;
    private ValueAnimator.AnimatorUpdateListener m;
    private Animator.AnimatorListener n;
    private ValueAnimator.AnimatorUpdateListener o;
    private Animator.AnimatorListener p;

    public FadeInVideoView(Context context) {
        this(context, null);
    }

    public FadeInVideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FadeInVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.m = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.view.FadeInVideoView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                FadeInVideoView.this.b.setImageAlpha((int) (255.0f * floatValue));
                if (!FadeInVideoView.this.g) {
                    FadeInVideoView.this.d.setAlpha(floatValue);
                }
            }
        };
        this.n = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.view.FadeInVideoView.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (FadeInVideoView.this.i != null && !FadeInVideoView.this.i.isRecycled()) {
                    FadeInVideoView.this.b.setImageBitmap(FadeInVideoView.this.i);
                }
                FadeInVideoView.this.b.setImageAlpha(255);
            }
        };
        this.o = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.view.FadeInVideoView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FadeInVideoView.this.e.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        };
        this.p = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.view.FadeInVideoView.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FadeInVideoView.this.d.setText(FadeInVideoView.this.h);
                FadeInVideoView.this.d.setAlpha(1.0f);
                FadeInVideoView.this.e.setText("");
                FadeInVideoView.this.j = false;
            }
        };
        LayoutInflater.from(context).inflate(R.layout.view_fade_in_video, this);
        this.a = (ImageView) findViewById(R.id.preview_iv);
        this.b = (ImageView) findViewById(R.id.image_iv);
        this.c = (ImageView) findViewById(R.id.icon_iv);
        this.d = (TextView) findViewById(R.id.type_tv);
        this.e = (TextView) findViewById(R.id.preview_type_tv);
        this.f = (TextView) findViewById(R.id.short_video_title);
        this.k = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.k.setDuration(1000L);
        this.k.addUpdateListener(this.m);
        this.k.addListener(this.n);
        this.l = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.l.setDuration(2300L);
        this.l.addUpdateListener(this.o);
        this.l.addListener(this.p);
    }

    public void clear() {
        this.l.removeAllListeners();
        this.l.removeAllUpdateListeners();
        this.k.removeAllListeners();
        this.k.removeAllUpdateListeners();
    }

    public void refreshPage(String str, final boolean z, final String str2, final String str3) {
        boolean z2 = false;
        if (this.j && this.i != null) {
            L.launcher.i(" is refreshing page, stop former refreshing");
            this.j = false;
            this.b.setImageBitmap(this.i);
            this.b.setImageAlpha(255);
            this.d.setText(str3);
            this.d.setAlpha(1.0f);
            this.l.cancel();
            this.k.cancel();
        }
        this.i = null;
        if (str3 != null && str3.equals(this.h)) {
            z2 = true;
        }
        this.g = z2;
        this.h = str3;
        Glide.with(this).asBitmap().load(str).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().override(this.b.getWidth(), this.b.getHeight())).into((RequestBuilder<Bitmap>) new ImageViewTarget<Bitmap>(this.b) { // from class: com.xiaomi.micolauncher.module.main.view.FadeInVideoView.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void setResource(@Nullable Bitmap bitmap) {
                if (bitmap != null) {
                    FadeInVideoView.this.i = bitmap;
                    FadeInVideoView.this.a(bitmap, str3, z, str2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Bitmap bitmap, final String str, final boolean z, final String str2) {
        MicoSchedulers.mainThread().scheduleDirect(new Runnable() { // from class: com.xiaomi.micolauncher.module.main.view.-$$Lambda$FadeInVideoView$xU36F7CFDx14zPG0vo92-K_i6ps
            @Override // java.lang.Runnable
            public final void run() {
                FadeInVideoView.this.a(z, str2, bitmap, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, String str, Bitmap bitmap, String str2) {
        if (!z || TextUtils.isEmpty(str)) {
            this.f.setText("");
        } else {
            this.f.setText(str);
        }
        this.a.setImageBitmap(bitmap);
        this.e.setText(str2);
        this.j = true;
        this.k.start();
        this.l.start();
    }
}
