package com.google.android.exoplayer2.analytics;

import android.os.Looper;
import android.util.SparseArray;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public class AnalyticsCollector implements Player.Listener, AudioRendererEventListener, DrmSessionEventListener, MediaSourceEventListener, BandwidthMeter.EventListener, VideoRendererEventListener {
    private final Clock a;
    private final Timeline.Period b = new Timeline.Period();
    private final Timeline.Window c = new Timeline.Window();
    private final a d = new a(this.b);
    private final SparseArray<AnalyticsListener.EventTime> e = new SparseArray<>();
    private ListenerSet<AnalyticsListener> f;
    private Player g;
    private HandlerWrapper h;
    private boolean i;

    public static /* synthetic */ void a(AnalyticsListener analyticsListener, FlagSet flagSet) {
    }

    public AnalyticsCollector(Clock clock) {
        this.a = (Clock) Assertions.checkNotNull(clock);
        this.f = new ListenerSet<>(Util.getCurrentOrMainLooper(), clock, $$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk.INSTANCE);
    }

    @CallSuper
    public void addListener(AnalyticsListener analyticsListener) {
        Assertions.checkNotNull(analyticsListener);
        this.f.add(analyticsListener);
    }

    @CallSuper
    public void removeListener(AnalyticsListener analyticsListener) {
        this.f.remove(analyticsListener);
    }

    @CallSuper
    public void setPlayer(final Player player, Looper looper) {
        Assertions.checkState(this.g == null || this.d.b.isEmpty());
        this.g = (Player) Assertions.checkNotNull(player);
        this.h = this.a.createHandler(looper, null);
        this.f = this.f.copy(looper, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$AMXNpXttTxaF0JuxNps_VdxciRw
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                AnalyticsCollector.this.a(player, (AnalyticsListener) obj, flagSet);
            }
        });
    }

    public /* synthetic */ void a(Player player, AnalyticsListener analyticsListener, FlagSet flagSet) {
        analyticsListener.onEvents(player, new AnalyticsListener.Events(flagSet, this.e));
    }

    @CallSuper
    public void release() {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        this.e.put(AnalyticsListener.EVENT_PLAYER_RELEASED, generateCurrentPlayerMediaPeriodEventTime);
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, AnalyticsListener.EVENT_PLAYER_RELEASED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$EkEH1HNHjlGeAFbQUsX81aYuQuQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayerReleased(AnalyticsListener.EventTime.this);
            }
        });
        ((HandlerWrapper) Assertions.checkStateNotNull(this.h)).post(new Runnable() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$aegSipgKzHfh8F5vWeTWSmuRpyQ
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticsCollector.this.d();
            }
        });
    }

    public /* synthetic */ void d() {
        this.f.release();
    }

    public final void updateMediaPeriodQueueInfo(List<MediaSource.MediaPeriodId> list, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        this.d.a(list, mediaPeriodId, (Player) Assertions.checkNotNull(this.g));
    }

    public final void notifySeekStarted() {
        if (!this.i) {
            final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
            this.i = true;
            sendEvent(generateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$6ZuqBzEgTXPQ9sWoHcHCKKEHGgQ
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((AnalyticsListener) obj).onSeekStarted(AnalyticsListener.EventTime.this);
                }
            });
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioEnabled(final DecoderCounters decoderCounters) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1008, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$HdDaUIr3_2-Def6SAjw4oUEGdAc
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.d(AnalyticsListener.EventTime.this, decoderCounters, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void d(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioEnabled(eventTime, decoderCounters);
        analyticsListener.onDecoderEnabled(eventTime, 1, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDecoderInitialized(final String str, final long j, final long j2) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1009, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$AjCAMnR1nyAjtzDX9tjI51Qb2OY
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.b(AnalyticsListener.EventTime.this, str, j2, j, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void b(AnalyticsListener.EventTime eventTime, String str, long j, long j2, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioDecoderInitialized(eventTime, str, j);
        analyticsListener.onAudioDecoderInitialized(eventTime, str, j2, j);
        analyticsListener.onDecoderInitialized(eventTime, 1, str, j);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioInputFormatChanged(final Format format, @Nullable final DecoderReuseEvaluation decoderReuseEvaluation) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1010, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$_GuzBRJEDhEZhVnzEr-Uqn1P-cw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.b(AnalyticsListener.EventTime.this, format, decoderReuseEvaluation, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void b(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioInputFormatChanged(eventTime, format);
        analyticsListener.onAudioInputFormatChanged(eventTime, format, decoderReuseEvaluation);
        analyticsListener.onDecoderInputFormatChanged(eventTime, 1, format);
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioPositionAdvancing(final long j) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1011, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$MwllaMPRMMgNGa9OBKb7QRGWjsc
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioPositionAdvancing(AnalyticsListener.EventTime.this, j);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioUnderrun(final int i, final long j, final long j2) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1012, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$wUF9jP0EwORPatA5f2PTKOwhA3g
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioUnderrun(AnalyticsListener.EventTime.this, i, j, j2);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDecoderReleased(final String str) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1013, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$u6XrK2XQlOzqEBRgP6NqRR2Xqyg
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioDecoderReleased(AnalyticsListener.EventTime.this, str);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDisabled(final DecoderCounters decoderCounters) {
        final AnalyticsListener.EventTime a2 = a();
        sendEvent(a2, 1014, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$FdsoYQGPz9C8qnAQVpoSrph7UlY
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.c(AnalyticsListener.EventTime.this, decoderCounters, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void c(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onAudioDisabled(eventTime, decoderCounters);
        analyticsListener.onDecoderDisabled(eventTime, 1, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onSkipSilenceEnabledChanged(final boolean z) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1017, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$DzcHwFMMD_jgGU8kUhRQ8wLiN1M
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSkipSilenceEnabledChanged(AnalyticsListener.EventTime.this, z);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioSinkError(final Exception exc) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1018, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$I1mF3KIrvsnReubaYGkjdi6WW4w
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioSinkError(AnalyticsListener.EventTime.this, exc);
            }
        });
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioCodecError(final Exception exc) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_AUDIO_CODEC_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$cJfY3hsVvD6VhL8qbYNhRIPJNys
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioCodecError(AnalyticsListener.EventTime.this, exc);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onAudioSessionIdChanged(final int i) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1015, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$F7ptDXQ3dw9WGEwSBM3UsvdUqZQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioSessionIdChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onAudioAttributesChanged(final AudioAttributes audioAttributes) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1016, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$ZDQLnLr4pm4qVkI4q8IEjbGiyN0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAudioAttributesChanged(AnalyticsListener.EventTime.this, audioAttributes);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.audio.AudioListener
    public final void onVolumeChanged(final float f) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1019, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$iWnzFPjfV1fHuhHBi3HygnJDwb8
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVolumeChanged(AnalyticsListener.EventTime.this, f);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoEnabled(final DecoderCounters decoderCounters) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1020, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$4g9jRoMwE15N0RUcaFv5lEqXiEY
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.b(AnalyticsListener.EventTime.this, decoderCounters, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void b(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoEnabled(eventTime, decoderCounters);
        analyticsListener.onDecoderEnabled(eventTime, 2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDecoderInitialized(final String str, final long j, final long j2) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1021, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$HsG6n7CCL8Re1rAnTiEA4XRd0zE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, str, j2, j, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, String str, long j, long j2, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoDecoderInitialized(eventTime, str, j);
        analyticsListener.onVideoDecoderInitialized(eventTime, str, j2, j);
        analyticsListener.onDecoderInitialized(eventTime, 2, str, j);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoInputFormatChanged(final Format format, @Nullable final DecoderReuseEvaluation decoderReuseEvaluation) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_VIDEO_INPUT_FORMAT_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$ucCzCZo3r8BhikKW_kni-AfRxG0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, format, decoderReuseEvaluation, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, Format format, DecoderReuseEvaluation decoderReuseEvaluation, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoInputFormatChanged(eventTime, format);
        analyticsListener.onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation);
        analyticsListener.onDecoderInputFormatChanged(eventTime, 2, format);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onDroppedFrames(final int i, final long j) {
        final AnalyticsListener.EventTime a2 = a();
        sendEvent(a2, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$hgcDsVsMdtMLE43bjmO_yo9FpKs
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDroppedVideoFrames(AnalyticsListener.EventTime.this, i, j);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDecoderReleased(final String str) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, 1024, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$1QqzECPrtA1Lqjqh6iBN9xx8VKM
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoDecoderReleased(AnalyticsListener.EventTime.this, str);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDisabled(final DecoderCounters decoderCounters) {
        final AnalyticsListener.EventTime a2 = a();
        sendEvent(a2, 1025, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$xELQTHI8XwhhmT11R2NROcX3iLo
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, decoderCounters, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoDisabled(eventTime, decoderCounters);
        analyticsListener.onDecoderDisabled(eventTime, 2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
    public final void onVideoSizeChanged(final VideoSize videoSize) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_VIDEO_SIZE_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$pfPe6i6aSPLIFNd_ZUAZIzfqjXw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, videoSize, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, VideoSize videoSize, AnalyticsListener analyticsListener) {
        analyticsListener.onVideoSizeChanged(eventTime, videoSize);
        analyticsListener.onVideoSizeChanged(eventTime, videoSize.width, videoSize.height, videoSize.unappliedRotationDegrees, videoSize.pixelWidthHeightRatio);
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onRenderedFirstFrame(final Object obj, final long j) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_RENDERED_FIRST_FRAME, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$PWsF7Ko7hafvEJY6BrGqBkVTPlY
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj2) {
                ((AnalyticsListener) obj2).onRenderedFirstFrame(AnalyticsListener.EventTime.this, obj, j);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoFrameProcessingOffset(final long j, final int i) {
        final AnalyticsListener.EventTime a2 = a();
        sendEvent(a2, AnalyticsListener.EVENT_VIDEO_FRAME_PROCESSING_OFFSET, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$WNRzw-XRYwnskRnMh0Xeknl2gFs
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoFrameProcessingOffset(AnalyticsListener.EventTime.this, j, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoCodecError(final Exception exc) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_VIDEO_CODEC_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$bF1rE4F0flM-vsqmDsk-DT7CvOA
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onVideoCodecError(AnalyticsListener.EventTime.this, exc);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.video.VideoListener
    public void onSurfaceSizeChanged(final int i, final int i2) {
        final AnalyticsListener.EventTime b = b();
        sendEvent(b, AnalyticsListener.EVENT_SURFACE_SIZE_CHANGED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$An86_wNmoJd65QzKIVsNHMHnRCE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSurfaceSizeChanged(AnalyticsListener.EventTime.this, i, i2);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadStarted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1000, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$wH73sEDYf0ze7gkGeoPEVcoHvLQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadStarted(AnalyticsListener.EventTime.this, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCompleted(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1001, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$5ZphVETKwAKs5FTqUlRBZLYi0-U
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadCompleted(AnalyticsListener.EventTime.this, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCanceled(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1002, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$rvjnaw2sGxN_VMkyu12N2PZX7xQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadCanceled(AnalyticsListener.EventTime.this, loadEventInfo, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData, final IOException iOException, final boolean z) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1003, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$-WnM8E8jSDfOSUi1oloYzKvvZ1M
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onLoadError(AnalyticsListener.EventTime.this, loadEventInfo, mediaLoadData, iOException, z);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onUpstreamDiscarded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1005, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$jLkq5W_m8qVy5pFLKDuRxQ7iIes
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onUpstreamDiscarded(AnalyticsListener.EventTime.this, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onDownstreamFormatChanged(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final MediaLoadData mediaLoadData) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, 1004, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$u_v7nB4WRX7WQZM_ypC5-_el1Zw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDownstreamFormatChanged(AnalyticsListener.EventTime.this, mediaLoadData);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onTimelineChanged(Timeline timeline, final int i) {
        this.d.b((Player) Assertions.checkNotNull(this.g));
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 0, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$uCejSeArPk_rSNumaVQahV0HvTU
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onTimelineChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onMediaItemTransition(@Nullable final MediaItem mediaItem, final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$In5RUtI3z50-v3Nwr7itmyqF8uc
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMediaItemTransition(AnalyticsListener.EventTime.this, mediaItem, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onTracksChanged(final TrackGroupArray trackGroupArray, final TrackSelectionArray trackSelectionArray) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$-B2a7CbTjVTa3rS8ufLqixPfmsE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onTracksChanged(AnalyticsListener.EventTime.this, trackGroupArray, trackSelectionArray);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    @Deprecated
    public final void onStaticMetadataChanged(final List<Metadata> list) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$Zmj7GbKkh3-oVEX4zeZulLZcQ8k
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onStaticMetadataChanged(AnalyticsListener.EventTime.this, list);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onIsLoadingChanged(final boolean z) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$S62OVVaPd_sSymAtc7RNjMe1h1M
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.c(AnalyticsListener.EventTime.this, z, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void c(AnalyticsListener.EventTime eventTime, boolean z, AnalyticsListener analyticsListener) {
        analyticsListener.onLoadingChanged(eventTime, z);
        analyticsListener.onIsLoadingChanged(eventTime, z);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onAvailableCommandsChanged(final Player.Commands commands) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$cPVDndQOGwnROjhL7yu698HKwO4
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onAvailableCommandsChanged(AnalyticsListener.EventTime.this, commands);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlayerStateChanged(final boolean z, final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$4S3tim4FYrExAEWG0g-4wwWUlgE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayerStateChanged(AnalyticsListener.EventTime.this, z, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 5, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$eb7qcGPBusVDUfxu4fhnyyDWlOA
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackStateChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(final boolean z, final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$4YhbcNhgafm-0YTyANNPpdC03HM
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlayWhenReadyChanged(AnalyticsListener.EventTime.this, z, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackSuppressionReasonChanged(final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$pg7n7eLH2kjFtDv2yk4gWv_cKoo
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onIsPlayingChanged(final boolean z) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 8, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$upRE5816cz-FuZvmTTlW4bq_ccE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onIsPlayingChanged(AnalyticsListener.EventTime.this, z);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onRepeatModeChanged(final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 9, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$eJVkUk0gN5aL4NarKnoXjk3rymg
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onRepeatModeChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onShuffleModeEnabledChanged(final boolean z) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$0BvzBztvgRClosJeFciRPIn1l-M
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onShuffleModeChanged(AnalyticsListener.EventTime.this, z);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001a  */
    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPlayerError(final com.google.android.exoplayer2.PlaybackException r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof com.google.android.exoplayer2.ExoPlaybackException
            if (r0 == 0) goto L_0x0017
            r0 = r4
            com.google.android.exoplayer2.ExoPlaybackException r0 = (com.google.android.exoplayer2.ExoPlaybackException) r0
            com.google.android.exoplayer2.source.MediaPeriodId r1 = r0.mediaPeriodId
            if (r1 == 0) goto L_0x0017
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r1 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId
            com.google.android.exoplayer2.source.MediaPeriodId r0 = r0.mediaPeriodId
            r1.<init>(r0)
            com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime r0 = r3.a(r1)
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            if (r0 != 0) goto L_0x001e
            com.google.android.exoplayer2.analytics.AnalyticsListener$EventTime r0 = r3.generateCurrentPlayerMediaPeriodEventTime()
        L_0x001e:
            r1 = 11
            com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$n6DSHl61HKrVf-bvJoZVDjfP9fk r2 = new com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$n6DSHl61HKrVf-bvJoZVDjfP9fk
            r2.<init>()
            r3.sendEvent(r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.analytics.AnalyticsCollector.onPlayerError(com.google.android.exoplayer2.PlaybackException):void");
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(final Player.PositionInfo positionInfo, final Player.PositionInfo positionInfo2, final int i) {
        if (i == 1) {
            this.i = false;
        }
        this.d.a((Player) Assertions.checkNotNull(this.g));
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$Ed0PseXUEjhmXCrqGQ2dRIPtQHQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, i, positionInfo, positionInfo2, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, int i, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, AnalyticsListener analyticsListener) {
        analyticsListener.onPositionDiscontinuity(eventTime, i);
        analyticsListener.onPositionDiscontinuity(eventTime, positionInfo, positionInfo2, i);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackParametersChanged(final PlaybackParameters playbackParameters) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 13, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$DLwwTfKlZOEfdjz79IDzrUkBBqE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaybackParametersChanged(AnalyticsListener.EventTime.this, playbackParameters);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onSeekBackIncrementChanged(final long j) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 17, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$LGEgcW8L4SkaTOsI-_o1XMT-vOE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekBackIncrementChanged(AnalyticsListener.EventTime.this, j);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onSeekForwardIncrementChanged(final long j) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 18, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$-Yb4JWk6S38ERxx4d7lXjciWN4I
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekForwardIncrementChanged(AnalyticsListener.EventTime.this, j);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onMaxSeekToPreviousPositionChanged(final int i) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 19, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$y6BG03WjXjKFyuEIg4A4mwlVWUM
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMaxSeekToPreviousPositionChanged(AnalyticsListener.EventTime.this, i);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onMediaMetadataChanged(final MediaMetadata mediaMetadata) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$nAgroaO6cMoqit8GUnQppGpNOcE
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMediaMetadataChanged(AnalyticsListener.EventTime.this, mediaMetadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public void onPlaylistMetadataChanged(final MediaMetadata mediaMetadata) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 16, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$2vLua55ZOiMQ3Ns60Retx_-UqPY
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onPlaylistMetadataChanged(AnalyticsListener.EventTime.this, mediaMetadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.metadata.MetadataOutput
    public final void onMetadata(final Metadata metadata) {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, 1007, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$9_ph3LLAdLrLgaYV5_7G17MpQaA
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onMetadata(AnalyticsListener.EventTime.this, metadata);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onSeekProcessed() {
        final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime = generateCurrentPlayerMediaPeriodEventTime();
        sendEvent(generateCurrentPlayerMediaPeriodEventTime, -1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$MaQUysLHk0UpH0n7TxxyUv83Diw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onSeekProcessed(AnalyticsListener.EventTime.this);
            }
        });
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter.EventListener
    public final void onBandwidthSample(final int i, final long j, final long j2) {
        final AnalyticsListener.EventTime c = c();
        sendEvent(c, 1006, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$rz-HQ4bF5bWmj7DjWl0LId5ACrA
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onBandwidthEstimate(AnalyticsListener.EventTime.this, i, j, j2);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionAcquired(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final int i2) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_SESSION_ACQUIRED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$eL2i9ZndJIk9J_IQ5u3h_l3rslc
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                AnalyticsCollector.a(AnalyticsListener.EventTime.this, i2, (AnalyticsListener) obj);
            }
        });
    }

    public static /* synthetic */ void a(AnalyticsListener.EventTime eventTime, int i, AnalyticsListener analyticsListener) {
        analyticsListener.onDrmSessionAcquired(eventTime);
        analyticsListener.onDrmSessionAcquired(eventTime, i);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysLoaded(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_KEYS_LOADED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$H14iwhBpA0XI91GuZHGsD0v8lRQ
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysLoaded(AnalyticsListener.EventTime.this);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionManagerError(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId, final Exception exc) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_SESSION_MANAGER_ERROR, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$NfuXTVmRHZQJXAklmsn_cXOBIAA
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmSessionManagerError(AnalyticsListener.EventTime.this, exc);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysRestored(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_KEYS_RESTORED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$UAv8ZBSDKPp-JJG04T8e19vrA4Y
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysRestored(AnalyticsListener.EventTime.this);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmKeysRemoved(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_KEYS_REMOVED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$qe-pzRhbCJKvZfwS3blaJwNzNIw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmKeysRemoved(AnalyticsListener.EventTime.this);
            }
        });
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public final void onDrmSessionReleased(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        final AnalyticsListener.EventTime a2 = a(i, mediaPeriodId);
        sendEvent(a2, AnalyticsListener.EVENT_DRM_SESSION_RELEASED, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$b84GiuJlR6Yb0rMH2S2DY5h1qBw
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((AnalyticsListener) obj).onDrmSessionReleased(AnalyticsListener.EventTime.this);
            }
        });
    }

    protected final void sendEvent(AnalyticsListener.EventTime eventTime, int i, ListenerSet.Event<AnalyticsListener> event) {
        this.e.put(i, eventTime);
        this.f.sendEvent(i, event);
    }

    protected final AnalyticsListener.EventTime generateCurrentPlayerMediaPeriodEventTime() {
        return a(this.d.a());
    }

    @RequiresNonNull({"player"})
    protected final AnalyticsListener.EventTime generateEventTime(Timeline timeline, int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaSource.MediaPeriodId mediaPeriodId2 = timeline.isEmpty() ? null : mediaPeriodId;
        long elapsedRealtime = this.a.elapsedRealtime();
        boolean z = true;
        boolean z2 = timeline.equals(this.g.getCurrentTimeline()) && i == this.g.getCurrentWindowIndex();
        long j = 0;
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (!(z2 && this.g.getCurrentAdGroupIndex() == mediaPeriodId2.adGroupIndex && this.g.getCurrentAdIndexInAdGroup() == mediaPeriodId2.adIndexInAdGroup)) {
                z = false;
            }
            if (z) {
                j = this.g.getCurrentPosition();
            }
        } else if (z2) {
            j = this.g.getContentPosition();
        } else if (!timeline.isEmpty()) {
            j = timeline.getWindow(i, this.c).getDefaultPositionMs();
        }
        return new AnalyticsListener.EventTime(elapsedRealtime, timeline, i, mediaPeriodId2, j, this.g.getCurrentTimeline(), this.g.getCurrentWindowIndex(), this.d.a(), this.g.getCurrentPosition(), this.g.getTotalBufferedDuration());
    }

    private AnalyticsListener.EventTime a(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.g);
        Timeline a2 = mediaPeriodId == null ? null : this.d.a(mediaPeriodId);
        if (mediaPeriodId != null && a2 != null) {
            return generateEventTime(a2, a2.getPeriodByUid(mediaPeriodId.periodUid, this.b).windowIndex, mediaPeriodId);
        }
        int currentWindowIndex = this.g.getCurrentWindowIndex();
        Timeline currentTimeline = this.g.getCurrentTimeline();
        if (!(currentWindowIndex < currentTimeline.getWindowCount())) {
            currentTimeline = Timeline.EMPTY;
        }
        return generateEventTime(currentTimeline, currentWindowIndex, null);
    }

    private AnalyticsListener.EventTime a() {
        return a(this.d.b());
    }

    private AnalyticsListener.EventTime b() {
        return a(this.d.c());
    }

    private AnalyticsListener.EventTime c() {
        return a(this.d.d());
    }

    private AnalyticsListener.EventTime a(int i, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        Assertions.checkNotNull(this.g);
        boolean z = true;
        if (mediaPeriodId != null) {
            if (this.d.a(mediaPeriodId) == null) {
                z = false;
            }
            if (z) {
                return a(mediaPeriodId);
            }
            return generateEventTime(Timeline.EMPTY, i, mediaPeriodId);
        }
        Timeline currentTimeline = this.g.getCurrentTimeline();
        if (i >= currentTimeline.getWindowCount()) {
            z = false;
        }
        if (!z) {
            currentTimeline = Timeline.EMPTY;
        }
        return generateEventTime(currentTimeline, i, null);
    }

    /* loaded from: classes.dex */
    public static final class a {
        private final Timeline.Period a;
        private ImmutableList<MediaSource.MediaPeriodId> b = ImmutableList.of();
        private ImmutableMap<MediaSource.MediaPeriodId, Timeline> c = ImmutableMap.of();
        @Nullable
        private MediaSource.MediaPeriodId d;
        private MediaSource.MediaPeriodId e;
        private MediaSource.MediaPeriodId f;

        public a(Timeline.Period period) {
            this.a = period;
        }

        @Nullable
        public MediaSource.MediaPeriodId a() {
            return this.d;
        }

        @Nullable
        public MediaSource.MediaPeriodId b() {
            return this.e;
        }

        @Nullable
        public MediaSource.MediaPeriodId c() {
            return this.f;
        }

        @Nullable
        public MediaSource.MediaPeriodId d() {
            if (this.b.isEmpty()) {
                return null;
            }
            return (MediaSource.MediaPeriodId) Iterables.getLast(this.b);
        }

        @Nullable
        public Timeline a(MediaSource.MediaPeriodId mediaPeriodId) {
            return this.c.get(mediaPeriodId);
        }

        public void a(Player player) {
            this.d = a(player, this.b, this.e, this.a);
        }

        public void b(Player player) {
            this.d = a(player, this.b, this.e, this.a);
            a(player.getCurrentTimeline());
        }

        public void a(List<MediaSource.MediaPeriodId> list, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Player player) {
            this.b = ImmutableList.copyOf((Collection) list);
            if (!list.isEmpty()) {
                this.e = list.get(0);
                this.f = (MediaSource.MediaPeriodId) Assertions.checkNotNull(mediaPeriodId);
            }
            if (this.d == null) {
                this.d = a(player, this.b, this.e, this.a);
            }
            a(player.getCurrentTimeline());
        }

        private void a(Timeline timeline) {
            ImmutableMap.Builder<MediaSource.MediaPeriodId, Timeline> builder = ImmutableMap.builder();
            if (this.b.isEmpty()) {
                a(builder, this.e, timeline);
                if (!Objects.equal(this.f, this.e)) {
                    a(builder, this.f, timeline);
                }
                if (!Objects.equal(this.d, this.e) && !Objects.equal(this.d, this.f)) {
                    a(builder, this.d, timeline);
                }
            } else {
                for (int i = 0; i < this.b.size(); i++) {
                    a(builder, this.b.get(i), timeline);
                }
                if (!this.b.contains(this.d)) {
                    a(builder, this.d, timeline);
                }
            }
            this.c = builder.build();
        }

        private void a(ImmutableMap.Builder<MediaSource.MediaPeriodId, Timeline> builder, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
            if (mediaPeriodId != null) {
                if (timeline.getIndexOfPeriod(mediaPeriodId.periodUid) != -1) {
                    builder.put(mediaPeriodId, timeline);
                    return;
                }
                Timeline timeline2 = this.c.get(mediaPeriodId);
                if (timeline2 != null) {
                    builder.put(mediaPeriodId, timeline2);
                }
            }
        }

        @Nullable
        private static MediaSource.MediaPeriodId a(Player player, ImmutableList<MediaSource.MediaPeriodId> immutableList, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Timeline.Period period) {
            Timeline currentTimeline = player.getCurrentTimeline();
            int currentPeriodIndex = player.getCurrentPeriodIndex();
            Object uidOfPeriod = currentTimeline.isEmpty() ? null : currentTimeline.getUidOfPeriod(currentPeriodIndex);
            int adGroupIndexAfterPositionUs = (player.isPlayingAd() || currentTimeline.isEmpty()) ? -1 : currentTimeline.getPeriod(currentPeriodIndex, period).getAdGroupIndexAfterPositionUs(C.msToUs(player.getCurrentPosition()) - period.getPositionInWindowUs());
            for (int i = 0; i < immutableList.size(); i++) {
                MediaSource.MediaPeriodId mediaPeriodId2 = immutableList.get(i);
                if (a(mediaPeriodId2, uidOfPeriod, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adGroupIndexAfterPositionUs)) {
                    return mediaPeriodId2;
                }
            }
            if (!immutableList.isEmpty() || mediaPeriodId == null || !a(mediaPeriodId, uidOfPeriod, player.isPlayingAd(), player.getCurrentAdGroupIndex(), player.getCurrentAdIndexInAdGroup(), adGroupIndexAfterPositionUs)) {
                return null;
            }
            return mediaPeriodId;
        }

        private static boolean a(MediaSource.MediaPeriodId mediaPeriodId, @Nullable Object obj, boolean z, int i, int i2, int i3) {
            if (!mediaPeriodId.periodUid.equals(obj)) {
                return false;
            }
            return (z && mediaPeriodId.adGroupIndex == i && mediaPeriodId.adIndexInAdGroup == i2) || (!z && mediaPeriodId.adGroupIndex == -1 && mediaPeriodId.nextAdGroupIndex == i3);
        }
    }
}
