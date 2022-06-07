package com.xiaomi.micolauncher.module.battery;

import android.content.Context;
import android.view.View;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.controllers.ControllerBase;
import com.xiaomi.micolauncher.application.setup.LogSetup;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.battery.BatteryStatusMonitor;
import com.xiaomi.micolauncher.skills.common.dlna.DlnaDevice;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class BatteryUiController extends ControllerBase {
    private boolean a = true;
    private boolean b = false;
    private FakePlayer c = new FakePlayer(AudioSource.AUDIO_SOURCE_UI);
    private CustomDialog d = null;

    public BatteryUiController(Context context) {
        super(context);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBatteryStatusChangedEvent(BatteryStatusMonitor.BatteryStatusChangedEvent batteryStatusChangedEvent) {
        if (BatteryStatusMonitor.getInstance().isCharging()) {
            L.battery.d("charging, ignore battery status changed event");
            a();
            f();
            return;
        }
        int level = BatteryStatusMonitor.getInstance().getLevel();
        boolean z = BatteryHelper.shouldShowPrompt(level) || (!this.b && level < 5);
        L.battery.d("Not charging, battery status changed event, battery level %s, show notification %s", Integer.valueOf(level), Boolean.valueOf(z));
        if (z) {
            b();
            this.b = true;
            if (level == 20) {
                a(getContext(), level);
            } else if (level == 10) {
                a(getContext(), level, R.string.battery_low_dialog_desc_2);
                d();
            } else if (level == 5) {
                a(getContext(), level, R.string.battery_low_dialog_desc_3);
                d();
            }
        }
    }

    private void a() {
        NotificationHelper.cancelNotification(getContext(), R.string.battery_notification_title);
    }

    private void b() {
        if (!this.b) {
            a(R.raw.power_disconnected);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPowerStatusChangedEvent(BatteryStatusMonitor.PowerStatusChangedEvent powerStatusChangedEvent) {
        int i;
        if (powerStatusChangedEvent.getEventType() == 1) {
            f();
            a();
            e();
            i = R.raw.power_connected;
            StatPoints.recordPoint(StatPoints.Event.power, StatPoints.PowerKey.connect);
        } else {
            i = R.raw.power_disconnected;
            int level = BatteryStatusMonitor.getInstance().getLevel();
            if (level <= 20 && level > 10) {
                a(getContext(), level);
            } else if (level <= 10) {
                L.battery.i("enter battery save mode on level %s", Integer.valueOf(level));
                d();
                a(getContext(), level, level <= 5 ? R.string.battery_low_dialog_desc_3 : R.string.battery_low_dialog_desc_2);
            } else {
                c();
            }
            StatPoints.recordPoint(StatPoints.Event.power, StatPoints.PowerKey.disconnect);
        }
        a(i);
    }

    private void c() {
        if (!BatteryStatusMonitor.getInstance().isCharging()) {
            int level = BatteryStatusMonitor.getInstance().getLevel();
            int b = b(level);
            if (BatteryStatusMonitor.isBatteryLow(level)) {
                c(level);
            } else {
                a(level, b);
            }
        }
    }

    private void a(int i) {
        this.c.start();
        PromptSoundPlayer.getInstance().play(getContext(), i, new PromptSoundPlayer.CompleteListener() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$BatteryUiController$jUQ4K9IUznTNbXjcIPdBjHG85eA
            @Override // com.xiaomi.micolauncher.common.player.PromptSoundPlayer.CompleteListener
            public final void onFinish() {
                BatteryUiController.this.g();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g() {
        this.c.stop();
    }

    private int b(int i) {
        return i / 20;
    }

    private void c(int i) {
        ToastUtil.showToast(getContext().getString(R.string.battery_notification_message_battery_low, Integer.valueOf(i)), 1);
    }

    private void a(int i, int i2) {
        Context context = getContext();
        ToastUtil.showToast(i2 == 0 ? context.getString(R.string.battery_notification_message_battery_low, Integer.valueOf(i)) : context.getString(R.string.battery_notification_message_power_disconnected, Integer.valueOf(i2)));
    }

    private void a(Context context, int i) {
        f();
        this.d = a(context, i, R.string.battery_low_dialog_desc_1, R.string.battery_low_mode, R.string.common_cancel, true).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$BatteryUiController$FNWk8fOuVG3d3GPTJVxPMaIwxis
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BatteryUiController.this.b(view);
            }
        }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.battery.-$$Lambda$BatteryUiController$kKyEsw7czYZilOhBDzWLHROX1HY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BatteryUiController.this.a(view);
            }
        }).build();
        this.d.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        e();
    }

    private void a(Context context, int i, int i2) {
        f();
        this.d = a(context, i, i2, R.string.understand_btn, -1, false).build();
        this.d.show();
    }

    private static CustomDialog.Builder a(Context context, int i, int i2, int i3, int i4, boolean z) {
        CustomDialog.Builder messageResId = new CustomDialog.Builder().setTitle(context.getString(R.string.battery_low_dialog_title, Integer.valueOf(i))).setMessageResId(i2);
        if (i3 == -1) {
            i3 = R.string.battery_low_mode;
        }
        CustomDialog.Builder hasNegativeButton = messageResId.setPositiveResId(i3).setHasPositiveButton(true).setHasNegativeButton(false);
        if (z) {
            hasNegativeButton.setHasNegativeButton(true).setNegativeResId(i4);
        }
        return hasNegativeButton;
    }

    private void d() {
        L.battery.i("BatteryUiController enter low battery mode scheduleScreenOff isLowBattery=false");
        a(LogSetup.getInstance(), 5);
        DlnaDevice.getInstance().stop();
        a(true);
        EnergySaver.getInstance(getContext()).scheduleScreenOff(true);
    }

    private void e() {
        L.battery.i("enter normal mode");
        a(LogSetup.getInstance(), 3);
        DlnaDevice.getInstance().start(getContext());
        a(false);
        EnergySaver.getInstance(getContext()).setPowerOnMode(true);
    }

    private void a(boolean z) {
        BatteryStatusMonitor.getInstance().setPowerSaveMode(z);
        EnergySaver.getInstance(getContext()).setPowerSaveMode(z);
    }

    private void a(LogSetup logSetup, int i) {
        if (logSetup != null && !DebugHelper.isDebugVersion()) {
            logSetup.setLogOutputLevel(i);
        }
    }

    private void f() {
        CustomDialog customDialog = this.d;
        if (customDialog != null) {
            customDialog.dismiss();
            this.d = null;
        }
    }
}
