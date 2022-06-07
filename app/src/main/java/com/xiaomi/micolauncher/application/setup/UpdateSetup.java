package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.base.utils.VersionUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;

/* loaded from: classes3.dex */
public class UpdateSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(final Context context) {
        RomUpdateAdapter.init(context);
        L.startuplauncher.i("starting launcher (app version name %s, version code %s), ROM (version %s, channel %s %s), daily (%B), debug (%B), Model %s, %s, has camera %s", VersionUtils.getVersionName(context), Integer.valueOf(VersionUtils.getVersionCode(context)), RomUpdateAdapter.getInstance().getVersion(), RomUpdateAdapter.getInstance().getChannel(), RomUpdateAdapter.getInstance().getChannelString(), Boolean.valueOf(DebugHelper.isDaily()), Boolean.valueOf(DebugHelper.isDebugInConfg()), Hardware.getBuildModel(), SystemSetting.getHardWareModel(), Boolean.valueOf(Hardware.hasCameraCapability(context)));
        Threads.getLightWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.application.setup.-$$Lambda$UpdateSetup$fZkRO1kZtyLtwzDAOdfujUeqAwM
            @Override // java.lang.Runnable
            public final void run() {
                UpdateSetup.a(context);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context) {
        boolean screenOffAfterReboot = SystemSetting.getScreenOffAfterReboot();
        Logger logger = L.startuplauncher;
        logger.d("checkScreen, turn screen off after reboot:" + screenOffAfterReboot);
        if (screenOffAfterReboot) {
            ScreenUtil.turnScreenOff(context);
            SystemSetting.setScreenOffAfterReboot(false);
        }
    }
}
