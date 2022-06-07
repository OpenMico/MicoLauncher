package com.xiaomi.micolauncher.common.player.base;

import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;

/* loaded from: classes3.dex */
public class FakePlayer extends Player {
    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void background(AudioSource audioSource) {
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void forceStop(AudioSource audioSource) {
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    protected void foreground(AudioSource audioSource) {
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    protected void playbackControl(PlaybackControllerInfo playbackControllerInfo) {
    }

    public void postStart() {
    }

    public void postStop(AudioSource audioSource) {
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void resume(AudioSource audioSource) {
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void suspend(AudioSource audioSource) {
    }

    public FakePlayer(AudioSource audioSource) {
        super(audioSource);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public void setListener(Player.PlayControlListener playControlListener) {
        super.setListener(playControlListener);
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void start() {
        super.a();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void stop() {
        super.a(0L);
    }

    public final void pause() {
        super.b();
    }

    @Override // com.xiaomi.micolauncher.common.player.base.Player
    public final void setIgnoreForceStop(AudioSource audioSource) {
        super.setIgnoreForceStop(audioSource);
    }

    public final void releaseFocus(long j) {
        super.a(j);
    }
}
