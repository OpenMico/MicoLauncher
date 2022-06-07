package com.google.android.exoplayer2.upstream.cache;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public final class CacheDataSink implements DataSink {
    public static final int DEFAULT_BUFFER_SIZE = 20480;
    public static final long DEFAULT_FRAGMENT_SIZE = 5242880;
    private final Cache a;
    private final long b;
    private final int c;
    @Nullable
    private DataSpec d;
    private long e;
    @Nullable
    private File f;
    @Nullable
    private OutputStream g;
    private long h;
    private long i;
    private ReusableBufferedOutputStream j;

    /* loaded from: classes2.dex */
    public static final class Factory implements DataSink.Factory {
        private Cache a;
        private long b = CacheDataSink.DEFAULT_FRAGMENT_SIZE;
        private int c = CacheDataSink.DEFAULT_BUFFER_SIZE;

        public Factory setCache(Cache cache) {
            this.a = cache;
            return this;
        }

        public Factory setFragmentSize(long j) {
            this.b = j;
            return this;
        }

        public Factory setBufferSize(int i) {
            this.c = i;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.DataSink.Factory
        public DataSink createDataSink() {
            return new CacheDataSink((Cache) Assertions.checkNotNull(this.a), this.b, this.c);
        }
    }

    /* loaded from: classes2.dex */
    public static final class CacheDataSinkException extends Cache.CacheException {
        public CacheDataSinkException(IOException iOException) {
            super(iOException);
        }
    }

    public CacheDataSink(Cache cache, long j) {
        this(cache, j, DEFAULT_BUFFER_SIZE);
    }

    public CacheDataSink(Cache cache, long j, int i) {
        Assertions.checkState(j > 0 || j == -1, "fragmentSize must be positive or C.LENGTH_UNSET.");
        int i2 = (j > (-1L) ? 1 : (j == (-1L) ? 0 : -1));
        if (i2 != 0 && j < PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
            Log.w("CacheDataSink", "fragmentSize is below the minimum recommended value of 2097152. This may cause poor cache performance.");
        }
        this.a = (Cache) Assertions.checkNotNull(cache);
        this.b = i2 == 0 ? Long.MAX_VALUE : j;
        this.c = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void open(DataSpec dataSpec) throws CacheDataSinkException {
        Assertions.checkNotNull(dataSpec.key);
        if (dataSpec.length != -1 || !dataSpec.isFlagSet(2)) {
            this.d = dataSpec;
            this.e = dataSpec.isFlagSet(4) ? this.b : Long.MAX_VALUE;
            this.i = 0L;
            try {
                a(dataSpec);
            } catch (IOException e) {
                throw new CacheDataSinkException(e);
            }
        } else {
            this.d = null;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void write(byte[] bArr, int i, int i2) throws CacheDataSinkException {
        DataSpec dataSpec = this.d;
        if (dataSpec != null) {
            int i3 = 0;
            while (i3 < i2) {
                try {
                    if (this.h == this.e) {
                        a();
                        a(dataSpec);
                    }
                    int min = (int) Math.min(i2 - i3, this.e - this.h);
                    ((OutputStream) Util.castNonNull(this.g)).write(bArr, i + i3, min);
                    i3 += min;
                    long j = min;
                    this.h += j;
                    this.i += j;
                } catch (IOException e) {
                    throw new CacheDataSinkException(e);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink
    public void close() throws CacheDataSinkException {
        if (this.d != null) {
            try {
                a();
            } catch (IOException e) {
                throw new CacheDataSinkException(e);
            }
        }
    }

    private void a(DataSpec dataSpec) throws IOException {
        this.f = this.a.startFile((String) Util.castNonNull(dataSpec.key), dataSpec.position + this.i, dataSpec.length == -1 ? -1L : Math.min(dataSpec.length - this.i, this.e));
        FileOutputStream fileOutputStream = new FileOutputStream(this.f);
        int i = this.c;
        if (i > 0) {
            ReusableBufferedOutputStream reusableBufferedOutputStream = this.j;
            if (reusableBufferedOutputStream == null) {
                this.j = new ReusableBufferedOutputStream(fileOutputStream, i);
            } else {
                reusableBufferedOutputStream.reset(fileOutputStream);
            }
            this.g = this.j;
        } else {
            this.g = fileOutputStream;
        }
        this.h = 0L;
    }

    private void a() throws IOException {
        OutputStream outputStream = this.g;
        if (outputStream != null) {
            try {
                outputStream.flush();
                Util.closeQuietly(this.g);
                this.g = null;
                this.f = null;
                this.a.commitFile((File) Util.castNonNull(this.f), this.h);
            } catch (Throwable th) {
                Util.closeQuietly(this.g);
                this.g = null;
                this.f = null;
                ((File) Util.castNonNull(this.f)).delete();
                throw th;
            }
        }
    }
}
