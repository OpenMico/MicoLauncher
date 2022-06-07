package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspHeaders;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.xiaomi.micolauncher.common.build.BuildSettings;
import com.xiaomi.mipush.sdk.Constants;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jetty.http.HttpMethods;

/* loaded from: classes2.dex */
public final class RtspMessageUtil {
    private static final Pattern a = Pattern.compile("([A-Z_]+) (.*) RTSP/1\\.0");
    private static final Pattern b = Pattern.compile("RTSP/1\\.0 (\\d+) (.+)");
    private static final Pattern c = Pattern.compile("Content-Length:\\s?(\\d+)", 2);
    private static final Pattern d = Pattern.compile("([\\w$\\-_.+]+)(?:;\\s?timeout=(\\d+))?");
    private static final Pattern e = Pattern.compile("Digest realm=\"([^\"\\x00-\\x08\\x0A-\\x1f\\x7f]+)\",\\s?(?:domain=\"(.+)\",\\s?)?nonce=\"([^\"\\x00-\\x08\\x0A-\\x1f\\x7f]+)\"(?:,\\s?opaque=\"([^\"\\x00-\\x08\\x0A-\\x1f\\x7f]+)\")?");
    private static final Pattern f = Pattern.compile("Basic realm=\"([^\"\\x00-\\x08\\x0A-\\x1f\\x7f]+)\"");
    private static final String g = new String(new byte[]{10});
    private static final String h = new String(new byte[]{13, 10});

    /* loaded from: classes2.dex */
    public static final class RtspSessionHeader {
        public final String sessionId;
        public final long timeoutMs;

        public RtspSessionHeader(String str, long j) {
            this.sessionId = str;
            this.timeoutMs = j;
        }
    }

    /* loaded from: classes2.dex */
    public static final class RtspAuthUserInfo {
        public final String password;
        public final String username;

        public RtspAuthUserInfo(String str, String str2) {
            this.username = str;
            this.password = str2;
        }
    }

