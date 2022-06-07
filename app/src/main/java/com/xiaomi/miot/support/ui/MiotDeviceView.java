package com.xiaomi.miot.support.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.MiotLog;
import com.xiaomi.miot.support.R;
import com.xiaomi.miot.support.core.MiotDeviceEvent;
import com.xiaomi.miot.support.ui.MiotDeviceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MiotDeviceView extends FrameLayout implements MiotDeviceHelper.DevicesStatusCallback {
    private View mEmptyView;
    protected MiotDeviceHelper mHelper;
    private ImageView mIvFailed;
    private View mLoadingView;
    private ProgressBar mProgressBar;
    private TextView mTv;

    protected abstract View getDeviceView();

    protected abstract void setupAdapter(List<DeviceInfo> list, DeviceOperator deviceOperator);

    protected abstract void updateDeviceList(List<DeviceInfo> list);

    protected void updateEvents(List<MiotDeviceEvent> list, boolean z) {
    }

    public MiotDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mEmptyView = findViewById(R.id.miot_support_device_empty);
        this.mLoadingView = findViewById(R.id.fl_load_bg);
        this.mIvFailed = (ImageView) findViewById(R.id.iv_load_failed);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        this.mTv = (TextView) findViewById(R.id.tv);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mHelper = new MiotDeviceHelper(getContext());
        this.mHelper.registerCallback(this);
        MiotLog.i("init (" + this.mHelper.getDevices().size() + ") devices");
        if (!this.mHelper.getDevices().isEmpty() && !this.mHelper.getEvents().isEmpty()) {
            updateEvents(this.mHelper.getEvents(), true);
        }
        if (this.mHelper.getDevices().isEmpty()) {
            showLoadingView();
        }
        setupAdapter(this.mHelper.getDevices(), this.mHelper);
        refreshDevices();
        this.mIvFailed.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.miot.support.ui.MiotDeviceView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MiotDeviceView.this.showLoadingView();
                MiotDeviceView.this.refreshDevices();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHelper.unregisterCallback();
    }

    public void refreshDevices() {
        MiotLog.i("Info: refresh device in MiotDeviceView");
        MiotDeviceHelper miotDeviceHelper = this.mHelper;
        if (miotDeviceHelper != null) {
            miotDeviceHelper.refreshDevices();
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public final void onDevicesRefreshed() {
        MiotLog.i("refresh (" + this.mHelper.getDevices().size() + ") devices");
        if (!this.mHelper.getEvents().isEmpty()) {
            updateEvents(this.mHelper.getEvents(), false);
        }
        updateDeviceList(this.mHelper.getDevices());
        if (!this.mHelper.getDevices().isEmpty()) {
            showDeviceView();
        } else if (this.mHelper.getOriginDeviceCount() > 0) {
            showEmptyView();
        } else {
            showLoadErrorView();
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public final void onDevicesFailed() {
        if (!this.mHelper.getDevices().isEmpty()) {
            return;
        }
        if (this.mHelper.getOriginDeviceCount() > 0) {
            showEmptyView();
        } else {
            showLoadErrorView();
        }
    }

    @Override // com.xiaomi.miot.support.ui.MiotDeviceHelper.DevicesStatusCallback
    public final void onEventsUpdated() {
        if (!this.mHelper.getDevices().isEmpty()) {
            updateEvents(this.mHelper.getEvents(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @CallSuper
    public void showDeviceView() {
        getDeviceView().setVisibility(0);
        this.mEmptyView.setVisibility(8);
        this.mLoadingView.setVisibility(8);
    }

    private void showEmptyView() {
        getDeviceView().setVisibility(8);
        this.mEmptyView.setVisibility(0);
        this.mLoadingView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingView() {
        this.mProgressBar.setVisibility(0);
        this.mIvFailed.setVisibility(8);
        this.mLoadingView.setVisibility(0);
        this.mTv.setText(getResources().getString(R.string.miot_support_loading));
    }

    private void showLoadErrorView() {
        this.mProgressBar.setVisibility(4);
        this.mIvFailed.setVisibility(0);
        this.mLoadingView.setVisibility(0);
        this.mTv.setText(getResources().getString(R.string.miot_support_load_failed));
    }
}
