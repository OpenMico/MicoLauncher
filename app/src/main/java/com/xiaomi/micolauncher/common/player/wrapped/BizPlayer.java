package com.xiaomi.micolauncher.common.player.wrapped;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class BizPlayer<T> {
    private a a;
    private AtomicReference<T> b = new AtomicReference<>();

    public BizPlayer(AudioSource audioSource, final BizControlCallback<T> bizControlCallback) {
        this.a = new a(audioSource);
        this.a.setListener(new Player.PlayControlListener() { // from class: com.xiaomi.micolauncher.common.player.wrapped.-$$Lambda$BizPlayer$N_DcPJNAnIXC3TWCVXPbjBTx7fk
            @Override // com.xiaomi.micolauncher.common.player.base.Player.PlayControlListener
            public final void onPlayControlNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource2) {
                BizPlayer.this.a(bizControlCallback, playControl, audioSource2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BizControlCallback bizControlCallback, AudioPolicyClient.PlayControl playControl, AudioSource audioSource) {
        bizControlCallback.onCallback(playControl, audioSource, this.b.get());
    }

    public void play(T t) {
        this.b.set(t);
        this.a.start();
        L.openplatform.i("BizPlayer play %s", t);
    }

    public void stop() {
        this.b.set(null);
        this.a.stop();
        L.openplatform.i("BizPlayer stop");
    }

    public void pause() {
        this.a.pause();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends FakePlayer {
        a(AudioSource audioSource) {
            super(audioSource);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void setListener(Player.PlayControlListener playControlListener) {
            super.setListener(playControlListener);
        }
    }
}
