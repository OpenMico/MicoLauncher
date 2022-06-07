package com.xiaomi.micolauncher.module.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.xiaomi.mico.base.utils.BitmapUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.main.manager.ImageSwatchCacheManager;

/* loaded from: classes3.dex */
public class CardViewForChildMode extends FrameLayout {
    ImageView a;
    TextView b;
    View c;
    private GradientDrawable d;
    private b e;
    private Context f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface a {
        void onGetBitmapSwatchFinished(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface b {
        void onImageLoaded(Bitmap bitmap);
    }

    public CardViewForChildMode(@NonNull Context context) {
        this(context, null);
    }

    public CardViewForChildMode(@NonNull Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CardViewForChildMode(@NonNull Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = context;
        LayoutInflater.from(context).inflate(R.layout.view_card_for_child_mode, this);
        this.a = (ImageView) findViewById(R.id.image_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = findViewById(R.id.titleContainer);
        View view = this.c;
        Drawable background = view != null ? view.getBackground() : null;
        if (background instanceof GradientDrawable) {
            this.d = (GradientDrawable) background;
        }
    }

    public void updateTitleAndImage(final String str, final String str2, final int i, int i2) {
        Context context = this.f;
        ImageView imageView = this.a;
        GlideUtils.bindImageViewWithListener(context, str2, imageView, i2, new ImageViewTarget<Bitmap>(imageView) { // from class: com.xiaomi.micolauncher.module.main.CardViewForChildMode.1
            @Override // com.bumptech.glide.request.target.ImageViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                L.launcher.i(" load image failed");
            }

            /* JADX INFO: Access modifiers changed from: protected */
            /* renamed from: a */
            public void setResource(@Nullable Bitmap bitmap) {
                if (!ActivityLifeCycleManager.isInvalidActivity(CardViewForChildMode.this.f) && bitmap != null) {
                    CardViewForChildMode.this.a(bitmap, str2, i, str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bitmap bitmap, String str, int i, String str2) {
        Drawable drawable;
        ImageView imageView = this.a;
        if (!(imageView == null || bitmap == null)) {
            imageView.setImageBitmap(bitmap);
        }
        a(str, bitmap);
        if (i == 0) {
            drawable = null;
        } else {
            drawable = getResources().getDrawable(i, null);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        TextView textView = this.b;
        if (textView != null) {
            textView.setCompoundDrawables(drawable, null, null, null);
            this.b.setText(str2);
        }
        b bVar = this.e;
        if (bVar != null) {
            bVar.onImageLoaded(bitmap);
        }
    }

    private void a(String str, Bitmap bitmap) {
        a(str, bitmap, new a() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$CardViewForChildMode$1fDOYiAodzi5o4RP43TuVMgAJ_I
            @Override // com.xiaomi.micolauncher.module.main.CardViewForChildMode.a
            public final void onGetBitmapSwatchFinished(int i) {
                CardViewForChildMode.this.a(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i) {
        GradientDrawable gradientDrawable = this.d;
        if (gradientDrawable != null) {
            gradientDrawable.setColor(i);
            View view = this.c;
            if (view != null) {
                view.setBackground(this.d);
            }
        }
    }

    private void a(String str, Bitmap bitmap, a aVar) {
        ImageSwatchCacheManager manager = ImageSwatchCacheManager.getManager();
        int cachedColor = manager.getCachedColor(str);
        if (cachedColor == 0) {
            int majorColor = BitmapUtil.getMajorColor(bitmap);
            manager.addCache(str, Integer.valueOf(majorColor));
            if (majorColor == 0) {
                L.launcher.e("CardViewForChildMode$getCurrentSwatchAsync error, swatch is null");
                majorColor = this.f.getColor(R.color.default_iv_bg);
            }
            if (aVar != null) {
                aVar.onGetBitmapSwatchFinished(majorColor);
                return;
            }
            return;
        }
        L.launcher.i("CardViewForChildMode$getCurrentSwatchAsync swatch color cached");
        if (aVar != null) {
            aVar.onGetBitmapSwatchFinished(cachedColor);
        }
    }

    public void setImageLoadListener(@org.jetbrains.annotations.Nullable b bVar) {
        this.e = bVar;
    }
}