    public static ImmutableList<String> a(RtspRequest rtspRequest) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        builder.add((ImmutableList.Builder) Util.formatInvariant("%s %s %s", a(rtspRequest.b), rtspRequest.a, "RTSP/1.0"));
        ImmutableListMultimap<String, String> a2 = rtspRequest.c.a();
        UnmodifiableIterator<String> it = a2.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            ImmutableList<String> immutableList = a2.get((ImmutableListMultimap<String, String>) next);
            for (int i = 0; i < immutableList.size(); i++) {
                builder.add((ImmutableList.Builder) Util.formatInvariant("%s: %s", next, immutableList.get(i)));
            }
        }
        builder.add((ImmutableList.Builder) "");
        builder.add((ImmutableList.Builder) rtspRequest.d);
        return builder.build();
    }

    public static byte[] a(List<String> list) {
        return Joiner.on(h).join(list).getBytes(RtspMessageChannel.a);
    }

    public static Uri a(Uri uri) {
        if (uri.getUserInfo() == null) {
            return uri;
        }
        String str = (String) Assertions.checkNotNull(uri.getAuthority());
        Assertions.checkArgument(str.contains("@"));
        return uri.buildUpon().encodedAuthority(Util.split(str, "@")[1]).build();
    }

    @Nullable
    public static RtspAuthUserInfo b(Uri uri) {
        String userInfo = uri.getUserInfo();
        if (userInfo == null || !userInfo.contains(Constants.COLON_SEPARATOR)) {
            return null;
        }
        String[] splitAtFirst = Util.splitAtFirst(userInfo, Constants.COLON_SEPARATOR);
        return new RtspAuthUserInfo(splitAtFirst[0], splitAtFirst[1]);
    }

    public static byte[] a(String str) {
        return str.getBytes(RtspMessageChannel.a);
    }

    public static String a(int i) {
        switch (i) {
            case 1:
                return "ANNOUNCE";
            case 2:
                return "DESCRIBE";
            case 3:
                return "GET_PARAMETER";
            case 4:
                return HttpMethods.OPTIONS;
            case 5:
                return "PAUSE";
            case 6:
                return BuildSettings.PLAY;
            case 7:
                return "PLAY_NOTIFY";
            case 8:
                return "RECORD";
            case 9:
                return "REDIRECT";
            case 10:
                return "SETUP";
            case 11:
                return "SET_PARAMETER";
            case 12:
                return "TEARDOWN";
            default:
                throw new IllegalStateException();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int i(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1881579439:
                if (str.equals("RECORD")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -880847356:
                if (str.equals("TEARDOWN")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -702888512:
                if (str.equals("GET_PARAMETER")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -531492226:
                if (str.equals(HttpMethods.OPTIONS)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -84360524:
                if (str.equals("PLAY_NOTIFY")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 2458420:
                if (str.equals(BuildSettings.PLAY)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 6481884:
                if (str.equals("REDIRECT")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 71242700:
                if (str.equals("SET_PARAMETER")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 75902422:
                if (str.equals("PAUSE")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 78791261:
                if (str.equals("SETUP")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 133006441:
                if (str.equals("ANNOUNCE")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 1800840907:
                if (str.equals("DESCRIBE")) {
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
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case '\b':
                return 9;
            case '\t':
                return 10;
            case '\n':
                return 11;
            case 11:
                return 12;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static i b(List<String> list) {
        boolean z = false;
        Matcher matcher = b.matcher(list.get(0));
        Assertions.checkArgument(matcher.matches());
        int parseInt = Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)));
        int indexOf = list.indexOf("");
        if (indexOf > 0) {
            z = true;
        }
        Assertions.checkArgument(z);
        return new i(parseInt, new RtspHeaders.Builder().addAll(list.subList(1, indexOf)).build(), Joiner.on(h).join(list.subList(indexOf + 1, list.size())));
    }

    public static boolean b(String str) {
        return a.matcher(str).matches() || b.matcher(str).matches();
    }

    public static String[] c(String str) {
        return Util.split(str, str.contains(h) ? h : g);
    }

    public static long d(String str) throws ParserException {
        try {
            Matcher matcher = c.matcher(str);
            if (matcher.find()) {
                return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1L;
        } catch (NumberFormatException e2) {
            throw ParserException.createForMalformedManifest(str, e2);
        }
    }

    public static ImmutableList<Integer> e(@Nullable String str) {
        if (str == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (String str2 : Util.split(str, ",\\s?")) {
            builder.add((ImmutableList.Builder) Integer.valueOf(i(str2)));
        }
        return builder.build();
    }

    public static RtspSessionHeader f(String str) throws ParserException {
        Matcher matcher = d.matcher(str);
        if (matcher.matches()) {
            String str2 = (String) Assertions.checkNotNull(matcher.group(1));
            long j = 60000;
            String group = matcher.group(2);
            if (group != null) {
                try {
                    j = Integer.parseInt(group) * 1000;
                } catch (NumberFormatException e2) {
                    throw ParserException.createForMalformedManifest(str, e2);
                }
            }
            return new RtspSessionHeader(str2, j);
        }
        throw ParserException.createForMalformedManifest(str, null);
    }

    public static c g(String str) throws ParserException {
        Matcher matcher = e.matcher(str);
        if (matcher.find()) {
            return new c(2, (String) Assertions.checkNotNull(matcher.group(1)), (String) Assertions.checkNotNull(matcher.group(3)), Strings.nullToEmpty(matcher.group(4)));
        }
        Matcher matcher2 = f.matcher(str);
        if (matcher2.matches()) {
            return new c(1, (String) Assertions.checkNotNull(matcher2.group(1)), "", "");
        }
        String valueOf = String.valueOf(str);
        throw ParserException.createForMalformedManifest(valueOf.length() != 0 ? "Invalid WWW-Authenticate header ".concat(valueOf) : new String("Invalid WWW-Authenticate header "), null);
    }

    public static int h(String str) throws ParserException {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            throw ParserException.createForMalformedManifest(str, e2);
        }
    }
}
