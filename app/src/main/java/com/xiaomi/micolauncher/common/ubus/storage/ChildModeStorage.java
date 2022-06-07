package com.xiaomi.micolauncher.common.ubus.storage;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import com.google.gson.JsonParseException;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.settingslib.core.ChildModeConfig;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.WakeupPlayer;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;
import com.xiaomi.micolauncher.module.child.util.ChildStatHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeRecommendApiHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.ChildModeConfigChangedEvent;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.OpenChildProtectModeEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.ShowQueryDistanceProtectEvent;

/* loaded from: classes3.dex */
public class ChildModeStorage {
    public static final String KEY_DISTANCE_CHILDMODE_QUERY_COUNT = "KEY_DISTANCE_CHILDMODE_QUERY_COUNT";
    public static final int MAX_DISTANCE_CHILDMODE_QUERY_COUNT = 3;
    private final Object a;
    @GuardedBy("childModeConfigLock")
    private ChildModeConfig b;

    private ChildModeStorage() {
        this.a = new Object();
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final ChildModeStorage a = new ChildModeStorage();
    }

    public static ChildModeStorage getInstance() {
        return a.a;
    }

    public boolean onConfigChange(Context context, @NonNull ChildModeConfig childModeConfig, boolean z) {
        boolean z2;
        boolean z3;
        if (DebugHelper.isChangeModeNotExecuted()) {
            L.childmode.i("notEnterChildModeFile exits, not executed");
            return false;
        }
        synchronized (this.a) {
            if (this.b != null && childModeConfig.isChildMode == this.b.isChildMode) {
                z2 = false;
                this.b = childModeConfig;
            }
            z2 = true;
            this.b = childModeConfig;
        }
        if (childModeConfig.isChildMode) {
            L.childmode.i("enter child mode");
        } else {
            L.childmode.i("quit child mode ");
        }
        if (z2) {
            EventBusRegistry.getEventBus().post(new ChildModeConfigChangedEvent());
            if (childModeConfig.isChildMode) {
                ChildModeRecommendApiHelper.getInstance().loadRecommendForChildMode(context);
                ChildStatHelper.recordChildModeSwitch(ChildStatHelper.ChildStatKey.CHILD_MODE_OPEN, z ? ChildStatHelper.SwitchChildMode.OPEN_FROM_APP : ChildStatHelper.SwitchChildMode.OPEN_FROM_LAUNCHER);
            } else {
                ChildModeRecommendApiHelper.getInstance().release();
                ChildStatHelper.recordChildModeSwitch(ChildStatHelper.ChildStatKey.CHILD_MODE_CLOSE, ChildStatHelper.SwitchChildMode.CLOSE);
            }
        }
        ChildModeManager.sendCurrentChildModeStatusBroadcast(context, childModeConfig.isChildMode);
        boolean isQuickEnterChildModeEnable = MicoSettings.isQuickEnterChildModeEnable(context);
        if (!childModeConfig.isChildMode || SystemSetting.getKeyHasShowQuickEnterChildDialog() || isQuickEnterChildModeEnable) {
            z3 = false;
        } else {
            CameraDetectionController.getManager().showQuickEnterChildDialog(context);
            z3 = true;
        }
        if (isQuickEnterChildModeEnable) {
            EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_ENTER_CHILD_MODE, !childModeConfig.isChildMode));
        }
        boolean isDistanceProtectionEnable = MicoSettings.isDistanceProtectionEnable(context);
        boolean isDistanceProtectionEnableChildMode = MicoSettings.isDistanceProtectionEnableChildMode(context);
        if (childModeConfig.isChildMode) {
            if (isDistanceProtectionEnable) {
                if (isDistanceProtectionEnableChildMode) {
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                }
            } else if (z3) {
                return true;
            } else {
                if (PreferenceUtils.getSettingInt(context, KEY_DISTANCE_CHILDMODE_QUERY_COUNT, 0) < 3) {
                    EventBusRegistry.getEventBus().post(new ShowQueryDistanceProtectEvent());
                }
            }
        } else if (isDistanceProtectionEnableChildMode) {
            EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, false));
        }
        EventBusRegistry.getEventBus().post(new OpenChildProtectModeEvent(childModeConfig.isChildMode));
        if (ChildModeConfig.getChildModeConfig(context).isChildMode != childModeConfig.isChildMode) {
            ActivityLifeCycleManager.getInstance().gotoMainActivity(context);
            if (!MediaSessionController.getInstance().isMiplayUsing()) {
                PlayerApi.pause();
            }
        }
        return true;
    }

    public void enterChildMode(Context context) {
        ChildModeConfig childModeConfig = ChildModeConfig.getChildModeConfig(context);
        childModeConfig.isChildMode = true;
        if (onConfigChange(context, childModeConfig, false)) {
            ChildModeConfig.saveChildModeConfig(context, childModeConfig);
        }
        L.base.i("enterChildMode");
        WakeupPlayer.onToneVendorChanged(context);
        StatPoints.recordPoint(StatPoints.Event.micolog_feature, StatPoints.FeatureKey.kid_mode_action, "on");
    }

    public void quitChildMode(Context context) {
        ChildModeConfig childModeConfig = ChildModeConfig.getChildModeConfig(context);
        childModeConfig.isChildMode = false;
        if (onConfigChange(context, childModeConfig, false)) {
            ChildModeConfig.saveChildModeConfig(context, childModeConfig);
        }
        L.base.i("quitChildMode");
        WakeupPlayer.onToneVendorChanged(context);
        StatPoints.recordPoint(StatPoints.Event.micolog_feature, StatPoints.FeatureKey.kid_mode_action, "off");
    }

    public ChildModeConfig load(Context context) {
        ChildModeConfig childModeConfig = this.b;
        if (childModeConfig != null) {
            return childModeConfig;
        }
        ChildModeConfig childModeConfig2 = ChildModeConfig.getChildModeConfig(context);
        synchronized (this.a) {
            this.b = childModeConfig2;
        }
        return childModeConfig2;
    }

    public ChildModeConfig loadFromPref(Context context) {
        String settingString = PreferenceUtils.getSettingString(context, "com.xiaomi.micolauncher.common.ubus.storage.childModeKey", null);
        if (TextUtils.isEmpty(settingString)) {
            return new ChildModeConfig();
        }
        return (ChildModeConfig) Gsons.getGson().fromJson(settingString, (Class<Object>) ChildModeConfig.class);
    }

    /* loaded from: classes3.dex */
    public static class JsonConverter {
        public static ChildModeConfig fromStr(String str) {
            try {
                return (ChildModeConfig) Gsons.getGson().fromJson(str, (Class<Object>) ChildModeConfig.class);
            } catch (JsonParseException e) {
                L.childmode.e("parse %s", str, e);
                return null;
            }
        }
    }
}
