package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class CharStreams {
    public static CharBuffer a() {
        return CharBuffer.allocate(2048);
    }

    private CharStreams() {
    }

    @CanIgnoreReturnValue
    public static long copy(Readable readable, Appendable appendable) throws IOException {
        if (!(readable instanceof Reader)) {
            Preconditions.checkNotNull(readable);
            Preconditions.checkNotNull(appendable);
            long j = 0;
            CharBuffer a2 = a();
            while (readable.read(a2) != -1) {
                a2.flip();
                appendable.append(a2);
                j += a2.remaining();
                a2.clear();
            }
            return j;
        } else if (appendable instanceof StringBuilder) {
            return a((Reader) readable, (StringBuilder) appendable);
        } else {
            return a((Reader) readable, asWriter(appendable));
        }
    }

    @CanIgnoreReturnValue
    static long a(Reader reader, StringBuilder sb) throws IOException {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(sb);
        char[] cArr = new char[2048];
        long j = 0;
        while (true) {
            int read = reader.read(cArr);
            if (read == -1) {
                return j;
            }
            sb.append(cArr, 0, read);
            j += read;
        }
    }

    @CanIgnoreReturnValue
    static long a(Reader reader, Writer writer) throws IOException {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(writer);
        char[] cArr = new char[2048];
        long j = 0;
        while (true) {
            int read = reader.read(cArr);
            if (read == -1) {
                return j;
            }
            writer.write(cArr, 0, read);
            j += read;
        }
    }

    public static String toString(Readable readable) throws IOException {
        return a(readable).toString();
    }

    private static StringBuilder a(Readable readable) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (readable instanceof Reader) {
            a((Reader) readable, sb);
        } else {
            copy(readable, sb);
        }
        return sb;
    }

    public static List<String> readLines(Readable readable) throws IOException {
        ArrayList arrayList = new ArrayList();
        LineReader lineReader = new LineReader(readable);
        while (true) {
            String readLine = lineReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            arrayList.add(readLine);
        }
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(Readable readable, LineProcessor<T> lineProcessor) throws IOException {
        String readLine;
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(lineProcessor);
        LineReader lineReader = new LineReader(readable);
        do {
            readLine = lineReader.readLine();
            if (readLine == null) {
                break;
            }
        } while (lineProcessor.processLine(readLine));
        return lineProcessor.getResult();
    }

    @CanIgnoreReturnValue
    public static long exhaust(Readable readable) throws IOException {
        CharBuffer a2 = a();
        long j = 0;
        while (true) {
            long read = readable.read(a2);
            if (read == -1) {
                return j;
            }
            j += read;
            a2.clear();
        }
    }

    public static void skipFully(Reader reader, long j) throws IOException {
        Preconditions.checkNotNull(reader);
        while (j > 0) {
            long skip = reader.skip(j);
            if (skip != 0) {
                j -= skip;
            } else {
                throw new EOFException();
            }
        }
    }

    public static Writer nullWriter() {
        return a.a;
    }

    /* loaded from: classes2.dex */
    private static final class a extends Writer {
        private static final a a = new a();

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(char c) {
            return this;
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        public String toString() {
            return "CharStreams.nullWriter()";
        }

        @Override // java.io.Writer
        public void write(int i) {
        }

        private a() {
        }

        @Override // java.io.Writer
        public void write(char[] cArr) {
            Preconditions.checkNotNull(cArr);
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2 + i, cArr.length);
        }

        @Override // java.io.Writer
        public void write(String str) {
            Preconditions.checkNotNull(str);
        }

        @Override // java.io.Writer
        public void write(String str, int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2 + i, str.length());
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return this;
        }

        @Override // java.io.Writer, java.lang.Appendable
        public Writer append(CharSequence charSequence, int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, charSequence.length());
            return this;
        }
    }

    public static Writer asWriter(Appendable appendable) {
        if (appendable instanceof Writer) {
            return (Writer) appendable;
        }
        return new a(appendable);
    }
}
