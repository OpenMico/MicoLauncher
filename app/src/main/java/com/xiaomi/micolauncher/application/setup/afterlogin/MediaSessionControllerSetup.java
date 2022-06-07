package com.xiaomi.micolauncher.application.setup.afterlogin;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;
import com.xiaomi.micolauncher.application.setup.AbsSyncSetup;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;

/* loaded from: classes3.dex */
public class MediaSessionControllerSetup extends AbsSyncSetup {
    @Override // com.xiaomi.micolauncher.application.setup.AbsSyncSetup, com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        Looper.getMainLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.xiaomi.micolauncher.application.setup.afterlogin.MediaSessionControllerSetup.1
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                MediaSessionController.getInstance().initService();
                return false;
            }
        });
        MediaSessionController.init(context);
        L.base.d("MediaSessionControllerSetup init MediaSessionController.");
    }
}
