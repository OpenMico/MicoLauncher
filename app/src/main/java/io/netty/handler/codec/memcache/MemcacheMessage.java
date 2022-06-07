package io.netty.handler.codec.memcache;

import io.netty.util.ReferenceCounted;

/* loaded from: classes4.dex */
public interface MemcacheMessage extends MemcacheObject, ReferenceCounted {
    @Override // io.netty.util.ReferenceCounted
    MemcacheMessage retain();

    @Override // io.netty.util.ReferenceCounted
    MemcacheMessage retain(int i);

    @Override // io.netty.util.ReferenceCounted
    MemcacheMessage touch();

    @Override // io.netty.util.ReferenceCounted
    MemcacheMessage touch(Object obj);
}
