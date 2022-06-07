package com.xiaomi.miplay.mylibrary.mirror;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.xiaomi.miplay.mylibrary.mirror.Encoder;
import com.xiaomi.miplay.mylibrary.mirror.b;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseEncoder.java */
/* loaded from: classes4.dex */
public abstract class b implements Encoder {
    private HandlerC0175b c;
    private HandlerThread d;
    private HandlerC0175b e;
    private HandlerThread f;
    private HandlerThread g;
    private String h;
    private MediaCodec i;
    private a j;
    private final Object a = new Object();
    private final Object b = new Object();
    private MediaCodec.Callback k = new MediaCodec.Callback() { // from class: com.xiaomi.miplay.mylibrary.mirror.b.1
        @Override // android.media.MediaCodec.Callback
        public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
            synchronized (b.this.a) {
                if (b.this.c != null) {
                    b.this.c.a(b.this, mediaCodec, i);
                }
            }
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
            synchronized (b.this.b) {
                if (b.this.e != null) {
                    b.this.e.a(b.this, i, bufferInfo);
                }
            }
        }

        @Override // android.media.MediaCodec.Callback
        public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
            Log.d("MiPlayQuickBaseEncoder", "onError before." + codecException);
            synchronized (b.this.a) {
                if (b.this.c != null) {
                    b.this.c.a(b.this, codecException);
                }
            }
            Log.d("MiPlayQuickBaseEncoder", "onError after." + codecException);
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
            Log.d("MiPlayQuickBaseEncoder", "onOutputFormatChanged before.");
            synchronized (b.this.b) {
                if (b.this.e != null) {
                    b.this.e.a(b.this, mediaFormat);
                }
            }
            Log.d("MiPlayQuickBaseEncoder", "onOutputFormatChanged after.");
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BaseEncoder.java */
    /* loaded from: classes4.dex */
    public static abstract class a implements Encoder.Callback {
        void a(b bVar, int i, MediaCodec.BufferInfo bufferInfo) {
        }

        void a(b bVar, MediaCodec mediaCodec, int i) {
        }

        void a(b bVar, MediaFormat mediaFormat) {
        }
    }

    protected abstract MediaFormat a();

    protected void a(MediaCodec mediaCodec) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(String str) {
        this.h = str;
    }

    void a(a aVar) {
        if (this.i == null) {
            this.j = aVar;
            return;
        }
        throw new IllegalStateException("mEncoder is not null");
    }

