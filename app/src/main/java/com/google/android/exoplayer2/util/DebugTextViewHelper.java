package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.TextView;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DebugTextViewHelper implements Player.Listener, Runnable {
    private final SimpleExoPlayer a;
    private final TextView b;
    private boolean c;

    public DebugTextViewHelper(SimpleExoPlayer simpleExoPlayer, TextView textView) {
        Assertions.checkArgument(simpleExoPlayer.getApplicationLooper() == Looper.getMainLooper());
        this.a = simpleExoPlayer;
        this.b = textView;
    }

    public final void start() {
        if (!this.c) {
            this.c = true;
            this.a.addListener((Player.Listener) this);
            updateAndPost();
        }
    }

    public final void stop() {
        if (this.c) {
            this.c = false;
            this.a.removeListener((Player.Listener) this);
            this.b.removeCallbacks(this);
        }
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(int i) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(boolean z, int i) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        updateAndPost();
    }

    @Override // java.lang.Runnable
    public final void run() {
        updateAndPost();
    }

    @SuppressLint({"SetTextI18n"})
    protected final void updateAndPost() {
        this.b.setText(getDebugString());
        this.b.removeCallbacks(this);
        this.b.postDelayed(this, 1000L);
    }

    protected String getDebugString() {
        String playerStateString = getPlayerStateString();
        String videoString = getVideoString();
        String audioString = getAudioString();
        StringBuilder sb = new StringBuilder(String.valueOf(playerStateString).length() + String.valueOf(videoString).length() + String.valueOf(audioString).length());
        sb.append(playerStateString);
        sb.append(videoString);
        sb.append(audioString);
        return sb.toString();
    }

    protected String getPlayerStateString() {
        String str;
        switch (this.a.getPlaybackState()) {
            case 1:
                str = "idle";
                break;
            case 2:
                str = "buffering";
                break;
            case 3:
                str = "ready";
                break;
            case 4:
                str = "ended";
                break;
            default:
                str = "unknown";
                break;
        }
        return String.format("playWhenReady:%s playbackState:%s window:%s", Boolean.valueOf(this.a.getPlayWhenReady()), str, Integer.valueOf(this.a.getCurrentWindowIndex()));
    }

    protected String getVideoString() {
        Format videoFormat = this.a.getVideoFormat();
        DecoderCounters videoDecoderCounters = this.a.getVideoDecoderCounters();
        if (videoFormat == null || videoDecoderCounters == null) {
            return "";
        }
        String str = videoFormat.sampleMimeType;
        String str2 = videoFormat.id;
        int i = videoFormat.width;
        int i2 = videoFormat.height;
        String a = a(videoFormat.pixelWidthHeightRatio);
        String a2 = a(videoDecoderCounters);
        String a3 = a(videoDecoderCounters.totalVideoFrameProcessingOffsetUs, videoDecoderCounters.videoFrameProcessingOffsetCount);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 39 + String.valueOf(str2).length() + String.valueOf(a).length() + String.valueOf(a2).length() + String.valueOf(a3).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" r:");
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        sb.append(a);
        sb.append(a2);
        sb.append(" vfpo: ");
        sb.append(a3);
        sb.append(")");
        return sb.toString();
    }

    protected String getAudioString() {
        Format audioFormat = this.a.getAudioFormat();
        DecoderCounters audioDecoderCounters = this.a.getAudioDecoderCounters();
        if (audioFormat == null || audioDecoderCounters == null) {
            return "";
        }
        String str = audioFormat.sampleMimeType;
        String str2 = audioFormat.id;
        int i = audioFormat.sampleRate;
        int i2 = audioFormat.channelCount;
        String a = a(audioDecoderCounters);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length() + String.valueOf(a).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" hz:");
        sb.append(i);
        sb.append(" ch:");
        sb.append(i2);
        sb.append(a);
        sb.append(")");
        return sb.toString();
    }

    private static String a(DecoderCounters decoderCounters) {
        if (decoderCounters == null) {
            return "";
        }
        decoderCounters.ensureUpdated();
        int i = decoderCounters.skippedInputBufferCount;
        int i2 = decoderCounters.skippedOutputBufferCount;
        int i3 = decoderCounters.renderedOutputBufferCount;
        int i4 = decoderCounters.droppedBufferCount;
        int i5 = decoderCounters.maxConsecutiveDroppedBufferCount;
        int i6 = decoderCounters.droppedToKeyframeCount;
        StringBuilder sb = new StringBuilder(93);
        sb.append(" sib:");
        sb.append(i);
        sb.append(" sb:");
        sb.append(i2);
        sb.append(" rb:");
        sb.append(i3);
        sb.append(" db:");
        sb.append(i4);
        sb.append(" mcdb:");
        sb.append(i5);
        sb.append(" dk:");
        sb.append(i6);
        return sb.toString();
    }

    private static String a(float f) {
        if (f == -1.0f || f == 1.0f) {
            return "";
        }
        String valueOf = String.valueOf(String.format(Locale.US, "%.02f", Float.valueOf(f)));
        return valueOf.length() != 0 ? " par:".concat(valueOf) : new String(" par:");
    }

    private static String a(long j, int i) {
        return i == 0 ? "N/A" : String.valueOf((long) (j / i));
    }
}
