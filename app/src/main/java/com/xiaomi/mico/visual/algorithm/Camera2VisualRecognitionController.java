package com.xiaomi.mico.visual.algorithm;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.visual.algorithm.VisualAlgorithmStatus;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Camera2VisualRecognitionController {
    private final Context b;
    private final String a = "[Camera2VisualRecognitionController]: ";
    private final c c = new c();
    private final a d = new a();
    private final b e = new b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Camera2VisualRecognitionController(Context context) {
        this.b = context;
    }

    private synchronized void a() {
        if (!this.d.a()) {
            this.d.a(true);
            Threads.getComputationThreadPool().submit(this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a implements Runnable {
        private volatile boolean b;
        private final long c;

        private a() {
            this.c = TimeUnit.MILLISECONDS.toMillis(100L);
        }

        @Override // java.lang.Runnable
        public void run() {
            L.camera2.i("%s start actionHandleWork", "[Camera2VisualRecognitionController]: ");
            int i = 0;
            while (true) {
                if (!Camera2VisualRecognitionController.this.c.a()) {
                    break;
                } else if (!Camera2VisualRecognitionController.this.c.a(Camera2VisualRecognitionController.this.e)) {
                    SystemClock.sleep(this.c);
                    i++;
                    if (i >= 50) {
                        L.camera2.d("%s check visualAlgoStatus timeout, visualAlgoStatus: %s", "[Camera2VisualRecognitionController]: ", Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).getVisualAlgoStatus());
                        a(false);
                        break;
                    }
                } else {
                    i = 0;
                }
            }
            L.camera2.i("%s stop actionHandleWork", "[Camera2VisualRecognitionController]: ");
        }

        synchronized boolean a() {
            return this.b;
        }

        synchronized void a(boolean z) {
            this.b = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class c {
        private final Map<Camera2ControlAction.EnumAction, Boolean> b;
        private volatile boolean c;
        private final Object d;

        private c() {
            this.b = new LinkedHashMap();
            this.c = false;
            this.d = new Object();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Camera2ControlAction.EnumAction enumAction, Boolean bool) {
            synchronized (this.d) {
                this.b.remove(enumAction);
                this.b.put(enumAction, bool);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a(b bVar) {
            VisualAlgorithmStatus.visualStatus SilentGetVisualAlgoStatus = Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).SilentGetVisualAlgoStatus();
            if (SilentGetVisualAlgoStatus == VisualAlgorithmStatus.visualStatus.STOPPING && Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).getServiceName() == null) {
                L.camera2.e("%s catch RemoteException during unbind", "[Camera2VisualRecognitionController]: ");
                Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.IDLE);
                SilentGetVisualAlgoStatus = VisualAlgorithmStatus.visualStatus.IDLE;
            }
            switch (SilentGetVisualAlgoStatus) {
                case INITIALIZING:
                case RESUMING:
                case PAUSING:
                case STOPPING:
                    bVar.a = false;
                    bVar.b = false;
                    bVar.c = false;
                    break;
                case RUNNING:
                case PAUSED:
                case IDLE:
                    bVar.a = true;
                    bVar.b = true;
                    bVar.c = true;
                    break;
            }
            if (!this.c) {
                synchronized (this.d) {
                    if (this.b.isEmpty()) {
                        return true;
                    }
                    Camera2ControlAction.EnumAction key = this.b.entrySet().iterator().next().getKey();
                    Boolean bool = this.b.get(key);
                    if (bool == null) {
                        return true;
                    }
                    if ((!bVar.a || !bool.booleanValue()) && (!bVar.b || bool.booleanValue())) {
                        return false;
                    }
                    this.b.remove(key);
                    if (!bool.booleanValue() && key == Camera2ControlAction.EnumAction.ACTION_RELEASE_CAMERA && SilentGetVisualAlgoStatus == VisualAlgorithmStatus.visualStatus.RUNNING) {
                        Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.PAUSING);
                    }
                    if (bool.booleanValue()) {
                        Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).startAction(key);
                    } else {
                        Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).stopAction(key);
                    }
                    L.camera2.i("%s handle action success, action:%s, enable:%s", "[Camera2VisualRecognitionController]: ", key, bool);
                    return true;
                }
            } else if (!bVar.c) {
                return false;
            } else {
                if (Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).getVisualAlgoStatus() != VisualAlgorithmStatus.visualStatus.IDLE) {
                    Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).setVisualAlgoStatus(VisualAlgorithmStatus.visualStatus.STOPPING);
                }
                Camera2VisualRecognitionManager.getInstance(Camera2VisualRecognitionController.this.b).unBindCamera2Service();
                this.c = false;
                L.camera2.i("%s handle action success, action:unBindCamera2Service", "[Camera2VisualRecognitionController]: ");
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            synchronized (this.d) {
                this.b.clear();
                this.c = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a() {
            synchronized (this.d) {
                if (this.b.isEmpty() && !this.c) {
                    Camera2VisualRecognitionController.this.d.a(false);
                    return false;
                }
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b {
        boolean a;
        boolean b;
        boolean c;

        private b() {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void requestStartAction(Camera2ControlAction.EnumAction enumAction) {
        this.c.a(enumAction, (Boolean) true);
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void requestStopAction(Camera2ControlAction.EnumAction enumAction) {
        this.c.a(enumAction, (Boolean) false);
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void requestUnbindCamera2Service() {
        L.camera2.i("%s requestUnbindCamera2Service", "[Camera2VisualRecognitionController]: ");
        this.c.a(true);
        a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void reset() {
        L.camera2.i("%s reset", "[Camera2VisualRecognitionController]: ");
        this.c.a(false);
    }
}
