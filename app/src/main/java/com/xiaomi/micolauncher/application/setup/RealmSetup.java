package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import io.realm.Realm;

/* loaded from: classes3.dex */
public class RealmSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        Realm.init(context);
    }
}
