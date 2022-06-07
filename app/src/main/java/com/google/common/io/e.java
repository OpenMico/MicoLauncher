package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: MultiReader.java */
@GwtIncompatible
/* loaded from: classes2.dex */
class e extends Reader {
    private final Iterator<? extends CharSource> a;
    @NullableDecl
    private Reader b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Iterator<? extends CharSource> it) throws IOException {
        this.a = it;
        a();
    }

    private void a() throws IOException {
        close();
        if (this.a.hasNext()) {
            this.b = ((CharSource) this.a.next()).openStream();
        }
    }

    @Override // java.io.Reader
    public int read(@NullableDecl char[] cArr, int i, int i2) throws IOException {
        Reader reader = this.b;
        if (reader == null) {
            return -1;
        }
        int read = reader.read(cArr, i, i2);
        if (read != -1) {
            return read;
        }
        a();
        return read(cArr, i, i2);
    }

    @Override // java.io.Reader
    public long skip(long j) throws IOException {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        Preconditions.checkArgument(i >= 0, "n is negative");
        if (i > 0) {
            while (true) {
                Reader reader = this.b;
                if (reader == null) {
                    break;
                }
                long skip = reader.skip(j);
                if (skip > 0) {
                    return skip;
                }
                a();
            }
        }
        return 0L;
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        Reader reader = this.b;
        return reader != null && reader.ready();
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Reader reader = this.b;
        if (reader != null) {
            try {
                reader.close();
            } finally {
                this.b = null;
            }
        }
    }
}
