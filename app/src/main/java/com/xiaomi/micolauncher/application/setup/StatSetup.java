package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoSetup;
import com.xiaomi.micolauncher.common.stat.StatJobService;
import com.xiaomi.micolauncher.common.stat.StatUtils;

@MicoSetup
/* loaded from: classes3.dex */
public class StatSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        StatUtils.init(context, Constants.XM_APP_ID, Constants.XM_APP_KEY, Constants.ONETRACK_APP_ID);
        StatJobService.StatJobStarter.start(context);
    }
}
