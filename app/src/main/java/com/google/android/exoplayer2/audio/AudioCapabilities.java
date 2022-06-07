package com.google.android.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class AudioCapabilities {
    public static final AudioCapabilities DEFAULT_AUDIO_CAPABILITIES = new AudioCapabilities(new int[]{2}, 8);
    private static final AudioCapabilities a = new AudioCapabilities(new int[]{2, 5, 6}, 8);
    private static final int[] b = {5, 6, 18, 17, 14, 7, 8};
    private final int[] c;
    private final int d;

    public static AudioCapabilities getCapabilities(Context context) {
        return a(context, context.registerReceiver(null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @SuppressLint({"InlinedApi"})
    public static AudioCapabilities a(Context context, @Nullable Intent intent) {
        if (c() && Settings.Global.getInt(context.getContentResolver(), "external_surround_sound_enabled", 0) == 1) {
            return a;
        }
        if (Util.SDK_INT >= 29 && Util.isTv(context)) {
            return new AudioCapabilities(a.a(), 8);
        }
        if (intent == null || intent.getIntExtra("android.media.extra.AUDIO_PLUG_STATE", 0) == 0) {
            return DEFAULT_AUDIO_CAPABILITIES;
        }
        return new AudioCapabilities(intent.getIntArrayExtra("android.media.extra.ENCODINGS"), intent.getIntExtra("android.media.extra.MAX_CHANNEL_COUNT", 8));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static Uri a() {
        if (c()) {
            return Settings.Global.getUriFor("external_surround_sound_enabled");
        }
        return null;
    }

    public AudioCapabilities(@Nullable int[] iArr, int i) {
        if (iArr != null) {
            this.c = Arrays.copyOf(iArr, iArr.length);
            Arrays.sort(this.c);
        } else {
            this.c = new int[0];
        }
        this.d = i;
    }

    public boolean supportsEncoding(int i) {
        return Arrays.binarySearch(this.c, i) >= 0;
    }

    public int getMaxChannelCount() {
        return this.d;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioCapabilities)) {
            return false;
        }
        AudioCapabilities audioCapabilities = (AudioCapabilities) obj;
        return Arrays.equals(this.c, audioCapabilities.c) && this.d == audioCapabilities.d;
    }

    public int hashCode() {
        return this.d + (Arrays.hashCode(this.c) * 31);
    }

    public String toString() {
        int i = this.d;
        String arrays = Arrays.toString(this.c);
        StringBuilder sb = new StringBuilder(String.valueOf(arrays).length() + 67);
        sb.append("AudioCapabilities[maxChannelCount=");
        sb.append(i);
        sb.append(", supportedEncodings=");
        sb.append(arrays);
        sb.append("]");
        return sb.toString();
    }

    private static boolean c() {
        return Util.SDK_INT >= 17 && ("Amazon".equals(Util.MANUFACTURER) || "Xiaomi".equals(Util.MANUFACTURER));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(29)
    /* loaded from: classes.dex */
    public static final class a {
        @DoNotInline
        public static int[] a() {
            ImmutableList.Builder builder = ImmutableList.builder();
            int[] iArr = AudioCapabilities.b;
            for (int i : iArr) {
                if (AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setChannelMask(12).setEncoding(i).setSampleRate(OpusUtil.SAMPLE_RATE).build(), new AudioAttributes.Builder().setUsage(1).setContentType(3).setFlags(0).build())) {
                    builder.add((ImmutableList.Builder) Integer.valueOf(i));
                }
            }
            builder.add((ImmutableList.Builder) 2);
            return Ints.toArray(builder.build());
        }
    }
}
