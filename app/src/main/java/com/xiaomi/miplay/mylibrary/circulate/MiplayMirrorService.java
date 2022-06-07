package com.xiaomi.miplay.mylibrary.circulate;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.external.MediaControlCallbackImpl;
import com.xiaomi.miplay.mylibrary.external.MediaControlImpl;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror;
import com.xiaomi.miplay.videoswitchmirror.IVideoSwitchMirrorCallback;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class MiplayMirrorService extends IIVideoSwitchMirror.Stub {
    public static int CIRCULATE_STATUS_CIRCULATE = 3;
    public static int CIRCULATE_STATUS_IDLE = -1;
    public static int CIRCULATE_STATUS_MIRROR = 1;
    public static int CIRCULATE_STATUS_RESUME_MIRRORING;
    private MiPlayAudioService a;
    private a b;
    private String l;
    private final Object d = new Object();
    private final Object e = new Object();
    private volatile boolean f = false;
    public int mCirculateStatus = CIRCULATE_STATUS_IDLE;
    private volatile boolean g = false;
    private volatile boolean h = false;
    private Map<String, String> j = new ConcurrentHashMap();
    private final Object k = new Object();
    private boolean m = true;
    private HandlerThread c = new HandlerThread(getClass().getName());
    public HashMap<String, MiPlayMirrorClientInfo> mMiPlayMirrorClientMap = new HashMap<>();
    private MediaControlCallbackImpl i = new MediaControlCallbackImpl();

    public int getCirculateStatus() {
        return this.mCirculateStatus;
    }

    public boolean isResumeMirrorings() {
        return this.g;
    }

    public void setResumeMirroring(boolean z) {
        this.g = z;
    }

    public boolean ismCirculateSwitch() {
        return this.m;
    }

    public boolean IsMirrorting() {
        return this.f;
    }

    public MiplayMirrorService(MiPlayAudioService miPlayAudioService) {
        Logger.i("MiplayMp_MirrorService", "MiplayMirrorService create !", new Object[0]);
        this.a = miPlayAudioService;
        this.c.start();
        this.b = new a(this.c.getLooper(), this, miPlayAudioService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a extends Handler {
        private final WeakReference<MiplayMirrorService> a;
        private final WeakReference<MiPlayAudioService> b;
        private String c = "";

        public a(Looper looper, MiplayMirrorService miplayMirrorService, MiPlayAudioService miPlayAudioService) {
            super(looper);
            this.a = new WeakReference<>(miplayMirrorService);
            this.b = new WeakReference<>(miPlayAudioService);
        }

        @Override // android.os.Handler
        @RequiresApi(api = 23)
        public void handleMessage(@NonNull Message message) {
            MiplayMirrorService miplayMirrorService = this.a.get();
            MiPlayAudioService miPlayAudioService = this.b.get();
            if (miplayMirrorService == null) {
                Logger.i("MiplayMp_MirrorService", "mirrorService is null", new Object[0]);
            } else if (miPlayAudioService == null) {
                Logger.i("MiplayMp_MirrorService", "audioService is null", new Object[0]);
            } else {
                Logger.i("MiplayMp_MirrorService", "handleMessage type:" + message.what, new Object[0]);
                switch (message.what) {
                    case 0:
                        MiPlayMirrorClientInfo miPlayMirrorClientInfo = (MiPlayMirrorClientInfo) message.obj;
                        this.c = miPlayMirrorClientInfo.a;
                        miplayMirrorService.a(miPlayMirrorClientInfo);
                        return;
                    case 1:
                        String str = (String) message.obj;
                        this.c = str;
                        miplayMirrorService.mMiPlayMirrorClientMap.remove(str);
                        return;
                    case 2:
                        Logger.i("MiplayMp_MirrorService", "msg_connectmirrorsuccess", new Object[0]);
                        miplayMirrorService.l = ((String) message.obj).toLowerCase();
                        Logger.d("MiplayMp_MirrorService", "deviceId:" + miplayMirrorService.l, new Object[0]);
                        miplayMirrorService.f = true;
                        MediaControlImpl.setDeviceId(miplayMirrorService.l);
                        miplayMirrorService.onCirculateModeChange(0);
                        miplayMirrorService.onConnectMirrorSuccess();
                        miplayMirrorService.mCirculateStatus = MiplayMirrorService.CIRCULATE_STATUS_MIRROR;
                        Logger.i("MiplayMp_MirrorService", "mCirculateStatus:" + miplayMirrorService.mCirculateStatus, new Object[0]);
                        return;
                    case 3:
                        Logger.i("MiplayMp_MirrorService", "msg_disconnectmirror", new Object[0]);
                        miplayMirrorService.f = false;
                        if (miplayMirrorService.getCirculateMode(miplayMirrorService.l) == 3) {
                            Logger.i("MiplayMp_MirrorService", "onCirculateModeChange no-report.", new Object[0]);
                            return;
                        }
                        miplayMirrorService.mCirculateStatus = MiplayMirrorService.CIRCULATE_STATUS_IDLE;
                        miplayMirrorService.onCirculateModeChange(-1);
                        return;
                    case 4:
                        Logger.i("MiplayMp_MirrorService", "msg_startdiscovery", new Object[0]);
                        miPlayAudioService.startDiscovery(((Integer) message.obj).intValue());
                        return;
                    case 5:
                    default:
                        Log.i("MiplayMp_MirrorService", "unknown msg!");
                        return;
                    case 6:
                        Logger.i("MiplayMp_MirrorService", "msg_userdisconnectmirror", new Object[0]);
                        miplayMirrorService.f = false;
                        miplayMirrorService.onUserStoped();
                        return;
                    case 7:
                        Logger.i("MiplayMp_MirrorService", "msg_resumemirrorsuccess", new Object[0]);
                        miplayMirrorService.mCirculateStatus = MiplayMirrorService.CIRCULATE_STATUS_MIRROR;
                        miplayMirrorService.onResumeMirrorSuccess();
                        return;
                    case 8:
                        Logger.i("MiplayMp_MirrorService", "msg_resumemirrorfail", new Object[0]);
                        miplayMirrorService.mCirculateStatus = MiplayMirrorService.CIRCULATE_STATUS_IDLE;
                        miplayMirrorService.onResumeMirrorFail();
                        return;
                }
            }
        }
    }

    public void a(MiPlayMirrorClientInfo miPlayMirrorClientInfo) {
        if (miPlayMirrorClientInfo.callback != null) {
            this.mMiPlayMirrorClientMap.put(miPlayMirrorClientInfo.a, miPlayMirrorClientInfo);
            Logger.i("MiplayMp_MirrorService", "ClientInfo put :  id " + miPlayMirrorClientInfo.a, new Object[0]);
            try {
                miPlayMirrorClientInfo.callback.onInitSuccess();
                Logger.i("MiplayMp_MirrorService", "miplay init end", new Object[0]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int init(String str, IVideoSwitchMirrorCallback iVideoSwitchMirrorCallback) throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "init", new Object[0]);
        this.b.obtainMessage(0, new MiPlayMirrorClientInfo(str, iVideoSwitchMirrorCallback)).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int unInit(String str) throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "unInit", new Object[0]);
        this.b.obtainMessage(1, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int connectMirrorSuccess(String str) throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "connectMirrorSuccess deviceId:" + str, new Object[0]);
        this.b.obtainMessage(2, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int disconnectMirror(String str) throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "disconnectMirror deviceId:" + str, new Object[0]);
        this.b.obtainMessage(3, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int resumeMirrorSuccess() throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "resumeMirrorSuccess:", new Object[0]);
        this.b.obtainMessage(7).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int resumeMirrorFail() throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "resumeMirrorFail:", new Object[0]);
        this.b.obtainMessage(8).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int startDiscovery(int i) throws RemoteException {
        this.b.obtainMessage(4, Integer.valueOf(i)).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    @RequiresApi(api = 23)
    public int getMirrorMode(String str) throws RemoteException {
        Logger.d("MiplayMp_MirrorService", "getMirrorMode:" + str, new Object[0]);
        Logger.d("MiplayMp_MirrorService", "mCirculateStatus:" + this.mCirculateStatus, new Object[0]);
        return this.mCirculateStatus;
    }

    @Override // com.xiaomi.miplay.videoswitchmirror.IIVideoSwitchMirror
    public int userDisconnectMirror(String str) throws RemoteException {
        Logger.i("MiplayMp_MirrorService", "userDisconnectMirror deviceId:" + str, new Object[0]);
        this.b.obtainMessage(6, str).sendToTarget();
        return 0;
    }

    @RequiresApi(api = 23)
    public void onResumeMirror() {
        synchronized (this.d) {
            Logger.i("MiplayMp_MirrorService", "resumeMirror.", new Object[0]);
            this.g = true;
            if (getCirculateMode(this.l) != 3) {
                Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
                return;
            }
            this.mCirculateStatus = CIRCULATE_STATUS_RESUME_MIRRORING;
            Logger.i("MiplayMp_MirrorService", "mCirculateStatus:" + this.mCirculateStatus, new Object[0]);
            try {
                for (Map.Entry<String, MiPlayMirrorClientInfo> entry : this.mMiPlayMirrorClientMap.entrySet()) {
                    entry.getValue().callback.resumeMirror();
                    Logger.i("MiplayMp_MirrorService", "send resumeMirror end.", new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = 23)
    public void cancelCirculate(int i) {
        synchronized (this.d) {
            Logger.i("MiplayMp_MirrorService", "cancelCirculate:, mCirculateStatus:" + this.mCirculateStatus + ",  mode" + i, new Object[0]);
            if (i == 0 && a()) {
                this.g = true;
                try {
                    for (Map.Entry<String, MiPlayMirrorClientInfo> entry : this.mMiPlayMirrorClientMap.entrySet()) {
                        entry.getValue().callback.resumeMirror();
                        Logger.i("MiplayMp_MirrorService", "send resumeMirror end.", new Object[0]);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (i == 1 && a()) {
                this.mCirculateStatus = CIRCULATE_STATUS_IDLE;
                Logger.i("MiplayMp_MirrorService", "mCirculateStatus:" + this.mCirculateStatus, new Object[0]);
                try {
                    for (Map.Entry<String, MiPlayMirrorClientInfo> entry2 : this.mMiPlayMirrorClientMap.entrySet()) {
                        entry2.getValue().callback.closeMirror();
                        Logger.i("MiplayMp_MirrorService", "send closeMirror end.", new Object[0]);
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = 23)
    public void onStoped(int i) {
        Logger.i("MiplayMp_MirrorService", "onStoped.", new Object[0]);
        Logger.i("MiplayMp_MirrorService", "state:" + i, new Object[0]);
        if (this.h) {
            Logger.i("MiplayMp_MirrorService", "UserDisconnectMirroring no-handle", new Object[0]);
            this.h = false;
            this.mCirculateStatus = CIRCULATE_STATUS_IDLE;
            Logger.i("MiplayMp_MirrorService", "mCirculateStatus:" + this.mCirculateStatus, new Object[0]);
            return;
        }
        if (i == 0) {
            try {
                for (Map.Entry<String, MiPlayMirrorClientInfo> entry : this.mMiPlayMirrorClientMap.entrySet()) {
                    entry.getValue().callback.closeMirror();
                    Logger.i("MiplayMp_MirrorService", "send closeMirror end.", new Object[0]);
                    this.mCirculateStatus = CIRCULATE_STATUS_IDLE;
                    this.f = false;
                    Logger.i("MiplayMp_MirrorService", "mCirculateStatus:" + this.mCirculateStatus, new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (i == 1) {
            if (this.mCirculateStatus == CIRCULATE_STATUS_CIRCULATE) {
                onResumeMirror();
            } else {
                Logger.i("MiplayMp_MirrorService", "already isMirrorting!", new Object[0]);
            }
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onStopped(i);
        }
    }

    @RequiresApi(api = 23)
    public void onUserStoped() {
        Logger.i("MiplayMp_MirrorService", "onUserStoped.", new Object[0]);
        if (this.a != null) {
            if (getCirculateMode(this.l) != 3) {
                Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            } else if (this.a.cmdSessionControlMap.get(this.l) == null) {
                Logger.i("MiplayMp_MirrorService", "CmdSessionControl getPosition on a null object", new Object[0]);
            } else {
                this.h = true;
                this.a.cmdSessionControlMap.get(this.l).closeDevice();
                MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
                if (mediaControlCallbackImpl == null) {
                    Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
                    return;
                }
                mediaControlCallbackImpl.onStopped(0);
                MediaControlImpl.setDeviceId("");
            }
        }
    }

    @RequiresApi(api = 23)
    public void onPlayed() {
        Logger.i("MiplayMp_MirrorService", "onPlayed.", new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onPlayed();
        }
    }

    @RequiresApi(api = 23)
    public void onPlayStateChange(int i) {
        if (i == 3) {
            onPaused();
        } else if (i == 2) {
            onResumed();
        }
    }

    @RequiresApi(api = 23)
    public void onPaused() {
        Logger.i("MiplayMp_MirrorService", "onPaused.", new Object[0]);
        int circulateMode = getCirculateMode(this.l);
        Logger.d("MiplayMp_MirrorService", "mirrorMode:" + circulateMode, new Object[0]);
        if (circulateMode != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onPaused();
        }
    }

    @RequiresApi(api = 23)
    public void onResumed() {
        Logger.i("MiplayMp_MirrorService", "onResumed.", new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onResumed();
        }
    }

    @RequiresApi(api = 23)
    public void positionChange(long j) {
        Logger.i("MiplayMp_MirrorService", "positionChange." + j, new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onPositionChanged(j);
        }
    }

    @RequiresApi(api = 23)
    public void onNotifyPropertiesInfo(byte[] bArr) {
        Logger.i("MiplayMp_MirrorService", "onNotifyPropertiesInfo.", new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
        } else if (this.i == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            String str = new String(bArr, StandardCharsets.UTF_8);
            Logger.d("MiplayMp_MirrorService", "proInfo:" + str, new Object[0]);
            this.i.onNotifyPropertiesInfo(str);
        }
    }

    @RequiresApi(api = 23)
    public void onSeekDoneNotify() {
        Logger.i("MiplayMp_MirrorService", "onSeekDoneNotify.", new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onSeekDoneNotify();
        }
    }

    @RequiresApi(api = 23)
    public void onCirculateFail(String str) {
        Logger.i("MiplayMp_MirrorService", "onCirculateFail." + str, new Object[0]);
        if (a()) {
            onResumeMirror();
        } else {
            Logger.i("MiplayMp_MirrorService", "already isMirrorting!", new Object[0]);
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onCirculateFail(str);
        }
    }

    @RequiresApi(api = 23)
    public void volumeChange(long j) {
        Logger.i("MiplayMp_MirrorService", "volumeChange." + j, new Object[0]);
        if (getCirculateMode(this.l) != 3) {
            Logger.i("MiplayMp_MirrorService", "current-non isvideocirculateting", new Object[0]);
            return;
        }
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else {
            mediaControlCallbackImpl.onVolumeChange(j);
        }
    }

    @RequiresApi(api = 23)
    public int getCirculateMode(String str) {
        Logger.d("MiplayMp_MirrorService", "getCirculateMode:" + this.mCirculateStatus, new Object[0]);
        return this.mCirculateStatus;
    }

    @RequiresApi(api = 23)
    public boolean getSupportMPVAbility(String str) {
        Logger.d("MiplayMp_MirrorService", "getSupportMPVAbility.:" + str, new Object[0]);
        MiPlayAudioService miPlayAudioService = this.a;
        if (miPlayAudioService == null) {
            Logger.i("MiplayMp_MirrorService", "mService is null.", new Object[0]);
            return false;
        } else if (miPlayAudioService.mDeviceManager == null) {
            Logger.i("MiplayMp_MirrorService", "mService mDeviceManager is null.", new Object[0]);
            return false;
        } else {
            for (MiDevice miDevice : this.a.mDeviceManager.getMiDeviceList()) {
                if (!TextUtils.isEmpty(str) && miDevice.getMac().equals(str.toLowerCase())) {
                    Logger.d("MiplayMp_MirrorService", "getSupportMPVAbility:" + miDevice.toString(), new Object[0]);
                    if (miDevice.isHaveSupportField() && !TextUtils.isEmpty(miDevice.getSupportMpAbility()) && miDevice.getSupportMpAbility().contains("video")) {
                        return true;
                    }
                    Logger.i("MiplayMp_MirrorService", "no-support mpv.", new Object[0]);
                    return false;
                }
            }
            return false;
        }
    }

    @RequiresApi(api = 23)
    public String getSourceName(String str) {
        Logger.d("MiplayMp_MirrorService", "getSourceName.:" + str, new Object[0]);
        MiPlayAudioService miPlayAudioService = this.a;
        if (miPlayAudioService == null) {
            Logger.i("MiplayMp_MirrorService", "mService is null.", new Object[0]);
            return "";
        } else if (miPlayAudioService.mDeviceManager == null) {
            Logger.i("MiplayMp_MirrorService", "mService mDeviceManager is null.", new Object[0]);
            return "";
        } else {
            for (MiDevice miDevice : this.a.mDeviceManager.getMiDeviceList()) {
                Logger.d("MiplayMp_MirrorService", miDevice.toString(), new Object[0]);
                if (!TextUtils.isEmpty(str) && miDevice.getMac().equals(str.toLowerCase())) {
                    return miDevice.getName();
                }
            }
            return "";
        }
    }

    public void onCirculateModeChange(int i) {
        Logger.i("MiplayMp_MirrorService", "onCirculateModeChange：" + i, new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else if (this.m) {
            mediaControlCallbackImpl.onCirculateModeChange(i);
        } else {
            mediaControlCallbackImpl.onCirculateModeChange(-1);
        }
    }

    public void onConnectMirrorSuccess() {
        Logger.i("MiplayMp_MirrorService", "onConnectMirrorSuccess.", new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else if (this.m) {
            mediaControlCallbackImpl.onConnectMirrorSuccess();
        }
    }

    public void onBuffering() {
        Logger.i("MiplayMp_MirrorService", "onBuffering.", new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else if (this.m) {
            mediaControlCallbackImpl.onBuffering();
        }
    }

    public void onResumeMirrorSuccess() {
        Logger.i("MiplayMp_MirrorService", "onResumeMirrorSuccess.", new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else if (this.m) {
            mediaControlCallbackImpl.onResumeMirrorSuccess();
        }
    }

    public void onResumeMirrorFail() {
        Logger.i("MiplayMp_MirrorService", "onResumeMirrorFail.", new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
        } else if (this.m) {
            mediaControlCallbackImpl.onResumeMirrorFail();
        }
    }

    public void onBeSeized() {
        Logger.i("MiplayMp_MirrorService", "onBeSeized.", new Object[0]);
        MediaControlCallbackImpl mediaControlCallbackImpl = this.i;
        if (mediaControlCallbackImpl == null) {
            Logger.i("MiplayMp_MirrorService", "mMediaControlCallback is null.", new Object[0]);
            return;
        }
        mediaControlCallbackImpl.onStopped(0);
        MediaControlImpl.setDeviceId("");
    }

    @RequiresApi(api = 23)
    private boolean a() {
        return this.mCirculateStatus == CIRCULATE_STATUS_CIRCULATE;
    }
}
