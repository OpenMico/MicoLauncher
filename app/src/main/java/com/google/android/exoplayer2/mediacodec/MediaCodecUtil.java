package com.google.android.exoplayer2.mediacodec;

import android.annotation.SuppressLint;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.CheckResult;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@SuppressLint({"InlinedApi"})
/* loaded from: classes2.dex */
public final class MediaCodecUtil {
    private static final Pattern a = Pattern.compile("^\\D?(\\d+)$");
    @GuardedBy("MediaCodecUtil.class")
    private static final HashMap<a, List<MediaCodecInfo>> b = new HashMap<>();
    private static int c = -1;

    /* loaded from: classes2.dex */
    public interface b {
        int a();

        MediaCodecInfo a(int i);

        boolean a(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);

        boolean b();

        boolean b(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);
    }

    /* loaded from: classes2.dex */
    public interface e<T> {
        int getScore(T t);
    }

    private static int a(int i) {
        switch (i) {
            case 1:
            case 2:
                return 25344;
            case 8:
            case 16:
            case 32:
                return 101376;
            case 64:
                return 202752;
            case 128:
            case 256:
                return 414720;
            case 512:
                return 921600;
            case 1024:
                return 1310720;
            case 2048:
            case 4096:
                return 2097152;
            case 8192:
                return 2228224;
            case 16384:
                return 5652480;
            case 32768:
            case 65536:
                return 9437184;
            case 131072:
            case 262144:
            case 524288:
                return 35651584;
            default:
                return -1;
        }
    }

    private static int b(int i) {
        if (i == 66) {
            return 1;
        }
        if (i == 77) {
            return 2;
        }
        if (i == 88) {
            return 4;
        }
        if (i == 100) {
            return 8;
        }
        if (i == 110) {
            return 16;
        }
        if (i != 122) {
            return i != 244 ? -1 : 64;
        }
        return 32;
    }

    private static int c(int i) {
        switch (i) {
            case 10:
                return 1;
            case 11:
                return 4;
            case 12:
                return 8;
            case 13:
                return 16;
            default:
                switch (i) {
                    case 20:
                        return 32;
                    case 21:
                        return 64;
                    case 22:
                        return 128;
                    default:
                        switch (i) {
                            case 30:
                                return 256;
                            case 31:
                                return 512;
                            case 32:
                                return 1024;
                            default:
                                switch (i) {
                                    case 40:
                                        return 2048;
                                    case 41:
                                        return 4096;
                                    case 42:
                                        return 8192;
                                    default:
                                        switch (i) {
                                            case 50:
                                                return 16384;
                                            case 51:
                                                return 32768;
                                            case 52:
                                                return 65536;
                                            default:
                                                return -1;
                                        }
                                }
                        }
                }
        }
    }

