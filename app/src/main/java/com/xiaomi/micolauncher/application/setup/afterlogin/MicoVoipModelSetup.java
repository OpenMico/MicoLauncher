package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.application.setup.AbsSyncSetup;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;

/* loaded from: classes3.dex */
public class MicoVoipModelSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(final Context context) {
        L.voip.i("bind MicoVoipService");
        Threads.getHeavyWorkHandler().post(new Runnable() { // from class: com.xiaomi.micolauncher.application.setup.afterlogin.-$$Lambda$MicoVoipModelSetup$cMBlYJW2VSdC8EZb8lJ5TSuMFXw
            @Override // java.lang.Runnable
            public final void run() {
                MicoVoipModelSetup.a(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context) {
        MicoVoipClient.getInstance(context).a(context);
    }
}
