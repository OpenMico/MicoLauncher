package com.xiaomi.micolauncher.module.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.android.internal.graphics.palette.Palette;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.BitmapUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.main.manager.ImageSwatchCacheManager;
import com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface;
import java.util.List;

/* loaded from: classes3.dex */
public class FadeInCardView extends RelativeLayout implements FadeInCardViewInterface {
    ImageView a;
    ImageView b;
    ImageView c;
    TextView d;
    TextView e;
    TextView f;
    TextView g;
    private boolean h;
    private boolean i;
    private String j;
    private Bitmap k;
    private int l;
    private String m;
    private String n;
    private ValueAnimator o;
    private ValueAnimator p;
    private ValueAnimator.AnimatorUpdateListener q;
    private Animator.AnimatorListener r;
    private ValueAnimator.AnimatorUpdateListener s;
    private Animator.AnimatorListener t;

    /* loaded from: classes3.dex */
    public interface FadeInCardItem {
        int getBgResId();

        String getCardAction();

        String getCardType();

        String getImgUrl();

        String getTitle();

        int getTypeResId();

        void handleItemClick(Context context);
    }

    /* loaded from: classes3.dex */
    public interface a {
        void onGetBitmapSwatchFinished();
    }

    public FadeInCardView(Context context) {
        this(context, null);
    }

