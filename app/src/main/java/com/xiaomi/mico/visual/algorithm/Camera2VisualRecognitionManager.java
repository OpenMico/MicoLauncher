package com.xiaomi.mico.visual.algorithm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.ai.api.MultiModal;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.mico.visual.algorithm.EventType;
import com.xiaomi.mico.visual.algorithm.IMicoCamera2Service;
import com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback;
import com.xiaomi.mico.visual.algorithm.VisualAlgorithmStatus;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.camera2.event.FaceInfoEvent;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.child.LockSetActivity;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public class Camera2VisualRecognitionManager {
    public static final int FIND_ADULT = 2;
    public static final int FIND_CHILD = 1;
    public static final int FIND_CHILD_ADULT = 3;
    @SuppressLint({"StaticFieldLeak"})
    private static volatile Camera2VisualRecognitionManager e = null;
    private static boolean f = false;
    private static final long l = TimeUnit.SECONDS.toMillis(3);
    private IMicoCamera2Service a;
    private final Context b;
    private GestureInfoEvent.GestureType g;
    private long i;
    private long j;
    private volatile boolean k;
    private Camera2VisualRecognitionController m;
    private final Object c = new Object();
    @GuardedBy("bindStateLock")
    private volatile boolean d = false;
    private volatile VisualAlgorithmStatus.visualStatus h = VisualAlgorithmStatus.visualStatus.IDLE;
    private final ServiceConnection n = new ServiceConnection() { // from class: com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager.3
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            L.camera2.e("%s onServiceDisconnected", "[Camera2VisualRecognitionManager]: ");
            try {
                if (Camera2VisualRecognitionManager.this.a != null) {
                    Camera2VisualRecognitionManager.this.a.unregisterCallback(Camera2VisualRecognitionManager.this.o);
                }
            } catch (RemoteException e2) {
                L.camera2.e("%s micoCamera2Service.unregisterCallback failed: %s", "[Camera2VisualRecognitionManager]: ", e2);
            }
            Camera2VisualRecognitionManager.this.a(false);
            Camera2VisualRecognitionManager.this.setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
            Camera2VisualRecognitionManager.this.m.reset();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.camera2.i("%s onServiceConnected", "[Camera2VisualRecognitionManager]: ");
            Camera2VisualRecognitionManager.this.a = IMicoCamera2Service.Stub.asInterface(iBinder);
            try {
                Camera2VisualRecognitionManager.this.a.registerCallback(Camera2VisualRecognitionManager.this.o);
                Camera2VisualRecognitionManager.this.a(true);
                if (Camera2VisualRecognitionManager.f) {
                    String serviceName = Camera2VisualRecognitionManager.this.getServiceName();
                    if (!TextUtils.isEmpty(serviceName)) {
                        L.camera2.i("!!!! get name: %s", serviceName);
                    }
                }
            } catch (RemoteException e2) {
                L.camera2.e("%s micoCamera2Service.registerCallback failed: %s", "[Camera2VisualRecognitionManager]: ", e2);
            }
        }
    };
    private IMicoCamera2ServiceCallback o = new AnonymousClass4();

    /* loaded from: classes3.dex */
    public enum UsePasswordToUnlock {
        FALSE,
        USE_PASSWORD,
        SET_PASSWORD
    }

    private Camera2VisualRecognitionManager(Context context) {
        this.b = context;
    }

    public static Camera2VisualRecognitionManager getInstance(Context context) {
        if (e == null && context != null) {
            L.camera2.e("%s instance is null !!", "[Camera2VisualRecognitionManager]: ");
            init(context);
        }
        return e;
    }

    public static void init(Context context) {
        L.camera2.d("%s init", "[Camera2VisualRecognitionManager]: ");
        if (e == null && context != null) {
            synchronized (Camera2VisualRecognitionManager.class) {
                if (e == null) {
                    e = new Camera2VisualRecognitionManager(context);
                    e.m = new Camera2VisualRecognitionController(context);
                }
            }
        }
    }

    public synchronized void setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus visualstatus) {
        L.camera2.d("%s setVisualAlgoStatus: %s", "[Camera2VisualRecognitionManager]: ", visualstatus.name());
        this.h = visualstatus;
    }

    public synchronized VisualAlgorithmStatus.visualStatus getVisualAlgoStatus() {
        L.camera2.d("%s getVisualAlgoStatus: %s", "[Camera2VisualRecognitionManager]: ", this.h.name());
        return this.h;
    }

    public synchronized VisualAlgorithmStatus.visualStatus SilentGetVisualAlgoStatus() {
        return this.h;
    }

    public void checkFaceDetect(final Context context, final Intent intent, final boolean[] zArr, final Camera2VisualRecognitionListener camera2VisualRecognitionListener) {
        ThreadUtil.verifyMainThread();
        Threads.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$hLLbcpn2BKVkOM-Qbk-yy2d2Bdo
            @Override // java.lang.Runnable
            public final void run() {
                Camera2VisualRecognitionManager.this.a(context, intent, zArr, camera2VisualRecognitionListener);
            }
        });
    }

    public /* synthetic */ void a(Context context, Intent intent, final boolean[] zArr, Camera2VisualRecognitionListener camera2VisualRecognitionListener) {
        int i = 30;
        while (true) {
            i--;
            if (i > 0) {
                VisualAlgorithmStatus.visualStatus visualAlgoStatus = getInstance(context).getVisualAlgoStatus();
                L.camera2.i("Camera2VisualRecognitionManager checking visualAlgoStatus... %s", visualAlgoStatus);
                int i2 = AnonymousClass5.c[visualAlgoStatus.ordinal()];
                if (i2 != 5) {
                    switch (i2) {
                        case 1:
                        case 2:
                            if (intent.getComponent() == null || !VoipModel.XIAOXUN_PACKAGE_NAME.equals(intent.getComponent().getPackageName())) {
                                ActivityLifeCycleManager.startActivityQuietly(context, intent);
                            } else {
                                if (Build.VERSION.SDK_INT >= 26) {
                                    context.startForegroundService(intent);
                                    L.voip.i("startForegroundService");
                                } else {
                                    context.startService(intent);
                                    L.voip.i("startService");
                                }
                                VoipModel.getInstance().setVoipRunning(true);
                            }
                            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    zArr[0] = false;
                                }
                            });
                            return;
                        default:
                            SystemClock.sleep(100L);
                            break;
                    }
                } else {
                    EventBusRegistry.getEventBus().post(new CameraRelatedSwitchEvent(CameraRelatedSwitchEvent.event.CLOSE_WITH_RELEASE_CAMERA));
                    L.camera2.i("Camera2VisualRecognitionManager checkFaceDetect post CLOSE_WITH_RELEASE_CAMERA event");
                    if (camera2VisualRecognitionListener != null) {
                        camera2VisualRecognitionListener.onClosingVisualAlgorithm();
                    }
                    SystemClock.sleep(100L);
                }
            } else {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        zArr[0] = false;
                        ToastUtil.showToast((int) R.string.video_chat_open_failed, 3);
                    }
                });
                L.camera2.e("check visualAlgoStatus failed");
                return;
            }
        }
    }

    public void unBindCamera2Service() {
        L.camera2.d("%s Start unbindCamera2Service", "[Camera2VisualRecognitionManager]: ");
        if (c()) {
            this.b.getApplicationContext().unbindService(this.n);
            a(false);
        }
    }

    @SuppressLint({"WrongConstant"})
    private void b() {
        L.camera2.d("%s Start bindCamera2Service", "[Camera2VisualRecognitionManager]: ");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xiaomi.mico.visual.algorithm", "com.xiaomi.mico.visual.algorithm.Camera2Service"));
        this.b.getApplicationContext().bindService(intent, this.n, 1);
    }

    private boolean c() {
        boolean z;
        synchronized (this.c) {
            z = this.d;
        }
        return z;
    }

    public void a(boolean z) {
        synchronized (this.c) {
            this.d = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager$4 */
    /* loaded from: classes3.dex */
    public class AnonymousClass4 extends IMicoCamera2ServiceCallback.Stub {
        AnonymousClass4() {
            Camera2VisualRecognitionManager.this = r1;
        }

        @Override // com.xiaomi.mico.visual.algorithm.IMicoCamera2ServiceCallback
        public void onResultCallBack(CallBackResult callBackResult) {
            int eventType = callBackResult.getEventType();
            int status = callBackResult.getStatus();
            final String visionRecognizeResult = callBackResult.getVisionRecognizeResult();
            EventType.EnumEvent enumEvent = EventType.EnumEvent.values()[eventType];
            boolean z = true;
            L.camera2.d("%s onResultCallBack event: %s, status: %s, visionRecognizeResult: %s", "[Camera2VisualRecognitionManager]: ", enumEvent, Integer.valueOf(status), visionRecognizeResult);
            switch (AnonymousClass5.a[enumEvent.ordinal()]) {
                case 1:
                    if (status != 1) {
                        z = false;
                    }
                    EventBusRegistry.getEventBus().post(new FaceInfoEvent(z));
                    return;
                case 2:
                    if (status == 1) {
                        L.camera2.d("%s status: FIND_CHILD", "[Camera2VisualRecognitionManager]: ");
                        CameraDetectionController.getManager().showEnterChildModeNotification(ActivityLifeCycleManager.getInstance().getTopActivity(), status);
                        return;
                    } else if (status == 3) {
                        L.camera2.d("%s status: FIND_CHILD_ADULT", "[Camera2VisualRecognitionManager]: ");
                        CameraDetectionController.getManager().showEnterChildModeNotification(ActivityLifeCycleManager.getInstance().getTopActivity(), status);
                        return;
                    } else {
                        L.camera2.e("%s not handle this status: %s", "[Camera2VisualRecognitionManager]: ", Integer.valueOf(status));
                        return;
                    }
                case 3:
                    GestureInfoEvent.GestureType gestureType = GestureInfoEvent.GestureType.values()[status];
                    L.camera2.d("%s GestureType: %s", "[Camera2VisualRecognitionManager]: ", gestureType);
                    if (Camera2VisualRecognitionManager.f) {
                        Camera2VisualRecognitionManager.this.a(gestureType);
                    }
                    if (gestureType != GestureInfoEvent.GestureType.Invalid) {
                        if (!Camera2VisualRecognitionManager.this.d() || Camera2VisualRecognitionManager.this.g != gestureType) {
                            Camera2VisualRecognitionManager.this.j = SystemClock.uptimeMillis();
                            Camera2VisualRecognitionManager camera2VisualRecognitionManager = Camera2VisualRecognitionManager.this;
                            camera2VisualRecognitionManager.i = camera2VisualRecognitionManager.j;
                            Camera2VisualRecognitionManager.this.g = gestureType;
                            EventBusRegistry.getEventBus().post(new GestureControlEvent(gestureType));
                            return;
                        }
                        L.camera2.i("two same gesture in short time  %s give up one", gestureType);
                        return;
                    }
                    return;
                case 4:
                    if (status == 1) {
                        ChildModeStorage.getInstance().quitChildMode(Camera2VisualRecognitionManager.this.b);
                    } else if (status == 0) {
                        Threads.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$4$xXBvrwzqV_1oN10ae94di0eA65U
                            @Override // java.lang.Runnable
                            public final void run() {
                                Camera2VisualRecognitionManager.AnonymousClass4.this.a(visionRecognizeResult);
                            }
                        });
                        L.camera2.d("%s check quit child mode failed", "[Camera2VisualRecognitionManager]: ");
                    }
                    Camera2VisualRecognitionManager.this.k = true;
                    L.camera2.i("quit child mode status=%s", Integer.valueOf(status));
                    return;
                case 5:
                    boolean z2 = status == 1;
                    VisualAlgorithmStatus.visualStatus visualAlgoStatus = Camera2VisualRecognitionManager.this.getVisualAlgoStatus();
                    L.camera2.d("%s EVENT_CAMERA_STATUS_CHANGED!", "[Camera2VisualRecognitionManager]: ");
                    L.camera2.d("%s cameraStatus: %s, visualStatus: %s", "[Camera2VisualRecognitionManager]: ", Boolean.valueOf(z2), visualAlgoStatus);
                    if (!z2) {
                        if (visualAlgoStatus == VisualAlgorithmStatus.visualStatus.STOPPING) {
                            Camera2VisualRecognitionManager.this.setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
                            return;
                        } else if (visualAlgoStatus == VisualAlgorithmStatus.visualStatus.INITIALIZING || visualAlgoStatus == VisualAlgorithmStatus.visualStatus.RESUMING) {
                            Camera2VisualRecognitionManager.this.requestUnbindCamera2Service();
                            Camera2VisualRecognitionManager.this.setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
                            return;
                        } else if (visualAlgoStatus == VisualAlgorithmStatus.visualStatus.PAUSING) {
                            Camera2VisualRecognitionManager.this.setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.PAUSED);
                            return;
                        } else {
                            return;
                        }
                    } else if (visualAlgoStatus == VisualAlgorithmStatus.visualStatus.INITIALIZING || visualAlgoStatus == VisualAlgorithmStatus.visualStatus.RESUMING) {
                        Camera2VisualRecognitionManager.this.setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.RUNNING);
                        return;
                    } else {
                        return;
                    }
                case 6:
                    MultiModal.VisionRecognizeResult visionRecognizeResult2 = null;
                    try {
                        visionRecognizeResult2 = (MultiModal.VisionRecognizeResult) APIUtils.fromJsonString(visionRecognizeResult, MultiModal.VisionRecognizeResult.class);
                    } catch (IOException e) {
                        L.camera2.e("%s APIUtils.fromJsonString failed: %s", "[Camera2VisualRecognitionManager]: ", e);
                    }
                    SpeechManager.getInstance().postVisionRecognizeResultEvent(visionRecognizeResult2);
                    return;
                case 7:
                    L.camera2.d("%s EVENT_ACTION_ACK action type: %s", "[Camera2VisualRecognitionManager]: ", Camera2ControlAction.EnumAction.values()[status].name());
                    Camera2VisualRecognitionManager.this.k = false;
                    return;
                default:
                    return;
            }
        }

        public /* synthetic */ void a(String str) {
            if (str.equals(UsePasswordToUnlock.USE_PASSWORD.toString())) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$4$WrOTDXJkw5wlg2be-pvpxOKdi_o
                    @Override // java.lang.Runnable
                    public final void run() {
                        Camera2VisualRecognitionManager.AnonymousClass4.this.a();
                    }
                });
            } else if (str.equals(UsePasswordToUnlock.SET_PASSWORD.toString())) {
                ActivityLifeCycleManager.getInstance().gotoMainActivity(Camera2VisualRecognitionManager.this.b);
                Intent intent = new Intent(Camera2VisualRecognitionManager.this.b, LockSetActivity.class);
                intent.putExtra(LockSetActivity.EXTRA_QUIT_CHILD_MODE, true);
                ActivityLifeCycleManager.startActivityQuietly(intent);
            }
        }

        public /* synthetic */ void a() {
            new LockAppDialog(Camera2VisualRecognitionManager.this.b, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$4$37bL5_RkCZOAPcnA2zWe6yxgHWQ
                @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
                public final void onPassCorrect() {
                    Camera2VisualRecognitionManager.AnonymousClass4.this.b();
                }
            }).show();
        }

        public /* synthetic */ void b() {
            ChildModeStorage.getInstance().quitChildMode(Camera2VisualRecognitionManager.this.b);
        }
    }

    public void a(GestureInfoEvent.GestureType gestureType) {
        switch (gestureType) {
            case OK:
                ToastUtil.showCustomToast(R.string.Gesture_Ok, 500);
                return;
            case FastBackward:
                ToastUtil.showCustomToast(R.string.Gesture_FastBackward, 500);
                return;
            case FastForward:
                ToastUtil.showCustomToast(R.string.Gesture_FastForward, 500);
                return;
            case Stop:
                ToastUtil.showCustomToast(R.string.Gesture_Stop, 500);
                return;
            case ThumbsUp:
                ToastUtil.showCustomToast(R.string.Gesture_ThumbsUp, 500);
                return;
            case Invalid:
                ToastUtil.showCustomToast(R.string.Gesture_Invalid, 500);
                return;
            default:
                return;
        }
    }

    public void startAction(final Camera2ControlAction.EnumAction enumAction) {
        L.camera2.d("%s request Start Action: %s", "[Camera2VisualRecognitionManager]: ", enumAction.name());
        Threads.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$o453tEyUPm3iP-shYYX-TY5luGY
            @Override // java.lang.Runnable
            public final void run() {
                Camera2VisualRecognitionManager.this.b(enumAction);
            }
        });
    }

    public /* synthetic */ void b(Camera2ControlAction.EnumAction enumAction) {
        if (!c()) {
            b();
        }
        switch (getVisualAlgoStatus()) {
            case IDLE:
                setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.INITIALIZING);
                break;
            case PAUSED:
                setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.RESUMING);
                break;
        }
        int i = 30;
        while (true) {
            i--;
            if (i > 0) {
                L.camera2.i("%s checking isServiceConnected.", "[Camera2VisualRecognitionManager]: ");
                if (c()) {
                    try {
                        this.a.requestStartAction(enumAction.ordinal());
                    } catch (RemoteException e2) {
                        L.camera2.e("%s requestSTartAction failed: %s", "[Camera2VisualRecognitionManager]: ", e2);
                        switch (getVisualAlgoStatus()) {
                            case RESUMING:
                                setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.PAUSED);
                                break;
                            case INITIALIZING:
                                setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
                                break;
                        }
                    }
                    L.camera2.d("%s requestSTartAction ok !!!", "[Camera2VisualRecognitionManager]: ");
                    return;
                }
                SystemClock.sleep(100L);
            } else {
                switch (getVisualAlgoStatus()) {
                    case RESUMING:
                        setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.PAUSED);
                        break;
                    case INITIALIZING:
                        setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
                        break;
                }
                L.camera2.e("%s check failed, isServiceConnected: %s", "[Camera2VisualRecognitionManager]: ", Boolean.valueOf(c()));
                return;
            }
        }
    }

    /* renamed from: com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager$5 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a;

        static {
            try {
                c[VisualAlgorithmStatus.visualStatus.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                c[VisualAlgorithmStatus.visualStatus.PAUSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                c[VisualAlgorithmStatus.visualStatus.RESUMING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                c[VisualAlgorithmStatus.visualStatus.INITIALIZING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                c[VisualAlgorithmStatus.visualStatus.RUNNING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            b = new int[GestureInfoEvent.GestureType.values().length];
            try {
                b[GestureInfoEvent.GestureType.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                b[GestureInfoEvent.GestureType.FastBackward.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                b[GestureInfoEvent.GestureType.FastForward.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                b[GestureInfoEvent.GestureType.Stop.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                b[GestureInfoEvent.GestureType.ThumbsUp.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                b[GestureInfoEvent.GestureType.Invalid.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            a = new int[EventType.EnumEvent.values().length];
            try {
                a[EventType.EnumEvent.EVENT_DISTANCE_DETECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[EventType.EnumEvent.EVENT_QUICK_ENTRY_CHILD_MODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[EventType.EnumEvent.EVENT_GESTURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[EventType.EnumEvent.EVENT_QUIT_CHILD_MODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[EventType.EnumEvent.EVENT_CAMERA_STATUS_CHANGED.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                a[EventType.EnumEvent.EVENT_CHILD_CONTENT_RECOMMENDATION.ordinal()] = 6;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                a[EventType.EnumEvent.EVENT_ACTION_ACK.ordinal()] = 7;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    public void sendRequestCmd(final Camera2ControlAction.EnumAction enumAction) {
        L.camera2.d("%s request Start Action: %s", "[Camera2VisualRecognitionManager]: ", enumAction.name());
        Threads.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.mico.visual.algorithm.-$$Lambda$Camera2VisualRecognitionManager$es68eLU4V1BSZPSxeHZ1aUPaVSQ
            @Override // java.lang.Runnable
            public final void run() {
                Camera2VisualRecognitionManager.this.a(enumAction);
            }
        });
    }

    public /* synthetic */ void a(Camera2ControlAction.EnumAction enumAction) {
        if (!c()) {
            L.camera2.d("service not Bind !!");
            return;
        }
        try {
            this.a.requestStartAction(enumAction.ordinal());
        } catch (RemoteException e2) {
            L.camera2.e("%s requestStartAction failed: %s", "[Camera2VisualRecognitionManager]: ", e2);
        }
    }

    public boolean d() {
        return SystemClock.uptimeMillis() - this.i <= l;
    }

    public void stopAction(Camera2ControlAction.EnumAction enumAction) {
        L.camera2.d("%s request Stop Action: %s", "[Camera2VisualRecognitionManager]: ", enumAction.name());
        if (!c()) {
            L.camera2.e("%s Camera2 Service is not bind!", "[Camera2VisualRecognitionManager]: ");
            return;
        }
        try {
            this.a.requestStopAction(enumAction.ordinal());
        } catch (RemoteException e2) {
            L.camera2.e("%s requestStopAction failed: %s", "[Camera2VisualRecognitionManager]: ", e2);
        }
    }

    public String getServiceName() {
        try {
            return this.a.getName();
        } catch (RemoteException unused) {
            L.camera2.e("%s getServiceName failed", "[Camera2VisualRecognitionManager]: ");
            return null;
        }
    }

    public void destroy() {
        if (c()) {
            unBindCamera2Service();
            this.a = null;
        }
    }

    public void requestStartAction(Camera2ControlAction.EnumAction enumAction) {
        this.m.requestStartAction(enumAction);
    }

    public void requestStopAction(Camera2ControlAction.EnumAction enumAction) {
        this.m.requestStopAction(enumAction);
    }

    public void requestUnbindCamera2Service() {
        this.m.requestUnbindCamera2Service();
    }

    public boolean isDetectingFace() {
        return this.k;
    }
}
