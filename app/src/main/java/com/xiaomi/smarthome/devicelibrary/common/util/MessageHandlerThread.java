package com.xiaomi.smarthome.devicelibrary.common.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes4.dex */
public class MessageHandlerThread extends HandlerThread {
    private Handler a;

    public MessageHandlerThread(String str) {
        super(str);
    }

    public MessageHandlerThread(String str, int i) {
        super(str, i);
    }

    void a() {
        this.a = new Handler(getLooper()) { // from class: com.xiaomi.smarthome.devicelibrary.common.util.MessageHandlerThread.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                MessageHandlerThread.this.a.sendEmptyMessageDelayed(1, 7200000L);
            }
        };
        this.a.sendEmptyMessageDelayed(1, 7200000L);
    }

    @Override // java.lang.Thread
    public synchronized void start() {
        super.start();
        a();
    }

    @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            if (Looper.myLooper() != null) {
                Log.d("MessageHandlerThread", "looper is not when run start");
            } else {
                super.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(getName(), e);
        }
    }
}
