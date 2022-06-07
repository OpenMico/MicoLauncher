package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class CharSource {
    public abstract Reader openStream() throws IOException;

    @Beta
    public ByteSource asByteSource(Charset charset) {
        return new a(charset);
    }

    public BufferedReader openBufferedStream() throws IOException {
        Reader openStream = openStream();
        return openStream instanceof BufferedReader ? (BufferedReader) openStream : new BufferedReader(openStream);
    }

    @Beta
    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    @Beta
    public long length() throws IOException {
        Optional<Long> lengthIfKnown = lengthIfKnown();
        if (lengthIfKnown.isPresent()) {
            return lengthIfKnown.get().longValue();
        }
        Closer create = Closer.create();
        try {
            return a((Reader) create.register(openStream()));
        } finally {
            create.close();
        }
    }

    private long a(Reader reader) throws IOException {
        long j = 0;
        while (true) {
            long skip = reader.skip(Long.MAX_VALUE);
            if (skip == 0) {
                return j;
            }
            j += skip;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(Appendable appendable) throws IOException {
        Preconditions.checkNotNull(appendable);
        Closer create = Closer.create();
        try {
            return CharStreams.copy((Reader) create.register(openStream()), appendable);
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(CharSink charSink) throws IOException {
        Preconditions.checkNotNull(charSink);
        Closer create = Closer.create();
        try {
            return CharStreams.copy((Reader) create.register(openStream()), (Writer) create.register(charSink.openStream()));
        } finally {
            create.close();
        }
    }

    public String read() throws IOException {
        Closer create = Closer.create();
        try {
            return CharStreams.toString((Reader) create.register(openStream()));
        } finally {
            create.close();
        }
    }

    @NullableDecl
    public String readFirstLine() throws IOException {
        Closer create = Closer.create();
        try {
            return ((BufferedReader) create.register(openBufferedStream())).readLine();
        } finally {
            create.close();
        }
    }

    public ImmutableList<String> readLines() throws IOException {
        Closer create = Closer.create();
        try {
            BufferedReader bufferedReader = (BufferedReader) create.register(openBufferedStream());
            ArrayList newArrayList = Lists.newArrayList();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return ImmutableList.copyOf((Collection) newArrayList);
                }
                newArrayList.add(readLine);
            }
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    @Beta
    public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
        Preconditions.checkNotNull(lineProcessor);
        Closer create = Closer.create();
        try {
            return (T) CharStreams.readLines((Reader) create.register(openStream()), lineProcessor);
        } finally {
            create.close();
        }
    }

    public boolean isEmpty() throws IOException {
        Optional<Long> lengthIfKnown = lengthIfKnown();
        boolean z = true;
        if (lengthIfKnown.isPresent()) {
            return lengthIfKnown.get().longValue() == 0;
        }
        Closer create = Closer.create();
        try {
            if (((Reader) create.register(openStream())).read() != -1) {
                z = false;
            }
            return z;
        } finally {
            create.close();
        }
    }

    public static CharSource concat(Iterable<? extends CharSource> iterable) {
        return new c(iterable);
    }

    public static CharSource concat(Iterator<? extends CharSource> it) {
        return concat(ImmutableList.copyOf(it));
    }

    public static CharSource concat(CharSource... charSourceArr) {
        return concat(ImmutableList.copyOf(charSourceArr));
    }

    public static CharSource wrap(CharSequence charSequence) {
        return charSequence instanceof String ? new e((String) charSequence) : new b(charSequence);
    }

    public static CharSource empty() {
        return d.b;
    }

    /* loaded from: classes2.dex */
    public final class a extends ByteSource {
        final Charset a;

        a(Charset charset) {
            CharSource.this = r1;
            this.a = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.ByteSource
        public CharSource asCharSource(Charset charset) {
            if (charset.equals(this.a)) {
                return CharSource.this;
            }
            return super.asCharSource(charset);
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return new f(CharSource.this.openStream(), this.a, 8192);
        }

        public String toString() {
            return CharSource.this.toString() + ".asByteSource(" + this.a + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b extends CharSource {
        private static final Splitter b = Splitter.onPattern("\r\n|\n|\r");
        protected final CharSequence a;

        protected b(CharSequence charSequence) {
            this.a = (CharSequence) Preconditions.checkNotNull(charSequence);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() {
            return new b(this.a);
        }

        @Override // com.google.common.io.CharSource
        public String read() {
            return this.a.toString();
        }

        @Override // com.google.common.io.CharSource
        public boolean isEmpty() {
            return this.a.length() == 0;
        }

        @Override // com.google.common.io.CharSource
        public long length() {
            return this.a.length();
        }

        @Override // com.google.common.io.CharSource
        public Optional<Long> lengthIfKnown() {
            return Optional.of(Long.valueOf(this.a.length()));
        }

        private Iterator<String> b() {
            return new AbstractIterator<String>() { // from class: com.google.common.io.CharSource.b.1
                Iterator<String> a;

                {
                    b.this = this;
                    this.a = b.b.split(b.this.a).iterator();
                }

                /* renamed from: a */
                public String computeNext() {
                    if (this.a.hasNext()) {
                        String next = this.a.next();
                        if (this.a.hasNext() || !next.isEmpty()) {
                            return next;
                        }
                    }
                    return endOfData();
                }
            };
        }

        @Override // com.google.common.io.CharSource
        public String readFirstLine() {
            Iterator<String> b2 = b();
            if (b2.hasNext()) {
                return b2.next();
            }
            return null;
        }

        @Override // com.google.common.io.CharSource
        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(b());
        }

        @Override // com.google.common.io.CharSource
        public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
            Iterator<String> b2 = b();
            while (b2.hasNext() && lineProcessor.processLine(b2.next())) {
            }
            return lineProcessor.getResult();
        }

        public String toString() {
            return "CharSource.wrap(" + Ascii.truncate(this.a, 30, "...") + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e extends b {
        protected e(String str) {
            super(str);
        }

        @Override // com.google.common.io.CharSource.b, com.google.common.io.CharSource
        public Reader openStream() {
            return new StringReader((String) this.a);
        }

        @Override // com.google.common.io.CharSource
        public long copyTo(Appendable appendable) throws IOException {
            appendable.append(this.a);
            return this.a.length();
        }

        @Override // com.google.common.io.CharSource
        public long copyTo(CharSink charSink) throws IOException {
            Preconditions.checkNotNull(charSink);
            Closer create = Closer.create();
            try {
                ((Writer) create.register(charSink.openStream())).write((String) this.a);
                return this.a.length();
            } finally {
                create.close();
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class d extends e {
        private static final d b = new d();

        @Override // com.google.common.io.CharSource.b
        public String toString() {
            return "CharSource.empty()";
        }

        private d() {
            super("");
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends CharSource {
        private final Iterable<? extends CharSource> a;

        c(Iterable<? extends CharSource> iterable) {
            this.a = (Iterable) Preconditions.checkNotNull(iterable);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() throws IOException {
            return new e(this.a.iterator());
        }

        @Override // com.google.common.io.CharSource
        public boolean isEmpty() throws IOException {
            for (CharSource charSource : this.a) {
                if (!charSource.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.CharSource
        public Optional<Long> lengthIfKnown() {
            long j = 0;
            for (CharSource charSource : this.a) {
                Optional<Long> lengthIfKnown = charSource.lengthIfKnown();
                if (!lengthIfKnown.isPresent()) {
                    return Optional.absent();
                }
                j += lengthIfKnown.get().longValue();
            }
            return Optional.of(Long.valueOf(j));
        }

        @Override // com.google.common.io.CharSource
        public long length() throws IOException {
            long j = 0;
            for (CharSource charSource : this.a) {
                j += charSource.length();
            }
            return j;
        }

        public String toString() {
            return "CharSource.concat(" + this.a + ")";
        }
    }
}
