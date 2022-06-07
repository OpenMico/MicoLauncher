package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.xiaomi.mico.base.utils.BitmapCache;
import com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin;
import com.xiaomi.miplay.mylibrary.utils.CpuInfo;
import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class MirrorControl implements Closeable {
    private static RemoteDisplayAdmin.Listener A = null;
    public static final int DISPLAY_INFO_BITRATE = 100003;
    public static final int DISPLAY_INFO_IS_UDP = 100001;
    public static final int DISPLAY_INFO_VIDEO_LATENCY = 100002;
    private static String c = "MiPlayQuickMirrorControl";
    private static boolean v = false;
    private static boolean w = true;
    private static Context y;
    private static int z;
    private Configuration B;
    private int C;
    private HandlerThread D;
    private a E;
    private Options d;
    private CaptureService e;
    private Context f;
    private long h;
    private volatile boolean g = false;
    private AtomicBoolean i = new AtomicBoolean(false);
    private AtomicBoolean j = new AtomicBoolean(false);
    private AtomicBoolean k = new AtomicBoolean(false);
    private AtomicBoolean l = new AtomicBoolean(false);
    private final Object m = new Object();
    private final Object n = new Object();
    private final Object o = new Object();
    private int q = 1920;
    private int r = 1080;
    private int s = 30;
    private int t = 7000000;
    private volatile int u = -1;
    private int x = 0;
    private int F = -1;
    private boolean G = false;
    ImageInfo a = new ImageInfo();
    ImageUtils b = new ImageUtils();
    private Lock p = new ReentrantLock();

    public static native void closeMirror(long j);

    public static native long createMirror(Object obj, String str, int i, int i2, int i3, int i4);

    public static native void deleteMirror(long j);

    public static native int getDeviceInfo(long j, int i);

    public static native boolean isSupportPhoto(long j);

    public static native boolean pauseMirror(long j, int i);

    public static native boolean resumeMirror(long j, int i);

    public static native int rotatePhoto(long j, String str, boolean z2, float f);

    public static native void setMirrorNetAnomalyQuit(int i);

    public static native void setMirrotVideoBitrate(int i);

    public static native int showPhoto(long j, String str);

    public static native boolean startMirror(long j);

    public static native int startShow(long j);

    public static native int startSlideshow(long j, int i, boolean z2);

    public static native int stopShow(long j);

    public static native int stopSlideshow(long j);

    public static native boolean writeData(long j, boolean z2, byte[] bArr, int i, long j2);

    public static native int zoomPhoto(long j, String str, int i, int i2, int i3, int i4, int i5, int i6, float f);

    public int restartVideo(int i) {
        return -1;
    }

    public void sendDeviceMessage(DeviceMessage deviceMessage) throws IOException {
    }

    static {
        try {
            System.loadLibrary("audiomirror-jni");
        } catch (Exception e) {
            Log.e(c, "Failed to load audiomirror-jni.so while initializing exception was ", e);
        }
    }

    public void getVideoEncodecInfoByCpu(Context context) {
        switch (CpuInfo.GetCpuSeriesFlag(context)) {
            case 1:
                Log.i(c, "CPU is CPU_Qualcomm_Series_8");
                this.q = 1920;
                this.r = 1080;
                this.s = 60;
                this.t = 7000000;
                this.x = 1;
                return;
            case 2:
                Log.i(c, "CPU is CPU_Qualcomm_Series_7");
                this.q = 1920;
                this.r = 1080;
                this.s = 60;
                this.t = 7000000;
                this.x = 2;
                return;
            case 3:
                Log.i(c, "CPU is CPU_Qualcomm_Series_6");
                this.q = 1920;
                this.r = 1080;
                this.s = 30;
                this.t = 5000000;
                this.x = 3;
                return;
            case 4:
                Log.i(c, "CPU is CPU_Qualcomm_Series_5");
                this.q = 1920;
                this.r = 1080;
                this.s = 30;
                this.t = 5000000;
                this.x = 4;
                return;
            case 5:
                Log.i(c, "CPU is CPU_Qualcomm_Series_4");
                this.q = 1280;
                this.r = BitmapCache.MAX_WIDTH;
                this.s = 30;
                this.t = 6000000;
                this.x = 5;
                return;
            case 6:
                Log.i(c, "CPU is CPU_MTK_HSeries");
                this.q = 1920;
                this.r = 1080;
                this.s = 60;
                this.t = 7000000;
                this.x = 6;
                return;
            case 7:
                Log.i(c, "CPU is CPU_MTK_MSeries");
                this.q = 1280;
                this.r = BitmapCache.MAX_WIDTH;
                this.s = 30;
                this.t = 3000000;
                this.x = 7;
                return;
            case 8:
                Log.i(c, "CPU is CPU_MTK_LSeries");
                this.q = 1280;
                this.r = BitmapCache.MAX_WIDTH;
                this.s = 30;
                this.t = 3000000;
                this.x = 8;
                return;
            default:
                Log.i(c, "CPU is CPU_Unknow");
                this.q = 1920;
                this.r = 1080;
                this.s = 30;
                this.t = 7000000;
                this.x = 0;
                return;
        }
    }

    private MirrorControl(Options options, CaptureService captureService, RemoteDisplayAdmin.Listener listener, Context context) throws IOException {
        this.B = null;
        v = false;
        this.d = options;
        this.e = captureService;
        A = listener;
        this.f = context;
        if (context != null && CpuInfo.GetProductNameIsAssign(context, "cetus")) {
            Configuration configuration = context.getResources().getConfiguration();
            this.B = new Configuration(configuration);
            this.C = configuration.screenLayout & 15;
            int i = this.C;
            if (i == 2) {
                Log.i(c, "start current is small screen");
            } else if (i == 3) {
                Log.i(c, "start current is large screen");
            }
        }
        this.D = new HandlerThread("HandlerThread");
        this.D.start();
        this.E = new a(this.D.getLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            boolean z = false;
            switch (message.what) {
                case 0:
                    Log.d(MirrorControl.c, "handleMessage MSG_START_MIRROR");
                    long createMirror = MirrorControl.createMirror(message.obj, MirrorControl.this.d.getIp(), MirrorControl.this.d.getPort(), MirrorControl.this.q, MirrorControl.this.r, MirrorControl.this.s);
                    if (createMirror != 0) {
                        MirrorControl.startMirror(createMirror);
                    }
                    MirrorControl.this.h = createMirror;
                    return;
                case 1:
                    Log.d(MirrorControl.c, "handleMessage MSG_CLOSE_MIRROR");
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.closeMirror(MirrorControl.this.h);
                    }
                    if (MirrorControl.this.g) {
                        synchronized (MirrorControl.this.m) {
                            Log.i(MirrorControl.c, "mMirrorControlStop start!");
                            try {
                                MirrorControl.this.m.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i(MirrorControl.c, "mMirrorControlStop end!");
                        }
                    }
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.deleteMirror(MirrorControl.this.h);
                        MirrorControl.this.h = 0L;
                    }
                    MirrorControl.this.E.removeCallbacksAndMessages(null);
                    MirrorControl.this.D.quitSafely();
                    return;
                case 2:
                    Log.d(MirrorControl.c, "handleMessage MSG_PAUSE_MIRROR");
                    int i = message.arg1;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.pauseMirror(MirrorControl.this.h, i);
                        return;
                    }
                    return;
                case 3:
                    Log.d(MirrorControl.c, "handleMessage MSG_RESUME_MIRROR");
                    int i2 = message.arg1;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.resumeMirror(MirrorControl.this.h, i2);
                        return;
                    }
                    return;
                case 4:
                    c cVar = (c) message.obj;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.writeData(MirrorControl.this.h, cVar.a(), cVar.b(), cVar.b().length, cVar.c());
                        return;
                    }
                    return;
                case 5:
                    int i3 = message.arg1;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl mirrorControl = MirrorControl.this;
                        mirrorControl.F = MirrorControl.getDeviceInfo(mirrorControl.h, i3);
                    } else {
                        MirrorControl.this.F = -1;
                    }
                    synchronized (MirrorControl.this.n) {
                        MirrorControl.this.n.notify();
                        Log.i(MirrorControl.c, "mMirrorControlDeviceInfo notify");
                    }
                    return;
                case 6:
                    if (MirrorControl.this.h != 0) {
                        MirrorControl mirrorControl2 = MirrorControl.this;
                        mirrorControl2.G = MirrorControl.isSupportPhoto(mirrorControl2.h);
                    } else {
                        MirrorControl.this.G = false;
                    }
                    synchronized (MirrorControl.this.o) {
                        MirrorControl.this.o.notify();
                        Log.i(MirrorControl.c, "mMirrorControlisSupportPhoto notify");
                    }
                    return;
                case 7:
                    Log.d(MirrorControl.c, "handleMessage MSG_START_SHOW");
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.startShow(MirrorControl.this.h);
                        return;
                    }
                    return;
                case 8:
                    Log.d(MirrorControl.c, "handleMessage MSG_SHOW_PHOTO");
                    String str = (String) message.obj;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.showPhoto(MirrorControl.this.h, str);
                        return;
                    }
                    return;
                case 9:
                    Log.d(MirrorControl.c, "handleMessage MSG_STOP_SHOW");
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.stopShow(MirrorControl.this.h);
                        return;
                    }
                    return;
                case 10:
                    d dVar = (d) message.obj;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.zoomPhoto(MirrorControl.this.h, dVar.a(), dVar.b(), dVar.c(), dVar.d(), dVar.e(), dVar.f(), dVar.g(), dVar.h());
                        return;
                    }
                    return;
                case 11:
                    b bVar = (b) message.obj;
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.rotatePhoto(MirrorControl.this.h, bVar.a(), bVar.b(), bVar.c());
                        return;
                    }
                    return;
                case 12:
                    int i4 = message.arg1;
                    if (message.arg2 == 1) {
                        z = true;
                    }
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.startSlideshow(MirrorControl.this.h, i4, z);
                        return;
                    }
                    return;
                case 13:
                    if (MirrorControl.this.h != 0) {
                        MirrorControl.stopSlideshow(MirrorControl.this.h);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static byte[] toUtf8String(String str) {
        byte[] bArr = new byte[str.length() + 1];
        for (int i = 0; i < str.length(); i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        bArr[str.length()] = 0;
        Log.i(c, "buf len:" + bArr.length);
        return bArr;
    }

    public void setMirrorControlNetAnomalyQuit(int i) {
        setMirrorNetAnomalyQuit(i);
    }

    public boolean startMirror() {
        getVideoEncodecInfoByCpu(y);
        Log.i(c, "start mirror");
        byte[] utf8String = toUtf8String(this.d.getIp());
        String str = c;
        Log.i(str, "set ip2:" + utf8String);
        setMirrotVideoBitrate(this.t);
        Message obtain = Message.obtain();
        obtain.obj = new WeakReference(this);
        this.E.sendMessage(obtain);
        Log.i(c, "start mirror end");
        return true;
    }

    public int initVideo(int i, int i2, int i3) {
        this.d.setMaxFps(i3);
        this.d.setVideoSize(new Size(i, i2));
        if (v) {
            this.t = 6000000;
        }
        if (!w) {
            this.t /= 2;
            int i4 = this.x;
            if (i4 == 3) {
                this.t = 3000000;
            } else if (i4 == 5) {
                this.t = 3000000;
            }
        }
        this.d.setVideoBitRate(this.t);
        Log.i(c, "initVideo start width:" + i + " height:" + i2 + " fps:" + i3 + " bitrate:" + this.t);
        return 0;
    }

    public int initVideo2(int i, int i2, int i3, int i4) {
        this.d.setMaxFps(i3);
        this.d.setVideoSize(new Size(i, i2));
        this.t = i4;
        if (v) {
            this.t = 6000000;
        }
        if (!w) {
            this.t /= 2;
            int i5 = this.x;
            if (i5 == 3) {
                this.t = 3000000;
            } else if (i5 == 5) {
                this.t = 3000000;
            }
        }
        this.d.setVideoBitRate(this.t);
        Log.i(c, "initVideo start width:" + i + " height:" + i2 + " fps:" + i3 + " bitrate:" + this.t);
        return 0;
    }

    public int initAudio(int i, int i2, int i3) {
        this.d.setChannel(i);
        this.d.setAudioBits(i2);
        this.d.setSampleRate(i3);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context) {
        if (context == null) {
            return false;
        }
        Configuration configuration = context.getResources().getConfiguration();
        boolean z2 = (this.B.updateFrom(configuration) & 1024) != 0;
        if (!z2) {
            return z2;
        }
        int i = configuration.screenLayout & 15;
        if (i == 2 && this.C != i) {
            this.C = i;
            Log.i(c, "mid current is small screen");
            return true;
        } else if (i != 3 || this.C == i) {
            return false;
        } else {
            this.C = i;
            Log.i(c, "mid current is large screen");
            return true;
        }
    }

    public void runVideoCheck() {
        new Thread(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.MirrorControl.1
            @Override // java.lang.Runnable
            public void run() {
                while (MirrorControl.this.g) {
                    if (MirrorControl.y != null ? MirrorControl.this.a(MirrorControl.y) : false) {
                        MirrorControl.this.restartVideo2(0);
                    }
                    try {
                        Thread.sleep(SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    } catch (Exception unused) {
                        Log.e(MirrorControl.c, "runVideoCheck Exception");
                    }
                }
            }
        }, "runVideoCheck").start();
    }

    public int startVideo() {
        Context context;
        if (this.i.get()) {
            return 0;
        }
        this.i.set(true);
        String str = c;
        Log.i(str, "startVideo width:" + this.d.getVideoSize().getWidth() + " height:" + this.d.getVideoSize().getHeight() + " fps" + this.d.getMaxFps());
        this.e.runCaptureVideo(this, this.d);
        if (this.i.get() && this.j.get()) {
            this.g = true;
        }
        if (this.g && (context = this.f) != null && CpuInfo.GetProductNameIsAssign(context, "cetus")) {
            runVideoCheck();
        }
        return 0;
    }

    public int stopVideo() {
        this.p.lock();
        Log.i(c, "stopVideo");
        this.k.set(true);
        if (this.k.get() && this.l.get()) {
            stopCapture();
            Log.i(c, "stopVideo  stopCapture end");
        }
        this.p.unlock();
        Log.i(c, "stopVideo end");
        return 0;
    }

    public int restartVideo2(int i) {
        if (this.k.get() || !this.i.get()) {
            return -1;
        }
        this.p.lock();
        CaptureService captureService = this.e;
        if (captureService != null) {
            captureService.restartCaptureVideo(this.d);
        }
        this.p.unlock();
        return 0;
    }

    public int startAudio() {
        Context context;
        if (this.j.get()) {
            return 0;
        }
        this.j.set(true);
        this.e.runCaptureAudio(this, this.d);
        if (this.i.get() && this.j.get()) {
            this.g = true;
        }
        if (this.g && (context = this.f) != null && CpuInfo.GetProductNameIsAssign(context, "cetus")) {
            runVideoCheck();
        }
        return 0;
    }

    public int stopAudio() {
        this.p.lock();
        Log.i(c, "stopAudio");
        this.l.set(true);
        if (this.k.get() && this.l.get()) {
            stopCapture();
            Log.i(c, "stopAudio  stopCapture end");
        }
        this.p.unlock();
        Log.i(c, "stopAudio end");
        return 0;
    }

    public int pauseVideo() {
        CaptureService captureService;
        Log.i(c, "pauseVideo");
        if (this.j.get() && (captureService = this.e) != null) {
            captureService.PauseCaptureVideo();
        }
        return 0;
    }

    public int resumeVideo() {
        Log.i(c, "resumeVideo");
        CaptureService captureService = this.e;
        if (captureService == null) {
            return 0;
        }
        captureService.ResumeCaptureVideo();
        return 0;
    }

    public int awakePauseVideo() {
        Log.i(c, "awakePauseVideo");
        CaptureService captureService = this.e;
        if (captureService == null) {
            return 0;
        }
        captureService.AwakeVideoPause();
        return 0;
    }

    public int pauseAudio(int i) {
        Log.i(c, "pauseAudio");
        CaptureService captureService = this.e;
        if (captureService == null) {
            return 0;
        }
        captureService.PauseCaptureAudio(i);
        return 0;
    }

    public int resumeAudio(int i) {
        Log.i(c, "resumeAudio");
        CaptureService captureService = this.e;
        if (captureService == null) {
            return 0;
        }
        captureService.ResumeCaptureAudio(i);
        return 0;
    }

    public int awakePauseAudio() {
        Log.i(c, "awakePauseAudio");
        CaptureService captureService = this.e;
        if (captureService == null) {
            return 0;
        }
        captureService.AwakeAudioPause();
        return 0;
    }

    public void stopCapture() {
        CaptureService captureService = this.e;
        if (captureService != null) {
            captureService.stopCapture();
        }
        synchronized (this.m) {
            this.m.notify();
            Log.i(c, "mMirrorControlStop notify");
            this.g = false;
        }
    }

    public static void SetNetAnomalyQuitFlag(int i) {
        z = i;
    }

    public static MirrorControl open(Options options, CaptureService captureService, RemoteDisplayAdmin.Listener listener, Context context, int i) throws IOException {
        if (i > 3000) {
            w = true;
        } else {
            w = false;
        }
        y = context;
        MirrorControl mirrorControl = new MirrorControl(options, captureService, listener, context);
        mirrorControl.setMirrorControlNetAnomalyQuit(z);
        mirrorControl.startMirror();
        return mirrorControl;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Log.d(c, "close mirror");
    }

    public void closeMirror() {
        Log.i(c, "close mirror");
        this.E.sendEmptyMessage(1);
    }

    public void pause(int i) {
        Log.i(c, "pause mirror");
        Message obtain = Message.obtain();
        obtain.what = 2;
        obtain.arg1 = i;
        obtain.arg2 = 0;
        this.E.sendMessage(obtain);
    }

    public void resume(int i) {
        Log.i(c, "resume mirror");
        Message obtain = Message.obtain();
        obtain.what = 3;
        obtain.arg1 = i;
        obtain.arg2 = 0;
        this.E.sendMessage(obtain);
    }

    /* loaded from: classes4.dex */
    private class c {
        private boolean b;
        private byte[] c;
        private long d;

        c(boolean z, byte[] bArr, long j) {
            this.b = z;
            this.c = bArr;
            this.d = j;
        }

        public boolean a() {
            return this.b;
        }

        public byte[] b() {
            return this.c;
        }

        public long c() {
            return this.d;
        }
    }

    public void WriteStream(boolean z2, byte[] bArr, long j) {
        Message obtain = Message.obtain();
        obtain.what = 4;
        obtain.obj = new c(z2, bArr, j);
        this.E.sendMessage(obtain);
    }

    public ControlMessage receiveControlMessage() throws IOException {
        return ControlMessage.createEmpty(0);
    }

    public static int startVideoCapture(Object obj) {
        Log.i(c, "start video capture");
        return ((MirrorControl) ((WeakReference) obj).get()).startVideo();
    }

    public static int stopVideoCapture(Object obj) {
        Log.i(c, "stop video capture");
        return ((MirrorControl) ((WeakReference) obj).get()).stopVideo();
    }

    public static int startAudioCapture(Object obj) {
        Log.i(c, "start audio capture");
        return ((MirrorControl) ((WeakReference) obj).get()).startAudio();
    }

    public static int stopAudioCapture(Object obj) {
        Log.i(c, "stop audio capture");
        return ((MirrorControl) ((WeakReference) obj).get()).stopAudio();
    }

    public static void onDisplayConnected(int i, int i2, int i3) {
        RemoteDisplayAdmin.Listener listener = A;
        if (listener != null) {
            listener.onDisplayConnected(null, i, i2, i3, 0);
        }
        Log.i(c, "onDisplayConnected");
    }

    public static void onDisplayDisconnected() {
        RemoteDisplayAdmin.Listener listener = A;
        if (listener != null) {
            listener.onDisplayDisconnected();
        }
        Log.i(c, "onDisplayDisconnected");
    }

    public static void onDisplayError(Object obj, int i, int i2) {
        String str = c;
        Log.i(str, "onDisplayError:" + i + " start");
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl != null) {
            mirrorControl.awakePauseAudio();
            mirrorControl.awakePauseVideo();
        }
        A.onDisplayError(i);
        String str2 = c;
        Log.i(str2, "onDisplayError:" + i + " end");
    }

    public static void onDisplayInfo(Object obj, int i, int i2) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (i == 100001) {
            v = i2 != 0;
        } else if (i == 100002) {
            mirrorControl.restartVideo(i2);
        } else if (i == 100003) {
            mirrorControl.u = i2;
        }
    }

    public static String getPrevPhoto(String str, boolean z2) {
        return A.getPrevPhoto(str, z2);
    }

    public static String getNextPhoto(String str, boolean z2) {
        return A.getNextPhoto(str, z2);
    }

    public static int pauseVideoCapture(Object obj) {
        Log.i(c, "pause Video capture");
        return ((MirrorControl) ((WeakReference) obj).get()).pauseVideo();
    }

    public static int resumeVideoCapture(Object obj) {
        Log.i(c, "resume Video capture");
        return ((MirrorControl) ((WeakReference) obj).get()).resumeVideo();
    }

    public static int pauseAudioCapture(Object obj, int i) {
        Log.i(c, "pause audio capture");
        return ((MirrorControl) ((WeakReference) obj).get()).pauseAudio(i);
    }

    public static int resumeAudioCapture(Object obj, int i) {
        Log.i(c, "resume audio capture");
        return ((MirrorControl) ((WeakReference) obj).get()).resumeAudio(i);
    }

    public int getDeviceInfo(int i) {
        Message obtain = Message.obtain();
        obtain.what = 5;
        obtain.arg1 = i;
        obtain.arg2 = 0;
        this.E.sendMessage(obtain);
        synchronized (this.n) {
            Log.i(c, "mMirrorControlDeviceInfo start!");
            try {
                this.n.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(c, "mMirrorControlDeviceInfo end!");
        }
        return this.F;
    }

    public boolean isSupportPhoto() {
        this.E.sendEmptyMessage(6);
        synchronized (this.o) {
            Log.i(c, "mMirrorControlisSupportPhoto start!");
            try {
                this.o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(c, "mMirrorControlisSupportPhoto end!");
        }
        return this.G;
    }

    public int startShow() {
        Log.i(c, "startShow");
        this.a.setImageMaxBitrate(getDeviceInfo(3));
        this.a.setImageMaxWidth(getDeviceInfo(4));
        this.a.setImageMaxHeight(getDeviceInfo(5));
        this.a.setImageBestWidth(getDeviceInfo(6));
        this.a.setImageBestHeight(getDeviceInfo(7));
        this.E.sendEmptyMessage(7);
        return 0;
    }

    public int showPhoto(String str) {
        Log.i(c, "showPhoto");
        if (str == null || str.isEmpty()) {
            return -1;
        }
        Message obtain = Message.obtain();
        obtain.what = 8;
        obtain.obj = str;
        this.E.sendMessage(obtain);
        return 0;
    }

    public int stopShow() {
        this.E.sendEmptyMessage(9);
        return 0;
    }

    /* loaded from: classes4.dex */
    private class d {
        private String b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private float i;

        d(String str, int i, int i2, int i3, int i4, int i5, int i6, float f) {
            this.b = str;
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
            this.g = i5;
            this.h = i6;
            this.i = f;
        }

        public String a() {
            return this.b;
        }

        public int b() {
            return this.c;
        }

        public int c() {
            return this.d;
        }

        public int d() {
            return this.e;
        }

        public int e() {
            return this.f;
        }

        public int f() {
            return this.g;
        }

        public int g() {
            return this.h;
        }

        public float h() {
            return this.i;
        }
    }

    public int zoomPhoto(String str, int i, int i2, int i3, int i4, int i5, int i6, float f) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        Message obtain = Message.obtain();
        obtain.what = 10;
        obtain.obj = new d(str, i, i2, i3, i4, i5, i6, f);
        this.E.sendMessage(obtain);
        return 0;
    }

    /* loaded from: classes4.dex */
    private class b {
        String a;
        boolean b;
        float c;

        b(String str, boolean z, float f) {
            this.a = str;
            this.b = z;
            this.c = f;
        }

        public String a() {
            return this.a;
        }

        public boolean b() {
            return this.b;
        }

        public float c() {
            return this.c;
        }
    }

    public int rotatePhoto(String str, boolean z2, float f) {
        String str2 = c;
        Log.i(str2, "rotatePhoto flag:" + z2 + " degree:" + f);
        if (str == null || str.isEmpty()) {
            return -1;
        }
        Message obtain = Message.obtain();
        obtain.what = 11;
        obtain.obj = new b(str, z2, f);
        this.E.sendMessage(obtain);
        return 0;
    }

    public int startSlideshow(int i, boolean z2) {
        Message obtain = Message.obtain();
        obtain.what = 12;
        obtain.arg1 = i;
        obtain.arg2 = z2 ? 1 : 0;
        this.E.sendMessage(obtain);
        return 0;
    }

    public int stopSlideshow() {
        this.E.sendEmptyMessage(13);
        return 0;
    }

    public static byte[] getPhotoData(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return null;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getData();
    }

    public static int getPhotoSize(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return -1;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getSize();
    }

    public static int getPhotoWidth(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return -1;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getWidth();
    }

    public static int getPhotoHeight(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return -1;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getHeight();
    }

    public static int getPhotoImageType(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return -1;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getImageType();
    }

    public static int getPhotoImageOrientation(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return -1;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.getOrientation();
    }

    public static boolean getPhotoCompressed(Object obj, String str) {
        MirrorControl mirrorControl = (MirrorControl) ((WeakReference) obj).get();
        if (mirrorControl == null) {
            return false;
        }
        if (!str.equals(mirrorControl.a.getPhotoUri())) {
            mirrorControl.b.getImageInfo(mirrorControl.f, str, mirrorControl.a);
        }
        return mirrorControl.a.isCompressed();
    }
}
