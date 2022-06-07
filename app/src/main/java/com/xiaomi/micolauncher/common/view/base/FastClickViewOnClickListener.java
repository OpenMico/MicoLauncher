package com.xiaomi.micolauncher.common.view.base;

import android.view.View;

/* loaded from: classes3.dex */
public abstract class FastClickViewOnClickListener implements View.OnClickListener {
    public abstract void onFastClickView(View view);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!FastClickJudgementManager.isFastContinuousClick()) {
            onFastClickView(view);
        }
    }
}
