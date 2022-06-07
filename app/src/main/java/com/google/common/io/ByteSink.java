package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class ByteSink {
    public abstract OutputStream openStream() throws IOException;

    public CharSink asCharSink(Charset charset) {
        return new a(charset);
    }

    public OutputStream openBufferedStream() throws IOException {
        OutputStream openStream = openStream();
        return openStream instanceof BufferedOutputStream ? (BufferedOutputStream) openStream : new BufferedOutputStream(openStream);
    }

    public void write(byte[] bArr) throws IOException {
        Preconditions.checkNotNull(bArr);
        Closer create = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) create.register(openStream());
            outputStream.write(bArr);
            outputStream.flush();
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(InputStream inputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Closer create = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) create.register(openStream());
            long copy = ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
            return copy;
        } finally {
            create.close();
        }
    }

    /* loaded from: classes2.dex */
    private final class a extends CharSink {
        private final Charset b;

        private a(Charset charset) {
            this.b = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSink
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(ByteSink.this.openStream(), this.b);
        }

        public String toString() {
            return ByteSink.this.toString() + ".asCharSink(" + this.b + ")";
        }
    }
}
