package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class ByteSource {
    public abstract InputStream openStream() throws IOException;

    public CharSource asCharSource(Charset charset) {
        return new a(charset);
    }

    public InputStream openBufferedStream() throws IOException {
        InputStream openStream = openStream();
        return openStream instanceof BufferedInputStream ? (BufferedInputStream) openStream : new BufferedInputStream(openStream);
    }

    public ByteSource slice(long j, long j2) {
        return new e(j, j2);
    }

    public boolean isEmpty() throws IOException {
        Optional<Long> sizeIfKnown = sizeIfKnown();
        boolean z = true;
        if (sizeIfKnown.isPresent()) {
            return sizeIfKnown.get().longValue() == 0;
        }
        Closer create = Closer.create();
        try {
            if (((InputStream) create.register(openStream())).read() != -1) {
                z = false;
            }
            return z;
        } finally {
            create.close();
        }
    }

    @Beta
    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    /* JADX WARN: Finally extract failed */
    public long size() throws IOException {
        Optional<Long> sizeIfKnown = sizeIfKnown();
        if (sizeIfKnown.isPresent()) {
            return sizeIfKnown.get().longValue();
        }
        Closer create = Closer.create();
        try {
            return a((InputStream) create.register(openStream()));
        } catch (IOException unused) {
            create.close();
            create = Closer.create();
            try {
                return ByteStreams.exhaust((InputStream) create.register(openStream()));
            } finally {
                create.close();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private long a(InputStream inputStream) throws IOException {
        long j = 0;
        while (true) {
            long b2 = ByteStreams.b(inputStream, 2147483647L);
            if (b2 <= 0) {
                return j;
            }
            j += b2;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(outputStream);
        Closer create = Closer.create();
        try {
            return ByteStreams.copy((InputStream) create.register(openStream()), outputStream);
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(ByteSink byteSink) throws IOException {
        Preconditions.checkNotNull(byteSink);
        Closer create = Closer.create();
        try {
            return ByteStreams.copy((InputStream) create.register(openStream()), (OutputStream) create.register(byteSink.openStream()));
        } finally {
            create.close();
        }
    }

    public byte[] read() throws IOException {
        byte[] bArr;
        Closer create = Closer.create();
        try {
            InputStream inputStream = (InputStream) create.register(openStream());
            Optional<Long> sizeIfKnown = sizeIfKnown();
            if (sizeIfKnown.isPresent()) {
                bArr = ByteStreams.a(inputStream, sizeIfKnown.get().longValue());
            } else {
                bArr = ByteStreams.toByteArray(inputStream);
            }
            return bArr;
        } finally {
            create.close();
        }
    }

    @CanIgnoreReturnValue
    @Beta
    public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
        Preconditions.checkNotNull(byteProcessor);
        Closer create = Closer.create();
        try {
            return (T) ByteStreams.readBytes((InputStream) create.register(openStream()), byteProcessor);
        } finally {
            create.close();
        }
    }

    public HashCode hash(HashFunction hashFunction) throws IOException {
        Hasher newHasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(newHasher));
        return newHasher.hash();
    }

    public boolean contentEquals(ByteSource byteSource) throws IOException {
        int read;
        Preconditions.checkNotNull(byteSource);
        byte[] a2 = ByteStreams.a();
        byte[] a3 = ByteStreams.a();
        Closer create = Closer.create();
        try {
            InputStream inputStream = (InputStream) create.register(openStream());
            InputStream inputStream2 = (InputStream) create.register(byteSource.openStream());
            do {
                read = ByteStreams.read(inputStream, a2, 0, a2.length);
                if (read == ByteStreams.read(inputStream2, a3, 0, a3.length) && Arrays.equals(a2, a3)) {
                }
                return false;
            } while (read == a2.length);
            return true;
        } finally {
            create.close();
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new c(iterable);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> it) {
        return concat(ImmutableList.copyOf(it));
    }

    public static ByteSource concat(ByteSource... byteSourceArr) {
        return concat(ImmutableList.copyOf(byteSourceArr));
    }

    public static ByteSource wrap(byte[] bArr) {
        return new b(bArr);
    }

    public static ByteSource empty() {
        return d.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class a extends CharSource {
        final Charset a;

        a(Charset charset) {
            ByteSource.this = r1;
            this.a = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSource
        public ByteSource asByteSource(Charset charset) {
            if (charset.equals(this.a)) {
                return ByteSource.this;
            }
            return super.asByteSource(charset);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.a);
        }

        @Override // com.google.common.io.CharSource
        public String read() throws IOException {
            return new String(ByteSource.this.read(), this.a);
        }

        public String toString() {
            return ByteSource.this.toString() + ".asCharSource(" + this.a + ")";
        }
    }

    /* loaded from: classes2.dex */
    public final class e extends ByteSource {
        final long a;
        final long b;

        e(long j, long j2) {
            ByteSource.this = r6;
            boolean z = true;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            Preconditions.checkArgument(j2 < 0 ? false : z, "length (%s) may not be negative", j2);
            this.a = j;
            this.b = j2;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return a(ByteSource.this.openStream());
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return a(ByteSource.this.openBufferedStream());
        }

        private InputStream a(InputStream inputStream) throws IOException {
            Closer create;
            long j = this.a;
            if (j > 0) {
                try {
                    if (ByteStreams.b(inputStream, j) < this.a) {
                        inputStream.close();
                        return new ByteArrayInputStream(new byte[0]);
                    }
                } finally {
                    try {
                        throw create.rethrow(th);
                    } catch (Throwable th) {
                    }
                }
            }
            return ByteStreams.limit(inputStream, this.b);
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j, long j2) {
            boolean z = true;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            if (j2 < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "length (%s) may not be negative", j2);
            return ByteSource.this.slice(this.a + j, Math.min(j2, this.b - j));
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            return this.b == 0 || ByteSource.super.isEmpty();
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Optional<Long> sizeIfKnown = ByteSource.this.sizeIfKnown();
            if (!sizeIfKnown.isPresent()) {
                return Optional.absent();
            }
            long longValue = sizeIfKnown.get().longValue();
            return Optional.of(Long.valueOf(Math.min(this.b, longValue - Math.min(this.a, longValue))));
        }

        public String toString() {
            return ByteSource.this.toString() + ".slice(" + this.a + ", " + this.b + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b extends ByteSource {
        final byte[] a;
        final int b;
        final int c;

        b(byte[] bArr) {
            this(bArr, 0, bArr.length);
        }

        b(byte[] bArr, int i, int i2) {
            this.a = bArr;
            this.b = i;
            this.c = i2;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() {
            return new ByteArrayInputStream(this.a, this.b, this.c);
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return openStream();
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() {
            return this.c == 0;
        }

        @Override // com.google.common.io.ByteSource
        public long size() {
            return this.c;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.c));
        }

        @Override // com.google.common.io.ByteSource
        public byte[] read() {
            byte[] bArr = this.a;
            int i = this.b;
            return Arrays.copyOfRange(bArr, i, this.c + i);
        }

        @Override // com.google.common.io.ByteSource
        public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
            byteProcessor.processBytes(this.a, this.b, this.c);
            return byteProcessor.getResult();
        }

        @Override // com.google.common.io.ByteSource
        public long copyTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.a, this.b, this.c);
            return this.c;
        }

        @Override // com.google.common.io.ByteSource
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.a, this.b, this.c);
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j, long j2) {
            boolean z = true;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            if (j2 < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "length (%s) may not be negative", j2);
            long min = Math.min(j, this.c);
            return new b(this.a, this.b + ((int) min), (int) Math.min(j2, this.c - min));
        }

        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.a, this.b, this.c), 30, "...") + ")";
        }
    }

    /* loaded from: classes2.dex */
    private static final class d extends b {
        static final d d = new d();

        @Override // com.google.common.io.ByteSource.b
        public String toString() {
            return "ByteSource.empty()";
        }

        d() {
            super(new byte[0]);
        }

        @Override // com.google.common.io.ByteSource
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override // com.google.common.io.ByteSource.b, com.google.common.io.ByteSource
        public byte[] read() {
            return this.a;
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends ByteSource {
        final Iterable<? extends ByteSource> a;

        c(Iterable<? extends ByteSource> iterable) {
            this.a = (Iterable) Preconditions.checkNotNull(iterable);
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return new d(this.a.iterator());
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            for (ByteSource byteSource : this.a) {
                if (!byteSource.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Iterable<? extends ByteSource> iterable = this.a;
            if (!(iterable instanceof Collection)) {
                return Optional.absent();
            }
            long j = 0;
            for (ByteSource byteSource : iterable) {
                Optional<Long> sizeIfKnown = byteSource.sizeIfKnown();
                if (!sizeIfKnown.isPresent()) {
                    return Optional.absent();
                }
                j += sizeIfKnown.get().longValue();
                if (j < 0) {
                    return Optional.of(Long.MAX_VALUE);
                }
            }
            return Optional.of(Long.valueOf(j));
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            long j = 0;
            for (ByteSource byteSource : this.a) {
                j += byteSource.size();
                if (j < 0) {
                    return Long.MAX_VALUE;
                }
            }
            return j;
        }

        public String toString() {
            return "ByteSource.concat(" + this.a + ")";
        }
    }
}
