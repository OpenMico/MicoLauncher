package com.xiaomi.miplay.mylibrary.external;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.xiaomi.miplay.mylibrary.MiPlayAudioService;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.miplay.mylibrary.utils.Constant;
import com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient;
import com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback;
import com.xiaomi.miplay.phoneclientsdk.info.MediaMetaData;
import com.xiaomi.miplay.phoneclientsdk.info.PropertiesInfo;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class MiplayAccessExternalService extends Service {
    private MiPlayAudioService b;
    private b c;
    private HandlerThread d;
    private HashMap<String, MiPlayExternalClientInfo> e;
    private MediaControlImpl f;
    private a g;
    private boolean a = false;
    private String h = "";
    private int i = -1;
    private ServiceConnection j = new ServiceConnection() { // from class: com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService.1
        @Override // android.content.ServiceConnection
        @RequiresApi(api = 23)
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.i("MiplayMp_AccessExternalService", "onServiceConnected", new Object[0]);
            MiplayAccessExternalService.this.b = ((MiPlayAudioService.LocalBinder) iBinder).getService();
            MiplayAccessExternalService.this.f.setAudioService(MiplayAccessExternalService.this.b);
            MiplayAccessExternalService.this.b.setMediaControl(MiplayAccessExternalService.this.f);
            if (MiplayAccessExternalService.this.f == null) {
                Logger.i("MiplayMp_AccessExternalService", "mediaControl is null.", new Object[0]);
                return;
            }
            int circulateMode = MiplayAccessExternalService.this.f.getCirculateMode();
            Logger.i("MiplayMp_AccessExternalService", "getCirculateMode:" + circulateMode, new Object[0]);
            Logger.i("MiplayMp_AccessExternalService", "mCirculateMode:" + MiplayAccessExternalService.this.i, new Object[0]);
            if (MiplayAccessExternalService.this.i != circulateMode) {
                try {
                    for (Map.Entry entry : MiplayAccessExternalService.this.e.entrySet()) {
                        if (TextUtils.equals(((MiPlayExternalClientInfo) entry.getValue()).a, Constant.PACKAGENAME_QIYI_VIDEO) && TextUtils.isEmpty(MiplayAccessExternalService.this.h)) {
                            Logger.i("MiplayMp_AccessExternalService", "callback id com.qiyi.video", new Object[0]);
                            ((MiPlayExternalClientInfo) entry.getValue()).callback.onCirculateModeChange(circulateMode);
                            ((MiPlayExternalClientInfo) entry.getValue()).callback.onConnectMirrorSuccess();
                            break;
                        }
                        if (TextUtils.equals(((MiPlayExternalClientInfo) entry.getValue()).a, Constant.PACKAGENAME_XIAOMI_VIDEO) && TextUtils.isEmpty(MiplayAccessExternalService.this.h)) {
                            Logger.i("MiplayMp_AccessExternalService", "callback id com.miui.video", new Object[0]);
                            ((MiPlayExternalClientInfo) entry.getValue()).callback.onCirculateModeChange(circulateMode);
                            ((MiPlayExternalClientInfo) entry.getValue()).callback.onConnectMirrorSuccess();
                            break;
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.i("MiplayMp_AccessExternalService", "onServiceDisconnected", new Object[0]);
            MiplayAccessExternalService.this.b = null;
            MiplayAccessExternalService.this.f.setAudioService(MiplayAccessExternalService.this.b);
        }
    };
    private IMiPlayExternalClient k = new IMiPlayExternalClient.Stub() { // from class: com.xiaomi.miplay.mylibrary.external.MiplayAccessExternalService.2
        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int initAsync(String str, IMiPlayExternalClientCallback iMiPlayExternalClientCallback) throws RemoteException {
            Logger.i("MiplayMp_AccessExternalService", "initAsync", new Object[0]);
            MiplayAccessExternalService.this.c.obtainMessage(0, new MiPlayExternalClientInfo(str, iMiPlayExternalClientCallback)).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int play(String str, MediaMetaData mediaMetaData) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(2, new Object[]{str, mediaMetaData}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int unInit(String str) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(1, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int playState(String str, int i) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(7, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int onBufferState(String str, int i) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(9, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int sendPropertiesInfo(String str, PropertiesInfo propertiesInfo) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(10, new Object[]{str, propertiesInfo}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int stop(String str) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(3, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int pause(String str) throws RemoteException {
            Logger.d("MiplayMp_AccessExternalService", "IMiPlayExternalClient pause", new Object[0]);
            MiplayAccessExternalService.this.c.obtainMessage(4, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int resume(String str) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(5, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int seek(String str, long j) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(6, new Object[]{str, Long.valueOf(j)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int setVolume(String str, double d) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(8, new Object[]{str, Double.valueOf(d)}).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        @RequiresApi(api = 23)
        public int getPosition(String str) throws RemoteException {
            Logger.i("MiplayMp_AccessExternalService", "getPosition.", new Object[0]);
            if (MiplayAccessExternalService.this.f != null) {
                return MiplayAccessExternalService.this.f.getPosition(str);
            }
            Logger.i("MiplayMp_AccessExternalService", "mediaControl is null.", new Object[0]);
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int getVolume(String str) throws RemoteException {
            MiplayAccessExternalService.this.c.obtainMessage(12, str).sendToTarget();
            return 0;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        @RequiresApi(api = 23)
        public String getSourceName(String str) throws RemoteException {
            Logger.i("MiplayMp_AccessExternalService", "getSourceName.", new Object[0]);
            if (MiplayAccessExternalService.this.f != null) {
                return MiplayAccessExternalService.this.f.getSourceName(str);
            }
            Logger.i("MiplayMp_AccessExternalService", "mediaControl is null.", new Object[0]);
            return "";
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        @RequiresApi(api = 23)
        public int getCirculateMode() throws RemoteException {
            Logger.i("MiplayMp_AccessExternalService", "getCirculateMode.", new Object[0]);
            if (MiplayAccessExternalService.this.f == null) {
                Logger.i("MiplayMp_AccessExternalService", "mediaControl is null.", new Object[0]);
                return -1;
            }
            int circulateMode = MiplayAccessExternalService.this.f.getCirculateMode();
            Logger.i("MiplayMp_AccessExternalService", "getCirculateMode:" + circulateMode, new Object[0]);
            MiplayAccessExternalService.this.i = circulateMode;
            return circulateMode;
        }

        @Override // com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClient
        public int cancelCirculate(String str, int i) throws RemoteException {
            Logger.i("MiplayMp_AccessExternalService", "cancelCirculate id:" + str + " :mode:" + i, new Object[0]);
            MiplayAccessExternalService.this.c.obtainMessage(13, new Object[]{str, Integer.valueOf(i)}).sendToTarget();
            return 0;
        }
    };

    public HashMap<String, MiPlayExternalClientInfo> getMiPlayExternalClientMap() {
        return this.e;
    }

    public String getmPlayPackageName() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a implements IBinder.DeathRecipient {
        private a() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.e("MiplayMp_AccessExternalService", "binderDied before");
            Logger.i("MiplayMp_AccessExternalService", "mMiPlayExternalClientMap:" + MiplayAccessExternalService.this.e.size(), new Object[0]);
            if (MiplayAccessExternalService.this.e != null && MiplayAccessExternalService.this.e.size() == 1) {
                for (Map.Entry entry : MiplayAccessExternalService.this.e.entrySet()) {
                    Logger.i("MiplayMp_AccessExternalService", "package:" + ((MiPlayExternalClientInfo) entry.getValue()).a, new Object[0]);
                    if (((MiPlayExternalClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_GALLERY) || ((MiPlayExternalClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_XIAOMI_VIDEO) || ((MiPlayExternalClientInfo) entry.getValue()).a.equals(Constant.PACKAGENAME_QIYI_VIDEO)) {
                        if (((MiPlayExternalClientInfo) entry.getValue()).callback != null) {
                            ((MiPlayExternalClientInfo) entry.getValue()).callback.asBinder().unlinkToDeath(MiplayAccessExternalService.this.g, 0);
                            Logger.i("MiplayMp_AccessExternalService", "releaseClient finish", new Object[0]);
                        }
                        MiplayAccessExternalService.this.e.remove(((MiPlayExternalClientInfo) entry.getValue()).a);
                    }
                }
            }
            Log.e("MiplayMp_AccessExternalService", "binderDied after");
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Logger.i("MiplayMp_AccessExternalService", "onCreate", new Object[0]);
        this.f = new MediaControlImpl(this);
        this.a = bindService(new Intent(this, MiPlayAudioService.class), this.j, 1);
        Logger.i("MiplayMp_AccessExternalService", "bindService :" + this.a, new Object[0]);
        this.d = new HandlerThread(getClass().getName());
        this.d.start();
        this.c = new b(this.d.getLooper(), this);
        this.e = new HashMap<>();
        MediaControlCallbackImpl.setExternalService(this);
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i("MiplayMp_AccessExternalService", "onBind: --- " + intent.getAction());
        if (TextUtils.equals(intent.getAction(), "COM.XIAOMI.MIPLAY.ACTION.EXTERNAL_CIRCULATION_CLIENT_SERVICE")) {
            return this.k.asBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Logger.i("MiplayMp_AccessExternalService", "onDestroy", new Object[0]);
        if (this.a) {
            unbindService(this.j);
            this.a = false;
            this.b = null;
        }
    }

    /* loaded from: classes4.dex */
    private static class b extends Handler {
        private final WeakReference<MiplayAccessExternalService> a;
        private String b = "";

        public b(Looper looper, MiplayAccessExternalService miplayAccessExternalService) {
            super(looper);
            this.a = new WeakReference<>(miplayAccessExternalService);
        }

        @Override // android.os.Handler
        @RequiresApi(api = 23)
        public void handleMessage(@NonNull Message message) {
            MiplayAccessExternalService miplayAccessExternalService = this.a.get();
            if (miplayAccessExternalService == null) {
                Logger.i("MiplayMp_AccessExternalService", "mirrorService is null", new Object[0]);
                return;
            }
            Logger.i("MiplayMp_AccessExternalService", "handleMessage type:" + message.what, new Object[0]);
            switch (message.what) {
                case 0:
                    Logger.i("MiplayMp_AccessExternalService", "msg_init.", new Object[0]);
                    MiPlayExternalClientInfo miPlayExternalClientInfo = (MiPlayExternalClientInfo) message.obj;
                    this.b = miPlayExternalClientInfo.a;
                    miplayAccessExternalService.a(miPlayExternalClientInfo);
                    return;
                case 1:
                    Logger.i("MiplayMp_AccessExternalService", "msg_uninit.", new Object[0]);
                    String str = (String) message.obj;
                    this.b = str;
                    miplayAccessExternalService.e.remove(str);
                    return;
                case 2:
                    Logger.i("MiplayMp_AccessExternalService", "msg_play.", new Object[0]);
                    Object[] objArr = (Object[]) message.obj;
                    String str2 = (String) objArr[0];
                    miplayAccessExternalService.h = str2;
                    miplayAccessExternalService.f.play(str2, (MediaMetaData) objArr[1]);
                    return;
                case 3:
                    miplayAccessExternalService.f.stop((String) message.obj);
                    return;
                case 4:
                    miplayAccessExternalService.f.pause((String) message.obj);
                    return;
                case 5:
                    miplayAccessExternalService.f.resume((String) message.obj);
                    return;
                case 6:
                    Object[] objArr2 = (Object[]) message.obj;
                    long longValue = ((Long) objArr2[1]).longValue();
                    miplayAccessExternalService.f.seek((String) objArr2[0], longValue);
                    return;
                case 7:
                    Logger.i("MiplayMp_AccessExternalService", "msg_playstate.", new Object[0]);
                    Object[] objArr3 = (Object[]) message.obj;
                    int intValue = ((Integer) objArr3[1]).intValue();
                    miplayAccessExternalService.f.playState((String) objArr3[0], intValue);
                    return;
                case 8:
                    Object[] objArr4 = (Object[]) message.obj;
                    double doubleValue = ((Double) objArr4[1]).doubleValue();
                    miplayAccessExternalService.f.setVolume((String) objArr4[0], doubleValue);
                    return;
                case 9:
                    Logger.i("MiplayMp_AccessExternalService", "msg_onbufferstate.", new Object[0]);
                    Object[] objArr5 = (Object[]) message.obj;
                    int intValue2 = ((Integer) objArr5[1]).intValue();
                    miplayAccessExternalService.f.onBufferState((String) objArr5[0], intValue2);
                    return;
                case 10:
                    Logger.i("MiplayMp_AccessExternalService", "msg_sendpropertiesinfo.", new Object[0]);
                    Object[] objArr6 = (Object[]) message.obj;
                    miplayAccessExternalService.f.sendPropertiesInfo((String) objArr6[0], (PropertiesInfo) objArr6[1]);
                    return;
                case 11:
                    miplayAccessExternalService.f.getPosition((String) message.obj);
                    return;
                case 12:
                    miplayAccessExternalService.f.getVolume((String) message.obj);
                    return;
                case 13:
                    Object[] objArr7 = (Object[]) message.obj;
                    int intValue3 = ((Integer) objArr7[1]).intValue();
                    miplayAccessExternalService.f.cancelCirculate((String) objArr7[0], intValue3);
                    return;
                default:
                    Log.i("MiplayMp_AccessExternalService", "unknown msg!");
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MiPlayExternalClientInfo miPlayExternalClientInfo) {
        if (miPlayExternalClientInfo.callback != null) {
            this.e.put(miPlayExternalClientInfo.a, miPlayExternalClientInfo);
            Logger.i("MiplayMp_AccessExternalService", "ClientInfo put :  id " + miPlayExternalClientInfo.a, new Object[0]);
            try {
                miPlayExternalClientInfo.callback.onInitSuccess();
                Logger.i("MiplayMp_AccessExternalService", "miplay init end", new Object[0]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.g = new a();
        HashMap<String, MiPlayExternalClientInfo> hashMap = this.e;
        if (hashMap != null) {
            try {
                for (Map.Entry<String, MiPlayExternalClientInfo> entry : hashMap.entrySet()) {
                    if (entry.getValue().callback != null) {
                        entry.getValue().callback.asBinder().linkToDeath(this.g, 0);
                        Logger.i("MiplayMp_AccessExternalService", "linkToDeath " + entry.getValue().a, new Object[0]);
                    }
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }
}
