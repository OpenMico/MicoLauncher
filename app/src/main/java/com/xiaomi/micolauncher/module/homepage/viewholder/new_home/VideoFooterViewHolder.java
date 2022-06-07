package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class VideoFooterViewHolder extends BaseViewHolder {
    TextView a;
    Button b;
    private ObjectAnimator c;
    private LoadMoreCallback d;
    private OnFooterRefreshCallback e;
    private boolean f;

    /* loaded from: classes3.dex */
    public interface LoadMoreCallback {
        void loadMore();
    }

    /* loaded from: classes3.dex */
    public interface OnFooterRefreshCallback {
        void onFooterRefresh();
    }

    public VideoFooterViewHolder(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.loading_text);
        this.b = (Button) view.findViewById(R.id.refresh);
        if (this.c == null) {
            this.c = ObjectAnimator.ofFloat(this.a, "alpha", 0.1f, 0.4f);
            this.c.setDuration(600L);
            this.c.setInterpolator(new LinearInterpolator());
            this.c.setRepeatMode(2);
            this.c.setRepeatCount(-1);
        }
    }

    public void start() {
        L.homepage.i("jiangliang onBindViewHolder start hasmore : %b", Boolean.valueOf(this.f));
        refreshUI();
        if (this.f) {
            L.homepage.i("jiangliang onBindViewHolder start hasmore ..........");
            this.c.start();
            LoadMoreCallback loadMoreCallback = this.d;
            if (loadMoreCallback != null) {
                loadMoreCallback.loadMore();
            }
        }
    }

    public void hasMore(boolean z) {
        this.f = z;
    }

    public void refreshUI() {
        int i = 0;
        this.a.setVisibility(this.f ? 0 : 8);
        Button button = this.b;
        if (this.f) {
            i = 8;
        }
        button.setVisibility(i);
    }

    public void stop() {
        this.c.cancel();
    }

    public VideoFooterViewHolder setLoadMoreCallback(LoadMoreCallback loadMoreCallback) {
        this.d = loadMoreCallback;
        return this;
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    protected void initViewInMain() {
        AnimLifecycleManager.repeatOnAttach(this.b, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoFooterViewHolder$td8sIaLsjDRFLgNxtYWIzsVIGfQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoFooterViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        OnFooterRefreshCallback onFooterRefreshCallback = this.e;
        if (onFooterRefreshCallback != null) {
            onFooterRefreshCallback.onFooterRefresh();
        }
    }

    public VideoFooterViewHolder setCallback(OnFooterRefreshCallback onFooterRefreshCallback) {
        this.e = onFooterRefreshCallback;
        return this;
    }
}
