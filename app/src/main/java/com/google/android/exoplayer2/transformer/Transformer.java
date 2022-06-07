package com.google.android.exoplayer2.transformer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.transformer.FrameworkMuxer;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(18)
/* loaded from: classes2.dex */
public final class Transformer {
    public static final int PROGRESS_STATE_AVAILABLE = 1;
    public static final int PROGRESS_STATE_NO_TRANSFORMATION = 4;
    public static final int PROGRESS_STATE_UNAVAILABLE = 2;
    public static final int PROGRESS_STATE_WAITING_FOR_AVAILABILITY = 0;
    private final Context a;
    private final MediaSourceFactory b;
    private final Muxer.Factory c;
    private final g d;
    private final Looper e;
    private final Clock f;
    private Listener g;
    @Nullable
    private b h;
    @Nullable
    private SimpleExoPlayer i;
    private int j;

    /* loaded from: classes2.dex */
    public interface Listener {
        default void onTransformationCompleted(MediaItem mediaItem) {
        }

        default void onTransformationError(MediaItem mediaItem, Exception exc) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ProgressState {
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private Context a;
        private MediaSourceFactory b;
        private Muxer.Factory c;
        private boolean d;
        private boolean e;
        private boolean f;
        private String g;
        private Listener h;
        private Looper i;
        private Clock j;

        public Builder() {
            this.c = new FrameworkMuxer.Factory();
            this.g = "video/mp4";
            this.h = new Listener(this) { // from class: com.google.android.exoplayer2.transformer.Transformer.Builder.1
            };
            this.i = Util.getCurrentOrMainLooper();
            this.j = Clock.DEFAULT;
        }

        private Builder(Transformer transformer) {
            this.a = transformer.a;
            this.b = transformer.b;
            this.c = transformer.c;
            this.d = transformer.d.a;
            this.e = transformer.d.b;
            this.f = transformer.d.c;
            this.g = transformer.d.d;
            this.h = transformer.g;
            this.i = transformer.e;
            this.j = transformer.f;
        }

        public Builder setContext(Context context) {
            this.a = context.getApplicationContext();
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            this.b = mediaSourceFactory;
            return this;
        }

        public Builder setRemoveAudio(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setRemoveVideo(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setFlattenForSlowMotion(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setOutputMimeType(String str) {
            this.g = str;
            return this;
        }

        public Builder setListener(Listener listener) {
            this.h = listener;
            return this;
        }

        public Builder setLooper(Looper looper) {
            this.i = looper;
            return this;
        }

        public Transformer build() {
            Assertions.checkStateNotNull(this.a);
            if (this.b == null) {
                DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();
                if (this.f) {
                    defaultExtractorsFactory.setMp4ExtractorFlags(4);
                }
                this.b = new DefaultMediaSourceFactory(this.a, defaultExtractorsFactory);
            }
            boolean supportsOutputMimeType = this.c.supportsOutputMimeType(this.g);
            String valueOf = String.valueOf(this.g);
            Assertions.checkState(supportsOutputMimeType, valueOf.length() != 0 ? "Unsupported output MIME type: ".concat(valueOf) : new String("Unsupported output MIME type: "));
            return new Transformer(this.a, this.b, this.c, new g(this.d, this.e, this.f, this.g), this.h, this.i, this.j);
        }
    }

    private Transformer(Context context, MediaSourceFactory mediaSourceFactory, Muxer.Factory factory, g gVar, Listener listener, Looper looper, Clock clock) {
        Assertions.checkState(!gVar.a || !gVar.b, "Audio and video cannot both be removed.");
        this.a = context;
        this.b = mediaSourceFactory;
        this.c = factory;
        this.d = gVar;
        this.g = listener;
        this.e = looper;
        this.f = clock;
        this.j = 4;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public void setListener(Listener listener) {
        a();
        this.g = listener;
    }

    public void startTransformation(MediaItem mediaItem, String str) throws IOException {
        a(mediaItem, this.c.create(str, this.d.d));
    }

    @RequiresApi(26)
    public void startTransformation(MediaItem mediaItem, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        a(mediaItem, this.c.create(parcelFileDescriptor, this.d.d));
    }

    private void a(MediaItem mediaItem, Muxer muxer) {
        a();
        if (this.i == null) {
            b bVar = new b(muxer);
            this.h = bVar;
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(this.a);
            defaultTrackSelector.setParameters(new DefaultTrackSelector.ParametersBuilder(this.a).setForceHighestSupportedBitrate(true).build());
            this.i = new SimpleExoPlayer.Builder(this.a, new b(bVar, this.d)).setMediaSourceFactory(this.b).setTrackSelector(defaultTrackSelector).setLoadControl(new DefaultLoadControl.Builder().setBufferDurationsMs(50000, 50000, 250, 500).build()).setLooper(this.e).setClock(this.f).build();
            this.i.setMediaItem(mediaItem);
            this.i.addAnalyticsListener(new a(mediaItem, bVar));
            this.i.prepare();
            this.j = 0;
            return;
        }
        throw new IllegalStateException("There is already a transformation in progress.");
    }

    public Looper getApplicationLooper() {
        return this.e;
    }

    public int getProgress(ProgressHolder progressHolder) {
        a();
        if (this.j == 1) {
            Player player = (Player) Assertions.checkNotNull(this.i);
            progressHolder.progress = Math.min((int) ((player.getCurrentPosition() * 100) / player.getDuration()), 99);
        }
        return this.j;
    }

    public void cancel() {
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        a();
        SimpleExoPlayer simpleExoPlayer = this.i;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            this.i = null;
        }
        b bVar = this.h;
        if (bVar != null) {
            bVar.a(z);
            this.h = null;
        }
        this.j = 4;
    }

    private void a() {
        if (Looper.myLooper() != this.e) {
            throw new IllegalStateException("Transformer is accessed on the wrong thread.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b implements RenderersFactory {
        private final b a;
        private final j b = new j();
        private final g c;

        public b(b bVar, g gVar) {
            this.a = bVar;
            this.c = gVar;
        }

        @Override // com.google.android.exoplayer2.RenderersFactory
        public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
            char c = 1;
            Renderer[] rendererArr = new Renderer[(this.c.a || this.c.b) ? 1 : 2];
            if (!this.c.a) {
                rendererArr[0] = new h(this.a, this.b, this.c);
            } else {
                c = 0;
            }
            if (!this.c.b) {
                rendererArr[c] = new k(this.a, this.b, this.c);
            }
            return rendererArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a implements AnalyticsListener {
        private final MediaItem b;
        private final b c;

        public a(MediaItem mediaItem, b bVar) {
            this.b = mediaItem;
            this.c = bVar;
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i) {
            if (i == 4) {
                a(null);
            }
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i) {
            if (Transformer.this.j == 0) {
                Timeline.Window window = new Timeline.Window();
                eventTime.timeline.getWindow(0, window);
                if (!window.isPlaceholder) {
                    long j = window.durationUs;
                    Transformer.this.j = (j <= 0 || j == C.TIME_UNSET) ? 2 : 1;
                    ((SimpleExoPlayer) Assertions.checkNotNull(Transformer.this.i)).play();
                }
            }
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            if (this.c.b() == 0) {
                a(new IllegalStateException("The output does not contain any tracks. Check that at least one of the input sample formats is supported."));
            }
        }

        @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
        public void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
            a(playbackException);
        }

        private void a(@Nullable Exception e) {
            try {
                Transformer.this.a(false);
            } catch (IllegalStateException e2) {
                e = e2;
                if (e == null) {
                }
            }
            if (e == null) {
                Transformer.this.g.onTransformationCompleted(this.b);
            } else {
                Transformer.this.g.onTransformationError(this.b, e);
            }
        }
    }
}
