package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.Target;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;

/* loaded from: classes3.dex */
public class MusicRecommendItemView extends BaseMusicView {
    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseMusicView
    protected int layoutId() {
        return R.layout.music_recommend_item;
    }

    public MusicRecommendItemView(@NonNull Context context) {
        this(context, null);
    }

    public MusicRecommendItemView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicRecommendItemView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseMusicView
    public void initInMain() {
        super.initInMain();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.view.BaseMusicView
    protected void setCoverImg() {
        GlideUtils.bindImageView(this.a, getItem().getItemImageUrl(), (int) R.drawable.app_icon_placeholder, UiUtils.getSize(getContext(), R.dimen.recommend_title_height), (Target<Bitmap>) null, UiUtils.getCornerRadius());
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        long beginTiming = DebugHelper.beginTiming();
        super.onMeasure(i, i2);
        DebugHelper.endTiming(beginTiming, this + " on measure", new Object[0]);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        long beginTiming = DebugHelper.beginTiming();
        super.onLayout(z, i, i2, i3, i4);
        DebugHelper.endTiming(beginTiming, this + " on layout", new Object[0]);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        long beginTiming = DebugHelper.beginTiming();
        super.onDraw(canvas);
        DebugHelper.endTiming(beginTiming, this + " on draw", new Object[0]);
    }
}
