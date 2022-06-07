package com.google.android.exoplayer2.source.ads;

import androidx.annotation.CheckResult;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaPeriodId;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class ServerSideInsertedAdsUtil {
    private ServerSideInsertedAdsUtil() {
    }

    @CheckResult
    public static AdPlaybackState addAdGroupToAdPlaybackState(AdPlaybackState adPlaybackState, long j, long j2, long j3) {
        long mediaPeriodPositionUsForContent = getMediaPeriodPositionUsForContent(j, -1, adPlaybackState);
        int i = adPlaybackState.removedAdGroupCount;
        while (i < adPlaybackState.adGroupCount && adPlaybackState.getAdGroup(i).timeUs != Long.MIN_VALUE && adPlaybackState.getAdGroup(i).timeUs <= mediaPeriodPositionUsForContent) {
            i++;
        }
        long j4 = j2 - j;
        AdPlaybackState withContentResumeOffsetUs = adPlaybackState.withNewAdGroup(i, mediaPeriodPositionUsForContent).withIsServerSideInserted(i, true).withAdCount(i, 1).withAdDurationsUs(i, j4).withContentResumeOffsetUs(i, j3);
        long j5 = (-j4) + j3;
        for (int i2 = i + 1; i2 < withContentResumeOffsetUs.adGroupCount; i2++) {
            long j6 = withContentResumeOffsetUs.getAdGroup(i2).timeUs;
            if (j6 != Long.MIN_VALUE) {
                withContentResumeOffsetUs = withContentResumeOffsetUs.withAdGroupTimeUs(i2, j6 + j5);
            }
        }
        return withContentResumeOffsetUs;
    }

    public static long getStreamDurationUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        return period.durationUs == C.TIME_UNSET ? C.TIME_UNSET : getStreamPositionUsForContent(period.durationUs, -1, adPlaybackState);
    }

    public static long getStreamPositionUs(Player player, AdPlaybackState adPlaybackState) {
        Timeline currentTimeline = player.getCurrentTimeline();
        if (currentTimeline.isEmpty()) {
            return C.TIME_UNSET;
        }
        Timeline.Period period = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), new Timeline.Period());
        if (!Util.areEqual(period.getAdsId(), adPlaybackState.adsId)) {
            return C.TIME_UNSET;
        }
        if (!player.isPlayingAd()) {
            return getStreamPositionUsForContent(C.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs(), -1, adPlaybackState);
        }
        return getStreamPositionUsForAd(C.msToUs(player.getCurrentPosition()), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adPlaybackState);
    }

    public static long getStreamPositionUs(long j, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getStreamPositionUsForAd(j, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getStreamPositionUsForContent(j, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getMediaPeriodPositionUs(long j, MediaPeriodId mediaPeriodId, AdPlaybackState adPlaybackState) {
        if (mediaPeriodId.isAd()) {
            return getMediaPeriodPositionUsForAd(j, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState);
        }
        return getMediaPeriodPositionUsForContent(j, mediaPeriodId.nextAdGroupIndex, adPlaybackState);
    }

    public static long getStreamPositionUsForAd(long j, int i, int i2, AdPlaybackState adPlaybackState) {
        int i3;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
        long j2 = j + adGroup.timeUs;
        int i4 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i3 = 0;
            if (i4 >= i) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i4);
            while (i3 < getAdCountInGroup(adPlaybackState, i4)) {
                j2 += adGroup2.durationsUs[i3];
                i3++;
            }
            j2 -= adGroup2.contentResumeOffsetUs;
            i4++;
        }
        if (i2 < getAdCountInGroup(adPlaybackState, i)) {
            while (i3 < i2) {
                j2 += adGroup.durationsUs[i3];
                i3++;
            }
        }
        return j2;
    }

    public static long getMediaPeriodPositionUsForAd(long j, int i, int i2, AdPlaybackState adPlaybackState) {
        int i3;
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
        long j2 = j - adGroup.timeUs;
        int i4 = adPlaybackState.removedAdGroupCount;
        while (true) {
            i3 = 0;
            if (i4 >= i) {
                break;
            }
            AdPlaybackState.AdGroup adGroup2 = adPlaybackState.getAdGroup(i4);
            while (i3 < getAdCountInGroup(adPlaybackState, i4)) {
                j2 -= adGroup2.durationsUs[i3];
                i3++;
            }
            j2 += adGroup2.contentResumeOffsetUs;
            i4++;
        }
        if (i2 < getAdCountInGroup(adPlaybackState, i)) {
            while (i3 < i2) {
                j2 -= adGroup.durationsUs[i3];
                i3++;
            }
        }
        return j2;
    }

    public static long getStreamPositionUsForContent(long j, int i, AdPlaybackState adPlaybackState) {
        if (i == -1) {
            i = adPlaybackState.adGroupCount;
        }
        long j2 = 0;
        for (int i2 = adPlaybackState.removedAdGroupCount; i2 < i; i2++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
            if (adGroup.timeUs == Long.MIN_VALUE || adGroup.timeUs > j) {
                break;
            }
            long j3 = adGroup.timeUs + j2;
            for (int i3 = 0; i3 < getAdCountInGroup(adPlaybackState, i2); i3++) {
                j2 += adGroup.durationsUs[i3];
            }
            j2 -= adGroup.contentResumeOffsetUs;
            if (adGroup.timeUs + adGroup.contentResumeOffsetUs > j) {
                return Math.max(j3, j + j2);
            }
        }
        return j + j2;
    }

    public static long getMediaPeriodPositionUsForContent(long j, int i, AdPlaybackState adPlaybackState) {
        if (i == -1) {
            i = adPlaybackState.adGroupCount;
        }
        long j2 = 0;
        for (int i2 = adPlaybackState.removedAdGroupCount; i2 < i; i2++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
            if (adGroup.timeUs == Long.MIN_VALUE || adGroup.timeUs > j - j2) {
                break;
            }
            for (int i3 = 0; i3 < getAdCountInGroup(adPlaybackState, i2); i3++) {
                j2 += adGroup.durationsUs[i3];
            }
            j2 -= adGroup.contentResumeOffsetUs;
            long j3 = j - j2;
            if (adGroup.timeUs + adGroup.contentResumeOffsetUs > j3) {
                return Math.max(adGroup.timeUs, j3);
            }
        }
        return j - j2;
    }

    public static int getAdCountInGroup(AdPlaybackState adPlaybackState, int i) {
        AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i);
        if (adGroup.count == -1) {
            return 0;
        }
        return adGroup.count;
    }
}
