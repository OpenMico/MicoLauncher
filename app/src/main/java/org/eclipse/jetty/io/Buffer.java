package org.eclipse.jetty.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes5.dex */
public interface Buffer extends Cloneable {
    public static final int IMMUTABLE = 0;
    public static final boolean NON_VOLATILE = false;
    public static final int READONLY = 1;
    public static final int READWRITE = 2;
    public static final boolean VOLATILE = true;

    /* loaded from: classes5.dex */
    public interface CaseInsensitve {
    }

    byte[] array();

    byte[] asArray();

    Buffer asImmutableBuffer();

    Buffer asMutableBuffer();

    Buffer asNonVolatileBuffer();

    Buffer asReadOnlyBuffer();

    Buffer buffer();

    int capacity();

    void clear();

    void compact();

    boolean equalsIgnoreCase(Buffer buffer);

    byte get();

    int get(byte[] bArr, int i, int i2);

    Buffer get(int i);

    int getIndex();

    boolean hasContent();

    boolean isImmutable();

    boolean isReadOnly();

    boolean isVolatile();

    int length();

    void mark();

    void mark(int i);

    int markIndex();

    byte peek();

    byte peek(int i);

    int peek(int i, byte[] bArr, int i2, int i3);

    Buffer peek(int i, int i2);

    int poke(int i, Buffer buffer);

    int poke(int i, byte[] bArr, int i2, int i3);

    void poke(int i, byte b);

    int put(Buffer buffer);

    int put(byte[] bArr);

    int put(byte[] bArr, int i, int i2);

    void put(byte b);

    int putIndex();

    int readFrom(InputStream inputStream, int i) throws IOException;

    void reset();

    void setGetIndex(int i);

    void setMarkIndex(int i);

    void setPutIndex(int i);

    int skip(int i);

    Buffer slice();

    Buffer sliceFromMark();

    Buffer sliceFromMark(int i);

    int space();

    String toDetailString();

    String toString(String str);

    String toString(Charset charset);

    void writeTo(OutputStream outputStream) throws IOException;
}
