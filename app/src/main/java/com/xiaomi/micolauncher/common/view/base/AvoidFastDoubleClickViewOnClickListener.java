package com.xiaomi.micolauncher.common.view.base;

import android.view.View;

/* loaded from: classes3.dex */
public abstract class AvoidFastDoubleClickViewOnClickListener implements View.OnClickListener {
    public abstract void onAvoidFastDoubleClick(View view);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!FastClickJudgementManager.isFastDoubleClick()) {
            onAvoidFastDoubleClick(view);
        }
    }
}
