package com.google.android.exoplayer2.audio;

import android.media.AudioAttributes;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class AudioAttributes implements Bundleable {
    @Nullable
    private android.media.AudioAttributes a;
    public final int allowedCapturePolicy;
    public final int contentType;
    public final int flags;
    public final int usage;
    public static final AudioAttributes DEFAULT = new Builder().build();
    public static final Bundleable.Creator<AudioAttributes> CREATOR = $$Lambda$AudioAttributes$jKKY4R_5rh4LBh2lb2xoG1XFAfQ.INSTANCE;

    /* loaded from: classes.dex */
    public static final class Builder {
        private int a = 0;
        private int b = 0;
        private int c = 1;
        private int d = 1;

        public Builder setContentType(int i) {
            this.a = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.b = i;
            return this;
        }

        public Builder setUsage(int i) {
            this.c = i;
            return this;
        }

        public Builder setAllowedCapturePolicy(int i) {
            this.d = i;
            return this;
        }

        public AudioAttributes build() {
            return new AudioAttributes(this.a, this.b, this.c, this.d);
        }
    }

    private AudioAttributes(int i, int i2, int i3, int i4) {
        this.contentType = i;
        this.flags = i2;
        this.usage = i3;
        this.allowedCapturePolicy = i4;
    }

    @RequiresApi(21)
    public android.media.AudioAttributes getAudioAttributesV21() {
        if (this.a == null) {
            AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(this.contentType).setFlags(this.flags).setUsage(this.usage);
            if (Util.SDK_INT >= 29) {
                usage.setAllowedCapturePolicy(this.allowedCapturePolicy);
            }
            this.a = usage.build();
        }
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioAttributes audioAttributes = (AudioAttributes) obj;
        return this.contentType == audioAttributes.contentType && this.flags == audioAttributes.flags && this.usage == audioAttributes.usage && this.allowedCapturePolicy == audioAttributes.allowedCapturePolicy;
    }

    public int hashCode() {
        return ((((((527 + this.contentType) * 31) + this.flags) * 31) + this.usage) * 31) + this.allowedCapturePolicy;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), this.contentType);
        bundle.putInt(a(1), this.flags);
        bundle.putInt(a(2), this.usage);
        bundle.putInt(a(3), this.allowedCapturePolicy);
        return bundle;
    }

    public static /* synthetic */ AudioAttributes a(Bundle bundle) {
        Builder builder = new Builder();
        if (bundle.containsKey(a(0))) {
            builder.setContentType(bundle.getInt(a(0)));
        }
        if (bundle.containsKey(a(1))) {
            builder.setFlags(bundle.getInt(a(1)));
        }
        if (bundle.containsKey(a(2))) {
            builder.setUsage(bundle.getInt(a(2)));
        }
        if (bundle.containsKey(a(3))) {
            builder.setAllowedCapturePolicy(bundle.getInt(a(3)));
        }
        return builder.build();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
