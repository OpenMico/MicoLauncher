package com.xiaomi.miplay.mylibrary.tv;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mi.milink.mediacore.MediaCoreConfig;
import com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient;
import com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback;
import com.xiaomi.miplay.audioclient.tv.TVMediaMetaData;
import com.xiaomi.miplay.mylibrary.DeviceManager;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.miplay.mylibrary.ThreadPoolManager;
import com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback;
import com.xiaomi.miplay.mylibrary.mirror.CmdSessionControl;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MiplayAudioTVService extends Service {
    public static final String ACTION_MIPLAY_TV_CLIENT = "com.xiaomi.miplay.action.TV_MIPLAY_AUDIO_CLIENT_SERVICE";
    private static final String a = "MiplayAudioTVService";
    private MiDevice b;
    private volatile boolean l;
    private a n;
    private BroadcastReceiver o;
    private final Object d = new Object();
    private final Object e = new Object();
    private final Object f = new Object();
    private final Object g = new Object();
    private String i = "";
    private String j = "";
    private Map<String, CmdSessionControl> k = new ConcurrentHashMap();
    private volatile boolean m = false;
    private volatile int p = 0;
    private int[] q = {1, 3, 5, 8, 13, 21, 34, 55};
    private Handler r = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            String str;
            String str2;
            Object[] objArr;
            Logger.i(MiplayAudioTVService.a, "handleMessage type:" + message.what, new Object[0]);
            switch (message.what) {
                case 100:
                    b bVar = (b) message.obj;
                    MiplayAudioTVService.this.i = bVar.a;
                    if (!MiplayAudioTVService.this.h.containsKey(bVar)) {
                        MiplayAudioTVService miplayAudioTVService = MiplayAudioTVService.this;
                        miplayAudioTVService.n = new a();
                        if (MiplayAudioTVService.this.h != null) {
                            try {
                                for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                    if (((b) entry.getValue()).b != null) {
                                        ((b) entry.getValue()).b.asBinder().linkToDeath(MiplayAudioTVService.this.n, 0);
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Logger.i(MiplayAudioTVService.a, "ClientInfo type :  id start：" + bVar.a, new Object[0]);
                    if (bVar.b != null) {
                        MiplayAudioTVService.this.h.put(bVar.a, bVar);
                        Logger.i(MiplayAudioTVService.a, "ClientInfo put :  id " + bVar.a, new Object[0]);
                    }
                    Logger.i(MiplayAudioTVService.a, "mMiPlayClientTVMap:" + MiplayAudioTVService.this.h.size(), new Object[0]);
                    Logger.i(MiplayAudioTVService.a, "cmdSession:" + MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()), new Object[0]);
                    if (!MiplayAudioTVService.this.isInited() || bVar.b == null || MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "ClientInfo init" + bVar.a, new Object[0]);
                        MiplayAudioTVService.this.b();
                        return;
                    }
                    Logger.i(MiplayAudioTVService.a, "ClientInfo callback", new Object[0]);
                    try {
                        bVar.b.onInitSuccess();
                        bVar.b.onCmdSessionSuccess();
                        Logger.i(MiplayAudioTVService.a, "miplay init end", new Object[0]);
                        return;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 101:
                    MiplayAudioTVService.this.i = (String) message.obj;
                    Logger.i(MiplayAudioTVService.a, "mUnitID:" + MiplayAudioTVService.this.i, new Object[0]);
                    MiplayAudioTVService.this.h.remove(MiplayAudioTVService.this.i);
                    if (MiplayAudioTVService.this.h.isEmpty()) {
                        MiplayAudioTVService.this.f();
                        return;
                    }
                    return;
                case 102:
                default:
                    return;
                case 103:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).closeDevice();
                        return;
                    }
                case 104:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).pauseDevice();
                        return;
                    }
                case 105:
                    Logger.i(MiplayAudioTVService.a, "rev resume cmd", new Object[0]);
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).resumeDevice();
                        return;
                    }
                case 106:
                    long longValue = ((Long) message.obj).longValue();
                    Logger.i(MiplayAudioTVService.a, "rev seek cmd", new Object[0]);
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).seekMediaPlayer(longValue);
                        return;
                    }
                case 107:
                    Logger.i(MiplayAudioTVService.a, "rev getposition cmd", new Object[0]);
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).getPosition();
                        return;
                    }
                case 108:
                    Logger.i(MiplayAudioTVService.a, "rev playstate cmd", new Object[0]);
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).getState();
                        return;
                    }
                case 109:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).getMediaInfo();
                        return;
                    }
                case 110:
                    Logger.i(MiplayAudioTVService.a, "rev seek cmd", new Object[0]);
                    int intValue = ((Integer) message.obj).intValue();
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).setVolume(intValue);
                        return;
                    }
                case 111:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).getVolume();
                        return;
                    }
                case 112:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).playNext();
                        return;
                    }
                case 113:
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).playPrev();
                        return;
                    }
                case 114:
                    Logger.i(MiplayAudioTVService.a, "rev msg_cmd_sessionerror cmd", new Object[0]);
                    MiplayAudioTVService.this.l = false;
                    MiplayAudioTVService.this.m = false;
                    String str3 = (String) message.obj;
                    MiplayAudioTVService.this.g();
                    return;
                case 115:
                    Logger.i(MiplayAudioTVService.a, "rev msg_cmd_sessionsuccess cmd", new Object[0]);
                    MiplayAudioTVService.this.l = true;
                    MiplayAudioTVService.this.m = false;
                    MiplayAudioTVService.this.p = 0;
                    MiplayAudioTVService.this.a((String) message.obj);
                    return;
                case 116:
                    synchronized (MiplayAudioTVService.this.e) {
                        Logger.i(MiplayAudioTVService.a, "rev msg_cmd_devicesinfo cmd", new Object[0]);
                        byte[] bArr = (byte[]) message.obj;
                        if (bArr != null) {
                            MiplayAudioTVService.this.c.updateSourceName(bArr);
                            try {
                                for (Map.Entry entry2 : MiplayAudioTVService.this.h.entrySet()) {
                                    ((b) entry2.getValue()).b.onSourceNameChange(MiplayAudioTVService.this.c.getSourceName());
                                }
                            } catch (RemoteException e3) {
                                e3.printStackTrace();
                            }
                            return;
                        }
                        return;
                    }
                case 117:
                    synchronized (MiplayAudioTVService.this.d) {
                        Logger.i(MiplayAudioTVService.a, "rev msg_cmd_ondurationupdated cmd", new Object[0]);
                        MiplayAudioTVService.this.s = false;
                        long longValue2 = ((Long) message.obj).longValue();
                        try {
                            for (Map.Entry entry3 : MiplayAudioTVService.this.h.entrySet()) {
                                Logger.i(MiplayAudioTVService.a, "id:" + ((b) entry3.getValue()).a, new Object[0]);
                                MiplayAudioTVService.this.j = ((b) entry3.getValue()).a;
                                ((b) entry3.getValue()).b.onDurationUpdated(longValue2);
                            }
                            Logger.i(MiplayAudioTVService.a, "finally start:", new Object[0]);
                            Logger.i(MiplayAudioTVService.a, "isDeadObjectException:" + MiplayAudioTVService.this.s, new Object[0]);
                            if (MiplayAudioTVService.this.s) {
                                MiplayAudioTVService.this.h.remove(MiplayAudioTVService.this.j);
                                if (MiplayAudioTVService.this.h.isEmpty()) {
                                    MiplayAudioTVService.this.f();
                                }
                                for (Map.Entry entry4 : MiplayAudioTVService.this.h.entrySet()) {
                                    Logger.i(MiplayAudioTVService.a, "id:" + ((b) entry4.getValue()).a, new Object[0]);
                                    try {
                                        ((b) entry4.getValue()).b.onDurationUpdated(longValue2);
                                    } catch (RemoteException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                MiplayAudioTVService.this.s = false;
                            }
                            str = MiplayAudioTVService.a;
                            str2 = "finally end:";
                            objArr = new Object[0];
                        } catch (RemoteException e5) {
                            Logger.i(MiplayAudioTVService.a, "ondurationupdated DeadObjectException", new Object[0]);
                            MiplayAudioTVService.this.s = true;
                            e5.printStackTrace();
                            Logger.i(MiplayAudioTVService.a, "finally start:", new Object[0]);
                            Logger.i(MiplayAudioTVService.a, "isDeadObjectException:" + MiplayAudioTVService.this.s, new Object[0]);
                            if (MiplayAudioTVService.this.s) {
                                MiplayAudioTVService.this.h.remove(MiplayAudioTVService.this.j);
                                if (MiplayAudioTVService.this.h.isEmpty()) {
                                    MiplayAudioTVService.this.f();
                                }
                                for (Map.Entry entry5 : MiplayAudioTVService.this.h.entrySet()) {
                                    Logger.i(MiplayAudioTVService.a, "id:" + ((b) entry5.getValue()).a, new Object[0]);
                                    try {
                                        ((b) entry5.getValue()).b.onDurationUpdated(longValue2);
                                    } catch (RemoteException e6) {
                                        e6.printStackTrace();
                                    }
                                }
                                MiplayAudioTVService.this.s = false;
                            }
                            str = MiplayAudioTVService.a;
                            str2 = "finally end:";
                            objArr = new Object[0];
                        }
                        Logger.i(str, str2, objArr);
                    }
                    return;
                case 118:
                    synchronized (MiplayAudioTVService.this.e) {
                        Logger.i(MiplayAudioTVService.a, "rev msg_cmd_deviceinfochanged cmd", new Object[0]);
                        byte[] bArr2 = (byte[]) message.obj;
                        if (bArr2 != null) {
                            MiplayAudioTVService.this.c.updateSourceName(bArr2);
                            try {
                                for (Map.Entry entry6 : MiplayAudioTVService.this.h.entrySet()) {
                                    ((b) entry6.getValue()).b.onSourceNameChange(MiplayAudioTVService.this.c.getSourceName());
                                }
                            } catch (RemoteException e7) {
                                e7.printStackTrace();
                            }
                            return;
                        }
                        return;
                    }
                case 119:
                    Logger.i(MiplayAudioTVService.a, "rev msg_musicrelay cmd", new Object[0]);
                    Object[] objArr2 = (Object[]) message.obj;
                    String str4 = (String) objArr2[0];
                    int intValue2 = ((Integer) objArr2[1]).intValue();
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    }
                    String str5 = "remote=" + str4 + "&needPlayed=" + intValue2;
                    Logger.d(MiplayAudioTVService.a, "addr:" + str5, new Object[0]);
                    ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).openDevice(str5, 8899);
                    return;
                case 120:
                    Logger.i(MiplayAudioTVService.a, "rev msg_setlocaldeviceinfo cmd", new Object[0]);
                    String str6 = (String) message.obj;
                    if (TextUtils.isEmpty(str6)) {
                        Logger.d(MiplayAudioTVService.a, "setLocalDeviceInfo parameter jInfo is null", new Object[0]);
                        return;
                    } else if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).setLocalDeviceInfo(str6.getBytes(StandardCharsets.UTF_8));
                        return;
                    }
                case 121:
                    Logger.i(MiplayAudioTVService.a, "rev msg_setlocaldeviceinfo cmd", new Object[0]);
                    final TVMediaMetaData tVMediaMetaData = (TVMediaMetaData) message.obj;
                    ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                                Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                            } else {
                                ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).setMediaInfo(MetaInfoTVManager.getInstance().mediaTVMetaDataToJson(tVMediaMetaData));
                            }
                        }
                    });
                    return;
                case 122:
                    Logger.i(MiplayAudioTVService.a, "rev msg_setlocalmediastate cmd", new Object[0]);
                    int intValue3 = ((Integer) message.obj).intValue();
                    if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) == null) {
                        Logger.i(MiplayAudioTVService.a, "CmdSessionControl  on a null object", new Object[0]);
                        return;
                    } else {
                        ((CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac())).setMediaState(intValue3);
                        return;
                    }
            }
        }
    };
    private boolean s = false;
    private IMiPlayTVClient t = new IMiPlayTVClient.Stub() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.2
        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int audioFcControl() throws RemoteException {
            return -1;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setMiplayAudioFcControl(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setMiplayVolumeControl(int i) throws RemoteException {
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int volumeControl() throws RemoteException {
            return -1;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int init(String str, IMiPlayTVClientCallback iMiPlayTVClientCallback) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(100, new b(str, iMiPlayTVClientCallback)).sendToTarget();
            Logger.i(MiplayAudioTVService.a, "miplay init start", new Object[0]);
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int unInit(String str) throws RemoteException {
            Log.i(MiplayAudioTVService.a, "unInit !");
            MiplayAudioTVService.this.r.obtainMessage(101, str).sendToTarget();
            Log.i(MiplayAudioTVService.a, "unInit end");
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int stop() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(103).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onPause() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(104).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onResume() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(105).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onSeek(long j) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(106, Long.valueOf(j)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getPosition() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(107).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getPlayState() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(108).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getMediaInfo() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(109).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setVolume(int i) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(110, Integer.valueOf(i)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int getVolume() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(111).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onNext() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(112).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int onPrev() throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(113).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public String getSourceName() throws RemoteException {
            return MiplayAudioTVService.this.c.getSourceName();
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int musicRelay(String str, int i) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(119, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalDeviceInfo(String str) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(120, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalMediaInfo(TVMediaMetaData tVMediaMetaData) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(121, tVMediaMetaData).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClient
        public int setLocalMediaState(int i) throws RemoteException {
            MiplayAudioTVService.this.r.obtainMessage(122, Integer.valueOf(i)).sendToTarget();
            return 0;
        }
    };
    private HashMap<String, b> h = new HashMap<>();
    private DeviceManager c = new DeviceManager();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class b {
        String a;
        IMiPlayTVClientCallback b;

        public b(String str, IMiPlayTVClientCallback iMiPlayTVClientCallback) {
            this.a = str;
            this.b = iMiPlayTVClientCallback;
        }
    }

    public MiplayAudioTVService() {
        Logger.i(a, "MiplayAudioTVService structure", new Object[0]);
        c();
    }

    /* loaded from: classes4.dex */
    private class a implements IBinder.DeathRecipient {
        private a() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e(MiplayAudioTVService.a, "binderDied before");
            String nameForUid = MiplayAudioTVService.this.getPackageManager().getNameForUid(Binder.getCallingUid());
            String str = MiplayAudioTVService.a;
            Logger.i(str, "binderDied clientPackageName:" + nameForUid, new Object[0]);
            String str2 = MiplayAudioTVService.a;
            Logger.i(str2, "binderDied mMiPlayClientTVMap:" + MiplayAudioTVService.this.h.size(), new Object[0]);
            Iterator it = MiplayAudioTVService.this.h.entrySet().iterator();
            while (it.hasNext()) {
                String str3 = MiplayAudioTVService.a;
                Logger.i(str3, "binderDied mMiPlayClientTVMap:" + ((b) ((Map.Entry) it.next()).getValue()).a, new Object[0]);
            }
            if (MiplayAudioTVService.this.h != null && MiplayAudioTVService.this.h.size() > 0 && MiplayAudioTVService.this.h.containsKey(nameForUid)) {
                if (((b) MiplayAudioTVService.this.h.get(nameForUid)).b != null) {
                    ((b) MiplayAudioTVService.this.h.get(nameForUid)).b.asBinder().unlinkToDeath(MiplayAudioTVService.this.n, 0);
                }
                MiplayAudioTVService.this.h.remove(nameForUid);
                if (MiplayAudioTVService.this.h.isEmpty()) {
                    MiplayAudioTVService.this.f();
                }
            }
            Log.e(MiplayAudioTVService.a, "binderDied after");
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        Logger.i(a, "onCreate.", new Object[0]);
        super.onCreate();
        d();
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        String str = a;
        Log.i(str, "onBind: --- " + intent.getAction());
        if (TextUtils.equals(intent.getAction(), ACTION_MIPLAY_TV_CLIENT)) {
            return this.t.asBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.i(a, "onDestroy.", new Object[0]);
        super.onDestroy();
        CmdSessionControl cmdSessionControl = this.k.get(this.b.getMac());
        if (this.k.get(this.b.getMac()) != null) {
            cmdSessionControl.closeCmdSession(this.b.getMac());
        }
        this.k.remove(this.b.getMac());
        this.k.clear();
        e();
        ThreadPoolManager.getInstance().shutdownExecutor();
        Handler handler = this.r;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            Logger.i(a, "remove all  msg!", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b() {
        Logger.i(a, "initCMDSessionControl.", new Object[0]);
        if (isInited()) {
            Logger.i(a, "already initCMDSessionControl.", new Object[0]);
        } else if (this.m) {
            String str = a;
            Logger.i(str, "mIsConnecting:" + this.m, new Object[0]);
        } else {
            this.m = true;
            CmdSessionControl cmdSessionControl = new CmdSessionControl(this.b);
            cmdSessionControl.addCmdClientCallback(new CmdClientCallback() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.3
                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onAudioOpenNotify(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onBeSeized(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onBtFrequencyACK(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onBufferStateNotify(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onChannelsAck(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onCirculateFail(MiDevice miDevice, byte[] bArr) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDeviceStartPlaying(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDisConnectOneNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDisconnectNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onMirrorModeAck(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onNextNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onNotifyPropertiesInfo(MiDevice miDevice, byte[] bArr) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onOpenAck(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPauseNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPhysicalBtVolumeNotify(MiDevice miDevice, int i) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPlayFinish(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPlayed(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPrevNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onResumeNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onSeekDoneNotify(MiDevice miDevice) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onSeekNotify(MiDevice miDevice, long j) {
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onSuccess() {
                    MiplayAudioTVService.this.r.obtainMessage(115, MiplayAudioTVService.this.b.getMac()).sendToTarget();
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onError() {
                    MiplayAudioTVService.this.r.obtainMessage(114, MiplayAudioTVService.this.b.getMac()).sendToTarget();
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPlayStateAck(MiDevice miDevice, int i) {
                    String str2 = MiplayAudioTVService.a;
                    Logger.i(str2, "onPlayStateAck:" + i, new Object[0]);
                    try {
                        for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                            ((b) entry.getValue()).b.onPlayStateAck(i);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPositionAck(MiDevice miDevice, long j) {
                    String str2 = MiplayAudioTVService.a;
                    Logger.i(str2, "onPositionAck:" + j, new Object[0]);
                    try {
                        for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                            ((b) entry.getValue()).b.onPositionAck(j);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onMediaInfoAck(MiDevice miDevice, byte[] bArr) {
                    synchronized (MiplayAudioTVService.this.f) {
                        Logger.i(MiplayAudioTVService.a, "onMediaInfoAck:", new Object[0]);
                        TVMediaMetaData analysisMediaInfo = MetaInfoTVManager.getInstance().analysisMediaInfo(bArr);
                        try {
                            for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                ((b) entry.getValue()).b.onMediaInfoAck(analysisMediaInfo);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onVolumeAck(MiDevice miDevice, int i) {
                    synchronized (MiplayAudioTVService.this.g) {
                        String str2 = MiplayAudioTVService.a;
                        Logger.i(str2, "onVolumeAck:" + i, new Object[0]);
                        try {
                            for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                ((b) entry.getValue()).b.onVolumeAck(i);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onPlayStateNotify(MiDevice miDevice, int i) {
                    String str2 = MiplayAudioTVService.a;
                    Logger.i(str2, "onPlayStateNotify:" + i, new Object[0]);
                    try {
                        for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                            ((b) entry.getValue()).b.onPlayStateChange(i);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onMediaInfoNotify(MiDevice miDevice, byte[] bArr) {
                    synchronized (MiplayAudioTVService.this.f) {
                        Logger.i(MiplayAudioTVService.a, "onMediaInfoNotify:", new Object[0]);
                        TVMediaMetaData analysisMediaInfo = MetaInfoTVManager.getInstance().analysisMediaInfo(bArr);
                        try {
                            for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                ((b) entry.getValue()).b.onMediaInfoChange(analysisMediaInfo);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onVolumeNotify(MiDevice miDevice, int i) {
                    synchronized (MiplayAudioTVService.this.g) {
                        String str2 = MiplayAudioTVService.a;
                        Logger.i(str2, "onVolumeNotify:" + i, new Object[0]);
                        try {
                            for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                ((b) entry.getValue()).b.onVolumeChange(i);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDeviceInfo(MiDevice miDevice, byte[] bArr) {
                    MiplayAudioTVService.this.r.obtainMessage(116, bArr).sendToTarget();
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDeviceNetworkChanged(MiDevice miDevice, byte[] bArr) {
                    MiplayAudioTVService.this.r.obtainMessage(118, bArr).sendToTarget();
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDeviceInfoChanged(MiDevice miDevice, byte[] bArr) {
                    MiplayAudioTVService.this.r.obtainMessage(118, bArr).sendToTarget();
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onMirrorModeNotify(MiDevice miDevice, int i) {
                    String str2 = MiplayAudioTVService.a;
                    Logger.i(str2, "onMirrorModeNotify:" + i, new Object[0]);
                    try {
                        for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                            ((b) entry.getValue()).b.onMirrorModeNotify(i);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
                public void onDurationUpdated(MiDevice miDevice, long j) {
                    MiplayAudioTVService.this.r.obtainMessage(117, Long.valueOf(j)).sendToTarget();
                }
            });
            this.k.put(this.b.getMac(), cmdSessionControl);
            cmdSessionControl.connectCmdSession(this.b.getMac(), this.b.getName(), this.b.getIp(), this.b.getPort());
        }
    }

    private void c() {
        this.b = new MiDevice();
        this.b.setMac("00:11:22:33:44:55");
        this.b.setIp("127.0.0.1");
        this.b.setPort(8899);
    }

    private void d() {
        this.o = new BroadcastReceiver() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    Logger.i(MiplayAudioTVService.a, "onReceive " + action, new Object[0]);
                    char c = 65535;
                    if (action.hashCode() == 595943702 && action.equals(MediaCoreConfig.ACTION_NOTICE)) {
                        c = 0;
                    }
                    if (c == 0) {
                        String stringExtra = intent.getStringExtra("msg");
                        Logger.i(MiplayAudioTVService.a, "onReceive-msg=" + stringExtra, new Object[0]);
                        if (!stringExtra.isEmpty()) {
                            try {
                                JSONObject jSONObject = new JSONObject(stringExtra);
                                Logger.i(MiplayAudioTVService.a, "jsonObject=" + jSONObject, new Object[0]);
                                String string = jSONObject.getString("listenPort");
                                if (!TextUtils.isEmpty(string)) {
                                    MiplayAudioTVService.this.b.setPort(Integer.valueOf(string).intValue());
                                }
                                MiplayAudioTVService.this.b();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        registerReceiver(this.o, new IntentFilter(MediaCoreConfig.ACTION_NOTICE));
    }

    private void e() {
        BroadcastReceiver broadcastReceiver = this.o;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.o = null;
        }
    }

    public boolean isInited() {
        return this.l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        Logger.i(a, "unInit.", new Object[0]);
        this.l = false;
        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.5
            @Override // java.lang.Runnable
            public void run() {
                CmdSessionControl cmdSessionControl = (CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac());
                if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) != null && !TextUtils.isEmpty(MiplayAudioTVService.this.b.getMac())) {
                    cmdSessionControl.closeCmdSession(MiplayAudioTVService.this.b.getMac());
                    MiplayAudioTVService.this.k.remove(MiplayAudioTVService.this.b.getMac());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        Logger.i(a, "cmdSessionSuccess.", new Object[0]);
        HashMap<String, b> hashMap = this.h;
        if (hashMap != null) {
            try {
                for (Map.Entry<String, b> entry : hashMap.entrySet()) {
                    if (entry.getValue().b != null) {
                        entry.getValue().b.onCmdSessionSuccess();
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.k.get(this.b.getMac()) == null) {
            Logger.i(a, "CmdSessionControl on a null object", new Object[0]);
            return;
        }
        this.k.get(this.b.getMac()).getDeviceInfo();
        byte[] localDeviceInfo = this.c.setLocalDeviceInfo("1", "mpa");
        if (this.k.get(this.b.getMac()) == null) {
            Logger.i(a, "cmdSessionControlMap on a null object", new Object[0]);
        } else {
            this.k.get(this.b.getMac()).setLocalDeviceInfo(localDeviceInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Logger.i(a, "cmdSessionError.", new Object[0]);
        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.tv.MiplayAudioTVService.6
            @Override // java.lang.Runnable
            public void run() {
                CmdSessionControl cmdSessionControl = (CmdSessionControl) MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac());
                if (MiplayAudioTVService.this.k.get(MiplayAudioTVService.this.b.getMac()) != null) {
                    cmdSessionControl.closeCmdSession(MiplayAudioTVService.this.b.getMac());
                }
                MiplayAudioTVService.this.k.remove(MiplayAudioTVService.this.b.getMac());
                try {
                    MiplayAudioTVService.this.p++;
                    if (MiplayAudioTVService.this.p > MiplayAudioTVService.this.q.length) {
                        MiplayAudioTVService.this.p = MiplayAudioTVService.this.q.length;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (MiplayAudioTVService.this.p != 0) {
                    int i = MiplayAudioTVService.this.q[MiplayAudioTVService.this.p - 1] * 1000;
                    Logger.i(MiplayAudioTVService.a, "delayTime:" + i, new Object[0]);
                    Thread.sleep((long) i);
                    if (MiplayAudioTVService.this.h != null) {
                        try {
                            for (Map.Entry entry : MiplayAudioTVService.this.h.entrySet()) {
                                if (((b) entry.getValue()).b != null) {
                                    ((b) entry.getValue()).b.onCmdSessionError();
                                }
                            }
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                        }
                    }
                    MiplayAudioTVService.this.b();
                }
            }
        });
    }
}
