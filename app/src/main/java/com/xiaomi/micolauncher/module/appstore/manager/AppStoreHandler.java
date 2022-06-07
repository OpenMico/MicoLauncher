package com.xiaomi.micolauncher.module.appstore.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AppStoreClearEvent;
import com.xiaomi.micolauncher.common.event.AppStoreFreeSpaceEvent;
import com.xiaomi.micolauncher.common.event.AppStoreListChange;
import com.xiaomi.micolauncher.common.event.AppStoreUninstallEvent;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import com.xiaomi.micolauncher.skills.voiceprint.controller.VoicePrintRegisterController;
import java.util.List;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class AppStoreHandler extends BroadcastReceiver {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        char c2 = 65535;
        if (intent.getAction().equals("com.xiaomi.mico.appstore.APP_STORE_ACTION")) {
            String stringExtra = intent.getStringExtra("opt");
            int hashCode = stringExtra.hashCode();
            if (hashCode != -1949240260) {
                if (hashCode != -1304997399) {
                    if (hashCode != 788021532) {
                        if (hashCode == 1316768351 && stringExtra.equals("startApp")) {
                            c2 = 0;
                        }
                    } else if (stringExtra.equals("ttsRequest")) {
                        c2 = 3;
                    }
                } else if (stringExtra.equals("handleSchema")) {
                    c2 = 1;
                }
            } else if (stringExtra.equals("update3rd")) {
                c2 = 2;
            }
            switch (c2) {
                case 0:
                    String stringExtra2 = intent.getStringExtra(IChannel.EXTRA_OPEN_PKG_NAME);
                    L.storage.i("%s :%s,%s", "AppStoreHandler", stringExtra, stringExtra2);
                    ThirdPartyAppProxy.getInstance().startApp(context, stringExtra2);
                    return;
                case 1:
                    String stringExtra3 = intent.getStringExtra("micoAction");
                    L.storage.i("%s :%s,%s", "AppStoreHandler", stringExtra, stringExtra3);
                    SchemaManager.handleSchema(context, stringExtra3);
                    return;
                case 2:
                    L.storage.i("%s :%s", "AppStoreHandler", stringExtra);
                    ThirdPartyAppProxy.getInstance().updateThirdPartyPackagesAndLauncherAsync(context);
                    return;
                case 3:
                    String stringExtra4 = intent.getStringExtra(VoicePrintRegisterController.PARAMS_TTS);
                    L.storage.i("%s :%s,%s", "AppStoreHandler", stringExtra, stringExtra4);
                    SpeechManager.getInstance().ttsRequest(stringExtra4);
                    return;
                default:
                    return;
            }
        } else if (intent.getAction().equals("com.xiaomi.mico.appstore.APP_STORE_EVENT")) {
            String stringExtra5 = intent.getStringExtra("name");
            L.storage.i("%s :%s", "AppStoreHandler", stringExtra5);
            switch (stringExtra5.hashCode()) {
                case -1765714720:
                    if (stringExtra5.equals("AppStoreUninstalledApp")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -168188864:
                    if (stringExtra5.equals("AppStoreFreeSpaceEvent")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 759584217:
                    if (stringExtra5.equals("AppStoreUpgradeResult")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1034680269:
                    if (stringExtra5.equals("AppStoreClearEvent")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1336034247:
                    if (stringExtra5.equals("AppStoreInstalledApp")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1686799726:
                    if (stringExtra5.equals("AppStoreListChange")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    EventBusRegistry.getEventBus().post(new AppStoreListChange());
                    return;
                case 1:
                    String stringExtra6 = intent.getStringExtra("params");
                    if (ContainerUtil.hasData(stringExtra6)) {
                        EventBusRegistry.getEventBus().post((AppStoreFreeSpaceEvent) Gsons.getGson().fromJson(stringExtra6, new TypeToken<AppStoreFreeSpaceEvent>() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreHandler.1
                        }.getType()));
                        return;
                    }
                    return;
                case 2:
                    String stringExtra7 = intent.getStringExtra("params");
                    L.storage.i("%s :%s,uninstalled pkg is : %s", "AppStoreHandler", stringExtra5, stringExtra7);
                    if (ContainerUtil.hasData(stringExtra7)) {
                        AppInfo appInfoByPkg = SkillDataManager.getManager().getAppInfoByPkg(stringExtra7);
                        List<AppInfo> appInfosByTabKey = SkillDataManager.getManager().getAppInfosByTabKey(SkillDataManager.TAB_KEY_MINE);
                        if (appInfosByTabKey == null) {
                            L.skillpage.w("appInfoList is null");
                            return;
                        }
                        int indexOf = appInfosByTabKey.indexOf(appInfoByPkg);
                        if (indexOf != -1) {
                            L.skillpage.i("uninstallApp position : %d", Integer.valueOf(indexOf));
                            SkillDataManager.getManager().deleteRecord(appInfoByPkg);
                            EventBusRegistry.getEventBus().post(new AppStoreUninstallEvent(indexOf));
                            return;
                        }
                        return;
                    }
                    return;
                case 3:
                    String stringExtra8 = intent.getStringExtra("params");
                    L.storage.i("%s :%s,InstalledApp pkg is : %s", "AppStoreHandler", stringExtra5, stringExtra8);
                    if (ContainerUtil.hasData(stringExtra8)) {
                        SkillDataManager.getManager().addRecordByPkg(stringExtra8);
                        return;
                    }
                    return;
                case 4:
                    AppStoreOtaUtils.getInstance().reportMeshGatewayOtaResult(context, intent.getIntExtra("from", 0), intent.getBooleanExtra("result", false), intent.getIntExtra("errCode", 0), (int) intent.getLongExtra("duration", 0L), intent.getStringExtra("version"));
                    return;
                case 5:
                    String stringExtra9 = intent.getStringExtra("params");
                    if (ContainerUtil.hasData(stringExtra9)) {
                        EventBusRegistry.getEventBus().post((AppStoreClearEvent) Gsons.getGson().fromJson(stringExtra9, new TypeToken<AppStoreClearEvent>() { // from class: com.xiaomi.micolauncher.module.appstore.manager.AppStoreHandler.2
                        }.getType()));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