    public void b() throws IOException {
        Logger.d("MiPlayQuickBaseEncoder", "mEncoder prepare.", new Object[0]);
        if (this.i == null) {
            this.d = new HandlerThread("encoder_thread");
            this.d.start();
            this.c = new HandlerC0175b(this.d.getLooper(), this.j);
            this.f = new HandlerThread("encoder_thread2");
            this.f.start();
            this.e = new HandlerC0175b(this.f.getLooper(), this.j);
            Logger.d("MiPlayQuickBaseEncoder", "mEncoder prepare thread", new Object[0]);
            MediaFormat a2 = a();
            Log.d("MiPlayQuickBaseEncoder", "Create media format: " + a2);
            String string = a2.getString("mime");
            Log.e("MiPlayQuickBaseEncoder", string);
            MediaCodec a3 = a(string);
            try {
                if (this.j != null) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        this.g = new HandlerThread("codec_callback");
                        this.g.start();
                        a3.setCallback(this.k, new Handler(this.g.getLooper()));
                    } else {
                        a3.setCallback(this.k);
                    }
                }
                a3.configure(a2, (Surface) null, (MediaCrypto) null, 1);
                a(a3);
                a3.start();
                this.i = a3;
            } catch (MediaCodec.CodecException e) {
                Log.e("MiPlayQuickBaseEncoder", "Configure codec failure!\n  with format" + a2, e);
                throw e;
            }
        } else {
            throw new IllegalStateException("prepared!");
        }
    }

    private MediaCodec a(String str) throws IOException {
        String str2;
        StringBuilder sb;
        MediaCodec mediaCodec = null;
        try {
            try {
                if (this.h != null) {
                    Log.e("MiPlayQuickBaseEncoder", this.h);
                    mediaCodec = MediaCodec.createByCodecName(this.h);
                    if (mediaCodec != null) {
                        Log.e("MiPlayQuickBaseEncoder", "Create MediaCodec by name " + this.h);
                    }
                }
            } catch (Exception e) {
                Log.w("MiPlayQuickBaseEncoder", "Create MediaCodec by name '" + this.h + "' failure!", e);
                if (mediaCodec == null && (mediaCodec = MediaCodec.createEncoderByType(str)) != null) {
                    str2 = "MiPlayQuickBaseEncoder";
                    sb = new StringBuilder();
                }
            }
            if (mediaCodec == null && (mediaCodec = MediaCodec.createEncoderByType(str)) != null) {
                str2 = "MiPlayQuickBaseEncoder";
                sb = new StringBuilder();
                sb.append("Create MediaCodec by type ");
                sb.append(str);
                Log.e(str2, sb.toString());
            }
            return mediaCodec;
        } catch (Throwable th) {
            if (mediaCodec == null && MediaCodec.createEncoderByType(str) != null) {
                Log.e("MiPlayQuickBaseEncoder", "Create MediaCodec by type " + str);
            }
            throw th;
        }
    }

    protected final MediaCodec c() {
        return (MediaCodec) Objects.requireNonNull(this.i, "doesn't prepare()");
    }

    public final ByteBuffer a(int i) {
        return c().getOutputBuffer(i);
    }

    public final void b(int i) {
        c().releaseOutputBuffer(i, false);
    }

    public void d() {
        Log.d("MiPlayQuickBaseEncoder", "stop encode thread");
        if (this.d != null) {
            Logger.d("MiPlayQuickBaseEncoder", "mEncoderThread start", new Object[0]);
            synchronized (this.a) {
                this.d.quitSafely();
                try {
                    this.d.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.d = null;
                this.c = null;
            }
            Logger.d("MiPlayQuickBaseEncoder", "mEncoderThread end", new Object[0]);
        }
        if (this.f != null) {
            Logger.d("MiPlayQuickBaseEncoder", "mEncoderThread_2 start", new Object[0]);
            synchronized (this.b) {
                this.f.quitSafely();
                try {
                    this.f.join();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.f = null;
                this.e = null;
            }
            Logger.d("MiPlayQuickBaseEncoder", "mEncoderThread_2 end", new Object[0]);
        }
        Log.d("MiPlayQuickBaseEncoder", "stop encode thread end");
        MediaCodec mediaCodec = this.i;
        if (mediaCodec != null) {
            mediaCodec.stop();
        }
        Log.d("MiPlayQuickBaseEncoder", "stop encode ");
    }

    public void e() {
        MediaCodec mediaCodec = this.i;
        if (mediaCodec != null) {
            mediaCodec.flush();
        }
    }

    public void f() {
        if (this.i != null) {
            Log.d("MiPlayQuickBaseEncoder", "BaseEncoder release start");
            this.i.release();
            this.i = null;
            Log.d("MiPlayQuickBaseEncoder", "BaseEncoder release end");
        }
        if (this.g != null) {
            Log.d("MiPlayQuickBaseEncoder", "codecCallbackThread quit");
            this.g.quitSafely();
            this.g = null;
        }
    }

    /* compiled from: BaseEncoder.java */
    /* renamed from: com.xiaomi.miplay.mylibrary.mirror.b$b  reason: collision with other inner class name */
    /* loaded from: classes4.dex */
    private static class HandlerC0175b extends Handler {
        private a a;

        HandlerC0175b(Looper looper, a aVar) {
            super(looper);
            this.a = aVar;
        }

        void a(final b bVar, final MediaCodec mediaCodec, final int i) {
            Message.obtain(this, new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.b.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (HandlerC0175b.this.a != null) {
                        HandlerC0175b.this.a.a(bVar, mediaCodec, i);
                    }
                }
            }).sendToTarget();
        }

        void a(final Encoder encoder, final Exception exc) {
            Message.obtain(this, new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.-$$Lambda$b$b$Bx-_P_Jl7_dB1LHncOXrNsIKNCU
                @Override // java.lang.Runnable
                public final void run() {
                    b.HandlerC0175b.this.b(encoder, exc);
                }
            }).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(Encoder encoder, Exception exc) {
            a aVar = this.a;
            if (aVar != null) {
                aVar.onError(encoder, exc);
            }
        }

        void a(final b bVar, final MediaFormat mediaFormat) {
            Message.obtain(this, new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.-$$Lambda$b$b$uVJBI7iU2LR0FSBvMLhfBgvoaLs
                @Override // java.lang.Runnable
                public final void run() {
                    b.HandlerC0175b.this.b(bVar, mediaFormat);
                }
            }).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(b bVar, MediaFormat mediaFormat) {
            a aVar = this.a;
            if (aVar != null) {
                aVar.a(bVar, mediaFormat);
            }
        }

        void a(final b bVar, final int i, final MediaCodec.BufferInfo bufferInfo) {
            Message.obtain(this, new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.b.b.2
                @Override // java.lang.Runnable
                public void run() {
                    if (HandlerC0175b.this.a != null) {
                        HandlerC0175b.this.a.a(bVar, i, bufferInfo);
                    }
                }
            }).sendToTarget();
        }
    }
}
