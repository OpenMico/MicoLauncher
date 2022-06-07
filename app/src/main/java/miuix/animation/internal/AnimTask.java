package miuix.animation.internal;

import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.LinkNode;

/* loaded from: classes5.dex */
public class AnimTask extends LinkNode<AnimTask> implements Runnable {
    public static final int MAX_PAGE_SIZE = 150;
    public static final int MAX_SINGLE_TASK_SIZE = 4000;
    public static final int MAX_TO_PAGE_SIZE = 500;
    public static final byte OP_CANCEL = 4;
    public static final byte OP_END = 3;
    public static final byte OP_FAILED = 5;
    public static final byte OP_INVALID = 0;
    public static final byte OP_START = 1;
    public static final byte OP_UPDATE = 2;
    public static final AtomicInteger sTaskCount = new AtomicInteger();
    public final c animStats = new c();
    public volatile long deltaT;
    public volatile TransitionInfo info;
    public volatile int startPos;
    public volatile boolean toPage;
    public volatile long totalT;

    public static boolean isRunning(byte b) {
        return b == 1 || b == 2;
    }

    public void setup(int i, int i2) {
        this.animStats.clear();
        this.animStats.g = i2;
        this.startPos = i;
    }

    public void start(long j, long j2, boolean z) {
        this.totalT = j;
        this.deltaT = j2;
        this.toPage = z;
        ThreadPoolUtil.post(this);
    }

    public int getAnimCount() {
        return this.animStats.g;
    }

    public int getTotalAnimCount() {
        int i = 0;
        for (AnimTask animTask = this; animTask != null; animTask = (AnimTask) animTask.next) {
            i += animTask.animStats.g;
        }
        return i;
    }

    public void a() {
        int i = this.startPos + this.animStats.g;
        for (int i2 = this.startPos; i2 < i; i2++) {
            UpdateInfo updateInfo = this.info.j.get(i2);
            if (updateInfo != null) {
                if (updateInfo.animInfo.op == 0 || updateInfo.animInfo.op == 1) {
                    this.animStats.a++;
                } else {
                    this.animStats.b++;
                    switch (updateInfo.animInfo.op) {
                        case 3:
                            this.animStats.f++;
                            continue;
                        case 4:
                            this.animStats.e++;
                            continue;
                        case 5:
                            this.animStats.c++;
                            continue;
                    }
                }
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            b.a(this, this.totalT, this.deltaT, true, this.toPage);
        } catch (Exception e) {
            Log.d(CommonUtils.TAG, "doAnimationFrame failed", e);
        }
        if (sTaskCount.decrementAndGet() == 0) {
            AnimRunner.sRunnerHandler.sendEmptyMessage(2);
        }
    }
}
