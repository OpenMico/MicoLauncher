package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Iterables;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes2.dex */
public final class HlsPlaylistParser implements ParsingLoadable.Parser<HlsPlaylist> {
    private final HlsMasterPlaylist aa;
    @Nullable
    private final HlsMediaPlaylist ab;
    private static final Pattern a = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern b = Pattern.compile("VIDEO=\"(.+?)\"");
    private static final Pattern c = Pattern.compile("AUDIO=\"(.+?)\"");
    private static final Pattern d = Pattern.compile("SUBTITLES=\"(.+?)\"");
    private static final Pattern e = Pattern.compile("CLOSED-CAPTIONS=\"(.+?)\"");
    private static final Pattern f = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern g = Pattern.compile("CHANNELS=\"(.+?)\"");
    private static final Pattern h = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern i = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern j = Pattern.compile("FRAME-RATE=([\\d\\.]+)\\b");
    private static final Pattern k = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern l = Pattern.compile("DURATION=([\\d\\.]+)\\b");
    private static final Pattern m = Pattern.compile("PART-TARGET=([\\d\\.]+)\\b");
    private static final Pattern n = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final Pattern o = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern p = Pattern.compile("CAN-SKIP-UNTIL=([\\d\\.]+)\\b");
    private static final Pattern q = d("CAN-SKIP-DATERANGES");
    private static final Pattern r = Pattern.compile("SKIPPED-SEGMENTS=(\\d+)\\b");
    private static final Pattern s = Pattern.compile("[:|,]HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern t = Pattern.compile("PART-HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern u = d("CAN-BLOCK-RELOAD");
    private static final Pattern v = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern w = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern x = Pattern.compile("#EXTINF:[\\d\\.]+\\b,(.+)");
    private static final Pattern y = Pattern.compile("LAST-MSN=(\\d+)\\b");
    private static final Pattern z = Pattern.compile("LAST-PART=(\\d+)\\b");
    private static final Pattern A = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern B = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern C = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern D = Pattern.compile("BYTERANGE-START=(\\d+)\\b");
    private static final Pattern E = Pattern.compile("BYTERANGE-LENGTH=(\\d+)\\b");
    private static final Pattern F = Pattern.compile("METHOD=(NONE|AES-128|SAMPLE-AES|SAMPLE-AES-CENC|SAMPLE-AES-CTR)\\s*(?:,|$)");
    private static final Pattern G = Pattern.compile("KEYFORMAT=\"(.+?)\"");
    private static final Pattern H = Pattern.compile("KEYFORMATVERSIONS=\"(.+?)\"");
    private static final Pattern I = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern J = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern K = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern L = Pattern.compile("TYPE=(PART|MAP)");
    private static final Pattern M = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern N = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern O = Pattern.compile("GROUP-ID=\"(.+?)\"");
    private static final Pattern P = Pattern.compile("CHARACTERISTICS=\"(.+?)\"");
    private static final Pattern Q = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern R = d("AUTOSELECT");
    private static final Pattern S = d("DEFAULT");
    private static final Pattern T = d("FORCED");
    private static final Pattern U = d("INDEPENDENT");
    private static final Pattern V = d("GAP");
    private static final Pattern W = d("PRECISE");
    private static final Pattern X = Pattern.compile("VALUE=\"(.+?)\"");
    private static final Pattern Y = Pattern.compile("IMPORT=\"(.+?)\"");
    private static final Pattern Z = Pattern.compile("\\{\\$([a-zA-Z0-9\\-_]+)\\}");

    /* loaded from: classes2.dex */
    public static final class DeltaUpdateException extends IOException {
    }

    public HlsPlaylistParser() {
        this(HlsMasterPlaylist.EMPTY, null);
    }

    public HlsPlaylistParser(HlsMasterPlaylist hlsMasterPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist) {
        this.aa = hlsMasterPlaylist;
        this.ab = hlsMediaPlaylist;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public HlsPlaylist parse(Uri uri, InputStream inputStream) throws IOException {
        String trim;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayDeque arrayDeque = new ArrayDeque();
        try {
            if (a(bufferedReader)) {
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        trim = readLine.trim();
                        if (!trim.isEmpty()) {
                            if (!trim.startsWith("#EXT-X-STREAM-INF")) {
                                if (trim.startsWith("#EXT-X-TARGETDURATION") || trim.startsWith("#EXT-X-MEDIA-SEQUENCE") || trim.startsWith("#EXTINF") || trim.startsWith("#EXT-X-KEY") || trim.startsWith("#EXT-X-BYTERANGE") || trim.equals("#EXT-X-DISCONTINUITY") || trim.equals("#EXT-X-DISCONTINUITY-SEQUENCE") || trim.equals("#EXT-X-ENDLIST")) {
                                    break;
                                }
                                arrayDeque.add(trim);
                            } else {
                                arrayDeque.add(trim);
                                return a(new a(arrayDeque, bufferedReader), uri.toString());
                            }
                        }
                    } else {
                        Util.closeQuietly(bufferedReader);
                        throw ParserException.createForMalformedManifest("Failed to parse the playlist, could not identify any tags.", null);
                    }
                }
                arrayDeque.add(trim);
                return a(this.aa, this.ab, new a(arrayDeque, bufferedReader), uri.toString());
            }
            throw ParserException.createForMalformedManifest("Input does not start with the #EXTM3U header.", null);
        } finally {
            Util.closeQuietly(bufferedReader);
        }
    }

