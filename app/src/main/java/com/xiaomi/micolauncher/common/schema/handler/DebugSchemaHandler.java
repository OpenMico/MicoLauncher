package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.crash.DeviceInfo;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;

/* loaded from: classes3.dex */
public class DebugSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "debug".equalsIgnoreCase(uri.getAuthority());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        int indexOf;
        String path = uri.getPath();
        if (path != null) {
            char c = 65535;
            int hashCode = path.hashCode();
            if (hashCode != -1466506495) {
                if (hashCode != -185856737) {
                    if (hashCode == 1722810181 && path.equals("/device")) {
                        c = 2;
                    }
                } else if (path.equals("/playing")) {
                    c = 1;
                }
            } else if (path.equals("/playlist")) {
                c = 0;
            }
            switch (c) {
                case 0:
                    LocalPlayerManager.getInstance().dumpPlaylist();
                    break;
                case 1:
                    Remote.Response.PlayingData currentPlayingData = MediaSessionController.getInstance().getCurrentPlayingData();
                    if (currentPlayingData == null) {
                        L.debug.i("No playing info");
                        break;
                    } else {
                        L.debug.json(Gsons.getGson().toJson(currentPlayingData));
                        break;
                    }
                case 2:
                    L.debug.json(Gsons.getGson().toJson(DeviceInfo.get(context)));
                    if (MiotDeviceManager.getInstance().getMiotAccessToken() != null) {
                        L.debug.i("Miot Session Token: session: %s, accessToken: %s", MiotDeviceManager.getInstance().getMiotAccessToken().getSessionId(), MiotDeviceManager.getInstance().getMiotAccessToken().getAccessToken());
                    } else {
                        L.debug.i("Miot Session Token is empty");
                    }
                    L.debug.i("Current User:%s", TokenManager.getInstance().getUserId());
                    break;
                default:
                    L.debug.w("path %s ignored", path);
                    break;
            }
            if (path.startsWith("/time") && (indexOf = path.indexOf(47, 5)) > 0) {
                long parseLong = Long.parseLong(path.substring(indexOf + 1));
                MiotDeviceManager.getInstance().disableSyncTime();
                SystemClock.setCurrentTimeMillis(parseLong);
                AlarmModel.getInstance().resetAlarmAfterTimeSync();
            }
        }
    }
}
