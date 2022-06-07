package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseLongArray;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.mirror.b;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MicRecorder.java */
/* loaded from: classes4.dex */
public class c implements Encoder {
    private a b;
    private AudioRecord c;
    private int d;
    private int e;
    private int h;
    private MirrorControl p;
    private MultiMirrorControl q;
    private int[] r;
    private Context s;
    private MiuiRecorder t;
    private boolean u;
    private int f = 2;
    private volatile boolean g = false;
    private final Object i = new Object();
    private final Object j = new Object();
    private final Object k = new Object();
    private int l = 0;
    private long m = 0;
    private long n = 0;
    private AtomicBoolean o = new AtomicBoolean(false);
    private long v = 0;
    private int w = 0;
    b.a a = new b.a() { // from class: com.xiaomi.miplay.mylibrary.mirror.c.2
        boolean a = false;

        @Override // com.xiaomi.miplay.mylibrary.mirror.b.a
        public void a(b bVar, MediaFormat mediaFormat) {
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.b.a
        public void a(b bVar, MediaCodec mediaCodec, int i) {
            int i2;
            if (mediaCodec == null) {
                Log.d("MicRecorder", "MediaCodec==null.");
            } else if (c.this.g) {
                Log.d("MicRecorder", "mPause.");
                synchronized (c.this.k) {
                    Log.i("MicRecorder", "mMicRecorderPause start! ");
                    try {
                        c.this.k.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                c.this.d();
                mediaCodec.queueInputBuffer(i, 0, 0, 0L, 0);
                Log.d("MicRecorder", "mPause after.");
            } else {
                ByteBuffer inputBuffer = mediaCodec.getInputBuffer(i);
                int position = inputBuffer.position();
                int limit = inputBuffer.limit();
                int i3 = 4096;
                if (limit <= 4096) {
                    i3 = limit;
                }
                boolean z = false;
                if (c.this.c == null) {
                    Log.d("MicRecorder", "------read--------");
                    try {
                        Thread.sleep(15L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    i2 = 0;
                } else {
                    i2 = c.this.c.read(inputBuffer, i3);
                }
                if (i2 <= 0) {
                    Log.e("MicRecorder", "Read frame data size " + i2 + " for index " + i + "e buffer : " + position + ", " + i3);
                    i2 = 0;
                }
                long c = c.this.c(i2 << 3);
                if (i2 > 0) {
                    long j = (i2 * 1000000) / c.this.h;
                    long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos() / 1000;
                    if (c.this.v > 0 && c.this.v + (2 * j) < elapsedRealtimeNanos) {
                        z = true;
                    }
                    if (z || c.this.w > 0) {
                        inputBuffer.clear();
                        inputBuffer.put(new byte[i2]);
                        if (z) {
                            c.this.w = 3;
                        }
                        c.h(c.this);
                    }
                    c.this.v = elapsedRealtimeNanos + j;
                }
                mediaCodec.queueInputBuffer(i, position, i2, c, 1);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.b.a
        public void a(b bVar, int i, MediaCodec.BufferInfo bufferInfo) {
            try {
                c.this.a(bVar, i, bufferInfo);
            } catch (Exception e) {
                Log.e("MicRecorder", "Muxer encountered an error! ", e);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.Encoder.Callback
        public void onError(Encoder encoder, Exception exc) {
            this.a = true;
            Log.e("MicRecorder", "MicRecorder ran into an error! ", exc);
        }
    };
    private SparseLongArray x = new SparseLongArray(2);

    static /* synthetic */ int h(c cVar) {
        int i = cVar.w;
        cVar.w = i - 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AudioEncodeConfig audioEncodeConfig) {
        this.u = false;
        this.s = audioEncodeConfig.getmContext();
        this.r = audioEncodeConfig.getUidList();
        this.b = new a(audioEncodeConfig);
        this.d = audioEncodeConfig.getSampleRate();
        this.h = this.d * audioEncodeConfig.getChannelCount();
        this.e = audioEncodeConfig.getChannelCount() == 2 ? 12 : 16;
        try {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (Class.forName("miui.media.MiuiAudioPlaybackRecorder") == null) {
            Log.d("MicRecorder", "MiuiAudioPlaybackRecorder is null object");
            return;
        }
        this.u = true;
        this.t = new MiuiRecorder(this.s);
        MiPlayAudioService.add(new MiPlayAudioService.CaptureCallBack() { // from class: com.xiaomi.miplay.mylibrary.mirror.c.1
            @Override // com.xiaomi.miplay.mylibrary.MiPlayAudioService.CaptureCallBack
            public void stopCapture(int[] iArr) {
            }

            @Override // com.xiaomi.miplay.mylibrary.MiPlayAudioService.CaptureCallBack
            public void startCapture(int[] iArr, int i, int i2, int i3) {
                Log.d("MicRecorder", "MiPlayAudioService start capture");
                c.this.r = iArr;
                c.this.d = i;
                c.this.e = i2;
                c.this.f = i3;
                if (c.this.t != null) {
                    c.this.t.updateUid(iArr, c.this.c);
                } else {
                    Logger.i("MicRecorder", "mMiuiRecorder==null", new Object[0]);
                }
                Log.d("MicRecorder", "startCapture end");
            }
        });
    }

    public void a(MirrorControl mirrorControl) {
        this.p = mirrorControl;
    }

    public void a(MultiMirrorControl multiMirrorControl) {
        this.q = multiMirrorControl;
    }

    public boolean a(long j) {
        this.o.set(true);
        return true;
    }

    private static void a(byte[] bArr, int i, int i2) {
        int i3;
        if (i2 == 96000) {
            i3 = 0;
        } else if (i2 == 48000) {
            i3 = 3;
        } else if (i2 == 44100) {
            i3 = 4;
        } else if (i2 == 32000) {
            i3 = 5;
        } else if (i2 == 24000) {
            i3 = 6;
        } else {
            i3 = i2 == 22050 ? 7 : i2 == 16000 ? 8 : i2 == 11025 ? 10 : i2 == 8000 ? 11 : 3;
        }
        bArr[0] = -1;
        bArr[1] = -7;
        bArr[2] = (byte) (64 + (i3 << 2) + 0);
        bArr[3] = (byte) (128 + (i >> 11));
        bArr[4] = (byte) ((i & 2047) >> 3);
        bArr[5] = (byte) (((i & 7) << 5) + 31);
        bArr[6] = -4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(b bVar, int i, MediaCodec.BufferInfo bufferInfo) throws IOException {
        ByteBuffer a = bVar.a(i);
        if (a != null) {
            a.position(bufferInfo.offset);
            a.limit(bufferInfo.offset + bufferInfo.size);
            byte[] bArr = new byte[bufferInfo.size + 7];
            a(bArr, bufferInfo.size + 7, this.d);
            a.get(bArr, 7, bufferInfo.size);
            if (this.m == 0) {
                this.m = bufferInfo.presentationTimeUs;
                this.n = SystemClock.elapsedRealtimeNanos() / 1000;
            }
            long j = bufferInfo.presentationTimeUs - this.m;
            long elapsedRealtimeNanos = (SystemClock.elapsedRealtimeNanos() / 1000) - this.n;
            long j2 = elapsedRealtimeNanos - j;
            if (j2 > 50) {
                this.m -= j2;
                j = bufferInfo.presentationTimeUs - this.m;
                Log.d("MicRecorder", " fix pts:" + (elapsedRealtimeNanos - j) + ", sys:" + elapsedRealtimeNanos + ",pts:" + j);
            }
            if (bufferInfo.size > 7) {
                MirrorControl mirrorControl = this.p;
                if (mirrorControl != null) {
                    mirrorControl.WriteStream(false, bArr, j);
                } else {
                    if (this.o.get()) {
                        Log.d("MicRecorder", "switch source:" + j);
                        this.q.switchSource(j);
                        this.o.set(false);
                    }
                    this.q.WriteStream(false, bArr, j);
                }
            } else {
                Log.e("MicRecorder", "configure size [" + bufferInfo.size + "] flag is [" + (bufferInfo.flags & 4) + "]");
            }
            bVar.b(i);
            if ((bufferInfo.flags & 4) != 0) {
                Log.d("MicRecorder", "Stop encoder and muxer, since the buffer has been marked with EOS");
            }
        }
    }

    public void a() throws IOException {
        AudioRecord audioRecord;
        if (this.u) {
            MiuiRecorder miuiRecorder = this.t;
            if (miuiRecorder == null) {
                Log.d("MicRecorder", "mMiuiRecorder==null");
                return;
            }
            audioRecord = miuiRecorder.createAudioRecord(this.d, this.e, this.f, this.r);
        } else {
            audioRecord = a(this.d, this.e, this.f);
        }
        if (audioRecord == null) {
            Log.e("MicRecorder", "create audio record failure");
        } else {
            audioRecord.startRecording();
            this.c = audioRecord;
        }
        try {
            this.b.a(this.a);
            this.b.b();
        } catch (Exception unused) {
            Log.e("MicRecorder", "mEncoder failure");
        }
    }

    public void b() {
        Log.d("MicRecorder", "record stop");
        if (this.g) {
            synchronized (this.k) {
                this.k.notify();
                Log.i("MicRecorder", "mMirrorControlPause notify");
            }
            this.g = false;
        }
        a aVar = this.b;
        if (aVar != null) {
            aVar.d();
        }
        AudioRecord audioRecord = this.c;
        if (audioRecord != null) {
            audioRecord.stop();
        }
        Log.d("MicRecorder", "record stop end");
    }

    public void c() {
        Log.d("MicRecorder", "release");
        a aVar = this.b;
        if (aVar != null) {
            aVar.f();
            this.b = null;
        }
        AudioRecord audioRecord = this.c;
        if (audioRecord != null) {
            audioRecord.release();
            this.c = null;
        }
        MiuiRecorder miuiRecorder = this.t;
        if (miuiRecorder != null) {
            miuiRecorder.releaseRecorder();
            this.t = null;
        }
    }

    public void d() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.e();
        }
        Log.i("MicRecorder", "flush");
    }

    public void a(int i) {
        this.g = true;
        Log.i("MicRecorder", "pause start");
        AudioRecord audioRecord = this.c;
        if (audioRecord != null) {
            audioRecord.stop();
        }
        Log.i("MicRecorder", "pause mMic stop");
        Log.i("MicRecorder", "pause end");
    }

    public void b(int i) {
        Log.i("MicRecorder", "resume start");
        AudioRecord audioRecord = this.c;
        if (audioRecord != null) {
            audioRecord.startRecording();
        }
        Log.i("MicRecorder", "resume mMic start");
        if (this.g) {
            synchronized (this.k) {
                this.k.notify();
                Log.i("MicRecorder", "mMicRecorderPause notify");
            }
        }
        this.g = false;
        Log.i("MicRecorder", "resume end");
    }

    public void e() {
        Log.i("MicRecorder", "awake_pause start");
        if (this.g) {
            synchronized (this.k) {
                this.k.notify();
                Log.i("MicRecorder", "mMicRecorderPause notify");
            }
        }
        this.g = false;
        Log.i("MicRecorder", "awake_pause end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long c(int i) {
        int i2 = i >> 4;
        long j = this.x.get(i2, -1L);
        if (j == -1) {
            j = (1000000 * i2) / this.h;
            this.x.put(i2, j);
        }
        long elapsedRealtimeNanos = (SystemClock.elapsedRealtimeNanos() / 1000) - j;
        long j2 = this.x.get(-1, -1L);
        if (elapsedRealtimeNanos >= 50 + j2) {
            j2 = -1;
        }
        if (j2 == -1) {
            j2 = elapsedRealtimeNanos;
        }
        if (elapsedRealtimeNanos - j2 < (j << 1)) {
            elapsedRealtimeNanos = j2;
        }
        this.x.put(-1, j + elapsedRealtimeNanos);
        return elapsedRealtimeNanos;
    }

    private AudioRecord a(int i, int i2, int i3) {
        this.l = AudioRecord.getMinBufferSize(i, i2, i3);
        if (this.l <= 0) {
            Log.e("MicRecorder", String.format(Locale.US, "Bad arguments: getMinBufferSize(%d, %d, %d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
            return null;
        }
        Log.e("MicRecorder", "audio buffersize:" + this.l);
        AudioRecord audioRecord = new AudioRecord(8, i, i2, i3, this.l * 16);
        if (audioRecord.getState() != 0) {
            return audioRecord;
        }
        Log.e("MicRecorder", String.format(Locale.US, "Bad arguments to new AudioRecord %d, %d, %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        return null;
    }
}
