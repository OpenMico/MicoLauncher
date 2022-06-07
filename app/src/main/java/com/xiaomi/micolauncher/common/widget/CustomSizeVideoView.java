package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.widget.VideoView;

/* loaded from: classes3.dex */
public class CustomSizeVideoView extends VideoView {
    public CustomSizeVideoView(Context context) {
        super(context);
    }

    public CustomSizeVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomSizeVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
    }

    @Override // android.widget.VideoView
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        super.setOnPreparedListener(onPreparedListener);
    }
}