    private static boolean a(BufferedReader bufferedReader) throws IOException {
        int read = bufferedReader.read();
        if (read == 239) {
            if (!(bufferedReader.read() == 187 && bufferedReader.read() == 191)) {
                return false;
            }
            read = bufferedReader.read();
        }
        int a2 = a(bufferedReader, true, read);
        for (int i2 = 0; i2 < 7; i2++) {
            if (a2 != "#EXTM3U".charAt(i2)) {
                return false;
            }
            a2 = bufferedReader.read();
        }
        return Util.isLinebreak(a(bufferedReader, false, a2));
    }

    private static int a(BufferedReader bufferedReader, boolean z2, int i2) throws IOException {
        while (i2 != -1 && Character.isWhitespace(i2) && (z2 || !Util.isLinebreak(i2))) {
            i2 = bufferedReader.read();
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x038c  */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist a(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser.a r36, java.lang.String r37) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser.a(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser$a, java.lang.String):com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist");
    }

    @Nullable
    private static HlsMasterPlaylist.Variant a(ArrayList<HlsMasterPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMasterPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.audioGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMasterPlaylist.Variant b(ArrayList<HlsMasterPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMasterPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.videoGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMasterPlaylist.Variant c(ArrayList<HlsMasterPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMasterPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.subtitleGroupId)) {
                return variant;
            }
        }
        return null;
    }

