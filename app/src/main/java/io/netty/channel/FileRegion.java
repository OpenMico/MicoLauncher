package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes4.dex */
public interface FileRegion extends ReferenceCounted {
    long count();

    long position();

    @Override // io.netty.util.ReferenceCounted
    FileRegion retain();

    @Override // io.netty.util.ReferenceCounted
    FileRegion retain(int i);

    @Override // io.netty.util.ReferenceCounted
    FileRegion touch();

    @Override // io.netty.util.ReferenceCounted
    FileRegion touch(Object obj);

    long transferTo(WritableByteChannel writableByteChannel, long j) throws IOException;

    @Deprecated
    long transfered();

    long transferred();
}
