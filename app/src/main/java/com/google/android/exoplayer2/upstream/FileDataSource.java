package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes2.dex */
public final class FileDataSource extends BaseDataSource {
    @Nullable
    private RandomAccessFile a;
    @Nullable
    private Uri b;
    private long c;
    private boolean d;

    /* loaded from: classes2.dex */
    public static class FileDataSourceException extends DataSourceException {
        @Deprecated
        public FileDataSourceException(Exception exc) {
            super(exc, 2000);
        }

        @Deprecated
        public FileDataSourceException(String str, IOException iOException) {
            super(str, iOException, 2000);
        }

        public FileDataSourceException(Throwable th, int i) {
            super(th, i);
        }

        public FileDataSourceException(@Nullable String str, @Nullable Throwable th, int i) {
            super(str, th, i);
        }
    }

    /* loaded from: classes2.dex */
    public static final class Factory implements DataSource.Factory {
        @Nullable
        private TransferListener a;

        public Factory setListener(@Nullable TransferListener transferListener) {
            this.a = transferListener;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        public FileDataSource createDataSource() {
            FileDataSource fileDataSource = new FileDataSource();
            TransferListener transferListener = this.a;
            if (transferListener != null) {
                fileDataSource.addTransferListener(transferListener);
            }
            return fileDataSource;
        }
    }

    public FileDataSource() {
        super(false);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws FileDataSourceException {
        Uri uri = dataSpec.uri;
        this.b = uri;
        transferInitializing(dataSpec);
        this.a = a(uri);
        try {
            this.a.seek(dataSpec.position);
            this.c = dataSpec.length == -1 ? this.a.length() - dataSpec.position : dataSpec.length;
            if (this.c >= 0) {
                this.d = true;
                transferStarted(dataSpec);
                return this.c;
            }
            throw new FileDataSourceException(null, null, 2008);
        } catch (IOException e) {
            throw new FileDataSourceException(e, 2000);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws FileDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        if (this.c == 0) {
            return -1;
        }
        try {
            int read = ((RandomAccessFile) Util.castNonNull(this.a)).read(bArr, i, (int) Math.min(this.c, i2));
            if (read > 0) {
                this.c -= read;
                bytesTransferred(read);
            }
            return read;
        } catch (IOException e) {
            throw new FileDataSourceException(e, 2000);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.b;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws FileDataSourceException {
        try {
            this.b = null;
            try {
                if (this.a != null) {
                    this.a.close();
                }
            } catch (IOException e) {
                throw new FileDataSourceException(e, 2000);
            }
        } finally {
            this.a = null;
            if (this.d) {
                this.d = false;
                transferEnded();
            }
        }
    }

    private static RandomAccessFile a(Uri uri) throws FileDataSourceException {
        int i = PlaybackException.ERROR_CODE_IO_NO_PERMISSION;
        try {
            return new RandomAccessFile((String) Assertions.checkNotNull(uri.getPath()), "r");
        } catch (FileNotFoundException e) {
            if (!TextUtils.isEmpty(uri.getQuery()) || !TextUtils.isEmpty(uri.getFragment())) {
                throw new FileDataSourceException(String.format("uri has query and/or fragment, which are not supported. Did you call Uri.parse() on a string containing '?' or '#'? Use Uri.fromFile(new File(path)) to avoid this. path=%s,query=%s,fragment=%s", uri.getPath(), uri.getQuery(), uri.getFragment()), e, 1004);
            }
            if (Util.SDK_INT < 21 || !a.b(e.getCause())) {
                i = 2005;
            }
            throw new FileDataSourceException(e, i);
        } catch (SecurityException e2) {
            throw new FileDataSourceException(e2, (int) PlaybackException.ERROR_CODE_IO_NO_PERMISSION);
        } catch (RuntimeException e3) {
            throw new FileDataSourceException(e3, 2000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes2.dex */
    public static final class a {
        /* JADX INFO: Access modifiers changed from: private */
        @DoNotInline
        public static boolean b(@Nullable Throwable th) {
            return (th instanceof ErrnoException) && ((ErrnoException) th).errno == OsConstants.EACCES;
        }
    }
}
