package com.google.android.exoplayer2.transformer;

import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import java.io.IOException;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
interface Muxer {

    /* loaded from: classes2.dex */
    public interface Factory {
        Muxer create(ParcelFileDescriptor parcelFileDescriptor, String str) throws IOException;

        Muxer create(String str, String str2) throws IOException;

        boolean supportsOutputMimeType(String str);
    }

    int a(Format format);

    void a(int i, ByteBuffer byteBuffer, boolean z, long j);

    void a(boolean z);

    boolean a(@Nullable String str);
}
