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
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.MiPlayClientAPI;
import com.xiaomi.miplay.mylibrary.MiPlayClientCallback;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.statistic.StatsUtils;
import com.xiaomi.miplay.videoclient.IMiPlayVideoCallback;
import com.xiaomi.miplay.videoclient.IMiPlayVideoClient;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class MiplayVideoService extends IMiPlayVideoClient.Stub implements MiPlayClientCallback {
    private static final String a = "MiplayVideoService";
    private MiPlayAudioService b;
    private a c;
    private MiPlayClientAPI e;
    private MiPlayClientCallback f;
    private final Object g = new Object();
    private HandlerThread d = new HandlerThread(getClass().getName());
    public HashMap<String, MiPlayVideoClientInfo> mMiPlayVideoClientMap = new HashMap<>();

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onUnBinderIdm() {
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onVerifySameAccountFail(String str) {
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onVerifySameAccountSuccess(String str) {
    }

    public MiplayVideoService(MiPlayAudioService miPlayAudioService, MiPlayClientAPI miPlayClientAPI, MiPlayClientCallback miPlayClientCallback) {
        Logger.i(a, "MiplayVideoService create !", new Object[0]);
        this.b = miPlayAudioService;
        this.e = miPlayClientAPI;
        this.f = miPlayClientCallback;
        this.d.start();
        this.c = new a(this.d.getLooper(), this, miPlayAudioService);
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onInitSuccess() {
        String str = a;
        Logger.i(str, "onInitSuccess size " + this.mMiPlayVideoClientMap.size(), new Object[0]);
        HashMap<String, MiPlayVideoClientInfo> hashMap = this.mMiPlayVideoClientMap;
        if (hashMap != null) {
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : hashMap.entrySet()) {
                    entry.getValue().callback.onInitSuccess();
                    Logger.i(a, "miplay init end", new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onInitError() {
        HashMap<String, MiPlayVideoClientInfo> hashMap = this.mMiPlayVideoClientMap;
        if (hashMap != null) {
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : hashMap.entrySet()) {
                    entry.getValue().callback.onInitError();
                    Logger.i(a, "miplay  onInitError", new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mMiPlayVideoClientMap.clear();
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onDeviceFound(MiDevice miDevice) {
        Logger.i(a, "onDeviceFound", new Object[0]);
        this.c.obtainMessage(16, miDevice).sendToTarget();
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onDeviceUpdate(MiDevice miDevice) {
        Logger.i(a, "onDeviceUpdate", new Object[0]);
        this.c.obtainMessage(17, miDevice).sendToTarget();
    }

    @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
    public void onDeviceLost(String str) {
        Logger.i(a, "onDeviceLost", new Object[0]);
    }

    /* loaded from: classes4.dex */
    private static class a extends Handler {
        private final WeakReference<MiplayVideoService> a;
        private final WeakReference<MiPlayAudioService> b;
        private String c = "";

        public a(Looper looper, MiplayVideoService miplayVideoService, MiPlayAudioService miPlayAudioService) {
            super(looper);
            this.a = new WeakReference<>(miplayVideoService);
            this.b = new WeakReference<>(miPlayAudioService);
        }

        @Override // android.os.Handler
        @RequiresApi(api = 23)
        public void handleMessage(@NonNull Message message) {
            MiplayVideoService miplayVideoService = this.a.get();
            MiPlayAudioService miPlayAudioService = this.b.get();
            if (miplayVideoService == null) {
                Logger.i(MiplayVideoService.a, "videoService is null", new Object[0]);
            } else if (miPlayAudioService == null) {
                Logger.i(MiplayVideoService.a, "audioService is null", new Object[0]);
            } else {
                String str = MiplayVideoService.a;
                Logger.i(str, "handleMessage type:" + message.what, new Object[0]);
                switch (message.what) {
                    case 0:
                        Object[] objArr = (Object[]) message.obj;
                        MiPlayVideoClientInfo miPlayVideoClientInfo = (MiPlayVideoClientInfo) objArr[0];
                        this.c = miPlayVideoClientInfo.a;
                        miplayVideoService.a(miPlayVideoClientInfo, (String) objArr[1]);
                        return;
                    case 1:
                        String str2 = (String) message.obj;
                        this.c = str2;
                        miplayVideoService.mMiPlayVideoClientMap.remove(str2);
                        if (miplayVideoService.mMiPlayVideoClientMap.isEmpty()) {
                            miplayVideoService.e.unInit(true);
                            return;
                        }
                        return;
                    case 2:
                        StatsUtils.getInstance().startTimer(0);
                        miPlayAudioService.startDiscovery(((Integer) message.obj).intValue());
                        return;
                    case 3:
                        return;
                    case 4:
                        String str3 = (String) message.obj;
                        String str4 = MiplayVideoService.a;
                        Logger.i(str4, "CmdSessionControl play id=" + str3, new Object[0]);
                        String macById = miPlayAudioService.getMacById(str3);
                        String str5 = MiplayVideoService.a;
                        Logger.i(str5, "CmdSessionControl play mac=" + macById, new Object[0]);
                        if (TextUtils.isEmpty(macById) || miPlayAudioService.cmdSessionControlMap.get(macById) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl play on a null object", new Object[0]);
                            return;
                        }
                        MetaData metaData = new MetaData();
                        metaData.setPlatform("local");
                        metaData.setURL("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4");
                        miPlayAudioService.cmdSessionControlMap.get(macById).openAVDevice(MetaInfoUtil.getInstance().metaInfoToJson(metaData));
                        return;
                    case 5:
                        String macById2 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById2) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl pause on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById2).pauseDevice();
                            return;
                        }
                    case 6:
                        String macById3 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById3) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl stop on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById3).closeDevice();
                            return;
                        }
                    case 7:
                        String macById4 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById4) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl resume on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById4).resumeDevice();
                            return;
                        }
                    case 8:
                        Object[] objArr2 = (Object[]) message.obj;
                        String macById5 = miPlayAudioService.getMacById((String) objArr2[0]);
                        long longValue = ((Long) objArr2[1]).longValue();
                        if (miPlayAudioService.cmdSessionControlMap.get(macById5) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl seek on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById5).seekMediaPlayer(longValue);
                            return;
                        }
                    case 9:
                        Object[] objArr3 = (Object[]) message.obj;
                        String macById6 = miPlayAudioService.getMacById((String) objArr3[0]);
                        int intValue = ((Integer) objArr3[1]).intValue();
                        if (miPlayAudioService.cmdSessionControlMap.get(macById6) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl setVolume on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById6).setVolume(intValue);
                            return;
                        }
                    case 10:
                        String macById7 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById7) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl getVolume on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById7).getVolume();
                            return;
                        }
                    case 11:
                        String macById8 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById8) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl getPosition on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById8).getPosition();
                            return;
                        }
                    case 12:
                        String macById9 = miPlayAudioService.getMacById((String) message.obj);
                        if (miPlayAudioService.cmdSessionControlMap.get(macById9) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl getPlayState on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(macById9).getState();
                            return;
                        }
                    case 13:
                        String str6 = (String) message.obj;
                        if (miPlayAudioService.cmdSessionControlMap.get(str6) == null) {
                            Logger.i(MiplayVideoService.a, "CmdSessionControl getMediaInfo on a null object", new Object[0]);
                            return;
                        } else {
                            miPlayAudioService.cmdSessionControlMap.get(str6).getMediaInfo();
                            return;
                        }
                    case 14:
                    case 15:
                    default:
                        Log.i(MiplayVideoService.a, "unknown msg!");
                        return;
                    case 16:
                        MiDevice miDevice = (MiDevice) message.obj;
                        String str7 = MiplayVideoService.a;
                        Logger.d(str7, "onDeviceFound: " + miDevice.toString(), new Object[0]);
                        miPlayAudioService.deviceFound(miDevice);
                        return;
                    case 17:
                        miPlayAudioService.deviceUpdateIDM((MiDevice) message.obj);
                        return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MiPlayVideoClientInfo miPlayVideoClientInfo, String str) {
        if (miPlayVideoClientInfo.callback != null) {
            this.mMiPlayVideoClientMap.put(miPlayVideoClientInfo.a, miPlayVideoClientInfo);
            String str2 = a;
            Logger.i(str2, "ClientInfo put :  id " + miPlayVideoClientInfo.a, new Object[0]);
        }
        if (!this.e.isInited() || miPlayVideoClientInfo.callback == null) {
            String str3 = a;
            Logger.i(str3, "ClientInfo init" + miPlayVideoClientInfo.a, new Object[0]);
            this.e.init(this.f, str);
            return;
        }
        Logger.i(a, "ClientInfo callback", new Object[0]);
        try {
            miPlayVideoClientInfo.callback.onInitSuccess();
            Logger.i(a, "miplay init end", new Object[0]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int init(String str, IMiPlayVideoCallback iMiPlayVideoCallback, String str2) throws RemoteException {
        Logger.i(a, "init", new Object[0]);
        this.c.obtainMessage(0, new Object[]{new MiPlayVideoClientInfo(str, iMiPlayVideoCallback), str2}).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int unInit(String str) throws RemoteException {
        Logger.i(a, "unInit", new Object[0]);
        this.c.obtainMessage(1, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int startDiscovery(int i) throws RemoteException {
        String str = a;
        Logger.i(str, "startDiscovery disctype" + i, new Object[0]);
        this.c.obtainMessage(2, Integer.valueOf(i)).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int stopDiscovery() throws RemoteException {
        Logger.i(a, "stopDiscovery", new Object[0]);
        this.c.obtainMessage(3).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int play(String str, int i) throws RemoteException {
        String str2 = a;
        Logger.i(str2, "play：" + i, new Object[0]);
        if (i == 0) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "controlcenter");
        } else if (i == 1) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "nearfield");
        } else if (i == 2) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "xiaoai_phone");
        } else if (i == 3) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "farfield");
        } else if (i == 4) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "lockscreen");
        } else if (i == 5) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "notification");
        } else if (i == 6) {
            StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "playpage");
        }
        this.c.obtainMessage(4, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int stop(String str) throws RemoteException {
        Logger.i(a, "stop", new Object[0]);
        this.c.obtainMessage(6, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int pause(String str) throws RemoteException {
        Logger.i(a, "pause", new Object[0]);
        this.c.obtainMessage(5, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int resume(String str) throws RemoteException {
        Logger.i(a, Commands.RESUME, new Object[0]);
        this.c.obtainMessage(7, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int seek(String str, long j) throws RemoteException {
        Logger.i(a, "seek", new Object[0]);
        this.c.obtainMessage(8, new Object[]{str, Long.valueOf(j)}).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int getPosition(String str) throws RemoteException {
        Logger.i(a, "getPosition", new Object[0]);
        this.c.obtainMessage(11, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int getPlayState(String str) throws RemoteException {
        Logger.i(a, "getPlayState", new Object[0]);
        this.c.obtainMessage(12, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int getMediaInfo(String str) throws RemoteException {
        Logger.i(a, "getMediaInfo", new Object[0]);
        this.c.obtainMessage(13, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int setVolume(String str, int i) throws RemoteException {
        String str2 = a;
        Logger.i(str2, "setVolume volume=" + i, new Object[0]);
        this.c.obtainMessage(9, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int getVolume(String str) throws RemoteException {
        Logger.i(a, "getVolume", new Object[0]);
        this.c.obtainMessage(10, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    @RequiresApi(api = 23)
    public boolean getCollectAudio() throws RemoteException {
        Logger.i(a, "getCollectAudio", new Object[0]);
        return this.b.queryActiveAudioSession();
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int next(String str) throws RemoteException {
        Logger.i(a, "next", new Object[0]);
        this.c.obtainMessage(14, str).sendToTarget();
        return 0;
    }

    @Override // com.xiaomi.miplay.videoclient.IMiPlayVideoClient
    public int prev(String str) throws RemoteException {
        Logger.i(a, Remote.Response.ControlAction.ACTION_PREV, new Object[0]);
        this.c.obtainMessage(15, str).sendToTarget();
        return 0;
    }

    public void playStateAck(String str, int i) {
        synchronized (this.g) {
            Logger.i(a, "playStateAck.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onPlayStateAck(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void positionAck(String str, long j) {
        Logger.i(a, "positionAck.", new Object[0]);
        try {
            for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                entry.getValue().callback.onPositionAck(str, j);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void positionChange(String str, long j) {
        Logger.i(a, "positionChange.", new Object[0]);
        try {
            for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                entry.getValue().callback.onPositionChange(str, j);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void playStateChange(String str, int i) {
        synchronized (this.g) {
            Logger.i(a, "playStateChange.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onPlayStateChange(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void mediaInfoAck(String str, MediaMetaData mediaMetaData) {
        synchronized (this.g) {
            Logger.i(a, "mediaInfoAck.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onMediaInfoAck(str, mediaMetaData);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void mediaInfoChange(String str, MediaMetaData mediaMetaData) {
        synchronized (this.g) {
            Logger.i(a, "mediaInfoChange.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onMediaInfoChange(str, mediaMetaData);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void volumeAck(String str, int i) {
        synchronized (this.g) {
            Logger.i(a, "volumeAck.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onVolumeAck(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void volumeChange(String str, int i) {
        synchronized (this.g) {
            Logger.i(a, "volumeChange.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onVolumeChange(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void bufferStateChange(String str, int i) {
        synchronized (this.g) {
            Logger.i(a, "bufferStateChange.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onBufferStateChange(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void beSeized(String str) {
        synchronized (this.g) {
            Logger.i(a, "beSeized.", new Object[0]);
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onBeSeized(str);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
