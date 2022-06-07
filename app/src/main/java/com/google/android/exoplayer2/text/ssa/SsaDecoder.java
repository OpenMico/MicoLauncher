package com.google.android.exoplayer2.text.ssa;

import android.text.Layout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.ssa.SsaStyle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class SsaDecoder extends SimpleSubtitleDecoder {
    private static final Pattern a = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)[:.](\\d+)");
    private final boolean b;
    @Nullable
    private final a c;
    private Map<String, SsaStyle> d;
    private float e;
    private float f;

    private static float d(int i) {
        switch (i) {
            case 0:
                return 0.05f;
            case 1:
                return 0.5f;
            case 2:
                return 0.95f;
            default:
                return -3.4028235E38f;
        }
    }

    public SsaDecoder() {
        this(null);
    }

    public SsaDecoder(@Nullable List<byte[]> list) {
        super("SsaDecoder");
        this.e = -3.4028235E38f;
        this.f = -3.4028235E38f;
        if (list == null || list.isEmpty()) {
            this.b = false;
            this.c = null;
            return;
        }
        this.b = true;
        String fromUtf8Bytes = Util.fromUtf8Bytes(list.get(0));
        Assertions.checkArgument(fromUtf8Bytes.startsWith("Format:"));
        this.c = (a) Assertions.checkNotNull(a.a(fromUtf8Bytes));
        a(new ParsableByteArray(list.get(1)));
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        if (!this.b) {
            a(parsableByteArray);
        }
        a(parsableByteArray, arrayList, arrayList2);
        return new b(arrayList, arrayList2);
    }

    private void a(ParsableByteArray parsableByteArray) {
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if ("[Script Info]".equalsIgnoreCase(readLine)) {
                b(parsableByteArray);
            } else if ("[V4+ Styles]".equalsIgnoreCase(readLine)) {
                this.d = c(parsableByteArray);
            } else if ("[V4 Styles]".equalsIgnoreCase(readLine)) {
                Log.i("SsaDecoder", "[V4 Styles] are not supported");
            } else if ("[Events]".equalsIgnoreCase(readLine)) {
                return;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
        if (r2.equals("playresx") != false) goto L_0x0048;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(com.google.android.exoplayer2.util.ParsableByteArray r7) {
        /*
            r6 = this;
        L_0x0000:
            java.lang.String r0 = r7.readLine()
            if (r0 == 0) goto L_0x0066
            int r1 = r7.bytesLeft()
            if (r1 == 0) goto L_0x0014
            int r1 = r7.peekUnsignedByte()
            r2 = 91
            if (r1 == r2) goto L_0x0066
        L_0x0014:
            java.lang.String r1 = ":"
            java.lang.String[] r0 = r0.split(r1)
            int r1 = r0.length
            r2 = 2
            if (r1 == r2) goto L_0x001f
            goto L_0x0000
        L_0x001f:
            r1 = 0
            r2 = r0[r1]
            java.lang.String r2 = r2.trim()
            java.lang.String r2 = com.google.common.base.Ascii.toLowerCase(r2)
            r3 = -1
            int r4 = r2.hashCode()
            r5 = 1
            switch(r4) {
                case 1879649548: goto L_0x003e;
                case 1879649549: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x0047
        L_0x0034:
            java.lang.String r1 = "playresy"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0047
            r1 = r5
            goto L_0x0048
        L_0x003e:
            java.lang.String r4 = "playresx"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r1 = r3
        L_0x0048:
            switch(r1) {
                case 0: goto L_0x0059;
                case 1: goto L_0x004c;
                default: goto L_0x004b;
            }
        L_0x004b:
            goto L_0x0000
        L_0x004c:
            r0 = r0[r5]     // Catch: NumberFormatException -> 0x0000
            java.lang.String r0 = r0.trim()     // Catch: NumberFormatException -> 0x0000
            float r0 = java.lang.Float.parseFloat(r0)     // Catch: NumberFormatException -> 0x0000
            r6.f = r0     // Catch: NumberFormatException -> 0x0000
            goto L_0x0000
        L_0x0059:
            r0 = r0[r5]     // Catch: NumberFormatException -> 0x0000
            java.lang.String r0 = r0.trim()     // Catch: NumberFormatException -> 0x0000
            float r0 = java.lang.Float.parseFloat(r0)     // Catch: NumberFormatException -> 0x0000
            r6.e = r0     // Catch: NumberFormatException -> 0x0000
            goto L_0x0000
        L_0x0066:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDecoder.b(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    private static Map<String, SsaStyle> c(ParsableByteArray parsableByteArray) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        SsaStyle.a aVar = null;
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null || (parsableByteArray.bytesLeft() != 0 && parsableByteArray.peekUnsignedByte() == 91)) {
                break;
            } else if (readLine.startsWith("Format:")) {
                aVar = SsaStyle.a.a(readLine);
            } else if (readLine.startsWith("Style:")) {
                if (aVar == null) {
                    String valueOf = String.valueOf(readLine);
                    Log.w("SsaDecoder", valueOf.length() != 0 ? "Skipping 'Style:' line before 'Format:' line: ".concat(valueOf) : new String("Skipping 'Style:' line before 'Format:' line: "));
                } else {
                    SsaStyle a2 = SsaStyle.a(readLine, aVar);
                    if (a2 != null) {
                        linkedHashMap.put(a2.a, a2);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private void a(ParsableByteArray parsableByteArray, List<List<Cue>> list, List<Long> list2) {
        a aVar = this.b ? this.c : null;
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if (readLine.startsWith("Format:")) {
                aVar = a.a(readLine);
            } else if (readLine.startsWith("Dialogue:")) {
                if (aVar == null) {
                    String valueOf = String.valueOf(readLine);
                    Log.w("SsaDecoder", valueOf.length() != 0 ? "Skipping dialogue line before complete format: ".concat(valueOf) : new String("Skipping dialogue line before complete format: "));
                } else {
                    a(readLine, aVar, list, list2);
                }
            }
        }
    }

    private void a(String str, a aVar, List<List<Cue>> list, List<Long> list2) {
        Assertions.checkArgument(str.startsWith("Dialogue:"));
        String[] split = str.substring(9).split(Constants.ACCEPT_TIME_SEPARATOR_SP, aVar.e);
        if (split.length != aVar.e) {
            String valueOf = String.valueOf(str);
            Log.w("SsaDecoder", valueOf.length() != 0 ? "Skipping dialogue line with fewer columns than format: ".concat(valueOf) : new String("Skipping dialogue line with fewer columns than format: "));
            return;
        }
        long a2 = a(split[aVar.a]);
        if (a2 == C.TIME_UNSET) {
            String valueOf2 = String.valueOf(str);
            Log.w("SsaDecoder", valueOf2.length() != 0 ? "Skipping invalid timing: ".concat(valueOf2) : new String("Skipping invalid timing: "));
            return;
        }
        long a3 = a(split[aVar.b]);
        if (a3 == C.TIME_UNSET) {
            String valueOf3 = String.valueOf(str);
            Log.w("SsaDecoder", valueOf3.length() != 0 ? "Skipping invalid timing: ".concat(valueOf3) : new String("Skipping invalid timing: "));
            return;
        }
        SsaStyle ssaStyle = (this.d == null || aVar.c == -1) ? null : this.d.get(split[aVar.c].trim());
        String str2 = split[aVar.d];
        Cue a4 = a(SsaStyle.b.b(str2).replace("\\N", "\n").replace("\\n", "\n").replace("\\h", " "), ssaStyle, SsaStyle.b.a(str2), this.e, this.f);
        int a5 = a(a3, list2, list);
        for (int a6 = a(a2, list2, list); a6 < a5; a6++) {
            list.get(a6).add(a4);
        }
    }

    private static long a(String str) {
        Matcher matcher = a.matcher(str.trim());
        return !matcher.matches() ? C.TIME_UNSET : (Long.parseLong((String) Util.castNonNull(matcher.group(1))) * 60 * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(2))) * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(3))) * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(4))) * 10000);
    }

    private static Cue a(String str, @Nullable SsaStyle ssaStyle, SsaStyle.b bVar, float f, float f2) {
        SpannableString spannableString = new SpannableString(str);
        Cue.Builder text = new Cue.Builder().setText(spannableString);
        if (ssaStyle != null) {
            if (ssaStyle.c != null) {
                spannableString.setSpan(new ForegroundColorSpan(ssaStyle.c.intValue()), 0, spannableString.length(), 33);
            }
            if (!(ssaStyle.d == -3.4028235E38f || f2 == -3.4028235E38f)) {
                text.setTextSize(ssaStyle.d / f2, 1);
            }
            if (ssaStyle.e && ssaStyle.f) {
                spannableString.setSpan(new StyleSpan(3), 0, spannableString.length(), 33);
            } else if (ssaStyle.e) {
                spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 33);
            } else if (ssaStyle.f) {
                spannableString.setSpan(new StyleSpan(2), 0, spannableString.length(), 33);
            }
            if (ssaStyle.g) {
                spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 33);
            }
            if (ssaStyle.h) {
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 33);
            }
        }
        int i = -1;
        if (bVar.a != -1) {
            i = bVar.a;
        } else if (ssaStyle != null) {
            i = ssaStyle.b;
        }
        text.setTextAlignment(a(i)).setPositionAnchor(c(i)).setLineAnchor(b(i));
        if (bVar.b == null || f2 == -3.4028235E38f || f == -3.4028235E38f) {
            text.setPosition(d(text.getPositionAnchor()));
            text.setLine(d(text.getLineAnchor()), 0);
        } else {
            text.setPosition(bVar.b.x / f);
            text.setLine(bVar.b.y / f2, 0);
        }
        return text.build();
    }

    @Nullable
    private static Layout.Alignment a(int i) {
        if (i == -1) {
            return null;
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
                return Layout.Alignment.ALIGN_NORMAL;
            case 2:
            case 5:
            case 8:
                return Layout.Alignment.ALIGN_CENTER;
            case 3:
            case 6:
            case 9:
                return Layout.Alignment.ALIGN_OPPOSITE;
            default:
                StringBuilder sb = new StringBuilder(30);
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.w("SsaDecoder", sb.toString());
                return null;
        }
    }

    private static int b(int i) {
        if (i == -1) {
            return Integer.MIN_VALUE;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                return 2;
            case 4:
            case 5:
            case 6:
                return 1;
            case 7:
            case 8:
            case 9:
                return 0;
            default:
                StringBuilder sb = new StringBuilder(30);
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.w("SsaDecoder", sb.toString());
                return Integer.MIN_VALUE;
        }
    }

    private static int c(int i) {
        if (i == -1) {
            return Integer.MIN_VALUE;
        }
        switch (i) {
            case 1:
            case 4:
            case 7:
                return 0;
            case 2:
            case 5:
            case 8:
                return 1;
            case 3:
            case 6:
            case 9:
                return 2;
            default:
                StringBuilder sb = new StringBuilder(30);
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.w("SsaDecoder", sb.toString());
                return Integer.MIN_VALUE;
        }
    }

    private static int a(long j, List<Long> list, List<List<Cue>> list2) {
        int i;
        int size = list.size() - 1;
        while (true) {
            if (size < 0) {
                i = 0;
                break;
            } else if (list.get(size).longValue() == j) {
                return size;
            } else {
                if (list.get(size).longValue() < j) {
                    i = size + 1;
                    break;
                }
                size--;
            }
        }
        list.add(i, Long.valueOf(j));
        list2.add(i, i == 0 ? new ArrayList() : new ArrayList(list2.get(i - 1)));
        return i;
    }
}
