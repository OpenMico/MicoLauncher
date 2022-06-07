package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public final class AssetDataSource extends BaseDataSource {
    private final AssetManager a;
    @Nullable
    private Uri b;
    @Nullable
    private InputStream c;
    private long d;
    private boolean e;

    /* loaded from: classes2.dex */
    public static final class AssetDataSourceException extends DataSourceException {
        @Deprecated
        public AssetDataSourceException(IOException iOException) {
            super(iOException, 2000);
        }

        public AssetDataSourceException(@Nullable Throwable th, int i) {
            super(th, i);
        }
    }

    public AssetDataSource(Context context) {
        super(false);
        this.a = context.getAssets();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws AssetDataSourceException {
        try {
            this.b = dataSpec.uri;
            String str = (String) Assertions.checkNotNull(this.b.getPath());
            if (str.startsWith("/android_asset/")) {
                str = str.substring(15);
            } else if (str.startsWith("/")) {
                str = str.substring(1);
            }
            transferInitializing(dataSpec);
            this.c = this.a.open(str, 1);
            if (this.c.skip(dataSpec.position) >= dataSpec.position) {
                if (dataSpec.length != -1) {
                    this.d = dataSpec.length;
                } else {
                    this.d = this.c.available();
                    if (this.d == 2147483647L) {
                        this.d = -1L;
                    }
                }
                this.e = true;
                transferStarted(dataSpec);
                return this.d;
            }
            throw new AssetDataSourceException(null, 2008);
        } catch (AssetDataSourceException e) {
            throw e;
        } catch (IOException e2) {
            throw new AssetDataSourceException(e2, e2 instanceof FileNotFoundException ? 2005 : 2000);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws AssetDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.d;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i2 = (int) Math.min(j, i2);
            } catch (IOException e) {
                throw new AssetDataSourceException(e, 2000);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.c)).read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        long j2 = this.d;
        if (j2 != -1) {
            this.d = j2 - read;
        }
        bytesTransferred(read);
        return read;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.b;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws AssetDataSourceException {
        try {
            this.b = null;
            try {
                if (this.c != null) {
                    this.c.close();
                }
            } catch (IOException e) {
                throw new AssetDataSourceException(e, 2000);
            }
        } finally {
            this.c = null;
            if (this.e) {
                this.e = false;
                transferEnded();
            }
        }
    }
}
