package com.google.android.exoplayer2.upstream;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes2.dex */
public final class ContentDataSource extends BaseDataSource {
    private final ContentResolver a;
    @Nullable
    private Uri b;
    @Nullable
    private AssetFileDescriptor c;
    @Nullable
    private FileInputStream d;
    private long e;
    private boolean f;

    /* loaded from: classes2.dex */
    public static class ContentDataSourceException extends DataSourceException {
        @Deprecated
        public ContentDataSourceException(IOException iOException) {
            this(iOException, 2000);
        }

        public ContentDataSourceException(@Nullable IOException iOException, int i) {
            super(iOException, i);
        }
    }

    public ContentDataSource(Context context) {
        super(false);
        this.a = context.getContentResolver();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws ContentDataSourceException {
        int i = 2000;
        try {
            Uri uri = dataSpec.uri;
            this.b = uri;
            transferInitializing(dataSpec);
            AssetFileDescriptor openAssetFileDescriptor = this.a.openAssetFileDescriptor(uri, "r");
            this.c = openAssetFileDescriptor;
            if (openAssetFileDescriptor != null) {
                long length = openAssetFileDescriptor.getLength();
                FileInputStream fileInputStream = new FileInputStream(openAssetFileDescriptor.getFileDescriptor());
                this.d = fileInputStream;
                int i2 = (length > (-1L) ? 1 : (length == (-1L) ? 0 : -1));
                if (i2 != 0 && dataSpec.position > length) {
                    throw new ContentDataSourceException(null, 2008);
                }
                long startOffset = openAssetFileDescriptor.getStartOffset();
                long skip = fileInputStream.skip(dataSpec.position + startOffset) - startOffset;
                if (skip == dataSpec.position) {
                    if (i2 == 0) {
                        FileChannel channel = fileInputStream.getChannel();
                        long size = channel.size();
                        if (size == 0) {
                            this.e = -1L;
                        } else {
                            this.e = size - channel.position();
                            if (this.e < 0) {
                                throw new ContentDataSourceException(null, 2008);
                            }
                        }
                    } else {
                        this.e = length - skip;
                        if (this.e < 0) {
                            throw new ContentDataSourceException(null, 2008);
                        }
                    }
                    if (dataSpec.length != -1) {
                        long j = this.e;
                        this.e = j == -1 ? dataSpec.length : Math.min(j, dataSpec.length);
                    }
                    this.f = true;
                    transferStarted(dataSpec);
                    return dataSpec.length != -1 ? dataSpec.length : this.e;
                }
                throw new ContentDataSourceException(null, 2008);
            }
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
            sb.append("Could not open file descriptor for: ");
            sb.append(valueOf);
            throw new ContentDataSourceException(new IOException(sb.toString()), 2000);
        } catch (ContentDataSourceException e) {
            throw e;
        } catch (IOException e2) {
            if (e2 instanceof FileNotFoundException) {
                i = 2005;
            }
            throw new ContentDataSourceException(e2, i);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws ContentDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.e;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i2 = (int) Math.min(j, i2);
            } catch (IOException e) {
                throw new ContentDataSourceException(e, 2000);
            }
        }
        int read = ((FileInputStream) Util.castNonNull(this.d)).read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        long j2 = this.e;
        if (j2 != -1) {
            this.e = j2 - read;
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
    public void close() throws ContentDataSourceException {
        try {
            this.b = null;
            try {
                if (this.d != null) {
                    this.d.close();
                }
                this.d = null;
                try {
                    try {
                        if (this.c != null) {
                            this.c.close();
                        }
                        this.c = null;
                        if (this.f) {
                            this.f = false;
                            transferEnded();
                        }
                    } catch (IOException e) {
                        throw new ContentDataSourceException(e, 2000);
                    }
                } catch (Throwable th) {
                    this.c = null;
                    if (this.f) {
                        this.f = false;
                        transferEnded();
                    }
                    throw th;
                }
            } catch (IOException e2) {
                throw new ContentDataSourceException(e2, 2000);
            }
        } catch (Throwable th2) {
            try {
                this.d = null;
                try {
                    if (this.c != null) {
                        this.c.close();
                    }
                    this.c = null;
                    if (this.f) {
                        this.f = false;
                        transferEnded();
                    }
                    throw th2;
                } catch (IOException e3) {
                    throw new ContentDataSourceException(e3, 2000);
                }
            } catch (Throwable th3) {
                this.c = null;
                if (this.f) {
                    this.f = false;
                    transferEnded();
                }
                throw th3;
            }
        }
    }
}
