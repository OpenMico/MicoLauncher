package com.xiaomi.micolauncher.common.widget;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.R;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;

/* loaded from: classes3.dex */
public class MaterialRefreshHeader implements RefreshHeader {
    private static final long ANIMATION_DURATION = 2000;
    private static final float END_ANGLE = 360.0f;
    private static final String PROPERTY_ROTATION = "rotation";
    private ObjectAnimator animator;
    private ImageView imageView;
    private final RefreshLayout.Direction mDirection;

    @Override // com.xiaomi.micolauncher.common.widget.RefreshHeader
    public void onDragging(float f, float f2, View view) {
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshHeader
    public void onReadyToRelease(View view) {
    }

    public MaterialRefreshHeader(RefreshLayout.Direction direction) {
        this.mDirection = direction;
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshHeader
    public void onCancel() {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    public void setImageResource(int i) {
        this.imageView.setImageResource(i);
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshHeader
    @NonNull
    public View getView(ViewGroup viewGroup) {
        ViewGroup viewGroup2;
        if (this.mDirection == RefreshLayout.Direction.HORIZONTAL) {
            viewGroup2 = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refresh_header_horizontal, viewGroup, false);
        } else {
            viewGroup2 = (ViewGroup) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.refresh_header_vertical, viewGroup, false);
        }
        this.imageView = (ImageView) viewGroup2.findViewById(R.id.refresh_img);
        this.animator = ObjectAnimator.ofFloat(this.imageView, PROPERTY_ROTATION, 0.0f, END_ANGLE);
        this.animator.setDuration(2000L);
        this.animator.setRepeatCount(-1);
        return viewGroup2;
    }

    @Override // com.xiaomi.micolauncher.common.widget.RefreshHeader
    public void onRefreshing(View view) {
        ObjectAnimator objectAnimator = this.animator;
        if (objectAnimator != null) {
            objectAnimator.start();
        }
    }
}
