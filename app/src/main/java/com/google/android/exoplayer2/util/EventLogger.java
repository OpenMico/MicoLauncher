package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import okhttp3.HttpUrl;
import okhttp3.internal.cache.DiskLruCache;
import org.apache.commons.codec.language.bm.Rule;

/* loaded from: classes2.dex */
public class EventLogger implements AnalyticsListener {
    private static final NumberFormat a = NumberFormat.getInstance(Locale.US);
    @Nullable
    private final MappingTrackSelector b;
    private final String c;
    private final Timeline.Window d;
    private final Timeline.Period e;
    private final long f;

    private static String a(int i) {
        switch (i) {
            case 1:
                return PlayerEvent.MUSIC_SOURCE_IDLE;
            case 2:
                return "BUFFERING";
            case 3:
                return "READY";
            case 4:
                return "ENDED";
            default:
                return "?";
        }
    }

    private static String a(boolean z) {
        return z ? "[X]" : "[ ]";
    }

    private static String b(int i) {
        switch (i) {
            case 0:
                return "OFF";
            case 1:
                return "ONE";
            case 2:
                return Rule.ALL;
            default:
                return "?";
        }
    }

    private static String c(int i) {
        switch (i) {
            case 0:
                return "AUTO_TRANSITION";
            case 1:
                return "SEEK";
            case 2:
                return "SEEK_ADJUSTMENT";
            case 3:
                return "SKIP";
            case 4:
                return DiskLruCache.REMOVE;
            case 5:
                return "INTERNAL";
            default:
                return "?";
        }
    }

    private static String d(int i) {
        switch (i) {
            case 0:
                return "PLAYLIST_CHANGED";
            case 1:
                return "SOURCE_UPDATE";
            default:
                return "?";
        }
    }

    private static String e(int i) {
        switch (i) {
            case 0:
                return "REPEAT";
            case 1:
                return "AUTO";
            case 2:
                return "SEEK";
            case 3:
                return "PLAYLIST_CHANGED";
            default:
                return "?";
        }
    }

