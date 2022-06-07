package org.eclipse.jetty.io;

import org.eclipse.jetty.io.Buffers;

/* loaded from: classes5.dex */
public class BuffersFactory {
    public static Buffers newBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3, int i3) {
        if (i3 >= 0) {
            return new PooledBuffers(type, i, type2, i2, type3, i3);
        }
        return new ThreadLocalBuffers(type, i, type2, i2, type3);
    }
}
