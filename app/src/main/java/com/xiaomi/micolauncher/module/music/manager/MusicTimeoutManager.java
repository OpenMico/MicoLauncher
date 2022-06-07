package com.xiaomi.micolauncher.module.music.manager;

import android.os.Message;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MusicTimeoutManager {
    private static final long a = TimeUnit.MINUTES.toMillis(1);
    private WeakRefHandler b = new WeakRefHandler($$Lambda$MusicTimeoutManager$g4sfGhHTKwj_I_efXSiTEW_Coyk.INSTANCE);

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Message message) {
        if (message.what != 1) {
            return false;
        }
        EventBusRegistry.getEventBus().post(new MusicTimeoutEvent());
        return false;
    }

    /* loaded from: classes3.dex */
    private static class a {
        private static MusicTimeoutManager a = new MusicTimeoutManager();
    }

    public static MusicTimeoutManager getManager() {
        return a.a;
    }

    public void startRecord() {
        this.b.removeMessages(1);
        this.b.sendEmptyMessageDelayed(1, a);
    }

    public void stopRecord() {
        this.b.removeMessages(1);
    }
}
