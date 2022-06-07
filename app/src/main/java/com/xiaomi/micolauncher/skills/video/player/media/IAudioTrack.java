package com.xiaomi.micolauncher.skills.video.player.media;

import java.util.List;

/* loaded from: classes3.dex */
public interface IAudioTrack {
    public static final int AUDIO_CODEC_AC3_AT = 9;
    public static final int AUDIO_CODEC_DOLBY = 1;
    public static final int AUDIO_CODEC_DOLBY_PLUS = 2;
    public static final int AUDIO_CODEC_DTS = 3;
    public static final int AUDIO_CODEC_DTS_EXPRESS = 4;
    public static final int AUDIO_CODEC_DTS_HDH = 6;
    public static final int AUDIO_CODEC_DTS_HDL = 7;
    public static final int AUDIO_CODEC_ELSE = 0;
    public static final int AUDIO_CODEC_MAT_AT = 10;
    public static final int AUDIO_CODEC_TRUEHD = 8;
    public static final int AUDIO_CODEC_TRUEHD_AT = 11;

    int getAudioCodec();

    List<AudioTrack> getAudioTracks();

    int getSelectedAudioTrack();

    void selectAudioTrack(int i);
}
