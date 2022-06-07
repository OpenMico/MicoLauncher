package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ColorParser;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.idm.service.iot.LightService;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: WebvttCssParser.java */
/* loaded from: classes2.dex */
final class b {
    private static final Pattern a = Pattern.compile("\\[voice=\"([^\"]*)\"\\]");
    private static final Pattern b = Pattern.compile("^((?:[0-9]*\\.)?[0-9]+)(px|em|%)$");
    private final ParsableByteArray c = new ParsableByteArray();
    private final StringBuilder d = new StringBuilder();

    public List<WebvttCssStyle> a(ParsableByteArray parsableByteArray) {
        this.d.setLength(0);
        int position = parsableByteArray.getPosition();
        c(parsableByteArray);
        this.c.reset(parsableByteArray.getData(), parsableByteArray.getPosition());
        this.c.setPosition(position);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String b2 = b(this.c, this.d);
            if (b2 == null || !"{".equals(a(this.c, this.d))) {
                return arrayList;
            }
            WebvttCssStyle webvttCssStyle = new WebvttCssStyle();
            a(webvttCssStyle, b2);
            String str = null;
            boolean z = false;
            while (!z) {
                int position2 = this.c.getPosition();
                str = a(this.c, this.d);
                boolean z2 = str == null || "}".equals(str);
                if (!z2) {
                    this.c.setPosition(position2);
                    a(this.c, webvttCssStyle, this.d);
                }
                z = z2;
            }
            if ("}".equals(str)) {
                arrayList.add(webvttCssStyle);
            }
        }
    }

    @Nullable
    private static String b(ParsableByteArray parsableByteArray, StringBuilder sb) {
        b(parsableByteArray);
        if (parsableByteArray.bytesLeft() < 5 || !"::cue".equals(parsableByteArray.readString(5))) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        String a2 = a(parsableByteArray, sb);
        if (a2 == null) {
            return null;
        }
        if ("{".equals(a2)) {
            parsableByteArray.setPosition(position);
            return "";
        }
        String d = "(".equals(a2) ? d(parsableByteArray) : null;
        if (!")".equals(a(parsableByteArray, sb))) {
            return null;
        }
        return d;
    }

    private static String d(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        boolean z = false;
        while (position < limit && !z) {
            position++;
            z = ((char) parsableByteArray.getData()[position]) == ')';
        }
        return parsableByteArray.readString((position - 1) - parsableByteArray.getPosition()).trim();
    }

    private static void a(ParsableByteArray parsableByteArray, WebvttCssStyle webvttCssStyle, StringBuilder sb) {
        b(parsableByteArray);
        String d = d(parsableByteArray, sb);
        if (!"".equals(d) && Constants.COLON_SEPARATOR.equals(a(parsableByteArray, sb))) {
            b(parsableByteArray);
            String c = c(parsableByteArray, sb);
            if (c != null && !"".equals(c)) {
                int position = parsableByteArray.getPosition();
                String a2 = a(parsableByteArray, sb);
                if (!";".equals(a2)) {
                    if ("}".equals(a2)) {
                        parsableByteArray.setPosition(position);
                    } else {
                        return;
                    }
                }
                if (LightService.LightPropertyCommand.COLOR.equals(d)) {
                    webvttCssStyle.setFontColor(ColorParser.parseCssColor(c));
                } else if ("background-color".equals(d)) {
                    webvttCssStyle.setBackgroundColor(ColorParser.parseCssColor(c));
                } else {
                    boolean z = true;
                    if ("ruby-position".equals(d)) {
                        if ("over".equals(c)) {
                            webvttCssStyle.setRubyPosition(1);
                        } else if ("under".equals(c)) {
                            webvttCssStyle.setRubyPosition(2);
                        }
                    } else if ("text-combine-upright".equals(d)) {
                        if (!"all".equals(c) && !c.startsWith("digits")) {
                            z = false;
                        }
                        webvttCssStyle.setCombineUpright(z);
                    } else if ("text-decoration".equals(d)) {
                        if ("underline".equals(c)) {
                            webvttCssStyle.setUnderline(true);
                        }
                    } else if ("font-family".equals(d)) {
                        webvttCssStyle.setFontFamily(c);
                    } else if ("font-weight".equals(d)) {
                        if ("bold".equals(c)) {
                            webvttCssStyle.setBold(true);
                        }
                    } else if ("font-style".equals(d)) {
                        if ("italic".equals(c)) {
                            webvttCssStyle.setItalic(true);
                        }
                    } else if ("font-size".equals(d)) {
                        a(c, webvttCssStyle);
                    }
                }
            }
        }
    }

    static void b(ParsableByteArray parsableByteArray) {
        boolean z = true;
        while (parsableByteArray.bytesLeft() > 0 && z) {
            z = e(parsableByteArray) || f(parsableByteArray);
        }
    }

    @Nullable
    static String a(ParsableByteArray parsableByteArray, StringBuilder sb) {
        b(parsableByteArray);
        if (parsableByteArray.bytesLeft() == 0) {
            return null;
        }
        String d = d(parsableByteArray, sb);
        if (!"".equals(d)) {
            return d;
        }
        StringBuilder sb2 = new StringBuilder(1);
        sb2.append((char) parsableByteArray.readUnsignedByte());
        return sb2.toString();
    }

    private static boolean e(ParsableByteArray parsableByteArray) {
        switch (a(parsableByteArray, parsableByteArray.getPosition())) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
                parsableByteArray.skipBytes(1);
                return true;
            default:
                return false;
        }
    }

    static void c(ParsableByteArray parsableByteArray) {
        do {
        } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
    }

    private static char a(ParsableByteArray parsableByteArray, int i) {
        return (char) parsableByteArray.getData()[i];
    }

    @Nullable
    private static String c(ParsableByteArray parsableByteArray, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder();
        boolean z = false;
        while (!z) {
            int position = parsableByteArray.getPosition();
            String a2 = a(parsableByteArray, sb);
            if (a2 == null) {
                return null;
            }
            if ("}".equals(a2) || ";".equals(a2)) {
                parsableByteArray.setPosition(position);
                z = true;
            } else {
                sb2.append(a2);
            }
        }
        return sb2.toString();
    }

    private static boolean f(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] data = parsableByteArray.getData();
        if (position + 2 > limit) {
            return false;
        }
        int i = position + 1;
        if (data[position] != 47) {
            return false;
        }
        int i2 = i + 1;
        if (data[i] != 42) {
            return false;
        }
        while (true) {
            int i3 = i2 + 1;
            if (i3 >= limit) {
                parsableByteArray.skipBytes(limit - parsableByteArray.getPosition());
                return true;
            } else if (((char) data[i2]) == '*' && ((char) data[i3]) == '/') {
                i2 = i3 + 1;
                limit = i2;
            } else {
                i2 = i3;
            }
        }
    }

    private static String d(ParsableByteArray parsableByteArray, StringBuilder sb) {
        boolean z = false;
        sb.setLength(0);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit && !z) {
            char c = (char) parsableByteArray.getData()[position];
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !((c >= '0' && c <= '9') || c == '#' || c == '-' || c == '.' || c == '_'))) {
                z = true;
            } else {
                position++;
                sb.append(c);
            }
        }
        parsableByteArray.skipBytes(position - parsableByteArray.getPosition());
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.lang.String r6, com.google.android.exoplayer2.text.webvtt.WebvttCssStyle r7) {
        /*
            java.util.regex.Pattern r0 = com.google.android.exoplayer2.text.webvtt.b.b
            java.lang.String r1 = com.google.common.base.Ascii.toLowerCase(r6)
            java.util.regex.Matcher r0 = r0.matcher(r1)
            boolean r1 = r0.matches()
            if (r1 != 0) goto L_0x0036
            java.lang.String r7 = "WebvttCssParser"
            java.lang.String r0 = java.lang.String.valueOf(r6)
            int r0 = r0.length()
            int r0 = r0 + 22
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r0)
            java.lang.String r0 = "Invalid font-size: '"
            r1.append(r0)
            r1.append(r6)
            java.lang.String r6 = "'."
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            com.google.android.exoplayer2.util.Log.w(r7, r6)
            return
        L_0x0036:
            r6 = 2
            java.lang.String r1 = r0.group(r6)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            r2 = -1
            int r3 = r1.hashCode()
            r4 = 37
            r5 = 1
            if (r3 == r4) goto L_0x0068
            r4 = 3240(0xca8, float:4.54E-42)
            if (r3 == r4) goto L_0x005e
            r4 = 3592(0xe08, float:5.033E-42)
            if (r3 == r4) goto L_0x0054
            goto L_0x0072
        L_0x0054:
            java.lang.String r3 = "px"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0072
            r1 = 0
            goto L_0x0073
        L_0x005e:
            java.lang.String r3 = "em"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0072
            r1 = r5
            goto L_0x0073
        L_0x0068:
            java.lang.String r3 = "%"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0072
            r1 = r6
            goto L_0x0073
        L_0x0072:
            r1 = r2
        L_0x0073:
            switch(r1) {
                case 0: goto L_0x0085;
                case 1: goto L_0x0081;
                case 2: goto L_0x007c;
                default: goto L_0x0076;
            }
        L_0x0076:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>()
            throw r6
        L_0x007c:
            r6 = 3
            r7.setFontSizeUnit(r6)
            goto L_0x0088
        L_0x0081:
            r7.setFontSizeUnit(r6)
            goto L_0x0088
        L_0x0085:
            r7.setFontSizeUnit(r5)
        L_0x0088:
            java.lang.String r6 = r0.group(r5)
            java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r6)
            java.lang.String r6 = (java.lang.String) r6
            float r6 = java.lang.Float.parseFloat(r6)
            r7.setFontSize(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.webvtt.b.a(java.lang.String, com.google.android.exoplayer2.text.webvtt.WebvttCssStyle):void");
    }

    private void a(WebvttCssStyle webvttCssStyle, String str) {
        if (!"".equals(str)) {
            int indexOf = str.indexOf(91);
            if (indexOf != -1) {
                Matcher matcher = a.matcher(str.substring(indexOf));
                if (matcher.matches()) {
                    webvttCssStyle.setTargetVoice((String) Assertions.checkNotNull(matcher.group(1)));
                }
                str = str.substring(0, indexOf);
            }
            String[] split = Util.split(str, "\\.");
            String str2 = split[0];
            int indexOf2 = str2.indexOf(35);
            if (indexOf2 != -1) {
                webvttCssStyle.setTargetTagName(str2.substring(0, indexOf2));
                webvttCssStyle.setTargetId(str2.substring(indexOf2 + 1));
            } else {
                webvttCssStyle.setTargetTagName(str2);
            }
            if (split.length > 1) {
                webvttCssStyle.setTargetClasses((String[]) Util.nullSafeArrayCopyOfRange(split, 1, split.length));
            }
        }
    }
}
