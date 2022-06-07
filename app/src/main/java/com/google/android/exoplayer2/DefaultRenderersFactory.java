package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    public static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private final Context a;
    private int b;
    private long c;
    private boolean d;
    private MediaCodecSelector e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ExtensionRendererMode {
    }

    protected void buildMiscellaneousRenderers(Context context, Handler handler, int i, ArrayList<Renderer> arrayList) {
    }

    public DefaultRenderersFactory(Context context) {
        this.a = context;
        this.b = 0;
        this.c = 5000L;
        this.e = MediaCodecSelector.DEFAULT;
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i) {
        this(context, i, 5000L);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i, long j) {
        this.a = context;
        this.b = i;
        this.c = j;
        this.e = MediaCodecSelector.DEFAULT;
    }

    public DefaultRenderersFactory setExtensionRendererMode(int i) {
        this.b = i;
        return this;
    }

    public DefaultRenderersFactory experimentalSetAsynchronousBufferQueueingEnabled(boolean z) {
        this.f = z;
        return this;
    }

    public DefaultRenderersFactory experimentalSetForceAsyncQueueingSynchronizationWorkaround(boolean z) {
        this.g = z;
        return this;
    }

    public DefaultRenderersFactory experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z) {
        this.h = z;
        return this;
    }

    public DefaultRenderersFactory setEnableDecoderFallback(boolean z) {
        this.d = z;
        return this;
    }

    public DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.e = mediaCodecSelector;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioFloatOutput(boolean z) {
        this.i = z;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioOffload(boolean z) {
        this.k = z;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean z) {
        this.j = z;
        return this;
    }

    public DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long j) {
        this.c = j;
        return this;
    }

    @Override // com.google.android.exoplayer2.RenderersFactory
    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
        ArrayList<Renderer> arrayList = new ArrayList<>();
        buildVideoRenderers(this.a, this.b, this.e, this.d, handler, videoRendererEventListener, this.c, arrayList);
        AudioSink buildAudioSink = buildAudioSink(this.a, this.i, this.j, this.k);
        if (buildAudioSink != null) {
            buildAudioRenderers(this.a, this.b, this.e, this.d, buildAudioSink, handler, audioRendererEventListener, arrayList);
        }
        buildTextRenderers(this.a, textOutput, handler.getLooper(), this.b, arrayList);
        buildMetadataRenderers(this.a, metadataOutput, handler.getLooper(), this.b, arrayList);
        buildCameraMotionRenderers(this.a, this.b, arrayList);
        buildMiscellaneousRenderers(this.a, handler, this.b, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[0]);
    }

    protected void buildVideoRenderers(Context context, int i, MediaCodecSelector mediaCodecSelector, boolean z, Handler handler, VideoRendererEventListener videoRendererEventListener, long j, ArrayList<Renderer> arrayList) {
        int i2;
        MediaCodecVideoRenderer mediaCodecVideoRenderer = new MediaCodecVideoRenderer(context, mediaCodecSelector, j, z, handler, videoRendererEventListener, 50);
        mediaCodecVideoRenderer.experimentalSetAsynchronousBufferQueueingEnabled(this.f);
        mediaCodecVideoRenderer.experimentalSetForceAsyncQueueingSynchronizationWorkaround(this.g);
        mediaCodecVideoRenderer.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(this.h);
        arrayList.add(mediaCodecVideoRenderer);
        if (i != 0) {
            int size = arrayList.size();
            if (i == 2) {
                size--;
            }
            try {
                try {
                    i2 = size + 1;
                    try {
                        arrayList.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j), handler, videoRendererEventListener, 50));
                        Log.i("DefaultRenderersFactory", "Loaded LibvpxVideoRenderer.");
                    } catch (ClassNotFoundException unused) {
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error instantiating VP9 extension", e);
                }
            } catch (ClassNotFoundException unused2) {
                i2 = size;
            }
            try {
                arrayList.add(i2, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j), handler, videoRendererEventListener, 50));
                Log.i("DefaultRenderersFactory", "Loaded Libgav1VideoRenderer.");
            } catch (ClassNotFoundException unused3) {
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating AV1 extension", e2);
            }
        }
    }

    protected void buildAudioRenderers(Context context, int i, MediaCodecSelector mediaCodecSelector, boolean z, AudioSink audioSink, Handler handler, AudioRendererEventListener audioRendererEventListener, ArrayList<Renderer> arrayList) {
        int i2;
        int i3;
        MediaCodecAudioRenderer mediaCodecAudioRenderer = new MediaCodecAudioRenderer(context, mediaCodecSelector, z, handler, audioRendererEventListener, audioSink);
        mediaCodecAudioRenderer.experimentalSetAsynchronousBufferQueueingEnabled(this.f);
        mediaCodecAudioRenderer.experimentalSetForceAsyncQueueingSynchronizationWorkaround(this.g);
        mediaCodecAudioRenderer.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(this.h);
        arrayList.add(mediaCodecAudioRenderer);
        if (i != 0) {
            int size = arrayList.size();
            if (i == 2) {
                size--;
            }
            try {
                try {
                    i2 = size + 1;
                    try {
                        arrayList.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                        Log.i("DefaultRenderersFactory", "Loaded LibopusAudioRenderer.");
                    } catch (ClassNotFoundException unused) {
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error instantiating Opus extension", e);
                }
            } catch (ClassNotFoundException unused2) {
                i2 = size;
            }
            try {
                try {
                    i3 = i2 + 1;
                    try {
                        arrayList.add(i2, (Renderer) Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                        Log.i("DefaultRenderersFactory", "Loaded LibflacAudioRenderer.");
                    } catch (ClassNotFoundException unused3) {
                    }
                } catch (Exception e2) {
                    throw new RuntimeException("Error instantiating FLAC extension", e2);
                }
            } catch (ClassNotFoundException unused4) {
                i3 = i2;
            }
            try {
                arrayList.add(i3, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i("DefaultRenderersFactory", "Loaded FfmpegAudioRenderer.");
            } catch (ClassNotFoundException unused5) {
            } catch (Exception e3) {
                throw new RuntimeException("Error instantiating FFmpeg extension", e3);
            }
        }
    }

    protected void buildTextRenderers(Context context, TextOutput textOutput, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new TextRenderer(textOutput, looper));
    }

    protected void buildMetadataRenderers(Context context, MetadataOutput metadataOutput, Looper looper, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new MetadataRenderer(metadataOutput, looper));
    }

    protected void buildCameraMotionRenderers(Context context, int i, ArrayList<Renderer> arrayList) {
        arrayList.add(new CameraMotionRenderer());
    }

    @Nullable
    protected AudioSink buildAudioSink(Context context, boolean z, boolean z2, boolean z3) {
        return new DefaultAudioSink(AudioCapabilities.getCapabilities(context), new DefaultAudioSink.DefaultAudioProcessorChain(new AudioProcessor[0]), z, z2, z3 ? 1 : 0);
    }
}