    private static int d(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            default:
                return -1;
        }
    }

    private static int e(int i) {
        switch (i) {
            case 10:
                return 1;
            case 11:
                return 2;
            case 20:
                return 4;
            case 21:
                return 8;
            case 30:
                return 16;
            case 31:
                return 32;
            case 40:
                return 64;
            case 41:
                return 128;
            case 50:
                return 256;
            case 51:
                return 512;
            case 60:
                return 2048;
            case 61:
                return 4096;
            case 62:
                return 8192;
            default:
                return -1;
        }
    }

    private static int f(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
                return 128;
            case 8:
                return 256;
            case 9:
                return 512;
            case 10:
                return 1024;
            case 11:
                return 2048;
            case 12:
                return 4096;
            case 13:
                return 8192;
            case 14:
                return 16384;
            case 15:
                return 32768;
            case 16:
                return 65536;
            case 17:
                return 131072;
            case 18:
                return 262144;
            case 19:
                return 524288;
            case 20:
                return 1048576;
            case 21:
                return 2097152;
            case 22:
                return 4194304;
            case 23:
                return 8388608;
            default:
                return -1;
        }
    }

    private static int g(int i) {
        if (i == 17) {
            return 17;
        }
        if (i == 20) {
            return 20;
        }
        if (i == 23) {
            return 23;
        }
        if (i == 29) {
            return 29;
        }
        if (i == 39) {
            return 39;
        }
        if (i == 42) {
            return 42;
        }
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return -1;
        }
    }

    /* loaded from: classes2.dex */
    public static class DecoderQueryException extends Exception {
        private DecoderQueryException(Throwable th) {
            super("Failed to query underlying media codecs", th);
        }
    }

    private MediaCodecUtil() {
    }

    public static void warmDecoderInfoCache(String str, boolean z, boolean z2) {
        try {
            getDecoderInfos(str, z, z2);
        } catch (DecoderQueryException e2) {
            Log.e("MediaCodecUtil", "Codec warming failed", e2);
        }
    }

    public static synchronized void clearDecoderInfoCache() {
        synchronized (MediaCodecUtil.class) {
            b.clear();
        }
    }

    @Nullable
    public static MediaCodecInfo getDecryptOnlyDecoderInfo() throws DecoderQueryException {
        return getDecoderInfo(MimeTypes.AUDIO_RAW, false, false);
    }

    @Nullable
    public static MediaCodecInfo getDecoderInfo(String str, boolean z, boolean z2) throws DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(str, z, z2);
        if (decoderInfos.isEmpty()) {
            return null;
        }
        return decoderInfos.get(0);
    }

    public static synchronized List<MediaCodecInfo> getDecoderInfos(String str, boolean z, boolean z2) throws DecoderQueryException {
        b bVar;
        synchronized (MediaCodecUtil.class) {
            a aVar = new a(str, z, z2);
            List<MediaCodecInfo> list = b.get(aVar);
            if (list != null) {
                return list;
            }
            if (Util.SDK_INT >= 21) {
                bVar = new d(z, z2);
            } else {
                bVar = new c();
            }
            ArrayList<MediaCodecInfo> a2 = a(aVar, bVar);
            if (z && a2.isEmpty() && 21 <= Util.SDK_INT && Util.SDK_INT <= 23) {
                a2 = a(aVar, new c());
                if (!a2.isEmpty()) {
                    String str2 = a2.get(0).name;
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 63 + String.valueOf(str2).length());
                    sb.append("MediaCodecList API didn't list secure decoder for: ");
                    sb.append(str);
                    sb.append(". Assuming: ");
                    sb.append(str2);
                    Log.w("MediaCodecUtil", sb.toString());
                }
            }
            a(str, a2);
            List<MediaCodecInfo> unmodifiableList = Collections.unmodifiableList(a2);
            b.put(aVar, unmodifiableList);
            return unmodifiableList;
        }
    }

    @CheckResult
    public static List<MediaCodecInfo> getDecoderInfosSortedByFormatSupport(List<MediaCodecInfo> list, final Format format) {
        ArrayList arrayList = new ArrayList(list);
        a(arrayList, new e() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$MediaCodecUtil$-5rRfvIM6Dc3iBnD5jdWgMH5Oeg
            @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.e
            public final int getScore(Object obj) {
                int a2;
                a2 = MediaCodecUtil.a(Format.this, (MediaCodecInfo) obj);
                return a2;
            }
        });
        return arrayList;
    }

    public static /* synthetic */ int a(Format format, MediaCodecInfo mediaCodecInfo) {
        try {
            return mediaCodecInfo.isFormatSupported(format) ? 1 : 0;
        } catch (DecoderQueryException unused) {
            return -1;
        }
    }

    public static int maxH264DecodableFrameSize() throws DecoderQueryException {
        if (c == -1) {
            int i = 0;
            MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.VIDEO_H264, false, false);
            if (decoderInfo != null) {
                MediaCodecInfo.CodecProfileLevel[] profileLevels = decoderInfo.getProfileLevels();
                int length = profileLevels.length;
                int i2 = 0;
                while (i < length) {
                    i2 = Math.max(a(profileLevels[i].level), i2);
                    i++;
                }
                i = Math.max(i2, Util.SDK_INT >= 21 ? 345600 : 172800);
            }
            c = i;
        }
        return c;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0063, code lost:
        if (r3.equals("avc1") != false) goto L_0x0071;
     */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getCodecProfileAndLevel(com.google.android.exoplayer2.Format r6) {
        /*
            java.lang.String r0 = r6.codecs
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.String r0 = r6.codecs
            java.lang.String r2 = "\\."
            java.lang.String[] r0 = r0.split(r2)
            java.lang.String r2 = "video/dolby-vision"
            java.lang.String r3 = r6.sampleMimeType
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x001f
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = a(r6, r0)
            return r6
        L_0x001f:
            r2 = 0
            r3 = r0[r2]
            r4 = -1
            int r5 = r3.hashCode()
            switch(r5) {
                case 3004662: goto L_0x0066;
                case 3006243: goto L_0x005d;
                case 3006244: goto L_0x0053;
                case 3199032: goto L_0x0049;
                case 3214780: goto L_0x003f;
                case 3356560: goto L_0x0035;
                case 3624515: goto L_0x002b;
                default: goto L_0x002a;
            }
        L_0x002a:
            goto L_0x0070
        L_0x002b:
            java.lang.String r2 = "vp09"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 2
            goto L_0x0071
        L_0x0035:
            java.lang.String r2 = "mp4a"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 6
            goto L_0x0071
        L_0x003f:
            java.lang.String r2 = "hvc1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 4
            goto L_0x0071
        L_0x0049:
            java.lang.String r2 = "hev1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 3
            goto L_0x0071
        L_0x0053:
            java.lang.String r2 = "avc2"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 1
            goto L_0x0071
        L_0x005d:
            java.lang.String r5 = "avc1"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0070
            goto L_0x0071
        L_0x0066:
            java.lang.String r2 = "av01"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 5
            goto L_0x0071
        L_0x0070:
            r2 = r4
        L_0x0071:
            switch(r2) {
                case 0: goto L_0x0093;
                case 1: goto L_0x0093;
                case 2: goto L_0x008c;
                case 3: goto L_0x0085;
                case 4: goto L_0x0085;
                case 5: goto L_0x007c;
                case 6: goto L_0x0075;
                default: goto L_0x0074;
            }
        L_0x0074:
            return r1
        L_0x0075:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = e(r6, r0)
            return r6
        L_0x007c:
            java.lang.String r1 = r6.codecs
            com.google.android.exoplayer2.video.ColorInfo r6 = r6.colorInfo
            android.util.Pair r6 = a(r1, r0, r6)
            return r6
        L_0x0085:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = b(r6, r0)
            return r6
        L_0x008c:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = d(r6, r0)
            return r6
        L_0x0093:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = c(r6, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(com.google.android.exoplayer2.Format):android.util.Pair");
    }

    private static ArrayList<MediaCodecInfo> a(a aVar, b bVar) throws DecoderQueryException {
        int i;
        String str;
        String str2;
        Exception e2;
        try {
            ArrayList<MediaCodecInfo> arrayList = new ArrayList<>();
            String str3 = aVar.a;
            int a2 = bVar.a();
            boolean b2 = bVar.b();
            for (int i2 = 0; i2 < a2; i2 = i + 1) {
                MediaCodecInfo a3 = bVar.a(i2);
                if (a(a3)) {
                    i = i2;
                } else {
                    String name = a3.getName();
                    if (!a(a3, name, b2, str3)) {
                        i = i2;
                    } else {
                        String a4 = a(a3, name, str3);
                        if (a4 == null) {
                            i = i2;
                        } else {
                            try {
                                MediaCodecInfo.CodecCapabilities capabilitiesForType = a3.getCapabilitiesForType(a4);
                                boolean a5 = bVar.a("tunneled-playback", a4, capabilitiesForType);
                                boolean b3 = bVar.b("tunneled-playback", a4, capabilitiesForType);
                                if ((aVar.c || !b3) && (!aVar.c || a5)) {
                                    boolean a6 = bVar.a("secure-playback", a4, capabilitiesForType);
                                    boolean b4 = bVar.b("secure-playback", a4, capabilitiesForType);
                                    if ((aVar.b || !b4) && (!aVar.b || a6)) {
                                        boolean c2 = c(a3);
                                        boolean e3 = e(a3);
                                        boolean g = g(a3);
                                        if ((!b2 || aVar.b != a6) && (b2 || aVar.b)) {
                                            str2 = a4;
                                            str = name;
                                            i = i2;
                                            if (!b2 && a6) {
                                                arrayList.add(MediaCodecInfo.newInstance(String.valueOf(str).concat(".secure"), str3, str2, capabilitiesForType, c2, e3, g, false, true));
                                                return arrayList;
                                            }
                                        } else {
                                            str2 = a4;
                                            str = name;
                                            i = i2;
                                            try {
                                                arrayList.add(MediaCodecInfo.newInstance(name, str3, a4, capabilitiesForType, c2, e3, g, false, false));
                                            } catch (Exception e4) {
                                                e2 = e4;
                                                if (Util.SDK_INT > 23 || arrayList.isEmpty()) {
                                                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 25 + String.valueOf(str2).length());
                                                    sb.append("Failed to query codec ");
                                                    sb.append(str);
                                                    sb.append(" (");
                                                    sb.append(str2);
                                                    sb.append(")");
                                                    Log.e("MediaCodecUtil", sb.toString());
                                                    throw e2;
                                                }
                                                StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 46);
                                                sb2.append("Skipping codec ");
                                                sb2.append(str);
                                                sb2.append(" (failed to query capabilities)");
                                                Log.e("MediaCodecUtil", sb2.toString());
                                            }
                                        }
                                    } else {
                                        i = i2;
                                    }
                                } else {
                                    i = i2;
                                }
                            } catch (Exception e5) {
                                e2 = e5;
                                str2 = a4;
                                str = name;
                                i = i2;
                            }
                        }
                    }
                }
            }
            return arrayList;
        } catch (Exception e6) {
            throw new DecoderQueryException(e6);
        }
    }

    @Nullable
    private static String a(MediaCodecInfo mediaCodecInfo, String str, String str2) {
        String[] supportedTypes = mediaCodecInfo.getSupportedTypes();
        for (String str3 : supportedTypes) {
            if (str3.equalsIgnoreCase(str2)) {
                return str3;
            }
        }
        if (str2.equals(MimeTypes.VIDEO_DOLBY_VISION)) {
            if ("OMX.MS.HEVCDV.Decoder".equals(str)) {
                return "video/hevcdv";
            }
            if ("OMX.RTK.video.decoder".equals(str) || "OMX.realtek.video.decoder.tunneled".equals(str)) {
                return "video/dv_hevc";
            }
            return null;
        } else if (str2.equals(MimeTypes.AUDIO_ALAC) && "OMX.lge.alac.decoder".equals(str)) {
            return "audio/x-lg-alac";
        } else {
            if (!str2.equals(MimeTypes.AUDIO_FLAC) || !"OMX.lge.flac.decoder".equals(str)) {
                return null;
            }
            return "audio/x-lg-flac";
        }
    }

    private static boolean a(MediaCodecInfo mediaCodecInfo, String str, boolean z, String str2) {
        if (mediaCodecInfo.isEncoder() || (!z && str.endsWith(".secure"))) {
            return false;
        }
        if (Util.SDK_INT < 21 && ("CIPAACDecoder".equals(str) || "CIPMP3Decoder".equals(str) || "CIPVorbisDecoder".equals(str) || "CIPAMRNBDecoder".equals(str) || "AACDecoder".equals(str) || "MP3Decoder".equals(str))) {
            return false;
        }
        if (Util.SDK_INT < 18 && "OMX.MTK.AUDIO.DECODER.AAC".equals(str) && ("a70".equals(Util.DEVICE) || ("Xiaomi".equals(Util.MANUFACTURER) && Util.DEVICE.startsWith("HM")))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.mp3".equals(str) && ("dlxu".equals(Util.DEVICE) || "protou".equals(Util.DEVICE) || "ville".equals(Util.DEVICE) || "villeplus".equals(Util.DEVICE) || "villec2".equals(Util.DEVICE) || Util.DEVICE.startsWith("gee") || "C6602".equals(Util.DEVICE) || "C6603".equals(Util.DEVICE) || "C6606".equals(Util.DEVICE) || "C6616".equals(Util.DEVICE) || "L36h".equals(Util.DEVICE) || "SO-02E".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.aac".equals(str) && ("C1504".equals(Util.DEVICE) || "C1505".equals(Util.DEVICE) || "C1604".equals(Util.DEVICE) || "C1605".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT < 24 && (("OMX.SEC.aac.dec".equals(str) || "OMX.Exynos.AAC.Decoder".equals(str)) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("zerolte") || Util.DEVICE.startsWith("zenlte") || "SC-05G".equals(Util.DEVICE) || "marinelteatt".equals(Util.DEVICE) || "404SC".equals(Util.DEVICE) || "SC-04G".equals(Util.DEVICE) || "SCV31".equals(Util.DEVICE)))) {
            return false;
        }
        if (Util.SDK_INT <= 19 && "OMX.SEC.vp8.dec".equals(str) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("d2") || Util.DEVICE.startsWith("serrano") || Util.DEVICE.startsWith("jflte") || Util.DEVICE.startsWith("santos") || Util.DEVICE.startsWith("t0"))) {
            return false;
        }
        if (Util.SDK_INT > 19 || !Util.DEVICE.startsWith("jflte") || !"OMX.qcom.video.decoder.vp8".equals(str)) {
            return !MimeTypes.AUDIO_E_AC3_JOC.equals(str2) || !"OMX.MTK.AUDIO.DECODER.DSPAC3".equals(str);
        }
        return false;
    }

    private static void a(String str, List<MediaCodecInfo> list) {
        if (MimeTypes.AUDIO_RAW.equals(str)) {
            if (Util.SDK_INT < 26 && Util.DEVICE.equals("R9") && list.size() == 1 && list.get(0).name.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
                list.add(MediaCodecInfo.newInstance("OMX.google.raw.decoder", MimeTypes.AUDIO_RAW, MimeTypes.AUDIO_RAW, null, false, true, false, false, false));
            }
            a(list, $$Lambda$MediaCodecUtil$OvTDCRiFGaPlRk1v10V17XjgbkQ.INSTANCE);
        }
        if (Util.SDK_INT < 21 && list.size() > 1) {
            String str2 = list.get(0).name;
            if ("OMX.SEC.mp3.dec".equals(str2) || "OMX.SEC.MP3.Decoder".equals(str2) || "OMX.brcm.audio.mp3.decoder".equals(str2)) {
                a(list, $$Lambda$MediaCodecUtil$9u1zd60NKDSmNct8I5tUfOmGZkQ.INSTANCE);
            }
        }
        if (Util.SDK_INT < 32 && list.size() > 1 && "OMX.qti.audio.decoder.flac".equals(list.get(0).name)) {
            list.add(list.remove(0));
        }
    }

    public static /* synthetic */ int b(MediaCodecInfo mediaCodecInfo) {
        String str = mediaCodecInfo.name;
        if (str.startsWith("OMX.google") || str.startsWith("c2.android")) {
            return 1;
        }
        return (Util.SDK_INT >= 26 || !str.equals("OMX.MTK.AUDIO.DECODER.RAW")) ? 0 : -1;
    }

    public static /* synthetic */ int a(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.name.startsWith("OMX.google") ? 1 : 0;
    }

    private static boolean a(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 29 && b(mediaCodecInfo);
    }

    @RequiresApi(29)
    private static boolean b(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isAlias();
    }

    private static boolean c(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return d(mediaCodecInfo);
        }
        return !e(mediaCodecInfo);
    }

    @RequiresApi(29)
    private static boolean d(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isHardwareAccelerated();
    }

    private static boolean e(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return f(mediaCodecInfo);
        }
        String lowerCase = Ascii.toLowerCase(mediaCodecInfo.getName());
        if (lowerCase.startsWith("arc.")) {
            return false;
        }
        return lowerCase.startsWith("omx.google.") || lowerCase.startsWith("omx.ffmpeg.") || (lowerCase.startsWith("omx.sec.") && lowerCase.contains(".sw.")) || lowerCase.equals("omx.qcom.video.decoder.hevcswvdec") || lowerCase.startsWith("c2.android.") || lowerCase.startsWith("c2.google.") || (!lowerCase.startsWith("omx.") && !lowerCase.startsWith("c2."));
    }

    @RequiresApi(29)
    private static boolean f(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isSoftwareOnly();
    }

    private static boolean g(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return h(mediaCodecInfo);
        }
        String lowerCase = Ascii.toLowerCase(mediaCodecInfo.getName());
        return !lowerCase.startsWith("omx.google.") && !lowerCase.startsWith("c2.android.") && !lowerCase.startsWith("c2.google.");
    }

    @RequiresApi(29)
    private static boolean h(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isVendor();
    }

    @Nullable
    private static Pair<Integer, Integer> a(String str, String[] strArr) {
        if (strArr.length < 3) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(valueOf) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        Matcher matcher = a.matcher(strArr[1]);
        if (!matcher.matches()) {
            String valueOf2 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(valueOf2) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        String group = matcher.group(1);
        Integer b2 = b(group);
        if (b2 == null) {
            String valueOf3 = String.valueOf(group);
            Log.w("MediaCodecUtil", valueOf3.length() != 0 ? "Unknown Dolby Vision profile string: ".concat(valueOf3) : new String("Unknown Dolby Vision profile string: "));
            return null;
        }
        String str2 = strArr[2];
        Integer c2 = c(str2);
        if (c2 != null) {
            return new Pair<>(b2, c2);
        }
        String valueOf4 = String.valueOf(str2);
        Log.w("MediaCodecUtil", valueOf4.length() != 0 ? "Unknown Dolby Vision level string: ".concat(valueOf4) : new String("Unknown Dolby Vision level string: "));
        return null;
    }

    @Nullable
    private static Pair<Integer, Integer> b(String str, String[] strArr) {
        if (strArr.length < 4) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(valueOf) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        int i = 1;
        Matcher matcher = a.matcher(strArr[1]);
        if (!matcher.matches()) {
            String valueOf2 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(valueOf2) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        String group = matcher.group(1);
        if (!"1".equals(group)) {
            if ("2".equals(group)) {
                i = 2;
            } else {
                String valueOf3 = String.valueOf(group);
                Log.w("MediaCodecUtil", valueOf3.length() != 0 ? "Unknown HEVC profile string: ".concat(valueOf3) : new String("Unknown HEVC profile string: "));
                return null;
            }
        }
        String str2 = strArr[3];
        Integer a2 = a(str2);
        if (a2 != null) {
            return new Pair<>(Integer.valueOf(i), a2);
        }
        String valueOf4 = String.valueOf(str2);
        Log.w("MediaCodecUtil", valueOf4.length() != 0 ? "Unknown HEVC level string: ".concat(valueOf4) : new String("Unknown HEVC level string: "));
        return null;
    }

    @Nullable
    private static Pair<Integer, Integer> c(String str, String[] strArr) {
        int i;
        int i2;
        if (strArr.length < 2) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
        try {
            if (strArr[1].length() == 6) {
                i2 = Integer.parseInt(strArr[1].substring(0, 2), 16);
                i = Integer.parseInt(strArr[1].substring(4), 16);
            } else if (strArr.length >= 3) {
                i2 = Integer.parseInt(strArr[1]);
                i = Integer.parseInt(strArr[2]);
            } else {
                String valueOf2 = String.valueOf(str);
                Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf2) : new String("Ignoring malformed AVC codec string: "));
                return null;
            }
            int b2 = b(i2);
            if (b2 == -1) {
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown AVC profile: ");
                sb.append(i2);
                Log.w("MediaCodecUtil", sb.toString());
                return null;
            }
            int c2 = c(i);
            if (c2 != -1) {
                return new Pair<>(Integer.valueOf(b2), Integer.valueOf(c2));
            }
            StringBuilder sb2 = new StringBuilder(30);
            sb2.append("Unknown AVC level: ");
            sb2.append(i);
            Log.w("MediaCodecUtil", sb2.toString());
            return null;
        } catch (NumberFormatException unused) {
            String valueOf3 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf3.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf3) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
    }

    @Nullable
    private static Pair<Integer, Integer> d(String str, String[] strArr) {
        if (strArr.length < 3) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed VP9 codec string: ".concat(valueOf) : new String("Ignoring malformed VP9 codec string: "));
            return null;
        }
        try {
            int parseInt = Integer.parseInt(strArr[1]);
            int parseInt2 = Integer.parseInt(strArr[2]);
            int d2 = d(parseInt);
            if (d2 == -1) {
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown VP9 profile: ");
                sb.append(parseInt);
                Log.w("MediaCodecUtil", sb.toString());
                return null;
            }
            int e2 = e(parseInt2);
            if (e2 != -1) {
                return new Pair<>(Integer.valueOf(d2), Integer.valueOf(e2));
            }
            StringBuilder sb2 = new StringBuilder(30);
            sb2.append("Unknown VP9 level: ");
            sb2.append(parseInt2);
            Log.w("MediaCodecUtil", sb2.toString());
            return null;
        } catch (NumberFormatException unused) {
            String valueOf2 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed VP9 codec string: ".concat(valueOf2) : new String("Ignoring malformed VP9 codec string: "));
            return null;
        }
    }

    @Nullable
    private static Pair<Integer, Integer> a(String str, String[] strArr, @Nullable ColorInfo colorInfo) {
        if (strArr.length < 4) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed AV1 codec string: ".concat(valueOf) : new String("Ignoring malformed AV1 codec string: "));
            return null;
        }
        int i = 1;
        try {
            int parseInt = Integer.parseInt(strArr[1]);
            int parseInt2 = Integer.parseInt(strArr[2].substring(0, 2));
            int parseInt3 = Integer.parseInt(strArr[3]);
            if (parseInt != 0) {
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown AV1 profile: ");
                sb.append(parseInt);
                Log.w("MediaCodecUtil", sb.toString());
                return null;
            } else if (parseInt3 == 8 || parseInt3 == 10) {
                if (parseInt3 != 8) {
                    i = (colorInfo == null || !(colorInfo.hdrStaticInfo != null || colorInfo.colorTransfer == 7 || colorInfo.colorTransfer == 6)) ? 2 : 4096;
                }
                int f = f(parseInt2);
                if (f != -1) {
                    return new Pair<>(Integer.valueOf(i), Integer.valueOf(f));
                }
                StringBuilder sb2 = new StringBuilder(30);
                sb2.append("Unknown AV1 level: ");
                sb2.append(parseInt2);
                Log.w("MediaCodecUtil", sb2.toString());
                return null;
            } else {
                StringBuilder sb3 = new StringBuilder(34);
                sb3.append("Unknown AV1 bit depth: ");
                sb3.append(parseInt3);
                Log.w("MediaCodecUtil", sb3.toString());
                return null;
            }
        } catch (NumberFormatException unused) {
            String valueOf2 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed AV1 codec string: ".concat(valueOf2) : new String("Ignoring malformed AV1 codec string: "));
            return null;
        }
    }

    @Nullable
    private static Pair<Integer, Integer> e(String str, String[] strArr) {
        int g;
        if (strArr.length != 3) {
            String valueOf = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(valueOf) : new String("Ignoring malformed MP4A codec string: "));
            return null;
        }
        try {
            if (MimeTypes.AUDIO_AAC.equals(MimeTypes.getMimeTypeFromMp4ObjectType(Integer.parseInt(strArr[1], 16))) && (g = g(Integer.parseInt(strArr[2]))) != -1) {
                return new Pair<>(Integer.valueOf(g), 0);
            }
        } catch (NumberFormatException unused) {
            String valueOf2 = String.valueOf(str);
            Log.w("MediaCodecUtil", valueOf2.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(valueOf2) : new String("Ignoring malformed MP4A codec string: "));
        }
        return null;
    }

    public static /* synthetic */ int a(e eVar, Object obj, Object obj2) {
        return eVar.getScore(obj2) - eVar.getScore(obj);
    }

    private static <T> void a(List<T> list, final e<T> eVar) {
        Collections.sort(list, new Comparator() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$MediaCodecUtil$Oihj0ewX7Fy_sYWvBqeukQVNMpE
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int a2;
                a2 = MediaCodecUtil.a(MediaCodecUtil.e.this, obj, obj2);
                return a2;
            }
        });
    }

    @RequiresApi(21)
    /* loaded from: classes2.dex */
    public static final class d implements b {
        private final int a;
        @Nullable
        private MediaCodecInfo[] b;

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean b() {
            return true;
        }

        public d(boolean z, boolean z2) {
            this.a = (z || z2) ? 1 : 0;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public int a() {
            c();
            return this.b.length;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public MediaCodecInfo a(int i) {
            c();
            return this.b[i];
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean a(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureSupported(str);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean b(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureRequired(str);
        }

        @EnsuresNonNull({"mediaCodecInfos"})
        private void c() {
            if (this.b == null) {
                this.b = new MediaCodecList(this.a).getCodecInfos();
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class c implements b {
        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean b() {
            return false;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean b(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return false;
        }

        private c() {
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public int a() {
            return MediaCodecList.getCodecCount();
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public MediaCodecInfo a(int i) {
            return MediaCodecList.getCodecInfoAt(i);
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b
        public boolean a(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return "secure-playback".equals(str) && MimeTypes.VIDEO_H264.equals(str2);
        }
    }

    /* loaded from: classes2.dex */
    public static final class a {
        public final String a;
        public final boolean b;
        public final boolean c;

        public a(String str, boolean z, boolean z2) {
            this.a = str;
            this.b = z;
            this.c = z2;
        }

        public int hashCode() {
            int i = 1231;
            int hashCode = (((this.a.hashCode() + 31) * 31) + (this.b ? 1231 : 1237)) * 31;
            if (!this.c) {
                i = 1237;
            }
            return hashCode + i;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != a.class) {
                return false;
            }
            a aVar = (a) obj;
            return TextUtils.equals(this.a, aVar.a) && this.b == aVar.b && this.c == aVar.c;
        }
    }

    @Nullable
    private static Integer a(@Nullable String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 70821:
                if (str.equals("H30")) {
                    c2 = '\r';
                    break;
                }
                break;
            case 70914:
                if (str.equals("H60")) {
                    c2 = 14;
                    break;
                }
                break;
            case 70917:
                if (str.equals("H63")) {
                    c2 = 15;
                    break;
                }
                break;
            case 71007:
                if (str.equals("H90")) {
                    c2 = 16;
                    break;
                }
                break;
            case 71010:
                if (str.equals("H93")) {
                    c2 = 17;
                    break;
                }
                break;
            case 74665:
                if (str.equals("L30")) {
                    c2 = 0;
                    break;
                }
                break;
            case 74758:
                if (str.equals("L60")) {
                    c2 = 1;
                    break;
                }
                break;
            case 74761:
                if (str.equals("L63")) {
                    c2 = 2;
                    break;
                }
                break;
            case 74851:
                if (str.equals("L90")) {
                    c2 = 3;
                    break;
                }
                break;
            case 74854:
                if (str.equals("L93")) {
                    c2 = 4;
                    break;
                }
                break;
            case 2193639:
                if (str.equals("H120")) {
                    c2 = 18;
                    break;
                }
                break;
            case 2193642:
                if (str.equals("H123")) {
                    c2 = 19;
                    break;
                }
                break;
            case 2193732:
                if (str.equals("H150")) {
                    c2 = 20;
                    break;
                }
                break;
            case 2193735:
                if (str.equals("H153")) {
                    c2 = 21;
                    break;
                }
                break;
            case 2193738:
                if (str.equals("H156")) {
                    c2 = 22;
                    break;
                }
                break;
            case 2193825:
                if (str.equals("H180")) {
                    c2 = 23;
                    break;
                }
                break;
            case 2193828:
                if (str.equals("H183")) {
                    c2 = 24;
                    break;
                }
                break;
            case 2193831:
                if (str.equals("H186")) {
                    c2 = 25;
                    break;
                }
                break;
            case 2312803:
                if (str.equals("L120")) {
                    c2 = 5;
                    break;
                }
                break;
            case 2312806:
                if (str.equals("L123")) {
                    c2 = 6;
                    break;
                }
                break;
            case 2312896:
                if (str.equals("L150")) {
                    c2 = 7;
                    break;
                }
                break;
            case 2312899:
                if (str.equals("L153")) {
                    c2 = '\b';
                    break;
                }
                break;
            case 2312902:
                if (str.equals("L156")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 2312989:
                if (str.equals("L180")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 2312992:
                if (str.equals("L183")) {
                    c2 = 11;
                    break;
                }
                break;
            case 2312995:
                if (str.equals("L186")) {
                    c2 = '\f';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 16;
            case 3:
                return 64;
            case 4:
                return 256;
            case 5:
                return 1024;
            case 6:
                return 4096;
            case 7:
                return 16384;
            case '\b':
                return 65536;
            case '\t':
                return 262144;
            case '\n':
                return 1048576;
            case 11:
                return 4194304;
            case '\f':
                return 16777216;
            case '\r':
                return 2;
            case 14:
                return 8;
            case 15:
                return 32;
            case 16:
                return 128;
            case 17:
                return 512;
            case 18:
                return 2048;
            case 19:
                return 8192;
            case 20:
                return 32768;
            case 21:
                return 131072;
            case 22:
                return 524288;
            case 23:
                return 2097152;
            case 24:
                return 8388608;
            case 25:
                return 33554432;
            default:
                return null;
        }
    }

    @Nullable
    private static Integer b(@Nullable String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 1536:
                if (str.equals("00")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1537:
                if (str.equals("01")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1538:
                if (str.equals("02")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1539:
                if (str.equals("03")) {
                    c2 = 3;
                    break;
                }
                break;
            case 1540:
                if (str.equals("04")) {
                    c2 = 4;
                    break;
                }
                break;
            case 1541:
                if (str.equals("05")) {
                    c2 = 5;
                    break;
                }
                break;
            case 1542:
                if (str.equals("06")) {
                    c2 = 6;
                    break;
                }
                break;
            case 1543:
                if (str.equals("07")) {
                    c2 = 7;
                    break;
                }
                break;
            case 1544:
                if (str.equals("08")) {
                    c2 = '\b';
                    break;
                }
                break;
            case 1545:
                if (str.equals("09")) {
                    c2 = '\t';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
                return 128;
            case '\b':
                return 256;
            case '\t':
                return 512;
            default:
                return null;
        }
    }

    @Nullable
    private static Integer c(@Nullable String str) {
        if (str == null) {
            return null;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        switch (hashCode) {
            case 1537:
                if (str.equals("01")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1538:
                if (str.equals("02")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1539:
                if (str.equals("03")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1540:
                if (str.equals("04")) {
                    c2 = 3;
                    break;
                }
                break;
            case 1541:
                if (str.equals("05")) {
                    c2 = 4;
                    break;
                }
                break;
            case 1542:
                if (str.equals("06")) {
                    c2 = 5;
                    break;
                }
                break;
            case 1543:
                if (str.equals("07")) {
                    c2 = 6;
                    break;
                }
                break;
            case 1544:
                if (str.equals("08")) {
                    c2 = 7;
                    break;
                }
                break;
            case 1545:
                if (str.equals("09")) {
                    c2 = '\b';
                    break;
                }
                break;
            default:
                switch (hashCode) {
                    case 1567:
                        if (str.equals("10")) {
                            c2 = '\t';
                            break;
                        }
                        break;
                    case 1568:
                        if (str.equals("11")) {
                            c2 = '\n';
                            break;
                        }
                        break;
                    case 1569:
                        if (str.equals("12")) {
                            c2 = 11;
                            break;
                        }
                        break;
                    case 1570:
                        if (str.equals("13")) {
                            c2 = '\f';
                            break;
                        }
                        break;
                }
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
                return 128;
            case '\b':
                return 256;
            case '\t':
                return 512;
            case '\n':
                return 1024;
            case 11:
                return 2048;
            case '\f':
                return 4096;
            default:
                return null;
        }
    }
}
