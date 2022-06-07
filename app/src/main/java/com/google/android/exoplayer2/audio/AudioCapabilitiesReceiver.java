package com.google.android.exoplayer2.audio;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class AudioCapabilitiesReceiver {
    @Nullable
    AudioCapabilities a;
    private final Context b;
    private final Listener c;
    private final Handler d = Util.createHandlerForCurrentOrMainLooper();
    @Nullable
    private final BroadcastReceiver e;
    @Nullable
    private final a f;
    private boolean g;

    /* loaded from: classes.dex */
    public interface Listener {
        void onAudioCapabilitiesChanged(AudioCapabilities audioCapabilities);
    }

    public AudioCapabilitiesReceiver(Context context, Listener listener) {
        Context applicationContext = context.getApplicationContext();
        this.b = applicationContext;
        this.c = (Listener) Assertions.checkNotNull(listener);
        a aVar = null;
        this.e = Util.SDK_INT >= 21 ? new b() : null;
        Uri a2 = AudioCapabilities.a();
        this.f = a2 != null ? new a(this.d, applicationContext.getContentResolver(), a2) : aVar;
    }

    public AudioCapabilities register() {
        if (this.g) {
            return (AudioCapabilities) Assertions.checkNotNull(this.a);
        }
        this.g = true;
        a aVar = this.f;
        if (aVar != null) {
            aVar.a();
        }
        Intent intent = null;
        if (this.e != null) {
            intent = this.b.registerReceiver(this.e, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG"), null, this.d);
        }
        this.a = AudioCapabilities.a(this.b, intent);
        return this.a;
    }

    public void unregister() {
        if (this.g) {
            this.a = null;
            BroadcastReceiver broadcastReceiver = this.e;
            if (broadcastReceiver != null) {
                this.b.unregisterReceiver(broadcastReceiver);
            }
            a aVar = this.f;
            if (aVar != null) {
                aVar.b();
            }
            this.g = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AudioCapabilities audioCapabilities) {
        if (this.g && !audioCapabilities.equals(this.a)) {
            this.a = audioCapabilities;
            this.c.onAudioCapabilitiesChanged(audioCapabilities);
        }
    }

    /* loaded from: classes.dex */
    private final class b extends BroadcastReceiver {
        private b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!isInitialStickyBroadcast()) {
                AudioCapabilitiesReceiver.this.a(AudioCapabilities.a(context, intent));
            }
        }
    }

    /* loaded from: classes.dex */
    private final class a extends ContentObserver {
        private final ContentResolver b;
        private final Uri c;

        public a(Handler handler, ContentResolver contentResolver, Uri uri) {
            super(handler);
            this.b = contentResolver;
            this.c = uri;
        }

        public void a() {
            this.b.registerContentObserver(this.c, false, this);
        }

        public void b() {
            this.b.unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            AudioCapabilitiesReceiver audioCapabilitiesReceiver = AudioCapabilitiesReceiver.this;
            audioCapabilitiesReceiver.a(AudioCapabilities.getCapabilities(audioCapabilitiesReceiver.b));
        }
    }
}
