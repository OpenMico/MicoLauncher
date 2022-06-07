package org.eclipse.jetty.io.nio;

import java.nio.ByteBuffer;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes5.dex */
public interface NIOBuffer extends Buffer {
    ByteBuffer getByteBuffer();

    boolean isDirect();
}
