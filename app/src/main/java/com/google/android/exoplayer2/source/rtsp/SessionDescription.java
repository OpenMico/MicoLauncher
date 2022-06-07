package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class SessionDescription {
    public final ImmutableMap<String, String> a;
    public final ImmutableList<MediaDescription> b;
    public final String c;
    public final String d;
    public final String e;
    public final int f;
    @Nullable
    public final Uri g;
    @Nullable
    public final String h;
    @Nullable
    public final String i;
    @Nullable
    public final String j;
    @Nullable
    public final String k;
    @Nullable
    public final String l;

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final HashMap<String, String> a = new HashMap<>();
        private final ImmutableList.Builder<MediaDescription> b = new ImmutableList.Builder<>();
        private int c = -1;
        @Nullable
        private String d;
        @Nullable
        private String e;
        @Nullable
        private String f;
        @Nullable
        private Uri g;
        @Nullable
        private String h;
        @Nullable
        private String i;
        @Nullable
        private String j;
        @Nullable
        private String k;
        @Nullable
        private String l;

        public Builder setSessionName(String str) {
            this.d = str;
            return this;
        }

        public Builder setSessionInfo(String str) {
            this.j = str;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.g = uri;
            return this;
        }

        public Builder setOrigin(String str) {
            this.e = str;
            return this;
        }

        public Builder setConnection(String str) {
            this.h = str;
            return this;
        }

        public Builder setBitrate(int i) {
            this.c = i;
            return this;
        }

        public Builder setTiming(String str) {
            this.f = str;
            return this;
        }

        public Builder setKey(String str) {
            this.i = str;
            return this;
        }

        public Builder setEmailAddress(String str) {
            this.k = str;
            return this;
        }

        public Builder setPhoneNumber(String str) {
            this.l = str;
            return this;
        }

        public Builder addAttribute(String str, String str2) {
            this.a.put(str, str2);
            return this;
        }

        public Builder addMediaDescription(MediaDescription mediaDescription) {
            this.b.add((ImmutableList.Builder<MediaDescription>) mediaDescription);
            return this;
        }

        public SessionDescription build() {
            if (this.d != null && this.e != null && this.f != null) {
                return new SessionDescription(this);
            }
            throw new IllegalStateException("One of more mandatory SDP fields are not set.");
        }
    }

    private SessionDescription(Builder builder) {
        this.a = ImmutableMap.copyOf(builder.a);
        this.b = builder.b.build();
        this.c = (String) Util.castNonNull(builder.d);
        this.d = (String) Util.castNonNull(builder.e);
        this.e = (String) Util.castNonNull(builder.f);
        this.g = builder.g;
        this.h = builder.h;
        this.f = builder.c;
        this.i = builder.i;
        this.j = builder.k;
        this.k = builder.l;
        this.l = builder.j;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SessionDescription sessionDescription = (SessionDescription) obj;
        return this.f == sessionDescription.f && this.a.equals(sessionDescription.a) && this.b.equals(sessionDescription.b) && this.d.equals(sessionDescription.d) && this.c.equals(sessionDescription.c) && this.e.equals(sessionDescription.e) && Util.areEqual(this.l, sessionDescription.l) && Util.areEqual(this.g, sessionDescription.g) && Util.areEqual(this.j, sessionDescription.j) && Util.areEqual(this.k, sessionDescription.k) && Util.areEqual(this.h, sessionDescription.h) && Util.areEqual(this.i, sessionDescription.i);
    }

    public int hashCode() {
        int hashCode = (((((((((((217 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.d.hashCode()) * 31) + this.c.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f) * 31;
        String str = this.l;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        Uri uri = this.g;
        int hashCode3 = (hashCode2 + (uri == null ? 0 : uri.hashCode())) * 31;
        String str2 = this.j;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.k;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.h;
        int hashCode6 = (hashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.i;
        if (str5 != null) {
            i = str5.hashCode();
        }
        return hashCode6 + i;
    }
}
