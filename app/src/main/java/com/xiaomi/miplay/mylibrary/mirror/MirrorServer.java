package com.xiaomi.miplay.mylibrary.mirror;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.google.android.exoplayer2.audio.AacUtil;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.text.SimpleDateFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public class MirrorServer {
    public static final int CONTROL_TYPE_MIRROR_CRUSHOUT_SCREEN = 1;
    public static final int CONTROL_TYPE_MIRROR_LIGHT_SCREEN = 2;
    public static final int CONTROL_TYPE_MIRROR_TO_PHOTO = 5;
    public static final int CONTROL_TYPE_PHOTO_CRUSHOUT_SCREEN = 3;
    public static final int CONTROL_TYPE_PHOTO_LIGHT_SCREEN = 4;
    public static final int CONTROL_TYPE_PHOTO_TO_MIRROR = 6;
    public static final int MIIROR_VIRTUAL_DISPLAY_FLAG_THE_THIRD_SCREEN_PROJECTION = Integer.MIN_VALUE;
    public static final int MIRROR_MEDIA_PROJECTIOIN = 2;
    public static final int MIRROR_VIRTUAL_DISPLAY = 1;
    public static MediaProjectionManager mMediaProjectionManager1;
    public static int mResultCode;
    public static Intent mResultData;
    private CaptureService r;
    private Service t;
    private RemoteDisplayAdmin.Listener u;
    private LinearLayout b = null;
    private WindowManager.LayoutParams c = null;
    private WindowManager d = null;
    private LayoutInflater e = null;
    private ImageButton f = null;
    private SimpleDateFormat g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private MediaProjection k = null;
    private VirtualDisplay l = null;
    private WindowManager m = null;
    private int n = 0;
    private int o = 0;
    private DisplayMetrics p = null;
    private int q = 0;
    MirrorServer a = this;
    private Options s = new Options();

    public int onTimelineChange() {
        return 0;
    }

    public MirrorServer(Service service, RemoteDisplayAdmin.Listener listener) {
        this.t = service;
        this.u = listener;
    }

    public boolean start(String str, int i, String str2, String str3) {
        return a(this.a, this.u, str, i, str2, str3);
    }

    public boolean start(String str, int i, String str2, String str3, Map<Integer, String> map) {
        return a(this.a, this.u, str, i, str2, str3, map);
    }

    public VirtualDisplay getVirtualDisplay() {
        return this.l;
    }

    public int getScreenDensity() {
        return this.q;
    }

    private void a() {
        mMediaProjectionManager1 = (MediaProjectionManager) this.t.getApplication().getSystemService("media_projection");
        this.m = (WindowManager) this.t.getApplication().getSystemService("window");
        this.n = this.m.getDefaultDisplay().getWidth();
        this.o = this.m.getDefaultDisplay().getHeight();
        this.p = new DisplayMetrics();
        Point point = new Point();
        this.m.getDefaultDisplay().getMetrics(this.p);
        this.q = this.p.densityDpi;
        Log.e("MiPlayQuickMirrorServer", "display:" + point + " windowWidth:" + this.n + " windowHeight:" + this.o);
        Log.i("MiPlayQuickMirrorServer", "prepared the virtual environment");
    }

    @TargetApi(21)
    public boolean startVirtual() {
        if (this.l != null) {
            return true;
        }
        a();
        if (this.k != null) {
            Log.i("MiPlayQuickMirrorServer", "want to display virtual");
            return b();
        }
        Log.i("MiPlayQuickMirrorServer", "start screen capture intent");
        Log.i("MiPlayQuickMirrorServer", "want to build mediaprojection and display virtual");
        if (setUpMediaProjection()) {
            return b();
        }
        this.u.onDisplayError(1);
        return false;
    }

    @TargetApi(21)
    public boolean setUpMediaProjection() {
        mResultData = ShotApplication.getInstance().getIntent();
        mResultCode = ShotApplication.getInstance().getResult();
        mMediaProjectionManager1 = ShotApplication.getInstance().getMediaProjectionManager();
        MediaProjectionManager mediaProjectionManager = mMediaProjectionManager1;
        if (mediaProjectionManager != null) {
            this.k = mediaProjectionManager.getMediaProjection(mResultCode, mResultData);
            if (this.k == null) {
                Log.e("MiPlayQuickMirrorServer", "mMediaProjection undefined");
                return false;
            }
            Log.i("MiPlayQuickMirrorServer", "mMediaProjection defined");
            return true;
        }
        Log.e("MiPlayQuickMirrorServer", "mMediaProjectionManager undefined");
        return false;
    }

    @TargetApi(21)
    private boolean b() {
        MediaProjection mediaProjection = this.k;
        if (mediaProjection != null) {
            this.l = mediaProjection.createVirtualDisplay("screen-mirror", this.n, this.o, this.q, -2147483645, null, null, null);
            if (this.l == null) {
                Log.e("MiPlayQuickMirrorServer", "virtual displayed error");
                this.u.onDisplayError(1);
                return false;
            }
            Log.i("MiPlayQuickMirrorServer", "virtual displayed");
            return true;
        }
        Log.i("MiPlayQuickMirrorServer", "virtual displayed mMediaProjection error ");
        return false;
    }

    @TargetApi(21)
    private boolean a(MirrorServer mirrorServer, RemoteDisplayAdmin.Listener listener, String str, int i, String str2, String str3) {
        if (this.r != null) {
            Log.d("MiPlayQuickMirrorServer", "capture start. stop");
            this.r.StopMirror();
            this.r = null;
        }
        this.r = new CaptureService(this.t.getApplicationContext());
        try {
            Log.e("MiPlayQuickMirrorServer", "start capture");
            if (i > 3000) {
                this.s.setVideoBitRate(7000000);
            } else {
                this.s.setVideoBitRate(4000000);
            }
            this.s.setIp(str);
            this.s.setPort(7236);
            this.s.setMultiPort(str2);
            this.s.setAudioName(str3);
            this.s.setAudioBitRate(AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND);
            this.r.setServer(mirrorServer);
            int i2 = Settings.System.getInt(this.t.getContentResolver(), "miplay_netanomaly_quit_mode", 0);
            if (i2 != 1) {
                i2 = 0;
            }
            Log.i("MiPlayQuickMirrorServer", "NetanomalyNoquit is " + i2);
            this.r.SetNetAnomalyQuitFlag(i2);
            return this.r.OpenMirror(this.s, listener, this.t.getApplicationContext(), i);
        } catch (Exception e) {
            Log.e("MiPlayQuickMirrorServer", e.toString());
            return false;
        }
    }

    @TargetApi(21)
    private boolean a(MirrorServer mirrorServer, RemoteDisplayAdmin.Listener listener, String str, int i, String str2, String str3, Map<Integer, String> map) {
        if (this.r != null) {
            Log.d("MiPlayQuickMirrorServer", "capture start. stop");
            this.r.StopMirror();
            this.r = null;
        }
        this.r = new CaptureService(this.t.getApplicationContext());
        try {
            Log.e("MiPlayQuickMirrorServer", "start capture");
            if (i > 3000) {
                this.s.setVideoBitRate(7000000);
            } else {
                this.s.setVideoBitRate(4000000);
            }
            this.s.setIp(str);
            this.s.setPort(7236);
            this.s.setMultiPort(str2);
            this.s.setAudioName(str3);
            this.s.setAudioBitRate(AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND);
            this.r.setServer(mirrorServer);
            int i2 = Settings.System.getInt(this.t.getContentResolver(), "miplay_netanomaly_quit_mode", 0);
            if (i2 != 1) {
                i2 = 0;
            }
            Log.i("MiPlayQuickMirrorServer", "NetanomalyNoquit is " + i2);
            this.r.SetNetAnomalyQuitFlag(i2);
            return this.r.OpenMirror(this.s, listener, this.t.getApplicationContext(), i, map);
        } catch (Exception e) {
            Log.e("MiPlayQuickMirrorServer", e.toString());
            return false;
        }
    }

    @TargetApi(21)
    public void tearDownMediaProjection() {
        MediaProjection mediaProjection = this.k;
        if (mediaProjection != null) {
            mediaProjection.stop();
            this.k = null;
        }
        Log.i("MiPlayQuickMirrorServer", "mMediaProjection undefined");
    }

    public void stopVirtual() {
        VirtualDisplay virtualDisplay = this.l;
        if (virtualDisplay != null) {
            virtualDisplay.release();
            this.l = null;
            Log.i("MiPlayQuickMirrorServer", "virtual display stopped");
        }
    }

    public boolean addMirror(String str) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.addMirror(str);
        }
        return false;
    }

    public boolean addMirror(String str, Map<Integer, String> map) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.addMirror(str, map);
        }
        return false;
    }

    public boolean deleteMirror(int i) {
        Logger.i("MiPlayQuickMirrorServer", "deleteMirror:" + i, new Object[0]);
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.deleteMirror(i);
        }
        return false;
    }

    public int getMirrorNums() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.getMirrorNums();
        }
        return -1;
    }

    public void stop() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            captureService.StopMirror();
            this.r = null;
        }
        Log.i("MiPlayQuickMirrorServer", "application destroy");
    }

    public void pause(int i) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            captureService.PauseMirror(i);
        }
        Log.i("MiPlayQuickMirrorServer", "pause");
    }

    public void resume(int i) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            captureService.ResumeMirror(i);
        }
        Log.i("MiPlayQuickMirrorServer", Commands.RESUME);
    }

    public boolean seek(long j) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.switchSource(j);
        }
        return false;
    }

    public int setAudioRecordParams(int[] iArr, int i, int i2, int i3) {
        this.s.setUidList(iArr);
        this.s.setSampleRate(i);
        this.s.setChannel(i2);
        this.s.setAudioBits(i3);
        if (iArr != null) {
            return 0;
        }
        Log.d("MiPlayQuickMirrorServer", "setAudioRecordParams uidList null object:");
        return -1;
    }

    public int getDeviceInfo(int i) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.getDeviceInfo(i);
        }
        Log.i("MiPlayQuickMirrorServer", "getDeviceInfo: capture is null");
        return -1;
    }

    public boolean isSupportPhoto() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.isSupportPhoto();
        }
        Log.i("MiPlayQuickMirrorServer", "isSupportPhoto: capture is null");
        return false;
    }

    public int startShow() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.startShow();
        }
        Log.i("MiPlayQuickMirrorServer", "startShow: capture is null");
        return -1;
    }

    public int showPhoto(String str) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.showPhoto(str);
        }
        Log.i("MiPlayQuickMirrorServer", "showPhoto: capture is null");
        return -1;
    }

    public int stopShow() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.stopShow();
        }
        return 0;
    }

    public int zoomPhoto(String str, int i, int i2, int i3, int i4, int i5, int i6, float f) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.zoomPhoto(str, i, i2, i3, i4, i5, i6, f);
        }
        Log.i("MiPlayQuickMirrorServer", "zoomPhoto: capture is null");
        return -1;
    }

    public int rotatePhoto(String str, boolean z, float f) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.rotatePhoto(str, z, f);
        }
        Log.i("MiPlayQuickMirrorServer", "rotatePhoto: capture is null");
        return -1;
    }

    public int startSlideshow(int i, boolean z) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.startSlideshow(i, z);
        }
        Log.i("MiPlayQuickMirrorServer", "startSlideshow: capture is null");
        return -1;
    }

    public int stopSlideshow() {
        CaptureService captureService = this.r;
        if (captureService != null) {
            return captureService.stopSlideshow();
        }
        Log.i("MiPlayQuickMirrorServer", "stopSlideshow: capture is null");
        return -1;
    }

    public void setVideoHandleMode(int i) {
        CaptureService captureService = this.r;
        if (captureService != null) {
            captureService.setVideoHandleMode(i);
        }
    }
}
