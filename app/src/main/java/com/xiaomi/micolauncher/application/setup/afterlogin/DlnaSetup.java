package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.common.dlna.DlnaDevice;

/* loaded from: classes3.dex */
public class DlnaSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        L.base.i("Dlna start");
        DlnaDevice.getInstance().start(context);
        L.base.i("Dlna start done");
    }
}
