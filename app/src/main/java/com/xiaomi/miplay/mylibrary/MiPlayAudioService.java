package com.xiaomi.miplay.mylibrary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.miplay.audioclient.IMiPlayClient;
import com.xiaomi.miplay.audioclient.IMiPlayClientCallback;
import com.xiaomi.miplay.audioclient.MediaMetaData;
import com.xiaomi.miplay.audioclient.MiPlayDevice;
import com.xiaomi.miplay.audioclient.MiPlayDeviceControlCenter;
import com.xiaomi.miplay.mylibrary.MiplayMdns;
import com.xiaomi.miplay.mylibrary.circulate.MiPlayVideoClientInfo;
import com.xiaomi.miplay.mylibrary.circulate.MiplayMirrorService;
import com.xiaomi.miplay.mylibrary.circulate.MiplayVideoService;
import com.xiaomi.miplay.mylibrary.circulate.PlayConfig;
import com.xiaomi.miplay.mylibrary.circulate.VideoCirculatePool;
import com.xiaomi.miplay.mylibrary.external.MediaControlImpl;
import com.xiaomi.miplay.mylibrary.manager.AudioManagerHelper;
import com.xiaomi.miplay.mylibrary.manager.ReceiveStopStackManager;
import com.xiaomi.miplay.mylibrary.manager.SyncVolumeStackManager;
import com.xiaomi.miplay.mylibrary.mirror.AudioRecodeBean;
import com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback;
import com.xiaomi.miplay.mylibrary.mirror.CmdSessionControl;
import com.xiaomi.miplay.mylibrary.mirror.DeviceUtil;
import com.xiaomi.miplay.mylibrary.mirror.MirrorServer;
import com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin;
import com.xiaomi.miplay.mylibrary.screenbox.PlayStateConfig;
import com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager;
import com.xiaomi.miplay.mylibrary.session.ActiveSessionRecord;
import com.xiaomi.miplay.mylibrary.session.ActiveSessionRecordStack;
import com.xiaomi.miplay.mylibrary.session.SessionStateDispatcher;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackStatistics;
import com.xiaomi.miplay.mylibrary.statistic.StatsUtils;
import com.xiaomi.miplay.mylibrary.tv.UpdatePositonPool;
import com.xiaomi.miplay.mylibrary.utils.BitMapUtils;
import com.xiaomi.miplay.mylibrary.utils.ByteUtils;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.mylibrary.utils.Iputils;
import com.xiaomi.miplay.mylibrary.utils.MiPlayAudioRecordSettingsHelper;
import com.xiaomi.miplay.mylibrary.utils.UUIDGenerator;
import com.xiaomi.mipush.sdk.Constants;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(api = 23)
/* loaded from: classes4.dex */
public class MiPlayAudioService extends Service implements ActiveAudioSessionManager.ActiveAudioSessionCallback {
    public static final String ACTION_MIPLAY_CLIENT = "com.xiaomi.miplay.action.MIPLAY_AUDIO_CLIENT_SERVICE";
    private static CaptureCallBack H = null;
    private static MiplayMdns K = null;
    public static final int RETRYFREQUENCY = 5;
    private static int j = 110;
    private NetworkConnectChangedReceiver C;
    private a E;
    private CountDownTimer I;
    private volatile boolean J;
    private b Q;
    private HandlerThread R;
    private String S;
    private AudioManagerHelper T;
    private long W;
    private MediaControlImpl X;
    private MirrorServer a;
    private ScheduledThreadPoolExecutor ad;
    private NotificationManager i;
    private MetaInfoManager m;
    private AudioRecodeBean p;
    @NonNull
    private ActiveAudioSessionManager q;
    private Context r;
    public Map<String, CmdSessionControl> cmdSessionControlMap = new ConcurrentHashMap();
    private Map<String, Integer> d = new ConcurrentHashMap();
    private Map<String, Integer> e = new ConcurrentHashMap();
    private Map<Integer, String> f = new ConcurrentHashMap();
    private Map<String, String> g = new ConcurrentHashMap();
    private String h = "";
    private List<MiPlayDevice> k = new ArrayList();
    private List<MiPlayDeviceControlCenter> l = new ArrayList();
    private List<MiDevice> n = new ArrayList();
    private int o = 7236;
    private AtomicBoolean s = new AtomicBoolean(false);
    private final ArraySet<Integer> t = new ArraySet<>();
    private final Object u = new Object();
    private final Object v = new Object();
    private final Object w = new Object();
    private final Object x = new Object();
    private final Object y = new Object();
    private final Object z = new Object();
    private final Object A = new Object();
    private final Object B = new Object();
    private int D = 1;
    private ActiveSessionRecord F = null;
    private boolean G = false;
    private boolean L = false;
    private int M = -1;
    private Map<String, Integer> N = new ConcurrentHashMap();
    private MiplayVideoService O = null;
    private MiplayMirrorService P = null;
    private int U = 0;
    private final IBinder V = new LocalBinder();
    private Handler Y = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Logger.i("MiPlayAudioService", "handleMessage type:" + message.what, new Object[0]);
            switch (message.what) {
                case 0:
                    Logger.i("MiPlayAudioService", "build time:202201211824", new Object[0]);
                    Logger.i("MiPlayAudioService", "commit :96a515b1", new Object[0]);
                    Logger.i("MiPlayAudioService", "versionCode:1", new Object[0]);
                    Logger.i("MiPlayAudioService", "versionName:1.0", new Object[0]);
                    Object[] objArr = (Object[]) message.obj;
                    MiPlayClientInfo miPlayClientInfo = (MiPlayClientInfo) objArr[0];
                    String str = (String) objArr[1];
                    MiPlayAudioService.this.h = miPlayClientInfo.a;
                    if (!MiPlayAudioService.this.c.containsKey(miPlayClientInfo)) {
                        MiPlayAudioService miPlayAudioService = MiPlayAudioService.this;
                        miPlayAudioService.E = new a();
                        if (MiPlayAudioService.this.c != null) {
                            try {
                                for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                                    if (((MiPlayClientInfo) entry.getValue()).b != null) {
                                        ((MiPlayClientInfo) entry.getValue()).b.asBinder().linkToDeath(MiPlayAudioService.this.E, 0);
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                MiPlayAudioService.this.c.remove(MiPlayAudioService.this.h);
                                Logger.i("MiPlayAudioService", "mMiPlayClientMap remove:" + MiPlayAudioService.this.h, new Object[0]);
                            }
                        }
                    }
                    Logger.i("MiPlayAudioService", "ClientInfo type :  id start：" + miPlayClientInfo.a, new Object[0]);
                    if (miPlayClientInfo.b != null) {
                        MiPlayAudioService.this.c.put(miPlayClientInfo.a, miPlayClientInfo);
                        Logger.i("MiPlayAudioService", "ClientInfo put :  id " + miPlayClientInfo.a, new Object[0]);
                    }
                    MiPlayAudioService.this.m();
                    if (!MiPlayAudioService.this.b.isInited() || miPlayClientInfo.b == null) {
                        Logger.i("MiPlayAudioService", "ClientInfo init" + miPlayClientInfo.a, new Object[0]);
                        MiPlayAudioService.this.b.init(MiPlayAudioService.this.aa, str);
                        return;
                    }
                    Logger.i("MiPlayAudioService", "ClientInfo callback", new Object[0]);
                    try {
                        miPlayClientInfo.b.onInitSuccess();
                        Logger.i("miplay_time", "miplay init end", new Object[0]);
                        return;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 1:
                    String str2 = (String) message.obj;
                    MiPlayAudioService.this.h = str2;
                    MiPlayAudioService.this.c.remove(str2);
                    if (MiPlayAudioService.this.c.isEmpty()) {
                        MiPlayAudioService.this.b.unInit(true);
                        MiPlayAudioService.this.stopMirror();
                    }
                    Logger.i("MiPlayAudioService", "uninit sendBroadcast  state ", new Object[0]);
                    return;
                case 2:
                    StatsUtils.getInstance().startTimer(0);
                    MiPlayAudioService.this.startDiscovery(((Integer) message.obj).intValue());
                    return;
                case 3:
                    Logger.i("MiPlayAudioService", "no response stopDiscovery", new Object[0]);
                    return;
                case 4:
                    Logger.i("MiPlayAudioService", "init cmdIngDeviceList:" + MiPlayAudioService.this.n.size(), new Object[0]);
                    Object[] objArr2 = (Object[]) message.obj;
                    String[] strArr = (String[]) objArr2[0];
                    Logger.i("MiPlayAudioService", "uuidList:" + strArr.length, new Object[0]);
                    if (strArr == null || strArr.length <= 0) {
                        Logger.i("MiPlayAudioService", "deviceMacList is null", new Object[0]);
                        return;
                    } else if (!MiPlayAudioService.this.mDeviceManager.isUuidExists(MiPlayAudioService.this.mDeviceManager.getMiDeviceList(), strArr)) {
                        Logger.i("MiPlayAudioService", "callback onDeviceLost and onMultiDisplayDisConnected", new Object[0]);
                        for (String str3 : strArr) {
                            MiPlayAudioService.this.d(str3);
                            MiPlayAudioService.this.a(str3, 0);
                        }
                        return;
                    } else {
                        boolean isEmpty = MiPlayAudioService.this.d.isEmpty();
                        Logger.i("MiPlayAudioService", "MSG_ONPLAY newCreate:" + isEmpty, new Object[0]);
                        int length = strArr.length;
                        for (int i = 0; i < length; i++) {
                            Logger.i("MiPlayAudioService", "uuid:" + strArr[i], new Object[0]);
                            StatsUtils.getInstance().startTimer(1);
                            StatsUtils.getInstance().startTimer(2);
                        }
                        MiPlayAudioRecordSettingsHelper.openMiPlayOptimize(MiPlayAudioService.this.r);
                        String str4 = (String) objArr2[1];
                        if (!isEmpty) {
                            Logger.i("MiPlayAudioService", "it's already playing", new Object[0]);
                            MiPlayAudioService.this.a(strArr);
                            if (MiPlayAudioService.this.n.size() == 0) {
                                Logger.i("MiPlayAudioService", "cmdIngDeviceList size 0", new Object[0]);
                                return;
                            }
                            String a2 = MiPlayAudioService.this.a("");
                            if (!TextUtils.isEmpty(a2)) {
                                a2 = a2.substring(0, a2.lastIndexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
                            }
                            Logger.i("MiPlayAudioService", "multiPort substring after:" + a2, new Object[0]);
                            if (MiPlayAudioService.this.a != null) {
                                List asList = Arrays.asList(a2.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
                                Logger.i("MiPlayAudioService", "resultList:" + asList.size(), new Object[0]);
                                for (int i2 = 0; i2 < asList.size(); i2++) {
                                    Logger.i("MiPlayAudioService", "val:" + ((String) asList.get(i2)), new Object[0]);
                                    if (!TextUtils.isEmpty((CharSequence) asList.get(i2))) {
                                        MiPlayAudioService.this.a.addMirror((String) asList.get(i2), MiPlayAudioService.this.f);
                                    } else {
                                        Logger.i("MiPlayAudioService", "port is empty!", new Object[0]);
                                    }
                                }
                            } else {
                                MiPlayAudioService miPlayAudioService2 = MiPlayAudioService.this;
                                miPlayAudioService2.startMirror(miPlayAudioService2.ac, a2, str4, MiPlayAudioService.this.f);
                            }
                        } else {
                            MiPlayAudioService.this.a((ActiveSessionRecord) null);
                            Logger.i("MiPlayAudioService", "first play", new Object[0]);
                            MiPlayAudioService.this.a(strArr);
                            Logger.i("MiPlayAudioService", "cmdIngDeviceList:" + MiPlayAudioService.this.n.size(), new Object[0]);
                            if (MiPlayAudioService.this.n.size() == 0) {
                                Logger.i("MiPlayAudioService", "cmdIngDeviceList size 0", new Object[0]);
                                return;
                            }
                            String a3 = MiPlayAudioService.this.a("");
                            String substring = a3.substring(0, a3.lastIndexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
                            Logger.i("MiPlayAudioService", "multiPort substring after:" + substring, new Object[0]);
                            MiPlayAudioService miPlayAudioService3 = MiPlayAudioService.this;
                            miPlayAudioService3.startMirror(miPlayAudioService3.ac, substring, str4, MiPlayAudioService.this.f);
                        }
                        StatsUtils.getInstance().setPlaySource(MiPlayAudioService.this.mDeviceManager, MiPlayAudioService.this.cmdSessionControlMap);
                        return;
                    }
                case 5:
                    String[] strArr2 = (String[]) message.obj;
                    Logger.i("MiPlayAudioService", "rev pause cmd", new Object[0]);
                    Logger.i("MiPlayAudioService", "MiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getMiDeviceList().size(), new Object[0]);
                    if (MiPlayAudioService.this.b(strArr2)) {
                        MiPlayAudioService.this.LocalPause();
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr2);
                    for (MiDevice miDevice : MiPlayAudioService.this.n) {
                        Logger.i("MiPlayAudioService", "MirrorMode:" + miDevice.getMirrorMode(), new Object[0]);
                        if (miDevice.getMirrorMode() == 1) {
                            Logger.i("MiPlayAudioService", "ConnectedMiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size(), new Object[0]);
                            if (MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size() == 1) {
                                MiPlayAudioService.this.LocalPause();
                            }
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "CmdSessionControl.pauseDevice()' on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).pauseDevice();
                        } else if (miDevice.getMirrorMode() != 2) {
                            continue;
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).pauseMediaPlayer();
                        }
                    }
                    return;
                case 6:
                    Logger.i("MiPlayAudioService", "rev close cmd", new Object[0]);
                    MiPlayAudioService.this.a((String[]) message.obj);
                    for (MiDevice miDevice2 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice2.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice2.getMac()).closeDevice();
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice2.getMac()).closeCmdSession(miDevice2.getMac());
                        }
                    }
                    MiPlayAudioService.this.stopMirror();
                    return;
                case 7:
                    Logger.i("MiPlayAudioService", "rev resume cmd", new Object[0]);
                    Logger.i("MiPlayAudioService", "MiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getMiDeviceList().size(), new Object[0]);
                    String[] strArr3 = (String[]) message.obj;
                    if (MiPlayAudioService.this.b(strArr3)) {
                        MiPlayAudioService.this.g();
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr3);
                    for (MiDevice miDevice3 : MiPlayAudioService.this.n) {
                        if (miDevice3.getMirrorMode() == 1) {
                            if (!TextUtils.isEmpty(miDevice3.getGroupId()) && MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size() == 2) {
                                MiPlayAudioService.this.g();
                            }
                            Logger.i("MiPlayAudioService", "ConnectedMiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size(), new Object[0]);
                            if (MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size() == 1) {
                                MiPlayAudioService.this.g();
                            }
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice3.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "CmdSessionControl on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice3.getMac()).resumeDevice();
                        } else if (miDevice3.getMirrorMode() != 2) {
                            continue;
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice3.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice3.getMac()).resumeMediaPlayer();
                        }
                    }
                    return;
                case 8:
                    Logger.i("MiPlayAudioService", "rev seek cmd", new Object[0]);
                    Object[] objArr3 = (Object[]) message.obj;
                    String[] strArr4 = (String[]) objArr3[0];
                    long longValue = ((Long) objArr3[1]).longValue();
                    if (MiPlayAudioService.this.b(strArr4)) {
                        MiPlayAudioService.this.a(longValue);
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr4);
                    for (MiDevice miDevice4 : MiPlayAudioService.this.n) {
                        Logger.i("MiPlayAudioService", "MirrorMode:" + miDevice4.getMirrorMode(), new Object[0]);
                        if (miDevice4.getMirrorMode() == 1) {
                            MiPlayAudioService.this.a(longValue);
                        } else if (miDevice4.getMirrorMode() != 2) {
                            continue;
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice4.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "CmdSessionControl.seek()' on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice4.getMac()).seekMediaPlayer(longValue);
                        }
                    }
                    return;
                case 9:
                    Logger.i("MiPlayAudioService", "rev setvolume cmd", new Object[0]);
                    Object[] objArr4 = (Object[]) message.obj;
                    String[] strArr5 = (String[]) objArr4[0];
                    int intValue = ((Integer) objArr4[1]).intValue();
                    if (MiPlayAudioService.this.b(strArr5)) {
                        MiPlayAudioService.this.T.setVolume(intValue);
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr5);
                    for (MiDevice miDevice5 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice5.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice5.getMac()).setVolume(intValue);
                    }
                    return;
                case 10:
                    Logger.i("MiPlayAudioService", "rev getvolume cmd", new Object[0]);
                    String[] strArr6 = (String[]) message.obj;
                    if (strArr6 == null || strArr6.length == 0) {
                        Logger.i("MiPlayAudioService", "deviceMacList is null", new Object[0]);
                        return;
                    } else if (MiPlayAudioService.this.b(strArr6)) {
                        int volume = MiPlayAudioService.this.T.getVolume();
                        MiDevice miDevice6 = new MiDevice();
                        miDevice6.setUuid(DataModel.LOCAL_DEVICE_ID);
                        MiPlayAudioService.this.h(miDevice6, volume);
                        return;
                    } else {
                        Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                        MiPlayAudioService.this.a(strArr6);
                        if (MiPlayAudioService.this.n == null || MiPlayAudioService.this.n.size() == 0) {
                            Logger.i("MiPlayAudioService", "cmdIngDeviceList is null", new Object[0]);
                            return;
                        }
                        for (MiDevice miDevice7 : MiPlayAudioService.this.n) {
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice7.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice7.getMac()).getVolume();
                        }
                        return;
                    }
                case 11:
                    Logger.i("MiPlayAudioService", "rev getposition cmd", new Object[0]);
                    String[] strArr7 = (String[]) message.obj;
                    if (MiPlayAudioService.this.b(strArr7)) {
                        long localPosition = MiPlayAudioService.this.getLocalPosition();
                        Logger.i("MiPlayAudioService", "local_device:local_device_id position:" + localPosition, new Object[0]);
                        MiPlayAudioService.this.a(DataModel.LOCAL_DEVICE_ID, localPosition);
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr7);
                    for (MiDevice miDevice8 : MiPlayAudioService.this.n) {
                        if (miDevice8.getMirrorMode() == 1) {
                            MiPlayAudioService.this.a(miDevice8.getUuid(), MiPlayAudioService.this.getLocalPosition());
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice8.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "CmdSessionControl.getPosition()' on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice8.getMac()).getPosition();
                        }
                    }
                    return;
                case 12:
                    Logger.i("MiPlayAudioService", "rev setmediainfo cmd", new Object[0]);
                    Object[] objArr5 = (Object[]) message.obj;
                    final MediaMetaData mediaMetaData = (MediaMetaData) objArr5[1];
                    MiPlayAudioService.this.a((String[]) objArr5[0]);
                    for (final MiDevice miDevice9 : MiPlayAudioService.this.n) {
                        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice9.getMac()) == null) {
                                    Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                                    return;
                                }
                                Logger.i("MiPlayAudioService", "setMediaInfo = " + mediaMetaData.toString(), new Object[0]);
                                MiPlayAudioService.this.cmdSessionControlMap.get(miDevice9.getMac()).setMediaInfo(MiPlayAudioService.this.m.mediaMetaDataToJson(mediaMetaData, miDevice9));
                            }
                        });
                    }
                    return;
                case 13:
                    Logger.i("MiPlayAudioService", "rev getmediainfo cmd", new Object[0]);
                    MiPlayAudioService.this.a((String[]) message.obj);
                    for (MiDevice miDevice10 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice10.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice10.getMac()).getMediaInfo();
                    }
                    return;
                case 14:
                    Object[] objArr6 = (Object[]) message.obj;
                    String str5 = (String) objArr6[0];
                    Logger.d("MiPlayAudioService", "MSG_CHANNEL deviceMac:" + str5, new Object[0]);
                    int intValue2 = ((Integer) objArr6[1]).intValue();
                    Logger.i("MiPlayAudioService", "MSG_CHANNEL layout:" + intValue2, new Object[0]);
                    if (MiPlayAudioService.this.cmdSessionControlMap.get(str5) == null) {
                        Logger.i("MiPlayAudioService", "CmdSessionControl.setChannel()' on a null object", new Object[0]);
                        return;
                    } else {
                        MiPlayAudioService.this.cmdSessionControlMap.get(str5).setChannel(intValue2);
                        return;
                    }
                case 15:
                    Logger.i("MiPlayAudioService", "rev playstate cmd", new Object[0]);
                    String[] strArr8 = (String[]) message.obj;
                    if (MiPlayAudioService.this.b(strArr8)) {
                        MiDevice miDevice11 = new MiDevice();
                        miDevice11.setUuid(DataModel.LOCAL_DEVICE_ID);
                        int localPlayState = MiPlayAudioService.this.getLocalPlayState();
                        Logger.i("MiPlayAudioService", "local_device:" + miDevice11.getUuid() + " state", new Object[0]);
                        MiPlayAudioService.this.a(miDevice11, localPlayState);
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr8);
                    for (MiDevice miDevice12 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice12.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice12.getMac()).getState();
                    }
                    return;
                case 16:
                    Logger.i("MiPlayAudioService", "rev stop cmd", new Object[0]);
                    MiPlayAudioService.this.U = 0;
                    MiPlayAudioService.this.d((String[]) message.obj);
                    return;
                case 17:
                    String[] strArr9 = (String[]) message.obj;
                    for (String str6 : strArr9) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(str6) == null) {
                            Logger.i("MiPlayAudioService", "CmdSessionControl.getChannel()' on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(str6).getChannel();
                        }
                    }
                    return;
                case 18:
                case 19:
                case 20:
                case 21:
                case 34:
                default:
                    return;
                case 22:
                    Object[] objArr7 = (Object[]) message.obj;
                    int intValue3 = ((Integer) objArr7[0]).intValue();
                    Logger.i("MiPlayAudioService", "rev stopMirror indexMap:" + MiPlayAudioService.this.d.size() + " index:" + intValue3, new Object[0]);
                    MiPlayAudioService.this.a(intValue3, (String) objArr7[1]);
                    return;
                case 23:
                    Logger.i("MiPlayAudioService", "rev next cmd", new Object[0]);
                    String[] strArr10 = (String[]) message.obj;
                    if (MiPlayAudioService.this.b(strArr10)) {
                        MiPlayAudioService.this.e();
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr10);
                    for (MiDevice miDevice13 : MiPlayAudioService.this.n) {
                        Logger.i("MiPlayAudioService", "MirrorMode:" + miDevice13.getMirrorMode(), new Object[0]);
                        if (miDevice13.getMirrorMode() == 1) {
                            MiPlayAudioService.this.e();
                        } else if (miDevice13.getMirrorMode() != 2) {
                            continue;
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice13.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice13.getMac()).playNext();
                        }
                    }
                    return;
                case 24:
                    Logger.i("MiPlayAudioService", "rev prev cmd", new Object[0]);
                    String[] strArr11 = (String[]) message.obj;
                    if (MiPlayAudioService.this.b(strArr11)) {
                        MiPlayAudioService.this.f();
                        return;
                    }
                    Logger.i("MiPlayAudioService", "no-local cmd", new Object[0]);
                    MiPlayAudioService.this.a(strArr11);
                    for (MiDevice miDevice14 : MiPlayAudioService.this.n) {
                        if (miDevice14.getMirrorMode() == 1) {
                            MiPlayAudioService.this.f();
                        } else if (miDevice14.getMirrorMode() != 2) {
                            continue;
                        } else if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice14.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        } else {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice14.getMac()).playPrev();
                        }
                    }
                    return;
                case 25:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_sessionerror cmd", new Object[0]);
                    MiDevice miDevice15 = (MiDevice) message.obj;
                    if (miDevice15 != null) {
                        MiPlayAudioService.this.f(miDevice15);
                        return;
                    }
                    return;
                case 26:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_devicesinfo cmd", new Object[0]);
                    Object[] objArr8 = (Object[]) message.obj;
                    MiDevice miDevice16 = (MiDevice) objArr8[0];
                    byte[] bArr = (byte[]) objArr8[1];
                    if (miDevice16 != null) {
                        MiPlayAudioService.this.a(miDevice16, bArr);
                        return;
                    }
                    return;
                case 27:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_opendevice cmd", new Object[0]);
                    Object[] objArr9 = (Object[]) message.obj;
                    String str7 = (String) objArr9[0];
                    int intValue4 = ((Integer) objArr9[1]).intValue();
                    if (MiPlayAudioService.this.cmdSessionControlMap.get(str7) == null) {
                        Logger.i("MiPlayAudioService", "CmdSessionControl.openDevice()' on a null object", new Object[0]);
                        return;
                    } else {
                        MiPlayAudioService.this.cmdSessionControlMap.get(str7).openDevice(DeviceUtil.getWifiIpAddress(MiPlayAudioService.this), intValue4);
                        return;
                    }
                case 28:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_devicenetworkchanged cmd", new Object[0]);
                    Object[] objArr10 = (Object[]) message.obj;
                    MiDevice miDevice17 = (MiDevice) objArr10[0];
                    byte[] bArr2 = (byte[]) objArr10[1];
                    if (miDevice17 != null) {
                        MiPlayAudioService.this.c(miDevice17, bArr2);
                        return;
                    }
                    return;
                case 29:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_onrefresh cmd", new Object[0]);
                    for (MiDevice miDevice18 : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                        MiPlayAudioService.this.f(miDevice18.getMac());
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice18.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice18.getMac()).getDeviceInfo();
                    }
                    return;
                case 30:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_deviceinfochanged cmd", new Object[0]);
                    Object[] objArr11 = (Object[]) message.obj;
                    MiDevice miDevice19 = (MiDevice) objArr11[0];
                    byte[] bArr3 = (byte[]) objArr11[1];
                    if (miDevice19 != null) {
                        MiPlayAudioService.this.b(miDevice19, bArr3);
                        return;
                    }
                    return;
                case 31:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_setbtfrequency cmd", new Object[0]);
                    Object[] objArr12 = (Object[]) message.obj;
                    int intValue5 = ((Integer) objArr12[1]).intValue();
                    MiPlayAudioService.this.a((String[]) objArr12[0]);
                    for (MiDevice miDevice20 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice20.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice20.getMac()).setBtFrequency(intValue5);
                    }
                    return;
                case 32:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_getbtfrequency cmd", new Object[0]);
                    MiPlayAudioService.this.a((String[]) message.obj);
                    for (MiDevice miDevice21 : MiPlayAudioService.this.n) {
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice21.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice21.getMac()).getBtFrequency();
                    }
                    return;
                case 33:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_ondevicefound cmd", new Object[0]);
                    MiDevice miDevice22 = (MiDevice) message.obj;
                    Logger.d("MiPlayAudioService", "onDeviceFound: " + miDevice22.toString(), new Object[0]);
                    StatsUtils.getInstance().stopTimer(0, miDevice22.getDeviceType(), MiPlayAudioService.this.mDeviceManager);
                    MiPlayAudioService.this.deviceFound(miDevice22);
                    return;
                case 35:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_multidisplayconnected cmd", new Object[0]);
                    Object[] objArr13 = (Object[]) message.obj;
                    MiPlayAudioService.this.b(((Integer) objArr13[0]).intValue(), (String) objArr13[1]);
                    return;
                case 36:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_multidisplaydisconnected cmd", new Object[0]);
                    MiPlayAudioService.this.multiDisplayDisConnected(((Integer) message.obj).intValue());
                    return;
                case 37:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_multidisplayerror cmd", new Object[0]);
                    Object[] objArr14 = (Object[]) message.obj;
                    MiPlayAudioService.this.multiDisplayError(((Integer) objArr14[0]).intValue(), ((Integer) objArr14[1]).intValue(), ((Integer) objArr14[2]).intValue());
                    return;
                case 38:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_sessionsuccess cmd", new Object[0]);
                    MiPlayAudioService.this.j((MiDevice) message.obj);
                    return;
                case 39:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_mirrormodeack cmd", new Object[0]);
                    Object[] objArr15 = (Object[]) message.obj;
                    MiPlayAudioService.this.d((MiDevice) objArr15[0], ((Integer) objArr15[1]).intValue());
                    return;
                case 40:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_mirrormodenotify cmd", new Object[0]);
                    Object[] objArr16 = (Object[]) message.obj;
                    MiPlayAudioService.this.e((MiDevice) objArr16[0], ((Integer) objArr16[1]).intValue());
                    return;
                case 41:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_setboxpause cmd", new Object[0]);
                    Logger.i("MiPlayAudioService", "MiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getMiDeviceList().size(), new Object[0]);
                    MiPlayAudioService.this.a((String[]) message.obj);
                    for (MiDevice miDevice23 : MiPlayAudioService.this.n) {
                        Logger.d("MiPlayAudioService", "devicemac:" + miDevice23.getMac() + " MirrorMode:" + miDevice23.getMirrorMode(), new Object[0]);
                        if (miDevice23.getMirrorMode() == 1) {
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice23.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "CmdSessionControl.pauseDevice()' on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice23.getMac()).pauseDevice();
                        }
                    }
                    return;
                case 42:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_setboxresume cmd", new Object[0]);
                    Logger.i("MiPlayAudioService", "MiDeviceList:" + MiPlayAudioService.this.mDeviceManager.getMiDeviceList().size(), new Object[0]);
                    MiPlayAudioService.this.a((String[]) message.obj);
                    for (MiDevice miDevice24 : MiPlayAudioService.this.n) {
                        Logger.d("MiPlayAudioService", "devicemac:" + miDevice24.getMac() + " MirrorMode:" + miDevice24.getMirrorMode(), new Object[0]);
                        if (miDevice24.getMirrorMode() == 1) {
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice24.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "CmdSessionControl on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice24.getMac()).resumeDevice();
                        }
                    }
                    return;
                case 43:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_multidisplayprepared cmd", new Object[0]);
                    Object[] objArr17 = (Object[]) message.obj;
                    MiPlayAudioService.this.a(((Integer) objArr17[0]).intValue(), ((Integer) objArr17[1]).intValue());
                    return;
                case 44:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_mediainfo_notify cmd", new Object[0]);
                    Object[] objArr18 = (Object[]) message.obj;
                    final MiDevice miDevice25 = (MiDevice) objArr18[0];
                    final byte[] bArr4 = (byte[]) objArr18[1];
                    if (miDevice25 != null) {
                        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                MiPlayAudioService.this.mediaInfoNotify(miDevice25, bArr4);
                            }
                        });
                        return;
                    }
                    return;
                case 45:
                    Logger.i("MiPlayAudioService", "rev msg_cmd_mediainfo_ack cmd", new Object[0]);
                    Object[] objArr19 = (Object[]) message.obj;
                    final MiDevice miDevice26 = (MiDevice) objArr19[0];
                    final byte[] bArr5 = (byte[]) objArr19[1];
                    if (miDevice26 != null) {
                        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.1.3
                            @Override // java.lang.Runnable
                            public void run() {
                                MiPlayAudioService.this.mediaInfoAck(miDevice26, bArr5);
                            }
                        });
                        return;
                    }
                    return;
                case 46:
                    Logger.i("MiPlayAudioService", "response stopUwbDiscovery", new Object[0]);
                    MiPlayAudioService.this.s.set(false);
                    MiPlayAudioService.this.stopDiscovery();
                    return;
                case 47:
                    Logger.i("MiPlayAudioService", "msg_network_state_changed", new Object[0]);
                    ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.1.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                for (MiDevice miDevice27 : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                                    Logger.d("MiPlayAudioService", "miDevice:" + miDevice27.toString(), new Object[0]);
                                    MiPlayAudioService.this.d(miDevice27.getUuid());
                                    CmdSessionControl cmdSessionControl = MiPlayAudioService.this.cmdSessionControlMap.get(miDevice27.getMac());
                                    if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice27.getMac()) != null) {
                                        cmdSessionControl.closeCmdSession(miDevice27.getMac());
                                    }
                                    MiPlayAudioService.this.cmdSessionControlMap.remove(miDevice27.getMac());
                                    UpdatePositonPool.getInstance().setCmdSessionControlMap(MiPlayAudioService.this.cmdSessionControlMap);
                                }
                                MiPlayAudioService.this.d();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    });
                    return;
                case 48:
                    Logger.i("MiPlayAudioService", "rev msg_physical_bt_volumenotify cmd", new Object[0]);
                    Object[] objArr20 = (Object[]) message.obj;
                    MiPlayAudioService.this.g((MiDevice) objArr20[0], ((Integer) objArr20[1]).intValue());
                    return;
                case 49:
                    Logger.i("MiPlayAudioService", "rev msg_onvolume_ack cmd", new Object[0]);
                    Object[] objArr21 = (Object[]) message.obj;
                    MiPlayAudioService.this.h((MiDevice) objArr21[0], ((Integer) objArr21[1]).intValue());
                    return;
                case 50:
                    Logger.i("MiPlayAudioService", "rev msg_onplaystate_notify cmd", new Object[0]);
                    Object[] objArr22 = (Object[]) message.obj;
                    MiPlayAudioService.this.b((MiDevice) objArr22[0], ((Integer) objArr22[1]).intValue());
                    return;
            }
        }
    };
    private IMiPlayClient Z = new IMiPlayClient.Stub() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.6
        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int init(String str, IMiPlayClientCallback iMiPlayClientCallback, String str2) {
            MiPlayAudioService.this.Y.obtainMessage(0, new Object[]{new MiPlayClientInfo(str, iMiPlayClientCallback), str2}).sendToTarget();
            Logger.i("miplay_time", "miplay init start", new Object[0]);
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int unInit(String str) {
            Logger.i("MiPlayAudioService", "unInit !", new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(1, str).sendToTarget();
            Logger.i("MiPlayAudioService", "unInit end", new Object[0]);
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int startDiscovery(int i) throws RemoteException {
            Logger.i("miplay_time", "startDiscovery disctype" + i, new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(2, Integer.valueOf(i)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stopDiscovery() {
            Logger.i("MiPlayAudioService", "stopDiscovery", new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(3).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public boolean isDiscovering() throws RemoteException {
            return MiPlayAudioService.this.b.isDiscovering();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPlay(String[] strArr, String str) throws RemoteException {
            String nameForUid = MiPlayAudioService.this.getPackageManager().getNameForUid(Binder.getCallingUid());
            Logger.i("MiPlayAudioService", "clientPackageName:" + nameForUid, new Object[0]);
            if (nameForUid.equals(Constant.PACKAGENAME_XIAOAI)) {
                StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "xiaoai_phone");
            } else if (nameForUid.equals("com.xiaomi.smarthome")) {
                StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "farfield");
            }
            MiPlayAudioService.this.Y.obtainMessage(4, new Object[]{strArr, str}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stop(String[] strArr) throws RemoteException {
            Logger.i("MiPlayAudioService", "---------stop------mMultiMirrorstatus:" + MiPlayAudioService.this.U, new Object[0]);
            if (MiPlayAudioService.this.U == 1) {
                MiPlayAudioService.this.Y.sendMessageDelayed(MiPlayAudioService.this.Y.obtainMessage(16, strArr), 500L);
            } else {
                MiPlayAudioService.this.Y.obtainMessage(16, strArr).sendToTarget();
            }
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int closeDevice(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(6, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPause(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(5, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onResume(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(7, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onSeek(String[] strArr, long j2) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(8, new Object[]{strArr, Long.valueOf(j2)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setVolume(String[] strArr, int i) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(9, new Object[]{strArr, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getVolume(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(10, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getPosition(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(11, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setMediaInfo(String[] strArr, MediaMetaData mediaMetaData) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(12, new Object[]{strArr, mediaMetaData}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getMediaInfo(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(13, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setChannel(String str, int i) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(14, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getChannel(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(17, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getPlayState(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(15, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onTimelineChange() throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(20).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public boolean getCollectAudio() throws RemoteException {
            Logger.i("MiPlayAudioService", "getCollectAudio.", new Object[0]);
            return MiPlayAudioService.this.queryActiveAudioSession();
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public List<MiPlayDevice> getDevices() throws RemoteException {
            MiPlayAudioService.this.k.clear();
            for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                MiPlayAudioService.this.k.add(MiPlayAudioService.this.mDeviceManager.parseClientDevice(miDevice));
            }
            return MiPlayAudioService.this.k;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onNext(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(23, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onPrev(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(24, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int onRefreshDeviceInfo() throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(29).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBtFrequency(String[] strArr, int i) throws RemoteException {
            Logger.i("miplay_time", "setBtFrequency type" + i, new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(31, new Object[]{strArr, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int getBtFrequency(String[] strArr) throws RemoteException {
            Logger.i("miplay_time", "getBtFrequency.", new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(32, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int play(String[] strArr, String str, int i) throws RemoteException {
            Logger.i("MiPlayAudioService", "play:" + i, new Object[0]);
            MiPlayAudioService.this.U = 1;
            Logger.i("MiPlayAudioService", "---------play------ mMultiMirrorstatus:" + MiPlayAudioService.this.U, new Object[0]);
            StatsUtils.getInstance().setRefChannel(i);
            MiPlayAudioService.this.Y.obtainMessage(4, new Object[]{strArr, str}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBoxPause(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(41, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int setBoxResume(String[] strArr) throws RemoteException {
            MiPlayAudioService.this.Y.obtainMessage(42, strArr).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public int stopUwbDiscovery() throws RemoteException {
            Logger.i("MiPlayAudioService", "stopUwbDiscovery", new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(46).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public List<MiPlayDeviceControlCenter> getStereoDevices(String str) throws RemoteException {
            MiPlayAudioService.this.l.clear();
            Logger.i("MiPlayAudioService", "getStereoDevices groupid:" + str, new Object[0]);
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.queryDeviceFromGroupId(str)) {
                MiPlayAudioService.this.l.add(MiPlayAudioService.this.mDeviceManager.parseClientDeviceControlCenter(miDevice));
            }
            return MiPlayAudioService.this.l;
        }

        @Override // com.xiaomi.miplay.audioclient.IMiPlayClient
        public MediaMetaData getLocalMediaInfo() throws RemoteException {
            if (MiPlayAudioService.this.q.getTopActiveSessionRecord() == null) {
                Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                return new MediaMetaData();
            }
            com.xiaomi.miplay.mylibrary.session.data.MediaMetaData mediaMetaData = MiPlayAudioService.this.q.getTopActiveSessionRecord().getAudioMediaController().getMediaMetaData();
            MetaInfoManager metaInfoManager = MiPlayAudioService.this.m;
            String packageName = MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName();
            MiPlayAudioService miPlayAudioService = MiPlayAudioService.this;
            MediaMetaData parseClientDevice = metaInfoManager.parseClientDevice(mediaMetaData, -1, packageName, miPlayAudioService.getDeviceState(miPlayAudioService.getLocalPlayState()));
            Logger.i("MiPlayAudioService", "getLocalMediaInfo:" + parseClientDevice.toString(), new Object[0]);
            return parseClientDevice;
        }
    };
    private MiPlayClientCallback aa = new MiPlayClientCallback() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.11
        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onInitSuccess() {
            Logger.i("MiPlayAudioService", "onInitSuccess size " + MiPlayAudioService.this.c.size(), new Object[0]);
            if (MiPlayAudioService.this.c != null) {
                try {
                    for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                        ((MiPlayClientInfo) entry.getValue()).b.onInitSuccess();
                        Logger.i("miplay_time", "miplay init end", new Object[0]);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (MiPlayAudioService.this.O != null) {
                MiPlayAudioService.this.O.onInitSuccess();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onInitError() {
            if (MiPlayAudioService.this.c != null) {
                try {
                    for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                        ((MiPlayClientInfo) entry.getValue()).b.onInitError();
                        Logger.i("miplay_time", "miplay  onInitError", new Object[0]);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                MiPlayAudioService.this.c.clear();
            }
            if (MiPlayAudioService.this.O != null) {
                MiPlayAudioService.this.O.onInitError();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onDeviceFound(MiDevice miDevice) {
            MiPlayAudioService.this.Y.obtainMessage(33, miDevice).sendToTarget();
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onDeviceUpdate(MiDevice miDevice) {
            Logger.d("MiPlayAudioService", "onDeviceUpdate: " + miDevice.toString(), new Object[0]);
            MiPlayAudioService.this.Q.obtainMessage(34, miDevice).sendToTarget();
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onDeviceLost(String str) {
            Logger.d("MiPlayAudioService", "onDeviceLost " + Constant.getInstance().convertMac(str), new Object[0]);
            if (MiPlayAudioService.this.O != null) {
                MiPlayAudioService.this.O.onDeviceLost(str);
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onUnBinderIdm() {
            Logger.i("MiPlayAudioService", "onUnBinderIdm.", new Object[0]);
            if (MiPlayAudioService.this.b != null) {
                MiPlayAudioService.this.b.unInit(false);
                Logger.i("MiPlayAudioService", "again init idm", new Object[0]);
                MiPlayAudioService.this.b.init(MiPlayAudioService.this.aa, "");
                return;
            }
            Logger.i("MiPlayAudioService", "mClient is null", new Object[0]);
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onVerifySameAccountSuccess(String str) {
            Logger.d("MiPlayAudioService", "onVerifySameAccountSuccess:" + str, new Object[0]);
            try {
                for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                    if (TextUtils.equals(miDevice.getMac(), str)) {
                        MiPlayAudioService.this.b(str, 1);
                        miDevice.setVerifySameAccount(true);
                        ActiveSessionRecord topActiveSessionRecord = MiPlayAudioService.this.q.getTopActiveSessionRecord();
                        if (topActiveSessionRecord == null) {
                            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                            return;
                        }
                        MediaMetaData parseClientDevice = MiPlayAudioService.this.m.parseClientDevice(topActiveSessionRecord.getAudioMediaController().getMediaMetaData(), miDevice.getDeviceType(), MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName(), MiPlayAudioService.this.getDeviceState(MiPlayAudioService.this.getLocalPlayState()));
                        if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).setMediaInfo(MiPlayAudioService.this.m.mediaMetaDataToJson(parseClientDevice, miDevice));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.MiPlayClientCallback
        public void onVerifySameAccountFail(String str) {
            Logger.d("MiPlayAudioService", "onVerifySameAccountFail.", new Object[0]);
            MiPlayAudioService.this.b(str, 0);
        }
    };
    private MiplayMdns.MdnsCallback ab = new MiplayMdns.MdnsCallback() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.12
        @Override // com.xiaomi.miplay.mylibrary.MiplayMdns.MdnsCallback
        public void onServiceFound(int i, String str, int i2, String str2, String str3, int i3, byte[] bArr, byte[] bArr2) {
            Log.d("MiPlayAudioService", "mdns host:" + str2 + "server:" + str3);
            if (str3.indexOf("_miplay_audio._tcp.local.") >= 0) {
                String str4 = null;
                try {
                    str4 = new String(bArr, "utf-8");
                } catch (Exception e) {
                    Logger.i("MiPlayAudioService", "Exception:" + e, new Object[0]);
                }
                if (TextUtils.isEmpty(str4)) {
                    Logger.i("MiPlayAudioService", "appExt is null", new Object[0]);
                    return;
                }
                String[] split = str4.split("\n");
                if (split.length >= 2) {
                    String str5 = split[0];
                    MiDevice miDevice = new MiDevice();
                    miDevice.setIp(str);
                    miDevice.setIdhash(str);
                    miDevice.setMac(str5);
                    miDevice.setName(str2);
                    Log.d("MiPlayAudioService", "mdns ip:" + str + "mac:" + str5 + "name:" + str2);
                    if (i3 == -1) {
                        miDevice.setPort(8899);
                    } else {
                        miDevice.setPort(i3);
                    }
                    if (MiPlayAudioService.this.aa != null) {
                        MiPlayAudioService.this.aa.onDeviceFound(miDevice);
                        return;
                    }
                    return;
                }
                Log.d("MiPlayAudioService", "mdns add fail");
            }
        }
    };
    private RemoteDisplayAdmin.Listener ac = new RemoteDisplayAdmin.Listener() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.2
        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public String getNextPhoto(String str, boolean z) {
            return null;
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public String getPrevPhoto(String str, boolean z) {
            return null;
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onDisplayConnected(Surface surface, int i, int i2, int i3, int i4) {
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onDisplayDisconnected() {
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onDisplayError(int i) {
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayInfo(int i, int i2, int i3) {
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayPrepared(int i, int i2) {
            MiPlayAudioService.this.Y.obtainMessage(43, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayConnected(int i, String str) {
            MiPlayAudioService.this.Y.obtainMessage(35, new Object[]{Integer.valueOf(i), str}).sendToTarget();
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayDisConnected(int i) {
            Logger.i("MiPlayAudioService", "onMultiDisplayDisConnected.", new Object[0]);
            MiPlayAudioService.this.Y.obtainMessage(36, Integer.valueOf(i)).sendToTarget();
            Logger.i("MiPlayAudioService", "onMultiDisplayDisConnected end.", new Object[0]);
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayAudioCaptureInitDone(int i) {
            Logger.i("MiPlayAudioService", "onMultiDisplayAudioCaptureInitDone." + MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size(), new Object[0]);
            if (MiPlayAudioService.this.mDeviceManager.getConnectedMiDeviceList().size() == 1) {
                MiPlayAudioService.this.h();
            }
        }

        @Override // com.xiaomi.miplay.mylibrary.mirror.RemoteDisplayAdmin.Listener
        public void onMultiDisplayError(int i, int i2, int i3) {
            MiPlayAudioService.this.Y.obtainMessage(37, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)}).sendToTarget();
        }
    };
    private int ae = 0;
    public DeviceManager mDeviceManager = new DeviceManager();
    private MiPlayClientAPI b = new MiPlayClient(this, this.mDeviceManager);
    private HashMap<String, MiPlayClientInfo> c = new HashMap<>();

    /* loaded from: classes4.dex */
    public interface CaptureCallBack {
        void startCapture(int[] iArr, int i, int i2, int i3);

        void stopCapture(int[] iArr);
    }

    public int getDeviceState(int i) {
        if (i == 3) {
            return 2;
        }
        return i == 2 ? 3 : 0;
    }

    static /* synthetic */ int G(MiPlayAudioService miPlayAudioService) {
        int i = miPlayAudioService.ae;
        miPlayAudioService.ae = i + 1;
        return i;
    }

    public HashMap<String, MiPlayClientInfo> getMiPlayClientMap() {
        return this.c;
    }

    public MiplayMirrorService getMiplayMirrorService() {
        return this.P;
    }

    public long getPosition() {
        return this.W;
    }

    public String getActiveSsionPackageName() {
        return this.S;
    }

    public void setActiveSsionPackageName(String str) {
        this.S = str;
    }

    public void setMediaControl(MediaControlImpl mediaControlImpl) {
        this.X = mediaControlImpl;
    }

    /* loaded from: classes4.dex */
    public class a implements IBinder.DeathRecipient {
        private a() {
            MiPlayAudioService.this = r1;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e("MiPlayAudioService", "binderDied before");
            Logger.i("MiPlayAudioService", "mMiPlayClientMap:" + MiPlayAudioService.this.c.size(), new Object[0]);
            if (MiPlayAudioService.this.c != null && MiPlayAudioService.this.c.size() == 1) {
                for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                    Logger.i("MiPlayAudioService", "package:" + ((MiPlayClientInfo) entry.getValue()).a, new Object[0]);
                    if (((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_XIAOAI) || ((MiPlayClientInfo) entry.getValue()).a.equals("com.xiaomi.smarthome") || ((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_SYSTEMUI) || ((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_NEARFIELD) || ((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_VIDEO_AUDIO_CIRCULATION)) {
                        MiPlayAudioService.this.c.remove(((MiPlayClientInfo) entry.getValue()).a);
                        if (MiPlayAudioService.this.c.isEmpty()) {
                            MiPlayAudioService.this.b.unInit(true);
                            if (MiPlayAudioService.this.L) {
                                MiPlayAudioService.K.free();
                            }
                            MiPlayAudioService.this.b();
                        }
                    }
                }
                MiPlayAudioService.this.releaseClient();
                Log.e("MiPlayAudioService", "binderDied after");
            }
        }
    }

    public void releaseClient() {
        Logger.i("MiPlayAudioService", "releaseClient.", new Object[0]);
        HashMap<String, MiPlayClientInfo> hashMap = this.c;
        if (hashMap != null) {
            for (Map.Entry<String, MiPlayClientInfo> entry : hashMap.entrySet()) {
                if (entry.getValue().b != null) {
                    entry.getValue().b.asBinder().unlinkToDeath(this.E, 0);
                    Logger.i("MiPlayAudioService", "releaseClient finish", new Object[0]);
                }
            }
        }
    }

    public void b() {
        Logger.i("MiPlayAudioService", "closeDevice.", new Object[0]);
        for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
            if (this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                return;
            } else {
                this.cmdSessionControlMap.get(miDevice.getMac()).closeDevice();
                this.cmdSessionControlMap.get(miDevice.getMac()).closeCmdSession(miDevice.getMac());
            }
        }
        stopMirror();
    }

    /* loaded from: classes4.dex */
    private class b extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private b(Looper looper) {
            super(looper);
            MiPlayAudioService.this = r1;
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            if (message.what != 34) {
                Log.i("MiPlayAudioService", "unknown msg!");
                return;
            }
            Logger.i("MiPlayAudioService", "SmHandler rev msg_cmd_ondeviceupdate cmd", new Object[0]);
            MiPlayAudioService.this.deviceUpdateIDM((MiDevice) message.obj);
        }
    }

    public synchronized void a(String[] strArr) {
        Logger.i("MiPlayAudioService", "getDevices", new Object[0]);
        this.n.clear();
        if (!(this.mDeviceManager.getMiDeviceList() == null || this.mDeviceManager.getMiDeviceList().size() == 0)) {
            for (String str : strArr) {
                for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
                    if (TextUtils.equals(miDevice.getUuid(), str)) {
                        this.n.add(miDevice);
                    }
                }
            }
            return;
        }
        Logger.i("MiPlayAudioService", "mMiDeviceList is null", new Object[0]);
    }

    public boolean b(String[] strArr) {
        for (String str : strArr) {
            if (TextUtils.equals(str, DataModel.LOCAL_DEVICE_ID)) {
                Logger.i("MiPlayAudioService", DataModel.LOCAL_DEVICE_ID, new Object[0]);
                return true;
            }
        }
        return false;
    }

    private synchronized void c(String[] strArr) {
        Logger.i("MiPlayAudioService", "getDevices from devicemac", new Object[0]);
        this.n.clear();
        if (!(this.mDeviceManager.getMiDeviceList() == null || this.mDeviceManager.getMiDeviceList().size() == 0)) {
            for (String str : strArr) {
                for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
                    if (TextUtils.equals(miDevice.getMac(), str)) {
                        this.n.add(miDevice);
                    }
                }
            }
            return;
        }
        Logger.i("MiPlayAudioService", "mMiDeviceList is null", new Object[0]);
    }

    public static void add(CaptureCallBack captureCallBack) {
        H = captureCallBack;
    }

    public MiPlayAudioService() {
        Logger.i("MiPlayAudioService", "MiPlayAudioService structure", new Object[0]);
        UpdatePositonPool.getInstance().setmDeviceManager(this.mDeviceManager);
        if (this.L) {
            K = new MiplayMdns();
            K.setMdnsCallback(this.ab);
        }
    }

    private void c() {
        String string = getResources().getString(R.string.service_name);
        this.i = (NotificationManager) getSystemService("notification");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(string).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.launcher_icon_newhome)).setSmallIcon(R.drawable.launcher_icon_newhome).setContentTitle("全场景音乐服务").setContentText("全场景音乐服务运行中").setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 26) {
            this.i.createNotificationChannel(new NotificationChannel("hide_foreground", Constant.PACKAGENAME_SYSTEMUI, 2));
            builder.setChannelId("hide_foreground");
        }
        Notification build = builder.build();
        build.defaults = 1;
        startForeground(j, build);
        Logger.i("MiPlayAudioService", "notifyNotification", new Object[0]);
    }

    @Override // android.app.Service
    public void onCreate() {
        Logger.setDebug(true);
        Logger.i("MiPlayAudioService", "onCreate MiPlayAudioService", new Object[0]);
        this.r = this;
        this.m = new MetaInfoManager(this.mDeviceManager, this);
        this.q = new ActiveAudioSessionManager(this.r, this);
        UpdatePositonPool.getInstance().setmActiveAudioSessionManager(this.q);
        this.C = new NetworkConnectChangedReceiver(this, this.mDeviceManager, this.Y);
        i();
        c();
        MiPlayAudioRecordSettingsHelper.closeMiPlayOptimize(this);
        OneTrackStatistics.init(this.r);
        ThreadPoolManager.getInstance().initThreadPool();
        this.R = new HandlerThread(getClass().getName());
        this.R.start();
        this.Q = new b(this.R.getLooper());
        this.T = new AudioManagerHelper(this);
        super.onCreate();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.i("MiPlayAudioService", "onBind: --- " + intent.getAction());
        if (TextUtils.equals(intent.getAction(), ACTION_MIPLAY_CLIENT)) {
            return this.Z.asBinder();
        }
        if (TextUtils.equals(intent.getAction(), "COM.XIAOMI.MIPLAY.ACTION.VIDEO_CIRCULATION_CLIENT_SERVICE")) {
            this.O = new MiplayVideoService(this, this.b, this.aa);
            return this.O.asBinder();
        } else if (!TextUtils.equals(intent.getAction(), "COM.XIAOMI.MIPLAY.ACTION.VIDEOSWITCHMIRROR_CLIENT_SERVICE")) {
            return this.V;
        } else {
            this.P = new MiplayMirrorService(this);
            return this.P.asBinder();
        }
    }

    /* loaded from: classes4.dex */
    public class LocalBinder extends Binder {
        public LocalBinder() {
            MiPlayAudioService.this = r1;
        }

        public MiPlayAudioService getService() {
            return MiPlayAudioService.this;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.i("MiPlayAudioService", "onDestroy", new Object[0]);
        cancleDownTimer();
        d();
        this.q.release();
        this.c.clear();
        j();
        ThreadPoolManager.getInstance().shutdownExecutor();
        UpdatePositonPool.getInstance().stopTimer();
        stopForeground(true);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.ad;
        if (scheduledThreadPoolExecutor != null) {
            scheduledThreadPoolExecutor.shutdown();
            this.ad = null;
        }
        super.onDestroy();
    }

    public void d() {
        Logger.i("MiPlayAudioService", "ReleaseResources.", new Object[0]);
        stopMirror();
        this.mDeviceManager.getVolumeMap().clear();
        if (this.mDeviceManager.getMiDeviceList() != null) {
            for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
                if (this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                    this.cmdSessionControlMap.get(miDevice.getMac()).closeCmdSession(miDevice.getMac());
                    Logger.i("MiPlayAudioService", "closeDevice", new Object[0]);
                } else {
                    Logger.i("MiPlayAudioService", "cmdSessionControlMap.get(deviceInfo.getMac())==null", new Object[0]);
                }
            }
            this.mDeviceManager.getMiDeviceList().clear();
            Logger.i("MiPlayAudioService", "MiDeviceList:" + this.mDeviceManager.getMiDeviceList().size(), new Object[0]);
            this.cmdSessionControlMap.clear();
        }
        this.k.clear();
        this.mDeviceManager.getConnectedMiDeviceList().clear();
        this.f.clear();
        this.d.clear();
        this.e.clear();
        this.mDeviceManager.getPlayStatusMap().clear();
        this.mDeviceManager.getMiDeviceMap().clear();
        this.U = 0;
        Logger.i("MiPlayAudioService", "ReleaseResources end.", new Object[0]);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Logger.i("MiPlayAudioService", "onUnbind...", new Object[0]);
        return super.onUnbind(intent);
    }

    public boolean queryActiveAudioSession() {
        ActiveSessionRecord topActiveSessionRecord = this.q.getTopActiveSessionRecord();
        Logger.i("MiPlayAudioService", "ActiveSessionRecord:" + topActiveSessionRecord, new Object[0]);
        return topActiveSessionRecord != null;
    }

    @Override // com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager.ActiveAudioSessionCallback
    public void onTopActiveSessionChange(@Nullable ActiveSessionRecord activeSessionRecord) {
        if (activeSessionRecord == null) {
            if (!isStartTimered()) {
                synchronized (this.x) {
                    runDownTimer();
                }
            }
            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
            final MediaMetaData mediaMetaData = new MediaMetaData();
            ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.7
                @Override // java.lang.Runnable
                public void run() {
                    for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                        Logger.i("MiPlayAudioService", "canrevctrl:" + miDevice.getCanRevCtrl() + " verifysameaccount:" + miDevice.isVerifySameAccount(), new Object[0]);
                        if (miDevice.getDeviceConnectState() == 1 || (TextUtils.equals(miDevice.getCanRevCtrl(), "1") && miDevice.isVerifySameAccount())) {
                            Logger.i("MiPlayAudioService", "clear box meta", new Object[0]);
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                                Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                                return;
                            }
                            MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).setMediaInfo(MiPlayAudioService.this.m.mediaMetaDataToJson(mediaMetaData, miDevice));
                            MiPlayAudioService.this.m.setMetadata(null);
                            if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) != null && miDevice.getDeviceConnectState() == 1) {
                                Logger.i("MiPlayAudioService", "pauseDevice.", new Object[0]);
                                MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).pauseDevice();
                            }
                        } else {
                            Logger.d("MiPlayAudioService", "device_no_connected:" + miDevice.toString(), new Object[0]);
                        }
                    }
                }
            });
            return;
        }
        Logger.i("MiPlayAudioService", "onTopActiveSessionChange, " + activeSessionRecord.getPackageName(), new Object[0]);
        setActiveSsionPackageName(activeSessionRecord.getPackageName());
        if (!StatsUtils.packageNameEquals(StatsUtils.getInstance().getRecordPackageName(), activeSessionRecord.getPackageName())) {
            StatsUtils.getInstance().setRefContent(activeSessionRecord.getPackageName());
            StatsUtils.getInstance().setPlaySource(this.mDeviceManager, this.cmdSessionControlMap);
            StatsUtils.getInstance().setRecordPackageName(activeSessionRecord.getPackageName());
        }
        int appUid = activeSessionRecord.getAppUid();
        if (!this.t.contains(Integer.valueOf(appUid))) {
            int[] iArr = new int[this.t.size()];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = this.t.valueAt(i).intValue();
            }
            this.t.clear();
            this.t.add(Integer.valueOf(appUid));
            Logger.i("MiPlayAudioService", "mInputUid:" + this.t.size() + "  indexMap:" + this.d.size(), new Object[0]);
            if (this.t.size() > 0 && this.d.size() > 0) {
                Logger.i("MiPlayAudioService", "Switch app", new Object[0]);
                a(iArr);
                a(activeSessionRecord);
                if (this.q.getTopActiveSessionRecord() != null) {
                    ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.8
                        @Override // java.lang.Runnable
                        public void run() {
                            Logger.i("MiPlayAudioService", "Switch app setMediaInfo start", new Object[0]);
                            for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                                ActiveSessionRecord topActiveSessionRecord = MiPlayAudioService.this.q.getTopActiveSessionRecord();
                                if (topActiveSessionRecord == null) {
                                    Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                                    return;
                                }
                                com.xiaomi.miplay.mylibrary.session.data.MediaMetaData mediaMetaData2 = topActiveSessionRecord.getAudioMediaController().getMediaMetaData();
                                Logger.i("MiPlayAudioService", "canrevctrl:" + miDevice.getCanRevCtrl() + " verifysameaccount:" + miDevice.isVerifySameAccount(), new Object[0]);
                                if (miDevice.getDeviceConnectState() == 1 || (TextUtils.equals(miDevice.getCanRevCtrl(), "1") && miDevice.isVerifySameAccount())) {
                                    MetaInfoManager metaInfoManager = MiPlayAudioService.this.m;
                                    int deviceType = miDevice.getDeviceType();
                                    String packageName = MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName();
                                    MiPlayAudioService miPlayAudioService = MiPlayAudioService.this;
                                    MediaMetaData parseClientDevice = metaInfoManager.parseClientDevice(mediaMetaData2, deviceType, packageName, miPlayAudioService.getDeviceState(miPlayAudioService.getLocalPlayState()));
                                    if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                                        MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).setMediaInfo(MiPlayAudioService.this.m.mediaMetaDataToJson(parseClientDevice, miDevice));
                                    }
                                    MiPlayAudioService.this.l(miDevice);
                                } else {
                                    Logger.d("MiPlayAudioService", "device_no_connected:" + miDevice.toString(), new Object[0]);
                                }
                            }
                            try {
                                for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                                    if (((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_VIDEO_AUDIO_CIRCULATION)) {
                                        ActiveSessionRecord topActiveSessionRecord2 = MiPlayAudioService.this.q.getTopActiveSessionRecord();
                                        if (topActiveSessionRecord2 == null) {
                                            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                                            return;
                                        }
                                        MediaMetaData parseClientDevice2 = MiPlayAudioService.this.m.parseClientDevice(topActiveSessionRecord2.getAudioMediaController().getMediaMetaData(), -1, MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName(), MiPlayAudioService.this.getDeviceState(MiPlayAudioService.this.getLocalPlayState()));
                                        Logger.i("MiPlayAudioService", "Switch app:" + parseClientDevice2.toString(), new Object[0]);
                                        ((MiPlayClientInfo) entry.getValue()).b.onLocalMediaInfoChange(parseClientDevice2);
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            Logger.i("MiPlayAudioService", "Switch app setMediaInfo end", new Object[0]);
                        }
                    });
                }
            } else if (activeSessionRecord != null) {
                this.F = activeSessionRecord;
                Logger.i("MiPlayAudioService", "replace oldRecord 2", new Object[0]);
            }
        }
    }

    private void a(int[] iArr) {
        Logger.i("MiPlayAudioService", "stopCapture...uid:" + Arrays.toString(iArr), new Object[0]);
        this.G = true;
        if (iArr == null) {
            Logger.i("MiPlayAudioService", "uidList is null object", new Object[0]);
            return;
        }
        for (int i : iArr) {
            Logger.i("MiPlayAudioService", "uid:" + i, new Object[0]);
        }
        CaptureCallBack captureCallBack = H;
        if (captureCallBack != null) {
            captureCallBack.stopCapture(iArr);
        }
    }

    public void a(ActiveSessionRecord activeSessionRecord) {
        int[] iArr = new int[this.t.size()];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = this.t.valueAt(i).intValue();
        }
        if (iArr.length == 0) {
            iArr = new int[]{0};
        }
        Logger.i("MiPlayAudioService", "startCapture...uid:" + this.t.toString(), new Object[0]);
        if (!this.G || H == null) {
            this.p = new AudioRecodeBean(iArr, OpusUtil.SAMPLE_RATE, 12, 2);
            return;
        }
        PhoneAudioHelper.getInstance(this.r).setStreamMute(true);
        Logger.i("MiPlayAudioService", "set mute", new Object[0]);
        ActiveSessionRecord activeSessionRecord2 = this.F;
        if (activeSessionRecord2 != null) {
            if (TextUtils.equals(Constant.MUSIC_QQ, activeSessionRecord2.getPackageName())) {
                this.F.getAudioMediaController().pause();
            } else {
                this.F.getMediaController().getTransportControls().pause();
            }
            Logger.d("MiPlayAudioService", "oldRecordPause:" + this.F.getPackageName(), new Object[0]);
        }
        if (activeSessionRecord != null) {
            this.F = activeSessionRecord;
            Logger.i("MiPlayAudioService", "replace oldRecord 1", new Object[0]);
        }
        H.startCapture(iArr, OpusUtil.SAMPLE_RATE, 12, 2);
        this.G = false;
        this.Y.postDelayed(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.9
            @Override // java.lang.Runnable
            public void run() {
                Logger.i("MiPlayAudioService", "recovery mute", new Object[0]);
                PhoneAudioHelper.getInstance(MiPlayAudioService.this.r).setStreamMute(false);
            }
        }, 1000L);
    }

    @Override // com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager.ActiveAudioSessionCallback
    public void onActiveSessionChange(@NonNull ActiveSessionRecordStack activeSessionRecordStack) {
        Logger.i("MiPlayAudioService", "onActiveSessionChange:", new Object[0]);
        SessionStateDispatcher.getInstance().dispatchOnActiveSessionChanged(activeSessionRecordStack);
    }

    @Override // com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager.ActiveAudioSessionCallback
    public void onTopActivePlaybackStateChange(int i) {
        SessionStateDispatcher.getInstance().dispatchOnPlaybackStateChanged(i);
    }

    @Override // com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager.ActiveAudioSessionCallback
    public void dispatchMetaChange(final com.xiaomi.miplay.mylibrary.session.data.MediaMetaData mediaMetaData, String str) {
        synchronized (this.v) {
            Logger.i("MiPlayAudioService", "dispatchMetaChange:" + str + "  ActiveSsionPackageName:" + getActiveSsionPackageName(), new Object[0]);
            if (!TextUtils.equals(getActiveSsionPackageName(), str)) {
                Logger.i("MiPlayAudioService", "non-current activession metachange", new Object[0]);
            } else if (mediaMetaData == null) {
                Logger.i("MiPlayAudioService", "metadata is null", new Object[0]);
            } else {
                ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.10
                    @Override // java.lang.Runnable
                    public void run() {
                        String str2;
                        Logger.i("MiPlayAudioService", "dispatchMetaChange:" + mediaMetaData.toString(), new Object[0]);
                        for (MiDevice miDevice : MiPlayAudioService.this.mDeviceManager.getMiDeviceList()) {
                            if ((miDevice.getDeviceConnectState() == 1 && miDevice.getMirrorMode() == 1) || (miDevice.getDeviceType() == 16 && miDevice.isVerifySameAccount())) {
                                if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                                    Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                                    return;
                                }
                                if (MiPlayAudioService.this.q.getTopActiveSessionRecord() == null) {
                                    Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                                    str2 = "";
                                } else {
                                    str2 = MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName();
                                }
                                MetaInfoManager metaInfoManager = MiPlayAudioService.this.m;
                                com.xiaomi.miplay.mylibrary.session.data.MediaMetaData mediaMetaData2 = mediaMetaData;
                                int deviceType = miDevice.getDeviceType();
                                MiPlayAudioService miPlayAudioService = MiPlayAudioService.this;
                                MediaMetaData parseClientDevice = metaInfoManager.parseClientDevice(mediaMetaData2, deviceType, str2, miPlayAudioService.getDeviceState(miPlayAudioService.getLocalPlayState()));
                                if (MiPlayAudioService.this.mDeviceManager.getVolumeMap().get(miDevice.getMac()) != null) {
                                    parseClientDevice.setVolume(MiPlayAudioService.this.mDeviceManager.getVolumeMap().get(miDevice.getMac()).intValue());
                                }
                                if (MiPlayAudioService.this.mDeviceManager.getPlayStatusMap().get(miDevice.getMac()) != null) {
                                    parseClientDevice.setStatus(MiPlayAudioService.this.mDeviceManager.getPlayStatusMap().get(miDevice.getMac()).intValue());
                                }
                                Logger.i("MiPlayAudioService", "setMediaInfo = " + parseClientDevice.toString(), new Object[0]);
                                MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()).setMediaInfo(MiPlayAudioService.this.m.mediaMetaDataToJson(parseClientDevice, miDevice));
                            }
                        }
                        MiPlayAudioService.this.m.setMetadata(mediaMetaData);
                        try {
                            for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                                if (((MiPlayClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_VIDEO_AUDIO_CIRCULATION)) {
                                    if (MiPlayAudioService.this.q.getTopActiveSessionRecord() == null) {
                                        Logger.i("MiPlayAudioService", "record is null", new Object[0]);
                                        return;
                                    }
                                    MediaMetaData parseClientDevice2 = MiPlayAudioService.this.m.parseClientDevice(MiPlayAudioService.this.q.getTopActiveSessionRecord().getAudioMediaController().getMediaMetaData(), -1, MiPlayAudioService.this.q.getTopActiveSessionRecord().getPackageName(), MiPlayAudioService.this.getDeviceState(MiPlayAudioService.this.getLocalPlayState()));
                                    Logger.i("MiPlayAudioService", "dispatchMetaChange:" + parseClientDevice2.toString(), new Object[0]);
                                    ((MiPlayClientInfo) entry.getValue()).b.onLocalMediaInfoChange(parseClientDevice2);
                                }
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override // com.xiaomi.miplay.mylibrary.session.ActiveAudioSessionManager.ActiveAudioSessionCallback
    public void dispatchPlaybackStateChange(int i) {
        Logger.i("MiPlayAudioService", "dispatchPlaybackStateChange:" + i, new Object[0]);
        if (isStartTimered()) {
            if (i == 3) {
                synchronized (this.x) {
                    cancleDownTimer();
                }
            }
        } else if (i <= 2) {
            synchronized (this.x) {
                runDownTimer();
            }
        }
        if (i == 3) {
            for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
                if (miDevice.getDeviceConnectState() == 1 && miDevice.getMirrorMode() == 1) {
                    if (this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                        Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                        return;
                    } else {
                        Logger.i("MiPlayAudioService", "resumeDevice.", new Object[0]);
                        this.cmdSessionControlMap.get(miDevice.getMac()).resumeDevice();
                    }
                }
                Logger.i("MiPlayAudioService", "canrevctrl:" + miDevice.getCanRevCtrl() + " verifysameaccount:" + miDevice.isVerifySameAccount(), new Object[0]);
                if (TextUtils.equals(miDevice.getCanRevCtrl(), "1") && miDevice.isVerifySameAccount()) {
                    Logger.i("MiPlayAudioService", "speaker_with_screen setMediaState", new Object[0]);
                    if (this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
                        Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                        return;
                    }
                    this.cmdSessionControlMap.get(miDevice.getMac()).setMediaState(getDeviceState(i));
                }
            }
        }
    }

    public void startMirror(RemoteDisplayAdmin.Listener listener, String str, String str2, Map<Integer, String> map) {
        Logger.i("MiPlayAudioService", "startMirror.", new Object[0]);
        if (TextUtils.isEmpty(str)) {
            Logger.i("MiPlayAudioService", "port is null", new Object[0]);
        } else if (this.a != null) {
            Logger.i("MiPlayAudioService", "startMirror already start.", new Object[0]);
            Logger.i("MiPlayAudioService", "multiPort substring after:" + str, new Object[0]);
            List asList = Arrays.asList(str.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
            Logger.i("MiPlayAudioService", "resultList:" + asList.size(), new Object[0]);
            for (int i = 0; i < asList.size(); i++) {
                Logger.i("MiPlayAudioService", "val:" + ((String) asList.get(i)), new Object[0]);
                if (!TextUtils.isEmpty((CharSequence) asList.get(i))) {
                    this.a.addMirror((String) asList.get(i), this.f);
                } else {
                    Logger.i("MiPlayAudioService", "port is empty!", new Object[0]);
                }
            }
        } else {
            this.a = new MirrorServer(this, listener);
            if (this.p != null) {
                Logger.i("MiPlayAudioService", "setAudioRecordParams.", new Object[0]);
                this.a.setAudioRecordParams(this.p.getUidList(), this.p.getSampleRate(), this.p.getChannel(), this.p.getAudioBits());
            } else {
                Logger.i("MiPlayAudioService", "audioRecodeBean is null object", new Object[0]);
            }
            this.a.start(DeviceUtil.getWifiIpAddress(this), 7236, str, str2, map);
            Logger.i("MiPlayAudioService", "startMirror end.", new Object[0]);
        }
    }

    public void stopMirror() {
        Logger.i("MiPlayAudioService", "stopMirror start.", new Object[0]);
        if (this.a != null) {
            Logger.i("MiPlayAudioService", "stopMirror ing.", new Object[0]);
            this.a.stop();
            this.a = null;
        }
        MiPlayAudioRecordSettingsHelper.closeMiPlayOptimize(this.r);
        Logger.i("MiPlayAudioService", "stopMirror end.", new Object[0]);
    }

    public void pauseMirror() {
        MirrorServer mirrorServer = this.a;
        if (mirrorServer != null) {
            mirrorServer.pause(1);
        }
    }

    public void resumeMirror() {
        MirrorServer mirrorServer = this.a;
        if (mirrorServer != null) {
            mirrorServer.resume(2);
        }
    }

    private synchronized void a(final MiDevice miDevice) {
        CmdSessionControl cmdSessionControl = new CmdSessionControl(miDevice);
        cmdSessionControl.addCmdClientCallback(new CmdClientCallback() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.13
            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onSuccess() {
                MiPlayAudioService.this.Y.obtainMessage(38, miDevice).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onError() {
                MiPlayAudioService.this.Y.obtainMessage(25, miDevice).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPlayStateAck(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "onPlayStateAck  state::" + i, new Object[0]);
                MiPlayAudioService.this.a(miDevice2, i);
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPositionAck(MiDevice miDevice2, long j2) {
                Logger.i("MiPlayAudioService", "onPositionAck  position::" + j2, new Object[0]);
                if (miDevice2.getMirrorMode() == 3) {
                    if (MiPlayAudioService.this.P != null) {
                        MiPlayAudioService.this.b(j2);
                    }
                    if (MiPlayAudioService.this.O != null) {
                        MiPlayAudioService.this.O.positionAck(miDevice2.getUuid(), j2);
                        return;
                    }
                    return;
                }
                MiPlayAudioService.this.a(miDevice2.getUuid(), j2);
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onChannelsAck(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "layout::" + i, new Object[0]);
                HashMap hashMap = new HashMap();
                hashMap.put(miDevice2.getMac(), Integer.valueOf(i));
                try {
                    for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                        ((MiPlayClientInfo) entry.getValue()).b.onChannelsAck(hashMap);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onMediaInfoAck(MiDevice miDevice2, byte[] bArr) {
                Logger.i("MiPlayAudioService", "onMediaInfoAck.", new Object[0]);
                MiPlayAudioService.this.Y.obtainMessage(45, new Object[]{miDevice2, bArr}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onVolumeAck(MiDevice miDevice2, int i) {
                MiPlayAudioService.this.Y.obtainMessage(49, new Object[]{miDevice2, Integer.valueOf(i)}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPlayStateNotify(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "onPlayStateNotify  state::" + i + "  device name:" + miDevice2.getName(), new Object[0]);
                MiPlayAudioService.this.Y.obtainMessage(50, new Object[]{miDevice2, Integer.valueOf(i)}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onMediaInfoNotify(MiDevice miDevice2, byte[] bArr) {
                Logger.i("MiPlayAudioService", "onMediaInfoNotify.", new Object[0]);
                MiPlayAudioService.this.Y.obtainMessage(44, new Object[]{miDevice2, bArr}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onVolumeNotify(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "onVolumeNotify  volume::" + i, new Object[0]);
                Logger.d("MiPlayAudioService", miDevice2.toString(), new Object[0]);
                MiPlayAudioService.this.f(miDevice2, i);
                MiPlayAudioService.this.mDeviceManager.getVolumeMap().put(miDevice2.getMac(), Integer.valueOf(i));
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onBufferStateNotify(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "onBufferStateNotify  state::" + i, new Object[0]);
                if (miDevice2.getMirrorMode() != 3) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(miDevice2.getUuid(), Integer.valueOf(i));
                    try {
                        for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                            ((MiPlayClientInfo) entry.getValue()).b.onBufferStateChange(hashMap);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else if (MiPlayAudioService.this.O != null) {
                    MiPlayAudioService.this.O.bufferStateChange(miDevice2.getUuid(), i);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDeviceInfo(MiDevice miDevice2, byte[] bArr) {
                MiPlayAudioService.this.Y.obtainMessage(26, new Object[]{miDevice2, bArr}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDeviceNetworkChanged(MiDevice miDevice2, byte[] bArr) {
                MiPlayAudioService.this.Y.obtainMessage(28, new Object[]{miDevice2, bArr}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDeviceInfoChanged(MiDevice miDevice2, byte[] bArr) {
                MiPlayAudioService.this.Y.obtainMessage(30, new Object[]{miDevice2, bArr}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onMirrorModeAck(MiDevice miDevice2, int i) {
                MiPlayAudioService.this.Y.obtainMessage(39, new Object[]{miDevice2, Integer.valueOf(i)}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onMirrorModeNotify(MiDevice miDevice2, int i) {
                MiPlayAudioService.this.Y.obtainMessage(40, new Object[]{miDevice2, Integer.valueOf(i)}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onNextNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onNextNotify.", new Object[0]);
                Logger.i("MiPlayAudioService", "deviceConnectState:" + miDevice2.getDeviceConnectState(), new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.e();
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPrevNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onPrevNotify.", new Object[0]);
                Logger.i("MiPlayAudioService", "deviceConnectState:" + miDevice2.getDeviceConnectState(), new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.f();
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPauseNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onPauseNotify.", new Object[0]);
                Logger.d("MiPlayAudioService", "device:" + miDevice2.getMac() + "  deviceConnectState:" + miDevice2.getDeviceConnectState(), new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.LocalPause();
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onResumeNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onResumeNotify.", new Object[0]);
                Logger.d("MiPlayAudioService", "device:" + miDevice2.getMac() + " deviceConnectState:" + miDevice2.getDeviceConnectState(), new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.g();
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPhysicalBtVolumeNotify(MiDevice miDevice2, int i) {
                MiPlayAudioService.this.Y.obtainMessage(48, new Object[]{miDevice2, Integer.valueOf(i)}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDisconnectNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onDisconnectNotify.", new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.c(miDevice2);
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onBtFrequencyACK(MiDevice miDevice2, int i) {
                Logger.i("MiPlayAudioService", "onBtFrequencyACK::" + i, new Object[0]);
                HashMap hashMap = new HashMap();
                hashMap.put(miDevice2.getUuid(), Integer.valueOf(i));
                try {
                    for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                        ((MiPlayClientInfo) entry.getValue()).b.onBtFrequencyACK(hashMap);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onBeSeized(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onBeSeized:", new Object[0]);
                Logger.d("MiPlayAudioService", miDevice2.toString(), new Object[0]);
                if (miDevice2.getMirrorMode() != 3 || MiPlayAudioService.this.P == null) {
                    try {
                        for (Map.Entry entry : MiPlayAudioService.this.c.entrySet()) {
                            ((MiPlayClientInfo) entry.getValue()).b.onBeSeized(miDevice2.getUuid());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    MiPlayAudioService.this.P.onBeSeized();
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDeviceStartPlaying(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onDeviceStartPlaying:" + Constant.getInstance().convertMac(miDevice2.getMac()), new Object[0]);
                int localPlayState = MiPlayAudioService.this.getLocalPlayState();
                Logger.i("MiPlayAudioService", "playState:" + localPlayState, new Object[0]);
                if (localPlayState == 3) {
                    StatsUtils.getInstance().stopTimer(2, miDevice2.getDeviceType(), MiPlayAudioService.this.mDeviceManager);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDisConnectOneNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onDisConnectOneNotify:" + Constant.getInstance().convertMac(miDevice2.getMac()), new Object[0]);
                if (MiPlayAudioService.this.d.get(miDevice2.getMac()) == null) {
                    Logger.i("MiPlayAudioService", "indexMap non-existent", new Object[0]);
                } else if (MiPlayAudioService.this.Y != null) {
                    MiPlayAudioService.this.Y.obtainMessage(22, new Object[]{MiPlayAudioService.this.d.get(miDevice2.getMac()), miDevice2.getMac()}).sendToTarget();
                }
                if (MiPlayAudioService.this.d.size() == 1) {
                    MiPlayAudioService.this.LocalPause();
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onSeekNotify(MiDevice miDevice2, long j2) {
                Logger.i("MiPlayAudioService", "onSeekNotify：" + j2, new Object[0]);
                Logger.d("MiPlayAudioService", "device:" + miDevice2.getMac() + " deviceConnectState:" + miDevice2.getDeviceConnectState(), new Object[0]);
                if (miDevice2.getDeviceConnectState() != 0) {
                    if (miDevice2.getMirrorMode() != 1) {
                        Logger.i("MiPlayAudioService", "no Mobile streaming", new Object[0]);
                    } else {
                        MiPlayAudioService.this.a(j2);
                    }
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onDurationUpdated(MiDevice miDevice2, long j2) {
                Logger.i("MiPlayAudioService", "onDurationUpdated:" + miDevice2.getMirrorMode(), new Object[0]);
                if (miDevice2.getMirrorMode() == 3 && MiPlayAudioService.this.O != null) {
                    MiPlayAudioService.this.O.positionChange(miDevice2.getUuid(), j2);
                }
                if (miDevice2.getMirrorMode() == 3 && MiPlayAudioService.this.P != null) {
                    MiPlayAudioService.this.b(j2);
                    MiPlayAudioService.this.P.positionChange(j2);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onOpenAck(MiDevice miDevice2, int i) {
                Logger.d("MiPlayAudioService", "onOpenAck：" + miDevice2.toString(), new Object[0]);
                if (i == 0) {
                    return;
                }
                if (MiPlayAudioService.this.d.get(miDevice2.getMac()) != null) {
                    if (MiPlayAudioService.this.Y != null) {
                        MiPlayAudioService.this.Y.obtainMessage(22, new Object[]{MiPlayAudioService.this.d.get(miDevice2.getMac()), miDevice2.getMac()}).sendToTarget();
                    }
                    MiPlayAudioService.this.c(miDevice2, i);
                    StatsUtils.getInstance().connectStats("connect_fail", i, true, miDevice2.getDeviceType(), MiPlayAudioService.this.mDeviceManager);
                    return;
                }
                Logger.i("MiPlayAudioService", "indexMap non-existent", new Object[0]);
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onNotifyPropertiesInfo(MiDevice miDevice2, byte[] bArr) {
                Logger.i("MiPlayAudioService", "onNotifyPropertiesInfo.", new Object[0]);
                if (miDevice2.getMirrorMode() == 3 && MiPlayAudioService.this.P != null) {
                    MiPlayAudioService.this.P.onNotifyPropertiesInfo(bArr);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onSeekDoneNotify(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onSeekDoneNotify.", new Object[0]);
                if (miDevice2.getMirrorMode() == 3 && MiPlayAudioService.this.P != null) {
                    MiPlayAudioService.this.P.onSeekDoneNotify();
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onAudioOpenNotify(MiDevice miDevice2, int i) {
                Logger.d("MiPlayAudioService", "onAudioOpenNotify:" + i + "  uuid:" + miDevice2.getUuid(), new Object[0]);
                StringBuilder sb = new StringBuilder();
                sb.append("deviceId:");
                sb.append(miDevice2.getMac());
                Logger.d("MiPlayAudioService", sb.toString(), new Object[0]);
                PlayStateConfig playStateConfig = new PlayStateConfig();
                playStateConfig.setDeviceId(miDevice2.getMac());
                playStateConfig.setNeedPlayed(i == 1);
                Constant.getInstance().setPlayStateConfig(playStateConfig);
                StatsUtils.getInstance().getRef_channelMap().put("ref_channel", "relay_card");
                MiPlayAudioService.this.Y.obtainMessage(4, new Object[]{new String[]{miDevice2.getUuid()}, ""}).sendToTarget();
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPlayFinish(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onPlayFinish.", new Object[0]);
                if (miDevice.getMirrorMode() == 3 && MiPlayAudioService.this.P != null) {
                    MiPlayAudioService.this.P.onStoped(1);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onCirculateFail(MiDevice miDevice2, byte[] bArr) {
                Logger.i("MiPlayAudioService", "onCirculateFail.", new Object[0]);
                if (bArr == null) {
                    Logger.i("MiPlayAudioService", "errorInfo is null.", new Object[0]);
                }
                if (miDevice.getMirrorMode() == 3 && MiPlayAudioService.this.P != null) {
                    String str = new String(bArr, StandardCharsets.UTF_8);
                    Logger.i("MiPlayAudioService", "errorInfo:" + str, new Object[0]);
                    MiPlayAudioService.this.P.onCirculateFail(str);
                }
            }

            @Override // com.xiaomi.miplay.mylibrary.mirror.CmdClientCallback
            public void onPlayed(MiDevice miDevice2) {
                Logger.i("MiPlayAudioService", "onPlayed..", new Object[0]);
                if (MiPlayAudioService.this.P != null) {
                    MiPlayAudioService.this.P.onPlayed();
                    MiPlayAudioService.this.P.onCirculateModeChange(1);
                    MiPlayAudioService.this.cancleDownTimer();
                }
            }
        });
        this.cmdSessionControlMap.put(miDevice.getMac(), cmdSessionControl);
        Logger.d("MiPlayAudioService", "device.getIp():" + miDevice.getIp(), new Object[0]);
        cmdSessionControl.connectCmdSession(miDevice.getMac(), miDevice.getName(), miDevice.getIp(), miDevice.getPort());
        UpdatePositonPool.getInstance().setCmdSessionControlMap(this.cmdSessionControlMap);
    }

    public void mediaInfoAck(MiDevice miDevice, byte[] bArr) {
        Logger.i("MiPlayAudioService", "mediaInfoAck:", new Object[0]);
        MediaMetaData a2 = a(bArr, miDevice);
        if (miDevice.getMirrorMode() == 3) {
            MiplayVideoService miplayVideoService = this.O;
            if (miplayVideoService != null) {
                miplayVideoService.mediaInfoAck(miDevice.getUuid(), a2);
                return;
            }
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(miDevice.getUuid(), a2);
        try {
            for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                entry.getValue().b.onMediaInfoAck(hashMap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mediaInfoNotify(MiDevice miDevice, byte[] bArr) {
        Logger.i("MiPlayAudioService", "mediaInfoNotify：", new Object[0]);
        MediaMetaData a2 = a(bArr, miDevice);
        if (miDevice.getMirrorMode() == 3) {
            MiplayVideoService miplayVideoService = this.O;
            if (miplayVideoService != null) {
                miplayVideoService.mediaInfoChange(miDevice.getUuid(), a2);
                return;
            }
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(miDevice.getUuid(), a2);
        try {
            for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                entry.getValue().b.onMediaInfoChange(hashMap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void a(MiDevice miDevice, int i) {
        synchronized (this.u) {
            Logger.i("MiPlayAudioService", "playStateAck.", new Object[0]);
            if (miDevice.getMirrorMode() != 3) {
                HashMap hashMap = new HashMap();
                hashMap.put(miDevice.getUuid(), Integer.valueOf(i));
                try {
                    for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                        entry.getValue().b.onPlayStateAck(hashMap);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (this.O != null) {
                this.O.playStateAck(miDevice.getUuid(), i);
            }
            if (!TextUtils.equals(miDevice.getUuid(), DataModel.LOCAL_DEVICE_ID)) {
                Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MiDevice next = it.next();
                    if (next.getMac().equals(miDevice.getMac())) {
                        next.setStatus(i);
                        b(next);
                        Logger.i("MiPlayAudioService", "fix after state::" + next.getStatus(), new Object[0]);
                        break;
                    }
                }
                this.mDeviceManager.getPlayStatusMap().put(miDevice.getMac(), Integer.valueOf(i));
            }
        }
    }

    public void b(MiDevice miDevice, int i) {
        synchronized (this.u) {
            Logger.i("MiPlayAudioService", "playStateNotify.", new Object[0]);
            if (miDevice.getMirrorMode() == 3) {
                if (this.O != null) {
                    this.O.playStateChange(miDevice.getUuid(), i);
                }
                if (this.P != null) {
                    this.P.onPlayStateChange(i);
                }
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put(miDevice.getUuid(), Integer.valueOf(i));
                try {
                    for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                        entry.getValue().b.onPlayStateChange(hashMap);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MiDevice next = it.next();
                if (next.getMac().equals(miDevice.getMac())) {
                    next.setStatus(i);
                    b(next);
                    Logger.i("MiPlayAudioService", "fix after state::" + next.getStatus(), new Object[0]);
                    break;
                }
            }
            this.mDeviceManager.getPlayStatusMap().put(miDevice.getMac(), Integer.valueOf(i));
        }
    }

    private void b(MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "deviceUpdate.", new Object[0]);
        if (this.c == null) {
            Logger.i("MiPlayAudioService", "mMiPlayClientMap is null", new Object[0]);
            return;
        }
        MiPlayDevice parseClientDevice = this.mDeviceManager.parseClientDevice(miDevice);
        MiplayVideoService miplayVideoService = this.O;
        if (!(miplayVideoService == null || miplayVideoService.mMiPlayVideoClientMap == null)) {
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.O.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onDeviceUpdateControlCenter(this.mDeviceManager.parseClientDeviceControlCenter(miDevice));
                    Logger.i("MiPlayAudioService", "onDeviceUpdateControlCenter.", new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (!miDevice.isHaveSupportField() || (!TextUtils.isEmpty(miDevice.getSupportMpAbility()) && miDevice.getSupportMpAbility().contains("audio"))) {
            HashMap<String, MiPlayClientInfo> hashMap = this.c;
            if (hashMap != null) {
                try {
                    for (Map.Entry<String, MiPlayClientInfo> entry2 : hashMap.entrySet()) {
                        if (!entry2.getValue().a.equals(Constant.PACKAGENAME_XIAOAI)) {
                            Logger.i("MiPlayAudioService", "entry.getValue().id:" + entry2.getValue().a, new Object[0]);
                            entry2.getValue().b.onDeviceUpdateControlCenter(this.mDeviceManager.parseClientDeviceControlCenter(miDevice));
                            Logger.i("MiPlayAudioService", "onDeviceUpdateControlCenter.", new Object[0]);
                        } else {
                            entry2.getValue().b.onDeviceUpdate(parseClientDevice);
                        }
                    }
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            Logger.i("MiPlayAudioService", "no-report deviceUpdate,no-support mpa.", new Object[0]);
        }
    }

    public void c(MiDevice miDevice) {
        for (MiDevice miDevice2 : this.mDeviceManager.getConnectedMiDeviceList()) {
            if (this.cmdSessionControlMap.get(miDevice2.getMac()) == null) {
                Logger.i("MiPlayAudioService", "CmdSessionControl.closeDevice()' on a null object", new Object[0]);
                return;
            }
            this.cmdSessionControlMap.get(miDevice2.getMac()).closeDevice();
            HashMap hashMap = new HashMap();
            hashMap.put(miDevice.getUuid(), 1);
            try {
                for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                    entry.getValue().b.onDisconnectNotify(hashMap);
                    Logger.d("MiPlayAudioService", "NORMAL_DISCONNECT:" + miDevice2.getMac(), new Object[0]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.a != null) {
            stopMirror();
        } else {
            Logger.i("MiPlayAudioService", "mMirrotServer is null or index is null", new Object[0]);
        }
    }

    public String a(String str) {
        boolean z = false;
        for (MiDevice miDevice : this.n) {
            Logger.i("MiPlayAudioService", "GroupId:" + miDevice.getGroupId(), new Object[0]);
            if (!TextUtils.isEmpty(miDevice.getGroupId()) && !z) {
                z = true;
                if (this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                    SyncVolumeStackManager.getInstance().addGroupId(miDevice.getGroupId());
                    Logger.d("MiPlayAudioService", "obtain report device volume:" + miDevice.getGroupId() + "  deviceMac:" + miDevice.getMac(), new Object[0]);
                    this.cmdSessionControlMap.get(miDevice.getMac()).getVolume();
                }
            }
            if (!Iputils.checkIpPort(DeviceUtil.getWifiIpAddress(this), this.o)) {
                this.o += 2;
            }
            str = str + this.o + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
            this.f.put(Integer.valueOf(this.o), miDevice.getMac());
            Logger.d("MiPlayAudioService", "multiPort:" + str + ";   mirrorPort:" + this.o, new Object[0]);
            this.o = this.o + 2;
            StatsUtils.getInstance().connectStats("start_connect", -111, false, miDevice.getDeviceType(), this.mDeviceManager);
            StatsUtils.getInstance().activeMiplayStats(miDevice.getGroupId(), miDevice.getDeviceType(), this.mDeviceManager);
        }
        return str;
    }

    private String b(String str) {
        for (MiDevice miDevice : this.n) {
            if (!Iputils.checkIpPort(DeviceUtil.getWifiIpAddress(this), this.o)) {
                this.o += 2;
            }
            str = str + this.o + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
            this.f.put(Integer.valueOf(this.o), miDevice.getMac());
            Logger.d("MiPlayAudioService", "no group multiPort:" + str + ";   mirrorPort:" + this.o, new Object[0]);
            this.o = this.o + 2;
            StatsUtils.getInstance().connectStats("start_connect", -111, false, miDevice.getDeviceType(), this.mDeviceManager);
            StatsUtils.getInstance().activeMiplayStats(miDevice.getGroupId(), miDevice.getDeviceType(), this.mDeviceManager);
        }
        return str;
    }

    private MediaMetaData a(byte[] bArr, MiDevice miDevice) {
        int deviceKeyLen;
        int i;
        int i2;
        if (bArr == null) {
            Logger.i("MiPlayAudioService", "mediainfo==null", new Object[0]);
            return null;
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= bArr.length || (deviceKeyLen = ByteUtils.getDeviceKeyLen(bArr, i4)) == -1) {
                break;
            }
            int i5 = i4 + 1;
            String deviceValue = ByteUtils.getDeviceValue(bArr, deviceKeyLen, i5);
            if (TextUtils.isEmpty(deviceValue)) {
                break;
            }
            int i6 = i5 + deviceKeyLen;
            Logger.i("MiPlayAudioService", "key:" + deviceValue, new Object[0]);
            int deviceTypeLen = ByteUtils.getDeviceTypeLen(bArr, i6);
            Logger.i("MiPlayAudioService", "typeLen:" + deviceTypeLen, new Object[0]);
            int i7 = i6 + 1;
            if (deviceTypeLen == 20) {
                i = ByteUtils.getDeviceValueInfoLen(bArr, i7, 4);
                i2 = i7 + 4;
            } else {
                i = ByteUtils.getDeviceValueInfoLen(bArr, i7, 2);
                i2 = i7 + 2;
            }
            if (i == -1) {
                break;
            }
            Logger.i("MiPlayAudioService", "valueLen:" + i, new Object[0]);
            String deviceValue2 = ByteUtils.getDeviceValue(bArr, i, i2);
            i4 = i2 + i;
            if (!deviceValue.equals("mArt")) {
                Logger.i("MiPlayAudioService", "value :" + deviceValue2, new Object[0]);
            }
            if (!TextUtils.isEmpty(deviceValue)) {
                this.g.put(deviceValue, deviceValue2);
            }
            if (i4 == bArr.length) {
                Logger.i("MiPlayAudioService", "exit MediaInfo analysis", new Object[0]);
                break;
            }
            i3++;
        }
        if (this.g.size() == 0) {
            Logger.i("MiPlayAudioService", "analysisMediaInfo fail", new Object[0]);
            return null;
        }
        MediaMetaData mediaMetaData = new MediaMetaData();
        for (String str : this.g.keySet()) {
            if (str.equals("mArtist")) {
                mediaMetaData.setArtist(this.g.get(str));
            } else if (str.equals("mAlbum")) {
                mediaMetaData.setAlbum(this.g.get(str));
            } else if (str.equals("mTitle")) {
                mediaMetaData.setTitle(this.g.get(str));
            } else if (str.equals("mDuration")) {
                if (!TextUtils.isEmpty(this.g.get(str))) {
                    mediaMetaData.setDuration(Long.parseLong(this.g.get(str)));
                }
            } else if (str.equals("id")) {
                mediaMetaData.setId(this.g.get(str));
            } else if (str.equals("mCoverUrl")) {
                mediaMetaData.setCoverUrl(this.g.get(str));
            } else if (str.equals("status")) {
                if (!TextUtils.isEmpty(this.g.get(str))) {
                    mediaMetaData.setStatus(Integer.parseInt(this.g.get(str)));
                }
            } else if (str.equals(SchemaActivity.KEY_VOLUME)) {
                if (!TextUtils.isEmpty(this.g.get(str))) {
                    mediaMetaData.setVolume(Long.parseLong(this.g.get(str)));
                }
            } else if (str.equals("mArt")) {
                if (!TextUtils.isEmpty(this.g.get(str))) {
                    mediaMetaData.setArt(BitMapUtils.base64ToBitmap(this.g.get(str)));
                } else {
                    Logger.i("MiPlayAudioService", "metaData is null", new Object[0]);
                }
            } else if (!str.equals("mPosition")) {
                Logger.i("MiPlayAudioService", "invalid field:" + str, new Object[0]);
            } else if (!TextUtils.isEmpty(this.g.get(str))) {
                mediaMetaData.setPosition(Long.parseLong(this.g.get(str)));
            }
        }
        this.g.clear();
        return mediaMetaData;
    }

    private boolean a(Map<String, Integer> map, int i) {
        if (map == null) {
            Logger.i("MiPlayAudioService", "isIndexExists map == null", new Object[0]);
            return false;
        }
        for (Integer num : map.values()) {
            if (num.intValue() == i) {
                return true;
            }
        }
        return false;
    }

    private boolean c(String str) {
        Map<String, Integer> map = this.d;
        if (map == null) {
            Logger.i("MiPlayAudioService", "isIndexKeyExists indexMap == null", new Object[0]);
            return false;
        }
        for (String str2 : map.keySet()) {
            if (TextUtils.equals(str2, str)) {
                return true;
            }
        }
        return false;
    }

    private void d(MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "reportDeviceFound:", new Object[0]);
        Logger.d("MiPlayAudioService", "reportDeviceFound:" + miDevice.toString(), new Object[0]);
        if (this.c == null) {
            Logger.i("MiPlayAudioService", "mMiPlayClientMap is null", new Object[0]);
            return;
        }
        MiPlayDevice parseClientDevice = this.mDeviceManager.parseClientDevice(miDevice);
        MiplayVideoService miplayVideoService = this.O;
        if (!(miplayVideoService == null || miplayVideoService.mMiPlayVideoClientMap == null)) {
            try {
                for (Map.Entry<String, MiPlayVideoClientInfo> entry : this.O.mMiPlayVideoClientMap.entrySet()) {
                    entry.getValue().callback.onDeviceFoundControlCenter(this.mDeviceManager.parseClientDeviceControlCenter(miDevice));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.c != null) {
            try {
                if (!miDevice.isHaveSupportField() || (!TextUtils.isEmpty(miDevice.getSupportMpAbility()) && miDevice.getSupportMpAbility().contains("audio"))) {
                    for (Map.Entry<String, MiPlayClientInfo> entry2 : this.c.entrySet()) {
                        if (!entry2.getValue().a.equals(Constant.PACKAGENAME_XIAOAI)) {
                            Logger.i("MiPlayAudioService", "entry.getValue().id:" + entry2.getValue().a, new Object[0]);
                            entry2.getValue().b.onDeviceFoundControlCenter(this.mDeviceManager.parseClientDeviceControlCenter(miDevice));
                            Logger.d("MiPlayAudioService", "DeviceFoundControlCenter:" + miDevice.toString(), new Object[0]);
                        } else {
                            entry2.getValue().b.onDeviceFound(parseClientDevice);
                        }
                    }
                } else {
                    Logger.i("MiPlayAudioService", "no-report,no-support mpa.", new Object[0]);
                }
                if (!this.mDeviceManager.isDeviceExists(miDevice.getMac())) {
                    this.mDeviceManager.getMiDeviceList().add(miDevice);
                } else {
                    Logger.i("MiPlayAudioService", "device already exists..", new Object[0]);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void e(MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "handleDevice.", new Object[0]);
        if (!TextUtils.isEmpty(miDevice.getGroupId())) {
            DeviceManager deviceManager = this.mDeviceManager;
            if (deviceManager.isGroupIdExists(deviceManager.getMiDeviceList(), miDevice.getGroupId())) {
                String firstStereoUuid = this.mDeviceManager.getFirstStereoUuid(miDevice.getGroupId(), miDevice.getMac());
                if (TextUtils.isEmpty(firstStereoUuid)) {
                    Logger.i("MiPlayAudioService", "this Same device", new Object[0]);
                    d(miDevice);
                } else {
                    Logger.i("MiPlayAudioService", "groupId already exists,uuid:" + firstStereoUuid, new Object[0]);
                    miDevice.updateUuid(firstStereoUuid);
                    d(miDevice);
                }
            } else {
                d(miDevice);
            }
        } else {
            d(miDevice);
        }
        if (!TextUtils.isEmpty(miDevice.getGroupId()) && this.mDeviceManager.isGroupIdExistTwoSame(miDevice.getGroupId())) {
            DeviceManager deviceManager2 = this.mDeviceManager;
            if (deviceManager2.queryOtherConnectStateFromGroupId(deviceManager2.getMiDeviceList(), miDevice.getGroupId(), miDevice.getMac()) == 1) {
                DeviceManager deviceManager3 = this.mDeviceManager;
                if (deviceManager3.queryCurrentConnectStateFromGroupId(deviceManager3.getMiDeviceList(), miDevice.getGroupId(), miDevice.getMac()) != 1) {
                    Logger.d("MiPlayAudioService", "dynamic join stereo mac:" + miDevice.getMac(), new Object[0]);
                    e(miDevice.getMac());
                }
            }
        }
        if (this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
            Logger.i("MiPlayAudioService", "setDevice.", new Object[0]);
            this.cmdSessionControlMap.get(miDevice.getMac()).setDevice(miDevice);
            this.cmdSessionControlMap.get(miDevice.getMac()).getMirrorMode();
        }
    }

    public void e() {
        Logger.i("MiPlayAudioService", "LocalNext.", new Object[0]);
        ActiveSessionRecord topActiveSessionRecord = this.q.getTopActiveSessionRecord();
        if (topActiveSessionRecord == null) {
            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
        } else {
            topActiveSessionRecord.getAudioMediaController().next();
        }
    }

    public void f() {
        Logger.i("MiPlayAudioService", "LocalPrevious.", new Object[0]);
        ActiveSessionRecord topActiveSessionRecord = this.q.getTopActiveSessionRecord();
        if (topActiveSessionRecord == null) {
            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
        } else {
            topActiveSessionRecord.getAudioMediaController().previous();
        }
    }

    public void LocalPause() {
        Logger.i("MiPlayAudioService", "onMediaSessionPause.", new Object[0]);
        ActiveSessionRecord topActiveSessionRecord = this.q.getTopActiveSessionRecord();
        if (topActiveSessionRecord == null) {
            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
        } else {
            topActiveSessionRecord.getAudioMediaController().pause();
        }
    }

    public void g() {
        Logger.i("MiPlayAudioService", "LocalPlay.", new Object[0]);
        ActiveSessionRecord topActiveSessionRecord = this.q.getTopActiveSessionRecord();
        if (topActiveSessionRecord == null) {
            Logger.i("MiPlayAudioService", "record is null", new Object[0]);
        } else {
            topActiveSessionRecord.getAudioMediaController().play();
        }
    }

    public void a(long j2) {
        Logger.i("MiPlayAudioService", "LocalSeek.", new Object[0]);
        if (this.q.getTopActiveSessionRecord() == null) {
            Logger.i("MiPlayAudioService", "TopActiveSessionRecord is null", new Object[0]);
        } else {
            this.q.getTopActiveSessionRecord().getAudioMediaController().seekTo(j2);
        }
    }

    public void h() {
        Logger.i("MiPlayAudioService", "relayNeedPlay.", new Object[0]);
        PlayStateConfig playStateConfig = Constant.getInstance().getPlayStateConfig();
        if (playStateConfig != null) {
            Logger.d("MiPlayAudioService", "PlayStateConfig:" + playStateConfig.getDeviceId(), new Object[0]);
            if (playStateConfig.isNeedPlayed()) {
                g();
                Constant.getInstance().setPlayStateConfig(null);
                return;
            }
            return;
        }
        Logger.d("MiPlayAudioService", "playstateconfig is null.", new Object[0]);
    }

    public void a(int i, String str) {
        Logger.i("MiPlayAudioService", "disconnectMirrorPhoneControl.", new Object[0]);
        if (this.a == null) {
            return;
        }
        if (!a(this.d, i)) {
            Logger.i("MiPlayAudioService", "index non-existent", new Object[0]);
        } else if (this.d.size() > 1) {
            this.a.deleteMirror(i);
        } else {
            stopMirror();
        }
    }

    private String a(int i) {
        for (Map.Entry<String, Integer> entry : this.d.entrySet()) {
            if (entry.getValue().intValue() == i) {
                Logger.d("MiPlayAudioService", "DeviceMac:" + entry.getKey() + "  index:" + entry.getValue(), new Object[0]);
                return entry.getKey();
            }
        }
        return "";
    }

    private void i() {
        Logger.i("MiPlayAudioService", "registerNetworkReceiver.", new Object[0]);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(this.C, intentFilter);
    }

    private void j() {
        Logger.i("MiPlayAudioService", "unregisterNetworkReceiver.", new Object[0]);
        NetworkConnectChangedReceiver networkConnectChangedReceiver = this.C;
        if (networkConnectChangedReceiver != null) {
            unregisterReceiver(networkConnectChangedReceiver);
            this.C = null;
        }
    }

    public void startDiscovery(int i) {
        synchronized (this.y) {
            if (this.L && K != null) {
                Log.d("MiPlayAudioService", "mdns start find");
                K.startQuery(2, "_miplay_audio._tcp.local.", 12, 5354, 5000);
            }
            if (this.b == null) {
                Logger.i("MiPlayAudioService", "mClient is null", new Object[0]);
            } else if (this.s.get()) {
                Logger.i("MiPlayAudioService", "already start uwb startDiscovery", new Object[0]);
            } else {
                if (i == 258) {
                    this.s.set(true);
                    Logger.i("MiPlayAudioService", "Uwb startDiscovery", new Object[0]);
                }
                if (!this.b.isInited()) {
                    Constant.getInstance().setDisctype(i);
                    Constant.getInstance().setStartDiscoveryInit(true);
                    this.b.init(this.aa, "");
                }
                if (this.b.isDiscovering()) {
                    this.b.stopDiscovery();
                }
                this.b.startDiscovery(i);
            }
        }
    }

    public void stopDiscovery() {
        synchronized (this.y) {
            if (this.L && K != null) {
                K.stopQuery();
            }
            if (this.b == null) {
                Logger.i("MiPlayAudioService", "mClient is null", new Object[0]);
            } else if (!this.b.isInited()) {
                Logger.i("MiPlayAudioService", "not initialized yet", new Object[0]);
            } else {
                this.b.stopDiscovery();
            }
        }
    }

    public int getLocalPlayState() {
        synchronized (this.A) {
            if (this.q.getTopActiveSessionRecord() == null) {
                Logger.i("MiPlayAudioService", "TopActiveSessionRecord is null", new Object[0]);
                return -1;
            } else if (this.q.getTopActiveSessionRecord().getAudioMediaController() == null) {
                Logger.i("MiPlayAudioService", "getAudioMediaController is null", new Object[0]);
                return -1;
            } else {
                return this.q.getTopActiveSessionRecord().getAudioMediaController().getPlaybackState();
            }
        }
    }

    public long getLocalPosition() {
        if (this.q.getTopActiveSessionRecord() != null) {
            return this.q.getTopActiveSessionRecord().getAudioMediaController().getPosition();
        }
        Logger.i("MiPlayAudioService", "TopActiveSessionRecord is null", new Object[0]);
        return 0L;
    }

    public void highFrequencyDiscovery() {
        Logger.d("MiPlayAudioService", "highFrequencyDiscovery.", new Object[0]);
        if (this.q.getTopActiveSessionRecord() == null) {
            Logger.i("MiPlayAudioService", "TopActiveSessionRecord is null", new Object[0]);
            startDiscovery(2);
        } else if (this.ad != null) {
            Logger.d("MiPlayAudioService", "highFrequencyDiscovery executing.", new Object[0]);
        } else {
            this.ad = new ScheduledThreadPoolExecutor(2);
            this.ad.scheduleAtFixedRate(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.3
                @Override // java.lang.Runnable
                public void run() {
                    MiPlayAudioService.this.startDiscovery(2);
                    if (MiPlayAudioService.this.ae == 60) {
                        Logger.d("MiPlayAudioService", "highFrequencyDiscovery executed.", new Object[0]);
                        MiPlayAudioService.this.ae = 0;
                        MiPlayAudioService.this.ad.shutdown();
                        MiPlayAudioService.this.ad = null;
                    }
                    MiPlayAudioService.G(MiPlayAudioService.this);
                }
            }, 0L, 5L, TimeUnit.SECONDS);
        }
    }

    public void f(final MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "statCommandChannel onError:" + Constant.getInstance().convertMac(miDevice.getMac()), new Object[0]);
        Logger.i("MiPlayAudioService", "cmdSessionControlMap:" + this.cmdSessionControlMap.size(), new Object[0]);
        DeviceManager deviceManager = this.mDeviceManager;
        if (!deviceManager.isDeviceExists(deviceManager.getSessionErrorDeviceList(), miDevice.getMac())) {
            this.mDeviceManager.getSessionErrorDeviceList().add(miDevice);
            Logger.i("MiPlayAudioService", "SessionErrorDeviceList:" + this.mDeviceManager.getSessionErrorDeviceList().size(), new Object[0]);
        }
        Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
        while (it.hasNext()) {
            Logger.d("MiPlayAudioService", "cmdSessionError deviceInfo:" + it.next().toString(), new Object[0]);
        }
        Iterator<MiDevice> it2 = this.mDeviceManager.getMiDeviceList().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            MiDevice next = it2.next();
            if (miDevice.getMac().equals(next.getMac())) {
                Logger.i("MiPlayAudioService", "find lost devices:" + Constant.getInstance().convertMac(miDevice.getMac()), new Object[0]);
                if (this.mDeviceManager.isGroupIdExistTwoSame(miDevice.getGroupId())) {
                    Logger.i("MiPlayAudioService", "not the last one stereo", new Object[0]);
                } else {
                    d(miDevice.getUuid());
                }
                this.mDeviceManager.getMiDeviceList().remove(next);
                Logger.i("MiPlayAudioService", "remove device success!", new Object[0]);
            }
        }
        k(miDevice);
        ThreadPoolManager.getInstance().executeRunable(new Runnable() { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.4
            @Override // java.lang.Runnable
            public void run() {
                CmdSessionControl cmdSessionControl = MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac());
                if (MiPlayAudioService.this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                    cmdSessionControl.closeCmdSession(miDevice.getMac());
                }
                MiPlayAudioService.this.cmdSessionControlMap.remove(miDevice.getMac());
                Logger.d("MiPlayAudioService", "remove cdmsession " + miDevice.getMac(), new Object[0]);
                UpdatePositonPool.getInstance().setCmdSessionControlMap(MiPlayAudioService.this.cmdSessionControlMap);
            }
        });
    }

    public void a(MiDevice miDevice, byte[] bArr) {
        Logger.i("MiPlayAudioService", "onDeviceInfo." + Constant.getInstance().convertMac(miDevice.getMac()), new Object[0]);
        if (bArr == null || bArr.length < 40) {
            Logger.i("MiPlayAudioService", "deviceinfo==null", new Object[0]);
            d(miDevice);
            return;
        }
        this.mDeviceManager.analysisDeviceInfo(bArr);
        MiDevice updateMiDevice = this.mDeviceManager.updateMiDevice(miDevice);
        Logger.i("MiPlayAudioService", "verifyStatus:" + miDevice.getVerifyStatus(), new Object[0]);
        Logger.i("MiPlayAudioService", "canrevctrl:" + miDevice.getCanRevCtrl(), new Object[0]);
        if (miDevice.getVerifyStatus() != 1 || !TextUtils.equals(miDevice.getCanRevCtrl(), "1")) {
            b(miDevice.getMac(), 0);
        } else {
            this.b.VerifySameAccount(updateMiDevice.getServiceProto());
        }
        e(updateMiDevice);
    }

    public void b(MiDevice miDevice, byte[] bArr) {
        Logger.d("MiPlayAudioService", "cmdSessionDevicesInfoChanged:" + miDevice.toString(), new Object[0]);
        boolean isEmpty = TextUtils.isEmpty(miDevice.getGroupId());
        if (bArr == null || bArr.length < 40) {
            Logger.i("MiPlayAudioService", "deviceInfoChanged==null", new Object[0]);
            return;
        }
        this.mDeviceManager.analysisDeviceInfo(bArr);
        if (!this.mDeviceManager.deviceEquals(miDevice)) {
            MiDevice updateMiDevice = this.mDeviceManager.updateMiDevice(miDevice);
            Logger.d("MiPlayAudioService", "cmdSessionDevicesInfoChanged update after device:" + miDevice.toString(), new Object[0]);
            if (!TextUtils.isEmpty(updateMiDevice.getGroupId())) {
                Logger.i("MiPlayAudioService", "isGroupIdEmpty:" + isEmpty, new Object[0]);
                if (isEmpty) {
                    Logger.i("MiPlayAudioService", "cmdSessionDevicesInfoChanged need delete device , because Last time deviceinfo is null", new Object[0]);
                    i(updateMiDevice);
                    DeviceManager deviceManager = this.mDeviceManager;
                    if (deviceManager.isGroupIdExists(deviceManager.getMiDeviceList(), miDevice.getGroupId())) {
                        String firstStereoUuid = this.mDeviceManager.getFirstStereoUuid(miDevice.getGroupId(), miDevice.getMac());
                        if (TextUtils.isEmpty(firstStereoUuid)) {
                            Logger.i("MiPlayAudioService", "this same device", new Object[0]);
                            d(updateMiDevice);
                        } else {
                            updateMiDevice.updateUuid(firstStereoUuid);
                            Logger.i("MiPlayAudioService", "uuid update after:" + updateMiDevice.getUuid(), new Object[0]);
                            this.mDeviceManager.updateMiDeviceList(updateMiDevice);
                            this.mDeviceManager.updateConnectedMiDeviceList(updateMiDevice);
                            d(updateMiDevice);
                        }
                    } else {
                        updateMiDevice.updateUuid(UUIDGenerator.getUUID());
                        Logger.i("MiPlayAudioService", "uuid update after:" + updateMiDevice.getUuid(), new Object[0]);
                        this.mDeviceManager.updateMiDeviceList(updateMiDevice);
                        this.mDeviceManager.updateConnectedMiDeviceList(updateMiDevice);
                        d(updateMiDevice);
                    }
                    h(updateMiDevice);
                    return;
                }
            }
            g(updateMiDevice);
            return;
        }
        Logger.i("MiPlayAudioService", "deviceInfoChanged no Changed", new Object[0]);
        this.mDeviceManager.clearDeviceMap();
    }

    private void g(MiDevice miDevice) {
        this.mDeviceManager.updateMiDeviceList(miDevice);
        this.mDeviceManager.updateConnectedMiDeviceList(miDevice);
        b(miDevice);
        h(miDevice);
    }

    private void h(MiDevice miDevice) {
        if (this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
            Logger.i("MiPlayAudioService", "setDevice.", new Object[0]);
            this.cmdSessionControlMap.get(miDevice.getMac()).setDevice(miDevice);
        }
    }

    public void c(MiDevice miDevice, byte[] bArr) {
        Logger.i("MiPlayAudioService", "cmdSessionDeviceChange update before device:", new Object[0]);
        Logger.d("MiPlayAudioService", miDevice.toString(), new Object[0]);
        if (bArr == null || bArr.length < 40) {
            Logger.i("MiPlayAudioService", "deviceinfo==null", new Object[0]);
            return;
        }
        synchronized (this.u) {
            this.mDeviceManager.analysisDeviceInfo(bArr);
            boolean isDevicesChangeGroupIdNull = this.mDeviceManager.isDevicesChangeGroupIdNull();
            Logger.i("MiPlayAudioService", "cmdSessionDeviceChange isGroupIdNull:" + isDevicesChangeGroupIdNull, new Object[0]);
            if (TextUtils.isEmpty(miDevice.getGroupId())) {
                MiDevice updateMiDevice = this.mDeviceManager.updateMiDevice(miDevice);
                Logger.d("MiPlayAudioService", "newDevice:" + updateMiDevice.toString(), new Object[0]);
                if (isDevicesChangeGroupIdNull) {
                    Logger.i("MiPlayAudioService", "cmdSessionDeviceChange current Still is whole house", new Object[0]);
                    b(updateMiDevice);
                    return;
                }
                i(updateMiDevice);
                Logger.i("MiPlayAudioService", "-----------Stereo on", new Object[0]);
                if (this.mDeviceManager.isGroupIdExistTwoSame(updateMiDevice.getGroupId())) {
                    String firstStereoUuid = this.mDeviceManager.getFirstStereoUuid(updateMiDevice.getGroupId(), updateMiDevice.getMac());
                    if (TextUtils.isEmpty(firstStereoUuid)) {
                        Logger.i("MiPlayAudioService", "this same device", new Object[0]);
                        d(miDevice);
                    } else {
                        Logger.i("MiPlayAudioService", "groupId already exists,uuid:" + firstStereoUuid, new Object[0]);
                        miDevice.updateUuid(firstStereoUuid);
                        d(miDevice);
                        this.mDeviceManager.updateConnectedMiDeviceList(updateMiDevice);
                    }
                } else {
                    updateMiDevice.updateUuid(UUIDGenerator.getUUID());
                    d(updateMiDevice);
                    this.mDeviceManager.updateConnectedMiDeviceList(updateMiDevice);
                }
                Logger.d("MiPlayAudioService", "cmdSessionDeviceChange stereo after:" + updateMiDevice.toString(), new Object[0]);
                h(updateMiDevice);
            } else {
                MiDevice updateMiDevice2 = this.mDeviceManager.updateMiDevice(miDevice);
                Logger.d("MiPlayAudioService", "newDevice:" + updateMiDevice2.toString(), new Object[0]);
                if (!isDevicesChangeGroupIdNull) {
                    Logger.i("MiPlayAudioService", "onDeviceChanged current Still is stereo", new Object[0]);
                    b(updateMiDevice2);
                    return;
                }
                Logger.i("MiPlayAudioService", "-----------Stereo off", new Object[0]);
                i(updateMiDevice2);
                updateMiDevice2.updateUuid(UUIDGenerator.getUUID());
                Logger.i("MiPlayAudioService", "single uuid update after:" + updateMiDevice2.getUuid(), new Object[0]);
                this.mDeviceManager.updateMiDeviceList(updateMiDevice2);
                this.mDeviceManager.updateConnectedMiDeviceList(updateMiDevice2);
                d(updateMiDevice2);
                h(updateMiDevice2);
            }
            Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
            while (it.hasNext()) {
                Logger.d("MiPlayAudioService", "cmdSessionDeviceChange end:" + it.next().toString(), new Object[0]);
            }
        }
    }

    private void i(MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "reportDeviceLost.", new Object[0]);
        d(miDevice.getUuid());
        Logger.d("MiPlayAudioService", "cmdSessionDeviceChange stop cast:" + miDevice.toString(), new Object[0]);
        d(new String[]{miDevice.getUuid()});
    }

    public void deviceFound(MiDevice miDevice) {
        if (this.mDeviceManager.getMiDeviceList() != null) {
            if (this.mDeviceManager.getMiDeviceList().size() > 19) {
                Logger.i("MiPlayAudioService", "device cmdsession beyond limit", new Object[0]);
                Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MiDevice next = it.next();
                    if (TextUtils.equals(next.getMac(), miDevice.getMac())) {
                        if (next.getDeviceConnectState() == 0) {
                            this.Y.obtainMessage(25, miDevice).sendToTarget();
                            break;
                        }
                        Logger.i("MiPlayAudioService", "device already  cast,no-delete", new Object[0]);
                    }
                }
            }
            Iterator<MiDevice> it2 = this.mDeviceManager.getMiDeviceList().iterator();
            while (it2.hasNext()) {
                Logger.d("MiPlayAudioService", "deviceInfo: " + it2.next().toString(), new Object[0]);
            }
            l();
            if (this.mDeviceManager.isDeviceExists(miDevice.getMac()) || this.mDeviceManager.isIdhashExists(miDevice.getIdhash())) {
                for (MiDevice miDevice2 : this.mDeviceManager.getMiDeviceList()) {
                    if (TextUtils.equals(miDevice2.getMac(), miDevice.getMac())) {
                        if (TextUtils.isEmpty(miDevice2.getGroupId()) && TextUtils.isEmpty(miDevice2.getMiName())) {
                            miDevice2.setName(miDevice.getName());
                            Logger.i("MiPlayAudioService", "update deviceName:" + miDevice.getName(), new Object[0]);
                        }
                        d(miDevice2);
                        return;
                    }
                }
                return;
            }
            miDevice.setUuid(UUIDGenerator.getUUID());
            this.mDeviceManager.getMiDeviceList().add(miDevice);
            a(miDevice);
        }
    }

    public void deviceUpdateIDM(MiDevice miDevice) {
        synchronized (this.z) {
            Logger.i("MiPlayAudioService", "deviceUpdateIDM.", new Object[0]);
            if (this.mDeviceManager.isDeviceExists(miDevice.getMac())) {
                Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MiDevice next = it.next();
                    Logger.d("MiPlayAudioService", "MiDevice:" + next.toString(), new Object[0]);
                    if (next.getMac().equals(miDevice.getMac())) {
                        next.setAltitude(miDevice.getAltitude());
                        next.setAzimuth(miDevice.getAzimuth());
                        next.setDistance(miDevice.getDistance());
                        b(next);
                        break;
                    }
                }
            }
        }
    }

    public void a(int i, int i2) {
        Logger.i("MiPlayAudioService", "onMultiDisplayPrepared index:" + i + "  port:" + i2, new Object[0]);
        this.U = 2;
        StringBuilder sb = new StringBuilder();
        sb.append("onMultiDisplayPrepared mMultiMirrorstatus:");
        sb.append(this.U);
        Logger.i("MiPlayAudioService", sb.toString(), new Object[0]);
        if (TextUtils.isEmpty(this.f.get(Integer.valueOf(i2))) || this.a == null) {
            Logger.i("MiPlayAudioService", "port is invalid.", new Object[0]);
            MirrorServer mirrorServer = this.a;
            if (mirrorServer != null) {
                mirrorServer.deleteMirror(i);
                return;
            }
            return;
        }
        String str = this.f.get(Integer.valueOf(i2));
        Logger.d("MiPlayAudioService", "deviceMac:" + Constant.getInstance().convertMac(str), new Object[0]);
        DeviceManager deviceManager = this.mDeviceManager;
        deviceManager.updateConnected(deviceManager.getMiDeviceList(), -1, str);
        this.f.remove(Integer.valueOf(i2));
        if (c(str)) {
            int intValue = this.d.get(str).intValue();
            Logger.i("MiPlayAudioService", "onMultiDisplayPrepared backupIndex:" + intValue, new Object[0]);
            this.e.put(str, Integer.valueOf(intValue));
        }
        this.d.put(str, Integer.valueOf(i));
        this.Y.obtainMessage(27, new Object[]{str, Integer.valueOf(i2)}).sendToTarget();
        MirrorServer mirrorServer2 = this.a;
        if (mirrorServer2 != null) {
            int mirrorNums = mirrorServer2.getMirrorNums();
            Logger.i("MiPlayAudioService", "onMultiDisplayPrepared mirrorNums:" + mirrorNums, new Object[0]);
            Logger.i("MiPlayAudioService", "onMultiDisplayPrepared ConnectedMiDeviceList:" + this.mDeviceManager.getConnectedMiDeviceList().size(), new Object[0]);
            if (mirrorNums != -1 && mirrorNums > 0) {
                if (this.mDeviceManager.getConnectedMiDeviceList().size() != mirrorNums - 1) {
                    Logger.i("MiPlayAudioService", "onMultiDisplayPrepared ConnectedMiDeviceList!=mirrorNums-1", new Object[0]);
                }
                if (mirrorNums == 1) {
                    this.mDeviceManager.getConnectedMiDeviceList().clear();
                    Logger.i("MiPlayAudioService", "onMultiDisplayPrepared clear ConnectedMiDeviceList", new Object[0]);
                }
            }
        }
        if (this.N.size() > 0 && this.N.get(str) != null) {
            Logger.i("MiPlayAudioService", "onMultiDisplayPrepared reconnect", new Object[0]);
            a(getmErrorIndex(), str);
        }
    }

    public void b(int i, String str) {
        Logger.i("MiPlayAudioService", "onMultiDisplayConnected.", new Object[0]);
        Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MiDevice next = it.next();
            Logger.i("MiPlayAudioService", "MiPlayDevice IP:" + next.getIp(), new Object[0]);
            Logger.i("MiPlayAudioService", "callback IP:" + str, new Object[0]);
            if (next.getIp().equals(str)) {
                Logger.d("MiPlayAudioService", "deviceMac:" + Constant.getInstance().convertMac(next.getMac()), new Object[0]);
                if (this.N.size() > 0 && this.N.get(next.getMac()) != null) {
                    Logger.i("MiPlayAudioService", "onMultiDisplayConnected reconnect success!", new Object[0]);
                    this.N.remove(next.getMac());
                }
                StatsUtils.getInstance().stopTimer(1, next.getDeviceType(), this.mDeviceManager);
                Logger.d("MiPlayAudioService", "修改前:" + next.toString(), new Object[0]);
                this.d.put(next.getMac(), Integer.valueOf(i));
                Logger.i("MiPlayAudioService", "indexMap:" + next.getName() + "  " + i, new Object[0]);
                a(next.getUuid(), 1);
                next.setDeviceConnectState(1);
                if (this.cmdSessionControlMap.get(next.getMac()) != null) {
                    Logger.i("MiPlayAudioService", "setDevice.", new Object[0]);
                    this.cmdSessionControlMap.get(next.getMac()).setDevice(next);
                }
                b(next);
                l(next);
                DeviceManager deviceManager = this.mDeviceManager;
                if (!deviceManager.isDeviceExists(deviceManager.getConnectedMiDeviceList(), next.getMac())) {
                    this.mDeviceManager.getConnectedMiDeviceList().add(next);
                } else {
                    Logger.d("MiPlayAudioService", "ConnectedMiDeviceList exists:" + next.toString(), new Object[0]);
                }
                Logger.i("MiPlayAudioService", "ConnectedMiDeviceList:" + this.mDeviceManager.getConnectedMiDeviceList().size(), new Object[0]);
                this.m.setMetaInfo(this.q, next, this.cmdSessionControlMap, getDeviceState(getLocalPlayState()));
                StatsUtils.getInstance().connectStats("connect_success", -111, false, next.getDeviceType(), this.mDeviceManager);
                if (next.getDeviceType() == 2 || next.getDeviceType() == 16) {
                    Logger.i("MiPlayAudioService", "cast mc_mi_dev_tv", new Object[0]);
                    Logger.i("MiPlayAudioService", "isStartTimered:" + UpdatePositonPool.getInstance().isStartTimered(), new Object[0]);
                    if (!UpdatePositonPool.getInstance().isStartTimered()) {
                        UpdatePositonPool.getInstance().startTimer();
                    }
                    if (this.mDeviceManager.getConnectedMiDeviceList().size() > 1) {
                        h();
                    }
                }
            }
        }
        Iterator<MiDevice> it2 = this.mDeviceManager.getMiDeviceList().iterator();
        while (it2.hasNext()) {
            Logger.d("MiPlayAudioService", "修改后:" + it2.next().toString(), new Object[0]);
        }
        Iterator<MiDevice> it3 = this.mDeviceManager.getConnectedMiDeviceList().iterator();
        while (it3.hasNext()) {
            Logger.d("MiPlayAudioService", "ConnectedMiDeviceList 修改后:" + it3.next().toString(), new Object[0]);
        }
        l();
        if (this.mDeviceManager.getConnectedMiDeviceList().size() > 8) {
            Logger.i("MiPlayAudioService", "cast connect beyond limit", new Object[0]);
            Iterator<MiDevice> it4 = this.mDeviceManager.getConnectedMiDeviceList().iterator();
            if (it4.hasNext()) {
                MiDevice next2 = it4.next();
                String[] strArr = {next2.getUuid()};
                Logger.d("MiPlayAudioService", "device:" + next2.toString(), new Object[0]);
                d(strArr);
            }
        }
        Logger.i("MiPlayAudioService", "onMultiDisplayConnected finish", new Object[0]);
    }

    public void a(String str, int i) {
        Logger.i("MiPlayAudioService", "deviceConnectStateChange:" + i, new Object[0]);
        HashMap<String, MiPlayClientInfo> hashMap = this.c;
        if (hashMap != null) {
            try {
                for (Map.Entry<String, MiPlayClientInfo> entry : hashMap.entrySet()) {
                    entry.getValue().b.onDeviceConnectStateChange(str, i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void d(String str) {
        Logger.i("MiPlayAudioService", "deviceLost:" + str, new Object[0]);
        HashMap<String, MiPlayClientInfo> hashMap = this.c;
        if (hashMap != null) {
            for (Map.Entry<String, MiPlayClientInfo> entry : hashMap.entrySet()) {
                try {
                    Logger.d("MiPlayAudioService", "deviceLost:" + str, new Object[0]);
                    Logger.d("MiPlayAudioService", "deviceLost:" + entry.getValue().a, new Object[0]);
                    entry.getValue().b.onDeviceLost(str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void multiDisplayDisConnected(int i) {
        Logger.i("MiPlayAudioService", "onMultiDisplayDisConnected indexMap:" + this.d.size(), new Object[0]);
        Map<String, Integer> map = this.d;
        if (map != null) {
            for (Integer num : map.values()) {
                int intValue = num.intValue();
                Logger.i("MiPlayAudioService", "indexValue:" + intValue, new Object[0]);
            }
        }
        if (a(this.d, i)) {
            b(this.d, i);
        } else if (a(this.e, i)) {
            b(this.e, i);
        } else {
            Logger.i("MiPlayAudioService", "index abnormal", new Object[0]);
        }
        Iterator<MiDevice> it = this.mDeviceManager.getConnectedMiDeviceList().iterator();
        while (it.hasNext()) {
            Logger.d("MiPlayAudioService", "ConnectedMiDeviceList 修改后:" + it.next().toString(), new Object[0]);
        }
        if (this.d.isEmpty()) {
            stopMirror();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0008, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(java.util.Map<java.lang.String, java.lang.Integer> r8, int r9) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.MiPlayAudioService.b(java.util.Map, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x006b, code lost:
        if (r0.queryOtherConnectStateFromGroupId(r0.getMiDeviceList(), r7.getGroupId(), r7.getMac()) != 0) goto L_0x006d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x008b, code lost:
        if (r0.isDeviceExists(r0.getMiDeviceList(), r7.getMac()) != false) goto L_0x0098;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0033, code lost:
        if (r0.isDeviceExists(r0.getConnectedMiDeviceList(), r7.getMac()) == false) goto L_0x0035;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c(com.xiaomi.miplay.mylibrary.MiDevice r7, int r8) {
        /*
            r6 = this;
            java.lang.String r0 = "MiPlayAudioService"
            java.lang.String r1 = "reportConnectState."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r0, r1, r3)
            java.lang.String r0 = r7.getGroupId()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00a0
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r1 = r0.getConnectedMiDeviceList()
            java.lang.String r3 = r7.getGroupId()
            int r0 = r0.queryGroupIdNum(r1, r3)
            r1 = 1
            if (r0 != r1) goto L_0x0035
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r3 = r0.getConnectedMiDeviceList()
            java.lang.String r4 = r7.getMac()
            boolean r0 = r0.isDeviceExists(r3, r4)
            if (r0 != 0) goto L_0x0098
        L_0x0035:
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r3 = r0.getConnectedMiDeviceList()
            java.lang.String r4 = r7.getGroupId()
            java.lang.String r5 = r7.getMac()
            int r0 = r0.queryOtherConnectStateFromGroupId(r3, r4, r5)
            if (r0 == 0) goto L_0x0098
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r3 = r0.getConnectedMiDeviceList()
            java.lang.String r4 = r7.getGroupId()
            boolean r0 = r0.isGroupIdExists(r3, r4)
            if (r0 != 0) goto L_0x006d
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r3 = r0.getMiDeviceList()
            java.lang.String r4 = r7.getGroupId()
            java.lang.String r5 = r7.getMac()
            int r0 = r0.queryOtherConnectStateFromGroupId(r3, r4, r5)
            if (r0 == 0) goto L_0x0098
        L_0x006d:
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r3 = r0.getMiDeviceList()
            java.lang.String r4 = r7.getGroupId()
            int r0 = r0.queryGroupIdNum(r3, r4)
            if (r0 != r1) goto L_0x008e
            com.xiaomi.miplay.mylibrary.DeviceManager r0 = r6.mDeviceManager
            java.util.List r1 = r0.getMiDeviceList()
            java.lang.String r3 = r7.getMac()
            boolean r0 = r0.isDeviceExists(r1, r3)
            if (r0 == 0) goto L_0x008e
            goto L_0x0098
        L_0x008e:
            java.lang.String r7 = "MiPlayAudioService"
            java.lang.String r8 = "stereo There is one"
            java.lang.Object[] r0 = new java.lang.Object[r2]
            com.xiaomi.miplay.mylibrary.session.utils.Logger.i(r7, r8, r0)
            goto L_0x00a7
        L_0x0098:
            java.lang.String r7 = r7.getUuid()
            r6.a(r7, r8)
            goto L_0x00a7
        L_0x00a0:
            java.lang.String r7 = r7.getUuid()
            r6.a(r7, r8)
        L_0x00a7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.MiPlayAudioService.c(com.xiaomi.miplay.mylibrary.MiDevice, int):void");
    }

    public void multiDisplayError(int i, int i2, int i3) {
        Logger.i("MiPlayAudioService", "onMultiDisplayError indexMap:" + this.d.size() + "    errorCode:" + i, new Object[0]);
        String a2 = a(i2);
        StringBuilder sb = new StringBuilder();
        sb.append("deviceMac:");
        sb.append(Constant.getInstance().convertMac(a2));
        Logger.d("MiPlayAudioService", sb.toString(), new Object[0]);
        DeviceManager deviceManager = this.mDeviceManager;
        deviceManager.updateConnected(deviceManager.getMiDeviceList(), -2, a2);
        DeviceManager deviceManager2 = this.mDeviceManager;
        MiDevice queryDeviceFromDeviceMac = deviceManager2.queryDeviceFromDeviceMac(deviceManager2.getMiDeviceList(), a2);
        if (queryDeviceFromDeviceMac == null) {
            Logger.i("MiPlayAudioService", "device is null", new Object[0]);
            DeviceManager deviceManager3 = this.mDeviceManager;
            queryDeviceFromDeviceMac = deviceManager3.queryDeviceFromDeviceMac(deviceManager3.getSessionErrorDeviceList(), a2);
        }
        if (queryDeviceFromDeviceMac == null) {
            Logger.i("MiPlayAudioService", "device==null", new Object[0]);
            Handler handler = this.Y;
            if (handler != null) {
                handler.obtainMessage(22, new Object[]{Integer.valueOf(i2), a2}).sendToTarget();
                return;
            }
            return;
        }
        StatsUtils.getInstance().connectStats("connect_fail", i, true, queryDeviceFromDeviceMac.getDeviceType(), this.mDeviceManager);
        if (!ReceiveStopStackManager.getInstance().isEmpty()) {
            Logger.i("MiPlayAudioService", "onMultiDisplayError stop reConnect", new Object[0]);
            this.D = 1;
            c(queryDeviceFromDeviceMac, i);
            Handler handler2 = this.Y;
            if (handler2 != null) {
                handler2.obtainMessage(22, new Object[]{Integer.valueOf(i2), a2}).sendToTarget();
            }
        } else if (i == -6) {
            if (this.D > 5) {
                Logger.i("MiPlayAudioService", "reConnect timeout:", new Object[0]);
                c(queryDeviceFromDeviceMac, i);
                a(i2, a2);
                this.D = 1;
            } else {
                setmErrorIndex(i2);
                e(queryDeviceFromDeviceMac.getMac());
                Logger.i("MiPlayAudioService", "reConnect mirror:" + this.D, new Object[0]);
            }
            this.D++;
        } else {
            c(queryDeviceFromDeviceMac, i);
            Handler handler3 = this.Y;
            if (handler3 != null) {
                handler3.obtainMessage(22, new Object[]{Integer.valueOf(i2), a2}).sendToTarget();
            }
        }
    }

    public int getmErrorIndex() {
        return this.M;
    }

    public void setmErrorIndex(int i) {
        this.M = i;
    }

    public void j(MiDevice miDevice) {
        MediaControlImpl mediaControlImpl;
        Logger.i("MiPlayAudioService", "onSuccess.", new Object[0]);
        if (this.cmdSessionControlMap.get(miDevice.getMac()) == null) {
            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
            return;
        }
        Logger.d("MiPlayAudioService", "deviceMac:" + miDevice.getMac() + "  cmdSessionControlMap:" + this.cmdSessionControlMap.size(), new Object[0]);
        this.cmdSessionControlMap.get(miDevice.getMac()).getDeviceInfo();
        if (VideoCirculatePool.getInstance().size() == 1) {
            Logger.d("MiPlayAudioService", "videocirculatepool deviceId:" + miDevice.getMac(), new Object[0]);
            PlayConfig playConfig = VideoCirculatePool.getInstance().getPlayConfig();
            if (playConfig == null) {
                Logger.i("MiPlayAudioService", "playconfig is null.", new Object[0]);
            } else if (TextUtils.equals(playConfig.getDeviceId(), miDevice.getMac()) && (mediaControlImpl = this.X) != null) {
                mediaControlImpl.play(playConfig.getId(), playConfig.getMeta());
            }
        }
        f(miDevice.getMac());
    }

    public void d(MiDevice miDevice, int i) {
        Logger.i("MiPlayAudioService", "onMirrorModeAck  mode::" + i, new Object[0]);
        Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            MiDevice next = it.next();
            Logger.d("MiPlayAudioService", "midevicelist:" + next.getMac(), new Object[0]);
            if (next.getMac().equals(miDevice.getMac())) {
                next.setMirrorMode(i);
                b(next);
                break;
            }
        }
        for (MiDevice miDevice2 : this.mDeviceManager.getMiDeviceList()) {
            if (miDevice2.getMac().equals(miDevice.getMac())) {
                Logger.d("MiPlayAudioService", "fix after MirrorMode::" + miDevice2.toString(), new Object[0]);
                return;
            }
        }
    }

    public void e(MiDevice miDevice, int i) {
        Logger.i("MiPlayAudioService", "onMirrorModeNotify  mode::" + i, new Object[0]);
        Logger.i("MiPlayAudioService", "last  mode::" + miDevice.getMirrorMode(), new Object[0]);
        Logger.d("MiPlayAudioService", "device::" + miDevice.getMac(), new Object[0]);
        for (MiDevice miDevice2 : this.mDeviceManager.getMiDeviceList()) {
            if (miDevice2.getMac().equals(miDevice.getMac())) {
                if (miDevice.getMirrorMode() != i) {
                    this.W = 0L;
                    if (miDevice.getMirrorMode() == 3) {
                        Logger.i("MiPlayAudioService", "disconnect video circulate", new Object[0]);
                        if (this.P != null) {
                            Logger.i("MiPlayAudioService", "mCirculateStatus:" + this.P.getCirculateStatus(), new Object[0]);
                            Logger.i("MiPlayAudioService", "isResumeMirroring:" + this.P.isResumeMirrorings(), new Object[0]);
                            if (!this.P.isResumeMirrorings()) {
                                int circulateStatus = this.P.getCirculateStatus();
                                MiplayMirrorService miplayMirrorService = this.P;
                                if (circulateStatus == MiplayMirrorService.CIRCULATE_STATUS_CIRCULATE) {
                                    this.P.onStoped(0);
                                }
                            }
                        }
                    }
                    miDevice2.setMirrorMode(i);
                    Logger.i("MiPlayAudioService", "fix after MirrorMode::" + miDevice2.getMirrorMode(), new Object[0]);
                    if (i == 3) {
                        Logger.i("MiPlayAudioService", "connect video circulate", new Object[0]);
                        return;
                    }
                    return;
                }
                return;
            }
        }
    }

    private void k(MiDevice miDevice) {
        Logger.i("MiPlayAudioService", "stopSingleMirror.", new Object[0]);
        if (this.a == null || this.d.get(miDevice.getMac()) == null) {
            Logger.i("MiPlayAudioService", "mMirrotServer is null or index is null", new Object[0]);
        } else {
            int intValue = this.d.get(miDevice.getMac()).intValue();
            Logger.i("MiPlayAudioService", "MSG_ONSTOP index:" + intValue, new Object[0]);
            if (this.d.size() > 1) {
                this.a.deleteMirror(intValue);
            } else {
                stopMirror();
            }
        }
        Logger.i("MiPlayAudioService", "stopSingleMirror end.", new Object[0]);
    }

    private void e(String str) {
        synchronized (this.w) {
            this.N.put(str, 1);
            Logger.i("MiPlayAudioService", "reConnectMirror.", new Object[0]);
            c(new String[]{str});
            Log.e("MiPlayAudioService", "cmdIngDeviceList:" + this.n.size());
            if (this.n.size() == 0) {
                Logger.i("MiPlayAudioService", "cmdIngDeviceList size 0", new Object[0]);
                return;
            }
            String b2 = b("");
            String substring = b2.substring(0, b2.lastIndexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
            Log.e("MiPlayAudioService", "multiPort substring after:" + substring);
            if (this.a != null) {
                List asList = Arrays.asList(substring.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER));
                Log.e("MiPlayAudioService", "resultList:" + asList.size());
                for (int i = 0; i < asList.size(); i++) {
                    Log.e("MiPlayAudioService", "val:" + ((String) asList.get(i)));
                    if (!TextUtils.isEmpty((CharSequence) asList.get(i))) {
                        this.a.addMirror((String) asList.get(i), this.f);
                    } else {
                        Logger.i("MiPlayAudioService", "port is empty!", new Object[0]);
                    }
                }
            } else {
                startMirror(this.ac, substring, "", this.f);
            }
            Logger.i("MiPlayAudioService", "reConnectMirror end.", new Object[0]);
        }
    }

    public void a(String str, long j2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, Long.valueOf(j2));
        try {
            for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                entry.getValue().b.onPositionAck(hashMap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void f(MiDevice miDevice, int i) {
        Logger.d("MiPlayAudioService", "volumeChange:" + i, new Object[0]);
        if (miDevice.getMirrorMode() == 3) {
            MiplayVideoService miplayVideoService = this.O;
            if (miplayVideoService != null) {
                miplayVideoService.volumeChange(miDevice.getUuid(), i);
            }
            MiplayMirrorService miplayMirrorService = this.P;
            if (miplayMirrorService != null) {
                miplayMirrorService.volumeChange(i);
                return;
            }
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(miDevice.getUuid(), Integer.valueOf(i));
        try {
            for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                entry.getValue().b.onVolumeChange(hashMap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void g(MiDevice miDevice, int i) {
        Logger.i("MiPlayAudioService", "onPhysicalBtVolumeNotify.volume：" + i, new Object[0]);
        if (TextUtils.isEmpty(miDevice.getGroupId())) {
            Logger.i("MiPlayAudioService", "no stereo notify volume change：", new Object[0]);
            f(miDevice, i);
        } else if (miDevice.getDeviceConnectState() == 1) {
            Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MiDevice next = it.next();
                if (TextUtils.equals(next.getGroupId(), miDevice.getGroupId()) && !TextUtils.equals(next.getMac(), miDevice.getMac())) {
                    if (this.cmdSessionControlMap.get(next.getMac()) == null) {
                        Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                        return;
                    }
                    this.cmdSessionControlMap.get(next.getMac()).setVolume(i);
                    Logger.d("MiPlayAudioService", "start stereo syncvolume:" + next.toString(), new Object[0]);
                }
            }
            Logger.i("MiPlayAudioService", "start report volume", new Object[0]);
            f(miDevice, i);
        } else {
            f(miDevice, i);
        }
    }

    public void h(MiDevice miDevice, int i) {
        Logger.i("MiPlayAudioService", "volumeAck::" + i, new Object[0]);
        Logger.d("MiPlayAudioService", miDevice.toString(), new Object[0]);
        if (miDevice.getMirrorMode() == 3) {
            MiplayVideoService miplayVideoService = this.O;
            if (miplayVideoService != null) {
                miplayVideoService.volumeAck(miDevice.getUuid(), i);
            }
            MiplayMirrorService miplayMirrorService = this.P;
            if (miplayMirrorService != null) {
                miplayMirrorService.volumeChange(i);
                return;
            }
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(miDevice.getUuid(), Integer.valueOf(i));
        try {
            for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
                entry.getValue().b.onVolumeAck(hashMap);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!TextUtils.equals(miDevice.getUuid(), DataModel.LOCAL_DEVICE_ID)) {
            this.mDeviceManager.getVolumeMap().put(miDevice.getMac(), Integer.valueOf(i));
            if (!SyncVolumeStackManager.getInstance().isEmpty()) {
                String pop = SyncVolumeStackManager.getInstance().getGroupIdStack().pop();
                Logger.i("MiPlayAudioService", "sync groupId:" + pop, new Object[0]);
                if (TextUtils.equals(pop, miDevice.getGroupId())) {
                    for (MiDevice miDevice2 : this.mDeviceManager.getMiDeviceList()) {
                        if (TextUtils.equals(miDevice2.getGroupId(), pop) && !TextUtils.equals(miDevice2.getMac(), miDevice.getMac()) && this.cmdSessionControlMap.get(miDevice2.getMac()) == null) {
                            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void d(String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                ReceiveStopStackManager.getInstance().addDevice(str);
                Logger.i("MiPlayAudioService", "receivestopstack:" + ReceiveStopStackManager.getInstance().getStack().size(), new Object[0]);
            }
        }
        a(strArr);
        for (MiDevice miDevice : this.n) {
            Logger.d("MiPlayAudioService", "cmdIngDeviceList:" + miDevice.toString(), new Object[0]);
            k(miDevice);
        }
    }

    public boolean isStartTimered() {
        return this.J;
    }

    private boolean k() {
        for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
            if (miDevice.getMirrorMode() == 3) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.xiaomi.miplay.mylibrary.MiPlayAudioService$5] */
    public void runDownTimer() {
        Logger.i("MiPlayAudioService", "runDownTimer.", new Object[0]);
        if (k()) {
            Logger.i("MiPlayAudioService", "iscirculate_mode no runDownTimer", new Object[0]);
        } else if (this.J) {
            Logger.i("MiPlayAudioService", "timer already started", new Object[0]);
        } else {
            this.J = true;
            this.I = new CountDownTimer(600000L, 10000L) { // from class: com.xiaomi.miplay.mylibrary.MiPlayAudioService.5
                @Override // android.os.CountDownTimer
                public void onTick(long j2) {
                    Logger.i("MiPlayAudioService", "onTick:" + (j2 / 1000) + ai.az, new Object[0]);
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    Logger.i("MiPlayAudioService", "DownTimer onFinish", new Object[0]);
                    MiPlayAudioService.this.releasePowerWaste();
                    MiPlayAudioService.this.cancleDownTimer();
                }
            }.start();
        }
    }

    public void cancleDownTimer() {
        Logger.i("MiPlayAudioService", "cancleDownTimer.", new Object[0]);
        CountDownTimer countDownTimer = this.I;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.I = null;
        }
        this.J = false;
    }

    public void releasePowerWaste() {
        Logger.i("MiPlayAudioService", "releasePowerWaste.", new Object[0]);
        for (MiDevice miDevice : this.mDeviceManager.getMiDeviceList()) {
            this.Y.obtainMessage(25, miDevice).sendToTarget();
        }
        MiPlayClientAPI miPlayClientAPI = this.b;
        if (miPlayClientAPI != null) {
            miPlayClientAPI.unInit(true);
        }
    }

    private void l() {
        Logger.i("MiPlayAudioService", "powerWaste.", new Object[0]);
        int localPlayState = getLocalPlayState();
        Logger.d("MiPlayAudioService", "playState:" + localPlayState, new Object[0]);
        if (localPlayState <= 2 || !queryActiveAudioSession()) {
            synchronized (this.x) {
                runDownTimer();
            }
        }
    }

    public void f(String str) {
        Logger.i("MiPlayAudioService", "setLocalDeviceInfoSourceName..", new Object[0]);
        DeviceManager deviceManager = this.mDeviceManager;
        byte[] sourceNameToJson = deviceManager.sourceNameToJson(deviceManager.getLocalPhoneName(), this.r);
        if (this.cmdSessionControlMap.get(str) == null) {
            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
        } else {
            this.cmdSessionControlMap.get(str).setLocalDeviceInfo(sourceNameToJson);
        }
    }

    public void b(String str, int i) {
        Logger.i("MiPlayAudioService", "setLocalDeviceInfoSameAccount.", new Object[0]);
        byte[] isSameAccountToJson = this.mDeviceManager.isSameAccountToJson(i);
        if (this.cmdSessionControlMap.get(str) == null) {
            Logger.i("MiPlayAudioService", "cmdSessionControlMap on a null object", new Object[0]);
        } else {
            this.cmdSessionControlMap.get(str).setLocalDeviceInfo(isSameAccountToJson);
        }
    }

    public String getMacById(String str) {
        return this.mDeviceManager.getMacById(str);
    }

    public void b(long j2) {
        Logger.i("MiPlayAudioService", "updatePosition.", new Object[0]);
        synchronized (this.B) {
            this.W = j2;
            Logger.i("MiPlayAudioService", "mPosition:" + this.W, new Object[0]);
        }
    }

    public void getCurrentDeviceCache() {
        Logger.i("MiPlayAudioService", "getCurrentDeviceCache.", new Object[0]);
        if (this.mDeviceManager.getMiDeviceList() != null) {
            Iterator<MiDevice> it = this.mDeviceManager.getMiDeviceList().iterator();
            while (it.hasNext()) {
                Logger.d("MiPlayAudioService", "currentDeviceCache:" + it.next().toString(), new Object[0]);
            }
        }
    }

    public void l(MiDevice miDevice) {
        int localPlayState = getLocalPlayState();
        Logger.i("MiPlayAudioService", "playState:" + localPlayState, new Object[0]);
        if (localPlayState == 2 || localPlayState == -1 || localPlayState == 0) {
            Logger.i("MiPlayAudioService", "current state_paused set to box", new Object[0]);
            if (this.cmdSessionControlMap.get(miDevice.getMac()) != null) {
                Logger.i("MiPlayAudioService", "pauseDevice.", new Object[0]);
                this.cmdSessionControlMap.get(miDevice.getMac()).pauseDevice();
            }
        }
    }

    public void m() {
        Logger.i("MiPlayAudioService", "mMiPlayClientMap size:" + this.c.size(), new Object[0]);
        for (Map.Entry<String, MiPlayClientInfo> entry : this.c.entrySet()) {
            Logger.i("MiPlayAudioService", "mMiPlayClientMap id:" + entry.getValue().a + "  callback:" + entry.getValue().b, new Object[0]);
        }
    }
}
