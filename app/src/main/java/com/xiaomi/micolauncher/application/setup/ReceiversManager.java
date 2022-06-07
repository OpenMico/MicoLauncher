package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.module.AppOpenBroadcastReceiver;
import com.xiaomi.micolauncher.module.SchemaReceiver;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenLifeCycleReceiver;

/* loaded from: classes3.dex */
public class ReceiversManager extends AbsSyncSetup {
    private IAppBroadcastReceiverRule[] a;

    /* loaded from: classes3.dex */
    public interface IAppBroadcastReceiverRule {
        void destroy();

        void register();
    }

    public void register(Context context) {
        this.a = new IAppBroadcastReceiverRule[]{new SchemaReceiver(context), new LockScreenLifeCycleReceiver(context), new AppOpenBroadcastReceiver(context)};
        IAppBroadcastReceiverRule[] iAppBroadcastReceiverRuleArr = this.a;
        for (IAppBroadcastReceiverRule iAppBroadcastReceiverRule : iAppBroadcastReceiverRuleArr) {
            if (iAppBroadcastReceiverRule != null) {
                iAppBroadcastReceiverRule.register();
            }
        }
    }

    public void unRegister() {
        if (!ContainerUtil.isEmpty(this.a)) {
            IAppBroadcastReceiverRule[] iAppBroadcastReceiverRuleArr = this.a;
            for (IAppBroadcastReceiverRule iAppBroadcastReceiverRule : iAppBroadcastReceiverRuleArr) {
                if (iAppBroadcastReceiverRule != null) {
                    iAppBroadcastReceiverRule.destroy();
                }
            }
        }
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        register(context);
    }

    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
        unRegister();
    }
}