    private static HlsMediaPlaylist a(HlsMasterPlaylist hlsMasterPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist, a aVar, String str) throws IOException {
        long j2;
        String str2;
        long j3;
        long j4;
        long j5;
        DrmInitData drmInitData;
        HlsMasterPlaylist hlsMasterPlaylist2 = hlsMasterPlaylist;
        HlsMediaPlaylist hlsMediaPlaylist2 = hlsMediaPlaylist;
        boolean z2 = hlsMasterPlaylist2.hasIndependentSegments;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap3 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        String str3 = "";
        HlsMediaPlaylist.ServerControl serverControl = new HlsMediaPlaylist.ServerControl(C.TIME_UNSET, false, C.TIME_UNSET, C.TIME_UNSET, false);
        TreeMap treeMap = new TreeMap();
        boolean z3 = z2;
        HlsMediaPlaylist.ServerControl serverControl2 = serverControl;
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        long j9 = 0;
        long j10 = 0;
        long j11 = 0;
        long j12 = 0;
        long j13 = 0;
        HlsMediaPlaylist.Part part = null;
        int i2 = 0;
        DrmInitData drmInitData2 = null;
        DrmInitData drmInitData3 = null;
        boolean z4 = false;
        boolean z5 = false;
        long j14 = C.TIME_UNSET;
        int i3 = 1;
        long j15 = C.TIME_UNSET;
        long j16 = C.TIME_UNSET;
        boolean z6 = false;
        boolean z7 = false;
        int i4 = 0;
        long j17 = -1;
        String str4 = null;
        String str5 = null;
        int i5 = 0;
        boolean z8 = false;
        HlsMediaPlaylist.Segment segment = null;
        ArrayList arrayList4 = arrayList2;
        String str6 = null;
        while (aVar.a()) {
            String b2 = aVar.b();
            if (b2.startsWith("#EXT")) {
                arrayList3.add(b2);
            }
            if (b2.startsWith("#EXT-X-PLAYLIST-TYPE")) {
                String a2 = a(b2, o, hashMap);
                if ("VOD".equals(a2)) {
                    i2 = 1;
                } else if ("EVENT".equals(a2)) {
                    i2 = 2;
                }
            } else if (b2.equals("#EXT-X-I-FRAMES-ONLY")) {
                z8 = true;
            } else if (b2.startsWith("#EXT-X-START")) {
                j14 = (long) (c(b2, A) * 1000000.0d);
                z5 = a(b2, W, false);
            } else if (b2.startsWith("#EXT-X-SERVER-CONTROL")) {
                serverControl2 = b(b2);
            } else if (b2.startsWith("#EXT-X-PART-INF")) {
                j16 = (long) (c(b2, m) * 1000000.0d);
            } else if (b2.startsWith("#EXT-X-MAP")) {
                String a3 = a(b2, I, hashMap);
                String b3 = b(b2, C, hashMap);
                if (b3 != null) {
                    String[] split = Util.split(b3, "@");
                    long parseLong = Long.parseLong(split[0]);
                    if (split.length > 1) {
                        j6 = Long.parseLong(split[1]);
                        j17 = parseLong;
                        j2 = -1;
                    } else {
                        j17 = parseLong;
                        j2 = -1;
                    }
                } else {
                    j2 = -1;
                }
                int i6 = (j17 > j2 ? 1 : (j17 == j2 ? 0 : -1));
                if (i6 == 0) {
                    j6 = 0;
                }
                if (str6 != null) {
                    str2 = str4;
                    if (str2 == null) {
                        throw ParserException.createForMalformedManifest("The encryption IV attribute must be present when an initialization segment is encrypted with METHOD=AES-128.", null);
                    }
                } else {
                    str2 = str4;
                }
                segment = new HlsMediaPlaylist.Segment(a3, j6, j17, str6, str2);
                if (i6 != 0) {
                    j6 += j17;
                }
                str4 = str2;
                j17 = -1;
            } else {
                String str7 = str4;
                if (b2.startsWith("#EXT-X-TARGETDURATION")) {
                    j15 = a(b2, k) * 1000000;
                    str4 = str7;
                } else if (b2.startsWith("#EXT-X-MEDIA-SEQUENCE")) {
                    j11 = b(b2, v);
                    str4 = str7;
                    j9 = j11;
                } else if (b2.startsWith("#EXT-X-VERSION")) {
                    i3 = a(b2, n);
                    str4 = str7;
                } else {
                    if (b2.startsWith("#EXT-X-DEFINE")) {
                        String b4 = b(b2, Y, hashMap);
                        if (b4 != null) {
                            String str8 = hlsMasterPlaylist2.variableDefinitions.get(b4);
                            if (str8 != null) {
                                hashMap.put(b4, str8);
                            }
                        } else {
                            hashMap.put(a(b2, N, hashMap), a(b2, X, hashMap));
                        }
                        hashMap = hashMap;
                        hashMap2 = hashMap2;
                        str6 = str6;
                        arrayList3 = arrayList3;
                        str4 = str7;
                        str5 = str5;
                        hashMap3 = hashMap3;
                        j11 = j11;
                        i2 = i2;
                    } else if (b2.startsWith("#EXTINF")) {
                        j12 = (long) (c(b2, w) * 1000000.0d);
                        str3 = a(b2, x, "", hashMap);
                        hashMap2 = hashMap2;
                        str6 = str6;
                        hlsMasterPlaylist2 = hlsMasterPlaylist;
                        str4 = str7;
                    } else {
                        HashMap hashMap4 = hashMap2;
                        str6 = str6;
                        if (b2.startsWith("#EXT-X-SKIP")) {
                            int a4 = a(b2, r);
                            Assertions.checkState(hlsMediaPlaylist2 != null && arrayList.isEmpty());
                            int i7 = (int) (j9 - ((HlsMediaPlaylist) Util.castNonNull(hlsMediaPlaylist)).mediaSequence);
                            int i8 = a4 + i7;
                            if (i7 < 0 || i8 > hlsMediaPlaylist2.segments.size()) {
                                throw new DeltaUpdateException();
                            }
                            long j18 = j10;
                            while (i7 < i8) {
                                HlsMediaPlaylist.Segment segment2 = hlsMediaPlaylist2.segments.get(i7);
                                if (j9 != hlsMediaPlaylist2.mediaSequence) {
                                    segment2 = segment2.copyWith(j18, (hlsMediaPlaylist2.discontinuitySequence - i4) + segment2.relativeDiscontinuitySequence);
                                }
                                arrayList.add(segment2);
                                j7 = j18 + segment2.durationUs;
                                if (segment2.byteRangeLength != -1) {
                                    j6 = segment2.byteRangeOffset + segment2.byteRangeLength;
                                }
                                int i9 = segment2.relativeDiscontinuitySequence;
                                HlsMediaPlaylist.Segment segment3 = segment2.initializationSegment;
                                DrmInitData drmInitData4 = segment2.drmInitData;
                                String str9 = segment2.fullSegmentEncryptionKeyUri;
                                str7 = (segment2.encryptionIV == null || !segment2.encryptionIV.equals(Long.toHexString(j11))) ? segment2.encryptionIV : str7;
                                j11++;
                                i7++;
                                i5 = i9;
                                segment = segment3;
                                drmInitData2 = drmInitData4;
                                str6 = str9;
                                j18 = j7;
                                hashMap4 = hashMap4;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                i8 = i8;
                            }
                            j10 = j18;
                            hashMap2 = hashMap4;
                            hlsMasterPlaylist2 = hlsMasterPlaylist;
                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                            str4 = str7;
                        } else if (b2.startsWith("#EXT-X-KEY")) {
                            String a5 = a(b2, F, hashMap);
                            String a6 = a(b2, G, "identity", hashMap);
                            if ("NONE".equals(a5)) {
                                treeMap.clear();
                                str6 = null;
                                drmInitData2 = null;
                                str4 = null;
                            } else {
                                String b5 = b(b2, J, hashMap);
                                if (!"identity".equals(a6)) {
                                    str5 = str5 == null ? c(a5) : str5;
                                    DrmInitData.SchemeData a7 = a(b2, a6, hashMap);
                                    if (a7 != null) {
                                        treeMap.put(a6, a7);
                                        str4 = b5;
                                        str6 = null;
                                        drmInitData2 = null;
                                    } else {
                                        str4 = b5;
                                        str6 = null;
                                    }
                                } else if ("AES-128".equals(a5)) {
                                    str6 = a(b2, I, hashMap);
                                    str4 = b5;
                                } else {
                                    str4 = b5;
                                    str6 = null;
                                }
                            }
                            hashMap2 = hashMap4;
                            hlsMasterPlaylist2 = hlsMasterPlaylist;
                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                        } else {
                            str5 = str5;
                            if (b2.startsWith("#EXT-X-BYTERANGE")) {
                                String[] split2 = Util.split(a(b2, B, hashMap), "@");
                                j17 = Long.parseLong(split2[0]);
                                if (split2.length > 1) {
                                    j6 = Long.parseLong(split2[1]);
                                }
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                str4 = str7;
                            } else if (b2.startsWith("#EXT-X-DISCONTINUITY-SEQUENCE")) {
                                i4 = Integer.parseInt(b2.substring(b2.indexOf(58) + 1));
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                z7 = true;
                                str4 = str7;
                            } else if (b2.equals("#EXT-X-DISCONTINUITY")) {
                                i5++;
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                str4 = str7;
                            } else if (b2.startsWith("#EXT-X-PROGRAM-DATE-TIME")) {
                                if (j8 == 0) {
                                    j8 = C.msToUs(Util.parseXsDateTime(b2.substring(b2.indexOf(58) + 1))) - j10;
                                    str5 = str5;
                                    str6 = str6;
                                    hashMap2 = hashMap4;
                                    hlsMasterPlaylist2 = hlsMasterPlaylist;
                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                    str4 = str7;
                                } else {
                                    hashMap = hashMap;
                                    str4 = str7;
                                    hashMap3 = hashMap3;
                                    str6 = str6;
                                    j11 = j11;
                                    hashMap2 = hashMap4;
                                    i2 = i2;
                                    arrayList3 = arrayList3;
                                }
                            } else if (b2.equals("#EXT-X-GAP")) {
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                z4 = true;
                                str4 = str7;
                            } else if (b2.equals("#EXT-X-INDEPENDENT-SEGMENTS")) {
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                z3 = true;
                                str4 = str7;
                            } else if (b2.equals("#EXT-X-ENDLIST")) {
                                str5 = str5;
                                str6 = str6;
                                hashMap2 = hashMap4;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                z6 = true;
                                str4 = str7;
                            } else if (b2.startsWith("#EXT-X-RENDITION-REPORT")) {
                                hashMap3 = hashMap3;
                                long a8 = a(b2, y, (j9 + arrayList.size()) - (arrayList4.isEmpty() ? 1L : 0L));
                                int a9 = a(b2, z, j16 != C.TIME_UNSET ? (arrayList4.isEmpty() ? ((HlsMediaPlaylist.Segment) Iterables.getLast(arrayList)).parts : arrayList4).size() - 1 : -1);
                                Uri parse = Uri.parse(UriUtil.resolve(str, a(b2, I, hashMap)));
                                hashMap3.put(parse, new HlsMediaPlaylist.RenditionReport(parse, a8, a9));
                                hashMap = hashMap;
                                str4 = str7;
                                str6 = str6;
                                j11 = j11;
                                hashMap2 = hashMap4;
                                i2 = i2;
                                arrayList3 = arrayList3;
                            } else if (!b2.startsWith("#EXT-X-PRELOAD-HINT")) {
                                str6 = str6;
                                j11 = j11;
                                if (b2.startsWith("#EXT-X-PART")) {
                                    String a10 = a(j11, str6, str7);
                                    String a11 = a(b2, I, hashMap);
                                    long c2 = (long) (c(b2, l) * 1000000.0d);
                                    boolean a12 = a(b2, U, false) | (z3 && arrayList4.isEmpty());
                                    boolean a13 = a(b2, V, false);
                                    String b6 = b(b2, C, hashMap);
                                    if (b6 != null) {
                                        String[] split3 = Util.split(b6, "@");
                                        j5 = Long.parseLong(split3[0]);
                                        if (split3.length > 1) {
                                            j13 = Long.parseLong(split3[1]);
                                            j4 = -1;
                                        } else {
                                            j4 = -1;
                                        }
                                    } else {
                                        j5 = -1;
                                        j4 = -1;
                                    }
                                    int i10 = (j5 > j4 ? 1 : (j5 == j4 ? 0 : -1));
                                    if (i10 == 0) {
                                        j13 = 0;
                                    }
                                    if (drmInitData2 != null || treeMap.isEmpty()) {
                                        i2 = i2;
                                    } else {
                                        i2 = i2;
                                        DrmInitData.SchemeData[] schemeDataArr = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                        DrmInitData drmInitData5 = new DrmInitData(str5, schemeDataArr);
                                        if (drmInitData3 == null) {
                                            drmInitData3 = a(str5, schemeDataArr);
                                            drmInitData2 = drmInitData5;
                                        } else {
                                            drmInitData2 = drmInitData5;
                                        }
                                    }
                                    arrayList4.add(new HlsMediaPlaylist.Part(a11, segment, c2, i5, j7, drmInitData2, str6, a10, j13, j5, a13, a12, false));
                                    j7 += c2;
                                    if (i10 != 0) {
                                        j13 += j5;
                                    }
                                    arrayList3 = arrayList3;
                                    hlsMasterPlaylist2 = hlsMasterPlaylist;
                                    str4 = str7;
                                    j11 = j11;
                                    hashMap3 = hashMap3;
                                    hashMap2 = hashMap4;
                                    str5 = str5;
                                    str6 = str6;
                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                } else {
                                    hashMap3 = hashMap3;
                                    i2 = i2;
                                    arrayList3 = arrayList3;
                                    if (!b2.startsWith("#")) {
                                        String a14 = a(j11, str6, str7);
                                        j11 = 1 + j11;
                                        String b7 = b(b2, hashMap);
                                        HlsMediaPlaylist.Segment segment4 = (HlsMediaPlaylist.Segment) hashMap4.get(b7);
                                        int i11 = (j17 > (-1L) ? 1 : (j17 == (-1L) ? 0 : -1));
                                        if (i11 == 0) {
                                            j6 = 0;
                                        } else if (z8 && segment == null && segment4 == null) {
                                            segment4 = new HlsMediaPlaylist.Segment(b7, 0L, j6, null, null);
                                            hashMap4.put(b7, segment4);
                                            j6 = j6;
                                        } else {
                                            j6 = j6;
                                        }
                                        if (drmInitData2 != null || treeMap.isEmpty()) {
                                            hashMap = hashMap;
                                            str4 = str7;
                                            drmInitData = drmInitData2;
                                            drmInitData3 = drmInitData3;
                                        } else {
                                            hashMap = hashMap;
                                            str4 = str7;
                                            DrmInitData.SchemeData[] schemeDataArr2 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                            drmInitData = new DrmInitData(str5, schemeDataArr2);
                                            drmInitData3 = drmInitData3 == null ? a(str5, schemeDataArr2) : drmInitData3;
                                        }
                                        if (segment != null) {
                                            segment4 = segment;
                                        }
                                        arrayList.add(new HlsMediaPlaylist.Segment(b7, segment4, str3, j12, i5, j10, drmInitData, str6, a14, j6, j17, z4, arrayList4));
                                        j7 = j10 + j12;
                                        str3 = "";
                                        arrayList4 = new ArrayList();
                                        if (i11 != 0) {
                                            j6 += j17;
                                        }
                                        drmInitData2 = drmInitData;
                                        hashMap2 = hashMap4;
                                        j12 = 0;
                                        j10 = j7;
                                        j17 = -1;
                                        arrayList3 = arrayList3;
                                        hashMap3 = hashMap3;
                                        i2 = i2;
                                        hlsMasterPlaylist2 = hlsMasterPlaylist;
                                        z4 = false;
                                        str5 = str5;
                                        str6 = str6;
                                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                                    } else {
                                        hashMap = hashMap;
                                        str4 = str7;
                                        hashMap2 = hashMap4;
                                    }
                                }
                            } else if (part != null) {
                                hashMap3 = hashMap3;
                                hashMap = hashMap;
                                str4 = str7;
                                str6 = str6;
                                j11 = j11;
                                hashMap2 = hashMap4;
                                i2 = i2;
                                arrayList3 = arrayList3;
                            } else if (!"PART".equals(a(b2, L, hashMap))) {
                                hashMap3 = hashMap3;
                                hashMap = hashMap;
                                str4 = str7;
                                str6 = str6;
                                j11 = j11;
                                hashMap2 = hashMap4;
                                i2 = i2;
                                arrayList3 = arrayList3;
                            } else {
                                String a15 = a(b2, I, hashMap);
                                long a16 = a(b2, D, -1L);
                                long a17 = a(b2, E, -1L);
                                String a18 = a(j11, str6, str7);
                                if (drmInitData2 != null || treeMap.isEmpty()) {
                                    j3 = -1;
                                } else {
                                    DrmInitData.SchemeData[] schemeDataArr3 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                    DrmInitData drmInitData6 = new DrmInitData(str5, schemeDataArr3);
                                    if (drmInitData3 == null) {
                                        drmInitData3 = a(str5, schemeDataArr3);
                                        drmInitData2 = drmInitData6;
                                        j3 = -1;
                                    } else {
                                        drmInitData2 = drmInitData6;
                                        j3 = -1;
                                    }
                                }
                                int i12 = (a16 > j3 ? 1 : (a16 == j3 ? 0 : -1));
                                if (i12 == 0 || a17 != j3) {
                                    part = new HlsMediaPlaylist.Part(a15, segment, 0L, i5, j7, drmInitData2, str6, a18, i12 != 0 ? a16 : 0L, a17, false, false, true);
                                }
                                str5 = str5;
                                str4 = str7;
                                j11 = j11;
                                hashMap2 = hashMap4;
                                hashMap3 = hashMap3;
                                str6 = str6;
                                hlsMasterPlaylist2 = hlsMasterPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                            }
                        }
                    }
                    hlsMasterPlaylist2 = hlsMasterPlaylist;
                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                }
            }
        }
        if (part != null) {
            arrayList4.add(part);
        }
        return new HlsMediaPlaylist(i2, str, arrayList3, j14, z5, j8, z7, i4, j9, i3, j15, j16, z3, z6, j8 != 0, drmInitData3, arrayList, arrayList4, serverControl2, hashMap3);
    }

