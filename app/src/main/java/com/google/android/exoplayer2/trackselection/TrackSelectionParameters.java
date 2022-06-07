package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.accessibility.CaptioningManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/* loaded from: classes2.dex */
public class TrackSelectionParameters implements Parcelable {
    public final boolean forceHighestSupportedBitrate;
    public final boolean forceLowestBitrate;
    public final int maxAudioBitrate;
    public final int maxAudioChannelCount;
    public final int maxVideoBitrate;
    public final int maxVideoFrameRate;
    public final int maxVideoHeight;
    public final int maxVideoWidth;
    public final int minVideoBitrate;
    public final int minVideoFrameRate;
    public final int minVideoHeight;
    public final int minVideoWidth;
    public final ImmutableList<String> preferredAudioLanguages;
    public final ImmutableList<String> preferredAudioMimeTypes;
    public final int preferredAudioRoleFlags;
    public final ImmutableList<String> preferredTextLanguages;
    public final int preferredTextRoleFlags;
    public final ImmutableList<String> preferredVideoMimeTypes;
    public final boolean selectUndeterminedTextLanguage;
    public final int viewportHeight;
    public final boolean viewportOrientationMayChange;
    public final int viewportWidth;
    public static final TrackSelectionParameters DEFAULT_WITHOUT_CONTEXT = new Builder().build();
    @Deprecated
    public static final TrackSelectionParameters DEFAULT = DEFAULT_WITHOUT_CONTEXT;
    public static final Parcelable.Creator<TrackSelectionParameters> CREATOR = new Parcelable.Creator<TrackSelectionParameters>() { // from class: com.google.android.exoplayer2.trackselection.TrackSelectionParameters.1
        /* renamed from: a */
        public TrackSelectionParameters createFromParcel(Parcel parcel) {
            return new TrackSelectionParameters(parcel);
        }

        /* renamed from: a */
        public TrackSelectionParameters[] newArray(int i) {
            return new TrackSelectionParameters[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;
        private int j;
        private boolean k;
        private ImmutableList<String> l;
        private ImmutableList<String> m;
        private int n;
        private int o;
        private int p;
        private ImmutableList<String> q;
        private ImmutableList<String> r;
        private int s;
        private boolean t;
        private boolean u;
        private boolean v;

        @Deprecated
        public Builder() {
            this.a = Integer.MAX_VALUE;
            this.b = Integer.MAX_VALUE;
            this.c = Integer.MAX_VALUE;
            this.d = Integer.MAX_VALUE;
            this.i = Integer.MAX_VALUE;
            this.j = Integer.MAX_VALUE;
            this.k = true;
            this.l = ImmutableList.of();
            this.m = ImmutableList.of();
            this.n = 0;
            this.o = Integer.MAX_VALUE;
            this.p = Integer.MAX_VALUE;
            this.q = ImmutableList.of();
            this.r = ImmutableList.of();
            this.s = 0;
            this.t = false;
            this.u = false;
            this.v = false;
        }

        public Builder(Context context) {
            this();
            setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            setViewportSizeToPhysicalDisplaySize(context, true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Builder(TrackSelectionParameters trackSelectionParameters) {
            this.a = trackSelectionParameters.maxVideoWidth;
            this.b = trackSelectionParameters.maxVideoHeight;
            this.c = trackSelectionParameters.maxVideoFrameRate;
            this.d = trackSelectionParameters.maxVideoBitrate;
            this.e = trackSelectionParameters.minVideoWidth;
            this.f = trackSelectionParameters.minVideoHeight;
            this.g = trackSelectionParameters.minVideoFrameRate;
            this.h = trackSelectionParameters.minVideoBitrate;
            this.i = trackSelectionParameters.viewportWidth;
            this.j = trackSelectionParameters.viewportHeight;
            this.k = trackSelectionParameters.viewportOrientationMayChange;
            this.l = trackSelectionParameters.preferredVideoMimeTypes;
            this.m = trackSelectionParameters.preferredAudioLanguages;
            this.n = trackSelectionParameters.preferredAudioRoleFlags;
            this.o = trackSelectionParameters.maxAudioChannelCount;
            this.p = trackSelectionParameters.maxAudioBitrate;
            this.q = trackSelectionParameters.preferredAudioMimeTypes;
            this.r = trackSelectionParameters.preferredTextLanguages;
            this.s = trackSelectionParameters.preferredTextRoleFlags;
            this.t = trackSelectionParameters.selectUndeterminedTextLanguage;
            this.u = trackSelectionParameters.forceLowestBitrate;
            this.v = trackSelectionParameters.forceHighestSupportedBitrate;
        }

        public Builder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, 719);
        }

        public Builder clearVideoSizeConstraints() {
            return setMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public Builder setMaxVideoSize(int i, int i2) {
            this.a = i;
            this.b = i2;
            return this;
        }

        public Builder setMaxVideoFrameRate(int i) {
            this.c = i;
            return this;
        }

        public Builder setMaxVideoBitrate(int i) {
            this.d = i;
            return this;
        }

        public Builder setMinVideoSize(int i, int i2) {
            this.e = i;
            this.f = i2;
            return this;
        }

        public Builder setMinVideoFrameRate(int i) {
            this.g = i;
            return this;
        }

        public Builder setMinVideoBitrate(int i) {
            this.h = i;
            return this;
        }

        public Builder setViewportSizeToPhysicalDisplaySize(Context context, boolean z) {
            Point currentDisplayModeSize = Util.getCurrentDisplayModeSize(context);
            return setViewportSize(currentDisplayModeSize.x, currentDisplayModeSize.y, z);
        }

        public Builder clearViewportSizeConstraints() {
            return setViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public Builder setViewportSize(int i, int i2, boolean z) {
            this.i = i;
            this.j = i2;
            this.k = z;
            return this;
        }

        public Builder setPreferredVideoMimeType(@Nullable String str) {
            return str == null ? setPreferredVideoMimeTypes(new String[0]) : setPreferredVideoMimeTypes(str);
        }

        public Builder setPreferredVideoMimeTypes(String... strArr) {
            this.l = ImmutableList.copyOf(strArr);
            return this;
        }

        public Builder setPreferredAudioLanguage(@Nullable String str) {
            return str == null ? setPreferredAudioLanguages(new String[0]) : setPreferredAudioLanguages(str);
        }

        public Builder setPreferredAudioLanguages(String... strArr) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String str : (String[]) Assertions.checkNotNull(strArr)) {
                builder.add((ImmutableList.Builder) Util.normalizeLanguageCode((String) Assertions.checkNotNull(str)));
            }
            this.m = builder.build();
            return this;
        }

        public Builder setPreferredAudioRoleFlags(int i) {
            this.n = i;
            return this;
        }

        public Builder setMaxAudioChannelCount(int i) {
            this.o = i;
            return this;
        }

        public Builder setMaxAudioBitrate(int i) {
            this.p = i;
            return this;
        }

        public Builder setPreferredAudioMimeType(@Nullable String str) {
            return str == null ? setPreferredAudioMimeTypes(new String[0]) : setPreferredAudioMimeTypes(str);
        }

        public Builder setPreferredAudioMimeTypes(String... strArr) {
            this.q = ImmutableList.copyOf(strArr);
            return this;
        }

        public Builder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            if (Util.SDK_INT >= 19) {
                a(context);
            }
            return this;
        }

        public Builder setPreferredTextLanguage(@Nullable String str) {
            return str == null ? setPreferredTextLanguages(new String[0]) : setPreferredTextLanguages(str);
        }

        public Builder setPreferredTextLanguages(String... strArr) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String str : (String[]) Assertions.checkNotNull(strArr)) {
                builder.add((ImmutableList.Builder) Util.normalizeLanguageCode((String) Assertions.checkNotNull(str)));
            }
            this.r = builder.build();
            return this;
        }

