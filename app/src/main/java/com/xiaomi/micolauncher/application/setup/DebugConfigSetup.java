package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import android.os.Environment;
import com.xiaomi.mico.base.utils.FileUtils;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.common.L;
import java.io.File;

/* loaded from: classes3.dex */
public class DebugConfigSetup implements ISetupRule {
    private static final String a = new File(Environment.getExternalStorageDirectory(), "dumpPref").getAbsolutePath();
    private volatile boolean b;

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        this.b = FileUtils.existsFile(a, true);
        if (this.b) {
            L.base.i("start dump default pref in DebugConfigSetup");
            PreferenceUtils.dumpDefaultPreference(context);
        }
    }

    public boolean isDumpPreference() {
        return this.b;
    }
}
