package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.base.utils.Cache;
import com.xiaomi.mico.base.utils.VersionUtils;
import com.xiaomi.micolauncher.application.Constants;
import java.io.File;
import java.io.IOException;

/* loaded from: classes3.dex */
public class CacheSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        try {
            Cache.init(new File(Constants.getCacheDirectory(context)), VersionUtils.getVersionCode(context), 20971520L);
        } catch (IOException e) {
            XLog.e(e);
        }
    }
}
