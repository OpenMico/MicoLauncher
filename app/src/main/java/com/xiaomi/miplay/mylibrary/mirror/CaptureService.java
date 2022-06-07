package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class CaptureService {
    public static final String QCOM_AUDIO_ENCODER_AAC = "OMX.qcom.audio.encoder.aac";
    private static MirrorControl c;
    private static MultiMirrorControl d;
    private static long f;
    private c a;
    private MirrorServer b;
    private ScreenEncoder e;
    private Context n;
    private int g = 0;
    private Context h = null;
    private final boolean i = false;
    private final AtomicBoolean j = new AtomicBoolean(false);
    private final AtomicBoolean k = new AtomicBoolean(false);
    private final Object l = new Object();
    private int[] m = new int[16];
    private final Object o = new Object();
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private long s = 0;
    private long t = 0;
    private volatile long u = -1;

    public CaptureService(Context context) {
        this.n = context;
        int[] iArr = this.m;
        iArr[0] = 96000;
        iArr[1] = 88200;
        iArr[2] = 64000;
        iArr[3] = 48000;
        iArr[4] = 44100;
        iArr[5] = 32000;
        iArr[6] = 24000;
        iArr[7] = 22050;
        iArr[8] = 16000;
        iArr[9] = 12000;
        iArr[10] = 11025;
        iArr[11] = 8000;
        iArr[12] = 7350;
    }

    public void stopCapture() {
        synchronized (this.o) {
            Log.e("MiPlayQuickCapture", "stopCapture start");
            if (this.a != null) {
                this.a.b();
                this.a.c();
                this.a = null;
            }
        }
        MirrorServer mirrorServer = this.b;
        if (mirrorServer != null) {
            mirrorServer.stopVirtual();
        }
        MirrorServer mirrorServer2 = this.b;
        if (mirrorServer2 != null) {
            mirrorServer2.tearDownMediaProjection();
        }
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder != null) {
            screenEncoder.stop();
        }
        Log.e("MiPlayQuickCapture", "stopCapture stop");
    }

    public void runMultiCaptureAudio(final MultiMirrorControl multiMirrorControl, final Options options) {
        Log.d("MiPlayQuickCapture", "start port capture");
        new Thread(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.CaptureService.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    synchronized (CaptureService.this.o) {
                        Log.d("MiPlayQuickCapture", "run port capture");
                        AudioEncodeConfig audioEncodeConfig = new AudioEncodeConfig(CaptureService.this.n, CaptureService.QCOM_AUDIO_ENCODER_AAC, MimeTypes.AUDIO_AAC, options.getUidList(), options.getAudioBitRate(), options.getSampleRate(), options.getChannel(), 2);
                        CaptureService.this.a = new c(audioEncodeConfig);
                        CaptureService.this.a(CaptureService.this.a, null, multiMirrorControl);
                        if (multiMirrorControl != null) {
                            multiMirrorControl.onMultiDisplayInfo(MultiMirrorControl.MULTI_DISPLAY_INFO_AUDIOCAPTURE_INITDONE, -1, -1);
                        }
                        Log.d("MiPlayQuickCapture", "run port capture end");
                    }
                } catch (Exception e) {
                    Log.e("MiPlayQuickCapture", "runCaptureAudio Exception:" + e);
                    e.printStackTrace();
                }
            }
        }, "runCaptureAudio").start();
    }

    public void runCaptureAudio(final MirrorControl mirrorControl, final Options options) {
        new Thread(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.CaptureService.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AudioEncodeConfig audioEncodeConfig = new AudioEncodeConfig(CaptureService.this.n, CaptureService.QCOM_AUDIO_ENCODER_AAC, MimeTypes.AUDIO_AAC, options.getUidList(), options.getAudioBitRate(), options.getSampleRate(), options.getChannel(), 2);
                    CaptureService.this.a = new c(audioEncodeConfig);
                    CaptureService.this.a(CaptureService.this.a, mirrorControl, null);
                } catch (Exception unused) {
                    Log.e("MiPlayQuickCapture", "runCaptureAudio Exception");
                }
            }
        }, "runCaptureAudio").start();
    }

    public void runCaptureVideo(final MirrorControl mirrorControl, final Options options) {
        new Thread(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.mirror.CaptureService.3
            @Override // java.lang.Runnable
            public void run() {
                Log.e("MiPlayQuickCapture", "runCapture:" + Thread.currentThread().getName() + " ID:" + Thread.currentThread().getId() + " RR:" + Process.myTid());
                try {
                    Device device = new Device(options);
                    CaptureService.this.e = new ScreenEncoder(options.getSendFrameMeta(), options.getVideoBitRate(), options.getMaxFps(), CaptureService.this.h);
                    try {
                        CaptureService.this.e.streamScreen(device, mirrorControl, CaptureService.this.b);
                    } catch (IOException unused) {
                        Ln.d("Screen streaming stopped");
                    }
                } catch (Exception unused2) {
                    Log.e("MiPlayQuickCapture", "runCaptureVideo Exception");
                }
            }
        }, "runCaptureVideo").start();
    }

    public boolean PauseCaptureVideo() {
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder == null) {
            return true;
        }
        screenEncoder.pause();
        return true;
    }

    public boolean ResumeCaptureVideo() {
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder == null) {
            return true;
        }
        screenEncoder.resume();
        return true;
    }

    public boolean AwakeVideoPause() {
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder == null) {
            return true;
        }
        screenEncoder.awake_pause();
        return true;
    }

    public boolean PauseCaptureAudio(int i) {
        c cVar = this.a;
        if (cVar != null) {
            cVar.a(i);
        }
        this.k.set(true);
        return true;
    }

    public boolean ResumeCaptureAudio(int i) {
        if (this.k.get()) {
            synchronized (this.l) {
                Log.i("MiPlayQuickCapture", "ResumeCaptureAudio!");
                this.l.notify();
                this.k.set(false);
            }
        }
        c cVar = this.a;
        if (cVar == null) {
            return true;
        }
        cVar.b(i);
        return true;
    }

    public boolean AwakeAudioPause() {
        c cVar = this.a;
        if (cVar == null) {
            return true;
        }
        cVar.e();
        return true;
    }

    public void SetNetAnomalyQuitFlag(int i) {
        this.g = i;
    }

    public boolean OpenMirror(Options options, RemoteDisplayAdmin.Listener listener, Context context, int i) throws IOException {
        try {
            if (d != null) {
                d.closeMirror();
            }
            this.h = context;
            MirrorControl.SetNetAnomalyQuitFlag(this.g);
            d = MultiMirrorControl.open(options, this, listener, context);
            return d != null;
        } catch (Exception e) {
            Log.d("Mirror", e.toString());
            return false;
        }
    }

    public boolean OpenMirror(Options options, RemoteDisplayAdmin.Listener listener, Context context, int i, Map<Integer, String> map) throws IOException {
        try {
            if (d != null) {
                d.closeMirror();
            }
            this.h = context;
            MirrorControl.SetNetAnomalyQuitFlag(this.g);
            d = MultiMirrorControl.open(options, this, listener, context, map);
            return d != null;
        } catch (Exception e) {
            Log.d("Mirror", e.toString());
            return false;
        }
    }

    public boolean addMirror(String str) {
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl != null) {
            return multiMirrorControl.addMirror(str);
        }
        return false;
    }

    public boolean addMirror(String str, Map<Integer, String> map) {
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl != null) {
            return multiMirrorControl.addMirror(str, map);
        }
        return false;
    }

    public boolean deleteMirror(int i) {
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl != null) {
            return multiMirrorControl.deleteMirror(i);
        }
        return false;
    }

    public int getMirrorNums() {
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl != null) {
            return multiMirrorControl.getMirrorNums();
        }
        return -1;
    }

    public boolean StopMirror() {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            mirrorControl.closeMirror();
            c = null;
        }
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl == null) {
            return true;
        }
        multiMirrorControl.closeMirror();
        d = null;
        return true;
    }

    public boolean PauseMirror(int i) {
        PauseCaptureAudio(i);
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            mirrorControl.pause(i);
        }
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl == null) {
            return true;
        }
        multiMirrorControl.pause(i);
        return true;
    }

    public boolean ResumeMirror(int i) {
        ResumeCaptureAudio(i);
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            mirrorControl.resume(i);
        }
        MultiMirrorControl multiMirrorControl = d;
        if (multiMirrorControl == null) {
            return true;
        }
        multiMirrorControl.resume(i);
        return true;
    }

    public boolean switchSource(long j) {
        c cVar = this.a;
        if (cVar == null) {
            return false;
        }
        cVar.a(j);
        return false;
    }

    public boolean restartCaptureVideo(Options options) {
        if (c == null) {
            return false;
        }
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder != null) {
            screenEncoder.stop();
            this.e = null;
        }
        runCaptureVideo(c, options);
        return true;
    }

    public void a(c cVar, MirrorControl mirrorControl, MultiMirrorControl multiMirrorControl) throws IOException {
        if (cVar != null) {
            cVar.a(mirrorControl);
            cVar.a(multiMirrorControl);
            cVar.a();
        }
    }

    public void setServer(MirrorServer mirrorServer) {
        this.b = mirrorServer;
    }

    public int getDeviceInfo(int i) {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.getDeviceInfo(i);
        }
        Log.i("MiPlayQuickCapture", "getDeviceInfo: mirror is null");
        return -1;
    }

    public boolean isSupportPhoto() {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.isSupportPhoto();
        }
        Log.i("MiPlayQuickCapture", "isSupportPhoto: mirror is null");
        return false;
    }

    public int startShow() {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.startShow();
        }
        Log.i("MiPlayQuickCapture", "startShow: mirror is null");
        return -1;
    }

    public int showPhoto(String str) {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.showPhoto(str);
        }
        Log.i("MiPlayQuickCapture", "showPhoto: mirror is null");
        return -1;
    }

    public int stopShow() {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.stopShow();
        }
        return 0;
    }

    public int zoomPhoto(String str, int i, int i2, int i3, int i4, int i5, int i6, float f2) {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.zoomPhoto(str, i, i2, i3, i4, i5, i6, f2);
        }
        Log.i("MiPlayQuickCapture", "zoomPhoto: mirror is null");
        return -1;
    }

    public int rotatePhoto(String str, boolean z, float f2) {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.rotatePhoto(str, z, f2);
        }
        Log.i("MiPlayQuickCapture", "rotatePhoto: mirror is null");
        return -1;
    }

    public int startSlideshow(int i, boolean z) {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.startSlideshow(i, z);
        }
        Log.i("MiPlayQuickCapture", "startSlideshow: mirror is null");
        return -1;
    }

    public int stopSlideshow() {
        MirrorControl mirrorControl = c;
        if (mirrorControl != null) {
            return mirrorControl.stopSlideshow();
        }
        Log.i("MiPlayQuickCapture", "stopSlideshow: mirror is null");
        return -1;
    }

    public void setVideoHandleMode(int i) {
        ScreenEncoder screenEncoder = this.e;
        if (screenEncoder != null) {
            screenEncoder.setVideoHandleMode(i);
        }
    }
}
