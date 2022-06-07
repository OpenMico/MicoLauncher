package com.xiaomi.micolauncher.api.exception;

import android.content.Intent;

/* loaded from: classes3.dex */
public class NeedUserInteractionException extends Exception {
    private static final long serialVersionUID = 4951225316343246487L;
    private Intent mIntent;

    public NeedUserInteractionException(Intent intent) {
        super("User Interaction Needed.");
        this.mIntent = intent;
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
