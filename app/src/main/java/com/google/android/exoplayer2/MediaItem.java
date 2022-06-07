package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class MediaItem implements Bundleable {
    public static final String DEFAULT_MEDIA_ID = "";
    public final ClippingProperties clippingProperties;
    public final LiveConfiguration liveConfiguration;
    public final String mediaId;
    public final MediaMetadata mediaMetadata;
    @Nullable
    public final PlaybackProperties playbackProperties;
    public static final MediaItem EMPTY = new Builder().build();
    public static final Bundleable.Creator<MediaItem> CREATOR = $$Lambda$MediaItem$1bnLubhsPaK0RtjNSTX2XRDSfY.INSTANCE;

    public static MediaItem fromUri(String str) {
        return new Builder().setUri(str).build();
    }

    public static MediaItem fromUri(Uri uri) {
        return new Builder().setUri(uri).build();
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private float A;
        private float B;
        @Nullable
        private String a;
        @Nullable
        private Uri b;
        @Nullable
        private String c;
        private long d;
        private long e;
        private boolean f;
        private boolean g;
        private boolean h;
        @Nullable
        private Uri i;
        private Map<String, String> j;
        @Nullable
        private UUID k;
        private boolean l;
        private boolean m;
        private boolean n;
        private List<Integer> o;
        @Nullable
        private byte[] p;
        private List<StreamKey> q;
        @Nullable
        private String r;
        private List<Subtitle> s;
        @Nullable
        private Uri t;
        @Nullable
        private Object u;
        @Nullable
        private Object v;
        @Nullable
        private MediaMetadata w;
        private long x;
        private long y;
        private long z;

        public Builder() {
            this.e = Long.MIN_VALUE;
            this.o = Collections.emptyList();
            this.j = Collections.emptyMap();
            this.q = Collections.emptyList();
            this.s = Collections.emptyList();
            this.x = C.TIME_UNSET;
            this.y = C.TIME_UNSET;
            this.z = C.TIME_UNSET;
            this.A = -3.4028235E38f;
            this.B = -3.4028235E38f;
        }

        private Builder(MediaItem mediaItem) {
            this();
            this.e = mediaItem.clippingProperties.endPositionMs;
            this.f = mediaItem.clippingProperties.relativeToLiveWindow;
            this.g = mediaItem.clippingProperties.relativeToDefaultPosition;
            this.d = mediaItem.clippingProperties.startPositionMs;
            this.h = mediaItem.clippingProperties.startsAtKeyFrame;
            this.a = mediaItem.mediaId;
            this.w = mediaItem.mediaMetadata;
            this.x = mediaItem.liveConfiguration.targetOffsetMs;
            this.y = mediaItem.liveConfiguration.minOffsetMs;
            this.z = mediaItem.liveConfiguration.maxOffsetMs;
            this.A = mediaItem.liveConfiguration.minPlaybackSpeed;
            this.B = mediaItem.liveConfiguration.maxPlaybackSpeed;
            PlaybackProperties playbackProperties = mediaItem.playbackProperties;
            if (playbackProperties != null) {
                this.r = playbackProperties.customCacheKey;
                this.c = playbackProperties.mimeType;
                this.b = playbackProperties.uri;
                this.q = playbackProperties.streamKeys;
                this.s = playbackProperties.subtitles;
                this.v = playbackProperties.tag;
                DrmConfiguration drmConfiguration = playbackProperties.drmConfiguration;
                if (drmConfiguration != null) {
                    this.i = drmConfiguration.licenseUri;
                    this.j = drmConfiguration.requestHeaders;
                    this.l = drmConfiguration.multiSession;
                    this.n = drmConfiguration.forceDefaultLicenseUri;
                    this.m = drmConfiguration.playClearContentWithoutKey;
                    this.o = drmConfiguration.sessionForClearTypes;
                    this.k = drmConfiguration.uuid;
                    this.p = drmConfiguration.getKeySetId();
                }
                AdsConfiguration adsConfiguration = playbackProperties.adsConfiguration;
                if (adsConfiguration != null) {
                    this.t = adsConfiguration.adTagUri;
                    this.u = adsConfiguration.adsId;
                }
            }
        }

        public Builder setMediaId(String str) {
            this.a = (String) Assertions.checkNotNull(str);
            return this;
        }

        public Builder setUri(@Nullable String str) {
            return setUri(str == null ? null : Uri.parse(str));
        }

        public Builder setUri(@Nullable Uri uri) {
            this.b = uri;
            return this;
        }

        public Builder setMimeType(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setClipStartPositionMs(long j) {
            Assertions.checkArgument(j >= 0);
            this.d = j;
            return this;
        }

        public Builder setClipEndPositionMs(long j) {
            Assertions.checkArgument(j == Long.MIN_VALUE || j >= 0);
            this.e = j;
            return this;
        }

        public Builder setClipRelativeToLiveWindow(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setClipRelativeToDefaultPosition(boolean z) {
            this.g = z;
            return this;
        }

        public Builder setClipStartsAtKeyFrame(boolean z) {
            this.h = z;
            return this;
        }

        public Builder setDrmLicenseUri(@Nullable Uri uri) {
            this.i = uri;
            return this;
        }

        public Builder setDrmLicenseUri(@Nullable String str) {
            this.i = str == null ? null : Uri.parse(str);
            return this;
        }

        public Builder setDrmLicenseRequestHeaders(@Nullable Map<String, String> map) {
            Map<String, String> map2;
            if (map == null || map.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(new HashMap(map));
            }
            this.j = map2;
            return this;
        }

        public Builder setDrmUuid(@Nullable UUID uuid) {
            this.k = uuid;
            return this;
        }

        public Builder setDrmMultiSession(boolean z) {
            this.l = z;
            return this;
        }

        public Builder setDrmForceDefaultLicenseUri(boolean z) {
            this.n = z;
            return this;
        }

        public Builder setDrmPlayClearContentWithoutKey(boolean z) {
            this.m = z;
            return this;
        }

        public Builder setDrmSessionForClearPeriods(boolean z) {
            setDrmSessionForClearTypes(z ? Arrays.asList(2, 1) : Collections.emptyList());
            return this;
        }

        public Builder setDrmSessionForClearTypes(@Nullable List<Integer> list) {
            List<Integer> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.o = list2;
            return this;
        }

        public Builder setDrmKeySetId(@Nullable byte[] bArr) {
            this.p = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            List<StreamKey> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.q = list2;
            return this;
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.r = str;
            return this;
        }

        public Builder setSubtitles(@Nullable List<Subtitle> list) {
            List<Subtitle> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.s = list2;
            return this;
        }

        public Builder setAdTagUri(@Nullable String str) {
            return setAdTagUri(str != null ? Uri.parse(str) : null);
        }

        public Builder setAdTagUri(@Nullable Uri uri) {
            return setAdTagUri(uri, null);
        }

        public Builder setAdTagUri(@Nullable Uri uri, @Nullable Object obj) {
            this.t = uri;
            this.u = obj;
            return this;
        }

        public Builder setLiveTargetOffsetMs(long j) {
            this.x = j;
            return this;
        }

        public Builder setLiveMinOffsetMs(long j) {
            this.y = j;
            return this;
        }

        public Builder setLiveMaxOffsetMs(long j) {
            this.z = j;
            return this;
        }

        public Builder setLiveMinPlaybackSpeed(float f) {
            this.A = f;
            return this;
        }

        public Builder setLiveMaxPlaybackSpeed(float f) {
            this.B = f;
            return this;
        }

        public Builder setTag(@Nullable Object obj) {
            this.v = obj;
            return this;
        }

        public Builder setMediaMetadata(MediaMetadata mediaMetadata) {
            this.w = mediaMetadata;
            return this;
        }

        public MediaItem build() {
            PlaybackProperties playbackProperties;
            Assertions.checkState(this.i == null || this.k != null);
            Uri uri = this.b;
            if (uri != null) {
                String str = this.c;
                UUID uuid = this.k;
                DrmConfiguration drmConfiguration = uuid != null ? new DrmConfiguration(uuid, this.i, this.j, this.l, this.n, this.m, this.o, this.p) : null;
                Uri uri2 = this.t;
                playbackProperties = new PlaybackProperties(uri, str, drmConfiguration, uri2 != null ? new AdsConfiguration(uri2, this.u) : null, this.q, this.r, this.s, this.v);
            } else {
                playbackProperties = null;
            }
            String str2 = this.a;
            if (str2 == null) {
                str2 = "";
            }
            ClippingProperties clippingProperties = new ClippingProperties(this.d, this.e, this.f, this.g, this.h);
            LiveConfiguration liveConfiguration = new LiveConfiguration(this.x, this.y, this.z, this.A, this.B);
            MediaMetadata mediaMetadata = this.w;
            if (mediaMetadata == null) {
                mediaMetadata = MediaMetadata.EMPTY;
            }
            return new MediaItem(str2, clippingProperties, playbackProperties, liveConfiguration, mediaMetadata);
        }
    }

    /* loaded from: classes.dex */
    public static final class DrmConfiguration {
        @Nullable
        private final byte[] a;
        public final boolean forceDefaultLicenseUri;
        @Nullable
        public final Uri licenseUri;
        public final boolean multiSession;
        public final boolean playClearContentWithoutKey;
        public final Map<String, String> requestHeaders;
        public final List<Integer> sessionForClearTypes;
        public final UUID uuid;

        private DrmConfiguration(UUID uuid, @Nullable Uri uri, Map<String, String> map, boolean z, boolean z2, boolean z3, List<Integer> list, @Nullable byte[] bArr) {
            Assertions.checkArgument(!z2 || uri != null);
            this.uuid = uuid;
            this.licenseUri = uri;
            this.requestHeaders = map;
            this.multiSession = z;
            this.forceDefaultLicenseUri = z2;
            this.playClearContentWithoutKey = z3;
            this.sessionForClearTypes = list;
            this.a = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        }

        @Nullable
        public byte[] getKeySetId() {
            byte[] bArr = this.a;
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrmConfiguration)) {
                return false;
            }
            DrmConfiguration drmConfiguration = (DrmConfiguration) obj;
            return this.uuid.equals(drmConfiguration.uuid) && Util.areEqual(this.licenseUri, drmConfiguration.licenseUri) && Util.areEqual(this.requestHeaders, drmConfiguration.requestHeaders) && this.multiSession == drmConfiguration.multiSession && this.forceDefaultLicenseUri == drmConfiguration.forceDefaultLicenseUri && this.playClearContentWithoutKey == drmConfiguration.playClearContentWithoutKey && this.sessionForClearTypes.equals(drmConfiguration.sessionForClearTypes) && Arrays.equals(this.a, drmConfiguration.a);
        }

        public int hashCode() {
            int hashCode = this.uuid.hashCode() * 31;
            Uri uri = this.licenseUri;
            return ((((((((((((hashCode + (uri != null ? uri.hashCode() : 0)) * 31) + this.requestHeaders.hashCode()) * 31) + (this.multiSession ? 1 : 0)) * 31) + (this.forceDefaultLicenseUri ? 1 : 0)) * 31) + (this.playClearContentWithoutKey ? 1 : 0)) * 31) + this.sessionForClearTypes.hashCode()) * 31) + Arrays.hashCode(this.a);
        }
    }

    /* loaded from: classes.dex */
    public static final class AdsConfiguration {
        public final Uri adTagUri;
        @Nullable
        public final Object adsId;

        private AdsConfiguration(Uri uri, @Nullable Object obj) {
            this.adTagUri = uri;
            this.adsId = obj;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdsConfiguration)) {
                return false;
            }
            AdsConfiguration adsConfiguration = (AdsConfiguration) obj;
            return this.adTagUri.equals(adsConfiguration.adTagUri) && Util.areEqual(this.adsId, adsConfiguration.adsId);
        }

        public int hashCode() {
            int hashCode = this.adTagUri.hashCode() * 31;
            Object obj = this.adsId;
            return hashCode + (obj != null ? obj.hashCode() : 0);
        }
    }

    /* loaded from: classes.dex */
    public static final class PlaybackProperties {
        @Nullable
        public final AdsConfiguration adsConfiguration;
        @Nullable
        public final String customCacheKey;
        @Nullable
        public final DrmConfiguration drmConfiguration;
        @Nullable
        public final String mimeType;
        public final List<StreamKey> streamKeys;
        public final List<Subtitle> subtitles;
        @Nullable
        public final Object tag;
        public final Uri uri;

        private PlaybackProperties(Uri uri, @Nullable String str, @Nullable DrmConfiguration drmConfiguration, @Nullable AdsConfiguration adsConfiguration, List<StreamKey> list, @Nullable String str2, List<Subtitle> list2, @Nullable Object obj) {
            this.uri = uri;
            this.mimeType = str;
            this.drmConfiguration = drmConfiguration;
            this.adsConfiguration = adsConfiguration;
            this.streamKeys = list;
            this.customCacheKey = str2;
            this.subtitles = list2;
            this.tag = obj;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PlaybackProperties)) {
                return false;
            }
            PlaybackProperties playbackProperties = (PlaybackProperties) obj;
            return this.uri.equals(playbackProperties.uri) && Util.areEqual(this.mimeType, playbackProperties.mimeType) && Util.areEqual(this.drmConfiguration, playbackProperties.drmConfiguration) && Util.areEqual(this.adsConfiguration, playbackProperties.adsConfiguration) && this.streamKeys.equals(playbackProperties.streamKeys) && Util.areEqual(this.customCacheKey, playbackProperties.customCacheKey) && this.subtitles.equals(playbackProperties.subtitles) && Util.areEqual(this.tag, playbackProperties.tag);
        }

        public int hashCode() {
            int hashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int i = 0;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            DrmConfiguration drmConfiguration = this.drmConfiguration;
            int hashCode3 = (hashCode2 + (drmConfiguration == null ? 0 : drmConfiguration.hashCode())) * 31;
            AdsConfiguration adsConfiguration = this.adsConfiguration;
            int hashCode4 = (((hashCode3 + (adsConfiguration == null ? 0 : adsConfiguration.hashCode())) * 31) + this.streamKeys.hashCode()) * 31;
            String str2 = this.customCacheKey;
            int hashCode5 = (((hashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.subtitles.hashCode()) * 31;
            Object obj = this.tag;
            if (obj != null) {
                i = obj.hashCode();
            }
            return hashCode5 + i;
        }
    }

    /* loaded from: classes.dex */
    public static final class LiveConfiguration implements Bundleable {
        public final long maxOffsetMs;
        public final float maxPlaybackSpeed;
        public final long minOffsetMs;
        public final float minPlaybackSpeed;
        public final long targetOffsetMs;
        public static final LiveConfiguration UNSET = new LiveConfiguration(C.TIME_UNSET, C.TIME_UNSET, C.TIME_UNSET, -3.4028235E38f, -3.4028235E38f);
        public static final Bundleable.Creator<LiveConfiguration> CREATOR = $$Lambda$MediaItem$LiveConfiguration$jx6D5czcaFfM49XE0FKcm4khx2k.INSTANCE;

        public LiveConfiguration(long j, long j2, long j3, float f, float f2) {
            this.targetOffsetMs = j;
            this.minOffsetMs = j2;
            this.maxOffsetMs = j3;
            this.minPlaybackSpeed = f;
            this.maxPlaybackSpeed = f2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LiveConfiguration)) {
                return false;
            }
            LiveConfiguration liveConfiguration = (LiveConfiguration) obj;
            return this.targetOffsetMs == liveConfiguration.targetOffsetMs && this.minOffsetMs == liveConfiguration.minOffsetMs && this.maxOffsetMs == liveConfiguration.maxOffsetMs && this.minPlaybackSpeed == liveConfiguration.minPlaybackSpeed && this.maxPlaybackSpeed == liveConfiguration.maxPlaybackSpeed;
        }

        public int hashCode() {
            long j = this.targetOffsetMs;
            long j2 = this.minOffsetMs;
            long j3 = this.maxOffsetMs;
            int i = ((((((int) (j ^ (j >>> 32))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) ((j3 >>> 32) ^ j3))) * 31;
            float f = this.minPlaybackSpeed;
            int i2 = 0;
            int floatToIntBits = (i + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31;
            float f2 = this.maxPlaybackSpeed;
            if (f2 != 0.0f) {
                i2 = Float.floatToIntBits(f2);
            }
            return floatToIntBits + i2;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(a(0), this.targetOffsetMs);
            bundle.putLong(a(1), this.minOffsetMs);
            bundle.putLong(a(2), this.maxOffsetMs);
            bundle.putFloat(a(3), this.minPlaybackSpeed);
            bundle.putFloat(a(4), this.maxPlaybackSpeed);
            return bundle;
        }

        public static /* synthetic */ LiveConfiguration a(Bundle bundle) {
            return new LiveConfiguration(bundle.getLong(a(0), C.TIME_UNSET), bundle.getLong(a(1), C.TIME_UNSET), bundle.getLong(a(2), C.TIME_UNSET), bundle.getFloat(a(3), -3.4028235E38f), bundle.getFloat(a(4), -3.4028235E38f));
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    /* loaded from: classes.dex */
    public static final class Subtitle {
        @Nullable
        public final String label;
        @Nullable
        public final String language;
        public final String mimeType;
        public final int roleFlags;
        public final int selectionFlags;
        public final Uri uri;

        public Subtitle(Uri uri, String str, @Nullable String str2) {
            this(uri, str, str2, 0);
        }

        public Subtitle(Uri uri, String str, @Nullable String str2, int i) {
            this(uri, str, str2, i, 0, null);
        }

        public Subtitle(Uri uri, String str, @Nullable String str2, int i, int i2, @Nullable String str3) {
            this.uri = uri;
            this.mimeType = str;
            this.language = str2;
            this.selectionFlags = i;
            this.roleFlags = i2;
            this.label = str3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Subtitle)) {
                return false;
            }
            Subtitle subtitle = (Subtitle) obj;
            return this.uri.equals(subtitle.uri) && this.mimeType.equals(subtitle.mimeType) && Util.areEqual(this.language, subtitle.language) && this.selectionFlags == subtitle.selectionFlags && this.roleFlags == subtitle.roleFlags && Util.areEqual(this.label, subtitle.label);
        }

        public int hashCode() {
            int hashCode = ((this.uri.hashCode() * 31) + this.mimeType.hashCode()) * 31;
            String str = this.language;
            int i = 0;
            int hashCode2 = (((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31;
            String str2 = this.label;
            if (str2 != null) {
                i = str2.hashCode();
            }
            return hashCode2 + i;
        }
    }

    /* loaded from: classes.dex */
    public static final class ClippingProperties implements Bundleable {
        public static final Bundleable.Creator<ClippingProperties> CREATOR = $$Lambda$MediaItem$ClippingProperties$gEfxThw2LG8w0Djzn4tHqdazTM4.INSTANCE;
        public final long endPositionMs;
        public final boolean relativeToDefaultPosition;
        public final boolean relativeToLiveWindow;
        public final long startPositionMs;
        public final boolean startsAtKeyFrame;

        private ClippingProperties(long j, long j2, boolean z, boolean z2, boolean z3) {
            this.startPositionMs = j;
            this.endPositionMs = j2;
            this.relativeToLiveWindow = z;
            this.relativeToDefaultPosition = z2;
            this.startsAtKeyFrame = z3;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClippingProperties)) {
                return false;
            }
            ClippingProperties clippingProperties = (ClippingProperties) obj;
            return this.startPositionMs == clippingProperties.startPositionMs && this.endPositionMs == clippingProperties.endPositionMs && this.relativeToLiveWindow == clippingProperties.relativeToLiveWindow && this.relativeToDefaultPosition == clippingProperties.relativeToDefaultPosition && this.startsAtKeyFrame == clippingProperties.startsAtKeyFrame;
        }

        public int hashCode() {
            long j = this.startPositionMs;
            long j2 = this.endPositionMs;
            return (((((((((int) (j ^ (j >>> 32))) * 31) + ((int) ((j2 >>> 32) ^ j2))) * 31) + (this.relativeToLiveWindow ? 1 : 0)) * 31) + (this.relativeToDefaultPosition ? 1 : 0)) * 31) + (this.startsAtKeyFrame ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(a(0), this.startPositionMs);
            bundle.putLong(a(1), this.endPositionMs);
            bundle.putBoolean(a(2), this.relativeToLiveWindow);
            bundle.putBoolean(a(3), this.relativeToDefaultPosition);
            bundle.putBoolean(a(4), this.startsAtKeyFrame);
            return bundle;
        }

        public static /* synthetic */ ClippingProperties a(Bundle bundle) {
            return new ClippingProperties(bundle.getLong(a(0), 0L), bundle.getLong(a(1), Long.MIN_VALUE), bundle.getBoolean(a(2), false), bundle.getBoolean(a(3), false), bundle.getBoolean(a(4), false));
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    private MediaItem(String str, ClippingProperties clippingProperties, @Nullable PlaybackProperties playbackProperties, LiveConfiguration liveConfiguration, MediaMetadata mediaMetadata) {
        this.mediaId = str;
        this.playbackProperties = playbackProperties;
        this.liveConfiguration = liveConfiguration;
        this.mediaMetadata = mediaMetadata;
        this.clippingProperties = clippingProperties;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return Util.areEqual(this.mediaId, mediaItem.mediaId) && this.clippingProperties.equals(mediaItem.clippingProperties) && Util.areEqual(this.playbackProperties, mediaItem.playbackProperties) && Util.areEqual(this.liveConfiguration, mediaItem.liveConfiguration) && Util.areEqual(this.mediaMetadata, mediaItem.mediaMetadata);
    }

    public int hashCode() {
        int hashCode = this.mediaId.hashCode() * 31;
        PlaybackProperties playbackProperties = this.playbackProperties;
        return ((((((hashCode + (playbackProperties != null ? playbackProperties.hashCode() : 0)) * 31) + this.liveConfiguration.hashCode()) * 31) + this.clippingProperties.hashCode()) * 31) + this.mediaMetadata.hashCode();
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(a(0), this.mediaId);
        bundle.putBundle(a(1), this.liveConfiguration.toBundle());
        bundle.putBundle(a(2), this.mediaMetadata.toBundle());
        bundle.putBundle(a(3), this.clippingProperties.toBundle());
        return bundle;
    }

    public static MediaItem a(Bundle bundle) {
        LiveConfiguration liveConfiguration;
        MediaMetadata mediaMetadata;
        ClippingProperties clippingProperties;
        String str = (String) Assertions.checkNotNull(bundle.getString(a(0), ""));
        Bundle bundle2 = bundle.getBundle(a(1));
        if (bundle2 == null) {
            liveConfiguration = LiveConfiguration.UNSET;
        } else {
            liveConfiguration = LiveConfiguration.CREATOR.fromBundle(bundle2);
        }
        Bundle bundle3 = bundle.getBundle(a(2));
        if (bundle3 == null) {
            mediaMetadata = MediaMetadata.EMPTY;
        } else {
            mediaMetadata = MediaMetadata.CREATOR.fromBundle(bundle3);
        }
        Bundle bundle4 = bundle.getBundle(a(3));
        if (bundle4 == null) {
            clippingProperties = new ClippingProperties(0L, Long.MIN_VALUE, false, false, false);
        } else {
            clippingProperties = ClippingProperties.CREATOR.fromBundle(bundle4);
        }
        return new MediaItem(str, clippingProperties, null, liveConfiguration, mediaMetadata);
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }
}
