package com.xiaomi.micolauncher.common.event;

import com.xiaomi.micolauncher.common.player.MicoMultiAudioPlayer;
import com.xiaomi.micolauncher.skills.joke.JokeItem;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AudioPlayerStateChangeEvent {
    public String audioId;
    public MicoMultiAudioPlayer.AudioState audioState;
    public String dialogId;
    public int index = -1;
    public ArrayList<JokeItem> jokeItems;
    public int timeoutInMs;

    public AudioPlayerStateChangeEvent(MicoMultiAudioPlayer.AudioState audioState) {
        this.audioState = audioState;
    }

    public String toString() {
        return "AudioPlayerStateChangeEvent{dialogId='" + this.dialogId + "', audioId='" + this.audioId + "', index=" + this.index + ", audioState=" + this.audioState + '}';
    }
}
