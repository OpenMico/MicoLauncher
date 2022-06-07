package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mipush.sdk.Constants;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes2.dex */
public final class RawResourceDataSource extends BaseDataSource {
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    private final Resources a;
    private final String b;
    @Nullable
    private Uri c;
    @Nullable
    private AssetFileDescriptor d;
    @Nullable
    private InputStream e;
    private long f;
    private boolean g;

    /* loaded from: classes2.dex */
    public static class RawResourceDataSourceException extends DataSourceException {
        @Deprecated
        public RawResourceDataSourceException(String str) {
            super(str, null, 2000);
        }

        @Deprecated
        public RawResourceDataSourceException(Throwable th) {
            super(th, 2000);
        }

        public RawResourceDataSourceException(@Nullable String str, @Nullable Throwable th, int i) {
            super(str, th, i);
        }
    }

    public static Uri buildRawResourceUri(int i) {
        StringBuilder sb = new StringBuilder(26);
        sb.append("rawresource:///");
        sb.append(i);
        return Uri.parse(sb.toString());
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.a = context.getResources();
        this.b = context.getPackageName();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws RawResourceDataSourceException {
        int parseInt;
        boolean z;
        Uri uri = dataSpec.uri;
        this.c = uri;
        if (TextUtils.equals(RAW_RESOURCE_SCHEME, uri.getScheme()) || (TextUtils.equals("android.resource", uri.getScheme()) && uri.getPathSegments().size() == 1 && ((String) Assertions.checkNotNull(uri.getLastPathSegment())).matches("\\d+"))) {
            try {
                parseInt = Integer.parseInt((String) Assertions.checkNotNull(uri.getLastPathSegment()));
            } catch (NumberFormatException unused) {
                throw new RawResourceDataSourceException("Resource identifier must be an integer.", null, 1004);
            }
        } else if (TextUtils.equals("android.resource", uri.getScheme())) {
            String str = (String) Assertions.checkNotNull(uri.getPath());
            if (str.startsWith("/")) {
                str = str.substring(1);
            }
            String host = uri.getHost();
            String valueOf = String.valueOf(TextUtils.isEmpty(host) ? "" : String.valueOf(host).concat(Constants.COLON_SEPARATOR));
            String valueOf2 = String.valueOf(str);
            parseInt = this.a.getIdentifier(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), "raw", this.b);
            if (parseInt == 0) {
                throw new RawResourceDataSourceException("Resource not found.", null, 2005);
            }
        } else {
            throw new RawResourceDataSourceException("URI must either use scheme rawresource or android.resource", null, 1004);
        }
        transferInitializing(dataSpec);
        try {
            AssetFileDescriptor openRawResourceFd = this.a.openRawResourceFd(parseInt);
            this.d = openRawResourceFd;
            if (openRawResourceFd != null) {
                long length = openRawResourceFd.getLength();
                FileInputStream fileInputStream = new FileInputStream(openRawResourceFd.getFileDescriptor());
                this.e = fileInputStream;
                int i = (length > (-1L) ? 1 : (length == (-1L) ? 0 : -1));
                if (i != 0) {
                    try {
                        if (dataSpec.position > length) {
                            throw new RawResourceDataSourceException(null, null, 2008);
                        }
                    } catch (RawResourceDataSourceException e) {
                        throw e;
                    } catch (IOException e2) {
                        throw new RawResourceDataSourceException(null, e2, 2000);
                    }
                }
                long startOffset = openRawResourceFd.getStartOffset();
                long skip = fileInputStream.skip(dataSpec.position + startOffset) - startOffset;
                if (skip == dataSpec.position) {
                    if (i == 0) {
                        FileChannel channel = fileInputStream.getChannel();
                        if (channel.size() == 0) {
                            this.f = -1L;
                        } else {
                            this.f = channel.size() - channel.position();
                            if (this.f < 0) {
                                throw new RawResourceDataSourceException(null, null, 2008);
                            }
                        }
                    } else {
                        this.f = length - skip;
                        if (this.f < 0) {
                            throw new DataSourceException(2008);
                        }
                    }
                    if (dataSpec.length != -1) {
                        long j = this.f;
                        this.f = j == -1 ? dataSpec.length : Math.min(j, dataSpec.length);
                        z = true;
                    } else {
                        z = true;
                    }
                    this.g = z;
                    transferStarted(dataSpec);
                    return dataSpec.length != -1 ? dataSpec.length : this.f;
                }
                throw new RawResourceDataSourceException(null, null, 2008);
            } else {
                String valueOf3 = String.valueOf(uri);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 24);
                sb.append("Resource is compressed: ");
                sb.append(valueOf3);
                throw new RawResourceDataSourceException(sb.toString(), null, 2000);
            }
        } catch (Resources.NotFoundException e3) {
            throw new RawResourceDataSourceException(null, e3, 2005);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws RawResourceDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.f;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i2 = (int) Math.min(j, i2);
            } catch (IOException e) {
                throw new RawResourceDataSourceException(null, e, 2000);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.e)).read(bArr, i, i2);
        if (read != -1) {
            long j2 = this.f;
            if (j2 != -1) {
                this.f = j2 - read;
            }
            bytesTransferred(read);
            return read;
        } else if (this.f == -1) {
            return -1;
        } else {
            throw new RawResourceDataSourceException("End of stream reached having not read sufficient data.", new EOFException(), 2000);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.c;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws RawResourceDataSourceException {
        try {
            this.c = null;
            try {
                if (this.e != null) {
                    this.e.close();
                }
                this.e = null;
                try {
                    try {
                        if (this.d != null) {
                            this.d.close();
                        }
                        this.d = null;
                        if (this.g) {
                            this.g = false;
                            transferEnded();
                        }
                    } catch (IOException e) {
                        throw new RawResourceDataSourceException(null, e, 2000);
                    }
                } catch (Throwable th) {
                    this.d = null;
                    if (this.g) {
                        this.g = false;
                        transferEnded();
                    }
                    throw th;
                }
            } catch (IOException e2) {
                throw new RawResourceDataSourceException(null, e2, 2000);
            }
        } catch (Throwable th2) {
            try {
                this.e = null;
                try {
                    if (this.d != null) {
                        this.d.close();
                    }
                    this.d = null;
                    if (this.g) {
                        this.g = false;
                        transferEnded();
                    }
                    throw th2;
                } catch (IOException e3) {
                    throw new RawResourceDataSourceException(null, e3, 2000);
                }
            } catch (Throwable th3) {
                this.d = null;
                if (this.g) {
                    this.g = false;
                    transferEnded();
                }
                throw th3;
            }
        }
    }
}