        public Builder setPreferredTextRoleFlags(int i) {
            this.s = i;
            return this;
        }

        public Builder setSelectUndeterminedTextLanguage(boolean z) {
            this.t = z;
            return this;
        }

        public Builder setForceLowestBitrate(boolean z) {
            this.u = z;
            return this;
        }

        public Builder setForceHighestSupportedBitrate(boolean z) {
            this.v = z;
            return this;
        }

        public TrackSelectionParameters build() {
            return new TrackSelectionParameters(this);
        }

        @RequiresApi(19)
        private void a(Context context) {
            CaptioningManager captioningManager;
            if ((Util.SDK_INT >= 23 || Looper.myLooper() != null) && (captioningManager = (CaptioningManager) context.getSystemService("captioning")) != null && captioningManager.isEnabled()) {
                this.s = 1088;
                Locale locale = captioningManager.getLocale();
                if (locale != null) {
                    this.r = ImmutableList.of(Util.getLocaleLanguageTag(locale));
                }
            }
        }
    }

    public static TrackSelectionParameters getDefaults(Context context) {
        return new Builder(context).build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TrackSelectionParameters(Builder builder) {
        this.maxVideoWidth = builder.a;
        this.maxVideoHeight = builder.b;
        this.maxVideoFrameRate = builder.c;
        this.maxVideoBitrate = builder.d;
        this.minVideoWidth = builder.e;
        this.minVideoHeight = builder.f;
        this.minVideoFrameRate = builder.g;
        this.minVideoBitrate = builder.h;
        this.viewportWidth = builder.i;
        this.viewportHeight = builder.j;
        this.viewportOrientationMayChange = builder.k;
        this.preferredVideoMimeTypes = builder.l;
        this.preferredAudioLanguages = builder.m;
        this.preferredAudioRoleFlags = builder.n;
        this.maxAudioChannelCount = builder.o;
        this.maxAudioBitrate = builder.p;
        this.preferredAudioMimeTypes = builder.q;
        this.preferredTextLanguages = builder.r;
        this.preferredTextRoleFlags = builder.s;
        this.selectUndeterminedTextLanguage = builder.t;
        this.forceLowestBitrate = builder.u;
        this.forceHighestSupportedBitrate = builder.v;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TrackSelectionParameters(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readList(arrayList, null);
        this.preferredAudioLanguages = ImmutableList.copyOf((Collection) arrayList);
        this.preferredAudioRoleFlags = parcel.readInt();
        ArrayList arrayList2 = new ArrayList();
        parcel.readList(arrayList2, null);
        this.preferredTextLanguages = ImmutableList.copyOf((Collection) arrayList2);
        this.preferredTextRoleFlags = parcel.readInt();
        this.selectUndeterminedTextLanguage = Util.readBoolean(parcel);
        this.maxVideoWidth = parcel.readInt();
        this.maxVideoHeight = parcel.readInt();
        this.maxVideoFrameRate = parcel.readInt();
        this.maxVideoBitrate = parcel.readInt();
        this.minVideoWidth = parcel.readInt();
        this.minVideoHeight = parcel.readInt();
        this.minVideoFrameRate = parcel.readInt();
        this.minVideoBitrate = parcel.readInt();
        this.viewportWidth = parcel.readInt();
        this.viewportHeight = parcel.readInt();
        this.viewportOrientationMayChange = Util.readBoolean(parcel);
        ArrayList arrayList3 = new ArrayList();
        parcel.readList(arrayList3, null);
        this.preferredVideoMimeTypes = ImmutableList.copyOf((Collection) arrayList3);
        this.maxAudioChannelCount = parcel.readInt();
        this.maxAudioBitrate = parcel.readInt();
        ArrayList arrayList4 = new ArrayList();
        parcel.readList(arrayList4, null);
        this.preferredAudioMimeTypes = ImmutableList.copyOf((Collection) arrayList4);
        this.forceLowestBitrate = Util.readBoolean(parcel);
        this.forceHighestSupportedBitrate = Util.readBoolean(parcel);
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackSelectionParameters trackSelectionParameters = (TrackSelectionParameters) obj;
        return this.maxVideoWidth == trackSelectionParameters.maxVideoWidth && this.maxVideoHeight == trackSelectionParameters.maxVideoHeight && this.maxVideoFrameRate == trackSelectionParameters.maxVideoFrameRate && this.maxVideoBitrate == trackSelectionParameters.maxVideoBitrate && this.minVideoWidth == trackSelectionParameters.minVideoWidth && this.minVideoHeight == trackSelectionParameters.minVideoHeight && this.minVideoFrameRate == trackSelectionParameters.minVideoFrameRate && this.minVideoBitrate == trackSelectionParameters.minVideoBitrate && this.viewportOrientationMayChange == trackSelectionParameters.viewportOrientationMayChange && this.viewportWidth == trackSelectionParameters.viewportWidth && this.viewportHeight == trackSelectionParameters.viewportHeight && this.preferredVideoMimeTypes.equals(trackSelectionParameters.preferredVideoMimeTypes) && this.preferredAudioLanguages.equals(trackSelectionParameters.preferredAudioLanguages) && this.preferredAudioRoleFlags == trackSelectionParameters.preferredAudioRoleFlags && this.maxAudioChannelCount == trackSelectionParameters.maxAudioChannelCount && this.maxAudioBitrate == trackSelectionParameters.maxAudioBitrate && this.preferredAudioMimeTypes.equals(trackSelectionParameters.preferredAudioMimeTypes) && this.preferredTextLanguages.equals(trackSelectionParameters.preferredTextLanguages) && this.preferredTextRoleFlags == trackSelectionParameters.preferredTextRoleFlags && this.selectUndeterminedTextLanguage == trackSelectionParameters.selectUndeterminedTextLanguage && this.forceLowestBitrate == trackSelectionParameters.forceLowestBitrate && this.forceHighestSupportedBitrate == trackSelectionParameters.forceHighestSupportedBitrate;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((((this.maxVideoWidth + 31) * 31) + this.maxVideoHeight) * 31) + this.maxVideoFrameRate) * 31) + this.maxVideoBitrate) * 31) + this.minVideoWidth) * 31) + this.minVideoHeight) * 31) + this.minVideoFrameRate) * 31) + this.minVideoBitrate) * 31) + (this.viewportOrientationMayChange ? 1 : 0)) * 31) + this.viewportWidth) * 31) + this.viewportHeight) * 31) + this.preferredVideoMimeTypes.hashCode()) * 31) + this.preferredAudioLanguages.hashCode()) * 31) + this.preferredAudioRoleFlags) * 31) + this.maxAudioChannelCount) * 31) + this.maxAudioBitrate) * 31) + this.preferredAudioMimeTypes.hashCode()) * 31) + this.preferredTextLanguages.hashCode()) * 31) + this.preferredTextRoleFlags) * 31) + (this.selectUndeterminedTextLanguage ? 1 : 0)) * 31) + (this.forceLowestBitrate ? 1 : 0)) * 31) + (this.forceHighestSupportedBitrate ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.preferredAudioLanguages);
        parcel.writeInt(this.preferredAudioRoleFlags);
        parcel.writeList(this.preferredTextLanguages);
        parcel.writeInt(this.preferredTextRoleFlags);
        Util.writeBoolean(parcel, this.selectUndeterminedTextLanguage);
        parcel.writeInt(this.maxVideoWidth);
        parcel.writeInt(this.maxVideoHeight);
        parcel.writeInt(this.maxVideoFrameRate);
        parcel.writeInt(this.maxVideoBitrate);
        parcel.writeInt(this.minVideoWidth);
        parcel.writeInt(this.minVideoHeight);
        parcel.writeInt(this.minVideoFrameRate);
        parcel.writeInt(this.minVideoBitrate);
        parcel.writeInt(this.viewportWidth);
        parcel.writeInt(this.viewportHeight);
        Util.writeBoolean(parcel, this.viewportOrientationMayChange);
        parcel.writeList(this.preferredVideoMimeTypes);
        parcel.writeInt(this.maxAudioChannelCount);
        parcel.writeInt(this.maxAudioBitrate);
        parcel.writeList(this.preferredAudioMimeTypes);
        Util.writeBoolean(parcel, this.forceLowestBitrate);
        Util.writeBoolean(parcel, this.forceHighestSupportedBitrate);
    }
}
