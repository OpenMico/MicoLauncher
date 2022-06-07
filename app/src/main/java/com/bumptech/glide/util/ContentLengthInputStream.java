package com.bumptech.glide.util;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class ContentLengthInputStream extends FilterInputStream {
    private final long a;
    private int b;

    @NonNull
    public static InputStream obtain(@NonNull InputStream inputStream, @Nullable String str) {
        return obtain(inputStream, a(str));
    }

    @NonNull
    public static InputStream obtain(@NonNull InputStream inputStream, long j) {
        return new ContentLengthInputStream(inputStream, j);
    }

    private static int a(@Nullable String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                if (Log.isLoggable("ContentLengthStream", 3)) {
                    Log.d("ContentLengthStream", "failed to parse content length header: " + str, e);
                }
            }
        }
        return -1;
    }

    private ContentLengthInputStream(@NonNull InputStream inputStream, long j) {
        super(inputStream);
        this.a = j;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int available() throws IOException {
        return (int) Math.max(this.a - this.b, this.in.available());
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        int read;
        read = super.read();
        a(read >= 0 ? 1 : -1);
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        return a(super.read(bArr, i, i2));
    }

    private int a(int i) throws IOException {
        if (i >= 0) {
            this.b += i;
        } else if (this.a - this.b > 0) {
            throw new IOException("Failed to read all expected data, expected: " + this.a + ", but read: " + this.b);
        }
        return i;
    }
}
