package com.xiaomi.ai.android.impl;

import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.xiaomi.ai.android.capability.SpeechSynthesizerCapability;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class a {
    private int a;
    private String b;
    private AudioTrack c;
    private MediaCodec d;
    private SpeechSynthesizerCapability e;
    private final Object f;
    private final Object g;
    private Future<?> h;
    private Future<?> i;
    private final com.xiaomi.ai.android.core.d j;
    private c k;
    private d l;
    private MediaPlayer m;
    private b n;
    private HandlerThread o;
    private Handler p;
    private boolean q;
    private volatile boolean r;

    /* renamed from: com.xiaomi.ai.android.impl.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static class C0155a {
        private final HashMap<String, Integer> a = new HashMap<>();
        private final HashMap<String, Integer> b = new HashMap<>();
        private String c;
        private String d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;

        C0155a() {
            this.a.put(a("Layer III", "MPEG2", new char[]{'0', '0', '1', '1'}, 0, 4), 128000);
            this.a.put(a("Layer III", "MPEG2", new char[]{'0', '1', '0', '0'}, 0, 4), 16000);
            this.a.put(a("Layer III", "MPEG2", new char[]{'0', '0', '1', '0'}, 0, 4), 32000);
            this.b.put(a("Layer III", "MPEG1", null, 0, 0), 1152);
            this.b.put(a("Layer III", "MPEG2", null, 0, 0), 576);
            this.b.put(a("Layer III", "MPEG2.5", null, 0, 0), 576);
        }

        private String a(byte b) {
            String binaryString = Integer.toBinaryString(b | 256);
            int length = binaryString.length();
            return binaryString.substring(length - 8, length);
        }

        private String a(String str, String str2, char[] cArr, int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2);
            if (cArr != null) {
                for (int i3 = i; i3 < i + i2; i3++) {
                    sb.append(cArr[i3]);
                }
            }
            return sb.toString();
        }

        private boolean a(char[] cArr) {
            for (int i = 21; i < 31; i++) {
                if (cArr[i] != '1') {
                    return false;
                }
            }
            return true;
        }

        private int b(char[] cArr) {
            if (cArr[7] == '0' && cArr[6] == '0') {
                return 2;
            }
            return (cArr[7] == '1' && cArr[6] == '1') ? 1 : 0;
        }

        private char[] b(byte[] bArr) {
            char[] c = c(bArr);
            char[] cArr = new char[c.length];
            for (int i = 0; i < c.length; i++) {
                cArr[(c.length - i) - 1] = c[i];
            }
            return cArr;
        }

        private int c(char[] cArr) {
            Integer num = this.b.get(a(g(cArr), h(cArr), null, 0, 0));
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        private char[] c(byte[] bArr) {
            StringBuilder sb = new StringBuilder();
            int length = bArr.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%s", a(bArr[i])));
            }
            return sb.toString().toCharArray();
        }

        private int d(char[] cArr) {
            Integer num = this.a.get(a(g(cArr), h(cArr), cArr, 12, 4));
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        private int e(char[] cArr) {
            int i = 0;
            if (g(cArr) == "Layer I") {
                int floor = (int) Math.floor(((c(cArr) / 8.0f) * d(cArr)) / f(cArr));
                if (cArr[9] == '1') {
                    i = 4;
                }
                return floor + i;
            }
            int floor2 = (int) Math.floor(((c(cArr) / 8.0f) * d(cArr)) / f(cArr));
            if (cArr[9] == '1') {
                i = 1;
            }
            return floor2 + i;
        }

        private int f(char[] cArr) {
            if (cArr[20] == '0' && cArr[19] == '0') {
                if (cArr[11] == '0' && cArr[10] == '0') {
                    return 11025;
                }
                if (cArr[11] == '0' && cArr[10] == '1') {
                    return 12000;
                }
                return (cArr[11] == '1' && cArr[10] == '0') ? 8000 : -1;
            } else if (cArr[20] != '1' || cArr[19] != '0') {
                if (cArr[20] == '1' && cArr[19] == '1') {
                    if (cArr[11] == '0' && cArr[10] == '0') {
                        return 44100;
                    }
                    if (cArr[11] == '0' && cArr[10] == '1') {
                        return OpusUtil.SAMPLE_RATE;
                    }
                    if (cArr[11] == '1' && cArr[10] == '0') {
                        return 32000;
                    }
                }
                return -1;
            } else if (cArr[11] == '0' && cArr[10] == '0') {
                return 22050;
            } else {
                if (cArr[11] == '0' && cArr[10] == '1') {
                    return 24000;
                }
                return (cArr[11] == '1' && cArr[10] == '0') ? 16000 : -1;
            }
        }

        private String g(char[] cArr) {
            return (cArr[18] == '0' && cArr[17] == '1') ? "Layer III" : (cArr[18] == '1' && cArr[17] == '0') ? "Layer II" : (cArr[18] == '1' && cArr[17] == '1') ? "Layer I" : "Layer unkown";
        }

        private String h(char[] cArr) {
            return (cArr[20] == '0' && cArr[19] == '0') ? "MPEG2.5" : (cArr[20] == '1' && cArr[19] == '0') ? "MPEG2" : (cArr[20] == '1' && cArr[19] == '1') ? "MPEG1" : "MPEG_UNKNOW";
        }

        public int a() {
            return this.h;
        }

        public boolean a(byte[] bArr) {
            char[] b = b(bArr);
            if (!a(b)) {
                return false;
            }
            this.c = g(b);
            this.d = h(b);
            this.f = d(b);
            this.e = f(b);
            this.g = c(b);
            this.h = e(b);
            this.i = b(b);
            return this.c != "Layer unkown" && this.d != "MPEG_UNKNOW" && this.h > 0 && this.e > 0 && this.f > 0 && this.i > 0 && this.g > 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum b {
        PLAYER_MODE_STREAM,
        PLAYER_MODE_URL
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c implements Runnable {
        private PipedOutputStream c;
        private d g;
        private volatile boolean d = true;
        private boolean e = false;
        private boolean f = false;
        private PipedInputStream b = new PipedInputStream(1024);

        c(d dVar) {
            this.g = dVar;
            try {
                this.c = new PipedOutputStream(this.b);
            } catch (IOException e) {
                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
            }
        }

        private int a(byte[] bArr, int i, int i2) {
            int i3 = 0;
            while (this.d && i2 > 0) {
                try {
                    int read = this.b.read(bArr, i, i2);
                    if (!Thread.interrupted() && this.d) {
                        if (read <= 0) {
                            Logger.b("MediaPlayerImpl", "readStream: read size = " + read);
                            return i3;
                        }
                        i3 += read;
                        i += read;
                        i2 -= read;
                    }
                    Logger.b("MediaPlayerImpl", "readStream: interrupted or loop=" + this.d);
                    return -1;
                } catch (IOException e) {
                    Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
                }
            }
            return i3;
        }

        private void b() {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int dequeueOutputBuffer = a.this.d.dequeueOutputBuffer(bufferInfo, 10000L);
            if (dequeueOutputBuffer >= 0) {
                ByteBuffer outputBuffer = Build.VERSION.SDK_INT >= 21 ? a.this.d.getOutputBuffer(dequeueOutputBuffer) : a.this.d.getOutputBuffers()[dequeueOutputBuffer];
                if (outputBuffer == null) {
                    Logger.d("MediaPlayerImpl", "pullDecodedData: byteBuffer is null");
                    return;
                }
                if (bufferInfo.size > 0) {
                    byte[] bArr = new byte[bufferInfo.size];
                    outputBuffer.get(bArr, 0, bufferInfo.size);
                    this.g.a(bArr);
                    Logger.a("MediaPlayerImpl", "pullDecodedData: length=" + bufferInfo.size);
                }
                outputBuffer.clear();
                a.this.d.releaseOutputBuffer(dequeueOutputBuffer, false);
                if (bufferInfo.flags == 4) {
                    this.g.a(new byte[0]);
                    this.d = false;
                    Logger.b("MediaPlayerImpl", "pullDecodedData: BUFFER_FLAG_END_OF_STREAM");
                }
            }
        }

        private void b(byte[] bArr, boolean z) {
            int i;
            StringBuilder sb = new StringBuilder();
            sb.append("putEncodeData: length=");
            int i2 = 0;
            sb.append(bArr != null ? bArr.length : 0);
            sb.append(", eof=");
            sb.append(z);
            Logger.a("MediaPlayerImpl", sb.toString());
            while (this.d) {
                int dequeueInputBuffer = a.this.d.dequeueInputBuffer(10000L);
                if (dequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer = Build.VERSION.SDK_INT >= 21 ? a.this.d.getInputBuffer(dequeueInputBuffer) : a.this.d.getInputBuffers()[dequeueInputBuffer];
                    if (inputBuffer != null) {
                        inputBuffer.clear();
                        if (bArr != null) {
                            inputBuffer.put(bArr);
                            i = bArr.length;
                        } else {
                            i = 0;
                        }
                        MediaCodec mediaCodec = a.this.d;
                        if (z) {
                            i2 = 4;
                        }
                        mediaCodec.queueInputBuffer(dequeueInputBuffer, 0, i, 0L, i2);
                        return;
                    }
                    Logger.d("MediaPlayerImpl", "putEncodeData: byteBuffer is null, index=" + dequeueInputBuffer);
                }
            }
        }

        public int a(byte[] bArr, boolean z) {
            int length;
            synchronized (this) {
                if (this.c != null) {
                    if (bArr != null) {
                        try {
                            this.c.write(bArr);
                        } catch (Exception e) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask write: " + e.getMessage());
                            return -1;
                        }
                    }
                    if (this.c != null && z) {
                        this.c.close();
                        this.c = null;
                    }
                }
                length = bArr != null ? bArr.length : 0;
            }
            return length;
        }

        public void a() {
            Logger.b("MediaPlayerImpl", "StreamDecodeTask exit");
            this.d = false;
            a(null, true);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    Logger.b("MediaPlayerImpl", "StreamDecodeTask begin");
                    byte[] bArr = new byte[4];
                    C0155a aVar = new C0155a();
                    while (this.d) {
                        if (!this.f) {
                            if (a(bArr, 0, 4) == 4) {
                                if (!aVar.a(bArr)) {
                                    Logger.d("MediaPlayerImpl", "StreamDecodeTask: invalid mp3 header");
                                } else {
                                    int a = aVar.a();
                                    byte[] bArr2 = new byte[a];
                                    System.arraycopy(bArr, 0, bArr2, 0, 4);
                                    int i = a - 4;
                                    if (a(bArr2, 4, i) == i) {
                                        b(bArr2, false);
                                    }
                                }
                            }
                            this.f = true;
                        } else if (!this.e) {
                            b(null, true);
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask: put end flag");
                            this.e = true;
                        }
                        b();
                    }
                    try {
                        if (this.c != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask OutputStream close");
                            this.c.close();
                            this.c = null;
                        }
                        if (this.b != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask InputStream close");
                            this.b.close();
                            this.b = null;
                        }
                    } catch (Exception e) {
                        Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
                    }
                    try {
                        synchronized (a.this.f) {
                            try {
                                if (a.this.d != null) {
                                    a.this.d.flush();
                                }
                            } catch (IllegalStateException e2) {
                                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e2));
                                a.this.r = false;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                } catch (IllegalStateException e3) {
                    Logger.d("MediaPlayerImpl", Log.getStackTraceString(e3));
                    a.this.r = false;
                    try {
                        if (this.c != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask OutputStream close");
                            this.c.close();
                            this.c = null;
                        }
                        if (this.b != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask InputStream close");
                            this.b.close();
                            this.b = null;
                        }
                    } catch (Exception e4) {
                        Logger.d("MediaPlayerImpl", Log.getStackTraceString(e4));
                    }
                    synchronized (a.this.f) {
                        try {
                            try {
                                if (a.this.d != null) {
                                    a.this.d.flush();
                                }
                            } catch (IllegalStateException e5) {
                                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e5));
                                a.this.r = false;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                } catch (Exception e6) {
                    Logger.d("MediaPlayerImpl", Log.getStackTraceString(e6));
                    try {
                        if (this.c != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask OutputStream close");
                            this.c.close();
                            this.c = null;
                        }
                        if (this.b != null) {
                            Logger.b("MediaPlayerImpl", "StreamDecodeTask InputStream close");
                            this.b.close();
                            this.b = null;
                        }
                    } catch (Exception e7) {
                        Logger.d("MediaPlayerImpl", Log.getStackTraceString(e7));
                    }
                    synchronized (a.this.f) {
                        try {
                            try {
                                if (a.this.d != null) {
                                    a.this.d.flush();
                                }
                            } catch (IllegalStateException e8) {
                                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e8));
                                a.this.r = false;
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }
                Logger.b("MediaPlayerImpl", "StreamDecodeTask end");
            } catch (Throwable th4) {
                try {
                    if (this.c != null) {
                        Logger.b("MediaPlayerImpl", "StreamDecodeTask OutputStream close");
                        this.c.close();
                        this.c = null;
                    }
                    if (this.b != null) {
                        Logger.b("MediaPlayerImpl", "StreamDecodeTask InputStream close");
                        this.b.close();
                        this.b = null;
                    }
                } catch (Exception e9) {
                    Logger.d("MediaPlayerImpl", Log.getStackTraceString(e9));
                }
                synchronized (a.this.f) {
                    try {
                        try {
                            if (a.this.d != null) {
                                a.this.d.flush();
                            }
                        } catch (IllegalStateException e10) {
                            Logger.d("MediaPlayerImpl", Log.getStackTraceString(e10));
                            a.this.r = false;
                        }
                        throw th4;
                    } catch (Throwable th5) {
                        throw th5;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class d implements Runnable {
        private volatile boolean c = true;
        BlockingQueue<byte[]> a = new LinkedBlockingQueue();

        d() {
            if (!a.this.q) {
                return;
            }
            if (a.this.c == null || a.this.c.getState() == 0) {
                a.this.g();
            } else if (a.this.c != null) {
                try {
                    a.this.c.play();
                } catch (IllegalStateException e) {
                    a.this.r = false;
                    Logger.d("MediaPlayerImpl", Logger.throwableToString(e));
                }
            }
        }

        public int a(byte[] bArr) {
            try {
                this.a.put(bArr);
                return bArr.length;
            } catch (InterruptedException e) {
                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
                return -1;
            }
        }

        public void a() {
            Logger.b("MediaPlayerImpl", "StreamPlayerTask exit");
            this.c = false;
            this.a.clear();
            this.a.add(new byte[0]);
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x00f4, code lost:
            if (r5.b.j.b().getBoolean(com.xiaomi.ai.core.AivsConfig.Tts.ENABLE_PLAY_FINISH_DIALOG_ID) != false) goto L_0x0168;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x0166, code lost:
            if (r5.b.j.b().getBoolean(com.xiaomi.ai.core.AivsConfig.Tts.ENABLE_PLAY_FINISH_DIALOG_ID) != false) goto L_0x0168;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x0168, code lost:
            r5.b.e.onPlayFinish(r5.b.b);
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x0178, code lost:
            r5.b.e.onPlayFinish();
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 526
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.android.impl.a.d.run():void");
        }
    }

    public a(com.xiaomi.ai.android.core.d dVar) {
        this.a = 16000;
        this.f = new Object();
        this.g = new Object();
        this.n = b.PLAYER_MODE_STREAM;
        this.j = dVar;
        h();
    }

    public a(com.xiaomi.ai.android.core.d dVar, int i, String str) {
        this.a = 16000;
        this.f = new Object();
        this.g = new Object();
        this.n = b.PLAYER_MODE_STREAM;
        this.j = dVar;
        this.a = i;
        this.b = str;
        h();
    }

    private boolean h() {
        Logger.b("MediaPlayerImpl", "init: sampleRate = " + this.a);
        i();
        this.q = this.j.b().getBoolean(AivsConfig.Tts.ENABLE_INTERNAL_PLAYER, true);
        this.r = g();
        this.r = f();
        this.o = new HandlerThread("MediaPlayerImplThread");
        this.o.start();
        this.p = new Handler(this.o.getLooper(), new Handler.Callback() { // from class: com.xiaomi.ai.android.impl.a.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what == 1) {
                    c cVar = null;
                    synchronized (a.this) {
                        if (a.this.k != null) {
                            cVar = a.this.k;
                        }
                    }
                    if (cVar != null) {
                        Bundle data = message.getData();
                        cVar.a(data.getByteArray("data"), data.getBoolean(com.umeng.analytics.pro.c.aB));
                    }
                }
                return true;
            }
        });
        return this.r;
    }

    private void i() {
        Logger.b("MediaPlayerImpl", "release start");
        this.r = false;
        Handler handler = this.p;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.p = null;
        }
        HandlerThread handlerThread = this.o;
        if (handlerThread != null) {
            handlerThread.quit();
            Logger.b("MediaPlayerImpl", "release HandlerThread");
            this.o = null;
        }
        c cVar = this.k;
        if (cVar != null) {
            cVar.a();
            this.k = null;
            Logger.b("MediaPlayerImpl", "release StreamDecodeTask");
        }
        d dVar = this.l;
        if (dVar != null) {
            dVar.a();
            this.l = null;
            Logger.a("MediaPlayerImpl", "release StreamPlayerTask");
        }
        if (this.q) {
            synchronized (this.g) {
                if (this.c != null) {
                    this.c.release();
                    this.c = null;
                    Logger.b("MediaPlayerImpl", "release AudioTrack");
                }
            }
        }
        try {
            synchronized (this.f) {
                if (this.d != null) {
                    this.d.stop();
                    this.d.release();
                    this.d = null;
                    Logger.b("MediaPlayerImpl", "release MediaCodec");
                }
            }
        } catch (Exception e) {
            Logger.d("MediaPlayerImpl", Logger.throwableToString(e));
        }
        j();
        Logger.b("MediaPlayerImpl", "release end");
    }

    private void j() {
        if (this.m != null) {
            Logger.b("MediaPlayerImpl", "releaseMediaPlayer");
            this.m.stop();
            this.m.release();
            this.m = null;
        }
    }

    private String k() {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                MediaCodecInfo[] codecInfos = new MediaCodecList(1).getCodecInfos();
                for (MediaCodecInfo mediaCodecInfo : codecInfos) {
                    for (String str : mediaCodecInfo.getSupportedTypes()) {
                        if (str.equals("audio/mpeg")) {
                            Logger.b("MediaPlayerImpl", "getCodecName: " + mediaCodecInfo.getName());
                            return mediaCodecInfo.getName();
                        }
                    }
                }
                return null;
            }
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equals("audio/mpeg")) {
                        Logger.b("MediaPlayerImpl", "getCodecName: " + codecInfoAt.getName());
                        return codecInfoAt.getName();
                    }
                }
            }
            return null;
        } catch (Exception e) {
            Logger.d("MediaPlayerImpl", Logger.throwableToString(e));
            return null;
        }
    }

    public int a() {
        return this.a;
    }

    public int a(byte[] bArr, boolean z) {
        synchronized (this) {
            if (this.p == null) {
                return -1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("write: length=");
            int i = 0;
            sb.append(bArr != null ? bArr.length : 0);
            sb.append(", eof=");
            sb.append(z);
            Logger.b("MediaPlayerImpl", sb.toString());
            Bundle bundle = new Bundle();
            bundle.putByteArray("data", bArr);
            bundle.putBoolean(com.umeng.analytics.pro.c.aB, z);
            Message obtainMessage = this.p.obtainMessage(1);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
            if (bArr != null) {
                i = bArr.length;
            }
            return i;
        }
    }

    public void a(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public boolean a(String str) {
        synchronized (this) {
            Logger.b("MediaPlayerImpl", "prepare url=" + str);
            j();
            this.m = new MediaPlayer();
            this.m.setAudioStreamType(3);
            try {
                this.m.setDataSource(str);
                this.m.prepare();
                this.n = b.PLAYER_MODE_URL;
            } catch (Exception e) {
                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
                j();
                return false;
            }
        }
        return true;
    }

    public boolean b() {
        synchronized (this) {
            Logger.b("MediaPlayerImpl", "prepare");
            this.e = (SpeechSynthesizerCapability) this.j.a(SpeechSynthesizerCapability.class);
            int i = 0;
            if (this.q || this.e != null) {
                if (!this.r) {
                    h();
                }
                if (this.p != null) {
                    this.p.removeMessages(1);
                }
                if (this.l != null) {
                    this.l.a();
                    this.l = null;
                }
                if (this.k != null) {
                    this.k.a();
                    this.k = null;
                }
                if (this.h != null && this.i != null) {
                    while (true) {
                        if ((this.h.isDone() && this.i.isDone()) || i > 100) {
                            break;
                        }
                        try {
                            Thread.sleep(10L);
                            i++;
                        } catch (InterruptedException unused) {
                            Logger.b("MediaPlayerImpl", "prepare: wait InterruptedException");
                        }
                    }
                }
                this.l = new d();
                this.k = new c(this.l);
                this.h = com.xiaomi.ai.b.c.a.submit(this.l);
                this.i = com.xiaomi.ai.b.c.a.submit(this.k);
                return true;
            }
            Logger.d("MediaPlayerImpl", "init: SpeechSynthesizerCapability not registered");
            return false;
        }
    }

    public void c() {
        Logger.b("MediaPlayerImpl", "interrupt");
        Handler handler = this.p;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        d dVar = this.l;
        if (dVar != null) {
            dVar.a();
            this.l = null;
        }
        c cVar = this.k;
        if (cVar != null) {
            cVar.a();
            this.k = null;
        }
        j();
    }

    public boolean d() {
        synchronized (this) {
            Logger.b("MediaPlayerImpl", "play");
            if (this.n == b.PLAYER_MODE_URL) {
                if (this.m != null) {
                    this.m.start();
                } else {
                    Logger.d("MediaPlayerImpl", "play: not prepared yet");
                    return false;
                }
            }
            return true;
        }
    }

    public boolean e() {
        synchronized (this) {
            Logger.b("MediaPlayerImpl", "stop");
            i();
        }
        return true;
    }

    public boolean f() {
        String k = k();
        if (k == null) {
            Logger.d("MediaPlayerImpl", "init: no supported codec for MIME=audio/mpeg");
            return false;
        }
        try {
            this.d = MediaCodec.createByCodecName(k);
        } catch (Exception e) {
            Logger.d("MediaPlayerImpl", Log.getStackTraceString(e));
        }
        if (this.d == null) {
            try {
                this.d = MediaCodec.createDecoderByType("audio/mpeg");
            } catch (Exception e2) {
                Logger.d("MediaPlayerImpl", Log.getStackTraceString(e2));
                return false;
            }
        }
        try {
            this.d.configure(MediaFormat.createAudioFormat("audio/mpeg", this.a, 1), (Surface) null, (MediaCrypto) null, 0);
            this.d.start();
            return true;
        } catch (Exception e3) {
            Logger.d("MediaPlayerImpl", "initMediaCodec:init failed:" + e3.getMessage());
            return false;
        }
    }

    public boolean g() {
        if (!this.q) {
            return true;
        }
        this.c = new AudioTrack(3, this.a, 4, 2, AudioTrack.getMinBufferSize(this.a, 4, 2), 1);
        int state = this.c.getState();
        if (state == 0) {
            Logger.d("MediaPlayerImpl", "init: invalid AudioTrack state=" + state);
            this.c.release();
            this.c = null;
            return false;
        }
        this.c.play();
        return true;
    }
}
