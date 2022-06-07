package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.xiaomi.mipush.sdk.Constants;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RtspMediaTrack.java */
/* loaded from: classes2.dex */
public final class f {
    public final RtpPayloadFormat a;
    public final Uri b;

    public f(MediaDescription mediaDescription, Uri uri) {
        Assertions.checkArgument(mediaDescription.i.containsKey("control"));
        this.a = a(mediaDescription);
        this.b = a(uri, (String) Util.castNonNull(mediaDescription.i.get("control")));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        return this.a.equals(fVar.a) && this.b.equals(fVar.b);
    }

    public int hashCode() {
        return ((217 + this.a.hashCode()) * 31) + this.b.hashCode();
    }

    @VisibleForTesting
    static RtpPayloadFormat a(MediaDescription mediaDescription) {
        int i;
        char c;
        Format.Builder builder = new Format.Builder();
        if (mediaDescription.e > 0) {
            builder.setAverageBitrate(mediaDescription.e);
        }
        int i2 = mediaDescription.j.payloadType;
        String mimeTypeFromRtpMediaType = RtpPayloadFormat.getMimeTypeFromRtpMediaType(mediaDescription.j.mediaEncoding);
        builder.setSampleMimeType(mimeTypeFromRtpMediaType);
        int i3 = mediaDescription.j.clockRate;
        if ("audio".equals(mediaDescription.a)) {
            i = a(mediaDescription.j.encodingParameters, mimeTypeFromRtpMediaType);
            builder.setSampleRate(i3).setChannelCount(i);
        } else {
            i = -1;
        }
        ImmutableMap<String, String> a = mediaDescription.a();
        int hashCode = mimeTypeFromRtpMediaType.hashCode();
        boolean z = false;
        if (hashCode == -53558318) {
            if (mimeTypeFromRtpMediaType.equals(MimeTypes.AUDIO_AAC)) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 187078296) {
            if (hashCode == 1331836730 && mimeTypeFromRtpMediaType.equals(MimeTypes.VIDEO_H264)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (mimeTypeFromRtpMediaType.equals(MimeTypes.AUDIO_AC3)) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                Assertions.checkArgument(i != -1);
                Assertions.checkArgument(!a.isEmpty());
                a(builder, a, i, i3);
                break;
            case 1:
                Assertions.checkArgument(!a.isEmpty());
                a(builder, a);
                break;
        }
        Assertions.checkArgument(i3 > 0);
        if (i2 >= 96) {
            z = true;
        }
        Assertions.checkArgument(z);
        return new RtpPayloadFormat(builder.build(), i2, i3, a);
    }

    private static int a(int i, String str) {
        return i != -1 ? i : str.equals(MimeTypes.AUDIO_AC3) ? 6 : 1;
    }

    private static void a(Format.Builder builder, ImmutableMap<String, String> immutableMap, int i, int i2) {
        Assertions.checkArgument(immutableMap.containsKey("profile-level-id"));
        String valueOf = String.valueOf("mp4a.40.");
        String valueOf2 = String.valueOf((String) Assertions.checkNotNull(immutableMap.get("profile-level-id")));
        builder.setCodecs(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        builder.setInitializationData(ImmutableList.of(AacUtil.buildAacLcAudioSpecificConfig(i2, i)));
    }

    private static void a(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        Assertions.checkArgument(immutableMap.containsKey("sprop-parameter-sets"));
        String[] split = Util.split((String) Assertions.checkNotNull(immutableMap.get("sprop-parameter-sets")), Constants.ACCEPT_TIME_SEPARATOR_SP);
        Assertions.checkArgument(split.length == 2);
        ImmutableList of = ImmutableList.of(a(split[0]), a(split[1]));
        builder.setInitializationData(of);
        byte[] bArr = (byte[]) of.get(0);
        NalUnitUtil.SpsData parseSpsNalUnit = NalUnitUtil.parseSpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(parseSpsNalUnit.pixelWidthAspectRatio);
        builder.setHeight(parseSpsNalUnit.height);
        builder.setWidth(parseSpsNalUnit.width);
        String str = immutableMap.get("profile-level-id");
        if (str != null) {
            String valueOf = String.valueOf("avc1.");
            String valueOf2 = String.valueOf(str);
            builder.setCodecs(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            return;
        }
        builder.setCodecs(CodecSpecificDataUtil.buildAvcCodecString(parseSpsNalUnit.profileIdc, parseSpsNalUnit.constraintsFlagsAndReservedZero2Bits, parseSpsNalUnit.levelIdc));
    }

    private static byte[] a(String str) {
        byte[] decode = Base64.decode(str, 0);
        byte[] bArr = new byte[decode.length + NalUnitUtil.NAL_START_CODE.length];
        System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, bArr, 0, NalUnitUtil.NAL_START_CODE.length);
        System.arraycopy(decode, 0, bArr, NalUnitUtil.NAL_START_CODE.length, decode.length);
        return bArr;
    }

    private static Uri a(Uri uri, String str) {
        Uri parse = Uri.parse(str);
        return parse.isAbsolute() ? parse : str.equals("*") ? uri : uri.buildUpon().appendEncodedPath(str).build();
    }
}
