package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import com.xiaomi.micolauncher.application.setup.ISetupRule;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.quickapp.PersistUtils;
import com.xiaomi.micolauncher.common.quickapp.QuickAppHelper;
import com.xiaomi.micolauncher.common.quickapp.Worker;
import org.hapjs.features.channel.vui.VuiBridgeManager;

/* loaded from: classes3.dex */
public class QuickAppSetup implements ISetupRule {
    public static final String QUICK_COMPONENT_NAME = "com.miui.hybrid.PersistService";
    public static final String QUICK_PKG_NAME = "com.miui.hybrid.soundbox";
    private Worker a = new Worker();

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        PersistUtils.persist(context, QUICK_PKG_NAME, QUICK_COMPONENT_NAME);
        VuiBridgeManager.getInstance().onInit(context);
        VuiBridgeManager.getInstance().setWorker(this.a);
        if (!EventBusRegistry.getEventBus().isRegistered(this.a)) {
            EventBusRegistry.getEventBus().register(this.a);
        }
        L.quickapp.d("HapChannelSetup invoke setup");
        QuickAppHelper.initChannels(context);
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        PersistUtils.unpersist(QUICK_PKG_NAME, QUICK_COMPONENT_NAME);
        if (EventBusRegistry.getEventBus().isRegistered(this.a)) {
            EventBusRegistry.getEventBus().unregister(this.a);
        }
        QuickAppHelper.clearChannels();
    }
}
