package com.google.android.exoplayer2.text.subrip;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class SubripDecoder extends SimpleSubtitleDecoder {
    private static final Pattern a = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*");
    private static final Pattern b = Pattern.compile("\\{\\\\.*?\\}");
    private final StringBuilder c = new StringBuilder();
    private final ArrayList<String> d = new ArrayList<>();

    public SubripDecoder() {
        super("SubripDecoder");
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        LongArray longArray = new LongArray();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        while (true) {
            String readLine = parsableByteArray.readLine();
            int i2 = 0;
            if (readLine == null) {
                break;
            } else if (readLine.length() != 0) {
                try {
                    Integer.parseInt(readLine);
                    String readLine2 = parsableByteArray.readLine();
                    if (readLine2 == null) {
                        Log.w("SubripDecoder", "Unexpected end");
                        break;
                    }
                    Matcher matcher = a.matcher(readLine2);
                    if (matcher.matches()) {
                        longArray.add(a(matcher, 1));
                        longArray.add(a(matcher, 6));
                        this.c.setLength(0);
                        this.d.clear();
                        for (String readLine3 = parsableByteArray.readLine(); !TextUtils.isEmpty(readLine3); readLine3 = parsableByteArray.readLine()) {
                            if (this.c.length() > 0) {
                                this.c.append("<br>");
                            }
                            this.c.append(a(readLine3, this.d));
                        }
                        Spanned fromHtml = Html.fromHtml(this.c.toString());
                        String str = null;
                        while (true) {
                            if (i2 >= this.d.size()) {
                                break;
                            }
                            String str2 = this.d.get(i2);
                            if (str2.matches("\\{\\\\an[1-9]\\}")) {
                                str = str2;
                                break;
                            }
                            i2++;
                        }
                        arrayList.add(a(fromHtml, str));
                        arrayList.add(Cue.EMPTY);
                    } else {
                        String valueOf = String.valueOf(readLine2);
                        Log.w("SubripDecoder", valueOf.length() != 0 ? "Skipping invalid timing: ".concat(valueOf) : new String("Skipping invalid timing: "));
                    }
                } catch (NumberFormatException unused) {
                    String valueOf2 = String.valueOf(readLine);
                    Log.w("SubripDecoder", valueOf2.length() != 0 ? "Skipping invalid index: ".concat(valueOf2) : new String("Skipping invalid index: "));
                }
            }
        }
        return new a((Cue[]) arrayList.toArray(new Cue[0]), longArray.toArray());
    }

    private String a(String str, ArrayList<String> arrayList) {
        String trim = str.trim();
        StringBuilder sb = new StringBuilder(trim);
        Matcher matcher = b.matcher(trim);
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            arrayList.add(group);
            int start = matcher.start() - i;
            int length = group.length();
            sb.replace(start, start + length, "");
            i += length;
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x009c, code lost:
        if (r13.equals("{\\an9}") != false) goto L_0x00f0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.android.exoplayer2.text.Cue a(android.text.Spanned r12, @androidx.annotation.Nullable java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.subrip.SubripDecoder.a(android.text.Spanned, java.lang.String):com.google.android.exoplayer2.text.Cue");
    }

    private static long a(Matcher matcher, int i) {
        String group = matcher.group(i + 1);
        long parseLong = (group != null ? Long.parseLong(group) * 60 * 60 * 1000 : 0L) + (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 2))) * 60 * 1000) + (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 3))) * 1000);
        String group2 = matcher.group(i + 4);
        if (group2 != null) {
            parseLong += Long.parseLong(group2);
        }
        return parseLong * 1000;
    }

    static float a(int i) {
        switch (i) {
            case 0:
                return 0.08f;
            case 1:
                return 0.5f;
            case 2:
                return 0.92f;
            default:
                throw new IllegalArgumentException();
        }
    }
}