    public FadeInCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FadeInCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.q = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                FadeInCardView.this.b.setImageAlpha((int) (255.0f * floatValue));
                FadeInCardView.this.e.setAlpha(floatValue);
                if (!FadeInCardView.this.i) {
                    FadeInCardView.this.d.setAlpha(floatValue);
                }
            }
        };
        this.r = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardView.3
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
                if (FadeInCardView.this.k != null && !FadeInCardView.this.k.isRecycled()) {
                    FadeInCardView.this.b.setImageBitmap(FadeInCardView.this.k);
                }
                FadeInCardView.this.b.setImageAlpha(255);
            }
        };
        this.s = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                FadeInCardView.this.f.setAlpha(floatValue);
                FadeInCardView.this.g.setAlpha(floatValue);
            }
        };
        this.t = new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardView.5
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
                FadeInCardView.this.e.setText(FadeInCardView.this.m);
                FadeInCardView.this.e.setAlpha(1.0f);
                FadeInCardView.this.f.setText("");
                FadeInCardView.this.d.setText(FadeInCardView.this.n);
                FadeInCardView.this.d.setAlpha(1.0f);
                FadeInCardView.this.g.setText("");
                FadeInCardView.this.h = false;
            }
        };
        LayoutInflater.from(context).inflate(R.layout.view_fade_in_card, this);
        this.a = (ImageView) findViewById(R.id.preview_iv);
        this.b = (ImageView) findViewById(R.id.image_iv);
        this.c = (ImageView) findViewById(R.id.icon_iv);
        this.d = (TextView) findViewById(R.id.type_tv);
        this.e = (TextView) findViewById(R.id.title_tv);
        this.f = (TextView) findViewById(R.id.preview_tv);
        this.g = (TextView) findViewById(R.id.preview_type_tv);
        this.o = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.o.setDuration(1000L);
        this.o.addUpdateListener(this.q);
        this.o.addListener(this.r);
        this.p = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.p.setDuration(2300L);
        this.p.addUpdateListener(this.s);
        this.p.addListener(this.t);
    }

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void clear() {
        this.p.removeAllListeners();
        this.p.removeAllUpdateListeners();
        this.o.removeAllListeners();
        this.o.removeAllUpdateListeners();
    }

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void setIconIv(int i) {
        GlideUtils.bindImageView(getContext(), i, this.c);
    }

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void refreshPage(final String str, final String str2, final String str3, int i, int i2) {
        if (this.h && this.k != null) {
            L.launcher.i(" is refreshing page, stop former refreshing");
            this.h = false;
            this.b.setImageBitmap(this.k);
            this.b.setImageAlpha(255);
            this.e.setText(str2);
            this.e.setAlpha(1.0f);
            this.d.setText(str3);
            this.d.setAlpha(1.0f);
            this.p.cancel();
            this.o.cancel();
        }
        this.l = 0;
        this.i = str3.equals(this.n);
        this.m = str2;
        this.n = str3;
        this.j = str;
        ImageView imageView = this.b;
        GlideUtils.bindImageView(imageView, str, 0, new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.module.main.FadeInCardView.1
            @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                L.launcher.i(" load image failed");
            }

            /* renamed from: a */
            public void setResource(@Nullable Bitmap bitmap) {
                if (bitmap != null) {
                    if (FadeInCardView.this.k == null) {
                        Logger logger = L.launcher;
                        logger.d(" set current iv bg url " + str);
                        FadeInCardView.this.k = bitmap;
                        FadeInCardView.this.b.setImageBitmap(bitmap);
                        FadeInCardView.this.a.setImageBitmap(bitmap);
                    } else {
                        String str4 = str;
                        if (str4 != null && str4.equals(FadeInCardView.this.j)) {
                            Logger logger2 = L.launcher;
                            logger2.d(" set preview iv bg url " + str);
                            FadeInCardView.this.k = bitmap;
                            FadeInCardView.this.a(str, bitmap);
                        }
                    }
                    L.launcher.i(" start fade out anim ");
                    FadeInCardView.this.f.setText(str2);
                    FadeInCardView.this.g.setText(str3);
                    FadeInCardView.this.p.start();
                    FadeInCardView.this.o.start();
                    FadeInCardView.this.h = true;
                }
            }
        });
    }

    public void a(String str, Bitmap bitmap) {
        a(str, bitmap, new a() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardView$-qt2v7FNhS5IyJAwihRi-2WQgZc
            @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.a
            public final void onGetBitmapSwatchFinished() {
                FadeInCardView.this.a();
            }
        });
    }

    public /* synthetic */ void a() {
        Bitmap bitmap = this.k;
        if (bitmap != null) {
            Canvas canvas = new Canvas(bitmap);
            Bitmap linearGradientBitmap = BitmapUtil.getLinearGradientBitmap(0, this.l, canvas.getWidth(), DisplayUtils.dip2px(getContext(), 152.0f), DisplayUtils.dip2px(getContext(), 32.0f));
            canvas.drawBitmap(linearGradientBitmap, 0.0f, canvas.getHeight() - linearGradientBitmap.getHeight(), new Paint());
            canvas.save();
            canvas.restore();
            this.a.setImageBitmap(this.k);
        }
    }

    private void a(final String str, Bitmap bitmap, final a aVar) {
        try {
            final ImageSwatchCacheManager manager = ImageSwatchCacheManager.getManager();
            this.l = manager.getCachedColor(str);
            if (this.l == 0) {
                Palette.Builder builder = new Palette.Builder(bitmap);
                builder.maximumColorCount(2);
                builder.generate(new Palette.PaletteAsyncListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardView$TS_ypwfvi2mflXgKwrMIJzFZH5M
                    public final void onGenerated(Palette palette) {
                        FadeInCardView.this.a(manager, str, aVar, palette);
                    }
                });
            } else {
                L.launcher.i(" swatch color cached");
                if (aVar != null) {
                    aVar.onGetBitmapSwatchFinished();
                }
            }
        } catch (Exception e) {
            L.launcher.e(e);
            if (aVar != null) {
                aVar.onGetBitmapSwatchFinished();
            }
        }
    }

    public /* synthetic */ void a(ImageSwatchCacheManager imageSwatchCacheManager, String str, a aVar, Palette palette) {
        List<Palette.Swatch> swatches = palette.getSwatches();
        if (swatches != null && swatches.size() > 0) {
            int i = 0;
            Palette.Swatch swatch = null;
            for (Palette.Swatch swatch2 : swatches) {
                if (swatch2 != null && swatch2.getPopulation() > i) {
                    i = swatch2.getPopulation();
                    swatch = swatch2;
                }
            }
            if (swatch != null) {
                this.l = swatch.getRgb();
                imageSwatchCacheManager.addCache(str, Integer.valueOf(this.l));
            }
        }
        if (this.l == 0) {
            L.launcher.e(" error, swatch is null");
            this.l = getResources().getColor(R.color.default_iv_bg);
        }
        if (aVar != null) {
            aVar.onGetBitmapSwatchFinished();
        }
    }
}
