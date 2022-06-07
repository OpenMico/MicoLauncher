package com.google.android.exoplayer2.source.dash.manifest;

import androidx.annotation.Nullable;
import com.google.common.base.Objects;

/* loaded from: classes2.dex */
public final class BaseUrl {
    public static final int DEFAULT_PRIORITY = 1;
    public static final int DEFAULT_WEIGHT = 1;
    public final int priority;
    public final String serviceLocation;
    public final String url;
    public final int weight;

    public BaseUrl(String str) {
        this(str, str, 1, 1);
    }

    public BaseUrl(String str, String str2, int i, int i2) {
        this.url = str;
        this.serviceLocation = str2;
        this.priority = i;
        this.weight = i2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseUrl)) {
            return false;
        }
        BaseUrl baseUrl = (BaseUrl) obj;
        return this.priority == baseUrl.priority && this.weight == baseUrl.weight && Objects.equal(this.url, baseUrl.url) && Objects.equal(this.serviceLocation, baseUrl.serviceLocation);
    }

    public int hashCode() {
        return Objects.hashCode(this.url, this.serviceLocation, Integer.valueOf(this.priority), Integer.valueOf(this.weight));
    }
}
