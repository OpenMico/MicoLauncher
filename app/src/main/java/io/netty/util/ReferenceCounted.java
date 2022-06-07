package io.netty.util;

/* loaded from: classes4.dex */
public interface ReferenceCounted {
    int refCnt();

    boolean release();

    boolean release(int i);

    ReferenceCounted retain();

    ReferenceCounted retain(int i);

    ReferenceCounted touch();

    ReferenceCounted touch(Object obj);
}
