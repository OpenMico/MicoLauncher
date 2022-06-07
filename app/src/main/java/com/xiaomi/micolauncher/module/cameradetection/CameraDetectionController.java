package com.xiaomi.micolauncher.module.cameradetection;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.SettingSchemaHandler;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.common.utils.VideoMonitorHelper;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.module.cameradetection.view.CountdownNotificationView;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.voip.utils.CameraStatusHelper;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CameraDetectionController {
    public static final String ACTION_NOTICE_CLICK = "com.xiaomi.micolauncher.action.CLICK_NOTIFICATION";
    public static final String ACTION_NOTICE_DELETE = "com.xiaomi.micolauncher.action.DELETE_NOTIFICATION";
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    private static boolean b;
    private static final long a = TimeUnit.HOURS.toMillis(2);
    private static final long c = TimeUnit.SECONDS.toMillis(1);

    /* loaded from: classes3.dex */
    public static class a {
        private static final CameraDetectionController a = new CameraDetectionController();
    }

    public static CameraDetectionController getManager() {
        return a.a;
    }

    public void showSupportGestureNotification(Context context) {
        if (SystemSetting.needGestureFunction(context) && SystemSetting.getKeyGestureControlEnable()) {
            L.camera2.i("show support gesture dialog and open gesture service");
            if (CameraStatusHelper.getInstance().isCameraEnabled()) {
                NotificationHelper.notify(context, 5);
            }
        }
    }

    public void showQuickEnterChildDialog(Context context) {
        if (Hardware.hasCameraCapability(context) && ActivityLifeCycleManager.isValidActivity(ActivityLifeCycleManager.getInstance().getTopActivity())) {
            SystemSetting.setKeyHasShowQuickEnterChildDialog(true);
            final CustomDialog build = new CustomDialog.Builder().setTitleResId(R.string.setting_child_mode_quick_enter).setMessageResId(R.string.setting_child_mode_quick_enter_tip).setPositiveResId(R.string.distance_protection_open_now).setNegativeResId(R.string.child_mode_hold_on).build();
            build.show();
            build.getPositiveButton().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.cameradetection.-$$Lambda$CameraDetectionController$s-X4-PkE6iyBByAPrnapJe1Vi0U
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CameraDetectionController.a(CustomDialog.this, view);
                }
            });
        }
    }

    public static /* synthetic */ void a(CustomDialog customDialog, View view) {
        if (VideoMonitorHelper.getInstance().isVideoMonitorRunning()) {
            ToastUtil.showToast((int) R.string.video_monitor_running);
            return;
        }
        SystemSetting.setKeyQuickEnterChildModeEnable(true);
        customDialog.dismiss();
    }

    /* loaded from: classes3.dex */
    public static class b extends BroadcastReceiver {
        boolean a = false;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.base.d("onReceive: action->%s", intent.getAction());
            if (CountDownModel.getInstance().getStatus() == CountDownModel.CountDownStatus.FIRING) {
                CountDownModel.getInstance().cancel();
            }
            if (TextUtils.equals(action, CameraDetectionController.ACTION_NOTICE_DELETE)) {
                try {
                    context.unregisterReceiver(this);
                } catch (IllegalArgumentException e) {
                    L.base.w(e);
                    e.printStackTrace();
                }
                if (!this.a && !CameraDetectionController.b) {
                    L.camera2.i("notification auto dismiss");
                    boolean unused = CameraDetectionController.b = true;
                    ThreadUtil.postDelayedInMainThread($$Lambda$CameraDetectionController$b$zCwMzpaNGUpZHebb92lKs9su8ds.INSTANCE, CameraDetectionController.a);
                }
            } else if (TextUtils.equals(action, CameraDetectionController.ACTION_NOTICE_CLICK)) {
                SchemaManager.handleSchema(context, SettingSchemaHandler.ACTION_ENTER_CHILD_MODE);
                this.a = true;
            }
        }

        public static /* synthetic */ void a() {
            L.camera2.i("two hours pass");
            boolean unused = CameraDetectionController.b = false;
        }
    }

    public void showEnterChildModeNotification(final Context context, int i) {
        if (context != null && SystemSetting.getKeyQuickEnterChildModeEnable() && !ChildModeManager.getManager().isChildMode()) {
            if (i == 1) {
                EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE, false));
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.cameradetection.-$$Lambda$CameraDetectionController$1shjto3w6Jd_y4ZuAWUOZgxfIEc
                    @Override // java.lang.Runnable
                    public final void run() {
                        CameraDetectionController.a(context);
                    }
                });
            } else if (i == 3 && !b) {
                b bVar = new b();
                context.registerReceiver(bVar, new IntentFilter(ACTION_NOTICE_DELETE));
                Intent action = new Intent().setAction(ACTION_NOTICE_DELETE);
                action.putExtra("NOTICE_ID", R.string.notify_adult_child_use);
                context.registerReceiver(bVar, new IntentFilter(ACTION_NOTICE_CLICK));
                Intent action2 = new Intent().setAction(ACTION_NOTICE_CLICK);
                action2.putExtra("NOTICE_ID", R.string.notify_adult_child_use);
                new MicoNotification.Builder(context).setNotificationId(R.string.notify_adult_child_use).setTimeoutAfter(7).setSmallIcon(R.drawable.notice_child).setText(context.getString(R.string.notify_click_to_child_mode)).setTitle(context.getString(R.string.notify_adult_child_use)).setPendingIntent(PendingIntent.getBroadcast(context, R.string.notify_adult_child_use, action2, 268435456)).setDeleteIntent(PendingIntent.getBroadcast(context, R.string.notify_adult_child_use, action, 268435456)).build().show();
            }
        }
    }

    public static /* synthetic */ void a(final Context context) {
        new CountdownNotificationView.Builder(context).setTitle(context.getString(R.string.notify_only_child_use)).setCountdownTime(Constants.TimeConstants.SECONDS_10S_TO_MILLIS).setOnMicoNotificationViewListener(new CountdownNotificationView.OnMicoNotificationViewListener() { // from class: com.xiaomi.micolauncher.module.cameradetection.-$$Lambda$CameraDetectionController$1xew3VLN6P-igWL-YDL6kYloTuA
            @Override // com.xiaomi.micolauncher.module.cameradetection.view.CountdownNotificationView.OnMicoNotificationViewListener
            public final void onDismiss() {
                CameraDetectionController.b(context);
            }
        }).build().show();
    }

    public static /* synthetic */ void b(Context context) {
        ChildModeStorage.getInstance().enterChildMode(context);
    }
}
