package com.google.android.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.UnsupportedMediaCrypto;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Joiner;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class Format implements Parcelable {
    public static final Parcelable.Creator<Format> CREATOR = new Parcelable.Creator<Format>() { // from class: com.google.android.exoplayer2.Format.1
        /* renamed from: a */
        public Format createFromParcel(Parcel parcel) {
            return new Format(parcel);
        }

        /* renamed from: a */
        public Format[] newArray(int i) {
            return new Format[i];
        }
    };
    public static final int NO_VALUE = -1;
    public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;
    private int a;
    public final int accessibilityChannel;
    public final int averageBitrate;
    public final int bitrate;
    public final int channelCount;
    @Nullable
    public final String codecs;
    @Nullable
    public final ColorInfo colorInfo;
    @Nullable
    public final String containerMimeType;
    @Nullable
    public final DrmInitData drmInitData;
    public final int encoderDelay;
    public final int encoderPadding;
    @Nullable
    public final Class<? extends ExoMediaCrypto> exoMediaCryptoType;
    public final float frameRate;
    public final int height;
    @Nullable
    public final String id;
    public final List<byte[]> initializationData;
    @Nullable
    public final String label;
    @Nullable
    public final String language;
    public final int maxInputSize;
    @Nullable
    public final Metadata metadata;
    public final int pcmEncoding;
    public final int peakBitrate;
    public final float pixelWidthHeightRatio;
    @Nullable
    public final byte[] projectionData;
    public final int roleFlags;
    public final int rotationDegrees;
    @Nullable
    public final String sampleMimeType;
    public final int sampleRate;
    public final int selectionFlags;
    public final int stereoMode;
    public final long subsampleOffsetUs;
    public final int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private int A;
        private int B;
        private int C;
        @Nullable
        private Class<? extends ExoMediaCrypto> D;
        @Nullable
        private String a;
        @Nullable
        private String b;
        @Nullable
        private String c;
        private int d;
        private int e;
        private int f;
        private int g;
        @Nullable
        private String h;
        @Nullable
        private Metadata i;
        @Nullable
        private String j;
        @Nullable
        private String k;
        private int l;
        @Nullable
        private List<byte[]> m;
        @Nullable
        private DrmInitData n;
        private long o;
        private int p;
        private int q;
        private float r;
        private int s;
        private float t;
        @Nullable
        private byte[] u;
        private int v;
        @Nullable
        private ColorInfo w;
        private int x;
        private int y;
        private int z;

        public Builder() {
            this.f = -1;
            this.g = -1;
            this.l = -1;
            this.o = Long.MAX_VALUE;
            this.p = -1;
            this.q = -1;
            this.r = -1.0f;
            this.t = 1.0f;
            this.v = -1;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.C = -1;
        }

        private Builder(Format format) {
            this.a = format.id;
            this.b = format.label;
            this.c = format.language;
            this.d = format.selectionFlags;
            this.e = format.roleFlags;
            this.f = format.averageBitrate;
            this.g = format.peakBitrate;
            this.h = format.codecs;
            this.i = format.metadata;
            this.j = format.containerMimeType;
            this.k = format.sampleMimeType;
            this.l = format.maxInputSize;
            this.m = format.initializationData;
            this.n = format.drmInitData;
            this.o = format.subsampleOffsetUs;
            this.p = format.width;
            this.q = format.height;
            this.r = format.frameRate;
            this.s = format.rotationDegrees;
            this.t = format.pixelWidthHeightRatio;
            this.u = format.projectionData;
            this.v = format.stereoMode;
            this.w = format.colorInfo;
            this.x = format.channelCount;
            this.y = format.sampleRate;
            this.z = format.pcmEncoding;
            this.A = format.encoderDelay;
            this.B = format.encoderPadding;
            this.C = format.accessibilityChannel;
            this.D = format.exoMediaCryptoType;
        }

        public Builder setId(@Nullable String str) {
            this.a = str;
            return this;
        }

        public Builder setId(int i) {
            this.a = Integer.toString(i);
            return this;
        }

        public Builder setLabel(@Nullable String str) {
            this.b = str;
            return this;
        }

        public Builder setLanguage(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setSelectionFlags(int i) {
            this.d = i;
            return this;
        }

        public Builder setRoleFlags(int i) {
            this.e = i;
            return this;
        }

        public Builder setAverageBitrate(int i) {
            this.f = i;
            return this;
        }

        public Builder setPeakBitrate(int i) {
            this.g = i;
            return this;
        }

        public Builder setCodecs(@Nullable String str) {
            this.h = str;
            return this;
        }

        public Builder setMetadata(@Nullable Metadata metadata) {
            this.i = metadata;
            return this;
        }

        public Builder setContainerMimeType(@Nullable String str) {
            this.j = str;
            return this;
        }

        public Builder setSampleMimeType(@Nullable String str) {
            this.k = str;
            return this;
        }

        public Builder setMaxInputSize(int i) {
            this.l = i;
            return this;
        }

        public Builder setInitializationData(@Nullable List<byte[]> list) {
            this.m = list;
            return this;
        }

        public Builder setDrmInitData(@Nullable DrmInitData drmInitData) {
            this.n = drmInitData;
            return this;
        }

        public Builder setSubsampleOffsetUs(long j) {
            this.o = j;
            return this;
        }

        public Builder setWidth(int i) {
            this.p = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.q = i;
            return this;
        }

        public Builder setFrameRate(float f) {
            this.r = f;
            return this;
        }

        public Builder setRotationDegrees(int i) {
            this.s = i;
            return this;
        }

        public Builder setPixelWidthHeightRatio(float f) {
            this.t = f;
            return this;
        }

        public Builder setProjectionData(@Nullable byte[] bArr) {
            this.u = bArr;
            return this;
        }

        public Builder setStereoMode(int i) {
            this.v = i;
            return this;
        }

        public Builder setColorInfo(@Nullable ColorInfo colorInfo) {
            this.w = colorInfo;
            return this;
        }

        public Builder setChannelCount(int i) {
            this.x = i;
            return this;
        }

        public Builder setSampleRate(int i) {
            this.y = i;
            return this;
        }

        public Builder setPcmEncoding(int i) {
            this.z = i;
            return this;
        }

        public Builder setEncoderDelay(int i) {
            this.A = i;
            return this;
        }

        public Builder setEncoderPadding(int i) {
            this.B = i;
            return this;
        }

        public Builder setAccessibilityChannel(int i) {
            this.C = i;
            return this;
        }

        public Builder setExoMediaCryptoType(@Nullable Class<? extends ExoMediaCrypto> cls) {
            this.D = cls;
            return this;
        }

        public Format build() {
            return new Format(this);
        }
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, float f, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i).setPeakBitrate(i).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i2).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i3).setHeight(i4).setFrameRate(f).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, float f, @Nullable List<byte[]> list, int i5, float f2, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i).setPeakBitrate(i).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i2).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i3).setHeight(i4).setFrameRate(f).setRotationDegrees(i5).setPixelWidthHeightRatio(f2).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i5, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i5).setAverageBitrate(i).setPeakBitrate(i).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i2).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i3).setSampleRate(i4).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, int i5, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i6, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i6).setAverageBitrate(i).setPeakBitrate(i).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i2).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i3).setSampleRate(i4).setPcmEncoding(i5).build();
    }

    @Deprecated
    public static Format createContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, int i3, @Nullable String str6) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i2).setRoleFlags(i3).setAverageBitrate(i).setPeakBitrate(i).setCodecs(str5).setContainerMimeType(str3).setSampleMimeType(str4).build();
    }

    @Deprecated
    public static Format createSampleFormat(@Nullable String str, @Nullable String str2) {
        return new Builder().setId(str).setSampleMimeType(str2).build();
    }

    private Format(Builder builder) {
        this.id = builder.a;
        this.label = builder.b;
        this.language = Util.normalizeLanguageCode(builder.c);
        this.selectionFlags = builder.d;
        this.roleFlags = builder.e;
        this.averageBitrate = builder.f;
        this.peakBitrate = builder.g;
        int i = this.peakBitrate;
        this.bitrate = i == -1 ? this.averageBitrate : i;
        this.codecs = builder.h;
        this.metadata = builder.i;
        this.containerMimeType = builder.j;
        this.sampleMimeType = builder.k;
        this.maxInputSize = builder.l;
        this.initializationData = builder.m == null ? Collections.emptyList() : builder.m;
        this.drmInitData = builder.n;
        this.subsampleOffsetUs = builder.o;
        this.width = builder.p;
        this.height = builder.q;
        this.frameRate = builder.r;
        int i2 = 0;
        this.rotationDegrees = builder.s == -1 ? 0 : builder.s;
        this.pixelWidthHeightRatio = builder.t == -1.0f ? 1.0f : builder.t;
        this.projectionData = builder.u;
        this.stereoMode = builder.v;
        this.colorInfo = builder.w;
        this.channelCount = builder.x;
        this.sampleRate = builder.y;
        this.pcmEncoding = builder.z;
        this.encoderDelay = builder.A == -1 ? 0 : builder.A;
        this.encoderPadding = builder.B != -1 ? builder.B : i2;
        this.accessibilityChannel = builder.C;
        if (builder.D != null || this.drmInitData == null) {
            this.exoMediaCryptoType = builder.D;
        } else {
            this.exoMediaCryptoType = UnsupportedMediaCrypto.class;
        }
    }

    Format(Parcel parcel) {
        this.id = parcel.readString();
        this.label = parcel.readString();
        this.language = parcel.readString();
        this.selectionFlags = parcel.readInt();
        this.roleFlags = parcel.readInt();
        this.averageBitrate = parcel.readInt();
        this.peakBitrate = parcel.readInt();
        int i = this.peakBitrate;
        this.bitrate = i == -1 ? this.averageBitrate : i;
        this.codecs = parcel.readString();
        this.metadata = (Metadata) parcel.readParcelable(Metadata.class.getClassLoader());
        this.containerMimeType = parcel.readString();
        this.sampleMimeType = parcel.readString();
        this.maxInputSize = parcel.readInt();
        int readInt = parcel.readInt();
        this.initializationData = new ArrayList(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            this.initializationData.add((byte[]) Assertions.checkNotNull(parcel.createByteArray()));
        }
        this.drmInitData = (DrmInitData) parcel.readParcelable(DrmInitData.class.getClassLoader());
        this.subsampleOffsetUs = parcel.readLong();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.frameRate = parcel.readFloat();
        this.rotationDegrees = parcel.readInt();
        this.pixelWidthHeightRatio = parcel.readFloat();
        Class cls = null;
        this.projectionData = Util.readBoolean(parcel) ? parcel.createByteArray() : null;
        this.stereoMode = parcel.readInt();
        this.colorInfo = (ColorInfo) parcel.readParcelable(ColorInfo.class.getClassLoader());
        this.channelCount = parcel.readInt();
        this.sampleRate = parcel.readInt();
        this.pcmEncoding = parcel.readInt();
        this.encoderDelay = parcel.readInt();
        this.encoderPadding = parcel.readInt();
        this.accessibilityChannel = parcel.readInt();
        this.exoMediaCryptoType = this.drmInitData != null ? UnsupportedMediaCrypto.class : cls;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    @Deprecated
    public Format copyWithMaxInputSize(int i) {
        return buildUpon().setMaxInputSize(i).build();
    }

    @Deprecated
    public Format copyWithSubsampleOffsetUs(long j) {
        return buildUpon().setSubsampleOffsetUs(j).build();
    }

    @Deprecated
    public Format copyWithLabel(@Nullable String str) {
        return buildUpon().setLabel(str).build();
    }

    @Deprecated
    public Format copyWithManifestFormatInfo(Format format) {
        return withManifestFormatInfo(format);
    }

    public Format withManifestFormatInfo(Format format) {
        Metadata metadata;
        String str;
        if (this == format) {
            return this;
        }
        int trackType = MimeTypes.getTrackType(this.sampleMimeType);
        String str2 = format.id;
        String str3 = format.label;
        if (str3 == null) {
            str3 = this.label;
        }
        String str4 = this.language;
        if ((trackType == 3 || trackType == 1) && (str = format.language) != null) {
            str4 = str;
        }
        int i = this.averageBitrate;
        if (i == -1) {
            i = format.averageBitrate;
        }
        int i2 = this.peakBitrate;
        if (i2 == -1) {
            i2 = format.peakBitrate;
        }
        String str5 = this.codecs;
        if (str5 == null) {
            String codecsOfType = Util.getCodecsOfType(format.codecs, trackType);
            if (Util.splitCodecs(codecsOfType).length == 1) {
                str5 = codecsOfType;
            }
        }
        Metadata metadata2 = this.metadata;
        if (metadata2 == null) {
            metadata = format.metadata;
        } else {
            metadata = metadata2.copyWithAppendedEntriesFrom(format.metadata);
        }
        float f = this.frameRate;
        if (f == -1.0f && trackType == 2) {
            f = format.frameRate;
        }
        int i3 = this.selectionFlags | format.selectionFlags;
        return buildUpon().setId(str2).setLabel(str3).setLanguage(str4).setSelectionFlags(i3).setRoleFlags(this.roleFlags | format.roleFlags).setAverageBitrate(i).setPeakBitrate(i2).setCodecs(str5).setMetadata(metadata).setDrmInitData(DrmInitData.createSessionCreationData(format.drmInitData, this.drmInitData)).setFrameRate(f).build();
    }

    @Deprecated
    public Format copyWithGaplessInfo(int i, int i2) {
        return buildUpon().setEncoderDelay(i).setEncoderPadding(i2).build();
    }

    @Deprecated
    public Format copyWithFrameRate(float f) {
        return buildUpon().setFrameRate(f).build();
    }

    @Deprecated
    public Format copyWithDrmInitData(@Nullable DrmInitData drmInitData) {
        return buildUpon().setDrmInitData(drmInitData).build();
    }

    @Deprecated
    public Format copyWithMetadata(@Nullable Metadata metadata) {
        return buildUpon().setMetadata(metadata).build();
    }

    @Deprecated
    public Format copyWithBitrate(int i) {
        return buildUpon().setAverageBitrate(i).setPeakBitrate(i).build();
    }

    @Deprecated
    public Format copyWithVideoSize(int i, int i2) {
        return buildUpon().setWidth(i).setHeight(i2).build();
    }

    public Format copyWithExoMediaCryptoType(@Nullable Class<? extends ExoMediaCrypto> cls) {
        return buildUpon().setExoMediaCryptoType(cls).build();
    }

    public int getPixelCount() {
        int i;
        int i2 = this.width;
        if (i2 == -1 || (i = this.height) == -1) {
            return -1;
        }
        return i2 * i;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.label;
        String str3 = this.containerMimeType;
        String str4 = this.sampleMimeType;
        String str5 = this.codecs;
        int i = this.bitrate;
        String str6 = this.language;
        int i2 = this.width;
        int i3 = this.height;
        float f = this.frameRate;
        int i4 = this.channelCount;
        int i5 = this.sampleRate;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 104 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str6).length());
        sb.append("Format(");
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        sb.append(", ");
        sb.append(str3);
        sb.append(", ");
        sb.append(str4);
        sb.append(", ");
        sb.append(str5);
        sb.append(", ");
        sb.append(i);
        sb.append(", ");
        sb.append(str6);
        sb.append(", [");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        sb.append(", ");
        sb.append(f);
        sb.append("], [");
        sb.append(i4);
        sb.append(", ");
        sb.append(i5);
        sb.append("])");
        return sb.toString();
    }

    public int hashCode() {
        if (this.a == 0) {
            String str = this.id;
            int i = 0;
            int hashCode = (527 + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.label;
            int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.language;
            int hashCode3 = (((((((((hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31) + this.averageBitrate) * 31) + this.peakBitrate) * 31;
            String str4 = this.codecs;
            int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            Metadata metadata = this.metadata;
            int hashCode5 = (hashCode4 + (metadata == null ? 0 : metadata.hashCode())) * 31;
            String str5 = this.containerMimeType;
            int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
            String str6 = this.sampleMimeType;
            int hashCode7 = (((((((((((((((((((((((((((((hashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31) + this.maxInputSize) * 31) + ((int) this.subsampleOffsetUs)) * 31) + this.width) * 31) + this.height) * 31) + Float.floatToIntBits(this.frameRate)) * 31) + this.rotationDegrees) * 31) + Float.floatToIntBits(this.pixelWidthHeightRatio)) * 31) + this.stereoMode) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + this.pcmEncoding) * 31) + this.encoderDelay) * 31) + this.encoderPadding) * 31) + this.accessibilityChannel) * 31;
            Class<? extends ExoMediaCrypto> cls = this.exoMediaCryptoType;
            if (cls != null) {
                i = cls.hashCode();
            }
            this.a = hashCode7 + i;
        }
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Format format = (Format) obj;
        int i2 = this.a;
        if (i2 == 0 || (i = format.a) == 0 || i2 == i) {
            return this.selectionFlags == format.selectionFlags && this.roleFlags == format.roleFlags && this.averageBitrate == format.averageBitrate && this.peakBitrate == format.peakBitrate && this.maxInputSize == format.maxInputSize && this.subsampleOffsetUs == format.subsampleOffsetUs && this.width == format.width && this.height == format.height && this.rotationDegrees == format.rotationDegrees && this.stereoMode == format.stereoMode && this.channelCount == format.channelCount && this.sampleRate == format.sampleRate && this.pcmEncoding == format.pcmEncoding && this.encoderDelay == format.encoderDelay && this.encoderPadding == format.encoderPadding && this.accessibilityChannel == format.accessibilityChannel && Float.compare(this.frameRate, format.frameRate) == 0 && Float.compare(this.pixelWidthHeightRatio, format.pixelWidthHeightRatio) == 0 && Util.areEqual(this.exoMediaCryptoType, format.exoMediaCryptoType) && Util.areEqual(this.id, format.id) && Util.areEqual(this.label, format.label) && Util.areEqual(this.codecs, format.codecs) && Util.areEqual(this.containerMimeType, format.containerMimeType) && Util.areEqual(this.sampleMimeType, format.sampleMimeType) && Util.areEqual(this.language, format.language) && Arrays.equals(this.projectionData, format.projectionData) && Util.areEqual(this.metadata, format.metadata) && Util.areEqual(this.colorInfo, format.colorInfo) && Util.areEqual(this.drmInitData, format.drmInitData) && initializationDataEquals(format);
        }
        return false;
    }

    public boolean initializationDataEquals(Format format) {
        if (this.initializationData.size() != format.initializationData.size()) {
            return false;
        }
        for (int i = 0; i < this.initializationData.size(); i++) {
            if (!Arrays.equals(this.initializationData.get(i), format.initializationData.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String toLogString(@Nullable Format format) {
        if (format == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("id=");
        sb.append(format.id);
        sb.append(", mimeType=");
        sb.append(format.sampleMimeType);
        if (format.bitrate != -1) {
            sb.append(", bitrate=");
            sb.append(format.bitrate);
        }
        if (format.codecs != null) {
            sb.append(", codecs=");
            sb.append(format.codecs);
        }
        if (format.drmInitData != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i = 0; i < format.drmInitData.schemeDataCount; i++) {
                UUID uuid = format.drmInitData.get(i).uuid;
                if (uuid.equals(C.COMMON_PSSH_UUID)) {
                    linkedHashSet.add(C.CENC_TYPE_cenc);
                } else if (uuid.equals(C.CLEARKEY_UUID)) {
                    linkedHashSet.add("clearkey");
                } else if (uuid.equals(C.PLAYREADY_UUID)) {
                    linkedHashSet.add("playready");
                } else if (uuid.equals(C.WIDEVINE_UUID)) {
                    linkedHashSet.add("widevine");
                } else if (uuid.equals(C.UUID_NIL)) {
                    linkedHashSet.add("universal");
                } else {
                    String valueOf = String.valueOf(uuid);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 10);
                    sb2.append("unknown (");
                    sb2.append(valueOf);
                    sb2.append(")");
                    linkedHashSet.add(sb2.toString());
                }
            }
            sb.append(", drm=[");
            sb.append(Joiner.on((char) StringUtil.COMMA).join(linkedHashSet));
            sb.append(']');
        }
        if (!(format.width == -1 || format.height == -1)) {
            sb.append(", res=");
            sb.append(format.width);
            sb.append("x");
            sb.append(format.height);
        }
        if (format.frameRate != -1.0f) {
            sb.append(", fps=");
            sb.append(format.frameRate);
        }
        if (format.channelCount != -1) {
            sb.append(", channels=");
            sb.append(format.channelCount);
        }
        if (format.sampleRate != -1) {
            sb.append(", sample_rate=");
            sb.append(format.sampleRate);
        }
        if (format.language != null) {
            sb.append(", language=");
            sb.append(format.language);
        }
        if (format.label != null) {
            sb.append(", label=");
            sb.append(format.label);
        }
        if ((format.roleFlags & 16384) != 0) {
            sb.append(", trick-play-track");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.label);
        parcel.writeString(this.language);
        parcel.writeInt(this.selectionFlags);
        parcel.writeInt(this.roleFlags);
        parcel.writeInt(this.averageBitrate);
        parcel.writeInt(this.peakBitrate);
        parcel.writeString(this.codecs);
        boolean z = false;
        parcel.writeParcelable(this.metadata, 0);
        parcel.writeString(this.containerMimeType);
        parcel.writeString(this.sampleMimeType);
        parcel.writeInt(this.maxInputSize);
        int size = this.initializationData.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeByteArray(this.initializationData.get(i2));
        }
        parcel.writeParcelable(this.drmInitData, 0);
        parcel.writeLong(this.subsampleOffsetUs);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeFloat(this.frameRate);
        parcel.writeInt(this.rotationDegrees);
        parcel.writeFloat(this.pixelWidthHeightRatio);
        if (this.projectionData != null) {
            z = true;
        }
        Util.writeBoolean(parcel, z);
        byte[] bArr = this.projectionData;
        if (bArr != null) {
            parcel.writeByteArray(bArr);
        }
        parcel.writeInt(this.stereoMode);
        parcel.writeParcelable(this.colorInfo, i);
        parcel.writeInt(this.channelCount);
        parcel.writeInt(this.sampleRate);
        parcel.writeInt(this.pcmEncoding);
        parcel.writeInt(this.encoderDelay);
        parcel.writeInt(this.encoderPadding);
        parcel.writeInt(this.accessibilityChannel);
    }
}
