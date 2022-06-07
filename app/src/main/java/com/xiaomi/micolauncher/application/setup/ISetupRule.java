package com.xiaomi.micolauncher.application.setup;

import android.content.Context;

/* loaded from: classes3.dex */
public interface ISetupRule {
    boolean mustBeSync(Context context);

    void onDestroy();

    void setup(Context context);
}
