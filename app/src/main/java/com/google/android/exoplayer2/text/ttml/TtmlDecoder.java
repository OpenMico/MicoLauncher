package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private final XmlPullParserFactory j;
    private static final Pattern c = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final Pattern d = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern e = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    static final Pattern a = Pattern.compile("^([-+]?\\d+\\.?\\d*?)%$");
    static final Pattern b = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern f = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    private static final Pattern g = Pattern.compile("^(\\d+) (\\d+)$");
    private static final b h = new b(30.0f, 1, 1);
    private static final a i = new a(32, 15);

    public TtmlDecoder() {
        super("TtmlDecoder");
        try {
            this.j = XmlPullParserFactory.newInstance();
            this.j.setNamespaceAware(true);
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i2, boolean z) throws SubtitleDecoderException {
        b bVar;
        try {
            XmlPullParser newPullParser = this.j.newPullParser();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            hashMap2.put("", new c(""));
            c cVar = null;
            newPullParser.setInput(new ByteArrayInputStream(bArr, 0, i2), null);
            ArrayDeque arrayDeque = new ArrayDeque();
            b bVar2 = h;
            a aVar = i;
            int i3 = 0;
            e eVar = null;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.getEventType()) {
                b bVar3 = (b) arrayDeque.peek();
                if (i3 == 0) {
                    String name = newPullParser.getName();
                    if (eventType == 2) {
                        if ("tt".equals(name)) {
                            b a2 = a(newPullParser);
                            aVar = a(newPullParser, i);
                            cVar = b(newPullParser);
                            bVar = a2;
                        } else {
                            cVar = cVar;
                            bVar = bVar2;
                            aVar = aVar;
                        }
                        if (!c(name)) {
                            String valueOf = String.valueOf(newPullParser.getName());
                            Log.i("TtmlDecoder", valueOf.length() != 0 ? "Ignoring unsupported tag: ".concat(valueOf) : new String("Ignoring unsupported tag: "));
                            i3++;
                            bVar2 = bVar;
                        } else if ("head".equals(name)) {
                            bVar2 = bVar;
                            a(newPullParser, hashMap, aVar, cVar, hashMap2, hashMap3);
                        } else {
                            bVar2 = bVar;
                            try {
                                b a3 = a(newPullParser, bVar3, hashMap2, bVar2);
                                arrayDeque.push(a3);
                                if (bVar3 != null) {
                                    bVar3.a(a3);
                                }
                            } catch (SubtitleDecoderException e2) {
                                Log.w("TtmlDecoder", "Suppressing parser error", e2);
                                i3++;
                            }
                        }
                    } else {
                        if (eventType == 4) {
                            ((b) Assertions.checkNotNull(bVar3)).a(b.a(newPullParser.getText()));
                        } else if (eventType == 3) {
                            if (newPullParser.getName().equals("tt")) {
                                eVar = new e((b) Assertions.checkNotNull((b) arrayDeque.peek()), hashMap, hashMap2, hashMap3);
                            }
                            arrayDeque.pop();
                            cVar = cVar;
                            bVar2 = bVar2;
                            aVar = aVar;
                        }
                        cVar = cVar;
                        bVar2 = bVar2;
                        aVar = aVar;
                    }
                } else if (eventType == 2) {
                    i3++;
                } else if (eventType == 3) {
                    i3--;
                }
                newPullParser.next();
            }
            if (eVar != null) {
                return eVar;
            }
            throw new SubtitleDecoderException("No TTML subtitles found");
        } catch (IOException e3) {
            throw new IllegalStateException("Unexpected error when reading input.", e3);
        } catch (XmlPullParserException e4) {
            throw new SubtitleDecoderException("Unable to decode source", e4);
        }
    }

    private static b a(XmlPullParser xmlPullParser) throws SubtitleDecoderException {
        String attributeValue = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "frameRate");
        int parseInt = attributeValue != null ? Integer.parseInt(attributeValue) : 30;
        float f2 = 1.0f;
        String attributeValue2 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "frameRateMultiplier");
        if (attributeValue2 != null) {
            String[] split = Util.split(attributeValue2, StringUtils.SPACE);
            if (split.length == 2) {
                f2 = Integer.parseInt(split[0]) / Integer.parseInt(split[1]);
            } else {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
        }
        int i2 = h.b;
        String attributeValue3 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "subFrameRate");
        if (attributeValue3 != null) {
            i2 = Integer.parseInt(attributeValue3);
        }
        int i3 = h.c;
        String attributeValue4 = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "tickRate");
        if (attributeValue4 != null) {
            i3 = Integer.parseInt(attributeValue4);
        }
        return new b(parseInt * f2, i2, i3);
    }

    private static a a(XmlPullParser xmlPullParser, a aVar) throws SubtitleDecoderException {
        String attributeValue = xmlPullParser.getAttributeValue("http://www.w3.org/ns/ttml#parameter", "cellResolution");
        if (attributeValue == null) {
            return aVar;
        }
        Matcher matcher = g.matcher(attributeValue);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(attributeValue);
            Log.w("TtmlDecoder", valueOf.length() != 0 ? "Ignoring malformed cell resolution: ".concat(valueOf) : new String("Ignoring malformed cell resolution: "));
            return aVar;
        }
        try {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
            int parseInt2 = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2)));
            if (parseInt != 0 && parseInt2 != 0) {
                return new a(parseInt, parseInt2);
            }
            StringBuilder sb = new StringBuilder(47);
            sb.append("Invalid cell resolution ");
            sb.append(parseInt);
            sb.append(StringUtils.SPACE);
            sb.append(parseInt2);
            throw new SubtitleDecoderException(sb.toString());
        } catch (NumberFormatException unused) {
            String valueOf2 = String.valueOf(attributeValue);
            Log.w("TtmlDecoder", valueOf2.length() != 0 ? "Ignoring malformed cell resolution: ".concat(valueOf2) : new String("Ignoring malformed cell resolution: "));
            return aVar;
        }
    }

    @Nullable
    private static c b(XmlPullParser xmlPullParser) {
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "extent");
        if (attributeValue == null) {
            return null;
        }
        Matcher matcher = f.matcher(attributeValue);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(attributeValue);
            Log.w("TtmlDecoder", valueOf.length() != 0 ? "Ignoring non-pixel tts extent: ".concat(valueOf) : new String("Ignoring non-pixel tts extent: "));
            return null;
        }
        try {
            return new c(Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2))));
        } catch (NumberFormatException unused) {
            String valueOf2 = String.valueOf(attributeValue);
            Log.w("TtmlDecoder", valueOf2.length() != 0 ? "Ignoring malformed tts extent: ".concat(valueOf2) : new String("Ignoring malformed tts extent: "));
            return null;
        }
    }

    private static Map<String, TtmlStyle> a(XmlPullParser xmlPullParser, Map<String, TtmlStyle> map, a aVar, @Nullable c cVar, Map<String, c> map2, Map<String, String> map3) throws IOException, XmlPullParserException {
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "style")) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "style");
                TtmlStyle a2 = a(xmlPullParser, new TtmlStyle());
                if (attributeValue != null) {
                    for (String str : a(attributeValue)) {
                        a2.a(map.get(str));
                    }
                }
                String j = a2.j();
                if (j != null) {
                    map.put(j, a2);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "region")) {
                c a3 = a(xmlPullParser, aVar, cVar);
                if (a3 != null) {
                    map2.put(a3.a, a3);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, PlayerEvent.METADATA)) {
                a(xmlPullParser, map3);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "head"));
        return map;
    }

    private static void a(XmlPullParser xmlPullParser, Map<String, String> map) throws IOException, XmlPullParserException {
        String attributeValue;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, MimeTypes.BASE_TYPE_IMAGE) && (attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "id")) != null) {
                map.put(attributeValue, xmlPullParser.nextText());
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, PlayerEvent.METADATA));
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01b3  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.c a(org.xmlpull.v1.XmlPullParser r17, com.google.android.exoplayer2.text.ttml.TtmlDecoder.a r18, @androidx.annotation.Nullable com.google.android.exoplayer2.text.ttml.TtmlDecoder.c r19) {
        /*
            Method dump skipped, instructions count: 664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.a(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlDecoder$a, com.google.android.exoplayer2.text.ttml.TtmlDecoder$c):com.google.android.exoplayer2.text.ttml.c");
    }

    private static String[] a(String str) {
        String trim = str.trim();
        return trim.isEmpty() ? new String[0] : Util.split(trim, "\\s+");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01c6, code lost:
        if (r3.equals("delimiter") != false) goto L_0x01de;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0126, code lost:
        if (r3.equals("nounderline") != false) goto L_0x012a;
     */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0319 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.TtmlStyle a(org.xmlpull.v1.XmlPullParser r12, com.google.android.exoplayer2.text.ttml.TtmlStyle r13) {
        /*
            Method dump skipped, instructions count: 964
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.a(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlStyle):com.google.android.exoplayer2.text.ttml.TtmlStyle");
    }

    private static TtmlStyle a(@Nullable TtmlStyle ttmlStyle) {
        return ttmlStyle == null ? new TtmlStyle() : ttmlStyle;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Nullable
    private static Layout.Alignment b(String str) {
        char c2;
        String lowerCase = Ascii.toLowerCase(str);
        switch (lowerCase.hashCode()) {
            case -1364013995:
                if (lowerCase.equals("center")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 100571:
                if (lowerCase.equals("end")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 3317767:
                if (lowerCase.equals("left")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 108511772:
                if (lowerCase.equals("right")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 109757538:
                if (lowerCase.equals("start")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
                return Layout.Alignment.ALIGN_NORMAL;
            case 2:
            case 3:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return Layout.Alignment.ALIGN_CENTER;
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.text.ttml.b a(org.xmlpull.v1.XmlPullParser r21, @androidx.annotation.Nullable com.google.android.exoplayer2.text.ttml.b r22, java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.c> r23, com.google.android.exoplayer2.text.ttml.TtmlDecoder.b r24) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.a(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.b, java.util.Map, com.google.android.exoplayer2.text.ttml.TtmlDecoder$b):com.google.android.exoplayer2.text.ttml.b");
    }

    private static boolean c(String str) {
        return str.equals("tt") || str.equals("head") || str.equals("body") || str.equals("div") || str.equals(ai.av) || str.equals("span") || str.equals("br") || str.equals("style") || str.equals("styling") || str.equals("layout") || str.equals("region") || str.equals(PlayerEvent.METADATA) || str.equals(MimeTypes.BASE_TYPE_IMAGE) || str.equals("data") || str.equals("information");
    }

    private static void a(String str, TtmlStyle ttmlStyle) throws SubtitleDecoderException {
        Matcher matcher;
        String[] split = Util.split(str, "\\s+");
        if (split.length == 1) {
            matcher = e.matcher(str);
        } else if (split.length == 2) {
            matcher = e.matcher(split[1]);
            Log.w("TtmlDecoder", "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            int length = split.length;
            StringBuilder sb = new StringBuilder(52);
            sb.append("Invalid number of entries for fontSize: ");
            sb.append(length);
            sb.append(".");
            throw new SubtitleDecoderException(sb.toString());
        }
        if (matcher.matches()) {
            String str2 = (String) Assertions.checkNotNull(matcher.group(3));
            char c2 = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != 37) {
                if (hashCode != 3240) {
                    if (hashCode == 3592 && str2.equals("px")) {
                        c2 = 0;
                    }
                } else if (str2.equals("em")) {
                    c2 = 1;
                }
            } else if (str2.equals("%")) {
                c2 = 2;
            }
            switch (c2) {
                case 0:
                    ttmlStyle.e(1);
                    break;
                case 1:
                    ttmlStyle.e(2);
                    break;
                case 2:
                    ttmlStyle.e(3);
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 30);
                    sb2.append("Invalid unit for fontSize: '");
                    sb2.append(str2);
                    sb2.append("'.");
                    throw new SubtitleDecoderException(sb2.toString());
            }
            ttmlStyle.b(Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1))));
            return;
        }
        StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 36);
        sb3.append("Invalid expression for fontSize: '");
        sb3.append(str);
        sb3.append("'.");
        throw new SubtitleDecoderException(sb3.toString());
    }

    private static float d(String str) {
        Matcher matcher = a.matcher(str);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(str);
            Log.w("TtmlDecoder", valueOf.length() != 0 ? "Invalid value for shear: ".concat(valueOf) : new String("Invalid value for shear: "));
            return Float.MAX_VALUE;
        }
        try {
            return Math.min(100.0f, Math.max(-100.0f, Float.parseFloat((String) Assertions.checkNotNull(matcher.group(1)))));
        } catch (NumberFormatException e2) {
            String valueOf2 = String.valueOf(str);
            Log.w("TtmlDecoder", valueOf2.length() != 0 ? "Failed to parse shear: ".concat(valueOf2) : new String("Failed to parse shear: "), e2);
            return Float.MAX_VALUE;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c8, code lost:
        if (r14.equals(com.umeng.analytics.pro.ai.aF) != false) goto L_0x00fe;
     */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0117  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static long a(java.lang.String r14, com.google.android.exoplayer2.text.ttml.TtmlDecoder.b r15) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            Method dump skipped, instructions count: 340
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.a(java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlDecoder$b):long");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        final float a;
        final int b;
        final int c;

        b(float f, int i, int i2) {
            this.a = f;
            this.b = i;
            this.c = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        final int a;
        final int b;

        a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c {
        final int a;
        final int b;

        c(int i, int i2) {
            this.a = i;
            this.b = i2;
        }
    }
}
