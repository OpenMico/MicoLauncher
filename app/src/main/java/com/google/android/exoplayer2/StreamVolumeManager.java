package com.google.android.exoplayer2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class StreamVolumeManager {
    private final Context a;
    private final Handler b;
    private final Listener c;
    private final AudioManager d;
    @Nullable
    private a e;
    private int f = 3;
    private int g;
    private boolean h;

    /* loaded from: classes.dex */
    public interface Listener {
        void onStreamTypeChanged(int i);

        void onStreamVolumeChanged(int i, boolean z);
    }

    public StreamVolumeManager(Context context, Handler handler, Listener listener) {
        this.a = context.getApplicationContext();
        this.b = handler;
        this.c = listener;
        this.d = (AudioManager) Assertions.checkStateNotNull((AudioManager) this.a.getSystemService("audio"));
        this.g = a(this.d, this.f);
        this.h = b(this.d, this.f);
        a aVar = new a();
        try {
            this.a.registerReceiver(aVar, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            this.e = aVar;
        } catch (RuntimeException e) {
            Log.w("StreamVolumeManager", "Error registering stream volume receiver", e);
        }
    }

    public void a(int i) {
        if (this.f != i) {
            this.f = i;
            h();
            this.c.onStreamTypeChanged(i);
        }
    }

    public int a() {
        if (Util.SDK_INT >= 28) {
            return this.d.getStreamMinVolume(this.f);
        }
        return 0;
    }

    public int b() {
        return this.d.getStreamMaxVolume(this.f);
    }

    public int c() {
        return this.g;
    }

    public boolean d() {
        return this.h;
    }

    public void b(int i) {
        if (i >= a() && i <= b()) {
            this.d.setStreamVolume(this.f, i, 1);
            h();
        }
    }

    public void e() {
        if (this.g < b()) {
            this.d.adjustStreamVolume(this.f, 1, 1);
            h();
        }
    }

    public void f() {
        if (this.g > a()) {
            this.d.adjustStreamVolume(this.f, -1, 1);
            h();
        }
    }

    public void a(boolean z) {
        if (Util.SDK_INT >= 23) {
            this.d.adjustStreamVolume(this.f, z ? -100 : 100, 1);
        } else {
            this.d.setStreamMute(this.f, z);
        }
        h();
    }

    public void g() {
        a aVar = this.e;
        if (aVar != null) {
            try {
                this.a.unregisterReceiver(aVar);
            } catch (RuntimeException e) {
                Log.w("StreamVolumeManager", "Error unregistering stream volume receiver", e);
            }
            this.e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        int a2 = a(this.d, this.f);
        boolean b = b(this.d, this.f);
        if (this.g != a2 || this.h != b) {
            this.g = a2;
            this.h = b;
            this.c.onStreamVolumeChanged(a2, b);
        }
    }

    private static int a(AudioManager audioManager, int i) {
        try {
            return audioManager.getStreamVolume(i);
        } catch (RuntimeException e) {
            StringBuilder sb = new StringBuilder(60);
            sb.append("Could not retrieve stream volume for stream type ");
            sb.append(i);
            Log.w("StreamVolumeManager", sb.toString(), e);
            return audioManager.getStreamMaxVolume(i);
        }
    }

    private static boolean b(AudioManager audioManager, int i) {
        if (Util.SDK_INT >= 23) {
            return audioManager.isStreamMute(i);
        }
        return a(audioManager, i) == 0;
    }

    /* loaded from: classes.dex */
    private final class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Handler handler = StreamVolumeManager.this.b;
            final StreamVolumeManager streamVolumeManager = StreamVolumeManager.this;
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.-$$Lambda$StreamVolumeManager$a$ppiEhlBGELereVXxytvQP-5coXc
                @Override // java.lang.Runnable
                public final void run() {
                    StreamVolumeManager.this.h();
                }
            });
        }
    }
}
