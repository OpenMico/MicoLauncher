package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.MediaDescription;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.util.Assertions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: SessionDescriptionParser.java */
/* loaded from: classes2.dex */
final class m {
    private static final Pattern a = Pattern.compile("([a-z])=\\s?(.+)");
    private static final Pattern b = Pattern.compile("([0-9A-Za-z-]+)(?::(.*))?");
    private static final Pattern c = Pattern.compile("(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)");

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01d6, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.exoplayer2.source.rtsp.SessionDescription a(java.lang.String r13) throws com.google.android.exoplayer2.ParserException {
        /*
            Method dump skipped, instructions count: 578
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.rtsp.m.a(java.lang.String):com.google.android.exoplayer2.source.rtsp.SessionDescription");
    }

    private static void a(SessionDescription.Builder builder, MediaDescription.Builder builder2) throws ParserException {
        try {
            builder.addMediaDescription(builder2.build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    private static MediaDescription.Builder b(String str) throws ParserException {
        Matcher matcher = c.matcher(str);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(str);
            throw ParserException.createForMalformedManifest(valueOf.length() != 0 ? "Malformed SDP media description line: ".concat(valueOf) : new String("Malformed SDP media description line: "), null);
        }
        try {
            return new MediaDescription.Builder((String) Assertions.checkNotNull(matcher.group(1)), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(2))), (String) Assertions.checkNotNull(matcher.group(3)), Integer.parseInt((String) Assertions.checkNotNull(matcher.group(4))));
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(str);
            throw ParserException.createForMalformedManifest(valueOf2.length() != 0 ? "Malformed SDP media description line: ".concat(valueOf2) : new String("Malformed SDP media description line: "), e);
        }
    }
}
