package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class RtspHeaders {
    public static final RtspHeaders a = new Builder().build();
    private final ImmutableListMultimap<String, String> b;

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final ImmutableListMultimap.Builder<String, String> a = new ImmutableListMultimap.Builder<>();

        public Builder add(String str, String str2) {
            this.a.put((ImmutableListMultimap.Builder<String, String>) RtspHeaders.d(str.trim()), str2.trim());
            return this;
        }

        public Builder addAll(List<String> list) {
            for (int i = 0; i < list.size(); i++) {
                String[] splitAtFirst = Util.splitAtFirst(list.get(i), ":\\s?");
                if (splitAtFirst.length == 2) {
                    add(splitAtFirst[0], splitAtFirst[1]);
                }
            }
            return this;
        }

        public Builder addAll(Map<String, String> map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                add(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public RtspHeaders build() {
            return new RtspHeaders(this);
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RtspHeaders)) {
            return false;
        }
        return this.b.equals(((RtspHeaders) obj).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public ImmutableListMultimap<String, String> a() {
        return this.b;
    }

    @Nullable
    public String a(String str) {
        ImmutableList<String> b = b(str);
        if (b.isEmpty()) {
            return null;
        }
        return (String) Iterables.getLast(b);
    }

    public ImmutableList<String> b(String str) {
        return this.b.get((ImmutableListMultimap<String, String>) d(str));
    }

    private RtspHeaders(Builder builder) {
        this.b = builder.a.build();
    }

    public static String d(String str) {
        return Ascii.equalsIgnoreCase(str, "Accept") ? "Accept" : Ascii.equalsIgnoreCase(str, "Allow") ? "Allow" : Ascii.equalsIgnoreCase(str, "Authorization") ? "Authorization" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.BANDWIDTH) ? RtspHeaders.Names.BANDWIDTH : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.BLOCKSIZE) ? RtspHeaders.Names.BLOCKSIZE : Ascii.equalsIgnoreCase(str, "Cache-Control") ? "Cache-Control" : Ascii.equalsIgnoreCase(str, "Connection") ? "Connection" : Ascii.equalsIgnoreCase(str, "Content-Base") ? "Content-Base" : Ascii.equalsIgnoreCase(str, "Content-Encoding") ? "Content-Encoding" : Ascii.equalsIgnoreCase(str, "Content-Language") ? "Content-Language" : Ascii.equalsIgnoreCase(str, "Content-Length") ? "Content-Length" : Ascii.equalsIgnoreCase(str, "Content-Location") ? "Content-Location" : Ascii.equalsIgnoreCase(str, "Content-Type") ? "Content-Type" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.CSEQ) ? RtspHeaders.Names.CSEQ : Ascii.equalsIgnoreCase(str, "Date") ? "Date" : Ascii.equalsIgnoreCase(str, "Expires") ? "Expires" : Ascii.equalsIgnoreCase(str, "Proxy-Authenticate") ? "Proxy-Authenticate" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.PROXY_REQUIRE) ? RtspHeaders.Names.PROXY_REQUIRE : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.PUBLIC) ? RtspHeaders.Names.PUBLIC : Ascii.equalsIgnoreCase(str, "Range") ? "Range" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.RTP_INFO) ? RtspHeaders.Names.RTP_INFO : Ascii.equalsIgnoreCase(str, "RTCP-Interval") ? "RTCP-Interval" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.SCALE) ? RtspHeaders.Names.SCALE : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.SESSION) ? RtspHeaders.Names.SESSION : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.SPEED) ? RtspHeaders.Names.SPEED : Ascii.equalsIgnoreCase(str, "Supported") ? "Supported" : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.TIMESTAMP) ? RtspHeaders.Names.TIMESTAMP : Ascii.equalsIgnoreCase(str, RtspHeaders.Names.TRANSPORT) ? RtspHeaders.Names.TRANSPORT : Ascii.equalsIgnoreCase(str, "User-Agent") ? "User-Agent" : Ascii.equalsIgnoreCase(str, "Via") ? "Via" : Ascii.equalsIgnoreCase(str, "WWW-Authenticate") ? "WWW-Authenticate" : str;
    }
}
