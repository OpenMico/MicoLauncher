package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.decoder.DecoderException;

/* loaded from: classes2.dex */
public class MediaCodecDecoderException extends DecoderException {
    @Nullable
    public final MediaCodecInfo codecInfo;
    @Nullable
    public final String diagnosticInfo;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaCodecDecoderException(java.lang.Throwable r5, @androidx.annotation.Nullable com.google.android.exoplayer2.mediacodec.MediaCodecInfo r6) {
        /*
            r4 = this;
            java.lang.String r0 = "Decoder failed: "
            r1 = 0
            if (r6 != 0) goto L_0x0007
            r2 = r1
            goto L_0x0009
        L_0x0007:
            java.lang.String r2 = r6.name
        L_0x0009:
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r3 = r2.length()
            if (r3 == 0) goto L_0x0018
            java.lang.String r0 = r0.concat(r2)
            goto L_0x001e
        L_0x0018:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r0)
            r0 = r2
        L_0x001e:
            r4.<init>(r0, r5)
            r4.codecInfo = r6
            int r6 = com.google.android.exoplayer2.util.Util.SDK_INT
            r0 = 21
            if (r6 < r0) goto L_0x002d
            java.lang.String r1 = a(r5)
        L_0x002d:
            r4.diagnosticInfo = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException.<init>(java.lang.Throwable, com.google.android.exoplayer2.mediacodec.MediaCodecInfo):void");
    }

    @Nullable
    @RequiresApi(21)
    private static String a(Throwable th) {
        if (th instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) th).getDiagnosticInfo();
        }
        return null;
    }
}
