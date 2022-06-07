package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class MediaDescription {
    public final String a;
    public final int b;
    public final String c;
    public final int d;
    public final int e;
    @Nullable
    public final String f;
    @Nullable
    public final String g;
    @Nullable
    public final String h;
    public final ImmutableMap<String, String> i;
    public final RtpMapAttribute j;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface MediaType {
    }

    /* loaded from: classes2.dex */
    public static final class RtpMapAttribute {
        public final int clockRate;
        public final int encodingParameters;
        public final String mediaEncoding;
        public final int payloadType;

        public static RtpMapAttribute parse(String str) throws ParserException {
            String[] splitAtFirst = Util.splitAtFirst(str, StringUtils.SPACE);
            Assertions.checkArgument(splitAtFirst.length == 2);
            int h = RtspMessageUtil.h(splitAtFirst[0]);
            String[] split = Util.split(splitAtFirst[1].trim(), "/");
            Assertions.checkArgument(split.length >= 2);
            int h2 = RtspMessageUtil.h(split[1]);
            int i = -1;
            if (split.length == 3) {
                i = RtspMessageUtil.h(split[2]);
            }
            return new RtpMapAttribute(h, split[0], h2, i);
        }

        private RtpMapAttribute(int i, String str, int i2, int i3) {
            this.payloadType = i;
            this.mediaEncoding = str;
            this.clockRate = i2;
            this.encodingParameters = i3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RtpMapAttribute rtpMapAttribute = (RtpMapAttribute) obj;
            return this.payloadType == rtpMapAttribute.payloadType && this.mediaEncoding.equals(rtpMapAttribute.mediaEncoding) && this.clockRate == rtpMapAttribute.clockRate && this.encodingParameters == rtpMapAttribute.encodingParameters;
        }

        public int hashCode() {
            return ((((((217 + this.payloadType) * 31) + this.mediaEncoding.hashCode()) * 31) + this.clockRate) * 31) + this.encodingParameters;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final String a;
        private final int b;
        private final String c;
        private final int d;
        private final HashMap<String, String> e = new HashMap<>();
        private int f = -1;
        @Nullable
        private String g;
        @Nullable
        private String h;
        @Nullable
        private String i;

        public Builder(String str, int i, String str2, int i2) {
            this.a = str;
            this.b = i;
            this.c = str2;
            this.d = i2;
        }

        public Builder setMediaTitle(String str) {
            this.g = str;
            return this;
        }

        public Builder setConnection(String str) {
            this.h = str;
            return this;
        }

        public Builder setBitrate(int i) {
            this.f = i;
            return this;
        }

        public Builder setKey(String str) {
            this.i = str;
            return this;
        }

        public Builder addAttribute(String str, String str2) {
            this.e.put(str, str2);
            return this;
        }

        public MediaDescription build() {
            try {
                Assertions.checkState(this.e.containsKey("rtpmap"));
                return new MediaDescription(this, ImmutableMap.copyOf(this.e), RtpMapAttribute.parse((String) Util.castNonNull(this.e.get("rtpmap"))));
            } catch (ParserException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private MediaDescription(Builder builder, ImmutableMap<String, String> immutableMap, RtpMapAttribute rtpMapAttribute) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.f = builder.g;
        this.g = builder.h;
        this.e = builder.f;
        this.h = builder.i;
        this.i = immutableMap;
        this.j = rtpMapAttribute;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MediaDescription mediaDescription = (MediaDescription) obj;
        return this.a.equals(mediaDescription.a) && this.b == mediaDescription.b && this.c.equals(mediaDescription.c) && this.d == mediaDescription.d && this.e == mediaDescription.e && this.i.equals(mediaDescription.i) && this.j.equals(mediaDescription.j) && Util.areEqual(this.f, mediaDescription.f) && Util.areEqual(this.g, mediaDescription.g) && Util.areEqual(this.h, mediaDescription.h);
    }

    public int hashCode() {
        int hashCode = (((((((((((((217 + this.a.hashCode()) * 31) + this.b) * 31) + this.c.hashCode()) * 31) + this.d) * 31) + this.e) * 31) + this.i.hashCode()) * 31) + this.j.hashCode()) * 31;
        String str = this.f;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.g;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.h;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode3 + i;
    }

    public ImmutableMap<String, String> a() {
        String str = this.i.get("fmtp");
        if (str == null) {
            return ImmutableMap.of();
        }
        String[] splitAtFirst = Util.splitAtFirst(str, StringUtils.SPACE);
        Assertions.checkArgument(splitAtFirst.length == 2, str);
        String[] split = splitAtFirst[1].split(";\\s?", 0);
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (String str2 : split) {
            String[] splitAtFirst2 = Util.splitAtFirst(str2, "=");
            builder.put(splitAtFirst2[0], splitAtFirst2[1]);
        }
        return builder.build();
    }
}
