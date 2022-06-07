package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.skills.video.model.IqiyiModel;

/* loaded from: classes3.dex */
public class IqiyiModelSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        IqiyiModel.getInstance().init(context);
    }
}
