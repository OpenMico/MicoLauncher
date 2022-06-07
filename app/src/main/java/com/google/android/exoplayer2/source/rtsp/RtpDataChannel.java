package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.DataSource;
import java.io.IOException;

/* loaded from: classes2.dex */
public interface RtpDataChannel extends DataSource {

    /* loaded from: classes2.dex */
    public interface Factory {
        RtpDataChannel createAndOpenDataChannel(int i) throws IOException;

        @Nullable
        default Factory createFallbackDataChannelFactory() {
            return null;
        }
    }

    String a();

    int b();

    @Nullable
    RtspMessageChannel.InterleavedBinaryDataListener c();
}
