package com.xiaomi.micolauncher.common.player;

/* loaded from: classes3.dex */
public interface TtsPlayer {
    void disableFocus();

    void enableFocus();

    boolean isPlaying();

    void mute();

    void play(Object obj);

    void requestFinish();

    void requestPlay(TtsPayload ttsPayload);

    void requestPlay(String str, String str2);

    /* loaded from: classes3.dex */
    public static final class TtsPayload {
        public String content;
        public String id;
        public boolean interrupt;
        public String url;

        public TtsPayload(String str, String str2, String str3) {
            this.url = str;
            this.id = str2;
            this.content = str3;
        }
    }
}
