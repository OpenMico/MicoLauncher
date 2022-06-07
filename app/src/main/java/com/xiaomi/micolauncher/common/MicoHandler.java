package com.xiaomi.micolauncher.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes3.dex */
public abstract class MicoHandler extends Handler {
    public abstract String getLogTag();

    public abstract void processMessage(Message message);

    public MicoHandler() {
    }

    public MicoHandler(Looper looper) {
        super(looper);
    }

    public MicoHandler(Looper looper, String str, Handler.Callback callback) {
        super(looper, new a(str, callback));
    }

    public MicoHandler(String str, Handler.Callback callback) {
        super(new a(str, callback));
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        L.profile.d("%s : handle message what %d, arg1 %d, arg2 %d", getLogTag(), Integer.valueOf(message.what), Integer.valueOf(message.arg1), Integer.valueOf(message.arg2));
        processMessage(message);
    }

    /* loaded from: classes3.dex */
    private static class a implements Handler.Callback {
        private final Handler.Callback a;
        private final String b;

        private a(String str, Handler.Callback callback) {
            this.a = callback;
            this.b = str;
            L.profile.d("callback is %s", callback);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            L.profile.d("%s handle message %d", this.b, Integer.valueOf(message.what));
            return this.a.handleMessage(message);
        }
    }
}
