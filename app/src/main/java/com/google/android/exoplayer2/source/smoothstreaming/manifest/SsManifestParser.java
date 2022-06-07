package com.google.android.exoplayer2.source.smoothstreaming.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public class SsManifestParser implements ParsingLoadable.Parser<SsManifest> {
    private final XmlPullParserFactory a;

    public SsManifestParser() {
        try {
            this.a = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public SsManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser newPullParser = this.a.newPullParser();
            newPullParser.setInput(inputStream, null);
            return (SsManifest) new d(null, uri.toString()).a(newPullParser);
        } catch (XmlPullParserException e2) {
            throw ParserException.createForMalformedManifest(null, e2);
        }
    }

    /* loaded from: classes2.dex */
    public static class MissingFieldException extends ParserException {
        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public MissingFieldException(java.lang.String r4) {
            /*
                r3 = this;
                java.lang.String r0 = "Missing required field: "
                java.lang.String r4 = java.lang.String.valueOf(r4)
                int r1 = r4.length()
                if (r1 == 0) goto L_0x0011
                java.lang.String r4 = r0.concat(r4)
                goto L_0x0016
            L_0x0011:
                java.lang.String r4 = new java.lang.String
                r4.<init>(r0)
            L_0x0016:
                r0 = 0
                r1 = 1
                r2 = 4
                r3.<init>(r4, r0, r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.MissingFieldException.<init>(java.lang.String):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class a {
        private final String a;
        private final String b;
        @Nullable
        private final a c;
        private final List<Pair<String, Object>> d = new LinkedList();

        protected abstract Object a();

        protected void a(Object obj) {
        }

        protected void b(XmlPullParser xmlPullParser) throws ParserException {
        }

        protected boolean b(String str) {
            return false;
        }

        protected void c(XmlPullParser xmlPullParser) {
        }

        protected void d(XmlPullParser xmlPullParser) {
        }

        public a(@Nullable a aVar, String str, String str2) {
            this.c = aVar;
            this.a = str;
            this.b = str2;
        }

        public final Object a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            boolean z = false;
            int i = 0;
            while (true) {
                switch (xmlPullParser.getEventType()) {
                    case 1:
                        return null;
                    case 2:
                        String name = xmlPullParser.getName();
                        if (!this.b.equals(name)) {
                            if (z) {
                                if (i <= 0) {
                                    if (!b(name)) {
                                        a a = a(this, name, this.a);
                                        if (a != null) {
                                            a(a.a(xmlPullParser));
                                            break;
                                        } else {
                                            i = 1;
                                            break;
                                        }
                                    } else {
                                        b(xmlPullParser);
                                        break;
                                    }
                                } else {
                                    i++;
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            b(xmlPullParser);
                            z = true;
                            break;
                        }
                    case 3:
                        if (z) {
                            if (i <= 0) {
                                String name2 = xmlPullParser.getName();
                                d(xmlPullParser);
                                if (b(name2)) {
                                    break;
                                } else {
                                    return a();
                                }
                            } else {
                                i--;
                                break;
                            }
                        } else {
                            continue;
                        }
                    case 4:
                        if (z && i == 0) {
                            c(xmlPullParser);
                            break;
                        }
                        break;
                }
                xmlPullParser.next();
            }
        }

        private a a(a aVar, String str, String str2) {
            if ("QualityLevel".equals(str)) {
                return new c(aVar, str2);
            }
            if ("Protection".equals(str)) {
                return new b(aVar, str2);
            }
            if ("StreamIndex".equals(str)) {
                return new e(aVar, str2);
            }
            return null;
        }

        protected final void a(String str, @Nullable Object obj) {
            this.d.add(Pair.create(str, obj));
        }

        @Nullable
        protected final Object a(String str) {
            for (int i = 0; i < this.d.size(); i++) {
                Pair<String, Object> pair = this.d.get(i);
                if (((String) pair.first).equals(str)) {
                    return pair.second;
                }
            }
            a aVar = this.c;
            if (aVar == null) {
                return null;
            }
            return aVar.a(str);
        }

        protected final String a(XmlPullParser xmlPullParser, String str) throws MissingFieldException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue != null) {
                return attributeValue;
            }
            throw new MissingFieldException(str);
        }

        protected final int a(XmlPullParser xmlPullParser, String str, int i) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                return i;
            }
            try {
                return Integer.parseInt(attributeValue);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }

        protected final int b(XmlPullParser xmlPullParser, String str) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue != null) {
                try {
                    return Integer.parseInt(attributeValue);
                } catch (NumberFormatException e) {
                    throw ParserException.createForMalformedManifest(null, e);
                }
            } else {
                throw new MissingFieldException(str);
            }
        }

        protected final long a(XmlPullParser xmlPullParser, String str, long j) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                return j;
            }
            try {
                return Long.parseLong(attributeValue);
            } catch (NumberFormatException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }

        protected final long c(XmlPullParser xmlPullParser, String str) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue != null) {
                try {
                    return Long.parseLong(attributeValue);
                } catch (NumberFormatException e) {
                    throw ParserException.createForMalformedManifest(null, e);
                }
            } else {
                throw new MissingFieldException(str);
            }
        }

        protected final boolean a(XmlPullParser xmlPullParser, String str, boolean z) {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            return attributeValue != null ? Boolean.parseBoolean(attributeValue) : z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class d extends a {
        private int b;
        private int c;
        private long d;
        private long e;
        private long f;
        private boolean h;
        private int g = -1;
        @Nullable
        private SsManifest.ProtectionElement i = null;
        private final List<SsManifest.StreamElement> a = new LinkedList();

        public d(a aVar, String str) {
            super(aVar, str, "SmoothStreamingMedia");
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void b(XmlPullParser xmlPullParser) throws ParserException {
            this.b = b(xmlPullParser, "MajorVersion");
            this.c = b(xmlPullParser, "MinorVersion");
            this.d = a(xmlPullParser, "TimeScale", 10000000L);
            this.e = c(xmlPullParser, "Duration");
            this.f = a(xmlPullParser, "DVRWindowLength", 0L);
            this.g = a(xmlPullParser, "LookaheadCount", -1);
            this.h = a(xmlPullParser, "IsLive", false);
            a("TimeScale", Long.valueOf(this.d));
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void a(Object obj) {
            if (obj instanceof SsManifest.StreamElement) {
                this.a.add((SsManifest.StreamElement) obj);
            } else if (obj instanceof SsManifest.ProtectionElement) {
                Assertions.checkState(this.i == null);
                this.i = (SsManifest.ProtectionElement) obj;
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public Object a() {
            SsManifest.StreamElement[] streamElementArr = new SsManifest.StreamElement[this.a.size()];
            this.a.toArray(streamElementArr);
            SsManifest.ProtectionElement protectionElement = this.i;
            if (protectionElement != null) {
                DrmInitData drmInitData = new DrmInitData(new DrmInitData.SchemeData(protectionElement.uuid, "video/mp4", this.i.data));
                for (SsManifest.StreamElement streamElement : streamElementArr) {
                    int i = streamElement.type;
                    if (i == 2 || i == 1) {
                        Format[] formatArr = streamElement.formats;
                        for (int i2 = 0; i2 < formatArr.length; i2++) {
                            formatArr[i2] = formatArr[i2].buildUpon().setDrmInitData(drmInitData).build();
                        }
                    }
                }
            }
            return new SsManifest(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, streamElementArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b extends a {
        private boolean a;
        private UUID b;
        private byte[] c;

        public b(a aVar, String str) {
            super(aVar, str, "Protection");
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public boolean b(String str) {
            return "ProtectionHeader".equals(str);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void b(XmlPullParser xmlPullParser) {
            if ("ProtectionHeader".equals(xmlPullParser.getName())) {
                this.a = true;
                this.b = UUID.fromString(c(xmlPullParser.getAttributeValue(null, "SystemID")));
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void c(XmlPullParser xmlPullParser) {
            if (this.a) {
                this.c = Base64.decode(xmlPullParser.getText(), 0);
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void d(XmlPullParser xmlPullParser) {
            if ("ProtectionHeader".equals(xmlPullParser.getName())) {
                this.a = false;
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public Object a() {
            UUID uuid = this.b;
            return new SsManifest.ProtectionElement(uuid, PsshAtomUtil.buildPsshAtom(uuid, this.c), a(this.c));
        }

        private static TrackEncryptionBox[] a(byte[] bArr) {
            return new TrackEncryptionBox[]{new TrackEncryptionBox(true, null, 8, b(bArr), 0, 0, null)};
        }

        private static byte[] b(byte[] bArr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bArr.length; i += 2) {
                sb.append((char) bArr[i]);
            }
            String sb2 = sb.toString();
            byte[] decode = Base64.decode(sb2.substring(sb2.indexOf("<KID>") + 5, sb2.indexOf("</KID>")), 0);
            a(decode, 0, 3);
            a(decode, 1, 2);
            a(decode, 4, 5);
            a(decode, 6, 7);
            return decode;
        }

        private static void a(byte[] bArr, int i, int i2) {
            byte b = bArr[i];
            bArr[i] = bArr[i2];
            bArr[i2] = b;
        }

        private static String c(String str) {
            return (str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') ? str.substring(1, str.length() - 1) : str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e extends a {
        private final String a;
        private final List<Format> b = new LinkedList();
        private int c;
        private String d;
        private long e;
        private String f;
        private String g;
        private int h;
        private int i;
        private int j;
        private int k;
        private String l;
        private ArrayList<Long> m;
        private long n;

        public e(a aVar, String str) {
            super(aVar, str, "StreamIndex");
            this.a = str;
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public boolean b(String str) {
            return ai.aD.equals(str);
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void b(XmlPullParser xmlPullParser) throws ParserException {
            if (ai.aD.equals(xmlPullParser.getName())) {
                e(xmlPullParser);
            } else {
                f(xmlPullParser);
            }
        }

        private void e(XmlPullParser xmlPullParser) throws ParserException {
            int size = this.m.size();
            long a = a(xmlPullParser, ai.aF, C.TIME_UNSET);
            int i = 1;
            if (a == C.TIME_UNSET) {
                if (size == 0) {
                    a = 0;
                } else if (this.n != -1) {
                    a = this.n + this.m.get(size - 1).longValue();
                } else {
                    throw ParserException.createForMalformedManifest("Unable to infer start time", null);
                }
            }
            this.m.add(Long.valueOf(a));
            this.n = a(xmlPullParser, "d", C.TIME_UNSET);
            long a2 = a(xmlPullParser, "r", 1L);
            if (a2 <= 1 || this.n != C.TIME_UNSET) {
                while (true) {
                    long j = i;
                    if (j < a2) {
                        this.m.add(Long.valueOf((this.n * j) + a));
                        i++;
                    } else {
                        return;
                    }
                }
            } else {
                throw ParserException.createForMalformedManifest("Repeated chunk with unspecified duration", null);
            }
        }

        private void f(XmlPullParser xmlPullParser) throws ParserException {
            this.c = g(xmlPullParser);
            a("Type", Integer.valueOf(this.c));
            if (this.c == 3) {
                this.d = a(xmlPullParser, "Subtype");
            } else {
                this.d = xmlPullParser.getAttributeValue(null, "Subtype");
            }
            a("Subtype", this.d);
            this.f = xmlPullParser.getAttributeValue(null, "Name");
            a("Name", this.f);
            this.g = a(xmlPullParser, "Url");
            this.h = a(xmlPullParser, "MaxWidth", -1);
            this.i = a(xmlPullParser, "MaxHeight", -1);
            this.j = a(xmlPullParser, "DisplayWidth", -1);
            this.k = a(xmlPullParser, "DisplayHeight", -1);
            this.l = xmlPullParser.getAttributeValue(null, "Language");
            a("Language", this.l);
            this.e = a(xmlPullParser, "TimeScale", -1);
            if (this.e == -1) {
                this.e = ((Long) a("TimeScale")).longValue();
            }
            this.m = new ArrayList<>();
        }

        private int g(XmlPullParser xmlPullParser) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, "Type");
            if (attributeValue == null) {
                throw new MissingFieldException("Type");
            } else if ("audio".equalsIgnoreCase(attributeValue)) {
                return 1;
            } else {
                if ("video".equalsIgnoreCase(attributeValue)) {
                    return 2;
                }
                if ("text".equalsIgnoreCase(attributeValue)) {
                    return 3;
                }
                StringBuilder sb = new StringBuilder(String.valueOf(attributeValue).length() + 19);
                sb.append("Invalid key value[");
                sb.append(attributeValue);
                sb.append("]");
                throw ParserException.createForMalformedManifest(sb.toString(), null);
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public void a(Object obj) {
            if (obj instanceof Format) {
                this.b.add((Format) obj);
            }
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public Object a() {
            Format[] formatArr = new Format[this.b.size()];
            this.b.toArray(formatArr);
            return new SsManifest.StreamElement(this.a, this.g, this.c, this.d, this.e, this.f, this.h, this.i, this.j, this.k, this.l, formatArr, this.m, this.n);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class c extends a {
        private Format a;

        public c(a aVar, String str) {
            super(aVar, str, "QualityLevel");
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x00b1, code lost:
            if (r2.equals("DESC") == false) goto L_0x00be;
         */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00c3  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00c6  */
        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void b(org.xmlpull.v1.XmlPullParser r10) throws com.google.android.exoplayer2.ParserException {
            /*
                Method dump skipped, instructions count: 278
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.c.b(org.xmlpull.v1.XmlPullParser):void");
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser.a
        public Object a() {
            return this.a;
        }

        private static List<byte[]> c(String str) {
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(str)) {
                byte[] bytesFromHexString = Util.getBytesFromHexString(str);
                byte[][] splitNalUnits = CodecSpecificDataUtil.splitNalUnits(bytesFromHexString);
                if (splitNalUnits == null) {
                    arrayList.add(bytesFromHexString);
                } else {
                    Collections.addAll(arrayList, splitNalUnits);
                }
            }
            return arrayList;
        }

        @Nullable
        private static String d(String str) {
            if (str.equalsIgnoreCase("H264") || str.equalsIgnoreCase("X264") || str.equalsIgnoreCase("AVC1") || str.equalsIgnoreCase("DAVC")) {
                return MimeTypes.VIDEO_H264;
            }
            if (str.equalsIgnoreCase("AAC") || str.equalsIgnoreCase("AACL") || str.equalsIgnoreCase("AACH") || str.equalsIgnoreCase("AACP")) {
                return MimeTypes.AUDIO_AAC;
            }
            if (str.equalsIgnoreCase("TTML") || str.equalsIgnoreCase("DFXP")) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (str.equalsIgnoreCase("ac-3") || str.equalsIgnoreCase("dac3")) {
                return MimeTypes.AUDIO_AC3;
            }
            if (str.equalsIgnoreCase("ec-3") || str.equalsIgnoreCase("dec3")) {
                return MimeTypes.AUDIO_E_AC3;
            }
            if (str.equalsIgnoreCase("dtsc")) {
                return MimeTypes.AUDIO_DTS;
            }
            if (str.equalsIgnoreCase("dtsh") || str.equalsIgnoreCase("dtsl")) {
                return MimeTypes.AUDIO_DTS_HD;
            }
            if (str.equalsIgnoreCase("dtse")) {
                return MimeTypes.AUDIO_DTS_EXPRESS;
            }
            if (str.equalsIgnoreCase("opus")) {
                return MimeTypes.AUDIO_OPUS;
            }
            return null;
        }
    }
}
