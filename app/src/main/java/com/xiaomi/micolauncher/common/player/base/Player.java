package com.xiaomi.micolauncher.common.player.base;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.policy.PlaybackControllerInfo;

/* loaded from: classes3.dex */
public abstract class Player {
    private PlayControlListener a;
    private a b;
    private AudioSource c;

    /* loaded from: classes3.dex */
    public interface PlayControlListener {
        void onPlayControlNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource);
    }

    abstract void background(AudioSource audioSource);

    abstract void forceStop(AudioSource audioSource);

    abstract void foreground(AudioSource audioSource);

    abstract void playbackControl(PlaybackControllerInfo playbackControllerInfo);

    abstract void postStart();

    abstract void postStop(AudioSource audioSource);

    public void restart() {
    }

    abstract void resume(AudioSource audioSource);

    public abstract void start();

    protected abstract void stop();

    abstract void suspend(AudioSource audioSource);

    public Player(AudioSource audioSource) {
        this.b = new a(audioSource);
        this.c = audioSource;
    }

    public void a() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.requestPlayAsync();
        }
    }

    public void a(long j) {
        a aVar = this.b;
        if (aVar != null) {
            aVar.requestFinish(j);
        }
    }

    public void b() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.requestPause();
        }
    }

    public void setListener(PlayControlListener playControlListener) {
        this.a = playControlListener;
    }

    public void setIgnoreForceStop(AudioSource audioSource) {
        a aVar = this.b;
        if (aVar != null) {
            aVar.setIgnoreForceStopSource(audioSource);
        }
    }

    public void release() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.release();
            this.b = null;
        }
    }

    /* loaded from: classes3.dex */
    public final class a extends AudioPolicyClient {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a(AudioSource audioSource) {
            super(audioSource);
            Player.this = r1;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
        public final void onNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource) {
            super.onNotify(playControl, audioSource);
            L.player.d("[Player]: %s(%s).onNotify.cmd=%s", Player.this.c, Integer.valueOf(hashCode()), playControl);
            switch (playControl) {
                case ALLOW:
                    Player.this.postStart();
                    break;
                case BACKGROUND:
                    Player.this.background(audioSource);
                    break;
                case SUSPEND:
                    Player.this.suspend(audioSource);
                    break;
                case NOT_ALLOW:
                    Player.this.postStop(audioSource);
                    break;
                case FOREGROUND:
                    Player.this.foreground(audioSource);
                    break;
                case RESUME:
                    Player.this.resume(audioSource);
                    break;
                case FORCE_STOP:
                    Player.this.postStop(audioSource);
                    Player.this.forceStop(audioSource);
                    break;
            }
            if (Player.this.a != null) {
                Player.this.a.onPlayControlNotify(playControl, audioSource);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
        public void onPlaybackControl(PlaybackControllerInfo playbackControllerInfo) {
            Player.this.playbackControl(playbackControllerInfo);
        }
    }
}
