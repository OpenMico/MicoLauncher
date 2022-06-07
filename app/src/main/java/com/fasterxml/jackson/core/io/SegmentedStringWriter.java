package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.Writer;

/* loaded from: classes.dex */
public final class SegmentedStringWriter extends Writer {
    private final TextBuffer a;

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this.a = new TextBuffer(bufferRecycler);
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        this.a.append(charSequence2, 0, charSequence2.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i, int i2) {
        String charSequence2 = charSequence.subSequence(i, i2).toString();
        this.a.append(charSequence2, 0, charSequence2.length());
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr) {
        this.a.append(cArr, 0, cArr.length);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        this.a.append(cArr, i, i2);
    }

    @Override // java.io.Writer
    public void write(int i) {
        this.a.append((char) i);
    }

    @Override // java.io.Writer
    public void write(String str) {
        this.a.append(str, 0, str.length());
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) {
        this.a.append(str, i, i2);
    }

    public String getAndClear() {
        String contentsAsString = this.a.contentsAsString();
        this.a.releaseBuffers();
        return contentsAsString;
    }
}
