package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class CharSink {
    public abstract Writer openStream() throws IOException;

    public Writer openBufferedStream() throws IOException {
        Writer openStream = openStream();
        return openStream instanceof BufferedWriter ? (BufferedWriter) openStream : new BufferedWriter(openStream);
    }

    public void write(CharSequence charSequence) throws IOException {
        Preconditions.checkNotNull(charSequence);
        Closer create = Closer.create();
        try {
            Writer writer = (Writer) create.register(openStream());
            writer.append(charSequence);
            writer.flush();
        } finally {
            create.close();
        }
    }

    public void writeLines(Iterable<? extends CharSequence> iterable) throws IOException {
        writeLines(iterable, System.getProperty("line.separator"));
    }

    public void writeLines(Iterable<? extends CharSequence> iterable, String str) throws IOException {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(str);
        Closer create = Closer.create();
        try {
            Writer writer = (Writer) create.register(openBufferedStream());
            for (CharSequence charSequence : iterable) {
                writer.append(charSequence).append((CharSequence) str);
            }
            writer.flush();
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(Readable readable) throws IOException {
        Preconditions.checkNotNull(readable);
        Closer create = Closer.create();
        try {
            Writer writer = (Writer) create.register(openStream());
            long copy = CharStreams.copy(readable, writer);
            writer.flush();
            return copy;
        } finally {
            create.close();
        }
    }
}
