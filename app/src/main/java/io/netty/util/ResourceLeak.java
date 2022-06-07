package io.netty.util;

/* loaded from: classes4.dex */
public interface ResourceLeak {
    boolean close();

    void record();

    void record(Object obj);
}
