package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;

/* loaded from: classes3.dex */
public class ChildHotSongCardView extends FrameLayout {
    ImageView a;
    TextView b;
    @Nullable
    FrameLayout c;
    @Nullable
    private GradientDrawable d;
    private int e;

    public ChildHotSongCardView(@NonNull Context context) {
        this(context, null);
    }

    public ChildHotSongCardView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildHotSongCardView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.layout_child_mode_card_view, this);
        this.a = (ImageView) findViewById(R.id.image_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (FrameLayout) findViewById(R.id.titleContainer);
        a(context, attributeSet);
        FrameLayout frameLayout = this.c;
        if (frameLayout != null) {
            Drawable background = frameLayout.getBackground();
            if (background instanceof GradientDrawable) {
                this.d = (GradientDrawable) background;
                GradientDrawable gradientDrawable = this.d;
                int i2 = this.e;
                gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, i2, i2, i2, i2});
            }
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChildHotSongCardView);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        if (dimensionPixelOffset > 0) {
            setTitleContainerHeight(dimensionPixelOffset);
        }
        this.e = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        Drawable drawable = this.a.getDrawable();
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setCornerRadius(this.e);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        if (dimensionPixelSize > 0) {
            this.b.setTextSize(dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    public void setTitleContainerHeight(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.c.getLayoutParams();
        layoutParams.height = i;
        this.c.setLayoutParams(layoutParams);
    }

    public void updateTitleAndImage(String str, String str2, int i) {
        this.b.setText(str);
        ImageView imageView = this.a;
        GlideUtils.bindImageViewWithRoundCorners(imageView, str2, this.e, i, imageView.getWidth(), this.a.getHeight());
    }
}
