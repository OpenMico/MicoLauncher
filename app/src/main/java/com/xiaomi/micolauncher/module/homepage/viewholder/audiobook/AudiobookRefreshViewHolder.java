package com.xiaomi.micolauncher.module.homepage.viewholder.audiobook;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4SmallButton;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class AudiobookRefreshViewHolder extends BaseViewHolder {
    Button a;
    private OnFooterRefreshCallback b;

    /* loaded from: classes3.dex */
    public interface OnFooterRefreshCallback {
        void onFooterRefresh();
    }

    public AudiobookRefreshViewHolder(View view) {
        super(view);
        this.a = (Button) view.findViewById(R.id.refresh);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    protected void initViewInMain() {
        this.a.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.book_button_refresh, 0, 0);
        AnimLifecycleManager.repeatOnAttach(this.a, MicoAnimConfigurator4SmallButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.-$$Lambda$AudiobookRefreshViewHolder$46sOVwOrjWS9rlGzk7axB0BxY3Q
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookRefreshViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        OnFooterRefreshCallback onFooterRefreshCallback = this.b;
        if (onFooterRefreshCallback != null) {
            onFooterRefreshCallback.onFooterRefresh();
        }
    }

    public AudiobookRefreshViewHolder setCallback(OnFooterRefreshCallback onFooterRefreshCallback) {
        this.b = onFooterRefreshCallback;
        return this;
    }
}
