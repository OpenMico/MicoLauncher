package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.net.Uri;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;
import com.xiaomi.micolauncher.module.cameradetection.event.CameraSingleSwitchEvent;

/* loaded from: classes3.dex */
public class SettingsTestSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "test".equalsIgnoreCase(uri.getAuthority());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        L.base.i("SettingsTest process %s", uri);
        String path = uri.getPath();
        if (path != null) {
            char c = 65535;
            switch (path.hashCode()) {
                case -1893444210:
                    if (path.equals("/distanceOpen")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1694795593:
                    if (path.equals("/eyeProtectOpen")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1543243038:
                    if (path.equals("/distanceOpenOnlyChild")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1010247381:
                    if (path.equals("/eyeProtectClose")) {
                        c = '\b';
                        break;
                    }
                    break;
                case -41318238:
                    if (path.equals("/nearWakeupClose")) {
                        c = '\n';
                        break;
                    }
                    break;
                case 691764832:
                    if (path.equals("/nearWakeupOpen")) {
                        c = '\t';
                        break;
                    }
                    break;
                case 885386413:
                    if (path.equals("/childContentProtectOpen")) {
                        c = 5;
                        break;
                    }
                    break;
                case 1084168439:
                    if (path.equals("/wakeupSoundClose")) {
                        c = '\f';
                        break;
                    }
                    break;
                case 1164879838:
                    if (path.equals("/gestureClose")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1421580084:
                    if (path.equals("/distanceClose")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1423411108:
                    if (path.equals("/gestureOpen")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1665983477:
                    if (path.equals("/childContentProtectClose")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1974996843:
                    if (path.equals("/wakeupSoundOpen")) {
                        c = 11;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    MicoSettings.setGestureControlEnable(context.getApplicationContext(), true);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_GESTURE, true));
                    return;
                case 1:
                    MicoSettings.setGestureControlEnable(context.getApplicationContext(), false);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_GESTURE, false));
                    return;
                case 2:
                    MicoSettings.setDistanceProtectionEnable(context.getApplicationContext(), true);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                    return;
                case 3:
                    MicoSettings.setDistanceProtectionEnableChildMode(context.getApplicationContext(), true);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, true));
                    return;
                case 4:
                    MicoSettings.setDistanceProtectionEnable(context.getApplicationContext(), false);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_DISTANCE, false));
                    return;
                case 5:
                    MicoSettings.setChildContentRecommendationEnable(context.getApplicationContext(), true);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_CHILD_CONTENT_RECOMMENDATION, true));
                    return;
                case 6:
                    MicoSettings.setChildContentRecommendationEnable(context.getApplicationContext(), false);
                    EventBusRegistry.getEventBus().post(new CameraSingleSwitchEvent(Camera2ControlAction.EnumAction.ACTION_CHILD_CONTENT_RECOMMENDATION, false));
                    return;
                case 7:
                    MicoSettings.setEyeProtectionEnable(context.getApplicationContext(), true);
                    return;
                case '\b':
                    MicoSettings.setEyeProtectionEnable(context.getApplicationContext(), false);
                    return;
                case '\t':
                    MicoSettings.setNearbyWakeupEnable(context.getApplicationContext(), true);
                    return;
                case '\n':
                    MicoSettings.setNearbyWakeupEnable(context.getApplicationContext(), false);
                    return;
                case 11:
                    MicoSettings.setWakeupSoundEnable(context.getApplicationContext(), true);
                    return;
                case '\f':
                    MicoSettings.setWakeupSoundEnable(context.getApplicationContext(), false);
                    return;
                default:
                    L.base.e("unexpected path %s", path);
                    return;
            }
        }
    }
}
