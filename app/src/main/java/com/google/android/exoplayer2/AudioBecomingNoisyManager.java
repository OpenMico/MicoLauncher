package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* loaded from: classes.dex */
final class AudioBecomingNoisyManager {
    private final Context a;
    private final a b;
    private boolean c;

    /* loaded from: classes.dex */
    public interface EventListener {
        void onAudioBecomingNoisy();
    }

    public AudioBecomingNoisyManager(Context context, Handler handler, EventListener eventListener) {
        this.a = context.getApplicationContext();
        this.b = new a(handler, eventListener);
    }

    public void a(boolean z) {
        if (z && !this.c) {
            this.a.registerReceiver(this.b, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
            this.c = true;
        } else if (!z && this.c) {
            this.a.unregisterReceiver(this.b);
            this.c = false;
        }
    }

    /* loaded from: classes.dex */
    private final class a extends BroadcastReceiver implements Runnable {
        private final EventListener b;
        private final Handler c;

        public a(Handler handler, EventListener eventListener) {
            this.c = handler;
            this.b = eventListener;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
                this.c.post(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AudioBecomingNoisyManager.this.c) {
                this.b.onAudioBecomingNoisy();
            }
        }
    }
}
