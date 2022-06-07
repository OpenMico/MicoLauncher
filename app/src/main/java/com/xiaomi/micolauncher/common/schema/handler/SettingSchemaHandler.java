package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.DoorBellStartEvent;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.setting.SettingOpenManager;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.voip.controller.uievent.AlarmEvent;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;

/* loaded from: classes3.dex */
public class SettingSchemaHandler implements SchemaHandler {
    public static final String ACTION_BABY_COURSE = "mico://settings/play_baby_course";
    public static final String ACTION_DOORBELL = "mico://settings/doorbell";
    public static final String ACTION_ENTER_CHILD_MODE = "mico://settings/enter_child_mode";
    public static final String ACTION_SETTINGS = "mico://settings/main";
    public static final String ACTION_WIFI = "mico://settings/wifi";

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "settings".equalsIgnoreCase(uri.getAuthority());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        L.base.i("SettingSchemaHandler process %s", uri);
        String path = uri.getPath();
        if (path != null) {
            char c = 65535;
            switch (path.hashCode()) {
                case -2143808800:
                    if (path.equals("/doorbell")) {
                        c = 2;
                        break;
                    }
                    break;
                case -621696804:
                    if (path.equals("/enter_child_mode")) {
                        c = 4;
                        break;
                    }
                    break;
                case 46749288:
                    if (path.equals("/main")) {
                        c = 1;
                        break;
                    }
                    break;
                case 47054788:
                    if (path.equals("/wifi")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1328109896:
                    if (path.equals("/play_baby_course")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    SettingOpenManager.openWifi(context);
                    return;
                case 1:
                    SettingOpenManager.openSetting(context);
                    return;
                case 2:
                    a();
                    DoorBellStartEvent doorBellStartEvent = new DoorBellStartEvent();
                    doorBellStartEvent.action = true;
                    EventBusRegistry.getEventBus().post(doorBellStartEvent);
                    break;
                case 3:
                    break;
                case 4:
                    ChildModeStorage.getInstance().enterChildMode(context);
                    return;
                default:
                    L.base.e("unexpected path %s", path);
                    return;
            }
            String queryParameter = uri.getQueryParameter(AlarmEvent.KEY_ALARM_ID);
            a();
            PlayerApi.playBabyCourse(queryParameter);
        }
    }

    private void a() {
        Intent intent = new Intent();
        intent.setAction(MicoVoipClient.ACTION_HANG_UP);
        intent.putExtra(MicoVoipClient.EXTRA_IS_FROM_QUERY, false);
        MicoVoipClient.getInstance(MicoApplication.getGlobalContext()).sendMessageToMicoVoipService(MicoVoipClient.MSG_FROM_LAUNCHER_CLIENT_VOIP_EVENT, intent);
    }
}
