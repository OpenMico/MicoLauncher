package com.xiaomi.micolauncher.skills.music.view_v2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class PlayListRefreshFooter extends SimpleComponent implements RefreshFooter {
    private ProgressBar a;

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    @NonNull
    public View getView() {
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onHorizontalDrag(float f, int i, int i2) {
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onMoving(boolean z, float f, int i, int i2, int i3) {
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i2) {
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean z) {
        return false;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void setPrimaryColors(int... iArr) {
    }

    public PlayListRefreshFooter(Context context) {
        this(context, null);
    }

    public PlayListRefreshFooter(Context context, @Nullable AttributeSet attributeSet) {
        this(context, null, 0);
    }

    public PlayListRefreshFooter(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        View.inflate(context, R.layout.play_list_loading_header, this);
        setGravity(17);
        this.a = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void setMargins(int i, int i2, int i3, int i4) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.a.getLayoutParams();
        layoutParams.setMargins(i, i2, i3, i4);
        this.a.setLayoutParams(layoutParams);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onStartAnimator(RefreshLayout refreshLayout, int i, int i2) {
        this.a.setVisibility(0);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(RefreshLayout refreshLayout, boolean z) {
        this.a.setVisibility(8);
        return 500;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        switch (refreshState2) {
            case None:
            case PullDownToRefresh:
            case Refreshing:
                this.a.setVisibility(0);
                return;
            default:
                return;
        }
    }
}
