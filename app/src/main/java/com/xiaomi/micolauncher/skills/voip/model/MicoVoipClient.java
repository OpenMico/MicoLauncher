package com.xiaomi.micolauncher.skills.voip.model;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaomi.mico.base.utils.FileUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.mico.visual.algorithm.VisualAlgorithmStatus;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.DoorBellStartEvent;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.UbusManager;
import com.xiaomi.micolauncher.module.QuickSettingHelper;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDaoManager;
import com.xiaomi.micolauncher.skills.cmcc.ims.model.ImsCallLog;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.CameraEvent;
import com.xiaomi.micolauncher.skills.voip.view.VoipUiStarter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MicoVoipClient {
    public static final String ACTION_HANG_UP = "com.xiaomi.micolauncher.action.voip.hang_up";
    public static final String ACTION_PICK_UP = "com.xiaomi.micolauncher.action.voip.pick_up";
    public static final String ACTION_PLAY = "com.xiaomi.micolauncher.action.voip.play";
    public static final String ACTION_QUERY = "com.xiaomi.micolauncher.action.voip.query";
    public static final String AGORA_SDK_LOG = "agora";
    public static final String CU_SDK_LOG_PATH = "/sdcard/MicoLauncher/log";
    public static final String EXTRA_IS_FROM_QUERY = "isFromQuery";
    public static final String IMS_LOG_PATH = "/ims_log";
    public static final String MICOLAUNCHER_LOG = "log";
    public static final String MILINK_LOG = "milink";
    public static final String MILINK_LOG_DIR_ORIGIN = "/sdcard/Xiaomi/com.xiaomi.micolauncher/logs/com.xiaomi.micolauncher";
    public static final String MIMC_SDK_LOG = "mimc";
    public static final int MSG_FROM_LAUNCHER_CLIENT_CALL_BLACK_LIST = 65552;
    public static final int MSG_FROM_LAUNCHER_CLIENT_CALL_LOG_LIST = 65554;
    public static final int MSG_FROM_LAUNCHER_CLIENT_UPDATE_STATUS = 65545;
    public static final int MSG_FROM_LAUNCHER_CLIENT_VOIP_EVENT = 65541;
    public static final int MSG_FROM_LAUNCHER_CLIENT_VOIP_UBUS = 65543;
    public static final int MSG_TO_LAUNCHER_CLIENT_CALL_BLACK_LIST_COPY_SUCCESS = 65553;
    public static final int MSG_TO_LAUNCHER_CLIENT_CALL_LOG_COPY_SUCCESS = 65555;
    public static final int MSG_TO_LAUNCHER_CLIENT_VOIP_STOP = 65540;
    public static final int MSG_TO_LAUNCHER_CLIENT_VOIP_UBUS_RESPONSE = 65544;
    public static final String VOIP_SDK_LOG_DIR = "VoipSdk";
    public static final String VOIP_SDK_LOG_DIR_ORIGIN = "/sdcard/Xiaomi/com.xiaomi.micolauncher/VoipSdk";
    private static volatile MicoVoipClient f;
    private volatile boolean d;
    private Messenger e;
    private Context g;
    private CountDownLatch h;
    private CountDownLatch i;
    public volatile String ubusResponse;
    private static final long b = TimeUnit.SECONDS.toMillis(3);
    private static final long c = TimeUnit.SECONDS.toMillis(3);
    private static volatile boolean[] j = new boolean[1];
    private final String a = "ims_call_log";
    @SuppressLint({"HandlerLeak"})
    private Messenger k = new Messenger(new Handler() { // from class: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.2
        @Override // android.os.Handler
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message message) {
            switch (message.what) {
                case 65538:
                    VoipModel.getInstance().setVoipRunning(true);
                    MicoVoipClient.this.config();
                    EventBusRegistry.getEventBus().post(new VoipStartEvent());
                    break;
                case 65539:
                    L.micoVoip.i("MSG_TO_LAUNCHER_CLIENT_CHECK_FACE_DETECT,checkFaceDetect");
                    if (!MicoVoipClient.j[0]) {
                        MicoVoipClient.j[0] = true;
                        VoipUiStarter.checkFaceDetect(MicoVoipClient.this.g, (Intent) message.obj, MicoVoipClient.j);
                        break;
                    }
                    break;
                case 65540:
                    QuickSettingHelper.enable(MicoVoipClient.this.g, true);
                    UbusManager.getInstance().enable();
                    VoipModel.getInstance().setVoipRunning(false);
                    VisualAlgorithmStatus.visualStatus visualAlgoStatus = Camera2VisualRecognitionManager.getInstance(MicoVoipClient.this.g).getVisualAlgoStatus();
                    L.voip.i("BaseCallingActivity,onStop visualAlgoStatus:%s", visualAlgoStatus);
                    if (visualAlgoStatus == VisualAlgorithmStatus.visualStatus.PAUSED && VoipModel.getInstance().isVoipTriggerClosingVisualAlgorithm()) {
                        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EventBusRegistry.getEventBus().post(new CameraRelatedSwitchEvent(CameraRelatedSwitchEvent.event.OPEN));
                                L.voip.i("BaseCallingActivity,post CameraRelatedSwitchEvent.event.OPEN event");
                                VoipModel.getInstance().setIsVoipTriggerClosingVisualAlgorithm(false);
                            }
                        }, MicoVoipClient.c);
                    }
                    if (MiotProvisionManagerWrapper.isMeshEnable()) {
                        MiotProvisionManagerWrapper.getInstance().resumeMesh();
                    }
                    EventBusRegistry.getEventBus().post(new VoipStopEvent());
                    break;
                case 65542:
                    SpeechManager.getInstance().ttsRequest(((Intent) message.obj).getStringExtra("ttsRequestStr"));
                    break;
                case MicoVoipClient.MSG_TO_LAUNCHER_CLIENT_VOIP_UBUS_RESPONSE /* 65544 */:
                    Intent intent = (Intent) message.obj;
                    L.micoVoip.i("voipUbusIntent:%s", intent);
                    if (intent != null) {
                        MicoVoipClient.this.ubusResponse = intent.getStringExtra("ubusResponse");
                    }
                    if (MicoVoipClient.this.i != null) {
                        MicoVoipClient.this.i.countDown();
                        break;
                    }
                    break;
                case MicoVoipClient.MSG_TO_LAUNCHER_CLIENT_CALL_BLACK_LIST_COPY_SUCCESS /* 65553 */:
                    L.micoVoip.i("MSG_TO_LAUNCHER_CLIENT_CALL_BLACK_LIST_COPY_SUCCESS,delete call black list db");
                    MicoVoipClient.this.f();
                    break;
                case MicoVoipClient.MSG_TO_LAUNCHER_CLIENT_CALL_LOG_COPY_SUCCESS /* 65555 */:
                    L.micoVoip.i("MSG_TO_LAUNCHER_CLIENT_CALL_LOG_COPY_SUCCESS,delete call log db");
                    MicoVoipClient.this.g();
                    break;
            }
            super.handleMessage(message);
        }
    });
    private ServiceConnection l = new ServiceConnection() { // from class: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.4
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.micoVoip.i("%s onServiceConnected, ComponentName:%s", "MicoVoipClient", componentName);
            MicoVoipClient.this.e = new Messenger(iBinder);
            MicoVoipClient.this.d = true;
            MicoVoipClient.this.h.countDown();
            MicoVoipClient.this.a(65537);
            L.micoVoip.i("queryCallLogDbAndSendMessageToMicoVoipService");
            MicoVoipClient.this.queryCallLogDbAndSendMessageToMicoVoipService();
            L.micoVoip.i("queryBlackListDbAndSendMessageToMicoVoipService");
            MicoVoipClient.this.queryBlackListDbAndSendMessageToMicoVoipService();
            L.micoVoip.i("deleteVoipLogAsync");
            MicoVoipClient.this.d();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.micoVoip.w("%s onServiceDisconnected, ComponentName:%s", "MicoVoipClient", componentName);
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            L.micoVoip.e("%s onBindingDied, ComponentName:%s", "MicoVoipClient", componentName);
            MicoVoipClient micoVoipClient = MicoVoipClient.this;
            micoVoipClient.a(micoVoipClient.g, 2);
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            L.micoVoip.w("%s onNullBinding, ComponentName:%s", "MicoVoipClient", componentName);
            MicoVoipClient.this.h();
        }
    };

    public static MicoVoipClient getInstance(Context context) {
        if (f == null) {
            synchronized (MicoVoipClient.class) {
                if (f == null) {
                    f = new MicoVoipClient(context);
                }
            }
        }
        return f;
    }

    private MicoVoipClient(Context context) {
        this.g = context.getApplicationContext();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.xiaomi.micolauncher.rebind.voip");
        context.registerReceiver(new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                MicoVoipClient.this.a(context2, 1);
            }
        }, intentFilter);
    }

    public void queryCallLogDbAndSendMessageToMicoVoipService() {
        List<ImsCallLog> imsCallLogs = ImsDaoManager.getInstance().getImsCallLogs();
        L.micoVoip.i("callLogList size:%d", Integer.valueOf(imsCallLogs.size()));
        if (!ContainerUtil.isEmpty(imsCallLogs)) {
            String json = Gsons.getGson().toJson(imsCallLogs);
            L.micoVoip.i("callLogJson:%s", json);
            final Bundle bundle = new Bundle();
            bundle.putString("callLogList", json);
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.3
                @Override // java.lang.Runnable
                public void run() {
                    L.micoVoip.i("send MSG_FROM_LAUNCHER_CLIENT_CALL_LOG_LIST to mico voip service");
                    MicoVoipClient.this.sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_CALL_LOG_LIST, bundle);
                }
            }, b);
        }
    }

    public void queryBlackListDbAndSendMessageToMicoVoipService() {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$S8iWO2ly5eiyBn9JZNp7Kk0bZBE
            @Override // java.lang.Runnable
            public final void run() {
                MicoVoipClient.this.c();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c() {
        /*
            r6 = this;
            com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmHelper r0 = new com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmHelper
            r0.<init>()
            java.util.List r1 = r0.b()     // Catch: Throwable -> 0x0052, all -> 0x004f
            r0.close()
            boolean r0 = com.xiaomi.mico.common.ContainerUtil.isEmpty(r1)
            if (r0 != 0) goto L_0x004e
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.micoVoip
            java.lang.String r2 = "callBlackListRealmObjectList size:%d,send MSG_FROM_LAUNCHER_CLIENT_CALL_BLACK_LIST to mico voip"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            int r5 = r1.size()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r4] = r5
            r0.i(r2, r3)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x0030:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0044
            java.lang.Object r2 = r1.next()
            com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject r2 = (com.xiaomi.micolauncher.skills.voip.model.CallBlackListRealmObject) r2
            java.lang.String r2 = r2.a()
            r0.add(r2)
            goto L_0x0030
        L_0x0044:
            com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$JswFpLB5PxMMAB-G4tR1jey9bp4 r1 = new com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$JswFpLB5PxMMAB-G4tR1jey9bp4
            r1.<init>()
            long r2 = com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.b
            com.xiaomi.mico.utils.ThreadUtil.postDelayedInMainThread(r1, r2)
        L_0x004e:
            return
        L_0x004f:
            r1 = move-exception
            r2 = 0
            goto L_0x0055
        L_0x0052:
            r2 = move-exception
            throw r2     // Catch: all -> 0x0054
        L_0x0054:
            r1 = move-exception
        L_0x0055:
            if (r2 == 0) goto L_0x0060
            r0.close()     // Catch: Throwable -> 0x005b
            goto L_0x0063
        L_0x005b:
            r0 = move-exception
            r2.addSuppressed(r0)
            goto L_0x0063
        L_0x0060:
            r0.close()
        L_0x0063:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient.c():void");
    }

    public /* synthetic */ void a(ArrayList arrayList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("callBlackList", arrayList);
        sendMessageToMicoVoipService(MSG_FROM_LAUNCHER_CLIENT_CALL_BLACK_LIST, bundle);
    }

    public void d() {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$nUUdaxRptGQL9rCmbGrweaDMNLM
            @Override // java.lang.Runnable
            public final void run() {
                MicoVoipClient.this.l();
            }
        });
    }

    public /* synthetic */ void l() {
        try {
            L.voip.i("begin to deleteVoipLog");
            e();
            L.voip.i("end deleteVoipLog");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void e() throws IOException {
        File[] listFiles;
        String str = Environment.getExternalStorageDirectory().getPath() + IMS_LOG_PATH;
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            FileUtils.deleteFile(str);
        }
        File file2 = new File(((File) Objects.requireNonNull(this.g.getApplicationContext().getExternalFilesDir(null))).getAbsolutePath());
        if (file2.exists() && file2.isDirectory() && (listFiles = file2.listFiles()) != null && listFiles.length > 0) {
            for (File file3 : listFiles) {
                if (file3 != null && file3.getName() != null) {
                    if (file3.getName().contains(VOIP_SDK_LOG_DIR) || file3.getName().contains(MIMC_SDK_LOG) || file3.getName().contains(AGORA_SDK_LOG)) {
                        FileUtils.deleteFile(file3.getAbsolutePath());
                    } else if (file3.getName().equals(MICOLAUNCHER_LOG) && file3.isDirectory()) {
                        File[] listFiles2 = file3.listFiles();
                        int length = listFiles2.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            File file4 = listFiles2[i];
                            if (!(file4 == null || file4.getName() == null || !file4.getName().contains(MILINK_LOG))) {
                                FileUtils.deleteFile(file4.getAbsolutePath());
                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        File file5 = new File(CU_SDK_LOG_PATH);
        if (file5.exists() && file5.isDirectory()) {
            FileUtils.deleteFile(CU_SDK_LOG_PATH);
        }
        File file6 = new File(VOIP_SDK_LOG_DIR_ORIGIN);
        if (file6.exists() && file6.isDirectory()) {
            FileUtils.deleteFile(VOIP_SDK_LOG_DIR_ORIGIN);
        }
        File file7 = new File(MILINK_LOG_DIR_ORIGIN);
        if (file7.exists() && file7.isDirectory()) {
            FileUtils.deleteFile(MILINK_LOG_DIR_ORIGIN);
        }
    }

    public void f() {
        ThreadUtil.getComputationThreadPool().submit($$Lambda$MicoVoipClient$yxIDkCbcop3nLcru6sOmetJlKLY.INSTANCE);
    }

    public static /* synthetic */ void k() {
        CallBlackListRealmHelper callBlackListRealmHelper = new CallBlackListRealmHelper();
        Throwable th = null;
        try {
            L.micoVoip.i("callBlackListRealmHelper.deleteCallBlackListDb");
            callBlackListRealmHelper.a();
            callBlackListRealmHelper.close();
        } catch (Throwable th2) {
            if (0 != 0) {
                try {
                    callBlackListRealmHelper.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
            } else {
                callBlackListRealmHelper.close();
            }
            throw th2;
        }
    }

    public void g() {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$CxvJZACEDQEdTbuDGw7W7kUiJds
            @Override // java.lang.Runnable
            public final void run() {
                MicoVoipClient.this.j();
            }
        });
    }

    public /* synthetic */ void j() {
        a(this.g, "ims_call_log");
    }

    private void a(Context context, String str) {
        L.micoVoip.i("deleteDatabaseFile %s", str);
        File file = new File(context.getApplicationInfo().dataDir + "/databases");
        if (new File(file, str).delete()) {
            L.micoVoip.i("%s database deleted", str);
        } else {
            L.micoVoip.i("Failed to delete %s database", str);
        }
        File file2 = new File(file, str + "-journal");
        if (!file2.exists()) {
            return;
        }
        if (file2.delete()) {
            L.micoVoip.i("%s database journal deleted", str);
        } else {
            L.micoVoip.i("Failed to delete %s database journal", str);
        }
    }

    public void a(int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        if (i == 65537) {
            obtain.replyTo = this.k;
        }
        if (this.d) {
            try {
                this.e.send(obtain);
                L.micoVoip.i("send message to mico voip service:%d", Integer.valueOf(i));
            } catch (RemoteException e) {
                L.micoVoip.e("sendMessageToMicoVoipService exception", e);
            }
        }
    }

    public void sendMessageToMicoVoipService(int i, Object obj) {
        Message obtain = Message.obtain();
        obtain.what = i;
        if (i == 65545) {
            obtain.obj = new Intent();
        } else {
            obtain.obj = obj;
        }
        if (this.d) {
            try {
                this.e.send(obtain);
                L.micoVoip.i("send message to mico voip service:%d", Integer.valueOf(i));
            } catch (RemoteException e) {
                L.micoVoip.e("sendMessageToMicoVoipService exception", e);
            }
        }
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.i = countDownLatch;
    }

    public void a(final Context context, int i) {
        h();
        this.e = null;
        Threads.getHeavyWorkHandler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.voip.model.-$$Lambda$MicoVoipClient$Wuppm7xzwKwco80TgeFDwgiNq2M
            @Override // java.lang.Runnable
            public final void run() {
                MicoVoipClient.this.a(context);
            }
        }, TimeUnit.SECONDS.toMillis(i));
    }

    /* renamed from: bindVoIPService */
    public synchronized void a(Context context) {
        if (!this.d) {
            this.h = new CountDownLatch(1);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MICOVOIP_SERVICE");
            intent.setComponent(new ComponentName(Constants.MICO_VOIP_PKG, "com.xiaomi.micovoip.service.MicoVoipService"));
            L.micoVoip.i("%s bindVoIPService, bindResult:%s", "MicoVoipClient", Boolean.valueOf(context.bindService(intent, this.l, 65)));
            try {
                this.h.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void h() {
        Context context;
        if (this.d && (context = this.g) != null) {
            context.unbindService(this.l);
            this.d = false;
        }
    }

    protected void config() {
        L.micoVoip.d("MicoVoipClient,config");
        i();
        QuickSettingHelper.enable(this.g, false);
        UbusManager.getInstance().disable();
        EventBusRegistry.getEventBus().post(new AlarmVoiceCloseEvent());
        DoorBellStartEvent doorBellStartEvent = new DoorBellStartEvent();
        doorBellStartEvent.action = false;
        EventBusRegistry.getEventBus().post(doorBellStartEvent);
        if (MiotProvisionManagerWrapper.isMeshEnable()) {
            MiotProvisionManagerWrapper.getInstance().stopMesh();
        }
        LockScreenSendBroadcast.sendCloseLockScreenEventBroadcast(this.g);
        Screen.getInstance().systemUiDismiss(0);
        Screen.getInstance().onResume();
        OpenPlatformModel.getInstance().quit(OpenPlatformModel.QuitType.NLP_QUIET, false);
    }

    private void i() {
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraEvent(CameraEvent cameraEvent) {
        L.voip.i("CallingActivity : onCameraEvent");
        NotificationHelper.notify(this.g, 1);
    }
}
