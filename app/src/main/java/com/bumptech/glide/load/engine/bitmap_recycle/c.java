package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.f;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseKeyPool.java */
/* loaded from: classes.dex */
public abstract class c<T extends f> {
    private final Queue<T> a = Util.createQueue(20);

    abstract T b();

    T c() {
        T poll = this.a.poll();
        return poll == null ? b() : poll;
    }

    public void a(T t) {
        if (this.a.size() < 20) {
            this.a.offer(t);
        }
    }
}
