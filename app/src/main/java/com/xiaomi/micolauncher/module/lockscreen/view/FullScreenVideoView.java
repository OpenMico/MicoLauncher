package com.xiaomi.micolauncher.module.lockscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/* loaded from: classes3.dex */
public class FullScreenVideoView extends VideoView {
    public FullScreenVideoView(Context context) {
        this(context, null);
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FullScreenVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
    }
}