    private static DrmInitData a(@Nullable String str, DrmInitData.SchemeData[] schemeDataArr) {
        DrmInitData.SchemeData[] schemeDataArr2 = new DrmInitData.SchemeData[schemeDataArr.length];
        for (int i2 = 0; i2 < schemeDataArr.length; i2++) {
            schemeDataArr2[i2] = schemeDataArr[i2].copyWithData(null);
        }
        return new DrmInitData(str, schemeDataArr2);
    }

    @Nullable
    private static String a(long j2, @Nullable String str, @Nullable String str2) {
        if (str == null) {
            return null;
        }
        return str2 != null ? str2 : Long.toHexString(j2);
    }

    private static int a(String str) {
        int i2 = a(str, S, false) ? 1 : 0;
        if (a(str, T, false)) {
            i2 |= 2;
        }
        return a(str, R, false) ? i2 | 4 : i2;
    }

    private static int a(String str, Map<String, String> map) {
        String b2 = b(str, P, map);
        int i2 = 0;
        if (TextUtils.isEmpty(b2)) {
            return 0;
        }
        String[] split = Util.split(b2, Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (Util.contains(split, "public.accessibility.describes-video")) {
            i2 = 512;
        }
        if (Util.contains(split, "public.accessibility.transcribes-spoken-dialog")) {
            i2 |= 4096;
        }
        if (Util.contains(split, "public.accessibility.describes-music-and-sound")) {
            i2 |= 1024;
        }
        return Util.contains(split, "public.easy-to-read") ? i2 | 8192 : i2;
    }

    @Nullable
    private static DrmInitData.SchemeData a(String str, String str2, Map<String, String> map) throws ParserException {
        String a2 = a(str, H, "1", map);
        if ("urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed".equals(str2)) {
            String a3 = a(str, I, map);
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, "video/mp4", Base64.decode(a3.substring(a3.indexOf(44)), 0));
        } else if ("com.widevine".equals(str2)) {
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, "hls", Util.getUtf8Bytes(str));
        } else {
            if (!"com.microsoft.playready".equals(str2) || !"1".equals(a2)) {
                return null;
            }
            String a4 = a(str, I, map);
            return new DrmInitData.SchemeData(C.PLAYREADY_UUID, "video/mp4", PsshAtomUtil.buildPsshAtom(C.PLAYREADY_UUID, Base64.decode(a4.substring(a4.indexOf(44)), 0)));
        }
    }

    private static HlsMediaPlaylist.ServerControl b(String str) {
        double a2 = a(str, p, -9.223372036854776E18d);
        long j2 = a2 == -9.223372036854776E18d ? -9223372036854775807L : (long) (a2 * 1000000.0d);
        boolean a3 = a(str, q, false);
        double a4 = a(str, s, -9.223372036854776E18d);
        long j3 = a4 == -9.223372036854776E18d ? -9223372036854775807L : (long) (a4 * 1000000.0d);
        double a5 = a(str, t, -9.223372036854776E18d);
        return new HlsMediaPlaylist.ServerControl(j2, a3, j3, a5 == -9.223372036854776E18d ? -9223372036854775807L : (long) (a5 * 1000000.0d), a(str, u, false));
    }

    private static String c(String str) {
        return ("SAMPLE-AES-CENC".equals(str) || "SAMPLE-AES-CTR".equals(str)) ? C.CENC_TYPE_cenc : C.CENC_TYPE_cbcs;
    }

    private static int a(String str, Pattern pattern) throws ParserException {
        return Integer.parseInt(a(str, pattern, Collections.emptyMap()));
    }

    private static int a(String str, Pattern pattern, int i2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))) : i2;
    }

    private static long b(String str, Pattern pattern) throws ParserException {
        return Long.parseLong(a(str, pattern, Collections.emptyMap()));
    }

    private static long a(String str, Pattern pattern, long j2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Long.parseLong((String) Assertions.checkNotNull(matcher.group(1))) : j2;
    }

    private static double c(String str, Pattern pattern) throws ParserException {
        return Double.parseDouble(a(str, pattern, Collections.emptyMap()));
    }

    private static String a(String str, Pattern pattern, Map<String, String> map) throws ParserException {
        String b2 = b(str, pattern, map);
        if (b2 != null) {
            return b2;
        }
        String pattern2 = pattern.pattern();
        StringBuilder sb = new StringBuilder(String.valueOf(pattern2).length() + 19 + String.valueOf(str).length());
        sb.append("Couldn't match ");
        sb.append(pattern2);
        sb.append(" in ");
        sb.append(str);
        throw ParserException.createForMalformedManifest(sb.toString(), null);
    }

    @Nullable
    private static String b(String str, Pattern pattern, Map<String, String> map) {
        return a(str, pattern, (String) null, map);
    }

    private static String a(String str, Pattern pattern, String str2, Map<String, String> map) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str2 = (String) Assertions.checkNotNull(matcher.group(1));
        }
        return (map.isEmpty() || str2 == null) ? str2 : b(str2, map);
    }

    private static double a(String str, Pattern pattern, double d2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Double.parseDouble((String) Assertions.checkNotNull(matcher.group(1))) : d2;
    }

    private static String b(String str, Map<String, String> map) {
        Matcher matcher = Z.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            if (map.containsKey(group)) {
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(map.get(group)));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static boolean a(String str, Pattern pattern, boolean z2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? "YES".equals(matcher.group(1)) : z2;
    }

    private static Pattern d(String str) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 9);
        sb.append(str);
        sb.append("=(");
        sb.append("NO");
        sb.append("|");
        sb.append("YES");
        sb.append(")");
        return Pattern.compile(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a {
        private final BufferedReader a;
        private final Queue<String> b;
        @Nullable
        private String c;

        public a(Queue<String> queue, BufferedReader bufferedReader) {
            this.b = queue;
            this.a = bufferedReader;
        }

        @EnsuresNonNullIf(expression = {"next"}, result = true)
        public boolean a() throws IOException {
            if (this.c != null) {
                return true;
            }
            if (!this.b.isEmpty()) {
                this.c = (String) Assertions.checkNotNull(this.b.poll());
                return true;
            }
            do {
                String readLine = this.a.readLine();
                this.c = readLine;
                if (readLine == null) {
                    return false;
                }
                this.c = this.c.trim();
            } while (this.c.isEmpty());
            return true;
        }

        public String b() throws IOException {
            if (a()) {
                String str = this.c;
                this.c = null;
                return str;
            }
            throw new NoSuchElementException();
        }
    }
}
