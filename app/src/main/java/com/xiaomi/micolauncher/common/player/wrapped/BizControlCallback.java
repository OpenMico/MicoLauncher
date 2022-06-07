package com.xiaomi.micolauncher.common.player.wrapped;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;

/* loaded from: classes3.dex */
public class BizControlCallback<T> {
    public void onAllow(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onBackground(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onForbidden(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onForeground(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onIdle(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onResume(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onSuspend(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
    }

    public void onCallback(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, T t) {
        L.player.i("BizControlCallback %s %s %s", playControl, audioSource, t);
        switch (playControl) {
            case IDLE:
                onIdle(playControl, audioSource, t);
                return;
            case ALLOW:
                onAllow(playControl, audioSource, t);
                return;
            case FOREGROUND:
                onForeground(playControl, audioSource, t);
                return;
            case RESUME:
                onResume(playControl, audioSource, t);
                return;
            case FORCE_STOP:
            case NOT_ALLOW:
                onForbidden(playControl, audioSource, t);
                return;
            case SUSPEND:
                onSuspend(playControl, audioSource, t);
                return;
            case BACKGROUND:
                onBackground(playControl, audioSource, t);
                return;
            default:
                return;
        }
    }
}
