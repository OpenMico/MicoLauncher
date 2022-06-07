package com.xiaomi.micolauncher.module.faceDetection;

import android.content.Context;
import android.view.View;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.mico.visual.algorithm.Camera2VisualRecognitionManager;
import com.xiaomi.mico.visual.algorithm.FaceDetectDistanceDialog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.application.controllers.ControllerBase;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.QuitChildModeDialogEvent;
import com.xiaomi.micolauncher.common.event.ScreenOnOffEvent;
import com.xiaomi.micolauncher.common.event.ScreenSaverSwitchEvent;
import com.xiaomi.micolauncher.common.event.SystemCameraEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.VideoMonitorHelper;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.camera2.event.FaceInfoEvent;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraRelatedSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.ChildContentRecommendCalculateEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.cameradetection.event.SupportGestureDialogEvent;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.update.UpdateBeginEvent;
import com.xiaomi.micolauncher.skills.update.UpdateCompleteEvent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.IAppCommandProcessor;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.micolauncher.skills.voip.utils.CameraStatusHelper;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public class FaceDistanceDetectController extends ControllerBase {
    private static final long a = TimeUnit.SECONDS.toMillis(15);
    private FaceDetectDistanceDialog b;
    private Context c;
    private boolean d = false;

    public FaceDistanceDetectController(Context context) {
        super(context);
        this.c = context;
        ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.faceDetection.-$$Lambda$FaceDistanceDetectController$-reoRkDnvmimToXkMyWeNzJ1buM
            @Override // java.lang.Runnable
            public final void run() {
                FaceDistanceDetectController.this.c();
            }
        }, 30000L);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemCameraEvent(SystemCameraEvent systemCameraEvent) {
        boolean isCameraEnabled = systemCameraEvent.isCameraEnabled();
        L.camera2.i("%s SystemCameraEvent, isCameraEnabled: %b !!", "[FaceDistanceDetectController]: ", Boolean.valueOf(isCameraEnabled));
        if (this.d) {
            b(Camera2ControlAction.EnumAction.ACTION_QUITE_CHILD_MODE);
            this.d = false;
        }
        if (!isCameraEnabled) {
            l();
        } else if (Screen.getInstance().isLockScreenDisplaying()) {
            L.camera2.d("%s lookScreen displaying is true return !!", "[FaceDistanceDetectController]: ");
        } else {
            p();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScreenOnOffEvent(ScreenOnOffEvent screenOnOffEvent) {
        boolean isScreenOn = screenOnOffEvent.isScreenOn();
        L.camera2.d("%s onScreenOnOffEvent isScreenOn: %s !!", "[FaceDistanceDetectController]: ", Boolean.valueOf(isScreenOn));
        if (!isScreenOn) {
            l();
        } else if (Screen.getInstance().isLockScreenDisplaying()) {
            L.camera2.d("%s lookScreen displaying is true not start !!", "[FaceDistanceDetectController]: ");
        } else {
            p();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScreenSaverSwitchEvent(ScreenSaverSwitchEvent screenSaverSwitchEvent) {
        boolean screenSaverSwitchOn = screenSaverSwitchEvent.screenSaverSwitchOn();
        L.camera2.d("%s onScreenSaverSwitchEvent screenSaverSwitchOn: %s !!", "[FaceDistanceDetectController]: ", Boolean.valueOf(screenSaverSwitchOn));
        if (screenSaverSwitchOn) {
            l();
        } else if (!Screen.getInstance().isInteractive()) {
            L.camera2.d("%s screen isInteractive is true not start !!", "[FaceDistanceDetectController]: ");
        } else {
            p();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFaceDetectEvent(FaceInfoEvent faceInfoEvent) {
        boolean isDistanceThresholdTriggered = faceInfoEvent.getIsDistanceThresholdTriggered();
        L.camera2.d("%s onFaceDetectEvent isDistanceThresholdTriggered: %s", "[FaceDistanceDetectController]: ", Boolean.valueOf(isDistanceThresholdTriggered));
        if (isDistanceThresholdTriggered) {
            d();
        } else {
            f();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        L.camera2.d("%s receive GestureType: %s", "[FaceDistanceDetectController]: ", gestureControlEvent.gestureType);
        if (SystemSetting.getKeyGestureControlEnable()) {
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            IAppCommandProcessor currentProcessor = ThirdPartyAppProxy.getInstance().getCurrentProcessor();
            if (currentProcessor != null) {
                if ((!ThirdPartyAppProxy.getInstance().isMicoProcessor(currentProcessor) || MicoApplication.isVideoInForeground()) && currentProcessor.supportGesture()) {
                    switch (gestureType) {
                        case FastBackward:
                            if (currentProcessor.preferPreviousAndNextOnGesture()) {
                                ThirdPartyAppProxy.getInstance().previous(this.c);
                                GestureToast.showGesture(this.c, GestureInfoEvent.GestureType.FastBackward, this.c.getResources().getString(R.string.gesture_prev_serials));
                                return;
                            }
                            ThirdPartyAppProxy.getInstance().fastBackward(this.c, a);
                            GestureToast.showBackwardGesture(this.c);
                            return;
                        case FastForward:
                            if (currentProcessor.preferPreviousAndNextOnGesture()) {
                                ThirdPartyAppProxy.getInstance().next(this.c);
                                GestureToast.showGesture(this.c, GestureInfoEvent.GestureType.FastForward, this.c.getResources().getString(R.string.gesture_next_serials));
                                return;
                            }
                            ThirdPartyAppProxy.getInstance().fastForward(this.c, a);
                            GestureToast.showForwardGesture(this.c);
                            return;
                        case Stop:
                            ThirdPartyAppProxy.getInstance().pause(this.c);
                            Context context = this.c;
                            GestureToast.showGesture(context, gestureType, context.getString(R.string.gesture_pause));
                            return;
                        case OK:
                            ThirdPartyAppProxy.getInstance().play(this.c);
                            Context context2 = this.c;
                            GestureToast.showGesture(context2, gestureType, context2.getString(R.string.gesture_resume));
                            return;
                        case ThumbsUp:
                            if (ThirdPartyAppProxy.getInstance().supportLike()) {
                                ThirdPartyAppProxy.getInstance().like(this.c);
                                Context context3 = this.c;
                                GestureToast.showGesture(context3, gestureType, context3.getString(R.string.gesture_thumbs_up));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onQuitChildModeDialogEvent(QuitChildModeDialogEvent quitChildModeDialogEvent) {
        VideoMonitorHelper.getInstance().stopVideoMonitorService();
        if (PreferenceUtils.getSettingInt(this.c, "quite_child_mode_camera_privacy_tip", 0) == 0) {
            new CustomDialog.Builder().setTitleResId(R.string.camera_privacy_dialog_title).setMessageResId(R.string.camera_privacy_dialog_msg).setPositiveResId(R.string.camera_privacy_dialog_positive).setNegativeResId(R.string.camera_privacy_dialog_negative).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.faceDetection.-$$Lambda$FaceDistanceDetectController$L6K4jJ-CjOb8iJ4TYEGZzfJyAfI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FaceDistanceDetectController.this.c(view);
                }
            }).setHasNegativeButton(true).build().show();
        } else {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        PreferenceUtils.setSettingInt(this.c, "quite_child_mode_camera_privacy_tip", 1);
        a();
    }

    private void a() {
        if (CameraStatusHelper.getInstance().isCameraEnabled()) {
            b(Camera2ControlAction.EnumAction.ACTION_QUITE_CHILD_MODE);
        } else {
            b();
        }
    }

    private void b() {
        new CustomDialog.Builder().setTitleResId(R.string.camera_open).setMessageResId(R.string.camera_open_msg).setPositiveResId(R.string.camera_open_open_now).setNegativeResId(R.string.camera_open_hold_on).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.faceDetection.-$$Lambda$FaceDistanceDetectController$7fWyrcq0bxuwSD3-b3Mxc3Bwu4w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FaceDistanceDetectController.this.b(view);
            }
        }).setNegativeOnClickListener($$Lambda$FaceDistanceDetectController$kJlUo7ehVHmruHr1cJ_JPhYI7g.INSTANCE).setOnCancelListener($$Lambda$FaceDistanceDetectController$SB2y6eQCMJ86OCrO_lP4BXbvi1U.INSTANCE).setHasNegativeButton(true).build().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        if (CameraStatusHelper.getInstance().setCameraEnabled(true)) {
            this.d = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraRelatedSwitchEvent(CameraRelatedSwitchEvent cameraRelatedSwitchEvent) {
        L.camera2.d("%s onCameraRelatedSwitchEvent: %s !", "[FaceDistanceDetectController]: ", cameraRelatedSwitchEvent.status.name());
        if (cameraRelatedSwitchEvent.status == CameraRelatedSwitchEvent.event.OPEN) {
            p();
        } else if (cameraRelatedSwitchEvent.status == CameraRelatedSwitchEvent.event.CLOSE) {
            l();
        } else if (cameraRelatedSwitchEvent.status == CameraRelatedSwitchEvent.event.CLOSE_WITH_RELEASE_CAMERA) {
            l();
            m();
        } else if (cameraRelatedSwitchEvent.status == CameraRelatedSwitchEvent.event.DESTROY) {
            o();
        }
        L.camera2.d("%s onCameraRelatedSwitchEvent end !", "[FaceDistanceDetectController]: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSupportGestureDialogEvent(SupportGestureDialogEvent supportGestureDialogEvent) {
        CameraDetectionController.getManager().showSupportGestureNotification(getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCameraSingleSwitchEvent(CameraSingleSwitchEvent cameraSingleSwitchEvent) {
        L.camera2.d("%s onCameraSingleSwitchEvent:  %s  %s !", "[FaceDistanceDetectController]: ", cameraSingleSwitchEvent.action, Boolean.valueOf(cameraSingleSwitchEvent.open));
        if (cameraSingleSwitchEvent.open) {
            b(cameraSingleSwitchEvent.action);
        } else {
            d(cameraSingleSwitchEvent.action);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateBeginEvent(UpdateBeginEvent updateBeginEvent) {
        L.camera2.d("%s onUpdateBeginEvent !", "[FaceDistanceDetectController]: ");
        l();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateCompleteEvent(UpdateCompleteEvent updateCompleteEvent) {
        L.camera2.d("%s onUpdateCompleteEvent !", "[FaceDistanceDetectController]: ");
        p();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        L.camera2.d("open camera service delay !!!");
        if (!SystemSetting.getKeyDistanceProtectionEnable()) {
            L.camera2.d("KeyDistanceProtectionEnable is false.");
        } else if (ChildModeManager.getManager().isChildMode()) {
            L.camera2.d("KeyDistanceProtectionEnable is true, starting.");
            b(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        } else if (!SystemSetting.getKeyDistanceProtectionEnableChildMode()) {
            L.camera2.d("KeyDistanceProtectionEnable is true, starting.");
            b(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        }
        b(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE);
        b(Camera2ControlAction.EnumAction.ACTION_GESTURE);
    }

    private void d() {
        L.camera2.i("%s startDistanceRemindingDialog !", "[FaceDistanceDetectController]: ");
        if (h()) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.faceDetection.-$$Lambda$FaceDistanceDetectController$uMPRjJ75YpV5hQCJEuUp7UVgEbE
                @Override // java.lang.Runnable
                public final void run() {
                    FaceDistanceDetectController.this.e();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void e() {
        g();
        this.b = new FaceDetectDistanceDialog(MicoApplication.getApp());
        if (!this.b.isShown()) {
            L.camera2.d("%s showFaceDetectDialog !", "[FaceDistanceDetectController]: ");
            this.b.show();
        }
    }

    private void f() {
        L.camera2.i("%s stopDistanceRemindingDialog !", "[FaceDistanceDetectController]: ");
        g();
    }

    private synchronized void g() {
        L.camera2.d("%s dismissFaceDetectDialog !", "[FaceDistanceDetectController]: ");
        if (this.b != null) {
            this.b.dismiss();
            this.b = null;
        }
    }

    private boolean h() {
        return SystemSetting.getKeyDistanceProtectionEnable();
    }

    private boolean i() {
        return SystemSetting.getKeyGestureControlEnable();
    }

    private boolean j() {
        return SystemSetting.getKeyQuickEnterChildModeEnable();
    }

    private boolean k() {
        return SystemSetting.getKeyChildContentRecommendationEnable();
    }

    private boolean a(Camera2ControlAction.EnumAction enumAction) {
        switch (enumAction) {
            case ACTION_DISTANCE:
                return h();
            case ACTION_GESTURE:
                return i();
            case ACTION_ENTER_CHILD_MODE:
                return j();
            case ACTION_CHILD_CONTENT_RECOMMENDATION:
                return k();
            default:
                return true;
        }
    }

    private void l() {
        L.camera2.i("close all camera feature");
        d(Camera2ControlAction.EnumAction.ACTION_DESTROY_PREVIEW_ACTIVITY);
        d(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        d(Camera2ControlAction.EnumAction.ACTION_GESTURE);
        d(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE);
        f();
    }

    private void m() {
        d(Camera2ControlAction.EnumAction.ACTION_CHILD_CONTENT_RECOMMENDATION);
        d(Camera2ControlAction.EnumAction.ACTION_RELEASE_CAMERA);
    }

    private void n() {
        Camera2VisualRecognitionManager.getInstance(this.c).requestUnbindCamera2Service();
    }

    private void o() {
        l();
        m();
        n();
    }

    private void p() {
        L.camera2.i("open all camera feature");
        b(Camera2ControlAction.EnumAction.ACTION_CHILD_CONTENT_RECOMMENDATION);
        if (ChildModeManager.getManager().isChildMode()) {
            L.camera2.i("%s in child mode, start action distance", "[FaceDistanceDetectController]: ");
            b(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
        } else {
            b(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE);
            if (!SystemSetting.getKeyDistanceProtectionEnableChildMode()) {
                L.camera2.i("%s not in child mode, all mode use distance  start action distance", "[FaceDistanceDetectController]: ");
                b(Camera2ControlAction.EnumAction.ACTION_DISTANCE);
            } else {
                L.camera2.i("%s not in child mode, only child mode use distance not start!!", "[FaceDistanceDetectController]: ");
            }
        }
        b(Camera2ControlAction.EnumAction.ACTION_GESTURE);
    }

    private void b(Camera2ControlAction.EnumAction enumAction) {
        if (DebugHelper.isAutomatorRun()) {
            L.camera2.i("%s current model is in automatorRun, not start!!", "[FaceDistanceDetectController]: ");
        } else if (Camera2ControlAction.EnumAction.ACTION_GESTURE.equals(enumAction) && !SystemSetting.needGestureFunction(this.c)) {
            L.camera2.i("%s current model not need gesture, not start!!", "[FaceDistanceDetectController]: ");
        } else if (ThirdPartyAppProxy.getInstance().isCurrentAppNeedCamera() || VoipModel.getInstance().isVoipActive()) {
            L.camera2.i("%s current app need camera, not start !!", "[FaceDistanceDetectController]: ");
        } else if (!a(enumAction)) {
            L.camera2.i("%s ignore start action=%s because switch not open", "[FaceDistanceDetectController]: ", enumAction);
        } else {
            c(enumAction);
        }
    }

    private void c(Camera2ControlAction.EnumAction enumAction) {
        if (!CameraStatusHelper.getInstance().isCameraEnabled()) {
            L.camera2.i("%s camera current status: disable, %s  not start !!", "[FaceDistanceDetectController]: ", enumAction);
        } else if (a(enumAction)) {
            Camera2VisualRecognitionManager.getInstance(this.c).requestStartAction(enumAction);
            L.camera2.i("start action  %s", enumAction);
        } else {
            L.camera2.i("not start action  %s because switch not open", enumAction);
        }
    }

    private void d(Camera2ControlAction.EnumAction enumAction) {
        if (enumAction == Camera2ControlAction.EnumAction.ACTION_DISTANCE) {
            EventBusRegistry.getEventBus().post(new FaceInfoEvent(false));
        }
        if (SystemSetting.getKeyDistanceProtectionEnable() || SystemSetting.getKeyQuickEnterChildModeEnable() || SystemSetting.getKeyGestureControlEnable() || SystemSetting.getKeyChildContentRecommendationEnable()) {
            Camera2VisualRecognitionManager.getInstance(this.c).requestStopAction(enumAction);
            L.camera2.i("stop action  %s", enumAction);
            return;
        }
        L.camera2.i("all settings close, stop service ");
        n();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChildContentRecommendCalculateEvent(ChildContentRecommendCalculateEvent childContentRecommendCalculateEvent) {
        L.camera2.d("%s onChildContentRecommendCalculateEvent getIsNeedCalculate: %s", "[FaceDistanceDetectController]: ", Boolean.valueOf(childContentRecommendCalculateEvent.getIsNeedCalculate()));
        Camera2VisualRecognitionManager.getInstance(this.c).sendRequestCmd(Camera2ControlAction.EnumAction.ACTION_VPM_ASR_START);
    }
}
