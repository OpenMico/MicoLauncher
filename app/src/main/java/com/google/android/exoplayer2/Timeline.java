package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Timeline implements Bundleable {
    public static final Timeline EMPTY = new Timeline() { // from class: com.google.android.exoplayer2.Timeline.1
        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int i, Window window, long j) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int i, Period period, boolean z) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            throw new IndexOutOfBoundsException();
        }
    };
    public static final Bundleable.Creator<Timeline> CREATOR = $$Lambda$Timeline$3zS2gSnNL7WROFHvYqVXJs8IKU.INSTANCE;

    public abstract int getIndexOfPeriod(Object obj);

    public abstract Period getPeriod(int i, Period period, boolean z);

    public abstract int getPeriodCount();

    public abstract Object getUidOfPeriod(int i);

    public abstract Window getWindow(int i, Window window, long j);

    public abstract int getWindowCount();

    /* loaded from: classes.dex */
    public static final class Window implements Bundleable {
        public long defaultPositionUs;
        public long durationUs;
        public long elapsedRealtimeEpochOffsetMs;
        public int firstPeriodIndex;
        public boolean isDynamic;
        @Deprecated
        public boolean isLive;
        public boolean isPlaceholder;
        public boolean isSeekable;
        public int lastPeriodIndex;
        @Nullable
        public MediaItem.LiveConfiguration liveConfiguration;
        @Nullable
        public Object manifest;
        public long positionInFirstPeriodUs;
        public long presentationStartTimeMs;
        @Nullable
        @Deprecated
        public Object tag;
        public long windowStartTimeMs;
        public static final Object SINGLE_WINDOW_UID = new Object();
        private static final Object a = new Object();
        private static final MediaItem b = new MediaItem.Builder().setMediaId("com.google.android.exoplayer2.Timeline").setUri(Uri.EMPTY).build();
        public static final Bundleable.Creator<Window> CREATOR = $$Lambda$Timeline$Window$Jq_wlH426GytKzQkZFnMShEQUVU.INSTANCE;
        public Object uid = SINGLE_WINDOW_UID;
        public MediaItem mediaItem = b;

        public Window set(Object obj, @Nullable MediaItem mediaItem, @Nullable Object obj2, long j, long j2, long j3, boolean z, boolean z2, @Nullable MediaItem.LiveConfiguration liveConfiguration, long j4, long j5, int i, int i2, long j6) {
            this.uid = obj;
            this.mediaItem = mediaItem != null ? mediaItem : b;
            this.tag = (mediaItem == null || mediaItem.playbackProperties == null) ? null : mediaItem.playbackProperties.tag;
            this.manifest = obj2;
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.elapsedRealtimeEpochOffsetMs = j3;
            this.isSeekable = z;
            this.isDynamic = z2;
            this.isLive = liveConfiguration != null;
            this.liveConfiguration = liveConfiguration;
            this.defaultPositionUs = j4;
            this.durationUs = j5;
            this.firstPeriodIndex = i;
            this.lastPeriodIndex = i2;
            this.positionInFirstPeriodUs = j6;
            this.isPlaceholder = false;
            return this;
        }

        public long getDefaultPositionMs() {
            return C.usToMs(this.defaultPositionUs);
        }

        public long getDefaultPositionUs() {
            return this.defaultPositionUs;
        }

        public long getDurationMs() {
            return C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInFirstPeriodMs() {
            return C.usToMs(this.positionInFirstPeriodUs);
        }

        public long getPositionInFirstPeriodUs() {
            return this.positionInFirstPeriodUs;
        }

        public long getCurrentUnixTimeMs() {
            return Util.getNowUnixTimeMs(this.elapsedRealtimeEpochOffsetMs);
        }

        public boolean isLive() {
            Assertions.checkState(this.isLive == (this.liveConfiguration != null));
            return this.liveConfiguration != null;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Window window = (Window) obj;
            return Util.areEqual(this.uid, window.uid) && Util.areEqual(this.mediaItem, window.mediaItem) && Util.areEqual(this.manifest, window.manifest) && Util.areEqual(this.liveConfiguration, window.liveConfiguration) && this.presentationStartTimeMs == window.presentationStartTimeMs && this.windowStartTimeMs == window.windowStartTimeMs && this.elapsedRealtimeEpochOffsetMs == window.elapsedRealtimeEpochOffsetMs && this.isSeekable == window.isSeekable && this.isDynamic == window.isDynamic && this.isPlaceholder == window.isPlaceholder && this.defaultPositionUs == window.defaultPositionUs && this.durationUs == window.durationUs && this.firstPeriodIndex == window.firstPeriodIndex && this.lastPeriodIndex == window.lastPeriodIndex && this.positionInFirstPeriodUs == window.positionInFirstPeriodUs;
        }

        public int hashCode() {
            int hashCode = (((217 + this.uid.hashCode()) * 31) + this.mediaItem.hashCode()) * 31;
            Object obj = this.manifest;
            int i = 0;
            int hashCode2 = (hashCode + (obj == null ? 0 : obj.hashCode())) * 31;
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            if (liveConfiguration != null) {
                i = liveConfiguration.hashCode();
            }
            long j = this.presentationStartTimeMs;
            long j2 = this.windowStartTimeMs;
            long j3 = this.elapsedRealtimeEpochOffsetMs;
            long j4 = this.defaultPositionUs;
            long j5 = this.durationUs;
            long j6 = this.positionInFirstPeriodUs;
            return ((((((((((((((((((((((hashCode2 + i) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.isSeekable ? 1 : 0)) * 31) + (this.isDynamic ? 1 : 0)) * 31) + (this.isPlaceholder ? 1 : 0)) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31) + ((int) (j5 ^ (j5 >>> 32)))) * 31) + this.firstPeriodIndex) * 31) + this.lastPeriodIndex) * 31) + ((int) (j6 ^ (j6 >>> 32)));
        }

        public final Bundle a(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putBundle(a(1), (z ? MediaItem.EMPTY : this.mediaItem).toBundle());
            bundle.putLong(a(2), this.presentationStartTimeMs);
            bundle.putLong(a(3), this.windowStartTimeMs);
            bundle.putLong(a(4), this.elapsedRealtimeEpochOffsetMs);
            bundle.putBoolean(a(5), this.isSeekable);
            bundle.putBoolean(a(6), this.isDynamic);
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            if (liveConfiguration != null) {
                bundle.putBundle(a(7), liveConfiguration.toBundle());
            }
            bundle.putBoolean(a(8), this.isPlaceholder);
            bundle.putLong(a(9), this.defaultPositionUs);
            bundle.putLong(a(10), this.durationUs);
            bundle.putInt(a(11), this.firstPeriodIndex);
            bundle.putInt(a(12), this.lastPeriodIndex);
            bundle.putLong(a(13), this.positionInFirstPeriodUs);
            return bundle;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            return a(false);
        }

        public static Window a(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(a(1));
            MediaItem fromBundle = bundle2 != null ? MediaItem.CREATOR.fromBundle(bundle2) : null;
            long j = bundle.getLong(a(2), C.TIME_UNSET);
            long j2 = bundle.getLong(a(3), C.TIME_UNSET);
            long j3 = bundle.getLong(a(4), C.TIME_UNSET);
            boolean z = bundle.getBoolean(a(5), false);
            boolean z2 = bundle.getBoolean(a(6), false);
            Bundle bundle3 = bundle.getBundle(a(7));
            MediaItem.LiveConfiguration fromBundle2 = bundle3 != null ? MediaItem.LiveConfiguration.CREATOR.fromBundle(bundle3) : null;
            boolean z3 = bundle.getBoolean(a(8), false);
            long j4 = bundle.getLong(a(9), 0L);
            long j5 = bundle.getLong(a(10), C.TIME_UNSET);
            int i = bundle.getInt(a(11), 0);
            int i2 = bundle.getInt(a(12), 0);
            long j6 = bundle.getLong(a(13), 0L);
            Window window = new Window();
            window.set(a, fromBundle, null, j, j2, j3, z, z2, fromBundle2, j4, j5, i, i2, j6);
            window.isPlaceholder = z3;
            return window;
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    /* loaded from: classes.dex */
    public static final class Period implements Bundleable {
        public static final Bundleable.Creator<Period> CREATOR = $$Lambda$Timeline$Period$Asr4kxTElRYeyAZNO0AnLAatJog.INSTANCE;
        private AdPlaybackState a = AdPlaybackState.NONE;
        public long durationUs;
        @Nullable
        public Object id;
        public boolean isPlaceholder;
        public long positionInWindowUs;
        @Nullable
        public Object uid;
        public int windowIndex;

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i, long j, long j2) {
            return set(obj, obj2, i, j, j2, AdPlaybackState.NONE, false);
        }

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i, long j, long j2, AdPlaybackState adPlaybackState, boolean z) {
            this.id = obj;
            this.uid = obj2;
            this.windowIndex = i;
            this.durationUs = j;
            this.positionInWindowUs = j2;
            this.a = adPlaybackState;
            this.isPlaceholder = z;
            return this;
        }

        public long getDurationMs() {
            return C.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInWindowMs() {
            return C.usToMs(this.positionInWindowUs);
        }

        public long getPositionInWindowUs() {
            return this.positionInWindowUs;
        }

        @Nullable
        public Object getAdsId() {
            return this.a.adsId;
        }

        public int getAdGroupCount() {
            return this.a.adGroupCount;
        }

        public int getRemovedAdGroupCount() {
            return this.a.removedAdGroupCount;
        }

        public long getAdGroupTimeUs(int i) {
            return this.a.getAdGroup(i).timeUs;
        }

        public int getFirstAdIndexToPlay(int i) {
            return this.a.getAdGroup(i).getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int i, int i2) {
            return this.a.getAdGroup(i).getNextAdIndexToPlay(i2);
        }

        public boolean hasPlayedAdGroup(int i) {
            return !this.a.getAdGroup(i).hasUnplayedAds();
        }

        public int getAdGroupIndexForPositionUs(long j) {
            return this.a.getAdGroupIndexForPositionUs(j, this.durationUs);
        }

        public int getAdGroupIndexAfterPositionUs(long j) {
            return this.a.getAdGroupIndexAfterPositionUs(j, this.durationUs);
        }

        public int getAdCountInAdGroup(int i) {
            return this.a.getAdGroup(i).count;
        }

        public long getAdDurationUs(int i, int i2) {
            AdPlaybackState.AdGroup adGroup = this.a.getAdGroup(i);
            return adGroup.count != -1 ? adGroup.durationsUs[i2] : C.TIME_UNSET;
        }

        public long getAdResumePositionUs() {
            return this.a.adResumePositionUs;
        }

        public boolean isServerSideInsertedAdGroup(int i) {
            return this.a.getAdGroup(i).isServerSideInserted;
        }

        public long getContentResumeOffsetUs(int i) {
            return this.a.getAdGroup(i).contentResumeOffsetUs;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Period period = (Period) obj;
            return Util.areEqual(this.id, period.id) && Util.areEqual(this.uid, period.uid) && this.windowIndex == period.windowIndex && this.durationUs == period.durationUs && this.positionInWindowUs == period.positionInWindowUs && this.isPlaceholder == period.isPlaceholder && Util.areEqual(this.a, period.a);
        }

        public int hashCode() {
            Object obj = this.id;
            int i = 0;
            int hashCode = (217 + (obj == null ? 0 : obj.hashCode())) * 31;
            Object obj2 = this.uid;
            if (obj2 != null) {
                i = obj2.hashCode();
            }
            long j = this.durationUs;
            long j2 = this.positionInWindowUs;
            return ((((((((((hashCode + i) * 31) + this.windowIndex) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (this.isPlaceholder ? 1 : 0)) * 31) + this.a.hashCode();
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(a(0), this.windowIndex);
            bundle.putLong(a(1), this.durationUs);
            bundle.putLong(a(2), this.positionInWindowUs);
            bundle.putBoolean(a(3), this.isPlaceholder);
            bundle.putBundle(a(4), this.a.toBundle());
            return bundle;
        }

        public static Period a(Bundle bundle) {
            AdPlaybackState adPlaybackState;
            int i = bundle.getInt(a(0), 0);
            long j = bundle.getLong(a(1), C.TIME_UNSET);
            long j2 = bundle.getLong(a(2), 0L);
            boolean z = bundle.getBoolean(a(3));
            Bundle bundle2 = bundle.getBundle(a(4));
            if (bundle2 != null) {
                adPlaybackState = AdPlaybackState.CREATOR.fromBundle(bundle2);
            } else {
                adPlaybackState = AdPlaybackState.NONE;
            }
            Period period = new Period();
            period.set(null, null, i, j, j2, adPlaybackState, z);
            return period;
        }

        private static String a(int i) {
            return Integer.toString(i, 36);
        }
    }

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public int getNextWindowIndex(int i, int i2, boolean z) {
        switch (i2) {
            case 0:
                if (i == getLastWindowIndex(z)) {
                    return -1;
                }
                return i + 1;
            case 1:
                return i;
            case 2:
                return i == getLastWindowIndex(z) ? getFirstWindowIndex(z) : i + 1;
            default:
                throw new IllegalStateException();
        }
    }

    public int getPreviousWindowIndex(int i, int i2, boolean z) {
        switch (i2) {
            case 0:
                if (i == getFirstWindowIndex(z)) {
                    return -1;
                }
                return i - 1;
            case 1:
                return i;
            case 2:
                return i == getFirstWindowIndex(z) ? getLastWindowIndex(z) : i - 1;
            default:
                throw new IllegalStateException();
        }
    }

    public int getLastWindowIndex(boolean z) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public int getFirstWindowIndex(boolean z) {
        return isEmpty() ? -1 : 0;
    }

    public final Window getWindow(int i, Window window) {
        return getWindow(i, window, 0L);
    }

    public final int getNextPeriodIndex(int i, Period period, Window window, int i2, boolean z) {
        int i3 = getPeriod(i, period).windowIndex;
        if (getWindow(i3, window).lastPeriodIndex != i) {
            return i + 1;
        }
        int nextWindowIndex = getNextWindowIndex(i3, i2, z);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).firstPeriodIndex;
    }

    public final boolean isLastPeriod(int i, Period period, Window window, int i2, boolean z) {
        return getNextPeriodIndex(i, period, window, i2, z) == -1;
    }

    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i, long j) {
        return (Pair) Assertions.checkNotNull(getPeriodPosition(window, period, i, j, 0L));
    }

    @Nullable
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i, long j, long j2) {
        Assertions.checkIndex(i, 0, getWindowCount());
        getWindow(i, window, j2);
        if (j == C.TIME_UNSET) {
            j = window.getDefaultPositionUs();
            if (j == C.TIME_UNSET) {
                return null;
            }
        }
        int i2 = window.firstPeriodIndex;
        getPeriod(i2, period);
        while (i2 < window.lastPeriodIndex && period.positionInWindowUs != j) {
            int i3 = i2 + 1;
            if (getPeriod(i3, period).positionInWindowUs > j) {
                break;
            }
            i2 = i3;
        }
        getPeriod(i2, period, true);
        long j3 = j - period.positionInWindowUs;
        if (period.durationUs != C.TIME_UNSET) {
            j3 = Math.min(j3, period.durationUs - 1);
        }
        long max = Math.max(0L, j3);
        if (max == 9) {
            Log.e("XXX", "YYY");
        }
        return Pair.create(Assertions.checkNotNull(period.uid), Long.valueOf(max));
    }

    public Period getPeriodByUid(Object obj, Period period) {
        return getPeriod(getIndexOfPeriod(obj), period, true);
    }

    public final Period getPeriod(int i, Period period) {
        return getPeriod(i, period, false);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Timeline)) {
            return false;
        }
        Timeline timeline = (Timeline) obj;
        if (!(timeline.getWindowCount() == getWindowCount() && timeline.getPeriodCount() == getPeriodCount())) {
            return false;
        }
        Window window = new Window();
        Period period = new Period();
        Window window2 = new Window();
        Period period2 = new Period();
        for (int i = 0; i < getWindowCount(); i++) {
            if (!getWindow(i, window).equals(timeline.getWindow(i, window2))) {
                return false;
            }
        }
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            if (!getPeriod(i2, period, true).equals(timeline.getPeriod(i2, period2, true))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        Window window = new Window();
        Period period = new Period();
        int windowCount = 217 + getWindowCount();
        for (int i = 0; i < getWindowCount(); i++) {
            windowCount = (windowCount * 31) + getWindow(i, window).hashCode();
        }
        int periodCount = (windowCount * 31) + getPeriodCount();
        for (int i2 = 0; i2 < getPeriodCount(); i2++) {
            periodCount = (periodCount * 31) + getPeriod(i2, period, true).hashCode();
        }
        return periodCount;
    }

    public final Bundle toBundle(boolean z) {
        ArrayList arrayList = new ArrayList();
        int windowCount = getWindowCount();
        Window window = new Window();
        for (int i = 0; i < windowCount; i++) {
            arrayList.add(getWindow(i, window, 0L).a(z));
        }
        ArrayList arrayList2 = new ArrayList();
        int periodCount = getPeriodCount();
        Period period = new Period();
        for (int i2 = 0; i2 < periodCount; i2++) {
            arrayList2.add(getPeriod(i2, period, false).toBundle());
        }
        int[] iArr = new int[windowCount];
        if (windowCount > 0) {
            iArr[0] = getFirstWindowIndex(true);
        }
        for (int i3 = 1; i3 < windowCount; i3++) {
            iArr[i3] = getNextWindowIndex(iArr[i3 - 1], 0, true);
        }
        Bundle bundle = new Bundle();
        BundleUtil.putBinder(bundle, a(0), new BundleListRetriever(arrayList));
        BundleUtil.putBinder(bundle, a(1), new BundleListRetriever(arrayList2));
        bundle.putIntArray(a(2), iArr);
        return bundle;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public final Bundle toBundle() {
        return toBundle(false);
    }

    public static Timeline a(Bundle bundle) {
        ImmutableList a = a(Window.CREATOR, BundleUtil.getBinder(bundle, a(0)));
        ImmutableList a2 = a(Period.CREATOR, BundleUtil.getBinder(bundle, a(1)));
        int[] intArray = bundle.getIntArray(a(2));
        if (intArray == null) {
            intArray = b(a.size());
        }
        return new RemotableTimeline(a, a2, intArray);
    }

    private static <T extends Bundleable> ImmutableList<T> a(Bundleable.Creator<T> creator, @Nullable IBinder iBinder) {
        if (iBinder == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = new ImmutableList.Builder();
        ImmutableList<Bundle> list = BundleListRetriever.getList(iBinder);
        for (int i = 0; i < list.size(); i++) {
            builder.add((ImmutableList.Builder) creator.fromBundle(list.get(i)));
        }
        return builder.build();
    }

    private static String a(int i) {
        return Integer.toString(i, 36);
    }

    private static int[] b(int i) {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = i2;
        }
        return iArr;
    }

    /* loaded from: classes.dex */
    public static final class RemotableTimeline extends Timeline {
        private final ImmutableList<Window> a;
        private final ImmutableList<Period> b;
        private final int[] c;
        private final int[] d;

        public RemotableTimeline(ImmutableList<Window> immutableList, ImmutableList<Period> immutableList2, int[] iArr) {
            Assertions.checkArgument(immutableList.size() == iArr.length);
            this.a = immutableList;
            this.b = immutableList2;
            this.c = iArr;
            this.d = new int[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                this.d[iArr[i]] = i;
            }
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.a.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int i, Window window, long j) {
            Window window2 = this.a.get(i);
            window.set(window2.uid, window2.mediaItem, window2.manifest, window2.presentationStartTimeMs, window2.windowStartTimeMs, window2.elapsedRealtimeEpochOffsetMs, window2.isSeekable, window2.isDynamic, window2.liveConfiguration, window2.defaultPositionUs, window2.durationUs, window2.firstPeriodIndex, window2.lastPeriodIndex, window2.positionInFirstPeriodUs);
            window.isPlaceholder = window2.isPlaceholder;
            return window;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int i, int i2, boolean z) {
            if (i2 == 1) {
                return i;
            }
            if (i != getLastWindowIndex(z)) {
                return z ? this.c[this.d[i] + 1] : i + 1;
            }
            if (i2 == 2) {
                return getFirstWindowIndex(z);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int i, int i2, boolean z) {
            if (i2 == 1) {
                return i;
            }
            if (i != getFirstWindowIndex(z)) {
                return z ? this.c[this.d[i] - 1] : i - 1;
            }
            if (i2 == 2) {
                return getLastWindowIndex(z);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getLastWindowIndex(boolean z) {
            if (isEmpty()) {
                return -1;
            }
            if (z) {
                return this.c[getWindowCount() - 1];
            }
            return getWindowCount() - 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getFirstWindowIndex(boolean z) {
            if (isEmpty()) {
                return -1;
            }
            if (z) {
                return this.c[0];
            }
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.b.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int i, Period period, boolean z) {
            Period period2 = this.b.get(i);
            period.set(period2.id, period2.uid, period2.windowIndex, period2.durationUs, period2.positionInWindowUs, period2.a, period2.isPlaceholder);
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i) {
            throw new UnsupportedOperationException();
        }
    }
}
