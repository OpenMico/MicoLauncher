package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.mirror.Device;
import com.xiaomi.miplay.mylibrary.mirror.glec.EGLRender;
import com.xiaomi.miplay.mylibrary.utils.CpuInfo;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class ScreenEncoder implements Device.RotationListener {
    public static long firastPts = -1;
    public static MirrorControl mMirror = null;
    private static boolean p = true;
    private String a;
    private final AtomicBoolean b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private long g;
    private AtomicBoolean h;
    private final Object i;
    private final Object j;
    private MediaCodec k;
    private Surface l;
    private VirtualDisplay m;
    private volatile boolean n;
    private EGLRender o;
    private Context q;
    private int r;

    public boolean consumeRotationChange() {
        return false;
    }

    public ScreenEncoder(boolean z, int i, int i2, int i3) {
        this.a = "MiPlayQuickEncoder";
        this.b = new AtomicBoolean();
        this.h = new AtomicBoolean(false);
        this.i = new Object();
        this.j = new Object();
        this.n = false;
        this.q = null;
        this.r = 0;
        this.f = z;
        this.c = i;
        this.d = i2;
        this.e = i3;
    }

    public ScreenEncoder(boolean z, int i, int i2, int i3, Context context) {
        this.a = "MiPlayQuickEncoder";
        this.b = new AtomicBoolean();
        this.h = new AtomicBoolean(false);
        this.i = new Object();
        this.j = new Object();
        this.n = false;
        this.q = null;
        this.r = 0;
        this.f = z;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.q = context;
    }

    public ScreenEncoder(boolean z, int i, int i2) {
        this(z, i, i2, 1);
    }

    public ScreenEncoder(boolean z, int i, int i2, Context context) {
        this(z, i, i2, 1, context);
    }

    @Override // com.xiaomi.miplay.mylibrary.mirror.Device.RotationListener
    public void onRotationChanged(int i) {
        this.b.set(true);
    }

    public void stop() {
        if (!this.h.get()) {
            this.h.set(true);
            if (this.n) {
                synchronized (this.j) {
                    this.j.notify();
                    Log.i(this.a, "mMirrorControlPause notify");
                }
                this.n = false;
            }
            if (this.k != null) {
                synchronized (this.i) {
                    String str = this.a;
                    Log.i(str, "ScreenEncoderStop start! bQuit " + this.h.get());
                    try {
                        this.i.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pause() {
        this.n = true;
        Log.i(this.a, "pause");
    }

    private boolean a() {
        return CpuInfo.getCpuName().equals("SM8350");
    }

    private boolean b() {
        if (this.r == 0) {
            if (a() && Build.VERSION.SDK_INT == 30) {
                this.r = 2;
            } else {
                this.r = 1;
            }
        }
        return this.r == 1;
    }

    public void resume() {
        if (this.n) {
            synchronized (this.j) {
                if (this.k != null && b()) {
                    this.k.flush();
                }
                this.j.notify();
                Log.i(this.a, "mMirrorControlPause notify");
            }
        }
        this.n = false;
        Log.i(this.a, Commands.RESUME);
    }

    public void awake_pause() {
        Log.i(this.a, "awake_pause start");
        if (this.n) {
            synchronized (this.j) {
                this.j.notify();
                Log.i(this.a, "mMirrorControlPause notify");
            }
        }
        this.n = false;
        Log.i(this.a, "awake_pause end");
    }

    private long c() {
        return p ? SystemClock.elapsedRealtimeNanos() : System.nanoTime();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void streamScreen(Device device, MirrorControl mirrorControl, MirrorServer mirrorServer) throws IOException {
        Device device2;
        Device.RotationListener rotationListener;
        Throwable th;
        Device device3;
        Device.RotationListener rotationListener2;
        Device device4;
        Exception e;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (mirrorServer.startVirtual()) {
            try {
                Log.i(this.a, "format, bitrate:" + this.c + ", fps:" + this.d + ", iframe:" + this.e);
                MediaFormat a = a(this.c, this.d, this.e);
                device.setRotationListener(this);
                mMirror = mirrorControl;
                try {
                    Rect contentRect = device.getScreenInfo().getContentRect();
                    Rect rect = device.getScreenInfo().getVideoSize().toRect();
                    Log.i(this.a, "contentRect left:" + contentRect.left + " top:" + contentRect.top);
                    Log.i(this.a, "contentRect right:" + contentRect.right + " bottom:" + contentRect.bottom);
                    Log.i(this.a, "contentRect size:" + contentRect.width() + "x" + contentRect.height());
                    Log.i(this.a, "video left:" + rect.left + " top:" + rect.top);
                    Log.i(this.a, "video right:" + rect.right + " bottom:" + rect.bottom);
                    Log.i(this.a, "video size:" + rect.width() + "x" + rect.height());
                    this.k = d();
                    a(a, rect.width(), rect.height());
                    a(this.k, a);
                    Surface createInputSurface = this.k.createInputSurface();
                    this.l = createInputSurface;
                    Configuration configuration = this.q.getResources().getConfiguration();
                    contentRect.width();
                    contentRect.height();
                    if (!p) {
                        i = rect.width();
                        i2 = rect.height();
                    } else if (configuration.orientation == 1) {
                        i = contentRect.height();
                        i2 = contentRect.width();
                    } else {
                        i = contentRect.width();
                        i2 = contentRect.height();
                    }
                    if (p) {
                        try {
                            try {
                                if (configuration.orientation == 1) {
                                    i3 = i2;
                                    i4 = i;
                                    i5 = 1;
                                    this.o = new EGLRender(createInputSurface, i, i2, rect.width(), rect.height(), this.d, 0L, this.q, device);
                                } else {
                                    i3 = i2;
                                    i4 = i;
                                    i5 = 1;
                                    this.o = new EGLRender(createInputSurface, i4, i3, rect.width(), rect.height(), this.d, 0L, this.q, device);
                                }
                                this.o.setCallBack(new EGLRender.onFrameCallBack() { // from class: com.xiaomi.miplay.mylibrary.mirror.ScreenEncoder.1
                                    @Override // com.xiaomi.miplay.mylibrary.mirror.glec.EGLRender.onFrameCallBack
                                    public void onError() {
                                    }

                                    @Override // com.xiaomi.miplay.mylibrary.mirror.glec.EGLRender.onFrameCallBack
                                    public void onStop() {
                                        Log.i(ScreenEncoder.this.a, "EglRender onStop!");
                                        ScreenEncoder.this.o.releaseResource();
                                        ScreenEncoder.this.o = null;
                                    }
                                });
                                this.l = this.o.getDecodeSurface();
                            } catch (Throwable th2) {
                                th = th2;
                                rotationListener = null;
                                device2 = device;
                                device2.setRotationListener(rotationListener);
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            device4 = device;
                            Log.e(this.a, e.toString());
                            rotationListener2 = null;
                            device3 = device4;
                            device3.setRotationListener(rotationListener2);
                        }
                    } else {
                        i3 = i2;
                        i4 = i;
                        i5 = 1;
                    }
                    try {
                        this.m = mirrorServer.getVirtualDisplay();
                        if (configuration.orientation == i5) {
                            VirtualDisplay virtualDisplay = this.m;
                            int screenDensity = mirrorServer.getScreenDensity();
                            virtualDisplay.resize(i4, i3, screenDensity);
                            rotationListener2 = virtualDisplay;
                            device4 = screenDensity;
                        } else {
                            VirtualDisplay virtualDisplay2 = this.m;
                            int screenDensity2 = mirrorServer.getScreenDensity();
                            virtualDisplay2.resize(i4, i3, screenDensity2);
                            rotationListener2 = virtualDisplay2;
                            device4 = screenDensity2;
                        }
                        try {
                            try {
                                try {
                                    this.k.start();
                                    try {
                                        if (p && this.o != null) {
                                            this.o.setStartTimeNs(c());
                                            this.o.start();
                                        }
                                        this.m.setSurface(this.l);
                                        a(this.k, mirrorControl);
                                        this.k.stop();
                                        if (p && this.o != null) {
                                            this.o.stop();
                                        }
                                        this.k.release();
                                        this.k = null;
                                        this.l.release();
                                        synchronized (this.i) {
                                            try {
                                                this.i.notify();
                                                Log.i(this.a, "mScreenEncoderStop notify");
                                            } catch (Throwable th3) {
                                                throw th3;
                                            }
                                        }
                                        Log.i(this.a, "close encode");
                                    } catch (Exception e3) {
                                        Log.e(this.a, e3.toString());
                                        this.k.stop();
                                        if (p && this.o != null) {
                                            this.o.stop();
                                        }
                                        this.k.release();
                                        this.k = null;
                                        this.l.release();
                                        synchronized (this.i) {
                                            try {
                                                this.i.notify();
                                                Log.i(this.a, "mScreenEncoderStop notify");
                                                Log.i(this.a, "close encode");
                                            } catch (Throwable th4) {
                                                th = th4;
                                                while (true) {
                                                    try {
                                                        break;
                                                    } catch (Throwable th5) {
                                                        th = th5;
                                                    }
                                                }
                                                throw th;
                                            }
                                        }
                                    }
                                    rotationListener2 = null;
                                    device3 = device;
                                } catch (Exception e4) {
                                    e = e4;
                                    Log.e(this.a, e.toString());
                                    rotationListener2 = null;
                                    device3 = device4;
                                    device3.setRotationListener(rotationListener2);
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                rotationListener = rotationListener2;
                                device2 = device;
                                device2.setRotationListener(rotationListener);
                                throw th;
                            }
                        } catch (Throwable th7) {
                            device2 = device;
                            this.k.stop();
                            if (p && this.o != null) {
                                this.o.stop();
                            }
                            this.k.release();
                            rotationListener = null;
                            try {
                                this.k = null;
                                this.l.release();
                                synchronized (this.i) {
                                    try {
                                        this.i.notify();
                                        Log.i(this.a, "mScreenEncoderStop notify");
                                        Log.i(this.a, "close encode");
                                        throw th7;
                                    } catch (Throwable th8) {
                                        throw th8;
                                    }
                                }
                            } catch (Throwable th9) {
                                th = th9;
                                device2.setRotationListener(rotationListener);
                                throw th;
                            }
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        device2 = device;
                        rotationListener = null;
                        device2.setRotationListener(rotationListener);
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                    device4 = device;
                } catch (Throwable th11) {
                    th = th11;
                    device2 = device;
                }
                device3.setRotationListener(rotationListener2);
            } catch (Throwable th12) {
                th = th12;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0096, code lost:
        android.util.Log.i(r10.a, "consumeRotationChange");
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x009d, code lost:
        if (r2 < 0) goto L_0x00a2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x009f, code lost:
        r11.releaseOutputBuffer(r2, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a2, code lost:
        r2 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(android.media.MediaCodec r11, com.xiaomi.miplay.mylibrary.mirror.MirrorControl r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.mirror.ScreenEncoder.a(android.media.MediaCodec, com.xiaomi.miplay.mylibrary.mirror.MirrorControl):boolean");
    }

    private long a(MediaCodec.BufferInfo bufferInfo, int i) throws IOException {
        if ((bufferInfo.flags & 2) != 0) {
            return -1L;
        }
        if (this.g == 0) {
            this.g = bufferInfo.presentationTimeUs;
        }
        return bufferInfo.presentationTimeUs - this.g;
    }

    private static MediaCodec d() throws IOException {
        return MediaCodec.createEncoderByType(MimeTypes.VIDEO_H264);
    }

    private static MediaFormat a(int i, int i2, int i3) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", MimeTypes.VIDEO_H264);
        mediaFormat.setInteger("bitrate", i);
        mediaFormat.setInteger("bitrate-mode", 2);
        mediaFormat.setInteger("prepend-sps-pps-to-idr-frames", 1);
        mediaFormat.setInteger("frame-rate", i2);
        mediaFormat.setInteger("color-format", 2130708361);
        mediaFormat.setInteger("i-frame-interval", i3);
        mediaFormat.setInteger("max-bframes", 0);
        mediaFormat.setLong("repeat-previous-frame-after", 500000L);
        if (i2 > 0) {
            if (Build.VERSION.SDK_INT >= 29) {
                mediaFormat.setFloat("max-fps-to-encoder", i2);
            } else {
                Ln.w("Max FPS is only supported since Android 10, the option has been ignored");
            }
        }
        return mediaFormat;
    }

    private void a(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        mediaCodec.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
    }

    private static void a(MediaFormat mediaFormat, int i, int i2) {
        mediaFormat.setInteger("width", i);
        mediaFormat.setInteger("height", i2);
    }

    public static void setVideoHandleEnable(boolean z) {
        p = z;
        Log.d("MiPlayQuickEncoder", "VideoHandleEnable is " + p);
    }

    public void setVideoHandleMode(int i) {
        EGLRender eGLRender;
        if (p && (eGLRender = this.o) != null) {
            eGLRender.setVideoHandleMode(i);
        }
    }
}