    private static String f(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "TRANSIENT_AUDIO_FOCUS_LOSS";
            default:
                return "?";
        }
    }

    private static String g(int i) {
        switch (i) {
            case 1:
                return "USER_REQUEST";
            case 2:
                return "AUDIO_FOCUS_LOSS";
            case 3:
                return "AUDIO_BECOMING_NOISY";
            case 4:
                return "REMOTE";
            case 5:
                return "END_OF_MEDIA_ITEM";
            default:
                return "?";
        }
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    static {
        a.setMinimumFractionDigits(2);
        a.setMaximumFractionDigits(2);
        a.setGroupingUsed(false);
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector) {
        this(mappingTrackSelector, "EventLogger");
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector, String str) {
        this.b = mappingTrackSelector;
        this.c = str;
        this.d = new Timeline.Window();
        this.e = new Timeline.Period();
        this.f = SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        a(eventTime, "loading", Boolean.toString(z));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i) {
        a(eventTime, XiaomiOAuthConstants.EXTRA_STATE_2, a(i));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z, int i) {
        String g = g(i);
        StringBuilder sb = new StringBuilder(String.valueOf(g).length() + 7);
        sb.append(z);
        sb.append(", ");
        sb.append(g);
        a(eventTime, "playWhenReady", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i) {
        a(eventTime, "playbackSuppressionReason", f(i));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        a(eventTime, "isPlaying", Boolean.toString(z));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i) {
        a(eventTime, "repeatMode", b(i));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        a(eventTime, "shuffleModeEnabled", Boolean.toString(z));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("reason=");
        sb.append(c(i));
        sb.append(", PositionInfo:old [");
        sb.append("window=");
        sb.append(positionInfo.windowIndex);
        sb.append(", period=");
        sb.append(positionInfo.periodIndex);
        sb.append(", pos=");
        sb.append(positionInfo.positionMs);
        if (positionInfo.adGroupIndex != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo.contentPositionMs);
            sb.append(", adGroup=");
            sb.append(positionInfo.adGroupIndex);
            sb.append(", ad=");
            sb.append(positionInfo.adIndexInAdGroup);
        }
        sb.append("], PositionInfo:new [");
        sb.append("window=");
        sb.append(positionInfo2.windowIndex);
        sb.append(", period=");
        sb.append(positionInfo2.periodIndex);
        sb.append(", pos=");
        sb.append(positionInfo2.positionMs);
        if (positionInfo2.adGroupIndex != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo2.contentPositionMs);
            sb.append(", adGroup=");
            sb.append(positionInfo2.adGroupIndex);
            sb.append(", ad=");
            sb.append(positionInfo2.adIndexInAdGroup);
        }
        sb.append("]");
        a(eventTime, "positionDiscontinuity", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        a(eventTime, "playbackParameters", playbackParameters.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i) {
        int periodCount = eventTime.timeline.getPeriodCount();
        int windowCount = eventTime.timeline.getWindowCount();
        String a2 = a(eventTime);
        String d = d(i);
        StringBuilder sb = new StringBuilder(String.valueOf(a2).length() + 69 + String.valueOf(d).length());
        sb.append("timeline [");
        sb.append(a2);
        sb.append(", periodCount=");
        sb.append(periodCount);
        sb.append(", windowCount=");
        sb.append(windowCount);
        sb.append(", reason=");
        sb.append(d);
        logd(sb.toString());
        for (int i2 = 0; i2 < Math.min(periodCount, 3); i2++) {
            eventTime.timeline.getPeriod(i2, this.e);
            String a3 = a(this.e.getDurationMs());
            StringBuilder sb2 = new StringBuilder(String.valueOf(a3).length() + 11);
            sb2.append("  period [");
            sb2.append(a3);
            sb2.append("]");
            logd(sb2.toString());
        }
        if (periodCount > 3) {
            logd("  ...");
        }
        for (int i3 = 0; i3 < Math.min(windowCount, 3); i3++) {
            eventTime.timeline.getWindow(i3, this.d);
            String a4 = a(this.d.getDurationMs());
            boolean z = this.d.isSeekable;
            boolean z2 = this.d.isDynamic;
            StringBuilder sb3 = new StringBuilder(String.valueOf(a4).length() + 42);
            sb3.append("  window [");
            sb3.append(a4);
            sb3.append(", seekable=");
            sb3.append(z);
            sb3.append(", dynamic=");
            sb3.append(z2);
            sb3.append("]");
            logd(sb3.toString());
        }
        if (windowCount > 3) {
            logd("  ...");
        }
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMediaItemTransition(AnalyticsListener.EventTime eventTime, @Nullable MediaItem mediaItem, int i) {
        String a2 = a(eventTime);
        String e = e(i);
        StringBuilder sb = new StringBuilder(String.valueOf(a2).length() + 21 + String.valueOf(e).length());
        sb.append("mediaItem [");
        sb.append(a2);
        sb.append(", reason=");
        sb.append(e);
        sb.append("]");
        logd(sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
        a(eventTime, "playerFailed", (Throwable) playbackException);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        MappingTrackSelector mappingTrackSelector = this.b;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = mappingTrackSelector != null ? mappingTrackSelector.getCurrentMappedTrackInfo() : null;
        if (currentMappedTrackInfo == null) {
            a(eventTime, "tracks", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        String valueOf = String.valueOf(a(eventTime));
        logd(valueOf.length() != 0 ? "tracks [".concat(valueOf) : new String("tracks ["));
        int rendererCount = currentMappedTrackInfo.getRendererCount();
        for (int i = 0; i < rendererCount; i++) {
            TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i);
            TrackSelection trackSelection = trackSelectionArray.get(i);
            if (trackGroups.length == 0) {
                String rendererName = currentMappedTrackInfo.getRendererName(i);
                StringBuilder sb = new StringBuilder(String.valueOf(rendererName).length() + 5);
                sb.append("  ");
                sb.append(rendererName);
                sb.append(" []");
                logd(sb.toString());
            } else {
                String rendererName2 = currentMappedTrackInfo.getRendererName(i);
                StringBuilder sb2 = new StringBuilder(String.valueOf(rendererName2).length() + 4);
                sb2.append("  ");
                sb2.append(rendererName2);
                sb2.append(" [");
                logd(sb2.toString());
                for (int i2 = 0; i2 < trackGroups.length; i2++) {
                    TrackGroup trackGroup = trackGroups.get(i2);
                    String a2 = a(trackGroup.length, currentMappedTrackInfo.getAdaptiveSupport(i, i2, false));
                    StringBuilder sb3 = new StringBuilder(String.valueOf(a2).length() + 44);
                    sb3.append("    Group:");
                    sb3.append(i2);
                    sb3.append(", adaptive_supported=");
                    sb3.append(a2);
                    sb3.append(" [");
                    logd(sb3.toString());
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        String a3 = a(trackSelection, trackGroup, i3);
                        String formatSupportString = C.getFormatSupportString(currentMappedTrackInfo.getTrackSupport(i, i2, i3));
                        String logString = Format.toLogString(trackGroup.getFormat(i3));
                        StringBuilder sb4 = new StringBuilder(String.valueOf(a3).length() + 38 + String.valueOf(logString).length() + String.valueOf(formatSupportString).length());
                        sb4.append("      ");
                        sb4.append(a3);
                        sb4.append(" Track:");
                        sb4.append(i3);
                        sb4.append(", ");
                        sb4.append(logString);
                        sb4.append(", supported=");
                        sb4.append(formatSupportString);
                        logd(sb4.toString());
                    }
                    logd("    ]");
                }
                if (trackSelection != null) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= trackSelection.length()) {
                            break;
                        }
                        Metadata metadata = trackSelection.getFormat(i4).metadata;
                        if (metadata != null) {
                            logd("    Metadata [");
                            a(metadata, "      ");
                            logd("    ]");
                            break;
                        }
                        i4++;
                    }
                }
                logd("  ]");
            }
        }
        TrackGroupArray unmappedTrackGroups = currentMappedTrackInfo.getUnmappedTrackGroups();
        if (unmappedTrackGroups.length > 0) {
            logd("  Unmapped [");
            for (int i5 = 0; i5 < unmappedTrackGroups.length; i5++) {
                StringBuilder sb5 = new StringBuilder(23);
                sb5.append("    Group:");
                sb5.append(i5);
                sb5.append(" [");
                logd(sb5.toString());
                TrackGroup trackGroup2 = unmappedTrackGroups.get(i5);
                for (int i6 = 0; i6 < trackGroup2.length; i6++) {
                    String a4 = a(false);
                    String formatSupportString2 = C.getFormatSupportString(0);
                    String logString2 = Format.toLogString(trackGroup2.getFormat(i6));
                    StringBuilder sb6 = new StringBuilder(String.valueOf(a4).length() + 38 + String.valueOf(logString2).length() + String.valueOf(formatSupportString2).length());
                    sb6.append("      ");
                    sb6.append(a4);
                    sb6.append(" Track:");
                    sb6.append(i6);
                    sb6.append(", ");
                    sb6.append(logString2);
                    sb6.append(", supported=");
                    sb6.append(formatSupportString2);
                    logd(sb6.toString());
                }
                logd("    ]");
            }
            logd("  ]");
        }
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        String valueOf = String.valueOf(a(eventTime));
        logd(valueOf.length() != 0 ? "metadata [".concat(valueOf) : new String("metadata ["));
        a(metadata, "  ");
        logd("]");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a(eventTime, "audioEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j) {
        a(eventTime, "audioDecoderInitialized", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        a(eventTime, "audioInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i, long j, long j2) {
        StringBuilder sb = new StringBuilder(55);
        sb.append(i);
        sb.append(", ");
        sb.append(j);
        sb.append(", ");
        sb.append(j2);
        a(eventTime, "audioTrackUnderrun", sb.toString(), null);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        a(eventTime, "audioDecoderReleased", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a(eventTime, "audioDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i) {
        a(eventTime, "audioSessionId", Integer.toString(i));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        int i = audioAttributes.contentType;
        int i2 = audioAttributes.flags;
        int i3 = audioAttributes.usage;
        int i4 = audioAttributes.allowedCapturePolicy;
        StringBuilder sb = new StringBuilder(47);
        sb.append(i);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(i2);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(i3);
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        sb.append(i4);
        a(eventTime, "audioAttributes", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z) {
        a(eventTime, "skipSilenceEnabled", Boolean.toString(z));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f) {
        a(eventTime, SchemaActivity.KEY_VOLUME, Float.toString(f));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a(eventTime, "videoEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j) {
        a(eventTime, "videoDecoderInitialized", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        a(eventTime, "videoInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i, long j) {
        a(eventTime, "droppedFrames", Integer.toString(i));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        a(eventTime, "videoDecoderReleased", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        a(eventTime, "videoDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j) {
        a(eventTime, "renderedFirstFrame", String.valueOf(obj));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        int i = videoSize.width;
        int i2 = videoSize.height;
        StringBuilder sb = new StringBuilder(24);
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        a(eventTime, "videoSize", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        a(eventTime, "loadError", (Exception) iOException);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i, int i2) {
        StringBuilder sb = new StringBuilder(24);
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        a(eventTime, "surfaceSize", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        a(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        a(eventTime, "downstreamFormat", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i) {
        StringBuilder sb = new StringBuilder(17);
        sb.append("state=");
        sb.append(i);
        a(eventTime, "drmSessionAcquired", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        a(eventTime, "drmSessionManagerError", exc);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        a(eventTime, "drmKeysRestored");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        a(eventTime, "drmKeysRemoved");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        a(eventTime, "drmKeysLoaded");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        a(eventTime, "drmSessionReleased");
    }

    protected void logd(String str) {
        Log.d(this.c, str);
    }

    protected void loge(String str) {
        Log.e(this.c, str);
    }

    private void a(AnalyticsListener.EventTime eventTime, String str) {
        logd(b(eventTime, str, null, null));
    }

    private void a(AnalyticsListener.EventTime eventTime, String str, String str2) {
        logd(b(eventTime, str, str2, null));
    }

    private void a(AnalyticsListener.EventTime eventTime, String str, @Nullable Throwable th) {
        loge(b(eventTime, str, null, th));
    }

    private void a(AnalyticsListener.EventTime eventTime, String str, String str2, @Nullable Throwable th) {
        loge(b(eventTime, str, str2, th));
    }

    private void a(AnalyticsListener.EventTime eventTime, String str, Exception exc) {
        a(eventTime, "internalError", str, exc);
    }

    private void a(Metadata metadata, String str) {
        for (int i = 0; i < metadata.length(); i++) {
            String valueOf = String.valueOf(metadata.get(i));
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + String.valueOf(valueOf).length());
            sb.append(str);
            sb.append(valueOf);
            logd(sb.toString());
        }
    }

    private String b(AnalyticsListener.EventTime eventTime, String str, @Nullable String str2, @Nullable Throwable th) {
        String a2 = a(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(a2).length());
        sb.append(str);
        sb.append(" [");
        sb.append(a2);
        String sb2 = sb.toString();
        if (th instanceof PlaybackException) {
            String valueOf = String.valueOf(sb2);
            String errorCodeName = ((PlaybackException) th).getErrorCodeName();
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 12 + String.valueOf(errorCodeName).length());
            sb3.append(valueOf);
            sb3.append(", errorCode=");
            sb3.append(errorCodeName);
            sb2 = sb3.toString();
        }
        if (str2 != null) {
            String valueOf2 = String.valueOf(sb2);
            StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf2).length() + 2 + String.valueOf(str2).length());
            sb4.append(valueOf2);
            sb4.append(", ");
            sb4.append(str2);
            sb2 = sb4.toString();
        }
        String throwableString = Log.getThrowableString(th);
        if (!TextUtils.isEmpty(throwableString)) {
            String valueOf3 = String.valueOf(sb2);
            String replace = throwableString.replace("\n", "\n  ");
            StringBuilder sb5 = new StringBuilder(String.valueOf(valueOf3).length() + 4 + String.valueOf(replace).length());
            sb5.append(valueOf3);
            sb5.append("\n  ");
            sb5.append(replace);
            sb5.append('\n');
            sb2 = sb5.toString();
        }
        return String.valueOf(sb2).concat("]");
    }

    private String a(AnalyticsListener.EventTime eventTime) {
        int i = eventTime.windowIndex;
        StringBuilder sb = new StringBuilder(18);
        sb.append("window=");
        sb.append(i);
        String sb2 = sb.toString();
        if (eventTime.mediaPeriodId != null) {
            String valueOf = String.valueOf(sb2);
            int indexOfPeriod = eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 20);
            sb3.append(valueOf);
            sb3.append(", period=");
            sb3.append(indexOfPeriod);
            sb2 = sb3.toString();
            if (eventTime.mediaPeriodId.isAd()) {
                String valueOf2 = String.valueOf(sb2);
                int i2 = eventTime.mediaPeriodId.adGroupIndex;
                StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf2).length() + 21);
                sb4.append(valueOf2);
                sb4.append(", adGroup=");
                sb4.append(i2);
                String valueOf3 = String.valueOf(sb4.toString());
                int i3 = eventTime.mediaPeriodId.adIndexInAdGroup;
                StringBuilder sb5 = new StringBuilder(String.valueOf(valueOf3).length() + 16);
                sb5.append(valueOf3);
                sb5.append(", ad=");
                sb5.append(i3);
                sb2 = sb5.toString();
            }
        }
        String a2 = a(eventTime.realtimeMs - this.f);
        String a3 = a(eventTime.eventPlaybackPositionMs);
        StringBuilder sb6 = new StringBuilder(String.valueOf(a2).length() + 23 + String.valueOf(a3).length() + String.valueOf(sb2).length());
        sb6.append("eventTime=");
        sb6.append(a2);
        sb6.append(", mediaPos=");
        sb6.append(a3);
        sb6.append(", ");
        sb6.append(sb2);
        return sb6.toString();
    }

    private static String a(long j) {
        return j == C.TIME_UNSET ? "?" : a.format(((float) j) / 1000.0f);
    }

    private static String a(int i, int i2) {
        if (i < 2) {
            return "N/A";
        }
        if (i2 == 0) {
            return "NO";
        }
        if (i2 == 8) {
            return "YES_NOT_SEAMLESS";
        }
        if (i2 == 16) {
            return "YES";
        }
        throw new IllegalStateException();
    }

    private static String a(@Nullable TrackSelection trackSelection, TrackGroup trackGroup, int i) {
        return a((trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i) == -1) ? false : true);
    }
}
