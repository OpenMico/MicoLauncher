package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.URLDecoder;

/* loaded from: classes2.dex */
public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    @Nullable
    private DataSpec a;
    @Nullable
    private byte[] b;
    private int c;
    private int d;

    public DataSchemeDataSource() {
        super(false);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        transferInitializing(dataSpec);
        this.a = dataSpec;
        Uri uri = dataSpec.uri;
        String scheme = uri.getScheme();
        boolean equals = "data".equals(scheme);
        String valueOf = String.valueOf(scheme);
        Assertions.checkArgument(equals, valueOf.length() != 0 ? "Unsupported scheme: ".concat(valueOf) : new String("Unsupported scheme: "));
        String[] split = Util.split(uri.getSchemeSpecificPart(), Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 2) {
            String str = split[1];
            if (split[0].contains(";base64")) {
                try {
                    this.b = Base64.decode(str, 0);
                } catch (IllegalArgumentException e) {
                    String valueOf2 = String.valueOf(str);
                    throw ParserException.createForMalformedDataOfUnknownType(valueOf2.length() != 0 ? "Error while parsing Base64 encoded string: ".concat(valueOf2) : new String("Error while parsing Base64 encoded string: "), e);
                }
            } else {
                this.b = Util.getUtf8Bytes(URLDecoder.decode(str, Charsets.US_ASCII.name()));
            }
            if (dataSpec.position <= this.b.length) {
                this.c = (int) dataSpec.position;
                this.d = this.b.length - this.c;
                if (dataSpec.length != -1) {
                    this.d = (int) Math.min(this.d, dataSpec.length);
                }
                transferStarted(dataSpec);
                return dataSpec.length != -1 ? dataSpec.length : this.d;
            }
            this.b = null;
            throw new DataSourceException(2008);
        }
        String valueOf3 = String.valueOf(uri);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 23);
        sb.append("Unexpected URI format: ");
        sb.append(valueOf3);
        throw ParserException.createForMalformedDataOfUnknownType(sb.toString(), null);
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.d;
        if (i3 == 0) {
            return -1;
        }
        int min = Math.min(i2, i3);
        System.arraycopy(Util.castNonNull(this.b), this.c, bArr, i, min);
        this.c += min;
        this.d -= min;
        bytesTransferred(min);
        return min;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        DataSpec dataSpec = this.a;
        if (dataSpec != null) {
            return dataSpec.uri;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.b != null) {
            this.b = null;
            transferEnded();
        }
        this.a = null;
    }
}
