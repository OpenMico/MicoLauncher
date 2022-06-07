package com.google.android.exoplayer2.source.rtsp.reader;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes2.dex */
public final class DefaultRtpPayloadReaderFactory implements RtpPayloadReader.Factory {
    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader.Factory
    @Nullable
    public RtpPayloadReader createPayloadReader(RtpPayloadFormat rtpPayloadFormat) {
        char c;
        String str = (String) Assertions.checkNotNull(rtpPayloadFormat.format.sampleMimeType);
        int hashCode = str.hashCode();
        if (hashCode == -53558318) {
            if (str.equals(MimeTypes.AUDIO_AAC)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 187078296) {
            if (hashCode == 1331836730 && str.equals(MimeTypes.VIDEO_H264)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals(MimeTypes.AUDIO_AC3)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return new RtpAc3Reader(rtpPayloadFormat);
            case 1:
                return new a(rtpPayloadFormat);
            case 2:
                return new b(rtpPayloadFormat);
            default:
                return null;
        }
    }
}
