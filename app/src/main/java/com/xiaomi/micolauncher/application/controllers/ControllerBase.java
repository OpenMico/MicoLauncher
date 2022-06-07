package com.xiaomi.micolauncher.application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public class ControllerBase {
    private final Context a;

    public ControllerBase(Context context) {
        this.a = context;
    }

    @NonNull
    protected Context getContext() {
        return this.a;
    }

    protected <T extends Activity> Intent getIntentOfActivity(Class<T> cls) {
        return new Intent(this.a, (Class<?>) cls);
    }
}
