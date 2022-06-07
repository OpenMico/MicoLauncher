package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class RtspRequest {
    public final Uri a;
    public final int b;
    public final RtspHeaders c;
    public final String d;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Method {
    }

    public RtspRequest(Uri uri, int i, RtspHeaders rtspHeaders, String str) {
        this.a = uri;
        this.b = i;
        this.c = rtspHeaders;
        this.d = str;
    }
}
