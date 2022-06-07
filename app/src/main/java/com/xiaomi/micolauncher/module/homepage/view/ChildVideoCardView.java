package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.Target;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* loaded from: classes3.dex */
public class ChildVideoCardView extends LinearLayout {
    private ImageView a;
    private TextView b;
    private FrameLayout c;
    private int d;
    private GradientDrawable e;
    private int f;
    private ChildVideo.ItemsBean g;

    public ChildVideoCardView(@NonNull Context context) {
        this(context, null);
    }

    public ChildVideoCardView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildVideoCardView(@NonNull final Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.layout_child_video_card_view, this);
        this.a = (ImageView) findViewById(R.id.image_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (FrameLayout) findViewById(R.id.titleContainer);
        a(context, attributeSet);
        FrameLayout frameLayout = this.c;
        if (frameLayout != null) {
            Drawable background = frameLayout.getBackground();
            if (background instanceof GradientDrawable) {
                this.e = (GradientDrawable) background;
                GradientDrawable gradientDrawable = this.e;
                int i2 = this.f;
                gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, i2, i2, i2, i2});
            }
        }
        setOrientation(1);
        setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.view.ChildVideoCardView.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                if (ChildVideoCardView.this.g == null) {
                    L.launcher.i("ChildVideoCardView itemsBean is empty");
                } else {
                    ChildPlayUtil.playVideo(context, ChildVideoCardView.this.g.getMediaId(), ChildVideoCardView.this.g.getTitle(), ChildVideoCardView.this.g.getItemImageUrl(), ChildVideoCardView.this.g.getMiTvType());
                }
            }
        });
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ChildHotSongCardView);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(2, 0);
        if (dimensionPixelOffset > 0) {
            setTitleContainerHeight(dimensionPixelOffset);
        }
        this.f = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        Drawable drawable = this.a.getDrawable();
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setCornerRadius(this.f);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        if (dimensionPixelSize > 0) {
            this.b.setTextSize(dimensionPixelSize);
        }
        this.d = obtainStyledAttributes.getResourceId(0, R.drawable.home_child_song_img_holder_with_bg_color_4342ff);
        this.a.setBackgroundResource(this.d);
        obtainStyledAttributes.recycle();
    }

    public void setTitleContainerHeight(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.c.getLayoutParams();
        layoutParams.height = i;
        this.c.setLayoutParams(layoutParams);
    }

    public void updateTitleAndImage(IListItem iListItem, int i) {
        if (iListItem instanceof ChildVideo.ItemsBean) {
            this.g = (ChildVideo.ItemsBean) iListItem;
            this.b.setText(this.g.getTitle());
            GlideUtils.bindImageView(this.a, this.g.getItemImageUrl(), i, new RoundedCornersTransformation(this.f, 0, RoundedCornersTransformation.CornerType.TOP), (Target<Bitmap>) null);
        }
    }
}
