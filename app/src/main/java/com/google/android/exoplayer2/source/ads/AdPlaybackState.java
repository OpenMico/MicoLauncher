package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class AdPlaybackState implements Bundleable {
    public static final int AD_STATE_AVAILABLE = 1;
    public static final int AD_STATE_ERROR = 4;
    public static final int AD_STATE_PLAYED = 3;
    public static final int AD_STATE_SKIPPED = 2;
    public static final int AD_STATE_UNAVAILABLE = 0;
    public final int adGroupCount;
    public final long adResumePositionUs;
    @Nullable
    public final Object adsId;
    private final AdGroup[] b;
    public final long contentDurationUs;
    public final int removedAdGroupCount;
    public static final AdPlaybackState NONE = new AdPlaybackState(null, new AdGroup[0], 0, C.TIME_UNSET, 0);
    private static final AdGroup a = new AdGroup(0).withAdCount(0);
    public static final Bundleable.Creator<AdPlaybackState> CREATOR = $$Lambda$AdPlaybackState$4AZlnmZitipR8jX7vUoA8mek1lg.INSTANCE;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface AdState {
    }

    /* loaded from: classes2.dex */
    public static final class AdGroup implements Bundleable {
        public static final Bundleable.Creator<AdGroup> CREATOR = $$Lambda$AdPlaybackState$AdGroup$wLadnwMFEjYVbEy5tqhD3rFvsis.INSTANCE;
        public final long contentResumeOffsetUs;
        public final int count;
        public final long[] durationsUs;
        public final boolean isServerSideInserted;
        public final int[] states;
        public final long timeUs;
        public final Uri[] uris;

        public AdGroup(long j) {
            this(j, -1, new int[0], new Uri[0], new long[0], 0L, false);
        }

        private AdGroup(long j, int i, int[] iArr, Uri[] uriArr, long[] jArr, long j2, boolean z) {
            Assertions.checkArgument(iArr.length == uriArr.length);
            this.timeUs = j;
            this.count = i;
            this.states = iArr;
            this.uris = uriArr;
            this.durationsUs = jArr;
            this.contentResumeOffsetUs = j2;
            this.isServerSideInserted = z;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(int i) {
            int i2 = i + 1;
            while (true) {
                int[] iArr = this.states;
                if (i2 >= iArr.length || this.isServerSideInserted || iArr[i2] == 0 || iArr[i2] == 1) {
                    break;
                }
                i2++;
            }
            return i2;
        }

        public boolean shouldPlayAdGroup() {
            return this.count == -1 || getFirstAdIndexToPlay() < this.count;
        }

        public boolean hasUnplayedAds() {
            if (this.count == -1) {
                return true;
            }
            for (int i = 0; i < this.count; i++) {
                int[] iArr = this.states;
                if (iArr[i] == 0 || iArr[i] == 1) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AdGroup adGroup = (AdGroup) obj;
            return this.timeUs == adGroup.timeUs && this.count == adGroup.count && Arrays.equals(this.uris, adGroup.uris) && Arrays.equals(this.states, adGroup.states) && Arrays.equals(this.durationsUs, adGroup.durationsUs) && this.contentResumeOffsetUs == adGroup.contentResumeOffsetUs && this.isServerSideInserted == adGroup.isServerSideInserted;
        }

        public int hashCode() {
            long j = this.timeUs;
            long j2 = this.contentResumeOffsetUs;
            return (((((((((((this.count * 31) + ((int) (j ^ (j >>> 32)))) * 31) + Arrays.hashCode(this.uris)) * 31) + Arrays.hashCode(this.states)) * 31) + Arrays.hashCode(this.durationsUs)) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (this.isServerSideInserted ? 1 : 0);
        }

        @CheckResult
        public AdGroup withTimeUs(long j) {
            return new AdGroup(j, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdCount(int i) {
            int[] a = a(this.states, i);
            long[] a2 = a(this.durationsUs, i);
            return new AdGroup(this.timeUs, i, a, (Uri[]) Arrays.copyOf(this.uris, i), a2, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdUri(Uri uri, int i) {
            int[] a = a(this.states, i + 1);
            long[] jArr = this.durationsUs;
            long[] a2 = jArr.length == a.length ? jArr : a(jArr, a.length);
            Uri[] uriArr = (Uri[]) Arrays.copyOf(this.uris, a.length);
            uriArr[i] = uri;
            a[i] = 1;
            return new AdGroup(this.timeUs, this.count, a, uriArr, a2, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdState(int i, int i2) {
            int i3 = this.count;
            boolean z = false;
            Assertions.checkArgument(i3 == -1 || i2 < i3);
            int[] a = a(this.states, i2 + 1);
            if (a[i2] == 0 || a[i2] == 1 || a[i2] == i) {
                z = true;
            }
            Assertions.checkArgument(z);
            long[] jArr = this.durationsUs;
            long[] a2 = jArr.length == a.length ? jArr : a(jArr, a.length);
            Uri[] uriArr = this.uris;
            if (uriArr.length != a.length) {
                uriArr = (Uri[]) Arrays.copyOf(uriArr, a.length);
            }
            a[i2] = i;
            return new AdGroup(this.timeUs, this.count, a, uriArr, a2, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdDurationsUs(long[] jArr) {
            long[] jArr2;
            int length = jArr.length;
            Uri[] uriArr = this.uris;
            if (length < uriArr.length) {
                jArr2 = a(jArr, uriArr.length);
            } else {
                jArr2 = (this.count == -1 || jArr.length <= uriArr.length) ? jArr : Arrays.copyOf(jArr, uriArr.length);
            }
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, jArr2, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withContentResumeOffsetUs(long j) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, j, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withIsServerSideInserted(boolean z) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, z);
        }

        @CheckResult
        public AdGroup withAllAdsSkipped() {
            if (this.count == -1) {
                return new AdGroup(this.timeUs, 0, new int[0], new Uri[0], new long[0], this.contentResumeOffsetUs, this.isServerSideInserted);
            }
            int[] iArr = this.states;
            int length = iArr.length;
            int[] copyOf = Arrays.copyOf(iArr, length);
            for (int i = 0; i < length; i++) {
                if (copyOf[i] == 1 || copyOf[i] == 0) {
                    copyOf[i] = 2;
                }
            }
            return new AdGroup(this.timeUs, length, copyOf, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        private static int[] a(int[] iArr, int i) {
            int length = iArr.length;
            int max = Math.max(i, length);
            int[] copyOf = Arrays.copyOf(iArr, max);
            Arrays.fill(copyOf, length, max, 0);
            return copyOf;
        }

        @CheckResult
        private static long[] a(long[] jArr, int i) {
            int length = jArr.length;
            int max = Math.max(i, length);
            long[] copyOf = Arrays.copyOf(jArr, max);
            Arrays.fill(copyOf, length, max, (long) C.TIME_UNSET);
            return copyOf;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(a(0), this.timeUs);
            bundle.putInt(a(1), this.count);
            bundle.putParcelableArrayList(a(2), new ArrayList<>(Arrays.asList(this.uris)));
            bundle.putIntArray(a(3), this.states);
            bundle.putLongArray(a(4), this.durationsUs);
            bundle.putLong(a(5), this.contentResumeOffsetUs);
            bundle.putBoolean(a(6), this.isServerSideInserted);
            return bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static AdGroup a(Bundle bundle) {
            Uri[] uriArr;
            long j = bundle.getLong(a(0));
            int i = bundle.getInt(a(1), -1);
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(a(2));
            int[] intArray = bundle.getIntArray(a(3));
            long[] longArray = bundle.getLongArray(a(4));
            long j2 = bundle.getLong(a(5));
            boolean z = bundle.getBoolean(a(6));
            if (intArray == null) {
                intArray = new int[0];
            }
            if (parcelableArrayList == null) {
                uriArr = new Uri[0];
            } else {
                uriArr = (Uri[]) parcelableArrayList.toArray(new Uri[0]);
            }
            return new AdGroup(j, i, intArray, uriArr, longArray == null ? new long[0] : longArray, j2, z);
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    public AdPlaybackState(Object obj, long... jArr) {
        this(obj, a(jArr), 0L, C.TIME_UNSET, 0);
    }

    private AdPlaybackState(@Nullable Object obj, AdGroup[] adGroupArr, long j, long j2, int i) {
        this.adsId = obj;
        this.adResumePositionUs = j;
        this.contentDurationUs = j2;
        this.adGroupCount = adGroupArr.length + i;
        this.b = adGroupArr;
        this.removedAdGroupCount = i;
    }

    public AdGroup getAdGroup(int i) {
        int i2 = this.removedAdGroupCount;
        if (i < i2) {
            return a;
        }
        return this.b[i - i2];
    }

    public int getAdGroupIndexForPositionUs(long j, long j2) {
        int i = this.adGroupCount - 1;
        while (i >= 0 && a(j, j2, i)) {
            i--;
        }
        if (i < 0 || !getAdGroup(i).hasUnplayedAds()) {
            return -1;
        }
        return i;
    }

    public int getAdGroupIndexAfterPositionUs(long j, long j2) {
        if (j == Long.MIN_VALUE || (j2 != C.TIME_UNSET && j >= j2)) {
            return -1;
        }
        int i = this.removedAdGroupCount;
        while (i < this.adGroupCount && ((getAdGroup(i).timeUs != Long.MIN_VALUE && getAdGroup(i).timeUs <= j) || !getAdGroup(i).shouldPlayAdGroup())) {
            i++;
        }
        if (i < this.adGroupCount) {
            return i;
        }
        return -1;
    }

    public boolean isAdInErrorState(int i, int i2) {
        if (i >= this.adGroupCount) {
            return false;
        }
        AdGroup adGroup = getAdGroup(i);
        return adGroup.count != -1 && i2 < adGroup.count && adGroup.states[i2] == 4;
    }

    @CheckResult
    public AdPlaybackState withAdGroupTimeUs(int i, long j) {
        int i2 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = this.b[i2].withTimeUs(j);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withNewAdGroup(int i, long j) {
        int i2 = i - this.removedAdGroupCount;
        AdGroup adGroup = new AdGroup(j);
        AdGroup[] adGroupArr = (AdGroup[]) Util.nullSafeArrayAppend(this.b, adGroup);
        System.arraycopy(adGroupArr, i2, adGroupArr, i2 + 1, this.b.length - i2);
        adGroupArr[i2] = adGroup;
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdCount(int i, int i2) {
        Assertions.checkArgument(i2 > 0);
        int i3 = i - this.removedAdGroupCount;
        if (this.b[i3].count == i2) {
            return this;
        }
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = this.b[i3].withAdCount(i2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdUri(int i, int i2, Uri uri) {
        int i3 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAdUri(uri, i2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withPlayedAd(int i, int i2) {
        int i3 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAdState(3, i2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withSkippedAd(int i, int i2) {
        int i3 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAdState(2, i2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdLoadError(int i, int i2) {
        int i3 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAdState(4, i2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withSkippedAdGroup(int i) {
        int i2 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAllAdsSkipped();
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(long[][] jArr) {
        Assertions.checkState(this.removedAdGroupCount == 0);
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        for (int i = 0; i < this.adGroupCount; i++) {
            adGroupArr2[i] = adGroupArr2[i].withAdDurationsUs(jArr[i]);
        }
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(int i, long... jArr) {
        int i2 = i - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withAdDurationsUs(jArr);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdResumePositionUs(long j) {
        return this.adResumePositionUs == j ? this : new AdPlaybackState(this.adsId, this.b, j, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withContentDurationUs(long j) {
        return this.contentDurationUs == j ? this : new AdPlaybackState(this.adsId, this.b, this.adResumePositionUs, j, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withRemovedAdGroupCount(int i) {
        int i2 = this.removedAdGroupCount;
        if (i2 == i) {
            return this;
        }
        Assertions.checkArgument(i > i2);
        AdGroup[] adGroupArr = new AdGroup[this.adGroupCount - i];
        System.arraycopy(this.b, i - this.removedAdGroupCount, adGroupArr, 0, adGroupArr.length);
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, i);
    }

    @CheckResult
    public AdPlaybackState withContentResumeOffsetUs(int i, long j) {
        int i2 = i - this.removedAdGroupCount;
        if (this.b[i2].contentResumeOffsetUs == j) {
            return this;
        }
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withContentResumeOffsetUs(j);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withIsServerSideInserted(int i, boolean z) {
        int i2 = i - this.removedAdGroupCount;
        if (this.b[i2].isServerSideInserted == z) {
            return this;
        }
        AdGroup[] adGroupArr = this.b;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i2] = adGroupArr2[i2].withIsServerSideInserted(z);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdPlaybackState adPlaybackState = (AdPlaybackState) obj;
        return Util.areEqual(this.adsId, adPlaybackState.adsId) && this.adGroupCount == adPlaybackState.adGroupCount && this.adResumePositionUs == adPlaybackState.adResumePositionUs && this.contentDurationUs == adPlaybackState.contentDurationUs && this.removedAdGroupCount == adPlaybackState.removedAdGroupCount && Arrays.equals(this.b, adPlaybackState.b);
    }

    public int hashCode() {
        int i = this.adGroupCount * 31;
        Object obj = this.adsId;
        return ((((((((i + (obj == null ? 0 : obj.hashCode())) * 31) + ((int) this.adResumePositionUs)) * 31) + ((int) this.contentDurationUs)) * 31) + this.removedAdGroupCount) * 31) + Arrays.hashCode(this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdPlaybackState(adsId=");
        sb.append(this.adsId);
        sb.append(", adResumePositionUs=");
        sb.append(this.adResumePositionUs);
        sb.append(", adGroups=[");
        for (int i = 0; i < this.b.length; i++) {
            sb.append("adGroup(timeUs=");
            sb.append(this.b[i].timeUs);
            sb.append(", ads=[");
            for (int i2 = 0; i2 < this.b[i].states.length; i2++) {
                sb.append("ad(state=");
                switch (this.b[i].states[i2]) {
                    case 0:
                        sb.append('_');
                        break;
                    case 1:
                        sb.append('R');
                        break;
                    case 2:
                        sb.append('S');
                        break;
                    case 3:
                        sb.append('P');
                        break;
                    case 4:
                        sb.append('!');
                        break;
                    default:
                        sb.append('?');
                        break;
                }
                sb.append(", durationUs=");
                sb.append(this.b[i].durationsUs[i2]);
                sb.append(')');
                if (i2 < this.b[i].states.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("])");
            if (i < this.b.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("])");
        return sb.toString();
    }

    private boolean a(long j, long j2, int i) {
        if (j == Long.MIN_VALUE) {
            return false;
        }
        long j3 = getAdGroup(i).timeUs;
        return j3 == Long.MIN_VALUE ? j2 == C.TIME_UNSET || j < j2 : j < j3;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (AdGroup adGroup : this.b) {
            arrayList.add(adGroup.toBundle());
        }
        bundle.putParcelableArrayList(a(1), arrayList);
        bundle.putLong(a(2), this.adResumePositionUs);
        bundle.putLong(a(3), this.contentDurationUs);
        bundle.putInt(a(4), this.removedAdGroupCount);
        return bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AdPlaybackState a(Bundle bundle) {
        AdGroup[] adGroupArr;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(a(1));
        if (parcelableArrayList == null) {
            adGroupArr = new AdGroup[0];
        } else {
            AdGroup[] adGroupArr2 = new AdGroup[parcelableArrayList.size()];
            for (int i = 0; i < parcelableArrayList.size(); i++) {
                adGroupArr2[i] = AdGroup.CREATOR.fromBundle((Bundle) parcelableArrayList.get(i));
            }
            adGroupArr = adGroupArr2;
        }
        return new AdPlaybackState(null, adGroupArr, bundle.getLong(a(2), 0L), bundle.getLong(a(3), C.TIME_UNSET), bundle.getInt(a(4)));
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }

    private static AdGroup[] a(long[] jArr) {
        AdGroup[] adGroupArr = new AdGroup[jArr.length];
        for (int i = 0; i < adGroupArr.length; i++) {
            adGroupArr[i] = new AdGroup(jArr[i]);
        }
        return adGroupArr;
    }
}
