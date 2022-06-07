package com.xiaomi.miplay.mylibrary.circulate;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public class VideoCirculatePool {
    private static final String a = "VideoCirculatePool";
    private LinkedBlockingQueue<PlayConfig> b;

    private VideoCirculatePool() {
        this.b = new LinkedBlockingQueue<>();
    }

    public static VideoCirculatePool getInstance() {
        return VideoCirculatePoolSingleton.a;
    }

    /* loaded from: classes4.dex */
    public static class VideoCirculatePoolSingleton {
        private static final VideoCirculatePool a = new VideoCirculatePool();
    }

    public void put(PlayConfig playConfig) {
        try {
            this.b.put(playConfig);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.b.clear();
    }

    public PlayConfig getPlayConfig() {
        Logger.d(a, "getDeviceId.", new Object[0]);
        String str = a;
        Logger.d(str, "payloadQueue:" + this.b.size(), new Object[0]);
        PlayConfig poll = this.b.poll();
        if (poll != null) {
            String str2 = a;
            Logger.d(str2, "deviceId:" + poll.getDeviceId(), new Object[0]);
        }
        return poll;
    }

    public int size() {
        return this.b.size();
    }
}
